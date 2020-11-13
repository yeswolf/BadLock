package com.yeswolf.badlock.packages.mappers

import com.yeswolf.badlock.model.Version

object VersionMapper {
    fun fromDto(versionName: String): Version =
        Version(versionName)
}