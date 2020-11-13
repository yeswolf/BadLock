package com.yeswolf.badlock.model

import kotlin.math.max

data class Version(
    var url: String = "",
    var parts: Array<String> = emptyArray()
) : Comparable<Version> {

    fun dotted(): String {
        return parts.joinToString(".") { it.toString() }
    }

    fun defised(): String {
        return parts.joinToString("-") { it.toString() }
    }

    override fun toString(): String {
        return dotted()
    }

    override fun compareTo(other: Version): Int {
        val thisParts: Array<Int> = this.parts.map { it.toInt() }.toTypedArray()
        val thatParts: Array<Int> = other.parts.map { it.toInt() }.toTypedArray()
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