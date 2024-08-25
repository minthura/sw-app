package com.minthuya.gradle.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

class FeatureModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("Applying feature plugin for ${project.name}")
        val catalogs = project.extensions.getByType(VersionCatalogsExtension::class.java)
        val libs = catalogs.named("libs")

        /* <------- Apply Plugins ------> */
        project.pluginManager.apply(libs.findPlugin("android.library").get().get().pluginId)
        project.pluginManager.apply(libs.findPlugin("jetbrains.kotlin.android").get().get().pluginId)
        project.pluginManager.apply(libs.findPlugin("jetbrains.kotlin.kapt").get().get().pluginId)
        project.pluginManager.apply(libs.findPlugin("jetbrains.kotlin.parcelize").get().get().pluginId)
        project.pluginManager.apply(libs.findPlugin("navigation.safeargs.kotlin").get().get().pluginId)
        /* <------- Apply Plugins ------> */

        /* <------- Android Block ------> */
        val android = project.extensions.findByType(LibraryExtension::class.java)
        android?.apply {
            namespace = "com.minthuya.${project.name}"
            resourcePrefix = project.name
            buildFeatures.apply {
                viewBinding = true
            }
        }
        /* <------- Android Block ------> */

        /* <------- Add Dependencies ------> */
        libs.libraryAliases.forEach {
            val library = libs.findLibrary(it).get()
            when (it) {
                "dagger2" -> project.dependencies.addProvider("implementation", library)
                "dagger2.ktx" -> project.dependencies.addProvider("kapt", library)
                else -> if (!EXCLUDED_LIBRARIES.contains(it) && !it.startsWith(EXCLUDE_PREFIX)) {
                    println("----> Adding dependency: [$it]")
                    project.dependencies.addProvider(
                        "implementation",
                        library
                    )
                }
            }
        }
        project.dependencies.add("implementation", project.project(":platform:component"))
        project.dependencies.add("implementation", project.project(":platform:networkKit"))
        project.dependencies.addProvider("testImplementation", libs.findLibrary("junit").get())
        /* <------- Add Dependencies ------> */
    }

    companion object {
        const val EXCLUDE_PREFIX = "exclude"
        val EXCLUDED_LIBRARIES = listOf(
            "androidx.legacy",
            "junit",
            "junit.androidx",
            "junit.androidx.ktx",
            "jupiter",
            "mockk",
            "mockito.core",
            "mockito.android",
            "mockito.kotlin",
            "espresso.core",
            "espresso.contrib",
            "lint",
            "lint.api",
            "lint.checks",
            "lint.test",
            "detekt.test",
            "detekt.plugin",
            "detekt.api",
            "detekt.formatting",
            "ktlint.plugin",
            "sonar.source",
            "spotless.plugin",
            "androidx.test.core",
            "androidx.test.orchestrator",
            "androidx.test.rules",
            "androidx.test.runner",
            "androidx.test.uiautomator"
        )
    }
}