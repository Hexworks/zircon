import Versions.assertjVersion
import Versions.junitVersion
import Versions.logbackVersion
import Versions.mockitoKotlinVersion
import Versions.mockitoVersion

object TestLibs {

    const val kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common"
    const val kotlinTestAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common"

    const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"
    const val logbackCore = "ch.qos.logback:logback-core:$logbackVersion"

    const val junit = "junit:junit:$junitVersion"
    const val mockitoAll = "org.mockito:mockito-all:$mockitoVersion"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin:$mockitoKotlinVersion"
    const val assertJCore = "org.assertj:assertj-core:$assertjVersion"
}
