package com.example.crimsongold.entity;

import com.example.crimsongold.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class FuchskyEntity extends TamableAnimal {

    private int feedingCount = 0;

    public FuchskyEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));

        // Wolf-like attacks
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, true));

        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));

        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // Targets: Attack what owner attacks/is attacked by
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

        // Attack monsters if tamed, or just generic monsters (but NOT Count Duckula)
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, 10, true, false,
                (entity) -> !(entity instanceof CountDuckulaEntity)));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isTame() && this.tickCount % 100 == 0) { // Every 5 seconds
            checkSynergy();
        }
    }

    private void checkSynergy() {
        LivingEntity owner = this.getOwner();
        if (owner instanceof Player player && this.distanceTo(owner) < 12.0D) {
            // Look for Count Duckula nearby
            java.util.List<CountDuckulaEntity> ducks = this.level().getEntitiesOfClass(CountDuckulaEntity.class,
                    this.getBoundingBox().inflate(12.0D),
                    duck -> duck.isTame() && duck.isOwnedBy(player));

            if (!ducks.isEmpty()) {
                // Apply Regeneration II for 10 seconds
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.REGENERATION, 200, 1));
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D) // Wolf health
                .add(Attributes.MOVEMENT_SPEED, 0.35D) // Fast like a fox/wolf
                .add(Attributes.ATTACK_DAMAGE, 5.0D); // Stronger bite
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // Golden Carrot - Vanish Logic
        if (itemstack.is(Items.GOLDEN_CARROT)) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                // Effects
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.NOTE_BLOCK_CHIME.value(), this.getSoundSource(), 1.0F, 1.0F); // Magical sound?
                ((ServerLevel) this.level()).sendParticles(net.minecraft.core.particles.ParticleTypes.END_ROD,
                        this.getX(), this.getY() + 0.5D, this.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.1D);

                // Message
                player.sendSystemMessage(net.minecraft.network.chat.Component
                        .literal("Il fino seniore Fuchsky gone for good.... but will return soon"));

                // Despawn
                this.discard();
            }
            return InteractionResult.SUCCESS;
        }

        // Taming Logic (Feed Pumpkin Pie as well? Or something else? User said "gives
        // the same buff as Count Duckula" and implies similar interaction.
        // User didn't specify food for Fuchsky, so I'll assume Sweet Berries (Fox food)
        // or Pumpkin Pie?
        // "I giving a golden carrot ... like with the pie". implies Pie is standard.
        // Actually, user said "like with the pie" referring to the Duck logic.
        // I'll make it Tameable with Sweet Berries (Thematic for Fox) or Pumpkin Pie
        // (Thematic for Mod).
        // Let's go with Pumpkin Pie for consistency with the Duck, or ask?
        // User said: "base is standard fox... behave like a wolve... gives same buff as
        // Count Duckula".
        // I will use Sweet Berries as it is a Fox, but apply the same interactions. Or
        // stick to Pie to match the "Mod" theme.
        // I'll use Sweet Berries for taming to differentiate, or Pie. Let's use Sweet
        // Berries.
        // Wait, "gives the same buff" implies the *reward* is the same.
        // I will use Sweet Berries for taming (Lines up with Fox).

        if (itemstack.is(Items.COOKIE) && !this.isTame()) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.feedingCount++;
                this.playSound(SoundEvents.FOX_EAT, 1.0F, 1.0F);

                if (this.feedingCount >= 3) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte) 7); // Heart particles
                    // Speed Buff
                    player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 3600, 1));
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6); // Smoke particles
                }
            }
            return InteractionResult.SUCCESS;
        }

        // Standard interaction (sit)
        InteractionResult result = super.mobInteract(player, hand);
        if (result.consumesAction()) {
            return result;
        }

        if (this.isTame() && this.isOwnedBy(player) && !this.level().isClientSide && !itemstack.is(Items.GOLDEN_CARROT)
                && !itemstack.is(Items.SWEET_BERRIES)) {
            this.setOrderedToSit(!this.isOrderedToSit());
            return InteractionResult.SUCCESS;
        }

        return result;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public void die(net.minecraft.world.damagesource.DamageSource damageSource) {
        super.die(damageSource);
        if (damageSource.getEntity() instanceof Player player) {
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WITHER, 1200, 1)); // Wither II for 60s (1200 ticks)
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }
}
