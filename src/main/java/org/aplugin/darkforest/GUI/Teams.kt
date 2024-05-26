package org.aplugin.darkforest.GUI

import net.kyori.adventure.text.Component
import org.aplugin.darkforest.DarkForest
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.scoreboard.ScoreboardManager
import org.bukkit.scoreboard.Team

class Teams:Listener {
    val df=DarkForest.Instance
    val manager=Bukkit.getScoreboardManager()
    val board=manager.newScoreboard

    fun open(p:Player) {
        val i=Bukkit.createInventory(p,9,"§a팀")

        val addTeam=ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val addMeta=addTeam.itemMeta
        addMeta.displayName(Component.text("§a팀 생성하기"))
        addTeam.itemMeta=addMeta
        i.setItem(8,addTeam)

        val teamList = df?.config?.getStringList("teams") ?: mutableListOf()
        for (teamName in teamList) {
            val owner = df?.config?.getString("$teamName.name")
            if (owner != null) {
                val skull = ItemStack(Material.PLAYER_HEAD)
                val skullMeta = skull.itemMeta as SkullMeta
                skullMeta.owner=owner
                skullMeta.displayName(Component.text(teamName))
                skull.itemMeta = skullMeta
                i.addItem(skull)
            }
        }

        p.openInventory(i)
    }

    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (e.view.title.contains("팀")) {
            e.isCancelled=true
            if (e.currentItem==null) return
            if (e.slot==8) {
                addTeam(e.whoClicked.name,e.whoClicked as Player)
                e.whoClicked.closeInventory()
            }
            if (e.currentItem!!.itemMeta.hasDisplayName() && e.slot!=8) {
                joinTeam(e.currentItem!!.itemMeta.displayName,e.whoClicked as Player)
            }
        }
    }

    @EventHandler
    fun onJoin(e:PlayerJoinEvent) {
        open(e.player)
    }

    @EventHandler
    fun openGUI(e:PlayerSwapHandItemsEvent) {
        if (e.player.isSneaking) {
            open(e.player)
        }
    }

    fun addTeam(name: String, owner: Player) {
        val list=df?.config?.getStringList("teams")
        if (list?.size!! >=5) return
        if (board.getTeam(name)!=null) {
            owner.sendMessage("${ChatColor.RED}동일한 가문이 이미 존재합니다!")
            return
        }

        val team=board.registerNewTeam(name)

        team.addPlayer(owner)
        team.displayName(Component.text(name))
        team.prefix(Component.text("§a[$name]§f"))
        df?.logger?.info(board.teams.toList().toString())

        val teamsPath = "teams"

        val teamList = df?.config?.getStringList(teamsPath) ?: mutableListOf()
        teamList.add(name)
        df?.config?.set(teamsPath, teamList)

        df?.config?.set("$name.name", name)
        df?.config?.set("$name.owner", owner.uniqueId.toString())
        df?.config?.set("$name.members", listOf(owner.uniqueId.toString()))

        df?.saveConfig()

        owner.sendMessage("${ChatColor.AQUA}새로운 가문을 개척해갑니다.")
        val beacon=ItemStack(Material.BEACON)
        val bme=beacon.itemMeta
        bme.setDisplayName("파일런")
        beacon.itemMeta=bme
        owner.inventory.addItem(beacon)

        owner.closeInventory()
    }

    fun joinTeam(name: String, member:Player) {
        val team=board.getTeam(name) ?:run {
            member.sendMessage("§c팀을 찾을수 없습니다. 팀: $name")
            return
        }
        val mem = df?.config?.getStringList("$name.members") ?: mutableListOf()
        mem.add(member.uniqueId.toString())
        df?.config?.set("$name.members",mem)
        df?.saveConfig()

        team.addPlayer(member)

        member.closeInventory()
        member.sendMessage("§a$name'의 가문에 참여했습니다.")
    }

}