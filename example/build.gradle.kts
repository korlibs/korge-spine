import korlibs.korge.gradle.*

plugins {
    //alias(libs.plugins.korge)
    id("com.soywiz.korge") version "4.0.0-rc"
    //id("com.soywiz.korge") version "999.0.0.999"
}

korge {
    id = "org.korge.samples.spine"

    targetJvm()
    targetJs()
    targetDesktop()
    targetDesktopCross()
    targetIos()
    targetAndroid()
    serializationJson()
}

dependencies {
    add("commonMainApi", project(":deps"))
}

