package com.github.aszecsei.crowtech.utils

import com.github.aszecsei.crowtech.CrowTech
import net.minecraft.resources.ResourceLocation

object Reference {
    fun forge(path: String): ResourceLocation {
        return ResourceLocation("forge", path)
    }
    fun vanilla(path: String): ResourceLocation {
        return ResourceLocation("minecraft", path)
    }
    fun ct(path: String): ResourceLocation {
        return ResourceLocation(CrowTech.ID, path)
    }
    fun ingot(path: String): ResourceLocation {
        return forge("ingots/$path")
    }
    fun vanillaIngot(path: String): ResourceLocation {
        return vanilla("ingots/$path")
    }
    fun nugget(path: String): ResourceLocation {
        return forge("nuggets/$path")
    }
    fun ore(path: String): ResourceLocation {
        return forge("ores/$path")
    }
    fun gem(path: String): ResourceLocation {
        return forge("gems/$path")
    }
    fun block(path: String): ResourceLocation {
        return forge("storage_blocks/$path")
    }
    fun raw(path: String): ResourceLocation {
        return forge("raw_materials/$path")
    }
    fun ores_in_ground(path: String): ResourceLocation {
        return forge("ores_in_ground/$path")
    }
    fun dust(path: String): ResourceLocation {
        return forge("dusts/$path")
    }
    fun rod(path: String): ResourceLocation {
        return forge("rods/$path")
    }
    fun bucket(path: String): ResourceLocation {
        return forge("buckets/$path")
    }
    fun gear(path: String): ResourceLocation {
        return forge("gears/$path")
    }
    fun plate(path: String): ResourceLocation {
        return forge("plates/$path")
    }
    fun fluid(path: String): ResourceLocation {
        return forge("molten/$path")
    }
    fun wire(path: String): ResourceLocation {
        return forge("wires/$path")
    }
}