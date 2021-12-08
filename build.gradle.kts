import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import kotlin.collections.listOf

plugins {
    kotlin("jvm") version("1.5.32")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.0"
}

group = "run.dn5"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    api("com.destroystokyo.paper:paper-api:1.15.2-R0.1-SNAPSHOT")
    api("org.jetbrains.kotlin:kotlin-stdlib:1.5.32")
}


tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    jar {
        from(
            configurations.compileOnly.get().map {
                if (it.isDirectory) it else zipTree(it)
            }
        )
    }
    register<Copy>("devBuild"){
        dependsOn("clean", "jar")
        from("$buildDir/libs/${rootProject.name}-$version.jar")
        into("$projectDir/.debug/plugins")
    }
}
bukkit {
    main = "$group.${rootProject.name}.Main"
    apiVersion = "1.17"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = listOf("ddPn08")
    defaultPermission = BukkitPluginDescription.Permission.Default.OP
}