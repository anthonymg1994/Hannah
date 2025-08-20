// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.hilt) apply false
}


subprojects {
    plugins.withId("io.gitlab.arturbosch.detekt") {
        extensions.configure(io.gitlab.arturbosch.detekt.extensions.DetektExtension::class.java) {
            toolVersion = libs.versions.detekt.get()
            buildUponDefaultConfig = true
            allRules = false
            source.setFrom(files("${rootProject.projectDir}/detekt-config.yml"))
        }

        tasks.withType(io.gitlab.arturbosch.detekt.Detekt::class.java).configureEach {
            reports {
                xml.required.set(true)
                html.required.set(true)
                txt.required.set(false)
            }
        }
    }
}