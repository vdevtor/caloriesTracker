plugins{
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android{
    namespace = "com.vitorthemyth.tracker_presentation"
}

dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.core_ui))
    implementation(project(Modules.trackerDomain))
    implementation(Coil.coilCompose)
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.5")
}