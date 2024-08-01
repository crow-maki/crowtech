package com.github.aszecsei.crowtech.client.textures

import java.io.IOException

fun interface IORunnable {
    @Throws(IOException::class)
    fun run()

    fun safeRun() {
        try {
            run()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}