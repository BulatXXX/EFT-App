package com.singularity.eft_app.apollo

import com.singularity.apollo.type.GameMode
import com.singularity.apollo.type.LanguageCode
import com.singularity.eft_app.Item.Item

interface ItemClient {
    suspend fun searchItemsByName(name: String, lang: LanguageCode,gameMode: GameMode): List<Item>?
    suspend fun getItemById(id: String): Item?
    suspend fun getItemByIds(ids:List<String>): List<Item>

}