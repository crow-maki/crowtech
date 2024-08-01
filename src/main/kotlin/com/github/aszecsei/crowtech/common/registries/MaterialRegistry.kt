package com.github.aszecsei.crowtech.common.registries

import com.github.aszecsei.crowtech.common.materials.Material
import com.github.aszecsei.crowtech.common.materials.properties.MaterialProperty
import com.github.aszecsei.crowtech.common.materials.properties.OreProperty
import java.util.*

object MaterialRegistry {
    val MATERIALS = TreeMap<String, Material>()

    val ALUMINUM = Material("aluminum", "Aluminum")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0x3fcaff)
        .register()

    val IRIDIUM = Material("iridium", "Iridium")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0xe1e6f5)
        .register()

    val LEAD = Material("lead", "Lead")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0x6a76bc)
        .register()

    val NICKEL = Material("nickel", "Nickel")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0xFAFAC8)
        .register()

    val OSMIUM = Material("osmium", "Osmium")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0xFAFAC8)
        .register()

    val PLATINUM = Material("platinum", "Platinum")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0xffe5ba)
        .register()

    val SILVER = Material("silver", "Silver")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0xDCDCFF)
        .register()

    val TIN = Material("tin", "Tin")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0xc0bcd0)
        .register()

    val TITANIUM = Material("titanium", "Titanium")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0xDCA0F0)
        .register()

    val TUNGSTEN = Material("tungsten", "Tungsten")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0x8760ad)
        .register()

    val URANIUM = Material("uranium", "Uranium")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0x39e600)
        .register()

    val ZINC = Material("zinc", "Zinc")
        .set(MaterialProperty.ORE_PROPERTY, OreProperty().withMin(1).withMax(3))
        .set(MaterialProperty.MEAN_RGB, 0xDADAA8)
        .register()
}