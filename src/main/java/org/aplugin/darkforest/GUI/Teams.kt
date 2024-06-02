package org.aplugin.darkforest.GUI

import org.aplugin.darkforest.DarkForest
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

class Teams:Listener {
    private val inventory: Inventory=Bukkit.createInventory(null, 9, "§a팀")
    val df=DarkForest.Instance
    val config=df!!.config
    private var score: Scoreboard = Bukkit.getScoreboardManager().mainScoreboard
    private var team: Team? = null
    private val TlistC: MutableList<String> = config.getList("teams")?.filterIsInstance<String>()?.toMutableList() ?: mutableListOf()

    fun open(player: Player){
        inventory.clear()
        val addTeam=ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val meta=addTeam.itemMeta
        meta.setDisplayName("${ChatColor.GREEN}팀 생성하기")
        addTeam.itemMeta = meta
        inventory.setItem(8,addTeam)

        val teams=config.getList("teams")
        if (teams != null) {
            for (team in teams) {
                if (team==null) break
                val head= ItemStack(Material.PLAYER_HEAD)
                val skMeta:SkullMeta= head.itemMeta as SkullMeta
                skMeta.setDisplayName(team.toString())
                skMeta.owner=team.toString()
                val lore=mutableListOf<String>()
                lore.addFirst("${ChatColor.RED}${ChatColor.BOLD}Owner: §f$team")
                val m=config.getList("${team}.mb")
                lore.add("${ChatColor.BOLD}${ChatColor.GREEN}Member")
                if (m!=null) for (mb in m) {
                    lore.add("${ChatColor.BOLD}§f - $mb")
                    lore.removeIf { l->l.contains("인원") }
                    lore.addLast("${ChatColor.YELLOW}${ChatColor.BOLD}인원수: ${m.size+1}")
                }
                if (m==null) {
                    lore.add("${ChatColor.BOLD}§f - None")
                    lore.addLast("${ChatColor.YELLOW}${ChatColor.BOLD}인원수: ${1}")

                }
                skMeta.lore=lore.toMutableList()
                head.itemMeta=skMeta
                inventory.addItem(head)
            }
        }

        player.openInventory(inventory)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        if (event.player.hasPlayedBefore()) return
        open(event.player)
    }

    @EventHandler
    fun 나중에지울거(event: PlayerSwapHandItemsEvent) {
        event.isCancelled=true
        if (event.player.isSneaking) open(event.player)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) return
        if (!event.view.title.contains("팀")) return
        if (event.clickedInventory==null) return
        if (event.clickedInventory?.type?.equals(InventoryType.PLAYER)!!) return
        event.isCancelled=true
        if (event.currentItem==null) return
        val item=event.currentItem
        if (item?.itemMeta!=null) {
            val name=item.itemMeta.displayName
            val player=event.whoClicked as Player
            if (item.type.equals(Material.LIME_STAINED_GLASS_PANE)) {
                addTeam(player.name, player)
                return
            }
            joinTeam(name,player)
        }
    }

    private fun addTeam(name:String, player: Player){
        val Tlist=score.teams
        if (Tlist.size>=5) return
        if (Tlist.any { team -> team.name == name }) return

        team=score.registerNewTeam(name)
        team!!.addPlayer(player)

        TlistC.add(name)
        config.set("teams",TlistC)
        config.set("${name}.owner",player.name)

        player.sendMessage(score.teams.toString())
        player.sendMessage("${ChatColor.GREEN}새로운 팀을 만들었습니다.")
        df?.saveConfig()
        player.closeInventory()
    }

    private fun joinTeam(name:String ,player: Player){
        val member=config.getList("${name}.mb")?.filterIsInstance<String>()?.toMutableList() ?: mutableListOf()
        member.add(player.name)
        team=score.getTeam(name)
        team!!.addPlayer(player)
        config.set("${name}.mb",member)
        df?.saveConfig()
        player.sendMessage("${ChatColor.GREEN}팀 ${name}에 참여했습니다")
        player.closeInventory()
    }

    fun TeamAverage():Int {
        val teams=config.getList("teams")

        return 0
    }
}