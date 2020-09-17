package com.yeswolf.badlock.model

import kotlin.math.max

class Version(version: String, var url: String) : Comparable<Version> {
    var intParts: Array<Int> = arrayOf()
    var stringParts: Array<String> = arrayOf()
    var string: String = version

    init {
        this.intParts = version.split(".").map { it.toInt() }.toTypedArray()
        this.stringParts = version.split(".").toTypedArray()
    }

    fun dotted(): String {
        return stringParts.joinToString(".") { it }
    }

    fun defised(): String {
        return stringParts.joinToString("-") { it }
    }

    override fun toString(): String {
        return string
    }

    override fun compareTo(other: Version): Int {
        val thisParts: Array<Int> = this.intParts
        val thatParts: Array<Int> = other.intParts
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