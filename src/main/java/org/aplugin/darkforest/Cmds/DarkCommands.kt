package org.aplugin.darkforest.Cmds

import org.aplugin.darkforest.DarkForest
import org.aplugin.darkforest.System.ItemNameTranslator
import org.aplugin.darkforest.System.StarS
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class DarkCommands: TabExecutor {

    fun DarkForest(plugin: JavaPlugin) {
        plugin.getCommand("dark")!!.setExecutor(this)
        plugin.getCommand("dark")!!.tabCompleter = this
    }

    val translator=ItemNameTranslator()
    val star=StarS()

    override fun onTabComplete(
        sender: CommandSender,
        cms: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        val list = mutableListOf<String>()
        if (args == null) {
            return list
        }
        if (args.size == 1) {
            list.add("force")
            list.add("event")
        }
        if (args.size == 2) {
            if (args[0].equals("force", ignoreCase = true)) {
                for (i in 0..10) {
                    list.add(i.toString())
                }
            }
            if (args[1].equals("event", ignoreCase = true)) {
                list.add("upgrade")
                list.add("enchant")
            }
        }

        return list
    }

    override fun onCommand(sender: CommandSender, cms: Command, labels: String, args: Array<out String>?): Boolean {
        if (sender is Player) {
            if (args == null) return false
            if (!sender.name.contains("APO")) return false
            if (args.size > 1) {
                if (args[0].equals("force", ignoreCase = true)) {
                    val item=sender.inventory.itemInMainHand
                    val meta= item.itemMeta ?: return false
                    val lore=meta.lore ?: mutableListOf()
                    lore.removeIf { l->l.contains("★") }
                    lore.removeIf {l->l.contains("%")}
                    lore.removeIf {l->l.contains(":")}

                    lore.addFirst("${ChatColor.YELLOW}${star.StageSet(args[1].toInt())}")

                    meta.lore=lore
                    meta.setDisplayName("${ChatColor.YELLOW}${sender.name}의 ${translator.translate(item.type.toString())}")
                    meta.isUnbreakable=true

                    item.itemMeta = meta

                    return true
                }
            }
        }
        return false
    }
}