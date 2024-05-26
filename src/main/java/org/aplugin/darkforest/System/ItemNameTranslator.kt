package org.aplugin.darkforest.System

class ItemNameTranslator {
    fun translate(n: String): String {
        var n = n
        for ((key, value) in nameMap) {
            n = n.replace(key!!, value!!)
        }
        return n.replace("_", " ")
    }

    companion object {
        private val nameMap: MutableMap<String?, String?> = HashMap()

        init {
            nameMap["DIAMOND"] = "다이아몬드"
            nameMap["NETHERITE"] = "네더라이트"
            nameMap["IRON"] = "철"
            nameMap["GOLDEN"] = "금"
            nameMap["STONE"] = "돌"
            nameMap["WOODEN"] = "나무"
            nameMap["CHAINMAIL"] = "사슬"

            nameMap["SWORD"] = "검"
            nameMap["AXE"] = "도끼"
            nameMap["SHOVEL"] = "삽"
            nameMap["PICKAXE"] = "곡괭이"
            nameMap["HOE"] = "괭이"

            nameMap["HELMET"] = "투구"
            nameMap["CHESTPLATE"] = "흉갑"
            nameMap["LEGGINGS"] = "레깅스"
            nameMap["BOOTS"] = "부츠"

            nameMap["TRIDENT"] = "삼지창"
            nameMap["SHIELD"] = "방패"
            nameMap["BOW"] = "활"
            nameMap["CROSSBOW"] = "쇠뇌"
        }
    }
}
