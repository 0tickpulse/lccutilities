package me.tick.lccutilities.mythic.conditions

import com.google.common.base.Function
import io.lumine.mythic.api.adapters.AbstractEntity
import io.lumine.mythic.api.config.MythicLineConfig
import io.lumine.mythic.api.skills.conditions.IEntityComparisonCondition
import io.lumine.mythic.core.skills.SkillCondition
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier
import java.util.*

class CanAttackCondition(mlc: MythicLineConfig) : SkillCondition(mlc.line), IEntityComparisonCondition {
    val cause: DamageCause

    companion object {
        var isRunning: Boolean = false
    }

    init {
        cause = mlc.getEnum(arrayOf("cause", "c", "damagecause", "dc"), DamageCause::class.java, DamageCause.CUSTOM)
    }

    override fun check(caster: AbstractEntity, target: AbstractEntity): Boolean {
        if (isRunning) {
            return false
        }
        val attacker: Entity = caster.bukkitEntity
        val targetEntity: Entity = target.bukkitEntity
        isRunning = true
        val damageMap = EnumMap<DamageModifier, Double>(DamageModifier::class.java)
        damageMap.put(DamageModifier.BASE, 0.0)
        val modifierFunctions = EnumMap<DamageModifier, Function<Double, Double>>(DamageModifier::class.java)
        modifierFunctions.put(DamageModifier.BASE) { input -> input }
        val event = EntityDamageByEntityEvent(
            attacker,
            targetEntity,
            cause,
            damageMap,
            modifierFunctions.toMap(),
            false
        )
        return try {
            Bukkit.getPluginManager().callEvent(event)
            !event.isCancelled()
        } catch (e: IllegalStateException) {
            false
        } finally {
            isRunning = false
        }
    }
}