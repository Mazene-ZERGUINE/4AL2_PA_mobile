import com.android.build.gradle.internal.dsl.decorator.SupportedPropertyType.Collection.List.type

buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.0.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:12.0.2")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
