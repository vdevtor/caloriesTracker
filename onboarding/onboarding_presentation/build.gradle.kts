plugins{
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android{
    namespace = "com.vitorthemyth.onboarding_presentation"
}

dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.core_ui))
    implementation(project(Modules.onboardingDomain))
}