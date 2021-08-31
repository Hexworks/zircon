import Versions.cobaltVersion
import Versions.filtersVersion
import Versions.gdxVersion
import Versions.kotlinxCollectionsImmutableVersion
import Versions.kotlinxCoroutinesVersion
import Versions.logbackVersion
import Versions.caffeineVersion
import Versions.snakeyamlVersion
import Versions.slf4jVersion

object Libs {

    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"
    const val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion"
    const val kotlinxCollectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinxCollectionsImmutableVersion"

    const val cobaltCore = "org.hexworks.cobalt:cobalt.core:$cobaltVersion"

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
}
