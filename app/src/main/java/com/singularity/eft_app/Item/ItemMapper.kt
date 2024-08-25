package com.singularity.eft_app.Item

import com.singularity.apollo.GetItemByIDQuery
import com.singularity.apollo.SearchItemByNameQuery

fun SearchItemByNameQuery.Item.toItem(): Item{
    return Item(
        id = id,
        name = name,
        avg24hPrice = avg24hPrice ?: 0,
        description = description,
        height = height,
        width = width,
        iconLink = iconLink,
        image512pxLink = image512pxLink
    )
}

fun GetItemByIDQuery.Item.toItem():Item{
    return Item(
        id = id,
        name = name,
        avg24hPrice = avg24hPrice ?: 0,
        description = description,
        height = height,
        width = width,
        iconLink = iconLink,
        image512pxLink = image512pxLink
    )
}