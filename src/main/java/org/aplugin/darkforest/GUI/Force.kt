package org.aplugin.darkforest.GUI

import net.kyori.adventure.text.Component
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.aplugin.darkforest.DarkForest
import org.aplugin.darkforest.System.Enchantable
import org.aplugin.darkforest.System.ItemNameTranslator
import org.aplugin.darkforest.System.StarS
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.EquipmentSlot
import java.util.*
import java.util.function.Predicate

class Force:Listener {
    var star:StarS=StarS()
    var df =DarkForest.Instance
    var enchantable=Enchantable()
    var translator= ItemNameTranslator()



    fun open(p: Player) {
        val i= Bukkit.createInventory(p,3*9, Component.text("§d강화"))
        p.openInventory(i)
    }

    @EventHandler
    fun openGUI(e:InventoryOpenEvent) {
        if (e.inventory.type == InventoryType.ANVIL) {
            val p:Player= e.player as Player
            e.isCancelled = true
            open(p)
        }
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val i = e.inventory
        val ci = e.clickedInventory
        val it = e.currentItem
        val p=e.whoClicked

        if (ci == null) return
        if (!e.view.title.contains("강화")) return
        if (it == null) return

        if (e.isShiftClick ) {
            e.isCancelled=true
            if (e.isLeftClick) {
                val item = it.clone()
                val meta = item.itemMeta
                val stage = star.Stage(item)
                if (stage != 0) {
                    val lore: MutableList<String> = meta.lore?.toMutableList() ?: mutableListOf()
                    lore.removeIf(Predicate { l:String->l.contains("%") })
                    if (stage!=10) {
                        lore.add(ChatColor.GREEN.toString() + "§l -성공 확률: ${star.p(stage)}%")
                        lore.add(ChatColor.RED.toString() + "§l -실패 확률: ${100 - star.p(stage) - star.b(stage)}%")
                        lore.add(ChatColor.GRAY.toString() + "§l -파괴 확률: ${star.b(stage)}%")
                    }

                    meta.lore = lore
                }
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
                    var lore = itemMeta.lore
                    if (lore == null) lore = ArrayList()
                    lore.removeIf(Predicate { l: String -> l.contains("%") })
                    itemMeta.lore = lore
                    iu.setItemMeta(itemMeta)
                    e.whoClicked.inventory.addItem(iu)
                    i.clear(13)
                }
            }
        }

        if (ci.type == InventoryType.PLAYER) return
        e.isCancelled=true
        if (!enchantable.enchantment(it)) return
        val random:Random=Random()
        val meta=it.itemMeta
        if (!p.inventory.contains(Material.ECHO_SHARD)) return
        p.inventory.contents
            .filterNotNull()
            .find { it.type == Material.ECHO_SHARD }
            ?.let { it.amount -= 1 }

        val pp = random.nextInt(100)
        val stage: Int = star.Stage(it)
        val suc: Double = star.p(star.Stage(it)).toDouble()

        if (stage == 10) {
            if (i.getItem(13) == null) return
            val itemMeta = e.currentItem!!.itemMeta
            val iu = i.getItem(13)!!.clone()
            iu.setItemMeta(itemMeta)
            e.whoClicked.inventory.addItem(iu)
            i.clear(13)
        }

        if (stage == 9) {
            val text = TextComponent(ChatColor.YELLOW.toString() + "전설의 장비가 탄생했습니다!")
            text.hoverEvent = HoverEvent(
                HoverEvent.Action.SHOW_TEXT, ComponentBuilder(
                    p.getName() + "의 "
                            + enchantable.type(e.currentItem!!)
                ).create()
            )
            Bukkit.spigot().broadcast(text)

            val lore: MutableList<String> = meta.lore?.toMutableList() ?: mutableListOf()
            lore.removeIf { line: String -> line.contains("★") }
            lore.removeIf { line: String -> line.contains(":") }
            lore.add(ChatColor.YELLOW.toString() + "★★★★★★★★★★")
            meta.setDisplayName(
                ChatColor.YELLOW.toString() + p.name + "의 " + translator.translate(
                    it.getType().toString()
                )
            )
            if (enchantable.sword(it)) {
                meta.addAttributeModifier(
                    Attribute.GENERIC_ATTACK_SPEED,
                    AttributeModifier(
                        UUID.randomUUID(),
                        Attribute.GENERIC_ATTACK_SPEED.name,
                        1000.0,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HAND
                    )
                )
                meta.addAttributeModifier(
                    Attribute.GENERIC_ATTACK_DAMAGE,
                    AttributeModifier(
                        UUID.randomUUID(),
                        Attribute.GENERIC_ATTACK_DAMAGE.name,
                        enchantable.damage(it) * 0.65,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HAND
                    )
                )
            }
            if (enchantable.armor(it)) {
                if (enchantable.Helmet(it))
                    meta.addAttributeModifier(
                    Attribute.GENERIC_ARMOR, AttributeModifier(
                        UUID.randomUUID(),
                        Attribute.GENERIC_ARMOR.name,
                        enchantable.getArmorDefense(it.getType()) * 1.8,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HEAD
                    )
                )
                if (enchantable.Chestplate(it)) meta.addAttributeModifier(
                    Attribute.GENERIC_ARMOR, AttributeModifier(
                        UUID.randomUUID(),
                        Attribute.GENERIC_ARMOR.name,
                        enchantable.getArmorDefense(it.getType()) * 1.8,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.CHEST
                    )
                )
                if (enchantable.Leggings(it)) meta.addAttributeModifier(
                    Attribute.GENERIC_ARMOR, AttributeModifier(
                        UUID.randomUUID(),
                        Attribute.GENERIC_ARMOR.name,
                        enchantable.getArmorDefense(it.getType()) * 1.8,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.LEGS
                    )
                )
                if (enchantable.Boots(it)) meta.addAttributeModifier(
                    Attribute.GENERIC_ARMOR, AttributeModifier(
                        UUID.randomUUID(),
                        Attribute.GENERIC_ARMOR.name,
                        enchantable.getArmorDefense(it.getType()) * 1.8,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.FEET
                    )
                )

                if (it.getType() == Material.NETHERITE_HELMET) {
                    meta.addAttributeModifier(
                        Attribute.GENERIC_ARMOR_TOUGHNESS,
                        AttributeModifier(
                            UUID.randomUUID(),
                            Attribute.GENERIC_ARMOR_TOUGHNESS.name,
                            3.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.HEAD
                        )
                    )
                    meta.addAttributeModifier(
                        Attribute.GENERIC_KNOCKBACK_RESISTANCE, AttributeModifier(
                            UUID.randomUUID(),
                            Attribute.GENERIC_KNOCKBACK_RESISTANCE.name,
                            1.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.HEAD
                        )
                    )
                }
                if (it.getType() == Material.NETHERITE_CHESTPLATE) {
                    meta.addAttributeModifier(
                        Attribute.GENERIC_ARMOR_TOUGHNESS,
                        AttributeModifier(
                            UUID.randomUUID(),
                            Attribute.GENERIC_ARMOR_TOUGHNESS.name,
                            3.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.CHEST
                        )
                    )
                    meta.addAttributeModifier(
                        Attribute.GENERIC_KNOCKBACK_RESISTANCE, AttributeModifier(
                            UUID.randomUUID(),
                            Attribute.GENERIC_KNOCKBACK_RESISTANCE.name,
                            1.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.CHEST
                        )
                    )
                }
                if (it.getType() == Material.NETHERITE_LEGGINGS) {
                    meta.addAttributeModifier(
                        Attribute.GENERIC_ARMOR_TOUGHNESS,
                        AttributeModifier(
                            UUID.randomUUID(),
                            Attribute.GENERIC_ARMOR_TOUGHNESS.name,
                            3.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.LEGS
                        )
                    )
                    meta.addAttributeModifier(
                        Attribute.GENERIC_KNOCKBACK_RESISTANCE, AttributeModifier(
                            UUID.randomUUID(),
                            Attribute.GENERIC_KNOCKBACK_RESISTANCE.name,
                            1.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.LEGS
                        )
                    )
                }
                if (it.getType() == Material.NETHERITE_BOOTS) {
                    meta.addAttributeModifier(
                        Attribute.GENERIC_ARMOR_TOUGHNESS,
                        AttributeModifier(
                            UUID.randomUUID(),
                            Attribute.GENERIC_ARMOR_TOUGHNESS.name,
                            3.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.FEET
                        )
                    )
                    meta.addAttributeModifier(
                        Attribute.GENERIC_KNOCKBACK_RESISTANCE, AttributeModifier(
                            UUID.randomUUID(),
                            Attribute.GENERIC_KNOCKBACK_RESISTANCE.name,
                            1.0,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.FEET
                        )
                    )
                }
            }
            meta.isUnbreakable=true

            if (it.getItemMeta().hasEnchants()) {
                for (en in meta.getEnchants().keys) {
                    val ml = en.maxLevel
                    meta.addEnchant(en, ml, true)
                }
            }

            meta.setLore(lore)
            it.setItemMeta(meta)
            return
        }

        if (suc - star.b(stage) - 3 >= pp) {
            val lore: MutableList<String> = meta.lore?.toMutableList() ?: mutableListOf()
            lore.removeIf { line: String -> line.contains("★") }
            lore.removeIf { line: String -> line.contains("%") }
            lore.add(ChatColor.YELLOW.toString() + star.StageS(it))
            lore.add(ChatColor.GREEN.toString() + "§l -성공 확률: " + star.p(star.Stage(it) + 1) + "%")
            val br = (100 - star.p(star.Stage(it)) - star.b(star.Stage(it))).toDouble()
            lore.add(ChatColor.RED.toString() + "§l -실패 확률: " + br + "%")
            lore.add(ChatColor.GRAY.toString() + "§l -파괴 확률: " + star.b(star.Stage(it) + 1) + "%")
            meta.setLore(lore)
        }
        if (stage > 5) {
            if (star.b(star.Stage(it)) + 1 >= pp) {
                it.setAmount(0)
                Bukkit.broadcastMessage(ChatColor.RED.toString() + "누군가의 장비가 파괴되었습니다!")
            }
        }

        it.setItemMeta(meta)
    }

    @EventHandler
    fun Colse(e: InventoryCloseEvent) {
        if (e.view.title.contains("강화")) {
            val i = e.inventory
            if (i.getItem(13) != null) {
                val iu = i.getItem(13)!!.clone()
                e.player.inventory.addItem(iu)
            }
        }
    }

}