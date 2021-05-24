import Versions.assertjVersion
import Versions.junitVersion
import Versions.logbackVersion
import Versions.mockitoKotlinVersion
import Versions.mockitoVersion
import Versions.assertkVersion

object TestLibs {

    const val kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common"
    const val kotlinTestAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common"

    const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"
    const val logbackCore = "ch.qos.logback:logback-core:$logbackVersion"

    const val junit = "junit:junit:$junitVersion"
    const val mockitoCore = "org.mockito:mockito-core:$mockitoVersion"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion"
    const val assertjCore = "org.assertj:assertj-core:$assertjVersion"
    const val assertk = "com.willowtreeapps.assertk:assertk-jvm:$assertkVersion"
}
