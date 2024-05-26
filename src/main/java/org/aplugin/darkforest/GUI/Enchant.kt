package org.aplugin.darkforest.GUI

import org.aplugin.darkforest.DarkForest
import org.aplugin.darkforest.System.Enchantable
import org.aplugin.darkforest.System.ItemNameTranslator
import org.aplugin.darkforest.System.StarS
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import java.util.*

class Enchant: Listener {
    var star: StarS = StarS()
    var df = DarkForest.Instance
    var enchantable= Enchantable()
    var translator= ItemNameTranslator()

    fun open(p:Player) {
        val i= Bukkit.createInventory(p,9*3,"§b인첸트")
        p.openInventory(i)
    }

    @EventHandler
    fun openGUI(e:InventoryOpenEvent) {
        if (e.inventory.type==InventoryType.ENCHANTING) {
            e.isCancelled=true
            open(e.player as Player)
        }
    }

    @EventHandler
    fun onClose(e:InventoryCloseEvent) {
        if (e.view.title.contains("인첸트")) {
            val i = e.inventory
            if (i.getItem(13) != null) {
                val iu = i.getItem(13)!!.clone()
                e.player.inventory.addItem(iu)
            }
        }
    }

    @EventHandler
    fun onClick(e:InventoryClickEvent) {
        val i = e.inventory
        val ci = e.clickedInventory
        val it = e.currentItem
        val p=e.whoClicked

        if (ci == null) return
        if (!e.view.title.contains("인첸트")) return
        if (it == null) return


        if (e.isShiftClick) {
            e.isCancelled=true
            if (e.isLeftClick) {
                val item = it.clone()
                val meta = item.itemMeta
                item.itemMeta = meta
                e.currentItem!!.amount = 0
                i.setItem(13, item)
            }
            if (e.isRightClick) {
                if (ci.type == InventoryType.PLAYER) return
                if (i.getItem(13) != null) {
                    if (e.currentItem == null) return
                    val itemMeta = e.currentItem!!.itemMeta
                    val iu = i.getItem(13)!!.clone()
                    iu.setItemMeta(itemMeta)
                    e.whoClicked.inventory.addItem(iu)
                    i.clear(13)
                }
            }
        }

        if (ci.type==InventoryType.PLAYER) return
        e.isCancelled=true
        if (!enchantable.enchantment(it)) return
        if (star.Stage(it)!=0) return
        if (!p.inventory.contains(Material.AMETHYST_SHARD)) return

        p.inventory.contents
            .filterNotNull()
            .find { it.type == Material.AMETHYST_SHARD }
            ?.let { it.amount -= 1 }

        val meta=it.itemMeta
        if (meta.hasEnchants()) {
            meta.enchants.keys.forEach(meta::removeEnchant)
        }

        val enchantments: List<Enchantment> = ArrayList(Arrays.asList(*Enchantment.values()))

        val random = Random()
        for (l in 0..1) {
            val randomEnchantment = enchantments[random.nextInt(enchantments.size)]
            val maxLevel = randomEnchantment.maxLevel
            val enchantLevel = random.nextInt(maxLevel) + 1
            meta.addEnchant(randomEnchantment, enchantLevel, true)
        }
        val enchantCount = random.nextInt(4)
        for (v in 0 until enchantCount) {
            val randomEnchantment = enchantments[random.nextInt(enchantments.size)]
            if (!meta.hasEnchant(randomEnchantment)) {
                val maxLevel = randomEnchantment.maxLevel
                val enchantLevel = random.nextInt(maxLevel) + 1
                meta.addEnchant(randomEnchantment, enchantLevel, true)
            }
        }

        it.setItemMeta(meta)
    }
}