package com.cosmetics.domain.util

fun handleEscapeCharacter(str: String): String =
    try {
        var str = str
        val escapeCharacters =
            arrayOf("&gt;", "&lt;", "&amp;", "&quot;", "&apos;")
        val onReadableCharacter =
            arrayOf(">", "<", "&", "\"\"", "'")
        for (i in escapeCharacters.indices) {
            str = str.replace(escapeCharacters[i], onReadableCharacter[i])
        }
        str
    } catch (e: Exception) {
        ""
    }
