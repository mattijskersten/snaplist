package com.snaplist.ui.handoff

import com.snaplist.data.db.ListingDraft

/**
 * The listing fields the user pastes into Vinted's form, in form order.
 * Shared by the handoff screen, the floating overlay and the notification
 * copy actions so all three present the same list.
 */
fun ListingDraft.handoffFields(): List<Pair<String, String>> {
    val d = this // buildList's MutableList receiver shadows e.g. `size`
    return buildList {
        add("Title" to d.title)
        add("Description" to d.description)
        if (d.brand.isNotBlank()) add("Brand" to d.brand)
        if (d.categoryPath.isNotEmpty()) add("Category" to d.categoryPath.joinToString(" › "))
        d.condition?.let { add("Condition" to it.label) }
        if (d.size.isNotBlank()) add("Size" to d.size)
        if (d.colors.isNotEmpty()) add("Colors" to d.colors.joinToString(", "))
        if (d.material.isNotBlank()) add("Material" to d.material)
        d.price?.let { add("Price" to it.toString()) }
    }
}
