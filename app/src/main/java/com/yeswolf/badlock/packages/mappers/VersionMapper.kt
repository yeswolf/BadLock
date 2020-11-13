package com.yeswolf.badlock.packages.mappers

import com.yeswolf.badlock.apkmirror.data.VersionData
import com.yeswolf.badlock.apkmirror.domain.Version

object VersionMapper {
    fun fromDto(versionData: VersionData): Version {
        return Version(
            url = versionData.url,
            parts = versionData.version.split(".")
        )
    }
}