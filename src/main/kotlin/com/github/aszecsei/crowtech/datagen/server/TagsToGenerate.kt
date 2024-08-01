package com.github.aszecsei.crowtech.datagen.server

import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

object TagsToGenerate {

    val TAG_TO_ITEM_MAP = HashMap<String, MutableList<ItemLike>>()
    val OPTIONAL_TAGS = HashSet<String>()
    val TAG_TRANSLATIONS = HashMap<String, String>()
    val TAG_TO_BE_ADDED_TO_ANOTHER_TAG = HashMap<String, MutableSet<String>>()

    private fun addTranslation(tag: String, tagEnglishName: String) {
        val tagId = ResourceLocation(tag)
        TAG_TRANSLATIONS["tag.${tagId.namespace}.${tagId.path}".replace('/', '.')] = tagEnglishName
    }

    fun generateTag(tag: String, item: ItemLike, tagEnglishName: String) {
        if (tag.startsWith("#")) {
            throw IllegalArgumentException("Tag must not start with #: $tag")
        }
        TAG_TO_ITEM_MAP.computeIfAbsent(tag) { _ -> ArrayList<ItemLike>() }.add(item)
        addTranslation(tag, tagEnglishName)
    }

    fun addTagToTag(tagToBeAdded: String, tagTarget: String, targetEnglishName: String) {
        if (tagToBeAdded.startsWith("#")) {
            throw IllegalArgumentException("Tag must not start with #: $tagToBeAdded")
        }
        if (tagTarget.startsWith("#")) {
            throw IllegalArgumentException("Tag must not start with #: $tagTarget")
        }

        TAG_TO_BE_ADDED_TO_ANOTHER_TAG.computeIfAbsent(tagTarget) { TreeSet<String>() }.add(tagToBeAdded)
        addTranslation(tagTarget, targetEnglishName)
    }

    fun generateTag(tag: TagKey<Item>, item: ItemLike, tagEnglishName: String) {
        generateTag(tag.location.toString(), item, tagEnglishName)
    }

    fun markTagOptional(tag: TagKey<Item>) {
        OPTIONAL_TAGS.add(tag.location.toString())
    }
}