import Versions.assertjVersion
import Versions.cache4kVersion
import Versions.cobaltVersion
import Versions.kotlinxCollectionsImmutableVersion
import Versions.kotlinxCoroutinesVersion
import Versions.mockitoKotlinVersion
import Versions.mockitoVersion
import Versions.snakeyamlVersion

object Libraries {

    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"
    const val kotlinxCollectionsImmutable =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinxCollectionsImmutableVersion"

    const val cobaltCore = "org.hexworks.cobalt:cobalt.core:$cobaltVersion"
    const val cache4k = "io.github.reactivecircus.cache4k:cache4k:$cache4kVersion"

    const val snakeYaml = "org.yaml:snakeyaml:$snakeyamlVersion"

    const val korge = "com.soywiz.korlibs.korge2:korge:4.0.3"

    // TEST
    const val kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common"
    const val kotlinTestAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common"
    const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit"
    const val kotlinTestJs = "org.jetbrains.kotlin:kotlin-test-js"

    const val mockitoCore = "org.mockito:mockito-core:$mockitoVersion"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion"
    const val assertjCore = "org.assertj:assertj-core:$assertjVersion"
}
