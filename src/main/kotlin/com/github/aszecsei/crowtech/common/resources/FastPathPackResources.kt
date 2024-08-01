package com.github.aszecsei.crowtech.common.resources

import net.minecraft.FileUtil
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PathPackResources
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.IoSupplier
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.FileSystems
import java.nio.file.Path

class FastPathPackResources(name: String, val root: Path, isBuiltin: Boolean): PathPackResources(name, root, isBuiltin) {
    override fun getResource(pPackType: PackType, pLocation: ResourceLocation): IoSupplier<InputStream>? {
        val path = root.resolve(pPackType.directory).resolve(pLocation.namespace)
        return getResource(pLocation, path)
    }

    companion object {
        fun getResource(location: ResourceLocation, path: Path): IoSupplier<InputStream>? {
            return FileUtil.decomposePath(location.path).get().map({ list ->
                val path2 = FileUtil.resolvePath(path, list)
                return@map returnIfFileExists(path2)
            }, {
                return@map null
            })
        }

        private fun returnIfFileExists(path: Path): IoSupplier<InputStream>? {
            return if (exists(path) && validatePath(path)) IoSupplier.create(path) else null
        }

        private val DEFAULT_FS = FileSystems.getDefault()
        private fun exists(path: Path): Boolean {
            return if (path.fileSystem == DEFAULT_FS) path.toFile().exists() else Files.exists(path)
        }
    }
}