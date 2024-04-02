plugins{
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android{
    namespace = "com.vitorthemyth.onboarding_domain"
}

dependencies{
    implementation(project(Modules.core))
}