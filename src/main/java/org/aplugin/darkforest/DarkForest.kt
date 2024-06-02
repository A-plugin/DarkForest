package org.aplugin.darkforest

import net.kyori.adventure.text.Component
import org.aplugin.darkforest.Cmds.DarkCommands
import org.aplugin.darkforest.GUI.Enchant
import org.aplugin.darkforest.GUI.Teams
import org.aplugin.darkforest.GUI.Force
import org.aplugin.darkforest.Listener.Listeners
import org.aplugin.darkforest.System.Avatar
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

class DarkForest : JavaPlugin(), Listener {
    override fun onEnable() {
        // Plugin startup logic
        if (Instance != null) return
        Instance = this
        logger.info("${ChatColor.WHITE}==================/DarkForest/==================")

        logger.info("${ChatColor.LIGHT_PURPLE}\t\t\t\t\t\tBy.APO2073")
        saveDefaultConfig()

        //===================[EventListener]===================
        server.pluginManager.registerEvents(Listeners(), this)
        server.pluginManager.registerEvents(Force(), this)
        server.pluginManager.registerEvents(Enchant(), this)
        server.pluginManager.registerEvents(Avatar(), this)
        server.pluginManager.registerEvents(Teams(),this)
        server.pluginManager.registerEvents(this,this)

        //===================[Commands]===================
        DarkCommands().DarkForest(this)

        //===================[Teams]===================
        /*
        val teams = config.getList("teams")
        if (teams != null) {
            for (t in teams) {
                if (t == null) break
                val score: Scoreboard = Bukkit.getScoreboardManager().mainScoreboard
                if (score.teams.any {team1-> team1.name!=t.toString() }) {
                    val team=score.registerNewTeam(t.toString())
                    val owner = config.getString("$t.owner")
                    val members = config.getList("$t.mb")
                    if (owner != null) {
                        val ownerPlayer = Bukkit.getPlayer(owner)
                        if (ownerPlayer != null) team.addPlayer(ownerPlayer)
                    }
                    if (members != null) for (member in members) {
                        if (member == null) break
                        val memberPlayer = Bukkit.getPlayer(member.toString())
                        if (memberPlayer != null) team.addPlayer(memberPlayer)
                    }
                }

            }
        }
        */

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        var Instance: DarkForest? = null
    }
}
