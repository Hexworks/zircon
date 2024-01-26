import Versions.ASSERTJ_VERSION
import Versions.COBALT_VERSION
import Versions.KOR_VERSION
import Versions.KOTLINX_COLLECTIONS_IMMUTABLE_VERSION
import Versions.KOTLINX_COROUTINES_VERSION
import Versions.MOCKITO_KOTLIN_VERSION
import Versions.MOCKITO_VERSION
import Versions.SNAKEYAML_VERSION

object Libraries {

    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLINX_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$KOTLINX_COROUTINES_VERSION"
    const val KOTLINX_COLLECTIONS_IMMUTABLE =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:$KOTLINX_COLLECTIONS_IMMUTABLE_VERSION"

    const val COBALT_CORE = "org.hexworks.cobalt:cobalt.core:$COBALT_VERSION"

    const val SNAKE_YAML = "org.yaml:snakeyaml:$SNAKEYAML_VERSION"

    const val KORGE = "com.soywiz.korge:korge:$KOR_VERSION"

    // TEST
    const val KOTLIN_TEST_COMMON = "org.jetbrains.kotlin:kotlin-test-common"
    const val KOTLIN_TEST_ANNOTATIONS_COMMON = "org.jetbrains.kotlin:kotlin-test-annotations-common"
    const val KOTLIN_TEST_JUNIT = "org.jetbrains.kotlin:kotlin-test-junit"
    const val KOTLIN_TEST_JS = "org.jetbrains.kotlin:kotlin-test-js"

    const val MOCKITO_CORE = "org.mockito:mockito-core:$MOCKITO_VERSION"
    const val MOCKITO_KOTLIN = "org.mockito.kotlin:mockito-kotlin:$MOCKITO_KOTLIN_VERSION"
    const val ASSERTJ_CORE = "org.assertj:assertj-core:$ASSERTJ_VERSION"
}
