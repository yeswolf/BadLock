package com.yeswolf.badlock.packages.mappers

import com.yeswolf.badlock.model.Version

object VersionMapper {
    fun toDto(versionName: String): Version =
        Version(versionName)
}