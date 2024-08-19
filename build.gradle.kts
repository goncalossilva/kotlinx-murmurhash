import com.goncalossilva.useanybrowser.useAnyBrowser
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport

plugins {
    kotlin("multiplatform") version "2.0.10"

    id("com.goncalossilva.resources") version "0.4.2"
    id("com.goncalossilva.useanybrowser") version "0.3.0"

    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"

    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

plugins.withType<NodeJsRootPlugin> {
    configure<NodeJsRootExtension> {
        nodeVersion = "20.12.0"
    }
}

plugins.withType<YarnPlugin> {
    configure<YarnRootExtension> {
        version = "1.22.19"
        yarnLockMismatchReport = YarnLockMismatchReport.WARNING
        yarnLockAutoReplace = true
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

kotlin {
    explicitApi()

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(IR) {
        browser {
            testTask {
                useKarma {
                    useAnyBrowser()
                }
            }
        }
        nodejs {
            testTask {
               useMocha {
                   // Disable test case timeout, bringing parity with other platforms.
                   timeout = "0"
               }
            }
        }
    }

    iosArm64()
    iosX64()
    iosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()

    mingwX64()
    macosX64()
    macosArm64()
    linuxX64()
    linuxArm64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.goncalossilva:resources:0.4.2")
            }
        }
    }
}

// TODO: Remove when https://youtrack.jetbrains.com/issue/KT-46466 is fixed.
val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(signingTasks)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

// Setup publishing.
publishing {
    // Configure all publications.
    publications.withType<MavenPublication> {
        // Publish docs with each artifact.
        artifact(javadocJar)

        // Provide information requited by Maven Central.
        pom {
            name.set(rootProject.name)
            description.set(findProperty("pomDescription") as String)
            url.set(findProperty("pomUrl") as String)

            licenses {
                license {
                    name.set(findProperty("pomLicenseName") as String)
                    url.set(findProperty("pomLicenseUrl") as String)
                }
            }

            scm {
                url.set(findProperty("pomScmUrl") as String)
                connection.set(findProperty("pomScmConnection") as String)
                developerConnection.set(findProperty("pomScmDeveloperConnection") as String)
            }

            developers {
                developer {
                    id.set(findProperty("pomDeveloperId") as String)
                    name.set(findProperty("pomDeveloperName") as String)
                }
            }
        }
    }
}

// Sign artifacts.
signing {
    // Use `signingKey` and `signingPassword` properties if provided.
    // Otherwise, default to `signing.keyId`, `signing.password` and `signing.secretKeyRingFile`.
    val signingKey: String? by project
    val signingPassword: String? by project
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
    sign(publishing.publications)
}

// Leverage Gradle Nexus Publish Plugin to create, close and release staging repositories,
// covering the last part of the release process to Maven Central.
nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            // Read `ossrhUsername` and `ossrhPassword` properties.
            // DO NOT ADD THESE TO SOURCE CONTROL. Store them in your system properties,
            // or pass them in using ORG_GRADLE_PROJECT_* environment variables.
            val ossrhUsername: String? by project
            val ossrhPassword: String? by project
            val ossrhStagingProfileId: String? by project
            username.set(ossrhUsername)
            password.set(ossrhPassword)
            stagingProfileId.set(ossrhStagingProfileId)
        }
    }
}

// Install git hooks automatically.
gradle.taskGraph.whenReady {
    val from = File("${rootProject.rootDir}/config/detekt/pre-commit")
    val to = File("${rootProject.rootDir}/.git/hooks/pre-commit")
    from.copyTo(to, overwrite = true)
    to.setExecutable(true)
}
