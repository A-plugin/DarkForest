package org.aplugin.darkforest.System

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class Avatar:Listener {
    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        val p = e.player
        val l = p.location
        if (p.gameMode != GameMode.SPECTATOR) {
            p.world.spawn(l, ArmorStand::class.java) { a: ArmorStand ->
                val PlayerH = ItemStack(Material.PLAYER_HEAD)
                val Ph = PlayerH.itemMeta as SkullMeta
                Ph.setOwningPlayer(p)
                Ph.setDisplayName(p.name)
                PlayerH.setItemMeta(Ph)
                a.isSmall = true
                a.setHelmet(PlayerH)
                a.setChestplate(ItemStack(Material.LEATHER_CHESTPLATE))
                a.setLeggings(ItemStack(Material.LEATHER_LEGGINGS))
                a.setBoots(ItemStack(Material.LEATHER_BOOTS))
                a.setGravity(false)
                a.addScoreboardTag(p.name)
                a.customName = p.name
                a.isCustomNameVisible = true
                a.isPersistent = true
                a.isSilent = true
            }
        }
    }

    @EventHandler
    fun onPlayerInteractAtEntityEvent(e: PlayerInteractAtEntityEvent) {
        if (e.rightClicked is ArmorStand) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player

        for (world in Bukkit.getServer().worlds) {
            for (entity in world.entities) {
                if (entity !is ArmorStand) continue

                if (entity.getCustomName() == null || !entity.getCustomName()!!.contains(player.name)) continue

                player.teleport(entity.getLocation())
                entity.remove()
            }
        }
    }
}