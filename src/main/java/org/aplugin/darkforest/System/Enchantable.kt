package org.aplugin.darkforest.System

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Enchantable {
    fun armor(itemStack: ItemStack): Boolean {
        val material = itemStack.type
        if (material == Material.LEATHER_BOOTS) return true
        if (material == Material.LEATHER_LEGGINGS) return true
        if (material == Material.LEATHER_CHESTPLATE) return true
        if (material == Material.LEATHER_HELMET) return true
        if (material == Material.IRON_BOOTS) return true
        if (material == Material.IRON_LEGGINGS) return true
        if (material == Material.IRON_CHESTPLATE) return true
        if (material == Material.IRON_HELMET) return true
        if (material == Material.GOLDEN_BOOTS) return true
        if (material == Material.GOLDEN_LEGGINGS) return true
        if (material == Material.GOLDEN_CHESTPLATE) return true
        if (material == Material.GOLDEN_HELMET) return true
        if (material == Material.DIAMOND_BOOTS) return true
        if (material == Material.DIAMOND_LEGGINGS) return true
        if (material == Material.DIAMOND_CHESTPLATE) return true
        if (material == Material.DIAMOND_HELMET) return true
        if (material == Material.NETHERITE_BOOTS) return true
        if (material == Material.NETHERITE_LEGGINGS) return true
        if (material == Material.NETHERITE_CHESTPLATE) return true
        if (material == Material.NETHERITE_HELMET) return true
        return false
    }

    fun Helmet(itemStack: ItemStack): Boolean {
        val material = itemStack.type
        if (material == Material.LEATHER_HELMET) return true
        if (material == Material.IRON_HELMET) return true
        if (material == Material.GOLDEN_HELMET) return true
        if (material == Material.DIAMOND_HELMET) return true
        if (material == Material.NETHERITE_HELMET) return true
        return false
    }

    fun Chestplate(itemStack: ItemStack): Boolean {
        val material = itemStack.type
        if (material == Material.LEATHER_CHESTPLATE) return true
        if (material == Material.IRON_CHESTPLATE) return true
        if (material == Material.GOLDEN_CHESTPLATE) return true
        if (material == Material.DIAMOND_CHESTPLATE) return true
        if (material == Material.NETHERITE_CHESTPLATE) return true
        return false
    }

    fun Leggings(itemStack: ItemStack): Boolean {
        val material = itemStack.type
        if (material == Material.LEATHER_LEGGINGS) return true
        if (material == Material.IRON_LEGGINGS) return true
        if (material == Material.GOLDEN_LEGGINGS) return true
        if (material == Material.DIAMOND_LEGGINGS) return true
        if (material == Material.NETHERITE_LEGGINGS) return true
        return false
    }

    fun Boots(itemStack: ItemStack): Boolean {
        val material = itemStack.type
        if (material == Material.LEATHER_BOOTS) return true
        if (material == Material.IRON_BOOTS) return true
        if (material == Material.GOLDEN_BOOTS) return true
        if (material == Material.DIAMOND_BOOTS) return true
        if (material == Material.NETHERITE_BOOTS) return true
        return false
    }

    fun Stuff(itemStack: ItemStack): Boolean {
        val material = itemStack.type
        if (material == Material.WOODEN_SWORD) return true
        if (material == Material.STONE_SWORD) return true
        if (material == Material.IRON_SWORD) return true
        if (material == Material.GOLDEN_SWORD) return true
        if (material == Material.DIAMOND_SWORD) return true
        if (material == Material.NETHERITE_SWORD) return true
        if (material == Material.WOODEN_AXE) return true
        if (material == Material.STONE_AXE) return true
        if (material == Material.IRON_AXE) return true
        if (material == Material.GOLDEN_AXE) return true
        if (material == Material.DIAMOND_AXE) return true
        if (material == Material.NETHERITE_AXE) return true
        if (material == Material.WOODEN_PICKAXE) return true
        if (material == Material.STONE_PICKAXE) return true
        if (material == Material.IRON_PICKAXE) return true
        if (material == Material.GOLDEN_PICKAXE) return true
        if (material == Material.DIAMOND_PICKAXE) return true
        if (material == Material.NETHERITE_PICKAXE) return true
        if (material == Material.WOODEN_SHOVEL) return true
        if (material == Material.STONE_SHOVEL) return true
        if (material == Material.IRON_SHOVEL) return true
        if (material == Material.GOLDEN_SHOVEL) return true
        if (material == Material.DIAMOND_SHOVEL) return true
        if (material == Material.NETHERITE_SHOVEL) return true
        if (material == Material.WOODEN_HOE) return true
        if (material == Material.STONE_HOE) return true
        if (material == Material.IRON_HOE) return true
        if (material == Material.GOLDEN_HOE) return true
        if (material == Material.DIAMOND_HOE) return true
        if (material == Material.NETHERITE_HOE) return true
        if (material == Material.FISHING_ROD) return true
        if (material == Material.BOW) return true
        if (material == Material.CROSSBOW) return true
        if (material == Material.TRIDENT) return true
        if (material == Material.FLINT_AND_STEEL) return true
        if (material == Material.SHEARS) return true
        return false
    }

    fun sword(itemStack: ItemStack): Boolean {
        val material = itemStack.type
        if (material == Material.WOODEN_SWORD) return true
        if (material == Material.STONE_SWORD) return true
        if (material == Material.IRON_SWORD) return true
        if (material == Material.GOLDEN_SWORD) return true
        if (material == Material.DIAMOND_SWORD) return true
        if (material == Material.NETHERITE_SWORD) return true
        return false
    }

    fun getArmorDefense(material: Material?): Double {
        return when (material) {
            Material.LEATHER_HELMET -> 1.0
            Material.LEATHER_CHESTPLATE -> 3.0
            Material.LEATHER_LEGGINGS -> 2.0
            Material.LEATHER_BOOTS -> 1.0
            Material.CHAINMAIL_HELMET -> 2.0
            Material.CHAINMAIL_CHESTPLATE -> 5.0
            Material.CHAINMAIL_LEGGINGS -> 4.0
            Material.CHAINMAIL_BOOTS -> 1.0
            Material.IRON_HELMET -> 2.0
            Material.IRON_CHESTPLATE -> 6.0
            Material.IRON_LEGGINGS -> 5.0
            Material.IRON_BOOTS -> 2.0
            Material.DIAMOND_HELMET -> 3.0
            Material.DIAMOND_CHESTPLATE -> 8.0
            Material.DIAMOND_LEGGINGS -> 6.0
            Material.DIAMOND_BOOTS -> 3.0
            Material.GOLDEN_HELMET -> 2.0
            Material.GOLDEN_CHESTPLATE -> 5.0
            Material.GOLDEN_LEGGINGS -> 3.0
            Material.GOLDEN_BOOTS -> 1.0
            Material.NETHERITE_HELMET -> 3.0
            Material.NETHERITE_CHESTPLATE -> 8.0
            Material.NETHERITE_LEGGINGS -> 6.0
            Material.NETHERITE_BOOTS -> 3.0
            else -> 0.0
        }
    }


    fun damage(item: ItemStack): Double {
        val material = item.type
        return when (material) {
            Material.WOODEN_SWORD -> 4.0
            Material.STONE_SWORD -> 5.0
            Material.IRON_SWORD -> 6.0
            Material.GOLDEN_SWORD -> 4.0
            Material.DIAMOND_SWORD -> 7.0
            Material.NETHERITE_SWORD -> 8.0
            Material.WOODEN_AXE -> 7.0
            Material.STONE_AXE -> 9.0
            Material.IRON_AXE -> 9.0
            Material.GOLDEN_AXE -> 7.0
            Material.DIAMOND_AXE -> 9.0
            Material.NETHERITE_AXE -> 10.0
            Material.WOODEN_PICKAXE -> 2.0
            Material.STONE_PICKAXE -> 3.0
            Material.IRON_PICKAXE -> 4.0
            Material.GOLDEN_PICKAXE -> 2.0
            Material.DIAMOND_PICKAXE -> 5.0
            Material.NETHERITE_PICKAXE -> 6.0
            Material.WOODEN_SHOVEL -> 2.5
            Material.STONE_SHOVEL -> 3.5
            Material.IRON_SHOVEL -> 4.5
            Material.GOLDEN_SHOVEL -> 2.5
            Material.DIAMOND_SHOVEL -> 5.5
            Material.NETHERITE_SHOVEL -> 6.5
            Material.WOODEN_HOE -> 1.0
            Material.STONE_HOE -> 1.0
            Material.IRON_HOE -> 1.0
            Material.GOLDEN_HOE -> 1.0
            Material.DIAMOND_HOE -> 1.0
            Material.NETHERITE_HOE -> 1.0
            Material.FISHING_ROD -> 0.0
            Material.BOW -> 0.0
            Material.CROSSBOW -> 0.0
            Material.TRIDENT -> 9.0
            Material.FLINT_AND_STEEL -> 0.0
            Material.SHEARS -> 0.0
            else -> 0.0
        }
    }

    fun enchantment(itemStack: ItemStack): Boolean {
        val material = itemStack.type
        if (material == Material.WOODEN_SWORD) return true
        if (material == Material.STONE_SWORD) return true
        if (material == Material.IRON_SWORD) return true
        if (material == Material.GOLDEN_SWORD) return true
        if (material == Material.DIAMOND_SWORD) return true
        if (material == Material.NETHERITE_SWORD) return true
        if (material == Material.WOODEN_AXE) return true
        if (material == Material.STONE_AXE) return true
        if (material == Material.IRON_AXE) return true
        if (material == Material.GOLDEN_AXE) return true
        if (material == Material.DIAMOND_AXE) return true
        if (material == Material.NETHERITE_AXE) return true
        if (material == Material.WOODEN_PICKAXE) return true
        if (material == Material.STONE_PICKAXE) return true
        if (material == Material.IRON_PICKAXE) return true
        if (material == Material.GOLDEN_PICKAXE) return true
        if (material == Material.DIAMOND_PICKAXE) return true
        if (material == Material.NETHERITE_PICKAXE) return true
        if (material == Material.WOODEN_SHOVEL) return true
        if (material == Material.STONE_SHOVEL) return true
        if (material == Material.IRON_SHOVEL) return true
        if (material == Material.GOLDEN_SHOVEL) return true
        if (material == Material.DIAMOND_SHOVEL) return true
        if (material == Material.NETHERITE_SHOVEL) return true
        if (material == Material.WOODEN_HOE) return true
        if (material == Material.STONE_HOE) return true
        if (material == Material.IRON_HOE) return true
        if (material == Material.GOLDEN_HOE) return true
        if (material == Material.DIAMOND_HOE) return true
        if (material == Material.NETHERITE_HOE) return true
        if (material == Material.FISHING_ROD) return true
        if (material == Material.BOW) return true
        if (material == Material.CROSSBOW) return true
        if (material == Material.TRIDENT) return true
        if (material == Material.FLINT_AND_STEEL) return true
        if (material == Material.SHEARS) return true
        if (material == Material.LEATHER_BOOTS) return true
        if (material == Material.LEATHER_LEGGINGS) return true
        if (material == Material.LEATHER_CHESTPLATE) return true
        if (material == Material.LEATHER_HELMET) return true
        if (material == Material.IRON_BOOTS) return true
        if (material == Material.IRON_LEGGINGS) return true
        if (material == Material.IRON_CHESTPLATE) return true
        if (material == Material.IRON_HELMET) return true
        if (material == Material.GOLDEN_BOOTS) return true
        if (material == Material.GOLDEN_LEGGINGS) return true
        if (material == Material.GOLDEN_CHESTPLATE) return true
        if (material == Material.GOLDEN_HELMET) return true
        if (material == Material.DIAMOND_BOOTS) return true
        if (material == Material.DIAMOND_LEGGINGS) return true
        if (material == Material.DIAMOND_CHESTPLATE) return true
        if (material == Material.DIAMOND_HELMET) return true
        if (material == Material.NETHERITE_BOOTS) return true
        if (material == Material.NETHERITE_LEGGINGS) return true
        if (material == Material.NETHERITE_CHESTPLATE) return true
        if (material == Material.NETHERITE_HELMET) return true
        return false
    }

    fun type(i: ItemStack): String {
        val type = i.type.toString()
        if (type.contains("SWORD")) return "검"
        if (type.contains("PICKAXE")) return "곡괭이"
        if (type.contains("HOE")) return "괭이"
        if (type.contains("AXE")) return "도끼"
        if (type.contains("SHOVEL")) return "삽"
        if (type.contains("HELMET")) return "헬멧"
        if (type.contains("CHESTPLATE")) return "흉갑"
        if (type.contains("LEGGINGS")) return "레깅스"
        if (type.contains("BOOTS")) return "부츠"
        if (type.contains("TRIDENT")) return "삼지창"
        if (type.contains("BOW")) return "활"
        if (type.contains("CROSSBOW")) return "쇠뇌"
        if (type.contains("FISHING_ROD")) return "낚시대"
        return type
    }
}
