import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform") version "1.6.10"

    id("com.goncalossilva.resources") version "0.2.1"

    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"

    id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.configure<NodeJsRootExtension> {
        nodeVersion = "16.13.1"
    }
}

repositories {
    mavenCentral()
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
                    // List all browsers so that the plugin downloads their runners and sets up
                    // failure capture. `karma-detect-browsers` (below) figures out which ones are
                    // available, and `karma.config.d/select-browser.js` selects one to run tests.
                    useChromeHeadless()
                    useChromiumHeadless()
                    useFirefoxHeadless()
                    useFirefoxDeveloperHeadless()
                    useOpera()
                    useSafari()
                    useIe()
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

    ios()
    watchos()
    tvos()

    mingwX64()
    macosX64()
    macosArm64()
    linuxX64()
    linuxArm64()

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.goncalossilva:resources:0.2.1")
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(npm("karma-detect-browsers", "^2.0"))
            }
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

// Setup publishing.
publishing {
    // Configure all publications.
    @Suppress("LocalVariableName")
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
