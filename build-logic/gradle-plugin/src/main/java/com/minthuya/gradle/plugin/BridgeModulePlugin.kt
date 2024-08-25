package com.minthuya.gradle.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

class BridgeModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("Applying bridge plugin for for ${project.name}")
        val catalogs = project.extensions.getByType(VersionCatalogsExtension::class.java)
        val libs = catalogs.named("libs")

        /* <------- Apply Plugins ------> */
        project.pluginManager.apply(libs.findPlugin("android.library").get().get().pluginId)
        project.pluginManager.apply(libs.findPlugin("jetbrains.kotlin.android").get().get().pluginId)
        /* <------- Apply Plugins ------> */

        /* <------- Android Block ------> */
        val android = project.extensions.findByType(LibraryExtension::class.java)
        android?.apply {
            namespace = "com.minthuya.${project.name}"
            resourcePrefix = project.name
        }
        /* <------- Android Block ------> */
    }
}