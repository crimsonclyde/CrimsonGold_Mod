package com.example.crimsongold.util;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import java.util.List;

public class ModUtils {
    private static final List<String> VANISH_MESSAGES = List.of(
            "has left the building, but is waiting just outside!",
            "wandered off for a moment — no worries!",
            "gone fishin’ — back before the sun sets!",
            "vanished into thin air, but never too far away.",
            "says goodbye… only for now.",
            "stepped away briefly and left the lights on.",
            "went to grab a snack. Be right back!",
            "is playing hide and seek. No peeking!",
            "took a short detour through a portal. Back soon!",
            "set off on a tiny adventure.",
            "needs a quick power nap.",
            "slipped into the shadows… just resting there.",
            "went chasing butterflies in the Aether.",
            "is consulting the ancient scrolls. BRB.",
            "stepped out for a breath of fresh air.",
            "will be back after these short messages!",
            "paused the game — not the friendship.",
            "is just around the corner, humming softly.",
            "wandered beyond render distance.",
            "took a coffee break with the villagers.",
            "left a warm spot behind — still close by.",
            "is reorganizing their inventory.",
            "went to watch the sunset.",
            "is resting safely at spawn.",
            "temporarily AFK, permanently appreciated.",
            "took a moment to recharge their hearts.",
            "exploring gently — nothing to worry about.",
            "is waiting patiently for your return.",
            "stepped out, but never left your world.",
            "logged out with a smile.",
            "gone exploring, carrying good vibes.",
            "just a short pause in the adventure.",
            "left footprints — they lead back here.");

    public static Component getRandomVanishMessage(Entity entity, RandomSource random) {
        String randomMsg = VANISH_MESSAGES.get(random.nextInt(VANISH_MESSAGES.size()));
        return Component.literal(entity.getDisplayName().getString() + ": " + randomMsg);
    }
}
