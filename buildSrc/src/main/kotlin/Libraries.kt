import Versions.assertjVersion
import Versions.assertkVersion
import Versions.cache4kVersion
import Versions.caffeineVersion
import Versions.cobaltVersion
import Versions.filtersVersion
import Versions.gdxVersion
import Versions.junitVersion
import Versions.kotlinxCollectionsImmutableVersion
import Versions.kotlinxCoroutinesVersion
import Versions.logbackVersion
import Versions.mockitoKotlinVersion
import Versions.mockitoVersion
import Versions.slf4jVersion
import Versions.snakeyamlVersion

object Libraries {

    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"
    const val kotlinxCollectionsImmutable =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinxCollectionsImmutableVersion"

    const val cobaltCore = "org.hexworks.cobalt:cobalt.core:$cobaltVersion"
    const val cache4k = "io.github.reactivecircus.cache4k:cache4k:$cache4kVersion"

    const val caffeine = "com.github.ben-manes.caffeine:caffeine:$caffeineVersion"
    const val snakeYaml = "org.yaml:snakeyaml:$snakeyamlVersion"
    const val slf4jApi = "org.slf4j:slf4j-api:$slf4jVersion"

    const val gdx = "com.badlogicgames.gdx:gdx:$gdxVersion"
    const val gdxFreetype = "com.badlogicgames.gdx:gdx-freetype:$gdxVersion"
    const val gdxFreetypePlatform = "com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion"
    const val gdxFreetypePlatformNatives = "com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop"
    const val gdxPlatformNativesDesktop = "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"
    const val gdxBox2D = "com.badlogicgames.gdx:gdx-box2d:$gdxVersion"
    const val gdxBox2DPlatform = "com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop"
    const val gdxBackendLwjgl = "com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion"

    const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"
    const val filters = "com.jhlabs:filters:$filtersVersion"

    // TEST
    const val kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common"
    const val kotlinTestAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common"
    const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit"
    const val kotlinTestJs = "org.jetbrains.kotlin:kotlin-test-js"

    const val logbackCore = "ch.qos.logback:logback-core:$logbackVersion"

    const val junit = "junit:junit:$junitVersion"
    const val mockitoCore = "org.mockito:mockito-core:$mockitoVersion"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion"
    const val assertjCore = "org.assertj:assertj-core:$assertjVersion"
    const val assertk = "com.willowtreeapps.assertk:assertk-jvm:$assertkVersion"
}
