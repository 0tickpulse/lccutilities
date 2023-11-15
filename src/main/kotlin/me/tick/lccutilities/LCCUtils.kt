package me.tick.lccutilities

import me.tick.lccutilities.listeners.ConditionsEvent
import me.tick.lccutilities.listeners.MechanicsEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * KotlinPluginTemplate plugin
 */
class LCCUtils : JavaPlugin() {

    override fun onEnable() {
        // ensure config file exists
        saveDefaultConfig()

        logger.info("${description.name} version ${description.version} enabled!")

        // register events
        server.pluginManager.registerEvents(MechanicsEvent(), this)
        server.pluginManager.registerEvents(ConditionsEvent(), this)
    }

    override fun onDisable() {
        logger.info("${description.name} disabled.")
    }
}
