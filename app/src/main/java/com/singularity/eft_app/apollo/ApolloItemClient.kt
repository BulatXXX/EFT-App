package com.singularity.eft_app.apollo

import com.apollographql.apollo3.ApolloClient
import com.singularity.apollo.GetItemByIDQuery
import com.singularity.apollo.GetItemsByIDQuery
import com.singularity.apollo.SearchItemByNameQuery
import com.singularity.apollo.type.GameMode
import com.singularity.apollo.type.LanguageCode
import com.singularity.eft_app.Item.Item
import com.singularity.eft_app.Item.toItem

class ApolloItemClient(
    private val apolloClient: ApolloClient
) : ItemClient {
    override suspend fun searchItemsByName(
        name: String,
        lang: LanguageCode,
        gameMode: GameMode
    ): List<Item> {
        return apolloClient.query(
            SearchItemByNameQuery(
                name = name,
                lang = lang,
                gamemode = gameMode
            )
        )
            .execute()
            .data
            ?.items?.mapNotNull { item -> item?.toItem() }
            ?: emptyList()

    }

    override suspend fun getItemById(id: String): Item? {
        return apolloClient
            .query(GetItemByIDQuery(id = id))
            .execute()
            .data
            ?.item?.toItem()
    }

    override suspend fun getItemByIds(ids: List<String>): List<Item> {
        return apolloClient
            .query(GetItemsByIDQuery(ids = ids, lang = LanguageCode.en,GameMode.regular))
            .execute()
            .data
            ?.items?.mapNotNull { item -> item?.toItem() }
            ?: emptyList()
    }


}