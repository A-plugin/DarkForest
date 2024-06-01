package org.aplugin.darkforest

import org.aplugin.darkforest.Cmds.DarkCommands
import org.aplugin.darkforest.GUI.Enchant
import org.aplugin.darkforest.GUI.Teams
import org.aplugin.darkforest.GUI.Upgrade
import org.aplugin.darkforest.Listener.Listeners
import org.aplugin.darkforest.System.Avatar
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class DarkForest : JavaPlugin(), Listener {
    override fun onEnable() {
        // Plugin startup logic
        if (Instance != null) return
        Instance = this
        logger.info("${ChatColor.WHITE}==================/DarkForest/==================")

        logger.info("${ChatColor.LIGHT_PURPLE}\t\t\t\t     By.APO2073")
        saveDefaultConfig()

        //===================[EventListener]===================
        server.pluginManager.registerEvents(Listeners(), this)
        server.pluginManager.registerEvents(Upgrade(), this)
        server.pluginManager.registerEvents(Enchant(), this)
        server.pluginManager.registerEvents(Avatar(), this)
        server.pluginManager.registerEvents(Teams(),this)
        server.pluginManager.registerEvents(this,this)

        //===================[Commands]===================
        DarkCommands().DarkForest(this)



    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        var Instance: DarkForest? = null
    }
}
