package com.yeswolf.badlock.apkmirror.domain

import kotlin.math.max

data class Version(
    val url: String = "",
    val parts: List<String> = emptyList()
) : Comparable<Version> {

    fun dotted(): String {
        return parts.joinToString(".")
    }

    fun defised(): String {
        return parts.joinToString("-")
    }

    override fun toString(): String {
        return dotted()
    }

    override fun compareTo(other: Version): Int {
        val thisParts: List<Int> = this.parts.map { it.toInt() }
        val thatParts: List<Int> = other.parts.map { it.toInt() }
        val length = max(thisParts.size, thatParts.size)
        for (i in 0 until length) {
            val thisPart = if (i < thisParts.size) thisParts[i] else 0
            val thatPart = if (i < thatParts.size) thatParts[i] else 0
            if (thisPart < thatPart) return -1
            if (thisPart > thatPart) return 1
        }
        return 0
    }
}