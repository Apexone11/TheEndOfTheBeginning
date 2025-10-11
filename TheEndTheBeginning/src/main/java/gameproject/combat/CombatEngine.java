package gameproject.combat;

import java.util.*;
import main.model.Player;
import gameproject.Monster;

/**
 * Advanced Combat Engine for "The End The Beginning"
 * Handles complex combat mechanics including:
 * - Miss/Hit/Critical Hit calculations
 * - Status effects and their durations
 * - Combat animations and timing
 * - Special abilities and combos
 * - Environmental effects
 * 
 * @author Abdul Fornah
 * @version 4.0.0 - Advanced Combat System
 */
public class CombatEngine {
    
    public enum AttackResult {
        MISS,
        HIT,
        CRITICAL_HIT,
        BLOCKED,
        PARRIED,
        COUNTERED
    }
    
    public enum StatusEffect {
        POISON(3, -5, "💚 Poison"),
        BURN(2, -8, "🔥 Burn"),
        FREEZE(1, 0, "🧊 Freeze"),
        STUN(1, 0, "⚡ Stun"),
        RAGE(3, 10, "😡 Rage"),
        BLESSED(5, 5, "✨ Blessed"),
        CURSED(4, -10, "💀 Cursed"),
        HASTE(3, 0, "💨 Haste"),
        SHIELD(2, 0, "🛡️ Shield"),
        REGENERATION(4, 3, "💚 Regeneration");
        
        public final int duration;
        public final int damagePerTurn;
        public final String displayName;
        
        StatusEffect(int duration, int damagePerTurn, String displayName) {
            this.duration = duration;
            this.damagePerTurn = damagePerTurn;
            this.displayName = displayName;
        }
    }
    
    public static class CombatResult {
        public AttackResult result;
        public int damage;
        public List<StatusEffect> appliedEffects;
        public String description;
        public boolean targetDefeated;
        
        public CombatResult() {
            this.appliedEffects = new ArrayList<>();
        }
    }
    
    private static final Random random = new Random();
    
    /**
     * Calculates the result of a player attack on a monster
     */
    public static CombatResult playerAttackMonster(Player player, Monster monster, AttackType attackType) {
        CombatResult result = new CombatResult();
        
        // Calculate base accuracy (85% base + agility bonus)
        double accuracy = 0.85 + (player.getAgility() * 0.002);
        
        // Apply status effect modifiers
        if (player.hasStatusEffect(StatusEffect.HASTE)) accuracy += 0.15;
        if (player.hasStatusEffect(StatusEffect.CURSED)) accuracy -= 0.20;
        if (monster.hasStatusEffect(StatusEffect.SHIELD)) accuracy -= 0.25;
        
        double roll = random.nextDouble();
        
        // Determine attack result
        if (roll > accuracy) {
            result.result = AttackResult.MISS;
            result.damage = 0;
            result.description = "🎯 Your attack misses!";
            return result;
        }
        
        // Check for critical hit (Rogues have higher crit chance)
        double critChance = player.getPlayerClass() == Player.PlayerClass.ROGUE ? 0.25 : 0.15;
        if (player.hasStatusEffect(StatusEffect.RAGE)) critChance += 0.20;
        
        boolean isCritical = random.nextDouble() < critChance;
        
        // Calculate base damage
        int baseDamage = player.getAttackPower();
        
        // Apply attack type modifiers
        switch (attackType) {
            case HEAVY_ATTACK:
                baseDamage = (int)(baseDamage * 1.5);
                if (random.nextDouble() < 0.3) {
                    result.appliedEffects.add(StatusEffect.STUN);
                }
                break;
            case QUICK_ATTACK:
                baseDamage = (int)(baseDamage * 0.8);
                accuracy += 0.1; // Already calculated above
                break;
            case MAGIC_ATTACK:
                baseDamage = player.getBaseMagic() + (player.getLevel() * 2);
                if (random.nextDouble() < 0.4) {
                    StatusEffect[] magicEffects = {StatusEffect.BURN, StatusEffect.FREEZE, StatusEffect.POISON};
                    result.appliedEffects.add(magicEffects[random.nextInt(magicEffects.length)]);
                }
                break;
            case SPECIAL_ABILITY:
                baseDamage = calculateSpecialAbilityDamage(player);
                applySpecialAbilityEffects(player, result);
                break;
        }
        
        // Apply critical hit multiplier
        if (isCritical) {
            result.result = AttackResult.CRITICAL_HIT;
            baseDamage = (int)(baseDamage * 2.0);
            result.description = "💥 CRITICAL HIT! ";
        } else {
            result.result = AttackResult.HIT;
            result.description = "⚔️ You strike ";
        }
        
        // Apply random variance (±15%)
        double variance = 0.85 + (random.nextDouble() * 0.3);
        int finalDamage = (int)(baseDamage * variance);
        
        // Apply monster's defense
        int defense = monster.getDefense();
        finalDamage = Math.max(1, finalDamage - defense);
        
        result.damage = finalDamage;
        result.description += "the " + monster.getName() + " for " + finalDamage + " damage!";
        
        // Apply damage and check if monster is defeated
        monster.takeDamage(finalDamage);
        result.targetDefeated = !monster.isAlive();
        
        return result;
    }
    
    /**
     * Calculates the result of a monster attack on the player
     */
    public static CombatResult monsterAttackPlayer(Monster monster, Player player) {
        CombatResult result = new CombatResult();
        
        // Monster accuracy (varies by monster type)
        double accuracy = monster.getAccuracy();
        
        // Player dodge chance (based on agility and class)
        double dodgeChance = player.getAgility() * 0.001;
        if (player.getPlayerClass() == Player.PlayerClass.ROGUE) dodgeChance += 0.15;
        if (player.hasStatusEffect(StatusEffect.HASTE)) dodgeChance += 0.20;
        if (player.hasStatusEffect(StatusEffect.FREEZE)) dodgeChance = 0;
        
        double roll = random.nextDouble();
        
        if (roll < dodgeChance) {
            result.result = AttackResult.MISS;
            result.damage = 0;
            result.description = "💨 You dodge the " + monster.getName() + "'s attack!";
            return result;
        }
        
        if (roll > accuracy) {
            result.result = AttackResult.MISS;
            result.damage = 0;
            result.description = "🎯 The " + monster.getName() + "'s attack misses!";
            return result;
        }
        
        // Calculate damage
        int baseDamage = monster.calculateDamage();
        
        // Apply monster's special attack effects
        if (monster.useSpecialAttack()) {
            baseDamage = (int)(baseDamage * monster.getSpecialAttackMultiplier());
            result.appliedEffects.addAll(monster.getSpecialAttackEffects());
            result.description = "💀 " + monster.getName() + " uses " + monster.getSpecialAbility() + "! ";
        } else {
            result.description = "🗡️ " + monster.getName() + " attacks! ";
        }
        
        // Apply player's defense
        int defense = player.getDefensePower();
        if (player.hasStatusEffect(StatusEffect.SHIELD)) defense += 10;
        
        int finalDamage = Math.max(1, baseDamage - defense);
        
        // Check for player block/parry (Warriors have higher chance)
        double blockChance = player.getPlayerClass() == Player.PlayerClass.WARRIOR ? 0.25 : 0.10;
        if (player.hasStatusEffect(StatusEffect.SHIELD)) blockChance += 0.30;
        
        if (random.nextDouble() < blockChance) {
            result.result = AttackResult.BLOCKED;
            finalDamage = finalDamage / 2;
            result.description += "🛡️ You block some of the damage! ";
        } else {
            result.result = AttackResult.HIT;
        }
        
        result.damage = finalDamage;
        result.description += "You take " + finalDamage + " damage!";
        
        // Apply damage and check if player is defeated
        player.takeDamage(finalDamage);
        result.targetDefeated = !player.isAlive();
        
        return result;
    }
    
    /**
     * Processes status effects at the end of each turn
     */
    public static List<String> processStatusEffects(Player player, Monster monster) {
        List<String> messages = new ArrayList<>();
        
        // Process player status effects
        Map<StatusEffect, Integer> playerEffects = new HashMap<>(player.getStatusEffects());
        for (Map.Entry<StatusEffect, Integer> entry : playerEffects.entrySet()) {
            StatusEffect effect = entry.getKey();
            int remainingTurns = entry.getValue();
            
            // Apply effect
            if (effect.damagePerTurn != 0) {
                if (effect.damagePerTurn > 0) {
                    player.heal(effect.damagePerTurn);
                    messages.add("💚 " + effect.displayName + " heals you for " + effect.damagePerTurn + " HP!");
                } else {
                    player.takeDamage(-effect.damagePerTurn);
                    messages.add("💀 " + effect.displayName + " damages you for " + (-effect.damagePerTurn) + " HP!");
                }
            }
            
            // Decrease duration
            remainingTurns--;
            if (remainingTurns <= 0) {
                player.removeStatusEffect(effect);
                messages.add("✨ " + effect.displayName + " effect wears off.");
            } else {
                player.updateStatusEffect(effect, remainingTurns);
            }
        }
        
        // Process monster status effects
        Map<StatusEffect, Integer> monsterEffects = new HashMap<>(monster.getStatusEffects());
        for (Map.Entry<StatusEffect, Integer> entry : monsterEffects.entrySet()) {
            StatusEffect effect = entry.getKey();
            int remainingTurns = entry.getValue();
            
            // Apply effect
            if (effect.damagePerTurn != 0) {
                if (effect.damagePerTurn > 0) {
                    monster.heal(effect.damagePerTurn);
                    messages.add("💚 " + monster.getName() + " is healed by " + effect.displayName + "!");
                } else {
                    monster.takeDamage(-effect.damagePerTurn);
                    messages.add("💀 " + monster.getName() + " takes " + (-effect.damagePerTurn) + " damage from " + effect.displayName + "!");
                }
            }
            
            // Decrease duration
            remainingTurns--;
            if (remainingTurns <= 0) {
                monster.removeStatusEffect(effect);
                messages.add("✨ " + effect.displayName + " wears off " + monster.getName() + ".");
            } else {
                monster.updateStatusEffect(effect, remainingTurns);
            }
        }
        
        return messages;
    }
    
    private static int calculateSpecialAbilityDamage(Player player) {
        switch (player.getPlayerClass()) {
            case WARRIOR:
                return (int)(player.getAttackPower() * 2.5); // Berserker Strike
            case MAGE:
                return player.getBaseMagic() * 3; // Arcane Blast
            case ROGUE:
                return (int)(player.getAttackPower() * 1.8); // Poison Strike
            default:
                return player.getAttackPower();
        }
    }
    
    private static void applySpecialAbilityEffects(Player player, CombatResult result) {
        switch (player.getPlayerClass()) {
            case WARRIOR:
                // Berserker Strike: Apply Rage to self
                result.appliedEffects.add(StatusEffect.RAGE);
                break;
            case MAGE:
                // Arcane Blast: Random magical effect
                StatusEffect[] mageEffects = {StatusEffect.BURN, StatusEffect.FREEZE, StatusEffect.STUN};
                result.appliedEffects.add(mageEffects[random.nextInt(mageEffects.length)]);
                break;
            case ROGUE:
                // Poison Strike: Apply poison
                result.appliedEffects.add(StatusEffect.POISON);
                break;
        }
    }
    
    public enum AttackType {
        NORMAL_ATTACK,
        HEAVY_ATTACK,
        QUICK_ATTACK,
        MAGIC_ATTACK,
        SPECIAL_ABILITY,
        DEFENSIVE_STANCE
    }
}