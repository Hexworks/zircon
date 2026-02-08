package org.hexworks.zircon.api.graphics.nerd

enum class WeatherGlyphs(
    val glyph: Char,
) {

    /**
     *  (0xE300)
     */
    DAY_CLOUDY_GUSTS(0xE300.toChar()),

    /**
     *  (0xE301)
     */
    DAY_CLOUDY_WINDY(0xE301.toChar()),

    /**
     *  (0xE302)
     */
    DAY_CLOUDY(0xE302.toChar()),

    /**
     *  (0xE303)
     */
    DAY_FOG(0xE303.toChar()),

    /**
     *  (0xE304)
     */
    DAY_HAIL(0xE304.toChar()),

    /**
     *  (0xE305)
     */
    DAY_LIGHTNING(0xE305.toChar()),

    /**
     *  (0xE306)
     */
    DAY_RAIN_MIX(0xE306.toChar()),

    /**
     *  (0xE307)
     */
    DAY_RAIN_WIND(0xE307.toChar()),

    /**
     *  (0xE308)
     */
    DAY_RAIN(0xE308.toChar()),

    /**
     *  (0xE309)
     */
    DAY_SHOWERS(0xE309.toChar()),

    /**
     *  (0xE30A)
     */
    DAY_SNOW(0xE30A.toChar()),

    /**
     *  (0xE30B)
     */
    DAY_SPRINKLE(0xE30B.toChar()),

    /**
     *  (0xE30C)
     */
    DAY_SUNNY_OVERCAST(0xE30C.toChar()),

    /**
     *  (0xE30D)
     */
    DAY_SUNNY(0xE30D.toChar()),

    /**
     *  (0xE30E)
     */
    DAY_STORM_SHOWERS(0xE30E.toChar()),

    /**
     *  (0xE30F)
     */
    DAY_THUNDERSTORM(0xE30F.toChar()),

    /**
     *  (0xE310)
     */
    CLOUDY_GUSTS(0xE310.toChar()),

    /**
     *  (0xE311)
     */
    CLOUDY_WINDY(0xE311.toChar()),

    /**
     *  (0xE312)
     */
    CLOUDY(0xE312.toChar()),

    /**
     *  (0xE313)
     */
    FOG(0xE313.toChar()),

    /**
     *  (0xE314)
     */
    HAIL(0xE314.toChar()),

    /**
     *  (0xE315)
     */
    LIGHTNING(0xE315.toChar()),

    /**
     *  (0xE316)
     */
    RAIN_MIX(0xE316.toChar()),

    /**
     *  (0xE317)
     */
    RAIN_WIND(0xE317.toChar()),

    /**
     *  (0xE318)
     */
    RAIN(0xE318.toChar()),

    /**
     *  (0xE319)
     */
    SHOWERS(0xE319.toChar()),

    /**
     *  (0xE31A)
     */
    SNOW(0xE31A.toChar()),

    /**
     *  (0xE31B)
     */
    SPRINKLE(0xE31B.toChar()),

    /**
     *  (0xE31C)
     */
    STORM_SHOWERS(0xE31C.toChar()),

    /**
     *  (0xE31D)
     */
    THUNDERSTORM(0xE31D.toChar()),

    /**
     *  (0xE31E)
     */
    WINDY(0xE31E.toChar()),

    /**
     *  (0xE31F)
     */
    NIGHT_ALT_CLOUDY_GUSTS(0xE31F.toChar()),

    /**
     *  (0xE320)
     */
    NIGHT_ALT_CLOUDY_WINDY(0xE320.toChar()),

    /**
     *  (0xE321)
     */
    NIGHT_ALT_HAIL(0xE321.toChar()),

    /**
     *  (0xE322)
     */
    NIGHT_ALT_LIGHTNING(0xE322.toChar()),

    /**
     *  (0xE323)
     */
    NIGHT_ALT_RAIN_MIX(0xE323.toChar()),

    /**
     *  (0xE324)
     */
    NIGHT_ALT_RAIN_WIND(0xE324.toChar()),

    /**
     *  (0xE325)
     */
    NIGHT_ALT_RAIN(0xE325.toChar()),

    /**
     *  (0xE326)
     */
    NIGHT_ALT_SHOWERS(0xE326.toChar()),

    /**
     *  (0xE327)
     */
    NIGHT_ALT_SNOW(0xE327.toChar()),

    /**
     *  (0xE328)
     */
    NIGHT_ALT_SPRINKLE(0xE328.toChar()),

    /**
     *  (0xE329)
     */
    NIGHT_ALT_STORM_SHOWERS(0xE329.toChar()),

    /**
     *  (0xE32A)
     */
    NIGHT_ALT_THUNDERSTORM(0xE32A.toChar()),

    /**
     *  (0xE32B)
     */
    NIGHT_CLEAR(0xE32B.toChar()),

    /**
     *  (0xE32C)
     */
    NIGHT_CLOUDY_GUSTS(0xE32C.toChar()),

    /**
     *  (0xE32D)
     */
    NIGHT_CLOUDY_WINDY(0xE32D.toChar()),

    /**
     *  (0xE32E)
     */
    NIGHT_CLOUDY(0xE32E.toChar()),

    /**
     *  (0xE32F)
     */
    NIGHT_HAIL(0xE32F.toChar()),

    /**
     *  (0xE330)
     */
    NIGHT_LIGHTNING(0xE330.toChar()),

    /**
     *  (0xE331)
     */
    NIGHT_RAIN_MIX(0xE331.toChar()),

    /**
     *  (0xE332)
     */
    NIGHT_RAIN_WIND(0xE332.toChar()),

    /**
     *  (0xE333)
     */
    NIGHT_RAIN(0xE333.toChar()),

    /**
     *  (0xE334)
     */
    NIGHT_SHOWERS(0xE334.toChar()),

    /**
     *  (0xE335)
     */
    NIGHT_SNOW(0xE335.toChar()),

    /**
     *  (0xE336)
     */
    NIGHT_SPRINKLE(0xE336.toChar()),

    /**
     *  (0xE337)
     */
    NIGHT_STORM_SHOWERS(0xE337.toChar()),

    /**
     *  (0xE338)
     */
    NIGHT_THUNDERSTORM(0xE338.toChar()),

    /**
     *  (0xE339)
     */
    CELSIUS(0xE339.toChar()),

    /**
     *  (0xE33A)
     */
    CLOUD_DOWN(0xE33A.toChar()),

    /**
     *  (0xE33B)
     */
    CLOUD_REFRESH(0xE33B.toChar()),

    /**
     *  (0xE33C)
     */
    CLOUD_UP(0xE33C.toChar()),

    /**
     *  (0xE33D)
     */
    CLOUD(0xE33D.toChar()),

    /**
     *  (0xE33E)
     */
    DEGREES(0xE33E.toChar()),

    /**
     *  (0xE33F)
     */
    DIRECTION_DOWN_LEFT(0xE33F.toChar()),

    /**
     *  (0xE340)
     */
    DIRECTION_DOWN(0xE340.toChar()),

    /**
     *  (0xE341)
     */
    FAHRENHEIT(0xE341.toChar()),

    /**
     *  (0xE342)
     */
    HORIZON_ALT(0xE342.toChar()),

    /**
     *  (0xE343)
     */
    HORIZON(0xE343.toChar()),

    /**
     *  (0xE344)
     */
    DIRECTION_LEFT(0xE344.toChar()),

    /**
     *  (0xE345)
     */
    ALIENS(0xE345.toChar()),

    /**
     *  (0xE346)
     */
    NIGHT_FOG(0xE346.toChar()),

    /**
     *  (0xE347)
     */
    REFRESH_ALT(0xE347.toChar()),

    /**
     *  (0xE348)
     */
    REFRESH(0xE348.toChar()),

    /**
     *  (0xE349)
     */
    DIRECTION_RIGHT(0xE349.toChar()),

    /**
     *  (0xE34A)
     */
    RAINDROPS(0xE34A.toChar()),

    /**
     *  (0xE34B)
     */
    STRONG_WIND(0xE34B.toChar()),

    /**
     *  (0xE34C)
     */
    SUNRISE(0xE34C.toChar()),

    /**
     *  (0xE34D)
     */
    SUNSET(0xE34D.toChar()),

    /**
     *  (0xE34E)
     */
    THERMOMETER_EXTERIOR(0xE34E.toChar()),

    /**
     *  (0xE34F)
     */
    THERMOMETER_INTERNAL(0xE34F.toChar()),

    /**
     *  (0xE350)
     */
    THERMOMETER(0xE350.toChar()),

    /**
     *  (0xE351)
     */
    TORNADO(0xE351.toChar()),

    /**
     *  (0xE352)
     */
    DIRECTION_UP_RIGHT(0xE352.toChar()),

    /**
     *  (0xE353)
     */
    DIRECTION_UP(0xE353.toChar()),

    /**
     *  (0xE354)
     */
    WIND_WEST(0xE354.toChar()),

    /**
     *  (0xE355)
     */
    WIND_SOUTH_WEST(0xE355.toChar()),

    /**
     *  (0xE356)
     */
    WIND_SOUTH_EAST(0xE356.toChar()),

    /**
     *  (0xE357)
     */
    WIND_SOUTH(0xE357.toChar()),

    /**
     *  (0xE358)
     */
    WIND_NORTH_WEST(0xE358.toChar()),

    /**
     *  (0xE359)
     */
    WIND_NORTH_EAST(0xE359.toChar()),

    /**
     *  (0xE35A)
     */
    WIND_NORTH(0xE35A.toChar()),

    /**
     *  (0xE35B)
     */
    WIND_EAST(0xE35B.toChar()),

    /**
     *  (0xE35C)
     */
    SMOKE(0xE35C.toChar()),

    /**
     *  (0xE35D)
     */
    DUST(0xE35D.toChar()),

    /**
     *  (0xE35E)
     */
    SNOW_WIND(0xE35E.toChar()),

    /**
     *  (0xE35F)
     */
    DAY_SNOW_WIND(0xE35F.toChar()),

    /**
     *  (0xE360)
     */
    NIGHT_SNOW_WIND(0xE360.toChar()),

    /**
     *  (0xE361)
     */
    NIGHT_ALT_SNOW_WIND(0xE361.toChar()),

    /**
     *  (0xE362)
     */
    DAY_SLEET_STORM(0xE362.toChar()),

    /**
     *  (0xE363)
     */
    NIGHT_SLEET_STORM(0xE363.toChar()),

    /**
     *  (0xE364)
     */
    NIGHT_ALT_SLEET_STORM(0xE364.toChar()),

    /**
     *  (0xE365)
     */
    DAY_SNOW_THUNDERSTORM(0xE365.toChar()),

    /**
     *  (0xE366)
     */
    NIGHT_SNOW_THUNDERSTORM(0xE366.toChar()),

    /**
     *  (0xE367)
     */
    NIGHT_ALT_SNOW_THUNDERSTORM(0xE367.toChar()),

    /**
     *  (0xE368)
     */
    SOLAR_ECLIPSE(0xE368.toChar()),

    /**
     *  (0xE369)
     */
    LUNAR_ECLIPSE(0xE369.toChar()),

    /**
     *  (0xE36A)
     */
    METEOR(0xE36A.toChar()),

    /**
     *  (0xE36B)
     */
    HOT(0xE36B.toChar()),

    /**
     *  (0xE36C)
     */
    HURRICANE(0xE36C.toChar()),

    /**
     *  (0xE36D)
     */
    SMOG(0xE36D.toChar()),

    /**
     *  (0xE36E)
     */
    ALIEN(0xE36E.toChar()),

    /**
     *  (0xE36F)
     */
    SNOWFLAKE_COLD(0xE36F.toChar()),

    /**
     *  (0xE370)
     */
    STARS(0xE370.toChar()),

    /**
     *  (0xE371)
     */
    RAINDROP(0xE371.toChar()),

    /**
     *  (0xE372)
     */
    BAROMETER(0xE372.toChar()),

    /**
     *  (0xE373)
     */
    HUMIDITY(0xE373.toChar()),

    /**
     *  (0xE374)
     */
    NA(0xE374.toChar()),

    /**
     *  (0xE375)
     */
    FLOOD(0xE375.toChar()),

    /**
     *  (0xE376)
     */
    DAY_CLOUDY_HIGH(0xE376.toChar()),

    /**
     *  (0xE377)
     */
    NIGHT_ALT_CLOUDY_HIGH(0xE377.toChar()),

    /**
     *  (0xE378)
     */
    NIGHT_CLOUDY_HIGH(0xE378.toChar()),

    /**
     *  (0xE379)
     */
    NIGHT_ALT_PARTLY_CLOUDY(0xE379.toChar()),

    /**
     *  (0xE37A)
     */
    SANDSTORM(0xE37A.toChar()),

    /**
     *  (0xE37B)
     */
    NIGHT_PARTLY_CLOUDY(0xE37B.toChar()),

    /**
     *  (0xE37C)
     */
    UMBRELLA(0xE37C.toChar()),

    /**
     *  (0xE37D)
     */
    DAY_WINDY(0xE37D.toChar()),

    /**
     *  (0xE37E)
     */
    NIGHT_ALT_CLOUDY(0xE37E.toChar()),

    /**
     *  (0xE37F)
     */
    DIRECTION_UP_LEFT(0xE37F.toChar()),

    /**
     *  (0xE380)
     */
    DIRECTION_DOWN_RIGHT(0xE380.toChar()),

    /**
     *  (0xE381)
     */
    TIME_12(0xE381.toChar()),

    /**
     *  (0xE382)
     */
    TIME_1(0xE382.toChar()),

    /**
     *  (0xE383)
     */
    TIME_2(0xE383.toChar()),

    /**
     *  (0xE384)
     */
    TIME_3(0xE384.toChar()),

    /**
     *  (0xE385)
     */
    TIME_4(0xE385.toChar()),

    /**
     *  (0xE386)
     */
    TIME_5(0xE386.toChar()),

    /**
     *  (0xE387)
     */
    TIME_6(0xE387.toChar()),

    /**
     *  (0xE388)
     */
    TIME_7(0xE388.toChar()),

    /**
     *  (0xE389)
     */
    TIME_8(0xE389.toChar()),

    /**
     *  (0xE38A)
     */
    TIME_9(0xE38A.toChar()),

    /**
     *  (0xE38B)
     */
    TIME_10(0xE38B.toChar()),

    /**
     *  (0xE38C)
     */
    TIME_11(0xE38C.toChar()),

    /**
     *  (0xE38D)
     */
    MOON_NEW(0xE38D.toChar()),

    /**
     *  (0xE38E)
     */
    MOON_WAXING_CRESCENT_1(0xE38E.toChar()),

    /**
     *  (0xE38F)
     */
    MOON_WAXING_CRESCENT_2(0xE38F.toChar()),

    /**
     *  (0xE390)
     */
    MOON_WAXING_CRESCENT_3(0xE390.toChar()),

    /**
     *  (0xE391)
     */
    MOON_WAXING_CRESCENT_4(0xE391.toChar()),

    /**
     *  (0xE392)
     */
    MOON_WAXING_CRESCENT_5(0xE392.toChar()),

    /**
     *  (0xE393)
     */
    MOON_WAXING_CRESCENT_6(0xE393.toChar()),

    /**
     *  (0xE394)
     */
    MOON_FIRST_QUARTER(0xE394.toChar()),

    /**
     *  (0xE395)
     */
    MOON_WAXING_GIBBOUS_1(0xE395.toChar()),

    /**
     *  (0xE396)
     */
    MOON_WAXING_GIBBOUS_2(0xE396.toChar()),

    /**
     *  (0xE397)
     */
    MOON_WAXING_GIBBOUS_3(0xE397.toChar()),

    /**
     *  (0xE398)
     */
    MOON_WAXING_GIBBOUS_4(0xE398.toChar()),

    /**
     *  (0xE399)
     */
    MOON_WAXING_GIBBOUS_5(0xE399.toChar()),

    /**
     *  (0xE39A)
     */
    MOON_WAXING_GIBBOUS_6(0xE39A.toChar()),

    /**
     *  (0xE39B)
     */
    MOON_FULL(0xE39B.toChar()),

    /**
     *  (0xE39C)
     */
    MOON_WANING_GIBBOUS_1(0xE39C.toChar()),

    /**
     *  (0xE39D)
     */
    MOON_WANING_GIBBOUS_2(0xE39D.toChar()),

    /**
     *  (0xE39E)
     */
    MOON_WANING_GIBBOUS_3(0xE39E.toChar()),

    /**
     *  (0xE39F)
     */
    MOON_WANING_GIBBOUS_4(0xE39F.toChar()),

    /**
     *  (0xE3A0)
     */
    MOON_WANING_GIBBOUS_5(0xE3A0.toChar()),

    /**
     *  (0xE3A1)
     */
    MOON_WANING_GIBBOUS_6(0xE3A1.toChar()),

    /**
     *  (0xE3A2)
     */
    MOON_THIRD_QUARTER(0xE3A2.toChar()),

    /**
     *  (0xE3A3)
     */
    MOON_WANING_CRESCENT_1(0xE3A3.toChar()),

    /**
     *  (0xE3A4)
     */
    MOON_WANING_CRESCENT_2(0xE3A4.toChar()),

    /**
     *  (0xE3A5)
     */
    MOON_WANING_CRESCENT_3(0xE3A5.toChar()),

    /**
     *  (0xE3A6)
     */
    MOON_WANING_CRESCENT_4(0xE3A6.toChar()),

    /**
     *  (0xE3A7)
     */
    MOON_WANING_CRESCENT_5(0xE3A7.toChar()),

    /**
     *  (0xE3A8)
     */
    MOON_WANING_CRESCENT_6(0xE3A8.toChar()),

    /**
     *  (0xE3A9)
     */
    WIND_DIRECTION(0xE3A9.toChar()),

    /**
     *  (0xE3AA)
     */
    DAY_SLEET(0xE3AA.toChar()),

    /**
     *  (0xE3AB)
     */
    NIGHT_SLEET(0xE3AB.toChar()),

    /**
     *  (0xE3AC)
     */
    NIGHT_ALT_SLEET(0xE3AC.toChar()),

    /**
     *  (0xE3AD)
     */
    SLEET(0xE3AD.toChar()),

    /**
     *  (0xE3AE)
     */
    DAY_HAZE(0xE3AE.toChar()),

    /**
     *  (0xE3AF)
     */
    WIND_BEAUFORT_0(0xE3AF.toChar()),

    /**
     *  (0xE3B0)
     */
    WIND_BEAUFORT_1(0xE3B0.toChar()),

    /**
     *  (0xE3B1)
     */
    WIND_BEAUFORT_2(0xE3B1.toChar()),

    /**
     *  (0xE3B2)
     */
    WIND_BEAUFORT_3(0xE3B2.toChar()),

    /**
     *  (0xE3B3)
     */
    WIND_BEAUFORT_4(0xE3B3.toChar()),

    /**
     *  (0xE3B4)
     */
    WIND_BEAUFORT_5(0xE3B4.toChar()),

    /**
     *  (0xE3B5)
     */
    WIND_BEAUFORT_6(0xE3B5.toChar()),

    /**
     *  (0xE3B6)
     */
    WIND_BEAUFORT_7(0xE3B6.toChar()),

    /**
     *  (0xE3B7)
     */
    WIND_BEAUFORT_8(0xE3B7.toChar()),

    /**
     *  (0xE3B8)
     */
    WIND_BEAUFORT_9(0xE3B8.toChar()),

    /**
     *  (0xE3B9)
     */
    WIND_BEAUFORT_10(0xE3B9.toChar()),

    /**
     *  (0xE3BA)
     */
    WIND_BEAUFORT_11(0xE3BA.toChar()),

    /**
     *  (0xE3BB)
     */
    WIND_BEAUFORT_12(0xE3BB.toChar()),

    /**
     *  (0xE3BC)
     */
    DAY_LIGHT_WIND(0xE3BC.toChar()),

    /**
     *  (0xE3BD)
     */
    TSUNAMI(0xE3BD.toChar()),

    /**
     *  (0xE3BE)
     */
    EARTHQUAKE(0xE3BE.toChar()),

    /**
     *  (0xE3BF)
     */
    FIRE(0xE3BF.toChar()),

    /**
     *  (0xE3C0)
     */
    VOLCANO(0xE3C0.toChar()),

    /**
     *  (0xE3C1)
     */
    MOONRISE(0xE3C1.toChar()),

    /**
     *  (0xE3C2)
     */
    MOONSET(0xE3C2.toChar()),

    /**
     *  (0xE3C3)
     */
    TRAIN(0xE3C3.toChar()),

    /**
     *  (0xE3C4)
     */
    SMALL_CRAFT_ADVISORY(0xE3C4.toChar()),

    /**
     *  (0xE3C5)
     */
    GALE_WARNING(0xE3C5.toChar()),

    /**
     *  (0xE3C6)
     */
    STORM_WARNING(0xE3C6.toChar()),

    /**
     *  (0xE3C7)
     */
    HURRICANE_WARNING(0xE3C7.toChar()),

    /**
     *  (0xE3C8)
     */
    MOON_ALT_WAXING_CRESCENT_1(0xE3C8.toChar()),

    /**
     *  (0xE3C9)
     */
    MOON_ALT_WAXING_CRESCENT_2(0xE3C9.toChar()),

    /**
     *  (0xE3CA)
     */
    MOON_ALT_WAXING_CRESCENT_3(0xE3CA.toChar()),

    /**
     *  (0xE3CB)
     */
    MOON_ALT_WAXING_CRESCENT_4(0xE3CB.toChar()),

    /**
     *  (0xE3CC)
     */
    MOON_ALT_WAXING_CRESCENT_5(0xE3CC.toChar()),

    /**
     *  (0xE3CD)
     */
    MOON_ALT_WAXING_CRESCENT_6(0xE3CD.toChar()),

    /**
     *  (0xE3CE)
     */
    MOON_ALT_FIRST_QUARTER(0xE3CE.toChar()),

    /**
     *  (0xE3CF)
     */
    MOON_ALT_WAXING_GIBBOUS_1(0xE3CF.toChar()),

    /**
     *  (0xE3D0)
     */
    MOON_ALT_WAXING_GIBBOUS_2(0xE3D0.toChar()),

    /**
     *  (0xE3D1)
     */
    MOON_ALT_WAXING_GIBBOUS_3(0xE3D1.toChar()),

    /**
     *  (0xE3D2)
     */
    MOON_ALT_WAXING_GIBBOUS_4(0xE3D2.toChar()),

    /**
     *  (0xE3D3)
     */
    MOON_ALT_WAXING_GIBBOUS_5(0xE3D3.toChar()),

    /**
     *  (0xE3D4)
     */
    MOON_ALT_WAXING_GIBBOUS_6(0xE3D4.toChar()),

    /**
     *  (0xE3D5)
     */
    MOON_ALT_FULL(0xE3D5.toChar()),

    /**
     *  (0xE3D6)
     */
    MOON_ALT_WANING_GIBBOUS_1(0xE3D6.toChar()),

    /**
     *  (0xE3D7)
     */
    MOON_ALT_WANING_GIBBOUS_2(0xE3D7.toChar()),

    /**
     *  (0xE3D8)
     */
    MOON_ALT_WANING_GIBBOUS_3(0xE3D8.toChar()),

    /**
     *  (0xE3D9)
     */
    MOON_ALT_WANING_GIBBOUS_4(0xE3D9.toChar()),

    /**
     *  (0xE3DA)
     */
    MOON_ALT_WANING_GIBBOUS_5(0xE3DA.toChar()),

    /**
     *  (0xE3DB)
     */
    MOON_ALT_WANING_GIBBOUS_6(0xE3DB.toChar()),

    /**
     *  (0xE3DC)
     */
    MOON_ALT_THIRD_QUARTER(0xE3DC.toChar()),

    /**
     *  (0xE3DD)
     */
    MOON_ALT_WANING_CRESCENT_1(0xE3DD.toChar()),

    /**
     *  (0xE3DE)
     */
    MOON_ALT_WANING_CRESCENT_2(0xE3DE.toChar()),

    /**
     *  (0xE3DF)
     */
    MOON_ALT_WANING_CRESCENT_3(0xE3DF.toChar()),

    /**
     *  (0xE3E0)
     */
    MOON_ALT_WANING_CRESCENT_4(0xE3E0.toChar()),

    /**
     *  (0xE3E1)
     */
    MOON_ALT_WANING_CRESCENT_5(0xE3E1.toChar()),

    /**
     *  (0xE3E2)
     */
    MOON_ALT_WANING_CRESCENT_6(0xE3E2.toChar()),

    /**
     *  (0xE3E3)
     */
    MOON_ALT_NEW(0xE3E3.toChar());

}