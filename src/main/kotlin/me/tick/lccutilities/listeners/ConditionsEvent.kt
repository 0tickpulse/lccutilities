package me.tick.lccutilities.listeners

import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent
import me.tick.lccutilities.mythic.conditions.CanAttackCondition
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ConditionsEvent : Listener {
    @EventHandler
    fun onConditionsLoad(e: MythicConditionLoadEvent?) {
        if (e == null) {
            return
        }
        when (e.conditionName.lowercase()) {
            "canattack", "attackable" -> e.register(CanAttackCondition(e.config))
        }
    }
}
