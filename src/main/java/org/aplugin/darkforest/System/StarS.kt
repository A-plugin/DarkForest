package org.aplugin.darkforest.System

import org.bukkit.inventory.ItemStack

class StarS {
    fun Stage(i: ItemStack): Int {
        val m = i.itemMeta
        var stage = 0
        if (m != null) {
            if (m.hasLore()) {
                val lore = m.lore
                var lv = 0
                for (line in lore!!) {
                    for (c in line.toCharArray()) {
                        if (c == '★') {
                            lv++
                            stage = lv
                        }
                    }
                }
                if (stage >= 10) {
                    return 10
                }
                return stage
            }
        }
        return 0
    }

    fun StageS(i: ItemStack): String {
        val m = i.itemMeta
        if (Stage(i) == 0) return "★☆☆☆☆☆☆☆☆☆"
        val stars = StringBuilder()
        for (j in 0..Stage(i)) {
            stars.append("★")
        }
        for (k in stars.length..9) {
            stars.append("☆")
        }

        return stars.toString()
    }

    fun StageSet(i: Int): String {
        val stars = StringBuilder()
        for (j in 0..<i) {
            stars.append("★")
        }
        for (k in stars.length..9) {
            stars.append("☆")
        }

        return stars.toString()
    }

    fun p(x: Int): Int {
        val y = -x + 10
        return y * 10
    }

    fun b(x: Int): Int {
        var y = 0
        if (x >= 5) {
            y = 10 - x
            return 2 * y
        }
        return y
    }
}