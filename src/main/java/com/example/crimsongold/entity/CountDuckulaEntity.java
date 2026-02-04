package com.example.crimsongold.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.Nullable;

public class CountDuckulaEntity extends TamableAnimal {

    private int feedingCount = 0;

    public CountDuckulaEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
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
            // Look for Fuchsky nearby
            java.util.List<FuchskyEntity> foxes = this.level().getEntitiesOfClass(FuchskyEntity.class,
                    this.getBoundingBox().inflate(12.0D),
                    fox -> fox.isTame() && fox.isOwnedBy(player));

            if (!foxes.isEmpty()) {
                // Apply Regeneration II for 10 seconds (redundant if both apply, but Refreshing
                // duration is fine)
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.REGENERATION, 200, 1));
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));

        // Sit goal must be high priority
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));

        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, true));

        // Follow owner when tamed - Fixed constructor: (entity, speed, minDistance,
        // maxDistance)
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));

        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // Only attack players if NOT tamed
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return !CountDuckulaEntity.this.isTame() && super.canUse();
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false; // Not breedable with food
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
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENDERMAN_TELEPORT,
                        this.getSoundSource(), 1.0F, 1.0F);
                ((ServerLevel) this.level()).sendParticles(net.minecraft.core.particles.ParticleTypes.PORTAL,
                        this.getX(), this.getY() + 0.5D, this.getZ(), 20, 0.5D, 0.5D, 0.5D, 0.1D);
                // Message
                player.sendSystemMessage(
                        com.example.crimsongold.util.ModUtils.getRandomVanishMessage(this, this.getRandom()));
                // Despawn
                this.discard();
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // Feeding logic for taming
        if (itemstack.is(Items.PUMPKIN_PIE) && !this.isTame()) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.feedingCount++;
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

                if (this.feedingCount >= 3) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte) 7); // Heart particles
                    player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                            net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 3600, 1));
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6); // Smoke particles
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // Sit/Stand Logic (Custom to ensure reliable toggling)
        if (this.isTame() && this.isOwnedBy(player) && !this.isFood(itemstack) && !itemstack.is(Items.PUMPKIN_PIE)) {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("FeedingCount", this.feedingCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.feedingCount = compound.getInt("FeedingCount");
    }

    /** Spawn rule: only at night AND in low light, similar to other monsters. */
    public static boolean canSpawnHere(EntityType<CountDuckulaEntity> type, ServerLevelAccessor level,
            MobSpawnType spawnType,
            BlockPos pos, RandomSource random) {
        if (spawnType == MobSpawnType.SPAWNER)
            return true;

        if (level.getDifficulty() == Difficulty.PEACEFUL)
            return false;

        if (level.getLevel().isDay())
            return false;

        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);

        return blockLight <= 7 && skyLight <= 7 && checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    public void checkDespawn() {
        if (this.isTame()) {
            this.noActionTime = 0; // Reset despawn timer
            return;
        }
        super.checkDespawn();
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
        return null; // Not currently breedable
    }
}
