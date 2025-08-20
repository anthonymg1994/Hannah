import java.io.FileInputStream
import java.util.Properties

val versionPropsFile = rootProject.file("version.properties")
val versionProperties = Properties()
if (versionPropsFile.exists()) {
    versionProperties.load(FileInputStream(versionPropsFile))
}
val versionCode = versionProperties["VERSION_CODE"].toString().toInt()
val versionName = versionProperties["VERSION_NAME"].toString()

extra["VERSION_CODE"] = versionCode
extra["VERSION_NAME"] = versionName

tasks.register("incrementVersion") {
    onlyIf {
        gradle.startParameter.taskNames.any { it.contains("assembleRelease") }
    }

    doLast {
        val currentVersionCode = versionProperties["VERSION_CODE"].toString().toInt()
        val currentVersionName = versionProperties["VERSION_NAME"].toString()

        val newVersionCode = currentVersionCode + 1
        val parts = currentVersionName.split(".").map { it.toInt() }.toMutableList()
        if (parts.size == 3) parts[2] = parts[2] + 1
        val newVersionName = parts.joinToString(".")

        versionPropsFile.writeText(buildString {
            append("VERSION_CODE=$newVersionCode\n")
            append("VERSION_NAME=$newVersionName\n")
        })

        println("✅ Updated version.properties")
        println("   VERSION_CODE: $currentVersionCode → $newVersionCode")
        println("   VERSION_NAME: $currentVersionName → $newVersionName")
    }
}
