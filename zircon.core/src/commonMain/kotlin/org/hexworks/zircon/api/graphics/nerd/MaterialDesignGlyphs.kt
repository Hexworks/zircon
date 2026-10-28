package org.hexworks.zircon.api.graphics.nerd

public sealed class MaterialDesignGlyphs(
    public val codePoint: Int,
) {

    /** The glyph as a string (a surrogate pair above U+FFFF). */
    public val glyph: String get() = codePointToString(codePoint)


    /**
     * 󰀁 (0xF0001)
     */
    public data object VECTOR_SQUARE : MaterialDesignGlyphs(0xF0001)

    /**
     * 󰀂 (0xF0002)
     */
    public data object ACCESS_POINT_NETWORK : MaterialDesignGlyphs(0xF0002)

    /**
     * 󰀃 (0xF0003)
     */
    public data object ACCESS_POINT : MaterialDesignGlyphs(0xF0003)

    /**
     * 󰀄 (0xF0004)
     */
    public data object ACCOUNT : MaterialDesignGlyphs(0xF0004)

    /**
     * 󰀅 (0xF0005)
     */
    public data object ACCOUNT_ALERT : MaterialDesignGlyphs(0xF0005)

    /**
     * 󰀆 (0xF0006)
     */
    public data object ACCOUNT_BOX : MaterialDesignGlyphs(0xF0006)

    /**
     * 󰀇 (0xF0007)
     */
    public data object ACCOUNT_BOX_OUTLINE : MaterialDesignGlyphs(0xF0007)

    /**
     * 󰀈 (0xF0008)
     */
    public data object ACCOUNT_CHECK : MaterialDesignGlyphs(0xF0008)

    /**
     * 󰀉 (0xF0009)
     */
    public data object ACCOUNT_CIRCLE : MaterialDesignGlyphs(0xF0009)

    /**
     * 󰀊 (0xF000A)
     */
    public data object ACCOUNT_CONVERT : MaterialDesignGlyphs(0xF000A)

    /**
     * 󰀋 (0xF000B)
     */
    public data object ACCOUNT_KEY : MaterialDesignGlyphs(0xF000B)

    /**
     * 󰀌 (0xF000C)
     */
    public data object TOOLTIP_ACCOUNT : MaterialDesignGlyphs(0xF000C)

    /**
     * 󰀍 (0xF000D)
     */
    public data object ACCOUNT_MINUS : MaterialDesignGlyphs(0xF000D)

    /**
     * 󰀎 (0xF000E)
     */
    public data object ACCOUNT_MULTIPLE : MaterialDesignGlyphs(0xF000E)

    /**
     * 󰀏 (0xF000F)
     */
    public data object ACCOUNT_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF000F)

    /**
     * 󰀐 (0xF0010)
     */
    public data object ACCOUNT_MULTIPLE_PLUS : MaterialDesignGlyphs(0xF0010)

    /**
     * 󰀑 (0xF0011)
     */
    public data object ACCOUNT_NETWORK : MaterialDesignGlyphs(0xF0011)

    /**
     * 󰀒 (0xF0012)
     */
    public data object ACCOUNT_OFF : MaterialDesignGlyphs(0xF0012)

    /**
     * 󰀓 (0xF0013)
     */
    public data object ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF0013)

    /**
     * 󰀔 (0xF0014)
     */
    public data object ACCOUNT_PLUS : MaterialDesignGlyphs(0xF0014)

    /**
     * 󰀕 (0xF0015)
     */
    public data object ACCOUNT_REMOVE : MaterialDesignGlyphs(0xF0015)

    /**
     * 󰀖 (0xF0016)
     */
    public data object ACCOUNT_SEARCH : MaterialDesignGlyphs(0xF0016)

    /**
     * 󰀗 (0xF0017)
     */
    public data object ACCOUNT_STAR : MaterialDesignGlyphs(0xF0017)

    /**
     * 󰀘 (0xF0018)
     */
    public data object ORBIT : MaterialDesignGlyphs(0xF0018)

    /**
     * 󰀙 (0xF0019)
     */
    public data object ACCOUNT_SWITCH : MaterialDesignGlyphs(0xF0019)

    /**
     * 󰀚 (0xF001A)
     */
    public data object ADJUST : MaterialDesignGlyphs(0xF001A)

    /**
     * 󰀛 (0xF001B)
     */
    public data object AIR_CONDITIONER : MaterialDesignGlyphs(0xF001B)

    /**
     * 󰀜 (0xF001C)
     */
    public data object AIRBALLOON : MaterialDesignGlyphs(0xF001C)

    /**
     * 󰀝 (0xF001D)
     */
    public data object AIRPLANE : MaterialDesignGlyphs(0xF001D)

    /**
     * 󰀞 (0xF001E)
     */
    public data object AIRPLANE_OFF : MaterialDesignGlyphs(0xF001E)

    /**
     * 󰀟 (0xF001F)
     */
    public data object CAST_VARIANT : MaterialDesignGlyphs(0xF001F)

    /**
     * 󰀠 (0xF0020)
     */
    public data object ALARM : MaterialDesignGlyphs(0xF0020)

    /**
     * 󰀡 (0xF0021)
     */
    public data object ALARM_CHECK : MaterialDesignGlyphs(0xF0021)

    /**
     * 󰀢 (0xF0022)
     */
    public data object ALARM_MULTIPLE : MaterialDesignGlyphs(0xF0022)

    /**
     * 󰀣 (0xF0023)
     */
    public data object ALARM_OFF : MaterialDesignGlyphs(0xF0023)

    /**
     * 󰀤 (0xF0024)
     */
    public data object ALARM_PLUS : MaterialDesignGlyphs(0xF0024)

    /**
     * 󰀥 (0xF0025)
     */
    public data object ALBUM : MaterialDesignGlyphs(0xF0025)

    /**
     * 󰀦 (0xF0026)
     */
    public data object ALERT : MaterialDesignGlyphs(0xF0026)

    /**
     * 󰀧 (0xF0027)
     */
    public data object ALERT_BOX : MaterialDesignGlyphs(0xF0027)

    /**
     * 󰀨 (0xF0028)
     */
    public data object ALERT_CIRCLE : MaterialDesignGlyphs(0xF0028)

    /**
     * 󰀩 (0xF0029)
     */
    public data object ALERT_OCTAGON : MaterialDesignGlyphs(0xF0029)

    /**
     * 󰀪 (0xF002A)
     */
    public data object ALERT_OUTLINE : MaterialDesignGlyphs(0xF002A)

    /**
     * 󰀫 (0xF002B)
     */
    public data object ALPHA : MaterialDesignGlyphs(0xF002B)

    /**
     * 󰀬 (0xF002C)
     */
    public data object ALPHABETICAL : MaterialDesignGlyphs(0xF002C)

    /**
     * 󰀭 (0xF002D)
     */
    public data object GREENHOUSE : MaterialDesignGlyphs(0xF002D)

    /**
     * 󰀮 (0xF002E)
     */
    public data object ROLLERBLADE_OFF : MaterialDesignGlyphs(0xF002E)

    /**
     * 󰀯 (0xF002F)
     */
    public data object AMBULANCE : MaterialDesignGlyphs(0xF002F)

    /**
     * 󰀰 (0xF0030)
     */
    public data object AMPLIFIER : MaterialDesignGlyphs(0xF0030)

    /**
     * 󰀱 (0xF0031)
     */
    public data object ANCHOR : MaterialDesignGlyphs(0xF0031)

    /**
     * 󰀲 (0xF0032)
     */
    public data object ANDROID : MaterialDesignGlyphs(0xF0032)

    /**
     * 󰀳 (0xF0033)
     */
    public data object WEB_PLUS : MaterialDesignGlyphs(0xF0033)

    /**
     * 󰀴 (0xF0034)
     */
    public data object ANDROID_STUDIO : MaterialDesignGlyphs(0xF0034)

    /**
     * 󰀵 (0xF0035)
     */
    public data object APPLE : MaterialDesignGlyphs(0xF0035)

    /**
     * 󰀶 (0xF0036)
     */
    public data object APPLE_FINDER : MaterialDesignGlyphs(0xF0036)

    /**
     * 󰀷 (0xF0037)
     */
    public data object APPLE_IOS : MaterialDesignGlyphs(0xF0037)

    /**
     * 󰀸 (0xF0038)
     */
    public data object APPLE_ICLOUD : MaterialDesignGlyphs(0xF0038)

    /**
     * 󰀹 (0xF0039)
     */
    public data object APPLE_SAFARI : MaterialDesignGlyphs(0xF0039)

    /**
     * 󰀺 (0xF003A)
     */
    public data object FONT_AWESOME : MaterialDesignGlyphs(0xF003A)

    /**
     * 󰀻 (0xF003B)
     */
    public data object APPS : MaterialDesignGlyphs(0xF003B)

    /**
     * 󰀼 (0xF003C)
     */
    public data object ARCHIVE : MaterialDesignGlyphs(0xF003C)

    /**
     * 󰀽 (0xF003D)
     */
    public data object ARRANGE_BRING_FORWARD : MaterialDesignGlyphs(0xF003D)

    /**
     * 󰀾 (0xF003E)
     */
    public data object ARRANGE_BRING_TO_FRONT : MaterialDesignGlyphs(0xF003E)

    /**
     * 󰀿 (0xF003F)
     */
    public data object ARRANGE_SEND_BACKWARD : MaterialDesignGlyphs(0xF003F)

    /**
     * 󰁀 (0xF0040)
     */
    public data object ARRANGE_SEND_TO_BACK : MaterialDesignGlyphs(0xF0040)

    /**
     * 󰁁 (0xF0041)
     */
    public data object ARROW_ALL : MaterialDesignGlyphs(0xF0041)

    /**
     * 󰁂 (0xF0042)
     */
    public data object ARROW_BOTTOM_LEFT : MaterialDesignGlyphs(0xF0042)

    /**
     * 󰁃 (0xF0043)
     */
    public data object ARROW_BOTTOM_RIGHT : MaterialDesignGlyphs(0xF0043)

    /**
     * 󰁄 (0xF0044)
     */
    public data object ARROW_COLLAPSE_ALL : MaterialDesignGlyphs(0xF0044)

    /**
     * 󰁅 (0xF0045)
     */
    public data object ARROW_DOWN : MaterialDesignGlyphs(0xF0045)

    /**
     * 󰁆 (0xF0046)
     */
    public data object ARROW_DOWN_THICK : MaterialDesignGlyphs(0xF0046)

    /**
     * 󰁇 (0xF0047)
     */
    public data object ARROW_DOWN_BOLD_CIRCLE : MaterialDesignGlyphs(0xF0047)

    /**
     * 󰁈 (0xF0048)
     */
    public data object ARROW_DOWN_BOLD_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0048)

    /**
     * 󰁉 (0xF0049)
     */
    public data object ARROW_DOWN_BOLD_HEXAGON_OUTLINE : MaterialDesignGlyphs(0xF0049)

    /**
     * 󰁊 (0xF004A)
     */
    public data object ARROW_DOWN_DROP_CIRCLE : MaterialDesignGlyphs(0xF004A)

    /**
     * 󰁋 (0xF004B)
     */
    public data object ARROW_DOWN_DROP_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF004B)

    /**
     * 󰁌 (0xF004C)
     */
    public data object ARROW_EXPAND_ALL : MaterialDesignGlyphs(0xF004C)

    /**
     * 󰁍 (0xF004D)
     */
    public data object ARROW_LEFT : MaterialDesignGlyphs(0xF004D)

    /**
     * 󰁎 (0xF004E)
     */
    public data object ARROW_LEFT_THICK : MaterialDesignGlyphs(0xF004E)

    /**
     * 󰁏 (0xF004F)
     */
    public data object ARROW_LEFT_BOLD_CIRCLE : MaterialDesignGlyphs(0xF004F)

    /**
     * 󰁐 (0xF0050)
     */
    public data object ARROW_LEFT_BOLD_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0050)

    /**
     * 󰁑 (0xF0051)
     */
    public data object ARROW_LEFT_BOLD_HEXAGON_OUTLINE : MaterialDesignGlyphs(0xF0051)

    /**
     * 󰁒 (0xF0052)
     */
    public data object ARROW_LEFT_DROP_CIRCLE : MaterialDesignGlyphs(0xF0052)

    /**
     * 󰁓 (0xF0053)
     */
    public data object ARROW_LEFT_DROP_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0053)

    /**
     * 󰁔 (0xF0054)
     */
    public data object ARROW_RIGHT : MaterialDesignGlyphs(0xF0054)

    /**
     * 󰁕 (0xF0055)
     */
    public data object ARROW_RIGHT_THICK : MaterialDesignGlyphs(0xF0055)

    /**
     * 󰁖 (0xF0056)
     */
    public data object ARROW_RIGHT_BOLD_CIRCLE : MaterialDesignGlyphs(0xF0056)

    /**
     * 󰁗 (0xF0057)
     */
    public data object ARROW_RIGHT_BOLD_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0057)

    /**
     * 󰁘 (0xF0058)
     */
    public data object ARROW_RIGHT_BOLD_HEXAGON_OUTLINE : MaterialDesignGlyphs(0xF0058)

    /**
     * 󰁙 (0xF0059)
     */
    public data object ARROW_RIGHT_DROP_CIRCLE : MaterialDesignGlyphs(0xF0059)

    /**
     * 󰁚 (0xF005A)
     */
    public data object ARROW_RIGHT_DROP_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF005A)

    /**
     * 󰁛 (0xF005B)
     */
    public data object ARROW_TOP_LEFT : MaterialDesignGlyphs(0xF005B)

    /**
     * 󰁜 (0xF005C)
     */
    public data object ARROW_TOP_RIGHT : MaterialDesignGlyphs(0xF005C)

    /**
     * 󰁝 (0xF005D)
     */
    public data object ARROW_UP : MaterialDesignGlyphs(0xF005D)

    /**
     * 󰁞 (0xF005E)
     */
    public data object ARROW_UP_THICK : MaterialDesignGlyphs(0xF005E)

    /**
     * 󰁟 (0xF005F)
     */
    public data object ARROW_UP_BOLD_CIRCLE : MaterialDesignGlyphs(0xF005F)

    /**
     * 󰁠 (0xF0060)
     */
    public data object ARROW_UP_BOLD_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0060)

    /**
     * 󰁡 (0xF0061)
     */
    public data object ARROW_UP_BOLD_HEXAGON_OUTLINE : MaterialDesignGlyphs(0xF0061)

    /**
     * 󰁢 (0xF0062)
     */
    public data object ARROW_UP_DROP_CIRCLE : MaterialDesignGlyphs(0xF0062)

    /**
     * 󰁣 (0xF0063)
     */
    public data object ARROW_UP_DROP_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0063)

    /**
     * 󰁤 (0xF0064)
     */
    public data object ASSISTANT : MaterialDesignGlyphs(0xF0064)

    /**
     * 󰁥 (0xF0065)
     */
    public data object AT : MaterialDesignGlyphs(0xF0065)

    /**
     * 󰁦 (0xF0066)
     */
    public data object ATTACHMENT : MaterialDesignGlyphs(0xF0066)

    /**
     * 󰁧 (0xF0067)
     */
    public data object BOOK_MUSIC : MaterialDesignGlyphs(0xF0067)

    /**
     * 󰁨 (0xF0068)
     */
    public data object AUTO_FIX : MaterialDesignGlyphs(0xF0068)

    /**
     * 󰁩 (0xF0069)
     */
    public data object AUTO_UPLOAD : MaterialDesignGlyphs(0xF0069)

    /**
     * 󰁪 (0xF006A)
     */
    public data object AUTORENEW : MaterialDesignGlyphs(0xF006A)

    /**
     * 󰁫 (0xF006B)
     */
    public data object AV_TIMER : MaterialDesignGlyphs(0xF006B)

    /**
     * 󰁬 (0xF006C)
     */
    public data object BABY : MaterialDesignGlyphs(0xF006C)

    /**
     * 󰁭 (0xF006D)
     */
    public data object BACKBURGER : MaterialDesignGlyphs(0xF006D)

    /**
     * 󰁮 (0xF006E)
     */
    public data object BACKSPACE : MaterialDesignGlyphs(0xF006E)

    /**
     * 󰁯 (0xF006F)
     */
    public data object BACKUP_RESTORE : MaterialDesignGlyphs(0xF006F)

    /**
     * 󰁰 (0xF0070)
     */
    public data object BANK : MaterialDesignGlyphs(0xF0070)

    /**
     * 󰁱 (0xF0071)
     */
    public data object BARCODE : MaterialDesignGlyphs(0xF0071)

    /**
     * 󰁲 (0xF0072)
     */
    public data object BARCODE_SCAN : MaterialDesignGlyphs(0xF0072)

    /**
     * 󰁳 (0xF0073)
     */
    public data object BARLEY : MaterialDesignGlyphs(0xF0073)

    /**
     * 󰁴 (0xF0074)
     */
    public data object BARREL : MaterialDesignGlyphs(0xF0074)

    /**
     * 󰁵 (0xF0075)
     */
    public data object INCOGNITO_OFF : MaterialDesignGlyphs(0xF0075)

    /**
     * 󰁶 (0xF0076)
     */
    public data object BASKET : MaterialDesignGlyphs(0xF0076)

    /**
     * 󰁷 (0xF0077)
     */
    public data object BASKET_FILL : MaterialDesignGlyphs(0xF0077)

    /**
     * 󰁸 (0xF0078)
     */
    public data object BASKET_UNFILL : MaterialDesignGlyphs(0xF0078)

    /**
     * 󰁹 (0xF0079)
     */
    public data object BATTERY : MaterialDesignGlyphs(0xF0079)

    /**
     * 󰁺 (0xF007A)
     */
    public data object BATTERY_10 : MaterialDesignGlyphs(0xF007A)

    /**
     * 󰁻 (0xF007B)
     */
    public data object BATTERY_20 : MaterialDesignGlyphs(0xF007B)

    /**
     * 󰁼 (0xF007C)
     */
    public data object BATTERY_30 : MaterialDesignGlyphs(0xF007C)

    /**
     * 󰁽 (0xF007D)
     */
    public data object BATTERY_40 : MaterialDesignGlyphs(0xF007D)

    /**
     * 󰁾 (0xF007E)
     */
    public data object BATTERY_50 : MaterialDesignGlyphs(0xF007E)

    /**
     * 󰁿 (0xF007F)
     */
    public data object BATTERY_60 : MaterialDesignGlyphs(0xF007F)

    /**
     * 󰂀 (0xF0080)
     */
    public data object BATTERY_70 : MaterialDesignGlyphs(0xF0080)

    /**
     * 󰂁 (0xF0081)
     */
    public data object BATTERY_80 : MaterialDesignGlyphs(0xF0081)

    /**
     * 󰂂 (0xF0082)
     */
    public data object BATTERY_90 : MaterialDesignGlyphs(0xF0082)

    /**
     * 󰂃 (0xF0083)
     */
    public data object BATTERY_ALERT : MaterialDesignGlyphs(0xF0083)

    /**
     * 󰂄 (0xF0084)
     */
    public data object BATTERY_CHARGING : MaterialDesignGlyphs(0xF0084)

    /**
     * 󰂅 (0xF0085)
     */
    public data object BATTERY_CHARGING_100 : MaterialDesignGlyphs(0xF0085)

    /**
     * 󰂆 (0xF0086)
     */
    public data object BATTERY_CHARGING_20 : MaterialDesignGlyphs(0xF0086)

    /**
     * 󰂇 (0xF0087)
     */
    public data object BATTERY_CHARGING_30 : MaterialDesignGlyphs(0xF0087)

    /**
     * 󰂈 (0xF0088)
     */
    public data object BATTERY_CHARGING_40 : MaterialDesignGlyphs(0xF0088)

    /**
     * 󰂉 (0xF0089)
     */
    public data object BATTERY_CHARGING_60 : MaterialDesignGlyphs(0xF0089)

    /**
     * 󰂊 (0xF008A)
     */
    public data object BATTERY_CHARGING_80 : MaterialDesignGlyphs(0xF008A)

    /**
     * 󰂋 (0xF008B)
     */
    public data object BATTERY_CHARGING_90 : MaterialDesignGlyphs(0xF008B)

    /**
     * 󰂌 (0xF008C)
     */
    public data object BATTERY_MINUS_VARIANT : MaterialDesignGlyphs(0xF008C)

    /**
     * 󰂍 (0xF008D)
     */
    public data object BATTERY_NEGATIVE : MaterialDesignGlyphs(0xF008D)

    /**
     * 󰂎 (0xF008E)
     */
    public data object BATTERY_OUTLINE : MaterialDesignGlyphs(0xF008E)

    /**
     * 󰂏 (0xF008F)
     */
    public data object BATTERY_PLUS_VARIANT : MaterialDesignGlyphs(0xF008F)

    /**
     * 󰂐 (0xF0090)
     */
    public data object BATTERY_POSITIVE : MaterialDesignGlyphs(0xF0090)

    /**
     * 󰂑 (0xF0091)
     */
    public data object BATTERY_UNKNOWN : MaterialDesignGlyphs(0xF0091)

    /**
     * 󰂒 (0xF0092)
     */
    public data object BEACH : MaterialDesignGlyphs(0xF0092)

    /**
     * 󰂓 (0xF0093)
     */
    public data object FLASK : MaterialDesignGlyphs(0xF0093)

    /**
     * 󰂔 (0xF0094)
     */
    public data object FLASK_EMPTY : MaterialDesignGlyphs(0xF0094)

    /**
     * 󰂕 (0xF0095)
     */
    public data object FLASK_EMPTY_OUTLINE : MaterialDesignGlyphs(0xF0095)

    /**
     * 󰂖 (0xF0096)
     */
    public data object FLASK_OUTLINE : MaterialDesignGlyphs(0xF0096)

    /**
     * 󰂗 (0xF0097)
     */
    public data object BUNK_BED_OUTLINE : MaterialDesignGlyphs(0xF0097)

    /**
     * 󰂘 (0xF0098)
     */
    public data object BEER : MaterialDesignGlyphs(0xF0098)

    /**
     * 󰂙 (0xF0099)
     */
    public data object BED_OUTLINE : MaterialDesignGlyphs(0xF0099)

    /**
     * 󰂚 (0xF009A)
     */
    public data object BELL : MaterialDesignGlyphs(0xF009A)

    /**
     * 󰂛 (0xF009B)
     */
    public data object BELL_OFF : MaterialDesignGlyphs(0xF009B)

    /**
     * 󰂜 (0xF009C)
     */
    public data object BELL_OUTLINE : MaterialDesignGlyphs(0xF009C)

    /**
     * 󰂝 (0xF009D)
     */
    public data object BELL_PLUS : MaterialDesignGlyphs(0xF009D)

    /**
     * 󰂞 (0xF009E)
     */
    public data object BELL_RING : MaterialDesignGlyphs(0xF009E)

    /**
     * 󰂟 (0xF009F)
     */
    public data object BELL_RING_OUTLINE : MaterialDesignGlyphs(0xF009F)

    /**
     * 󰂠 (0xF00A0)
     */
    public data object BELL_SLEEP : MaterialDesignGlyphs(0xF00A0)

    /**
     * 󰂡 (0xF00A1)
     */
    public data object BETA : MaterialDesignGlyphs(0xF00A1)

    /**
     * 󰂢 (0xF00A2)
     */
    public data object BOOK_CROSS : MaterialDesignGlyphs(0xF00A2)

    /**
     * 󰂣 (0xF00A3)
     */
    public data object BIKE : MaterialDesignGlyphs(0xF00A3)

    /**
     * 󰂤 (0xF00A4)
     */
    public data object MICROSOFT_BING : MaterialDesignGlyphs(0xF00A4)

    /**
     * 󰂥 (0xF00A5)
     */
    public data object BINOCULARS : MaterialDesignGlyphs(0xF00A5)

    /**
     * 󰂦 (0xF00A6)
     */
    public data object BIO : MaterialDesignGlyphs(0xF00A6)

    /**
     * 󰂧 (0xF00A7)
     */
    public data object BIOHAZARD : MaterialDesignGlyphs(0xF00A7)

    /**
     * 󰂨 (0xF00A8)
     */
    public data object BITBUCKET : MaterialDesignGlyphs(0xF00A8)

    /**
     * 󰂩 (0xF00A9)
     */
    public data object BLACK_MESA : MaterialDesignGlyphs(0xF00A9)

    /**
     * 󰂪 (0xF00AA)
     */
    public data object SHIELD_REFRESH : MaterialDesignGlyphs(0xF00AA)

    /**
     * 󰂫 (0xF00AB)
     */
    public data object BLENDER_SOFTWARE : MaterialDesignGlyphs(0xF00AB)

    /**
     * 󰂬 (0xF00AC)
     */
    public data object BLINDS : MaterialDesignGlyphs(0xF00AC)

    /**
     * 󰂭 (0xF00AD)
     */
    public data object BLOCK_HELPER : MaterialDesignGlyphs(0xF00AD)

    /**
     * 󰂮 (0xF00AE)
     */
    public data object APPLICATION_EDIT : MaterialDesignGlyphs(0xF00AE)

    /**
     * 󰂯 (0xF00AF)
     */
    public data object BLUETOOTH : MaterialDesignGlyphs(0xF00AF)

    /**
     * 󰂰 (0xF00B0)
     */
    public data object BLUETOOTH_AUDIO : MaterialDesignGlyphs(0xF00B0)

    /**
     * 󰂱 (0xF00B1)
     */
    public data object BLUETOOTH_CONNECT : MaterialDesignGlyphs(0xF00B1)

    /**
     * 󰂲 (0xF00B2)
     */
    public data object BLUETOOTH_OFF : MaterialDesignGlyphs(0xF00B2)

    /**
     * 󰂳 (0xF00B3)
     */
    public data object BLUETOOTH_SETTINGS : MaterialDesignGlyphs(0xF00B3)

    /**
     * 󰂴 (0xF00B4)
     */
    public data object BLUETOOTH_TRANSFER : MaterialDesignGlyphs(0xF00B4)

    /**
     * 󰂵 (0xF00B5)
     */
    public data object BLUR : MaterialDesignGlyphs(0xF00B5)

    /**
     * 󰂶 (0xF00B6)
     */
    public data object BLUR_LINEAR : MaterialDesignGlyphs(0xF00B6)

    /**
     * 󰂷 (0xF00B7)
     */
    public data object BLUR_OFF : MaterialDesignGlyphs(0xF00B7)

    /**
     * 󰂸 (0xF00B8)
     */
    public data object BLUR_RADIAL : MaterialDesignGlyphs(0xF00B8)

    /**
     * 󰂹 (0xF00B9)
     */
    public data object BONE : MaterialDesignGlyphs(0xF00B9)

    /**
     * 󰂺 (0xF00BA)
     */
    public data object BOOK : MaterialDesignGlyphs(0xF00BA)

    /**
     * 󰂻 (0xF00BB)
     */
    public data object BOOK_MULTIPLE : MaterialDesignGlyphs(0xF00BB)

    /**
     * 󰂼 (0xF00BC)
     */
    public data object BOOK_VARIANT_MULTIPLE : MaterialDesignGlyphs(0xF00BC)

    /**
     * 󰂽 (0xF00BD)
     */
    public data object BOOK_OPEN : MaterialDesignGlyphs(0xF00BD)

    /**
     * 󰂾 (0xF00BE)
     */
    public data object BOOK_OPEN_BLANK_VARIANT : MaterialDesignGlyphs(0xF00BE)

    /**
     * 󰂿 (0xF00BF)
     */
    public data object BOOK_VARIANT : MaterialDesignGlyphs(0xF00BF)

    /**
     * 󰃀 (0xF00C0)
     */
    public data object BOOKMARK : MaterialDesignGlyphs(0xF00C0)

    /**
     * 󰃁 (0xF00C1)
     */
    public data object BOOKMARK_CHECK : MaterialDesignGlyphs(0xF00C1)

    /**
     * 󰃂 (0xF00C2)
     */
    public data object BOOKMARK_MUSIC : MaterialDesignGlyphs(0xF00C2)

    /**
     * 󰃃 (0xF00C3)
     */
    public data object BOOKMARK_OUTLINE : MaterialDesignGlyphs(0xF00C3)

    /**
     * 󰃄 (0xF00C4)
     */
    public data object BOOKMARK_PLUS_OUTLINE : MaterialDesignGlyphs(0xF00C4)

    /**
     * 󰃅 (0xF00C5)
     */
    public data object BOOKMARK_PLUS : MaterialDesignGlyphs(0xF00C5)

    /**
     * 󰃆 (0xF00C6)
     */
    public data object BOOKMARK_REMOVE : MaterialDesignGlyphs(0xF00C6)

    /**
     * 󰃇 (0xF00C7)
     */
    public data object BORDER_ALL : MaterialDesignGlyphs(0xF00C7)

    /**
     * 󰃈 (0xF00C8)
     */
    public data object BORDER_BOTTOM : MaterialDesignGlyphs(0xF00C8)

    /**
     * 󰃉 (0xF00C9)
     */
    public data object BORDER_COLOR : MaterialDesignGlyphs(0xF00C9)

    /**
     * 󰃊 (0xF00CA)
     */
    public data object BORDER_HORIZONTAL : MaterialDesignGlyphs(0xF00CA)

    /**
     * 󰃋 (0xF00CB)
     */
    public data object BORDER_INSIDE : MaterialDesignGlyphs(0xF00CB)

    /**
     * 󰃌 (0xF00CC)
     */
    public data object BORDER_LEFT : MaterialDesignGlyphs(0xF00CC)

    /**
     * 󰃍 (0xF00CD)
     */
    public data object BORDER_NONE : MaterialDesignGlyphs(0xF00CD)

    /**
     * 󰃎 (0xF00CE)
     */
    public data object BORDER_OUTSIDE : MaterialDesignGlyphs(0xF00CE)

    /**
     * 󰃏 (0xF00CF)
     */
    public data object BORDER_RIGHT : MaterialDesignGlyphs(0xF00CF)

    /**
     * 󰃐 (0xF00D0)
     */
    public data object BORDER_STYLE : MaterialDesignGlyphs(0xF00D0)

    /**
     * 󰃑 (0xF00D1)
     */
    public data object BORDER_TOP : MaterialDesignGlyphs(0xF00D1)

    /**
     * 󰃒 (0xF00D2)
     */
    public data object BORDER_VERTICAL : MaterialDesignGlyphs(0xF00D2)

    /**
     * 󰃓 (0xF00D3)
     */
    public data object BOWLING : MaterialDesignGlyphs(0xF00D3)

    /**
     * 󰃔 (0xF00D4)
     */
    public data object BOX : MaterialDesignGlyphs(0xF00D4)

    /**
     * 󰃕 (0xF00D5)
     */
    public data object BOX_CUTTER : MaterialDesignGlyphs(0xF00D5)

    /**
     * 󰃖 (0xF00D6)
     */
    public data object BRIEFCASE : MaterialDesignGlyphs(0xF00D6)

    /**
     * 󰃗 (0xF00D7)
     */
    public data object BRIEFCASE_CHECK : MaterialDesignGlyphs(0xF00D7)

    /**
     * 󰃘 (0xF00D8)
     */
    public data object BRIEFCASE_DOWNLOAD : MaterialDesignGlyphs(0xF00D8)

    /**
     * 󰃙 (0xF00D9)
     */
    public data object BRIEFCASE_UPLOAD : MaterialDesignGlyphs(0xF00D9)

    /**
     * 󰃚 (0xF00DA)
     */
    public data object BRIGHTNESS_1 : MaterialDesignGlyphs(0xF00DA)

    /**
     * 󰃛 (0xF00DB)
     */
    public data object BRIGHTNESS_2 : MaterialDesignGlyphs(0xF00DB)

    /**
     * 󰃜 (0xF00DC)
     */
    public data object BRIGHTNESS_3 : MaterialDesignGlyphs(0xF00DC)

    /**
     * 󰃝 (0xF00DD)
     */
    public data object BRIGHTNESS_4 : MaterialDesignGlyphs(0xF00DD)

    /**
     * 󰃞 (0xF00DE)
     */
    public data object BRIGHTNESS_5 : MaterialDesignGlyphs(0xF00DE)

    /**
     * 󰃟 (0xF00DF)
     */
    public data object BRIGHTNESS_6 : MaterialDesignGlyphs(0xF00DF)

    /**
     * 󰃠 (0xF00E0)
     */
    public data object BRIGHTNESS_7 : MaterialDesignGlyphs(0xF00E0)

    /**
     * 󰃡 (0xF00E1)
     */
    public data object BRIGHTNESS_AUTO : MaterialDesignGlyphs(0xF00E1)

    /**
     * 󰃢 (0xF00E2)
     */
    public data object BROOM : MaterialDesignGlyphs(0xF00E2)

    /**
     * 󰃣 (0xF00E3)
     */
    public data object BRUSH : MaterialDesignGlyphs(0xF00E3)

    /**
     * 󰃤 (0xF00E4)
     */
    public data object BUG : MaterialDesignGlyphs(0xF00E4)

    /**
     * 󰃥 (0xF00E5)
     */
    public data object BULLETIN_BOARD : MaterialDesignGlyphs(0xF00E5)

    /**
     * 󰃦 (0xF00E6)
     */
    public data object BULLHORN : MaterialDesignGlyphs(0xF00E6)

    /**
     * 󰃧 (0xF00E7)
     */
    public data object BUS : MaterialDesignGlyphs(0xF00E7)

    /**
     * 󰃨 (0xF00E8)
     */
    public data object CACHED : MaterialDesignGlyphs(0xF00E8)

    /**
     * 󰃩 (0xF00E9)
     */
    public data object CAKE : MaterialDesignGlyphs(0xF00E9)

    /**
     * 󰃪 (0xF00EA)
     */
    public data object CAKE_LAYERED : MaterialDesignGlyphs(0xF00EA)

    /**
     * 󰃫 (0xF00EB)
     */
    public data object CAKE_VARIANT : MaterialDesignGlyphs(0xF00EB)

    /**
     * 󰃬 (0xF00EC)
     */
    public data object CALCULATOR : MaterialDesignGlyphs(0xF00EC)

    /**
     * 󰃭 (0xF00ED)
     */
    public data object CALENDAR : MaterialDesignGlyphs(0xF00ED)

    /**
     * 󰃮 (0xF00EE)
     */
    public data object CALENDAR_BLANK : MaterialDesignGlyphs(0xF00EE)

    /**
     * 󰃯 (0xF00EF)
     */
    public data object CALENDAR_CHECK : MaterialDesignGlyphs(0xF00EF)

    /**
     * 󰃰 (0xF00F0)
     */
    public data object CALENDAR_CLOCK : MaterialDesignGlyphs(0xF00F0)

    /**
     * 󰃱 (0xF00F1)
     */
    public data object CALENDAR_MULTIPLE : MaterialDesignGlyphs(0xF00F1)

    /**
     * 󰃲 (0xF00F2)
     */
    public data object CALENDAR_MULTIPLE_CHECK : MaterialDesignGlyphs(0xF00F2)

    /**
     * 󰃳 (0xF00F3)
     */
    public data object CALENDAR_PLUS : MaterialDesignGlyphs(0xF00F3)

    /**
     * 󰃴 (0xF00F4)
     */
    public data object CALENDAR_REMOVE : MaterialDesignGlyphs(0xF00F4)

    /**
     * 󰃵 (0xF00F5)
     */
    public data object CALENDAR_TEXT : MaterialDesignGlyphs(0xF00F5)

    /**
     * 󰃶 (0xF00F6)
     */
    public data object CALENDAR_TODAY : MaterialDesignGlyphs(0xF00F6)

    /**
     * 󰃷 (0xF00F7)
     */
    public data object CALL_MADE : MaterialDesignGlyphs(0xF00F7)

    /**
     * 󰃸 (0xF00F8)
     */
    public data object CALL_MERGE : MaterialDesignGlyphs(0xF00F8)

    /**
     * 󰃹 (0xF00F9)
     */
    public data object CALL_MISSED : MaterialDesignGlyphs(0xF00F9)

    /**
     * 󰃺 (0xF00FA)
     */
    public data object CALL_RECEIVED : MaterialDesignGlyphs(0xF00FA)

    /**
     * 󰃻 (0xF00FB)
     */
    public data object CALL_SPLIT : MaterialDesignGlyphs(0xF00FB)

    /**
     * 󰃼 (0xF00FC)
     */
    public data object CAMCORDER : MaterialDesignGlyphs(0xF00FC)

    /**
     * 󰃽 (0xF00FD)
     */
    public data object VIDEO_BOX : MaterialDesignGlyphs(0xF00FD)

    /**
     * 󰃾 (0xF00FE)
     */
    public data object VIDEO_BOX_OFF : MaterialDesignGlyphs(0xF00FE)

    /**
     * 󰃿 (0xF00FF)
     */
    public data object CAMCORDER_OFF : MaterialDesignGlyphs(0xF00FF)

    /**
     * 󰄀 (0xF0100)
     */
    public data object CAMERA : MaterialDesignGlyphs(0xF0100)

    /**
     * 󰄁 (0xF0101)
     */
    public data object CAMERA_ENHANCE : MaterialDesignGlyphs(0xF0101)

    /**
     * 󰄂 (0xF0102)
     */
    public data object CAMERA_FRONT : MaterialDesignGlyphs(0xF0102)

    /**
     * 󰄃 (0xF0103)
     */
    public data object CAMERA_FRONT_VARIANT : MaterialDesignGlyphs(0xF0103)

    /**
     * 󰄄 (0xF0104)
     */
    public data object CAMERA_IRIS : MaterialDesignGlyphs(0xF0104)

    /**
     * 󰄅 (0xF0105)
     */
    public data object CAMERA_PARTY_MODE : MaterialDesignGlyphs(0xF0105)

    /**
     * 󰄆 (0xF0106)
     */
    public data object CAMERA_REAR : MaterialDesignGlyphs(0xF0106)

    /**
     * 󰄇 (0xF0107)
     */
    public data object CAMERA_REAR_VARIANT : MaterialDesignGlyphs(0xF0107)

    /**
     * 󰄈 (0xF0108)
     */
    public data object CAMERA_SWITCH : MaterialDesignGlyphs(0xF0108)

    /**
     * 󰄉 (0xF0109)
     */
    public data object CAMERA_TIMER : MaterialDesignGlyphs(0xF0109)

    /**
     * 󰄊 (0xF010A)
     */
    public data object CANDYCANE : MaterialDesignGlyphs(0xF010A)

    /**
     * 󰄋 (0xF010B)
     */
    public data object CAR : MaterialDesignGlyphs(0xF010B)

    /**
     * 󰄌 (0xF010C)
     */
    public data object CAR_BATTERY : MaterialDesignGlyphs(0xF010C)

    /**
     * 󰄍 (0xF010D)
     */
    public data object CAR_CONNECTED : MaterialDesignGlyphs(0xF010D)

    /**
     * 󰄎 (0xF010E)
     */
    public data object CAR_WASH : MaterialDesignGlyphs(0xF010E)

    /**
     * 󰄏 (0xF010F)
     */
    public data object CARROT : MaterialDesignGlyphs(0xF010F)

    /**
     * 󰄐 (0xF0110)
     */
    public data object CART : MaterialDesignGlyphs(0xF0110)

    /**
     * 󰄑 (0xF0111)
     */
    public data object CART_OUTLINE : MaterialDesignGlyphs(0xF0111)

    /**
     * 󰄒 (0xF0112)
     */
    public data object CART_PLUS : MaterialDesignGlyphs(0xF0112)

    /**
     * 󰄓 (0xF0113)
     */
    public data object CASE_SENSITIVE_ALT : MaterialDesignGlyphs(0xF0113)

    /**
     * 󰄔 (0xF0114)
     */
    public data object CASH : MaterialDesignGlyphs(0xF0114)

    /**
     * 󰄕 (0xF0115)
     */
    public data object CASH_100 : MaterialDesignGlyphs(0xF0115)

    /**
     * 󰄖 (0xF0116)
     */
    public data object CASH_MULTIPLE : MaterialDesignGlyphs(0xF0116)

    /**
     * 󰄗 (0xF0117)
     */
    public data object CHECKBOX_BLANK_BADGE_OUTLINE : MaterialDesignGlyphs(0xF0117)

    /**
     * 󰄘 (0xF0118)
     */
    public data object CAST : MaterialDesignGlyphs(0xF0118)

    /**
     * 󰄙 (0xF0119)
     */
    public data object CAST_CONNECTED : MaterialDesignGlyphs(0xF0119)

    /**
     * 󰄚 (0xF011A)
     */
    public data object CASTLE : MaterialDesignGlyphs(0xF011A)

    /**
     * 󰄛 (0xF011B)
     */
    public data object CAT : MaterialDesignGlyphs(0xF011B)

    /**
     * 󰄜 (0xF011C)
     */
    public data object CELLPHONE : MaterialDesignGlyphs(0xF011C)

    /**
     * 󰄝 (0xF011D)
     */
    public data object TRAY_ARROW_UP : MaterialDesignGlyphs(0xF011D)

    /**
     * 󰄞 (0xF011E)
     */
    public data object CELLPHONE_BASIC : MaterialDesignGlyphs(0xF011E)

    /**
     * 󰄟 (0xF011F)
     */
    public data object CELLPHONE_DOCK : MaterialDesignGlyphs(0xF011F)

    /**
     * 󰄠 (0xF0120)
     */
    public data object TRAY_ARROW_DOWN : MaterialDesignGlyphs(0xF0120)

    /**
     * 󰄡 (0xF0121)
     */
    public data object CELLPHONE_LINK : MaterialDesignGlyphs(0xF0121)

    /**
     * 󰄢 (0xF0122)
     */
    public data object CELLPHONE_LINK_OFF : MaterialDesignGlyphs(0xF0122)

    /**
     * 󰄣 (0xF0123)
     */
    public data object CELLPHONE_SETTINGS : MaterialDesignGlyphs(0xF0123)

    /**
     * 󰄤 (0xF0124)
     */
    public data object CERTIFICATE : MaterialDesignGlyphs(0xF0124)

    /**
     * 󰄥 (0xF0125)
     */
    public data object CHAIR_SCHOOL : MaterialDesignGlyphs(0xF0125)

    /**
     * 󰄦 (0xF0126)
     */
    public data object CHART_ARC : MaterialDesignGlyphs(0xF0126)

    /**
     * 󰄧 (0xF0127)
     */
    public data object CHART_AREASPLINE : MaterialDesignGlyphs(0xF0127)

    /**
     * 󰄨 (0xF0128)
     */
    public data object CHART_BAR : MaterialDesignGlyphs(0xF0128)

    /**
     * 󰄩 (0xF0129)
     */
    public data object CHART_HISTOGRAM : MaterialDesignGlyphs(0xF0129)

    /**
     * 󰄪 (0xF012A)
     */
    public data object CHART_LINE : MaterialDesignGlyphs(0xF012A)

    /**
     * 󰄫 (0xF012B)
     */
    public data object CHART_PIE : MaterialDesignGlyphs(0xF012B)

    /**
     * 󰄬 (0xF012C)
     */
    public data object CHECK : MaterialDesignGlyphs(0xF012C)

    /**
     * 󰄭 (0xF012D)
     */
    public data object CHECK_ALL : MaterialDesignGlyphs(0xF012D)

    /**
     * 󰄮 (0xF012E)
     */
    public data object CHECKBOX_BLANK : MaterialDesignGlyphs(0xF012E)

    /**
     * 󰄯 (0xF012F)
     */
    public data object CHECKBOX_BLANK_CIRCLE : MaterialDesignGlyphs(0xF012F)

    /**
     * 󰄰 (0xF0130)
     */
    public data object CHECKBOX_BLANK_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0130)

    /**
     * 󰄱 (0xF0131)
     */
    public data object CHECKBOX_BLANK_OUTLINE : MaterialDesignGlyphs(0xF0131)

    /**
     * 󰄲 (0xF0132)
     */
    public data object CHECKBOX_MARKED : MaterialDesignGlyphs(0xF0132)

    /**
     * 󰄳 (0xF0133)
     */
    public data object CHECKBOX_MARKED_CIRCLE : MaterialDesignGlyphs(0xF0133)

    /**
     * 󰄴 (0xF0134)
     */
    public data object CHECKBOX_MARKED_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0134)

    /**
     * 󰄵 (0xF0135)
     */
    public data object CHECKBOX_MARKED_OUTLINE : MaterialDesignGlyphs(0xF0135)

    /**
     * 󰄶 (0xF0136)
     */
    public data object CHECKBOX_MULTIPLE_BLANK : MaterialDesignGlyphs(0xF0136)

    /**
     * 󰄷 (0xF0137)
     */
    public data object CHECKBOX_MULTIPLE_BLANK_OUTLINE : MaterialDesignGlyphs(0xF0137)

    /**
     * 󰄸 (0xF0138)
     */
    public data object CHECKBOX_MULTIPLE_MARKED : MaterialDesignGlyphs(0xF0138)

    /**
     * 󰄹 (0xF0139)
     */
    public data object CHECKBOX_MULTIPLE_MARKED_OUTLINE : MaterialDesignGlyphs(0xF0139)

    /**
     * 󰄺 (0xF013A)
     */
    public data object CHECKERBOARD : MaterialDesignGlyphs(0xF013A)

    /**
     * 󰄻 (0xF013B)
     */
    public data object CHEMICAL_WEAPON : MaterialDesignGlyphs(0xF013B)

    /**
     * 󰄼 (0xF013C)
     */
    public data object CHEVRON_DOUBLE_DOWN : MaterialDesignGlyphs(0xF013C)

    /**
     * 󰄽 (0xF013D)
     */
    public data object CHEVRON_DOUBLE_LEFT : MaterialDesignGlyphs(0xF013D)

    /**
     * 󰄾 (0xF013E)
     */
    public data object CHEVRON_DOUBLE_RIGHT : MaterialDesignGlyphs(0xF013E)

    /**
     * 󰄿 (0xF013F)
     */
    public data object CHEVRON_DOUBLE_UP : MaterialDesignGlyphs(0xF013F)

    /**
     * 󰅀 (0xF0140)
     */
    public data object CHEVRON_DOWN : MaterialDesignGlyphs(0xF0140)

    /**
     * 󰅁 (0xF0141)
     */
    public data object CHEVRON_LEFT : MaterialDesignGlyphs(0xF0141)

    /**
     * 󰅂 (0xF0142)
     */
    public data object CHEVRON_RIGHT : MaterialDesignGlyphs(0xF0142)

    /**
     * 󰅃 (0xF0143)
     */
    public data object CHEVRON_UP : MaterialDesignGlyphs(0xF0143)

    /**
     * 󰅄 (0xF0144)
     */
    public data object CHURCH : MaterialDesignGlyphs(0xF0144)

    /**
     * 󰅅 (0xF0145)
     */
    public data object ROLLER_SKATE_OFF : MaterialDesignGlyphs(0xF0145)

    /**
     * 󰅆 (0xF0146)
     */
    public data object CITY : MaterialDesignGlyphs(0xF0146)

    /**
     * 󰅇 (0xF0147)
     */
    public data object CLIPBOARD : MaterialDesignGlyphs(0xF0147)

    /**
     * 󰅈 (0xF0148)
     */
    public data object CLIPBOARD_ACCOUNT : MaterialDesignGlyphs(0xF0148)

    /**
     * 󰅉 (0xF0149)
     */
    public data object CLIPBOARD_ALERT : MaterialDesignGlyphs(0xF0149)

    /**
     * 󰅊 (0xF014A)
     */
    public data object CLIPBOARD_ARROW_DOWN : MaterialDesignGlyphs(0xF014A)

    /**
     * 󰅋 (0xF014B)
     */
    public data object CLIPBOARD_ARROW_LEFT : MaterialDesignGlyphs(0xF014B)

    /**
     * 󰅌 (0xF014C)
     */
    public data object CLIPBOARD_OUTLINE : MaterialDesignGlyphs(0xF014C)

    /**
     * 󰅍 (0xF014D)
     */
    public data object CLIPBOARD_TEXT : MaterialDesignGlyphs(0xF014D)

    /**
     * 󰅎 (0xF014E)
     */
    public data object CLIPBOARD_CHECK : MaterialDesignGlyphs(0xF014E)

    /**
     * 󰅏 (0xF014F)
     */
    public data object CLIPPY : MaterialDesignGlyphs(0xF014F)

    /**
     * 󰅐 (0xF0150)
     */
    public data object CLOCK_OUTLINE : MaterialDesignGlyphs(0xF0150)

    /**
     * 󰅑 (0xF0151)
     */
    public data object CLOCK_END : MaterialDesignGlyphs(0xF0151)

    /**
     * 󰅒 (0xF0152)
     */
    public data object CLOCK_FAST : MaterialDesignGlyphs(0xF0152)

    /**
     * 󰅓 (0xF0153)
     */
    public data object CLOCK_IN : MaterialDesignGlyphs(0xF0153)

    /**
     * 󰅔 (0xF0154)
     */
    public data object CLOCK_OUT : MaterialDesignGlyphs(0xF0154)

    /**
     * 󰅕 (0xF0155)
     */
    public data object CLOCK_START : MaterialDesignGlyphs(0xF0155)

    /**
     * 󰅖 (0xF0156)
     */
    public data object CLOSE : MaterialDesignGlyphs(0xF0156)

    /**
     * 󰅗 (0xF0157)
     */
    public data object CLOSE_BOX : MaterialDesignGlyphs(0xF0157)

    /**
     * 󰅘 (0xF0158)
     */
    public data object CLOSE_BOX_OUTLINE : MaterialDesignGlyphs(0xF0158)

    /**
     * 󰅙 (0xF0159)
     */
    public data object CLOSE_CIRCLE : MaterialDesignGlyphs(0xF0159)

    /**
     * 󰅚 (0xF015A)
     */
    public data object CLOSE_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF015A)

    /**
     * 󰅛 (0xF015B)
     */
    public data object CLOSE_NETWORK : MaterialDesignGlyphs(0xF015B)

    /**
     * 󰅜 (0xF015C)
     */
    public data object CLOSE_OCTAGON : MaterialDesignGlyphs(0xF015C)

    /**
     * 󰅝 (0xF015D)
     */
    public data object CLOSE_OCTAGON_OUTLINE : MaterialDesignGlyphs(0xF015D)

    /**
     * 󰅞 (0xF015E)
     */
    public data object CLOSED_CAPTION : MaterialDesignGlyphs(0xF015E)

    /**
     * 󰅟 (0xF015F)
     */
    public data object CLOUD : MaterialDesignGlyphs(0xF015F)

    /**
     * 󰅠 (0xF0160)
     */
    public data object CLOUD_CHECK : MaterialDesignGlyphs(0xF0160)

    /**
     * 󰅡 (0xF0161)
     */
    public data object CLOUD_CIRCLE : MaterialDesignGlyphs(0xF0161)

    /**
     * 󰅢 (0xF0162)
     */
    public data object CLOUD_DOWNLOAD : MaterialDesignGlyphs(0xF0162)

    /**
     * 󰅣 (0xF0163)
     */
    public data object CLOUD_OUTLINE : MaterialDesignGlyphs(0xF0163)

    /**
     * 󰅤 (0xF0164)
     */
    public data object CLOUD_OFF_OUTLINE : MaterialDesignGlyphs(0xF0164)

    /**
     * 󰅥 (0xF0165)
     */
    public data object CLOUD_PRINT : MaterialDesignGlyphs(0xF0165)

    /**
     * 󰅦 (0xF0166)
     */
    public data object CLOUD_PRINT_OUTLINE : MaterialDesignGlyphs(0xF0166)

    /**
     * 󰅧 (0xF0167)
     */
    public data object CLOUD_UPLOAD : MaterialDesignGlyphs(0xF0167)

    /**
     * 󰅨 (0xF0168)
     */
    public data object CODE_ARRAY : MaterialDesignGlyphs(0xF0168)

    /**
     * 󰅩 (0xF0169)
     */
    public data object CODE_BRACES : MaterialDesignGlyphs(0xF0169)

    /**
     * 󰅪 (0xF016A)
     */
    public data object CODE_BRACKETS : MaterialDesignGlyphs(0xF016A)

    /**
     * 󰅫 (0xF016B)
     */
    public data object CODE_EQUAL : MaterialDesignGlyphs(0xF016B)

    /**
     * 󰅬 (0xF016C)
     */
    public data object CODE_GREATER_THAN : MaterialDesignGlyphs(0xF016C)

    /**
     * 󰅭 (0xF016D)
     */
    public data object CODE_GREATER_THAN_OR_EQUAL : MaterialDesignGlyphs(0xF016D)

    /**
     * 󰅮 (0xF016E)
     */
    public data object CODE_LESS_THAN : MaterialDesignGlyphs(0xF016E)

    /**
     * 󰅯 (0xF016F)
     */
    public data object CODE_LESS_THAN_OR_EQUAL : MaterialDesignGlyphs(0xF016F)

    /**
     * 󰅰 (0xF0170)
     */
    public data object CODE_NOT_EQUAL : MaterialDesignGlyphs(0xF0170)

    /**
     * 󰅱 (0xF0171)
     */
    public data object CODE_NOT_EQUAL_VARIANT : MaterialDesignGlyphs(0xF0171)

    /**
     * 󰅲 (0xF0172)
     */
    public data object CODE_PARENTHESES : MaterialDesignGlyphs(0xF0172)

    /**
     * 󰅳 (0xF0173)
     */
    public data object CODE_STRING : MaterialDesignGlyphs(0xF0173)

    /**
     * 󰅴 (0xF0174)
     */
    public data object CODE_TAGS : MaterialDesignGlyphs(0xF0174)

    /**
     * 󰅵 (0xF0175)
     */
    public data object CODEPEN : MaterialDesignGlyphs(0xF0175)

    /**
     * 󰅶 (0xF0176)
     */
    public data object COFFEE : MaterialDesignGlyphs(0xF0176)

    /**
     * 󰅷 (0xF0177)
     */
    public data object COFFEE_TO_GO : MaterialDesignGlyphs(0xF0177)

    /**
     * 󰅸 (0xF0178)
     */
    public data object BELL_BADGE_OUTLINE : MaterialDesignGlyphs(0xF0178)

    /**
     * 󰅹 (0xF0179)
     */
    public data object COLOR_HELPER : MaterialDesignGlyphs(0xF0179)

    /**
     * 󰅺 (0xF017A)
     */
    public data object COMMENT : MaterialDesignGlyphs(0xF017A)

    /**
     * 󰅻 (0xF017B)
     */
    public data object COMMENT_ACCOUNT : MaterialDesignGlyphs(0xF017B)

    /**
     * 󰅼 (0xF017C)
     */
    public data object COMMENT_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF017C)

    /**
     * 󰅽 (0xF017D)
     */
    public data object COMMENT_ALERT : MaterialDesignGlyphs(0xF017D)

    /**
     * 󰅾 (0xF017E)
     */
    public data object COMMENT_ALERT_OUTLINE : MaterialDesignGlyphs(0xF017E)

    /**
     * 󰅿 (0xF017F)
     */
    public data object COMMENT_CHECK : MaterialDesignGlyphs(0xF017F)

    /**
     * 󰆀 (0xF0180)
     */
    public data object COMMENT_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0180)

    /**
     * 󰆁 (0xF0181)
     */
    public data object COMMENT_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0181)

    /**
     * 󰆂 (0xF0182)
     */
    public data object COMMENT_OUTLINE : MaterialDesignGlyphs(0xF0182)

    /**
     * 󰆃 (0xF0183)
     */
    public data object COMMENT_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0183)

    /**
     * 󰆄 (0xF0184)
     */
    public data object COMMENT_PROCESSING : MaterialDesignGlyphs(0xF0184)

    /**
     * 󰆅 (0xF0185)
     */
    public data object COMMENT_PROCESSING_OUTLINE : MaterialDesignGlyphs(0xF0185)

    /**
     * 󰆆 (0xF0186)
     */
    public data object COMMENT_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF0186)

    /**
     * 󰆇 (0xF0187)
     */
    public data object COMMENT_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0187)

    /**
     * 󰆈 (0xF0188)
     */
    public data object COMMENT_TEXT : MaterialDesignGlyphs(0xF0188)

    /**
     * 󰆉 (0xF0189)
     */
    public data object COMMENT_TEXT_OUTLINE : MaterialDesignGlyphs(0xF0189)

    /**
     * 󰆊 (0xF018A)
     */
    public data object COMPARE : MaterialDesignGlyphs(0xF018A)

    /**
     * 󰆋 (0xF018B)
     */
    public data object COMPASS : MaterialDesignGlyphs(0xF018B)

    /**
     * 󰆌 (0xF018C)
     */
    public data object COMPASS_OUTLINE : MaterialDesignGlyphs(0xF018C)

    /**
     * 󰆍 (0xF018D)
     */
    public data object CONSOLE : MaterialDesignGlyphs(0xF018D)

    /**
     * 󰆎 (0xF018E)
     */
    public data object CARD_ACCOUNT_MAIL : MaterialDesignGlyphs(0xF018E)

    /**
     * 󰆏 (0xF018F)
     */
    public data object CONTENT_COPY : MaterialDesignGlyphs(0xF018F)

    /**
     * 󰆐 (0xF0190)
     */
    public data object CONTENT_CUT : MaterialDesignGlyphs(0xF0190)

    /**
     * 󰆑 (0xF0191)
     */
    public data object CONTENT_DUPLICATE : MaterialDesignGlyphs(0xF0191)

    /**
     * 󰆒 (0xF0192)
     */
    public data object CONTENT_PASTE : MaterialDesignGlyphs(0xF0192)

    /**
     * 󰆓 (0xF0193)
     */
    public data object CONTENT_SAVE : MaterialDesignGlyphs(0xF0193)

    /**
     * 󰆔 (0xF0194)
     */
    public data object CONTENT_SAVE_ALL : MaterialDesignGlyphs(0xF0194)

    /**
     * 󰆕 (0xF0195)
     */
    public data object CONTRAST : MaterialDesignGlyphs(0xF0195)

    /**
     * 󰆖 (0xF0196)
     */
    public data object CONTRAST_BOX : MaterialDesignGlyphs(0xF0196)

    /**
     * 󰆗 (0xF0197)
     */
    public data object CONTRAST_CIRCLE : MaterialDesignGlyphs(0xF0197)

    /**
     * 󰆘 (0xF0198)
     */
    public data object COOKIE : MaterialDesignGlyphs(0xF0198)

    /**
     * 󰆙 (0xF0199)
     */
    public data object COUNTER : MaterialDesignGlyphs(0xF0199)

    /**
     * 󰆚 (0xF019A)
     */
    public data object COW : MaterialDesignGlyphs(0xF019A)

    /**
     * 󰆛 (0xF019B)
     */
    public data object CREDIT_CARD_OUTLINE : MaterialDesignGlyphs(0xF019B)

    /**
     * 󰆜 (0xF019C)
     */
    public data object CREDIT_CARD_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF019C)

    /**
     * 󰆝 (0xF019D)
     */
    public data object CREDIT_CARD_SCAN_OUTLINE : MaterialDesignGlyphs(0xF019D)

    /**
     * 󰆞 (0xF019E)
     */
    public data object CROP : MaterialDesignGlyphs(0xF019E)

    /**
     * 󰆟 (0xF019F)
     */
    public data object CROP_FREE : MaterialDesignGlyphs(0xF019F)

    /**
     * 󰆠 (0xF01A0)
     */
    public data object CROP_LANDSCAPE : MaterialDesignGlyphs(0xF01A0)

    /**
     * 󰆡 (0xF01A1)
     */
    public data object CROP_PORTRAIT : MaterialDesignGlyphs(0xF01A1)

    /**
     * 󰆢 (0xF01A2)
     */
    public data object CROP_SQUARE : MaterialDesignGlyphs(0xF01A2)

    /**
     * 󰆣 (0xF01A3)
     */
    public data object CROSSHAIRS : MaterialDesignGlyphs(0xF01A3)

    /**
     * 󰆤 (0xF01A4)
     */
    public data object CROSSHAIRS_GPS : MaterialDesignGlyphs(0xF01A4)

    /**
     * 󰆥 (0xF01A5)
     */
    public data object CROWN : MaterialDesignGlyphs(0xF01A5)

    /**
     * 󰆦 (0xF01A6)
     */
    public data object CUBE : MaterialDesignGlyphs(0xF01A6)

    /**
     * 󰆧 (0xF01A7)
     */
    public data object CUBE_OUTLINE : MaterialDesignGlyphs(0xF01A7)

    /**
     * 󰆨 (0xF01A8)
     */
    public data object CUBE_SEND : MaterialDesignGlyphs(0xF01A8)

    /**
     * 󰆩 (0xF01A9)
     */
    public data object CUBE_UNFOLDED : MaterialDesignGlyphs(0xF01A9)

    /**
     * 󰆪 (0xF01AA)
     */
    public data object CUP : MaterialDesignGlyphs(0xF01AA)

    /**
     * 󰆫 (0xF01AB)
     */
    public data object CUP_WATER : MaterialDesignGlyphs(0xF01AB)

    /**
     * 󰆬 (0xF01AC)
     */
    public data object CURRENCY_BTC : MaterialDesignGlyphs(0xF01AC)

    /**
     * 󰆭 (0xF01AD)
     */
    public data object CURRENCY_EUR : MaterialDesignGlyphs(0xF01AD)

    /**
     * 󰆮 (0xF01AE)
     */
    public data object CURRENCY_GBP : MaterialDesignGlyphs(0xF01AE)

    /**
     * 󰆯 (0xF01AF)
     */
    public data object CURRENCY_INR : MaterialDesignGlyphs(0xF01AF)

    /**
     * 󰆰 (0xF01B0)
     */
    public data object CURRENCY_NGN : MaterialDesignGlyphs(0xF01B0)

    /**
     * 󰆱 (0xF01B1)
     */
    public data object CURRENCY_RUB : MaterialDesignGlyphs(0xF01B1)

    /**
     * 󰆲 (0xF01B2)
     */
    public data object CURRENCY_TRY : MaterialDesignGlyphs(0xF01B2)

    /**
     * 󰆳 (0xF01B3)
     */
    public data object DELETE_VARIANT : MaterialDesignGlyphs(0xF01B3)

    /**
     * 󰆴 (0xF01B4)
     */
    public data object DELETE : MaterialDesignGlyphs(0xF01B4)

    /**
     * 󰆵 (0xF01B5)
     */
    public data object DECIMAL_INCREASE : MaterialDesignGlyphs(0xF01B5)

    /**
     * 󰆶 (0xF01B6)
     */
    public data object DECIMAL_DECREASE : MaterialDesignGlyphs(0xF01B6)

    /**
     * 󰆷 (0xF01B7)
     */
    public data object DEBUG_STEP_OVER : MaterialDesignGlyphs(0xF01B7)

    /**
     * 󰆸 (0xF01B8)
     */
    public data object DEBUG_STEP_OUT : MaterialDesignGlyphs(0xF01B8)

    /**
     * 󰆹 (0xF01B9)
     */
    public data object DEBUG_STEP_INTO : MaterialDesignGlyphs(0xF01B9)

    /**
     * 󰆺 (0xF01BA)
     */
    public data object DATABASE_PLUS : MaterialDesignGlyphs(0xF01BA)

    /**
     * 󰆻 (0xF01BB)
     */
    public data object DATABASE_MINUS : MaterialDesignGlyphs(0xF01BB)

    /**
     * 󰆼 (0xF01BC)
     */
    public data object DATABASE : MaterialDesignGlyphs(0xF01BC)

    /**
     * 󰆽 (0xF01BD)
     */
    public data object CURSOR_POINTER : MaterialDesignGlyphs(0xF01BD)

    /**
     * 󰆾 (0xF01BE)
     */
    public data object CURSOR_MOVE : MaterialDesignGlyphs(0xF01BE)

    /**
     * 󰆿 (0xF01BF)
     */
    public data object CURSOR_DEFAULT_OUTLINE : MaterialDesignGlyphs(0xF01BF)

    /**
     * 󰇀 (0xF01C0)
     */
    public data object CURSOR_DEFAULT : MaterialDesignGlyphs(0xF01C0)

    /**
     * 󰇁 (0xF01C1)
     */
    public data object CURRENCY_USD : MaterialDesignGlyphs(0xF01C1)

    /**
     * 󰇂 (0xF01C2)
     */
    public data object DELTA : MaterialDesignGlyphs(0xF01C2)

    /**
     * 󰇃 (0xF01C3)
     */
    public data object DESKPHONE : MaterialDesignGlyphs(0xF01C3)

    /**
     * 󰇄 (0xF01C4)
     */
    public data object DESKTOP_MAC : MaterialDesignGlyphs(0xF01C4)

    /**
     * 󰇅 (0xF01C5)
     */
    public data object DESKTOP_TOWER : MaterialDesignGlyphs(0xF01C5)

    /**
     * 󰇆 (0xF01C6)
     */
    public data object DETAILS : MaterialDesignGlyphs(0xF01C6)

    /**
     * 󰇇 (0xF01C7)
     */
    public data object DEVIANTART : MaterialDesignGlyphs(0xF01C7)

    /**
     * 󰇈 (0xF01C8)
     */
    public data object DIAMOND_STONE : MaterialDesignGlyphs(0xF01C8)

    /**
     * 󰇉 (0xF01C9)
     */
    public data object AB_TESTING : MaterialDesignGlyphs(0xF01C9)

    /**
     * 󰇊 (0xF01CA)
     */
    public data object DICE_1 : MaterialDesignGlyphs(0xF01CA)

    /**
     * 󰇋 (0xF01CB)
     */
    public data object DICE_2 : MaterialDesignGlyphs(0xF01CB)

    /**
     * 󰇌 (0xF01CC)
     */
    public data object DICE_3 : MaterialDesignGlyphs(0xF01CC)

    /**
     * 󰇍 (0xF01CD)
     */
    public data object DICE_4 : MaterialDesignGlyphs(0xF01CD)

    /**
     * 󰇎 (0xF01CE)
     */
    public data object DICE_5 : MaterialDesignGlyphs(0xF01CE)

    /**
     * 󰇏 (0xF01CF)
     */
    public data object DICE_6 : MaterialDesignGlyphs(0xF01CF)

    /**
     * 󰇐 (0xF01D0)
     */
    public data object DIRECTIONS : MaterialDesignGlyphs(0xF01D0)

    /**
     * 󰇑 (0xF01D1)
     */
    public data object DISC_ALERT : MaterialDesignGlyphs(0xF01D1)

    /**
     * 󰇒 (0xF01D2)
     */
    public data object DISQUS : MaterialDesignGlyphs(0xF01D2)

    /**
     * 󰇓 (0xF01D3)
     */
    public data object VIDEO_PLUS_OUTLINE : MaterialDesignGlyphs(0xF01D3)

    /**
     * 󰇔 (0xF01D4)
     */
    public data object DIVISION : MaterialDesignGlyphs(0xF01D4)

    /**
     * 󰇕 (0xF01D5)
     */
    public data object DIVISION_BOX : MaterialDesignGlyphs(0xF01D5)

    /**
     * 󰇖 (0xF01D6)
     */
    public data object DNS : MaterialDesignGlyphs(0xF01D6)

    /**
     * 󰇗 (0xF01D7)
     */
    public data object DOMAIN : MaterialDesignGlyphs(0xF01D7)

    /**
     * 󰇘 (0xF01D8)
     */
    public data object DOTS_HORIZONTAL : MaterialDesignGlyphs(0xF01D8)

    /**
     * 󰇙 (0xF01D9)
     */
    public data object DOTS_VERTICAL : MaterialDesignGlyphs(0xF01D9)

    /**
     * 󰇚 (0xF01DA)
     */
    public data object DOWNLOAD : MaterialDesignGlyphs(0xF01DA)

    /**
     * 󰇛 (0xF01DB)
     */
    public data object DRAG : MaterialDesignGlyphs(0xF01DB)

    /**
     * 󰇜 (0xF01DC)
     */
    public data object DRAG_HORIZONTAL : MaterialDesignGlyphs(0xF01DC)

    /**
     * 󰇝 (0xF01DD)
     */
    public data object DRAG_VERTICAL : MaterialDesignGlyphs(0xF01DD)

    /**
     * 󰇞 (0xF01DE)
     */
    public data object DRAWING : MaterialDesignGlyphs(0xF01DE)

    /**
     * 󰇟 (0xF01DF)
     */
    public data object DRAWING_BOX : MaterialDesignGlyphs(0xF01DF)

    /**
     * 󰇠 (0xF01E0)
     */
    public data object SHIELD_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF01E0)

    /**
     * 󰇡 (0xF01E1)
     */
    public data object CALENDAR_REFRESH : MaterialDesignGlyphs(0xF01E1)

    /**
     * 󰇢 (0xF01E2)
     */
    public data object DRONE : MaterialDesignGlyphs(0xF01E2)

    /**
     * 󰇣 (0xF01E3)
     */
    public data object DROPBOX : MaterialDesignGlyphs(0xF01E3)

    /**
     * 󰇤 (0xF01E4)
     */
    public data object DRUPAL : MaterialDesignGlyphs(0xF01E4)

    /**
     * 󰇥 (0xF01E5)
     */
    public data object DUCK : MaterialDesignGlyphs(0xF01E5)

    /**
     * 󰇦 (0xF01E6)
     */
    public data object DUMBBELL : MaterialDesignGlyphs(0xF01E6)

    /**
     * 󰇧 (0xF01E7)
     */
    public data object EARTH : MaterialDesignGlyphs(0xF01E7)

    /**
     * 󰇨 (0xF01E8)
     */
    public data object EARTH_OFF : MaterialDesignGlyphs(0xF01E8)

    /**
     * 󰇩 (0xF01E9)
     */
    public data object MICROSOFT_EDGE : MaterialDesignGlyphs(0xF01E9)

    /**
     * 󰇪 (0xF01EA)
     */
    public data object EJECT : MaterialDesignGlyphs(0xF01EA)

    /**
     * 󰇫 (0xF01EB)
     */
    public data object ELEVATION_DECLINE : MaterialDesignGlyphs(0xF01EB)

    /**
     * 󰇬 (0xF01EC)
     */
    public data object ELEVATION_RISE : MaterialDesignGlyphs(0xF01EC)

    /**
     * 󰇭 (0xF01ED)
     */
    public data object ELEVATOR : MaterialDesignGlyphs(0xF01ED)

    /**
     * 󰇮 (0xF01EE)
     */
    public data object EMAIL : MaterialDesignGlyphs(0xF01EE)

    /**
     * 󰇯 (0xF01EF)
     */
    public data object EMAIL_OPEN : MaterialDesignGlyphs(0xF01EF)

    /**
     * 󰇰 (0xF01F0)
     */
    public data object EMAIL_OUTLINE : MaterialDesignGlyphs(0xF01F0)

    /**
     * 󰇱 (0xF01F1)
     */
    public data object EMAIL_LOCK : MaterialDesignGlyphs(0xF01F1)

    /**
     * 󰇲 (0xF01F2)
     */
    public data object EMOTICON_OUTLINE : MaterialDesignGlyphs(0xF01F2)

    /**
     * 󰇳 (0xF01F3)
     */
    public data object EMOTICON_COOL_OUTLINE : MaterialDesignGlyphs(0xF01F3)

    /**
     * 󰇴 (0xF01F4)
     */
    public data object EMOTICON_DEVIL_OUTLINE : MaterialDesignGlyphs(0xF01F4)

    /**
     * 󰇵 (0xF01F5)
     */
    public data object EMOTICON_HAPPY_OUTLINE : MaterialDesignGlyphs(0xF01F5)

    /**
     * 󰇶 (0xF01F6)
     */
    public data object EMOTICON_NEUTRAL_OUTLINE : MaterialDesignGlyphs(0xF01F6)

    /**
     * 󰇷 (0xF01F7)
     */
    public data object EMOTICON_POOP : MaterialDesignGlyphs(0xF01F7)

    /**
     * 󰇸 (0xF01F8)
     */
    public data object EMOTICON_SAD_OUTLINE : MaterialDesignGlyphs(0xF01F8)

    /**
     * 󰇹 (0xF01F9)
     */
    public data object EMOTICON_TONGUE : MaterialDesignGlyphs(0xF01F9)

    /**
     * 󰇺 (0xF01FA)
     */
    public data object ENGINE : MaterialDesignGlyphs(0xF01FA)

    /**
     * 󰇻 (0xF01FB)
     */
    public data object ENGINE_OUTLINE : MaterialDesignGlyphs(0xF01FB)

    /**
     * 󰇼 (0xF01FC)
     */
    public data object EQUAL : MaterialDesignGlyphs(0xF01FC)

    /**
     * 󰇽 (0xF01FD)
     */
    public data object EQUAL_BOX : MaterialDesignGlyphs(0xF01FD)

    /**
     * 󰇾 (0xF01FE)
     */
    public data object ERASER : MaterialDesignGlyphs(0xF01FE)

    /**
     * 󰇿 (0xF01FF)
     */
    public data object ESCALATOR : MaterialDesignGlyphs(0xF01FF)

    /**
     * 󰈀 (0xF0200)
     */
    public data object ETHERNET : MaterialDesignGlyphs(0xF0200)

    /**
     * 󰈁 (0xF0201)
     */
    public data object ETHERNET_CABLE : MaterialDesignGlyphs(0xF0201)

    /**
     * 󰈂 (0xF0202)
     */
    public data object ETHERNET_CABLE_OFF : MaterialDesignGlyphs(0xF0202)

    /**
     * 󰈃 (0xF0203)
     */
    public data object CALENDAR_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF0203)

    /**
     * 󰈄 (0xF0204)
     */
    public data object EVERNOTE : MaterialDesignGlyphs(0xF0204)

    /**
     * 󰈅 (0xF0205)
     */
    public data object EXCLAMATION : MaterialDesignGlyphs(0xF0205)

    /**
     * 󰈆 (0xF0206)
     */
    public data object EXIT_TO_APP : MaterialDesignGlyphs(0xF0206)

    /**
     * 󰈇 (0xF0207)
     */
    public data object EXPORT : MaterialDesignGlyphs(0xF0207)

    /**
     * 󰈈 (0xF0208)
     */
    public data object EYE : MaterialDesignGlyphs(0xF0208)

    /**
     * 󰈉 (0xF0209)
     */
    public data object EYE_OFF : MaterialDesignGlyphs(0xF0209)

    /**
     * 󰈊 (0xF020A)
     */
    public data object EYEDROPPER : MaterialDesignGlyphs(0xF020A)

    /**
     * 󰈋 (0xF020B)
     */
    public data object EYEDROPPER_VARIANT : MaterialDesignGlyphs(0xF020B)

    /**
     * 󰈌 (0xF020C)
     */
    public data object FACEBOOK : MaterialDesignGlyphs(0xF020C)

    /**
     * 󰈍 (0xF020D)
     */
    public data object ORDER_ALPHABETICAL_ASCENDING : MaterialDesignGlyphs(0xF020D)

    /**
     * 󰈎 (0xF020E)
     */
    public data object FACEBOOK_MESSENGER : MaterialDesignGlyphs(0xF020E)

    /**
     * 󰈏 (0xF020F)
     */
    public data object FACTORY : MaterialDesignGlyphs(0xF020F)

    /**
     * 󰈐 (0xF0210)
     */
    public data object FAN : MaterialDesignGlyphs(0xF0210)

    /**
     * 󰈑 (0xF0211)
     */
    public data object FAST_FORWARD : MaterialDesignGlyphs(0xF0211)

    /**
     * 󰈒 (0xF0212)
     */
    public data object FAX : MaterialDesignGlyphs(0xF0212)

    /**
     * 󰈓 (0xF0213)
     */
    public data object FERRY : MaterialDesignGlyphs(0xF0213)

    /**
     * 󰈔 (0xF0214)
     */
    public data object FILE : MaterialDesignGlyphs(0xF0214)

    /**
     * 󰈕 (0xF0215)
     */
    public data object FILE_CHART : MaterialDesignGlyphs(0xF0215)

    /**
     * 󰈖 (0xF0216)
     */
    public data object FILE_CHECK : MaterialDesignGlyphs(0xF0216)

    /**
     * 󰈗 (0xF0217)
     */
    public data object FILE_CLOUD : MaterialDesignGlyphs(0xF0217)

    /**
     * 󰈘 (0xF0218)
     */
    public data object FILE_DELIMITED : MaterialDesignGlyphs(0xF0218)

    /**
     * 󰈙 (0xF0219)
     */
    public data object FILE_DOCUMENT : MaterialDesignGlyphs(0xF0219)

    /**
     * 󰈚 (0xF021A)
     */
    public data object TEXT_BOX : MaterialDesignGlyphs(0xF021A)

    /**
     * 󰈛 (0xF021B)
     */
    public data object FILE_EXCEL : MaterialDesignGlyphs(0xF021B)

    /**
     * 󰈜 (0xF021C)
     */
    public data object FILE_EXCEL_BOX : MaterialDesignGlyphs(0xF021C)

    /**
     * 󰈝 (0xF021D)
     */
    public data object FILE_EXPORT : MaterialDesignGlyphs(0xF021D)

    /**
     * 󰈞 (0xF021E)
     */
    public data object FILE_FIND : MaterialDesignGlyphs(0xF021E)

    /**
     * 󰈟 (0xF021F)
     */
    public data object FILE_IMAGE : MaterialDesignGlyphs(0xF021F)

    /**
     * 󰈠 (0xF0220)
     */
    public data object FILE_IMPORT : MaterialDesignGlyphs(0xF0220)

    /**
     * 󰈡 (0xF0221)
     */
    public data object FILE_LOCK : MaterialDesignGlyphs(0xF0221)

    /**
     * 󰈢 (0xF0222)
     */
    public data object FILE_MULTIPLE : MaterialDesignGlyphs(0xF0222)

    /**
     * 󰈣 (0xF0223)
     */
    public data object FILE_MUSIC : MaterialDesignGlyphs(0xF0223)

    /**
     * 󰈤 (0xF0224)
     */
    public data object FILE_OUTLINE : MaterialDesignGlyphs(0xF0224)

    /**
     * 󰈥 (0xF0225)
     */
    public data object FILE_JPG_BOX : MaterialDesignGlyphs(0xF0225)

    /**
     * 󰈦 (0xF0226)
     */
    public data object FILE_PDF_BOX : MaterialDesignGlyphs(0xF0226)

    /**
     * 󰈧 (0xF0227)
     */
    public data object FILE_POWERPOINT : MaterialDesignGlyphs(0xF0227)

    /**
     * 󰈨 (0xF0228)
     */
    public data object FILE_POWERPOINT_BOX : MaterialDesignGlyphs(0xF0228)

    /**
     * 󰈩 (0xF0229)
     */
    public data object FILE_PRESENTATION_BOX : MaterialDesignGlyphs(0xF0229)

    /**
     * 󰈪 (0xF022A)
     */
    public data object FILE_SEND : MaterialDesignGlyphs(0xF022A)

    /**
     * 󰈫 (0xF022B)
     */
    public data object FILE_VIDEO : MaterialDesignGlyphs(0xF022B)

    /**
     * 󰈬 (0xF022C)
     */
    public data object FILE_WORD : MaterialDesignGlyphs(0xF022C)

    /**
     * 󰈭 (0xF022D)
     */
    public data object FILE_WORD_BOX : MaterialDesignGlyphs(0xF022D)

    /**
     * 󰈮 (0xF022E)
     */
    public data object FILE_CODE : MaterialDesignGlyphs(0xF022E)

    /**
     * 󰈯 (0xF022F)
     */
    public data object FILM : MaterialDesignGlyphs(0xF022F)

    /**
     * 󰈰 (0xF0230)
     */
    public data object FILMSTRIP : MaterialDesignGlyphs(0xF0230)

    /**
     * 󰈱 (0xF0231)
     */
    public data object FILMSTRIP_OFF : MaterialDesignGlyphs(0xF0231)

    /**
     * 󰈲 (0xF0232)
     */
    public data object FILTER : MaterialDesignGlyphs(0xF0232)

    /**
     * 󰈳 (0xF0233)
     */
    public data object FILTER_OUTLINE : MaterialDesignGlyphs(0xF0233)

    /**
     * 󰈴 (0xF0234)
     */
    public data object FILTER_REMOVE : MaterialDesignGlyphs(0xF0234)

    /**
     * 󰈵 (0xF0235)
     */
    public data object FILTER_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0235)

    /**
     * 󰈶 (0xF0236)
     */
    public data object FILTER_VARIANT : MaterialDesignGlyphs(0xF0236)

    /**
     * 󰈷 (0xF0237)
     */
    public data object FINGERPRINT : MaterialDesignGlyphs(0xF0237)

    /**
     * 󰈸 (0xF0238)
     */
    public data object FIRE : MaterialDesignGlyphs(0xF0238)

    /**
     * 󰈹 (0xF0239)
     */
    public data object FIREFOX : MaterialDesignGlyphs(0xF0239)

    /**
     * 󰈺 (0xF023A)
     */
    public data object FISH : MaterialDesignGlyphs(0xF023A)

    /**
     * 󰈻 (0xF023B)
     */
    public data object FLAG : MaterialDesignGlyphs(0xF023B)

    /**
     * 󰈼 (0xF023C)
     */
    public data object FLAG_CHECKERED : MaterialDesignGlyphs(0xF023C)

    /**
     * 󰈽 (0xF023D)
     */
    public data object FLAG_OUTLINE : MaterialDesignGlyphs(0xF023D)

    /**
     * 󰈾 (0xF023E)
     */
    public data object FLAG_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF023E)

    /**
     * 󰈿 (0xF023F)
     */
    public data object FLAG_TRIANGLE : MaterialDesignGlyphs(0xF023F)

    /**
     * 󰉀 (0xF0240)
     */
    public data object FLAG_VARIANT : MaterialDesignGlyphs(0xF0240)

    /**
     * 󰉁 (0xF0241)
     */
    public data object FLASH : MaterialDesignGlyphs(0xF0241)

    /**
     * 󰉂 (0xF0242)
     */
    public data object FLASH_AUTO : MaterialDesignGlyphs(0xF0242)

    /**
     * 󰉃 (0xF0243)
     */
    public data object FLASH_OFF : MaterialDesignGlyphs(0xF0243)

    /**
     * 󰉄 (0xF0244)
     */
    public data object FLASHLIGHT : MaterialDesignGlyphs(0xF0244)

    /**
     * 󰉅 (0xF0245)
     */
    public data object FLASHLIGHT_OFF : MaterialDesignGlyphs(0xF0245)

    /**
     * 󰉆 (0xF0246)
     */
    public data object STAR_HALF : MaterialDesignGlyphs(0xF0246)

    /**
     * 󰉇 (0xF0247)
     */
    public data object FLIP_TO_BACK : MaterialDesignGlyphs(0xF0247)

    /**
     * 󰉈 (0xF0248)
     */
    public data object FLIP_TO_FRONT : MaterialDesignGlyphs(0xF0248)

    /**
     * 󰉉 (0xF0249)
     */
    public data object FLOPPY : MaterialDesignGlyphs(0xF0249)

    /**
     * 󰉊 (0xF024A)
     */
    public data object FLOWER : MaterialDesignGlyphs(0xF024A)

    /**
     * 󰉋 (0xF024B)
     */
    public data object FOLDER : MaterialDesignGlyphs(0xF024B)

    /**
     * 󰉌 (0xF024C)
     */
    public data object FOLDER_ACCOUNT : MaterialDesignGlyphs(0xF024C)

    /**
     * 󰉍 (0xF024D)
     */
    public data object FOLDER_DOWNLOAD : MaterialDesignGlyphs(0xF024D)

    /**
     * 󰉎 (0xF024E)
     */
    public data object FOLDER_GOOGLE_DRIVE : MaterialDesignGlyphs(0xF024E)

    /**
     * 󰉏 (0xF024F)
     */
    public data object FOLDER_IMAGE : MaterialDesignGlyphs(0xF024F)

    /**
     * 󰉐 (0xF0250)
     */
    public data object FOLDER_LOCK : MaterialDesignGlyphs(0xF0250)

    /**
     * 󰉑 (0xF0251)
     */
    public data object FOLDER_LOCK_OPEN : MaterialDesignGlyphs(0xF0251)

    /**
     * 󰉒 (0xF0252)
     */
    public data object FOLDER_MOVE : MaterialDesignGlyphs(0xF0252)

    /**
     * 󰉓 (0xF0253)
     */
    public data object FOLDER_MULTIPLE : MaterialDesignGlyphs(0xF0253)

    /**
     * 󰉔 (0xF0254)
     */
    public data object FOLDER_MULTIPLE_IMAGE : MaterialDesignGlyphs(0xF0254)

    /**
     * 󰉕 (0xF0255)
     */
    public data object FOLDER_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0255)

    /**
     * 󰉖 (0xF0256)
     */
    public data object FOLDER_OUTLINE : MaterialDesignGlyphs(0xF0256)

    /**
     * 󰉗 (0xF0257)
     */
    public data object FOLDER_PLUS : MaterialDesignGlyphs(0xF0257)

    /**
     * 󰉘 (0xF0258)
     */
    public data object FOLDER_REMOVE : MaterialDesignGlyphs(0xF0258)

    /**
     * 󰉙 (0xF0259)
     */
    public data object FOLDER_UPLOAD : MaterialDesignGlyphs(0xF0259)

    /**
     * 󰉚 (0xF025A)
     */
    public data object FOOD : MaterialDesignGlyphs(0xF025A)

    /**
     * 󰉛 (0xF025B)
     */
    public data object FOOD_APPLE : MaterialDesignGlyphs(0xF025B)

    /**
     * 󰉜 (0xF025C)
     */
    public data object FOOD_VARIANT : MaterialDesignGlyphs(0xF025C)

    /**
     * 󰉝 (0xF025D)
     */
    public data object FOOTBALL : MaterialDesignGlyphs(0xF025D)

    /**
     * 󰉞 (0xF025E)
     */
    public data object FOOTBALL_AUSTRALIAN : MaterialDesignGlyphs(0xF025E)

    /**
     * 󰉟 (0xF025F)
     */
    public data object FOOTBALL_HELMET : MaterialDesignGlyphs(0xF025F)

    /**
     * 󰉠 (0xF0260)
     */
    public data object FORMAT_ALIGN_CENTER : MaterialDesignGlyphs(0xF0260)

    /**
     * 󰉡 (0xF0261)
     */
    public data object FORMAT_ALIGN_JUSTIFY : MaterialDesignGlyphs(0xF0261)

    /**
     * 󰉢 (0xF0262)
     */
    public data object FORMAT_ALIGN_LEFT : MaterialDesignGlyphs(0xF0262)

    /**
     * 󰉣 (0xF0263)
     */
    public data object FORMAT_ALIGN_RIGHT : MaterialDesignGlyphs(0xF0263)

    /**
     * 󰉤 (0xF0264)
     */
    public data object FORMAT_BOLD : MaterialDesignGlyphs(0xF0264)

    /**
     * 󰉥 (0xF0265)
     */
    public data object FORMAT_CLEAR : MaterialDesignGlyphs(0xF0265)

    /**
     * 󰉦 (0xF0266)
     */
    public data object FORMAT_COLOR_FILL : MaterialDesignGlyphs(0xF0266)

    /**
     * 󰉧 (0xF0267)
     */
    public data object FORMAT_FLOAT_CENTER : MaterialDesignGlyphs(0xF0267)

    /**
     * 󰉨 (0xF0268)
     */
    public data object FORMAT_FLOAT_LEFT : MaterialDesignGlyphs(0xF0268)

    /**
     * 󰉩 (0xF0269)
     */
    public data object FORMAT_FLOAT_NONE : MaterialDesignGlyphs(0xF0269)

    /**
     * 󰉪 (0xF026A)
     */
    public data object FORMAT_FLOAT_RIGHT : MaterialDesignGlyphs(0xF026A)

    /**
     * 󰉫 (0xF026B)
     */
    public data object FORMAT_HEADER_1 : MaterialDesignGlyphs(0xF026B)

    /**
     * 󰉬 (0xF026C)
     */
    public data object FORMAT_HEADER_2 : MaterialDesignGlyphs(0xF026C)

    /**
     * 󰉭 (0xF026D)
     */
    public data object FORMAT_HEADER_3 : MaterialDesignGlyphs(0xF026D)

    /**
     * 󰉮 (0xF026E)
     */
    public data object FORMAT_HEADER_4 : MaterialDesignGlyphs(0xF026E)

    /**
     * 󰉯 (0xF026F)
     */
    public data object FORMAT_HEADER_5 : MaterialDesignGlyphs(0xF026F)

    /**
     * 󰉰 (0xF0270)
     */
    public data object FORMAT_HEADER_6 : MaterialDesignGlyphs(0xF0270)

    /**
     * 󰉱 (0xF0271)
     */
    public data object FORMAT_HEADER_DECREASE : MaterialDesignGlyphs(0xF0271)

    /**
     * 󰉲 (0xF0272)
     */
    public data object FORMAT_HEADER_EQUAL : MaterialDesignGlyphs(0xF0272)

    /**
     * 󰉳 (0xF0273)
     */
    public data object FORMAT_HEADER_INCREASE : MaterialDesignGlyphs(0xF0273)

    /**
     * 󰉴 (0xF0274)
     */
    public data object FORMAT_HEADER_POUND : MaterialDesignGlyphs(0xF0274)

    /**
     * 󰉵 (0xF0275)
     */
    public data object FORMAT_INDENT_DECREASE : MaterialDesignGlyphs(0xF0275)

    /**
     * 󰉶 (0xF0276)
     */
    public data object FORMAT_INDENT_INCREASE : MaterialDesignGlyphs(0xF0276)

    /**
     * 󰉷 (0xF0277)
     */
    public data object FORMAT_ITALIC : MaterialDesignGlyphs(0xF0277)

    /**
     * 󰉸 (0xF0278)
     */
    public data object FORMAT_LINE_SPACING : MaterialDesignGlyphs(0xF0278)

    /**
     * 󰉹 (0xF0279)
     */
    public data object FORMAT_LIST_BULLETED : MaterialDesignGlyphs(0xF0279)

    /**
     * 󰉺 (0xF027A)
     */
    public data object FORMAT_LIST_BULLETED_TYPE : MaterialDesignGlyphs(0xF027A)

    /**
     * 󰉻 (0xF027B)
     */
    public data object FORMAT_LIST_NUMBERED : MaterialDesignGlyphs(0xF027B)

    /**
     * 󰉼 (0xF027C)
     */
    public data object FORMAT_PAINT : MaterialDesignGlyphs(0xF027C)

    /**
     * 󰉽 (0xF027D)
     */
    public data object FORMAT_PARAGRAPH : MaterialDesignGlyphs(0xF027D)

    /**
     * 󰉾 (0xF027E)
     */
    public data object FORMAT_QUOTE_CLOSE : MaterialDesignGlyphs(0xF027E)

    /**
     * 󰉿 (0xF027F)
     */
    public data object FORMAT_SIZE : MaterialDesignGlyphs(0xF027F)

    /**
     * 󰊀 (0xF0280)
     */
    public data object FORMAT_STRIKETHROUGH : MaterialDesignGlyphs(0xF0280)

    /**
     * 󰊁 (0xF0281)
     */
    public data object FORMAT_STRIKETHROUGH_VARIANT : MaterialDesignGlyphs(0xF0281)

    /**
     * 󰊂 (0xF0282)
     */
    public data object FORMAT_SUBSCRIPT : MaterialDesignGlyphs(0xF0282)

    /**
     * 󰊃 (0xF0283)
     */
    public data object FORMAT_SUPERSCRIPT : MaterialDesignGlyphs(0xF0283)

    /**
     * 󰊄 (0xF0284)
     */
    public data object FORMAT_TEXT : MaterialDesignGlyphs(0xF0284)

    /**
     * 󰊅 (0xF0285)
     */
    public data object FORMAT_TEXTDIRECTION_L_TO_R : MaterialDesignGlyphs(0xF0285)

    /**
     * 󰊆 (0xF0286)
     */
    public data object FORMAT_TEXTDIRECTION_R_TO_L : MaterialDesignGlyphs(0xF0286)

    /**
     * 󰊇 (0xF0287)
     */
    public data object FORMAT_UNDERLINE : MaterialDesignGlyphs(0xF0287)

    /**
     * 󰊈 (0xF0288)
     */
    public data object FORMAT_WRAP_INLINE : MaterialDesignGlyphs(0xF0288)

    /**
     * 󰊉 (0xF0289)
     */
    public data object FORMAT_WRAP_SQUARE : MaterialDesignGlyphs(0xF0289)

    /**
     * 󰊊 (0xF028A)
     */
    public data object FORMAT_WRAP_TIGHT : MaterialDesignGlyphs(0xF028A)

    /**
     * 󰊋 (0xF028B)
     */
    public data object FORMAT_WRAP_TOP_BOTTOM : MaterialDesignGlyphs(0xF028B)

    /**
     * 󰊌 (0xF028C)
     */
    public data object FORUM : MaterialDesignGlyphs(0xF028C)

    /**
     * 󰊍 (0xF028D)
     */
    public data object FORWARD : MaterialDesignGlyphs(0xF028D)

    /**
     * 󰊎 (0xF028E)
     */
    public data object BOWL : MaterialDesignGlyphs(0xF028E)

    /**
     * 󰊏 (0xF028F)
     */
    public data object FRIDGE_OUTLINE : MaterialDesignGlyphs(0xF028F)

    /**
     * 󰊐 (0xF0290)
     */
    public data object FRIDGE : MaterialDesignGlyphs(0xF0290)

    /**
     * 󰊑 (0xF0291)
     */
    public data object FRIDGE_TOP : MaterialDesignGlyphs(0xF0291)

    /**
     * 󰊒 (0xF0292)
     */
    public data object FRIDGE_BOTTOM : MaterialDesignGlyphs(0xF0292)

    /**
     * 󰊓 (0xF0293)
     */
    public data object FULLSCREEN : MaterialDesignGlyphs(0xF0293)

    /**
     * 󰊔 (0xF0294)
     */
    public data object FULLSCREEN_EXIT : MaterialDesignGlyphs(0xF0294)

    /**
     * 󰊕 (0xF0295)
     */
    public data object FUNCTION : MaterialDesignGlyphs(0xF0295)

    /**
     * 󰊖 (0xF0296)
     */
    public data object GAMEPAD : MaterialDesignGlyphs(0xF0296)

    /**
     * 󰊗 (0xF0297)
     */
    public data object GAMEPAD_VARIANT : MaterialDesignGlyphs(0xF0297)

    /**
     * 󰊘 (0xF0298)
     */
    public data object GAS_STATION : MaterialDesignGlyphs(0xF0298)

    /**
     * 󰊙 (0xF0299)
     */
    public data object GATE : MaterialDesignGlyphs(0xF0299)

    /**
     * 󰊚 (0xF029A)
     */
    public data object GAUGE : MaterialDesignGlyphs(0xF029A)

    /**
     * 󰊛 (0xF029B)
     */
    public data object GAVEL : MaterialDesignGlyphs(0xF029B)

    /**
     * 󰊜 (0xF029C)
     */
    public data object GENDER_FEMALE : MaterialDesignGlyphs(0xF029C)

    /**
     * 󰊝 (0xF029D)
     */
    public data object GENDER_MALE : MaterialDesignGlyphs(0xF029D)

    /**
     * 󰊞 (0xF029E)
     */
    public data object GENDER_MALE_FEMALE : MaterialDesignGlyphs(0xF029E)

    /**
     * 󰊟 (0xF029F)
     */
    public data object GENDER_TRANSGENDER : MaterialDesignGlyphs(0xF029F)

    /**
     * 󰊠 (0xF02A0)
     */
    public data object GHOST : MaterialDesignGlyphs(0xF02A0)

    /**
     * 󰊡 (0xF02A1)
     */
    public data object GIFT_OUTLINE : MaterialDesignGlyphs(0xF02A1)

    /**
     * 󰊢 (0xF02A2)
     */
    public data object GIT : MaterialDesignGlyphs(0xF02A2)

    /**
     * 󰊣 (0xF02A3)
     */
    public data object CARD_ACCOUNT_DETAILS_STAR : MaterialDesignGlyphs(0xF02A3)

    /**
     * 󰊤 (0xF02A4)
     */
    public data object GITHUB : MaterialDesignGlyphs(0xF02A4)

    /**
     * 󰊥 (0xF02A5)
     */
    public data object GLASS_FLUTE : MaterialDesignGlyphs(0xF02A5)

    /**
     * 󰊦 (0xF02A6)
     */
    public data object GLASS_MUG : MaterialDesignGlyphs(0xF02A6)

    /**
     * 󰊧 (0xF02A7)
     */
    public data object GLASS_STANGE : MaterialDesignGlyphs(0xF02A7)

    /**
     * 󰊨 (0xF02A8)
     */
    public data object GLASS_TULIP : MaterialDesignGlyphs(0xF02A8)

    /**
     * 󰊩 (0xF02A9)
     */
    public data object BOWL_OUTLINE : MaterialDesignGlyphs(0xF02A9)

    /**
     * 󰊪 (0xF02AA)
     */
    public data object GLASSES : MaterialDesignGlyphs(0xF02AA)

    /**
     * 󰊫 (0xF02AB)
     */
    public data object GMAIL : MaterialDesignGlyphs(0xF02AB)

    /**
     * 󰊬 (0xF02AC)
     */
    public data object GNOME : MaterialDesignGlyphs(0xF02AC)

    /**
     * 󰊭 (0xF02AD)
     */
    public data object GOOGLE : MaterialDesignGlyphs(0xF02AD)

    /**
     * 󰊮 (0xF02AE)
     */
    public data object GOOGLE_CARDBOARD : MaterialDesignGlyphs(0xF02AE)

    /**
     * 󰊯 (0xF02AF)
     */
    public data object GOOGLE_CHROME : MaterialDesignGlyphs(0xF02AF)

    /**
     * 󰊰 (0xF02B0)
     */
    public data object GOOGLE_CIRCLES : MaterialDesignGlyphs(0xF02B0)

    /**
     * 󰊱 (0xF02B1)
     */
    public data object GOOGLE_CIRCLES_COMMUNITIES : MaterialDesignGlyphs(0xF02B1)

    /**
     * 󰊲 (0xF02B2)
     */
    public data object GOOGLE_CIRCLES_EXTENDED : MaterialDesignGlyphs(0xF02B2)

    /**
     * 󰊳 (0xF02B3)
     */
    public data object GOOGLE_CIRCLES_GROUP : MaterialDesignGlyphs(0xF02B3)

    /**
     * 󰊴 (0xF02B4)
     */
    public data object GOOGLE_CONTROLLER : MaterialDesignGlyphs(0xF02B4)

    /**
     * 󰊵 (0xF02B5)
     */
    public data object GOOGLE_CONTROLLER_OFF : MaterialDesignGlyphs(0xF02B5)

    /**
     * 󰊶 (0xF02B6)
     */
    public data object GOOGLE_DRIVE : MaterialDesignGlyphs(0xF02B6)

    /**
     * 󰊷 (0xF02B7)
     */
    public data object GOOGLE_EARTH : MaterialDesignGlyphs(0xF02B7)

    /**
     * 󰊸 (0xF02B8)
     */
    public data object GOOGLE_GLASS : MaterialDesignGlyphs(0xF02B8)

    /**
     * 󰊹 (0xF02B9)
     */
    public data object GOOGLE_NEARBY : MaterialDesignGlyphs(0xF02B9)

    /**
     * 󰊺 (0xF02BA)
     */
    public data object VIDEO_MINUS_OUTLINE : MaterialDesignGlyphs(0xF02BA)

    /**
     * 󰊻 (0xF02BB)
     */
    public data object MICROSOFT_TEAMS : MaterialDesignGlyphs(0xF02BB)

    /**
     * 󰊼 (0xF02BC)
     */
    public data object GOOGLE_PLAY : MaterialDesignGlyphs(0xF02BC)

    /**
     * 󰊽 (0xF02BD)
     */
    public data object GOOGLE_PLUS : MaterialDesignGlyphs(0xF02BD)

    /**
     * 󰊾 (0xF02BE)
     */
    public data object ORDER_BOOL_ASCENDING : MaterialDesignGlyphs(0xF02BE)

    /**
     * 󰊿 (0xF02BF)
     */
    public data object GOOGLE_TRANSLATE : MaterialDesignGlyphs(0xF02BF)

    /**
     * 󰋀 (0xF02C0)
     */
    public data object GOOGLE_CLASSROOM : MaterialDesignGlyphs(0xF02C0)

    /**
     * 󰋁 (0xF02C1)
     */
    public data object GRID : MaterialDesignGlyphs(0xF02C1)

    /**
     * 󰋂 (0xF02C2)
     */
    public data object GRID_OFF : MaterialDesignGlyphs(0xF02C2)

    /**
     * 󰋃 (0xF02C3)
     */
    public data object GROUP : MaterialDesignGlyphs(0xF02C3)

    /**
     * 󰋄 (0xF02C4)
     */
    public data object GUITAR_ELECTRIC : MaterialDesignGlyphs(0xF02C4)

    /**
     * 󰋅 (0xF02C5)
     */
    public data object GUITAR_PICK : MaterialDesignGlyphs(0xF02C5)

    /**
     * 󰋆 (0xF02C6)
     */
    public data object GUITAR_PICK_OUTLINE : MaterialDesignGlyphs(0xF02C6)

    /**
     * 󰋇 (0xF02C7)
     */
    public data object HAND_POINTING_RIGHT : MaterialDesignGlyphs(0xF02C7)

    /**
     * 󰋈 (0xF02C8)
     */
    public data object HANGER : MaterialDesignGlyphs(0xF02C8)

    /**
     * 󰋉 (0xF02C9)
     */
    public data object GOOGLE_HANGOUTS : MaterialDesignGlyphs(0xF02C9)

    /**
     * 󰋊 (0xF02CA)
     */
    public data object HARDDISK : MaterialDesignGlyphs(0xF02CA)

    /**
     * 󰋋 (0xF02CB)
     */
    public data object HEADPHONES : MaterialDesignGlyphs(0xF02CB)

    /**
     * 󰋌 (0xF02CC)
     */
    public data object HEADPHONES_BOX : MaterialDesignGlyphs(0xF02CC)

    /**
     * 󰋍 (0xF02CD)
     */
    public data object HEADPHONES_SETTINGS : MaterialDesignGlyphs(0xF02CD)

    /**
     * 󰋎 (0xF02CE)
     */
    public data object HEADSET : MaterialDesignGlyphs(0xF02CE)

    /**
     * 󰋏 (0xF02CF)
     */
    public data object HEADSET_DOCK : MaterialDesignGlyphs(0xF02CF)

    /**
     * 󰋐 (0xF02D0)
     */
    public data object HEADSET_OFF : MaterialDesignGlyphs(0xF02D0)

    /**
     * 󰋑 (0xF02D1)
     */
    public data object HEART : MaterialDesignGlyphs(0xF02D1)

    /**
     * 󰋒 (0xF02D2)
     */
    public data object HEART_BOX : MaterialDesignGlyphs(0xF02D2)

    /**
     * 󰋓 (0xF02D3)
     */
    public data object HEART_BOX_OUTLINE : MaterialDesignGlyphs(0xF02D3)

    /**
     * 󰋔 (0xF02D4)
     */
    public data object HEART_BROKEN : MaterialDesignGlyphs(0xF02D4)

    /**
     * 󰋕 (0xF02D5)
     */
    public data object HEART_OUTLINE : MaterialDesignGlyphs(0xF02D5)

    /**
     * 󰋖 (0xF02D6)
     */
    public data object HELP : MaterialDesignGlyphs(0xF02D6)

    /**
     * 󰋗 (0xF02D7)
     */
    public data object HELP_CIRCLE : MaterialDesignGlyphs(0xF02D7)

    /**
     * 󰋘 (0xF02D8)
     */
    public data object HEXAGON : MaterialDesignGlyphs(0xF02D8)

    /**
     * 󰋙 (0xF02D9)
     */
    public data object HEXAGON_OUTLINE : MaterialDesignGlyphs(0xF02D9)

    /**
     * 󰋚 (0xF02DA)
     */
    public data object HISTORY : MaterialDesignGlyphs(0xF02DA)

    /**
     * 󰋛 (0xF02DB)
     */
    public data object HOLOLENS : MaterialDesignGlyphs(0xF02DB)

    /**
     * 󰋜 (0xF02DC)
     */
    public data object HOME : MaterialDesignGlyphs(0xF02DC)

    /**
     * 󰋝 (0xF02DD)
     */
    public data object HOME_MODERN : MaterialDesignGlyphs(0xF02DD)

    /**
     * 󰋞 (0xF02DE)
     */
    public data object HOME_VARIANT : MaterialDesignGlyphs(0xF02DE)

    /**
     * 󰋟 (0xF02DF)
     */
    public data object HOPS : MaterialDesignGlyphs(0xF02DF)

    /**
     * 󰋠 (0xF02E0)
     */
    public data object HOSPITAL_BOX : MaterialDesignGlyphs(0xF02E0)

    /**
     * 󰋡 (0xF02E1)
     */
    public data object HOSPITAL_BUILDING : MaterialDesignGlyphs(0xF02E1)

    /**
     * 󰋢 (0xF02E2)
     */
    public data object HOSPITAL_MARKER : MaterialDesignGlyphs(0xF02E2)

    /**
     * 󰋣 (0xF02E3)
     */
    public data object BED : MaterialDesignGlyphs(0xF02E3)

    /**
     * 󰋤 (0xF02E4)
     */
    public data object BOWL_MIX_OUTLINE : MaterialDesignGlyphs(0xF02E4)

    /**
     * 󰋥 (0xF02E5)
     */
    public data object POT : MaterialDesignGlyphs(0xF02E5)

    /**
     * 󰋦 (0xF02E6)
     */
    public data object HUMAN : MaterialDesignGlyphs(0xF02E6)

    /**
     * 󰋧 (0xF02E7)
     */
    public data object HUMAN_CHILD : MaterialDesignGlyphs(0xF02E7)

    /**
     * 󰋨 (0xF02E8)
     */
    public data object HUMAN_MALE_FEMALE : MaterialDesignGlyphs(0xF02E8)

    /**
     * 󰋩 (0xF02E9)
     */
    public data object IMAGE : MaterialDesignGlyphs(0xF02E9)

    /**
     * 󰋪 (0xF02EA)
     */
    public data object IMAGE_ALBUM : MaterialDesignGlyphs(0xF02EA)

    /**
     * 󰋫 (0xF02EB)
     */
    public data object IMAGE_AREA : MaterialDesignGlyphs(0xF02EB)

    /**
     * 󰋬 (0xF02EC)
     */
    public data object IMAGE_AREA_CLOSE : MaterialDesignGlyphs(0xF02EC)

    /**
     * 󰋭 (0xF02ED)
     */
    public data object IMAGE_BROKEN : MaterialDesignGlyphs(0xF02ED)

    /**
     * 󰋮 (0xF02EE)
     */
    public data object IMAGE_BROKEN_VARIANT : MaterialDesignGlyphs(0xF02EE)

    /**
     * 󰋯 (0xF02EF)
     */
    public data object IMAGE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF02EF)

    /**
     * 󰋰 (0xF02F0)
     */
    public data object IMAGE_FILTER_BLACK_WHITE : MaterialDesignGlyphs(0xF02F0)

    /**
     * 󰋱 (0xF02F1)
     */
    public data object IMAGE_FILTER_CENTER_FOCUS : MaterialDesignGlyphs(0xF02F1)

    /**
     * 󰋲 (0xF02F2)
     */
    public data object IMAGE_FILTER_CENTER_FOCUS_WEAK : MaterialDesignGlyphs(0xF02F2)

    /**
     * 󰋳 (0xF02F3)
     */
    public data object IMAGE_FILTER_DRAMA : MaterialDesignGlyphs(0xF02F3)

    /**
     * 󰋴 (0xF02F4)
     */
    public data object IMAGE_FILTER_FRAMES : MaterialDesignGlyphs(0xF02F4)

    /**
     * 󰋵 (0xF02F5)
     */
    public data object IMAGE_FILTER_HDR : MaterialDesignGlyphs(0xF02F5)

    /**
     * 󰋶 (0xF02F6)
     */
    public data object IMAGE_FILTER_NONE : MaterialDesignGlyphs(0xF02F6)

    /**
     * 󰋷 (0xF02F7)
     */
    public data object IMAGE_FILTER_TILT_SHIFT : MaterialDesignGlyphs(0xF02F7)

    /**
     * 󰋸 (0xF02F8)
     */
    public data object IMAGE_FILTER_VINTAGE : MaterialDesignGlyphs(0xF02F8)

    /**
     * 󰋹 (0xF02F9)
     */
    public data object IMAGE_MULTIPLE : MaterialDesignGlyphs(0xF02F9)

    /**
     * 󰋺 (0xF02FA)
     */
    public data object IMPORT : MaterialDesignGlyphs(0xF02FA)

    /**
     * 󰋻 (0xF02FB)
     */
    public data object INBOX_ARROW_DOWN : MaterialDesignGlyphs(0xF02FB)

    /**
     * 󰋼 (0xF02FC)
     */
    public data object INFORMATION : MaterialDesignGlyphs(0xF02FC)

    /**
     * 󰋽 (0xF02FD)
     */
    public data object INFORMATION_OUTLINE : MaterialDesignGlyphs(0xF02FD)

    /**
     * 󰋾 (0xF02FE)
     */
    public data object INSTAGRAM : MaterialDesignGlyphs(0xF02FE)

    /**
     * 󰋿 (0xF02FF)
     */
    public data object POT_OUTLINE : MaterialDesignGlyphs(0xF02FF)

    /**
     * 󰌀 (0xF0300)
     */
    public data object MICROSOFT_INTERNET_EXPLORER : MaterialDesignGlyphs(0xF0300)

    /**
     * 󰌁 (0xF0301)
     */
    public data object INVERT_COLORS : MaterialDesignGlyphs(0xF0301)

    /**
     * 󰌂 (0xF0302)
     */
    public data object JEEPNEY : MaterialDesignGlyphs(0xF0302)

    /**
     * 󰌃 (0xF0303)
     */
    public data object JIRA : MaterialDesignGlyphs(0xF0303)

    /**
     * 󰌄 (0xF0304)
     */
    public data object JSFIDDLE : MaterialDesignGlyphs(0xF0304)

    /**
     * 󰌅 (0xF0305)
     */
    public data object KEG : MaterialDesignGlyphs(0xF0305)

    /**
     * 󰌆 (0xF0306)
     */
    public data object KEY : MaterialDesignGlyphs(0xF0306)

    /**
     * 󰌇 (0xF0307)
     */
    public data object KEY_CHANGE : MaterialDesignGlyphs(0xF0307)

    /**
     * 󰌈 (0xF0308)
     */
    public data object KEY_MINUS : MaterialDesignGlyphs(0xF0308)

    /**
     * 󰌉 (0xF0309)
     */
    public data object KEY_PLUS : MaterialDesignGlyphs(0xF0309)

    /**
     * 󰌊 (0xF030A)
     */
    public data object KEY_REMOVE : MaterialDesignGlyphs(0xF030A)

    /**
     * 󰌋 (0xF030B)
     */
    public data object KEY_VARIANT : MaterialDesignGlyphs(0xF030B)

    /**
     * 󰌌 (0xF030C)
     */
    public data object KEYBOARD : MaterialDesignGlyphs(0xF030C)

    /**
     * 󰌍 (0xF030D)
     */
    public data object KEYBOARD_BACKSPACE : MaterialDesignGlyphs(0xF030D)

    /**
     * 󰌎 (0xF030E)
     */
    public data object KEYBOARD_CAPS : MaterialDesignGlyphs(0xF030E)

    /**
     * 󰌏 (0xF030F)
     */
    public data object KEYBOARD_CLOSE : MaterialDesignGlyphs(0xF030F)

    /**
     * 󰌐 (0xF0310)
     */
    public data object KEYBOARD_OFF : MaterialDesignGlyphs(0xF0310)

    /**
     * 󰌑 (0xF0311)
     */
    public data object KEYBOARD_RETURN : MaterialDesignGlyphs(0xF0311)

    /**
     * 󰌒 (0xF0312)
     */
    public data object KEYBOARD_TAB : MaterialDesignGlyphs(0xF0312)

    /**
     * 󰌓 (0xF0313)
     */
    public data object KEYBOARD_VARIANT : MaterialDesignGlyphs(0xF0313)

    /**
     * 󰌔 (0xF0314)
     */
    public data object KODI : MaterialDesignGlyphs(0xF0314)

    /**
     * 󰌕 (0xF0315)
     */
    public data object LABEL : MaterialDesignGlyphs(0xF0315)

    /**
     * 󰌖 (0xF0316)
     */
    public data object LABEL_OUTLINE : MaterialDesignGlyphs(0xF0316)

    /**
     * 󰌗 (0xF0317)
     */
    public data object LAN : MaterialDesignGlyphs(0xF0317)

    /**
     * 󰌘 (0xF0318)
     */
    public data object LAN_CONNECT : MaterialDesignGlyphs(0xF0318)

    /**
     * 󰌙 (0xF0319)
     */
    public data object LAN_DISCONNECT : MaterialDesignGlyphs(0xF0319)

    /**
     * 󰌚 (0xF031A)
     */
    public data object LAN_PENDING : MaterialDesignGlyphs(0xF031A)

    /**
     * 󰌛 (0xF031B)
     */
    public data object LANGUAGE_CSHARP : MaterialDesignGlyphs(0xF031B)

    /**
     * 󰌜 (0xF031C)
     */
    public data object LANGUAGE_CSS3 : MaterialDesignGlyphs(0xF031C)

    /**
     * 󰌝 (0xF031D)
     */
    public data object LANGUAGE_HTML5 : MaterialDesignGlyphs(0xF031D)

    /**
     * 󰌞 (0xF031E)
     */
    public data object LANGUAGE_JAVASCRIPT : MaterialDesignGlyphs(0xF031E)

    /**
     * 󰌟 (0xF031F)
     */
    public data object LANGUAGE_PHP : MaterialDesignGlyphs(0xF031F)

    /**
     * 󰌠 (0xF0320)
     */
    public data object LANGUAGE_PYTHON : MaterialDesignGlyphs(0xF0320)

    /**
     * 󰌡 (0xF0321)
     */
    public data object CONTACTLESS_PAYMENT_CIRCLE : MaterialDesignGlyphs(0xF0321)

    /**
     * 󰌢 (0xF0322)
     */
    public data object LAPTOP : MaterialDesignGlyphs(0xF0322)

    /**
     * 󰌣 (0xF0323)
     */
    public data object MAGAZINE_RIFLE : MaterialDesignGlyphs(0xF0323)

    /**
     * 󰌤 (0xF0324)
     */
    public data object MAGAZINE_PISTOL : MaterialDesignGlyphs(0xF0324)

    /**
     * 󰌥 (0xF0325)
     */
    public data object KEYBOARD_TAB_REVERSE : MaterialDesignGlyphs(0xF0325)

    /**
     * 󰌦 (0xF0326)
     */
    public data object POT_STEAM_OUTLINE : MaterialDesignGlyphs(0xF0326)

    /**
     * 󰌧 (0xF0327)
     */
    public data object LAUNCH : MaterialDesignGlyphs(0xF0327)

    /**
     * 󰌨 (0xF0328)
     */
    public data object LAYERS : MaterialDesignGlyphs(0xF0328)

    /**
     * 󰌩 (0xF0329)
     */
    public data object LAYERS_OFF : MaterialDesignGlyphs(0xF0329)

    /**
     * 󰌪 (0xF032A)
     */
    public data object LEAF : MaterialDesignGlyphs(0xF032A)

    /**
     * 󰌫 (0xF032B)
     */
    public data object LED_OFF : MaterialDesignGlyphs(0xF032B)

    /**
     * 󰌬 (0xF032C)
     */
    public data object LED_ON : MaterialDesignGlyphs(0xF032C)

    /**
     * 󰌭 (0xF032D)
     */
    public data object LED_OUTLINE : MaterialDesignGlyphs(0xF032D)

    /**
     * 󰌮 (0xF032E)
     */
    public data object LED_VARIANT_OFF : MaterialDesignGlyphs(0xF032E)

    /**
     * 󰌯 (0xF032F)
     */
    public data object LED_VARIANT_ON : MaterialDesignGlyphs(0xF032F)

    /**
     * 󰌰 (0xF0330)
     */
    public data object LED_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF0330)

    /**
     * 󰌱 (0xF0331)
     */
    public data object LIBRARY : MaterialDesignGlyphs(0xF0331)

    /**
     * 󰌲 (0xF0332)
     */
    public data object FILMSTRIP_BOX : MaterialDesignGlyphs(0xF0332)

    /**
     * 󰌳 (0xF0333)
     */
    public data object MUSIC_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0333)

    /**
     * 󰌴 (0xF0334)
     */
    public data object PLUS_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0334)

    /**
     * 󰌵 (0xF0335)
     */
    public data object LIGHTBULB : MaterialDesignGlyphs(0xF0335)

    /**
     * 󰌶 (0xF0336)
     */
    public data object LIGHTBULB_OUTLINE : MaterialDesignGlyphs(0xF0336)

    /**
     * 󰌷 (0xF0337)
     */
    public data object LINK : MaterialDesignGlyphs(0xF0337)

    /**
     * 󰌸 (0xF0338)
     */
    public data object LINK_OFF : MaterialDesignGlyphs(0xF0338)

    /**
     * 󰌹 (0xF0339)
     */
    public data object LINK_VARIANT : MaterialDesignGlyphs(0xF0339)

    /**
     * 󰌺 (0xF033A)
     */
    public data object LINK_VARIANT_OFF : MaterialDesignGlyphs(0xF033A)

    /**
     * 󰌻 (0xF033B)
     */
    public data object LINKEDIN : MaterialDesignGlyphs(0xF033B)

    /**
     * 󰌼 (0xF033C)
     */
    public data object SORT_REVERSE_VARIANT : MaterialDesignGlyphs(0xF033C)

    /**
     * 󰌽 (0xF033D)
     */
    public data object LINUX : MaterialDesignGlyphs(0xF033D)

    /**
     * 󰌾 (0xF033E)
     */
    public data object LOCK : MaterialDesignGlyphs(0xF033E)

    /**
     * 󰌿 (0xF033F)
     */
    public data object LOCK_OPEN : MaterialDesignGlyphs(0xF033F)

    /**
     * 󰍀 (0xF0340)
     */
    public data object LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF0340)

    /**
     * 󰍁 (0xF0341)
     */
    public data object LOCK_OUTLINE : MaterialDesignGlyphs(0xF0341)

    /**
     * 󰍂 (0xF0342)
     */
    public data object LOGIN : MaterialDesignGlyphs(0xF0342)

    /**
     * 󰍃 (0xF0343)
     */
    public data object LOGOUT : MaterialDesignGlyphs(0xF0343)

    /**
     * 󰍄 (0xF0344)
     */
    public data object LOOKS : MaterialDesignGlyphs(0xF0344)

    /**
     * 󰍅 (0xF0345)
     */
    public data object LOUPE : MaterialDesignGlyphs(0xF0345)

    /**
     * 󰍆 (0xF0346)
     */
    public data object LUMX : MaterialDesignGlyphs(0xF0346)

    /**
     * 󰍇 (0xF0347)
     */
    public data object MAGNET : MaterialDesignGlyphs(0xF0347)

    /**
     * 󰍈 (0xF0348)
     */
    public data object MAGNET_ON : MaterialDesignGlyphs(0xF0348)

    /**
     * 󰍉 (0xF0349)
     */
    public data object MAGNIFY : MaterialDesignGlyphs(0xF0349)

    /**
     * 󰍊 (0xF034A)
     */
    public data object MAGNIFY_MINUS : MaterialDesignGlyphs(0xF034A)

    /**
     * 󰍋 (0xF034B)
     */
    public data object MAGNIFY_PLUS : MaterialDesignGlyphs(0xF034B)

    /**
     * 󰍌 (0xF034C)
     */
    public data object PLUS_CIRCLE_MULTIPLE : MaterialDesignGlyphs(0xF034C)

    /**
     * 󰍍 (0xF034D)
     */
    public data object MAP : MaterialDesignGlyphs(0xF034D)

    /**
     * 󰍎 (0xF034E)
     */
    public data object MAP_MARKER : MaterialDesignGlyphs(0xF034E)

    /**
     * 󰍏 (0xF034F)
     */
    public data object MAP_MARKER_CIRCLE : MaterialDesignGlyphs(0xF034F)

    /**
     * 󰍐 (0xF0350)
     */
    public data object MAP_MARKER_MULTIPLE : MaterialDesignGlyphs(0xF0350)

    /**
     * 󰍑 (0xF0351)
     */
    public data object MAP_MARKER_OFF : MaterialDesignGlyphs(0xF0351)

    /**
     * 󰍒 (0xF0352)
     */
    public data object MAP_MARKER_RADIUS : MaterialDesignGlyphs(0xF0352)

    /**
     * 󰍓 (0xF0353)
     */
    public data object MARGIN : MaterialDesignGlyphs(0xF0353)

    /**
     * 󰍔 (0xF0354)
     */
    public data object LANGUAGE_MARKDOWN : MaterialDesignGlyphs(0xF0354)

    /**
     * 󰍕 (0xF0355)
     */
    public data object MARKER_CHECK : MaterialDesignGlyphs(0xF0355)

    /**
     * 󰍖 (0xF0356)
     */
    public data object GLASS_COCKTAIL : MaterialDesignGlyphs(0xF0356)

    /**
     * 󰍗 (0xF0357)
     */
    public data object MATERIAL_UI : MaterialDesignGlyphs(0xF0357)

    /**
     * 󰍘 (0xF0358)
     */
    public data object MATH_COMPASS : MaterialDesignGlyphs(0xF0358)

    /**
     * 󰍙 (0xF0359)
     */
    public data object STACKPATH : MaterialDesignGlyphs(0xF0359)

    /**
     * 󰍚 (0xF035A)
     */
    public data object MINUS_CIRCLE_MULTIPLE : MaterialDesignGlyphs(0xF035A)

    /**
     * 󰍛 (0xF035B)
     */
    public data object MEMORY : MaterialDesignGlyphs(0xF035B)

    /**
     * 󰍜 (0xF035C)
     */
    public data object MENU : MaterialDesignGlyphs(0xF035C)

    /**
     * 󰍝 (0xF035D)
     */
    public data object MENU_DOWN : MaterialDesignGlyphs(0xF035D)

    /**
     * 󰍞 (0xF035E)
     */
    public data object MENU_LEFT : MaterialDesignGlyphs(0xF035E)

    /**
     * 󰍟 (0xF035F)
     */
    public data object MENU_RIGHT : MaterialDesignGlyphs(0xF035F)

    /**
     * 󰍠 (0xF0360)
     */
    public data object MENU_UP : MaterialDesignGlyphs(0xF0360)

    /**
     * 󰍡 (0xF0361)
     */
    public data object MESSAGE : MaterialDesignGlyphs(0xF0361)

    /**
     * 󰍢 (0xF0362)
     */
    public data object MESSAGE_ALERT : MaterialDesignGlyphs(0xF0362)

    /**
     * 󰍣 (0xF0363)
     */
    public data object MESSAGE_DRAW : MaterialDesignGlyphs(0xF0363)

    /**
     * 󰍤 (0xF0364)
     */
    public data object MESSAGE_IMAGE : MaterialDesignGlyphs(0xF0364)

    /**
     * 󰍥 (0xF0365)
     */
    public data object MESSAGE_OUTLINE : MaterialDesignGlyphs(0xF0365)

    /**
     * 󰍦 (0xF0366)
     */
    public data object MESSAGE_PROCESSING : MaterialDesignGlyphs(0xF0366)

    /**
     * 󰍧 (0xF0367)
     */
    public data object MESSAGE_REPLY : MaterialDesignGlyphs(0xF0367)

    /**
     * 󰍨 (0xF0368)
     */
    public data object MESSAGE_REPLY_TEXT : MaterialDesignGlyphs(0xF0368)

    /**
     * 󰍩 (0xF0369)
     */
    public data object MESSAGE_TEXT : MaterialDesignGlyphs(0xF0369)

    /**
     * 󰍪 (0xF036A)
     */
    public data object MESSAGE_TEXT_OUTLINE : MaterialDesignGlyphs(0xF036A)

    /**
     * 󰍫 (0xF036B)
     */
    public data object MESSAGE_VIDEO : MaterialDesignGlyphs(0xF036B)

    /**
     * 󰍬 (0xF036C)
     */
    public data object MICROPHONE : MaterialDesignGlyphs(0xF036C)

    /**
     * 󰍭 (0xF036D)
     */
    public data object MICROPHONE_OFF : MaterialDesignGlyphs(0xF036D)

    /**
     * 󰍮 (0xF036E)
     */
    public data object MICROPHONE_OUTLINE : MaterialDesignGlyphs(0xF036E)

    /**
     * 󰍯 (0xF036F)
     */
    public data object MICROPHONE_SETTINGS : MaterialDesignGlyphs(0xF036F)

    /**
     * 󰍰 (0xF0370)
     */
    public data object MICROPHONE_VARIANT : MaterialDesignGlyphs(0xF0370)

    /**
     * 󰍱 (0xF0371)
     */
    public data object MICROPHONE_VARIANT_OFF : MaterialDesignGlyphs(0xF0371)

    /**
     * 󰍲 (0xF0372)
     */
    public data object MICROSOFT : MaterialDesignGlyphs(0xF0372)

    /**
     * 󰍳 (0xF0373)
     */
    public data object MINECRAFT : MaterialDesignGlyphs(0xF0373)

    /**
     * 󰍴 (0xF0374)
     */
    public data object MINUS : MaterialDesignGlyphs(0xF0374)

    /**
     * 󰍵 (0xF0375)
     */
    public data object MINUS_BOX : MaterialDesignGlyphs(0xF0375)

    /**
     * 󰍶 (0xF0376)
     */
    public data object MINUS_CIRCLE : MaterialDesignGlyphs(0xF0376)

    /**
     * 󰍷 (0xF0377)
     */
    public data object MINUS_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0377)

    /**
     * 󰍸 (0xF0378)
     */
    public data object MINUS_NETWORK : MaterialDesignGlyphs(0xF0378)

    /**
     * 󰍹 (0xF0379)
     */
    public data object MONITOR : MaterialDesignGlyphs(0xF0379)

    /**
     * 󰍺 (0xF037A)
     */
    public data object MONITOR_MULTIPLE : MaterialDesignGlyphs(0xF037A)

    /**
     * 󰍻 (0xF037B)
     */
    public data object MORE : MaterialDesignGlyphs(0xF037B)

    /**
     * 󰍼 (0xF037C)
     */
    public data object MOTORBIKE : MaterialDesignGlyphs(0xF037C)

    /**
     * 󰍽 (0xF037D)
     */
    public data object MOUSE : MaterialDesignGlyphs(0xF037D)

    /**
     * 󰍾 (0xF037E)
     */
    public data object MOUSE_OFF : MaterialDesignGlyphs(0xF037E)

    /**
     * 󰍿 (0xF037F)
     */
    public data object MOUSE_VARIANT : MaterialDesignGlyphs(0xF037F)

    /**
     * 󰎀 (0xF0380)
     */
    public data object MOUSE_VARIANT_OFF : MaterialDesignGlyphs(0xF0380)

    /**
     * 󰎁 (0xF0381)
     */
    public data object MOVIE : MaterialDesignGlyphs(0xF0381)

    /**
     * 󰎂 (0xF0382)
     */
    public data object MULTIPLICATION : MaterialDesignGlyphs(0xF0382)

    /**
     * 󰎃 (0xF0383)
     */
    public data object MULTIPLICATION_BOX : MaterialDesignGlyphs(0xF0383)

    /**
     * 󰎄 (0xF0384)
     */
    public data object MUSIC_BOX : MaterialDesignGlyphs(0xF0384)

    /**
     * 󰎅 (0xF0385)
     */
    public data object MUSIC_BOX_OUTLINE : MaterialDesignGlyphs(0xF0385)

    /**
     * 󰎆 (0xF0386)
     */
    public data object MUSIC_CIRCLE : MaterialDesignGlyphs(0xF0386)

    /**
     * 󰎇 (0xF0387)
     */
    public data object MUSIC_NOTE : MaterialDesignGlyphs(0xF0387)

    /**
     * 󰎉 (0xF0389)
     */
    public data object MUSIC_NOTE_HALF : MaterialDesignGlyphs(0xF0389)

    /**
     * 󰎊 (0xF038A)
     */
    public data object MUSIC_NOTE_OFF : MaterialDesignGlyphs(0xF038A)

    /**
     * 󰎋 (0xF038B)
     */
    public data object MUSIC_NOTE_QUARTER : MaterialDesignGlyphs(0xF038B)

    /**
     * 󰎌 (0xF038C)
     */
    public data object MUSIC_NOTE_SIXTEENTH : MaterialDesignGlyphs(0xF038C)

    /**
     * 󰎍 (0xF038D)
     */
    public data object MUSIC_NOTE_WHOLE : MaterialDesignGlyphs(0xF038D)

    /**
     * 󰎎 (0xF038E)
     */
    public data object NATURE : MaterialDesignGlyphs(0xF038E)

    /**
     * 󰎏 (0xF038F)
     */
    public data object NATURE_PEOPLE : MaterialDesignGlyphs(0xF038F)

    /**
     * 󰎐 (0xF0390)
     */
    public data object NAVIGATION : MaterialDesignGlyphs(0xF0390)

    /**
     * 󰎑 (0xF0391)
     */
    public data object NEEDLE : MaterialDesignGlyphs(0xF0391)

    /**
     * 󰎒 (0xF0392)
     */
    public data object SMOKE_DETECTOR : MaterialDesignGlyphs(0xF0392)

    /**
     * 󰎓 (0xF0393)
     */
    public data object THERMOSTAT : MaterialDesignGlyphs(0xF0393)

    /**
     * 󰎔 (0xF0394)
     */
    public data object NEW_BOX : MaterialDesignGlyphs(0xF0394)

    /**
     * 󰎕 (0xF0395)
     */
    public data object NEWSPAPER : MaterialDesignGlyphs(0xF0395)

    /**
     * 󰎖 (0xF0396)
     */
    public data object NFC : MaterialDesignGlyphs(0xF0396)

    /**
     * 󰎗 (0xF0397)
     */
    public data object NFC_TAP : MaterialDesignGlyphs(0xF0397)

    /**
     * 󰎘 (0xF0398)
     */
    public data object NFC_VARIANT : MaterialDesignGlyphs(0xF0398)

    /**
     * 󰎙 (0xF0399)
     */
    public data object NODEJS : MaterialDesignGlyphs(0xF0399)

    /**
     * 󰎚 (0xF039A)
     */
    public data object NOTE : MaterialDesignGlyphs(0xF039A)

    /**
     * 󰎛 (0xF039B)
     */
    public data object NOTE_OUTLINE : MaterialDesignGlyphs(0xF039B)

    /**
     * 󰎜 (0xF039C)
     */
    public data object NOTE_PLUS : MaterialDesignGlyphs(0xF039C)

    /**
     * 󰎝 (0xF039D)
     */
    public data object NOTE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF039D)

    /**
     * 󰎞 (0xF039E)
     */
    public data object NOTE_TEXT : MaterialDesignGlyphs(0xF039E)

    /**
     * 󰎟 (0xF039F)
     */
    public data object NOTIFICATION_CLEAR_ALL : MaterialDesignGlyphs(0xF039F)

    /**
     * 󰎠 (0xF03A0)
     */
    public data object NUMERIC : MaterialDesignGlyphs(0xF03A0)

    /**
     * 󰎡 (0xF03A1)
     */
    public data object NUMERIC_0_BOX : MaterialDesignGlyphs(0xF03A1)

    /**
     * 󰎢 (0xF03A2)
     */
    public data object NUMERIC_0_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03A2)

    /**
     * 󰎣 (0xF03A3)
     */
    public data object NUMERIC_0_BOX_OUTLINE : MaterialDesignGlyphs(0xF03A3)

    /**
     * 󰎤 (0xF03A4)
     */
    public data object NUMERIC_1_BOX : MaterialDesignGlyphs(0xF03A4)

    /**
     * 󰎥 (0xF03A5)
     */
    public data object NUMERIC_1_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03A5)

    /**
     * 󰎦 (0xF03A6)
     */
    public data object NUMERIC_1_BOX_OUTLINE : MaterialDesignGlyphs(0xF03A6)

    /**
     * 󰎧 (0xF03A7)
     */
    public data object NUMERIC_2_BOX : MaterialDesignGlyphs(0xF03A7)

    /**
     * 󰎨 (0xF03A8)
     */
    public data object NUMERIC_2_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03A8)

    /**
     * 󰎩 (0xF03A9)
     */
    public data object NUMERIC_2_BOX_OUTLINE : MaterialDesignGlyphs(0xF03A9)

    /**
     * 󰎪 (0xF03AA)
     */
    public data object NUMERIC_3_BOX : MaterialDesignGlyphs(0xF03AA)

    /**
     * 󰎫 (0xF03AB)
     */
    public data object NUMERIC_3_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03AB)

    /**
     * 󰎬 (0xF03AC)
     */
    public data object NUMERIC_3_BOX_OUTLINE : MaterialDesignGlyphs(0xF03AC)

    /**
     * 󰎭 (0xF03AD)
     */
    public data object NUMERIC_4_BOX : MaterialDesignGlyphs(0xF03AD)

    /**
     * 󰎮 (0xF03AE)
     */
    public data object NUMERIC_4_BOX_OUTLINE : MaterialDesignGlyphs(0xF03AE)

    /**
     * 󰎯 (0xF03AF)
     */
    public data object NUMERIC_5_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03AF)

    /**
     * 󰎰 (0xF03B0)
     */
    public data object NUMERIC_5_BOX_OUTLINE : MaterialDesignGlyphs(0xF03B0)

    /**
     * 󰎱 (0xF03B1)
     */
    public data object NUMERIC_5_BOX : MaterialDesignGlyphs(0xF03B1)

    /**
     * 󰎲 (0xF03B2)
     */
    public data object NUMERIC_4_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03B2)

    /**
     * 󰎳 (0xF03B3)
     */
    public data object NUMERIC_6_BOX : MaterialDesignGlyphs(0xF03B3)

    /**
     * 󰎴 (0xF03B4)
     */
    public data object NUMERIC_6_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03B4)

    /**
     * 󰎵 (0xF03B5)
     */
    public data object NUMERIC_6_BOX_OUTLINE : MaterialDesignGlyphs(0xF03B5)

    /**
     * 󰎶 (0xF03B6)
     */
    public data object NUMERIC_7_BOX : MaterialDesignGlyphs(0xF03B6)

    /**
     * 󰎷 (0xF03B7)
     */
    public data object NUMERIC_7_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03B7)

    /**
     * 󰎸 (0xF03B8)
     */
    public data object NUMERIC_7_BOX_OUTLINE : MaterialDesignGlyphs(0xF03B8)

    /**
     * 󰎹 (0xF03B9)
     */
    public data object NUMERIC_8_BOX : MaterialDesignGlyphs(0xF03B9)

    /**
     * 󰎺 (0xF03BA)
     */
    public data object NUMERIC_8_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03BA)

    /**
     * 󰎻 (0xF03BB)
     */
    public data object NUMERIC_8_BOX_OUTLINE : MaterialDesignGlyphs(0xF03BB)

    /**
     * 󰎼 (0xF03BC)
     */
    public data object NUMERIC_9_BOX : MaterialDesignGlyphs(0xF03BC)

    /**
     * 󰎽 (0xF03BD)
     */
    public data object NUMERIC_9_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03BD)

    /**
     * 󰎾 (0xF03BE)
     */
    public data object NUMERIC_9_BOX_OUTLINE : MaterialDesignGlyphs(0xF03BE)

    /**
     * 󰎿 (0xF03BF)
     */
    public data object NUMERIC_9_PLUS_BOX : MaterialDesignGlyphs(0xF03BF)

    /**
     * 󰏀 (0xF03C0)
     */
    public data object NUMERIC_9_PLUS_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF03C0)

    /**
     * 󰏁 (0xF03C1)
     */
    public data object NUMERIC_9_PLUS_BOX_OUTLINE : MaterialDesignGlyphs(0xF03C1)

    /**
     * 󰏂 (0xF03C2)
     */
    public data object NUTRITION : MaterialDesignGlyphs(0xF03C2)

    /**
     * 󰏃 (0xF03C3)
     */
    public data object OCTAGON : MaterialDesignGlyphs(0xF03C3)

    /**
     * 󰏄 (0xF03C4)
     */
    public data object OCTAGON_OUTLINE : MaterialDesignGlyphs(0xF03C4)

    /**
     * 󰏅 (0xF03C5)
     */
    public data object ODNOKLASSNIKI : MaterialDesignGlyphs(0xF03C5)

    /**
     * 󰏆 (0xF03C6)
     */
    public data object MICROSOFT_OFFICE : MaterialDesignGlyphs(0xF03C6)

    /**
     * 󰏇 (0xF03C7)
     */
    public data object OIL : MaterialDesignGlyphs(0xF03C7)

    /**
     * 󰏈 (0xF03C8)
     */
    public data object COOLANT_TEMPERATURE : MaterialDesignGlyphs(0xF03C8)

    /**
     * 󰏉 (0xF03C9)
     */
    public data object OMEGA : MaterialDesignGlyphs(0xF03C9)

    /**
     * 󰏊 (0xF03CA)
     */
    public data object MICROSOFT_ONEDRIVE : MaterialDesignGlyphs(0xF03CA)

    /**
     * 󰏋 (0xF03CB)
     */
    public data object OPEN_IN_APP : MaterialDesignGlyphs(0xF03CB)

    /**
     * 󰏌 (0xF03CC)
     */
    public data object OPEN_IN_NEW : MaterialDesignGlyphs(0xF03CC)

    /**
     * 󰏍 (0xF03CD)
     */
    public data object OPENID : MaterialDesignGlyphs(0xF03CD)

    /**
     * 󰏎 (0xF03CE)
     */
    public data object OPERA : MaterialDesignGlyphs(0xF03CE)

    /**
     * 󰏏 (0xF03CF)
     */
    public data object ORNAMENT : MaterialDesignGlyphs(0xF03CF)

    /**
     * 󰏐 (0xF03D0)
     */
    public data object ORNAMENT_VARIANT : MaterialDesignGlyphs(0xF03D0)

    /**
     * 󰏑 (0xF03D1)
     */
    public data object INBOX_ARROW_UP : MaterialDesignGlyphs(0xF03D1)

    /**
     * 󰏒 (0xF03D2)
     */
    public data object OWL : MaterialDesignGlyphs(0xF03D2)

    /**
     * 󰏓 (0xF03D3)
     */
    public data object PACKAGE : MaterialDesignGlyphs(0xF03D3)

    /**
     * 󰏔 (0xF03D4)
     */
    public data object PACKAGE_DOWN : MaterialDesignGlyphs(0xF03D4)

    /**
     * 󰏕 (0xF03D5)
     */
    public data object PACKAGE_UP : MaterialDesignGlyphs(0xF03D5)

    /**
     * 󰏖 (0xF03D6)
     */
    public data object PACKAGE_VARIANT : MaterialDesignGlyphs(0xF03D6)

    /**
     * 󰏗 (0xF03D7)
     */
    public data object PACKAGE_VARIANT_CLOSED : MaterialDesignGlyphs(0xF03D7)

    /**
     * 󰏘 (0xF03D8)
     */
    public data object PALETTE : MaterialDesignGlyphs(0xF03D8)

    /**
     * 󰏙 (0xF03D9)
     */
    public data object PALETTE_ADVANCED : MaterialDesignGlyphs(0xF03D9)

    /**
     * 󰏚 (0xF03DA)
     */
    public data object PANDA : MaterialDesignGlyphs(0xF03DA)

    /**
     * 󰏛 (0xF03DB)
     */
    public data object PANDORA : MaterialDesignGlyphs(0xF03DB)

    /**
     * 󰏜 (0xF03DC)
     */
    public data object PANORAMA : MaterialDesignGlyphs(0xF03DC)

    /**
     * 󰏝 (0xF03DD)
     */
    public data object PANORAMA_FISHEYE : MaterialDesignGlyphs(0xF03DD)

    /**
     * 󰏞 (0xF03DE)
     */
    public data object PANORAMA_HORIZONTAL_OUTLINE : MaterialDesignGlyphs(0xF03DE)

    /**
     * 󰏟 (0xF03DF)
     */
    public data object PANORAMA_VERTICAL_OUTLINE : MaterialDesignGlyphs(0xF03DF)

    /**
     * 󰏠 (0xF03E0)
     */
    public data object PANORAMA_WIDE_ANGLE_OUTLINE : MaterialDesignGlyphs(0xF03E0)

    /**
     * 󰏡 (0xF03E1)
     */
    public data object PAPER_CUT_VERTICAL : MaterialDesignGlyphs(0xF03E1)

    /**
     * 󰏢 (0xF03E2)
     */
    public data object PAPERCLIP : MaterialDesignGlyphs(0xF03E2)

    /**
     * 󰏣 (0xF03E3)
     */
    public data object PARKING : MaterialDesignGlyphs(0xF03E3)

    /**
     * 󰏤 (0xF03E4)
     */
    public data object PAUSE : MaterialDesignGlyphs(0xF03E4)

    /**
     * 󰏥 (0xF03E5)
     */
    public data object PAUSE_CIRCLE : MaterialDesignGlyphs(0xF03E5)

    /**
     * 󰏦 (0xF03E6)
     */
    public data object PAUSE_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF03E6)

    /**
     * 󰏧 (0xF03E7)
     */
    public data object PAUSE_OCTAGON : MaterialDesignGlyphs(0xF03E7)

    /**
     * 󰏨 (0xF03E8)
     */
    public data object PAUSE_OCTAGON_OUTLINE : MaterialDesignGlyphs(0xF03E8)

    /**
     * 󰏩 (0xF03E9)
     */
    public data object PAW : MaterialDesignGlyphs(0xF03E9)

    /**
     * 󰏪 (0xF03EA)
     */
    public data object PEN : MaterialDesignGlyphs(0xF03EA)

    /**
     * 󰏫 (0xF03EB)
     */
    public data object PENCIL : MaterialDesignGlyphs(0xF03EB)

    /**
     * 󰏬 (0xF03EC)
     */
    public data object PENCIL_BOX : MaterialDesignGlyphs(0xF03EC)

    /**
     * 󰏭 (0xF03ED)
     */
    public data object PENCIL_BOX_OUTLINE : MaterialDesignGlyphs(0xF03ED)

    /**
     * 󰏮 (0xF03EE)
     */
    public data object PENCIL_LOCK : MaterialDesignGlyphs(0xF03EE)

    /**
     * 󰏯 (0xF03EF)
     */
    public data object PENCIL_OFF : MaterialDesignGlyphs(0xF03EF)

    /**
     * 󰏰 (0xF03F0)
     */
    public data object PERCENT : MaterialDesignGlyphs(0xF03F0)

    /**
     * 󰏱 (0xF03F1)
     */
    public data object MORTAR_PESTLE_PLUS : MaterialDesignGlyphs(0xF03F1)

    /**
     * 󰏲 (0xF03F2)
     */
    public data object PHONE : MaterialDesignGlyphs(0xF03F2)

    /**
     * 󰏳 (0xF03F3)
     */
    public data object PHONE_BLUETOOTH : MaterialDesignGlyphs(0xF03F3)

    /**
     * 󰏴 (0xF03F4)
     */
    public data object PHONE_FORWARD : MaterialDesignGlyphs(0xF03F4)

    /**
     * 󰏵 (0xF03F5)
     */
    public data object PHONE_HANGUP : MaterialDesignGlyphs(0xF03F5)

    /**
     * 󰏶 (0xF03F6)
     */
    public data object PHONE_IN_TALK : MaterialDesignGlyphs(0xF03F6)

    /**
     * 󰏷 (0xF03F7)
     */
    public data object PHONE_INCOMING : MaterialDesignGlyphs(0xF03F7)

    /**
     * 󰏸 (0xF03F8)
     */
    public data object PHONE_LOCK : MaterialDesignGlyphs(0xF03F8)

    /**
     * 󰏹 (0xF03F9)
     */
    public data object PHONE_LOG : MaterialDesignGlyphs(0xF03F9)

    /**
     * 󰏺 (0xF03FA)
     */
    public data object PHONE_MISSED : MaterialDesignGlyphs(0xF03FA)

    /**
     * 󰏻 (0xF03FB)
     */
    public data object PHONE_OUTGOING : MaterialDesignGlyphs(0xF03FB)

    /**
     * 󰏼 (0xF03FC)
     */
    public data object PHONE_PAUSED : MaterialDesignGlyphs(0xF03FC)

    /**
     * 󰏽 (0xF03FD)
     */
    public data object PHONE_SETTINGS : MaterialDesignGlyphs(0xF03FD)

    /**
     * 󰏾 (0xF03FE)
     */
    public data object PHONE_VOIP : MaterialDesignGlyphs(0xF03FE)

    /**
     * 󰏿 (0xF03FF)
     */
    public data object PI : MaterialDesignGlyphs(0xF03FF)

    /**
     * 󰐀 (0xF0400)
     */
    public data object PI_BOX : MaterialDesignGlyphs(0xF0400)

    /**
     * 󰐁 (0xF0401)
     */
    public data object PIG : MaterialDesignGlyphs(0xF0401)

    /**
     * 󰐂 (0xF0402)
     */
    public data object PILL : MaterialDesignGlyphs(0xF0402)

    /**
     * 󰐃 (0xF0403)
     */
    public data object PIN : MaterialDesignGlyphs(0xF0403)

    /**
     * 󰐄 (0xF0404)
     */
    public data object PIN_OFF : MaterialDesignGlyphs(0xF0404)

    /**
     * 󰐅 (0xF0405)
     */
    public data object PINE_TREE : MaterialDesignGlyphs(0xF0405)

    /**
     * 󰐆 (0xF0406)
     */
    public data object PINE_TREE_BOX : MaterialDesignGlyphs(0xF0406)

    /**
     * 󰐇 (0xF0407)
     */
    public data object PINTEREST : MaterialDesignGlyphs(0xF0407)

    /**
     * 󰐈 (0xF0408)
     */
    public data object CONTACTLESS_PAYMENT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0408)

    /**
     * 󰐉 (0xF0409)
     */
    public data object PIZZA : MaterialDesignGlyphs(0xF0409)

    /**
     * 󰐊 (0xF040A)
     */
    public data object PLAY : MaterialDesignGlyphs(0xF040A)

    /**
     * 󰐋 (0xF040B)
     */
    public data object PLAY_BOX_OUTLINE : MaterialDesignGlyphs(0xF040B)

    /**
     * 󰐌 (0xF040C)
     */
    public data object PLAY_CIRCLE : MaterialDesignGlyphs(0xF040C)

    /**
     * 󰐍 (0xF040D)
     */
    public data object PLAY_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF040D)

    /**
     * 󰐎 (0xF040E)
     */
    public data object PLAY_PAUSE : MaterialDesignGlyphs(0xF040E)

    /**
     * 󰐏 (0xF040F)
     */
    public data object PLAY_PROTECTED_CONTENT : MaterialDesignGlyphs(0xF040F)

    /**
     * 󰐐 (0xF0410)
     */
    public data object PLAYLIST_MINUS : MaterialDesignGlyphs(0xF0410)

    /**
     * 󰐑 (0xF0411)
     */
    public data object PLAYLIST_PLAY : MaterialDesignGlyphs(0xF0411)

    /**
     * 󰐒 (0xF0412)
     */
    public data object PLAYLIST_PLUS : MaterialDesignGlyphs(0xF0412)

    /**
     * 󰐓 (0xF0413)
     */
    public data object PLAYLIST_REMOVE : MaterialDesignGlyphs(0xF0413)

    /**
     * 󰐔 (0xF0414)
     */
    public data object SONY_PLAYSTATION : MaterialDesignGlyphs(0xF0414)

    /**
     * 󰐕 (0xF0415)
     */
    public data object PLUS : MaterialDesignGlyphs(0xF0415)

    /**
     * 󰐖 (0xF0416)
     */
    public data object PLUS_BOX : MaterialDesignGlyphs(0xF0416)

    /**
     * 󰐗 (0xF0417)
     */
    public data object PLUS_CIRCLE : MaterialDesignGlyphs(0xF0417)

    /**
     * 󰐘 (0xF0418)
     */
    public data object PLUS_CIRCLE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0418)

    /**
     * 󰐙 (0xF0419)
     */
    public data object PLUS_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0419)

    /**
     * 󰐚 (0xF041A)
     */
    public data object PLUS_NETWORK : MaterialDesignGlyphs(0xF041A)

    /**
     * 󰐛 (0xF041B)
     */
    public data object SLEDDING : MaterialDesignGlyphs(0xF041B)

    /**
     * 󰐜 (0xF041C)
     */
    public data object WALL_SCONCE_FLAT_VARIANT : MaterialDesignGlyphs(0xF041C)

    /**
     * 󰐝 (0xF041D)
     */
    public data object POKEBALL : MaterialDesignGlyphs(0xF041D)

    /**
     * 󰐞 (0xF041E)
     */
    public data object POLAROID : MaterialDesignGlyphs(0xF041E)

    /**
     * 󰐟 (0xF041F)
     */
    public data object POLL : MaterialDesignGlyphs(0xF041F)

    /**
     * 󰐠 (0xF0420)
     */
    public data object ACCOUNT_EYE : MaterialDesignGlyphs(0xF0420)

    /**
     * 󰐡 (0xF0421)
     */
    public data object POLYMER : MaterialDesignGlyphs(0xF0421)

    /**
     * 󰐢 (0xF0422)
     */
    public data object POPCORN : MaterialDesignGlyphs(0xF0422)

    /**
     * 󰐣 (0xF0423)
     */
    public data object POUND : MaterialDesignGlyphs(0xF0423)

    /**
     * 󰐤 (0xF0424)
     */
    public data object POUND_BOX : MaterialDesignGlyphs(0xF0424)

    /**
     * 󰐥 (0xF0425)
     */
    public data object POWER : MaterialDesignGlyphs(0xF0425)

    /**
     * 󰐦 (0xF0426)
     */
    public data object POWER_SETTINGS : MaterialDesignGlyphs(0xF0426)

    /**
     * 󰐧 (0xF0427)
     */
    public data object POWER_SOCKET : MaterialDesignGlyphs(0xF0427)

    /**
     * 󰐨 (0xF0428)
     */
    public data object PRESENTATION : MaterialDesignGlyphs(0xF0428)

    /**
     * 󰐩 (0xF0429)
     */
    public data object PRESENTATION_PLAY : MaterialDesignGlyphs(0xF0429)

    /**
     * 󰐪 (0xF042A)
     */
    public data object PRINTER : MaterialDesignGlyphs(0xF042A)

    /**
     * 󰐫 (0xF042B)
     */
    public data object PRINTER_3D : MaterialDesignGlyphs(0xF042B)

    /**
     * 󰐬 (0xF042C)
     */
    public data object PRINTER_ALERT : MaterialDesignGlyphs(0xF042C)

    /**
     * 󰐭 (0xF042D)
     */
    public data object PROFESSIONAL_HEXAGON : MaterialDesignGlyphs(0xF042D)

    /**
     * 󰐮 (0xF042E)
     */
    public data object PROJECTOR : MaterialDesignGlyphs(0xF042E)

    /**
     * 󰐯 (0xF042F)
     */
    public data object PROJECTOR_SCREEN : MaterialDesignGlyphs(0xF042F)

    /**
     * 󰐰 (0xF0430)
     */
    public data object PULSE : MaterialDesignGlyphs(0xF0430)

    /**
     * 󰐱 (0xF0431)
     */
    public data object PUZZLE : MaterialDesignGlyphs(0xF0431)

    /**
     * 󰐲 (0xF0432)
     */
    public data object QRCODE : MaterialDesignGlyphs(0xF0432)

    /**
     * 󰐳 (0xF0433)
     */
    public data object QRCODE_SCAN : MaterialDesignGlyphs(0xF0433)

    /**
     * 󰐴 (0xF0434)
     */
    public data object QUADCOPTER : MaterialDesignGlyphs(0xF0434)

    /**
     * 󰐵 (0xF0435)
     */
    public data object QUALITY_HIGH : MaterialDesignGlyphs(0xF0435)

    /**
     * 󰐶 (0xF0436)
     */
    public data object BOOK_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0436)

    /**
     * 󰐷 (0xF0437)
     */
    public data object RADAR : MaterialDesignGlyphs(0xF0437)

    /**
     * 󰐸 (0xF0438)
     */
    public data object RADIATOR : MaterialDesignGlyphs(0xF0438)

    /**
     * 󰐹 (0xF0439)
     */
    public data object RADIO : MaterialDesignGlyphs(0xF0439)

    /**
     * 󰐺 (0xF043A)
     */
    public data object RADIO_HANDHELD : MaterialDesignGlyphs(0xF043A)

    /**
     * 󰐻 (0xF043B)
     */
    public data object RADIO_TOWER : MaterialDesignGlyphs(0xF043B)

    /**
     * 󰐼 (0xF043C)
     */
    public data object RADIOACTIVE : MaterialDesignGlyphs(0xF043C)

    /**
     * 󰐾 (0xF043E)
     */
    public data object RADIOBOX_MARKED : MaterialDesignGlyphs(0xF043E)

    /**
     * 󰐿 (0xF043F)
     */
    public data object RASPBERRY_PI : MaterialDesignGlyphs(0xF043F)

    /**
     * 󰑀 (0xF0440)
     */
    public data object RAY_END : MaterialDesignGlyphs(0xF0440)

    /**
     * 󰑁 (0xF0441)
     */
    public data object RAY_END_ARROW : MaterialDesignGlyphs(0xF0441)

    /**
     * 󰑂 (0xF0442)
     */
    public data object RAY_START : MaterialDesignGlyphs(0xF0442)

    /**
     * 󰑃 (0xF0443)
     */
    public data object RAY_START_ARROW : MaterialDesignGlyphs(0xF0443)

    /**
     * 󰑄 (0xF0444)
     */
    public data object RAY_START_END : MaterialDesignGlyphs(0xF0444)

    /**
     * 󰑅 (0xF0445)
     */
    public data object RAY_VERTEX : MaterialDesignGlyphs(0xF0445)

    /**
     * 󰑆 (0xF0446)
     */
    public data object LASTPASS : MaterialDesignGlyphs(0xF0446)

    /**
     * 󰑇 (0xF0447)
     */
    public data object READ : MaterialDesignGlyphs(0xF0447)

    /**
     * 󰑈 (0xF0448)
     */
    public data object YOUTUBE_TV : MaterialDesignGlyphs(0xF0448)

    /**
     * 󰑉 (0xF0449)
     */
    public data object RECEIPT : MaterialDesignGlyphs(0xF0449)

    /**
     * 󰑊 (0xF044A)
     */
    public data object RECORD : MaterialDesignGlyphs(0xF044A)

    /**
     * 󰑋 (0xF044B)
     */
    public data object RECORD_REC : MaterialDesignGlyphs(0xF044B)

    /**
     * 󰑌 (0xF044C)
     */
    public data object RECYCLE : MaterialDesignGlyphs(0xF044C)

    /**
     * 󰑍 (0xF044D)
     */
    public data object REDDIT : MaterialDesignGlyphs(0xF044D)

    /**
     * 󰑎 (0xF044E)
     */
    public data object REDO : MaterialDesignGlyphs(0xF044E)

    /**
     * 󰑏 (0xF044F)
     */
    public data object REDO_VARIANT : MaterialDesignGlyphs(0xF044F)

    /**
     * 󰑐 (0xF0450)
     */
    public data object REFRESH : MaterialDesignGlyphs(0xF0450)

    /**
     * 󰑑 (0xF0451)
     */
    public data object REGEX : MaterialDesignGlyphs(0xF0451)

    /**
     * 󰑒 (0xF0452)
     */
    public data object RELATIVE_SCALE : MaterialDesignGlyphs(0xF0452)

    /**
     * 󰑓 (0xF0453)
     */
    public data object RELOAD : MaterialDesignGlyphs(0xF0453)

    /**
     * 󰑔 (0xF0454)
     */
    public data object REMOTE : MaterialDesignGlyphs(0xF0454)

    /**
     * 󰑕 (0xF0455)
     */
    public data object RENAME_BOX : MaterialDesignGlyphs(0xF0455)

    /**
     * 󰑖 (0xF0456)
     */
    public data object REPEAT : MaterialDesignGlyphs(0xF0456)

    /**
     * 󰑗 (0xF0457)
     */
    public data object REPEAT_OFF : MaterialDesignGlyphs(0xF0457)

    /**
     * 󰑘 (0xF0458)
     */
    public data object REPEAT_ONCE : MaterialDesignGlyphs(0xF0458)

    /**
     * 󰑙 (0xF0459)
     */
    public data object REPLAY : MaterialDesignGlyphs(0xF0459)

    /**
     * 󰑚 (0xF045A)
     */
    public data object REPLY : MaterialDesignGlyphs(0xF045A)

    /**
     * 󰑛 (0xF045B)
     */
    public data object REPLY_ALL : MaterialDesignGlyphs(0xF045B)

    /**
     * 󰑜 (0xF045C)
     */
    public data object REPRODUCTION : MaterialDesignGlyphs(0xF045C)

    /**
     * 󰑝 (0xF045D)
     */
    public data object RESIZE_BOTTOM_RIGHT : MaterialDesignGlyphs(0xF045D)

    /**
     * 󰑞 (0xF045E)
     */
    public data object RESPONSIVE : MaterialDesignGlyphs(0xF045E)

    /**
     * 󰑟 (0xF045F)
     */
    public data object REWIND : MaterialDesignGlyphs(0xF045F)

    /**
     * 󰑠 (0xF0460)
     */
    public data object RIBBON : MaterialDesignGlyphs(0xF0460)

    /**
     * 󰑡 (0xF0461)
     */
    public data object ROAD : MaterialDesignGlyphs(0xF0461)

    /**
     * 󰑢 (0xF0462)
     */
    public data object ROAD_VARIANT : MaterialDesignGlyphs(0xF0462)

    /**
     * 󰑣 (0xF0463)
     */
    public data object ROCKET : MaterialDesignGlyphs(0xF0463)

    /**
     * 󰑤 (0xF0464)
     */
    public data object ROTATE_3D_VARIANT : MaterialDesignGlyphs(0xF0464)

    /**
     * 󰑥 (0xF0465)
     */
    public data object ROTATE_LEFT : MaterialDesignGlyphs(0xF0465)

    /**
     * 󰑦 (0xF0466)
     */
    public data object ROTATE_LEFT_VARIANT : MaterialDesignGlyphs(0xF0466)

    /**
     * 󰑧 (0xF0467)
     */
    public data object ROTATE_RIGHT : MaterialDesignGlyphs(0xF0467)

    /**
     * 󰑨 (0xF0468)
     */
    public data object ROTATE_RIGHT_VARIANT : MaterialDesignGlyphs(0xF0468)

    /**
     * 󰑩 (0xF0469)
     */
    public data object ROUTER_WIRELESS : MaterialDesignGlyphs(0xF0469)

    /**
     * 󰑪 (0xF046A)
     */
    public data object ROUTES : MaterialDesignGlyphs(0xF046A)

    /**
     * 󰑫 (0xF046B)
     */
    public data object RSS : MaterialDesignGlyphs(0xF046B)

    /**
     * 󰑬 (0xF046C)
     */
    public data object RSS_BOX : MaterialDesignGlyphs(0xF046C)

    /**
     * 󰑭 (0xF046D)
     */
    public data object RULER : MaterialDesignGlyphs(0xF046D)

    /**
     * 󰑮 (0xF046E)
     */
    public data object RUN_FAST : MaterialDesignGlyphs(0xF046E)

    /**
     * 󰑯 (0xF046F)
     */
    public data object SALE : MaterialDesignGlyphs(0xF046F)

    /**
     * 󰑰 (0xF0470)
     */
    public data object SATELLITE : MaterialDesignGlyphs(0xF0470)

    /**
     * 󰑱 (0xF0471)
     */
    public data object SATELLITE_VARIANT : MaterialDesignGlyphs(0xF0471)

    /**
     * 󰑲 (0xF0472)
     */
    public data object SCALE : MaterialDesignGlyphs(0xF0472)

    /**
     * 󰑳 (0xF0473)
     */
    public data object SCALE_BATHROOM : MaterialDesignGlyphs(0xF0473)

    /**
     * 󰑴 (0xF0474)
     */
    public data object SCHOOL : MaterialDesignGlyphs(0xF0474)

    /**
     * 󰑵 (0xF0475)
     */
    public data object SCREEN_ROTATION : MaterialDesignGlyphs(0xF0475)

    /**
     * 󰑶 (0xF0476)
     */
    public data object SCREWDRIVER : MaterialDesignGlyphs(0xF0476)

    /**
     * 󰑷 (0xF0477)
     */
    public data object SCRIPT_OUTLINE : MaterialDesignGlyphs(0xF0477)

    /**
     * 󰑸 (0xF0478)
     */
    public data object SCREEN_ROTATION_LOCK : MaterialDesignGlyphs(0xF0478)

    /**
     * 󰑹 (0xF0479)
     */
    public data object SD : MaterialDesignGlyphs(0xF0479)

    /**
     * 󰑺 (0xF047A)
     */
    public data object SEAL : MaterialDesignGlyphs(0xF047A)

    /**
     * 󰑻 (0xF047B)
     */
    public data object SEAT_FLAT : MaterialDesignGlyphs(0xF047B)

    /**
     * 󰑼 (0xF047C)
     */
    public data object SEAT_FLAT_ANGLED : MaterialDesignGlyphs(0xF047C)

    /**
     * 󰑽 (0xF047D)
     */
    public data object SEAT_INDIVIDUAL_SUITE : MaterialDesignGlyphs(0xF047D)

    /**
     * 󰑾 (0xF047E)
     */
    public data object SEAT_LEGROOM_EXTRA : MaterialDesignGlyphs(0xF047E)

    /**
     * 󰑿 (0xF047F)
     */
    public data object SEAT_LEGROOM_NORMAL : MaterialDesignGlyphs(0xF047F)

    /**
     * 󰒀 (0xF0480)
     */
    public data object SEAT_LEGROOM_REDUCED : MaterialDesignGlyphs(0xF0480)

    /**
     * 󰒁 (0xF0481)
     */
    public data object SEAT_RECLINE_EXTRA : MaterialDesignGlyphs(0xF0481)

    /**
     * 󰒂 (0xF0482)
     */
    public data object SEAT_RECLINE_NORMAL : MaterialDesignGlyphs(0xF0482)

    /**
     * 󰒃 (0xF0483)
     */
    public data object SECURITY : MaterialDesignGlyphs(0xF0483)

    /**
     * 󰒄 (0xF0484)
     */
    public data object SECURITY_NETWORK : MaterialDesignGlyphs(0xF0484)

    /**
     * 󰒅 (0xF0485)
     */
    public data object SELECT : MaterialDesignGlyphs(0xF0485)

    /**
     * 󰒆 (0xF0486)
     */
    public data object SELECT_ALL : MaterialDesignGlyphs(0xF0486)

    /**
     * 󰒇 (0xF0487)
     */
    public data object SELECT_INVERSE : MaterialDesignGlyphs(0xF0487)

    /**
     * 󰒈 (0xF0488)
     */
    public data object SELECT_OFF : MaterialDesignGlyphs(0xF0488)

    /**
     * 󰒉 (0xF0489)
     */
    public data object SELECTION : MaterialDesignGlyphs(0xF0489)

    /**
     * 󰒊 (0xF048A)
     */
    public data object SEND : MaterialDesignGlyphs(0xF048A)

    /**
     * 󰒋 (0xF048B)
     */
    public data object SERVER : MaterialDesignGlyphs(0xF048B)

    /**
     * 󰒌 (0xF048C)
     */
    public data object SERVER_MINUS : MaterialDesignGlyphs(0xF048C)

    /**
     * 󰒍 (0xF048D)
     */
    public data object SERVER_NETWORK : MaterialDesignGlyphs(0xF048D)

    /**
     * 󰒎 (0xF048E)
     */
    public data object SERVER_NETWORK_OFF : MaterialDesignGlyphs(0xF048E)

    /**
     * 󰒏 (0xF048F)
     */
    public data object SERVER_OFF : MaterialDesignGlyphs(0xF048F)

    /**
     * 󰒐 (0xF0490)
     */
    public data object SERVER_PLUS : MaterialDesignGlyphs(0xF0490)

    /**
     * 󰒑 (0xF0491)
     */
    public data object SERVER_REMOVE : MaterialDesignGlyphs(0xF0491)

    /**
     * 󰒒 (0xF0492)
     */
    public data object SERVER_SECURITY : MaterialDesignGlyphs(0xF0492)

    /**
     * 󰒓 (0xF0493)
     */
    public data object COG : MaterialDesignGlyphs(0xF0493)

    /**
     * 󰒔 (0xF0494)
     */
    public data object COG_BOX : MaterialDesignGlyphs(0xF0494)

    /**
     * 󰒕 (0xF0495)
     */
    public data object SHAPE_PLUS : MaterialDesignGlyphs(0xF0495)

    /**
     * 󰒖 (0xF0496)
     */
    public data object SHARE : MaterialDesignGlyphs(0xF0496)

    /**
     * 󰒗 (0xF0497)
     */
    public data object SHARE_VARIANT : MaterialDesignGlyphs(0xF0497)

    /**
     * 󰒘 (0xF0498)
     */
    public data object SHIELD : MaterialDesignGlyphs(0xF0498)

    /**
     * 󰒙 (0xF0499)
     */
    public data object SHIELD_OUTLINE : MaterialDesignGlyphs(0xF0499)

    /**
     * 󰒚 (0xF049A)
     */
    public data object SHOPPING : MaterialDesignGlyphs(0xF049A)

    /**
     * 󰒛 (0xF049B)
     */
    public data object SHOPPING_MUSIC : MaterialDesignGlyphs(0xF049B)

    /**
     * 󰒜 (0xF049C)
     */
    public data object SHREDDER : MaterialDesignGlyphs(0xF049C)

    /**
     * 󰒝 (0xF049D)
     */
    public data object SHUFFLE : MaterialDesignGlyphs(0xF049D)

    /**
     * 󰒞 (0xF049E)
     */
    public data object SHUFFLE_DISABLED : MaterialDesignGlyphs(0xF049E)

    /**
     * 󰒟 (0xF049F)
     */
    public data object SHUFFLE_VARIANT : MaterialDesignGlyphs(0xF049F)

    /**
     * 󰒠 (0xF04A0)
     */
    public data object SIGMA : MaterialDesignGlyphs(0xF04A0)

    /**
     * 󰒡 (0xF04A1)
     */
    public data object SIGN_CAUTION : MaterialDesignGlyphs(0xF04A1)

    /**
     * 󰒢 (0xF04A2)
     */
    public data object SIGNAL : MaterialDesignGlyphs(0xF04A2)

    /**
     * 󰒣 (0xF04A3)
     */
    public data object SILVERWARE : MaterialDesignGlyphs(0xF04A3)

    /**
     * 󰒤 (0xF04A4)
     */
    public data object SILVERWARE_FORK : MaterialDesignGlyphs(0xF04A4)

    /**
     * 󰒥 (0xF04A5)
     */
    public data object SILVERWARE_SPOON : MaterialDesignGlyphs(0xF04A5)

    /**
     * 󰒦 (0xF04A6)
     */
    public data object SILVERWARE_VARIANT : MaterialDesignGlyphs(0xF04A6)

    /**
     * 󰒧 (0xF04A7)
     */
    public data object SIM : MaterialDesignGlyphs(0xF04A7)

    /**
     * 󰒨 (0xF04A8)
     */
    public data object SIM_ALERT : MaterialDesignGlyphs(0xF04A8)

    /**
     * 󰒩 (0xF04A9)
     */
    public data object SIM_OFF : MaterialDesignGlyphs(0xF04A9)

    /**
     * 󰒪 (0xF04AA)
     */
    public data object SITEMAP : MaterialDesignGlyphs(0xF04AA)

    /**
     * 󰒫 (0xF04AB)
     */
    public data object SKIP_BACKWARD : MaterialDesignGlyphs(0xF04AB)

    /**
     * 󰒬 (0xF04AC)
     */
    public data object SKIP_FORWARD : MaterialDesignGlyphs(0xF04AC)

    /**
     * 󰒭 (0xF04AD)
     */
    public data object SKIP_NEXT : MaterialDesignGlyphs(0xF04AD)

    /**
     * 󰒮 (0xF04AE)
     */
    public data object SKIP_PREVIOUS : MaterialDesignGlyphs(0xF04AE)

    /**
     * 󰒯 (0xF04AF)
     */
    public data object SKYPE : MaterialDesignGlyphs(0xF04AF)

    /**
     * 󰒰 (0xF04B0)
     */
    public data object SKYPE_BUSINESS : MaterialDesignGlyphs(0xF04B0)

    /**
     * 󰒱 (0xF04B1)
     */
    public data object SLACK : MaterialDesignGlyphs(0xF04B1)

    /**
     * 󰒲 (0xF04B2)
     */
    public data object SLEEP : MaterialDesignGlyphs(0xF04B2)

    /**
     * 󰒳 (0xF04B3)
     */
    public data object SLEEP_OFF : MaterialDesignGlyphs(0xF04B3)

    /**
     * 󰒴 (0xF04B4)
     */
    public data object SMOKING : MaterialDesignGlyphs(0xF04B4)

    /**
     * 󰒵 (0xF04B5)
     */
    public data object SMOKING_OFF : MaterialDesignGlyphs(0xF04B5)

    /**
     * 󰒶 (0xF04B6)
     */
    public data object SNAPCHAT : MaterialDesignGlyphs(0xF04B6)

    /**
     * 󰒷 (0xF04B7)
     */
    public data object SNOWMAN : MaterialDesignGlyphs(0xF04B7)

    /**
     * 󰒸 (0xF04B8)
     */
    public data object SOCCER : MaterialDesignGlyphs(0xF04B8)

    /**
     * 󰒹 (0xF04B9)
     */
    public data object SOFA : MaterialDesignGlyphs(0xF04B9)

    /**
     * 󰒺 (0xF04BA)
     */
    public data object SORT : MaterialDesignGlyphs(0xF04BA)

    /**
     * 󰒻 (0xF04BB)
     */
    public data object SORT_ALPHABETICAL_VARIANT : MaterialDesignGlyphs(0xF04BB)

    /**
     * 󰒼 (0xF04BC)
     */
    public data object SORT_ASCENDING : MaterialDesignGlyphs(0xF04BC)

    /**
     * 󰒽 (0xF04BD)
     */
    public data object SORT_DESCENDING : MaterialDesignGlyphs(0xF04BD)

    /**
     * 󰒾 (0xF04BE)
     */
    public data object SORT_NUMERIC_VARIANT : MaterialDesignGlyphs(0xF04BE)

    /**
     * 󰒿 (0xF04BF)
     */
    public data object SORT_VARIANT : MaterialDesignGlyphs(0xF04BF)

    /**
     * 󰓀 (0xF04C0)
     */
    public data object SOUNDCLOUD : MaterialDesignGlyphs(0xF04C0)

    /**
     * 󰓁 (0xF04C1)
     */
    public data object SOURCE_FORK : MaterialDesignGlyphs(0xF04C1)

    /**
     * 󰓂 (0xF04C2)
     */
    public data object SOURCE_PULL : MaterialDesignGlyphs(0xF04C2)

    /**
     * 󰓃 (0xF04C3)
     */
    public data object SPEAKER : MaterialDesignGlyphs(0xF04C3)

    /**
     * 󰓄 (0xF04C4)
     */
    public data object SPEAKER_OFF : MaterialDesignGlyphs(0xF04C4)

    /**
     * 󰓅 (0xF04C5)
     */
    public data object SPEEDOMETER : MaterialDesignGlyphs(0xF04C5)

    /**
     * 󰓆 (0xF04C6)
     */
    public data object SPELLCHECK : MaterialDesignGlyphs(0xF04C6)

    /**
     * 󰓇 (0xF04C7)
     */
    public data object SPOTIFY : MaterialDesignGlyphs(0xF04C7)

    /**
     * 󰓈 (0xF04C8)
     */
    public data object SPOTLIGHT : MaterialDesignGlyphs(0xF04C8)

    /**
     * 󰓉 (0xF04C9)
     */
    public data object SPOTLIGHT_BEAM : MaterialDesignGlyphs(0xF04C9)

    /**
     * 󰓊 (0xF04CA)
     */
    public data object BOOK_REMOVE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF04CA)

    /**
     * 󰓋 (0xF04CB)
     */
    public data object ACCOUNT_SWITCH_OUTLINE : MaterialDesignGlyphs(0xF04CB)

    /**
     * 󰓌 (0xF04CC)
     */
    public data object STACK_OVERFLOW : MaterialDesignGlyphs(0xF04CC)

    /**
     * 󰓍 (0xF04CD)
     */
    public data object STAIRS : MaterialDesignGlyphs(0xF04CD)

    /**
     * 󰓎 (0xF04CE)
     */
    public data object STAR : MaterialDesignGlyphs(0xF04CE)

    /**
     * 󰓏 (0xF04CF)
     */
    public data object STAR_CIRCLE : MaterialDesignGlyphs(0xF04CF)

    /**
     * 󰓐 (0xF04D0)
     */
    public data object STAR_HALF_FULL : MaterialDesignGlyphs(0xF04D0)

    /**
     * 󰓑 (0xF04D1)
     */
    public data object STAR_OFF : MaterialDesignGlyphs(0xF04D1)

    /**
     * 󰓒 (0xF04D2)
     */
    public data object STAR_OUTLINE : MaterialDesignGlyphs(0xF04D2)

    /**
     * 󰓓 (0xF04D3)
     */
    public data object STEAM : MaterialDesignGlyphs(0xF04D3)

    /**
     * 󰓔 (0xF04D4)
     */
    public data object STEERING : MaterialDesignGlyphs(0xF04D4)

    /**
     * 󰓕 (0xF04D5)
     */
    public data object STEP_BACKWARD : MaterialDesignGlyphs(0xF04D5)

    /**
     * 󰓖 (0xF04D6)
     */
    public data object STEP_BACKWARD_2 : MaterialDesignGlyphs(0xF04D6)

    /**
     * 󰓗 (0xF04D7)
     */
    public data object STEP_FORWARD : MaterialDesignGlyphs(0xF04D7)

    /**
     * 󰓘 (0xF04D8)
     */
    public data object STEP_FORWARD_2 : MaterialDesignGlyphs(0xF04D8)

    /**
     * 󰓙 (0xF04D9)
     */
    public data object STETHOSCOPE : MaterialDesignGlyphs(0xF04D9)

    /**
     * 󰓚 (0xF04DA)
     */
    public data object STOCKING : MaterialDesignGlyphs(0xF04DA)

    /**
     * 󰓛 (0xF04DB)
     */
    public data object STOP : MaterialDesignGlyphs(0xF04DB)

    /**
     * 󰓜 (0xF04DC)
     */
    public data object STORE : MaterialDesignGlyphs(0xF04DC)

    /**
     * 󰓝 (0xF04DD)
     */
    public data object STORE_24_HOUR : MaterialDesignGlyphs(0xF04DD)

    /**
     * 󰓞 (0xF04DE)
     */
    public data object STOVE : MaterialDesignGlyphs(0xF04DE)

    /**
     * 󰓟 (0xF04DF)
     */
    public data object SUBWAY_VARIANT : MaterialDesignGlyphs(0xF04DF)

    /**
     * 󰓠 (0xF04E0)
     */
    public data object SUNGLASSES : MaterialDesignGlyphs(0xF04E0)

    /**
     * 󰓡 (0xF04E1)
     */
    public data object SWAP_HORIZONTAL : MaterialDesignGlyphs(0xF04E1)

    /**
     * 󰓢 (0xF04E2)
     */
    public data object SWAP_VERTICAL : MaterialDesignGlyphs(0xF04E2)

    /**
     * 󰓣 (0xF04E3)
     */
    public data object SWIM : MaterialDesignGlyphs(0xF04E3)

    /**
     * 󰓤 (0xF04E4)
     */
    public data object SWITCH : MaterialDesignGlyphs(0xF04E4)

    /**
     * 󰓥 (0xF04E5)
     */
    public data object SWORD : MaterialDesignGlyphs(0xF04E5)

    /**
     * 󰓦 (0xF04E6)
     */
    public data object SYNC : MaterialDesignGlyphs(0xF04E6)

    /**
     * 󰓧 (0xF04E7)
     */
    public data object SYNC_ALERT : MaterialDesignGlyphs(0xF04E7)

    /**
     * 󰓨 (0xF04E8)
     */
    public data object SYNC_OFF : MaterialDesignGlyphs(0xF04E8)

    /**
     * 󰓩 (0xF04E9)
     */
    public data object TAB : MaterialDesignGlyphs(0xF04E9)

    /**
     * 󰓪 (0xF04EA)
     */
    public data object TAB_UNSELECTED : MaterialDesignGlyphs(0xF04EA)

    /**
     * 󰓫 (0xF04EB)
     */
    public data object TABLE : MaterialDesignGlyphs(0xF04EB)

    /**
     * 󰓬 (0xF04EC)
     */
    public data object TABLE_COLUMN_PLUS_AFTER : MaterialDesignGlyphs(0xF04EC)

    /**
     * 󰓭 (0xF04ED)
     */
    public data object TABLE_COLUMN_PLUS_BEFORE : MaterialDesignGlyphs(0xF04ED)

    /**
     * 󰓮 (0xF04EE)
     */
    public data object TABLE_COLUMN_REMOVE : MaterialDesignGlyphs(0xF04EE)

    /**
     * 󰓯 (0xF04EF)
     */
    public data object TABLE_COLUMN_WIDTH : MaterialDesignGlyphs(0xF04EF)

    /**
     * 󰓰 (0xF04F0)
     */
    public data object TABLE_EDIT : MaterialDesignGlyphs(0xF04F0)

    /**
     * 󰓱 (0xF04F1)
     */
    public data object TABLE_LARGE : MaterialDesignGlyphs(0xF04F1)

    /**
     * 󰓲 (0xF04F2)
     */
    public data object TABLE_ROW_HEIGHT : MaterialDesignGlyphs(0xF04F2)

    /**
     * 󰓳 (0xF04F3)
     */
    public data object TABLE_ROW_PLUS_AFTER : MaterialDesignGlyphs(0xF04F3)

    /**
     * 󰓴 (0xF04F4)
     */
    public data object TABLE_ROW_PLUS_BEFORE : MaterialDesignGlyphs(0xF04F4)

    /**
     * 󰓵 (0xF04F5)
     */
    public data object TABLE_ROW_REMOVE : MaterialDesignGlyphs(0xF04F5)

    /**
     * 󰓶 (0xF04F6)
     */
    public data object TABLET : MaterialDesignGlyphs(0xF04F6)

    /**
     * 󰓷 (0xF04F7)
     */
    public data object TABLET_ANDROID : MaterialDesignGlyphs(0xF04F7)

    /**
     * 󰓸 (0xF04F8)
     */
    public data object TANGRAM : MaterialDesignGlyphs(0xF04F8)

    /**
     * 󰓹 (0xF04F9)
     */
    public data object TAG : MaterialDesignGlyphs(0xF04F9)

    /**
     * 󰓺 (0xF04FA)
     */
    public data object TAG_FACES : MaterialDesignGlyphs(0xF04FA)

    /**
     * 󰓻 (0xF04FB)
     */
    public data object TAG_MULTIPLE : MaterialDesignGlyphs(0xF04FB)

    /**
     * 󰓼 (0xF04FC)
     */
    public data object TAG_OUTLINE : MaterialDesignGlyphs(0xF04FC)

    /**
     * 󰓽 (0xF04FD)
     */
    public data object TAG_TEXT_OUTLINE : MaterialDesignGlyphs(0xF04FD)

    /**
     * 󰓾 (0xF04FE)
     */
    public data object TARGET : MaterialDesignGlyphs(0xF04FE)

    /**
     * 󰓿 (0xF04FF)
     */
    public data object TAXI : MaterialDesignGlyphs(0xF04FF)

    /**
     * 󰔀 (0xF0500)
     */
    public data object TEAMVIEWER : MaterialDesignGlyphs(0xF0500)

    /**
     * 󰔁 (0xF0501)
     */
    public data object SKATEBOARDING : MaterialDesignGlyphs(0xF0501)

    /**
     * 󰔂 (0xF0502)
     */
    public data object TELEVISION : MaterialDesignGlyphs(0xF0502)

    /**
     * 󰔃 (0xF0503)
     */
    public data object TELEVISION_GUIDE : MaterialDesignGlyphs(0xF0503)

    /**
     * 󰔄 (0xF0504)
     */
    public data object TEMPERATURE_CELSIUS : MaterialDesignGlyphs(0xF0504)

    /**
     * 󰔅 (0xF0505)
     */
    public data object TEMPERATURE_FAHRENHEIT : MaterialDesignGlyphs(0xF0505)

    /**
     * 󰔆 (0xF0506)
     */
    public data object TEMPERATURE_KELVIN : MaterialDesignGlyphs(0xF0506)

    /**
     * 󰔇 (0xF0507)
     */
    public data object TENNIS_BALL : MaterialDesignGlyphs(0xF0507)

    /**
     * 󰔈 (0xF0508)
     */
    public data object TENT : MaterialDesignGlyphs(0xF0508)

    /**
     * 󰔊 (0xF050A)
     */
    public data object TEXT_TO_SPEECH : MaterialDesignGlyphs(0xF050A)

    /**
     * 󰔋 (0xF050B)
     */
    public data object TEXT_TO_SPEECH_OFF : MaterialDesignGlyphs(0xF050B)

    /**
     * 󰔌 (0xF050C)
     */
    public data object TEXTURE : MaterialDesignGlyphs(0xF050C)

    /**
     * 󰔍 (0xF050D)
     */
    public data object THEATER : MaterialDesignGlyphs(0xF050D)

    /**
     * 󰔎 (0xF050E)
     */
    public data object THEME_LIGHT_DARK : MaterialDesignGlyphs(0xF050E)

    /**
     * 󰔏 (0xF050F)
     */
    public data object THERMOMETER : MaterialDesignGlyphs(0xF050F)

    /**
     * 󰔐 (0xF0510)
     */
    public data object THERMOMETER_LINES : MaterialDesignGlyphs(0xF0510)

    /**
     * 󰔑 (0xF0511)
     */
    public data object THUMB_DOWN : MaterialDesignGlyphs(0xF0511)

    /**
     * 󰔒 (0xF0512)
     */
    public data object THUMB_DOWN_OUTLINE : MaterialDesignGlyphs(0xF0512)

    /**
     * 󰔓 (0xF0513)
     */
    public data object THUMB_UP : MaterialDesignGlyphs(0xF0513)

    /**
     * 󰔔 (0xF0514)
     */
    public data object THUMB_UP_OUTLINE : MaterialDesignGlyphs(0xF0514)

    /**
     * 󰔕 (0xF0515)
     */
    public data object THUMBS_UP_DOWN : MaterialDesignGlyphs(0xF0515)

    /**
     * 󰔖 (0xF0516)
     */
    public data object TICKET : MaterialDesignGlyphs(0xF0516)

    /**
     * 󰔗 (0xF0517)
     */
    public data object TICKET_ACCOUNT : MaterialDesignGlyphs(0xF0517)

    /**
     * 󰔘 (0xF0518)
     */
    public data object TICKET_CONFIRMATION : MaterialDesignGlyphs(0xF0518)

    /**
     * 󰔙 (0xF0519)
     */
    public data object TIE : MaterialDesignGlyphs(0xF0519)

    /**
     * 󰔚 (0xF051A)
     */
    public data object TIMELAPSE : MaterialDesignGlyphs(0xF051A)

    /**
     * 󰔛 (0xF051B)
     */
    public data object TIMER_OUTLINE : MaterialDesignGlyphs(0xF051B)

    /**
     * 󰔜 (0xF051C)
     */
    public data object TIMER_10 : MaterialDesignGlyphs(0xF051C)

    /**
     * 󰔝 (0xF051D)
     */
    public data object TIMER_3 : MaterialDesignGlyphs(0xF051D)

    /**
     * 󰔞 (0xF051E)
     */
    public data object TIMER_OFF_OUTLINE : MaterialDesignGlyphs(0xF051E)

    /**
     * 󰔟 (0xF051F)
     */
    public data object TIMER_SAND : MaterialDesignGlyphs(0xF051F)

    /**
     * 󰔠 (0xF0520)
     */
    public data object TIMETABLE : MaterialDesignGlyphs(0xF0520)

    /**
     * 󰔡 (0xF0521)
     */
    public data object TOGGLE_SWITCH : MaterialDesignGlyphs(0xF0521)

    /**
     * 󰔢 (0xF0522)
     */
    public data object TOGGLE_SWITCH_OFF : MaterialDesignGlyphs(0xF0522)

    /**
     * 󰔣 (0xF0523)
     */
    public data object TOOLTIP : MaterialDesignGlyphs(0xF0523)

    /**
     * 󰔤 (0xF0524)
     */
    public data object TOOLTIP_EDIT : MaterialDesignGlyphs(0xF0524)

    /**
     * 󰔥 (0xF0525)
     */
    public data object TOOLTIP_IMAGE : MaterialDesignGlyphs(0xF0525)

    /**
     * 󰔦 (0xF0526)
     */
    public data object TOOLTIP_OUTLINE : MaterialDesignGlyphs(0xF0526)

    /**
     * 󰔧 (0xF0527)
     */
    public data object TOOLTIP_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0527)

    /**
     * 󰔨 (0xF0528)
     */
    public data object TOOLTIP_TEXT : MaterialDesignGlyphs(0xF0528)

    /**
     * 󰔩 (0xF0529)
     */
    public data object TOOTH_OUTLINE : MaterialDesignGlyphs(0xF0529)

    /**
     * 󰔪 (0xF052A)
     */
    public data object CLOUD_REFRESH : MaterialDesignGlyphs(0xF052A)

    /**
     * 󰔫 (0xF052B)
     */
    public data object TRAFFIC_LIGHT : MaterialDesignGlyphs(0xF052B)

    /**
     * 󰔬 (0xF052C)
     */
    public data object TRAIN : MaterialDesignGlyphs(0xF052C)

    /**
     * 󰔭 (0xF052D)
     */
    public data object TRAM : MaterialDesignGlyphs(0xF052D)

    /**
     * 󰔮 (0xF052E)
     */
    public data object TRANSCRIBE : MaterialDesignGlyphs(0xF052E)

    /**
     * 󰔯 (0xF052F)
     */
    public data object TRANSCRIBE_CLOSE : MaterialDesignGlyphs(0xF052F)

    /**
     * 󰔰 (0xF0530)
     */
    public data object TRANSFER_RIGHT : MaterialDesignGlyphs(0xF0530)

    /**
     * 󰔱 (0xF0531)
     */
    public data object TREE : MaterialDesignGlyphs(0xF0531)

    /**
     * 󰔲 (0xF0532)
     */
    public data object TRELLO : MaterialDesignGlyphs(0xF0532)

    /**
     * 󰔳 (0xF0533)
     */
    public data object TRENDING_DOWN : MaterialDesignGlyphs(0xF0533)

    /**
     * 󰔴 (0xF0534)
     */
    public data object TRENDING_NEUTRAL : MaterialDesignGlyphs(0xF0534)

    /**
     * 󰔵 (0xF0535)
     */
    public data object TRENDING_UP : MaterialDesignGlyphs(0xF0535)

    /**
     * 󰔶 (0xF0536)
     */
    public data object TRIANGLE : MaterialDesignGlyphs(0xF0536)

    /**
     * 󰔷 (0xF0537)
     */
    public data object TRIANGLE_OUTLINE : MaterialDesignGlyphs(0xF0537)

    /**
     * 󰔸 (0xF0538)
     */
    public data object TROPHY : MaterialDesignGlyphs(0xF0538)

    /**
     * 󰔹 (0xF0539)
     */
    public data object TROPHY_AWARD : MaterialDesignGlyphs(0xF0539)

    /**
     * 󰔺 (0xF053A)
     */
    public data object TROPHY_OUTLINE : MaterialDesignGlyphs(0xF053A)

    /**
     * 󰔻 (0xF053B)
     */
    public data object TROPHY_VARIANT : MaterialDesignGlyphs(0xF053B)

    /**
     * 󰔼 (0xF053C)
     */
    public data object TROPHY_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF053C)

    /**
     * 󰔽 (0xF053D)
     */
    public data object TRUCK : MaterialDesignGlyphs(0xF053D)

    /**
     * 󰔾 (0xF053E)
     */
    public data object TRUCK_DELIVERY : MaterialDesignGlyphs(0xF053E)

    /**
     * 󰔿 (0xF053F)
     */
    public data object TSHIRT_CREW_OUTLINE : MaterialDesignGlyphs(0xF053F)

    /**
     * 󰕀 (0xF0540)
     */
    public data object TSHIRT_V_OUTLINE : MaterialDesignGlyphs(0xF0540)

    /**
     * 󰕁 (0xF0541)
     */
    public data object FILE_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF0541)

    /**
     * 󰕂 (0xF0542)
     */
    public data object FOLDER_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF0542)

    /**
     * 󰕃 (0xF0543)
     */
    public data object TWITCH : MaterialDesignGlyphs(0xF0543)

    /**
     * 󰕄 (0xF0544)
     */
    public data object TWITTER : MaterialDesignGlyphs(0xF0544)

    /**
     * 󰕅 (0xF0545)
     */
    public data object ORDER_NUMERIC_ASCENDING : MaterialDesignGlyphs(0xF0545)

    /**
     * 󰕆 (0xF0546)
     */
    public data object ORDER_NUMERIC_DESCENDING : MaterialDesignGlyphs(0xF0546)

    /**
     * 󰕇 (0xF0547)
     */
    public data object REPEAT_VARIANT : MaterialDesignGlyphs(0xF0547)

    /**
     * 󰕈 (0xF0548)
     */
    public data object UBUNTU : MaterialDesignGlyphs(0xF0548)

    /**
     * 󰕉 (0xF0549)
     */
    public data object UMBRACO : MaterialDesignGlyphs(0xF0549)

    /**
     * 󰕊 (0xF054A)
     */
    public data object UMBRELLA : MaterialDesignGlyphs(0xF054A)

    /**
     * 󰕋 (0xF054B)
     */
    public data object UMBRELLA_OUTLINE : MaterialDesignGlyphs(0xF054B)

    /**
     * 󰕌 (0xF054C)
     */
    public data object UNDO : MaterialDesignGlyphs(0xF054C)

    /**
     * 󰕍 (0xF054D)
     */
    public data object UNDO_VARIANT : MaterialDesignGlyphs(0xF054D)

    /**
     * 󰕎 (0xF054E)
     */
    public data object UNFOLD_LESS_HORIZONTAL : MaterialDesignGlyphs(0xF054E)

    /**
     * 󰕏 (0xF054F)
     */
    public data object UNFOLD_MORE_HORIZONTAL : MaterialDesignGlyphs(0xF054F)

    /**
     * 󰕐 (0xF0550)
     */
    public data object UNGROUP : MaterialDesignGlyphs(0xF0550)

    /**
     * 󰕑 (0xF0551)
     */
    public data object WEB_REMOVE : MaterialDesignGlyphs(0xF0551)

    /**
     * 󰕒 (0xF0552)
     */
    public data object UPLOAD : MaterialDesignGlyphs(0xF0552)

    /**
     * 󰕓 (0xF0553)
     */
    public data object USB : MaterialDesignGlyphs(0xF0553)

    /**
     * 󰕔 (0xF0554)
     */
    public data object VECTOR_ARRANGE_ABOVE : MaterialDesignGlyphs(0xF0554)

    /**
     * 󰕕 (0xF0555)
     */
    public data object VECTOR_ARRANGE_BELOW : MaterialDesignGlyphs(0xF0555)

    /**
     * 󰕖 (0xF0556)
     */
    public data object VECTOR_CIRCLE : MaterialDesignGlyphs(0xF0556)

    /**
     * 󰕗 (0xF0557)
     */
    public data object VECTOR_CIRCLE_VARIANT : MaterialDesignGlyphs(0xF0557)

    /**
     * 󰕘 (0xF0558)
     */
    public data object VECTOR_COMBINE : MaterialDesignGlyphs(0xF0558)

    /**
     * 󰕙 (0xF0559)
     */
    public data object VECTOR_CURVE : MaterialDesignGlyphs(0xF0559)

    /**
     * 󰕚 (0xF055A)
     */
    public data object VECTOR_DIFFERENCE : MaterialDesignGlyphs(0xF055A)

    /**
     * 󰕛 (0xF055B)
     */
    public data object VECTOR_DIFFERENCE_AB : MaterialDesignGlyphs(0xF055B)

    /**
     * 󰕜 (0xF055C)
     */
    public data object VECTOR_DIFFERENCE_BA : MaterialDesignGlyphs(0xF055C)

    /**
     * 󰕝 (0xF055D)
     */
    public data object VECTOR_INTERSECTION : MaterialDesignGlyphs(0xF055D)

    /**
     * 󰕞 (0xF055E)
     */
    public data object VECTOR_LINE : MaterialDesignGlyphs(0xF055E)

    /**
     * 󰕟 (0xF055F)
     */
    public data object VECTOR_POINT : MaterialDesignGlyphs(0xF055F)

    /**
     * 󰕠 (0xF0560)
     */
    public data object VECTOR_POLYGON : MaterialDesignGlyphs(0xF0560)

    /**
     * 󰕡 (0xF0561)
     */
    public data object VECTOR_POLYLINE : MaterialDesignGlyphs(0xF0561)

    /**
     * 󰕢 (0xF0562)
     */
    public data object VECTOR_SELECTION : MaterialDesignGlyphs(0xF0562)

    /**
     * 󰕣 (0xF0563)
     */
    public data object VECTOR_TRIANGLE : MaterialDesignGlyphs(0xF0563)

    /**
     * 󰕤 (0xF0564)
     */
    public data object VECTOR_UNION : MaterialDesignGlyphs(0xF0564)

    /**
     * 󰕥 (0xF0565)
     */
    public data object SHIELD_CHECK : MaterialDesignGlyphs(0xF0565)

    /**
     * 󰕦 (0xF0566)
     */
    public data object VIBRATE : MaterialDesignGlyphs(0xF0566)

    /**
     * 󰕧 (0xF0567)
     */
    public data object VIDEO : MaterialDesignGlyphs(0xF0567)

    /**
     * 󰕨 (0xF0568)
     */
    public data object VIDEO_OFF : MaterialDesignGlyphs(0xF0568)

    /**
     * 󰕩 (0xF0569)
     */
    public data object VIDEO_SWITCH : MaterialDesignGlyphs(0xF0569)

    /**
     * 󰕪 (0xF056A)
     */
    public data object VIEW_AGENDA : MaterialDesignGlyphs(0xF056A)

    /**
     * 󰕫 (0xF056B)
     */
    public data object VIEW_ARRAY : MaterialDesignGlyphs(0xF056B)

    /**
     * 󰕬 (0xF056C)
     */
    public data object VIEW_CAROUSEL : MaterialDesignGlyphs(0xF056C)

    /**
     * 󰕭 (0xF056D)
     */
    public data object VIEW_COLUMN : MaterialDesignGlyphs(0xF056D)

    /**
     * 󰕮 (0xF056E)
     */
    public data object VIEW_DASHBOARD : MaterialDesignGlyphs(0xF056E)

    /**
     * 󰕯 (0xF056F)
     */
    public data object VIEW_DAY : MaterialDesignGlyphs(0xF056F)

    /**
     * 󰕰 (0xF0570)
     */
    public data object VIEW_GRID : MaterialDesignGlyphs(0xF0570)

    /**
     * 󰕱 (0xF0571)
     */
    public data object VIEW_HEADLINE : MaterialDesignGlyphs(0xF0571)

    /**
     * 󰕲 (0xF0572)
     */
    public data object VIEW_LIST : MaterialDesignGlyphs(0xF0572)

    /**
     * 󰕳 (0xF0573)
     */
    public data object VIEW_MODULE : MaterialDesignGlyphs(0xF0573)

    /**
     * 󰕴 (0xF0574)
     */
    public data object VIEW_QUILT : MaterialDesignGlyphs(0xF0574)

    /**
     * 󰕵 (0xF0575)
     */
    public data object VIEW_STREAM : MaterialDesignGlyphs(0xF0575)

    /**
     * 󰕶 (0xF0576)
     */
    public data object VIEW_WEEK : MaterialDesignGlyphs(0xF0576)

    /**
     * 󰕷 (0xF0577)
     */
    public data object VIMEO : MaterialDesignGlyphs(0xF0577)

    /**
     * 󰕸 (0xF0578)
     */
    public data object BUFFET : MaterialDesignGlyphs(0xF0578)

    /**
     * 󰕹 (0xF0579)
     */
    public data object HANDS_PRAY : MaterialDesignGlyphs(0xF0579)

    /**
     * 󰕺 (0xF057A)
     */
    public data object CREDIT_CARD_WIRELESS_OFF : MaterialDesignGlyphs(0xF057A)

    /**
     * 󰕻 (0xF057B)
     */
    public data object CREDIT_CARD_WIRELESS_OFF_OUTLINE : MaterialDesignGlyphs(0xF057B)

    /**
     * 󰕼 (0xF057C)
     */
    public data object VLC : MaterialDesignGlyphs(0xF057C)

    /**
     * 󰕽 (0xF057D)
     */
    public data object VOICEMAIL : MaterialDesignGlyphs(0xF057D)

    /**
     * 󰕾 (0xF057E)
     */
    public data object VOLUME_HIGH : MaterialDesignGlyphs(0xF057E)

    /**
     * 󰕿 (0xF057F)
     */
    public data object VOLUME_LOW : MaterialDesignGlyphs(0xF057F)

    /**
     * 󰖀 (0xF0580)
     */
    public data object VOLUME_MEDIUM : MaterialDesignGlyphs(0xF0580)

    /**
     * 󰖁 (0xF0581)
     */
    public data object VOLUME_OFF : MaterialDesignGlyphs(0xF0581)

    /**
     * 󰖂 (0xF0582)
     */
    public data object VPN : MaterialDesignGlyphs(0xF0582)

    /**
     * 󰖃 (0xF0583)
     */
    public data object WALK : MaterialDesignGlyphs(0xF0583)

    /**
     * 󰖄 (0xF0584)
     */
    public data object WALLET : MaterialDesignGlyphs(0xF0584)

    /**
     * 󰖅 (0xF0585)
     */
    public data object WALLET_GIFTCARD : MaterialDesignGlyphs(0xF0585)

    /**
     * 󰖆 (0xF0586)
     */
    public data object WALLET_MEMBERSHIP : MaterialDesignGlyphs(0xF0586)

    /**
     * 󰖇 (0xF0587)
     */
    public data object WALLET_TRAVEL : MaterialDesignGlyphs(0xF0587)

    /**
     * 󰖈 (0xF0588)
     */
    public data object WAN : MaterialDesignGlyphs(0xF0588)

    /**
     * 󰖉 (0xF0589)
     */
    public data object WATCH : MaterialDesignGlyphs(0xF0589)

    /**
     * 󰖊 (0xF058A)
     */
    public data object WATCH_EXPORT : MaterialDesignGlyphs(0xF058A)

    /**
     * 󰖋 (0xF058B)
     */
    public data object WATCH_IMPORT : MaterialDesignGlyphs(0xF058B)

    /**
     * 󰖌 (0xF058C)
     */
    public data object WATER : MaterialDesignGlyphs(0xF058C)

    /**
     * 󰖍 (0xF058D)
     */
    public data object WATER_OFF : MaterialDesignGlyphs(0xF058D)

    /**
     * 󰖎 (0xF058E)
     */
    public data object WATER_PERCENT : MaterialDesignGlyphs(0xF058E)

    /**
     * 󰖏 (0xF058F)
     */
    public data object WATER_PUMP : MaterialDesignGlyphs(0xF058F)

    /**
     * 󰖐 (0xF0590)
     */
    public data object WEATHER_CLOUDY : MaterialDesignGlyphs(0xF0590)

    /**
     * 󰖑 (0xF0591)
     */
    public data object WEATHER_FOG : MaterialDesignGlyphs(0xF0591)

    /**
     * 󰖒 (0xF0592)
     */
    public data object WEATHER_HAIL : MaterialDesignGlyphs(0xF0592)

    /**
     * 󰖓 (0xF0593)
     */
    public data object WEATHER_LIGHTNING : MaterialDesignGlyphs(0xF0593)

    /**
     * 󰖔 (0xF0594)
     */
    public data object WEATHER_NIGHT : MaterialDesignGlyphs(0xF0594)

    /**
     * 󰖕 (0xF0595)
     */
    public data object WEATHER_PARTLY_CLOUDY : MaterialDesignGlyphs(0xF0595)

    /**
     * 󰖖 (0xF0596)
     */
    public data object WEATHER_POURING : MaterialDesignGlyphs(0xF0596)

    /**
     * 󰖗 (0xF0597)
     */
    public data object WEATHER_RAINY : MaterialDesignGlyphs(0xF0597)

    /**
     * 󰖘 (0xF0598)
     */
    public data object WEATHER_SNOWY : MaterialDesignGlyphs(0xF0598)

    /**
     * 󰖙 (0xF0599)
     */
    public data object WEATHER_SUNNY : MaterialDesignGlyphs(0xF0599)

    /**
     * 󰖚 (0xF059A)
     */
    public data object WEATHER_SUNSET : MaterialDesignGlyphs(0xF059A)

    /**
     * 󰖛 (0xF059B)
     */
    public data object WEATHER_SUNSET_DOWN : MaterialDesignGlyphs(0xF059B)

    /**
     * 󰖜 (0xF059C)
     */
    public data object WEATHER_SUNSET_UP : MaterialDesignGlyphs(0xF059C)

    /**
     * 󰖝 (0xF059D)
     */
    public data object WEATHER_WINDY : MaterialDesignGlyphs(0xF059D)

    /**
     * 󰖞 (0xF059E)
     */
    public data object WEATHER_WINDY_VARIANT : MaterialDesignGlyphs(0xF059E)

    /**
     * 󰖟 (0xF059F)
     */
    public data object WEB : MaterialDesignGlyphs(0xF059F)

    /**
     * 󰖠 (0xF05A0)
     */
    public data object WEBCAM : MaterialDesignGlyphs(0xF05A0)

    /**
     * 󰖡 (0xF05A1)
     */
    public data object WEIGHT : MaterialDesignGlyphs(0xF05A1)

    /**
     * 󰖢 (0xF05A2)
     */
    public data object WEIGHT_KILOGRAM : MaterialDesignGlyphs(0xF05A2)

    /**
     * 󰖣 (0xF05A3)
     */
    public data object WHATSAPP : MaterialDesignGlyphs(0xF05A3)

    /**
     * 󰖤 (0xF05A4)
     */
    public data object WHEELCHAIR_ACCESSIBILITY : MaterialDesignGlyphs(0xF05A4)

    /**
     * 󰖥 (0xF05A5)
     */
    public data object WHITE_BALANCE_AUTO : MaterialDesignGlyphs(0xF05A5)

    /**
     * 󰖦 (0xF05A6)
     */
    public data object WHITE_BALANCE_INCANDESCENT : MaterialDesignGlyphs(0xF05A6)

    /**
     * 󰖧 (0xF05A7)
     */
    public data object WHITE_BALANCE_IRIDESCENT : MaterialDesignGlyphs(0xF05A7)

    /**
     * 󰖨 (0xF05A8)
     */
    public data object WHITE_BALANCE_SUNNY : MaterialDesignGlyphs(0xF05A8)

    /**
     * 󰖩 (0xF05A9)
     */
    public data object WIFI : MaterialDesignGlyphs(0xF05A9)

    /**
     * 󰖪 (0xF05AA)
     */
    public data object WIFI_OFF : MaterialDesignGlyphs(0xF05AA)

    /**
     * 󰖫 (0xF05AB)
     */
    public data object NINTENDO_WII : MaterialDesignGlyphs(0xF05AB)

    /**
     * 󰖬 (0xF05AC)
     */
    public data object WIKIPEDIA : MaterialDesignGlyphs(0xF05AC)

    /**
     * 󰖭 (0xF05AD)
     */
    public data object WINDOW_CLOSE : MaterialDesignGlyphs(0xF05AD)

    /**
     * 󰖮 (0xF05AE)
     */
    public data object WINDOW_CLOSED : MaterialDesignGlyphs(0xF05AE)

    /**
     * 󰖯 (0xF05AF)
     */
    public data object WINDOW_MAXIMIZE : MaterialDesignGlyphs(0xF05AF)

    /**
     * 󰖰 (0xF05B0)
     */
    public data object WINDOW_MINIMIZE : MaterialDesignGlyphs(0xF05B0)

    /**
     * 󰖱 (0xF05B1)
     */
    public data object WINDOW_OPEN : MaterialDesignGlyphs(0xF05B1)

    /**
     * 󰖲 (0xF05B2)
     */
    public data object WINDOW_RESTORE : MaterialDesignGlyphs(0xF05B2)

    /**
     * 󰖳 (0xF05B3)
     */
    public data object MICROSOFT_WINDOWS : MaterialDesignGlyphs(0xF05B3)

    /**
     * 󰖴 (0xF05B4)
     */
    public data object WORDPRESS : MaterialDesignGlyphs(0xF05B4)

    /**
     * 󰖵 (0xF05B5)
     */
    public data object ACCOUNT_HARD_HAT : MaterialDesignGlyphs(0xF05B5)

    /**
     * 󰖶 (0xF05B6)
     */
    public data object WRAP : MaterialDesignGlyphs(0xF05B6)

    /**
     * 󰖷 (0xF05B7)
     */
    public data object WRENCH : MaterialDesignGlyphs(0xF05B7)

    /**
     * 󰖸 (0xF05B8)
     */
    public data object CONTACTS_OUTLINE : MaterialDesignGlyphs(0xF05B8)

    /**
     * 󰖹 (0xF05B9)
     */
    public data object MICROSOFT_XBOX : MaterialDesignGlyphs(0xF05B9)

    /**
     * 󰖺 (0xF05BA)
     */
    public data object MICROSOFT_XBOX_CONTROLLER : MaterialDesignGlyphs(0xF05BA)

    /**
     * 󰖻 (0xF05BB)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_OFF : MaterialDesignGlyphs(0xF05BB)

    /**
     * 󰖼 (0xF05BC)
     */
    public data object TABLE_FURNITURE : MaterialDesignGlyphs(0xF05BC)

    /**
     * 󰖽 (0xF05BD)
     */
    public data object SORT_ALPHABETICAL_ASCENDING : MaterialDesignGlyphs(0xF05BD)

    /**
     * 󰖾 (0xF05BE)
     */
    public data object FIREWIRE : MaterialDesignGlyphs(0xF05BE)

    /**
     * 󰖿 (0xF05BF)
     */
    public data object SORT_ALPHABETICAL_DESCENDING : MaterialDesignGlyphs(0xF05BF)

    /**
     * 󰗀 (0xF05C0)
     */
    public data object XML : MaterialDesignGlyphs(0xF05C0)

    /**
     * 󰗁 (0xF05C1)
     */
    public data object YEAST : MaterialDesignGlyphs(0xF05C1)

    /**
     * 󰗂 (0xF05C2)
     */
    public data object DATABASE_REFRESH : MaterialDesignGlyphs(0xF05C2)

    /**
     * 󰗃 (0xF05C3)
     */
    public data object YOUTUBE : MaterialDesignGlyphs(0xF05C3)

    /**
     * 󰗄 (0xF05C4)
     */
    public data object ZIP_BOX : MaterialDesignGlyphs(0xF05C4)

    /**
     * 󰗅 (0xF05C5)
     */
    public data object SURROUND_SOUND : MaterialDesignGlyphs(0xF05C5)

    /**
     * 󰗆 (0xF05C6)
     */
    public data object VECTOR_RECTANGLE : MaterialDesignGlyphs(0xF05C6)

    /**
     * 󰗇 (0xF05C7)
     */
    public data object PLAYLIST_CHECK : MaterialDesignGlyphs(0xF05C7)

    /**
     * 󰗈 (0xF05C8)
     */
    public data object FORMAT_LINE_STYLE : MaterialDesignGlyphs(0xF05C8)

    /**
     * 󰗉 (0xF05C9)
     */
    public data object FORMAT_LINE_WEIGHT : MaterialDesignGlyphs(0xF05C9)

    /**
     * 󰗊 (0xF05CA)
     */
    public data object TRANSLATE : MaterialDesignGlyphs(0xF05CA)

    /**
     * 󰗋 (0xF05CB)
     */
    public data object ACCOUNT_VOICE : MaterialDesignGlyphs(0xF05CB)

    /**
     * 󰗌 (0xF05CC)
     */
    public data object OPACITY : MaterialDesignGlyphs(0xF05CC)

    /**
     * 󰗍 (0xF05CD)
     */
    public data object NEAR_ME : MaterialDesignGlyphs(0xF05CD)

    /**
     * 󰗎 (0xF05CE)
     */
    public data object CLOCK_ALERT_OUTLINE : MaterialDesignGlyphs(0xF05CE)

    /**
     * 󰗏 (0xF05CF)
     */
    public data object HUMAN_PREGNANT : MaterialDesignGlyphs(0xF05CF)

    /**
     * 󰗐 (0xF05D0)
     */
    public data object STICKER_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF05D0)

    /**
     * 󰗑 (0xF05D1)
     */
    public data object SCALE_BALANCE : MaterialDesignGlyphs(0xF05D1)

    /**
     * 󰗒 (0xF05D2)
     */
    public data object CARD_ACCOUNT_DETAILS : MaterialDesignGlyphs(0xF05D2)

    /**
     * 󰗓 (0xF05D3)
     */
    public data object ACCOUNT_MULTIPLE_MINUS : MaterialDesignGlyphs(0xF05D3)

    /**
     * 󰗔 (0xF05D4)
     */
    public data object AIRPLANE_LANDING : MaterialDesignGlyphs(0xF05D4)

    /**
     * 󰗕 (0xF05D5)
     */
    public data object AIRPLANE_TAKEOFF : MaterialDesignGlyphs(0xF05D5)

    /**
     * 󰗖 (0xF05D6)
     */
    public data object ALERT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF05D6)

    /**
     * 󰗗 (0xF05D7)
     */
    public data object ALTIMETER : MaterialDesignGlyphs(0xF05D7)

    /**
     * 󰗘 (0xF05D8)
     */
    public data object ANIMATION : MaterialDesignGlyphs(0xF05D8)

    /**
     * 󰗙 (0xF05D9)
     */
    public data object BOOK_MINUS : MaterialDesignGlyphs(0xF05D9)

    /**
     * 󰗚 (0xF05DA)
     */
    public data object BOOK_OPEN_PAGE_VARIANT : MaterialDesignGlyphs(0xF05DA)

    /**
     * 󰗛 (0xF05DB)
     */
    public data object BOOK_PLUS : MaterialDesignGlyphs(0xF05DB)

    /**
     * 󰗜 (0xF05DC)
     */
    public data object BOOMBOX : MaterialDesignGlyphs(0xF05DC)

    /**
     * 󰗝 (0xF05DD)
     */
    public data object BULLSEYE : MaterialDesignGlyphs(0xF05DD)

    /**
     * 󰗞 (0xF05DE)
     */
    public data object COMMENT_REMOVE : MaterialDesignGlyphs(0xF05DE)

    /**
     * 󰗟 (0xF05DF)
     */
    public data object CAMERA_OFF : MaterialDesignGlyphs(0xF05DF)

    /**
     * 󰗠 (0xF05E0)
     */
    public data object CHECK_CIRCLE : MaterialDesignGlyphs(0xF05E0)

    /**
     * 󰗡 (0xF05E1)
     */
    public data object CHECK_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF05E1)

    /**
     * 󰗢 (0xF05E2)
     */
    public data object CANDLE : MaterialDesignGlyphs(0xF05E2)

    /**
     * 󰗣 (0xF05E3)
     */
    public data object CHART_BUBBLE : MaterialDesignGlyphs(0xF05E3)

    /**
     * 󰗤 (0xF05E4)
     */
    public data object CREDIT_CARD_OFF_OUTLINE : MaterialDesignGlyphs(0xF05E4)

    /**
     * 󰗥 (0xF05E5)
     */
    public data object CUP_OFF : MaterialDesignGlyphs(0xF05E5)

    /**
     * 󰗦 (0xF05E6)
     */
    public data object COPYRIGHT : MaterialDesignGlyphs(0xF05E6)

    /**
     * 󰗧 (0xF05E7)
     */
    public data object CURSOR_TEXT : MaterialDesignGlyphs(0xF05E7)

    /**
     * 󰗨 (0xF05E8)
     */
    public data object DELETE_FOREVER : MaterialDesignGlyphs(0xF05E8)

    /**
     * 󰗩 (0xF05E9)
     */
    public data object DELETE_SWEEP : MaterialDesignGlyphs(0xF05E9)

    /**
     * 󰗪 (0xF05EA)
     */
    public data object DICE_D20_OUTLINE : MaterialDesignGlyphs(0xF05EA)

    /**
     * 󰗫 (0xF05EB)
     */
    public data object DICE_D4_OUTLINE : MaterialDesignGlyphs(0xF05EB)

    /**
     * 󰗬 (0xF05EC)
     */
    public data object DICE_D8_OUTLINE : MaterialDesignGlyphs(0xF05EC)

    /**
     * 󰗭 (0xF05ED)
     */
    public data object DICE_D6_OUTLINE : MaterialDesignGlyphs(0xF05ED)

    /**
     * 󰗮 (0xF05EE)
     */
    public data object DISC : MaterialDesignGlyphs(0xF05EE)

    /**
     * 󰗯 (0xF05EF)
     */
    public data object EMAIL_OPEN_OUTLINE : MaterialDesignGlyphs(0xF05EF)

    /**
     * 󰗰 (0xF05F0)
     */
    public data object EMAIL_VARIANT : MaterialDesignGlyphs(0xF05F0)

    /**
     * 󰗱 (0xF05F1)
     */
    public data object EV_STATION : MaterialDesignGlyphs(0xF05F1)

    /**
     * 󰗲 (0xF05F2)
     */
    public data object FOOD_FORK_DRINK : MaterialDesignGlyphs(0xF05F2)

    /**
     * 󰗳 (0xF05F3)
     */
    public data object FOOD_OFF : MaterialDesignGlyphs(0xF05F3)

    /**
     * 󰗴 (0xF05F4)
     */
    public data object FORMAT_TITLE : MaterialDesignGlyphs(0xF05F4)

    /**
     * 󰗵 (0xF05F5)
     */
    public data object GOOGLE_MAPS : MaterialDesignGlyphs(0xF05F5)

    /**
     * 󰗶 (0xF05F6)
     */
    public data object HEART_PULSE : MaterialDesignGlyphs(0xF05F6)

    /**
     * 󰗷 (0xF05F7)
     */
    public data object HIGHWAY : MaterialDesignGlyphs(0xF05F7)

    /**
     * 󰗸 (0xF05F8)
     */
    public data object HOME_MAP_MARKER : MaterialDesignGlyphs(0xF05F8)

    /**
     * 󰗹 (0xF05F9)
     */
    public data object INCOGNITO : MaterialDesignGlyphs(0xF05F9)

    /**
     * 󰗺 (0xF05FA)
     */
    public data object KETTLE : MaterialDesignGlyphs(0xF05FA)

    /**
     * 󰗻 (0xF05FB)
     */
    public data object LOCK_PLUS : MaterialDesignGlyphs(0xF05FB)

    /**
     * 󰗽 (0xF05FD)
     */
    public data object LOGOUT_VARIANT : MaterialDesignGlyphs(0xF05FD)

    /**
     * 󰗾 (0xF05FE)
     */
    public data object MUSIC_NOTE_BLUETOOTH : MaterialDesignGlyphs(0xF05FE)

    /**
     * 󰗿 (0xF05FF)
     */
    public data object MUSIC_NOTE_BLUETOOTH_OFF : MaterialDesignGlyphs(0xF05FF)

    /**
     * 󰘀 (0xF0600)
     */
    public data object PAGE_FIRST : MaterialDesignGlyphs(0xF0600)

    /**
     * 󰘁 (0xF0601)
     */
    public data object PAGE_LAST : MaterialDesignGlyphs(0xF0601)

    /**
     * 󰘂 (0xF0602)
     */
    public data object PHONE_CLASSIC : MaterialDesignGlyphs(0xF0602)

    /**
     * 󰘃 (0xF0603)
     */
    public data object PRIORITY_HIGH : MaterialDesignGlyphs(0xF0603)

    /**
     * 󰘄 (0xF0604)
     */
    public data object PRIORITY_LOW : MaterialDesignGlyphs(0xF0604)

    /**
     * 󰘅 (0xF0605)
     */
    public data object QQCHAT : MaterialDesignGlyphs(0xF0605)

    /**
     * 󰘆 (0xF0606)
     */
    public data object POOL : MaterialDesignGlyphs(0xF0606)

    /**
     * 󰘇 (0xF0607)
     */
    public data object ROUNDED_CORNER : MaterialDesignGlyphs(0xF0607)

    /**
     * 󰘈 (0xF0608)
     */
    public data object ROWING : MaterialDesignGlyphs(0xF0608)

    /**
     * 󰘉 (0xF0609)
     */
    public data object SAXOPHONE : MaterialDesignGlyphs(0xF0609)

    /**
     * 󰘊 (0xF060A)
     */
    public data object SIGNAL_VARIANT : MaterialDesignGlyphs(0xF060A)

    /**
     * 󰘋 (0xF060B)
     */
    public data object STACK_EXCHANGE : MaterialDesignGlyphs(0xF060B)

    /**
     * 󰘌 (0xF060C)
     */
    public data object SUBDIRECTORY_ARROW_LEFT : MaterialDesignGlyphs(0xF060C)

    /**
     * 󰘍 (0xF060D)
     */
    public data object SUBDIRECTORY_ARROW_RIGHT : MaterialDesignGlyphs(0xF060D)

    /**
     * 󰘎 (0xF060E)
     */
    public data object FORM_TEXTBOX : MaterialDesignGlyphs(0xF060E)

    /**
     * 󰘏 (0xF060F)
     */
    public data object VIOLIN : MaterialDesignGlyphs(0xF060F)

    /**
     * 󰘐 (0xF0610)
     */
    public data object MICROSOFT_VISUAL_STUDIO : MaterialDesignGlyphs(0xF0610)

    /**
     * 󰘑 (0xF0611)
     */
    public data object WECHAT : MaterialDesignGlyphs(0xF0611)

    /**
     * 󰘒 (0xF0612)
     */
    public data object WATERMARK : MaterialDesignGlyphs(0xF0612)

    /**
     * 󰘓 (0xF0613)
     */
    public data object FILE_HIDDEN : MaterialDesignGlyphs(0xF0613)

    /**
     * 󰘔 (0xF0614)
     */
    public data object APPLICATION_OUTLINE : MaterialDesignGlyphs(0xF0614)

    /**
     * 󰘕 (0xF0615)
     */
    public data object ARROW_COLLAPSE : MaterialDesignGlyphs(0xF0615)

    /**
     * 󰘖 (0xF0616)
     */
    public data object ARROW_EXPAND : MaterialDesignGlyphs(0xF0616)

    /**
     * 󰘗 (0xF0617)
     */
    public data object BOWL_MIX : MaterialDesignGlyphs(0xF0617)

    /**
     * 󰘘 (0xF0618)
     */
    public data object BRIDGE : MaterialDesignGlyphs(0xF0618)

    /**
     * 󰘙 (0xF0619)
     */
    public data object APPLICATION_EDIT_OUTLINE : MaterialDesignGlyphs(0xF0619)

    /**
     * 󰘚 (0xF061A)
     */
    public data object CHIP : MaterialDesignGlyphs(0xF061A)

    /**
     * 󰘛 (0xF061B)
     */
    public data object CONTENT_SAVE_SETTINGS : MaterialDesignGlyphs(0xF061B)

    /**
     * 󰘜 (0xF061C)
     */
    public data object DIALPAD : MaterialDesignGlyphs(0xF061C)

    /**
     * 󰘝 (0xF061D)
     */
    public data object BOOK_ALPHABET : MaterialDesignGlyphs(0xF061D)

    /**
     * 󰘞 (0xF061E)
     */
    public data object FORMAT_HORIZONTAL_ALIGN_CENTER : MaterialDesignGlyphs(0xF061E)

    /**
     * 󰘟 (0xF061F)
     */
    public data object FORMAT_HORIZONTAL_ALIGN_LEFT : MaterialDesignGlyphs(0xF061F)

    /**
     * 󰘠 (0xF0620)
     */
    public data object FORMAT_HORIZONTAL_ALIGN_RIGHT : MaterialDesignGlyphs(0xF0620)

    /**
     * 󰘡 (0xF0621)
     */
    public data object FORMAT_VERTICAL_ALIGN_BOTTOM : MaterialDesignGlyphs(0xF0621)

    /**
     * 󰘢 (0xF0622)
     */
    public data object FORMAT_VERTICAL_ALIGN_CENTER : MaterialDesignGlyphs(0xF0622)

    /**
     * 󰘣 (0xF0623)
     */
    public data object FORMAT_VERTICAL_ALIGN_TOP : MaterialDesignGlyphs(0xF0623)

    /**
     * 󰘤 (0xF0624)
     */
    public data object LINE_SCAN : MaterialDesignGlyphs(0xF0624)

    /**
     * 󰘥 (0xF0625)
     */
    public data object HELP_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0625)

    /**
     * 󰘦 (0xF0626)
     */
    public data object CODE_JSON : MaterialDesignGlyphs(0xF0626)

    /**
     * 󰘧 (0xF0627)
     */
    public data object LAMBDA : MaterialDesignGlyphs(0xF0627)

    /**
     * 󰘨 (0xF0628)
     */
    public data object MATRIX : MaterialDesignGlyphs(0xF0628)

    /**
     * 󰘩 (0xF0629)
     */
    public data object METEOR : MaterialDesignGlyphs(0xF0629)

    /**
     * 󰘪 (0xF062A)
     */
    public data object CLOSE_CIRCLE_MULTIPLE : MaterialDesignGlyphs(0xF062A)

    /**
     * 󰘫 (0xF062B)
     */
    public data object SIGMA_LOWER : MaterialDesignGlyphs(0xF062B)

    /**
     * 󰘬 (0xF062C)
     */
    public data object SOURCE_BRANCH : MaterialDesignGlyphs(0xF062C)

    /**
     * 󰘭 (0xF062D)
     */
    public data object SOURCE_MERGE : MaterialDesignGlyphs(0xF062D)

    /**
     * 󰘮 (0xF062E)
     */
    public data object TUNE : MaterialDesignGlyphs(0xF062E)

    /**
     * 󰘯 (0xF062F)
     */
    public data object WEBHOOK : MaterialDesignGlyphs(0xF062F)

    /**
     * 󰘰 (0xF0630)
     */
    public data object ACCOUNT_SETTINGS : MaterialDesignGlyphs(0xF0630)

    /**
     * 󰘱 (0xF0631)
     */
    public data object ACCOUNT_DETAILS : MaterialDesignGlyphs(0xF0631)

    /**
     * 󰘲 (0xF0632)
     */
    public data object APPLE_KEYBOARD_CAPS : MaterialDesignGlyphs(0xF0632)

    /**
     * 󰘳 (0xF0633)
     */
    public data object APPLE_KEYBOARD_COMMAND : MaterialDesignGlyphs(0xF0633)

    /**
     * 󰘴 (0xF0634)
     */
    public data object APPLE_KEYBOARD_CONTROL : MaterialDesignGlyphs(0xF0634)

    /**
     * 󰘵 (0xF0635)
     */
    public data object APPLE_KEYBOARD_OPTION : MaterialDesignGlyphs(0xF0635)

    /**
     * 󰘶 (0xF0636)
     */
    public data object APPLE_KEYBOARD_SHIFT : MaterialDesignGlyphs(0xF0636)

    /**
     * 󰘷 (0xF0637)
     */
    public data object BOX_SHADOW : MaterialDesignGlyphs(0xF0637)

    /**
     * 󰘸 (0xF0638)
     */
    public data object CARDS : MaterialDesignGlyphs(0xF0638)

    /**
     * 󰘹 (0xF0639)
     */
    public data object CARDS_OUTLINE : MaterialDesignGlyphs(0xF0639)

    /**
     * 󰘺 (0xF063A)
     */
    public data object CARDS_PLAYING_OUTLINE : MaterialDesignGlyphs(0xF063A)

    /**
     * 󰘻 (0xF063B)
     */
    public data object CHECKBOX_MULTIPLE_BLANK_CIRCLE : MaterialDesignGlyphs(0xF063B)

    /**
     * 󰘼 (0xF063C)
     */
    public data object CHECKBOX_MULTIPLE_BLANK_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF063C)

    /**
     * 󰘽 (0xF063D)
     */
    public data object CHECKBOX_MULTIPLE_MARKED_CIRCLE : MaterialDesignGlyphs(0xF063D)

    /**
     * 󰘾 (0xF063E)
     */
    public data object CHECKBOX_MULTIPLE_MARKED_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF063E)

    /**
     * 󰘿 (0xF063F)
     */
    public data object CLOUD_SYNC : MaterialDesignGlyphs(0xF063F)

    /**
     * 󰙀 (0xF0640)
     */
    public data object COLLAGE : MaterialDesignGlyphs(0xF0640)

    /**
     * 󰙁 (0xF0641)
     */
    public data object DIRECTIONS_FORK : MaterialDesignGlyphs(0xF0641)

    /**
     * 󰙂 (0xF0642)
     */
    public data object ERASER_VARIANT : MaterialDesignGlyphs(0xF0642)

    /**
     * 󰙃 (0xF0643)
     */
    public data object FACE_MAN : MaterialDesignGlyphs(0xF0643)

    /**
     * 󰙄 (0xF0644)
     */
    public data object FACE_MAN_PROFILE : MaterialDesignGlyphs(0xF0644)

    /**
     * 󰙅 (0xF0645)
     */
    public data object FILE_TREE : MaterialDesignGlyphs(0xF0645)

    /**
     * 󰙆 (0xF0646)
     */
    public data object FORMAT_ANNOTATION_PLUS : MaterialDesignGlyphs(0xF0646)

    /**
     * 󰙇 (0xF0647)
     */
    public data object GAS_CYLINDER : MaterialDesignGlyphs(0xF0647)

    /**
     * 󰙈 (0xF0648)
     */
    public data object GREASE_PENCIL : MaterialDesignGlyphs(0xF0648)

    /**
     * 󰙉 (0xF0649)
     */
    public data object HUMAN_FEMALE : MaterialDesignGlyphs(0xF0649)

    /**
     * 󰙊 (0xF064A)
     */
    public data object HUMAN_GREETING_VARIANT : MaterialDesignGlyphs(0xF064A)

    /**
     * 󰙋 (0xF064B)
     */
    public data object HUMAN_HANDSDOWN : MaterialDesignGlyphs(0xF064B)

    /**
     * 󰙌 (0xF064C)
     */
    public data object HUMAN_HANDSUP : MaterialDesignGlyphs(0xF064C)

    /**
     * 󰙍 (0xF064D)
     */
    public data object HUMAN_MALE : MaterialDesignGlyphs(0xF064D)

    /**
     * 󰙎 (0xF064E)
     */
    public data object INFORMATION_VARIANT : MaterialDesignGlyphs(0xF064E)

    /**
     * 󰙏 (0xF064F)
     */
    public data object LEAD_PENCIL : MaterialDesignGlyphs(0xF064F)

    /**
     * 󰙐 (0xF0650)
     */
    public data object MAP_MARKER_MINUS : MaterialDesignGlyphs(0xF0650)

    /**
     * 󰙑 (0xF0651)
     */
    public data object MAP_MARKER_PLUS : MaterialDesignGlyphs(0xF0651)

    /**
     * 󰙒 (0xF0652)
     */
    public data object MARKER : MaterialDesignGlyphs(0xF0652)

    /**
     * 󰙓 (0xF0653)
     */
    public data object MESSAGE_PLUS : MaterialDesignGlyphs(0xF0653)

    /**
     * 󰙔 (0xF0654)
     */
    public data object MICROSCOPE : MaterialDesignGlyphs(0xF0654)

    /**
     * 󰙕 (0xF0655)
     */
    public data object MOVE_RESIZE : MaterialDesignGlyphs(0xF0655)

    /**
     * 󰙖 (0xF0656)
     */
    public data object MOVE_RESIZE_VARIANT : MaterialDesignGlyphs(0xF0656)

    /**
     * 󰙗 (0xF0657)
     */
    public data object PAW_OFF : MaterialDesignGlyphs(0xF0657)

    /**
     * 󰙘 (0xF0658)
     */
    public data object PHONE_MINUS : MaterialDesignGlyphs(0xF0658)

    /**
     * 󰙙 (0xF0659)
     */
    public data object PHONE_PLUS : MaterialDesignGlyphs(0xF0659)

    /**
     * 󰙚 (0xF065A)
     */
    public data object POT_STEAM : MaterialDesignGlyphs(0xF065A)

    /**
     * 󰙛 (0xF065B)
     */
    public data object POT_MIX : MaterialDesignGlyphs(0xF065B)

    /**
     * 󰙜 (0xF065C)
     */
    public data object SERIAL_PORT : MaterialDesignGlyphs(0xF065C)

    /**
     * 󰙝 (0xF065D)
     */
    public data object SHAPE_CIRCLE_PLUS : MaterialDesignGlyphs(0xF065D)

    /**
     * 󰙞 (0xF065E)
     */
    public data object SHAPE_POLYGON_PLUS : MaterialDesignGlyphs(0xF065E)

    /**
     * 󰙟 (0xF065F)
     */
    public data object SHAPE_RECTANGLE_PLUS : MaterialDesignGlyphs(0xF065F)

    /**
     * 󰙠 (0xF0660)
     */
    public data object SHAPE_SQUARE_PLUS : MaterialDesignGlyphs(0xF0660)

    /**
     * 󰙡 (0xF0661)
     */
    public data object SKIP_NEXT_CIRCLE : MaterialDesignGlyphs(0xF0661)

    /**
     * 󰙢 (0xF0662)
     */
    public data object SKIP_NEXT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0662)

    /**
     * 󰙣 (0xF0663)
     */
    public data object SKIP_PREVIOUS_CIRCLE : MaterialDesignGlyphs(0xF0663)

    /**
     * 󰙤 (0xF0664)
     */
    public data object SKIP_PREVIOUS_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0664)

    /**
     * 󰙥 (0xF0665)
     */
    public data object SPRAY : MaterialDesignGlyphs(0xF0665)

    /**
     * 󰙦 (0xF0666)
     */
    public data object STOP_CIRCLE : MaterialDesignGlyphs(0xF0666)

    /**
     * 󰙧 (0xF0667)
     */
    public data object STOP_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0667)

    /**
     * 󰙨 (0xF0668)
     */
    public data object TEST_TUBE : MaterialDesignGlyphs(0xF0668)

    /**
     * 󰙩 (0xF0669)
     */
    public data object TEXT_SHADOW : MaterialDesignGlyphs(0xF0669)

    /**
     * 󰙪 (0xF066A)
     */
    public data object TUNE_VERTICAL : MaterialDesignGlyphs(0xF066A)

    /**
     * 󰙫 (0xF066B)
     */
    public data object CART_OFF : MaterialDesignGlyphs(0xF066B)

    /**
     * 󰙬 (0xF066C)
     */
    public data object CHART_GANTT : MaterialDesignGlyphs(0xF066C)

    /**
     * 󰙭 (0xF066D)
     */
    public data object CHART_SCATTER_PLOT_HEXBIN : MaterialDesignGlyphs(0xF066D)

    /**
     * 󰙮 (0xF066E)
     */
    public data object CHART_TIMELINE : MaterialDesignGlyphs(0xF066E)

    /**
     * 󰙯 (0xF066F)
     */
    public data object DISCORD : MaterialDesignGlyphs(0xF066F)

    /**
     * 󰙰 (0xF0670)
     */
    public data object FILE_RESTORE : MaterialDesignGlyphs(0xF0670)

    /**
     * 󰙱 (0xF0671)
     */
    public data object LANGUAGE_C : MaterialDesignGlyphs(0xF0671)

    /**
     * 󰙲 (0xF0672)
     */
    public data object LANGUAGE_CPP : MaterialDesignGlyphs(0xF0672)

    /**
     * 󰙳 (0xF0673)
     */
    public data object LANGUAGE_XAML : MaterialDesignGlyphs(0xF0673)

    /**
     * 󰙴 (0xF0674)
     */
    public data object CREATION : MaterialDesignGlyphs(0xF0674)

    /**
     * 󰙵 (0xF0675)
     */
    public data object APPLICATION_COG : MaterialDesignGlyphs(0xF0675)

    /**
     * 󰙶 (0xF0676)
     */
    public data object CREDIT_CARD_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0676)

    /**
     * 󰙷 (0xF0677)
     */
    public data object POT_MIX_OUTLINE : MaterialDesignGlyphs(0xF0677)

    /**
     * 󰙸 (0xF0678)
     */
    public data object BOW_TIE : MaterialDesignGlyphs(0xF0678)

    /**
     * 󰙹 (0xF0679)
     */
    public data object CALENDAR_RANGE : MaterialDesignGlyphs(0xF0679)

    /**
     * 󰙺 (0xF067A)
     */
    public data object CURRENCY_USD_OFF : MaterialDesignGlyphs(0xF067A)

    /**
     * 󰙻 (0xF067B)
     */
    public data object FLASH_RED_EYE : MaterialDesignGlyphs(0xF067B)

    /**
     * 󰙼 (0xF067C)
     */
    public data object OAR : MaterialDesignGlyphs(0xF067C)

    /**
     * 󰙽 (0xF067D)
     */
    public data object PIANO : MaterialDesignGlyphs(0xF067D)

    /**
     * 󰙾 (0xF067E)
     */
    public data object WEATHER_LIGHTNING_RAINY : MaterialDesignGlyphs(0xF067E)

    /**
     * 󰙿 (0xF067F)
     */
    public data object WEATHER_SNOWY_RAINY : MaterialDesignGlyphs(0xF067F)

    /**
     * 󰚀 (0xF0680)
     */
    public data object YIN_YANG : MaterialDesignGlyphs(0xF0680)

    /**
     * 󰚁 (0xF0681)
     */
    public data object TOWER_BEACH : MaterialDesignGlyphs(0xF0681)

    /**
     * 󰚂 (0xF0682)
     */
    public data object TOWER_FIRE : MaterialDesignGlyphs(0xF0682)

    /**
     * 󰚃 (0xF0683)
     */
    public data object DELETE_CIRCLE : MaterialDesignGlyphs(0xF0683)

    /**
     * 󰚄 (0xF0684)
     */
    public data object DNA : MaterialDesignGlyphs(0xF0684)

    /**
     * 󰚅 (0xF0685)
     */
    public data object HAMBURGER : MaterialDesignGlyphs(0xF0685)

    /**
     * 󰚆 (0xF0686)
     */
    public data object GONDOLA : MaterialDesignGlyphs(0xF0686)

    /**
     * 󰚇 (0xF0687)
     */
    public data object INBOX : MaterialDesignGlyphs(0xF0687)

    /**
     * 󰚈 (0xF0688)
     */
    public data object REORDER_HORIZONTAL : MaterialDesignGlyphs(0xF0688)

    /**
     * 󰚉 (0xF0689)
     */
    public data object REORDER_VERTICAL : MaterialDesignGlyphs(0xF0689)

    /**
     * 󰚊 (0xF068A)
     */
    public data object SHIELD_HOME : MaterialDesignGlyphs(0xF068A)

    /**
     * 󰚋 (0xF068B)
     */
    public data object TAG_HEART : MaterialDesignGlyphs(0xF068B)

    /**
     * 󰚌 (0xF068C)
     */
    public data object SKULL : MaterialDesignGlyphs(0xF068C)

    /**
     * 󰚍 (0xF068D)
     */
    public data object SOLID : MaterialDesignGlyphs(0xF068D)

    /**
     * 󰚎 (0xF068E)
     */
    public data object ALARM_SNOOZE : MaterialDesignGlyphs(0xF068E)

    /**
     * 󰚏 (0xF068F)
     */
    public data object BABY_CARRIAGE : MaterialDesignGlyphs(0xF068F)

    /**
     * 󰚐 (0xF0690)
     */
    public data object BEAKER_OUTLINE : MaterialDesignGlyphs(0xF0690)

    /**
     * 󰚑 (0xF0691)
     */
    public data object BOMB : MaterialDesignGlyphs(0xF0691)

    /**
     * 󰚒 (0xF0692)
     */
    public data object CALENDAR_QUESTION : MaterialDesignGlyphs(0xF0692)

    /**
     * 󰚓 (0xF0693)
     */
    public data object CAMERA_BURST : MaterialDesignGlyphs(0xF0693)

    /**
     * 󰚔 (0xF0694)
     */
    public data object CODE_TAGS_CHECK : MaterialDesignGlyphs(0xF0694)

    /**
     * 󰚕 (0xF0695)
     */
    public data object CIRCLE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0695)

    /**
     * 󰚖 (0xF0696)
     */
    public data object CROP_ROTATE : MaterialDesignGlyphs(0xF0696)

    /**
     * 󰚗 (0xF0697)
     */
    public data object DEVELOPER_BOARD : MaterialDesignGlyphs(0xF0697)

    /**
     * 󰚘 (0xF0698)
     */
    public data object PIANO_OFF : MaterialDesignGlyphs(0xF0698)

    /**
     * 󰚙 (0xF0699)
     */
    public data object SKATE_OFF : MaterialDesignGlyphs(0xF0699)

    /**
     * 󰚚 (0xF069A)
     */
    public data object MESSAGE_STAR : MaterialDesignGlyphs(0xF069A)

    /**
     * 󰚛 (0xF069B)
     */
    public data object EMOTICON_DEAD_OUTLINE : MaterialDesignGlyphs(0xF069B)

    /**
     * 󰚜 (0xF069C)
     */
    public data object EMOTICON_EXCITED_OUTLINE : MaterialDesignGlyphs(0xF069C)

    /**
     * 󰚝 (0xF069D)
     */
    public data object FOLDER_STAR : MaterialDesignGlyphs(0xF069D)

    /**
     * 󰚞 (0xF069E)
     */
    public data object FORMAT_COLOR_TEXT : MaterialDesignGlyphs(0xF069E)

    /**
     * 󰚟 (0xF069F)
     */
    public data object FORMAT_SECTION : MaterialDesignGlyphs(0xF069F)

    /**
     * 󰚠 (0xF06A0)
     */
    public data object GRADIENT_VERTICAL : MaterialDesignGlyphs(0xF06A0)

    /**
     * 󰚡 (0xF06A1)
     */
    public data object HOME_OUTLINE : MaterialDesignGlyphs(0xF06A1)

    /**
     * 󰚢 (0xF06A2)
     */
    public data object MESSAGE_BULLETED : MaterialDesignGlyphs(0xF06A2)

    /**
     * 󰚣 (0xF06A3)
     */
    public data object MESSAGE_BULLETED_OFF : MaterialDesignGlyphs(0xF06A3)

    /**
     * 󰚤 (0xF06A4)
     */
    public data object NUKE : MaterialDesignGlyphs(0xF06A4)

    /**
     * 󰚥 (0xF06A5)
     */
    public data object POWER_PLUG : MaterialDesignGlyphs(0xF06A5)

    /**
     * 󰚦 (0xF06A6)
     */
    public data object POWER_PLUG_OFF : MaterialDesignGlyphs(0xF06A6)

    /**
     * 󰚧 (0xF06A7)
     */
    public data object PUBLISH : MaterialDesignGlyphs(0xF06A7)

    /**
     * 󰚨 (0xF06A8)
     */
    public data object CREDIT_CARD_MARKER : MaterialDesignGlyphs(0xF06A8)

    /**
     * 󰚩 (0xF06A9)
     */
    public data object ROBOT : MaterialDesignGlyphs(0xF06A9)

    /**
     * 󰚪 (0xF06AA)
     */
    public data object FORMAT_ROTATE_90 : MaterialDesignGlyphs(0xF06AA)

    /**
     * 󰚫 (0xF06AB)
     */
    public data object SCANNER : MaterialDesignGlyphs(0xF06AB)

    /**
     * 󰚬 (0xF06AC)
     */
    public data object SUBWAY : MaterialDesignGlyphs(0xF06AC)

    /**
     * 󰚭 (0xF06AD)
     */
    public data object TIMER_SAND_EMPTY : MaterialDesignGlyphs(0xF06AD)

    /**
     * 󰚮 (0xF06AE)
     */
    public data object TRANSIT_TRANSFER : MaterialDesignGlyphs(0xF06AE)

    /**
     * 󰚯 (0xF06AF)
     */
    public data object UNITY : MaterialDesignGlyphs(0xF06AF)

    /**
     * 󰚰 (0xF06B0)
     */
    public data object UPDATE : MaterialDesignGlyphs(0xF06B0)

    /**
     * 󰚱 (0xF06B1)
     */
    public data object WATCH_VIBRATE : MaterialDesignGlyphs(0xF06B1)

    /**
     * 󰚲 (0xF06B2)
     */
    public data object ANGULAR : MaterialDesignGlyphs(0xF06B2)

    /**
     * 󰚳 (0xF06B3)
     */
    public data object DOLBY : MaterialDesignGlyphs(0xF06B3)

    /**
     * 󰚴 (0xF06B4)
     */
    public data object EMBY : MaterialDesignGlyphs(0xF06B4)

    /**
     * 󰚵 (0xF06B5)
     */
    public data object LAMP : MaterialDesignGlyphs(0xF06B5)

    /**
     * 󰚶 (0xF06B6)
     */
    public data object MENU_DOWN_OUTLINE : MaterialDesignGlyphs(0xF06B6)

    /**
     * 󰚷 (0xF06B7)
     */
    public data object MENU_UP_OUTLINE : MaterialDesignGlyphs(0xF06B7)

    /**
     * 󰚸 (0xF06B8)
     */
    public data object NOTE_MULTIPLE : MaterialDesignGlyphs(0xF06B8)

    /**
     * 󰚹 (0xF06B9)
     */
    public data object NOTE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF06B9)

    /**
     * 󰚺 (0xF06BA)
     */
    public data object PLEX : MaterialDesignGlyphs(0xF06BA)

    /**
     * 󰚻 (0xF06BB)
     */
    public data object SHIELD_AIRPLANE : MaterialDesignGlyphs(0xF06BB)

    /**
     * 󰚼 (0xF06BC)
     */
    public data object ACCOUNT_EDIT : MaterialDesignGlyphs(0xF06BC)

    /**
     * 󰚽 (0xF06BD)
     */
    public data object ALERT_DECAGRAM : MaterialDesignGlyphs(0xF06BD)

    /**
     * 󰚾 (0xF06BE)
     */
    public data object ALL_INCLUSIVE : MaterialDesignGlyphs(0xF06BE)

    /**
     * 󰚿 (0xF06BF)
     */
    public data object ANGULARJS : MaterialDesignGlyphs(0xF06BF)

    /**
     * 󰛀 (0xF06C0)
     */
    public data object ARROW_DOWN_BOX : MaterialDesignGlyphs(0xF06C0)

    /**
     * 󰛁 (0xF06C1)
     */
    public data object ARROW_LEFT_BOX : MaterialDesignGlyphs(0xF06C1)

    /**
     * 󰛂 (0xF06C2)
     */
    public data object ARROW_RIGHT_BOX : MaterialDesignGlyphs(0xF06C2)

    /**
     * 󰛃 (0xF06C3)
     */
    public data object ARROW_UP_BOX : MaterialDesignGlyphs(0xF06C3)

    /**
     * 󰛄 (0xF06C4)
     */
    public data object ASTERISK : MaterialDesignGlyphs(0xF06C4)

    /**
     * 󰛅 (0xF06C5)
     */
    public data object BOMB_OFF : MaterialDesignGlyphs(0xF06C5)

    /**
     * 󰛆 (0xF06C6)
     */
    public data object BOOTSTRAP : MaterialDesignGlyphs(0xF06C6)

    /**
     * 󰛇 (0xF06C7)
     */
    public data object CARDS_VARIANT : MaterialDesignGlyphs(0xF06C7)

    /**
     * 󰛈 (0xF06C8)
     */
    public data object CLIPBOARD_FLOW : MaterialDesignGlyphs(0xF06C8)

    /**
     * 󰛉 (0xF06C9)
     */
    public data object CLOSE_OUTLINE : MaterialDesignGlyphs(0xF06C9)

    /**
     * 󰛊 (0xF06CA)
     */
    public data object COFFEE_OUTLINE : MaterialDesignGlyphs(0xF06CA)

    /**
     * 󰛋 (0xF06CB)
     */
    public data object CONTACTS : MaterialDesignGlyphs(0xF06CB)

    /**
     * 󰛌 (0xF06CC)
     */
    public data object DELETE_EMPTY : MaterialDesignGlyphs(0xF06CC)

    /**
     * 󰛍 (0xF06CD)
     */
    public data object EARTH_BOX : MaterialDesignGlyphs(0xF06CD)

    /**
     * 󰛎 (0xF06CE)
     */
    public data object EARTH_BOX_OFF : MaterialDesignGlyphs(0xF06CE)

    /**
     * 󰛏 (0xF06CF)
     */
    public data object EMAIL_ALERT : MaterialDesignGlyphs(0xF06CF)

    /**
     * 󰛐 (0xF06D0)
     */
    public data object EYE_OUTLINE : MaterialDesignGlyphs(0xF06D0)

    /**
     * 󰛑 (0xF06D1)
     */
    public data object EYE_OFF_OUTLINE : MaterialDesignGlyphs(0xF06D1)

    /**
     * 󰛒 (0xF06D2)
     */
    public data object FAST_FORWARD_OUTLINE : MaterialDesignGlyphs(0xF06D2)

    /**
     * 󰛓 (0xF06D3)
     */
    public data object FEATHER : MaterialDesignGlyphs(0xF06D3)

    /**
     * 󰛔 (0xF06D4)
     */
    public data object FIND_REPLACE : MaterialDesignGlyphs(0xF06D4)

    /**
     * 󰛕 (0xF06D5)
     */
    public data object FLASH_OUTLINE : MaterialDesignGlyphs(0xF06D5)

    /**
     * 󰛖 (0xF06D6)
     */
    public data object FORMAT_FONT : MaterialDesignGlyphs(0xF06D6)

    /**
     * 󰛗 (0xF06D7)
     */
    public data object FORMAT_PAGE_BREAK : MaterialDesignGlyphs(0xF06D7)

    /**
     * 󰛘 (0xF06D8)
     */
    public data object FORMAT_PILCROW : MaterialDesignGlyphs(0xF06D8)

    /**
     * 󰛙 (0xF06D9)
     */
    public data object GARAGE : MaterialDesignGlyphs(0xF06D9)

    /**
     * 󰛚 (0xF06DA)
     */
    public data object GARAGE_OPEN : MaterialDesignGlyphs(0xF06DA)

    /**
     * 󰛛 (0xF06DB)
     */
    public data object CARD_ACCOUNT_DETAILS_STAR_OUTLINE : MaterialDesignGlyphs(0xF06DB)

    /**
     * 󰛜 (0xF06DC)
     */
    public data object GOOGLE_KEEP : MaterialDesignGlyphs(0xF06DC)

    /**
     * 󰛝 (0xF06DD)
     */
    public data object SNOWMOBILE : MaterialDesignGlyphs(0xF06DD)

    /**
     * 󰛞 (0xF06DE)
     */
    public data object HEART_HALF_FULL : MaterialDesignGlyphs(0xF06DE)

    /**
     * 󰛟 (0xF06DF)
     */
    public data object HEART_HALF : MaterialDesignGlyphs(0xF06DF)

    /**
     * 󰛠 (0xF06E0)
     */
    public data object HEART_HALF_OUTLINE : MaterialDesignGlyphs(0xF06E0)

    /**
     * 󰛡 (0xF06E1)
     */
    public data object HEXAGON_MULTIPLE : MaterialDesignGlyphs(0xF06E1)

    /**
     * 󰛢 (0xF06E2)
     */
    public data object HOOK : MaterialDesignGlyphs(0xF06E2)

    /**
     * 󰛣 (0xF06E3)
     */
    public data object HOOK_OFF : MaterialDesignGlyphs(0xF06E3)

    /**
     * 󰛤 (0xF06E4)
     */
    public data object INFINITY : MaterialDesignGlyphs(0xF06E4)

    /**
     * 󰛥 (0xF06E5)
     */
    public data object LANGUAGE_SWIFT : MaterialDesignGlyphs(0xF06E5)

    /**
     * 󰛦 (0xF06E6)
     */
    public data object LANGUAGE_TYPESCRIPT : MaterialDesignGlyphs(0xF06E6)

    /**
     * 󰛧 (0xF06E7)
     */
    public data object LAPTOP_OFF : MaterialDesignGlyphs(0xF06E7)

    /**
     * 󰛨 (0xF06E8)
     */
    public data object LIGHTBULB_ON : MaterialDesignGlyphs(0xF06E8)

    /**
     * 󰛩 (0xF06E9)
     */
    public data object LIGHTBULB_ON_OUTLINE : MaterialDesignGlyphs(0xF06E9)

    /**
     * 󰛪 (0xF06EA)
     */
    public data object LOCK_PATTERN : MaterialDesignGlyphs(0xF06EA)

    /**
     * 󰛫 (0xF06EB)
     */
    public data object FOLDER_ZIP : MaterialDesignGlyphs(0xF06EB)

    /**
     * 󰛬 (0xF06EC)
     */
    public data object MAGNIFY_MINUS_OUTLINE : MaterialDesignGlyphs(0xF06EC)

    /**
     * 󰛭 (0xF06ED)
     */
    public data object MAGNIFY_PLUS_OUTLINE : MaterialDesignGlyphs(0xF06ED)

    /**
     * 󰛮 (0xF06EE)
     */
    public data object MAILBOX : MaterialDesignGlyphs(0xF06EE)

    /**
     * 󰛯 (0xF06EF)
     */
    public data object MEDICAL_BAG : MaterialDesignGlyphs(0xF06EF)

    /**
     * 󰛰 (0xF06F0)
     */
    public data object MESSAGE_SETTINGS : MaterialDesignGlyphs(0xF06F0)

    /**
     * 󰛱 (0xF06F1)
     */
    public data object MESSAGE_COG : MaterialDesignGlyphs(0xF06F1)

    /**
     * 󰛲 (0xF06F2)
     */
    public data object MINUS_BOX_OUTLINE : MaterialDesignGlyphs(0xF06F2)

    /**
     * 󰛳 (0xF06F3)
     */
    public data object NETWORK : MaterialDesignGlyphs(0xF06F3)

    /**
     * 󰛴 (0xF06F4)
     */
    public data object DOWNLOAD_NETWORK : MaterialDesignGlyphs(0xF06F4)

    /**
     * 󰛵 (0xF06F5)
     */
    public data object HELP_NETWORK : MaterialDesignGlyphs(0xF06F5)

    /**
     * 󰛶 (0xF06F6)
     */
    public data object UPLOAD_NETWORK : MaterialDesignGlyphs(0xF06F6)

    /**
     * 󰛷 (0xF06F7)
     */
    public data object NPM : MaterialDesignGlyphs(0xF06F7)

    /**
     * 󰛸 (0xF06F8)
     */
    public data object NUT : MaterialDesignGlyphs(0xF06F8)

    /**
     * 󰛹 (0xF06F9)
     */
    public data object OCTAGRAM : MaterialDesignGlyphs(0xF06F9)

    /**
     * 󰛺 (0xF06FA)
     */
    public data object PAGE_LAYOUT_BODY : MaterialDesignGlyphs(0xF06FA)

    /**
     * 󰛻 (0xF06FB)
     */
    public data object PAGE_LAYOUT_FOOTER : MaterialDesignGlyphs(0xF06FB)

    /**
     * 󰛼 (0xF06FC)
     */
    public data object PAGE_LAYOUT_HEADER : MaterialDesignGlyphs(0xF06FC)

    /**
     * 󰛽 (0xF06FD)
     */
    public data object PAGE_LAYOUT_SIDEBAR_LEFT : MaterialDesignGlyphs(0xF06FD)

    /**
     * 󰛾 (0xF06FE)
     */
    public data object PAGE_LAYOUT_SIDEBAR_RIGHT : MaterialDesignGlyphs(0xF06FE)

    /**
     * 󰛿 (0xF06FF)
     */
    public data object PENCIL_CIRCLE : MaterialDesignGlyphs(0xF06FF)

    /**
     * 󰜀 (0xF0700)
     */
    public data object PENTAGON_OUTLINE : MaterialDesignGlyphs(0xF0700)

    /**
     * 󰜁 (0xF0701)
     */
    public data object PENTAGON : MaterialDesignGlyphs(0xF0701)

    /**
     * 󰜂 (0xF0702)
     */
    public data object PILLAR : MaterialDesignGlyphs(0xF0702)

    /**
     * 󰜃 (0xF0703)
     */
    public data object PISTOL : MaterialDesignGlyphs(0xF0703)

    /**
     * 󰜄 (0xF0704)
     */
    public data object PLUS_BOX_OUTLINE : MaterialDesignGlyphs(0xF0704)

    /**
     * 󰜅 (0xF0705)
     */
    public data object PLUS_OUTLINE : MaterialDesignGlyphs(0xF0705)

    /**
     * 󰜆 (0xF0706)
     */
    public data object PRESCRIPTION : MaterialDesignGlyphs(0xF0706)

    /**
     * 󰜇 (0xF0707)
     */
    public data object PRINTER_SETTINGS : MaterialDesignGlyphs(0xF0707)

    /**
     * 󰜈 (0xF0708)
     */
    public data object REACT : MaterialDesignGlyphs(0xF0708)

    /**
     * 󰜉 (0xF0709)
     */
    public data object RESTART : MaterialDesignGlyphs(0xF0709)

    /**
     * 󰜊 (0xF070A)
     */
    public data object REWIND_OUTLINE : MaterialDesignGlyphs(0xF070A)

    /**
     * 󰜋 (0xF070B)
     */
    public data object RHOMBUS : MaterialDesignGlyphs(0xF070B)

    /**
     * 󰜌 (0xF070C)
     */
    public data object RHOMBUS_OUTLINE : MaterialDesignGlyphs(0xF070C)

    /**
     * 󰜍 (0xF070D)
     */
    public data object ROBOT_VACUUM : MaterialDesignGlyphs(0xF070D)

    /**
     * 󰜎 (0xF070E)
     */
    public data object RUN : MaterialDesignGlyphs(0xF070E)

    /**
     * 󰜏 (0xF070F)
     */
    public data object SEARCH_WEB : MaterialDesignGlyphs(0xF070F)

    /**
     * 󰜐 (0xF0710)
     */
    public data object SHOVEL : MaterialDesignGlyphs(0xF0710)

    /**
     * 󰜑 (0xF0711)
     */
    public data object SHOVEL_OFF : MaterialDesignGlyphs(0xF0711)

    /**
     * 󰜒 (0xF0712)
     */
    public data object SIGNAL_2G : MaterialDesignGlyphs(0xF0712)

    /**
     * 󰜓 (0xF0713)
     */
    public data object SIGNAL_3G : MaterialDesignGlyphs(0xF0713)

    /**
     * 󰜔 (0xF0714)
     */
    public data object SIGNAL_4G : MaterialDesignGlyphs(0xF0714)

    /**
     * 󰜕 (0xF0715)
     */
    public data object SIGNAL_HSPA : MaterialDesignGlyphs(0xF0715)

    /**
     * 󰜖 (0xF0716)
     */
    public data object SIGNAL_HSPA_PLUS : MaterialDesignGlyphs(0xF0716)

    /**
     * 󰜗 (0xF0717)
     */
    public data object SNOWFLAKE : MaterialDesignGlyphs(0xF0717)

    /**
     * 󰜘 (0xF0718)
     */
    public data object SOURCE_COMMIT : MaterialDesignGlyphs(0xF0718)

    /**
     * 󰜙 (0xF0719)
     */
    public data object SOURCE_COMMIT_END : MaterialDesignGlyphs(0xF0719)

    /**
     * 󰜚 (0xF071A)
     */
    public data object SOURCE_COMMIT_END_LOCAL : MaterialDesignGlyphs(0xF071A)

    /**
     * 󰜛 (0xF071B)
     */
    public data object SOURCE_COMMIT_LOCAL : MaterialDesignGlyphs(0xF071B)

    /**
     * 󰜜 (0xF071C)
     */
    public data object SOURCE_COMMIT_NEXT_LOCAL : MaterialDesignGlyphs(0xF071C)

    /**
     * 󰜝 (0xF071D)
     */
    public data object SOURCE_COMMIT_START : MaterialDesignGlyphs(0xF071D)

    /**
     * 󰜞 (0xF071E)
     */
    public data object SOURCE_COMMIT_START_NEXT_LOCAL : MaterialDesignGlyphs(0xF071E)

    /**
     * 󰜟 (0xF071F)
     */
    public data object SPEAKER_WIRELESS : MaterialDesignGlyphs(0xF071F)

    /**
     * 󰜠 (0xF0720)
     */
    public data object STADIUM_VARIANT : MaterialDesignGlyphs(0xF0720)

    /**
     * 󰜡 (0xF0721)
     */
    public data object SVG : MaterialDesignGlyphs(0xF0721)

    /**
     * 󰜢 (0xF0722)
     */
    public data object TAG_PLUS : MaterialDesignGlyphs(0xF0722)

    /**
     * 󰜣 (0xF0723)
     */
    public data object TAG_REMOVE : MaterialDesignGlyphs(0xF0723)

    /**
     * 󰜤 (0xF0724)
     */
    public data object TICKET_PERCENT : MaterialDesignGlyphs(0xF0724)

    /**
     * 󰜥 (0xF0725)
     */
    public data object TILDE : MaterialDesignGlyphs(0xF0725)

    /**
     * 󰜦 (0xF0726)
     */
    public data object TREASURE_CHEST : MaterialDesignGlyphs(0xF0726)

    /**
     * 󰜧 (0xF0727)
     */
    public data object TRUCK_TRAILER : MaterialDesignGlyphs(0xF0727)

    /**
     * 󰜨 (0xF0728)
     */
    public data object VIEW_PARALLEL : MaterialDesignGlyphs(0xF0728)

    /**
     * 󰜩 (0xF0729)
     */
    public data object VIEW_SEQUENTIAL : MaterialDesignGlyphs(0xF0729)

    /**
     * 󰜪 (0xF072A)
     */
    public data object WASHING_MACHINE : MaterialDesignGlyphs(0xF072A)

    /**
     * 󰜫 (0xF072B)
     */
    public data object WEBPACK : MaterialDesignGlyphs(0xF072B)

    /**
     * 󰜬 (0xF072C)
     */
    public data object WIDGETS : MaterialDesignGlyphs(0xF072C)

    /**
     * 󰜭 (0xF072D)
     */
    public data object NINTENDO_WIIU : MaterialDesignGlyphs(0xF072D)

    /**
     * 󰜮 (0xF072E)
     */
    public data object ARROW_DOWN_BOLD : MaterialDesignGlyphs(0xF072E)

    /**
     * 󰜯 (0xF072F)
     */
    public data object ARROW_DOWN_BOLD_BOX : MaterialDesignGlyphs(0xF072F)

    /**
     * 󰜰 (0xF0730)
     */
    public data object ARROW_DOWN_BOLD_BOX_OUTLINE : MaterialDesignGlyphs(0xF0730)

    /**
     * 󰜱 (0xF0731)
     */
    public data object ARROW_LEFT_BOLD : MaterialDesignGlyphs(0xF0731)

    /**
     * 󰜲 (0xF0732)
     */
    public data object ARROW_LEFT_BOLD_BOX : MaterialDesignGlyphs(0xF0732)

    /**
     * 󰜳 (0xF0733)
     */
    public data object ARROW_LEFT_BOLD_BOX_OUTLINE : MaterialDesignGlyphs(0xF0733)

    /**
     * 󰜴 (0xF0734)
     */
    public data object ARROW_RIGHT_BOLD : MaterialDesignGlyphs(0xF0734)

    /**
     * 󰜵 (0xF0735)
     */
    public data object ARROW_RIGHT_BOLD_BOX : MaterialDesignGlyphs(0xF0735)

    /**
     * 󰜶 (0xF0736)
     */
    public data object ARROW_RIGHT_BOLD_BOX_OUTLINE : MaterialDesignGlyphs(0xF0736)

    /**
     * 󰜷 (0xF0737)
     */
    public data object ARROW_UP_BOLD : MaterialDesignGlyphs(0xF0737)

    /**
     * 󰜸 (0xF0738)
     */
    public data object ARROW_UP_BOLD_BOX : MaterialDesignGlyphs(0xF0738)

    /**
     * 󰜹 (0xF0739)
     */
    public data object ARROW_UP_BOLD_BOX_OUTLINE : MaterialDesignGlyphs(0xF0739)

    /**
     * 󰜺 (0xF073A)
     */
    public data object CANCEL : MaterialDesignGlyphs(0xF073A)

    /**
     * 󰜻 (0xF073B)
     */
    public data object FILE_ACCOUNT : MaterialDesignGlyphs(0xF073B)

    /**
     * 󰜼 (0xF073C)
     */
    public data object GESTURE_DOUBLE_TAP : MaterialDesignGlyphs(0xF073C)

    /**
     * 󰜽 (0xF073D)
     */
    public data object GESTURE_SWIPE_DOWN : MaterialDesignGlyphs(0xF073D)

    /**
     * 󰜾 (0xF073E)
     */
    public data object GESTURE_SWIPE_LEFT : MaterialDesignGlyphs(0xF073E)

    /**
     * 󰜿 (0xF073F)
     */
    public data object GESTURE_SWIPE_RIGHT : MaterialDesignGlyphs(0xF073F)

    /**
     * 󰝀 (0xF0740)
     */
    public data object GESTURE_SWIPE_UP : MaterialDesignGlyphs(0xF0740)

    /**
     * 󰝁 (0xF0741)
     */
    public data object GESTURE_TAP : MaterialDesignGlyphs(0xF0741)

    /**
     * 󰝂 (0xF0742)
     */
    public data object GESTURE_TWO_DOUBLE_TAP : MaterialDesignGlyphs(0xF0742)

    /**
     * 󰝃 (0xF0743)
     */
    public data object GESTURE_TWO_TAP : MaterialDesignGlyphs(0xF0743)

    /**
     * 󰝄 (0xF0744)
     */
    public data object HUMBLE_BUNDLE : MaterialDesignGlyphs(0xF0744)

    /**
     * 󰝅 (0xF0745)
     */
    public data object KICKSTARTER : MaterialDesignGlyphs(0xF0745)

    /**
     * 󰝆 (0xF0746)
     */
    public data object NETFLIX : MaterialDesignGlyphs(0xF0746)

    /**
     * 󰝇 (0xF0747)
     */
    public data object MICROSOFT_ONENOTE : MaterialDesignGlyphs(0xF0747)

    /**
     * 󰝈 (0xF0748)
     */
    public data object WALL_SCONCE_ROUND : MaterialDesignGlyphs(0xF0748)

    /**
     * 󰝉 (0xF0749)
     */
    public data object FOLDER_REFRESH : MaterialDesignGlyphs(0xF0749)

    /**
     * 󰝊 (0xF074A)
     */
    public data object VECTOR_RADIUS : MaterialDesignGlyphs(0xF074A)

    /**
     * 󰝋 (0xF074B)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_BATTERY_ALERT : MaterialDesignGlyphs(0xF074B)

    /**
     * 󰝌 (0xF074C)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_BATTERY_EMPTY : MaterialDesignGlyphs(0xF074C)

    /**
     * 󰝍 (0xF074D)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_BATTERY_FULL : MaterialDesignGlyphs(0xF074D)

    /**
     * 󰝎 (0xF074E)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_BATTERY_LOW : MaterialDesignGlyphs(0xF074E)

    /**
     * 󰝏 (0xF074F)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_BATTERY_MEDIUM : MaterialDesignGlyphs(0xF074F)

    /**
     * 󰝐 (0xF0750)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_BATTERY_UNKNOWN : MaterialDesignGlyphs(0xF0750)

    /**
     * 󰝑 (0xF0751)
     */
    public data object CLIPBOARD_PLUS : MaterialDesignGlyphs(0xF0751)

    /**
     * 󰝒 (0xF0752)
     */
    public data object FILE_PLUS : MaterialDesignGlyphs(0xF0752)

    /**
     * 󰝓 (0xF0753)
     */
    public data object FORMAT_ALIGN_BOTTOM : MaterialDesignGlyphs(0xF0753)

    /**
     * 󰝔 (0xF0754)
     */
    public data object FORMAT_ALIGN_MIDDLE : MaterialDesignGlyphs(0xF0754)

    /**
     * 󰝕 (0xF0755)
     */
    public data object FORMAT_ALIGN_TOP : MaterialDesignGlyphs(0xF0755)

    /**
     * 󰝖 (0xF0756)
     */
    public data object FORMAT_LIST_CHECKS : MaterialDesignGlyphs(0xF0756)

    /**
     * 󰝗 (0xF0757)
     */
    public data object FORMAT_QUOTE_OPEN : MaterialDesignGlyphs(0xF0757)

    /**
     * 󰝘 (0xF0758)
     */
    public data object GRID_LARGE : MaterialDesignGlyphs(0xF0758)

    /**
     * 󰝙 (0xF0759)
     */
    public data object HEART_OFF : MaterialDesignGlyphs(0xF0759)

    /**
     * 󰝚 (0xF075A)
     */
    public data object MUSIC : MaterialDesignGlyphs(0xF075A)

    /**
     * 󰝛 (0xF075B)
     */
    public data object MUSIC_OFF : MaterialDesignGlyphs(0xF075B)

    /**
     * 󰝜 (0xF075C)
     */
    public data object TAB_PLUS : MaterialDesignGlyphs(0xF075C)

    /**
     * 󰝝 (0xF075D)
     */
    public data object VOLUME_PLUS : MaterialDesignGlyphs(0xF075D)

    /**
     * 󰝞 (0xF075E)
     */
    public data object VOLUME_MINUS : MaterialDesignGlyphs(0xF075E)

    /**
     * 󰝟 (0xF075F)
     */
    public data object VOLUME_MUTE : MaterialDesignGlyphs(0xF075F)

    /**
     * 󰝠 (0xF0760)
     */
    public data object UNFOLD_LESS_VERTICAL : MaterialDesignGlyphs(0xF0760)

    /**
     * 󰝡 (0xF0761)
     */
    public data object UNFOLD_MORE_VERTICAL : MaterialDesignGlyphs(0xF0761)

    /**
     * 󰝢 (0xF0762)
     */
    public data object TACO : MaterialDesignGlyphs(0xF0762)

    /**
     * 󰝣 (0xF0763)
     */
    public data object SQUARE_OUTLINE : MaterialDesignGlyphs(0xF0763)

    /**
     * 󰝤 (0xF0764)
     */
    public data object SQUARE : MaterialDesignGlyphs(0xF0764)

    /**
     * 󰝧 (0xF0767)
     */
    public data object ALERT_OCTAGRAM : MaterialDesignGlyphs(0xF0767)

    /**
     * 󰝨 (0xF0768)
     */
    public data object ATOM : MaterialDesignGlyphs(0xF0768)

    /**
     * 󰝩 (0xF0769)
     */
    public data object CEILING_LIGHT : MaterialDesignGlyphs(0xF0769)

    /**
     * 󰝪 (0xF076A)
     */
    public data object CHART_BAR_STACKED : MaterialDesignGlyphs(0xF076A)

    /**
     * 󰝫 (0xF076B)
     */
    public data object CHART_LINE_STACKED : MaterialDesignGlyphs(0xF076B)

    /**
     * 󰝬 (0xF076C)
     */
    public data object DECAGRAM : MaterialDesignGlyphs(0xF076C)

    /**
     * 󰝭 (0xF076D)
     */
    public data object DECAGRAM_OUTLINE : MaterialDesignGlyphs(0xF076D)

    /**
     * 󰝮 (0xF076E)
     */
    public data object DICE_MULTIPLE : MaterialDesignGlyphs(0xF076E)

    /**
     * 󰝯 (0xF076F)
     */
    public data object DICE_D10_OUTLINE : MaterialDesignGlyphs(0xF076F)

    /**
     * 󰝰 (0xF0770)
     */
    public data object FOLDER_OPEN : MaterialDesignGlyphs(0xF0770)

    /**
     * 󰝱 (0xF0771)
     */
    public data object GUITAR_ACOUSTIC : MaterialDesignGlyphs(0xF0771)

    /**
     * 󰝲 (0xF0772)
     */
    public data object LOADING : MaterialDesignGlyphs(0xF0772)

    /**
     * 󰝳 (0xF0773)
     */
    public data object LOCK_RESET : MaterialDesignGlyphs(0xF0773)

    /**
     * 󰝴 (0xF0774)
     */
    public data object NINJA : MaterialDesignGlyphs(0xF0774)

    /**
     * 󰝵 (0xF0775)
     */
    public data object OCTAGRAM_OUTLINE : MaterialDesignGlyphs(0xF0775)

    /**
     * 󰝶 (0xF0776)
     */
    public data object PENCIL_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0776)

    /**
     * 󰝷 (0xF0777)
     */
    public data object SELECTION_OFF : MaterialDesignGlyphs(0xF0777)

    /**
     * 󰝸 (0xF0778)
     */
    public data object SET_ALL : MaterialDesignGlyphs(0xF0778)

    /**
     * 󰝹 (0xF0779)
     */
    public data object SET_CENTER : MaterialDesignGlyphs(0xF0779)

    /**
     * 󰝺 (0xF077A)
     */
    public data object SET_CENTER_RIGHT : MaterialDesignGlyphs(0xF077A)

    /**
     * 󰝻 (0xF077B)
     */
    public data object SET_LEFT : MaterialDesignGlyphs(0xF077B)

    /**
     * 󰝼 (0xF077C)
     */
    public data object SET_LEFT_CENTER : MaterialDesignGlyphs(0xF077C)

    /**
     * 󰝽 (0xF077D)
     */
    public data object SET_LEFT_RIGHT : MaterialDesignGlyphs(0xF077D)

    /**
     * 󰝾 (0xF077E)
     */
    public data object SET_NONE : MaterialDesignGlyphs(0xF077E)

    /**
     * 󰝿 (0xF077F)
     */
    public data object SET_RIGHT : MaterialDesignGlyphs(0xF077F)

    /**
     * 󰞀 (0xF0780)
     */
    public data object SHIELD_HALF_FULL : MaterialDesignGlyphs(0xF0780)

    /**
     * 󰞁 (0xF0781)
     */
    public data object SIGN_DIRECTION : MaterialDesignGlyphs(0xF0781)

    /**
     * 󰞂 (0xF0782)
     */
    public data object SIGN_TEXT : MaterialDesignGlyphs(0xF0782)

    /**
     * 󰞃 (0xF0783)
     */
    public data object SIGNAL_OFF : MaterialDesignGlyphs(0xF0783)

    /**
     * 󰞄 (0xF0784)
     */
    public data object SQUARE_ROOT : MaterialDesignGlyphs(0xF0784)

    /**
     * 󰞅 (0xF0785)
     */
    public data object STICKER_EMOJI : MaterialDesignGlyphs(0xF0785)

    /**
     * 󰞆 (0xF0786)
     */
    public data object SUMMIT : MaterialDesignGlyphs(0xF0786)

    /**
     * 󰞇 (0xF0787)
     */
    public data object SWORD_CROSS : MaterialDesignGlyphs(0xF0787)

    /**
     * 󰞈 (0xF0788)
     */
    public data object TRUCK_FAST : MaterialDesignGlyphs(0xF0788)

    /**
     * 󰞉 (0xF0789)
     */
    public data object WEB_CHECK : MaterialDesignGlyphs(0xF0789)

    /**
     * 󰞊 (0xF078A)
     */
    public data object CAST_OFF : MaterialDesignGlyphs(0xF078A)

    /**
     * 󰞋 (0xF078B)
     */
    public data object HELP_BOX : MaterialDesignGlyphs(0xF078B)

    /**
     * 󰞌 (0xF078C)
     */
    public data object TIMER_SAND_FULL : MaterialDesignGlyphs(0xF078C)

    /**
     * 󰞍 (0xF078D)
     */
    public data object WAVES : MaterialDesignGlyphs(0xF078D)

    /**
     * 󰞎 (0xF078E)
     */
    public data object ALARM_BELL : MaterialDesignGlyphs(0xF078E)

    /**
     * 󰞏 (0xF078F)
     */
    public data object ALARM_LIGHT : MaterialDesignGlyphs(0xF078F)

    /**
     * 󰞐 (0xF0790)
     */
    public data object VIDEO_SWITCH_OUTLINE : MaterialDesignGlyphs(0xF0790)

    /**
     * 󰞑 (0xF0791)
     */
    public data object CHECK_DECAGRAM : MaterialDesignGlyphs(0xF0791)

    /**
     * 󰞒 (0xF0792)
     */
    public data object ARROW_COLLAPSE_DOWN : MaterialDesignGlyphs(0xF0792)

    /**
     * 󰞓 (0xF0793)
     */
    public data object ARROW_COLLAPSE_LEFT : MaterialDesignGlyphs(0xF0793)

    /**
     * 󰞔 (0xF0794)
     */
    public data object ARROW_COLLAPSE_RIGHT : MaterialDesignGlyphs(0xF0794)

    /**
     * 󰞕 (0xF0795)
     */
    public data object ARROW_COLLAPSE_UP : MaterialDesignGlyphs(0xF0795)

    /**
     * 󰞖 (0xF0796)
     */
    public data object ARROW_EXPAND_DOWN : MaterialDesignGlyphs(0xF0796)

    /**
     * 󰞗 (0xF0797)
     */
    public data object ARROW_EXPAND_LEFT : MaterialDesignGlyphs(0xF0797)

    /**
     * 󰞘 (0xF0798)
     */
    public data object ARROW_EXPAND_RIGHT : MaterialDesignGlyphs(0xF0798)

    /**
     * 󰞙 (0xF0799)
     */
    public data object ARROW_EXPAND_UP : MaterialDesignGlyphs(0xF0799)

    /**
     * 󰞚 (0xF079A)
     */
    public data object BOOK_LOCK : MaterialDesignGlyphs(0xF079A)

    /**
     * 󰞛 (0xF079B)
     */
    public data object BOOK_LOCK_OPEN : MaterialDesignGlyphs(0xF079B)

    /**
     * 󰞜 (0xF079C)
     */
    public data object BUS_ARTICULATED_END : MaterialDesignGlyphs(0xF079C)

    /**
     * 󰞝 (0xF079D)
     */
    public data object BUS_ARTICULATED_FRONT : MaterialDesignGlyphs(0xF079D)

    /**
     * 󰞞 (0xF079E)
     */
    public data object BUS_DOUBLE_DECKER : MaterialDesignGlyphs(0xF079E)

    /**
     * 󰞟 (0xF079F)
     */
    public data object BUS_SCHOOL : MaterialDesignGlyphs(0xF079F)

    /**
     * 󰞠 (0xF07A0)
     */
    public data object BUS_SIDE : MaterialDesignGlyphs(0xF07A0)

    /**
     * 󰞡 (0xF07A1)
     */
    public data object CAMERA_GOPRO : MaterialDesignGlyphs(0xF07A1)

    /**
     * 󰞢 (0xF07A2)
     */
    public data object CAMERA_METERING_CENTER : MaterialDesignGlyphs(0xF07A2)

    /**
     * 󰞣 (0xF07A3)
     */
    public data object CAMERA_METERING_MATRIX : MaterialDesignGlyphs(0xF07A3)

    /**
     * 󰞤 (0xF07A4)
     */
    public data object CAMERA_METERING_PARTIAL : MaterialDesignGlyphs(0xF07A4)

    /**
     * 󰞥 (0xF07A5)
     */
    public data object CAMERA_METERING_SPOT : MaterialDesignGlyphs(0xF07A5)

    /**
     * 󰞦 (0xF07A6)
     */
    public data object CANNABIS : MaterialDesignGlyphs(0xF07A6)

    /**
     * 󰞧 (0xF07A7)
     */
    public data object CAR_CONVERTIBLE : MaterialDesignGlyphs(0xF07A7)

    /**
     * 󰞨 (0xF07A8)
     */
    public data object CAR_ESTATE : MaterialDesignGlyphs(0xF07A8)

    /**
     * 󰞩 (0xF07A9)
     */
    public data object CAR_HATCHBACK : MaterialDesignGlyphs(0xF07A9)

    /**
     * 󰞪 (0xF07AA)
     */
    public data object CAR_PICKUP : MaterialDesignGlyphs(0xF07AA)

    /**
     * 󰞫 (0xF07AB)
     */
    public data object CAR_SIDE : MaterialDesignGlyphs(0xF07AB)

    /**
     * 󰞬 (0xF07AC)
     */
    public data object CAR_SPORTS : MaterialDesignGlyphs(0xF07AC)

    /**
     * 󰞭 (0xF07AD)
     */
    public data object CARAVAN : MaterialDesignGlyphs(0xF07AD)

    /**
     * 󰞮 (0xF07AE)
     */
    public data object CCTV : MaterialDesignGlyphs(0xF07AE)

    /**
     * 󰞯 (0xF07AF)
     */
    public data object CHART_DONUT : MaterialDesignGlyphs(0xF07AF)

    /**
     * 󰞰 (0xF07B0)
     */
    public data object CHART_DONUT_VARIANT : MaterialDesignGlyphs(0xF07B0)

    /**
     * 󰞱 (0xF07B1)
     */
    public data object CHART_LINE_VARIANT : MaterialDesignGlyphs(0xF07B1)

    /**
     * 󰞲 (0xF07B2)
     */
    public data object CHILI_HOT : MaterialDesignGlyphs(0xF07B2)

    /**
     * 󰞳 (0xF07B3)
     */
    public data object CHILI_MEDIUM : MaterialDesignGlyphs(0xF07B3)

    /**
     * 󰞴 (0xF07B4)
     */
    public data object CHILI_MILD : MaterialDesignGlyphs(0xF07B4)

    /**
     * 󰞵 (0xF07B5)
     */
    public data object CLOUD_BRACES : MaterialDesignGlyphs(0xF07B5)

    /**
     * 󰞶 (0xF07B6)
     */
    public data object CLOUD_TAGS : MaterialDesignGlyphs(0xF07B6)

    /**
     * 󰞷 (0xF07B7)
     */
    public data object CONSOLE_LINE : MaterialDesignGlyphs(0xF07B7)

    /**
     * 󰞸 (0xF07B8)
     */
    public data object CORN : MaterialDesignGlyphs(0xF07B8)

    /**
     * 󰞹 (0xF07B9)
     */
    public data object FOLDER_ZIP_OUTLINE : MaterialDesignGlyphs(0xF07B9)

    /**
     * 󰞺 (0xF07BA)
     */
    public data object CURRENCY_CNY : MaterialDesignGlyphs(0xF07BA)

    /**
     * 󰞻 (0xF07BB)
     */
    public data object CURRENCY_ETH : MaterialDesignGlyphs(0xF07BB)

    /**
     * 󰞼 (0xF07BC)
     */
    public data object CURRENCY_JPY : MaterialDesignGlyphs(0xF07BC)

    /**
     * 󰞽 (0xF07BD)
     */
    public data object CURRENCY_KRW : MaterialDesignGlyphs(0xF07BD)

    /**
     * 󰞾 (0xF07BE)
     */
    public data object CURRENCY_SIGN : MaterialDesignGlyphs(0xF07BE)

    /**
     * 󰞿 (0xF07BF)
     */
    public data object CURRENCY_TWD : MaterialDesignGlyphs(0xF07BF)

    /**
     * 󰟀 (0xF07C0)
     */
    public data object DESKTOP_CLASSIC : MaterialDesignGlyphs(0xF07C0)

    /**
     * 󰟁 (0xF07C1)
     */
    public data object DIP_SWITCH : MaterialDesignGlyphs(0xF07C1)

    /**
     * 󰟂 (0xF07C2)
     */
    public data object DONKEY : MaterialDesignGlyphs(0xF07C2)

    /**
     * 󰟃 (0xF07C3)
     */
    public data object DOTS_HORIZONTAL_CIRCLE : MaterialDesignGlyphs(0xF07C3)

    /**
     * 󰟄 (0xF07C4)
     */
    public data object DOTS_VERTICAL_CIRCLE : MaterialDesignGlyphs(0xF07C4)

    /**
     * 󰟅 (0xF07C5)
     */
    public data object EAR_HEARING : MaterialDesignGlyphs(0xF07C5)

    /**
     * 󰟆 (0xF07C6)
     */
    public data object ELEPHANT : MaterialDesignGlyphs(0xF07C6)

    /**
     * 󰟇 (0xF07C7)
     */
    public data object STOREFRONT : MaterialDesignGlyphs(0xF07C7)

    /**
     * 󰟈 (0xF07C8)
     */
    public data object FOOD_CROISSANT : MaterialDesignGlyphs(0xF07C8)

    /**
     * 󰟉 (0xF07C9)
     */
    public data object FORKLIFT : MaterialDesignGlyphs(0xF07C9)

    /**
     * 󰟊 (0xF07CA)
     */
    public data object FUEL : MaterialDesignGlyphs(0xF07CA)

    /**
     * 󰟋 (0xF07CB)
     */
    public data object GESTURE : MaterialDesignGlyphs(0xF07CB)

    /**
     * 󰟌 (0xF07CC)
     */
    public data object GOOGLE_ANALYTICS : MaterialDesignGlyphs(0xF07CC)

    /**
     * 󰟍 (0xF07CD)
     */
    public data object GOOGLE_ASSISTANT : MaterialDesignGlyphs(0xF07CD)

    /**
     * 󰟎 (0xF07CE)
     */
    public data object HEADPHONES_OFF : MaterialDesignGlyphs(0xF07CE)

    /**
     * 󰟏 (0xF07CF)
     */
    public data object HIGH_DEFINITION : MaterialDesignGlyphs(0xF07CF)

    /**
     * 󰟐 (0xF07D0)
     */
    public data object HOME_ASSISTANT : MaterialDesignGlyphs(0xF07D0)

    /**
     * 󰟑 (0xF07D1)
     */
    public data object HOME_AUTOMATION : MaterialDesignGlyphs(0xF07D1)

    /**
     * 󰟒 (0xF07D2)
     */
    public data object HOME_CIRCLE : MaterialDesignGlyphs(0xF07D2)

    /**
     * 󰟓 (0xF07D3)
     */
    public data object LANGUAGE_GO : MaterialDesignGlyphs(0xF07D3)

    /**
     * 󰟔 (0xF07D4)
     */
    public data object LANGUAGE_R : MaterialDesignGlyphs(0xF07D4)

    /**
     * 󰟕 (0xF07D5)
     */
    public data object LAVA_LAMP : MaterialDesignGlyphs(0xF07D5)

    /**
     * 󰟖 (0xF07D6)
     */
    public data object LED_STRIP : MaterialDesignGlyphs(0xF07D6)

    /**
     * 󰟗 (0xF07D7)
     */
    public data object LOCKER : MaterialDesignGlyphs(0xF07D7)

    /**
     * 󰟘 (0xF07D8)
     */
    public data object LOCKER_MULTIPLE : MaterialDesignGlyphs(0xF07D8)

    /**
     * 󰟙 (0xF07D9)
     */
    public data object MAP_MARKER_OUTLINE : MaterialDesignGlyphs(0xF07D9)

    /**
     * 󰟚 (0xF07DA)
     */
    public data object METRONOME : MaterialDesignGlyphs(0xF07DA)

    /**
     * 󰟛 (0xF07DB)
     */
    public data object METRONOME_TICK : MaterialDesignGlyphs(0xF07DB)

    /**
     * 󰟜 (0xF07DC)
     */
    public data object MICRO_SD : MaterialDesignGlyphs(0xF07DC)

    /**
     * 󰟝 (0xF07DD)
     */
    public data object FACEBOOK_GAMING : MaterialDesignGlyphs(0xF07DD)

    /**
     * 󰟞 (0xF07DE)
     */
    public data object MOVIE_ROLL : MaterialDesignGlyphs(0xF07DE)

    /**
     * 󰟟 (0xF07DF)
     */
    public data object MUSHROOM : MaterialDesignGlyphs(0xF07DF)

    /**
     * 󰟠 (0xF07E0)
     */
    public data object MUSHROOM_OUTLINE : MaterialDesignGlyphs(0xF07E0)

    /**
     * 󰟡 (0xF07E1)
     */
    public data object NINTENDO_SWITCH : MaterialDesignGlyphs(0xF07E1)

    /**
     * 󰟢 (0xF07E2)
     */
    public data object NULL : MaterialDesignGlyphs(0xF07E2)

    /**
     * 󰟣 (0xF07E3)
     */
    public data object PASSPORT : MaterialDesignGlyphs(0xF07E3)

    /**
     * 󰟤 (0xF07E4)
     */
    public data object MOLECULE_CO2 : MaterialDesignGlyphs(0xF07E4)

    /**
     * 󰟥 (0xF07E5)
     */
    public data object PIPE : MaterialDesignGlyphs(0xF07E5)

    /**
     * 󰟦 (0xF07E6)
     */
    public data object PIPE_DISCONNECTED : MaterialDesignGlyphs(0xF07E6)

    /**
     * 󰟧 (0xF07E7)
     */
    public data object POWER_SOCKET_EU : MaterialDesignGlyphs(0xF07E7)

    /**
     * 󰟨 (0xF07E8)
     */
    public data object POWER_SOCKET_UK : MaterialDesignGlyphs(0xF07E8)

    /**
     * 󰟩 (0xF07E9)
     */
    public data object POWER_SOCKET_US : MaterialDesignGlyphs(0xF07E9)

    /**
     * 󰟪 (0xF07EA)
     */
    public data object RICE : MaterialDesignGlyphs(0xF07EA)

    /**
     * 󰟫 (0xF07EB)
     */
    public data object RING : MaterialDesignGlyphs(0xF07EB)

    /**
     * 󰟬 (0xF07EC)
     */
    public data object SASS : MaterialDesignGlyphs(0xF07EC)

    /**
     * 󰟭 (0xF07ED)
     */
    public data object SEND_LOCK : MaterialDesignGlyphs(0xF07ED)

    /**
     * 󰟮 (0xF07EE)
     */
    public data object SOY_SAUCE : MaterialDesignGlyphs(0xF07EE)

    /**
     * 󰟯 (0xF07EF)
     */
    public data object STANDARD_DEFINITION : MaterialDesignGlyphs(0xF07EF)

    /**
     * 󰟰 (0xF07F0)
     */
    public data object SURROUND_SOUND_2_0 : MaterialDesignGlyphs(0xF07F0)

    /**
     * 󰟱 (0xF07F1)
     */
    public data object SURROUND_SOUND_3_1 : MaterialDesignGlyphs(0xF07F1)

    /**
     * 󰟲 (0xF07F2)
     */
    public data object SURROUND_SOUND_5_1 : MaterialDesignGlyphs(0xF07F2)

    /**
     * 󰟳 (0xF07F3)
     */
    public data object SURROUND_SOUND_7_1 : MaterialDesignGlyphs(0xF07F3)

    /**
     * 󰟴 (0xF07F4)
     */
    public data object TELEVISION_CLASSIC : MaterialDesignGlyphs(0xF07F4)

    /**
     * 󰟵 (0xF07F5)
     */
    public data object FORM_TEXTBOX_PASSWORD : MaterialDesignGlyphs(0xF07F5)

    /**
     * 󰟶 (0xF07F6)
     */
    public data object THOUGHT_BUBBLE : MaterialDesignGlyphs(0xF07F6)

    /**
     * 󰟷 (0xF07F7)
     */
    public data object THOUGHT_BUBBLE_OUTLINE : MaterialDesignGlyphs(0xF07F7)

    /**
     * 󰟸 (0xF07F8)
     */
    public data object TRACKPAD : MaterialDesignGlyphs(0xF07F8)

    /**
     * 󰟹 (0xF07F9)
     */
    public data object ULTRA_HIGH_DEFINITION : MaterialDesignGlyphs(0xF07F9)

    /**
     * 󰟺 (0xF07FA)
     */
    public data object VAN_PASSENGER : MaterialDesignGlyphs(0xF07FA)

    /**
     * 󰟻 (0xF07FB)
     */
    public data object VAN_UTILITY : MaterialDesignGlyphs(0xF07FB)

    /**
     * 󰟼 (0xF07FC)
     */
    public data object VANISH : MaterialDesignGlyphs(0xF07FC)

    /**
     * 󰟽 (0xF07FD)
     */
    public data object VIDEO_3D : MaterialDesignGlyphs(0xF07FD)

    /**
     * 󰟾 (0xF07FE)
     */
    public data object WALL : MaterialDesignGlyphs(0xF07FE)

    /**
     * 󰟿 (0xF07FF)
     */
    public data object XMPP : MaterialDesignGlyphs(0xF07FF)

    /**
     * 󰠀 (0xF0800)
     */
    public data object ACCOUNT_MULTIPLE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0800)

    /**
     * 󰠁 (0xF0801)
     */
    public data object ACCOUNT_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0801)

    /**
     * 󰠂 (0xF0802)
     */
    public data object CREDIT_CARD_WIRELESS : MaterialDesignGlyphs(0xF0802)

    /**
     * 󰠃 (0xF0803)
     */
    public data object ACCOUNT_MUSIC : MaterialDesignGlyphs(0xF0803)

    /**
     * 󰠄 (0xF0804)
     */
    public data object ATLASSIAN : MaterialDesignGlyphs(0xF0804)

    /**
     * 󰠅 (0xF0805)
     */
    public data object MICROSOFT_AZURE : MaterialDesignGlyphs(0xF0805)

    /**
     * 󰠆 (0xF0806)
     */
    public data object BASKETBALL : MaterialDesignGlyphs(0xF0806)

    /**
     * 󰠇 (0xF0807)
     */
    public data object BATTERY_CHARGING_WIRELESS : MaterialDesignGlyphs(0xF0807)

    /**
     * 󰠈 (0xF0808)
     */
    public data object BATTERY_CHARGING_WIRELESS_10 : MaterialDesignGlyphs(0xF0808)

    /**
     * 󰠉 (0xF0809)
     */
    public data object BATTERY_CHARGING_WIRELESS_20 : MaterialDesignGlyphs(0xF0809)

    /**
     * 󰠊 (0xF080A)
     */
    public data object BATTERY_CHARGING_WIRELESS_30 : MaterialDesignGlyphs(0xF080A)

    /**
     * 󰠋 (0xF080B)
     */
    public data object BATTERY_CHARGING_WIRELESS_40 : MaterialDesignGlyphs(0xF080B)

    /**
     * 󰠌 (0xF080C)
     */
    public data object BATTERY_CHARGING_WIRELESS_50 : MaterialDesignGlyphs(0xF080C)

    /**
     * 󰠍 (0xF080D)
     */
    public data object BATTERY_CHARGING_WIRELESS_60 : MaterialDesignGlyphs(0xF080D)

    /**
     * 󰠎 (0xF080E)
     */
    public data object BATTERY_CHARGING_WIRELESS_70 : MaterialDesignGlyphs(0xF080E)

    /**
     * 󰠏 (0xF080F)
     */
    public data object BATTERY_CHARGING_WIRELESS_80 : MaterialDesignGlyphs(0xF080F)

    /**
     * 󰠐 (0xF0810)
     */
    public data object BATTERY_CHARGING_WIRELESS_90 : MaterialDesignGlyphs(0xF0810)

    /**
     * 󰠑 (0xF0811)
     */
    public data object BATTERY_CHARGING_WIRELESS_ALERT : MaterialDesignGlyphs(0xF0811)

    /**
     * 󰠒 (0xF0812)
     */
    public data object BATTERY_CHARGING_WIRELESS_OUTLINE : MaterialDesignGlyphs(0xF0812)

    /**
     * 󰠓 (0xF0813)
     */
    public data object BITCOIN : MaterialDesignGlyphs(0xF0813)

    /**
     * 󰠔 (0xF0814)
     */
    public data object BRIEFCASE_OUTLINE : MaterialDesignGlyphs(0xF0814)

    /**
     * 󰠕 (0xF0815)
     */
    public data object CELLPHONE_WIRELESS : MaterialDesignGlyphs(0xF0815)

    /**
     * 󰠖 (0xF0816)
     */
    public data object CLOVER : MaterialDesignGlyphs(0xF0816)

    /**
     * 󰠗 (0xF0817)
     */
    public data object COMMENT_QUESTION : MaterialDesignGlyphs(0xF0817)

    /**
     * 󰠘 (0xF0818)
     */
    public data object CONTENT_SAVE_OUTLINE : MaterialDesignGlyphs(0xF0818)

    /**
     * 󰠙 (0xF0819)
     */
    public data object DELETE_RESTORE : MaterialDesignGlyphs(0xF0819)

    /**
     * 󰠚 (0xF081A)
     */
    public data object DOOR : MaterialDesignGlyphs(0xF081A)

    /**
     * 󰠛 (0xF081B)
     */
    public data object DOOR_CLOSED : MaterialDesignGlyphs(0xF081B)

    /**
     * 󰠜 (0xF081C)
     */
    public data object DOOR_OPEN : MaterialDesignGlyphs(0xF081C)

    /**
     * 󰠝 (0xF081D)
     */
    public data object FAN_OFF : MaterialDesignGlyphs(0xF081D)

    /**
     * 󰠞 (0xF081E)
     */
    public data object FILE_PERCENT : MaterialDesignGlyphs(0xF081E)

    /**
     * 󰠟 (0xF081F)
     */
    public data object FINANCE : MaterialDesignGlyphs(0xF081F)

    /**
     * 󰠠 (0xF0820)
     */
    public data object LIGHTNING_BOLT_CIRCLE : MaterialDesignGlyphs(0xF0820)

    /**
     * 󰠡 (0xF0821)
     */
    public data object FLOOR_PLAN : MaterialDesignGlyphs(0xF0821)

    /**
     * 󰠢 (0xF0822)
     */
    public data object FORUM_OUTLINE : MaterialDesignGlyphs(0xF0822)

    /**
     * 󰠣 (0xF0823)
     */
    public data object GOLF : MaterialDesignGlyphs(0xF0823)

    /**
     * 󰠤 (0xF0824)
     */
    public data object GOOGLE_HOME : MaterialDesignGlyphs(0xF0824)

    /**
     * 󰠥 (0xF0825)
     */
    public data object GUY_FAWKES_MASK : MaterialDesignGlyphs(0xF0825)

    /**
     * 󰠦 (0xF0826)
     */
    public data object HOME_ACCOUNT : MaterialDesignGlyphs(0xF0826)

    /**
     * 󰠧 (0xF0827)
     */
    public data object HOME_HEART : MaterialDesignGlyphs(0xF0827)

    /**
     * 󰠨 (0xF0828)
     */
    public data object HOT_TUB : MaterialDesignGlyphs(0xF0828)

    /**
     * 󰠩 (0xF0829)
     */
    public data object HULU : MaterialDesignGlyphs(0xF0829)

    /**
     * 󰠪 (0xF082A)
     */
    public data object ICE_CREAM : MaterialDesignGlyphs(0xF082A)

    /**
     * 󰠫 (0xF082B)
     */
    public data object IMAGE_OFF : MaterialDesignGlyphs(0xF082B)

    /**
     * 󰠬 (0xF082C)
     */
    public data object KARATE : MaterialDesignGlyphs(0xF082C)

    /**
     * 󰠭 (0xF082D)
     */
    public data object LADYBUG : MaterialDesignGlyphs(0xF082D)

    /**
     * 󰠮 (0xF082E)
     */
    public data object NOTEBOOK : MaterialDesignGlyphs(0xF082E)

    /**
     * 󰠯 (0xF082F)
     */
    public data object PHONE_RETURN : MaterialDesignGlyphs(0xF082F)

    /**
     * 󰠰 (0xF0830)
     */
    public data object POKER_CHIP : MaterialDesignGlyphs(0xF0830)

    /**
     * 󰠱 (0xF0831)
     */
    public data object SHAPE : MaterialDesignGlyphs(0xF0831)

    /**
     * 󰠲 (0xF0832)
     */
    public data object SHAPE_OUTLINE : MaterialDesignGlyphs(0xF0832)

    /**
     * 󰠳 (0xF0833)
     */
    public data object SHIP_WHEEL : MaterialDesignGlyphs(0xF0833)

    /**
     * 󰠴 (0xF0834)
     */
    public data object SOCCER_FIELD : MaterialDesignGlyphs(0xF0834)

    /**
     * 󰠵 (0xF0835)
     */
    public data object TABLE_COLUMN : MaterialDesignGlyphs(0xF0835)

    /**
     * 󰠶 (0xF0836)
     */
    public data object TABLE_OF_CONTENTS : MaterialDesignGlyphs(0xF0836)

    /**
     * 󰠷 (0xF0837)
     */
    public data object TABLE_ROW : MaterialDesignGlyphs(0xF0837)

    /**
     * 󰠸 (0xF0838)
     */
    public data object TABLE_SETTINGS : MaterialDesignGlyphs(0xF0838)

    /**
     * 󰠹 (0xF0839)
     */
    public data object TELEVISION_BOX : MaterialDesignGlyphs(0xF0839)

    /**
     * 󰠺 (0xF083A)
     */
    public data object TELEVISION_CLASSIC_OFF : MaterialDesignGlyphs(0xF083A)

    /**
     * 󰠻 (0xF083B)
     */
    public data object TELEVISION_OFF : MaterialDesignGlyphs(0xF083B)

    /**
     * 󰠼 (0xF083C)
     */
    public data object TOW_TRUCK : MaterialDesignGlyphs(0xF083C)

    /**
     * 󰠽 (0xF083D)
     */
    public data object UPLOAD_MULTIPLE : MaterialDesignGlyphs(0xF083D)

    /**
     * 󰠾 (0xF083E)
     */
    public data object VIDEO_4K_BOX : MaterialDesignGlyphs(0xF083E)

    /**
     * 󰠿 (0xF083F)
     */
    public data object VIDEO_INPUT_ANTENNA : MaterialDesignGlyphs(0xF083F)

    /**
     * 󰡀 (0xF0840)
     */
    public data object VIDEO_INPUT_COMPONENT : MaterialDesignGlyphs(0xF0840)

    /**
     * 󰡁 (0xF0841)
     */
    public data object VIDEO_INPUT_HDMI : MaterialDesignGlyphs(0xF0841)

    /**
     * 󰡂 (0xF0842)
     */
    public data object VIDEO_INPUT_SVIDEO : MaterialDesignGlyphs(0xF0842)

    /**
     * 󰡃 (0xF0843)
     */
    public data object VIEW_DASHBOARD_VARIANT : MaterialDesignGlyphs(0xF0843)

    /**
     * 󰡄 (0xF0844)
     */
    public data object VUEJS : MaterialDesignGlyphs(0xF0844)

    /**
     * 󰡅 (0xF0845)
     */
    public data object XAMARIN : MaterialDesignGlyphs(0xF0845)

    /**
     * 󰡆 (0xF0846)
     */
    public data object HUMAN_MALE_BOARD_POLL : MaterialDesignGlyphs(0xF0846)

    /**
     * 󰡇 (0xF0847)
     */
    public data object YOUTUBE_STUDIO : MaterialDesignGlyphs(0xF0847)

    /**
     * 󰡈 (0xF0848)
     */
    public data object YOUTUBE_GAMING : MaterialDesignGlyphs(0xF0848)

    /**
     * 󰡉 (0xF0849)
     */
    public data object ACCOUNT_GROUP : MaterialDesignGlyphs(0xF0849)

    /**
     * 󰡊 (0xF084A)
     */
    public data object CAMERA_SWITCH_OUTLINE : MaterialDesignGlyphs(0xF084A)

    /**
     * 󰡋 (0xF084B)
     */
    public data object AIRPORT : MaterialDesignGlyphs(0xF084B)

    /**
     * 󰡌 (0xF084C)
     */
    public data object ARROW_COLLAPSE_HORIZONTAL : MaterialDesignGlyphs(0xF084C)

    /**
     * 󰡍 (0xF084D)
     */
    public data object ARROW_COLLAPSE_VERTICAL : MaterialDesignGlyphs(0xF084D)

    /**
     * 󰡎 (0xF084E)
     */
    public data object ARROW_EXPAND_HORIZONTAL : MaterialDesignGlyphs(0xF084E)

    /**
     * 󰡏 (0xF084F)
     */
    public data object ARROW_EXPAND_VERTICAL : MaterialDesignGlyphs(0xF084F)

    /**
     * 󰡐 (0xF0850)
     */
    public data object AUGMENTED_REALITY : MaterialDesignGlyphs(0xF0850)

    /**
     * 󰡑 (0xF0851)
     */
    public data object BADMINTON : MaterialDesignGlyphs(0xF0851)

    /**
     * 󰡒 (0xF0852)
     */
    public data object BASEBALL : MaterialDesignGlyphs(0xF0852)

    /**
     * 󰡓 (0xF0853)
     */
    public data object BASEBALL_BAT : MaterialDesignGlyphs(0xF0853)

    /**
     * 󰡔 (0xF0854)
     */
    public data object BOTTLE_WINE : MaterialDesignGlyphs(0xF0854)

    /**
     * 󰡕 (0xF0855)
     */
    public data object CHECK_OUTLINE : MaterialDesignGlyphs(0xF0855)

    /**
     * 󰡖 (0xF0856)
     */
    public data object CHECKBOX_INTERMEDIATE : MaterialDesignGlyphs(0xF0856)

    /**
     * 󰡗 (0xF0857)
     */
    public data object CHESS_KING : MaterialDesignGlyphs(0xF0857)

    /**
     * 󰡘 (0xF0858)
     */
    public data object CHESS_KNIGHT : MaterialDesignGlyphs(0xF0858)

    /**
     * 󰡙 (0xF0859)
     */
    public data object CHESS_PAWN : MaterialDesignGlyphs(0xF0859)

    /**
     * 󰡚 (0xF085A)
     */
    public data object CHESS_QUEEN : MaterialDesignGlyphs(0xF085A)

    /**
     * 󰡛 (0xF085B)
     */
    public data object CHESS_ROOK : MaterialDesignGlyphs(0xF085B)

    /**
     * 󰡜 (0xF085C)
     */
    public data object CHESS_BISHOP : MaterialDesignGlyphs(0xF085C)

    /**
     * 󰡝 (0xF085D)
     */
    public data object CLIPBOARD_PULSE : MaterialDesignGlyphs(0xF085D)

    /**
     * 󰡞 (0xF085E)
     */
    public data object CLIPBOARD_PULSE_OUTLINE : MaterialDesignGlyphs(0xF085E)

    /**
     * 󰡟 (0xF085F)
     */
    public data object COMMENT_MULTIPLE : MaterialDesignGlyphs(0xF085F)

    /**
     * 󰡠 (0xF0860)
     */
    public data object COMMENT_TEXT_MULTIPLE : MaterialDesignGlyphs(0xF0860)

    /**
     * 󰡡 (0xF0861)
     */
    public data object COMMENT_TEXT_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0861)

    /**
     * 󰡢 (0xF0862)
     */
    public data object CRANE : MaterialDesignGlyphs(0xF0862)

    /**
     * 󰡣 (0xF0863)
     */
    public data object CURLING : MaterialDesignGlyphs(0xF0863)

    /**
     * 󰡤 (0xF0864)
     */
    public data object CURRENCY_BDT : MaterialDesignGlyphs(0xF0864)

    /**
     * 󰡥 (0xF0865)
     */
    public data object CURRENCY_KZT : MaterialDesignGlyphs(0xF0865)

    /**
     * 󰡦 (0xF0866)
     */
    public data object DATABASE_SEARCH : MaterialDesignGlyphs(0xF0866)

    /**
     * 󰡧 (0xF0867)
     */
    public data object DICE_D12_OUTLINE : MaterialDesignGlyphs(0xF0867)

    /**
     * 󰡨 (0xF0868)
     */
    public data object DOCKER : MaterialDesignGlyphs(0xF0868)

    /**
     * 󰡩 (0xF0869)
     */
    public data object DOORBELL_VIDEO : MaterialDesignGlyphs(0xF0869)

    /**
     * 󰡪 (0xF086A)
     */
    public data object ETHEREUM : MaterialDesignGlyphs(0xF086A)

    /**
     * 󰡫 (0xF086B)
     */
    public data object EYE_PLUS : MaterialDesignGlyphs(0xF086B)

    /**
     * 󰡬 (0xF086C)
     */
    public data object EYE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF086C)

    /**
     * 󰡭 (0xF086D)
     */
    public data object EYE_SETTINGS : MaterialDesignGlyphs(0xF086D)

    /**
     * 󰡮 (0xF086E)
     */
    public data object EYE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF086E)

    /**
     * 󰡯 (0xF086F)
     */
    public data object FILE_QUESTION : MaterialDesignGlyphs(0xF086F)

    /**
     * 󰡰 (0xF0870)
     */
    public data object FOLDER_NETWORK : MaterialDesignGlyphs(0xF0870)

    /**
     * 󰡱 (0xF0871)
     */
    public data object FUNCTION_VARIANT : MaterialDesignGlyphs(0xF0871)

    /**
     * 󰡲 (0xF0872)
     */
    public data object GARAGE_ALERT : MaterialDesignGlyphs(0xF0872)

    /**
     * 󰡳 (0xF0873)
     */
    public data object GAUGE_EMPTY : MaterialDesignGlyphs(0xF0873)

    /**
     * 󰡴 (0xF0874)
     */
    public data object GAUGE_FULL : MaterialDesignGlyphs(0xF0874)

    /**
     * 󰡵 (0xF0875)
     */
    public data object GAUGE_LOW : MaterialDesignGlyphs(0xF0875)

    /**
     * 󰡶 (0xF0876)
     */
    public data object GLASS_WINE : MaterialDesignGlyphs(0xF0876)

    /**
     * 󰡷 (0xF0877)
     */
    public data object GRAPHQL : MaterialDesignGlyphs(0xF0877)

    /**
     * 󰡸 (0xF0878)
     */
    public data object HIGH_DEFINITION_BOX : MaterialDesignGlyphs(0xF0878)

    /**
     * 󰡹 (0xF0879)
     */
    public data object HOCKEY_PUCK : MaterialDesignGlyphs(0xF0879)

    /**
     * 󰡺 (0xF087A)
     */
    public data object HOCKEY_STICKS : MaterialDesignGlyphs(0xF087A)

    /**
     * 󰡻 (0xF087B)
     */
    public data object HOME_ALERT : MaterialDesignGlyphs(0xF087B)

    /**
     * 󰡼 (0xF087C)
     */
    public data object IMAGE_PLUS : MaterialDesignGlyphs(0xF087C)

    /**
     * 󰡽 (0xF087D)
     */
    public data object JQUERY : MaterialDesignGlyphs(0xF087D)

    /**
     * 󰡾 (0xF087E)
     */
    public data object LIFEBUOY : MaterialDesignGlyphs(0xF087E)

    /**
     * 󰡿 (0xF087F)
     */
    public data object MIXED_REALITY : MaterialDesignGlyphs(0xF087F)

    /**
     * 󰢀 (0xF0880)
     */
    public data object NATIVESCRIPT : MaterialDesignGlyphs(0xF0880)

    /**
     * 󰢁 (0xF0881)
     */
    public data object ONEPASSWORD : MaterialDesignGlyphs(0xF0881)

    /**
     * 󰢂 (0xF0882)
     */
    public data object PATREON : MaterialDesignGlyphs(0xF0882)

    /**
     * 󰢃 (0xF0883)
     */
    public data object CLOSE_CIRCLE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0883)

    /**
     * 󰢄 (0xF0884)
     */
    public data object PEACE : MaterialDesignGlyphs(0xF0884)

    /**
     * 󰢅 (0xF0885)
     */
    public data object PHONE_ROTATE_LANDSCAPE : MaterialDesignGlyphs(0xF0885)

    /**
     * 󰢆 (0xF0886)
     */
    public data object PHONE_ROTATE_PORTRAIT : MaterialDesignGlyphs(0xF0886)

    /**
     * 󰢇 (0xF0887)
     */
    public data object PIER : MaterialDesignGlyphs(0xF0887)

    /**
     * 󰢈 (0xF0888)
     */
    public data object PIER_CRANE : MaterialDesignGlyphs(0xF0888)

    /**
     * 󰢉 (0xF0889)
     */
    public data object PIPE_LEAK : MaterialDesignGlyphs(0xF0889)

    /**
     * 󰢊 (0xF088A)
     */
    public data object PISTON : MaterialDesignGlyphs(0xF088A)

    /**
     * 󰢋 (0xF088B)
     */
    public data object PLAY_NETWORK : MaterialDesignGlyphs(0xF088B)

    /**
     * 󰢌 (0xF088C)
     */
    public data object REMINDER : MaterialDesignGlyphs(0xF088C)

    /**
     * 󰢍 (0xF088D)
     */
    public data object ROOM_SERVICE : MaterialDesignGlyphs(0xF088D)

    /**
     * 󰢎 (0xF088E)
     */
    public data object SALESFORCE : MaterialDesignGlyphs(0xF088E)

    /**
     * 󰢏 (0xF088F)
     */
    public data object SHIELD_ACCOUNT : MaterialDesignGlyphs(0xF088F)

    /**
     * 󰢐 (0xF0890)
     */
    public data object HUMAN_MALE_BOARD : MaterialDesignGlyphs(0xF0890)

    /**
     * 󰢑 (0xF0891)
     */
    public data object THERMOSTAT_BOX : MaterialDesignGlyphs(0xF0891)

    /**
     * 󰢒 (0xF0892)
     */
    public data object TRACTOR : MaterialDesignGlyphs(0xF0892)

    /**
     * 󰢓 (0xF0893)
     */
    public data object VECTOR_ELLIPSE : MaterialDesignGlyphs(0xF0893)

    /**
     * 󰢔 (0xF0894)
     */
    public data object VIRTUAL_REALITY : MaterialDesignGlyphs(0xF0894)

    /**
     * 󰢕 (0xF0895)
     */
    public data object WATCH_EXPORT_VARIANT : MaterialDesignGlyphs(0xF0895)

    /**
     * 󰢖 (0xF0896)
     */
    public data object WATCH_IMPORT_VARIANT : MaterialDesignGlyphs(0xF0896)

    /**
     * 󰢗 (0xF0897)
     */
    public data object WATCH_VARIANT : MaterialDesignGlyphs(0xF0897)

    /**
     * 󰢘 (0xF0898)
     */
    public data object WEATHER_HURRICANE : MaterialDesignGlyphs(0xF0898)

    /**
     * 󰢙 (0xF0899)
     */
    public data object ACCOUNT_HEART : MaterialDesignGlyphs(0xF0899)

    /**
     * 󰢚 (0xF089A)
     */
    public data object ALIEN : MaterialDesignGlyphs(0xF089A)

    /**
     * 󰢛 (0xF089B)
     */
    public data object ANVIL : MaterialDesignGlyphs(0xF089B)

    /**
     * 󰢜 (0xF089C)
     */
    public data object BATTERY_CHARGING_10 : MaterialDesignGlyphs(0xF089C)

    /**
     * 󰢝 (0xF089D)
     */
    public data object BATTERY_CHARGING_50 : MaterialDesignGlyphs(0xF089D)

    /**
     * 󰢞 (0xF089E)
     */
    public data object BATTERY_CHARGING_70 : MaterialDesignGlyphs(0xF089E)

    /**
     * 󰢟 (0xF089F)
     */
    public data object BATTERY_CHARGING_OUTLINE : MaterialDesignGlyphs(0xF089F)

    /**
     * 󰢠 (0xF08A0)
     */
    public data object BED_EMPTY : MaterialDesignGlyphs(0xF08A0)

    /**
     * 󰢡 (0xF08A1)
     */
    public data object BORDER_ALL_VARIANT : MaterialDesignGlyphs(0xF08A1)

    /**
     * 󰢢 (0xF08A2)
     */
    public data object BORDER_BOTTOM_VARIANT : MaterialDesignGlyphs(0xF08A2)

    /**
     * 󰢣 (0xF08A3)
     */
    public data object BORDER_LEFT_VARIANT : MaterialDesignGlyphs(0xF08A3)

    /**
     * 󰢤 (0xF08A4)
     */
    public data object BORDER_NONE_VARIANT : MaterialDesignGlyphs(0xF08A4)

    /**
     * 󰢥 (0xF08A5)
     */
    public data object BORDER_RIGHT_VARIANT : MaterialDesignGlyphs(0xF08A5)

    /**
     * 󰢦 (0xF08A6)
     */
    public data object BORDER_TOP_VARIANT : MaterialDesignGlyphs(0xF08A6)

    /**
     * 󰢧 (0xF08A7)
     */
    public data object CALENDAR_EDIT : MaterialDesignGlyphs(0xF08A7)

    /**
     * 󰢨 (0xF08A8)
     */
    public data object CLIPBOARD_CHECK_OUTLINE : MaterialDesignGlyphs(0xF08A8)

    /**
     * 󰢩 (0xF08A9)
     */
    public data object CONSOLE_NETWORK : MaterialDesignGlyphs(0xF08A9)

    /**
     * 󰢪 (0xF08AA)
     */
    public data object FILE_COMPARE : MaterialDesignGlyphs(0xF08AA)

    /**
     * 󰢫 (0xF08AB)
     */
    public data object FIRE_TRUCK : MaterialDesignGlyphs(0xF08AB)

    /**
     * 󰢬 (0xF08AC)
     */
    public data object FOLDER_KEY : MaterialDesignGlyphs(0xF08AC)

    /**
     * 󰢭 (0xF08AD)
     */
    public data object FOLDER_KEY_NETWORK : MaterialDesignGlyphs(0xF08AD)

    /**
     * 󰢮 (0xF08AE)
     */
    public data object EXPANSION_CARD : MaterialDesignGlyphs(0xF08AE)

    /**
     * 󰢯 (0xF08AF)
     */
    public data object KAYAKING : MaterialDesignGlyphs(0xF08AF)

    /**
     * 󰢰 (0xF08B0)
     */
    public data object INBOX_MULTIPLE : MaterialDesignGlyphs(0xF08B0)

    /**
     * 󰢱 (0xF08B1)
     */
    public data object LANGUAGE_LUA : MaterialDesignGlyphs(0xF08B1)

    /**
     * 󰢲 (0xF08B2)
     */
    public data object LOCK_SMART : MaterialDesignGlyphs(0xF08B2)

    /**
     * 󰢳 (0xF08B3)
     */
    public data object MICROPHONE_MINUS : MaterialDesignGlyphs(0xF08B3)

    /**
     * 󰢴 (0xF08B4)
     */
    public data object MICROPHONE_PLUS : MaterialDesignGlyphs(0xF08B4)

    /**
     * 󰢵 (0xF08B5)
     */
    public data object PALETTE_SWATCH : MaterialDesignGlyphs(0xF08B5)

    /**
     * 󰢶 (0xF08B6)
     */
    public data object PERIODIC_TABLE : MaterialDesignGlyphs(0xF08B6)

    /**
     * 󰢷 (0xF08B7)
     */
    public data object PICKAXE : MaterialDesignGlyphs(0xF08B7)

    /**
     * 󰢸 (0xF08B8)
     */
    public data object QRCODE_EDIT : MaterialDesignGlyphs(0xF08B8)

    /**
     * 󰢹 (0xF08B9)
     */
    public data object REMOTE_DESKTOP : MaterialDesignGlyphs(0xF08B9)

    /**
     * 󰢺 (0xF08BA)
     */
    public data object SAUSAGE : MaterialDesignGlyphs(0xF08BA)

    /**
     * 󰢻 (0xF08BB)
     */
    public data object COG_OUTLINE : MaterialDesignGlyphs(0xF08BB)

    /**
     * 󰢼 (0xF08BC)
     */
    public data object SIGNAL_CELLULAR_1 : MaterialDesignGlyphs(0xF08BC)

    /**
     * 󰢽 (0xF08BD)
     */
    public data object SIGNAL_CELLULAR_2 : MaterialDesignGlyphs(0xF08BD)

    /**
     * 󰢾 (0xF08BE)
     */
    public data object SIGNAL_CELLULAR_3 : MaterialDesignGlyphs(0xF08BE)

    /**
     * 󰢿 (0xF08BF)
     */
    public data object SIGNAL_CELLULAR_OUTLINE : MaterialDesignGlyphs(0xF08BF)

    /**
     * 󰣀 (0xF08C0)
     */
    public data object SSH : MaterialDesignGlyphs(0xF08C0)

    /**
     * 󰣁 (0xF08C1)
     */
    public data object SWAP_HORIZONTAL_VARIANT : MaterialDesignGlyphs(0xF08C1)

    /**
     * 󰣂 (0xF08C2)
     */
    public data object SWAP_VERTICAL_VARIANT : MaterialDesignGlyphs(0xF08C2)

    /**
     * 󰣃 (0xF08C3)
     */
    public data object TOOTH : MaterialDesignGlyphs(0xF08C3)

    /**
     * 󰣄 (0xF08C4)
     */
    public data object TRAIN_VARIANT : MaterialDesignGlyphs(0xF08C4)

    /**
     * 󰣅 (0xF08C5)
     */
    public data object ACCOUNT_MULTIPLE_CHECK : MaterialDesignGlyphs(0xF08C5)

    /**
     * 󰣆 (0xF08C6)
     */
    public data object APPLICATION : MaterialDesignGlyphs(0xF08C6)

    /**
     * 󰣇 (0xF08C7)
     */
    public data object ARCH : MaterialDesignGlyphs(0xF08C7)

    /**
     * 󰣈 (0xF08C8)
     */
    public data object AXE : MaterialDesignGlyphs(0xF08C8)

    /**
     * 󰣉 (0xF08C9)
     */
    public data object BULLSEYE_ARROW : MaterialDesignGlyphs(0xF08C9)

    /**
     * 󰣊 (0xF08CA)
     */
    public data object BUS_CLOCK : MaterialDesignGlyphs(0xF08CA)

    /**
     * 󰣋 (0xF08CB)
     */
    public data object CAMERA_ACCOUNT : MaterialDesignGlyphs(0xF08CB)

    /**
     * 󰣌 (0xF08CC)
     */
    public data object CAMERA_IMAGE : MaterialDesignGlyphs(0xF08CC)

    /**
     * 󰣍 (0xF08CD)
     */
    public data object CAR_LIMOUSINE : MaterialDesignGlyphs(0xF08CD)

    /**
     * 󰣎 (0xF08CE)
     */
    public data object CARDS_CLUB : MaterialDesignGlyphs(0xF08CE)

    /**
     * 󰣏 (0xF08CF)
     */
    public data object CARDS_DIAMOND : MaterialDesignGlyphs(0xF08CF)

    /**
     * 󰣑 (0xF08D1)
     */
    public data object CARDS_SPADE : MaterialDesignGlyphs(0xF08D1)

    /**
     * 󰣒 (0xF08D2)
     */
    public data object CELLPHONE_TEXT : MaterialDesignGlyphs(0xF08D2)

    /**
     * 󰣓 (0xF08D3)
     */
    public data object CELLPHONE_MESSAGE : MaterialDesignGlyphs(0xF08D3)

    /**
     * 󰣔 (0xF08D4)
     */
    public data object CHART_MULTILINE : MaterialDesignGlyphs(0xF08D4)

    /**
     * 󰣕 (0xF08D5)
     */
    public data object CIRCLE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF08D5)

    /**
     * 󰣖 (0xF08D6)
     */
    public data object COGS : MaterialDesignGlyphs(0xF08D6)

    /**
     * 󰣗 (0xF08D7)
     */
    public data object CREDIT_CARD_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF08D7)

    /**
     * 󰣘 (0xF08D8)
     */
    public data object DEATH_STAR : MaterialDesignGlyphs(0xF08D8)

    /**
     * 󰣙 (0xF08D9)
     */
    public data object DEATH_STAR_VARIANT : MaterialDesignGlyphs(0xF08D9)

    /**
     * 󰣚 (0xF08DA)
     */
    public data object DEBIAN : MaterialDesignGlyphs(0xF08DA)

    /**
     * 󰣛 (0xF08DB)
     */
    public data object FEDORA : MaterialDesignGlyphs(0xF08DB)

    /**
     * 󰣜 (0xF08DC)
     */
    public data object FILE_UNDO : MaterialDesignGlyphs(0xF08DC)

    /**
     * 󰣝 (0xF08DD)
     */
    public data object FLOOR_LAMP : MaterialDesignGlyphs(0xF08DD)

    /**
     * 󰣞 (0xF08DE)
     */
    public data object FOLDER_EDIT : MaterialDesignGlyphs(0xF08DE)

    /**
     * 󰣟 (0xF08DF)
     */
    public data object FORMAT_COLUMNS : MaterialDesignGlyphs(0xF08DF)

    /**
     * 󰣠 (0xF08E0)
     */
    public data object FREEBSD : MaterialDesignGlyphs(0xF08E0)

    /**
     * 󰣡 (0xF08E1)
     */
    public data object GATE_AND : MaterialDesignGlyphs(0xF08E1)

    /**
     * 󰣢 (0xF08E2)
     */
    public data object GATE_NAND : MaterialDesignGlyphs(0xF08E2)

    /**
     * 󰣣 (0xF08E3)
     */
    public data object GATE_NOR : MaterialDesignGlyphs(0xF08E3)

    /**
     * 󰣤 (0xF08E4)
     */
    public data object GATE_NOT : MaterialDesignGlyphs(0xF08E4)

    /**
     * 󰣥 (0xF08E5)
     */
    public data object GATE_OR : MaterialDesignGlyphs(0xF08E5)

    /**
     * 󰣦 (0xF08E6)
     */
    public data object GATE_XNOR : MaterialDesignGlyphs(0xF08E6)

    /**
     * 󰣧 (0xF08E7)
     */
    public data object GATE_XOR : MaterialDesignGlyphs(0xF08E7)

    /**
     * 󰣨 (0xF08E8)
     */
    public data object GENTOO : MaterialDesignGlyphs(0xF08E8)

    /**
     * 󰣩 (0xF08E9)
     */
    public data object GLOBE_MODEL : MaterialDesignGlyphs(0xF08E9)

    /**
     * 󰣪 (0xF08EA)
     */
    public data object HAMMER : MaterialDesignGlyphs(0xF08EA)

    /**
     * 󰣫 (0xF08EB)
     */
    public data object HOME_LOCK : MaterialDesignGlyphs(0xF08EB)

    /**
     * 󰣬 (0xF08EC)
     */
    public data object HOME_LOCK_OPEN : MaterialDesignGlyphs(0xF08EC)

    /**
     * 󰣭 (0xF08ED)
     */
    public data object LINUX_MINT : MaterialDesignGlyphs(0xF08ED)

    /**
     * 󰣮 (0xF08EE)
     */
    public data object LOCK_ALERT : MaterialDesignGlyphs(0xF08EE)

    /**
     * 󰣯 (0xF08EF)
     */
    public data object LOCK_QUESTION : MaterialDesignGlyphs(0xF08EF)

    /**
     * 󰣰 (0xF08F0)
     */
    public data object MAP_MARKER_DISTANCE : MaterialDesignGlyphs(0xF08F0)

    /**
     * 󰣱 (0xF08F1)
     */
    public data object MIDI : MaterialDesignGlyphs(0xF08F1)

    /**
     * 󰣲 (0xF08F2)
     */
    public data object MIDI_PORT : MaterialDesignGlyphs(0xF08F2)

    /**
     * 󰣳 (0xF08F3)
     */
    public data object NAS : MaterialDesignGlyphs(0xF08F3)

    /**
     * 󰣴 (0xF08F4)
     */
    public data object NETWORK_STRENGTH_1 : MaterialDesignGlyphs(0xF08F4)

    /**
     * 󰣵 (0xF08F5)
     */
    public data object NETWORK_STRENGTH_1_ALERT : MaterialDesignGlyphs(0xF08F5)

    /**
     * 󰣶 (0xF08F6)
     */
    public data object NETWORK_STRENGTH_2 : MaterialDesignGlyphs(0xF08F6)

    /**
     * 󰣷 (0xF08F7)
     */
    public data object NETWORK_STRENGTH_2_ALERT : MaterialDesignGlyphs(0xF08F7)

    /**
     * 󰣸 (0xF08F8)
     */
    public data object NETWORK_STRENGTH_3 : MaterialDesignGlyphs(0xF08F8)

    /**
     * 󰣹 (0xF08F9)
     */
    public data object NETWORK_STRENGTH_3_ALERT : MaterialDesignGlyphs(0xF08F9)

    /**
     * 󰣺 (0xF08FA)
     */
    public data object NETWORK_STRENGTH_4 : MaterialDesignGlyphs(0xF08FA)

    /**
     * 󰣻 (0xF08FB)
     */
    public data object NETWORK_STRENGTH_4_ALERT : MaterialDesignGlyphs(0xF08FB)

    /**
     * 󰣼 (0xF08FC)
     */
    public data object NETWORK_STRENGTH_OFF : MaterialDesignGlyphs(0xF08FC)

    /**
     * 󰣽 (0xF08FD)
     */
    public data object NETWORK_STRENGTH_OFF_OUTLINE : MaterialDesignGlyphs(0xF08FD)

    /**
     * 󰣾 (0xF08FE)
     */
    public data object NETWORK_STRENGTH_OUTLINE : MaterialDesignGlyphs(0xF08FE)

    /**
     * 󰣿 (0xF08FF)
     */
    public data object PLAY_SPEED : MaterialDesignGlyphs(0xF08FF)

    /**
     * 󰤀 (0xF0900)
     */
    public data object PLAYLIST_EDIT : MaterialDesignGlyphs(0xF0900)

    /**
     * 󰤁 (0xF0901)
     */
    public data object POWER_CYCLE : MaterialDesignGlyphs(0xF0901)

    /**
     * 󰤂 (0xF0902)
     */
    public data object POWER_OFF : MaterialDesignGlyphs(0xF0902)

    /**
     * 󰤃 (0xF0903)
     */
    public data object POWER_ON : MaterialDesignGlyphs(0xF0903)

    /**
     * 󰤄 (0xF0904)
     */
    public data object POWER_SLEEP : MaterialDesignGlyphs(0xF0904)

    /**
     * 󰤅 (0xF0905)
     */
    public data object POWER_SOCKET_AU : MaterialDesignGlyphs(0xF0905)

    /**
     * 󰤆 (0xF0906)
     */
    public data object POWER_STANDBY : MaterialDesignGlyphs(0xF0906)

    /**
     * 󰤇 (0xF0907)
     */
    public data object RABBIT : MaterialDesignGlyphs(0xF0907)

    /**
     * 󰤈 (0xF0908)
     */
    public data object ROBOT_VACUUM_VARIANT : MaterialDesignGlyphs(0xF0908)

    /**
     * 󰤉 (0xF0909)
     */
    public data object SATELLITE_UPLINK : MaterialDesignGlyphs(0xF0909)

    /**
     * 󰤊 (0xF090A)
     */
    public data object SCANNER_OFF : MaterialDesignGlyphs(0xF090A)

    /**
     * 󰤋 (0xF090B)
     */
    public data object BOOK_MINUS_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF090B)

    /**
     * 󰤌 (0xF090C)
     */
    public data object SQUARE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF090C)

    /**
     * 󰤍 (0xF090D)
     */
    public data object SORT_NUMERIC_ASCENDING_VARIANT : MaterialDesignGlyphs(0xF090D)

    /**
     * 󰤎 (0xF090E)
     */
    public data object STEERING_OFF : MaterialDesignGlyphs(0xF090E)

    /**
     * 󰤏 (0xF090F)
     */
    public data object TABLE_SEARCH : MaterialDesignGlyphs(0xF090F)

    /**
     * 󰤐 (0xF0910)
     */
    public data object TAG_MINUS : MaterialDesignGlyphs(0xF0910)

    /**
     * 󰤑 (0xF0911)
     */
    public data object TEST_TUBE_EMPTY : MaterialDesignGlyphs(0xF0911)

    /**
     * 󰤒 (0xF0912)
     */
    public data object TEST_TUBE_OFF : MaterialDesignGlyphs(0xF0912)

    /**
     * 󰤓 (0xF0913)
     */
    public data object TICKET_OUTLINE : MaterialDesignGlyphs(0xF0913)

    /**
     * 󰤔 (0xF0914)
     */
    public data object TRACK_LIGHT : MaterialDesignGlyphs(0xF0914)

    /**
     * 󰤕 (0xF0915)
     */
    public data object TRANSITION : MaterialDesignGlyphs(0xF0915)

    /**
     * 󰤖 (0xF0916)
     */
    public data object TRANSITION_MASKED : MaterialDesignGlyphs(0xF0916)

    /**
     * 󰤗 (0xF0917)
     */
    public data object TUMBLE_DRYER : MaterialDesignGlyphs(0xF0917)

    /**
     * 󰤘 (0xF0918)
     */
    public data object FILE_REFRESH : MaterialDesignGlyphs(0xF0918)

    /**
     * 󰤙 (0xF0919)
     */
    public data object VIDEO_ACCOUNT : MaterialDesignGlyphs(0xF0919)

    /**
     * 󰤚 (0xF091A)
     */
    public data object VIDEO_IMAGE : MaterialDesignGlyphs(0xF091A)

    /**
     * 󰤛 (0xF091B)
     */
    public data object VIDEO_STABILIZATION : MaterialDesignGlyphs(0xF091B)

    /**
     * 󰤜 (0xF091C)
     */
    public data object WALL_SCONCE : MaterialDesignGlyphs(0xF091C)

    /**
     * 󰤝 (0xF091D)
     */
    public data object WALL_SCONCE_FLAT : MaterialDesignGlyphs(0xF091D)

    /**
     * 󰤞 (0xF091E)
     */
    public data object WALL_SCONCE_ROUND_VARIANT : MaterialDesignGlyphs(0xF091E)

    /**
     * 󰤟 (0xF091F)
     */
    public data object WIFI_STRENGTH_1 : MaterialDesignGlyphs(0xF091F)

    /**
     * 󰤠 (0xF0920)
     */
    public data object WIFI_STRENGTH_1_ALERT : MaterialDesignGlyphs(0xF0920)

    /**
     * 󰤡 (0xF0921)
     */
    public data object WIFI_STRENGTH_1_LOCK : MaterialDesignGlyphs(0xF0921)

    /**
     * 󰤢 (0xF0922)
     */
    public data object WIFI_STRENGTH_2 : MaterialDesignGlyphs(0xF0922)

    /**
     * 󰤣 (0xF0923)
     */
    public data object WIFI_STRENGTH_2_ALERT : MaterialDesignGlyphs(0xF0923)

    /**
     * 󰤤 (0xF0924)
     */
    public data object WIFI_STRENGTH_2_LOCK : MaterialDesignGlyphs(0xF0924)

    /**
     * 󰤥 (0xF0925)
     */
    public data object WIFI_STRENGTH_3 : MaterialDesignGlyphs(0xF0925)

    /**
     * 󰤦 (0xF0926)
     */
    public data object WIFI_STRENGTH_3_ALERT : MaterialDesignGlyphs(0xF0926)

    /**
     * 󰤧 (0xF0927)
     */
    public data object WIFI_STRENGTH_3_LOCK : MaterialDesignGlyphs(0xF0927)

    /**
     * 󰤨 (0xF0928)
     */
    public data object WIFI_STRENGTH_4 : MaterialDesignGlyphs(0xF0928)

    /**
     * 󰤩 (0xF0929)
     */
    public data object WIFI_STRENGTH_4_ALERT : MaterialDesignGlyphs(0xF0929)

    /**
     * 󰤪 (0xF092A)
     */
    public data object WIFI_STRENGTH_4_LOCK : MaterialDesignGlyphs(0xF092A)

    /**
     * 󰤫 (0xF092B)
     */
    public data object WIFI_STRENGTH_ALERT_OUTLINE : MaterialDesignGlyphs(0xF092B)

    /**
     * 󰤬 (0xF092C)
     */
    public data object WIFI_STRENGTH_LOCK_OUTLINE : MaterialDesignGlyphs(0xF092C)

    /**
     * 󰤭 (0xF092D)
     */
    public data object WIFI_STRENGTH_OFF : MaterialDesignGlyphs(0xF092D)

    /**
     * 󰤮 (0xF092E)
     */
    public data object WIFI_STRENGTH_OFF_OUTLINE : MaterialDesignGlyphs(0xF092E)

    /**
     * 󰤯 (0xF092F)
     */
    public data object WIFI_STRENGTH_OUTLINE : MaterialDesignGlyphs(0xF092F)

    /**
     * 󰤰 (0xF0930)
     */
    public data object PIN_OFF_OUTLINE : MaterialDesignGlyphs(0xF0930)

    /**
     * 󰤱 (0xF0931)
     */
    public data object PIN_OUTLINE : MaterialDesignGlyphs(0xF0931)

    /**
     * 󰤲 (0xF0932)
     */
    public data object SHARE_OUTLINE : MaterialDesignGlyphs(0xF0932)

    /**
     * 󰤳 (0xF0933)
     */
    public data object TRACKPAD_LOCK : MaterialDesignGlyphs(0xF0933)

    /**
     * 󰤴 (0xF0934)
     */
    public data object ACCOUNT_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0934)

    /**
     * 󰤵 (0xF0935)
     */
    public data object ACCOUNT_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0935)

    /**
     * 󰤶 (0xF0936)
     */
    public data object ACCOUNT_FILTER : MaterialDesignGlyphs(0xF0936)

    /**
     * 󰤷 (0xF0937)
     */
    public data object ANGLE_ACUTE : MaterialDesignGlyphs(0xF0937)

    /**
     * 󰤸 (0xF0938)
     */
    public data object ANGLE_OBTUSE : MaterialDesignGlyphs(0xF0938)

    /**
     * 󰤹 (0xF0939)
     */
    public data object ANGLE_RIGHT : MaterialDesignGlyphs(0xF0939)

    /**
     * 󰤺 (0xF093A)
     */
    public data object ANIMATION_PLAY : MaterialDesignGlyphs(0xF093A)

    /**
     * 󰤻 (0xF093B)
     */
    public data object ARROW_SPLIT_HORIZONTAL : MaterialDesignGlyphs(0xF093B)

    /**
     * 󰤼 (0xF093C)
     */
    public data object ARROW_SPLIT_VERTICAL : MaterialDesignGlyphs(0xF093C)

    /**
     * 󰤽 (0xF093D)
     */
    public data object AUDIO_VIDEO : MaterialDesignGlyphs(0xF093D)

    /**
     * 󰤾 (0xF093E)
     */
    public data object BATTERY_10_BLUETOOTH : MaterialDesignGlyphs(0xF093E)

    /**
     * 󰤿 (0xF093F)
     */
    public data object BATTERY_20_BLUETOOTH : MaterialDesignGlyphs(0xF093F)

    /**
     * 󰥀 (0xF0940)
     */
    public data object BATTERY_30_BLUETOOTH : MaterialDesignGlyphs(0xF0940)

    /**
     * 󰥁 (0xF0941)
     */
    public data object BATTERY_40_BLUETOOTH : MaterialDesignGlyphs(0xF0941)

    /**
     * 󰥂 (0xF0942)
     */
    public data object BATTERY_50_BLUETOOTH : MaterialDesignGlyphs(0xF0942)

    /**
     * 󰥃 (0xF0943)
     */
    public data object BATTERY_60_BLUETOOTH : MaterialDesignGlyphs(0xF0943)

    /**
     * 󰥄 (0xF0944)
     */
    public data object BATTERY_70_BLUETOOTH : MaterialDesignGlyphs(0xF0944)

    /**
     * 󰥅 (0xF0945)
     */
    public data object BATTERY_80_BLUETOOTH : MaterialDesignGlyphs(0xF0945)

    /**
     * 󰥆 (0xF0946)
     */
    public data object BATTERY_90_BLUETOOTH : MaterialDesignGlyphs(0xF0946)

    /**
     * 󰥇 (0xF0947)
     */
    public data object BATTERY_ALERT_BLUETOOTH : MaterialDesignGlyphs(0xF0947)

    /**
     * 󰥈 (0xF0948)
     */
    public data object BATTERY_BLUETOOTH : MaterialDesignGlyphs(0xF0948)

    /**
     * 󰥉 (0xF0949)
     */
    public data object BATTERY_BLUETOOTH_VARIANT : MaterialDesignGlyphs(0xF0949)

    /**
     * 󰥊 (0xF094A)
     */
    public data object BATTERY_UNKNOWN_BLUETOOTH : MaterialDesignGlyphs(0xF094A)

    /**
     * 󰥋 (0xF094B)
     */
    public data object DHARMACHAKRA : MaterialDesignGlyphs(0xF094B)

    /**
     * 󰥌 (0xF094C)
     */
    public data object CALENDAR_SEARCH : MaterialDesignGlyphs(0xF094C)

    /**
     * 󰥍 (0xF094D)
     */
    public data object CELLPHONE_REMOVE : MaterialDesignGlyphs(0xF094D)

    /**
     * 󰥎 (0xF094E)
     */
    public data object CELLPHONE_KEY : MaterialDesignGlyphs(0xF094E)

    /**
     * 󰥏 (0xF094F)
     */
    public data object CELLPHONE_LOCK : MaterialDesignGlyphs(0xF094F)

    /**
     * 󰥐 (0xF0950)
     */
    public data object CELLPHONE_OFF : MaterialDesignGlyphs(0xF0950)

    /**
     * 󰥑 (0xF0951)
     */
    public data object CELLPHONE_COG : MaterialDesignGlyphs(0xF0951)

    /**
     * 󰥒 (0xF0952)
     */
    public data object CELLPHONE_SOUND : MaterialDesignGlyphs(0xF0952)

    /**
     * 󰥓 (0xF0953)
     */
    public data object CROSS : MaterialDesignGlyphs(0xF0953)

    /**
     * 󰥔 (0xF0954)
     */
    public data object CLOCK : MaterialDesignGlyphs(0xF0954)

    /**
     * 󰥕 (0xF0955)
     */
    public data object CLOCK_ALERT : MaterialDesignGlyphs(0xF0955)

    /**
     * 󰥖 (0xF0956)
     */
    public data object CLOUD_SEARCH : MaterialDesignGlyphs(0xF0956)

    /**
     * 󰥗 (0xF0957)
     */
    public data object CLOUD_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0957)

    /**
     * 󰥘 (0xF0958)
     */
    public data object CORDOVA : MaterialDesignGlyphs(0xF0958)

    /**
     * 󰥙 (0xF0959)
     */
    public data object CRYENGINE : MaterialDesignGlyphs(0xF0959)

    /**
     * 󰥚 (0xF095A)
     */
    public data object CUPCAKE : MaterialDesignGlyphs(0xF095A)

    /**
     * 󰥛 (0xF095B)
     */
    public data object SINE_WAVE : MaterialDesignGlyphs(0xF095B)

    /**
     * 󰥜 (0xF095C)
     */
    public data object CURRENT_DC : MaterialDesignGlyphs(0xF095C)

    /**
     * 󰥝 (0xF095D)
     */
    public data object DATABASE_IMPORT : MaterialDesignGlyphs(0xF095D)

    /**
     * 󰥞 (0xF095E)
     */
    public data object DATABASE_EXPORT : MaterialDesignGlyphs(0xF095E)

    /**
     * 󰥟 (0xF095F)
     */
    public data object DESK_LAMP : MaterialDesignGlyphs(0xF095F)

    /**
     * 󰥠 (0xF0960)
     */
    public data object DISC_PLAYER : MaterialDesignGlyphs(0xF0960)

    /**
     * 󰥡 (0xF0961)
     */
    public data object EMAIL_SEARCH : MaterialDesignGlyphs(0xF0961)

    /**
     * 󰥢 (0xF0962)
     */
    public data object EMAIL_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0962)

    /**
     * 󰥣 (0xF0963)
     */
    public data object EXPONENT : MaterialDesignGlyphs(0xF0963)

    /**
     * 󰥤 (0xF0964)
     */
    public data object EXPONENT_BOX : MaterialDesignGlyphs(0xF0964)

    /**
     * 󰥥 (0xF0965)
     */
    public data object FILE_DOWNLOAD : MaterialDesignGlyphs(0xF0965)

    /**
     * 󰥦 (0xF0966)
     */
    public data object FILE_DOWNLOAD_OUTLINE : MaterialDesignGlyphs(0xF0966)

    /**
     * 󰥧 (0xF0967)
     */
    public data object FIREBASE : MaterialDesignGlyphs(0xF0967)

    /**
     * 󰥨 (0xF0968)
     */
    public data object FOLDER_SEARCH : MaterialDesignGlyphs(0xF0968)

    /**
     * 󰥩 (0xF0969)
     */
    public data object FOLDER_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0969)

    /**
     * 󰥪 (0xF096A)
     */
    public data object FORMAT_LIST_CHECKBOX : MaterialDesignGlyphs(0xF096A)

    /**
     * 󰥫 (0xF096B)
     */
    public data object FOUNTAIN : MaterialDesignGlyphs(0xF096B)

    /**
     * 󰥬 (0xF096C)
     */
    public data object GOOGLE_FIT : MaterialDesignGlyphs(0xF096C)

    /**
     * 󰥭 (0xF096D)
     */
    public data object GREATER_THAN : MaterialDesignGlyphs(0xF096D)

    /**
     * 󰥮 (0xF096E)
     */
    public data object GREATER_THAN_OR_EQUAL : MaterialDesignGlyphs(0xF096E)

    /**
     * 󰥯 (0xF096F)
     */
    public data object HARD_HAT : MaterialDesignGlyphs(0xF096F)

    /**
     * 󰥰 (0xF0970)
     */
    public data object HEADPHONES_BLUETOOTH : MaterialDesignGlyphs(0xF0970)

    /**
     * 󰥱 (0xF0971)
     */
    public data object HEART_CIRCLE : MaterialDesignGlyphs(0xF0971)

    /**
     * 󰥲 (0xF0972)
     */
    public data object HEART_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0972)

    /**
     * 󰥳 (0xF0973)
     */
    public data object OM : MaterialDesignGlyphs(0xF0973)

    /**
     * 󰥴 (0xF0974)
     */
    public data object HOME_MINUS : MaterialDesignGlyphs(0xF0974)

    /**
     * 󰥵 (0xF0975)
     */
    public data object HOME_PLUS : MaterialDesignGlyphs(0xF0975)

    /**
     * 󰥶 (0xF0976)
     */
    public data object IMAGE_OUTLINE : MaterialDesignGlyphs(0xF0976)

    /**
     * 󰥷 (0xF0977)
     */
    public data object IMAGE_SEARCH : MaterialDesignGlyphs(0xF0977)

    /**
     * 󰥸 (0xF0978)
     */
    public data object IMAGE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0978)

    /**
     * 󰥹 (0xF0979)
     */
    public data object STAR_CRESCENT : MaterialDesignGlyphs(0xF0979)

    /**
     * 󰥺 (0xF097A)
     */
    public data object STAR_DAVID : MaterialDesignGlyphs(0xF097A)

    /**
     * 󰥻 (0xF097B)
     */
    public data object KEYBOARD_OUTLINE : MaterialDesignGlyphs(0xF097B)

    /**
     * 󰥼 (0xF097C)
     */
    public data object LESS_THAN : MaterialDesignGlyphs(0xF097C)

    /**
     * 󰥽 (0xF097D)
     */
    public data object LESS_THAN_OR_EQUAL : MaterialDesignGlyphs(0xF097D)

    /**
     * 󰥾 (0xF097E)
     */
    public data object LIGHT_SWITCH : MaterialDesignGlyphs(0xF097E)

    /**
     * 󰥿 (0xF097F)
     */
    public data object LOCK_CLOCK : MaterialDesignGlyphs(0xF097F)

    /**
     * 󰦀 (0xF0980)
     */
    public data object MAGNIFY_CLOSE : MaterialDesignGlyphs(0xF0980)

    /**
     * 󰦁 (0xF0981)
     */
    public data object MAP_MINUS : MaterialDesignGlyphs(0xF0981)

    /**
     * 󰦂 (0xF0982)
     */
    public data object MAP_OUTLINE : MaterialDesignGlyphs(0xF0982)

    /**
     * 󰦃 (0xF0983)
     */
    public data object MAP_PLUS : MaterialDesignGlyphs(0xF0983)

    /**
     * 󰦄 (0xF0984)
     */
    public data object MAP_SEARCH : MaterialDesignGlyphs(0xF0984)

    /**
     * 󰦅 (0xF0985)
     */
    public data object MAP_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0985)

    /**
     * 󰦆 (0xF0986)
     */
    public data object MATERIAL_DESIGN : MaterialDesignGlyphs(0xF0986)

    /**
     * 󰦇 (0xF0987)
     */
    public data object MEDAL : MaterialDesignGlyphs(0xF0987)

    /**
     * 󰦈 (0xF0988)
     */
    public data object MICROSOFT_DYNAMICS_365 : MaterialDesignGlyphs(0xF0988)

    /**
     * 󰦉 (0xF0989)
     */
    public data object MONITOR_CELLPHONE : MaterialDesignGlyphs(0xF0989)

    /**
     * 󰦊 (0xF098A)
     */
    public data object MONITOR_CELLPHONE_STAR : MaterialDesignGlyphs(0xF098A)

    /**
     * 󰦋 (0xF098B)
     */
    public data object MOUSE_BLUETOOTH : MaterialDesignGlyphs(0xF098B)

    /**
     * 󰦌 (0xF098C)
     */
    public data object MUFFIN : MaterialDesignGlyphs(0xF098C)

    /**
     * 󰦍 (0xF098D)
     */
    public data object NOT_EQUAL : MaterialDesignGlyphs(0xF098D)

    /**
     * 󰦎 (0xF098E)
     */
    public data object NOT_EQUAL_VARIANT : MaterialDesignGlyphs(0xF098E)

    /**
     * 󰦏 (0xF098F)
     */
    public data object ORDER_BOOL_ASCENDING_VARIANT : MaterialDesignGlyphs(0xF098F)

    /**
     * 󰦐 (0xF0990)
     */
    public data object ORDER_BOOL_DESCENDING_VARIANT : MaterialDesignGlyphs(0xF0990)

    /**
     * 󰦑 (0xF0991)
     */
    public data object OFFICE_BUILDING : MaterialDesignGlyphs(0xF0991)

    /**
     * 󰦒 (0xF0992)
     */
    public data object PLUS_MINUS : MaterialDesignGlyphs(0xF0992)

    /**
     * 󰦓 (0xF0993)
     */
    public data object PLUS_MINUS_BOX : MaterialDesignGlyphs(0xF0993)

    /**
     * 󰦔 (0xF0994)
     */
    public data object PODCAST : MaterialDesignGlyphs(0xF0994)

    /**
     * 󰦕 (0xF0995)
     */
    public data object PROGRESS_CHECK : MaterialDesignGlyphs(0xF0995)

    /**
     * 󰦖 (0xF0996)
     */
    public data object PROGRESS_CLOCK : MaterialDesignGlyphs(0xF0996)

    /**
     * 󰦗 (0xF0997)
     */
    public data object PROGRESS_DOWNLOAD : MaterialDesignGlyphs(0xF0997)

    /**
     * 󰦘 (0xF0998)
     */
    public data object PROGRESS_UPLOAD : MaterialDesignGlyphs(0xF0998)

    /**
     * 󰦙 (0xF0999)
     */
    public data object QI : MaterialDesignGlyphs(0xF0999)

    /**
     * 󰦚 (0xF099A)
     */
    public data object RECORD_PLAYER : MaterialDesignGlyphs(0xF099A)

    /**
     * 󰦛 (0xF099B)
     */
    public data object RESTORE : MaterialDesignGlyphs(0xF099B)

    /**
     * 󰦜 (0xF099C)
     */
    public data object SHIELD_OFF_OUTLINE : MaterialDesignGlyphs(0xF099C)

    /**
     * 󰦝 (0xF099D)
     */
    public data object SHIELD_LOCK : MaterialDesignGlyphs(0xF099D)

    /**
     * 󰦞 (0xF099E)
     */
    public data object SHIELD_OFF : MaterialDesignGlyphs(0xF099E)

    /**
     * 󰦟 (0xF099F)
     */
    public data object SET_TOP_BOX : MaterialDesignGlyphs(0xF099F)

    /**
     * 󰦠 (0xF09A0)
     */
    public data object SHOWER : MaterialDesignGlyphs(0xF09A0)

    /**
     * 󰦡 (0xF09A1)
     */
    public data object SHOWER_HEAD : MaterialDesignGlyphs(0xF09A1)

    /**
     * 󰦢 (0xF09A2)
     */
    public data object SPEAKER_BLUETOOTH : MaterialDesignGlyphs(0xF09A2)

    /**
     * 󰦣 (0xF09A3)
     */
    public data object SQUARE_ROOT_BOX : MaterialDesignGlyphs(0xF09A3)

    /**
     * 󰦤 (0xF09A4)
     */
    public data object STAR_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF09A4)

    /**
     * 󰦥 (0xF09A5)
     */
    public data object STAR_FACE : MaterialDesignGlyphs(0xF09A5)

    /**
     * 󰦦 (0xF09A6)
     */
    public data object TABLE_MERGE_CELLS : MaterialDesignGlyphs(0xF09A6)

    /**
     * 󰦧 (0xF09A7)
     */
    public data object TABLET_CELLPHONE : MaterialDesignGlyphs(0xF09A7)

    /**
     * 󰦨 (0xF09A8)
     */
    public data object TEXT : MaterialDesignGlyphs(0xF09A8)

    /**
     * 󰦩 (0xF09A9)
     */
    public data object TEXT_SHORT : MaterialDesignGlyphs(0xF09A9)

    /**
     * 󰦪 (0xF09AA)
     */
    public data object TEXT_LONG : MaterialDesignGlyphs(0xF09AA)

    /**
     * 󰦫 (0xF09AB)
     */
    public data object TOILET : MaterialDesignGlyphs(0xF09AB)

    /**
     * 󰦬 (0xF09AC)
     */
    public data object TOOLBOX : MaterialDesignGlyphs(0xF09AC)

    /**
     * 󰦭 (0xF09AD)
     */
    public data object TOOLBOX_OUTLINE : MaterialDesignGlyphs(0xF09AD)

    /**
     * 󰦮 (0xF09AE)
     */
    public data object TOURNAMENT : MaterialDesignGlyphs(0xF09AE)

    /**
     * 󰦯 (0xF09AF)
     */
    public data object TWO_FACTOR_AUTHENTICATION : MaterialDesignGlyphs(0xF09AF)

    /**
     * 󰦰 (0xF09B0)
     */
    public data object UMBRELLA_CLOSED : MaterialDesignGlyphs(0xF09B0)

    /**
     * 󰦱 (0xF09B1)
     */
    public data object UNREAL : MaterialDesignGlyphs(0xF09B1)

    /**
     * 󰦲 (0xF09B2)
     */
    public data object VIDEO_MINUS : MaterialDesignGlyphs(0xF09B2)

    /**
     * 󰦳 (0xF09B3)
     */
    public data object VIDEO_PLUS : MaterialDesignGlyphs(0xF09B3)

    /**
     * 󰦴 (0xF09B4)
     */
    public data object VOLLEYBALL : MaterialDesignGlyphs(0xF09B4)

    /**
     * 󰦵 (0xF09B5)
     */
    public data object WEIGHT_POUND : MaterialDesignGlyphs(0xF09B5)

    /**
     * 󰦶 (0xF09B6)
     */
    public data object WHISTLE : MaterialDesignGlyphs(0xF09B6)

    /**
     * 󰦷 (0xF09B7)
     */
    public data object ARROW_BOTTOM_LEFT_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09B7)

    /**
     * 󰦸 (0xF09B8)
     */
    public data object ARROW_BOTTOM_LEFT_THICK : MaterialDesignGlyphs(0xF09B8)

    /**
     * 󰦹 (0xF09B9)
     */
    public data object ARROW_BOTTOM_RIGHT_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09B9)

    /**
     * 󰦺 (0xF09BA)
     */
    public data object ARROW_BOTTOM_RIGHT_THICK : MaterialDesignGlyphs(0xF09BA)

    /**
     * 󰦻 (0xF09BB)
     */
    public data object ARROW_DECISION : MaterialDesignGlyphs(0xF09BB)

    /**
     * 󰦼 (0xF09BC)
     */
    public data object ARROW_DECISION_AUTO : MaterialDesignGlyphs(0xF09BC)

    /**
     * 󰦽 (0xF09BD)
     */
    public data object ARROW_DECISION_AUTO_OUTLINE : MaterialDesignGlyphs(0xF09BD)

    /**
     * 󰦾 (0xF09BE)
     */
    public data object ARROW_DECISION_OUTLINE : MaterialDesignGlyphs(0xF09BE)

    /**
     * 󰦿 (0xF09BF)
     */
    public data object ARROW_DOWN_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09BF)

    /**
     * 󰧀 (0xF09C0)
     */
    public data object ARROW_LEFT_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09C0)

    /**
     * 󰧁 (0xF09C1)
     */
    public data object ARROW_LEFT_RIGHT_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09C1)

    /**
     * 󰧂 (0xF09C2)
     */
    public data object ARROW_RIGHT_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09C2)

    /**
     * 󰧃 (0xF09C3)
     */
    public data object ARROW_TOP_LEFT_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09C3)

    /**
     * 󰧄 (0xF09C4)
     */
    public data object ARROW_TOP_LEFT_THICK : MaterialDesignGlyphs(0xF09C4)

    /**
     * 󰧅 (0xF09C5)
     */
    public data object ARROW_TOP_RIGHT_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09C5)

    /**
     * 󰧆 (0xF09C6)
     */
    public data object ARROW_TOP_RIGHT_THICK : MaterialDesignGlyphs(0xF09C6)

    /**
     * 󰧇 (0xF09C7)
     */
    public data object ARROW_UP_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09C7)

    /**
     * 󰧈 (0xF09C8)
     */
    public data object ARROW_UP_DOWN_BOLD_OUTLINE : MaterialDesignGlyphs(0xF09C8)

    /**
     * 󰧉 (0xF09C9)
     */
    public data object BALLOT : MaterialDesignGlyphs(0xF09C9)

    /**
     * 󰧊 (0xF09CA)
     */
    public data object BALLOT_OUTLINE : MaterialDesignGlyphs(0xF09CA)

    /**
     * 󰧋 (0xF09CB)
     */
    public data object BETAMAX : MaterialDesignGlyphs(0xF09CB)

    /**
     * 󰧌 (0xF09CC)
     */
    public data object BOOKMARK_MINUS : MaterialDesignGlyphs(0xF09CC)

    /**
     * 󰧍 (0xF09CD)
     */
    public data object BOOKMARK_MINUS_OUTLINE : MaterialDesignGlyphs(0xF09CD)

    /**
     * 󰧎 (0xF09CE)
     */
    public data object BOOKMARK_OFF : MaterialDesignGlyphs(0xF09CE)

    /**
     * 󰧏 (0xF09CF)
     */
    public data object BOOKMARK_OFF_OUTLINE : MaterialDesignGlyphs(0xF09CF)

    /**
     * 󰧐 (0xF09D0)
     */
    public data object BRAILLE : MaterialDesignGlyphs(0xF09D0)

    /**
     * 󰧑 (0xF09D1)
     */
    public data object BRAIN : MaterialDesignGlyphs(0xF09D1)

    /**
     * 󰧒 (0xF09D2)
     */
    public data object CALENDAR_HEART : MaterialDesignGlyphs(0xF09D2)

    /**
     * 󰧓 (0xF09D3)
     */
    public data object CALENDAR_STAR : MaterialDesignGlyphs(0xF09D3)

    /**
     * 󰧔 (0xF09D4)
     */
    public data object CASSETTE : MaterialDesignGlyphs(0xF09D4)

    /**
     * 󰧕 (0xF09D5)
     */
    public data object CELLPHONE_ARROW_DOWN : MaterialDesignGlyphs(0xF09D5)

    /**
     * 󰧖 (0xF09D6)
     */
    public data object CHEVRON_DOWN_BOX : MaterialDesignGlyphs(0xF09D6)

    /**
     * 󰧗 (0xF09D7)
     */
    public data object CHEVRON_DOWN_BOX_OUTLINE : MaterialDesignGlyphs(0xF09D7)

    /**
     * 󰧘 (0xF09D8)
     */
    public data object CHEVRON_LEFT_BOX : MaterialDesignGlyphs(0xF09D8)

    /**
     * 󰧙 (0xF09D9)
     */
    public data object CHEVRON_LEFT_BOX_OUTLINE : MaterialDesignGlyphs(0xF09D9)

    /**
     * 󰧚 (0xF09DA)
     */
    public data object CHEVRON_RIGHT_BOX : MaterialDesignGlyphs(0xF09DA)

    /**
     * 󰧛 (0xF09DB)
     */
    public data object CHEVRON_RIGHT_BOX_OUTLINE : MaterialDesignGlyphs(0xF09DB)

    /**
     * 󰧜 (0xF09DC)
     */
    public data object CHEVRON_UP_BOX : MaterialDesignGlyphs(0xF09DC)

    /**
     * 󰧝 (0xF09DD)
     */
    public data object CHEVRON_UP_BOX_OUTLINE : MaterialDesignGlyphs(0xF09DD)

    /**
     * 󰧞 (0xF09DE)
     */
    public data object CIRCLE_MEDIUM : MaterialDesignGlyphs(0xF09DE)

    /**
     * 󰧟 (0xF09DF)
     */
    public data object CIRCLE_SMALL : MaterialDesignGlyphs(0xF09DF)

    /**
     * 󰧠 (0xF09E0)
     */
    public data object CLOUD_ALERT : MaterialDesignGlyphs(0xF09E0)

    /**
     * 󰧡 (0xF09E1)
     */
    public data object COMMENT_ARROW_LEFT : MaterialDesignGlyphs(0xF09E1)

    /**
     * 󰧢 (0xF09E2)
     */
    public data object COMMENT_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF09E2)

    /**
     * 󰧣 (0xF09E3)
     */
    public data object COMMENT_ARROW_RIGHT : MaterialDesignGlyphs(0xF09E3)

    /**
     * 󰧤 (0xF09E4)
     */
    public data object COMMENT_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF09E4)

    /**
     * 󰧥 (0xF09E5)
     */
    public data object COMMENT_PLUS : MaterialDesignGlyphs(0xF09E5)

    /**
     * 󰧦 (0xF09E6)
     */
    public data object CURRENCY_PHP : MaterialDesignGlyphs(0xF09E6)

    /**
     * 󰧧 (0xF09E7)
     */
    public data object DELETE_OUTLINE : MaterialDesignGlyphs(0xF09E7)

    /**
     * 󰧨 (0xF09E8)
     */
    public data object DESKTOP_MAC_DASHBOARD : MaterialDesignGlyphs(0xF09E8)

    /**
     * 󰧩 (0xF09E9)
     */
    public data object DOWNLOAD_MULTIPLE : MaterialDesignGlyphs(0xF09E9)

    /**
     * 󰧪 (0xF09EA)
     */
    public data object EIGHT_TRACK : MaterialDesignGlyphs(0xF09EA)

    /**
     * 󰧫 (0xF09EB)
     */
    public data object EMAIL_PLUS : MaterialDesignGlyphs(0xF09EB)

    /**
     * 󰧬 (0xF09EC)
     */
    public data object EMAIL_PLUS_OUTLINE : MaterialDesignGlyphs(0xF09EC)

    /**
     * 󰧭 (0xF09ED)
     */
    public data object TEXT_BOX_OUTLINE : MaterialDesignGlyphs(0xF09ED)

    /**
     * 󰧮 (0xF09EE)
     */
    public data object FILE_DOCUMENT_OUTLINE : MaterialDesignGlyphs(0xF09EE)

    /**
     * 󰧯 (0xF09EF)
     */
    public data object FLOPPY_VARIANT : MaterialDesignGlyphs(0xF09EF)

    /**
     * 󰧰 (0xF09F0)
     */
    public data object FLOWER_OUTLINE : MaterialDesignGlyphs(0xF09F0)

    /**
     * 󰧱 (0xF09F1)
     */
    public data object FLOWER_TULIP : MaterialDesignGlyphs(0xF09F1)

    /**
     * 󰧲 (0xF09F2)
     */
    public data object FLOWER_TULIP_OUTLINE : MaterialDesignGlyphs(0xF09F2)

    /**
     * 󰧳 (0xF09F3)
     */
    public data object FORMAT_FONT_SIZE_DECREASE : MaterialDesignGlyphs(0xF09F3)

    /**
     * 󰧴 (0xF09F4)
     */
    public data object FORMAT_FONT_SIZE_INCREASE : MaterialDesignGlyphs(0xF09F4)

    /**
     * 󰧵 (0xF09F5)
     */
    public data object GHOST_OFF : MaterialDesignGlyphs(0xF09F5)

    /**
     * 󰧶 (0xF09F6)
     */
    public data object GOOGLE_LENS : MaterialDesignGlyphs(0xF09F6)

    /**
     * 󰧷 (0xF09F7)
     */
    public data object GOOGLE_SPREADSHEET : MaterialDesignGlyphs(0xF09F7)

    /**
     * 󰧸 (0xF09F8)
     */
    public data object IMAGE_MOVE : MaterialDesignGlyphs(0xF09F8)

    /**
     * 󰧹 (0xF09F9)
     */
    public data object KEYBOARD_SETTINGS : MaterialDesignGlyphs(0xF09F9)

    /**
     * 󰧺 (0xF09FA)
     */
    public data object KEYBOARD_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF09FA)

    /**
     * 󰧻 (0xF09FB)
     */
    public data object KNIFE : MaterialDesignGlyphs(0xF09FB)

    /**
     * 󰧼 (0xF09FC)
     */
    public data object KNIFE_MILITARY : MaterialDesignGlyphs(0xF09FC)

    /**
     * 󰧽 (0xF09FD)
     */
    public data object LAYERS_OFF_OUTLINE : MaterialDesignGlyphs(0xF09FD)

    /**
     * 󰧾 (0xF09FE)
     */
    public data object LAYERS_OUTLINE : MaterialDesignGlyphs(0xF09FE)

    /**
     * 󰧿 (0xF09FF)
     */
    public data object LIGHTHOUSE : MaterialDesignGlyphs(0xF09FF)

    /**
     * 󰨀 (0xF0A00)
     */
    public data object LIGHTHOUSE_ON : MaterialDesignGlyphs(0xF0A00)

    /**
     * 󰨁 (0xF0A01)
     */
    public data object MAP_LEGEND : MaterialDesignGlyphs(0xF0A01)

    /**
     * 󰨂 (0xF0A02)
     */
    public data object MENU_LEFT_OUTLINE : MaterialDesignGlyphs(0xF0A02)

    /**
     * 󰨃 (0xF0A03)
     */
    public data object MENU_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF0A03)

    /**
     * 󰨄 (0xF0A04)
     */
    public data object MESSAGE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0A04)

    /**
     * 󰨅 (0xF0A05)
     */
    public data object MINI_SD : MaterialDesignGlyphs(0xF0A05)

    /**
     * 󰨆 (0xF0A06)
     */
    public data object MINIDISC : MaterialDesignGlyphs(0xF0A06)

    /**
     * 󰨇 (0xF0A07)
     */
    public data object MONITOR_DASHBOARD : MaterialDesignGlyphs(0xF0A07)

    /**
     * 󰨈 (0xF0A08)
     */
    public data object PIRATE : MaterialDesignGlyphs(0xF0A08)

    /**
     * 󰨉 (0xF0A09)
     */
    public data object POKEMON_GO : MaterialDesignGlyphs(0xF0A09)

    /**
     * 󰨊 (0xF0A0A)
     */
    public data object POWERSHELL : MaterialDesignGlyphs(0xF0A0A)

    /**
     * 󰨋 (0xF0A0B)
     */
    public data object PRINTER_WIRELESS : MaterialDesignGlyphs(0xF0A0B)

    /**
     * 󰨌 (0xF0A0C)
     */
    public data object QUALITY_LOW : MaterialDesignGlyphs(0xF0A0C)

    /**
     * 󰨍 (0xF0A0D)
     */
    public data object QUALITY_MEDIUM : MaterialDesignGlyphs(0xF0A0D)

    /**
     * 󰨎 (0xF0A0E)
     */
    public data object REFLECT_HORIZONTAL : MaterialDesignGlyphs(0xF0A0E)

    /**
     * 󰨏 (0xF0A0F)
     */
    public data object REFLECT_VERTICAL : MaterialDesignGlyphs(0xF0A0F)

    /**
     * 󰨐 (0xF0A10)
     */
    public data object RHOMBUS_MEDIUM : MaterialDesignGlyphs(0xF0A10)

    /**
     * 󰨑 (0xF0A11)
     */
    public data object RHOMBUS_SPLIT : MaterialDesignGlyphs(0xF0A11)

    /**
     * 󰨒 (0xF0A12)
     */
    public data object SHIELD_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF0A12)

    /**
     * 󰨓 (0xF0A13)
     */
    public data object SQUARE_MEDIUM : MaterialDesignGlyphs(0xF0A13)

    /**
     * 󰨔 (0xF0A14)
     */
    public data object SQUARE_MEDIUM_OUTLINE : MaterialDesignGlyphs(0xF0A14)

    /**
     * 󰨕 (0xF0A15)
     */
    public data object SQUARE_SMALL : MaterialDesignGlyphs(0xF0A15)

    /**
     * 󰨖 (0xF0A16)
     */
    public data object SUBTITLES : MaterialDesignGlyphs(0xF0A16)

    /**
     * 󰨗 (0xF0A17)
     */
    public data object SUBTITLES_OUTLINE : MaterialDesignGlyphs(0xF0A17)

    /**
     * 󰨘 (0xF0A18)
     */
    public data object TABLE_BORDER : MaterialDesignGlyphs(0xF0A18)

    /**
     * 󰨙 (0xF0A19)
     */
    public data object TOGGLE_SWITCH_OFF_OUTLINE : MaterialDesignGlyphs(0xF0A19)

    /**
     * 󰨚 (0xF0A1A)
     */
    public data object TOGGLE_SWITCH_OUTLINE : MaterialDesignGlyphs(0xF0A1A)

    /**
     * 󰨛 (0xF0A1B)
     */
    public data object VHS : MaterialDesignGlyphs(0xF0A1B)

    /**
     * 󰨜 (0xF0A1C)
     */
    public data object VIDEO_VINTAGE : MaterialDesignGlyphs(0xF0A1C)

    /**
     * 󰨝 (0xF0A1D)
     */
    public data object VIEW_DASHBOARD_OUTLINE : MaterialDesignGlyphs(0xF0A1D)

    /**
     * 󰨞 (0xF0A1E)
     */
    public data object MICROSOFT_VISUAL_STUDIO_CODE : MaterialDesignGlyphs(0xF0A1E)

    /**
     * 󰨟 (0xF0A1F)
     */
    public data object VOTE : MaterialDesignGlyphs(0xF0A1F)

    /**
     * 󰨠 (0xF0A20)
     */
    public data object VOTE_OUTLINE : MaterialDesignGlyphs(0xF0A20)

    /**
     * 󰨡 (0xF0A21)
     */
    public data object MICROSOFT_WINDOWS_CLASSIC : MaterialDesignGlyphs(0xF0A21)

    /**
     * 󰨢 (0xF0A22)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_BATTERY_CHARGING : MaterialDesignGlyphs(0xF0A22)

    /**
     * 󰨣 (0xF0A23)
     */
    public data object ZIP_DISK : MaterialDesignGlyphs(0xF0A23)

    /**
     * 󰨤 (0xF0A24)
     */
    public data object ASPECT_RATIO : MaterialDesignGlyphs(0xF0A24)

    /**
     * 󰨥 (0xF0A25)
     */
    public data object BABEL : MaterialDesignGlyphs(0xF0A25)

    /**
     * 󰨦 (0xF0A26)
     */
    public data object BALLOON : MaterialDesignGlyphs(0xF0A26)

    /**
     * 󰨧 (0xF0A27)
     */
    public data object BANK_TRANSFER : MaterialDesignGlyphs(0xF0A27)

    /**
     * 󰨨 (0xF0A28)
     */
    public data object BANK_TRANSFER_IN : MaterialDesignGlyphs(0xF0A28)

    /**
     * 󰨩 (0xF0A29)
     */
    public data object BANK_TRANSFER_OUT : MaterialDesignGlyphs(0xF0A29)

    /**
     * 󰨪 (0xF0A2A)
     */
    public data object BRIEFCASE_MINUS : MaterialDesignGlyphs(0xF0A2A)

    /**
     * 󰨫 (0xF0A2B)
     */
    public data object BRIEFCASE_PLUS : MaterialDesignGlyphs(0xF0A2B)

    /**
     * 󰨬 (0xF0A2C)
     */
    public data object BRIEFCASE_REMOVE : MaterialDesignGlyphs(0xF0A2C)

    /**
     * 󰨭 (0xF0A2D)
     */
    public data object BRIEFCASE_SEARCH : MaterialDesignGlyphs(0xF0A2D)

    /**
     * 󰨮 (0xF0A2E)
     */
    public data object BUG_CHECK : MaterialDesignGlyphs(0xF0A2E)

    /**
     * 󰨯 (0xF0A2F)
     */
    public data object BUG_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0A2F)

    /**
     * 󰨰 (0xF0A30)
     */
    public data object BUG_OUTLINE : MaterialDesignGlyphs(0xF0A30)

    /**
     * 󰨱 (0xF0A31)
     */
    public data object CALENDAR_ALERT : MaterialDesignGlyphs(0xF0A31)

    /**
     * 󰨲 (0xF0A32)
     */
    public data object CALENDAR_MULTISELECT : MaterialDesignGlyphs(0xF0A32)

    /**
     * 󰨳 (0xF0A33)
     */
    public data object CALENDAR_WEEK : MaterialDesignGlyphs(0xF0A33)

    /**
     * 󰨴 (0xF0A34)
     */
    public data object CALENDAR_WEEK_BEGIN : MaterialDesignGlyphs(0xF0A34)

    /**
     * 󰨵 (0xF0A35)
     */
    public data object CELLPHONE_SCREENSHOT : MaterialDesignGlyphs(0xF0A35)

    /**
     * 󰨶 (0xF0A36)
     */
    public data object CITY_VARIANT : MaterialDesignGlyphs(0xF0A36)

    /**
     * 󰨷 (0xF0A37)
     */
    public data object CITY_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF0A37)

    /**
     * 󰨸 (0xF0A38)
     */
    public data object CLIPBOARD_TEXT_OUTLINE : MaterialDesignGlyphs(0xF0A38)

    /**
     * 󰨹 (0xF0A39)
     */
    public data object CLOUD_QUESTION : MaterialDesignGlyphs(0xF0A39)

    /**
     * 󰨺 (0xF0A3A)
     */
    public data object COMMENT_EYE : MaterialDesignGlyphs(0xF0A3A)

    /**
     * 󰨻 (0xF0A3B)
     */
    public data object COMMENT_EYE_OUTLINE : MaterialDesignGlyphs(0xF0A3B)

    /**
     * 󰨼 (0xF0A3C)
     */
    public data object COMMENT_SEARCH : MaterialDesignGlyphs(0xF0A3C)

    /**
     * 󰨽 (0xF0A3D)
     */
    public data object COMMENT_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0A3D)

    /**
     * 󰨾 (0xF0A3E)
     */
    public data object CONTAIN : MaterialDesignGlyphs(0xF0A3E)

    /**
     * 󰨿 (0xF0A3F)
     */
    public data object CONTAIN_END : MaterialDesignGlyphs(0xF0A3F)

    /**
     * 󰩀 (0xF0A40)
     */
    public data object CONTAIN_START : MaterialDesignGlyphs(0xF0A40)

    /**
     * 󰩁 (0xF0A41)
     */
    public data object DLNA : MaterialDesignGlyphs(0xF0A41)

    /**
     * 󰩂 (0xF0A42)
     */
    public data object DOCTOR : MaterialDesignGlyphs(0xF0A42)

    /**
     * 󰩃 (0xF0A43)
     */
    public data object DOG : MaterialDesignGlyphs(0xF0A43)

    /**
     * 󰩄 (0xF0A44)
     */
    public data object DOG_SIDE : MaterialDesignGlyphs(0xF0A44)

    /**
     * 󰩅 (0xF0A45)
     */
    public data object EAR_HEARING_OFF : MaterialDesignGlyphs(0xF0A45)

    /**
     * 󰩆 (0xF0A46)
     */
    public data object ENGINE_OFF : MaterialDesignGlyphs(0xF0A46)

    /**
     * 󰩇 (0xF0A47)
     */
    public data object ENGINE_OFF_OUTLINE : MaterialDesignGlyphs(0xF0A47)

    /**
     * 󰩈 (0xF0A48)
     */
    public data object EXIT_RUN : MaterialDesignGlyphs(0xF0A48)

    /**
     * 󰩉 (0xF0A49)
     */
    public data object FEATURE_SEARCH : MaterialDesignGlyphs(0xF0A49)

    /**
     * 󰩊 (0xF0A4A)
     */
    public data object FEATURE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0A4A)

    /**
     * 󰩋 (0xF0A4B)
     */
    public data object FILE_ALERT : MaterialDesignGlyphs(0xF0A4B)

    /**
     * 󰩌 (0xF0A4C)
     */
    public data object FILE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0A4C)

    /**
     * 󰩍 (0xF0A4D)
     */
    public data object FILE_UPLOAD : MaterialDesignGlyphs(0xF0A4D)

    /**
     * 󰩎 (0xF0A4E)
     */
    public data object FILE_UPLOAD_OUTLINE : MaterialDesignGlyphs(0xF0A4E)

    /**
     * 󰩏 (0xF0A4F)
     */
    public data object HAND_FRONT_RIGHT : MaterialDesignGlyphs(0xF0A4F)

    /**
     * 󰩐 (0xF0A50)
     */
    public data object HAND_OKAY : MaterialDesignGlyphs(0xF0A50)

    /**
     * 󰩑 (0xF0A51)
     */
    public data object HAND_PEACE : MaterialDesignGlyphs(0xF0A51)

    /**
     * 󰩒 (0xF0A52)
     */
    public data object HAND_PEACE_VARIANT : MaterialDesignGlyphs(0xF0A52)

    /**
     * 󰩓 (0xF0A53)
     */
    public data object HAND_POINTING_DOWN : MaterialDesignGlyphs(0xF0A53)

    /**
     * 󰩔 (0xF0A54)
     */
    public data object HAND_POINTING_LEFT : MaterialDesignGlyphs(0xF0A54)

    /**
     * 󰩕 (0xF0A55)
     */
    public data object HAND_POINTING_UP : MaterialDesignGlyphs(0xF0A55)

    /**
     * 󰩖 (0xF0A56)
     */
    public data object HEART_MULTIPLE : MaterialDesignGlyphs(0xF0A56)

    /**
     * 󰩗 (0xF0A57)
     */
    public data object HEART_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0A57)

    /**
     * 󰩘 (0xF0A58)
     */
    public data object HORSESHOE : MaterialDesignGlyphs(0xF0A58)

    /**
     * 󰩙 (0xF0A59)
     */
    public data object HUMAN_FEMALE_BOY : MaterialDesignGlyphs(0xF0A59)

    /**
     * 󰩚 (0xF0A5A)
     */
    public data object HUMAN_FEMALE_FEMALE : MaterialDesignGlyphs(0xF0A5A)

    /**
     * 󰩛 (0xF0A5B)
     */
    public data object HUMAN_FEMALE_GIRL : MaterialDesignGlyphs(0xF0A5B)

    /**
     * 󰩜 (0xF0A5C)
     */
    public data object HUMAN_MALE_BOY : MaterialDesignGlyphs(0xF0A5C)

    /**
     * 󰩝 (0xF0A5D)
     */
    public data object HUMAN_MALE_GIRL : MaterialDesignGlyphs(0xF0A5D)

    /**
     * 󰩞 (0xF0A5E)
     */
    public data object HUMAN_MALE_MALE : MaterialDesignGlyphs(0xF0A5E)

    /**
     * 󰩟 (0xF0A5F)
     */
    public data object IP : MaterialDesignGlyphs(0xF0A5F)

    /**
     * 󰩠 (0xF0A60)
     */
    public data object IP_NETWORK : MaterialDesignGlyphs(0xF0A60)

    /**
     * 󰩡 (0xF0A61)
     */
    public data object LITECOIN : MaterialDesignGlyphs(0xF0A61)

    /**
     * 󰩢 (0xF0A62)
     */
    public data object MAGNIFY_MINUS_CURSOR : MaterialDesignGlyphs(0xF0A62)

    /**
     * 󰩣 (0xF0A63)
     */
    public data object MAGNIFY_PLUS_CURSOR : MaterialDesignGlyphs(0xF0A63)

    /**
     * 󰩤 (0xF0A64)
     */
    public data object MENU_SWAP : MaterialDesignGlyphs(0xF0A64)

    /**
     * 󰩥 (0xF0A65)
     */
    public data object MENU_SWAP_OUTLINE : MaterialDesignGlyphs(0xF0A65)

    /**
     * 󰩦 (0xF0A66)
     */
    public data object PUZZLE_OUTLINE : MaterialDesignGlyphs(0xF0A66)

    /**
     * 󰩧 (0xF0A67)
     */
    public data object REGISTERED_TRADEMARK : MaterialDesignGlyphs(0xF0A67)

    /**
     * 󰩨 (0xF0A68)
     */
    public data object RESIZE : MaterialDesignGlyphs(0xF0A68)

    /**
     * 󰩩 (0xF0A69)
     */
    public data object ROUTER_WIRELESS_SETTINGS : MaterialDesignGlyphs(0xF0A69)

    /**
     * 󰩪 (0xF0A6A)
     */
    public data object SAFE : MaterialDesignGlyphs(0xF0A6A)

    /**
     * 󰩫 (0xF0A6B)
     */
    public data object SCISSORS_CUTTING : MaterialDesignGlyphs(0xF0A6B)

    /**
     * 󰩬 (0xF0A6C)
     */
    public data object SELECT_DRAG : MaterialDesignGlyphs(0xF0A6C)

    /**
     * 󰩭 (0xF0A6D)
     */
    public data object SELECTION_DRAG : MaterialDesignGlyphs(0xF0A6D)

    /**
     * 󰩮 (0xF0A6E)
     */
    public data object SETTINGS_HELPER : MaterialDesignGlyphs(0xF0A6E)

    /**
     * 󰩯 (0xF0A6F)
     */
    public data object SIGNAL_5G : MaterialDesignGlyphs(0xF0A6F)

    /**
     * 󰩰 (0xF0A70)
     */
    public data object SILVERWARE_FORK_KNIFE : MaterialDesignGlyphs(0xF0A70)

    /**
     * 󰩱 (0xF0A71)
     */
    public data object SMOG : MaterialDesignGlyphs(0xF0A71)

    /**
     * 󰩲 (0xF0A72)
     */
    public data object SOLAR_POWER : MaterialDesignGlyphs(0xF0A72)

    /**
     * 󰩳 (0xF0A73)
     */
    public data object STAR_BOX : MaterialDesignGlyphs(0xF0A73)

    /**
     * 󰩴 (0xF0A74)
     */
    public data object STAR_BOX_OUTLINE : MaterialDesignGlyphs(0xF0A74)

    /**
     * 󰩵 (0xF0A75)
     */
    public data object TABLE_PLUS : MaterialDesignGlyphs(0xF0A75)

    /**
     * 󰩶 (0xF0A76)
     */
    public data object TABLE_REMOVE : MaterialDesignGlyphs(0xF0A76)

    /**
     * 󰩷 (0xF0A77)
     */
    public data object TARGET_VARIANT : MaterialDesignGlyphs(0xF0A77)

    /**
     * 󰩸 (0xF0A78)
     */
    public data object TRADEMARK : MaterialDesignGlyphs(0xF0A78)

    /**
     * 󰩹 (0xF0A79)
     */
    public data object TRASH_CAN : MaterialDesignGlyphs(0xF0A79)

    /**
     * 󰩺 (0xF0A7A)
     */
    public data object TRASH_CAN_OUTLINE : MaterialDesignGlyphs(0xF0A7A)

    /**
     * 󰩻 (0xF0A7B)
     */
    public data object TSHIRT_CREW : MaterialDesignGlyphs(0xF0A7B)

    /**
     * 󰩼 (0xF0A7C)
     */
    public data object TSHIRT_V : MaterialDesignGlyphs(0xF0A7C)

    /**
     * 󰩽 (0xF0A7D)
     */
    public data object ZODIAC_AQUARIUS : MaterialDesignGlyphs(0xF0A7D)

    /**
     * 󰩾 (0xF0A7E)
     */
    public data object ZODIAC_ARIES : MaterialDesignGlyphs(0xF0A7E)

    /**
     * 󰩿 (0xF0A7F)
     */
    public data object ZODIAC_CANCER : MaterialDesignGlyphs(0xF0A7F)

    /**
     * 󰪀 (0xF0A80)
     */
    public data object ZODIAC_CAPRICORN : MaterialDesignGlyphs(0xF0A80)

    /**
     * 󰪁 (0xF0A81)
     */
    public data object ZODIAC_GEMINI : MaterialDesignGlyphs(0xF0A81)

    /**
     * 󰪂 (0xF0A82)
     */
    public data object ZODIAC_LEO : MaterialDesignGlyphs(0xF0A82)

    /**
     * 󰪃 (0xF0A83)
     */
    public data object ZODIAC_LIBRA : MaterialDesignGlyphs(0xF0A83)

    /**
     * 󰪄 (0xF0A84)
     */
    public data object ZODIAC_PISCES : MaterialDesignGlyphs(0xF0A84)

    /**
     * 󰪅 (0xF0A85)
     */
    public data object ZODIAC_SAGITTARIUS : MaterialDesignGlyphs(0xF0A85)

    /**
     * 󰪆 (0xF0A86)
     */
    public data object ZODIAC_SCORPIO : MaterialDesignGlyphs(0xF0A86)

    /**
     * 󰪇 (0xF0A87)
     */
    public data object ZODIAC_TAURUS : MaterialDesignGlyphs(0xF0A87)

    /**
     * 󰪈 (0xF0A88)
     */
    public data object ZODIAC_VIRGO : MaterialDesignGlyphs(0xF0A88)

    /**
     * 󰪉 (0xF0A89)
     */
    public data object ACCOUNT_CHILD : MaterialDesignGlyphs(0xF0A89)

    /**
     * 󰪊 (0xF0A8A)
     */
    public data object ACCOUNT_CHILD_CIRCLE : MaterialDesignGlyphs(0xF0A8A)

    /**
     * 󰪋 (0xF0A8B)
     */
    public data object ACCOUNT_SUPERVISOR : MaterialDesignGlyphs(0xF0A8B)

    /**
     * 󰪌 (0xF0A8C)
     */
    public data object ACCOUNT_SUPERVISOR_CIRCLE : MaterialDesignGlyphs(0xF0A8C)

    /**
     * 󰪍 (0xF0A8D)
     */
    public data object AMPERSAND : MaterialDesignGlyphs(0xF0A8D)

    /**
     * 󰪎 (0xF0A8E)
     */
    public data object WEB_OFF : MaterialDesignGlyphs(0xF0A8E)

    /**
     * 󰪏 (0xF0A8F)
     */
    public data object ANIMATION_OUTLINE : MaterialDesignGlyphs(0xF0A8F)

    /**
     * 󰪐 (0xF0A90)
     */
    public data object ANIMATION_PLAY_OUTLINE : MaterialDesignGlyphs(0xF0A90)

    /**
     * 󰪑 (0xF0A91)
     */
    public data object BELL_OFF_OUTLINE : MaterialDesignGlyphs(0xF0A91)

    /**
     * 󰪒 (0xF0A92)
     */
    public data object BELL_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0A92)

    /**
     * 󰪓 (0xF0A93)
     */
    public data object BELL_SLEEP_OUTLINE : MaterialDesignGlyphs(0xF0A93)

    /**
     * 󰪔 (0xF0A94)
     */
    public data object BOOK_MINUS_MULTIPLE : MaterialDesignGlyphs(0xF0A94)

    /**
     * 󰪕 (0xF0A95)
     */
    public data object BOOK_PLUS_MULTIPLE : MaterialDesignGlyphs(0xF0A95)

    /**
     * 󰪖 (0xF0A96)
     */
    public data object BOOK_REMOVE_MULTIPLE : MaterialDesignGlyphs(0xF0A96)

    /**
     * 󰪗 (0xF0A97)
     */
    public data object BOOK_REMOVE : MaterialDesignGlyphs(0xF0A97)

    /**
     * 󰪘 (0xF0A98)
     */
    public data object BRIEFCASE_EDIT : MaterialDesignGlyphs(0xF0A98)

    /**
     * 󰪙 (0xF0A99)
     */
    public data object BUS_ALERT : MaterialDesignGlyphs(0xF0A99)

    /**
     * 󰪚 (0xF0A9A)
     */
    public data object CALCULATOR_VARIANT : MaterialDesignGlyphs(0xF0A9A)

    /**
     * 󰪛 (0xF0A9B)
     */
    public data object CAPS_LOCK : MaterialDesignGlyphs(0xF0A9B)

    /**
     * 󰪜 (0xF0A9C)
     */
    public data object CASH_REFUND : MaterialDesignGlyphs(0xF0A9C)

    /**
     * 󰪝 (0xF0A9D)
     */
    public data object CHECKBOOK : MaterialDesignGlyphs(0xF0A9D)

    /**
     * 󰪞 (0xF0A9E)
     */
    public data object CIRCLE_SLICE_1 : MaterialDesignGlyphs(0xF0A9E)

    /**
     * 󰪟 (0xF0A9F)
     */
    public data object CIRCLE_SLICE_2 : MaterialDesignGlyphs(0xF0A9F)

    /**
     * 󰪠 (0xF0AA0)
     */
    public data object CIRCLE_SLICE_3 : MaterialDesignGlyphs(0xF0AA0)

    /**
     * 󰪡 (0xF0AA1)
     */
    public data object CIRCLE_SLICE_4 : MaterialDesignGlyphs(0xF0AA1)

    /**
     * 󰪢 (0xF0AA2)
     */
    public data object CIRCLE_SLICE_5 : MaterialDesignGlyphs(0xF0AA2)

    /**
     * 󰪣 (0xF0AA3)
     */
    public data object CIRCLE_SLICE_6 : MaterialDesignGlyphs(0xF0AA3)

    /**
     * 󰪤 (0xF0AA4)
     */
    public data object CIRCLE_SLICE_7 : MaterialDesignGlyphs(0xF0AA4)

    /**
     * 󰪥 (0xF0AA5)
     */
    public data object CIRCLE_SLICE_8 : MaterialDesignGlyphs(0xF0AA5)

    /**
     * 󰪦 (0xF0AA6)
     */
    public data object COLLAPSE_ALL : MaterialDesignGlyphs(0xF0AA6)

    /**
     * 󰪧 (0xF0AA7)
     */
    public data object COLLAPSE_ALL_OUTLINE : MaterialDesignGlyphs(0xF0AA7)

    /**
     * 󰪨 (0xF0AA8)
     */
    public data object CREDIT_CARD_REFUND_OUTLINE : MaterialDesignGlyphs(0xF0AA8)

    /**
     * 󰪩 (0xF0AA9)
     */
    public data object DATABASE_CHECK : MaterialDesignGlyphs(0xF0AA9)

    /**
     * 󰪪 (0xF0AAA)
     */
    public data object DATABASE_LOCK : MaterialDesignGlyphs(0xF0AAA)

    /**
     * 󰪫 (0xF0AAB)
     */
    public data object DESKTOP_TOWER_MONITOR : MaterialDesignGlyphs(0xF0AAB)

    /**
     * 󰪬 (0xF0AAC)
     */
    public data object DISHWASHER : MaterialDesignGlyphs(0xF0AAC)

    /**
     * 󰪭 (0xF0AAD)
     */
    public data object DOG_SERVICE : MaterialDesignGlyphs(0xF0AAD)

    /**
     * 󰪮 (0xF0AAE)
     */
    public data object DOT_NET : MaterialDesignGlyphs(0xF0AAE)

    /**
     * 󰪯 (0xF0AAF)
     */
    public data object EGG : MaterialDesignGlyphs(0xF0AAF)

    /**
     * 󰪰 (0xF0AB0)
     */
    public data object EGG_EASTER : MaterialDesignGlyphs(0xF0AB0)

    /**
     * 󰪱 (0xF0AB1)
     */
    public data object EMAIL_CHECK : MaterialDesignGlyphs(0xF0AB1)

    /**
     * 󰪲 (0xF0AB2)
     */
    public data object EMAIL_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0AB2)

    /**
     * 󰪳 (0xF0AB3)
     */
    public data object ET : MaterialDesignGlyphs(0xF0AB3)

    /**
     * 󰪴 (0xF0AB4)
     */
    public data object EXPAND_ALL : MaterialDesignGlyphs(0xF0AB4)

    /**
     * 󰪵 (0xF0AB5)
     */
    public data object EXPAND_ALL_OUTLINE : MaterialDesignGlyphs(0xF0AB5)

    /**
     * 󰪶 (0xF0AB6)
     */
    public data object FILE_CABINET : MaterialDesignGlyphs(0xF0AB6)

    /**
     * 󰪷 (0xF0AB7)
     */
    public data object TEXT_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0AB7)

    /**
     * 󰪸 (0xF0AB8)
     */
    public data object TEXT_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0AB8)

    /**
     * 󰪹 (0xF0AB9)
     */
    public data object FILE_MOVE : MaterialDesignGlyphs(0xF0AB9)

    /**
     * 󰪺 (0xF0ABA)
     */
    public data object FOLDER_CLOCK : MaterialDesignGlyphs(0xF0ABA)

    /**
     * 󰪻 (0xF0ABB)
     */
    public data object FOLDER_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF0ABB)

    /**
     * 󰪼 (0xF0ABC)
     */
    public data object FORMAT_ANNOTATION_MINUS : MaterialDesignGlyphs(0xF0ABC)

    /**
     * 󰪽 (0xF0ABD)
     */
    public data object GESTURE_PINCH : MaterialDesignGlyphs(0xF0ABD)

    /**
     * 󰪾 (0xF0ABE)
     */
    public data object GESTURE_SPREAD : MaterialDesignGlyphs(0xF0ABE)

    /**
     * 󰪿 (0xF0ABF)
     */
    public data object GESTURE_SWIPE_HORIZONTAL : MaterialDesignGlyphs(0xF0ABF)

    /**
     * 󰫀 (0xF0AC0)
     */
    public data object GESTURE_SWIPE_VERTICAL : MaterialDesignGlyphs(0xF0AC0)

    /**
     * 󰫁 (0xF0AC1)
     */
    public data object HAIL : MaterialDesignGlyphs(0xF0AC1)

    /**
     * 󰫂 (0xF0AC2)
     */
    public data object HELICOPTER : MaterialDesignGlyphs(0xF0AC2)

    /**
     * 󰫃 (0xF0AC3)
     */
    public data object HEXAGON_SLICE_1 : MaterialDesignGlyphs(0xF0AC3)

    /**
     * 󰫄 (0xF0AC4)
     */
    public data object HEXAGON_SLICE_2 : MaterialDesignGlyphs(0xF0AC4)

    /**
     * 󰫅 (0xF0AC5)
     */
    public data object HEXAGON_SLICE_3 : MaterialDesignGlyphs(0xF0AC5)

    /**
     * 󰫆 (0xF0AC6)
     */
    public data object HEXAGON_SLICE_4 : MaterialDesignGlyphs(0xF0AC6)

    /**
     * 󰫇 (0xF0AC7)
     */
    public data object HEXAGON_SLICE_5 : MaterialDesignGlyphs(0xF0AC7)

    /**
     * 󰫈 (0xF0AC8)
     */
    public data object HEXAGON_SLICE_6 : MaterialDesignGlyphs(0xF0AC8)

    /**
     * 󰫉 (0xF0AC9)
     */
    public data object HEXAGRAM : MaterialDesignGlyphs(0xF0AC9)

    /**
     * 󰫊 (0xF0ACA)
     */
    public data object HEXAGRAM_OUTLINE : MaterialDesignGlyphs(0xF0ACA)

    /**
     * 󰫋 (0xF0ACB)
     */
    public data object LABEL_OFF : MaterialDesignGlyphs(0xF0ACB)

    /**
     * 󰫌 (0xF0ACC)
     */
    public data object LABEL_OFF_OUTLINE : MaterialDesignGlyphs(0xF0ACC)

    /**
     * 󰫍 (0xF0ACD)
     */
    public data object LABEL_VARIANT : MaterialDesignGlyphs(0xF0ACD)

    /**
     * 󰫎 (0xF0ACE)
     */
    public data object LABEL_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF0ACE)

    /**
     * 󰫏 (0xF0ACF)
     */
    public data object LANGUAGE_RUBY_ON_RAILS : MaterialDesignGlyphs(0xF0ACF)

    /**
     * 󰫐 (0xF0AD0)
     */
    public data object LARAVEL : MaterialDesignGlyphs(0xF0AD0)

    /**
     * 󰫑 (0xF0AD1)
     */
    public data object MASTODON : MaterialDesignGlyphs(0xF0AD1)

    /**
     * 󰫒 (0xF0AD2)
     */
    public data object SORT_NUMERIC_DESCENDING_VARIANT : MaterialDesignGlyphs(0xF0AD2)

    /**
     * 󰫓 (0xF0AD3)
     */
    public data object MINUS_CIRCLE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0AD3)

    /**
     * 󰫔 (0xF0AD4)
     */
    public data object MUSIC_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0AD4)

    /**
     * 󰫕 (0xF0AD5)
     */
    public data object PINWHEEL : MaterialDesignGlyphs(0xF0AD5)

    /**
     * 󰫖 (0xF0AD6)
     */
    public data object PINWHEEL_OUTLINE : MaterialDesignGlyphs(0xF0AD6)

    /**
     * 󰫗 (0xF0AD7)
     */
    public data object RADIATOR_DISABLED : MaterialDesignGlyphs(0xF0AD7)

    /**
     * 󰫘 (0xF0AD8)
     */
    public data object RADIATOR_OFF : MaterialDesignGlyphs(0xF0AD8)

    /**
     * 󰫙 (0xF0AD9)
     */
    public data object SELECT_COMPARE : MaterialDesignGlyphs(0xF0AD9)

    /**
     * 󰫚 (0xF0ADA)
     */
    public data object SHIELD_PLUS : MaterialDesignGlyphs(0xF0ADA)

    /**
     * 󰫛 (0xF0ADB)
     */
    public data object SHIELD_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0ADB)

    /**
     * 󰫜 (0xF0ADC)
     */
    public data object SHIELD_REMOVE : MaterialDesignGlyphs(0xF0ADC)

    /**
     * 󰫝 (0xF0ADD)
     */
    public data object SHIELD_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0ADD)

    /**
     * 󰫞 (0xF0ADE)
     */
    public data object BOOK_PLUS_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0ADE)

    /**
     * 󰫟 (0xF0ADF)
     */
    public data object SINA_WEIBO : MaterialDesignGlyphs(0xF0ADF)

    /**
     * 󰫠 (0xF0AE0)
     */
    public data object SPRAY_BOTTLE : MaterialDesignGlyphs(0xF0AE0)

    /**
     * 󰫡 (0xF0AE1)
     */
    public data object SQUEEGEE : MaterialDesignGlyphs(0xF0AE1)

    /**
     * 󰫢 (0xF0AE2)
     */
    public data object STAR_FOUR_POINTS : MaterialDesignGlyphs(0xF0AE2)

    /**
     * 󰫣 (0xF0AE3)
     */
    public data object STAR_FOUR_POINTS_OUTLINE : MaterialDesignGlyphs(0xF0AE3)

    /**
     * 󰫤 (0xF0AE4)
     */
    public data object STAR_THREE_POINTS : MaterialDesignGlyphs(0xF0AE4)

    /**
     * 󰫥 (0xF0AE5)
     */
    public data object STAR_THREE_POINTS_OUTLINE : MaterialDesignGlyphs(0xF0AE5)

    /**
     * 󰫦 (0xF0AE6)
     */
    public data object SYMFONY : MaterialDesignGlyphs(0xF0AE6)

    /**
     * 󰫧 (0xF0AE7)
     */
    public data object VARIABLE : MaterialDesignGlyphs(0xF0AE7)

    /**
     * 󰫨 (0xF0AE8)
     */
    public data object VECTOR_BEZIER : MaterialDesignGlyphs(0xF0AE8)

    /**
     * 󰫩 (0xF0AE9)
     */
    public data object WIPER : MaterialDesignGlyphs(0xF0AE9)

    /**
     * 󰫪 (0xF0AEA)
     */
    public data object Z_WAVE : MaterialDesignGlyphs(0xF0AEA)

    /**
     * 󰫫 (0xF0AEB)
     */
    public data object ZEND : MaterialDesignGlyphs(0xF0AEB)

    /**
     * 󰫬 (0xF0AEC)
     */
    public data object ACCOUNT_MINUS_OUTLINE : MaterialDesignGlyphs(0xF0AEC)

    /**
     * 󰫭 (0xF0AED)
     */
    public data object ACCOUNT_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0AED)

    /**
     * 󰫮 (0xF0AEE)
     */
    public data object ALPHA_A : MaterialDesignGlyphs(0xF0AEE)

    /**
     * 󰫯 (0xF0AEF)
     */
    public data object ALPHA_B : MaterialDesignGlyphs(0xF0AEF)

    /**
     * 󰫰 (0xF0AF0)
     */
    public data object ALPHA_C : MaterialDesignGlyphs(0xF0AF0)

    /**
     * 󰫱 (0xF0AF1)
     */
    public data object ALPHA_D : MaterialDesignGlyphs(0xF0AF1)

    /**
     * 󰫲 (0xF0AF2)
     */
    public data object ALPHA_E : MaterialDesignGlyphs(0xF0AF2)

    /**
     * 󰫳 (0xF0AF3)
     */
    public data object ALPHA_F : MaterialDesignGlyphs(0xF0AF3)

    /**
     * 󰫴 (0xF0AF4)
     */
    public data object ALPHA_G : MaterialDesignGlyphs(0xF0AF4)

    /**
     * 󰫵 (0xF0AF5)
     */
    public data object ALPHA_H : MaterialDesignGlyphs(0xF0AF5)

    /**
     * 󰫶 (0xF0AF6)
     */
    public data object ALPHA_I : MaterialDesignGlyphs(0xF0AF6)

    /**
     * 󰫷 (0xF0AF7)
     */
    public data object ALPHA_J : MaterialDesignGlyphs(0xF0AF7)

    /**
     * 󰫸 (0xF0AF8)
     */
    public data object ALPHA_K : MaterialDesignGlyphs(0xF0AF8)

    /**
     * 󰫹 (0xF0AF9)
     */
    public data object ALPHA_L : MaterialDesignGlyphs(0xF0AF9)

    /**
     * 󰫺 (0xF0AFA)
     */
    public data object ALPHA_M : MaterialDesignGlyphs(0xF0AFA)

    /**
     * 󰫻 (0xF0AFB)
     */
    public data object ALPHA_N : MaterialDesignGlyphs(0xF0AFB)

    /**
     * 󰫼 (0xF0AFC)
     */
    public data object ALPHA_O : MaterialDesignGlyphs(0xF0AFC)

    /**
     * 󰫽 (0xF0AFD)
     */
    public data object ALPHA_P : MaterialDesignGlyphs(0xF0AFD)

    /**
     * 󰫾 (0xF0AFE)
     */
    public data object ALPHA_Q : MaterialDesignGlyphs(0xF0AFE)

    /**
     * 󰫿 (0xF0AFF)
     */
    public data object ALPHA_R : MaterialDesignGlyphs(0xF0AFF)

    /**
     * 󰬀 (0xF0B00)
     */
    public data object ALPHA_S : MaterialDesignGlyphs(0xF0B00)

    /**
     * 󰬁 (0xF0B01)
     */
    public data object ALPHA_T : MaterialDesignGlyphs(0xF0B01)

    /**
     * 󰬂 (0xF0B02)
     */
    public data object ALPHA_U : MaterialDesignGlyphs(0xF0B02)

    /**
     * 󰬃 (0xF0B03)
     */
    public data object ALPHA_V : MaterialDesignGlyphs(0xF0B03)

    /**
     * 󰬄 (0xF0B04)
     */
    public data object ALPHA_W : MaterialDesignGlyphs(0xF0B04)

    /**
     * 󰬅 (0xF0B05)
     */
    public data object ALPHA_X : MaterialDesignGlyphs(0xF0B05)

    /**
     * 󰬆 (0xF0B06)
     */
    public data object ALPHA_Y : MaterialDesignGlyphs(0xF0B06)

    /**
     * 󰬇 (0xF0B07)
     */
    public data object ALPHA_Z : MaterialDesignGlyphs(0xF0B07)

    /**
     * 󰬈 (0xF0B08)
     */
    public data object ALPHA_A_BOX : MaterialDesignGlyphs(0xF0B08)

    /**
     * 󰬉 (0xF0B09)
     */
    public data object ALPHA_B_BOX : MaterialDesignGlyphs(0xF0B09)

    /**
     * 󰬊 (0xF0B0A)
     */
    public data object ALPHA_C_BOX : MaterialDesignGlyphs(0xF0B0A)

    /**
     * 󰬋 (0xF0B0B)
     */
    public data object ALPHA_D_BOX : MaterialDesignGlyphs(0xF0B0B)

    /**
     * 󰬌 (0xF0B0C)
     */
    public data object ALPHA_E_BOX : MaterialDesignGlyphs(0xF0B0C)

    /**
     * 󰬍 (0xF0B0D)
     */
    public data object ALPHA_F_BOX : MaterialDesignGlyphs(0xF0B0D)

    /**
     * 󰬎 (0xF0B0E)
     */
    public data object ALPHA_G_BOX : MaterialDesignGlyphs(0xF0B0E)

    /**
     * 󰬏 (0xF0B0F)
     */
    public data object ALPHA_H_BOX : MaterialDesignGlyphs(0xF0B0F)

    /**
     * 󰬐 (0xF0B10)
     */
    public data object ALPHA_I_BOX : MaterialDesignGlyphs(0xF0B10)

    /**
     * 󰬑 (0xF0B11)
     */
    public data object ALPHA_J_BOX : MaterialDesignGlyphs(0xF0B11)

    /**
     * 󰬒 (0xF0B12)
     */
    public data object ALPHA_K_BOX : MaterialDesignGlyphs(0xF0B12)

    /**
     * 󰬓 (0xF0B13)
     */
    public data object ALPHA_L_BOX : MaterialDesignGlyphs(0xF0B13)

    /**
     * 󰬔 (0xF0B14)
     */
    public data object ALPHA_M_BOX : MaterialDesignGlyphs(0xF0B14)

    /**
     * 󰬕 (0xF0B15)
     */
    public data object ALPHA_N_BOX : MaterialDesignGlyphs(0xF0B15)

    /**
     * 󰬖 (0xF0B16)
     */
    public data object ALPHA_O_BOX : MaterialDesignGlyphs(0xF0B16)

    /**
     * 󰬗 (0xF0B17)
     */
    public data object ALPHA_P_BOX : MaterialDesignGlyphs(0xF0B17)

    /**
     * 󰬘 (0xF0B18)
     */
    public data object ALPHA_Q_BOX : MaterialDesignGlyphs(0xF0B18)

    /**
     * 󰬙 (0xF0B19)
     */
    public data object ALPHA_R_BOX : MaterialDesignGlyphs(0xF0B19)

    /**
     * 󰬚 (0xF0B1A)
     */
    public data object ALPHA_S_BOX : MaterialDesignGlyphs(0xF0B1A)

    /**
     * 󰬛 (0xF0B1B)
     */
    public data object ALPHA_T_BOX : MaterialDesignGlyphs(0xF0B1B)

    /**
     * 󰬜 (0xF0B1C)
     */
    public data object ALPHA_U_BOX : MaterialDesignGlyphs(0xF0B1C)

    /**
     * 󰬝 (0xF0B1D)
     */
    public data object ALPHA_V_BOX : MaterialDesignGlyphs(0xF0B1D)

    /**
     * 󰬞 (0xF0B1E)
     */
    public data object ALPHA_W_BOX : MaterialDesignGlyphs(0xF0B1E)

    /**
     * 󰬟 (0xF0B1F)
     */
    public data object ALPHA_X_BOX : MaterialDesignGlyphs(0xF0B1F)

    /**
     * 󰬠 (0xF0B20)
     */
    public data object ALPHA_Y_BOX : MaterialDesignGlyphs(0xF0B20)

    /**
     * 󰬡 (0xF0B21)
     */
    public data object ALPHA_Z_BOX : MaterialDesignGlyphs(0xF0B21)

    /**
     * 󰬢 (0xF0B22)
     */
    public data object BULLDOZER : MaterialDesignGlyphs(0xF0B22)

    /**
     * 󰬣 (0xF0B23)
     */
    public data object BULLHORN_OUTLINE : MaterialDesignGlyphs(0xF0B23)

    /**
     * 󰬤 (0xF0B24)
     */
    public data object CALENDAR_EXPORT : MaterialDesignGlyphs(0xF0B24)

    /**
     * 󰬥 (0xF0B25)
     */
    public data object CALENDAR_IMPORT : MaterialDesignGlyphs(0xF0B25)

    /**
     * 󰬦 (0xF0B26)
     */
    public data object CHEVRON_DOWN_CIRCLE : MaterialDesignGlyphs(0xF0B26)

    /**
     * 󰬧 (0xF0B27)
     */
    public data object CHEVRON_DOWN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B27)

    /**
     * 󰬨 (0xF0B28)
     */
    public data object CHEVRON_LEFT_CIRCLE : MaterialDesignGlyphs(0xF0B28)

    /**
     * 󰬩 (0xF0B29)
     */
    public data object CHEVRON_LEFT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B29)

    /**
     * 󰬪 (0xF0B2A)
     */
    public data object CHEVRON_RIGHT_CIRCLE : MaterialDesignGlyphs(0xF0B2A)

    /**
     * 󰬫 (0xF0B2B)
     */
    public data object CHEVRON_RIGHT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B2B)

    /**
     * 󰬬 (0xF0B2C)
     */
    public data object CHEVRON_UP_CIRCLE : MaterialDesignGlyphs(0xF0B2C)

    /**
     * 󰬭 (0xF0B2D)
     */
    public data object CHEVRON_UP_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B2D)

    /**
     * 󰬮 (0xF0B2E)
     */
    public data object CONTENT_SAVE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF0B2E)

    /**
     * 󰬯 (0xF0B2F)
     */
    public data object CRYSTAL_BALL : MaterialDesignGlyphs(0xF0B2F)

    /**
     * 󰬰 (0xF0B30)
     */
    public data object EMBER : MaterialDesignGlyphs(0xF0B30)

    /**
     * 󰬱 (0xF0B31)
     */
    public data object FACEBOOK_WORKPLACE : MaterialDesignGlyphs(0xF0B31)

    /**
     * 󰬲 (0xF0B32)
     */
    public data object FILE_REPLACE : MaterialDesignGlyphs(0xF0B32)

    /**
     * 󰬳 (0xF0B33)
     */
    public data object FILE_REPLACE_OUTLINE : MaterialDesignGlyphs(0xF0B33)

    /**
     * 󰬴 (0xF0B34)
     */
    public data object FORMAT_LETTER_CASE : MaterialDesignGlyphs(0xF0B34)

    /**
     * 󰬵 (0xF0B35)
     */
    public data object FORMAT_LETTER_CASE_LOWER : MaterialDesignGlyphs(0xF0B35)

    /**
     * 󰬶 (0xF0B36)
     */
    public data object FORMAT_LETTER_CASE_UPPER : MaterialDesignGlyphs(0xF0B36)

    /**
     * 󰬷 (0xF0B37)
     */
    public data object LANGUAGE_JAVA : MaterialDesignGlyphs(0xF0B37)

    /**
     * 󰬸 (0xF0B38)
     */
    public data object CIRCLE_MULTIPLE : MaterialDesignGlyphs(0xF0B38)

    /**
     * 󰬺 (0xF0B3A)
     */
    public data object NUMERIC_1 : MaterialDesignGlyphs(0xF0B3A)

    /**
     * 󰬻 (0xF0B3B)
     */
    public data object NUMERIC_2 : MaterialDesignGlyphs(0xF0B3B)

    /**
     * 󰬼 (0xF0B3C)
     */
    public data object NUMERIC_3 : MaterialDesignGlyphs(0xF0B3C)

    /**
     * 󰬽 (0xF0B3D)
     */
    public data object NUMERIC_4 : MaterialDesignGlyphs(0xF0B3D)

    /**
     * 󰬾 (0xF0B3E)
     */
    public data object NUMERIC_5 : MaterialDesignGlyphs(0xF0B3E)

    /**
     * 󰬿 (0xF0B3F)
     */
    public data object NUMERIC_6 : MaterialDesignGlyphs(0xF0B3F)

    /**
     * 󰭀 (0xF0B40)
     */
    public data object NUMERIC_7 : MaterialDesignGlyphs(0xF0B40)

    /**
     * 󰭁 (0xF0B41)
     */
    public data object NUMERIC_8 : MaterialDesignGlyphs(0xF0B41)

    /**
     * 󰭂 (0xF0B42)
     */
    public data object NUMERIC_9 : MaterialDesignGlyphs(0xF0B42)

    /**
     * 󰭃 (0xF0B43)
     */
    public data object ORIGIN : MaterialDesignGlyphs(0xF0B43)

    /**
     * 󰭄 (0xF0B44)
     */
    public data object RESISTOR : MaterialDesignGlyphs(0xF0B44)

    /**
     * 󰭅 (0xF0B45)
     */
    public data object RESISTOR_NODES : MaterialDesignGlyphs(0xF0B45)

    /**
     * 󰭆 (0xF0B46)
     */
    public data object ROBOT_INDUSTRIAL : MaterialDesignGlyphs(0xF0B46)

    /**
     * 󰭇 (0xF0B47)
     */
    public data object SHOE_FORMAL : MaterialDesignGlyphs(0xF0B47)

    /**
     * 󰭈 (0xF0B48)
     */
    public data object SHOE_HEEL : MaterialDesignGlyphs(0xF0B48)

    /**
     * 󰭉 (0xF0B49)
     */
    public data object SILO : MaterialDesignGlyphs(0xF0B49)

    /**
     * 󰭊 (0xF0B4A)
     */
    public data object BOX_CUTTER_OFF : MaterialDesignGlyphs(0xF0B4A)

    /**
     * 󰭋 (0xF0B4B)
     */
    public data object TAB_MINUS : MaterialDesignGlyphs(0xF0B4B)

    /**
     * 󰭌 (0xF0B4C)
     */
    public data object TAB_REMOVE : MaterialDesignGlyphs(0xF0B4C)

    /**
     * 󰭍 (0xF0B4D)
     */
    public data object TAPE_MEASURE : MaterialDesignGlyphs(0xF0B4D)

    /**
     * 󰭎 (0xF0B4E)
     */
    public data object TELESCOPE : MaterialDesignGlyphs(0xF0B4E)

    /**
     * 󰭏 (0xF0B4F)
     */
    public data object YAHOO : MaterialDesignGlyphs(0xF0B4F)

    /**
     * 󰭐 (0xF0B50)
     */
    public data object ACCOUNT_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0B50)

    /**
     * 󰭑 (0xF0B51)
     */
    public data object ACCOUNT_ARROW_LEFT : MaterialDesignGlyphs(0xF0B51)

    /**
     * 󰭒 (0xF0B52)
     */
    public data object ACCOUNT_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF0B52)

    /**
     * 󰭓 (0xF0B53)
     */
    public data object ACCOUNT_ARROW_RIGHT : MaterialDesignGlyphs(0xF0B53)

    /**
     * 󰭔 (0xF0B54)
     */
    public data object ACCOUNT_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF0B54)

    /**
     * 󰭕 (0xF0B55)
     */
    public data object ACCOUNT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B55)

    /**
     * 󰭖 (0xF0B56)
     */
    public data object ACCOUNT_CLOCK : MaterialDesignGlyphs(0xF0B56)

    /**
     * 󰭗 (0xF0B57)
     */
    public data object ACCOUNT_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF0B57)

    /**
     * 󰭘 (0xF0B58)
     */
    public data object ACCOUNT_GROUP_OUTLINE : MaterialDesignGlyphs(0xF0B58)

    /**
     * 󰭙 (0xF0B59)
     */
    public data object ACCOUNT_QUESTION : MaterialDesignGlyphs(0xF0B59)

    /**
     * 󰭚 (0xF0B5A)
     */
    public data object ACCOUNT_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF0B5A)

    /**
     * 󰭛 (0xF0B5B)
     */
    public data object ARTSTATION : MaterialDesignGlyphs(0xF0B5B)

    /**
     * 󰭜 (0xF0B5C)
     */
    public data object BACKSPACE_OUTLINE : MaterialDesignGlyphs(0xF0B5C)

    /**
     * 󰭝 (0xF0B5D)
     */
    public data object BARLEY_OFF : MaterialDesignGlyphs(0xF0B5D)

    /**
     * 󰭞 (0xF0B5E)
     */
    public data object BARN : MaterialDesignGlyphs(0xF0B5E)

    /**
     * 󰭟 (0xF0B5F)
     */
    public data object BAT : MaterialDesignGlyphs(0xF0B5F)

    /**
     * 󰭠 (0xF0B60)
     */
    public data object APPLICATION_SETTINGS : MaterialDesignGlyphs(0xF0B60)

    /**
     * 󰭡 (0xF0B61)
     */
    public data object BILLIARDS : MaterialDesignGlyphs(0xF0B61)

    /**
     * 󰭢 (0xF0B62)
     */
    public data object BILLIARDS_RACK : MaterialDesignGlyphs(0xF0B62)

    /**
     * 󰭣 (0xF0B63)
     */
    public data object BOOK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF0B63)

    /**
     * 󰭤 (0xF0B64)
     */
    public data object BOOK_OUTLINE : MaterialDesignGlyphs(0xF0B64)

    /**
     * 󰭥 (0xF0B65)
     */
    public data object BOXING_GLOVE : MaterialDesignGlyphs(0xF0B65)

    /**
     * 󰭦 (0xF0B66)
     */
    public data object CALENDAR_BLANK_OUTLINE : MaterialDesignGlyphs(0xF0B66)

    /**
     * 󰭧 (0xF0B67)
     */
    public data object CALENDAR_OUTLINE : MaterialDesignGlyphs(0xF0B67)

    /**
     * 󰭨 (0xF0B68)
     */
    public data object CALENDAR_RANGE_OUTLINE : MaterialDesignGlyphs(0xF0B68)

    /**
     * 󰭩 (0xF0B69)
     */
    public data object CAMERA_CONTROL : MaterialDesignGlyphs(0xF0B69)

    /**
     * 󰭪 (0xF0B6A)
     */
    public data object CAMERA_ENHANCE_OUTLINE : MaterialDesignGlyphs(0xF0B6A)

    /**
     * 󰭫 (0xF0B6B)
     */
    public data object CAR_DOOR : MaterialDesignGlyphs(0xF0B6B)

    /**
     * 󰭬 (0xF0B6C)
     */
    public data object CAR_ELECTRIC : MaterialDesignGlyphs(0xF0B6C)

    /**
     * 󰭭 (0xF0B6D)
     */
    public data object CAR_KEY : MaterialDesignGlyphs(0xF0B6D)

    /**
     * 󰭮 (0xF0B6E)
     */
    public data object CAR_MULTIPLE : MaterialDesignGlyphs(0xF0B6E)

    /**
     * 󰭯 (0xF0B6F)
     */
    public data object CARD : MaterialDesignGlyphs(0xF0B6F)

    /**
     * 󰭰 (0xF0B70)
     */
    public data object CARD_BULLETED : MaterialDesignGlyphs(0xF0B70)

    /**
     * 󰭱 (0xF0B71)
     */
    public data object CARD_BULLETED_OFF : MaterialDesignGlyphs(0xF0B71)

    /**
     * 󰭲 (0xF0B72)
     */
    public data object CARD_BULLETED_OFF_OUTLINE : MaterialDesignGlyphs(0xF0B72)

    /**
     * 󰭳 (0xF0B73)
     */
    public data object CARD_BULLETED_OUTLINE : MaterialDesignGlyphs(0xF0B73)

    /**
     * 󰭴 (0xF0B74)
     */
    public data object CARD_BULLETED_SETTINGS : MaterialDesignGlyphs(0xF0B74)

    /**
     * 󰭵 (0xF0B75)
     */
    public data object CARD_BULLETED_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF0B75)

    /**
     * 󰭶 (0xF0B76)
     */
    public data object CARD_OUTLINE : MaterialDesignGlyphs(0xF0B76)

    /**
     * 󰭷 (0xF0B77)
     */
    public data object CARD_TEXT : MaterialDesignGlyphs(0xF0B77)

    /**
     * 󰭸 (0xF0B78)
     */
    public data object CARD_TEXT_OUTLINE : MaterialDesignGlyphs(0xF0B78)

    /**
     * 󰭹 (0xF0B79)
     */
    public data object CHAT : MaterialDesignGlyphs(0xF0B79)

    /**
     * 󰭺 (0xF0B7A)
     */
    public data object CHAT_ALERT : MaterialDesignGlyphs(0xF0B7A)

    /**
     * 󰭻 (0xF0B7B)
     */
    public data object CHAT_PROCESSING : MaterialDesignGlyphs(0xF0B7B)

    /**
     * 󰭼 (0xF0B7C)
     */
    public data object CHEF_HAT : MaterialDesignGlyphs(0xF0B7C)

    /**
     * 󰭽 (0xF0B7D)
     */
    public data object CLOUD_DOWNLOAD_OUTLINE : MaterialDesignGlyphs(0xF0B7D)

    /**
     * 󰭾 (0xF0B7E)
     */
    public data object CLOUD_UPLOAD_OUTLINE : MaterialDesignGlyphs(0xF0B7E)

    /**
     * 󰭿 (0xF0B7F)
     */
    public data object COFFIN : MaterialDesignGlyphs(0xF0B7F)

    /**
     * 󰮀 (0xF0B80)
     */
    public data object COMPASS_OFF : MaterialDesignGlyphs(0xF0B80)

    /**
     * 󰮁 (0xF0B81)
     */
    public data object COMPASS_OFF_OUTLINE : MaterialDesignGlyphs(0xF0B81)

    /**
     * 󰮂 (0xF0B82)
     */
    public data object CONTROLLER_CLASSIC : MaterialDesignGlyphs(0xF0B82)

    /**
     * 󰮃 (0xF0B83)
     */
    public data object CONTROLLER_CLASSIC_OUTLINE : MaterialDesignGlyphs(0xF0B83)

    /**
     * 󰮄 (0xF0B84)
     */
    public data object CUBE_SCAN : MaterialDesignGlyphs(0xF0B84)

    /**
     * 󰮅 (0xF0B85)
     */
    public data object CURRENCY_BRL : MaterialDesignGlyphs(0xF0B85)

    /**
     * 󰮆 (0xF0B86)
     */
    public data object DATABASE_EDIT : MaterialDesignGlyphs(0xF0B86)

    /**
     * 󰮇 (0xF0B87)
     */
    public data object DEATHLY_HALLOWS : MaterialDesignGlyphs(0xF0B87)

    /**
     * 󰮈 (0xF0B88)
     */
    public data object DELETE_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B88)

    /**
     * 󰮉 (0xF0B89)
     */
    public data object DELETE_FOREVER_OUTLINE : MaterialDesignGlyphs(0xF0B89)

    /**
     * 󰮊 (0xF0B8A)
     */
    public data object DIAMOND : MaterialDesignGlyphs(0xF0B8A)

    /**
     * 󰮋 (0xF0B8B)
     */
    public data object DIAMOND_OUTLINE : MaterialDesignGlyphs(0xF0B8B)

    /**
     * 󰮌 (0xF0B8C)
     */
    public data object DNS_OUTLINE : MaterialDesignGlyphs(0xF0B8C)

    /**
     * 󰮍 (0xF0B8D)
     */
    public data object DOTS_HORIZONTAL_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B8D)

    /**
     * 󰮎 (0xF0B8E)
     */
    public data object DOTS_VERTICAL_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B8E)

    /**
     * 󰮏 (0xF0B8F)
     */
    public data object DOWNLOAD_OUTLINE : MaterialDesignGlyphs(0xF0B8F)

    /**
     * 󰮐 (0xF0B90)
     */
    public data object DRAG_VARIANT : MaterialDesignGlyphs(0xF0B90)

    /**
     * 󰮑 (0xF0B91)
     */
    public data object EJECT_OUTLINE : MaterialDesignGlyphs(0xF0B91)

    /**
     * 󰮒 (0xF0B92)
     */
    public data object EMAIL_MARK_AS_UNREAD : MaterialDesignGlyphs(0xF0B92)

    /**
     * 󰮓 (0xF0B93)
     */
    public data object EXPORT_VARIANT : MaterialDesignGlyphs(0xF0B93)

    /**
     * 󰮔 (0xF0B94)
     */
    public data object EYE_CIRCLE : MaterialDesignGlyphs(0xF0B94)

    /**
     * 󰮕 (0xF0B95)
     */
    public data object EYE_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0B95)

    /**
     * 󰮖 (0xF0B96)
     */
    public data object FACE_MAN_OUTLINE : MaterialDesignGlyphs(0xF0B96)

    /**
     * 󰮗 (0xF0B97)
     */
    public data object FILE_FIND_OUTLINE : MaterialDesignGlyphs(0xF0B97)

    /**
     * 󰮘 (0xF0B98)
     */
    public data object FILE_REMOVE : MaterialDesignGlyphs(0xF0B98)

    /**
     * 󰮙 (0xF0B99)
     */
    public data object FLAG_MINUS : MaterialDesignGlyphs(0xF0B99)

    /**
     * 󰮚 (0xF0B9A)
     */
    public data object FLAG_PLUS : MaterialDesignGlyphs(0xF0B9A)

    /**
     * 󰮛 (0xF0B9B)
     */
    public data object FLAG_REMOVE : MaterialDesignGlyphs(0xF0B9B)

    /**
     * 󰮜 (0xF0B9C)
     */
    public data object FOLDER_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF0B9C)

    /**
     * 󰮝 (0xF0B9D)
     */
    public data object FOLDER_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0B9D)

    /**
     * 󰮞 (0xF0B9E)
     */
    public data object FOLDER_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0B9E)

    /**
     * 󰮟 (0xF0B9F)
     */
    public data object FOLDER_STAR_OUTLINE : MaterialDesignGlyphs(0xF0B9F)

    /**
     * 󰮠 (0xF0BA0)
     */
    public data object GITLAB : MaterialDesignGlyphs(0xF0BA0)

    /**
     * 󰮡 (0xF0BA1)
     */
    public data object GOG : MaterialDesignGlyphs(0xF0BA1)

    /**
     * 󰮢 (0xF0BA2)
     */
    public data object GRAVE_STONE : MaterialDesignGlyphs(0xF0BA2)

    /**
     * 󰮣 (0xF0BA3)
     */
    public data object HALLOWEEN : MaterialDesignGlyphs(0xF0BA3)

    /**
     * 󰮤 (0xF0BA4)
     */
    public data object HAT_FEDORA : MaterialDesignGlyphs(0xF0BA4)

    /**
     * 󰮥 (0xF0BA5)
     */
    public data object HELP_RHOMBUS : MaterialDesignGlyphs(0xF0BA5)

    /**
     * 󰮦 (0xF0BA6)
     */
    public data object HELP_RHOMBUS_OUTLINE : MaterialDesignGlyphs(0xF0BA6)

    /**
     * 󰮧 (0xF0BA7)
     */
    public data object HOME_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF0BA7)

    /**
     * 󰮨 (0xF0BA8)
     */
    public data object INBOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0BA8)

    /**
     * 󰮩 (0xF0BA9)
     */
    public data object LIBRARY_SHELVES : MaterialDesignGlyphs(0xF0BA9)

    /**
     * 󰮪 (0xF0BAA)
     */
    public data object MAPBOX : MaterialDesignGlyphs(0xF0BAA)

    /**
     * 󰮫 (0xF0BAB)
     */
    public data object MENU_OPEN : MaterialDesignGlyphs(0xF0BAB)

    /**
     * 󰮬 (0xF0BAC)
     */
    public data object MOLECULE : MaterialDesignGlyphs(0xF0BAC)

    /**
     * 󰮭 (0xF0BAD)
     */
    public data object ONE_UP : MaterialDesignGlyphs(0xF0BAD)

    /**
     * 󰮮 (0xF0BAE)
     */
    public data object OPEN_SOURCE_INITIATIVE : MaterialDesignGlyphs(0xF0BAE)

    /**
     * 󰮯 (0xF0BAF)
     */
    public data object PAC_MAN : MaterialDesignGlyphs(0xF0BAF)

    /**
     * 󰮰 (0xF0BB0)
     */
    public data object PAGE_NEXT : MaterialDesignGlyphs(0xF0BB0)

    /**
     * 󰮱 (0xF0BB1)
     */
    public data object PAGE_NEXT_OUTLINE : MaterialDesignGlyphs(0xF0BB1)

    /**
     * 󰮲 (0xF0BB2)
     */
    public data object PAGE_PREVIOUS : MaterialDesignGlyphs(0xF0BB2)

    /**
     * 󰮳 (0xF0BB3)
     */
    public data object PAGE_PREVIOUS_OUTLINE : MaterialDesignGlyphs(0xF0BB3)

    /**
     * 󰮴 (0xF0BB4)
     */
    public data object PAN : MaterialDesignGlyphs(0xF0BB4)

    /**
     * 󰮵 (0xF0BB5)
     */
    public data object PAN_BOTTOM_LEFT : MaterialDesignGlyphs(0xF0BB5)

    /**
     * 󰮶 (0xF0BB6)
     */
    public data object PAN_BOTTOM_RIGHT : MaterialDesignGlyphs(0xF0BB6)

    /**
     * 󰮷 (0xF0BB7)
     */
    public data object PAN_DOWN : MaterialDesignGlyphs(0xF0BB7)

    /**
     * 󰮸 (0xF0BB8)
     */
    public data object PAN_HORIZONTAL : MaterialDesignGlyphs(0xF0BB8)

    /**
     * 󰮹 (0xF0BB9)
     */
    public data object PAN_LEFT : MaterialDesignGlyphs(0xF0BB9)

    /**
     * 󰮺 (0xF0BBA)
     */
    public data object PAN_RIGHT : MaterialDesignGlyphs(0xF0BBA)

    /**
     * 󰮻 (0xF0BBB)
     */
    public data object PAN_TOP_LEFT : MaterialDesignGlyphs(0xF0BBB)

    /**
     * 󰮼 (0xF0BBC)
     */
    public data object PAN_TOP_RIGHT : MaterialDesignGlyphs(0xF0BBC)

    /**
     * 󰮽 (0xF0BBD)
     */
    public data object PAN_UP : MaterialDesignGlyphs(0xF0BBD)

    /**
     * 󰮾 (0xF0BBE)
     */
    public data object PAN_VERTICAL : MaterialDesignGlyphs(0xF0BBE)

    /**
     * 󰮿 (0xF0BBF)
     */
    public data object PUMPKIN : MaterialDesignGlyphs(0xF0BBF)

    /**
     * 󰯀 (0xF0BC0)
     */
    public data object ROLLUPJS : MaterialDesignGlyphs(0xF0BC0)

    /**
     * 󰯁 (0xF0BC1)
     */
    public data object SCRIPT : MaterialDesignGlyphs(0xF0BC1)

    /**
     * 󰯂 (0xF0BC2)
     */
    public data object SCRIPT_TEXT : MaterialDesignGlyphs(0xF0BC2)

    /**
     * 󰯃 (0xF0BC3)
     */
    public data object SCRIPT_TEXT_OUTLINE : MaterialDesignGlyphs(0xF0BC3)

    /**
     * 󰯄 (0xF0BC4)
     */
    public data object SHIELD_KEY : MaterialDesignGlyphs(0xF0BC4)

    /**
     * 󰯅 (0xF0BC5)
     */
    public data object SHIELD_KEY_OUTLINE : MaterialDesignGlyphs(0xF0BC5)

    /**
     * 󰯆 (0xF0BC6)
     */
    public data object SKULL_CROSSBONES : MaterialDesignGlyphs(0xF0BC6)

    /**
     * 󰯇 (0xF0BC7)
     */
    public data object SKULL_CROSSBONES_OUTLINE : MaterialDesignGlyphs(0xF0BC7)

    /**
     * 󰯈 (0xF0BC8)
     */
    public data object SKULL_OUTLINE : MaterialDesignGlyphs(0xF0BC8)

    /**
     * 󰯉 (0xF0BC9)
     */
    public data object SPACE_INVADERS : MaterialDesignGlyphs(0xF0BC9)

    /**
     * 󰯊 (0xF0BCA)
     */
    public data object SPIDER_WEB : MaterialDesignGlyphs(0xF0BCA)

    /**
     * 󰯋 (0xF0BCB)
     */
    public data object VIEW_SPLIT_HORIZONTAL : MaterialDesignGlyphs(0xF0BCB)

    /**
     * 󰯌 (0xF0BCC)
     */
    public data object VIEW_SPLIT_VERTICAL : MaterialDesignGlyphs(0xF0BCC)

    /**
     * 󰯍 (0xF0BCD)
     */
    public data object SWAP_HORIZONTAL_BOLD : MaterialDesignGlyphs(0xF0BCD)

    /**
     * 󰯎 (0xF0BCE)
     */
    public data object SWAP_VERTICAL_BOLD : MaterialDesignGlyphs(0xF0BCE)

    /**
     * 󰯏 (0xF0BCF)
     */
    public data object TAG_HEART_OUTLINE : MaterialDesignGlyphs(0xF0BCF)

    /**
     * 󰯐 (0xF0BD0)
     */
    public data object TARGET_ACCOUNT : MaterialDesignGlyphs(0xF0BD0)

    /**
     * 󰯑 (0xF0BD1)
     */
    public data object TIMELINE : MaterialDesignGlyphs(0xF0BD1)

    /**
     * 󰯒 (0xF0BD2)
     */
    public data object TIMELINE_OUTLINE : MaterialDesignGlyphs(0xF0BD2)

    /**
     * 󰯓 (0xF0BD3)
     */
    public data object TIMELINE_TEXT : MaterialDesignGlyphs(0xF0BD3)

    /**
     * 󰯔 (0xF0BD4)
     */
    public data object TIMELINE_TEXT_OUTLINE : MaterialDesignGlyphs(0xF0BD4)

    /**
     * 󰯕 (0xF0BD5)
     */
    public data object TOOLTIP_IMAGE_OUTLINE : MaterialDesignGlyphs(0xF0BD5)

    /**
     * 󰯖 (0xF0BD6)
     */
    public data object TOOLTIP_PLUS : MaterialDesignGlyphs(0xF0BD6)

    /**
     * 󰯗 (0xF0BD7)
     */
    public data object TOOLTIP_TEXT_OUTLINE : MaterialDesignGlyphs(0xF0BD7)

    /**
     * 󰯘 (0xF0BD8)
     */
    public data object TRAIN_CAR : MaterialDesignGlyphs(0xF0BD8)

    /**
     * 󰯙 (0xF0BD9)
     */
    public data object TRIFORCE : MaterialDesignGlyphs(0xF0BD9)

    /**
     * 󰯚 (0xF0BDA)
     */
    public data object UBISOFT : MaterialDesignGlyphs(0xF0BDA)

    /**
     * 󰯛 (0xF0BDB)
     */
    public data object VIDEO_OFF_OUTLINE : MaterialDesignGlyphs(0xF0BDB)

    /**
     * 󰯜 (0xF0BDC)
     */
    public data object VIDEO_OUTLINE : MaterialDesignGlyphs(0xF0BDC)

    /**
     * 󰯝 (0xF0BDD)
     */
    public data object WALLET_OUTLINE : MaterialDesignGlyphs(0xF0BDD)

    /**
     * 󰯞 (0xF0BDE)
     */
    public data object WAZE : MaterialDesignGlyphs(0xF0BDE)

    /**
     * 󰯟 (0xF0BDF)
     */
    public data object WRAP_DISABLED : MaterialDesignGlyphs(0xF0BDF)

    /**
     * 󰯠 (0xF0BE0)
     */
    public data object WRENCH_OUTLINE : MaterialDesignGlyphs(0xF0BE0)

    /**
     * 󰯡 (0xF0BE1)
     */
    public data object ACCESS_POINT_NETWORK_OFF : MaterialDesignGlyphs(0xF0BE1)

    /**
     * 󰯢 (0xF0BE2)
     */
    public data object ACCOUNT_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0BE2)

    /**
     * 󰯣 (0xF0BE3)
     */
    public data object ACCOUNT_HEART_OUTLINE : MaterialDesignGlyphs(0xF0BE3)

    /**
     * 󰯤 (0xF0BE4)
     */
    public data object ACCOUNT_KEY_OUTLINE : MaterialDesignGlyphs(0xF0BE4)

    /**
     * 󰯥 (0xF0BE5)
     */
    public data object ACCOUNT_MULTIPLE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF0BE5)

    /**
     * 󰯦 (0xF0BE6)
     */
    public data object ACCOUNT_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0BE6)

    /**
     * 󰯧 (0xF0BE7)
     */
    public data object ACCOUNT_OFF_OUTLINE : MaterialDesignGlyphs(0xF0BE7)

    /**
     * 󰯨 (0xF0BE8)
     */
    public data object ACCOUNT_STAR_OUTLINE : MaterialDesignGlyphs(0xF0BE8)

    /**
     * 󰯩 (0xF0BE9)
     */
    public data object AIRBAG : MaterialDesignGlyphs(0xF0BE9)

    /**
     * 󰯪 (0xF0BEA)
     */
    public data object ALARM_LIGHT_OUTLINE : MaterialDesignGlyphs(0xF0BEA)

    /**
     * 󰯫 (0xF0BEB)
     */
    public data object ALPHA_A_BOX_OUTLINE : MaterialDesignGlyphs(0xF0BEB)

    /**
     * 󰯬 (0xF0BEC)
     */
    public data object ALPHA_A_CIRCLE : MaterialDesignGlyphs(0xF0BEC)

    /**
     * 󰯭 (0xF0BED)
     */
    public data object ALPHA_A_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0BED)

    /**
     * 󰯮 (0xF0BEE)
     */
    public data object ALPHA_B_BOX_OUTLINE : MaterialDesignGlyphs(0xF0BEE)

    /**
     * 󰯯 (0xF0BEF)
     */
    public data object ALPHA_B_CIRCLE : MaterialDesignGlyphs(0xF0BEF)

    /**
     * 󰯰 (0xF0BF0)
     */
    public data object ALPHA_B_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0BF0)

    /**
     * 󰯱 (0xF0BF1)
     */
    public data object ALPHA_C_BOX_OUTLINE : MaterialDesignGlyphs(0xF0BF1)

    /**
     * 󰯲 (0xF0BF2)
     */
    public data object ALPHA_C_CIRCLE : MaterialDesignGlyphs(0xF0BF2)

    /**
     * 󰯳 (0xF0BF3)
     */
    public data object ALPHA_C_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0BF3)

    /**
     * 󰯴 (0xF0BF4)
     */
    public data object ALPHA_D_BOX_OUTLINE : MaterialDesignGlyphs(0xF0BF4)

    /**
     * 󰯵 (0xF0BF5)
     */
    public data object ALPHA_D_CIRCLE : MaterialDesignGlyphs(0xF0BF5)

    /**
     * 󰯶 (0xF0BF6)
     */
    public data object ALPHA_D_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0BF6)

    /**
     * 󰯷 (0xF0BF7)
     */
    public data object ALPHA_E_BOX_OUTLINE : MaterialDesignGlyphs(0xF0BF7)

    /**
     * 󰯸 (0xF0BF8)
     */
    public data object ALPHA_E_CIRCLE : MaterialDesignGlyphs(0xF0BF8)

    /**
     * 󰯹 (0xF0BF9)
     */
    public data object ALPHA_E_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0BF9)

    /**
     * 󰯺 (0xF0BFA)
     */
    public data object ALPHA_F_BOX_OUTLINE : MaterialDesignGlyphs(0xF0BFA)

    /**
     * 󰯻 (0xF0BFB)
     */
    public data object ALPHA_F_CIRCLE : MaterialDesignGlyphs(0xF0BFB)

    /**
     * 󰯼 (0xF0BFC)
     */
    public data object ALPHA_F_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0BFC)

    /**
     * 󰯽 (0xF0BFD)
     */
    public data object ALPHA_G_BOX_OUTLINE : MaterialDesignGlyphs(0xF0BFD)

    /**
     * 󰯾 (0xF0BFE)
     */
    public data object ALPHA_G_CIRCLE : MaterialDesignGlyphs(0xF0BFE)

    /**
     * 󰯿 (0xF0BFF)
     */
    public data object ALPHA_G_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0BFF)

    /**
     * 󰰀 (0xF0C00)
     */
    public data object ALPHA_H_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C00)

    /**
     * 󰰁 (0xF0C01)
     */
    public data object ALPHA_H_CIRCLE : MaterialDesignGlyphs(0xF0C01)

    /**
     * 󰰂 (0xF0C02)
     */
    public data object ALPHA_H_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C02)

    /**
     * 󰰃 (0xF0C03)
     */
    public data object ALPHA_I_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C03)

    /**
     * 󰰄 (0xF0C04)
     */
    public data object ALPHA_I_CIRCLE : MaterialDesignGlyphs(0xF0C04)

    /**
     * 󰰅 (0xF0C05)
     */
    public data object ALPHA_I_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C05)

    /**
     * 󰰆 (0xF0C06)
     */
    public data object ALPHA_J_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C06)

    /**
     * 󰰇 (0xF0C07)
     */
    public data object ALPHA_J_CIRCLE : MaterialDesignGlyphs(0xF0C07)

    /**
     * 󰰈 (0xF0C08)
     */
    public data object ALPHA_J_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C08)

    /**
     * 󰰉 (0xF0C09)
     */
    public data object ALPHA_K_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C09)

    /**
     * 󰰊 (0xF0C0A)
     */
    public data object ALPHA_K_CIRCLE : MaterialDesignGlyphs(0xF0C0A)

    /**
     * 󰰋 (0xF0C0B)
     */
    public data object ALPHA_K_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C0B)

    /**
     * 󰰌 (0xF0C0C)
     */
    public data object ALPHA_L_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C0C)

    /**
     * 󰰍 (0xF0C0D)
     */
    public data object ALPHA_L_CIRCLE : MaterialDesignGlyphs(0xF0C0D)

    /**
     * 󰰎 (0xF0C0E)
     */
    public data object ALPHA_L_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C0E)

    /**
     * 󰰏 (0xF0C0F)
     */
    public data object ALPHA_M_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C0F)

    /**
     * 󰰐 (0xF0C10)
     */
    public data object ALPHA_M_CIRCLE : MaterialDesignGlyphs(0xF0C10)

    /**
     * 󰰑 (0xF0C11)
     */
    public data object ALPHA_M_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C11)

    /**
     * 󰰒 (0xF0C12)
     */
    public data object ALPHA_N_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C12)

    /**
     * 󰰓 (0xF0C13)
     */
    public data object ALPHA_N_CIRCLE : MaterialDesignGlyphs(0xF0C13)

    /**
     * 󰰔 (0xF0C14)
     */
    public data object ALPHA_N_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C14)

    /**
     * 󰰕 (0xF0C15)
     */
    public data object ALPHA_O_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C15)

    /**
     * 󰰖 (0xF0C16)
     */
    public data object ALPHA_O_CIRCLE : MaterialDesignGlyphs(0xF0C16)

    /**
     * 󰰗 (0xF0C17)
     */
    public data object ALPHA_O_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C17)

    /**
     * 󰰘 (0xF0C18)
     */
    public data object ALPHA_P_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C18)

    /**
     * 󰰙 (0xF0C19)
     */
    public data object ALPHA_P_CIRCLE : MaterialDesignGlyphs(0xF0C19)

    /**
     * 󰰚 (0xF0C1A)
     */
    public data object ALPHA_P_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C1A)

    /**
     * 󰰛 (0xF0C1B)
     */
    public data object ALPHA_Q_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C1B)

    /**
     * 󰰜 (0xF0C1C)
     */
    public data object ALPHA_Q_CIRCLE : MaterialDesignGlyphs(0xF0C1C)

    /**
     * 󰰝 (0xF0C1D)
     */
    public data object ALPHA_Q_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C1D)

    /**
     * 󰰞 (0xF0C1E)
     */
    public data object ALPHA_R_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C1E)

    /**
     * 󰰟 (0xF0C1F)
     */
    public data object ALPHA_R_CIRCLE : MaterialDesignGlyphs(0xF0C1F)

    /**
     * 󰰠 (0xF0C20)
     */
    public data object ALPHA_R_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C20)

    /**
     * 󰰡 (0xF0C21)
     */
    public data object ALPHA_S_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C21)

    /**
     * 󰰢 (0xF0C22)
     */
    public data object ALPHA_S_CIRCLE : MaterialDesignGlyphs(0xF0C22)

    /**
     * 󰰣 (0xF0C23)
     */
    public data object ALPHA_S_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C23)

    /**
     * 󰰤 (0xF0C24)
     */
    public data object ALPHA_T_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C24)

    /**
     * 󰰥 (0xF0C25)
     */
    public data object ALPHA_T_CIRCLE : MaterialDesignGlyphs(0xF0C25)

    /**
     * 󰰦 (0xF0C26)
     */
    public data object ALPHA_T_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C26)

    /**
     * 󰰧 (0xF0C27)
     */
    public data object ALPHA_U_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C27)

    /**
     * 󰰨 (0xF0C28)
     */
    public data object ALPHA_U_CIRCLE : MaterialDesignGlyphs(0xF0C28)

    /**
     * 󰰩 (0xF0C29)
     */
    public data object ALPHA_U_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C29)

    /**
     * 󰰪 (0xF0C2A)
     */
    public data object ALPHA_V_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C2A)

    /**
     * 󰰫 (0xF0C2B)
     */
    public data object ALPHA_V_CIRCLE : MaterialDesignGlyphs(0xF0C2B)

    /**
     * 󰰬 (0xF0C2C)
     */
    public data object ALPHA_V_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C2C)

    /**
     * 󰰭 (0xF0C2D)
     */
    public data object ALPHA_W_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C2D)

    /**
     * 󰰮 (0xF0C2E)
     */
    public data object ALPHA_W_CIRCLE : MaterialDesignGlyphs(0xF0C2E)

    /**
     * 󰰯 (0xF0C2F)
     */
    public data object ALPHA_W_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C2F)

    /**
     * 󰰰 (0xF0C30)
     */
    public data object ALPHA_X_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C30)

    /**
     * 󰰱 (0xF0C31)
     */
    public data object ALPHA_X_CIRCLE : MaterialDesignGlyphs(0xF0C31)

    /**
     * 󰰲 (0xF0C32)
     */
    public data object ALPHA_X_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C32)

    /**
     * 󰰳 (0xF0C33)
     */
    public data object ALPHA_Y_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C33)

    /**
     * 󰰴 (0xF0C34)
     */
    public data object ALPHA_Y_CIRCLE : MaterialDesignGlyphs(0xF0C34)

    /**
     * 󰰵 (0xF0C35)
     */
    public data object ALPHA_Y_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C35)

    /**
     * 󰰶 (0xF0C36)
     */
    public data object ALPHA_Z_BOX_OUTLINE : MaterialDesignGlyphs(0xF0C36)

    /**
     * 󰰷 (0xF0C37)
     */
    public data object ALPHA_Z_CIRCLE : MaterialDesignGlyphs(0xF0C37)

    /**
     * 󰰸 (0xF0C38)
     */
    public data object ALPHA_Z_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0C38)

    /**
     * 󰰹 (0xF0C39)
     */
    public data object BALLOT_RECOUNT : MaterialDesignGlyphs(0xF0C39)

    /**
     * 󰰺 (0xF0C3A)
     */
    public data object BALLOT_RECOUNT_OUTLINE : MaterialDesignGlyphs(0xF0C3A)

    /**
     * 󰰻 (0xF0C3B)
     */
    public data object BASKETBALL_HOOP : MaterialDesignGlyphs(0xF0C3B)

    /**
     * 󰰼 (0xF0C3C)
     */
    public data object BASKETBALL_HOOP_OUTLINE : MaterialDesignGlyphs(0xF0C3C)

    /**
     * 󰰽 (0xF0C3D)
     */
    public data object BRIEFCASE_DOWNLOAD_OUTLINE : MaterialDesignGlyphs(0xF0C3D)

    /**
     * 󰰾 (0xF0C3E)
     */
    public data object BRIEFCASE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF0C3E)

    /**
     * 󰰿 (0xF0C3F)
     */
    public data object BRIEFCASE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF0C3F)

    /**
     * 󰱀 (0xF0C40)
     */
    public data object BRIEFCASE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0C40)

    /**
     * 󰱁 (0xF0C41)
     */
    public data object BRIEFCASE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0C41)

    /**
     * 󰱂 (0xF0C42)
     */
    public data object BRIEFCASE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0C42)

    /**
     * 󰱃 (0xF0C43)
     */
    public data object BRIEFCASE_UPLOAD_OUTLINE : MaterialDesignGlyphs(0xF0C43)

    /**
     * 󰱄 (0xF0C44)
     */
    public data object CALENDAR_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0C44)

    /**
     * 󰱅 (0xF0C45)
     */
    public data object CALENDAR_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0C45)

    /**
     * 󰱆 (0xF0C46)
     */
    public data object CALENDAR_TEXT_OUTLINE : MaterialDesignGlyphs(0xF0C46)

    /**
     * 󰱇 (0xF0C47)
     */
    public data object CAR_BRAKE_ABS : MaterialDesignGlyphs(0xF0C47)

    /**
     * 󰱈 (0xF0C48)
     */
    public data object CAR_BRAKE_ALERT : MaterialDesignGlyphs(0xF0C48)

    /**
     * 󰱉 (0xF0C49)
     */
    public data object CAR_ESP : MaterialDesignGlyphs(0xF0C49)

    /**
     * 󰱊 (0xF0C4A)
     */
    public data object CAR_LIGHT_DIMMED : MaterialDesignGlyphs(0xF0C4A)

    /**
     * 󰱋 (0xF0C4B)
     */
    public data object CAR_LIGHT_FOG : MaterialDesignGlyphs(0xF0C4B)

    /**
     * 󰱌 (0xF0C4C)
     */
    public data object CAR_LIGHT_HIGH : MaterialDesignGlyphs(0xF0C4C)

    /**
     * 󰱍 (0xF0C4D)
     */
    public data object CAR_TIRE_ALERT : MaterialDesignGlyphs(0xF0C4D)

    /**
     * 󰱎 (0xF0C4E)
     */
    public data object CART_ARROW_RIGHT : MaterialDesignGlyphs(0xF0C4E)

    /**
     * 󰱏 (0xF0C4F)
     */
    public data object CHARITY : MaterialDesignGlyphs(0xF0C4F)

    /**
     * 󰱐 (0xF0C50)
     */
    public data object CHART_BELL_CURVE : MaterialDesignGlyphs(0xF0C50)

    /**
     * 󰱑 (0xF0C51)
     */
    public data object CHECKBOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0C51)

    /**
     * 󰱒 (0xF0C52)
     */
    public data object CHECKBOX_OUTLINE : MaterialDesignGlyphs(0xF0C52)

    /**
     * 󰱓 (0xF0C53)
     */
    public data object CHECK_NETWORK : MaterialDesignGlyphs(0xF0C53)

    /**
     * 󰱔 (0xF0C54)
     */
    public data object CHECK_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C54)

    /**
     * 󰱕 (0xF0C55)
     */
    public data object CLIPBOARD_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF0C55)

    /**
     * 󰱖 (0xF0C56)
     */
    public data object CLIPBOARD_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF0C56)

    /**
     * 󰱗 (0xF0C57)
     */
    public data object CLIPBOARD_ARROW_UP : MaterialDesignGlyphs(0xF0C57)

    /**
     * 󰱘 (0xF0C58)
     */
    public data object CLIPBOARD_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF0C58)

    /**
     * 󰱙 (0xF0C59)
     */
    public data object CLIPBOARD_PLAY : MaterialDesignGlyphs(0xF0C59)

    /**
     * 󰱚 (0xF0C5A)
     */
    public data object CLIPBOARD_PLAY_OUTLINE : MaterialDesignGlyphs(0xF0C5A)

    /**
     * 󰱛 (0xF0C5B)
     */
    public data object CLIPBOARD_TEXT_PLAY : MaterialDesignGlyphs(0xF0C5B)

    /**
     * 󰱜 (0xF0C5C)
     */
    public data object CLIPBOARD_TEXT_PLAY_OUTLINE : MaterialDesignGlyphs(0xF0C5C)

    /**
     * 󰱝 (0xF0C5D)
     */
    public data object CLOSE_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0C5D)

    /**
     * 󰱞 (0xF0C5E)
     */
    public data object CLOSE_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0C5E)

    /**
     * 󰱟 (0xF0C5F)
     */
    public data object CLOSE_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C5F)

    /**
     * 󰱠 (0xF0C60)
     */
    public data object CONSOLE_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C60)

    /**
     * 󰱡 (0xF0C61)
     */
    public data object CURRENCY_ILS : MaterialDesignGlyphs(0xF0C61)

    /**
     * 󰱢 (0xF0C62)
     */
    public data object DELETE_SWEEP_OUTLINE : MaterialDesignGlyphs(0xF0C62)

    /**
     * 󰱣 (0xF0C63)
     */
    public data object DIAMETER : MaterialDesignGlyphs(0xF0C63)

    /**
     * 󰱤 (0xF0C64)
     */
    public data object DIAMETER_OUTLINE : MaterialDesignGlyphs(0xF0C64)

    /**
     * 󰱥 (0xF0C65)
     */
    public data object DIAMETER_VARIANT : MaterialDesignGlyphs(0xF0C65)

    /**
     * 󰱦 (0xF0C66)
     */
    public data object DOWNLOAD_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C66)

    /**
     * 󰱧 (0xF0C67)
     */
    public data object DUMP_TRUCK : MaterialDesignGlyphs(0xF0C67)

    /**
     * 󰱨 (0xF0C68)
     */
    public data object EMOTICON : MaterialDesignGlyphs(0xF0C68)

    /**
     * 󰱩 (0xF0C69)
     */
    public data object EMOTICON_ANGRY : MaterialDesignGlyphs(0xF0C69)

    /**
     * 󰱪 (0xF0C6A)
     */
    public data object EMOTICON_ANGRY_OUTLINE : MaterialDesignGlyphs(0xF0C6A)

    /**
     * 󰱫 (0xF0C6B)
     */
    public data object EMOTICON_COOL : MaterialDesignGlyphs(0xF0C6B)

    /**
     * 󰱬 (0xF0C6C)
     */
    public data object EMOTICON_CRY : MaterialDesignGlyphs(0xF0C6C)

    /**
     * 󰱭 (0xF0C6D)
     */
    public data object EMOTICON_CRY_OUTLINE : MaterialDesignGlyphs(0xF0C6D)

    /**
     * 󰱮 (0xF0C6E)
     */
    public data object EMOTICON_DEAD : MaterialDesignGlyphs(0xF0C6E)

    /**
     * 󰱯 (0xF0C6F)
     */
    public data object EMOTICON_DEVIL : MaterialDesignGlyphs(0xF0C6F)

    /**
     * 󰱰 (0xF0C70)
     */
    public data object EMOTICON_EXCITED : MaterialDesignGlyphs(0xF0C70)

    /**
     * 󰱱 (0xF0C71)
     */
    public data object EMOTICON_HAPPY : MaterialDesignGlyphs(0xF0C71)

    /**
     * 󰱲 (0xF0C72)
     */
    public data object EMOTICON_KISS : MaterialDesignGlyphs(0xF0C72)

    /**
     * 󰱳 (0xF0C73)
     */
    public data object EMOTICON_KISS_OUTLINE : MaterialDesignGlyphs(0xF0C73)

    /**
     * 󰱴 (0xF0C74)
     */
    public data object EMOTICON_NEUTRAL : MaterialDesignGlyphs(0xF0C74)

    /**
     * 󰱵 (0xF0C75)
     */
    public data object EMOTICON_POOP_OUTLINE : MaterialDesignGlyphs(0xF0C75)

    /**
     * 󰱶 (0xF0C76)
     */
    public data object EMOTICON_SAD : MaterialDesignGlyphs(0xF0C76)

    /**
     * 󰱷 (0xF0C77)
     */
    public data object EMOTICON_TONGUE_OUTLINE : MaterialDesignGlyphs(0xF0C77)

    /**
     * 󰱸 (0xF0C78)
     */
    public data object EMOTICON_WINK : MaterialDesignGlyphs(0xF0C78)

    /**
     * 󰱹 (0xF0C79)
     */
    public data object EMOTICON_WINK_OUTLINE : MaterialDesignGlyphs(0xF0C79)

    /**
     * 󰱺 (0xF0C7A)
     */
    public data object ESLINT : MaterialDesignGlyphs(0xF0C7A)

    /**
     * 󰱻 (0xF0C7B)
     */
    public data object FACE_RECOGNITION : MaterialDesignGlyphs(0xF0C7B)

    /**
     * 󰱼 (0xF0C7C)
     */
    public data object FILE_SEARCH : MaterialDesignGlyphs(0xF0C7C)

    /**
     * 󰱽 (0xF0C7D)
     */
    public data object FILE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0C7D)

    /**
     * 󰱾 (0xF0C7E)
     */
    public data object FILE_TABLE : MaterialDesignGlyphs(0xF0C7E)

    /**
     * 󰱿 (0xF0C7F)
     */
    public data object FILE_TABLE_OUTLINE : MaterialDesignGlyphs(0xF0C7F)

    /**
     * 󰲀 (0xF0C80)
     */
    public data object FOLDER_KEY_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C80)

    /**
     * 󰲁 (0xF0C81)
     */
    public data object FOLDER_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C81)

    /**
     * 󰲂 (0xF0C82)
     */
    public data object FOLDER_TEXT : MaterialDesignGlyphs(0xF0C82)

    /**
     * 󰲃 (0xF0C83)
     */
    public data object FOLDER_TEXT_OUTLINE : MaterialDesignGlyphs(0xF0C83)

    /**
     * 󰲄 (0xF0C84)
     */
    public data object FOOD_APPLE_OUTLINE : MaterialDesignGlyphs(0xF0C84)

    /**
     * 󰲅 (0xF0C85)
     */
    public data object FUSE : MaterialDesignGlyphs(0xF0C85)

    /**
     * 󰲆 (0xF0C86)
     */
    public data object FUSE_BLADE : MaterialDesignGlyphs(0xF0C86)

    /**
     * 󰲇 (0xF0C87)
     */
    public data object GOOGLE_ADS : MaterialDesignGlyphs(0xF0C87)

    /**
     * 󰲈 (0xF0C88)
     */
    public data object GOOGLE_STREET_VIEW : MaterialDesignGlyphs(0xF0C88)

    /**
     * 󰲉 (0xF0C89)
     */
    public data object HAZARD_LIGHTS : MaterialDesignGlyphs(0xF0C89)

    /**
     * 󰲊 (0xF0C8A)
     */
    public data object HELP_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C8A)

    /**
     * 󰲋 (0xF0C8B)
     */
    public data object APPLICATION_BRACKETS : MaterialDesignGlyphs(0xF0C8B)

    /**
     * 󰲌 (0xF0C8C)
     */
    public data object APPLICATION_BRACKETS_OUTLINE : MaterialDesignGlyphs(0xF0C8C)

    /**
     * 󰲍 (0xF0C8D)
     */
    public data object IMAGE_SIZE_SELECT_ACTUAL : MaterialDesignGlyphs(0xF0C8D)

    /**
     * 󰲎 (0xF0C8E)
     */
    public data object IMAGE_SIZE_SELECT_LARGE : MaterialDesignGlyphs(0xF0C8E)

    /**
     * 󰲏 (0xF0C8F)
     */
    public data object IMAGE_SIZE_SELECT_SMALL : MaterialDesignGlyphs(0xF0C8F)

    /**
     * 󰲐 (0xF0C90)
     */
    public data object IP_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C90)

    /**
     * 󰲑 (0xF0C91)
     */
    public data object IPOD : MaterialDesignGlyphs(0xF0C91)

    /**
     * 󰲒 (0xF0C92)
     */
    public data object LANGUAGE_HASKELL : MaterialDesignGlyphs(0xF0C92)

    /**
     * 󰲓 (0xF0C93)
     */
    public data object LEAF_MAPLE : MaterialDesignGlyphs(0xF0C93)

    /**
     * 󰲔 (0xF0C94)
     */
    public data object LINK_PLUS : MaterialDesignGlyphs(0xF0C94)

    /**
     * 󰲕 (0xF0C95)
     */
    public data object MAP_MARKER_CHECK : MaterialDesignGlyphs(0xF0C95)

    /**
     * 󰲖 (0xF0C96)
     */
    public data object MATH_COS : MaterialDesignGlyphs(0xF0C96)

    /**
     * 󰲗 (0xF0C97)
     */
    public data object MATH_SIN : MaterialDesignGlyphs(0xF0C97)

    /**
     * 󰲘 (0xF0C98)
     */
    public data object MATH_TAN : MaterialDesignGlyphs(0xF0C98)

    /**
     * 󰲙 (0xF0C99)
     */
    public data object MICROWAVE : MaterialDesignGlyphs(0xF0C99)

    /**
     * 󰲚 (0xF0C9A)
     */
    public data object MINUS_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C9A)

    /**
     * 󰲛 (0xF0C9B)
     */
    public data object NETWORK_OFF : MaterialDesignGlyphs(0xF0C9B)

    /**
     * 󰲜 (0xF0C9C)
     */
    public data object NETWORK_OFF_OUTLINE : MaterialDesignGlyphs(0xF0C9C)

    /**
     * 󰲝 (0xF0C9D)
     */
    public data object NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0C9D)

    /**
     * 󰲠 (0xF0CA0)
     */
    public data object NUMERIC_1_CIRCLE : MaterialDesignGlyphs(0xF0CA0)

    /**
     * 󰲡 (0xF0CA1)
     */
    public data object NUMERIC_1_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CA1)

    /**
     * 󰲢 (0xF0CA2)
     */
    public data object NUMERIC_2_CIRCLE : MaterialDesignGlyphs(0xF0CA2)

    /**
     * 󰲣 (0xF0CA3)
     */
    public data object NUMERIC_2_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CA3)

    /**
     * 󰲤 (0xF0CA4)
     */
    public data object NUMERIC_3_CIRCLE : MaterialDesignGlyphs(0xF0CA4)

    /**
     * 󰲥 (0xF0CA5)
     */
    public data object NUMERIC_3_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CA5)

    /**
     * 󰲦 (0xF0CA6)
     */
    public data object NUMERIC_4_CIRCLE : MaterialDesignGlyphs(0xF0CA6)

    /**
     * 󰲧 (0xF0CA7)
     */
    public data object NUMERIC_4_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CA7)

    /**
     * 󰲨 (0xF0CA8)
     */
    public data object NUMERIC_5_CIRCLE : MaterialDesignGlyphs(0xF0CA8)

    /**
     * 󰲩 (0xF0CA9)
     */
    public data object NUMERIC_5_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CA9)

    /**
     * 󰲪 (0xF0CAA)
     */
    public data object NUMERIC_6_CIRCLE : MaterialDesignGlyphs(0xF0CAA)

    /**
     * 󰲫 (0xF0CAB)
     */
    public data object NUMERIC_6_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CAB)

    /**
     * 󰲬 (0xF0CAC)
     */
    public data object NUMERIC_7_CIRCLE : MaterialDesignGlyphs(0xF0CAC)

    /**
     * 󰲭 (0xF0CAD)
     */
    public data object NUMERIC_7_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CAD)

    /**
     * 󰲮 (0xF0CAE)
     */
    public data object NUMERIC_8_CIRCLE : MaterialDesignGlyphs(0xF0CAE)

    /**
     * 󰲯 (0xF0CAF)
     */
    public data object NUMERIC_8_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CAF)

    /**
     * 󰲰 (0xF0CB0)
     */
    public data object NUMERIC_9_CIRCLE : MaterialDesignGlyphs(0xF0CB0)

    /**
     * 󰲱 (0xF0CB1)
     */
    public data object NUMERIC_9_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CB1)

    /**
     * 󰲲 (0xF0CB2)
     */
    public data object NUMERIC_9_PLUS_CIRCLE : MaterialDesignGlyphs(0xF0CB2)

    /**
     * 󰲳 (0xF0CB3)
     */
    public data object NUMERIC_9_PLUS_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CB3)

    /**
     * 󰲴 (0xF0CB4)
     */
    public data object PARACHUTE : MaterialDesignGlyphs(0xF0CB4)

    /**
     * 󰲵 (0xF0CB5)
     */
    public data object PARACHUTE_OUTLINE : MaterialDesignGlyphs(0xF0CB5)

    /**
     * 󰲶 (0xF0CB6)
     */
    public data object PENCIL_OUTLINE : MaterialDesignGlyphs(0xF0CB6)

    /**
     * 󰲷 (0xF0CB7)
     */
    public data object PLAY_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0CB7)

    /**
     * 󰲸 (0xF0CB8)
     */
    public data object PLAYLIST_MUSIC : MaterialDesignGlyphs(0xF0CB8)

    /**
     * 󰲹 (0xF0CB9)
     */
    public data object PLAYLIST_MUSIC_OUTLINE : MaterialDesignGlyphs(0xF0CB9)

    /**
     * 󰲺 (0xF0CBA)
     */
    public data object PLUS_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0CBA)

    /**
     * 󰲻 (0xF0CBB)
     */
    public data object POSTAGE_STAMP : MaterialDesignGlyphs(0xF0CBB)

    /**
     * 󰲼 (0xF0CBC)
     */
    public data object PROGRESS_ALERT : MaterialDesignGlyphs(0xF0CBC)

    /**
     * 󰲽 (0xF0CBD)
     */
    public data object PROGRESS_WRENCH : MaterialDesignGlyphs(0xF0CBD)

    /**
     * 󰲾 (0xF0CBE)
     */
    public data object RADIO_AM : MaterialDesignGlyphs(0xF0CBE)

    /**
     * 󰲿 (0xF0CBF)
     */
    public data object RADIO_FM : MaterialDesignGlyphs(0xF0CBF)

    /**
     * 󰳀 (0xF0CC0)
     */
    public data object RADIUS : MaterialDesignGlyphs(0xF0CC0)

    /**
     * 󰳁 (0xF0CC1)
     */
    public data object RADIUS_OUTLINE : MaterialDesignGlyphs(0xF0CC1)

    /**
     * 󰳂 (0xF0CC2)
     */
    public data object RULER_SQUARE : MaterialDesignGlyphs(0xF0CC2)

    /**
     * 󰳃 (0xF0CC3)
     */
    public data object SEAT : MaterialDesignGlyphs(0xF0CC3)

    /**
     * 󰳄 (0xF0CC4)
     */
    public data object SEAT_OUTLINE : MaterialDesignGlyphs(0xF0CC4)

    /**
     * 󰳅 (0xF0CC5)
     */
    public data object SEATBELT : MaterialDesignGlyphs(0xF0CC5)

    /**
     * 󰳆 (0xF0CC6)
     */
    public data object SHEEP : MaterialDesignGlyphs(0xF0CC6)

    /**
     * 󰳇 (0xF0CC7)
     */
    public data object SHIELD_AIRPLANE_OUTLINE : MaterialDesignGlyphs(0xF0CC7)

    /**
     * 󰳈 (0xF0CC8)
     */
    public data object SHIELD_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0CC8)

    /**
     * 󰳉 (0xF0CC9)
     */
    public data object SHIELD_CROSS : MaterialDesignGlyphs(0xF0CC9)

    /**
     * 󰳊 (0xF0CCA)
     */
    public data object SHIELD_CROSS_OUTLINE : MaterialDesignGlyphs(0xF0CCA)

    /**
     * 󰳋 (0xF0CCB)
     */
    public data object SHIELD_HOME_OUTLINE : MaterialDesignGlyphs(0xF0CCB)

    /**
     * 󰳌 (0xF0CCC)
     */
    public data object SHIELD_LOCK_OUTLINE : MaterialDesignGlyphs(0xF0CCC)

    /**
     * 󰳍 (0xF0CCD)
     */
    public data object SORT_VARIANT_LOCK : MaterialDesignGlyphs(0xF0CCD)

    /**
     * 󰳎 (0xF0CCE)
     */
    public data object SORT_VARIANT_LOCK_OPEN : MaterialDesignGlyphs(0xF0CCE)

    /**
     * 󰳏 (0xF0CCF)
     */
    public data object SOURCE_REPOSITORY : MaterialDesignGlyphs(0xF0CCF)

    /**
     * 󰳐 (0xF0CD0)
     */
    public data object SOURCE_REPOSITORY_MULTIPLE : MaterialDesignGlyphs(0xF0CD0)

    /**
     * 󰳑 (0xF0CD1)
     */
    public data object SPA : MaterialDesignGlyphs(0xF0CD1)

    /**
     * 󰳒 (0xF0CD2)
     */
    public data object SPA_OUTLINE : MaterialDesignGlyphs(0xF0CD2)

    /**
     * 󰳓 (0xF0CD3)
     */
    public data object TOASTER_OVEN : MaterialDesignGlyphs(0xF0CD3)

    /**
     * 󰳔 (0xF0CD4)
     */
    public data object TRUCK_CHECK : MaterialDesignGlyphs(0xF0CD4)

    /**
     * 󰳕 (0xF0CD5)
     */
    public data object TURNSTILE : MaterialDesignGlyphs(0xF0CD5)

    /**
     * 󰳖 (0xF0CD6)
     */
    public data object TURNSTILE_OUTLINE : MaterialDesignGlyphs(0xF0CD6)

    /**
     * 󰳗 (0xF0CD7)
     */
    public data object TURTLE : MaterialDesignGlyphs(0xF0CD7)

    /**
     * 󰳘 (0xF0CD8)
     */
    public data object UPLOAD_NETWORK_OUTLINE : MaterialDesignGlyphs(0xF0CD8)

    /**
     * 󰳙 (0xF0CD9)
     */
    public data object VIBRATE_OFF : MaterialDesignGlyphs(0xF0CD9)

    /**
     * 󰳚 (0xF0CDA)
     */
    public data object WATCH_VIBRATE_OFF : MaterialDesignGlyphs(0xF0CDA)

    /**
     * 󰳛 (0xF0CDB)
     */
    public data object ARROW_DOWN_CIRCLE : MaterialDesignGlyphs(0xF0CDB)

    /**
     * 󰳜 (0xF0CDC)
     */
    public data object ARROW_DOWN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CDC)

    /**
     * 󰳝 (0xF0CDD)
     */
    public data object ARROW_LEFT_CIRCLE : MaterialDesignGlyphs(0xF0CDD)

    /**
     * 󰳞 (0xF0CDE)
     */
    public data object ARROW_LEFT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CDE)

    /**
     * 󰳟 (0xF0CDF)
     */
    public data object ARROW_RIGHT_CIRCLE : MaterialDesignGlyphs(0xF0CDF)

    /**
     * 󰳠 (0xF0CE0)
     */
    public data object ARROW_RIGHT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CE0)

    /**
     * 󰳡 (0xF0CE1)
     */
    public data object ARROW_UP_CIRCLE : MaterialDesignGlyphs(0xF0CE1)

    /**
     * 󰳢 (0xF0CE2)
     */
    public data object ARROW_UP_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0CE2)

    /**
     * 󰳣 (0xF0CE3)
     */
    public data object ACCOUNT_TIE : MaterialDesignGlyphs(0xF0CE3)

    /**
     * 󰳤 (0xF0CE4)
     */
    public data object ALERT_BOX_OUTLINE : MaterialDesignGlyphs(0xF0CE4)

    /**
     * 󰳥 (0xF0CE5)
     */
    public data object ALERT_DECAGRAM_OUTLINE : MaterialDesignGlyphs(0xF0CE5)

    /**
     * 󰳦 (0xF0CE6)
     */
    public data object ALERT_OCTAGON_OUTLINE : MaterialDesignGlyphs(0xF0CE6)

    /**
     * 󰳧 (0xF0CE7)
     */
    public data object ALERT_OCTAGRAM_OUTLINE : MaterialDesignGlyphs(0xF0CE7)

    /**
     * 󰳨 (0xF0CE8)
     */
    public data object AMMUNITION : MaterialDesignGlyphs(0xF0CE8)

    /**
     * 󰳩 (0xF0CE9)
     */
    public data object ACCOUNT_MUSIC_OUTLINE : MaterialDesignGlyphs(0xF0CE9)

    /**
     * 󰳪 (0xF0CEA)
     */
    public data object BEAKER : MaterialDesignGlyphs(0xF0CEA)

    /**
     * 󰳫 (0xF0CEB)
     */
    public data object BLENDER : MaterialDesignGlyphs(0xF0CEB)

    /**
     * 󰳬 (0xF0CEC)
     */
    public data object BLOOD_BAG : MaterialDesignGlyphs(0xF0CEC)

    /**
     * 󰳭 (0xF0CED)
     */
    public data object CROSS_BOLNISI : MaterialDesignGlyphs(0xF0CED)

    /**
     * 󰳮 (0xF0CEE)
     */
    public data object BREAD_SLICE : MaterialDesignGlyphs(0xF0CEE)

    /**
     * 󰳯 (0xF0CEF)
     */
    public data object BREAD_SLICE_OUTLINE : MaterialDesignGlyphs(0xF0CEF)

    /**
     * 󰳰 (0xF0CF0)
     */
    public data object BRIEFCASE_ACCOUNT : MaterialDesignGlyphs(0xF0CF0)

    /**
     * 󰳱 (0xF0CF1)
     */
    public data object BRIEFCASE_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF0CF1)

    /**
     * 󰳲 (0xF0CF2)
     */
    public data object BRIGHTNESS_PERCENT : MaterialDesignGlyphs(0xF0CF2)

    /**
     * 󰳳 (0xF0CF3)
     */
    public data object BULLET : MaterialDesignGlyphs(0xF0CF3)

    /**
     * 󰳴 (0xF0CF4)
     */
    public data object CASH_REGISTER : MaterialDesignGlyphs(0xF0CF4)

    /**
     * 󰳵 (0xF0CF5)
     */
    public data object CROSS_CELTIC : MaterialDesignGlyphs(0xF0CF5)

    /**
     * 󰳶 (0xF0CF6)
     */
    public data object CROSS_OUTLINE : MaterialDesignGlyphs(0xF0CF6)

    /**
     * 󰳷 (0xF0CF7)
     */
    public data object CLIPBOARD_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0CF7)

    /**
     * 󰳸 (0xF0CF8)
     */
    public data object CLIPBOARD_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF0CF8)

    /**
     * 󰳹 (0xF0CF9)
     */
    public data object CLIPBOARD_ARROW_RIGHT : MaterialDesignGlyphs(0xF0CF9)

    /**
     * 󰳺 (0xF0CFA)
     */
    public data object CLIPBOARD_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF0CFA)

    /**
     * 󰳻 (0xF0CFB)
     */
    public data object CONTENT_SAVE_EDIT : MaterialDesignGlyphs(0xF0CFB)

    /**
     * 󰳼 (0xF0CFC)
     */
    public data object CONTENT_SAVE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF0CFC)

    /**
     * 󰳽 (0xF0CFD)
     */
    public data object CURSOR_DEFAULT_CLICK : MaterialDesignGlyphs(0xF0CFD)

    /**
     * 󰳾 (0xF0CFE)
     */
    public data object CURSOR_DEFAULT_CLICK_OUTLINE : MaterialDesignGlyphs(0xF0CFE)

    /**
     * 󰳿 (0xF0CFF)
     */
    public data object DATABASE_SYNC : MaterialDesignGlyphs(0xF0CFF)

    /**
     * 󰴀 (0xF0D00)
     */
    public data object DATABASE_REMOVE : MaterialDesignGlyphs(0xF0D00)

    /**
     * 󰴁 (0xF0D01)
     */
    public data object DATABASE_SETTINGS : MaterialDesignGlyphs(0xF0D01)

    /**
     * 󰴂 (0xF0D02)
     */
    public data object DRAMA_MASKS : MaterialDesignGlyphs(0xF0D02)

    /**
     * 󰴃 (0xF0D03)
     */
    public data object EMAIL_BOX : MaterialDesignGlyphs(0xF0D03)

    /**
     * 󰴄 (0xF0D04)
     */
    public data object EYE_CHECK : MaterialDesignGlyphs(0xF0D04)

    /**
     * 󰴅 (0xF0D05)
     */
    public data object EYE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0D05)

    /**
     * 󰴆 (0xF0D06)
     */
    public data object FAST_FORWARD_30 : MaterialDesignGlyphs(0xF0D06)

    /**
     * 󰴇 (0xF0D07)
     */
    public data object ORDER_ALPHABETICAL_DESCENDING : MaterialDesignGlyphs(0xF0D07)

    /**
     * 󰴈 (0xF0D08)
     */
    public data object FLOWER_POPPY : MaterialDesignGlyphs(0xF0D08)

    /**
     * 󰴉 (0xF0D09)
     */
    public data object FOLDER_POUND : MaterialDesignGlyphs(0xF0D09)

    /**
     * 󰴊 (0xF0D0A)
     */
    public data object FOLDER_POUND_OUTLINE : MaterialDesignGlyphs(0xF0D0A)

    /**
     * 󰴋 (0xF0D0B)
     */
    public data object FOLDER_SYNC : MaterialDesignGlyphs(0xF0D0B)

    /**
     * 󰴌 (0xF0D0C)
     */
    public data object FOLDER_SYNC_OUTLINE : MaterialDesignGlyphs(0xF0D0C)

    /**
     * 󰴍 (0xF0D0D)
     */
    public data object FORMAT_LIST_NUMBERED_RTL : MaterialDesignGlyphs(0xF0D0D)

    /**
     * 󰴎 (0xF0D0E)
     */
    public data object FORMAT_TEXT_WRAPPING_CLIP : MaterialDesignGlyphs(0xF0D0E)

    /**
     * 󰴏 (0xF0D0F)
     */
    public data object FORMAT_TEXT_WRAPPING_OVERFLOW : MaterialDesignGlyphs(0xF0D0F)

    /**
     * 󰴐 (0xF0D10)
     */
    public data object FORMAT_TEXT_WRAPPING_WRAP : MaterialDesignGlyphs(0xF0D10)

    /**
     * 󰴑 (0xF0D11)
     */
    public data object FORMAT_TEXTBOX : MaterialDesignGlyphs(0xF0D11)

    /**
     * 󰴒 (0xF0D12)
     */
    public data object FOUNTAIN_PEN : MaterialDesignGlyphs(0xF0D12)

    /**
     * 󰴓 (0xF0D13)
     */
    public data object FOUNTAIN_PEN_TIP : MaterialDesignGlyphs(0xF0D13)

    /**
     * 󰴔 (0xF0D14)
     */
    public data object HEART_BROKEN_OUTLINE : MaterialDesignGlyphs(0xF0D14)

    /**
     * 󰴕 (0xF0D15)
     */
    public data object HOME_CITY : MaterialDesignGlyphs(0xF0D15)

    /**
     * 󰴖 (0xF0D16)
     */
    public data object HOME_CITY_OUTLINE : MaterialDesignGlyphs(0xF0D16)

    /**
     * 󰴗 (0xF0D17)
     */
    public data object HUBSPOT : MaterialDesignGlyphs(0xF0D17)

    /**
     * 󰴘 (0xF0D18)
     */
    public data object FILMSTRIP_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0D18)

    /**
     * 󰴙 (0xF0D19)
     */
    public data object PLAY_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0D19)

    /**
     * 󰴚 (0xF0D1A)
     */
    public data object LINK_BOX : MaterialDesignGlyphs(0xF0D1A)

    /**
     * 󰴛 (0xF0D1B)
     */
    public data object LINK_BOX_OUTLINE : MaterialDesignGlyphs(0xF0D1B)

    /**
     * 󰴜 (0xF0D1C)
     */
    public data object LINK_BOX_VARIANT : MaterialDesignGlyphs(0xF0D1C)

    /**
     * 󰴝 (0xF0D1D)
     */
    public data object LINK_BOX_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF0D1D)

    /**
     * 󰴞 (0xF0D1E)
     */
    public data object MAP_CLOCK : MaterialDesignGlyphs(0xF0D1E)

    /**
     * 󰴟 (0xF0D1F)
     */
    public data object MAP_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF0D1F)

    /**
     * 󰴠 (0xF0D20)
     */
    public data object MAP_MARKER_PATH : MaterialDesignGlyphs(0xF0D20)

    /**
     * 󰴡 (0xF0D21)
     */
    public data object MOTHER_NURSE : MaterialDesignGlyphs(0xF0D21)

    /**
     * 󰴢 (0xF0D22)
     */
    public data object MICROSOFT_OUTLOOK : MaterialDesignGlyphs(0xF0D22)

    /**
     * 󰴣 (0xF0D23)
     */
    public data object PERSPECTIVE_LESS : MaterialDesignGlyphs(0xF0D23)

    /**
     * 󰴤 (0xF0D24)
     */
    public data object PERSPECTIVE_MORE : MaterialDesignGlyphs(0xF0D24)

    /**
     * 󰴥 (0xF0D25)
     */
    public data object PODIUM : MaterialDesignGlyphs(0xF0D25)

    /**
     * 󰴦 (0xF0D26)
     */
    public data object PODIUM_BRONZE : MaterialDesignGlyphs(0xF0D26)

    /**
     * 󰴧 (0xF0D27)
     */
    public data object PODIUM_GOLD : MaterialDesignGlyphs(0xF0D27)

    /**
     * 󰴨 (0xF0D28)
     */
    public data object PODIUM_SILVER : MaterialDesignGlyphs(0xF0D28)

    /**
     * 󰴩 (0xF0D29)
     */
    public data object QUORA : MaterialDesignGlyphs(0xF0D29)

    /**
     * 󰴪 (0xF0D2A)
     */
    public data object REWIND_10 : MaterialDesignGlyphs(0xF0D2A)

    /**
     * 󰴫 (0xF0D2B)
     */
    public data object ROLLER_SKATE : MaterialDesignGlyphs(0xF0D2B)

    /**
     * 󰴬 (0xF0D2C)
     */
    public data object ROLLERBLADE : MaterialDesignGlyphs(0xF0D2C)

    /**
     * 󰴭 (0xF0D2D)
     */
    public data object LANGUAGE_RUBY : MaterialDesignGlyphs(0xF0D2D)

    /**
     * 󰴮 (0xF0D2E)
     */
    public data object SACK : MaterialDesignGlyphs(0xF0D2E)

    /**
     * 󰴯 (0xF0D2F)
     */
    public data object SACK_PERCENT : MaterialDesignGlyphs(0xF0D2F)

    /**
     * 󰴰 (0xF0D30)
     */
    public data object SAFETY_GOGGLES : MaterialDesignGlyphs(0xF0D30)

    /**
     * 󰴱 (0xF0D31)
     */
    public data object SELECT_COLOR : MaterialDesignGlyphs(0xF0D31)

    /**
     * 󰴲 (0xF0D32)
     */
    public data object SELECTION_ELLIPSE : MaterialDesignGlyphs(0xF0D32)

    /**
     * 󰴳 (0xF0D33)
     */
    public data object SHIELD_LINK_VARIANT : MaterialDesignGlyphs(0xF0D33)

    /**
     * 󰴴 (0xF0D34)
     */
    public data object SHIELD_LINK_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF0D34)

    /**
     * 󰴵 (0xF0D35)
     */
    public data object SKATE : MaterialDesignGlyphs(0xF0D35)

    /**
     * 󰴶 (0xF0D36)
     */
    public data object SKEW_LESS : MaterialDesignGlyphs(0xF0D36)

    /**
     * 󰴷 (0xF0D37)
     */
    public data object SKEW_MORE : MaterialDesignGlyphs(0xF0D37)

    /**
     * 󰴸 (0xF0D38)
     */
    public data object SPEAKER_MULTIPLE : MaterialDesignGlyphs(0xF0D38)

    /**
     * 󰴹 (0xF0D39)
     */
    public data object STAMPER : MaterialDesignGlyphs(0xF0D39)

    /**
     * 󰴺 (0xF0D3A)
     */
    public data object TANK : MaterialDesignGlyphs(0xF0D3A)

    /**
     * 󰴻 (0xF0D3B)
     */
    public data object TORTOISE : MaterialDesignGlyphs(0xF0D3B)

    /**
     * 󰴼 (0xF0D3C)
     */
    public data object TRANSIT_CONNECTION : MaterialDesignGlyphs(0xF0D3C)

    /**
     * 󰴽 (0xF0D3D)
     */
    public data object TRANSIT_CONNECTION_VARIANT : MaterialDesignGlyphs(0xF0D3D)

    /**
     * 󰴾 (0xF0D3E)
     */
    public data object TRANSMISSION_TOWER : MaterialDesignGlyphs(0xF0D3E)

    /**
     * 󰴿 (0xF0D3F)
     */
    public data object WEIGHT_GRAM : MaterialDesignGlyphs(0xF0D3F)

    /**
     * 󰵀 (0xF0D40)
     */
    public data object YOUTUBE_SUBSCRIPTION : MaterialDesignGlyphs(0xF0D40)

    /**
     * 󰵁 (0xF0D41)
     */
    public data object ZIGBEE : MaterialDesignGlyphs(0xF0D41)

    /**
     * 󰵂 (0xF0D42)
     */
    public data object EMAIL_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0D42)

    /**
     * 󰵃 (0xF0D43)
     */
    public data object AIR_FILTER : MaterialDesignGlyphs(0xF0D43)

    /**
     * 󰵄 (0xF0D44)
     */
    public data object AIR_PURIFIER : MaterialDesignGlyphs(0xF0D44)

    /**
     * 󰵅 (0xF0D45)
     */
    public data object ANDROID_MESSAGES : MaterialDesignGlyphs(0xF0D45)

    /**
     * 󰵆 (0xF0D46)
     */
    public data object APPS_BOX : MaterialDesignGlyphs(0xF0D46)

    /**
     * 󰵇 (0xF0D47)
     */
    public data object ATM : MaterialDesignGlyphs(0xF0D47)

    /**
     * 󰵈 (0xF0D48)
     */
    public data object AXIS : MaterialDesignGlyphs(0xF0D48)

    /**
     * 󰵉 (0xF0D49)
     */
    public data object AXIS_ARROW : MaterialDesignGlyphs(0xF0D49)

    /**
     * 󰵊 (0xF0D4A)
     */
    public data object AXIS_ARROW_LOCK : MaterialDesignGlyphs(0xF0D4A)

    /**
     * 󰵋 (0xF0D4B)
     */
    public data object AXIS_LOCK : MaterialDesignGlyphs(0xF0D4B)

    /**
     * 󰵌 (0xF0D4C)
     */
    public data object AXIS_X_ARROW : MaterialDesignGlyphs(0xF0D4C)

    /**
     * 󰵍 (0xF0D4D)
     */
    public data object AXIS_X_ARROW_LOCK : MaterialDesignGlyphs(0xF0D4D)

    /**
     * 󰵎 (0xF0D4E)
     */
    public data object AXIS_X_ROTATE_CLOCKWISE : MaterialDesignGlyphs(0xF0D4E)

    /**
     * 󰵏 (0xF0D4F)
     */
    public data object AXIS_X_ROTATE_COUNTERCLOCKWISE : MaterialDesignGlyphs(0xF0D4F)

    /**
     * 󰵐 (0xF0D50)
     */
    public data object AXIS_X_Y_ARROW_LOCK : MaterialDesignGlyphs(0xF0D50)

    /**
     * 󰵑 (0xF0D51)
     */
    public data object AXIS_Y_ARROW : MaterialDesignGlyphs(0xF0D51)

    /**
     * 󰵒 (0xF0D52)
     */
    public data object AXIS_Y_ARROW_LOCK : MaterialDesignGlyphs(0xF0D52)

    /**
     * 󰵓 (0xF0D53)
     */
    public data object AXIS_Y_ROTATE_CLOCKWISE : MaterialDesignGlyphs(0xF0D53)

    /**
     * 󰵔 (0xF0D54)
     */
    public data object AXIS_Y_ROTATE_COUNTERCLOCKWISE : MaterialDesignGlyphs(0xF0D54)

    /**
     * 󰵕 (0xF0D55)
     */
    public data object AXIS_Z_ARROW : MaterialDesignGlyphs(0xF0D55)

    /**
     * 󰵖 (0xF0D56)
     */
    public data object AXIS_Z_ARROW_LOCK : MaterialDesignGlyphs(0xF0D56)

    /**
     * 󰵗 (0xF0D57)
     */
    public data object AXIS_Z_ROTATE_CLOCKWISE : MaterialDesignGlyphs(0xF0D57)

    /**
     * 󰵘 (0xF0D58)
     */
    public data object AXIS_Z_ROTATE_COUNTERCLOCKWISE : MaterialDesignGlyphs(0xF0D58)

    /**
     * 󰵙 (0xF0D59)
     */
    public data object BELL_ALERT : MaterialDesignGlyphs(0xF0D59)

    /**
     * 󰵚 (0xF0D5A)
     */
    public data object BELL_CIRCLE : MaterialDesignGlyphs(0xF0D5A)

    /**
     * 󰵛 (0xF0D5B)
     */
    public data object BELL_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0D5B)

    /**
     * 󰵜 (0xF0D5C)
     */
    public data object CALENDAR_MINUS : MaterialDesignGlyphs(0xF0D5C)

    /**
     * 󰵝 (0xF0D5D)
     */
    public data object CAMERA_OUTLINE : MaterialDesignGlyphs(0xF0D5D)

    /**
     * 󰵞 (0xF0D5E)
     */
    public data object CAR_BRAKE_HOLD : MaterialDesignGlyphs(0xF0D5E)

    /**
     * 󰵟 (0xF0D5F)
     */
    public data object CAR_BRAKE_PARKING : MaterialDesignGlyphs(0xF0D5F)

    /**
     * 󰵠 (0xF0D60)
     */
    public data object CAR_CRUISE_CONTROL : MaterialDesignGlyphs(0xF0D60)

    /**
     * 󰵡 (0xF0D61)
     */
    public data object CAR_DEFROST_FRONT : MaterialDesignGlyphs(0xF0D61)

    /**
     * 󰵢 (0xF0D62)
     */
    public data object CAR_DEFROST_REAR : MaterialDesignGlyphs(0xF0D62)

    /**
     * 󰵣 (0xF0D63)
     */
    public data object CAR_PARKING_LIGHTS : MaterialDesignGlyphs(0xF0D63)

    /**
     * 󰵤 (0xF0D64)
     */
    public data object CAR_TRACTION_CONTROL : MaterialDesignGlyphs(0xF0D64)

    /**
     * 󰵥 (0xF0D65)
     */
    public data object BAG_CARRY_ON_CHECK : MaterialDesignGlyphs(0xF0D65)

    /**
     * 󰵦 (0xF0D66)
     */
    public data object CART_ARROW_DOWN : MaterialDesignGlyphs(0xF0D66)

    /**
     * 󰵧 (0xF0D67)
     */
    public data object CART_ARROW_UP : MaterialDesignGlyphs(0xF0D67)

    /**
     * 󰵨 (0xF0D68)
     */
    public data object CART_MINUS : MaterialDesignGlyphs(0xF0D68)

    /**
     * 󰵩 (0xF0D69)
     */
    public data object CART_REMOVE : MaterialDesignGlyphs(0xF0D69)

    /**
     * 󰵪 (0xF0D6A)
     */
    public data object CONTACTLESS_PAYMENT : MaterialDesignGlyphs(0xF0D6A)

    /**
     * 󰵫 (0xF0D6B)
     */
    public data object CREATIVE_COMMONS : MaterialDesignGlyphs(0xF0D6B)

    /**
     * 󰵬 (0xF0D6C)
     */
    public data object CREDIT_CARD_WIRELESS_OUTLINE : MaterialDesignGlyphs(0xF0D6C)

    /**
     * 󰵭 (0xF0D6D)
     */
    public data object CRICKET : MaterialDesignGlyphs(0xF0D6D)

    /**
     * 󰵮 (0xF0D6E)
     */
    public data object DEV_TO : MaterialDesignGlyphs(0xF0D6E)

    /**
     * 󰵯 (0xF0D6F)
     */
    public data object DOMAIN_OFF : MaterialDesignGlyphs(0xF0D6F)

    /**
     * 󰵰 (0xF0D70)
     */
    public data object FACE_AGENT : MaterialDesignGlyphs(0xF0D70)

    /**
     * 󰵱 (0xF0D71)
     */
    public data object FAST_FORWARD_10 : MaterialDesignGlyphs(0xF0D71)

    /**
     * 󰵲 (0xF0D72)
     */
    public data object FLARE : MaterialDesignGlyphs(0xF0D72)

    /**
     * 󰵳 (0xF0D73)
     */
    public data object FORMAT_TEXT_ROTATION_DOWN : MaterialDesignGlyphs(0xF0D73)

    /**
     * 󰵴 (0xF0D74)
     */
    public data object FORMAT_TEXT_ROTATION_NONE : MaterialDesignGlyphs(0xF0D74)

    /**
     * 󰵵 (0xF0D75)
     */
    public data object FORWARDBURGER : MaterialDesignGlyphs(0xF0D75)

    /**
     * 󰵶 (0xF0D76)
     */
    public data object GESTURE_SWIPE : MaterialDesignGlyphs(0xF0D76)

    /**
     * 󰵷 (0xF0D77)
     */
    public data object GESTURE_TAP_HOLD : MaterialDesignGlyphs(0xF0D77)

    /**
     * 󰵸 (0xF0D78)
     */
    public data object FILE_GIF_BOX : MaterialDesignGlyphs(0xF0D78)

    /**
     * 󰵹 (0xF0D79)
     */
    public data object GO_KART : MaterialDesignGlyphs(0xF0D79)

    /**
     * 󰵺 (0xF0D7A)
     */
    public data object GO_KART_TRACK : MaterialDesignGlyphs(0xF0D7A)

    /**
     * 󰵻 (0xF0D7B)
     */
    public data object GOODREADS : MaterialDesignGlyphs(0xF0D7B)

    /**
     * 󰵼 (0xF0D7C)
     */
    public data object GRAIN : MaterialDesignGlyphs(0xF0D7C)

    /**
     * 󰵽 (0xF0D7D)
     */
    public data object HDR : MaterialDesignGlyphs(0xF0D7D)

    /**
     * 󰵾 (0xF0D7E)
     */
    public data object HDR_OFF : MaterialDesignGlyphs(0xF0D7E)

    /**
     * 󰵿 (0xF0D7F)
     */
    public data object HIKING : MaterialDesignGlyphs(0xF0D7F)

    /**
     * 󰶀 (0xF0D80)
     */
    public data object HOME_FLOOR_1 : MaterialDesignGlyphs(0xF0D80)

    /**
     * 󰶁 (0xF0D81)
     */
    public data object HOME_FLOOR_2 : MaterialDesignGlyphs(0xF0D81)

    /**
     * 󰶂 (0xF0D82)
     */
    public data object HOME_FLOOR_3 : MaterialDesignGlyphs(0xF0D82)

    /**
     * 󰶃 (0xF0D83)
     */
    public data object HOME_FLOOR_A : MaterialDesignGlyphs(0xF0D83)

    /**
     * 󰶄 (0xF0D84)
     */
    public data object HOME_FLOOR_B : MaterialDesignGlyphs(0xF0D84)

    /**
     * 󰶅 (0xF0D85)
     */
    public data object HOME_FLOOR_G : MaterialDesignGlyphs(0xF0D85)

    /**
     * 󰶆 (0xF0D86)
     */
    public data object HOME_FLOOR_L : MaterialDesignGlyphs(0xF0D86)

    /**
     * 󰶇 (0xF0D87)
     */
    public data object KABADDI : MaterialDesignGlyphs(0xF0D87)

    /**
     * 󰶈 (0xF0D88)
     */
    public data object MAILBOX_OPEN : MaterialDesignGlyphs(0xF0D88)

    /**
     * 󰶉 (0xF0D89)
     */
    public data object MAILBOX_OPEN_OUTLINE : MaterialDesignGlyphs(0xF0D89)

    /**
     * 󰶊 (0xF0D8A)
     */
    public data object MAILBOX_OPEN_UP : MaterialDesignGlyphs(0xF0D8A)

    /**
     * 󰶋 (0xF0D8B)
     */
    public data object MAILBOX_OPEN_UP_OUTLINE : MaterialDesignGlyphs(0xF0D8B)

    /**
     * 󰶌 (0xF0D8C)
     */
    public data object MAILBOX_OUTLINE : MaterialDesignGlyphs(0xF0D8C)

    /**
     * 󰶍 (0xF0D8D)
     */
    public data object MAILBOX_UP : MaterialDesignGlyphs(0xF0D8D)

    /**
     * 󰶎 (0xF0D8E)
     */
    public data object MAILBOX_UP_OUTLINE : MaterialDesignGlyphs(0xF0D8E)

    /**
     * 󰶏 (0xF0D8F)
     */
    public data object MIXED_MARTIAL_ARTS : MaterialDesignGlyphs(0xF0D8F)

    /**
     * 󰶐 (0xF0D90)
     */
    public data object MONITOR_OFF : MaterialDesignGlyphs(0xF0D90)

    /**
     * 󰶑 (0xF0D91)
     */
    public data object MOTION_SENSOR : MaterialDesignGlyphs(0xF0D91)

    /**
     * 󰶒 (0xF0D92)
     */
    public data object POINT_OF_SALE : MaterialDesignGlyphs(0xF0D92)

    /**
     * 󰶓 (0xF0D93)
     */
    public data object RACING_HELMET : MaterialDesignGlyphs(0xF0D93)

    /**
     * 󰶔 (0xF0D94)
     */
    public data object RACQUETBALL : MaterialDesignGlyphs(0xF0D94)

    /**
     * 󰶕 (0xF0D95)
     */
    public data object RESTART_OFF : MaterialDesignGlyphs(0xF0D95)

    /**
     * 󰶖 (0xF0D96)
     */
    public data object REWIND_30 : MaterialDesignGlyphs(0xF0D96)

    /**
     * 󰶗 (0xF0D97)
     */
    public data object ROOM_SERVICE_OUTLINE : MaterialDesignGlyphs(0xF0D97)

    /**
     * 󰶘 (0xF0D98)
     */
    public data object ROTATE_ORBIT : MaterialDesignGlyphs(0xF0D98)

    /**
     * 󰶙 (0xF0D99)
     */
    public data object RUGBY : MaterialDesignGlyphs(0xF0D99)

    /**
     * 󰶚 (0xF0D9A)
     */
    public data object SHIELD_SEARCH : MaterialDesignGlyphs(0xF0D9A)

    /**
     * 󰶛 (0xF0D9B)
     */
    public data object SOLAR_PANEL : MaterialDesignGlyphs(0xF0D9B)

    /**
     * 󰶜 (0xF0D9C)
     */
    public data object SOLAR_PANEL_LARGE : MaterialDesignGlyphs(0xF0D9C)

    /**
     * 󰶝 (0xF0D9D)
     */
    public data object SUBWAY_ALERT_VARIANT : MaterialDesignGlyphs(0xF0D9D)

    /**
     * 󰶞 (0xF0D9E)
     */
    public data object TEA : MaterialDesignGlyphs(0xF0D9E)

    /**
     * 󰶟 (0xF0D9F)
     */
    public data object TEA_OUTLINE : MaterialDesignGlyphs(0xF0D9F)

    /**
     * 󰶠 (0xF0DA0)
     */
    public data object TENNIS : MaterialDesignGlyphs(0xF0DA0)

    /**
     * 󰶡 (0xF0DA1)
     */
    public data object TRANSFER_DOWN : MaterialDesignGlyphs(0xF0DA1)

    /**
     * 󰶢 (0xF0DA2)
     */
    public data object TRANSFER_LEFT : MaterialDesignGlyphs(0xF0DA2)

    /**
     * 󰶣 (0xF0DA3)
     */
    public data object TRANSFER_UP : MaterialDesignGlyphs(0xF0DA3)

    /**
     * 󰶤 (0xF0DA4)
     */
    public data object TROPHY_BROKEN : MaterialDesignGlyphs(0xF0DA4)

    /**
     * 󰶥 (0xF0DA5)
     */
    public data object WIND_TURBINE : MaterialDesignGlyphs(0xF0DA5)

    /**
     * 󰶦 (0xF0DA6)
     */
    public data object WIPER_WASH : MaterialDesignGlyphs(0xF0DA6)

    /**
     * 󰶧 (0xF0DA7)
     */
    public data object BADGE_ACCOUNT : MaterialDesignGlyphs(0xF0DA7)

    /**
     * 󰶨 (0xF0DA8)
     */
    public data object BADGE_ACCOUNT_ALERT : MaterialDesignGlyphs(0xF0DA8)

    /**
     * 󰶩 (0xF0DA9)
     */
    public data object BADGE_ACCOUNT_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0DA9)

    /**
     * 󰶪 (0xF0DAA)
     */
    public data object BADGE_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF0DAA)

    /**
     * 󰶫 (0xF0DAB)
     */
    public data object CARD_ACCOUNT_DETAILS_OUTLINE : MaterialDesignGlyphs(0xF0DAB)

    /**
     * 󰶬 (0xF0DAC)
     */
    public data object AIR_HORN : MaterialDesignGlyphs(0xF0DAC)

    /**
     * 󰶭 (0xF0DAD)
     */
    public data object APPLICATION_EXPORT : MaterialDesignGlyphs(0xF0DAD)

    /**
     * 󰶮 (0xF0DAE)
     */
    public data object APPLICATION_IMPORT : MaterialDesignGlyphs(0xF0DAE)

    /**
     * 󰶯 (0xF0DAF)
     */
    public data object BANDAGE : MaterialDesignGlyphs(0xF0DAF)

    /**
     * 󰶰 (0xF0DB0)
     */
    public data object BANK_MINUS : MaterialDesignGlyphs(0xF0DB0)

    /**
     * 󰶱 (0xF0DB1)
     */
    public data object BANK_PLUS : MaterialDesignGlyphs(0xF0DB1)

    /**
     * 󰶲 (0xF0DB2)
     */
    public data object BANK_REMOVE : MaterialDesignGlyphs(0xF0DB2)

    /**
     * 󰶳 (0xF0DB3)
     */
    public data object BOLT : MaterialDesignGlyphs(0xF0DB3)

    /**
     * 󰶴 (0xF0DB4)
     */
    public data object BUGLE : MaterialDesignGlyphs(0xF0DB4)

    /**
     * 󰶵 (0xF0DB5)
     */
    public data object CACTUS : MaterialDesignGlyphs(0xF0DB5)

    /**
     * 󰶶 (0xF0DB6)
     */
    public data object CAMERA_WIRELESS : MaterialDesignGlyphs(0xF0DB6)

    /**
     * 󰶷 (0xF0DB7)
     */
    public data object CAMERA_WIRELESS_OUTLINE : MaterialDesignGlyphs(0xF0DB7)

    /**
     * 󰶸 (0xF0DB8)
     */
    public data object CASH_MARKER : MaterialDesignGlyphs(0xF0DB8)

    /**
     * 󰶹 (0xF0DB9)
     */
    public data object CHEVRON_TRIPLE_DOWN : MaterialDesignGlyphs(0xF0DB9)

    /**
     * 󰶺 (0xF0DBA)
     */
    public data object CHEVRON_TRIPLE_LEFT : MaterialDesignGlyphs(0xF0DBA)

    /**
     * 󰶻 (0xF0DBB)
     */
    public data object CHEVRON_TRIPLE_RIGHT : MaterialDesignGlyphs(0xF0DBB)

    /**
     * 󰶼 (0xF0DBC)
     */
    public data object CHEVRON_TRIPLE_UP : MaterialDesignGlyphs(0xF0DBC)

    /**
     * 󰶽 (0xF0DBD)
     */
    public data object CLOSED_CAPTION_OUTLINE : MaterialDesignGlyphs(0xF0DBD)

    /**
     * 󰶾 (0xF0DBE)
     */
    public data object CREDIT_CARD_MARKER_OUTLINE : MaterialDesignGlyphs(0xF0DBE)

    /**
     * 󰶿 (0xF0DBF)
     */
    public data object DIVING_FLIPPERS : MaterialDesignGlyphs(0xF0DBF)

    /**
     * 󰷀 (0xF0DC0)
     */
    public data object DIVING_HELMET : MaterialDesignGlyphs(0xF0DC0)

    /**
     * 󰷁 (0xF0DC1)
     */
    public data object DIVING_SCUBA : MaterialDesignGlyphs(0xF0DC1)

    /**
     * 󰷂 (0xF0DC2)
     */
    public data object DIVING_SCUBA_FLAG : MaterialDesignGlyphs(0xF0DC2)

    /**
     * 󰷃 (0xF0DC3)
     */
    public data object DIVING_SCUBA_TANK : MaterialDesignGlyphs(0xF0DC3)

    /**
     * 󰷄 (0xF0DC4)
     */
    public data object DIVING_SCUBA_TANK_MULTIPLE : MaterialDesignGlyphs(0xF0DC4)

    /**
     * 󰷅 (0xF0DC5)
     */
    public data object DIVING_SNORKEL : MaterialDesignGlyphs(0xF0DC5)

    /**
     * 󰷆 (0xF0DC6)
     */
    public data object FILE_CANCEL : MaterialDesignGlyphs(0xF0DC6)

    /**
     * 󰷇 (0xF0DC7)
     */
    public data object FILE_CANCEL_OUTLINE : MaterialDesignGlyphs(0xF0DC7)

    /**
     * 󰷈 (0xF0DC8)
     */
    public data object FILE_DOCUMENT_EDIT : MaterialDesignGlyphs(0xF0DC8)

    /**
     * 󰷉 (0xF0DC9)
     */
    public data object FILE_DOCUMENT_EDIT_OUTLINE : MaterialDesignGlyphs(0xF0DC9)

    /**
     * 󰷊 (0xF0DCA)
     */
    public data object FILE_EYE : MaterialDesignGlyphs(0xF0DCA)

    /**
     * 󰷋 (0xF0DCB)
     */
    public data object FILE_EYE_OUTLINE : MaterialDesignGlyphs(0xF0DCB)

    /**
     * 󰷌 (0xF0DCC)
     */
    public data object FOLDER_ALERT : MaterialDesignGlyphs(0xF0DCC)

    /**
     * 󰷍 (0xF0DCD)
     */
    public data object FOLDER_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0DCD)

    /**
     * 󰷎 (0xF0DCE)
     */
    public data object FOLDER_EDIT_OUTLINE : MaterialDesignGlyphs(0xF0DCE)

    /**
     * 󰷏 (0xF0DCF)
     */
    public data object FOLDER_OPEN_OUTLINE : MaterialDesignGlyphs(0xF0DCF)

    /**
     * 󰷐 (0xF0DD0)
     */
    public data object FORMAT_LIST_BULLETED_SQUARE : MaterialDesignGlyphs(0xF0DD0)

    /**
     * 󰷑 (0xF0DD1)
     */
    public data object GANTRY_CRANE : MaterialDesignGlyphs(0xF0DD1)

    /**
     * 󰷒 (0xF0DD2)
     */
    public data object HOME_FLOOR_0 : MaterialDesignGlyphs(0xF0DD2)

    /**
     * 󰷓 (0xF0DD3)
     */
    public data object HOME_FLOOR_NEGATIVE_1 : MaterialDesignGlyphs(0xF0DD3)

    /**
     * 󰷔 (0xF0DD4)
     */
    public data object HOME_GROUP : MaterialDesignGlyphs(0xF0DD4)

    /**
     * 󰷕 (0xF0DD5)
     */
    public data object JABBER : MaterialDesignGlyphs(0xF0DD5)

    /**
     * 󰷖 (0xF0DD6)
     */
    public data object KEY_OUTLINE : MaterialDesignGlyphs(0xF0DD6)

    /**
     * 󰷗 (0xF0DD7)
     */
    public data object LEAK : MaterialDesignGlyphs(0xF0DD7)

    /**
     * 󰷘 (0xF0DD8)
     */
    public data object LEAK_OFF : MaterialDesignGlyphs(0xF0DD8)

    /**
     * 󰷙 (0xF0DD9)
     */
    public data object MARKER_CANCEL : MaterialDesignGlyphs(0xF0DD9)

    /**
     * 󰷚 (0xF0DDA)
     */
    public data object MINE : MaterialDesignGlyphs(0xF0DDA)

    /**
     * 󰷛 (0xF0DDB)
     */
    public data object MONITOR_LOCK : MaterialDesignGlyphs(0xF0DDB)

    /**
     * 󰷜 (0xF0DDC)
     */
    public data object MONITOR_STAR : MaterialDesignGlyphs(0xF0DDC)

    /**
     * 󰷝 (0xF0DDD)
     */
    public data object MOVIE_OUTLINE : MaterialDesignGlyphs(0xF0DDD)

    /**
     * 󰷞 (0xF0DDE)
     */
    public data object MUSIC_NOTE_PLUS : MaterialDesignGlyphs(0xF0DDE)

    /**
     * 󰷟 (0xF0DDF)
     */
    public data object NAIL : MaterialDesignGlyphs(0xF0DDF)

    /**
     * 󰷠 (0xF0DE0)
     */
    public data object OCARINA : MaterialDesignGlyphs(0xF0DE0)

    /**
     * 󰷡 (0xF0DE1)
     */
    public data object PASSPORT_BIOMETRIC : MaterialDesignGlyphs(0xF0DE1)

    /**
     * 󰷢 (0xF0DE2)
     */
    public data object PEN_LOCK : MaterialDesignGlyphs(0xF0DE2)

    /**
     * 󰷣 (0xF0DE3)
     */
    public data object PEN_MINUS : MaterialDesignGlyphs(0xF0DE3)

    /**
     * 󰷤 (0xF0DE4)
     */
    public data object PEN_OFF : MaterialDesignGlyphs(0xF0DE4)

    /**
     * 󰷥 (0xF0DE5)
     */
    public data object PEN_PLUS : MaterialDesignGlyphs(0xF0DE5)

    /**
     * 󰷦 (0xF0DE6)
     */
    public data object PEN_REMOVE : MaterialDesignGlyphs(0xF0DE6)

    /**
     * 󰷧 (0xF0DE7)
     */
    public data object PENCIL_LOCK_OUTLINE : MaterialDesignGlyphs(0xF0DE7)

    /**
     * 󰷨 (0xF0DE8)
     */
    public data object PENCIL_MINUS : MaterialDesignGlyphs(0xF0DE8)

    /**
     * 󰷩 (0xF0DE9)
     */
    public data object PENCIL_MINUS_OUTLINE : MaterialDesignGlyphs(0xF0DE9)

    /**
     * 󰷪 (0xF0DEA)
     */
    public data object PENCIL_OFF_OUTLINE : MaterialDesignGlyphs(0xF0DEA)

    /**
     * 󰷫 (0xF0DEB)
     */
    public data object PENCIL_PLUS : MaterialDesignGlyphs(0xF0DEB)

    /**
     * 󰷬 (0xF0DEC)
     */
    public data object PENCIL_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0DEC)

    /**
     * 󰷭 (0xF0DED)
     */
    public data object PENCIL_REMOVE : MaterialDesignGlyphs(0xF0DED)

    /**
     * 󰷮 (0xF0DEE)
     */
    public data object PENCIL_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0DEE)

    /**
     * 󰷯 (0xF0DEF)
     */
    public data object PHONE_OFF : MaterialDesignGlyphs(0xF0DEF)

    /**
     * 󰷰 (0xF0DF0)
     */
    public data object PHONE_OUTLINE : MaterialDesignGlyphs(0xF0DF0)

    /**
     * 󰷱 (0xF0DF1)
     */
    public data object PI_HOLE : MaterialDesignGlyphs(0xF0DF1)

    /**
     * 󰷲 (0xF0DF2)
     */
    public data object PLAYLIST_STAR : MaterialDesignGlyphs(0xF0DF2)

    /**
     * 󰷳 (0xF0DF3)
     */
    public data object SCREW_FLAT_TOP : MaterialDesignGlyphs(0xF0DF3)

    /**
     * 󰷴 (0xF0DF4)
     */
    public data object SCREW_LAG : MaterialDesignGlyphs(0xF0DF4)

    /**
     * 󰷵 (0xF0DF5)
     */
    public data object SCREW_MACHINE_FLAT_TOP : MaterialDesignGlyphs(0xF0DF5)

    /**
     * 󰷶 (0xF0DF6)
     */
    public data object SCREW_MACHINE_ROUND_TOP : MaterialDesignGlyphs(0xF0DF6)

    /**
     * 󰷷 (0xF0DF7)
     */
    public data object SCREW_ROUND_TOP : MaterialDesignGlyphs(0xF0DF7)

    /**
     * 󰷸 (0xF0DF8)
     */
    public data object SEND_CIRCLE : MaterialDesignGlyphs(0xF0DF8)

    /**
     * 󰷹 (0xF0DF9)
     */
    public data object SEND_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0DF9)

    /**
     * 󰷺 (0xF0DFA)
     */
    public data object SHOE_PRINT : MaterialDesignGlyphs(0xF0DFA)

    /**
     * 󰷻 (0xF0DFB)
     */
    public data object SIGNATURE : MaterialDesignGlyphs(0xF0DFB)

    /**
     * 󰷼 (0xF0DFC)
     */
    public data object SIGNATURE_FREEHAND : MaterialDesignGlyphs(0xF0DFC)

    /**
     * 󰷽 (0xF0DFD)
     */
    public data object SIGNATURE_IMAGE : MaterialDesignGlyphs(0xF0DFD)

    /**
     * 󰷾 (0xF0DFE)
     */
    public data object SIGNATURE_TEXT : MaterialDesignGlyphs(0xF0DFE)

    /**
     * 󰷿 (0xF0DFF)
     */
    public data object SLOPE_DOWNHILL : MaterialDesignGlyphs(0xF0DFF)

    /**
     * 󰸀 (0xF0E00)
     */
    public data object SLOPE_UPHILL : MaterialDesignGlyphs(0xF0E00)

    /**
     * 󰸁 (0xF0E01)
     */
    public data object THERMOMETER_ALERT : MaterialDesignGlyphs(0xF0E01)

    /**
     * 󰸂 (0xF0E02)
     */
    public data object THERMOMETER_CHEVRON_DOWN : MaterialDesignGlyphs(0xF0E02)

    /**
     * 󰸃 (0xF0E03)
     */
    public data object THERMOMETER_CHEVRON_UP : MaterialDesignGlyphs(0xF0E03)

    /**
     * 󰸄 (0xF0E04)
     */
    public data object THERMOMETER_MINUS : MaterialDesignGlyphs(0xF0E04)

    /**
     * 󰸅 (0xF0E05)
     */
    public data object THERMOMETER_PLUS : MaterialDesignGlyphs(0xF0E05)

    /**
     * 󰸆 (0xF0E06)
     */
    public data object TRANSLATE_OFF : MaterialDesignGlyphs(0xF0E06)

    /**
     * 󰸇 (0xF0E07)
     */
    public data object UPLOAD_OUTLINE : MaterialDesignGlyphs(0xF0E07)

    /**
     * 󰸈 (0xF0E08)
     */
    public data object VOLUME_VARIANT_OFF : MaterialDesignGlyphs(0xF0E08)

    /**
     * 󰸉 (0xF0E09)
     */
    public data object WALLPAPER : MaterialDesignGlyphs(0xF0E09)

    /**
     * 󰸊 (0xF0E0A)
     */
    public data object WATER_OUTLINE : MaterialDesignGlyphs(0xF0E0A)

    /**
     * 󰸋 (0xF0E0B)
     */
    public data object WIFI_STAR : MaterialDesignGlyphs(0xF0E0B)

    /**
     * 󰸌 (0xF0E0C)
     */
    public data object PALETTE_OUTLINE : MaterialDesignGlyphs(0xF0E0C)

    /**
     * 󰸍 (0xF0E0D)
     */
    public data object BADGE_ACCOUNT_HORIZONTAL : MaterialDesignGlyphs(0xF0E0D)

    /**
     * 󰸎 (0xF0E0E)
     */
    public data object BADGE_ACCOUNT_HORIZONTAL_OUTLINE : MaterialDesignGlyphs(0xF0E0E)

    /**
     * 󰸏 (0xF0E0F)
     */
    public data object AWS : MaterialDesignGlyphs(0xF0E0F)

    /**
     * 󰸐 (0xF0E10)
     */
    public data object BAG_PERSONAL : MaterialDesignGlyphs(0xF0E10)

    /**
     * 󰸑 (0xF0E11)
     */
    public data object BAG_PERSONAL_OFF : MaterialDesignGlyphs(0xF0E11)

    /**
     * 󰸒 (0xF0E12)
     */
    public data object BAG_PERSONAL_OFF_OUTLINE : MaterialDesignGlyphs(0xF0E12)

    /**
     * 󰸓 (0xF0E13)
     */
    public data object BAG_PERSONAL_OUTLINE : MaterialDesignGlyphs(0xF0E13)

    /**
     * 󰸔 (0xF0E14)
     */
    public data object BIATHLON : MaterialDesignGlyphs(0xF0E14)

    /**
     * 󰸕 (0xF0E15)
     */
    public data object BOOKMARK_MULTIPLE : MaterialDesignGlyphs(0xF0E15)

    /**
     * 󰸖 (0xF0E16)
     */
    public data object BOOKMARK_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0E16)

    /**
     * 󰸗 (0xF0E17)
     */
    public data object CALENDAR_MONTH : MaterialDesignGlyphs(0xF0E17)

    /**
     * 󰸘 (0xF0E18)
     */
    public data object CALENDAR_MONTH_OUTLINE : MaterialDesignGlyphs(0xF0E18)

    /**
     * 󰸙 (0xF0E19)
     */
    public data object CAMERA_RETAKE : MaterialDesignGlyphs(0xF0E19)

    /**
     * 󰸚 (0xF0E1A)
     */
    public data object CAMERA_RETAKE_OUTLINE : MaterialDesignGlyphs(0xF0E1A)

    /**
     * 󰸛 (0xF0E1B)
     */
    public data object CAR_BACK : MaterialDesignGlyphs(0xF0E1B)

    /**
     * 󰸜 (0xF0E1C)
     */
    public data object CAR_OFF : MaterialDesignGlyphs(0xF0E1C)

    /**
     * 󰸝 (0xF0E1D)
     */
    public data object CAST_EDUCATION : MaterialDesignGlyphs(0xF0E1D)

    /**
     * 󰸞 (0xF0E1E)
     */
    public data object CHECK_BOLD : MaterialDesignGlyphs(0xF0E1E)

    /**
     * 󰸟 (0xF0E1F)
     */
    public data object CHECK_UNDERLINE : MaterialDesignGlyphs(0xF0E1F)

    /**
     * 󰸠 (0xF0E20)
     */
    public data object CHECK_UNDERLINE_CIRCLE : MaterialDesignGlyphs(0xF0E20)

    /**
     * 󰸡 (0xF0E21)
     */
    public data object CHECK_UNDERLINE_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0E21)

    /**
     * 󰸢 (0xF0E22)
     */
    public data object CIRCULAR_SAW : MaterialDesignGlyphs(0xF0E22)

    /**
     * 󰸣 (0xF0E23)
     */
    public data object COMMA : MaterialDesignGlyphs(0xF0E23)

    /**
     * 󰸤 (0xF0E24)
     */
    public data object COMMA_BOX_OUTLINE : MaterialDesignGlyphs(0xF0E24)

    /**
     * 󰸥 (0xF0E25)
     */
    public data object COMMA_CIRCLE : MaterialDesignGlyphs(0xF0E25)

    /**
     * 󰸦 (0xF0E26)
     */
    public data object COMMA_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0E26)

    /**
     * 󰸧 (0xF0E27)
     */
    public data object CONTENT_SAVE_MOVE : MaterialDesignGlyphs(0xF0E27)

    /**
     * 󰸨 (0xF0E28)
     */
    public data object CONTENT_SAVE_MOVE_OUTLINE : MaterialDesignGlyphs(0xF0E28)

    /**
     * 󰸩 (0xF0E29)
     */
    public data object FILE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0E29)

    /**
     * 󰸪 (0xF0E2A)
     */
    public data object FILE_MUSIC_OUTLINE : MaterialDesignGlyphs(0xF0E2A)

    /**
     * 󰸫 (0xF0E2B)
     */
    public data object COMMA_BOX : MaterialDesignGlyphs(0xF0E2B)

    /**
     * 󰸬 (0xF0E2C)
     */
    public data object FILE_VIDEO_OUTLINE : MaterialDesignGlyphs(0xF0E2C)

    /**
     * 󰸭 (0xF0E2D)
     */
    public data object FILE_PNG_BOX : MaterialDesignGlyphs(0xF0E2D)

    /**
     * 󰸮 (0xF0E2E)
     */
    public data object FIREPLACE : MaterialDesignGlyphs(0xF0E2E)

    /**
     * 󰸯 (0xF0E2F)
     */
    public data object FIREPLACE_OFF : MaterialDesignGlyphs(0xF0E2F)

    /**
     * 󰸰 (0xF0E30)
     */
    public data object FIREWORK : MaterialDesignGlyphs(0xF0E30)

    /**
     * 󰸱 (0xF0E31)
     */
    public data object FORMAT_COLOR_HIGHLIGHT : MaterialDesignGlyphs(0xF0E31)

    /**
     * 󰸲 (0xF0E32)
     */
    public data object FORMAT_TEXT_VARIANT : MaterialDesignGlyphs(0xF0E32)

    /**
     * 󰸳 (0xF0E33)
     */
    public data object GAMEPAD_CIRCLE : MaterialDesignGlyphs(0xF0E33)

    /**
     * 󰸴 (0xF0E34)
     */
    public data object GAMEPAD_CIRCLE_DOWN : MaterialDesignGlyphs(0xF0E34)

    /**
     * 󰸵 (0xF0E35)
     */
    public data object GAMEPAD_CIRCLE_LEFT : MaterialDesignGlyphs(0xF0E35)

    /**
     * 󰸶 (0xF0E36)
     */
    public data object GAMEPAD_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0E36)

    /**
     * 󰸷 (0xF0E37)
     */
    public data object GAMEPAD_CIRCLE_RIGHT : MaterialDesignGlyphs(0xF0E37)

    /**
     * 󰸸 (0xF0E38)
     */
    public data object GAMEPAD_CIRCLE_UP : MaterialDesignGlyphs(0xF0E38)

    /**
     * 󰸹 (0xF0E39)
     */
    public data object GAMEPAD_DOWN : MaterialDesignGlyphs(0xF0E39)

    /**
     * 󰸺 (0xF0E3A)
     */
    public data object GAMEPAD_LEFT : MaterialDesignGlyphs(0xF0E3A)

    /**
     * 󰸻 (0xF0E3B)
     */
    public data object GAMEPAD_RIGHT : MaterialDesignGlyphs(0xF0E3B)

    /**
     * 󰸼 (0xF0E3C)
     */
    public data object GAMEPAD_ROUND : MaterialDesignGlyphs(0xF0E3C)

    /**
     * 󰸽 (0xF0E3D)
     */
    public data object GAMEPAD_ROUND_DOWN : MaterialDesignGlyphs(0xF0E3D)

    /**
     * 󰸾 (0xF0E3E)
     */
    public data object GAMEPAD_ROUND_LEFT : MaterialDesignGlyphs(0xF0E3E)

    /**
     * 󰸿 (0xF0E3F)
     */
    public data object GAMEPAD_ROUND_OUTLINE : MaterialDesignGlyphs(0xF0E3F)

    /**
     * 󰹀 (0xF0E40)
     */
    public data object GAMEPAD_ROUND_RIGHT : MaterialDesignGlyphs(0xF0E40)

    /**
     * 󰹁 (0xF0E41)
     */
    public data object GAMEPAD_ROUND_UP : MaterialDesignGlyphs(0xF0E41)

    /**
     * 󰹂 (0xF0E42)
     */
    public data object GAMEPAD_UP : MaterialDesignGlyphs(0xF0E42)

    /**
     * 󰹃 (0xF0E43)
     */
    public data object GATSBY : MaterialDesignGlyphs(0xF0E43)

    /**
     * 󰹄 (0xF0E44)
     */
    public data object GIFT : MaterialDesignGlyphs(0xF0E44)

    /**
     * 󰹅 (0xF0E45)
     */
    public data object GRILL : MaterialDesignGlyphs(0xF0E45)

    /**
     * 󰹆 (0xF0E46)
     */
    public data object HAND_BACK_LEFT : MaterialDesignGlyphs(0xF0E46)

    /**
     * 󰹇 (0xF0E47)
     */
    public data object HAND_BACK_RIGHT : MaterialDesignGlyphs(0xF0E47)

    /**
     * 󰹈 (0xF0E48)
     */
    public data object HAND_SAW : MaterialDesignGlyphs(0xF0E48)

    /**
     * 󰹉 (0xF0E49)
     */
    public data object IMAGE_FRAME : MaterialDesignGlyphs(0xF0E49)

    /**
     * 󰹊 (0xF0E4A)
     */
    public data object INVERT_COLORS_OFF : MaterialDesignGlyphs(0xF0E4A)

    /**
     * 󰹋 (0xF0E4B)
     */
    public data object KEYBOARD_OFF_OUTLINE : MaterialDesignGlyphs(0xF0E4B)

    /**
     * 󰹌 (0xF0E4C)
     */
    public data object LAYERS_MINUS : MaterialDesignGlyphs(0xF0E4C)

    /**
     * 󰹍 (0xF0E4D)
     */
    public data object LAYERS_PLUS : MaterialDesignGlyphs(0xF0E4D)

    /**
     * 󰹎 (0xF0E4E)
     */
    public data object LAYERS_REMOVE : MaterialDesignGlyphs(0xF0E4E)

    /**
     * 󰹏 (0xF0E4F)
     */
    public data object LIGHTBULB_OFF : MaterialDesignGlyphs(0xF0E4F)

    /**
     * 󰹐 (0xF0E50)
     */
    public data object LIGHTBULB_OFF_OUTLINE : MaterialDesignGlyphs(0xF0E50)

    /**
     * 󰹑 (0xF0E51)
     */
    public data object MONITOR_SCREENSHOT : MaterialDesignGlyphs(0xF0E51)

    /**
     * 󰹒 (0xF0E52)
     */
    public data object ICE_CREAM_OFF : MaterialDesignGlyphs(0xF0E52)

    /**
     * 󰹓 (0xF0E53)
     */
    public data object NFC_SEARCH_VARIANT : MaterialDesignGlyphs(0xF0E53)

    /**
     * 󰹔 (0xF0E54)
     */
    public data object NFC_VARIANT_OFF : MaterialDesignGlyphs(0xF0E54)

    /**
     * 󰹕 (0xF0E55)
     */
    public data object NOTEBOOK_MULTIPLE : MaterialDesignGlyphs(0xF0E55)

    /**
     * 󰹖 (0xF0E56)
     */
    public data object HOOP_HOUSE : MaterialDesignGlyphs(0xF0E56)

    /**
     * 󰹗 (0xF0E57)
     */
    public data object PICTURE_IN_PICTURE_BOTTOM_RIGHT : MaterialDesignGlyphs(0xF0E57)

    /**
     * 󰹘 (0xF0E58)
     */
    public data object PICTURE_IN_PICTURE_BOTTOM_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF0E58)

    /**
     * 󰹙 (0xF0E59)
     */
    public data object PICTURE_IN_PICTURE_TOP_RIGHT : MaterialDesignGlyphs(0xF0E59)

    /**
     * 󰹚 (0xF0E5A)
     */
    public data object PICTURE_IN_PICTURE_TOP_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF0E5A)

    /**
     * 󰹛 (0xF0E5B)
     */
    public data object PRINTER_3D_NOZZLE : MaterialDesignGlyphs(0xF0E5B)

    /**
     * 󰹜 (0xF0E5C)
     */
    public data object PRINTER_3D_NOZZLE_OUTLINE : MaterialDesignGlyphs(0xF0E5C)

    /**
     * 󰹝 (0xF0E5D)
     */
    public data object PRINTER_OFF : MaterialDesignGlyphs(0xF0E5D)

    /**
     * 󰹞 (0xF0E5E)
     */
    public data object RECTANGLE : MaterialDesignGlyphs(0xF0E5E)

    /**
     * 󰹟 (0xF0E5F)
     */
    public data object RECTANGLE_OUTLINE : MaterialDesignGlyphs(0xF0E5F)

    /**
     * 󰹠 (0xF0E60)
     */
    public data object RIVET : MaterialDesignGlyphs(0xF0E60)

    /**
     * 󰹡 (0xF0E61)
     */
    public data object SAW_BLADE : MaterialDesignGlyphs(0xF0E61)

    /**
     * 󰹢 (0xF0E62)
     */
    public data object SEED : MaterialDesignGlyphs(0xF0E62)

    /**
     * 󰹣 (0xF0E63)
     */
    public data object SEED_OUTLINE : MaterialDesignGlyphs(0xF0E63)

    /**
     * 󰹤 (0xF0E64)
     */
    public data object SIGNAL_DISTANCE_VARIANT : MaterialDesignGlyphs(0xF0E64)

    /**
     * 󰹥 (0xF0E65)
     */
    public data object SPADE : MaterialDesignGlyphs(0xF0E65)

    /**
     * 󰹦 (0xF0E66)
     */
    public data object SPROUT : MaterialDesignGlyphs(0xF0E66)

    /**
     * 󰹧 (0xF0E67)
     */
    public data object SPROUT_OUTLINE : MaterialDesignGlyphs(0xF0E67)

    /**
     * 󰹨 (0xF0E68)
     */
    public data object TABLE_TENNIS : MaterialDesignGlyphs(0xF0E68)

    /**
     * 󰹩 (0xF0E69)
     */
    public data object TREE_OUTLINE : MaterialDesignGlyphs(0xF0E69)

    /**
     * 󰹪 (0xF0E6A)
     */
    public data object VIEW_COMFY : MaterialDesignGlyphs(0xF0E6A)

    /**
     * 󰹫 (0xF0E6B)
     */
    public data object VIEW_COMPACT : MaterialDesignGlyphs(0xF0E6B)

    /**
     * 󰹬 (0xF0E6C)
     */
    public data object VIEW_COMPACT_OUTLINE : MaterialDesignGlyphs(0xF0E6C)

    /**
     * 󰹭 (0xF0E6D)
     */
    public data object VUETIFY : MaterialDesignGlyphs(0xF0E6D)

    /**
     * 󰹮 (0xF0E6E)
     */
    public data object WEATHER_CLOUDY_ARROW_RIGHT : MaterialDesignGlyphs(0xF0E6E)

    /**
     * 󰹯 (0xF0E6F)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_MENU : MaterialDesignGlyphs(0xF0E6F)

    /**
     * 󰹰 (0xF0E70)
     */
    public data object MICROSOFT_XBOX_CONTROLLER_VIEW : MaterialDesignGlyphs(0xF0E70)

    /**
     * 󰹱 (0xF0E71)
     */
    public data object ALARM_NOTE : MaterialDesignGlyphs(0xF0E71)

    /**
     * 󰹲 (0xF0E72)
     */
    public data object ALARM_NOTE_OFF : MaterialDesignGlyphs(0xF0E72)

    /**
     * 󰹳 (0xF0E73)
     */
    public data object ARROW_LEFT_RIGHT : MaterialDesignGlyphs(0xF0E73)

    /**
     * 󰹴 (0xF0E74)
     */
    public data object ARROW_LEFT_RIGHT_BOLD : MaterialDesignGlyphs(0xF0E74)

    /**
     * 󰹵 (0xF0E75)
     */
    public data object ARROW_TOP_LEFT_BOTTOM_RIGHT : MaterialDesignGlyphs(0xF0E75)

    /**
     * 󰹶 (0xF0E76)
     */
    public data object ARROW_TOP_LEFT_BOTTOM_RIGHT_BOLD : MaterialDesignGlyphs(0xF0E76)

    /**
     * 󰹷 (0xF0E77)
     */
    public data object ARROW_TOP_RIGHT_BOTTOM_LEFT : MaterialDesignGlyphs(0xF0E77)

    /**
     * 󰹸 (0xF0E78)
     */
    public data object ARROW_TOP_RIGHT_BOTTOM_LEFT_BOLD : MaterialDesignGlyphs(0xF0E78)

    /**
     * 󰹹 (0xF0E79)
     */
    public data object ARROW_UP_DOWN : MaterialDesignGlyphs(0xF0E79)

    /**
     * 󰹺 (0xF0E7A)
     */
    public data object ARROW_UP_DOWN_BOLD : MaterialDesignGlyphs(0xF0E7A)

    /**
     * 󰹻 (0xF0E7B)
     */
    public data object ATOM_VARIANT : MaterialDesignGlyphs(0xF0E7B)

    /**
     * 󰹼 (0xF0E7C)
     */
    public data object BABY_FACE : MaterialDesignGlyphs(0xF0E7C)

    /**
     * 󰹽 (0xF0E7D)
     */
    public data object BABY_FACE_OUTLINE : MaterialDesignGlyphs(0xF0E7D)

    /**
     * 󰹾 (0xF0E7E)
     */
    public data object BACKSPACE_REVERSE : MaterialDesignGlyphs(0xF0E7E)

    /**
     * 󰹿 (0xF0E7F)
     */
    public data object BACKSPACE_REVERSE_OUTLINE : MaterialDesignGlyphs(0xF0E7F)

    /**
     * 󰺀 (0xF0E80)
     */
    public data object BANK_OUTLINE : MaterialDesignGlyphs(0xF0E80)

    /**
     * 󰺁 (0xF0E81)
     */
    public data object BELL_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0E81)

    /**
     * 󰺂 (0xF0E82)
     */
    public data object BOOK_PLAY : MaterialDesignGlyphs(0xF0E82)

    /**
     * 󰺃 (0xF0E83)
     */
    public data object BOOK_PLAY_OUTLINE : MaterialDesignGlyphs(0xF0E83)

    /**
     * 󰺄 (0xF0E84)
     */
    public data object BOOK_SEARCH : MaterialDesignGlyphs(0xF0E84)

    /**
     * 󰺅 (0xF0E85)
     */
    public data object BOOK_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0E85)

    /**
     * 󰺆 (0xF0E86)
     */
    public data object BOOM_GATE : MaterialDesignGlyphs(0xF0E86)

    /**
     * 󰺇 (0xF0E87)
     */
    public data object BOOM_GATE_ALERT : MaterialDesignGlyphs(0xF0E87)

    /**
     * 󰺈 (0xF0E88)
     */
    public data object BOOM_GATE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0E88)

    /**
     * 󰺉 (0xF0E89)
     */
    public data object BOOM_GATE_ARROW_DOWN : MaterialDesignGlyphs(0xF0E89)

    /**
     * 󰺊 (0xF0E8A)
     */
    public data object BOOM_GATE_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF0E8A)

    /**
     * 󰺋 (0xF0E8B)
     */
    public data object BOOM_GATE_OUTLINE : MaterialDesignGlyphs(0xF0E8B)

    /**
     * 󰺌 (0xF0E8C)
     */
    public data object BOOM_GATE_ARROW_UP : MaterialDesignGlyphs(0xF0E8C)

    /**
     * 󰺍 (0xF0E8D)
     */
    public data object BOOM_GATE_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF0E8D)

    /**
     * 󰺎 (0xF0E8E)
     */
    public data object CALENDAR_SYNC : MaterialDesignGlyphs(0xF0E8E)

    /**
     * 󰺏 (0xF0E8F)
     */
    public data object CALENDAR_SYNC_OUTLINE : MaterialDesignGlyphs(0xF0E8F)

    /**
     * 󰺐 (0xF0E90)
     */
    public data object CELLPHONE_NFC : MaterialDesignGlyphs(0xF0E90)

    /**
     * 󰺑 (0xF0E91)
     */
    public data object CHART_AREASPLINE_VARIANT : MaterialDesignGlyphs(0xF0E91)

    /**
     * 󰺒 (0xF0E92)
     */
    public data object CHART_SCATTER_PLOT : MaterialDesignGlyphs(0xF0E92)

    /**
     * 󰺓 (0xF0E93)
     */
    public data object CHART_TIMELINE_VARIANT : MaterialDesignGlyphs(0xF0E93)

    /**
     * 󰺔 (0xF0E94)
     */
    public data object CHART_TREE : MaterialDesignGlyphs(0xF0E94)

    /**
     * 󰺕 (0xF0E95)
     */
    public data object CIRCLE_DOUBLE : MaterialDesignGlyphs(0xF0E95)

    /**
     * 󰺖 (0xF0E96)
     */
    public data object CIRCLE_EXPAND : MaterialDesignGlyphs(0xF0E96)

    /**
     * 󰺗 (0xF0E97)
     */
    public data object CLOCK_DIGITAL : MaterialDesignGlyphs(0xF0E97)

    /**
     * 󰺘 (0xF0E98)
     */
    public data object CARD_ACCOUNT_MAIL_OUTLINE : MaterialDesignGlyphs(0xF0E98)

    /**
     * 󰺙 (0xF0E99)
     */
    public data object CARD_ACCOUNT_PHONE : MaterialDesignGlyphs(0xF0E99)

    /**
     * 󰺚 (0xF0E9A)
     */
    public data object CARD_ACCOUNT_PHONE_OUTLINE : MaterialDesignGlyphs(0xF0E9A)

    /**
     * 󰺛 (0xF0E9B)
     */
    public data object ACCOUNT_COWBOY_HAT : MaterialDesignGlyphs(0xF0E9B)

    /**
     * 󰺜 (0xF0E9C)
     */
    public data object CURRENCY_RIAL : MaterialDesignGlyphs(0xF0E9C)

    /**
     * 󰺝 (0xF0E9D)
     */
    public data object DELETE_EMPTY_OUTLINE : MaterialDesignGlyphs(0xF0E9D)

    /**
     * 󰺞 (0xF0E9E)
     */
    public data object DOLLY : MaterialDesignGlyphs(0xF0E9E)

    /**
     * 󰺟 (0xF0E9F)
     */
    public data object ELECTRIC_SWITCH : MaterialDesignGlyphs(0xF0E9F)

    /**
     * 󰺠 (0xF0EA0)
     */
    public data object ELLIPSE : MaterialDesignGlyphs(0xF0EA0)

    /**
     * 󰺡 (0xF0EA1)
     */
    public data object ELLIPSE_OUTLINE : MaterialDesignGlyphs(0xF0EA1)

    /**
     * 󰺢 (0xF0EA2)
     */
    public data object EQUALIZER : MaterialDesignGlyphs(0xF0EA2)

    /**
     * 󰺣 (0xF0EA3)
     */
    public data object EQUALIZER_OUTLINE : MaterialDesignGlyphs(0xF0EA3)

    /**
     * 󰺤 (0xF0EA4)
     */
    public data object FERRIS_WHEEL : MaterialDesignGlyphs(0xF0EA4)

    /**
     * 󰺥 (0xF0EA5)
     */
    public data object FILE_DELIMITED_OUTLINE : MaterialDesignGlyphs(0xF0EA5)

    /**
     * 󰺦 (0xF0EA6)
     */
    public data object TEXT_BOX_CHECK : MaterialDesignGlyphs(0xF0EA6)

    /**
     * 󰺧 (0xF0EA7)
     */
    public data object TEXT_BOX_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0EA7)

    /**
     * 󰺨 (0xF0EA8)
     */
    public data object TEXT_BOX_MINUS : MaterialDesignGlyphs(0xF0EA8)

    /**
     * 󰺩 (0xF0EA9)
     */
    public data object TEXT_BOX_MINUS_OUTLINE : MaterialDesignGlyphs(0xF0EA9)

    /**
     * 󰺪 (0xF0EAA)
     */
    public data object TEXT_BOX_PLUS : MaterialDesignGlyphs(0xF0EAA)

    /**
     * 󰺫 (0xF0EAB)
     */
    public data object TEXT_BOX_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0EAB)

    /**
     * 󰺬 (0xF0EAC)
     */
    public data object TEXT_BOX_REMOVE : MaterialDesignGlyphs(0xF0EAC)

    /**
     * 󰺭 (0xF0EAD)
     */
    public data object TEXT_BOX_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0EAD)

    /**
     * 󰺮 (0xF0EAE)
     */
    public data object TEXT_BOX_SEARCH : MaterialDesignGlyphs(0xF0EAE)

    /**
     * 󰺯 (0xF0EAF)
     */
    public data object TEXT_BOX_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF0EAF)

    /**
     * 󰺰 (0xF0EB0)
     */
    public data object FILE_IMAGE_OUTLINE : MaterialDesignGlyphs(0xF0EB0)

    /**
     * 󰺱 (0xF0EB1)
     */
    public data object FINGERPRINT_OFF : MaterialDesignGlyphs(0xF0EB1)

    /**
     * 󰺲 (0xF0EB2)
     */
    public data object FORMAT_LIST_BULLETED_TRIANGLE : MaterialDesignGlyphs(0xF0EB2)

    /**
     * 󰺳 (0xF0EB3)
     */
    public data object FORMAT_OVERLINE : MaterialDesignGlyphs(0xF0EB3)

    /**
     * 󰺴 (0xF0EB4)
     */
    public data object FREQUENTLY_ASKED_QUESTIONS : MaterialDesignGlyphs(0xF0EB4)

    /**
     * 󰺵 (0xF0EB5)
     */
    public data object GAMEPAD_SQUARE : MaterialDesignGlyphs(0xF0EB5)

    /**
     * 󰺶 (0xF0EB6)
     */
    public data object GAMEPAD_SQUARE_OUTLINE : MaterialDesignGlyphs(0xF0EB6)

    /**
     * 󰺷 (0xF0EB7)
     */
    public data object GAMEPAD_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF0EB7)

    /**
     * 󰺸 (0xF0EB8)
     */
    public data object GAS_STATION_OUTLINE : MaterialDesignGlyphs(0xF0EB8)

    /**
     * 󰺹 (0xF0EB9)
     */
    public data object GOOGLE_PODCAST : MaterialDesignGlyphs(0xF0EB9)

    /**
     * 󰺺 (0xF0EBA)
     */
    public data object HOME_ANALYTICS : MaterialDesignGlyphs(0xF0EBA)

    /**
     * 󰺻 (0xF0EBB)
     */
    public data object MAIL : MaterialDesignGlyphs(0xF0EBB)

    /**
     * 󰺼 (0xF0EBC)
     */
    public data object MAP_CHECK : MaterialDesignGlyphs(0xF0EBC)

    /**
     * 󰺽 (0xF0EBD)
     */
    public data object MAP_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0EBD)

    /**
     * 󰺾 (0xF0EBE)
     */
    public data object RULER_SQUARE_COMPASS : MaterialDesignGlyphs(0xF0EBE)

    /**
     * 󰺿 (0xF0EBF)
     */
    public data object NOTEBOOK_OUTLINE : MaterialDesignGlyphs(0xF0EBF)

    /**
     * 󰻀 (0xF0EC0)
     */
    public data object PENGUIN : MaterialDesignGlyphs(0xF0EC0)

    /**
     * 󰻁 (0xF0EC1)
     */
    public data object RADIOACTIVE_OFF : MaterialDesignGlyphs(0xF0EC1)

    /**
     * 󰻂 (0xF0EC2)
     */
    public data object RECORD_CIRCLE : MaterialDesignGlyphs(0xF0EC2)

    /**
     * 󰻃 (0xF0EC3)
     */
    public data object RECORD_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0EC3)

    /**
     * 󰻄 (0xF0EC4)
     */
    public data object REMOTE_OFF : MaterialDesignGlyphs(0xF0EC4)

    /**
     * 󰻅 (0xF0EC5)
     */
    public data object REMOTE_TV : MaterialDesignGlyphs(0xF0EC5)

    /**
     * 󰻆 (0xF0EC6)
     */
    public data object REMOTE_TV_OFF : MaterialDesignGlyphs(0xF0EC6)

    /**
     * 󰻇 (0xF0EC7)
     */
    public data object ROTATE_3D : MaterialDesignGlyphs(0xF0EC7)

    /**
     * 󰻈 (0xF0EC8)
     */
    public data object SAIL_BOAT : MaterialDesignGlyphs(0xF0EC8)

    /**
     * 󰻉 (0xF0EC9)
     */
    public data object SCATTER_PLOT : MaterialDesignGlyphs(0xF0EC9)

    /**
     * 󰻊 (0xF0ECA)
     */
    public data object SCATTER_PLOT_OUTLINE : MaterialDesignGlyphs(0xF0ECA)

    /**
     * 󰻋 (0xF0ECB)
     */
    public data object SEGMENT : MaterialDesignGlyphs(0xF0ECB)

    /**
     * 󰻌 (0xF0ECC)
     */
    public data object SHIELD_ALERT : MaterialDesignGlyphs(0xF0ECC)

    /**
     * 󰻍 (0xF0ECD)
     */
    public data object SHIELD_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0ECD)

    /**
     * 󰻎 (0xF0ECE)
     */
    public data object TABLET_DASHBOARD : MaterialDesignGlyphs(0xF0ECE)

    /**
     * 󰻏 (0xF0ECF)
     */
    public data object TELEVISION_PLAY : MaterialDesignGlyphs(0xF0ECF)

    /**
     * 󰻐 (0xF0ED0)
     */
    public data object UNICODE : MaterialDesignGlyphs(0xF0ED0)

    /**
     * 󰻑 (0xF0ED1)
     */
    public data object VIDEO_3D_VARIANT : MaterialDesignGlyphs(0xF0ED1)

    /**
     * 󰻒 (0xF0ED2)
     */
    public data object VIDEO_WIRELESS : MaterialDesignGlyphs(0xF0ED2)

    /**
     * 󰻓 (0xF0ED3)
     */
    public data object VIDEO_WIRELESS_OUTLINE : MaterialDesignGlyphs(0xF0ED3)

    /**
     * 󰻔 (0xF0ED4)
     */
    public data object ACCOUNT_VOICE_OFF : MaterialDesignGlyphs(0xF0ED4)

    /**
     * 󰻕 (0xF0ED5)
     */
    public data object BACTERIA : MaterialDesignGlyphs(0xF0ED5)

    /**
     * 󰻖 (0xF0ED6)
     */
    public data object BACTERIA_OUTLINE : MaterialDesignGlyphs(0xF0ED6)

    /**
     * 󰻗 (0xF0ED7)
     */
    public data object CALENDAR_ACCOUNT : MaterialDesignGlyphs(0xF0ED7)

    /**
     * 󰻘 (0xF0ED8)
     */
    public data object CALENDAR_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF0ED8)

    /**
     * 󰻙 (0xF0ED9)
     */
    public data object CALENDAR_WEEKEND : MaterialDesignGlyphs(0xF0ED9)

    /**
     * 󰻚 (0xF0EDA)
     */
    public data object CALENDAR_WEEKEND_OUTLINE : MaterialDesignGlyphs(0xF0EDA)

    /**
     * 󰻛 (0xF0EDB)
     */
    public data object CAMERA_PLUS : MaterialDesignGlyphs(0xF0EDB)

    /**
     * 󰻜 (0xF0EDC)
     */
    public data object CAMERA_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0EDC)

    /**
     * 󰻝 (0xF0EDD)
     */
    public data object CAMPFIRE : MaterialDesignGlyphs(0xF0EDD)

    /**
     * 󰻞 (0xF0EDE)
     */
    public data object CHAT_OUTLINE : MaterialDesignGlyphs(0xF0EDE)

    /**
     * 󰻟 (0xF0EDF)
     */
    public data object CPU_32_BIT : MaterialDesignGlyphs(0xF0EDF)

    /**
     * 󰻠 (0xF0EE0)
     */
    public data object CPU_64_BIT : MaterialDesignGlyphs(0xF0EE0)

    /**
     * 󰻡 (0xF0EE1)
     */
    public data object CREDIT_CARD_CLOCK : MaterialDesignGlyphs(0xF0EE1)

    /**
     * 󰻢 (0xF0EE2)
     */
    public data object CREDIT_CARD_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF0EE2)

    /**
     * 󰻣 (0xF0EE3)
     */
    public data object EMAIL_EDIT : MaterialDesignGlyphs(0xF0EE3)

    /**
     * 󰻤 (0xF0EE4)
     */
    public data object EMAIL_EDIT_OUTLINE : MaterialDesignGlyphs(0xF0EE4)

    /**
     * 󰻥 (0xF0EE5)
     */
    public data object EMAIL_MINUS : MaterialDesignGlyphs(0xF0EE5)

    /**
     * 󰻦 (0xF0EE6)
     */
    public data object EMAIL_MINUS_OUTLINE : MaterialDesignGlyphs(0xF0EE6)

    /**
     * 󰻧 (0xF0EE7)
     */
    public data object EMAIL_MULTIPLE : MaterialDesignGlyphs(0xF0EE7)

    /**
     * 󰻨 (0xF0EE8)
     */
    public data object EMAIL_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0EE8)

    /**
     * 󰻩 (0xF0EE9)
     */
    public data object EMAIL_OPEN_MULTIPLE : MaterialDesignGlyphs(0xF0EE9)

    /**
     * 󰻪 (0xF0EEA)
     */
    public data object EMAIL_OPEN_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0EEA)

    /**
     * 󰻫 (0xF0EEB)
     */
    public data object FILE_CAD : MaterialDesignGlyphs(0xF0EEB)

    /**
     * 󰻬 (0xF0EEC)
     */
    public data object FILE_CAD_BOX : MaterialDesignGlyphs(0xF0EEC)

    /**
     * 󰻭 (0xF0EED)
     */
    public data object FILE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0EED)

    /**
     * 󰻮 (0xF0EEE)
     */
    public data object FILTER_MINUS : MaterialDesignGlyphs(0xF0EEE)

    /**
     * 󰻯 (0xF0EEF)
     */
    public data object FILTER_MINUS_OUTLINE : MaterialDesignGlyphs(0xF0EEF)

    /**
     * 󰻰 (0xF0EF0)
     */
    public data object FILTER_PLUS : MaterialDesignGlyphs(0xF0EF0)

    /**
     * 󰻱 (0xF0EF1)
     */
    public data object FILTER_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0EF1)

    /**
     * 󰻲 (0xF0EF2)
     */
    public data object FIRE_EXTINGUISHER : MaterialDesignGlyphs(0xF0EF2)

    /**
     * 󰻳 (0xF0EF3)
     */
    public data object FISHBOWL : MaterialDesignGlyphs(0xF0EF3)

    /**
     * 󰻴 (0xF0EF4)
     */
    public data object FISHBOWL_OUTLINE : MaterialDesignGlyphs(0xF0EF4)

    /**
     * 󰻵 (0xF0EF5)
     */
    public data object FIT_TO_PAGE : MaterialDesignGlyphs(0xF0EF5)

    /**
     * 󰻶 (0xF0EF6)
     */
    public data object FIT_TO_PAGE_OUTLINE : MaterialDesignGlyphs(0xF0EF6)

    /**
     * 󰻷 (0xF0EF7)
     */
    public data object FLASH_ALERT : MaterialDesignGlyphs(0xF0EF7)

    /**
     * 󰻸 (0xF0EF8)
     */
    public data object FLASH_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0EF8)

    /**
     * 󰻹 (0xF0EF9)
     */
    public data object HEART_FLASH : MaterialDesignGlyphs(0xF0EF9)

    /**
     * 󰻺 (0xF0EFA)
     */
    public data object HOME_FLOOD : MaterialDesignGlyphs(0xF0EFA)

    /**
     * 󰻻 (0xF0EFB)
     */
    public data object HUMAN_MALE_HEIGHT : MaterialDesignGlyphs(0xF0EFB)

    /**
     * 󰻼 (0xF0EFC)
     */
    public data object HUMAN_MALE_HEIGHT_VARIANT : MaterialDesignGlyphs(0xF0EFC)

    /**
     * 󰻽 (0xF0EFD)
     */
    public data object ICE_POP : MaterialDesignGlyphs(0xF0EFD)

    /**
     * 󰻾 (0xF0EFE)
     */
    public data object IDENTIFIER : MaterialDesignGlyphs(0xF0EFE)

    /**
     * 󰻿 (0xF0EFF)
     */
    public data object IMAGE_FILTER_CENTER_FOCUS_STRONG : MaterialDesignGlyphs(0xF0EFF)

    /**
     * 󰼀 (0xF0F00)
     */
    public data object IMAGE_FILTER_CENTER_FOCUS_STRONG_OUTLINE : MaterialDesignGlyphs(0xF0F00)

    /**
     * 󰼁 (0xF0F01)
     */
    public data object JELLYFISH : MaterialDesignGlyphs(0xF0F01)

    /**
     * 󰼂 (0xF0F02)
     */
    public data object JELLYFISH_OUTLINE : MaterialDesignGlyphs(0xF0F02)

    /**
     * 󰼃 (0xF0F03)
     */
    public data object LASSO : MaterialDesignGlyphs(0xF0F03)

    /**
     * 󰼄 (0xF0F04)
     */
    public data object MUSIC_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0F04)

    /**
     * 󰼅 (0xF0F05)
     */
    public data object MAP_MARKER_ALERT : MaterialDesignGlyphs(0xF0F05)

    /**
     * 󰼆 (0xF0F06)
     */
    public data object MAP_MARKER_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0F06)

    /**
     * 󰼇 (0xF0F07)
     */
    public data object MAP_MARKER_QUESTION : MaterialDesignGlyphs(0xF0F07)

    /**
     * 󰼈 (0xF0F08)
     */
    public data object MAP_MARKER_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF0F08)

    /**
     * 󰼉 (0xF0F09)
     */
    public data object MAP_MARKER_REMOVE : MaterialDesignGlyphs(0xF0F09)

    /**
     * 󰼊 (0xF0F0A)
     */
    public data object MAP_MARKER_REMOVE_VARIANT : MaterialDesignGlyphs(0xF0F0A)

    /**
     * 󰼋 (0xF0F0B)
     */
    public data object NECKLACE : MaterialDesignGlyphs(0xF0F0B)

    /**
     * 󰼌 (0xF0F0C)
     */
    public data object NEWSPAPER_MINUS : MaterialDesignGlyphs(0xF0F0C)

    /**
     * 󰼍 (0xF0F0D)
     */
    public data object NEWSPAPER_PLUS : MaterialDesignGlyphs(0xF0F0D)

    /**
     * 󰼎 (0xF0F0E)
     */
    public data object NUMERIC_0_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F0E)

    /**
     * 󰼏 (0xF0F0F)
     */
    public data object NUMERIC_1_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F0F)

    /**
     * 󰼐 (0xF0F10)
     */
    public data object NUMERIC_2_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F10)

    /**
     * 󰼑 (0xF0F11)
     */
    public data object NUMERIC_3_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F11)

    /**
     * 󰼒 (0xF0F12)
     */
    public data object NUMERIC_4_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F12)

    /**
     * 󰼓 (0xF0F13)
     */
    public data object NUMERIC_5_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F13)

    /**
     * 󰼔 (0xF0F14)
     */
    public data object NUMERIC_6_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F14)

    /**
     * 󰼕 (0xF0F15)
     */
    public data object NUMERIC_7_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F15)

    /**
     * 󰼖 (0xF0F16)
     */
    public data object NUMERIC_8_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F16)

    /**
     * 󰼗 (0xF0F17)
     */
    public data object NUMERIC_9_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F17)

    /**
     * 󰼘 (0xF0F18)
     */
    public data object NUMERIC_9_PLUS_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0F18)

    /**
     * 󰼙 (0xF0F19)
     */
    public data object OIL_LAMP : MaterialDesignGlyphs(0xF0F19)

    /**
     * 󰼚 (0xF0F1A)
     */
    public data object PHONE_ALERT : MaterialDesignGlyphs(0xF0F1A)

    /**
     * 󰼛 (0xF0F1B)
     */
    public data object PLAY_OUTLINE : MaterialDesignGlyphs(0xF0F1B)

    /**
     * 󰼜 (0xF0F1C)
     */
    public data object PURSE : MaterialDesignGlyphs(0xF0F1C)

    /**
     * 󰼝 (0xF0F1D)
     */
    public data object PURSE_OUTLINE : MaterialDesignGlyphs(0xF0F1D)

    /**
     * 󰼞 (0xF0F1E)
     */
    public data object RAILROAD_LIGHT : MaterialDesignGlyphs(0xF0F1E)

    /**
     * 󰼟 (0xF0F1F)
     */
    public data object REPLY_ALL_OUTLINE : MaterialDesignGlyphs(0xF0F1F)

    /**
     * 󰼠 (0xF0F20)
     */
    public data object REPLY_OUTLINE : MaterialDesignGlyphs(0xF0F20)

    /**
     * 󰼡 (0xF0F21)
     */
    public data object RSS_OFF : MaterialDesignGlyphs(0xF0F21)

    /**
     * 󰼢 (0xF0F22)
     */
    public data object SELECTION_ELLIPSE_ARROW_INSIDE : MaterialDesignGlyphs(0xF0F22)

    /**
     * 󰼣 (0xF0F23)
     */
    public data object SHARE_OFF : MaterialDesignGlyphs(0xF0F23)

    /**
     * 󰼤 (0xF0F24)
     */
    public data object SHARE_OFF_OUTLINE : MaterialDesignGlyphs(0xF0F24)

    /**
     * 󰼥 (0xF0F25)
     */
    public data object SKIP_BACKWARD_OUTLINE : MaterialDesignGlyphs(0xF0F25)

    /**
     * 󰼦 (0xF0F26)
     */
    public data object SKIP_FORWARD_OUTLINE : MaterialDesignGlyphs(0xF0F26)

    /**
     * 󰼧 (0xF0F27)
     */
    public data object SKIP_NEXT_OUTLINE : MaterialDesignGlyphs(0xF0F27)

    /**
     * 󰼨 (0xF0F28)
     */
    public data object SKIP_PREVIOUS_OUTLINE : MaterialDesignGlyphs(0xF0F28)

    /**
     * 󰼩 (0xF0F29)
     */
    public data object SNOWFLAKE_ALERT : MaterialDesignGlyphs(0xF0F29)

    /**
     * 󰼪 (0xF0F2A)
     */
    public data object SNOWFLAKE_VARIANT : MaterialDesignGlyphs(0xF0F2A)

    /**
     * 󰼫 (0xF0F2B)
     */
    public data object STRETCH_TO_PAGE : MaterialDesignGlyphs(0xF0F2B)

    /**
     * 󰼬 (0xF0F2C)
     */
    public data object STRETCH_TO_PAGE_OUTLINE : MaterialDesignGlyphs(0xF0F2C)

    /**
     * 󰼭 (0xF0F2D)
     */
    public data object TYPEWRITER : MaterialDesignGlyphs(0xF0F2D)

    /**
     * 󰼮 (0xF0F2E)
     */
    public data object WAVE : MaterialDesignGlyphs(0xF0F2E)

    /**
     * 󰼯 (0xF0F2F)
     */
    public data object WEATHER_CLOUDY_ALERT : MaterialDesignGlyphs(0xF0F2F)

    /**
     * 󰼰 (0xF0F30)
     */
    public data object WEATHER_HAZY : MaterialDesignGlyphs(0xF0F30)

    /**
     * 󰼱 (0xF0F31)
     */
    public data object WEATHER_NIGHT_PARTLY_CLOUDY : MaterialDesignGlyphs(0xF0F31)

    /**
     * 󰼲 (0xF0F32)
     */
    public data object WEATHER_PARTLY_LIGHTNING : MaterialDesignGlyphs(0xF0F32)

    /**
     * 󰼳 (0xF0F33)
     */
    public data object WEATHER_PARTLY_RAINY : MaterialDesignGlyphs(0xF0F33)

    /**
     * 󰼴 (0xF0F34)
     */
    public data object WEATHER_PARTLY_SNOWY : MaterialDesignGlyphs(0xF0F34)

    /**
     * 󰼵 (0xF0F35)
     */
    public data object WEATHER_PARTLY_SNOWY_RAINY : MaterialDesignGlyphs(0xF0F35)

    /**
     * 󰼶 (0xF0F36)
     */
    public data object WEATHER_SNOWY_HEAVY : MaterialDesignGlyphs(0xF0F36)

    /**
     * 󰼷 (0xF0F37)
     */
    public data object WEATHER_SUNNY_ALERT : MaterialDesignGlyphs(0xF0F37)

    /**
     * 󰼸 (0xF0F38)
     */
    public data object WEATHER_TORNADO : MaterialDesignGlyphs(0xF0F38)

    /**
     * 󰼹 (0xF0F39)
     */
    public data object BABY_BOTTLE : MaterialDesignGlyphs(0xF0F39)

    /**
     * 󰼺 (0xF0F3A)
     */
    public data object BABY_BOTTLE_OUTLINE : MaterialDesignGlyphs(0xF0F3A)

    /**
     * 󰼻 (0xF0F3B)
     */
    public data object BAG_CARRY_ON : MaterialDesignGlyphs(0xF0F3B)

    /**
     * 󰼼 (0xF0F3C)
     */
    public data object BAG_CARRY_ON_OFF : MaterialDesignGlyphs(0xF0F3C)

    /**
     * 󰼽 (0xF0F3D)
     */
    public data object BAG_CHECKED : MaterialDesignGlyphs(0xF0F3D)

    /**
     * 󰼾 (0xF0F3E)
     */
    public data object BAGUETTE : MaterialDesignGlyphs(0xF0F3E)

    /**
     * 󰼿 (0xF0F3F)
     */
    public data object BUS_MULTIPLE : MaterialDesignGlyphs(0xF0F3F)

    /**
     * 󰽀 (0xF0F40)
     */
    public data object CAR_SHIFT_PATTERN : MaterialDesignGlyphs(0xF0F40)

    /**
     * 󰽁 (0xF0F41)
     */
    public data object CELLPHONE_INFORMATION : MaterialDesignGlyphs(0xF0F41)

    /**
     * 󰽂 (0xF0F42)
     */
    public data object CONTENT_SAVE_ALERT : MaterialDesignGlyphs(0xF0F42)

    /**
     * 󰽃 (0xF0F43)
     */
    public data object CONTENT_SAVE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0F43)

    /**
     * 󰽄 (0xF0F44)
     */
    public data object CONTENT_SAVE_ALL_OUTLINE : MaterialDesignGlyphs(0xF0F44)

    /**
     * 󰽅 (0xF0F45)
     */
    public data object CROSSHAIRS_OFF : MaterialDesignGlyphs(0xF0F45)

    /**
     * 󰽆 (0xF0F46)
     */
    public data object CUPBOARD : MaterialDesignGlyphs(0xF0F46)

    /**
     * 󰽇 (0xF0F47)
     */
    public data object CUPBOARD_OUTLINE : MaterialDesignGlyphs(0xF0F47)

    /**
     * 󰽈 (0xF0F48)
     */
    public data object CHAIR_ROLLING : MaterialDesignGlyphs(0xF0F48)

    /**
     * 󰽉 (0xF0F49)
     */
    public data object DRAW : MaterialDesignGlyphs(0xF0F49)

    /**
     * 󰽊 (0xF0F4A)
     */
    public data object DRESSER : MaterialDesignGlyphs(0xF0F4A)

    /**
     * 󰽋 (0xF0F4B)
     */
    public data object DRESSER_OUTLINE : MaterialDesignGlyphs(0xF0F4B)

    /**
     * 󰽌 (0xF0F4C)
     */
    public data object EMOTICON_FROWN : MaterialDesignGlyphs(0xF0F4C)

    /**
     * 󰽍 (0xF0F4D)
     */
    public data object EMOTICON_FROWN_OUTLINE : MaterialDesignGlyphs(0xF0F4D)

    /**
     * 󰽎 (0xF0F4E)
     */
    public data object FOCUS_AUTO : MaterialDesignGlyphs(0xF0F4E)

    /**
     * 󰽏 (0xF0F4F)
     */
    public data object FOCUS_FIELD : MaterialDesignGlyphs(0xF0F4F)

    /**
     * 󰽐 (0xF0F50)
     */
    public data object FOCUS_FIELD_HORIZONTAL : MaterialDesignGlyphs(0xF0F50)

    /**
     * 󰽑 (0xF0F51)
     */
    public data object FOCUS_FIELD_VERTICAL : MaterialDesignGlyphs(0xF0F51)

    /**
     * 󰽒 (0xF0F52)
     */
    public data object FOOT_PRINT : MaterialDesignGlyphs(0xF0F52)

    /**
     * 󰽓 (0xF0F53)
     */
    public data object HANDBALL : MaterialDesignGlyphs(0xF0F53)

    /**
     * 󰽔 (0xF0F54)
     */
    public data object HOME_THERMOMETER : MaterialDesignGlyphs(0xF0F54)

    /**
     * 󰽕 (0xF0F55)
     */
    public data object HOME_THERMOMETER_OUTLINE : MaterialDesignGlyphs(0xF0F55)

    /**
     * 󰽖 (0xF0F56)
     */
    public data object KETTLE_OUTLINE : MaterialDesignGlyphs(0xF0F56)

    /**
     * 󰽗 (0xF0F57)
     */
    public data object LATITUDE : MaterialDesignGlyphs(0xF0F57)

    /**
     * 󰽘 (0xF0F58)
     */
    public data object LAYERS_TRIPLE : MaterialDesignGlyphs(0xF0F58)

    /**
     * 󰽙 (0xF0F59)
     */
    public data object LAYERS_TRIPLE_OUTLINE : MaterialDesignGlyphs(0xF0F59)

    /**
     * 󰽚 (0xF0F5A)
     */
    public data object LONGITUDE : MaterialDesignGlyphs(0xF0F5A)

    /**
     * 󰽛 (0xF0F5B)
     */
    public data object LANGUAGE_MARKDOWN_OUTLINE : MaterialDesignGlyphs(0xF0F5B)

    /**
     * 󰽜 (0xF0F5C)
     */
    public data object MERGE : MaterialDesignGlyphs(0xF0F5C)

    /**
     * 󰽝 (0xF0F5D)
     */
    public data object MIDDLEWARE : MaterialDesignGlyphs(0xF0F5D)

    /**
     * 󰽞 (0xF0F5E)
     */
    public data object MIDDLEWARE_OUTLINE : MaterialDesignGlyphs(0xF0F5E)

    /**
     * 󰽟 (0xF0F5F)
     */
    public data object MONITOR_SPEAKER : MaterialDesignGlyphs(0xF0F5F)

    /**
     * 󰽠 (0xF0F60)
     */
    public data object MONITOR_SPEAKER_OFF : MaterialDesignGlyphs(0xF0F60)

    /**
     * 󰽡 (0xF0F61)
     */
    public data object MOON_FIRST_QUARTER : MaterialDesignGlyphs(0xF0F61)

    /**
     * 󰽢 (0xF0F62)
     */
    public data object MOON_FULL : MaterialDesignGlyphs(0xF0F62)

    /**
     * 󰽣 (0xF0F63)
     */
    public data object MOON_LAST_QUARTER : MaterialDesignGlyphs(0xF0F63)

    /**
     * 󰽤 (0xF0F64)
     */
    public data object MOON_NEW : MaterialDesignGlyphs(0xF0F64)

    /**
     * 󰽥 (0xF0F65)
     */
    public data object MOON_WANING_CRESCENT : MaterialDesignGlyphs(0xF0F65)

    /**
     * 󰽦 (0xF0F66)
     */
    public data object MOON_WANING_GIBBOUS : MaterialDesignGlyphs(0xF0F66)

    /**
     * 󰽧 (0xF0F67)
     */
    public data object MOON_WAXING_CRESCENT : MaterialDesignGlyphs(0xF0F67)

    /**
     * 󰽨 (0xF0F68)
     */
    public data object MOON_WAXING_GIBBOUS : MaterialDesignGlyphs(0xF0F68)

    /**
     * 󰽩 (0xF0F69)
     */
    public data object MUSIC_ACCIDENTAL_DOUBLE_FLAT : MaterialDesignGlyphs(0xF0F69)

    /**
     * 󰽪 (0xF0F6A)
     */
    public data object MUSIC_ACCIDENTAL_DOUBLE_SHARP : MaterialDesignGlyphs(0xF0F6A)

    /**
     * 󰽫 (0xF0F6B)
     */
    public data object MUSIC_ACCIDENTAL_FLAT : MaterialDesignGlyphs(0xF0F6B)

    /**
     * 󰽬 (0xF0F6C)
     */
    public data object MUSIC_ACCIDENTAL_NATURAL : MaterialDesignGlyphs(0xF0F6C)

    /**
     * 󰽭 (0xF0F6D)
     */
    public data object MUSIC_ACCIDENTAL_SHARP : MaterialDesignGlyphs(0xF0F6D)

    /**
     * 󰽮 (0xF0F6E)
     */
    public data object MUSIC_CLEF_ALTO : MaterialDesignGlyphs(0xF0F6E)

    /**
     * 󰽯 (0xF0F6F)
     */
    public data object MUSIC_CLEF_BASS : MaterialDesignGlyphs(0xF0F6F)

    /**
     * 󰽰 (0xF0F70)
     */
    public data object MUSIC_CLEF_TREBLE : MaterialDesignGlyphs(0xF0F70)

    /**
     * 󰽱 (0xF0F71)
     */
    public data object MUSIC_NOTE_EIGHTH_DOTTED : MaterialDesignGlyphs(0xF0F71)

    /**
     * 󰽲 (0xF0F72)
     */
    public data object MUSIC_NOTE_HALF_DOTTED : MaterialDesignGlyphs(0xF0F72)

    /**
     * 󰽳 (0xF0F73)
     */
    public data object MUSIC_NOTE_OFF_OUTLINE : MaterialDesignGlyphs(0xF0F73)

    /**
     * 󰽴 (0xF0F74)
     */
    public data object MUSIC_NOTE_OUTLINE : MaterialDesignGlyphs(0xF0F74)

    /**
     * 󰽵 (0xF0F75)
     */
    public data object MUSIC_NOTE_QUARTER_DOTTED : MaterialDesignGlyphs(0xF0F75)

    /**
     * 󰽶 (0xF0F76)
     */
    public data object MUSIC_NOTE_SIXTEENTH_DOTTED : MaterialDesignGlyphs(0xF0F76)

    /**
     * 󰽷 (0xF0F77)
     */
    public data object MUSIC_NOTE_WHOLE_DOTTED : MaterialDesignGlyphs(0xF0F77)

    /**
     * 󰽸 (0xF0F78)
     */
    public data object MUSIC_REST_EIGHTH : MaterialDesignGlyphs(0xF0F78)

    /**
     * 󰽹 (0xF0F79)
     */
    public data object MUSIC_REST_HALF : MaterialDesignGlyphs(0xF0F79)

    /**
     * 󰽺 (0xF0F7A)
     */
    public data object MUSIC_REST_QUARTER : MaterialDesignGlyphs(0xF0F7A)

    /**
     * 󰽻 (0xF0F7B)
     */
    public data object MUSIC_REST_SIXTEENTH : MaterialDesignGlyphs(0xF0F7B)

    /**
     * 󰽼 (0xF0F7C)
     */
    public data object MUSIC_REST_WHOLE : MaterialDesignGlyphs(0xF0F7C)

    /**
     * 󰽽 (0xF0F7D)
     */
    public data object NUMERIC_10_BOX : MaterialDesignGlyphs(0xF0F7D)

    /**
     * 󰽾 (0xF0F7E)
     */
    public data object NUMERIC_10_BOX_OUTLINE : MaterialDesignGlyphs(0xF0F7E)

    /**
     * 󰽿 (0xF0F7F)
     */
    public data object PAGE_LAYOUT_HEADER_FOOTER : MaterialDesignGlyphs(0xF0F7F)

    /**
     * 󰾀 (0xF0F80)
     */
    public data object PATIO_HEATER : MaterialDesignGlyphs(0xF0F80)

    /**
     * 󰾁 (0xF0F81)
     */
    public data object WAREHOUSE : MaterialDesignGlyphs(0xF0F81)

    /**
     * 󰾂 (0xF0F82)
     */
    public data object SELECT_GROUP : MaterialDesignGlyphs(0xF0F82)

    /**
     * 󰾃 (0xF0F83)
     */
    public data object SHIELD_CAR : MaterialDesignGlyphs(0xF0F83)

    /**
     * 󰾄 (0xF0F84)
     */
    public data object SHOPPING_SEARCH : MaterialDesignGlyphs(0xF0F84)

    /**
     * 󰾅 (0xF0F85)
     */
    public data object SPEEDOMETER_MEDIUM : MaterialDesignGlyphs(0xF0F85)

    /**
     * 󰾆 (0xF0F86)
     */
    public data object SPEEDOMETER_SLOW : MaterialDesignGlyphs(0xF0F86)

    /**
     * 󰾇 (0xF0F87)
     */
    public data object TABLE_LARGE_PLUS : MaterialDesignGlyphs(0xF0F87)

    /**
     * 󰾈 (0xF0F88)
     */
    public data object TABLE_LARGE_REMOVE : MaterialDesignGlyphs(0xF0F88)

    /**
     * 󰾉 (0xF0F89)
     */
    public data object TELEVISION_PAUSE : MaterialDesignGlyphs(0xF0F89)

    /**
     * 󰾊 (0xF0F8A)
     */
    public data object TELEVISION_STOP : MaterialDesignGlyphs(0xF0F8A)

    /**
     * 󰾋 (0xF0F8B)
     */
    public data object TRANSIT_DETOUR : MaterialDesignGlyphs(0xF0F8B)

    /**
     * 󰾌 (0xF0F8C)
     */
    public data object VIDEO_INPUT_SCART : MaterialDesignGlyphs(0xF0F8C)

    /**
     * 󰾍 (0xF0F8D)
     */
    public data object VIEW_GRID_PLUS : MaterialDesignGlyphs(0xF0F8D)

    /**
     * 󰾎 (0xF0F8E)
     */
    public data object WALLET_PLUS : MaterialDesignGlyphs(0xF0F8E)

    /**
     * 󰾏 (0xF0F8F)
     */
    public data object WALLET_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0F8F)

    /**
     * 󰾐 (0xF0F90)
     */
    public data object WARDROBE : MaterialDesignGlyphs(0xF0F90)

    /**
     * 󰾑 (0xF0F91)
     */
    public data object WARDROBE_OUTLINE : MaterialDesignGlyphs(0xF0F91)

    /**
     * 󰾒 (0xF0F92)
     */
    public data object WATER_BOILER : MaterialDesignGlyphs(0xF0F92)

    /**
     * 󰾓 (0xF0F93)
     */
    public data object WATER_PUMP_OFF : MaterialDesignGlyphs(0xF0F93)

    /**
     * 󰾔 (0xF0F94)
     */
    public data object WEB_BOX : MaterialDesignGlyphs(0xF0F94)

    /**
     * 󰾕 (0xF0F95)
     */
    public data object TIMELINE_ALERT : MaterialDesignGlyphs(0xF0F95)

    /**
     * 󰾖 (0xF0F96)
     */
    public data object TIMELINE_PLUS : MaterialDesignGlyphs(0xF0F96)

    /**
     * 󰾗 (0xF0F97)
     */
    public data object TIMELINE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF0F97)

    /**
     * 󰾘 (0xF0F98)
     */
    public data object TIMELINE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF0F98)

    /**
     * 󰾙 (0xF0F99)
     */
    public data object TIMELINE_HELP : MaterialDesignGlyphs(0xF0F99)

    /**
     * 󰾚 (0xF0F9A)
     */
    public data object TIMELINE_HELP_OUTLINE : MaterialDesignGlyphs(0xF0F9A)

    /**
     * 󰾛 (0xF0F9B)
     */
    public data object HOME_EXPORT_OUTLINE : MaterialDesignGlyphs(0xF0F9B)

    /**
     * 󰾜 (0xF0F9C)
     */
    public data object HOME_IMPORT_OUTLINE : MaterialDesignGlyphs(0xF0F9C)

    /**
     * 󰾝 (0xF0F9D)
     */
    public data object ACCOUNT_FILTER_OUTLINE : MaterialDesignGlyphs(0xF0F9D)

    /**
     * 󰾞 (0xF0F9E)
     */
    public data object APPROXIMATELY_EQUAL : MaterialDesignGlyphs(0xF0F9E)

    /**
     * 󰾟 (0xF0F9F)
     */
    public data object APPROXIMATELY_EQUAL_BOX : MaterialDesignGlyphs(0xF0F9F)

    /**
     * 󰾠 (0xF0FA0)
     */
    public data object BABY_CARRIAGE_OFF : MaterialDesignGlyphs(0xF0FA0)

    /**
     * 󰾡 (0xF0FA1)
     */
    public data object BEE : MaterialDesignGlyphs(0xF0FA1)

    /**
     * 󰾢 (0xF0FA2)
     */
    public data object BEE_FLOWER : MaterialDesignGlyphs(0xF0FA2)

    /**
     * 󰾣 (0xF0FA3)
     */
    public data object CAR_CHILD_SEAT : MaterialDesignGlyphs(0xF0FA3)

    /**
     * 󰾤 (0xF0FA4)
     */
    public data object CAR_SEAT : MaterialDesignGlyphs(0xF0FA4)

    /**
     * 󰾥 (0xF0FA5)
     */
    public data object CAR_SEAT_COOLER : MaterialDesignGlyphs(0xF0FA5)

    /**
     * 󰾦 (0xF0FA6)
     */
    public data object CAR_SEAT_HEATER : MaterialDesignGlyphs(0xF0FA6)

    /**
     * 󰾧 (0xF0FA7)
     */
    public data object CHART_BELL_CURVE_CUMULATIVE : MaterialDesignGlyphs(0xF0FA7)

    /**
     * 󰾨 (0xF0FA8)
     */
    public data object CLOCK_CHECK : MaterialDesignGlyphs(0xF0FA8)

    /**
     * 󰾩 (0xF0FA9)
     */
    public data object CLOCK_CHECK_OUTLINE : MaterialDesignGlyphs(0xF0FA9)

    /**
     * 󰾪 (0xF0FAA)
     */
    public data object COFFEE_OFF : MaterialDesignGlyphs(0xF0FAA)

    /**
     * 󰾫 (0xF0FAB)
     */
    public data object COFFEE_OFF_OUTLINE : MaterialDesignGlyphs(0xF0FAB)

    /**
     * 󰾬 (0xF0FAC)
     */
    public data object CREDIT_CARD_MINUS : MaterialDesignGlyphs(0xF0FAC)

    /**
     * 󰾭 (0xF0FAD)
     */
    public data object CREDIT_CARD_MINUS_OUTLINE : MaterialDesignGlyphs(0xF0FAD)

    /**
     * 󰾮 (0xF0FAE)
     */
    public data object CREDIT_CARD_REMOVE : MaterialDesignGlyphs(0xF0FAE)

    /**
     * 󰾯 (0xF0FAF)
     */
    public data object CREDIT_CARD_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF0FAF)

    /**
     * 󰾰 (0xF0FB0)
     */
    public data object DEVICES : MaterialDesignGlyphs(0xF0FB0)

    /**
     * 󰾱 (0xF0FB1)
     */
    public data object EMAIL_NEWSLETTER : MaterialDesignGlyphs(0xF0FB1)

    /**
     * 󰾲 (0xF0FB2)
     */
    public data object EXPANSION_CARD_VARIANT : MaterialDesignGlyphs(0xF0FB2)

    /**
     * 󰾳 (0xF0FB3)
     */
    public data object POWER_SOCKET_CH : MaterialDesignGlyphs(0xF0FB3)

    /**
     * 󰾴 (0xF0FB4)
     */
    public data object FILE_SWAP : MaterialDesignGlyphs(0xF0FB4)

    /**
     * 󰾵 (0xF0FB5)
     */
    public data object FILE_SWAP_OUTLINE : MaterialDesignGlyphs(0xF0FB5)

    /**
     * 󰾶 (0xF0FB6)
     */
    public data object FOLDER_SWAP : MaterialDesignGlyphs(0xF0FB6)

    /**
     * 󰾷 (0xF0FB7)
     */
    public data object FOLDER_SWAP_OUTLINE : MaterialDesignGlyphs(0xF0FB7)

    /**
     * 󰾸 (0xF0FB8)
     */
    public data object FORMAT_LETTER_ENDS_WITH : MaterialDesignGlyphs(0xF0FB8)

    /**
     * 󰾹 (0xF0FB9)
     */
    public data object FORMAT_LETTER_MATCHES : MaterialDesignGlyphs(0xF0FB9)

    /**
     * 󰾺 (0xF0FBA)
     */
    public data object FORMAT_LETTER_STARTS_WITH : MaterialDesignGlyphs(0xF0FBA)

    /**
     * 󰾻 (0xF0FBB)
     */
    public data object FORMAT_TEXT_ROTATION_ANGLE_DOWN : MaterialDesignGlyphs(0xF0FBB)

    /**
     * 󰾼 (0xF0FBC)
     */
    public data object FORMAT_TEXT_ROTATION_ANGLE_UP : MaterialDesignGlyphs(0xF0FBC)

    /**
     * 󰾽 (0xF0FBD)
     */
    public data object FORMAT_TEXT_ROTATION_DOWN_VERTICAL : MaterialDesignGlyphs(0xF0FBD)

    /**
     * 󰾾 (0xF0FBE)
     */
    public data object FORMAT_TEXT_ROTATION_UP : MaterialDesignGlyphs(0xF0FBE)

    /**
     * 󰾿 (0xF0FBF)
     */
    public data object FORMAT_TEXT_ROTATION_VERTICAL : MaterialDesignGlyphs(0xF0FBF)

    /**
     * 󰿀 (0xF0FC0)
     */
    public data object ID_CARD : MaterialDesignGlyphs(0xF0FC0)

    /**
     * 󰿁 (0xF0FC1)
     */
    public data object IMAGE_AUTO_ADJUST : MaterialDesignGlyphs(0xF0FC1)

    /**
     * 󰿂 (0xF0FC2)
     */
    public data object KEY_WIRELESS : MaterialDesignGlyphs(0xF0FC2)

    /**
     * 󰿃 (0xF0FC3)
     */
    public data object LICENSE : MaterialDesignGlyphs(0xF0FC3)

    /**
     * 󰿄 (0xF0FC4)
     */
    public data object LOCATION_ENTER : MaterialDesignGlyphs(0xF0FC4)

    /**
     * 󰿅 (0xF0FC5)
     */
    public data object LOCATION_EXIT : MaterialDesignGlyphs(0xF0FC5)

    /**
     * 󰿆 (0xF0FC6)
     */
    public data object LOCK_OPEN_VARIANT : MaterialDesignGlyphs(0xF0FC6)

    /**
     * 󰿇 (0xF0FC7)
     */
    public data object LOCK_OPEN_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF0FC7)

    /**
     * 󰿈 (0xF0FC8)
     */
    public data object MATH_INTEGRAL : MaterialDesignGlyphs(0xF0FC8)

    /**
     * 󰿉 (0xF0FC9)
     */
    public data object MATH_INTEGRAL_BOX : MaterialDesignGlyphs(0xF0FC9)

    /**
     * 󰿊 (0xF0FCA)
     */
    public data object MATH_NORM : MaterialDesignGlyphs(0xF0FCA)

    /**
     * 󰿋 (0xF0FCB)
     */
    public data object MATH_NORM_BOX : MaterialDesignGlyphs(0xF0FCB)

    /**
     * 󰿌 (0xF0FCC)
     */
    public data object MESSAGE_LOCK : MaterialDesignGlyphs(0xF0FCC)

    /**
     * 󰿍 (0xF0FCD)
     */
    public data object MESSAGE_TEXT_LOCK : MaterialDesignGlyphs(0xF0FCD)

    /**
     * 󰿎 (0xF0FCE)
     */
    public data object MOVIE_OPEN : MaterialDesignGlyphs(0xF0FCE)

    /**
     * 󰿏 (0xF0FCF)
     */
    public data object MOVIE_OPEN_OUTLINE : MaterialDesignGlyphs(0xF0FCF)

    /**
     * 󰿐 (0xF0FD0)
     */
    public data object BED_QUEEN : MaterialDesignGlyphs(0xF0FD0)

    /**
     * 󰿑 (0xF0FD1)
     */
    public data object BED_KING_OUTLINE : MaterialDesignGlyphs(0xF0FD1)

    /**
     * 󰿒 (0xF0FD2)
     */
    public data object BED_KING : MaterialDesignGlyphs(0xF0FD2)

    /**
     * 󰿓 (0xF0FD3)
     */
    public data object BED_DOUBLE_OUTLINE : MaterialDesignGlyphs(0xF0FD3)

    /**
     * 󰿔 (0xF0FD4)
     */
    public data object BED_DOUBLE : MaterialDesignGlyphs(0xF0FD4)

    /**
     * 󰿕 (0xF0FD5)
     */
    public data object MICROSOFT_AZURE_DEVOPS : MaterialDesignGlyphs(0xF0FD5)

    /**
     * 󰿖 (0xF0FD6)
     */
    public data object ARM_FLEX_OUTLINE : MaterialDesignGlyphs(0xF0FD6)

    /**
     * 󰿗 (0xF0FD7)
     */
    public data object ARM_FLEX : MaterialDesignGlyphs(0xF0FD7)

    /**
     * 󰿘 (0xF0FD8)
     */
    public data object PROTOCOL : MaterialDesignGlyphs(0xF0FD8)

    /**
     * 󰿙 (0xF0FD9)
     */
    public data object SEAL_VARIANT : MaterialDesignGlyphs(0xF0FD9)

    /**
     * 󰿚 (0xF0FDA)
     */
    public data object SELECT_PLACE : MaterialDesignGlyphs(0xF0FDA)

    /**
     * 󰿛 (0xF0FDB)
     */
    public data object BED_QUEEN_OUTLINE : MaterialDesignGlyphs(0xF0FDB)

    /**
     * 󰿜 (0xF0FDC)
     */
    public data object SIGN_DIRECTION_PLUS : MaterialDesignGlyphs(0xF0FDC)

    /**
     * 󰿝 (0xF0FDD)
     */
    public data object SIGN_DIRECTION_REMOVE : MaterialDesignGlyphs(0xF0FDD)

    /**
     * 󰿞 (0xF0FDE)
     */
    public data object SILVERWARE_CLEAN : MaterialDesignGlyphs(0xF0FDE)

    /**
     * 󰿟 (0xF0FDF)
     */
    public data object SLASH_FORWARD : MaterialDesignGlyphs(0xF0FDF)

    /**
     * 󰿠 (0xF0FE0)
     */
    public data object SLASH_FORWARD_BOX : MaterialDesignGlyphs(0xF0FE0)

    /**
     * 󰿡 (0xF0FE1)
     */
    public data object SWAP_HORIZONTAL_CIRCLE : MaterialDesignGlyphs(0xF0FE1)

    /**
     * 󰿢 (0xF0FE2)
     */
    public data object SWAP_HORIZONTAL_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0FE2)

    /**
     * 󰿣 (0xF0FE3)
     */
    public data object SWAP_VERTICAL_CIRCLE : MaterialDesignGlyphs(0xF0FE3)

    /**
     * 󰿤 (0xF0FE4)
     */
    public data object SWAP_VERTICAL_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0FE4)

    /**
     * 󰿥 (0xF0FE5)
     */
    public data object TANKER_TRUCK : MaterialDesignGlyphs(0xF0FE5)

    /**
     * 󰿦 (0xF0FE6)
     */
    public data object TEXTURE_BOX : MaterialDesignGlyphs(0xF0FE6)

    /**
     * 󰿧 (0xF0FE7)
     */
    public data object TRAM_SIDE : MaterialDesignGlyphs(0xF0FE7)

    /**
     * 󰿨 (0xF0FE8)
     */
    public data object VECTOR_LINK : MaterialDesignGlyphs(0xF0FE8)

    /**
     * 󰿩 (0xF0FE9)
     */
    public data object NUMERIC_10 : MaterialDesignGlyphs(0xF0FE9)

    /**
     * 󰿪 (0xF0FEA)
     */
    public data object NUMERIC_10_BOX_MULTIPLE : MaterialDesignGlyphs(0xF0FEA)

    /**
     * 󰿫 (0xF0FEB)
     */
    public data object NUMERIC_10_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF0FEB)

    /**
     * 󰿬 (0xF0FEC)
     */
    public data object NUMERIC_10_CIRCLE : MaterialDesignGlyphs(0xF0FEC)

    /**
     * 󰿭 (0xF0FED)
     */
    public data object NUMERIC_10_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF0FED)

    /**
     * 󰿮 (0xF0FEE)
     */
    public data object NUMERIC_9_PLUS : MaterialDesignGlyphs(0xF0FEE)

    /**
     * 󰿯 (0xF0FEF)
     */
    public data object CREDIT_CARD : MaterialDesignGlyphs(0xF0FEF)

    /**
     * 󰿰 (0xF0FF0)
     */
    public data object CREDIT_CARD_MULTIPLE : MaterialDesignGlyphs(0xF0FF0)

    /**
     * 󰿱 (0xF0FF1)
     */
    public data object CREDIT_CARD_OFF : MaterialDesignGlyphs(0xF0FF1)

    /**
     * 󰿲 (0xF0FF2)
     */
    public data object CREDIT_CARD_PLUS : MaterialDesignGlyphs(0xF0FF2)

    /**
     * 󰿳 (0xF0FF3)
     */
    public data object CREDIT_CARD_REFUND : MaterialDesignGlyphs(0xF0FF3)

    /**
     * 󰿴 (0xF0FF4)
     */
    public data object CREDIT_CARD_SCAN : MaterialDesignGlyphs(0xF0FF4)

    /**
     * 󰿵 (0xF0FF5)
     */
    public data object CREDIT_CARD_SETTINGS : MaterialDesignGlyphs(0xF0FF5)

    /**
     * 󰿶 (0xF0FF6)
     */
    public data object HOSPITAL : MaterialDesignGlyphs(0xF0FF6)

    /**
     * 󰿷 (0xF0FF7)
     */
    public data object HOSPITAL_BOX_OUTLINE : MaterialDesignGlyphs(0xF0FF7)

    /**
     * 󰿸 (0xF0FF8)
     */
    public data object OIL_TEMPERATURE : MaterialDesignGlyphs(0xF0FF8)

    /**
     * 󰿹 (0xF0FF9)
     */
    public data object STADIUM : MaterialDesignGlyphs(0xF0FF9)

    /**
     * 󰿺 (0xF0FFA)
     */
    public data object ZIP_BOX_OUTLINE : MaterialDesignGlyphs(0xF0FFA)

    /**
     * 󰿻 (0xF0FFB)
     */
    public data object ACCOUNT_EDIT_OUTLINE : MaterialDesignGlyphs(0xF0FFB)

    /**
     * 󰿼 (0xF0FFC)
     */
    public data object PEANUT : MaterialDesignGlyphs(0xF0FFC)

    /**
     * 󰿽 (0xF0FFD)
     */
    public data object PEANUT_OFF : MaterialDesignGlyphs(0xF0FFD)

    /**
     * 󰿾 (0xF0FFE)
     */
    public data object PEANUT_OUTLINE : MaterialDesignGlyphs(0xF0FFE)

    /**
     * 󰿿 (0xF0FFF)
     */
    public data object PEANUT_OFF_OUTLINE : MaterialDesignGlyphs(0xF0FFF)

    /**
     * 󱀀 (0xF1000)
     */
    public data object SIGN_DIRECTION_MINUS : MaterialDesignGlyphs(0xF1000)

    /**
     * 󱀁 (0xF1001)
     */
    public data object NEWSPAPER_VARIANT : MaterialDesignGlyphs(0xF1001)

    /**
     * 󱀂 (0xF1002)
     */
    public data object NEWSPAPER_VARIANT_MULTIPLE : MaterialDesignGlyphs(0xF1002)

    /**
     * 󱀃 (0xF1003)
     */
    public data object NEWSPAPER_VARIANT_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1003)

    /**
     * 󱀄 (0xF1004)
     */
    public data object NEWSPAPER_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1004)

    /**
     * 󱀅 (0xF1005)
     */
    public data object OVERSCAN : MaterialDesignGlyphs(0xF1005)

    /**
     * 󱀆 (0xF1006)
     */
    public data object PIG_VARIANT : MaterialDesignGlyphs(0xF1006)

    /**
     * 󱀇 (0xF1007)
     */
    public data object PIGGY_BANK : MaterialDesignGlyphs(0xF1007)

    /**
     * 󱀈 (0xF1008)
     */
    public data object POST : MaterialDesignGlyphs(0xF1008)

    /**
     * 󱀉 (0xF1009)
     */
    public data object POST_OUTLINE : MaterialDesignGlyphs(0xF1009)

    /**
     * 󱀊 (0xF100A)
     */
    public data object ACCOUNT_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF100A)

    /**
     * 󱀋 (0xF100B)
     */
    public data object AIRBALLOON_OUTLINE : MaterialDesignGlyphs(0xF100B)

    /**
     * 󱀌 (0xF100C)
     */
    public data object ALPHABETICAL_OFF : MaterialDesignGlyphs(0xF100C)

    /**
     * 󱀍 (0xF100D)
     */
    public data object ALPHABETICAL_VARIANT : MaterialDesignGlyphs(0xF100D)

    /**
     * 󱀎 (0xF100E)
     */
    public data object ALPHABETICAL_VARIANT_OFF : MaterialDesignGlyphs(0xF100E)

    /**
     * 󱀏 (0xF100F)
     */
    public data object APACHE_KAFKA : MaterialDesignGlyphs(0xF100F)

    /**
     * 󱀐 (0xF1010)
     */
    public data object BILLBOARD : MaterialDesignGlyphs(0xF1010)

    /**
     * 󱀑 (0xF1011)
     */
    public data object BLINDS_OPEN : MaterialDesignGlyphs(0xF1011)

    /**
     * 󱀒 (0xF1012)
     */
    public data object BUS_STOP : MaterialDesignGlyphs(0xF1012)

    /**
     * 󱀓 (0xF1013)
     */
    public data object BUS_STOP_COVERED : MaterialDesignGlyphs(0xF1013)

    /**
     * 󱀔 (0xF1014)
     */
    public data object BUS_STOP_UNCOVERED : MaterialDesignGlyphs(0xF1014)

    /**
     * 󱀕 (0xF1015)
     */
    public data object CAR_2_PLUS : MaterialDesignGlyphs(0xF1015)

    /**
     * 󱀖 (0xF1016)
     */
    public data object CAR_3_PLUS : MaterialDesignGlyphs(0xF1016)

    /**
     * 󱀗 (0xF1017)
     */
    public data object CAR_BRAKE_RETARDER : MaterialDesignGlyphs(0xF1017)

    /**
     * 󱀘 (0xF1018)
     */
    public data object CAR_CLUTCH : MaterialDesignGlyphs(0xF1018)

    /**
     * 󱀙 (0xF1019)
     */
    public data object CAR_COOLANT_LEVEL : MaterialDesignGlyphs(0xF1019)

    /**
     * 󱀚 (0xF101A)
     */
    public data object CAR_TURBOCHARGER : MaterialDesignGlyphs(0xF101A)

    /**
     * 󱀛 (0xF101B)
     */
    public data object CAR_WINDSHIELD : MaterialDesignGlyphs(0xF101B)

    /**
     * 󱀜 (0xF101C)
     */
    public data object CAR_WINDSHIELD_OUTLINE : MaterialDesignGlyphs(0xF101C)

    /**
     * 󱀝 (0xF101D)
     */
    public data object CARDS_DIAMOND_OUTLINE : MaterialDesignGlyphs(0xF101D)

    /**
     * 󱀞 (0xF101E)
     */
    public data object CAST_AUDIO : MaterialDesignGlyphs(0xF101E)

    /**
     * 󱀟 (0xF101F)
     */
    public data object CELLPHONE_PLAY : MaterialDesignGlyphs(0xF101F)

    /**
     * 󱀠 (0xF1020)
     */
    public data object COACH_LAMP : MaterialDesignGlyphs(0xF1020)

    /**
     * 󱀡 (0xF1021)
     */
    public data object COMMENT_QUOTE : MaterialDesignGlyphs(0xF1021)

    /**
     * 󱀢 (0xF1022)
     */
    public data object COMMENT_QUOTE_OUTLINE : MaterialDesignGlyphs(0xF1022)

    /**
     * 󱀣 (0xF1023)
     */
    public data object DOMINO_MASK : MaterialDesignGlyphs(0xF1023)

    /**
     * 󱀤 (0xF1024)
     */
    public data object ELECTRON_FRAMEWORK : MaterialDesignGlyphs(0xF1024)

    /**
     * 󱀥 (0xF1025)
     */
    public data object EXCAVATOR : MaterialDesignGlyphs(0xF1025)

    /**
     * 󱀦 (0xF1026)
     */
    public data object EYE_MINUS : MaterialDesignGlyphs(0xF1026)

    /**
     * 󱀧 (0xF1027)
     */
    public data object EYE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1027)

    /**
     * 󱀨 (0xF1028)
     */
    public data object FILE_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF1028)

    /**
     * 󱀩 (0xF1029)
     */
    public data object FILE_CHART_OUTLINE : MaterialDesignGlyphs(0xF1029)

    /**
     * 󱀪 (0xF102A)
     */
    public data object FILE_CLOUD_OUTLINE : MaterialDesignGlyphs(0xF102A)

    /**
     * 󱀫 (0xF102B)
     */
    public data object FILE_CODE_OUTLINE : MaterialDesignGlyphs(0xF102B)

    /**
     * 󱀬 (0xF102C)
     */
    public data object FILE_EXCEL_BOX_OUTLINE : MaterialDesignGlyphs(0xF102C)

    /**
     * 󱀭 (0xF102D)
     */
    public data object FILE_EXCEL_OUTLINE : MaterialDesignGlyphs(0xF102D)

    /**
     * 󱀮 (0xF102E)
     */
    public data object FILE_EXPORT_OUTLINE : MaterialDesignGlyphs(0xF102E)

    /**
     * 󱀯 (0xF102F)
     */
    public data object FILE_IMPORT_OUTLINE : MaterialDesignGlyphs(0xF102F)

    /**
     * 󱀰 (0xF1030)
     */
    public data object FILE_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1030)

    /**
     * 󱀱 (0xF1031)
     */
    public data object FILE_MOVE_OUTLINE : MaterialDesignGlyphs(0xF1031)

    /**
     * 󱀲 (0xF1032)
     */
    public data object FILE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1032)

    /**
     * 󱀳 (0xF1033)
     */
    public data object FILE_PERCENT_OUTLINE : MaterialDesignGlyphs(0xF1033)

    /**
     * 󱀴 (0xF1034)
     */
    public data object FILE_POWERPOINT_BOX_OUTLINE : MaterialDesignGlyphs(0xF1034)

    /**
     * 󱀵 (0xF1035)
     */
    public data object FILE_POWERPOINT_OUTLINE : MaterialDesignGlyphs(0xF1035)

    /**
     * 󱀶 (0xF1036)
     */
    public data object FILE_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF1036)

    /**
     * 󱀷 (0xF1037)
     */
    public data object FILE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1037)

    /**
     * 󱀸 (0xF1038)
     */
    public data object FILE_RESTORE_OUTLINE : MaterialDesignGlyphs(0xF1038)

    /**
     * 󱀹 (0xF1039)
     */
    public data object FILE_SEND_OUTLINE : MaterialDesignGlyphs(0xF1039)

    /**
     * 󱀺 (0xF103A)
     */
    public data object FILE_STAR : MaterialDesignGlyphs(0xF103A)

    /**
     * 󱀻 (0xF103B)
     */
    public data object FILE_STAR_OUTLINE : MaterialDesignGlyphs(0xF103B)

    /**
     * 󱀼 (0xF103C)
     */
    public data object FILE_UNDO_OUTLINE : MaterialDesignGlyphs(0xF103C)

    /**
     * 󱀽 (0xF103D)
     */
    public data object FILE_WORD_BOX_OUTLINE : MaterialDesignGlyphs(0xF103D)

    /**
     * 󱀾 (0xF103E)
     */
    public data object FILE_WORD_OUTLINE : MaterialDesignGlyphs(0xF103E)

    /**
     * 󱀿 (0xF103F)
     */
    public data object FILTER_VARIANT_REMOVE : MaterialDesignGlyphs(0xF103F)

    /**
     * 󱁀 (0xF1040)
     */
    public data object FLOOR_LAMP_DUAL : MaterialDesignGlyphs(0xF1040)

    /**
     * 󱁁 (0xF1041)
     */
    public data object FLOOR_LAMP_TORCHIERE_VARIANT : MaterialDesignGlyphs(0xF1041)

    /**
     * 󱁂 (0xF1042)
     */
    public data object FRUIT_CHERRIES : MaterialDesignGlyphs(0xF1042)

    /**
     * 󱁃 (0xF1043)
     */
    public data object FRUIT_CITRUS : MaterialDesignGlyphs(0xF1043)

    /**
     * 󱁄 (0xF1044)
     */
    public data object FRUIT_GRAPES : MaterialDesignGlyphs(0xF1044)

    /**
     * 󱁅 (0xF1045)
     */
    public data object FRUIT_GRAPES_OUTLINE : MaterialDesignGlyphs(0xF1045)

    /**
     * 󱁆 (0xF1046)
     */
    public data object FRUIT_PINEAPPLE : MaterialDesignGlyphs(0xF1046)

    /**
     * 󱁇 (0xF1047)
     */
    public data object FRUIT_WATERMELON : MaterialDesignGlyphs(0xF1047)

    /**
     * 󱁈 (0xF1048)
     */
    public data object GOOGLE_MY_BUSINESS : MaterialDesignGlyphs(0xF1048)

    /**
     * 󱁉 (0xF1049)
     */
    public data object GRAPH : MaterialDesignGlyphs(0xF1049)

    /**
     * 󱁊 (0xF104A)
     */
    public data object GRAPH_OUTLINE : MaterialDesignGlyphs(0xF104A)

    /**
     * 󱁋 (0xF104B)
     */
    public data object HARDDISK_PLUS : MaterialDesignGlyphs(0xF104B)

    /**
     * 󱁌 (0xF104C)
     */
    public data object HARDDISK_REMOVE : MaterialDesignGlyphs(0xF104C)

    /**
     * 󱁍 (0xF104D)
     */
    public data object HOME_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF104D)

    /**
     * 󱁎 (0xF104E)
     */
    public data object INSTRUMENT_TRIANGLE : MaterialDesignGlyphs(0xF104E)

    /**
     * 󱁏 (0xF104F)
     */
    public data object ISLAND : MaterialDesignGlyphs(0xF104F)

    /**
     * 󱁐 (0xF1050)
     */
    public data object KEYBOARD_SPACE : MaterialDesignGlyphs(0xF1050)

    /**
     * 󱁑 (0xF1051)
     */
    public data object LED_STRIP_VARIANT : MaterialDesignGlyphs(0xF1051)

    /**
     * 󱁒 (0xF1052)
     */
    public data object NUMERIC_NEGATIVE_1 : MaterialDesignGlyphs(0xF1052)

    /**
     * 󱁓 (0xF1053)
     */
    public data object OIL_LEVEL : MaterialDesignGlyphs(0xF1053)

    /**
     * 󱁔 (0xF1054)
     */
    public data object OUTDOOR_LAMP : MaterialDesignGlyphs(0xF1054)

    /**
     * 󱁕 (0xF1055)
     */
    public data object PALM_TREE : MaterialDesignGlyphs(0xF1055)

    /**
     * 󱁖 (0xF1056)
     */
    public data object PARTY_POPPER : MaterialDesignGlyphs(0xF1056)

    /**
     * 󱁗 (0xF1057)
     */
    public data object PRINTER_POS : MaterialDesignGlyphs(0xF1057)

    /**
     * 󱁘 (0xF1058)
     */
    public data object ROBBER : MaterialDesignGlyphs(0xF1058)

    /**
     * 󱁙 (0xF1059)
     */
    public data object ROUTES_CLOCK : MaterialDesignGlyphs(0xF1059)

    /**
     * 󱁚 (0xF105A)
     */
    public data object SCALE_OFF : MaterialDesignGlyphs(0xF105A)

    /**
     * 󱁛 (0xF105B)
     */
    public data object COG_TRANSFER : MaterialDesignGlyphs(0xF105B)

    /**
     * 󱁜 (0xF105C)
     */
    public data object COG_TRANSFER_OUTLINE : MaterialDesignGlyphs(0xF105C)

    /**
     * 󱁝 (0xF105D)
     */
    public data object SHIELD_SUN : MaterialDesignGlyphs(0xF105D)

    /**
     * 󱁞 (0xF105E)
     */
    public data object SHIELD_SUN_OUTLINE : MaterialDesignGlyphs(0xF105E)

    /**
     * 󱁟 (0xF105F)
     */
    public data object SPRINKLER : MaterialDesignGlyphs(0xF105F)

    /**
     * 󱁠 (0xF1060)
     */
    public data object SPRINKLER_VARIANT : MaterialDesignGlyphs(0xF1060)

    /**
     * 󱁡 (0xF1061)
     */
    public data object TABLE_CHAIR : MaterialDesignGlyphs(0xF1061)

    /**
     * 󱁢 (0xF1062)
     */
    public data object TERRAFORM : MaterialDesignGlyphs(0xF1062)

    /**
     * 󱁣 (0xF1063)
     */
    public data object TOASTER : MaterialDesignGlyphs(0xF1063)

    /**
     * 󱁤 (0xF1064)
     */
    public data object TOOLS : MaterialDesignGlyphs(0xF1064)

    /**
     * 󱁥 (0xF1065)
     */
    public data object TRANSFER : MaterialDesignGlyphs(0xF1065)

    /**
     * 󱁦 (0xF1066)
     */
    public data object VALVE : MaterialDesignGlyphs(0xF1066)

    /**
     * 󱁧 (0xF1067)
     */
    public data object VALVE_CLOSED : MaterialDesignGlyphs(0xF1067)

    /**
     * 󱁨 (0xF1068)
     */
    public data object VALVE_OPEN : MaterialDesignGlyphs(0xF1068)

    /**
     * 󱁩 (0xF1069)
     */
    public data object VIDEO_CHECK : MaterialDesignGlyphs(0xF1069)

    /**
     * 󱁪 (0xF106A)
     */
    public data object VIDEO_CHECK_OUTLINE : MaterialDesignGlyphs(0xF106A)

    /**
     * 󱁫 (0xF106B)
     */
    public data object WATER_WELL : MaterialDesignGlyphs(0xF106B)

    /**
     * 󱁬 (0xF106C)
     */
    public data object WATER_WELL_OUTLINE : MaterialDesignGlyphs(0xF106C)

    /**
     * 󱁭 (0xF106D)
     */
    public data object BED_SINGLE : MaterialDesignGlyphs(0xF106D)

    /**
     * 󱁮 (0xF106E)
     */
    public data object BED_SINGLE_OUTLINE : MaterialDesignGlyphs(0xF106E)

    /**
     * 󱁯 (0xF106F)
     */
    public data object BOOK_INFORMATION_VARIANT : MaterialDesignGlyphs(0xF106F)

    /**
     * 󱁰 (0xF1070)
     */
    public data object BOTTLE_SODA : MaterialDesignGlyphs(0xF1070)

    /**
     * 󱁱 (0xF1071)
     */
    public data object BOTTLE_SODA_CLASSIC : MaterialDesignGlyphs(0xF1071)

    /**
     * 󱁲 (0xF1072)
     */
    public data object BOTTLE_SODA_OUTLINE : MaterialDesignGlyphs(0xF1072)

    /**
     * 󱁳 (0xF1073)
     */
    public data object CALENDAR_BLANK_MULTIPLE : MaterialDesignGlyphs(0xF1073)

    /**
     * 󱁴 (0xF1074)
     */
    public data object CARD_SEARCH : MaterialDesignGlyphs(0xF1074)

    /**
     * 󱁵 (0xF1075)
     */
    public data object CARD_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1075)

    /**
     * 󱁶 (0xF1076)
     */
    public data object FACE_WOMAN_PROFILE : MaterialDesignGlyphs(0xF1076)

    /**
     * 󱁷 (0xF1077)
     */
    public data object FACE_WOMAN : MaterialDesignGlyphs(0xF1077)

    /**
     * 󱁸 (0xF1078)
     */
    public data object FACE_WOMAN_OUTLINE : MaterialDesignGlyphs(0xF1078)

    /**
     * 󱁹 (0xF1079)
     */
    public data object FILE_SETTINGS : MaterialDesignGlyphs(0xF1079)

    /**
     * 󱁺 (0xF107A)
     */
    public data object FILE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF107A)

    /**
     * 󱁻 (0xF107B)
     */
    public data object FILE_COG : MaterialDesignGlyphs(0xF107B)

    /**
     * 󱁼 (0xF107C)
     */
    public data object FILE_COG_OUTLINE : MaterialDesignGlyphs(0xF107C)

    /**
     * 󱁽 (0xF107D)
     */
    public data object FOLDER_SETTINGS : MaterialDesignGlyphs(0xF107D)

    /**
     * 󱁾 (0xF107E)
     */
    public data object FOLDER_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF107E)

    /**
     * 󱁿 (0xF107F)
     */
    public data object FOLDER_COG : MaterialDesignGlyphs(0xF107F)

    /**
     * 󱂀 (0xF1080)
     */
    public data object FOLDER_COG_OUTLINE : MaterialDesignGlyphs(0xF1080)

    /**
     * 󱂁 (0xF1081)
     */
    public data object FURIGANA_HORIZONTAL : MaterialDesignGlyphs(0xF1081)

    /**
     * 󱂂 (0xF1082)
     */
    public data object FURIGANA_VERTICAL : MaterialDesignGlyphs(0xF1082)

    /**
     * 󱂃 (0xF1083)
     */
    public data object GOLF_TEE : MaterialDesignGlyphs(0xF1083)

    /**
     * 󱂄 (0xF1084)
     */
    public data object LUNGS : MaterialDesignGlyphs(0xF1084)

    /**
     * 󱂅 (0xF1085)
     */
    public data object MATH_LOG : MaterialDesignGlyphs(0xF1085)

    /**
     * 󱂆 (0xF1086)
     */
    public data object MOPED : MaterialDesignGlyphs(0xF1086)

    /**
     * 󱂇 (0xF1087)
     */
    public data object ROUTER_NETWORK : MaterialDesignGlyphs(0xF1087)

    /**
     * 󱂉 (0xF1089)
     */
    public data object ROMAN_NUMERAL_2 : MaterialDesignGlyphs(0xF1089)

    /**
     * 󱂊 (0xF108A)
     */
    public data object ROMAN_NUMERAL_3 : MaterialDesignGlyphs(0xF108A)

    /**
     * 󱂋 (0xF108B)
     */
    public data object ROMAN_NUMERAL_4 : MaterialDesignGlyphs(0xF108B)

    /**
     * 󱂍 (0xF108D)
     */
    public data object ROMAN_NUMERAL_6 : MaterialDesignGlyphs(0xF108D)

    /**
     * 󱂎 (0xF108E)
     */
    public data object ROMAN_NUMERAL_7 : MaterialDesignGlyphs(0xF108E)

    /**
     * 󱂏 (0xF108F)
     */
    public data object ROMAN_NUMERAL_8 : MaterialDesignGlyphs(0xF108F)

    /**
     * 󱂐 (0xF1090)
     */
    public data object ROMAN_NUMERAL_9 : MaterialDesignGlyphs(0xF1090)

    /**
     * 󱂒 (0xF1092)
     */
    public data object SOLDERING_IRON : MaterialDesignGlyphs(0xF1092)

    /**
     * 󱂓 (0xF1093)
     */
    public data object STOMACH : MaterialDesignGlyphs(0xF1093)

    /**
     * 󱂔 (0xF1094)
     */
    public data object TABLE_EYE : MaterialDesignGlyphs(0xF1094)

    /**
     * 󱂕 (0xF1095)
     */
    public data object FORM_TEXTAREA : MaterialDesignGlyphs(0xF1095)

    /**
     * 󱂖 (0xF1096)
     */
    public data object TRUMPET : MaterialDesignGlyphs(0xF1096)

    /**
     * 󱂗 (0xF1097)
     */
    public data object ACCOUNT_CASH : MaterialDesignGlyphs(0xF1097)

    /**
     * 󱂘 (0xF1098)
     */
    public data object ACCOUNT_CASH_OUTLINE : MaterialDesignGlyphs(0xF1098)

    /**
     * 󱂙 (0xF1099)
     */
    public data object AIR_HUMIDIFIER : MaterialDesignGlyphs(0xF1099)

    /**
     * 󱂚 (0xF109A)
     */
    public data object ANSIBLE : MaterialDesignGlyphs(0xF109A)

    /**
     * 󱂛 (0xF109B)
     */
    public data object API : MaterialDesignGlyphs(0xF109B)

    /**
     * 󱂜 (0xF109C)
     */
    public data object BICYCLE : MaterialDesignGlyphs(0xF109C)

    /**
     * 󱂝 (0xF109D)
     */
    public data object CAR_DOOR_LOCK : MaterialDesignGlyphs(0xF109D)

    /**
     * 󱂞 (0xF109E)
     */
    public data object COAT_RACK : MaterialDesignGlyphs(0xF109E)

    /**
     * 󱂟 (0xF109F)
     */
    public data object COFFEE_MAKER : MaterialDesignGlyphs(0xF109F)

    /**
     * 󱂠 (0xF10A0)
     */
    public data object WEB_MINUS : MaterialDesignGlyphs(0xF10A0)

    /**
     * 󱂡 (0xF10A1)
     */
    public data object DECIMAL : MaterialDesignGlyphs(0xF10A1)

    /**
     * 󱂢 (0xF10A2)
     */
    public data object DECIMAL_COMMA : MaterialDesignGlyphs(0xF10A2)

    /**
     * 󱂣 (0xF10A3)
     */
    public data object DECIMAL_COMMA_DECREASE : MaterialDesignGlyphs(0xF10A3)

    /**
     * 󱂤 (0xF10A4)
     */
    public data object DECIMAL_COMMA_INCREASE : MaterialDesignGlyphs(0xF10A4)

    /**
     * 󱂥 (0xF10A5)
     */
    public data object DELETE_ALERT : MaterialDesignGlyphs(0xF10A5)

    /**
     * 󱂦 (0xF10A6)
     */
    public data object DELETE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF10A6)

    /**
     * 󱂧 (0xF10A7)
     */
    public data object DELETE_OFF : MaterialDesignGlyphs(0xF10A7)

    /**
     * 󱂨 (0xF10A8)
     */
    public data object DELETE_OFF_OUTLINE : MaterialDesignGlyphs(0xF10A8)

    /**
     * 󱂩 (0xF10A9)
     */
    public data object DOCK_BOTTOM : MaterialDesignGlyphs(0xF10A9)

    /**
     * 󱂪 (0xF10AA)
     */
    public data object DOCK_LEFT : MaterialDesignGlyphs(0xF10AA)

    /**
     * 󱂫 (0xF10AB)
     */
    public data object DOCK_RIGHT : MaterialDesignGlyphs(0xF10AB)

    /**
     * 󱂬 (0xF10AC)
     */
    public data object DOCK_WINDOW : MaterialDesignGlyphs(0xF10AC)

    /**
     * 󱂭 (0xF10AD)
     */
    public data object DOMAIN_PLUS : MaterialDesignGlyphs(0xF10AD)

    /**
     * 󱂮 (0xF10AE)
     */
    public data object DOMAIN_REMOVE : MaterialDesignGlyphs(0xF10AE)

    /**
     * 󱂯 (0xF10AF)
     */
    public data object DOOR_CLOSED_LOCK : MaterialDesignGlyphs(0xF10AF)

    /**
     * 󱂰 (0xF10B0)
     */
    public data object DOWNLOAD_OFF : MaterialDesignGlyphs(0xF10B0)

    /**
     * 󱂱 (0xF10B1)
     */
    public data object DOWNLOAD_OFF_OUTLINE : MaterialDesignGlyphs(0xF10B1)

    /**
     * 󱂲 (0xF10B2)
     */
    public data object FLAG_MINUS_OUTLINE : MaterialDesignGlyphs(0xF10B2)

    /**
     * 󱂳 (0xF10B3)
     */
    public data object FLAG_PLUS_OUTLINE : MaterialDesignGlyphs(0xF10B3)

    /**
     * 󱂴 (0xF10B4)
     */
    public data object FLAG_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF10B4)

    /**
     * 󱂵 (0xF10B5)
     */
    public data object FOLDER_HOME : MaterialDesignGlyphs(0xF10B5)

    /**
     * 󱂶 (0xF10B6)
     */
    public data object FOLDER_HOME_OUTLINE : MaterialDesignGlyphs(0xF10B6)

    /**
     * 󱂷 (0xF10B7)
     */
    public data object FOLDER_INFORMATION : MaterialDesignGlyphs(0xF10B7)

    /**
     * 󱂸 (0xF10B8)
     */
    public data object FOLDER_INFORMATION_OUTLINE : MaterialDesignGlyphs(0xF10B8)

    /**
     * 󱂹 (0xF10B9)
     */
    public data object IV_BAG : MaterialDesignGlyphs(0xF10B9)

    /**
     * 󱂺 (0xF10BA)
     */
    public data object LINK_LOCK : MaterialDesignGlyphs(0xF10BA)

    /**
     * 󱂻 (0xF10BB)
     */
    public data object MESSAGE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF10BB)

    /**
     * 󱂼 (0xF10BC)
     */
    public data object PHONE_CANCEL : MaterialDesignGlyphs(0xF10BC)

    /**
     * 󱂽 (0xF10BD)
     */
    public data object SMART_CARD : MaterialDesignGlyphs(0xF10BD)

    /**
     * 󱂾 (0xF10BE)
     */
    public data object SMART_CARD_OUTLINE : MaterialDesignGlyphs(0xF10BE)

    /**
     * 󱂿 (0xF10BF)
     */
    public data object SMART_CARD_READER : MaterialDesignGlyphs(0xF10BF)

    /**
     * 󱃀 (0xF10C0)
     */
    public data object SMART_CARD_READER_OUTLINE : MaterialDesignGlyphs(0xF10C0)

    /**
     * 󱃁 (0xF10C1)
     */
    public data object STOREFRONT_OUTLINE : MaterialDesignGlyphs(0xF10C1)

    /**
     * 󱃂 (0xF10C2)
     */
    public data object THERMOMETER_HIGH : MaterialDesignGlyphs(0xF10C2)

    /**
     * 󱃃 (0xF10C3)
     */
    public data object THERMOMETER_LOW : MaterialDesignGlyphs(0xF10C3)

    /**
     * 󱃄 (0xF10C4)
     */
    public data object UFO : MaterialDesignGlyphs(0xF10C4)

    /**
     * 󱃅 (0xF10C5)
     */
    public data object UFO_OUTLINE : MaterialDesignGlyphs(0xF10C5)

    /**
     * 󱃆 (0xF10C6)
     */
    public data object UPLOAD_OFF : MaterialDesignGlyphs(0xF10C6)

    /**
     * 󱃇 (0xF10C7)
     */
    public data object UPLOAD_OFF_OUTLINE : MaterialDesignGlyphs(0xF10C7)

    /**
     * 󱃈 (0xF10C8)
     */
    public data object ACCOUNT_CHILD_OUTLINE : MaterialDesignGlyphs(0xF10C8)

    /**
     * 󱃉 (0xF10C9)
     */
    public data object ACCOUNT_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF10C9)

    /**
     * 󱃊 (0xF10CA)
     */
    public data object ACCOUNT_TIE_OUTLINE : MaterialDesignGlyphs(0xF10CA)

    /**
     * 󱃋 (0xF10CB)
     */
    public data object ALIEN_OUTLINE : MaterialDesignGlyphs(0xF10CB)

    /**
     * 󱃌 (0xF10CC)
     */
    public data object BATTERY_ALERT_VARIANT : MaterialDesignGlyphs(0xF10CC)

    /**
     * 󱃍 (0xF10CD)
     */
    public data object BATTERY_ALERT_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF10CD)

    /**
     * 󱃎 (0xF10CE)
     */
    public data object BEEHIVE_OUTLINE : MaterialDesignGlyphs(0xF10CE)

    /**
     * 󱃏 (0xF10CF)
     */
    public data object BOOMERANG : MaterialDesignGlyphs(0xF10CF)

    /**
     * 󱃐 (0xF10D0)
     */
    public data object BRIEFCASE_CLOCK : MaterialDesignGlyphs(0xF10D0)

    /**
     * 󱃑 (0xF10D1)
     */
    public data object BRIEFCASE_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF10D1)

    /**
     * 󱃒 (0xF10D2)
     */
    public data object CELLPHONE_MESSAGE_OFF : MaterialDesignGlyphs(0xF10D2)

    /**
     * 󱃓 (0xF10D3)
     */
    public data object CIRCLE_OFF_OUTLINE : MaterialDesignGlyphs(0xF10D3)

    /**
     * 󱃔 (0xF10D4)
     */
    public data object CLIPBOARD_LIST : MaterialDesignGlyphs(0xF10D4)

    /**
     * 󱃕 (0xF10D5)
     */
    public data object CLIPBOARD_LIST_OUTLINE : MaterialDesignGlyphs(0xF10D5)

    /**
     * 󱃖 (0xF10D6)
     */
    public data object CODE_BRACES_BOX : MaterialDesignGlyphs(0xF10D6)

    /**
     * 󱃗 (0xF10D7)
     */
    public data object CODE_PARENTHESES_BOX : MaterialDesignGlyphs(0xF10D7)

    /**
     * 󱃘 (0xF10D8)
     */
    public data object CONSOLIDATE : MaterialDesignGlyphs(0xF10D8)

    /**
     * 󱃙 (0xF10D9)
     */
    public data object ELECTRIC_SWITCH_CLOSED : MaterialDesignGlyphs(0xF10D9)

    /**
     * 󱃚 (0xF10DA)
     */
    public data object EMAIL_RECEIVE : MaterialDesignGlyphs(0xF10DA)

    /**
     * 󱃛 (0xF10DB)
     */
    public data object EMAIL_RECEIVE_OUTLINE : MaterialDesignGlyphs(0xF10DB)

    /**
     * 󱃜 (0xF10DC)
     */
    public data object EMAIL_SEND : MaterialDesignGlyphs(0xF10DC)

    /**
     * 󱃝 (0xF10DD)
     */
    public data object EMAIL_SEND_OUTLINE : MaterialDesignGlyphs(0xF10DD)

    /**
     * 󱃞 (0xF10DE)
     */
    public data object EMOTICON_CONFUSED : MaterialDesignGlyphs(0xF10DE)

    /**
     * 󱃟 (0xF10DF)
     */
    public data object EMOTICON_CONFUSED_OUTLINE : MaterialDesignGlyphs(0xF10DF)

    /**
     * 󱃠 (0xF10E0)
     */
    public data object EPSILON : MaterialDesignGlyphs(0xF10E0)

    /**
     * 󱃡 (0xF10E1)
     */
    public data object FILE_TABLE_BOX : MaterialDesignGlyphs(0xF10E1)

    /**
     * 󱃢 (0xF10E2)
     */
    public data object FILE_TABLE_BOX_MULTIPLE : MaterialDesignGlyphs(0xF10E2)

    /**
     * 󱃣 (0xF10E3)
     */
    public data object FILE_TABLE_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF10E3)

    /**
     * 󱃤 (0xF10E4)
     */
    public data object FILE_TABLE_BOX_OUTLINE : MaterialDesignGlyphs(0xF10E4)

    /**
     * 󱃥 (0xF10E5)
     */
    public data object FILTER_MENU : MaterialDesignGlyphs(0xF10E5)

    /**
     * 󱃦 (0xF10E6)
     */
    public data object FILTER_MENU_OUTLINE : MaterialDesignGlyphs(0xF10E6)

    /**
     * 󱃧 (0xF10E7)
     */
    public data object FLIP_HORIZONTAL : MaterialDesignGlyphs(0xF10E7)

    /**
     * 󱃨 (0xF10E8)
     */
    public data object FLIP_VERTICAL : MaterialDesignGlyphs(0xF10E8)

    /**
     * 󱃩 (0xF10E9)
     */
    public data object FOLDER_DOWNLOAD_OUTLINE : MaterialDesignGlyphs(0xF10E9)

    /**
     * 󱃪 (0xF10EA)
     */
    public data object FOLDER_HEART : MaterialDesignGlyphs(0xF10EA)

    /**
     * 󱃫 (0xF10EB)
     */
    public data object FOLDER_HEART_OUTLINE : MaterialDesignGlyphs(0xF10EB)

    /**
     * 󱃬 (0xF10EC)
     */
    public data object FOLDER_KEY_OUTLINE : MaterialDesignGlyphs(0xF10EC)

    /**
     * 󱃭 (0xF10ED)
     */
    public data object FOLDER_UPLOAD_OUTLINE : MaterialDesignGlyphs(0xF10ED)

    /**
     * 󱃮 (0xF10EE)
     */
    public data object GAMMA : MaterialDesignGlyphs(0xF10EE)

    /**
     * 󱃯 (0xF10EF)
     */
    public data object HAIR_DRYER : MaterialDesignGlyphs(0xF10EF)

    /**
     * 󱃰 (0xF10F0)
     */
    public data object HAIR_DRYER_OUTLINE : MaterialDesignGlyphs(0xF10F0)

    /**
     * 󱃱 (0xF10F1)
     */
    public data object HAND_HEART : MaterialDesignGlyphs(0xF10F1)

    /**
     * 󱃲 (0xF10F2)
     */
    public data object HEXAGON_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF10F2)

    /**
     * 󱃳 (0xF10F3)
     */
    public data object HORIZONTAL_ROTATE_CLOCKWISE : MaterialDesignGlyphs(0xF10F3)

    /**
     * 󱃴 (0xF10F4)
     */
    public data object HORIZONTAL_ROTATE_COUNTERCLOCKWISE : MaterialDesignGlyphs(0xF10F4)

    /**
     * 󱃵 (0xF10F5)
     */
    public data object APPLICATION_ARRAY : MaterialDesignGlyphs(0xF10F5)

    /**
     * 󱃶 (0xF10F6)
     */
    public data object APPLICATION_ARRAY_OUTLINE : MaterialDesignGlyphs(0xF10F6)

    /**
     * 󱃷 (0xF10F7)
     */
    public data object APPLICATION_BRACES : MaterialDesignGlyphs(0xF10F7)

    /**
     * 󱃸 (0xF10F8)
     */
    public data object APPLICATION_BRACES_OUTLINE : MaterialDesignGlyphs(0xF10F8)

    /**
     * 󱃹 (0xF10F9)
     */
    public data object APPLICATION_PARENTHESES : MaterialDesignGlyphs(0xF10F9)

    /**
     * 󱃺 (0xF10FA)
     */
    public data object APPLICATION_PARENTHESES_OUTLINE : MaterialDesignGlyphs(0xF10FA)

    /**
     * 󱃻 (0xF10FB)
     */
    public data object APPLICATION_VARIABLE : MaterialDesignGlyphs(0xF10FB)

    /**
     * 󱃼 (0xF10FC)
     */
    public data object APPLICATION_VARIABLE_OUTLINE : MaterialDesignGlyphs(0xF10FC)

    /**
     * 󱃽 (0xF10FD)
     */
    public data object KHANDA : MaterialDesignGlyphs(0xF10FD)

    /**
     * 󱃾 (0xF10FE)
     */
    public data object KUBERNETES : MaterialDesignGlyphs(0xF10FE)

    /**
     * 󱃿 (0xF10FF)
     */
    public data object LINK_VARIANT_MINUS : MaterialDesignGlyphs(0xF10FF)

    /**
     * 󱄀 (0xF1100)
     */
    public data object LINK_VARIANT_PLUS : MaterialDesignGlyphs(0xF1100)

    /**
     * 󱄁 (0xF1101)
     */
    public data object LINK_VARIANT_REMOVE : MaterialDesignGlyphs(0xF1101)

    /**
     * 󱄂 (0xF1102)
     */
    public data object MAP_MARKER_DOWN : MaterialDesignGlyphs(0xF1102)

    /**
     * 󱄃 (0xF1103)
     */
    public data object MAP_MARKER_UP : MaterialDesignGlyphs(0xF1103)

    /**
     * 󱄄 (0xF1104)
     */
    public data object MONITOR_SHIMMER : MaterialDesignGlyphs(0xF1104)

    /**
     * 󱄅 (0xF1105)
     */
    public data object NIX : MaterialDesignGlyphs(0xF1105)

    /**
     * 󱄆 (0xF1106)
     */
    public data object NUXT : MaterialDesignGlyphs(0xF1106)

    /**
     * 󱄇 (0xF1107)
     */
    public data object POWER_SOCKET_DE : MaterialDesignGlyphs(0xF1107)

    /**
     * 󱄈 (0xF1108)
     */
    public data object POWER_SOCKET_FR : MaterialDesignGlyphs(0xF1108)

    /**
     * 󱄉 (0xF1109)
     */
    public data object POWER_SOCKET_JP : MaterialDesignGlyphs(0xF1109)

    /**
     * 󱄊 (0xF110A)
     */
    public data object PROGRESS_CLOSE : MaterialDesignGlyphs(0xF110A)

    /**
     * 󱄋 (0xF110B)
     */
    public data object RELOAD_ALERT : MaterialDesignGlyphs(0xF110B)

    /**
     * 󱄌 (0xF110C)
     */
    public data object RESTART_ALERT : MaterialDesignGlyphs(0xF110C)

    /**
     * 󱄍 (0xF110D)
     */
    public data object RESTORE_ALERT : MaterialDesignGlyphs(0xF110D)

    /**
     * 󱄎 (0xF110E)
     */
    public data object SHAKER : MaterialDesignGlyphs(0xF110E)

    /**
     * 󱄏 (0xF110F)
     */
    public data object SHAKER_OUTLINE : MaterialDesignGlyphs(0xF110F)

    /**
     * 󱄐 (0xF1110)
     */
    public data object TELEVISION_SHIMMER : MaterialDesignGlyphs(0xF1110)

    /**
     * 󱄑 (0xF1111)
     */
    public data object VARIABLE_BOX : MaterialDesignGlyphs(0xF1111)

    /**
     * 󱄒 (0xF1112)
     */
    public data object FILTER_VARIANT_MINUS : MaterialDesignGlyphs(0xF1112)

    /**
     * 󱄓 (0xF1113)
     */
    public data object FILTER_VARIANT_PLUS : MaterialDesignGlyphs(0xF1113)

    /**
     * 󱄔 (0xF1114)
     */
    public data object SLOT_MACHINE : MaterialDesignGlyphs(0xF1114)

    /**
     * 󱄕 (0xF1115)
     */
    public data object SLOT_MACHINE_OUTLINE : MaterialDesignGlyphs(0xF1115)

    /**
     * 󱄖 (0xF1116)
     */
    public data object GLASS_MUG_VARIANT : MaterialDesignGlyphs(0xF1116)

    /**
     * 󱄗 (0xF1117)
     */
    public data object CLIPBOARD_FLOW_OUTLINE : MaterialDesignGlyphs(0xF1117)

    /**
     * 󱄘 (0xF1118)
     */
    public data object SIGN_REAL_ESTATE : MaterialDesignGlyphs(0xF1118)

    /**
     * 󱄙 (0xF1119)
     */
    public data object ANTENNA : MaterialDesignGlyphs(0xF1119)

    /**
     * 󱄚 (0xF111A)
     */
    public data object CENTOS : MaterialDesignGlyphs(0xF111A)

    /**
     * 󱄛 (0xF111B)
     */
    public data object REDHAT : MaterialDesignGlyphs(0xF111B)

    /**
     * 󱄜 (0xF111C)
     */
    public data object WINDOW_SHUTTER : MaterialDesignGlyphs(0xF111C)

    /**
     * 󱄝 (0xF111D)
     */
    public data object WINDOW_SHUTTER_ALERT : MaterialDesignGlyphs(0xF111D)

    /**
     * 󱄞 (0xF111E)
     */
    public data object WINDOW_SHUTTER_OPEN : MaterialDesignGlyphs(0xF111E)

    /**
     * 󱄟 (0xF111F)
     */
    public data object BIKE_FAST : MaterialDesignGlyphs(0xF111F)

    /**
     * 󱄠 (0xF1120)
     */
    public data object VOLUME_SOURCE : MaterialDesignGlyphs(0xF1120)

    /**
     * 󱄡 (0xF1121)
     */
    public data object VOLUME_VIBRATE : MaterialDesignGlyphs(0xF1121)

    /**
     * 󱄢 (0xF1122)
     */
    public data object MOVIE_EDIT : MaterialDesignGlyphs(0xF1122)

    /**
     * 󱄣 (0xF1123)
     */
    public data object MOVIE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF1123)

    /**
     * 󱄤 (0xF1124)
     */
    public data object MOVIE_FILTER : MaterialDesignGlyphs(0xF1124)

    /**
     * 󱄥 (0xF1125)
     */
    public data object MOVIE_FILTER_OUTLINE : MaterialDesignGlyphs(0xF1125)

    /**
     * 󱄦 (0xF1126)
     */
    public data object DIABETES : MaterialDesignGlyphs(0xF1126)

    /**
     * 󱄧 (0xF1127)
     */
    public data object CURSOR_DEFAULT_GESTURE : MaterialDesignGlyphs(0xF1127)

    /**
     * 󱄨 (0xF1128)
     */
    public data object CURSOR_DEFAULT_GESTURE_OUTLINE : MaterialDesignGlyphs(0xF1128)

    /**
     * 󱄩 (0xF1129)
     */
    public data object TOOTHBRUSH : MaterialDesignGlyphs(0xF1129)

    /**
     * 󱄪 (0xF112A)
     */
    public data object TOOTHBRUSH_PASTE : MaterialDesignGlyphs(0xF112A)

    /**
     * 󱄫 (0xF112B)
     */
    public data object HOME_ROOF : MaterialDesignGlyphs(0xF112B)

    /**
     * 󱄬 (0xF112C)
     */
    public data object TOOTHBRUSH_ELECTRIC : MaterialDesignGlyphs(0xF112C)

    /**
     * 󱄭 (0xF112D)
     */
    public data object ACCOUNT_SUPERVISOR_OUTLINE : MaterialDesignGlyphs(0xF112D)

    /**
     * 󱄮 (0xF112E)
     */
    public data object BOTTLE_TONIC : MaterialDesignGlyphs(0xF112E)

    /**
     * 󱄯 (0xF112F)
     */
    public data object BOTTLE_TONIC_OUTLINE : MaterialDesignGlyphs(0xF112F)

    /**
     * 󱄰 (0xF1130)
     */
    public data object BOTTLE_TONIC_PLUS : MaterialDesignGlyphs(0xF1130)

    /**
     * 󱄱 (0xF1131)
     */
    public data object BOTTLE_TONIC_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1131)

    /**
     * 󱄲 (0xF1132)
     */
    public data object BOTTLE_TONIC_SKULL : MaterialDesignGlyphs(0xF1132)

    /**
     * 󱄳 (0xF1133)
     */
    public data object BOTTLE_TONIC_SKULL_OUTLINE : MaterialDesignGlyphs(0xF1133)

    /**
     * 󱄴 (0xF1134)
     */
    public data object CALENDAR_ARROW_LEFT : MaterialDesignGlyphs(0xF1134)

    /**
     * 󱄵 (0xF1135)
     */
    public data object CALENDAR_ARROW_RIGHT : MaterialDesignGlyphs(0xF1135)

    /**
     * 󱄶 (0xF1136)
     */
    public data object CROSSHAIRS_QUESTION : MaterialDesignGlyphs(0xF1136)

    /**
     * 󱄷 (0xF1137)
     */
    public data object FIRE_HYDRANT : MaterialDesignGlyphs(0xF1137)

    /**
     * 󱄸 (0xF1138)
     */
    public data object FIRE_HYDRANT_ALERT : MaterialDesignGlyphs(0xF1138)

    /**
     * 󱄹 (0xF1139)
     */
    public data object FIRE_HYDRANT_OFF : MaterialDesignGlyphs(0xF1139)

    /**
     * 󱄺 (0xF113A)
     */
    public data object OCR : MaterialDesignGlyphs(0xF113A)

    /**
     * 󱄻 (0xF113B)
     */
    public data object SHIELD_STAR : MaterialDesignGlyphs(0xF113B)

    /**
     * 󱄼 (0xF113C)
     */
    public data object SHIELD_STAR_OUTLINE : MaterialDesignGlyphs(0xF113C)

    /**
     * 󱄽 (0xF113D)
     */
    public data object TEXT_RECOGNITION : MaterialDesignGlyphs(0xF113D)

    /**
     * 󱄾 (0xF113E)
     */
    public data object HANDCUFFS : MaterialDesignGlyphs(0xF113E)

    /**
     * 󱄿 (0xF113F)
     */
    public data object GENDER_MALE_FEMALE_VARIANT : MaterialDesignGlyphs(0xF113F)

    /**
     * 󱅀 (0xF1140)
     */
    public data object GENDER_NON_BINARY : MaterialDesignGlyphs(0xF1140)

    /**
     * 󱅁 (0xF1141)
     */
    public data object MINUS_BOX_MULTIPLE : MaterialDesignGlyphs(0xF1141)

    /**
     * 󱅂 (0xF1142)
     */
    public data object MINUS_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1142)

    /**
     * 󱅃 (0xF1143)
     */
    public data object PLUS_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1143)

    /**
     * 󱅄 (0xF1144)
     */
    public data object PENCIL_BOX_MULTIPLE : MaterialDesignGlyphs(0xF1144)

    /**
     * 󱅅 (0xF1145)
     */
    public data object PENCIL_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1145)

    /**
     * 󱅆 (0xF1146)
     */
    public data object PRINTER_CHECK : MaterialDesignGlyphs(0xF1146)

    /**
     * 󱅇 (0xF1147)
     */
    public data object SORT_VARIANT_REMOVE : MaterialDesignGlyphs(0xF1147)

    /**
     * 󱅈 (0xF1148)
     */
    public data object SORT_ALPHABETICAL_ASCENDING_VARIANT : MaterialDesignGlyphs(0xF1148)

    /**
     * 󱅉 (0xF1149)
     */
    public data object SORT_ALPHABETICAL_DESCENDING_VARIANT : MaterialDesignGlyphs(0xF1149)

    /**
     * 󱅊 (0xF114A)
     */
    public data object DICE_1_OUTLINE : MaterialDesignGlyphs(0xF114A)

    /**
     * 󱅋 (0xF114B)
     */
    public data object DICE_2_OUTLINE : MaterialDesignGlyphs(0xF114B)

    /**
     * 󱅌 (0xF114C)
     */
    public data object DICE_3_OUTLINE : MaterialDesignGlyphs(0xF114C)

    /**
     * 󱅍 (0xF114D)
     */
    public data object DICE_4_OUTLINE : MaterialDesignGlyphs(0xF114D)

    /**
     * 󱅎 (0xF114E)
     */
    public data object DICE_5_OUTLINE : MaterialDesignGlyphs(0xF114E)

    /**
     * 󱅏 (0xF114F)
     */
    public data object DICE_6_OUTLINE : MaterialDesignGlyphs(0xF114F)

    /**
     * 󱅐 (0xF1150)
     */
    public data object DICE_D4 : MaterialDesignGlyphs(0xF1150)

    /**
     * 󱅑 (0xF1151)
     */
    public data object DICE_D6 : MaterialDesignGlyphs(0xF1151)

    /**
     * 󱅒 (0xF1152)
     */
    public data object DICE_D8 : MaterialDesignGlyphs(0xF1152)

    /**
     * 󱅓 (0xF1153)
     */
    public data object DICE_D10 : MaterialDesignGlyphs(0xF1153)

    /**
     * 󱅔 (0xF1154)
     */
    public data object DICE_D12 : MaterialDesignGlyphs(0xF1154)

    /**
     * 󱅕 (0xF1155)
     */
    public data object DICE_D20 : MaterialDesignGlyphs(0xF1155)

    /**
     * 󱅖 (0xF1156)
     */
    public data object DICE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1156)

    /**
     * 󱅗 (0xF1157)
     */
    public data object PAPER_ROLL : MaterialDesignGlyphs(0xF1157)

    /**
     * 󱅘 (0xF1158)
     */
    public data object PAPER_ROLL_OUTLINE : MaterialDesignGlyphs(0xF1158)

    /**
     * 󱅙 (0xF1159)
     */
    public data object HOME_EDIT : MaterialDesignGlyphs(0xF1159)

    /**
     * 󱅚 (0xF115A)
     */
    public data object HOME_EDIT_OUTLINE : MaterialDesignGlyphs(0xF115A)

    /**
     * 󱅛 (0xF115B)
     */
    public data object ARROW_HORIZONTAL_LOCK : MaterialDesignGlyphs(0xF115B)

    /**
     * 󱅜 (0xF115C)
     */
    public data object ARROW_VERTICAL_LOCK : MaterialDesignGlyphs(0xF115C)

    /**
     * 󱅝 (0xF115D)
     */
    public data object WEIGHT_LIFTER : MaterialDesignGlyphs(0xF115D)

    /**
     * 󱅞 (0xF115E)
     */
    public data object ACCOUNT_LOCK : MaterialDesignGlyphs(0xF115E)

    /**
     * 󱅟 (0xF115F)
     */
    public data object ACCOUNT_LOCK_OUTLINE : MaterialDesignGlyphs(0xF115F)

    /**
     * 󱅠 (0xF1160)
     */
    public data object PASTA : MaterialDesignGlyphs(0xF1160)

    /**
     * 󱅡 (0xF1161)
     */
    public data object SEND_CHECK : MaterialDesignGlyphs(0xF1161)

    /**
     * 󱅢 (0xF1162)
     */
    public data object SEND_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1162)

    /**
     * 󱅣 (0xF1163)
     */
    public data object SEND_CLOCK : MaterialDesignGlyphs(0xF1163)

    /**
     * 󱅤 (0xF1164)
     */
    public data object SEND_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF1164)

    /**
     * 󱅥 (0xF1165)
     */
    public data object SEND_OUTLINE : MaterialDesignGlyphs(0xF1165)

    /**
     * 󱅦 (0xF1166)
     */
    public data object SEND_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1166)

    /**
     * 󱅧 (0xF1167)
     */
    public data object POLICE_BADGE : MaterialDesignGlyphs(0xF1167)

    /**
     * 󱅨 (0xF1168)
     */
    public data object POLICE_BADGE_OUTLINE : MaterialDesignGlyphs(0xF1168)

    /**
     * 󱅩 (0xF1169)
     */
    public data object GATE_ARROW_RIGHT : MaterialDesignGlyphs(0xF1169)

    /**
     * 󱅪 (0xF116A)
     */
    public data object GATE_OPEN : MaterialDesignGlyphs(0xF116A)

    /**
     * 󱅫 (0xF116B)
     */
    public data object BELL_BADGE : MaterialDesignGlyphs(0xF116B)

    /**
     * 󱅬 (0xF116C)
     */
    public data object MESSAGE_IMAGE_OUTLINE : MaterialDesignGlyphs(0xF116C)

    /**
     * 󱅭 (0xF116D)
     */
    public data object MESSAGE_LOCK_OUTLINE : MaterialDesignGlyphs(0xF116D)

    /**
     * 󱅮 (0xF116E)
     */
    public data object MESSAGE_MINUS : MaterialDesignGlyphs(0xF116E)

    /**
     * 󱅯 (0xF116F)
     */
    public data object MESSAGE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF116F)

    /**
     * 󱅰 (0xF1170)
     */
    public data object MESSAGE_PROCESSING_OUTLINE : MaterialDesignGlyphs(0xF1170)

    /**
     * 󱅱 (0xF1171)
     */
    public data object MESSAGE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF1171)

    /**
     * 󱅲 (0xF1172)
     */
    public data object MESSAGE_COG_OUTLINE : MaterialDesignGlyphs(0xF1172)

    /**
     * 󱅳 (0xF1173)
     */
    public data object MESSAGE_TEXT_CLOCK : MaterialDesignGlyphs(0xF1173)

    /**
     * 󱅴 (0xF1174)
     */
    public data object MESSAGE_TEXT_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF1174)

    /**
     * 󱅵 (0xF1175)
     */
    public data object MESSAGE_TEXT_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1175)

    /**
     * 󱅶 (0xF1176)
     */
    public data object CHECKBOX_BLANK_BADGE : MaterialDesignGlyphs(0xF1176)

    /**
     * 󱅷 (0xF1177)
     */
    public data object FILE_LINK : MaterialDesignGlyphs(0xF1177)

    /**
     * 󱅸 (0xF1178)
     */
    public data object FILE_LINK_OUTLINE : MaterialDesignGlyphs(0xF1178)

    /**
     * 󱅹 (0xF1179)
     */
    public data object FILE_PHONE : MaterialDesignGlyphs(0xF1179)

    /**
     * 󱅺 (0xF117A)
     */
    public data object FILE_PHONE_OUTLINE : MaterialDesignGlyphs(0xF117A)

    /**
     * 󱅻 (0xF117B)
     */
    public data object MEDITATION : MaterialDesignGlyphs(0xF117B)

    /**
     * 󱅼 (0xF117C)
     */
    public data object YOGA : MaterialDesignGlyphs(0xF117C)

    /**
     * 󱅽 (0xF117D)
     */
    public data object LEEK : MaterialDesignGlyphs(0xF117D)

    /**
     * 󱅾 (0xF117E)
     */
    public data object NOODLES : MaterialDesignGlyphs(0xF117E)

    /**
     * 󱅿 (0xF117F)
     */
    public data object POUND_BOX_OUTLINE : MaterialDesignGlyphs(0xF117F)

    /**
     * 󱆀 (0xF1180)
     */
    public data object SCHOOL_OUTLINE : MaterialDesignGlyphs(0xF1180)

    /**
     * 󱆁 (0xF1181)
     */
    public data object BASKET_OUTLINE : MaterialDesignGlyphs(0xF1181)

    /**
     * 󱆂 (0xF1182)
     */
    public data object PHONE_IN_TALK_OUTLINE : MaterialDesignGlyphs(0xF1182)

    /**
     * 󱆃 (0xF1183)
     */
    public data object BASH : MaterialDesignGlyphs(0xF1183)

    /**
     * 󱆄 (0xF1184)
     */
    public data object FILE_KEY : MaterialDesignGlyphs(0xF1184)

    /**
     * 󱆅 (0xF1185)
     */
    public data object FILE_KEY_OUTLINE : MaterialDesignGlyphs(0xF1185)

    /**
     * 󱆆 (0xF1186)
     */
    public data object FILE_CERTIFICATE : MaterialDesignGlyphs(0xF1186)

    /**
     * 󱆇 (0xF1187)
     */
    public data object FILE_CERTIFICATE_OUTLINE : MaterialDesignGlyphs(0xF1187)

    /**
     * 󱆈 (0xF1188)
     */
    public data object CERTIFICATE_OUTLINE : MaterialDesignGlyphs(0xF1188)

    /**
     * 󱆉 (0xF1189)
     */
    public data object CIGAR : MaterialDesignGlyphs(0xF1189)

    /**
     * 󱆊 (0xF118A)
     */
    public data object GRILL_OUTLINE : MaterialDesignGlyphs(0xF118A)

    /**
     * 󱆋 (0xF118B)
     */
    public data object QRCODE_PLUS : MaterialDesignGlyphs(0xF118B)

    /**
     * 󱆌 (0xF118C)
     */
    public data object QRCODE_MINUS : MaterialDesignGlyphs(0xF118C)

    /**
     * 󱆍 (0xF118D)
     */
    public data object QRCODE_REMOVE : MaterialDesignGlyphs(0xF118D)

    /**
     * 󱆎 (0xF118E)
     */
    public data object PHONE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF118E)

    /**
     * 󱆏 (0xF118F)
     */
    public data object PHONE_BLUETOOTH_OUTLINE : MaterialDesignGlyphs(0xF118F)

    /**
     * 󱆐 (0xF1190)
     */
    public data object PHONE_CANCEL_OUTLINE : MaterialDesignGlyphs(0xF1190)

    /**
     * 󱆑 (0xF1191)
     */
    public data object PHONE_FORWARD_OUTLINE : MaterialDesignGlyphs(0xF1191)

    /**
     * 󱆒 (0xF1192)
     */
    public data object PHONE_HANGUP_OUTLINE : MaterialDesignGlyphs(0xF1192)

    /**
     * 󱆓 (0xF1193)
     */
    public data object PHONE_INCOMING_OUTLINE : MaterialDesignGlyphs(0xF1193)

    /**
     * 󱆔 (0xF1194)
     */
    public data object PHONE_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1194)

    /**
     * 󱆕 (0xF1195)
     */
    public data object PHONE_LOG_OUTLINE : MaterialDesignGlyphs(0xF1195)

    /**
     * 󱆖 (0xF1196)
     */
    public data object PHONE_MESSAGE : MaterialDesignGlyphs(0xF1196)

    /**
     * 󱆗 (0xF1197)
     */
    public data object PHONE_MESSAGE_OUTLINE : MaterialDesignGlyphs(0xF1197)

    /**
     * 󱆘 (0xF1198)
     */
    public data object PHONE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1198)

    /**
     * 󱆙 (0xF1199)
     */
    public data object PHONE_OUTGOING_OUTLINE : MaterialDesignGlyphs(0xF1199)

    /**
     * 󱆚 (0xF119A)
     */
    public data object PHONE_PAUSED_OUTLINE : MaterialDesignGlyphs(0xF119A)

    /**
     * 󱆛 (0xF119B)
     */
    public data object PHONE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF119B)

    /**
     * 󱆜 (0xF119C)
     */
    public data object PHONE_RETURN_OUTLINE : MaterialDesignGlyphs(0xF119C)

    /**
     * 󱆝 (0xF119D)
     */
    public data object PHONE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF119D)

    /**
     * 󱆞 (0xF119E)
     */
    public data object KEY_STAR : MaterialDesignGlyphs(0xF119E)

    /**
     * 󱆟 (0xF119F)
     */
    public data object KEY_LINK : MaterialDesignGlyphs(0xF119F)

    /**
     * 󱆠 (0xF11A0)
     */
    public data object SHIELD_EDIT : MaterialDesignGlyphs(0xF11A0)

    /**
     * 󱆡 (0xF11A1)
     */
    public data object SHIELD_EDIT_OUTLINE : MaterialDesignGlyphs(0xF11A1)

    /**
     * 󱆢 (0xF11A2)
     */
    public data object SHIELD_SYNC : MaterialDesignGlyphs(0xF11A2)

    /**
     * 󱆣 (0xF11A3)
     */
    public data object SHIELD_SYNC_OUTLINE : MaterialDesignGlyphs(0xF11A3)

    /**
     * 󱆤 (0xF11A4)
     */
    public data object GOLF_CART : MaterialDesignGlyphs(0xF11A4)

    /**
     * 󱆥 (0xF11A5)
     */
    public data object PHONE_MISSED_OUTLINE : MaterialDesignGlyphs(0xF11A5)

    /**
     * 󱆦 (0xF11A6)
     */
    public data object PHONE_OFF_OUTLINE : MaterialDesignGlyphs(0xF11A6)

    /**
     * 󱆧 (0xF11A7)
     */
    public data object FORMAT_QUOTE_OPEN_OUTLINE : MaterialDesignGlyphs(0xF11A7)

    /**
     * 󱆨 (0xF11A8)
     */
    public data object FORMAT_QUOTE_CLOSE_OUTLINE : MaterialDesignGlyphs(0xF11A8)

    /**
     * 󱆩 (0xF11A9)
     */
    public data object PHONE_CHECK : MaterialDesignGlyphs(0xF11A9)

    /**
     * 󱆪 (0xF11AA)
     */
    public data object PHONE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF11AA)

    /**
     * 󱆫 (0xF11AB)
     */
    public data object PHONE_RING : MaterialDesignGlyphs(0xF11AB)

    /**
     * 󱆬 (0xF11AC)
     */
    public data object PHONE_RING_OUTLINE : MaterialDesignGlyphs(0xF11AC)

    /**
     * 󱆭 (0xF11AD)
     */
    public data object SHARE_CIRCLE : MaterialDesignGlyphs(0xF11AD)

    /**
     * 󱆮 (0xF11AE)
     */
    public data object REPLY_CIRCLE : MaterialDesignGlyphs(0xF11AE)

    /**
     * 󱆯 (0xF11AF)
     */
    public data object FRIDGE_OFF : MaterialDesignGlyphs(0xF11AF)

    /**
     * 󱆰 (0xF11B0)
     */
    public data object FRIDGE_OFF_OUTLINE : MaterialDesignGlyphs(0xF11B0)

    /**
     * 󱆱 (0xF11B1)
     */
    public data object FRIDGE_ALERT : MaterialDesignGlyphs(0xF11B1)

    /**
     * 󱆲 (0xF11B2)
     */
    public data object FRIDGE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF11B2)

    /**
     * 󱆳 (0xF11B3)
     */
    public data object WATER_BOILER_ALERT : MaterialDesignGlyphs(0xF11B3)

    /**
     * 󱆴 (0xF11B4)
     */
    public data object WATER_BOILER_OFF : MaterialDesignGlyphs(0xF11B4)

    /**
     * 󱆵 (0xF11B5)
     */
    public data object AMPLIFIER_OFF : MaterialDesignGlyphs(0xF11B5)

    /**
     * 󱆶 (0xF11B6)
     */
    public data object AUDIO_VIDEO_OFF : MaterialDesignGlyphs(0xF11B6)

    /**
     * 󱆷 (0xF11B7)
     */
    public data object TOASTER_OFF : MaterialDesignGlyphs(0xF11B7)

    /**
     * 󱆸 (0xF11B8)
     */
    public data object DISHWASHER_ALERT : MaterialDesignGlyphs(0xF11B8)

    /**
     * 󱆹 (0xF11B9)
     */
    public data object DISHWASHER_OFF : MaterialDesignGlyphs(0xF11B9)

    /**
     * 󱆺 (0xF11BA)
     */
    public data object TUMBLE_DRYER_ALERT : MaterialDesignGlyphs(0xF11BA)

    /**
     * 󱆻 (0xF11BB)
     */
    public data object TUMBLE_DRYER_OFF : MaterialDesignGlyphs(0xF11BB)

    /**
     * 󱆼 (0xF11BC)
     */
    public data object WASHING_MACHINE_ALERT : MaterialDesignGlyphs(0xF11BC)

    /**
     * 󱆽 (0xF11BD)
     */
    public data object WASHING_MACHINE_OFF : MaterialDesignGlyphs(0xF11BD)

    /**
     * 󱆾 (0xF11BE)
     */
    public data object CAR_INFO : MaterialDesignGlyphs(0xF11BE)

    /**
     * 󱆿 (0xF11BF)
     */
    public data object COMMENT_EDIT : MaterialDesignGlyphs(0xF11BF)

    /**
     * 󱇀 (0xF11C0)
     */
    public data object PRINTER_3D_NOZZLE_ALERT : MaterialDesignGlyphs(0xF11C0)

    /**
     * 󱇁 (0xF11C1)
     */
    public data object PRINTER_3D_NOZZLE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF11C1)

    /**
     * 󱇂 (0xF11C2)
     */
    public data object ALIGN_HORIZONTAL_LEFT : MaterialDesignGlyphs(0xF11C2)

    /**
     * 󱇃 (0xF11C3)
     */
    public data object ALIGN_HORIZONTAL_CENTER : MaterialDesignGlyphs(0xF11C3)

    /**
     * 󱇄 (0xF11C4)
     */
    public data object ALIGN_HORIZONTAL_RIGHT : MaterialDesignGlyphs(0xF11C4)

    /**
     * 󱇅 (0xF11C5)
     */
    public data object ALIGN_VERTICAL_BOTTOM : MaterialDesignGlyphs(0xF11C5)

    /**
     * 󱇆 (0xF11C6)
     */
    public data object ALIGN_VERTICAL_CENTER : MaterialDesignGlyphs(0xF11C6)

    /**
     * 󱇇 (0xF11C7)
     */
    public data object ALIGN_VERTICAL_TOP : MaterialDesignGlyphs(0xF11C7)

    /**
     * 󱇈 (0xF11C8)
     */
    public data object DISTRIBUTE_HORIZONTAL_LEFT : MaterialDesignGlyphs(0xF11C8)

    /**
     * 󱇉 (0xF11C9)
     */
    public data object DISTRIBUTE_HORIZONTAL_CENTER : MaterialDesignGlyphs(0xF11C9)

    /**
     * 󱇊 (0xF11CA)
     */
    public data object DISTRIBUTE_HORIZONTAL_RIGHT : MaterialDesignGlyphs(0xF11CA)

    /**
     * 󱇋 (0xF11CB)
     */
    public data object DISTRIBUTE_VERTICAL_BOTTOM : MaterialDesignGlyphs(0xF11CB)

    /**
     * 󱇌 (0xF11CC)
     */
    public data object DISTRIBUTE_VERTICAL_CENTER : MaterialDesignGlyphs(0xF11CC)

    /**
     * 󱇍 (0xF11CD)
     */
    public data object DISTRIBUTE_VERTICAL_TOP : MaterialDesignGlyphs(0xF11CD)

    /**
     * 󱇎 (0xF11CE)
     */
    public data object ALERT_RHOMBUS : MaterialDesignGlyphs(0xF11CE)

    /**
     * 󱇏 (0xF11CF)
     */
    public data object ALERT_RHOMBUS_OUTLINE : MaterialDesignGlyphs(0xF11CF)

    /**
     * 󱇐 (0xF11D0)
     */
    public data object CROWN_OUTLINE : MaterialDesignGlyphs(0xF11D0)

    /**
     * 󱇑 (0xF11D1)
     */
    public data object IMAGE_OFF_OUTLINE : MaterialDesignGlyphs(0xF11D1)

    /**
     * 󱇒 (0xF11D2)
     */
    public data object MOVIE_SEARCH : MaterialDesignGlyphs(0xF11D2)

    /**
     * 󱇓 (0xF11D3)
     */
    public data object MOVIE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF11D3)

    /**
     * 󱇔 (0xF11D4)
     */
    public data object RV_TRUCK : MaterialDesignGlyphs(0xF11D4)

    /**
     * 󱇕 (0xF11D5)
     */
    public data object SHOPPING_OUTLINE : MaterialDesignGlyphs(0xF11D5)

    /**
     * 󱇖 (0xF11D6)
     */
    public data object STRATEGY : MaterialDesignGlyphs(0xF11D6)

    /**
     * 󱇗 (0xF11D7)
     */
    public data object NOTE_TEXT_OUTLINE : MaterialDesignGlyphs(0xF11D7)

    /**
     * 󱇘 (0xF11D8)
     */
    public data object VIEW_AGENDA_OUTLINE : MaterialDesignGlyphs(0xF11D8)

    /**
     * 󱇙 (0xF11D9)
     */
    public data object VIEW_GRID_OUTLINE : MaterialDesignGlyphs(0xF11D9)

    /**
     * 󱇚 (0xF11DA)
     */
    public data object VIEW_GRID_PLUS_OUTLINE : MaterialDesignGlyphs(0xF11DA)

    /**
     * 󱇛 (0xF11DB)
     */
    public data object WINDOW_CLOSED_VARIANT : MaterialDesignGlyphs(0xF11DB)

    /**
     * 󱇜 (0xF11DC)
     */
    public data object WINDOW_OPEN_VARIANT : MaterialDesignGlyphs(0xF11DC)

    /**
     * 󱇝 (0xF11DD)
     */
    public data object COG_CLOCKWISE : MaterialDesignGlyphs(0xF11DD)

    /**
     * 󱇞 (0xF11DE)
     */
    public data object COG_COUNTERCLOCKWISE : MaterialDesignGlyphs(0xF11DE)

    /**
     * 󱇟 (0xF11DF)
     */
    public data object CHART_SANKEY : MaterialDesignGlyphs(0xF11DF)

    /**
     * 󱇠 (0xF11E0)
     */
    public data object CHART_SANKEY_VARIANT : MaterialDesignGlyphs(0xF11E0)

    /**
     * 󱇡 (0xF11E1)
     */
    public data object VANITY_LIGHT : MaterialDesignGlyphs(0xF11E1)

    /**
     * 󱇢 (0xF11E2)
     */
    public data object ROUTER : MaterialDesignGlyphs(0xF11E2)

    /**
     * 󱇣 (0xF11E3)
     */
    public data object IMAGE_EDIT : MaterialDesignGlyphs(0xF11E3)

    /**
     * 󱇤 (0xF11E4)
     */
    public data object IMAGE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF11E4)

    /**
     * 󱇥 (0xF11E5)
     */
    public data object BELL_CHECK : MaterialDesignGlyphs(0xF11E5)

    /**
     * 󱇦 (0xF11E6)
     */
    public data object BELL_CHECK_OUTLINE : MaterialDesignGlyphs(0xF11E6)

    /**
     * 󱇧 (0xF11E7)
     */
    public data object FILE_EDIT : MaterialDesignGlyphs(0xF11E7)

    /**
     * 󱇨 (0xF11E8)
     */
    public data object FILE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF11E8)

    /**
     * 󱇩 (0xF11E9)
     */
    public data object HUMAN_SCOOTER : MaterialDesignGlyphs(0xF11E9)

    /**
     * 󱇪 (0xF11EA)
     */
    public data object SPIDER : MaterialDesignGlyphs(0xF11EA)

    /**
     * 󱇫 (0xF11EB)
     */
    public data object SPIDER_THREAD : MaterialDesignGlyphs(0xF11EB)

    /**
     * 󱇬 (0xF11EC)
     */
    public data object PLUS_THICK : MaterialDesignGlyphs(0xF11EC)

    /**
     * 󱇭 (0xF11ED)
     */
    public data object ALERT_CIRCLE_CHECK : MaterialDesignGlyphs(0xF11ED)

    /**
     * 󱇮 (0xF11EE)
     */
    public data object ALERT_CIRCLE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF11EE)

    /**
     * 󱇯 (0xF11EF)
     */
    public data object STATE_MACHINE : MaterialDesignGlyphs(0xF11EF)

    /**
     * 󱇰 (0xF11F0)
     */
    public data object USB_PORT : MaterialDesignGlyphs(0xF11F0)

    /**
     * 󱇱 (0xF11F1)
     */
    public data object CLOUD_LOCK : MaterialDesignGlyphs(0xF11F1)

    /**
     * 󱇲 (0xF11F2)
     */
    public data object CLOUD_LOCK_OUTLINE : MaterialDesignGlyphs(0xF11F2)

    /**
     * 󱇳 (0xF11F3)
     */
    public data object ROBOT_MOWER_OUTLINE : MaterialDesignGlyphs(0xF11F3)

    /**
     * 󱇴 (0xF11F4)
     */
    public data object SHARE_ALL : MaterialDesignGlyphs(0xF11F4)

    /**
     * 󱇵 (0xF11F5)
     */
    public data object SHARE_ALL_OUTLINE : MaterialDesignGlyphs(0xF11F5)

    /**
     * 󱇶 (0xF11F6)
     */
    public data object GOOGLE_CLOUD : MaterialDesignGlyphs(0xF11F6)

    /**
     * 󱇷 (0xF11F7)
     */
    public data object ROBOT_MOWER : MaterialDesignGlyphs(0xF11F7)

    /**
     * 󱇸 (0xF11F8)
     */
    public data object FAST_FORWARD_5 : MaterialDesignGlyphs(0xF11F8)

    /**
     * 󱇹 (0xF11F9)
     */
    public data object REWIND_5 : MaterialDesignGlyphs(0xF11F9)

    /**
     * 󱇺 (0xF11FA)
     */
    public data object SHAPE_OVAL_PLUS : MaterialDesignGlyphs(0xF11FA)

    /**
     * 󱇻 (0xF11FB)
     */
    public data object TIMELINE_CLOCK : MaterialDesignGlyphs(0xF11FB)

    /**
     * 󱇼 (0xF11FC)
     */
    public data object TIMELINE_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF11FC)

    /**
     * 󱇽 (0xF11FD)
     */
    public data object MIRROR : MaterialDesignGlyphs(0xF11FD)

    /**
     * 󱇾 (0xF11FE)
     */
    public data object ACCOUNT_MULTIPLE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF11FE)

    /**
     * 󱇿 (0xF11FF)
     */
    public data object CARD_PLUS : MaterialDesignGlyphs(0xF11FF)

    /**
     * 󱈀 (0xF1200)
     */
    public data object CARD_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1200)

    /**
     * 󱈁 (0xF1201)
     */
    public data object CHECKERBOARD_PLUS : MaterialDesignGlyphs(0xF1201)

    /**
     * 󱈂 (0xF1202)
     */
    public data object CHECKERBOARD_MINUS : MaterialDesignGlyphs(0xF1202)

    /**
     * 󱈃 (0xF1203)
     */
    public data object CHECKERBOARD_REMOVE : MaterialDesignGlyphs(0xF1203)

    /**
     * 󱈄 (0xF1204)
     */
    public data object SELECT_SEARCH : MaterialDesignGlyphs(0xF1204)

    /**
     * 󱈅 (0xF1205)
     */
    public data object SELECTION_SEARCH : MaterialDesignGlyphs(0xF1205)

    /**
     * 󱈆 (0xF1206)
     */
    public data object LAYERS_SEARCH : MaterialDesignGlyphs(0xF1206)

    /**
     * 󱈇 (0xF1207)
     */
    public data object LAYERS_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1207)

    /**
     * 󱈈 (0xF1208)
     */
    public data object LIGHTBULB_CFL : MaterialDesignGlyphs(0xF1208)

    /**
     * 󱈉 (0xF1209)
     */
    public data object LIGHTBULB_CFL_OFF : MaterialDesignGlyphs(0xF1209)

    /**
     * 󱈊 (0xF120A)
     */
    public data object ACCOUNT_MULTIPLE_REMOVE : MaterialDesignGlyphs(0xF120A)

    /**
     * 󱈋 (0xF120B)
     */
    public data object ACCOUNT_MULTIPLE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF120B)

    /**
     * 󱈌 (0xF120C)
     */
    public data object MAGNIFY_REMOVE_CURSOR : MaterialDesignGlyphs(0xF120C)

    /**
     * 󱈍 (0xF120D)
     */
    public data object MAGNIFY_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF120D)

    /**
     * 󱈎 (0xF120E)
     */
    public data object ARCHIVE_OUTLINE : MaterialDesignGlyphs(0xF120E)

    /**
     * 󱈏 (0xF120F)
     */
    public data object BATTERY_HEART : MaterialDesignGlyphs(0xF120F)

    /**
     * 󱈐 (0xF1210)
     */
    public data object BATTERY_HEART_OUTLINE : MaterialDesignGlyphs(0xF1210)

    /**
     * 󱈑 (0xF1211)
     */
    public data object BATTERY_HEART_VARIANT : MaterialDesignGlyphs(0xF1211)

    /**
     * 󱈒 (0xF1212)
     */
    public data object BUS_MARKER : MaterialDesignGlyphs(0xF1212)

    /**
     * 󱈓 (0xF1213)
     */
    public data object CHART_MULTIPLE : MaterialDesignGlyphs(0xF1213)

    /**
     * 󱈔 (0xF1214)
     */
    public data object EMOTICON_LOL : MaterialDesignGlyphs(0xF1214)

    /**
     * 󱈕 (0xF1215)
     */
    public data object EMOTICON_LOL_OUTLINE : MaterialDesignGlyphs(0xF1215)

    /**
     * 󱈖 (0xF1216)
     */
    public data object FILE_SYNC : MaterialDesignGlyphs(0xF1216)

    /**
     * 󱈗 (0xF1217)
     */
    public data object FILE_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1217)

    /**
     * 󱈘 (0xF1218)
     */
    public data object HANDSHAKE : MaterialDesignGlyphs(0xF1218)

    /**
     * 󱈙 (0xF1219)
     */
    public data object LANGUAGE_KOTLIN : MaterialDesignGlyphs(0xF1219)

    /**
     * 󱈚 (0xF121A)
     */
    public data object LANGUAGE_FORTRAN : MaterialDesignGlyphs(0xF121A)

    /**
     * 󱈛 (0xF121B)
     */
    public data object OFFER : MaterialDesignGlyphs(0xF121B)

    /**
     * 󱈜 (0xF121C)
     */
    public data object RADIO_OFF : MaterialDesignGlyphs(0xF121C)

    /**
     * 󱈝 (0xF121D)
     */
    public data object TABLE_HEADERS_EYE : MaterialDesignGlyphs(0xF121D)

    /**
     * 󱈞 (0xF121E)
     */
    public data object TABLE_HEADERS_EYE_OFF : MaterialDesignGlyphs(0xF121E)

    /**
     * 󱈟 (0xF121F)
     */
    public data object TAG_MINUS_OUTLINE : MaterialDesignGlyphs(0xF121F)

    /**
     * 󱈠 (0xF1220)
     */
    public data object TAG_OFF : MaterialDesignGlyphs(0xF1220)

    /**
     * 󱈡 (0xF1221)
     */
    public data object TAG_OFF_OUTLINE : MaterialDesignGlyphs(0xF1221)

    /**
     * 󱈢 (0xF1222)
     */
    public data object TAG_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1222)

    /**
     * 󱈣 (0xF1223)
     */
    public data object TAG_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1223)

    /**
     * 󱈤 (0xF1224)
     */
    public data object TAG_TEXT : MaterialDesignGlyphs(0xF1224)

    /**
     * 󱈥 (0xF1225)
     */
    public data object VECTOR_POLYLINE_EDIT : MaterialDesignGlyphs(0xF1225)

    /**
     * 󱈦 (0xF1226)
     */
    public data object VECTOR_POLYLINE_MINUS : MaterialDesignGlyphs(0xF1226)

    /**
     * 󱈧 (0xF1227)
     */
    public data object VECTOR_POLYLINE_PLUS : MaterialDesignGlyphs(0xF1227)

    /**
     * 󱈨 (0xF1228)
     */
    public data object VECTOR_POLYLINE_REMOVE : MaterialDesignGlyphs(0xF1228)

    /**
     * 󱈩 (0xF1229)
     */
    public data object BEAKER_ALERT : MaterialDesignGlyphs(0xF1229)

    /**
     * 󱈪 (0xF122A)
     */
    public data object BEAKER_ALERT_OUTLINE : MaterialDesignGlyphs(0xF122A)

    /**
     * 󱈫 (0xF122B)
     */
    public data object BEAKER_CHECK : MaterialDesignGlyphs(0xF122B)

    /**
     * 󱈬 (0xF122C)
     */
    public data object BEAKER_CHECK_OUTLINE : MaterialDesignGlyphs(0xF122C)

    /**
     * 󱈭 (0xF122D)
     */
    public data object BEAKER_MINUS : MaterialDesignGlyphs(0xF122D)

    /**
     * 󱈮 (0xF122E)
     */
    public data object BEAKER_MINUS_OUTLINE : MaterialDesignGlyphs(0xF122E)

    /**
     * 󱈯 (0xF122F)
     */
    public data object BEAKER_PLUS : MaterialDesignGlyphs(0xF122F)

    /**
     * 󱈰 (0xF1230)
     */
    public data object BEAKER_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1230)

    /**
     * 󱈱 (0xF1231)
     */
    public data object BEAKER_QUESTION : MaterialDesignGlyphs(0xF1231)

    /**
     * 󱈲 (0xF1232)
     */
    public data object BEAKER_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF1232)

    /**
     * 󱈳 (0xF1233)
     */
    public data object BEAKER_REMOVE : MaterialDesignGlyphs(0xF1233)

    /**
     * 󱈴 (0xF1234)
     */
    public data object BEAKER_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1234)

    /**
     * 󱈵 (0xF1235)
     */
    public data object BICYCLE_BASKET : MaterialDesignGlyphs(0xF1235)

    /**
     * 󱈶 (0xF1236)
     */
    public data object BARCODE_OFF : MaterialDesignGlyphs(0xF1236)

    /**
     * 󱈷 (0xF1237)
     */
    public data object DIGITAL_OCEAN : MaterialDesignGlyphs(0xF1237)

    /**
     * 󱈸 (0xF1238)
     */
    public data object EXCLAMATION_THICK : MaterialDesignGlyphs(0xF1238)

    /**
     * 󱈹 (0xF1239)
     */
    public data object DESK : MaterialDesignGlyphs(0xF1239)

    /**
     * 󱈺 (0xF123A)
     */
    public data object FLASK_EMPTY_MINUS : MaterialDesignGlyphs(0xF123A)

    /**
     * 󱈻 (0xF123B)
     */
    public data object FLASK_EMPTY_MINUS_OUTLINE : MaterialDesignGlyphs(0xF123B)

    /**
     * 󱈼 (0xF123C)
     */
    public data object FLASK_EMPTY_PLUS : MaterialDesignGlyphs(0xF123C)

    /**
     * 󱈽 (0xF123D)
     */
    public data object FLASK_EMPTY_PLUS_OUTLINE : MaterialDesignGlyphs(0xF123D)

    /**
     * 󱈾 (0xF123E)
     */
    public data object FLASK_EMPTY_REMOVE : MaterialDesignGlyphs(0xF123E)

    /**
     * 󱈿 (0xF123F)
     */
    public data object FLASK_EMPTY_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF123F)

    /**
     * 󱉀 (0xF1240)
     */
    public data object FLASK_MINUS : MaterialDesignGlyphs(0xF1240)

    /**
     * 󱉁 (0xF1241)
     */
    public data object FLASK_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1241)

    /**
     * 󱉂 (0xF1242)
     */
    public data object FLASK_PLUS : MaterialDesignGlyphs(0xF1242)

    /**
     * 󱉃 (0xF1243)
     */
    public data object FLASK_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1243)

    /**
     * 󱉄 (0xF1244)
     */
    public data object FLASK_REMOVE : MaterialDesignGlyphs(0xF1244)

    /**
     * 󱉅 (0xF1245)
     */
    public data object FLASK_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1245)

    /**
     * 󱉆 (0xF1246)
     */
    public data object FOLDER_MOVE_OUTLINE : MaterialDesignGlyphs(0xF1246)

    /**
     * 󱉇 (0xF1247)
     */
    public data object HOME_REMOVE : MaterialDesignGlyphs(0xF1247)

    /**
     * 󱉈 (0xF1248)
     */
    public data object WEBRTC : MaterialDesignGlyphs(0xF1248)

    /**
     * 󱉉 (0xF1249)
     */
    public data object SEAT_PASSENGER : MaterialDesignGlyphs(0xF1249)

    /**
     * 󱉊 (0xF124A)
     */
    public data object WEB_CLOCK : MaterialDesignGlyphs(0xF124A)

    /**
     * 󱉋 (0xF124B)
     */
    public data object FLASK_ROUND_BOTTOM : MaterialDesignGlyphs(0xF124B)

    /**
     * 󱉌 (0xF124C)
     */
    public data object FLASK_ROUND_BOTTOM_EMPTY : MaterialDesignGlyphs(0xF124C)

    /**
     * 󱉍 (0xF124D)
     */
    public data object FLASK_ROUND_BOTTOM_EMPTY_OUTLINE : MaterialDesignGlyphs(0xF124D)

    /**
     * 󱉎 (0xF124E)
     */
    public data object FLASK_ROUND_BOTTOM_OUTLINE : MaterialDesignGlyphs(0xF124E)

    /**
     * 󱉏 (0xF124F)
     */
    public data object GOLD : MaterialDesignGlyphs(0xF124F)

    /**
     * 󱉐 (0xF1250)
     */
    public data object MESSAGE_STAR_OUTLINE : MaterialDesignGlyphs(0xF1250)

    /**
     * 󱉑 (0xF1251)
     */
    public data object HOME_LIGHTBULB : MaterialDesignGlyphs(0xF1251)

    /**
     * 󱉒 (0xF1252)
     */
    public data object HOME_LIGHTBULB_OUTLINE : MaterialDesignGlyphs(0xF1252)

    /**
     * 󱉓 (0xF1253)
     */
    public data object LIGHTBULB_GROUP : MaterialDesignGlyphs(0xF1253)

    /**
     * 󱉔 (0xF1254)
     */
    public data object LIGHTBULB_GROUP_OUTLINE : MaterialDesignGlyphs(0xF1254)

    /**
     * 󱉕 (0xF1255)
     */
    public data object LIGHTBULB_MULTIPLE : MaterialDesignGlyphs(0xF1255)

    /**
     * 󱉖 (0xF1256)
     */
    public data object LIGHTBULB_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1256)

    /**
     * 󱉗 (0xF1257)
     */
    public data object API_OFF : MaterialDesignGlyphs(0xF1257)

    /**
     * 󱉘 (0xF1258)
     */
    public data object ALLERGY : MaterialDesignGlyphs(0xF1258)

    /**
     * 󱉙 (0xF1259)
     */
    public data object ARCHIVE_ARROW_DOWN : MaterialDesignGlyphs(0xF1259)

    /**
     * 󱉚 (0xF125A)
     */
    public data object ARCHIVE_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF125A)

    /**
     * 󱉛 (0xF125B)
     */
    public data object ARCHIVE_ARROW_UP : MaterialDesignGlyphs(0xF125B)

    /**
     * 󱉜 (0xF125C)
     */
    public data object ARCHIVE_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF125C)

    /**
     * 󱉝 (0xF125D)
     */
    public data object BATTERY_OFF : MaterialDesignGlyphs(0xF125D)

    /**
     * 󱉞 (0xF125E)
     */
    public data object BATTERY_OFF_OUTLINE : MaterialDesignGlyphs(0xF125E)

    /**
     * 󱉟 (0xF125F)
     */
    public data object BOOKSHELF : MaterialDesignGlyphs(0xF125F)

    /**
     * 󱉠 (0xF1260)
     */
    public data object CASH_MINUS : MaterialDesignGlyphs(0xF1260)

    /**
     * 󱉡 (0xF1261)
     */
    public data object CASH_PLUS : MaterialDesignGlyphs(0xF1261)

    /**
     * 󱉢 (0xF1262)
     */
    public data object CASH_REMOVE : MaterialDesignGlyphs(0xF1262)

    /**
     * 󱉣 (0xF1263)
     */
    public data object CLIPBOARD_CHECK_MULTIPLE : MaterialDesignGlyphs(0xF1263)

    /**
     * 󱉤 (0xF1264)
     */
    public data object CLIPBOARD_CHECK_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1264)

    /**
     * 󱉥 (0xF1265)
     */
    public data object CLIPBOARD_FILE : MaterialDesignGlyphs(0xF1265)

    /**
     * 󱉦 (0xF1266)
     */
    public data object CLIPBOARD_FILE_OUTLINE : MaterialDesignGlyphs(0xF1266)

    /**
     * 󱉧 (0xF1267)
     */
    public data object CLIPBOARD_MULTIPLE : MaterialDesignGlyphs(0xF1267)

    /**
     * 󱉨 (0xF1268)
     */
    public data object CLIPBOARD_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1268)

    /**
     * 󱉩 (0xF1269)
     */
    public data object CLIPBOARD_PLAY_MULTIPLE : MaterialDesignGlyphs(0xF1269)

    /**
     * 󱉪 (0xF126A)
     */
    public data object CLIPBOARD_PLAY_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF126A)

    /**
     * 󱉫 (0xF126B)
     */
    public data object CLIPBOARD_TEXT_MULTIPLE : MaterialDesignGlyphs(0xF126B)

    /**
     * 󱉬 (0xF126C)
     */
    public data object CLIPBOARD_TEXT_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF126C)

    /**
     * 󱉭 (0xF126D)
     */
    public data object FOLDER_MARKER : MaterialDesignGlyphs(0xF126D)

    /**
     * 󱉮 (0xF126E)
     */
    public data object FOLDER_MARKER_OUTLINE : MaterialDesignGlyphs(0xF126E)

    /**
     * 󱉯 (0xF126F)
     */
    public data object FORMAT_LIST_TEXT : MaterialDesignGlyphs(0xF126F)

    /**
     * 󱉰 (0xF1270)
     */
    public data object INBOX_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF1270)

    /**
     * 󱉱 (0xF1271)
     */
    public data object INBOX_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF1271)

    /**
     * 󱉲 (0xF1272)
     */
    public data object INBOX_FULL : MaterialDesignGlyphs(0xF1272)

    /**
     * 󱉳 (0xF1273)
     */
    public data object INBOX_FULL_OUTLINE : MaterialDesignGlyphs(0xF1273)

    /**
     * 󱉴 (0xF1274)
     */
    public data object INBOX_OUTLINE : MaterialDesignGlyphs(0xF1274)

    /**
     * 󱉵 (0xF1275)
     */
    public data object LIGHTBULB_CFL_SPIRAL : MaterialDesignGlyphs(0xF1275)

    /**
     * 󱉶 (0xF1276)
     */
    public data object MAGNIFY_SCAN : MaterialDesignGlyphs(0xF1276)

    /**
     * 󱉷 (0xF1277)
     */
    public data object MAP_MARKER_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1277)

    /**
     * 󱉸 (0xF1278)
     */
    public data object PERCENT_OUTLINE : MaterialDesignGlyphs(0xF1278)

    /**
     * 󱉹 (0xF1279)
     */
    public data object PHONE_CLASSIC_OFF : MaterialDesignGlyphs(0xF1279)

    /**
     * 󱉺 (0xF127A)
     */
    public data object PLAY_BOX : MaterialDesignGlyphs(0xF127A)

    /**
     * 󱉻 (0xF127B)
     */
    public data object ACCOUNT_EYE_OUTLINE : MaterialDesignGlyphs(0xF127B)

    /**
     * 󱉼 (0xF127C)
     */
    public data object SAFE_SQUARE : MaterialDesignGlyphs(0xF127C)

    /**
     * 󱉽 (0xF127D)
     */
    public data object SAFE_SQUARE_OUTLINE : MaterialDesignGlyphs(0xF127D)

    /**
     * 󱉾 (0xF127E)
     */
    public data object SCOREBOARD : MaterialDesignGlyphs(0xF127E)

    /**
     * 󱉿 (0xF127F)
     */
    public data object SCOREBOARD_OUTLINE : MaterialDesignGlyphs(0xF127F)

    /**
     * 󱊀 (0xF1280)
     */
    public data object SELECT_MARKER : MaterialDesignGlyphs(0xF1280)

    /**
     * 󱊁 (0xF1281)
     */
    public data object SELECT_MULTIPLE : MaterialDesignGlyphs(0xF1281)

    /**
     * 󱊂 (0xF1282)
     */
    public data object SELECT_MULTIPLE_MARKER : MaterialDesignGlyphs(0xF1282)

    /**
     * 󱊃 (0xF1283)
     */
    public data object SELECTION_MARKER : MaterialDesignGlyphs(0xF1283)

    /**
     * 󱊄 (0xF1284)
     */
    public data object SELECTION_MULTIPLE_MARKER : MaterialDesignGlyphs(0xF1284)

    /**
     * 󱊅 (0xF1285)
     */
    public data object SELECTION_MULTIPLE : MaterialDesignGlyphs(0xF1285)

    /**
     * 󱊆 (0xF1286)
     */
    public data object STAR_BOX_MULTIPLE : MaterialDesignGlyphs(0xF1286)

    /**
     * 󱊇 (0xF1287)
     */
    public data object STAR_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1287)

    /**
     * 󱊈 (0xF1288)
     */
    public data object TOY_BRICK : MaterialDesignGlyphs(0xF1288)

    /**
     * 󱊉 (0xF1289)
     */
    public data object TOY_BRICK_MARKER : MaterialDesignGlyphs(0xF1289)

    /**
     * 󱊊 (0xF128A)
     */
    public data object TOY_BRICK_MARKER_OUTLINE : MaterialDesignGlyphs(0xF128A)

    /**
     * 󱊋 (0xF128B)
     */
    public data object TOY_BRICK_MINUS : MaterialDesignGlyphs(0xF128B)

    /**
     * 󱊌 (0xF128C)
     */
    public data object TOY_BRICK_MINUS_OUTLINE : MaterialDesignGlyphs(0xF128C)

    /**
     * 󱊍 (0xF128D)
     */
    public data object TOY_BRICK_OUTLINE : MaterialDesignGlyphs(0xF128D)

    /**
     * 󱊎 (0xF128E)
     */
    public data object TOY_BRICK_PLUS : MaterialDesignGlyphs(0xF128E)

    /**
     * 󱊏 (0xF128F)
     */
    public data object TOY_BRICK_PLUS_OUTLINE : MaterialDesignGlyphs(0xF128F)

    /**
     * 󱊐 (0xF1290)
     */
    public data object TOY_BRICK_REMOVE : MaterialDesignGlyphs(0xF1290)

    /**
     * 󱊑 (0xF1291)
     */
    public data object TOY_BRICK_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1291)

    /**
     * 󱊒 (0xF1292)
     */
    public data object TOY_BRICK_SEARCH : MaterialDesignGlyphs(0xF1292)

    /**
     * 󱊓 (0xF1293)
     */
    public data object TOY_BRICK_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1293)

    /**
     * 󱊔 (0xF1294)
     */
    public data object TRAY : MaterialDesignGlyphs(0xF1294)

    /**
     * 󱊕 (0xF1295)
     */
    public data object TRAY_ALERT : MaterialDesignGlyphs(0xF1295)

    /**
     * 󱊖 (0xF1296)
     */
    public data object TRAY_FULL : MaterialDesignGlyphs(0xF1296)

    /**
     * 󱊗 (0xF1297)
     */
    public data object TRAY_MINUS : MaterialDesignGlyphs(0xF1297)

    /**
     * 󱊘 (0xF1298)
     */
    public data object TRAY_PLUS : MaterialDesignGlyphs(0xF1298)

    /**
     * 󱊙 (0xF1299)
     */
    public data object TRAY_REMOVE : MaterialDesignGlyphs(0xF1299)

    /**
     * 󱊚 (0xF129A)
     */
    public data object TRUCK_CHECK_OUTLINE : MaterialDesignGlyphs(0xF129A)

    /**
     * 󱊛 (0xF129B)
     */
    public data object TRUCK_DELIVERY_OUTLINE : MaterialDesignGlyphs(0xF129B)

    /**
     * 󱊜 (0xF129C)
     */
    public data object TRUCK_FAST_OUTLINE : MaterialDesignGlyphs(0xF129C)

    /**
     * 󱊝 (0xF129D)
     */
    public data object TRUCK_OUTLINE : MaterialDesignGlyphs(0xF129D)

    /**
     * 󱊞 (0xF129E)
     */
    public data object USB_FLASH_DRIVE : MaterialDesignGlyphs(0xF129E)

    /**
     * 󱊟 (0xF129F)
     */
    public data object USB_FLASH_DRIVE_OUTLINE : MaterialDesignGlyphs(0xF129F)

    /**
     * 󱊠 (0xF12A0)
     */
    public data object WATER_POLO : MaterialDesignGlyphs(0xF12A0)

    /**
     * 󱊡 (0xF12A1)
     */
    public data object BATTERY_LOW : MaterialDesignGlyphs(0xF12A1)

    /**
     * 󱊢 (0xF12A2)
     */
    public data object BATTERY_MEDIUM : MaterialDesignGlyphs(0xF12A2)

    /**
     * 󱊣 (0xF12A3)
     */
    public data object BATTERY_HIGH : MaterialDesignGlyphs(0xF12A3)

    /**
     * 󱊤 (0xF12A4)
     */
    public data object BATTERY_CHARGING_LOW : MaterialDesignGlyphs(0xF12A4)

    /**
     * 󱊥 (0xF12A5)
     */
    public data object BATTERY_CHARGING_MEDIUM : MaterialDesignGlyphs(0xF12A5)

    /**
     * 󱊦 (0xF12A6)
     */
    public data object BATTERY_CHARGING_HIGH : MaterialDesignGlyphs(0xF12A6)

    /**
     * 󱊧 (0xF12A7)
     */
    public data object HEXADECIMAL : MaterialDesignGlyphs(0xF12A7)

    /**
     * 󱊨 (0xF12A8)
     */
    public data object GESTURE_TAP_BUTTON : MaterialDesignGlyphs(0xF12A8)

    /**
     * 󱊩 (0xF12A9)
     */
    public data object GESTURE_TAP_BOX : MaterialDesignGlyphs(0xF12A9)

    /**
     * 󱊪 (0xF12AA)
     */
    public data object LAN_CHECK : MaterialDesignGlyphs(0xF12AA)

    /**
     * 󱊫 (0xF12AB)
     */
    public data object KEYBOARD_F1 : MaterialDesignGlyphs(0xF12AB)

    /**
     * 󱊬 (0xF12AC)
     */
    public data object KEYBOARD_F2 : MaterialDesignGlyphs(0xF12AC)

    /**
     * 󱊭 (0xF12AD)
     */
    public data object KEYBOARD_F3 : MaterialDesignGlyphs(0xF12AD)

    /**
     * 󱊮 (0xF12AE)
     */
    public data object KEYBOARD_F4 : MaterialDesignGlyphs(0xF12AE)

    /**
     * 󱊯 (0xF12AF)
     */
    public data object KEYBOARD_F5 : MaterialDesignGlyphs(0xF12AF)

    /**
     * 󱊰 (0xF12B0)
     */
    public data object KEYBOARD_F6 : MaterialDesignGlyphs(0xF12B0)

    /**
     * 󱊱 (0xF12B1)
     */
    public data object KEYBOARD_F7 : MaterialDesignGlyphs(0xF12B1)

    /**
     * 󱊲 (0xF12B2)
     */
    public data object KEYBOARD_F8 : MaterialDesignGlyphs(0xF12B2)

    /**
     * 󱊳 (0xF12B3)
     */
    public data object KEYBOARD_F9 : MaterialDesignGlyphs(0xF12B3)

    /**
     * 󱊴 (0xF12B4)
     */
    public data object KEYBOARD_F10 : MaterialDesignGlyphs(0xF12B4)

    /**
     * 󱊵 (0xF12B5)
     */
    public data object KEYBOARD_F11 : MaterialDesignGlyphs(0xF12B5)

    /**
     * 󱊶 (0xF12B6)
     */
    public data object KEYBOARD_F12 : MaterialDesignGlyphs(0xF12B6)

    /**
     * 󱊷 (0xF12B7)
     */
    public data object KEYBOARD_ESC : MaterialDesignGlyphs(0xF12B7)

    /**
     * 󱊸 (0xF12B8)
     */
    public data object TOSLINK : MaterialDesignGlyphs(0xF12B8)

    /**
     * 󱊹 (0xF12B9)
     */
    public data object CHEESE : MaterialDesignGlyphs(0xF12B9)

    /**
     * 󱊺 (0xF12BA)
     */
    public data object STRING_LIGHTS : MaterialDesignGlyphs(0xF12BA)

    /**
     * 󱊻 (0xF12BB)
     */
    public data object STRING_LIGHTS_OFF : MaterialDesignGlyphs(0xF12BB)

    /**
     * 󱊼 (0xF12BC)
     */
    public data object WHISTLE_OUTLINE : MaterialDesignGlyphs(0xF12BC)

    /**
     * 󱊽 (0xF12BD)
     */
    public data object STAIRS_UP : MaterialDesignGlyphs(0xF12BD)

    /**
     * 󱊾 (0xF12BE)
     */
    public data object STAIRS_DOWN : MaterialDesignGlyphs(0xF12BE)

    /**
     * 󱊿 (0xF12BF)
     */
    public data object ESCALATOR_UP : MaterialDesignGlyphs(0xF12BF)

    /**
     * 󱋀 (0xF12C0)
     */
    public data object ESCALATOR_DOWN : MaterialDesignGlyphs(0xF12C0)

    /**
     * 󱋁 (0xF12C1)
     */
    public data object ELEVATOR_UP : MaterialDesignGlyphs(0xF12C1)

    /**
     * 󱋂 (0xF12C2)
     */
    public data object ELEVATOR_DOWN : MaterialDesignGlyphs(0xF12C2)

    /**
     * 󱋃 (0xF12C3)
     */
    public data object LIGHTBULB_CFL_SPIRAL_OFF : MaterialDesignGlyphs(0xF12C3)

    /**
     * 󱋄 (0xF12C4)
     */
    public data object COMMENT_EDIT_OUTLINE : MaterialDesignGlyphs(0xF12C4)

    /**
     * 󱋅 (0xF12C5)
     */
    public data object TOOLTIP_EDIT_OUTLINE : MaterialDesignGlyphs(0xF12C5)

    /**
     * 󱋆 (0xF12C6)
     */
    public data object MONITOR_EDIT : MaterialDesignGlyphs(0xF12C6)

    /**
     * 󱋇 (0xF12C7)
     */
    public data object EMAIL_SYNC : MaterialDesignGlyphs(0xF12C7)

    /**
     * 󱋈 (0xF12C8)
     */
    public data object EMAIL_SYNC_OUTLINE : MaterialDesignGlyphs(0xF12C8)

    /**
     * 󱋉 (0xF12C9)
     */
    public data object CHAT_ALERT_OUTLINE : MaterialDesignGlyphs(0xF12C9)

    /**
     * 󱋊 (0xF12CA)
     */
    public data object CHAT_PROCESSING_OUTLINE : MaterialDesignGlyphs(0xF12CA)

    /**
     * 󱋋 (0xF12CB)
     */
    public data object SNOWFLAKE_MELT : MaterialDesignGlyphs(0xF12CB)

    /**
     * 󱋌 (0xF12CC)
     */
    public data object CLOUD_CHECK_OUTLINE : MaterialDesignGlyphs(0xF12CC)

    /**
     * 󱋍 (0xF12CD)
     */
    public data object LIGHTBULB_GROUP_OFF : MaterialDesignGlyphs(0xF12CD)

    /**
     * 󱋎 (0xF12CE)
     */
    public data object LIGHTBULB_GROUP_OFF_OUTLINE : MaterialDesignGlyphs(0xF12CE)

    /**
     * 󱋏 (0xF12CF)
     */
    public data object LIGHTBULB_MULTIPLE_OFF : MaterialDesignGlyphs(0xF12CF)

    /**
     * 󱋐 (0xF12D0)
     */
    public data object LIGHTBULB_MULTIPLE_OFF_OUTLINE : MaterialDesignGlyphs(0xF12D0)

    /**
     * 󱋑 (0xF12D1)
     */
    public data object CHAT_SLEEP : MaterialDesignGlyphs(0xF12D1)

    /**
     * 󱋒 (0xF12D2)
     */
    public data object CHAT_SLEEP_OUTLINE : MaterialDesignGlyphs(0xF12D2)

    /**
     * 󱋓 (0xF12D3)
     */
    public data object GARAGE_VARIANT : MaterialDesignGlyphs(0xF12D3)

    /**
     * 󱋔 (0xF12D4)
     */
    public data object GARAGE_OPEN_VARIANT : MaterialDesignGlyphs(0xF12D4)

    /**
     * 󱋕 (0xF12D5)
     */
    public data object GARAGE_ALERT_VARIANT : MaterialDesignGlyphs(0xF12D5)

    /**
     * 󱋖 (0xF12D6)
     */
    public data object CLOUD_SYNC_OUTLINE : MaterialDesignGlyphs(0xF12D6)

    /**
     * 󱋗 (0xF12D7)
     */
    public data object GLOBE_LIGHT : MaterialDesignGlyphs(0xF12D7)

    /**
     * 󱋘 (0xF12D8)
     */
    public data object CELLPHONE_NFC_OFF : MaterialDesignGlyphs(0xF12D8)

    /**
     * 󱋙 (0xF12D9)
     */
    public data object LEAF_OFF : MaterialDesignGlyphs(0xF12D9)

    /**
     * 󱋚 (0xF12DA)
     */
    public data object LEAF_MAPLE_OFF : MaterialDesignGlyphs(0xF12DA)

    /**
     * 󱋛 (0xF12DB)
     */
    public data object MAP_MARKER_LEFT : MaterialDesignGlyphs(0xF12DB)

    /**
     * 󱋜 (0xF12DC)
     */
    public data object MAP_MARKER_RIGHT : MaterialDesignGlyphs(0xF12DC)

    /**
     * 󱋝 (0xF12DD)
     */
    public data object MAP_MARKER_LEFT_OUTLINE : MaterialDesignGlyphs(0xF12DD)

    /**
     * 󱋞 (0xF12DE)
     */
    public data object MAP_MARKER_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF12DE)

    /**
     * 󱋟 (0xF12DF)
     */
    public data object ACCOUNT_CANCEL : MaterialDesignGlyphs(0xF12DF)

    /**
     * 󱋠 (0xF12E0)
     */
    public data object ACCOUNT_CANCEL_OUTLINE : MaterialDesignGlyphs(0xF12E0)

    /**
     * 󱋡 (0xF12E1)
     */
    public data object FILE_CLOCK : MaterialDesignGlyphs(0xF12E1)

    /**
     * 󱋢 (0xF12E2)
     */
    public data object FILE_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF12E2)

    /**
     * 󱋣 (0xF12E3)
     */
    public data object FOLDER_TABLE : MaterialDesignGlyphs(0xF12E3)

    /**
     * 󱋤 (0xF12E4)
     */
    public data object FOLDER_TABLE_OUTLINE : MaterialDesignGlyphs(0xF12E4)

    /**
     * 󱋥 (0xF12E5)
     */
    public data object HYDRO_POWER : MaterialDesignGlyphs(0xF12E5)

    /**
     * 󱋦 (0xF12E6)
     */
    public data object DOORBELL : MaterialDesignGlyphs(0xF12E6)

    /**
     * 󱋧 (0xF12E7)
     */
    public data object BULMA : MaterialDesignGlyphs(0xF12E7)

    /**
     * 󱋨 (0xF12E8)
     */
    public data object IOBROKER : MaterialDesignGlyphs(0xF12E8)

    /**
     * 󱋩 (0xF12E9)
     */
    public data object OCI : MaterialDesignGlyphs(0xF12E9)

    /**
     * 󱋪 (0xF12EA)
     */
    public data object LABEL_PERCENT : MaterialDesignGlyphs(0xF12EA)

    /**
     * 󱋫 (0xF12EB)
     */
    public data object LABEL_PERCENT_OUTLINE : MaterialDesignGlyphs(0xF12EB)

    /**
     * 󱋬 (0xF12EC)
     */
    public data object CHECKBOX_BLANK_OFF : MaterialDesignGlyphs(0xF12EC)

    /**
     * 󱋭 (0xF12ED)
     */
    public data object CHECKBOX_BLANK_OFF_OUTLINE : MaterialDesignGlyphs(0xF12ED)

    /**
     * 󱋮 (0xF12EE)
     */
    public data object SQUARE_OFF : MaterialDesignGlyphs(0xF12EE)

    /**
     * 󱋯 (0xF12EF)
     */
    public data object SQUARE_OFF_OUTLINE : MaterialDesignGlyphs(0xF12EF)

    /**
     * 󱋰 (0xF12F0)
     */
    public data object DRAG_HORIZONTAL_VARIANT : MaterialDesignGlyphs(0xF12F0)

    /**
     * 󱋱 (0xF12F1)
     */
    public data object DRAG_VERTICAL_VARIANT : MaterialDesignGlyphs(0xF12F1)

    /**
     * 󱋲 (0xF12F2)
     */
    public data object MESSAGE_ARROW_LEFT : MaterialDesignGlyphs(0xF12F2)

    /**
     * 󱋳 (0xF12F3)
     */
    public data object MESSAGE_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF12F3)

    /**
     * 󱋴 (0xF12F4)
     */
    public data object MESSAGE_ARROW_RIGHT : MaterialDesignGlyphs(0xF12F4)

    /**
     * 󱋵 (0xF12F5)
     */
    public data object MESSAGE_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF12F5)

    /**
     * 󱋶 (0xF12F6)
     */
    public data object DATABASE_MARKER : MaterialDesignGlyphs(0xF12F6)

    /**
     * 󱋷 (0xF12F7)
     */
    public data object TAG_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF12F7)

    /**
     * 󱋸 (0xF12F8)
     */
    public data object MAP_MARKER_PLUS_OUTLINE : MaterialDesignGlyphs(0xF12F8)

    /**
     * 󱋹 (0xF12F9)
     */
    public data object MAP_MARKER_MINUS_OUTLINE : MaterialDesignGlyphs(0xF12F9)

    /**
     * 󱋺 (0xF12FA)
     */
    public data object MAP_MARKER_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF12FA)

    /**
     * 󱋻 (0xF12FB)
     */
    public data object MAP_MARKER_CHECK_OUTLINE : MaterialDesignGlyphs(0xF12FB)

    /**
     * 󱋼 (0xF12FC)
     */
    public data object MAP_MARKER_RADIUS_OUTLINE : MaterialDesignGlyphs(0xF12FC)

    /**
     * 󱋽 (0xF12FD)
     */
    public data object MAP_MARKER_OFF_OUTLINE : MaterialDesignGlyphs(0xF12FD)

    /**
     * 󱋾 (0xF12FE)
     */
    public data object MOLECULE_CO : MaterialDesignGlyphs(0xF12FE)

    /**
     * 󱋿 (0xF12FF)
     */
    public data object JUMP_ROPE : MaterialDesignGlyphs(0xF12FF)

    /**
     * 󱌀 (0xF1300)
     */
    public data object KETTLEBELL : MaterialDesignGlyphs(0xF1300)

    /**
     * 󱌁 (0xF1301)
     */
    public data object ACCOUNT_CONVERT_OUTLINE : MaterialDesignGlyphs(0xF1301)

    /**
     * 󱌂 (0xF1302)
     */
    public data object BUNK_BED : MaterialDesignGlyphs(0xF1302)

    /**
     * 󱌃 (0xF1303)
     */
    public data object FLEUR_DE_LIS : MaterialDesignGlyphs(0xF1303)

    /**
     * 󱌄 (0xF1304)
     */
    public data object SKI : MaterialDesignGlyphs(0xF1304)

    /**
     * 󱌅 (0xF1305)
     */
    public data object SKI_CROSS_COUNTRY : MaterialDesignGlyphs(0xF1305)

    /**
     * 󱌆 (0xF1306)
     */
    public data object SKI_WATER : MaterialDesignGlyphs(0xF1306)

    /**
     * 󱌇 (0xF1307)
     */
    public data object SNOWBOARD : MaterialDesignGlyphs(0xF1307)

    /**
     * 󱌈 (0xF1308)
     */
    public data object ACCOUNT_TIE_VOICE : MaterialDesignGlyphs(0xF1308)

    /**
     * 󱌉 (0xF1309)
     */
    public data object ACCOUNT_TIE_VOICE_OUTLINE : MaterialDesignGlyphs(0xF1309)

    /**
     * 󱌊 (0xF130A)
     */
    public data object ACCOUNT_TIE_VOICE_OFF : MaterialDesignGlyphs(0xF130A)

    /**
     * 󱌋 (0xF130B)
     */
    public data object ACCOUNT_TIE_VOICE_OFF_OUTLINE : MaterialDesignGlyphs(0xF130B)

    /**
     * 󱌌 (0xF130C)
     */
    public data object BEER_OUTLINE : MaterialDesignGlyphs(0xF130C)

    /**
     * 󱌍 (0xF130D)
     */
    public data object GLASS_PINT_OUTLINE : MaterialDesignGlyphs(0xF130D)

    /**
     * 󱌎 (0xF130E)
     */
    public data object COFFEE_TO_GO_OUTLINE : MaterialDesignGlyphs(0xF130E)

    /**
     * 󱌏 (0xF130F)
     */
    public data object CUP_OUTLINE : MaterialDesignGlyphs(0xF130F)

    /**
     * 󱌐 (0xF1310)
     */
    public data object BOTTLE_WINE_OUTLINE : MaterialDesignGlyphs(0xF1310)

    /**
     * 󱌑 (0xF1311)
     */
    public data object EARTH_ARROW_RIGHT : MaterialDesignGlyphs(0xF1311)

    /**
     * 󱌒 (0xF1312)
     */
    public data object KEY_ARROW_RIGHT : MaterialDesignGlyphs(0xF1312)

    /**
     * 󱌓 (0xF1313)
     */
    public data object FORMAT_COLOR_MARKER_CANCEL : MaterialDesignGlyphs(0xF1313)

    /**
     * 󱌔 (0xF1314)
     */
    public data object MOTHER_HEART : MaterialDesignGlyphs(0xF1314)

    /**
     * 󱌕 (0xF1315)
     */
    public data object CURRENCY_EUR_OFF : MaterialDesignGlyphs(0xF1315)

    /**
     * 󱌖 (0xF1316)
     */
    public data object SEMANTIC_WEB : MaterialDesignGlyphs(0xF1316)

    /**
     * 󱌗 (0xF1317)
     */
    public data object KETTLE_ALERT : MaterialDesignGlyphs(0xF1317)

    /**
     * 󱌘 (0xF1318)
     */
    public data object KETTLE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF1318)

    /**
     * 󱌙 (0xF1319)
     */
    public data object KETTLE_STEAM : MaterialDesignGlyphs(0xF1319)

    /**
     * 󱌚 (0xF131A)
     */
    public data object KETTLE_STEAM_OUTLINE : MaterialDesignGlyphs(0xF131A)

    /**
     * 󱌛 (0xF131B)
     */
    public data object KETTLE_OFF : MaterialDesignGlyphs(0xF131B)

    /**
     * 󱌜 (0xF131C)
     */
    public data object KETTLE_OFF_OUTLINE : MaterialDesignGlyphs(0xF131C)

    /**
     * 󱌝 (0xF131D)
     */
    public data object SIMPLE_ICONS : MaterialDesignGlyphs(0xF131D)

    /**
     * 󱌞 (0xF131E)
     */
    public data object BRIEFCASE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF131E)

    /**
     * 󱌟 (0xF131F)
     */
    public data object CLIPBOARD_PLUS_OUTLINE : MaterialDesignGlyphs(0xF131F)

    /**
     * 󱌠 (0xF1320)
     */
    public data object DOWNLOAD_LOCK : MaterialDesignGlyphs(0xF1320)

    /**
     * 󱌡 (0xF1321)
     */
    public data object DOWNLOAD_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1321)

    /**
     * 󱌢 (0xF1322)
     */
    public data object HAMMER_SCREWDRIVER : MaterialDesignGlyphs(0xF1322)

    /**
     * 󱌣 (0xF1323)
     */
    public data object HAMMER_WRENCH : MaterialDesignGlyphs(0xF1323)

    /**
     * 󱌤 (0xF1324)
     */
    public data object HYDRAULIC_OIL_LEVEL : MaterialDesignGlyphs(0xF1324)

    /**
     * 󱌥 (0xF1325)
     */
    public data object HYDRAULIC_OIL_TEMPERATURE : MaterialDesignGlyphs(0xF1325)

    /**
     * 󱌦 (0xF1326)
     */
    public data object MEDAL_OUTLINE : MaterialDesignGlyphs(0xF1326)

    /**
     * 󱌧 (0xF1327)
     */
    public data object RODENT : MaterialDesignGlyphs(0xF1327)

    /**
     * 󱌨 (0xF1328)
     */
    public data object ABJAD_ARABIC : MaterialDesignGlyphs(0xF1328)

    /**
     * 󱌩 (0xF1329)
     */
    public data object ABJAD_HEBREW : MaterialDesignGlyphs(0xF1329)

    /**
     * 󱌪 (0xF132A)
     */
    public data object ABUGIDA_DEVANAGARI : MaterialDesignGlyphs(0xF132A)

    /**
     * 󱌫 (0xF132B)
     */
    public data object ABUGIDA_THAI : MaterialDesignGlyphs(0xF132B)

    /**
     * 󱌬 (0xF132C)
     */
    public data object ALPHABET_AUREBESH : MaterialDesignGlyphs(0xF132C)

    /**
     * 󱌭 (0xF132D)
     */
    public data object ALPHABET_CYRILLIC : MaterialDesignGlyphs(0xF132D)

    /**
     * 󱌮 (0xF132E)
     */
    public data object ALPHABET_GREEK : MaterialDesignGlyphs(0xF132E)

    /**
     * 󱌯 (0xF132F)
     */
    public data object ALPHABET_LATIN : MaterialDesignGlyphs(0xF132F)

    /**
     * 󱌰 (0xF1330)
     */
    public data object ALPHABET_PIQAD : MaterialDesignGlyphs(0xF1330)

    /**
     * 󱌱 (0xF1331)
     */
    public data object IDEOGRAM_CJK : MaterialDesignGlyphs(0xF1331)

    /**
     * 󱌲 (0xF1332)
     */
    public data object IDEOGRAM_CJK_VARIANT : MaterialDesignGlyphs(0xF1332)

    /**
     * 󱌳 (0xF1333)
     */
    public data object SYLLABARY_HANGUL : MaterialDesignGlyphs(0xF1333)

    /**
     * 󱌴 (0xF1334)
     */
    public data object SYLLABARY_HIRAGANA : MaterialDesignGlyphs(0xF1334)

    /**
     * 󱌵 (0xF1335)
     */
    public data object SYLLABARY_KATAKANA : MaterialDesignGlyphs(0xF1335)

    /**
     * 󱌶 (0xF1336)
     */
    public data object SYLLABARY_KATAKANA_HALFWIDTH : MaterialDesignGlyphs(0xF1336)

    /**
     * 󱌷 (0xF1337)
     */
    public data object ALPHABET_TENGWAR : MaterialDesignGlyphs(0xF1337)

    /**
     * 󱌸 (0xF1338)
     */
    public data object HEAD_ALERT : MaterialDesignGlyphs(0xF1338)

    /**
     * 󱌹 (0xF1339)
     */
    public data object HEAD_ALERT_OUTLINE : MaterialDesignGlyphs(0xF1339)

    /**
     * 󱌺 (0xF133A)
     */
    public data object HEAD_CHECK : MaterialDesignGlyphs(0xF133A)

    /**
     * 󱌻 (0xF133B)
     */
    public data object HEAD_CHECK_OUTLINE : MaterialDesignGlyphs(0xF133B)

    /**
     * 󱌼 (0xF133C)
     */
    public data object HEAD_COG : MaterialDesignGlyphs(0xF133C)

    /**
     * 󱌽 (0xF133D)
     */
    public data object HEAD_COG_OUTLINE : MaterialDesignGlyphs(0xF133D)

    /**
     * 󱌾 (0xF133E)
     */
    public data object HEAD_DOTS_HORIZONTAL : MaterialDesignGlyphs(0xF133E)

    /**
     * 󱌿 (0xF133F)
     */
    public data object HEAD_DOTS_HORIZONTAL_OUTLINE : MaterialDesignGlyphs(0xF133F)

    /**
     * 󱍀 (0xF1340)
     */
    public data object HEAD_FLASH : MaterialDesignGlyphs(0xF1340)

    /**
     * 󱍁 (0xF1341)
     */
    public data object HEAD_FLASH_OUTLINE : MaterialDesignGlyphs(0xF1341)

    /**
     * 󱍂 (0xF1342)
     */
    public data object HEAD_HEART : MaterialDesignGlyphs(0xF1342)

    /**
     * 󱍃 (0xF1343)
     */
    public data object HEAD_HEART_OUTLINE : MaterialDesignGlyphs(0xF1343)

    /**
     * 󱍄 (0xF1344)
     */
    public data object HEAD_LIGHTBULB : MaterialDesignGlyphs(0xF1344)

    /**
     * 󱍅 (0xF1345)
     */
    public data object HEAD_LIGHTBULB_OUTLINE : MaterialDesignGlyphs(0xF1345)

    /**
     * 󱍆 (0xF1346)
     */
    public data object HEAD_MINUS : MaterialDesignGlyphs(0xF1346)

    /**
     * 󱍇 (0xF1347)
     */
    public data object HEAD_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1347)

    /**
     * 󱍈 (0xF1348)
     */
    public data object HEAD_PLUS : MaterialDesignGlyphs(0xF1348)

    /**
     * 󱍉 (0xF1349)
     */
    public data object HEAD_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1349)

    /**
     * 󱍊 (0xF134A)
     */
    public data object HEAD_QUESTION : MaterialDesignGlyphs(0xF134A)

    /**
     * 󱍋 (0xF134B)
     */
    public data object HEAD_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF134B)

    /**
     * 󱍌 (0xF134C)
     */
    public data object HEAD_REMOVE : MaterialDesignGlyphs(0xF134C)

    /**
     * 󱍍 (0xF134D)
     */
    public data object HEAD_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF134D)

    /**
     * 󱍎 (0xF134E)
     */
    public data object HEAD_SNOWFLAKE : MaterialDesignGlyphs(0xF134E)

    /**
     * 󱍏 (0xF134F)
     */
    public data object HEAD_SNOWFLAKE_OUTLINE : MaterialDesignGlyphs(0xF134F)

    /**
     * 󱍐 (0xF1350)
     */
    public data object HEAD_SYNC : MaterialDesignGlyphs(0xF1350)

    /**
     * 󱍑 (0xF1351)
     */
    public data object HEAD_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1351)

    /**
     * 󱍒 (0xF1352)
     */
    public data object HVAC : MaterialDesignGlyphs(0xF1352)

    /**
     * 󱍓 (0xF1353)
     */
    public data object PENCIL_RULER : MaterialDesignGlyphs(0xF1353)

    /**
     * 󱍔 (0xF1354)
     */
    public data object PIPE_WRENCH : MaterialDesignGlyphs(0xF1354)

    /**
     * 󱍕 (0xF1355)
     */
    public data object WIDGETS_OUTLINE : MaterialDesignGlyphs(0xF1355)

    /**
     * 󱍖 (0xF1356)
     */
    public data object TELEVISION_AMBIENT_LIGHT : MaterialDesignGlyphs(0xF1356)

    /**
     * 󱍗 (0xF1357)
     */
    public data object PROPANE_TANK : MaterialDesignGlyphs(0xF1357)

    /**
     * 󱍘 (0xF1358)
     */
    public data object PROPANE_TANK_OUTLINE : MaterialDesignGlyphs(0xF1358)

    /**
     * 󱍙 (0xF1359)
     */
    public data object FOLDER_MUSIC : MaterialDesignGlyphs(0xF1359)

    /**
     * 󱍚 (0xF135A)
     */
    public data object FOLDER_MUSIC_OUTLINE : MaterialDesignGlyphs(0xF135A)

    /**
     * 󱍛 (0xF135B)
     */
    public data object KLINGON : MaterialDesignGlyphs(0xF135B)

    /**
     * 󱍜 (0xF135C)
     */
    public data object PALETTE_SWATCH_OUTLINE : MaterialDesignGlyphs(0xF135C)

    /**
     * 󱍝 (0xF135D)
     */
    public data object FORM_TEXTBOX_LOCK : MaterialDesignGlyphs(0xF135D)

    /**
     * 󱍞 (0xF135E)
     */
    public data object HEAD : MaterialDesignGlyphs(0xF135E)

    /**
     * 󱍟 (0xF135F)
     */
    public data object HEAD_OUTLINE : MaterialDesignGlyphs(0xF135F)

    /**
     * 󱍠 (0xF1360)
     */
    public data object SHIELD_HALF : MaterialDesignGlyphs(0xF1360)

    /**
     * 󱍡 (0xF1361)
     */
    public data object STORE_OUTLINE : MaterialDesignGlyphs(0xF1361)

    /**
     * 󱍢 (0xF1362)
     */
    public data object GOOGLE_DOWNASAUR : MaterialDesignGlyphs(0xF1362)

    /**
     * 󱍣 (0xF1363)
     */
    public data object BOTTLE_SODA_CLASSIC_OUTLINE : MaterialDesignGlyphs(0xF1363)

    /**
     * 󱍤 (0xF1364)
     */
    public data object STICKER : MaterialDesignGlyphs(0xF1364)

    /**
     * 󱍥 (0xF1365)
     */
    public data object STICKER_ALERT : MaterialDesignGlyphs(0xF1365)

    /**
     * 󱍦 (0xF1366)
     */
    public data object STICKER_ALERT_OUTLINE : MaterialDesignGlyphs(0xF1366)

    /**
     * 󱍧 (0xF1367)
     */
    public data object STICKER_CHECK : MaterialDesignGlyphs(0xF1367)

    /**
     * 󱍨 (0xF1368)
     */
    public data object STICKER_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1368)

    /**
     * 󱍩 (0xF1369)
     */
    public data object STICKER_MINUS : MaterialDesignGlyphs(0xF1369)

    /**
     * 󱍪 (0xF136A)
     */
    public data object STICKER_MINUS_OUTLINE : MaterialDesignGlyphs(0xF136A)

    /**
     * 󱍫 (0xF136B)
     */
    public data object STICKER_OUTLINE : MaterialDesignGlyphs(0xF136B)

    /**
     * 󱍬 (0xF136C)
     */
    public data object STICKER_PLUS : MaterialDesignGlyphs(0xF136C)

    /**
     * 󱍭 (0xF136D)
     */
    public data object STICKER_PLUS_OUTLINE : MaterialDesignGlyphs(0xF136D)

    /**
     * 󱍮 (0xF136E)
     */
    public data object STICKER_REMOVE : MaterialDesignGlyphs(0xF136E)

    /**
     * 󱍯 (0xF136F)
     */
    public data object STICKER_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF136F)

    /**
     * 󱍰 (0xF1370)
     */
    public data object ACCOUNT_COG : MaterialDesignGlyphs(0xF1370)

    /**
     * 󱍱 (0xF1371)
     */
    public data object ACCOUNT_COG_OUTLINE : MaterialDesignGlyphs(0xF1371)

    /**
     * 󱍲 (0xF1372)
     */
    public data object ACCOUNT_DETAILS_OUTLINE : MaterialDesignGlyphs(0xF1372)

    /**
     * 󱍳 (0xF1373)
     */
    public data object UPLOAD_LOCK : MaterialDesignGlyphs(0xF1373)

    /**
     * 󱍴 (0xF1374)
     */
    public data object UPLOAD_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1374)

    /**
     * 󱍵 (0xF1375)
     */
    public data object LABEL_MULTIPLE : MaterialDesignGlyphs(0xF1375)

    /**
     * 󱍶 (0xF1376)
     */
    public data object LABEL_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1376)

    /**
     * 󱍷 (0xF1377)
     */
    public data object REFRESH_CIRCLE : MaterialDesignGlyphs(0xF1377)

    /**
     * 󱍸 (0xF1378)
     */
    public data object SYNC_CIRCLE : MaterialDesignGlyphs(0xF1378)

    /**
     * 󱍹 (0xF1379)
     */
    public data object BOOKMARK_MUSIC_OUTLINE : MaterialDesignGlyphs(0xF1379)

    /**
     * 󱍺 (0xF137A)
     */
    public data object BOOKMARK_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF137A)

    /**
     * 󱍻 (0xF137B)
     */
    public data object BOOKMARK_CHECK_OUTLINE : MaterialDesignGlyphs(0xF137B)

    /**
     * 󱍼 (0xF137C)
     */
    public data object TRAFFIC_CONE : MaterialDesignGlyphs(0xF137C)

    /**
     * 󱍽 (0xF137D)
     */
    public data object CUP_OFF_OUTLINE : MaterialDesignGlyphs(0xF137D)

    /**
     * 󱍾 (0xF137E)
     */
    public data object AUTO_DOWNLOAD : MaterialDesignGlyphs(0xF137E)

    /**
     * 󱍿 (0xF137F)
     */
    public data object SHURIKEN : MaterialDesignGlyphs(0xF137F)

    /**
     * 󱎀 (0xF1380)
     */
    public data object CHART_PPF : MaterialDesignGlyphs(0xF1380)

    /**
     * 󱎁 (0xF1381)
     */
    public data object ELEVATOR_PASSENGER : MaterialDesignGlyphs(0xF1381)

    /**
     * 󱎂 (0xF1382)
     */
    public data object COMPASS_ROSE : MaterialDesignGlyphs(0xF1382)

    /**
     * 󱎃 (0xF1383)
     */
    public data object SPACE_STATION : MaterialDesignGlyphs(0xF1383)

    /**
     * 󱎄 (0xF1384)
     */
    public data object ORDER_BOOL_DESCENDING : MaterialDesignGlyphs(0xF1384)

    /**
     * 󱎅 (0xF1385)
     */
    public data object SORT_BOOL_ASCENDING : MaterialDesignGlyphs(0xF1385)

    /**
     * 󱎆 (0xF1386)
     */
    public data object SORT_BOOL_ASCENDING_VARIANT : MaterialDesignGlyphs(0xF1386)

    /**
     * 󱎇 (0xF1387)
     */
    public data object SORT_BOOL_DESCENDING : MaterialDesignGlyphs(0xF1387)

    /**
     * 󱎈 (0xF1388)
     */
    public data object SORT_BOOL_DESCENDING_VARIANT : MaterialDesignGlyphs(0xF1388)

    /**
     * 󱎉 (0xF1389)
     */
    public data object SORT_NUMERIC_ASCENDING : MaterialDesignGlyphs(0xF1389)

    /**
     * 󱎊 (0xF138A)
     */
    public data object SORT_NUMERIC_DESCENDING : MaterialDesignGlyphs(0xF138A)

    /**
     * 󱎋 (0xF138B)
     */
    public data object HUMAN_BABY_CHANGING_TABLE : MaterialDesignGlyphs(0xF138B)

    /**
     * 󱎌 (0xF138C)
     */
    public data object HUMAN_MALE_CHILD : MaterialDesignGlyphs(0xF138C)

    /**
     * 󱎍 (0xF138D)
     */
    public data object HUMAN_WHEELCHAIR : MaterialDesignGlyphs(0xF138D)

    /**
     * 󱎎 (0xF138E)
     */
    public data object MICROSOFT_ACCESS : MaterialDesignGlyphs(0xF138E)

    /**
     * 󱎏 (0xF138F)
     */
    public data object MICROSOFT_EXCEL : MaterialDesignGlyphs(0xF138F)

    /**
     * 󱎐 (0xF1390)
     */
    public data object MICROSOFT_POWERPOINT : MaterialDesignGlyphs(0xF1390)

    /**
     * 󱎑 (0xF1391)
     */
    public data object MICROSOFT_SHAREPOINT : MaterialDesignGlyphs(0xF1391)

    /**
     * 󱎒 (0xF1392)
     */
    public data object MICROSOFT_WORD : MaterialDesignGlyphs(0xF1392)

    /**
     * 󱎓 (0xF1393)
     */
    public data object NINTENDO_GAME_BOY : MaterialDesignGlyphs(0xF1393)

    /**
     * 󱎔 (0xF1394)
     */
    public data object CABLE_DATA : MaterialDesignGlyphs(0xF1394)

    /**
     * 󱎕 (0xF1395)
     */
    public data object CIRCLE_HALF : MaterialDesignGlyphs(0xF1395)

    /**
     * 󱎖 (0xF1396)
     */
    public data object CIRCLE_HALF_FULL : MaterialDesignGlyphs(0xF1396)

    /**
     * 󱎗 (0xF1397)
     */
    public data object CELLPHONE_CHARGING : MaterialDesignGlyphs(0xF1397)

    /**
     * 󱎘 (0xF1398)
     */
    public data object CLOSE_THICK : MaterialDesignGlyphs(0xF1398)

    /**
     * 󱎙 (0xF1399)
     */
    public data object ESCALATOR_BOX : MaterialDesignGlyphs(0xF1399)

    /**
     * 󱎚 (0xF139A)
     */
    public data object LOCK_CHECK : MaterialDesignGlyphs(0xF139A)

    /**
     * 󱎛 (0xF139B)
     */
    public data object LOCK_OPEN_ALERT : MaterialDesignGlyphs(0xF139B)

    /**
     * 󱎜 (0xF139C)
     */
    public data object LOCK_OPEN_CHECK : MaterialDesignGlyphs(0xF139C)

    /**
     * 󱎝 (0xF139D)
     */
    public data object RECYCLE_VARIANT : MaterialDesignGlyphs(0xF139D)

    /**
     * 󱎞 (0xF139E)
     */
    public data object STAIRS_BOX : MaterialDesignGlyphs(0xF139E)

    /**
     * 󱎟 (0xF139F)
     */
    public data object HAND_WATER : MaterialDesignGlyphs(0xF139F)

    /**
     * 󱎠 (0xF13A0)
     */
    public data object TABLE_REFRESH : MaterialDesignGlyphs(0xF13A0)

    /**
     * 󱎡 (0xF13A1)
     */
    public data object TABLE_SYNC : MaterialDesignGlyphs(0xF13A1)

    /**
     * 󱎢 (0xF13A2)
     */
    public data object SIZE_XXS : MaterialDesignGlyphs(0xF13A2)

    /**
     * 󱎣 (0xF13A3)
     */
    public data object SIZE_XS : MaterialDesignGlyphs(0xF13A3)

    /**
     * 󱎤 (0xF13A4)
     */
    public data object SIZE_S : MaterialDesignGlyphs(0xF13A4)

    /**
     * 󱎥 (0xF13A5)
     */
    public data object SIZE_M : MaterialDesignGlyphs(0xF13A5)

    /**
     * 󱎧 (0xF13A7)
     */
    public data object SIZE_XL : MaterialDesignGlyphs(0xF13A7)

    /**
     * 󱎨 (0xF13A8)
     */
    public data object SIZE_XXL : MaterialDesignGlyphs(0xF13A8)

    /**
     * 󱎩 (0xF13A9)
     */
    public data object SIZE_XXXL : MaterialDesignGlyphs(0xF13A9)

    /**
     * 󱎪 (0xF13AA)
     */
    public data object TICKET_CONFIRMATION_OUTLINE : MaterialDesignGlyphs(0xF13AA)

    /**
     * 󱎫 (0xF13AB)
     */
    public data object TIMER : MaterialDesignGlyphs(0xF13AB)

    /**
     * 󱎬 (0xF13AC)
     */
    public data object TIMER_OFF : MaterialDesignGlyphs(0xF13AC)

    /**
     * 󱎭 (0xF13AD)
     */
    public data object BOOK_ACCOUNT : MaterialDesignGlyphs(0xF13AD)

    /**
     * 󱎮 (0xF13AE)
     */
    public data object BOOK_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF13AE)

    /**
     * 󱎯 (0xF13AF)
     */
    public data object ROCKET_OUTLINE : MaterialDesignGlyphs(0xF13AF)

    /**
     * 󱎰 (0xF13B0)
     */
    public data object HOME_SEARCH : MaterialDesignGlyphs(0xF13B0)

    /**
     * 󱎱 (0xF13B1)
     */
    public data object HOME_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF13B1)

    /**
     * 󱎲 (0xF13B2)
     */
    public data object CAR_ARROW_LEFT : MaterialDesignGlyphs(0xF13B2)

    /**
     * 󱎳 (0xF13B3)
     */
    public data object CAR_ARROW_RIGHT : MaterialDesignGlyphs(0xF13B3)

    /**
     * 󱎴 (0xF13B4)
     */
    public data object MONITOR_EYE : MaterialDesignGlyphs(0xF13B4)

    /**
     * 󱎵 (0xF13B5)
     */
    public data object LIPSTICK : MaterialDesignGlyphs(0xF13B5)

    /**
     * 󱎶 (0xF13B6)
     */
    public data object VIRUS : MaterialDesignGlyphs(0xF13B6)

    /**
     * 󱎷 (0xF13B7)
     */
    public data object VIRUS_OUTLINE : MaterialDesignGlyphs(0xF13B7)

    /**
     * 󱎸 (0xF13B8)
     */
    public data object TEXT_SEARCH : MaterialDesignGlyphs(0xF13B8)

    /**
     * 󱎹 (0xF13B9)
     */
    public data object TABLE_ACCOUNT : MaterialDesignGlyphs(0xF13B9)

    /**
     * 󱎺 (0xF13BA)
     */
    public data object TABLE_ALERT : MaterialDesignGlyphs(0xF13BA)

    /**
     * 󱎻 (0xF13BB)
     */
    public data object TABLE_ARROW_DOWN : MaterialDesignGlyphs(0xF13BB)

    /**
     * 󱎼 (0xF13BC)
     */
    public data object TABLE_ARROW_LEFT : MaterialDesignGlyphs(0xF13BC)

    /**
     * 󱎽 (0xF13BD)
     */
    public data object TABLE_ARROW_RIGHT : MaterialDesignGlyphs(0xF13BD)

    /**
     * 󱎾 (0xF13BE)
     */
    public data object TABLE_ARROW_UP : MaterialDesignGlyphs(0xF13BE)

    /**
     * 󱎿 (0xF13BF)
     */
    public data object TABLE_CANCEL : MaterialDesignGlyphs(0xF13BF)

    /**
     * 󱏀 (0xF13C0)
     */
    public data object TABLE_CHECK : MaterialDesignGlyphs(0xF13C0)

    /**
     * 󱏁 (0xF13C1)
     */
    public data object TABLE_CLOCK : MaterialDesignGlyphs(0xF13C1)

    /**
     * 󱏂 (0xF13C2)
     */
    public data object TABLE_COG : MaterialDesignGlyphs(0xF13C2)

    /**
     * 󱏃 (0xF13C3)
     */
    public data object TABLE_EYE_OFF : MaterialDesignGlyphs(0xF13C3)

    /**
     * 󱏄 (0xF13C4)
     */
    public data object TABLE_HEART : MaterialDesignGlyphs(0xF13C4)

    /**
     * 󱏅 (0xF13C5)
     */
    public data object TABLE_KEY : MaterialDesignGlyphs(0xF13C5)

    /**
     * 󱏆 (0xF13C6)
     */
    public data object TABLE_LOCK : MaterialDesignGlyphs(0xF13C6)

    /**
     * 󱏇 (0xF13C7)
     */
    public data object TABLE_MINUS : MaterialDesignGlyphs(0xF13C7)

    /**
     * 󱏈 (0xF13C8)
     */
    public data object TABLE_MULTIPLE : MaterialDesignGlyphs(0xF13C8)

    /**
     * 󱏉 (0xF13C9)
     */
    public data object TABLE_NETWORK : MaterialDesignGlyphs(0xF13C9)

    /**
     * 󱏊 (0xF13CA)
     */
    public data object TABLE_OFF : MaterialDesignGlyphs(0xF13CA)

    /**
     * 󱏋 (0xF13CB)
     */
    public data object TABLE_STAR : MaterialDesignGlyphs(0xF13CB)

    /**
     * 󱏌 (0xF13CC)
     */
    public data object CAR_COG : MaterialDesignGlyphs(0xF13CC)

    /**
     * 󱏍 (0xF13CD)
     */
    public data object CAR_SETTINGS : MaterialDesignGlyphs(0xF13CD)

    /**
     * 󱏎 (0xF13CE)
     */
    public data object COG_OFF : MaterialDesignGlyphs(0xF13CE)

    /**
     * 󱏏 (0xF13CF)
     */
    public data object COG_OFF_OUTLINE : MaterialDesignGlyphs(0xF13CF)

    /**
     * 󱏐 (0xF13D0)
     */
    public data object CREDIT_CARD_CHECK : MaterialDesignGlyphs(0xF13D0)

    /**
     * 󱏑 (0xF13D1)
     */
    public data object CREDIT_CARD_CHECK_OUTLINE : MaterialDesignGlyphs(0xF13D1)

    /**
     * 󱏒 (0xF13D2)
     */
    public data object FILE_TREE_OUTLINE : MaterialDesignGlyphs(0xF13D2)

    /**
     * 󱏓 (0xF13D3)
     */
    public data object FOLDER_STAR_MULTIPLE : MaterialDesignGlyphs(0xF13D3)

    /**
     * 󱏔 (0xF13D4)
     */
    public data object FOLDER_STAR_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF13D4)

    /**
     * 󱏕 (0xF13D5)
     */
    public data object HOME_MINUS_OUTLINE : MaterialDesignGlyphs(0xF13D5)

    /**
     * 󱏖 (0xF13D6)
     */
    public data object HOME_PLUS_OUTLINE : MaterialDesignGlyphs(0xF13D6)

    /**
     * 󱏗 (0xF13D7)
     */
    public data object HOME_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF13D7)

    /**
     * 󱏘 (0xF13D8)
     */
    public data object SCAN_HELPER : MaterialDesignGlyphs(0xF13D8)

    /**
     * 󱏙 (0xF13D9)
     */
    public data object VIDEO_3D_OFF : MaterialDesignGlyphs(0xF13D9)

    /**
     * 󱏚 (0xF13DA)
     */
    public data object SHIELD_BUG : MaterialDesignGlyphs(0xF13DA)

    /**
     * 󱏛 (0xF13DB)
     */
    public data object SHIELD_BUG_OUTLINE : MaterialDesignGlyphs(0xF13DB)

    /**
     * 󱏜 (0xF13DC)
     */
    public data object EYEDROPPER_PLUS : MaterialDesignGlyphs(0xF13DC)

    /**
     * 󱏝 (0xF13DD)
     */
    public data object EYEDROPPER_MINUS : MaterialDesignGlyphs(0xF13DD)

    /**
     * 󱏞 (0xF13DE)
     */
    public data object EYEDROPPER_REMOVE : MaterialDesignGlyphs(0xF13DE)

    /**
     * 󱏟 (0xF13DF)
     */
    public data object EYEDROPPER_OFF : MaterialDesignGlyphs(0xF13DF)

    /**
     * 󱏠 (0xF13E0)
     */
    public data object BABY_BUGGY : MaterialDesignGlyphs(0xF13E0)

    /**
     * 󱏡 (0xF13E1)
     */
    public data object UMBRELLA_CLOSED_VARIANT : MaterialDesignGlyphs(0xF13E1)

    /**
     * 󱏢 (0xF13E2)
     */
    public data object UMBRELLA_CLOSED_OUTLINE : MaterialDesignGlyphs(0xF13E2)

    /**
     * 󱏣 (0xF13E3)
     */
    public data object EMAIL_OFF : MaterialDesignGlyphs(0xF13E3)

    /**
     * 󱏤 (0xF13E4)
     */
    public data object EMAIL_OFF_OUTLINE : MaterialDesignGlyphs(0xF13E4)

    /**
     * 󱏥 (0xF13E5)
     */
    public data object FOOD_VARIANT_OFF : MaterialDesignGlyphs(0xF13E5)

    /**
     * 󱏦 (0xF13E6)
     */
    public data object PLAY_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF13E6)

    /**
     * 󱏧 (0xF13E7)
     */
    public data object BELL_CANCEL : MaterialDesignGlyphs(0xF13E7)

    /**
     * 󱏨 (0xF13E8)
     */
    public data object BELL_CANCEL_OUTLINE : MaterialDesignGlyphs(0xF13E8)

    /**
     * 󱏩 (0xF13E9)
     */
    public data object BELL_MINUS : MaterialDesignGlyphs(0xF13E9)

    /**
     * 󱏪 (0xF13EA)
     */
    public data object BELL_MINUS_OUTLINE : MaterialDesignGlyphs(0xF13EA)

    /**
     * 󱏫 (0xF13EB)
     */
    public data object BELL_REMOVE : MaterialDesignGlyphs(0xF13EB)

    /**
     * 󱏬 (0xF13EC)
     */
    public data object BELL_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF13EC)

    /**
     * 󱏭 (0xF13ED)
     */
    public data object BEEHIVE_OFF_OUTLINE : MaterialDesignGlyphs(0xF13ED)

    /**
     * 󱏮 (0xF13EE)
     */
    public data object CHEESE_OFF : MaterialDesignGlyphs(0xF13EE)

    /**
     * 󱏯 (0xF13EF)
     */
    public data object CORN_OFF : MaterialDesignGlyphs(0xF13EF)

    /**
     * 󱏰 (0xF13F0)
     */
    public data object EGG_OFF : MaterialDesignGlyphs(0xF13F0)

    /**
     * 󱏱 (0xF13F1)
     */
    public data object EGG_OFF_OUTLINE : MaterialDesignGlyphs(0xF13F1)

    /**
     * 󱏲 (0xF13F2)
     */
    public data object EGG_OUTLINE : MaterialDesignGlyphs(0xF13F2)

    /**
     * 󱏳 (0xF13F3)
     */
    public data object FISH_OFF : MaterialDesignGlyphs(0xF13F3)

    /**
     * 󱏴 (0xF13F4)
     */
    public data object FLASK_EMPTY_OFF : MaterialDesignGlyphs(0xF13F4)

    /**
     * 󱏵 (0xF13F5)
     */
    public data object FLASK_EMPTY_OFF_OUTLINE : MaterialDesignGlyphs(0xF13F5)

    /**
     * 󱏶 (0xF13F6)
     */
    public data object FLASK_OFF : MaterialDesignGlyphs(0xF13F6)

    /**
     * 󱏷 (0xF13F7)
     */
    public data object FLASK_OFF_OUTLINE : MaterialDesignGlyphs(0xF13F7)

    /**
     * 󱏸 (0xF13F8)
     */
    public data object FRUIT_CHERRIES_OFF : MaterialDesignGlyphs(0xF13F8)

    /**
     * 󱏹 (0xF13F9)
     */
    public data object FRUIT_CITRUS_OFF : MaterialDesignGlyphs(0xF13F9)

    /**
     * 󱏺 (0xF13FA)
     */
    public data object MUSHROOM_OFF : MaterialDesignGlyphs(0xF13FA)

    /**
     * 󱏻 (0xF13FB)
     */
    public data object MUSHROOM_OFF_OUTLINE : MaterialDesignGlyphs(0xF13FB)

    /**
     * 󱏼 (0xF13FC)
     */
    public data object SOY_SAUCE_OFF : MaterialDesignGlyphs(0xF13FC)

    /**
     * 󱏽 (0xF13FD)
     */
    public data object SEED_OFF : MaterialDesignGlyphs(0xF13FD)

    /**
     * 󱏾 (0xF13FE)
     */
    public data object SEED_OFF_OUTLINE : MaterialDesignGlyphs(0xF13FE)

    /**
     * 󱏿 (0xF13FF)
     */
    public data object TAILWIND : MaterialDesignGlyphs(0xF13FF)

    /**
     * 󱐀 (0xF1400)
     */
    public data object FORM_DROPDOWN : MaterialDesignGlyphs(0xF1400)

    /**
     * 󱐁 (0xF1401)
     */
    public data object FORM_SELECT : MaterialDesignGlyphs(0xF1401)

    /**
     * 󱐂 (0xF1402)
     */
    public data object PUMP : MaterialDesignGlyphs(0xF1402)

    /**
     * 󱐃 (0xF1403)
     */
    public data object EARTH_PLUS : MaterialDesignGlyphs(0xF1403)

    /**
     * 󱐄 (0xF1404)
     */
    public data object EARTH_MINUS : MaterialDesignGlyphs(0xF1404)

    /**
     * 󱐅 (0xF1405)
     */
    public data object EARTH_REMOVE : MaterialDesignGlyphs(0xF1405)

    /**
     * 󱐆 (0xF1406)
     */
    public data object EARTH_BOX_PLUS : MaterialDesignGlyphs(0xF1406)

    /**
     * 󱐇 (0xF1407)
     */
    public data object EARTH_BOX_MINUS : MaterialDesignGlyphs(0xF1407)

    /**
     * 󱐈 (0xF1408)
     */
    public data object EARTH_BOX_REMOVE : MaterialDesignGlyphs(0xF1408)

    /**
     * 󱐉 (0xF1409)
     */
    public data object GAS_STATION_OFF : MaterialDesignGlyphs(0xF1409)

    /**
     * 󱐊 (0xF140A)
     */
    public data object GAS_STATION_OFF_OUTLINE : MaterialDesignGlyphs(0xF140A)

    /**
     * 󱐋 (0xF140B)
     */
    public data object LIGHTNING_BOLT : MaterialDesignGlyphs(0xF140B)

    /**
     * 󱐌 (0xF140C)
     */
    public data object LIGHTNING_BOLT_OUTLINE : MaterialDesignGlyphs(0xF140C)

    /**
     * 󱐍 (0xF140D)
     */
    public data object SMOKING_PIPE : MaterialDesignGlyphs(0xF140D)

    /**
     * 󱐎 (0xF140E)
     */
    public data object AXIS_ARROW_INFO : MaterialDesignGlyphs(0xF140E)

    /**
     * 󱐏 (0xF140F)
     */
    public data object CHAT_PLUS : MaterialDesignGlyphs(0xF140F)

    /**
     * 󱐐 (0xF1410)
     */
    public data object CHAT_MINUS : MaterialDesignGlyphs(0xF1410)

    /**
     * 󱐑 (0xF1411)
     */
    public data object CHAT_REMOVE : MaterialDesignGlyphs(0xF1411)

    /**
     * 󱐒 (0xF1412)
     */
    public data object CHAT_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1412)

    /**
     * 󱐓 (0xF1413)
     */
    public data object CHAT_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1413)

    /**
     * 󱐔 (0xF1414)
     */
    public data object CHAT_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1414)

    /**
     * 󱐕 (0xF1415)
     */
    public data object BUCKET : MaterialDesignGlyphs(0xF1415)

    /**
     * 󱐖 (0xF1416)
     */
    public data object BUCKET_OUTLINE : MaterialDesignGlyphs(0xF1416)

    /**
     * 󱐗 (0xF1417)
     */
    public data object PAIL : MaterialDesignGlyphs(0xF1417)

    /**
     * 󱐘 (0xF1418)
     */
    public data object IMAGE_REMOVE : MaterialDesignGlyphs(0xF1418)

    /**
     * 󱐙 (0xF1419)
     */
    public data object IMAGE_MINUS : MaterialDesignGlyphs(0xF1419)

    /**
     * 󱐚 (0xF141A)
     */
    public data object PINE_TREE_FIRE : MaterialDesignGlyphs(0xF141A)

    /**
     * 󱐛 (0xF141B)
     */
    public data object CIGAR_OFF : MaterialDesignGlyphs(0xF141B)

    /**
     * 󱐜 (0xF141C)
     */
    public data object CUBE_OFF : MaterialDesignGlyphs(0xF141C)

    /**
     * 󱐝 (0xF141D)
     */
    public data object CUBE_OFF_OUTLINE : MaterialDesignGlyphs(0xF141D)

    /**
     * 󱐞 (0xF141E)
     */
    public data object DOME_LIGHT : MaterialDesignGlyphs(0xF141E)

    /**
     * 󱐟 (0xF141F)
     */
    public data object FOOD_DRUMSTICK : MaterialDesignGlyphs(0xF141F)

    /**
     * 󱐠 (0xF1420)
     */
    public data object FOOD_DRUMSTICK_OUTLINE : MaterialDesignGlyphs(0xF1420)

    /**
     * 󱐡 (0xF1421)
     */
    public data object INCOGNITO_CIRCLE : MaterialDesignGlyphs(0xF1421)

    /**
     * 󱐢 (0xF1422)
     */
    public data object INCOGNITO_CIRCLE_OFF : MaterialDesignGlyphs(0xF1422)

    /**
     * 󱐣 (0xF1423)
     */
    public data object MICROWAVE_OFF : MaterialDesignGlyphs(0xF1423)

    /**
     * 󱐤 (0xF1424)
     */
    public data object POWER_PLUG_OFF_OUTLINE : MaterialDesignGlyphs(0xF1424)

    /**
     * 󱐥 (0xF1425)
     */
    public data object POWER_PLUG_OUTLINE : MaterialDesignGlyphs(0xF1425)

    /**
     * 󱐦 (0xF1426)
     */
    public data object PUZZLE_CHECK : MaterialDesignGlyphs(0xF1426)

    /**
     * 󱐧 (0xF1427)
     */
    public data object PUZZLE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1427)

    /**
     * 󱐨 (0xF1428)
     */
    public data object SMOKING_PIPE_OFF : MaterialDesignGlyphs(0xF1428)

    /**
     * 󱐩 (0xF1429)
     */
    public data object SPOON_SUGAR : MaterialDesignGlyphs(0xF1429)

    /**
     * 󱐪 (0xF142A)
     */
    public data object TABLE_SPLIT_CELL : MaterialDesignGlyphs(0xF142A)

    /**
     * 󱐫 (0xF142B)
     */
    public data object TICKET_PERCENT_OUTLINE : MaterialDesignGlyphs(0xF142B)

    /**
     * 󱐬 (0xF142C)
     */
    public data object FUSE_OFF : MaterialDesignGlyphs(0xF142C)

    /**
     * 󱐭 (0xF142D)
     */
    public data object FUSE_ALERT : MaterialDesignGlyphs(0xF142D)

    /**
     * 󱐮 (0xF142E)
     */
    public data object HEART_PLUS : MaterialDesignGlyphs(0xF142E)

    /**
     * 󱐯 (0xF142F)
     */
    public data object HEART_MINUS : MaterialDesignGlyphs(0xF142F)

    /**
     * 󱐰 (0xF1430)
     */
    public data object HEART_REMOVE : MaterialDesignGlyphs(0xF1430)

    /**
     * 󱐱 (0xF1431)
     */
    public data object HEART_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1431)

    /**
     * 󱐲 (0xF1432)
     */
    public data object HEART_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1432)

    /**
     * 󱐳 (0xF1433)
     */
    public data object HEART_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1433)

    /**
     * 󱐴 (0xF1434)
     */
    public data object HEART_OFF_OUTLINE : MaterialDesignGlyphs(0xF1434)

    /**
     * 󱐵 (0xF1435)
     */
    public data object MOTION_SENSOR_OFF : MaterialDesignGlyphs(0xF1435)

    /**
     * 󱐶 (0xF1436)
     */
    public data object PAIL_PLUS : MaterialDesignGlyphs(0xF1436)

    /**
     * 󱐷 (0xF1437)
     */
    public data object PAIL_MINUS : MaterialDesignGlyphs(0xF1437)

    /**
     * 󱐸 (0xF1438)
     */
    public data object PAIL_REMOVE : MaterialDesignGlyphs(0xF1438)

    /**
     * 󱐹 (0xF1439)
     */
    public data object PAIL_OFF : MaterialDesignGlyphs(0xF1439)

    /**
     * 󱐺 (0xF143A)
     */
    public data object PAIL_OUTLINE : MaterialDesignGlyphs(0xF143A)

    /**
     * 󱐻 (0xF143B)
     */
    public data object PAIL_PLUS_OUTLINE : MaterialDesignGlyphs(0xF143B)

    /**
     * 󱐼 (0xF143C)
     */
    public data object PAIL_MINUS_OUTLINE : MaterialDesignGlyphs(0xF143C)

    /**
     * 󱐽 (0xF143D)
     */
    public data object PAIL_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF143D)

    /**
     * 󱐾 (0xF143E)
     */
    public data object PAIL_OFF_OUTLINE : MaterialDesignGlyphs(0xF143E)

    /**
     * 󱐿 (0xF143F)
     */
    public data object CLOCK_TIME_ONE : MaterialDesignGlyphs(0xF143F)

    /**
     * 󱑀 (0xF1440)
     */
    public data object CLOCK_TIME_TWO : MaterialDesignGlyphs(0xF1440)

    /**
     * 󱑁 (0xF1441)
     */
    public data object CLOCK_TIME_THREE : MaterialDesignGlyphs(0xF1441)

    /**
     * 󱑂 (0xF1442)
     */
    public data object CLOCK_TIME_FOUR : MaterialDesignGlyphs(0xF1442)

    /**
     * 󱑃 (0xF1443)
     */
    public data object CLOCK_TIME_FIVE : MaterialDesignGlyphs(0xF1443)

    /**
     * 󱑄 (0xF1444)
     */
    public data object CLOCK_TIME_SIX : MaterialDesignGlyphs(0xF1444)

    /**
     * 󱑅 (0xF1445)
     */
    public data object CLOCK_TIME_SEVEN : MaterialDesignGlyphs(0xF1445)

    /**
     * 󱑆 (0xF1446)
     */
    public data object CLOCK_TIME_EIGHT : MaterialDesignGlyphs(0xF1446)

    /**
     * 󱑇 (0xF1447)
     */
    public data object CLOCK_TIME_NINE : MaterialDesignGlyphs(0xF1447)

    /**
     * 󱑈 (0xF1448)
     */
    public data object CLOCK_TIME_TEN : MaterialDesignGlyphs(0xF1448)

    /**
     * 󱑉 (0xF1449)
     */
    public data object CLOCK_TIME_ELEVEN : MaterialDesignGlyphs(0xF1449)

    /**
     * 󱑊 (0xF144A)
     */
    public data object CLOCK_TIME_TWELVE : MaterialDesignGlyphs(0xF144A)

    /**
     * 󱑋 (0xF144B)
     */
    public data object CLOCK_TIME_ONE_OUTLINE : MaterialDesignGlyphs(0xF144B)

    /**
     * 󱑌 (0xF144C)
     */
    public data object CLOCK_TIME_TWO_OUTLINE : MaterialDesignGlyphs(0xF144C)

    /**
     * 󱑍 (0xF144D)
     */
    public data object CLOCK_TIME_THREE_OUTLINE : MaterialDesignGlyphs(0xF144D)

    /**
     * 󱑎 (0xF144E)
     */
    public data object CLOCK_TIME_FOUR_OUTLINE : MaterialDesignGlyphs(0xF144E)

    /**
     * 󱑏 (0xF144F)
     */
    public data object CLOCK_TIME_FIVE_OUTLINE : MaterialDesignGlyphs(0xF144F)

    /**
     * 󱑐 (0xF1450)
     */
    public data object CLOCK_TIME_SIX_OUTLINE : MaterialDesignGlyphs(0xF1450)

    /**
     * 󱑑 (0xF1451)
     */
    public data object CLOCK_TIME_SEVEN_OUTLINE : MaterialDesignGlyphs(0xF1451)

    /**
     * 󱑒 (0xF1452)
     */
    public data object CLOCK_TIME_EIGHT_OUTLINE : MaterialDesignGlyphs(0xF1452)

    /**
     * 󱑓 (0xF1453)
     */
    public data object CLOCK_TIME_NINE_OUTLINE : MaterialDesignGlyphs(0xF1453)

    /**
     * 󱑔 (0xF1454)
     */
    public data object CLOCK_TIME_TEN_OUTLINE : MaterialDesignGlyphs(0xF1454)

    /**
     * 󱑕 (0xF1455)
     */
    public data object CLOCK_TIME_ELEVEN_OUTLINE : MaterialDesignGlyphs(0xF1455)

    /**
     * 󱑖 (0xF1456)
     */
    public data object CLOCK_TIME_TWELVE_OUTLINE : MaterialDesignGlyphs(0xF1456)

    /**
     * 󱑗 (0xF1457)
     */
    public data object PRINTER_SEARCH : MaterialDesignGlyphs(0xF1457)

    /**
     * 󱑘 (0xF1458)
     */
    public data object PRINTER_EYE : MaterialDesignGlyphs(0xF1458)

    /**
     * 󱑙 (0xF1459)
     */
    public data object MINUS_CIRCLE_OFF : MaterialDesignGlyphs(0xF1459)

    /**
     * 󱑚 (0xF145A)
     */
    public data object MINUS_CIRCLE_OFF_OUTLINE : MaterialDesignGlyphs(0xF145A)

    /**
     * 󱑛 (0xF145B)
     */
    public data object CONTENT_SAVE_COG : MaterialDesignGlyphs(0xF145B)

    /**
     * 󱑜 (0xF145C)
     */
    public data object CONTENT_SAVE_COG_OUTLINE : MaterialDesignGlyphs(0xF145C)

    /**
     * 󱑝 (0xF145D)
     */
    public data object SET_SQUARE : MaterialDesignGlyphs(0xF145D)

    /**
     * 󱑞 (0xF145E)
     */
    public data object COG_REFRESH : MaterialDesignGlyphs(0xF145E)

    /**
     * 󱑟 (0xF145F)
     */
    public data object COG_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF145F)

    /**
     * 󱑠 (0xF1460)
     */
    public data object COG_SYNC : MaterialDesignGlyphs(0xF1460)

    /**
     * 󱑡 (0xF1461)
     */
    public data object COG_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1461)

    /**
     * 󱑢 (0xF1462)
     */
    public data object DOWNLOAD_BOX : MaterialDesignGlyphs(0xF1462)

    /**
     * 󱑣 (0xF1463)
     */
    public data object DOWNLOAD_BOX_OUTLINE : MaterialDesignGlyphs(0xF1463)

    /**
     * 󱑤 (0xF1464)
     */
    public data object DOWNLOAD_CIRCLE : MaterialDesignGlyphs(0xF1464)

    /**
     * 󱑥 (0xF1465)
     */
    public data object DOWNLOAD_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1465)

    /**
     * 󱑦 (0xF1466)
     */
    public data object AIR_HUMIDIFIER_OFF : MaterialDesignGlyphs(0xF1466)

    /**
     * 󱑧 (0xF1467)
     */
    public data object CHILI_OFF : MaterialDesignGlyphs(0xF1467)

    /**
     * 󱑨 (0xF1468)
     */
    public data object FOOD_DRUMSTICK_OFF : MaterialDesignGlyphs(0xF1468)

    /**
     * 󱑩 (0xF1469)
     */
    public data object FOOD_DRUMSTICK_OFF_OUTLINE : MaterialDesignGlyphs(0xF1469)

    /**
     * 󱑪 (0xF146A)
     */
    public data object FOOD_STEAK : MaterialDesignGlyphs(0xF146A)

    /**
     * 󱑫 (0xF146B)
     */
    public data object FOOD_STEAK_OFF : MaterialDesignGlyphs(0xF146B)

    /**
     * 󱑬 (0xF146C)
     */
    public data object FAN_ALERT : MaterialDesignGlyphs(0xF146C)

    /**
     * 󱑭 (0xF146D)
     */
    public data object FAN_CHEVRON_DOWN : MaterialDesignGlyphs(0xF146D)

    /**
     * 󱑮 (0xF146E)
     */
    public data object FAN_CHEVRON_UP : MaterialDesignGlyphs(0xF146E)

    /**
     * 󱑯 (0xF146F)
     */
    public data object FAN_PLUS : MaterialDesignGlyphs(0xF146F)

    /**
     * 󱑰 (0xF1470)
     */
    public data object FAN_MINUS : MaterialDesignGlyphs(0xF1470)

    /**
     * 󱑱 (0xF1471)
     */
    public data object FAN_REMOVE : MaterialDesignGlyphs(0xF1471)

    /**
     * 󱑲 (0xF1472)
     */
    public data object FAN_SPEED_1 : MaterialDesignGlyphs(0xF1472)

    /**
     * 󱑳 (0xF1473)
     */
    public data object FAN_SPEED_2 : MaterialDesignGlyphs(0xF1473)

    /**
     * 󱑴 (0xF1474)
     */
    public data object FAN_SPEED_3 : MaterialDesignGlyphs(0xF1474)

    /**
     * 󱑵 (0xF1475)
     */
    public data object RUG : MaterialDesignGlyphs(0xF1475)

    /**
     * 󱑶 (0xF1476)
     */
    public data object LINGERIE : MaterialDesignGlyphs(0xF1476)

    /**
     * 󱑷 (0xF1477)
     */
    public data object WIZARD_HAT : MaterialDesignGlyphs(0xF1477)

    /**
     * 󱑸 (0xF1478)
     */
    public data object HOURS_24 : MaterialDesignGlyphs(0xF1478)

    /**
     * 󱑹 (0xF1479)
     */
    public data object COSINE_WAVE : MaterialDesignGlyphs(0xF1479)

    /**
     * 󱑺 (0xF147A)
     */
    public data object SAWTOOTH_WAVE : MaterialDesignGlyphs(0xF147A)

    /**
     * 󱑻 (0xF147B)
     */
    public data object SQUARE_WAVE : MaterialDesignGlyphs(0xF147B)

    /**
     * 󱑼 (0xF147C)
     */
    public data object TRIANGLE_WAVE : MaterialDesignGlyphs(0xF147C)

    /**
     * 󱑽 (0xF147D)
     */
    public data object WAVEFORM : MaterialDesignGlyphs(0xF147D)

    /**
     * 󱑾 (0xF147E)
     */
    public data object FOLDER_MULTIPLE_PLUS : MaterialDesignGlyphs(0xF147E)

    /**
     * 󱑿 (0xF147F)
     */
    public data object FOLDER_MULTIPLE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF147F)

    /**
     * 󱒀 (0xF1480)
     */
    public data object CURRENT_AC : MaterialDesignGlyphs(0xF1480)

    /**
     * 󱒁 (0xF1481)
     */
    public data object WATERING_CAN : MaterialDesignGlyphs(0xF1481)

    /**
     * 󱒂 (0xF1482)
     */
    public data object WATERING_CAN_OUTLINE : MaterialDesignGlyphs(0xF1482)

    /**
     * 󱒃 (0xF1483)
     */
    public data object MONITOR_SHARE : MaterialDesignGlyphs(0xF1483)

    /**
     * 󱒄 (0xF1484)
     */
    public data object LASER_POINTER : MaterialDesignGlyphs(0xF1484)

    /**
     * 󱒅 (0xF1485)
     */
    public data object VIEW_ARRAY_OUTLINE : MaterialDesignGlyphs(0xF1485)

    /**
     * 󱒆 (0xF1486)
     */
    public data object VIEW_CAROUSEL_OUTLINE : MaterialDesignGlyphs(0xF1486)

    /**
     * 󱒇 (0xF1487)
     */
    public data object VIEW_COLUMN_OUTLINE : MaterialDesignGlyphs(0xF1487)

    /**
     * 󱒈 (0xF1488)
     */
    public data object VIEW_COMFY_OUTLINE : MaterialDesignGlyphs(0xF1488)

    /**
     * 󱒉 (0xF1489)
     */
    public data object VIEW_DASHBOARD_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1489)

    /**
     * 󱒊 (0xF148A)
     */
    public data object VIEW_DAY_OUTLINE : MaterialDesignGlyphs(0xF148A)

    /**
     * 󱒋 (0xF148B)
     */
    public data object VIEW_LIST_OUTLINE : MaterialDesignGlyphs(0xF148B)

    /**
     * 󱒌 (0xF148C)
     */
    public data object VIEW_MODULE_OUTLINE : MaterialDesignGlyphs(0xF148C)

    /**
     * 󱒍 (0xF148D)
     */
    public data object VIEW_PARALLEL_OUTLINE : MaterialDesignGlyphs(0xF148D)

    /**
     * 󱒎 (0xF148E)
     */
    public data object VIEW_QUILT_OUTLINE : MaterialDesignGlyphs(0xF148E)

    /**
     * 󱒏 (0xF148F)
     */
    public data object VIEW_SEQUENTIAL_OUTLINE : MaterialDesignGlyphs(0xF148F)

    /**
     * 󱒐 (0xF1490)
     */
    public data object VIEW_STREAM_OUTLINE : MaterialDesignGlyphs(0xF1490)

    /**
     * 󱒑 (0xF1491)
     */
    public data object VIEW_WEEK_OUTLINE : MaterialDesignGlyphs(0xF1491)

    /**
     * 󱒒 (0xF1492)
     */
    public data object COMPARE_HORIZONTAL : MaterialDesignGlyphs(0xF1492)

    /**
     * 󱒓 (0xF1493)
     */
    public data object COMPARE_VERTICAL : MaterialDesignGlyphs(0xF1493)

    /**
     * 󱒔 (0xF1494)
     */
    public data object BRIEFCASE_VARIANT : MaterialDesignGlyphs(0xF1494)

    /**
     * 󱒕 (0xF1495)
     */
    public data object BRIEFCASE_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1495)

    /**
     * 󱒖 (0xF1496)
     */
    public data object RELATION_MANY_TO_MANY : MaterialDesignGlyphs(0xF1496)

    /**
     * 󱒗 (0xF1497)
     */
    public data object RELATION_MANY_TO_ONE : MaterialDesignGlyphs(0xF1497)

    /**
     * 󱒘 (0xF1498)
     */
    public data object RELATION_MANY_TO_ONE_OR_MANY : MaterialDesignGlyphs(0xF1498)

    /**
     * 󱒙 (0xF1499)
     */
    public data object RELATION_MANY_TO_ONLY_ONE : MaterialDesignGlyphs(0xF1499)

    /**
     * 󱒚 (0xF149A)
     */
    public data object RELATION_MANY_TO_ZERO_OR_MANY : MaterialDesignGlyphs(0xF149A)

    /**
     * 󱒛 (0xF149B)
     */
    public data object RELATION_MANY_TO_ZERO_OR_ONE : MaterialDesignGlyphs(0xF149B)

    /**
     * 󱒜 (0xF149C)
     */
    public data object RELATION_ONE_OR_MANY_TO_MANY : MaterialDesignGlyphs(0xF149C)

    /**
     * 󱒝 (0xF149D)
     */
    public data object RELATION_ONE_OR_MANY_TO_ONE : MaterialDesignGlyphs(0xF149D)

    /**
     * 󱒞 (0xF149E)
     */
    public data object RELATION_ONE_OR_MANY_TO_ONE_OR_MANY : MaterialDesignGlyphs(0xF149E)

    /**
     * 󱒟 (0xF149F)
     */
    public data object RELATION_ONE_OR_MANY_TO_ONLY_ONE : MaterialDesignGlyphs(0xF149F)

    /**
     * 󱒠 (0xF14A0)
     */
    public data object RELATION_ONE_OR_MANY_TO_ZERO_OR_MANY : MaterialDesignGlyphs(0xF14A0)

    /**
     * 󱒡 (0xF14A1)
     */
    public data object RELATION_ONE_OR_MANY_TO_ZERO_OR_ONE : MaterialDesignGlyphs(0xF14A1)

    /**
     * 󱒢 (0xF14A2)
     */
    public data object RELATION_ONE_TO_MANY : MaterialDesignGlyphs(0xF14A2)

    /**
     * 󱒣 (0xF14A3)
     */
    public data object RELATION_ONE_TO_ONE : MaterialDesignGlyphs(0xF14A3)

    /**
     * 󱒤 (0xF14A4)
     */
    public data object RELATION_ONE_TO_ONE_OR_MANY : MaterialDesignGlyphs(0xF14A4)

    /**
     * 󱒥 (0xF14A5)
     */
    public data object RELATION_ONE_TO_ONLY_ONE : MaterialDesignGlyphs(0xF14A5)

    /**
     * 󱒦 (0xF14A6)
     */
    public data object RELATION_ONE_TO_ZERO_OR_MANY : MaterialDesignGlyphs(0xF14A6)

    /**
     * 󱒧 (0xF14A7)
     */
    public data object RELATION_ONE_TO_ZERO_OR_ONE : MaterialDesignGlyphs(0xF14A7)

    /**
     * 󱒨 (0xF14A8)
     */
    public data object RELATION_ONLY_ONE_TO_MANY : MaterialDesignGlyphs(0xF14A8)

    /**
     * 󱒩 (0xF14A9)
     */
    public data object RELATION_ONLY_ONE_TO_ONE : MaterialDesignGlyphs(0xF14A9)

    /**
     * 󱒪 (0xF14AA)
     */
    public data object RELATION_ONLY_ONE_TO_ONE_OR_MANY : MaterialDesignGlyphs(0xF14AA)

    /**
     * 󱒫 (0xF14AB)
     */
    public data object RELATION_ONLY_ONE_TO_ONLY_ONE : MaterialDesignGlyphs(0xF14AB)

    /**
     * 󱒬 (0xF14AC)
     */
    public data object RELATION_ONLY_ONE_TO_ZERO_OR_MANY : MaterialDesignGlyphs(0xF14AC)

    /**
     * 󱒭 (0xF14AD)
     */
    public data object RELATION_ONLY_ONE_TO_ZERO_OR_ONE : MaterialDesignGlyphs(0xF14AD)

    /**
     * 󱒮 (0xF14AE)
     */
    public data object RELATION_ZERO_OR_MANY_TO_MANY : MaterialDesignGlyphs(0xF14AE)

    /**
     * 󱒯 (0xF14AF)
     */
    public data object RELATION_ZERO_OR_MANY_TO_ONE : MaterialDesignGlyphs(0xF14AF)

    /**
     * 󱒰 (0xF14B0)
     */
    public data object RELATION_ZERO_OR_MANY_TO_ONE_OR_MANY : MaterialDesignGlyphs(0xF14B0)

    /**
     * 󱒱 (0xF14B1)
     */
    public data object RELATION_ZERO_OR_MANY_TO_ONLY_ONE : MaterialDesignGlyphs(0xF14B1)

    /**
     * 󱒲 (0xF14B2)
     */
    public data object RELATION_ZERO_OR_MANY_TO_ZERO_OR_MANY : MaterialDesignGlyphs(0xF14B2)

    /**
     * 󱒳 (0xF14B3)
     */
    public data object RELATION_ZERO_OR_MANY_TO_ZERO_OR_ONE : MaterialDesignGlyphs(0xF14B3)

    /**
     * 󱒴 (0xF14B4)
     */
    public data object RELATION_ZERO_OR_ONE_TO_MANY : MaterialDesignGlyphs(0xF14B4)

    /**
     * 󱒵 (0xF14B5)
     */
    public data object RELATION_ZERO_OR_ONE_TO_ONE : MaterialDesignGlyphs(0xF14B5)

    /**
     * 󱒶 (0xF14B6)
     */
    public data object RELATION_ZERO_OR_ONE_TO_ONE_OR_MANY : MaterialDesignGlyphs(0xF14B6)

    /**
     * 󱒷 (0xF14B7)
     */
    public data object RELATION_ZERO_OR_ONE_TO_ONLY_ONE : MaterialDesignGlyphs(0xF14B7)

    /**
     * 󱒸 (0xF14B8)
     */
    public data object RELATION_ZERO_OR_ONE_TO_ZERO_OR_MANY : MaterialDesignGlyphs(0xF14B8)

    /**
     * 󱒹 (0xF14B9)
     */
    public data object RELATION_ZERO_OR_ONE_TO_ZERO_OR_ONE : MaterialDesignGlyphs(0xF14B9)

    /**
     * 󱒺 (0xF14BA)
     */
    public data object ALERT_PLUS : MaterialDesignGlyphs(0xF14BA)

    /**
     * 󱒻 (0xF14BB)
     */
    public data object ALERT_MINUS : MaterialDesignGlyphs(0xF14BB)

    /**
     * 󱒼 (0xF14BC)
     */
    public data object ALERT_REMOVE : MaterialDesignGlyphs(0xF14BC)

    /**
     * 󱒽 (0xF14BD)
     */
    public data object ALERT_PLUS_OUTLINE : MaterialDesignGlyphs(0xF14BD)

    /**
     * 󱒾 (0xF14BE)
     */
    public data object ALERT_MINUS_OUTLINE : MaterialDesignGlyphs(0xF14BE)

    /**
     * 󱒿 (0xF14BF)
     */
    public data object ALERT_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF14BF)

    /**
     * 󱓀 (0xF14C0)
     */
    public data object CARABINER : MaterialDesignGlyphs(0xF14C0)

    /**
     * 󱓁 (0xF14C1)
     */
    public data object FENCING : MaterialDesignGlyphs(0xF14C1)

    /**
     * 󱓂 (0xF14C2)
     */
    public data object SKATEBOARD : MaterialDesignGlyphs(0xF14C2)

    /**
     * 󱓃 (0xF14C3)
     */
    public data object POLO : MaterialDesignGlyphs(0xF14C3)

    /**
     * 󱓄 (0xF14C4)
     */
    public data object TRACTOR_VARIANT : MaterialDesignGlyphs(0xF14C4)

    /**
     * 󱓅 (0xF14C5)
     */
    public data object RADIOLOGY_BOX : MaterialDesignGlyphs(0xF14C5)

    /**
     * 󱓆 (0xF14C6)
     */
    public data object RADIOLOGY_BOX_OUTLINE : MaterialDesignGlyphs(0xF14C6)

    /**
     * 󱓇 (0xF14C7)
     */
    public data object SKULL_SCAN : MaterialDesignGlyphs(0xF14C7)

    /**
     * 󱓈 (0xF14C8)
     */
    public data object SKULL_SCAN_OUTLINE : MaterialDesignGlyphs(0xF14C8)

    /**
     * 󱓉 (0xF14C9)
     */
    public data object PLUS_MINUS_VARIANT : MaterialDesignGlyphs(0xF14C9)

    /**
     * 󱓊 (0xF14CA)
     */
    public data object SOURCE_BRANCH_PLUS : MaterialDesignGlyphs(0xF14CA)

    /**
     * 󱓋 (0xF14CB)
     */
    public data object SOURCE_BRANCH_MINUS : MaterialDesignGlyphs(0xF14CB)

    /**
     * 󱓌 (0xF14CC)
     */
    public data object SOURCE_BRANCH_REMOVE : MaterialDesignGlyphs(0xF14CC)

    /**
     * 󱓍 (0xF14CD)
     */
    public data object SOURCE_BRANCH_REFRESH : MaterialDesignGlyphs(0xF14CD)

    /**
     * 󱓎 (0xF14CE)
     */
    public data object SOURCE_BRANCH_SYNC : MaterialDesignGlyphs(0xF14CE)

    /**
     * 󱓏 (0xF14CF)
     */
    public data object SOURCE_BRANCH_CHECK : MaterialDesignGlyphs(0xF14CF)

    /**
     * 󱓐 (0xF14D0)
     */
    public data object PUZZLE_PLUS : MaterialDesignGlyphs(0xF14D0)

    /**
     * 󱓑 (0xF14D1)
     */
    public data object PUZZLE_MINUS : MaterialDesignGlyphs(0xF14D1)

    /**
     * 󱓒 (0xF14D2)
     */
    public data object PUZZLE_REMOVE : MaterialDesignGlyphs(0xF14D2)

    /**
     * 󱓓 (0xF14D3)
     */
    public data object PUZZLE_EDIT : MaterialDesignGlyphs(0xF14D3)

    /**
     * 󱓔 (0xF14D4)
     */
    public data object PUZZLE_HEART : MaterialDesignGlyphs(0xF14D4)

    /**
     * 󱓕 (0xF14D5)
     */
    public data object PUZZLE_STAR : MaterialDesignGlyphs(0xF14D5)

    /**
     * 󱓖 (0xF14D6)
     */
    public data object PUZZLE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF14D6)

    /**
     * 󱓗 (0xF14D7)
     */
    public data object PUZZLE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF14D7)

    /**
     * 󱓘 (0xF14D8)
     */
    public data object PUZZLE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF14D8)

    /**
     * 󱓙 (0xF14D9)
     */
    public data object PUZZLE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF14D9)

    /**
     * 󱓚 (0xF14DA)
     */
    public data object PUZZLE_HEART_OUTLINE : MaterialDesignGlyphs(0xF14DA)

    /**
     * 󱓛 (0xF14DB)
     */
    public data object PUZZLE_STAR_OUTLINE : MaterialDesignGlyphs(0xF14DB)

    /**
     * 󱓜 (0xF14DC)
     */
    public data object RHOMBUS_MEDIUM_OUTLINE : MaterialDesignGlyphs(0xF14DC)

    /**
     * 󱓝 (0xF14DD)
     */
    public data object RHOMBUS_SPLIT_OUTLINE : MaterialDesignGlyphs(0xF14DD)

    /**
     * 󱓞 (0xF14DE)
     */
    public data object ROCKET_LAUNCH : MaterialDesignGlyphs(0xF14DE)

    /**
     * 󱓟 (0xF14DF)
     */
    public data object ROCKET_LAUNCH_OUTLINE : MaterialDesignGlyphs(0xF14DF)

    /**
     * 󱓠 (0xF14E0)
     */
    public data object SET_MERGE : MaterialDesignGlyphs(0xF14E0)

    /**
     * 󱓡 (0xF14E1)
     */
    public data object SET_SPLIT : MaterialDesignGlyphs(0xF14E1)

    /**
     * 󱓢 (0xF14E2)
     */
    public data object BEEKEEPER : MaterialDesignGlyphs(0xF14E2)

    /**
     * 󱓣 (0xF14E3)
     */
    public data object SNOWFLAKE_OFF : MaterialDesignGlyphs(0xF14E3)

    /**
     * 󱓤 (0xF14E4)
     */
    public data object WEATHER_SUNNY_OFF : MaterialDesignGlyphs(0xF14E4)

    /**
     * 󱓥 (0xF14E5)
     */
    public data object CLIPBOARD_EDIT : MaterialDesignGlyphs(0xF14E5)

    /**
     * 󱓦 (0xF14E6)
     */
    public data object CLIPBOARD_EDIT_OUTLINE : MaterialDesignGlyphs(0xF14E6)

    /**
     * 󱓧 (0xF14E7)
     */
    public data object NOTEBOOK_EDIT : MaterialDesignGlyphs(0xF14E7)

    /**
     * 󱓨 (0xF14E8)
     */
    public data object HUMAN_EDIT : MaterialDesignGlyphs(0xF14E8)

    /**
     * 󱓩 (0xF14E9)
     */
    public data object NOTEBOOK_EDIT_OUTLINE : MaterialDesignGlyphs(0xF14E9)

    /**
     * 󱓪 (0xF14EA)
     */
    public data object CASH_LOCK : MaterialDesignGlyphs(0xF14EA)

    /**
     * 󱓫 (0xF14EB)
     */
    public data object CASH_LOCK_OPEN : MaterialDesignGlyphs(0xF14EB)

    /**
     * 󱓬 (0xF14EC)
     */
    public data object ACCOUNT_SUPERVISOR_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF14EC)

    /**
     * 󱓭 (0xF14ED)
     */
    public data object CAR_OUTLINE : MaterialDesignGlyphs(0xF14ED)

    /**
     * 󱓮 (0xF14EE)
     */
    public data object CASH_CHECK : MaterialDesignGlyphs(0xF14EE)

    /**
     * 󱓯 (0xF14EF)
     */
    public data object FILTER_OFF : MaterialDesignGlyphs(0xF14EF)

    /**
     * 󱓰 (0xF14F0)
     */
    public data object FILTER_OFF_OUTLINE : MaterialDesignGlyphs(0xF14F0)

    /**
     * 󱓱 (0xF14F1)
     */
    public data object SPIRIT_LEVEL : MaterialDesignGlyphs(0xF14F1)

    /**
     * 󱓲 (0xF14F2)
     */
    public data object WHEEL_BARROW : MaterialDesignGlyphs(0xF14F2)

    /**
     * 󱓳 (0xF14F3)
     */
    public data object BOOK_CHECK : MaterialDesignGlyphs(0xF14F3)

    /**
     * 󱓴 (0xF14F4)
     */
    public data object BOOK_CHECK_OUTLINE : MaterialDesignGlyphs(0xF14F4)

    /**
     * 󱓵 (0xF14F5)
     */
    public data object NOTEBOOK_CHECK : MaterialDesignGlyphs(0xF14F5)

    /**
     * 󱓶 (0xF14F6)
     */
    public data object NOTEBOOK_CHECK_OUTLINE : MaterialDesignGlyphs(0xF14F6)

    /**
     * 󱓷 (0xF14F7)
     */
    public data object BOOK_OPEN_VARIANT : MaterialDesignGlyphs(0xF14F7)

    /**
     * 󱓸 (0xF14F8)
     */
    public data object SIGN_POLE : MaterialDesignGlyphs(0xF14F8)

    /**
     * 󱓹 (0xF14F9)
     */
    public data object SHORE : MaterialDesignGlyphs(0xF14F9)

    /**
     * 󱓺 (0xF14FA)
     */
    public data object SHAPE_SQUARE_ROUNDED_PLUS : MaterialDesignGlyphs(0xF14FA)

    /**
     * 󱓻 (0xF14FB)
     */
    public data object SQUARE_ROUNDED : MaterialDesignGlyphs(0xF14FB)

    /**
     * 󱓼 (0xF14FC)
     */
    public data object SQUARE_ROUNDED_OUTLINE : MaterialDesignGlyphs(0xF14FC)

    /**
     * 󱓽 (0xF14FD)
     */
    public data object ARCHIVE_ALERT : MaterialDesignGlyphs(0xF14FD)

    /**
     * 󱓾 (0xF14FE)
     */
    public data object ARCHIVE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF14FE)

    /**
     * 󱓿 (0xF14FF)
     */
    public data object POWER_SOCKET_IT : MaterialDesignGlyphs(0xF14FF)

    /**
     * 󱔀 (0xF1500)
     */
    public data object SQUARE_CIRCLE : MaterialDesignGlyphs(0xF1500)

    /**
     * 󱔁 (0xF1501)
     */
    public data object SYMBOL : MaterialDesignGlyphs(0xF1501)

    /**
     * 󱔂 (0xF1502)
     */
    public data object WATER_ALERT : MaterialDesignGlyphs(0xF1502)

    /**
     * 󱔃 (0xF1503)
     */
    public data object WATER_ALERT_OUTLINE : MaterialDesignGlyphs(0xF1503)

    /**
     * 󱔄 (0xF1504)
     */
    public data object WATER_CHECK : MaterialDesignGlyphs(0xF1504)

    /**
     * 󱔅 (0xF1505)
     */
    public data object WATER_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1505)

    /**
     * 󱔆 (0xF1506)
     */
    public data object WATER_MINUS : MaterialDesignGlyphs(0xF1506)

    /**
     * 󱔇 (0xF1507)
     */
    public data object WATER_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1507)

    /**
     * 󱔈 (0xF1508)
     */
    public data object WATER_OFF_OUTLINE : MaterialDesignGlyphs(0xF1508)

    /**
     * 󱔉 (0xF1509)
     */
    public data object WATER_PERCENT_ALERT : MaterialDesignGlyphs(0xF1509)

    /**
     * 󱔊 (0xF150A)
     */
    public data object WATER_PLUS : MaterialDesignGlyphs(0xF150A)

    /**
     * 󱔋 (0xF150B)
     */
    public data object WATER_PLUS_OUTLINE : MaterialDesignGlyphs(0xF150B)

    /**
     * 󱔌 (0xF150C)
     */
    public data object WATER_REMOVE : MaterialDesignGlyphs(0xF150C)

    /**
     * 󱔍 (0xF150D)
     */
    public data object WATER_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF150D)

    /**
     * 󱔎 (0xF150E)
     */
    public data object SNAKE : MaterialDesignGlyphs(0xF150E)

    /**
     * 󱔏 (0xF150F)
     */
    public data object FORMAT_TEXT_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF150F)

    /**
     * 󱔐 (0xF1510)
     */
    public data object GRASS : MaterialDesignGlyphs(0xF1510)

    /**
     * 󱔑 (0xF1511)
     */
    public data object ACCESS_POINT_OFF : MaterialDesignGlyphs(0xF1511)

    /**
     * 󱔒 (0xF1512)
     */
    public data object CURRENCY_MNT : MaterialDesignGlyphs(0xF1512)

    /**
     * 󱔓 (0xF1513)
     */
    public data object DOCK_TOP : MaterialDesignGlyphs(0xF1513)

    /**
     * 󱔔 (0xF1514)
     */
    public data object SHARE_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1514)

    /**
     * 󱔕 (0xF1515)
     */
    public data object TRANSIT_SKIP : MaterialDesignGlyphs(0xF1515)

    /**
     * 󱔖 (0xF1516)
     */
    public data object YURT : MaterialDesignGlyphs(0xF1516)

    /**
     * 󱔗 (0xF1517)
     */
    public data object FILE_DOCUMENT_MULTIPLE : MaterialDesignGlyphs(0xF1517)

    /**
     * 󱔘 (0xF1518)
     */
    public data object FILE_DOCUMENT_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1518)

    /**
     * 󱔙 (0xF1519)
     */
    public data object EV_PLUG_CCS1 : MaterialDesignGlyphs(0xF1519)

    /**
     * 󱔚 (0xF151A)
     */
    public data object EV_PLUG_CCS2 : MaterialDesignGlyphs(0xF151A)

    /**
     * 󱔛 (0xF151B)
     */
    public data object EV_PLUG_CHADEMO : MaterialDesignGlyphs(0xF151B)

    /**
     * 󱔜 (0xF151C)
     */
    public data object EV_PLUG_TESLA : MaterialDesignGlyphs(0xF151C)

    /**
     * 󱔝 (0xF151D)
     */
    public data object EV_PLUG_TYPE1 : MaterialDesignGlyphs(0xF151D)

    /**
     * 󱔞 (0xF151E)
     */
    public data object EV_PLUG_TYPE2 : MaterialDesignGlyphs(0xF151E)

    /**
     * 󱔟 (0xF151F)
     */
    public data object OFFICE_BUILDING_OUTLINE : MaterialDesignGlyphs(0xF151F)

    /**
     * 󱔠 (0xF1520)
     */
    public data object OFFICE_BUILDING_MARKER : MaterialDesignGlyphs(0xF1520)

    /**
     * 󱔡 (0xF1521)
     */
    public data object OFFICE_BUILDING_MARKER_OUTLINE : MaterialDesignGlyphs(0xF1521)

    /**
     * 󱔢 (0xF1522)
     */
    public data object PROGRESS_QUESTION : MaterialDesignGlyphs(0xF1522)

    /**
     * 󱔣 (0xF1523)
     */
    public data object BASKET_MINUS : MaterialDesignGlyphs(0xF1523)

    /**
     * 󱔤 (0xF1524)
     */
    public data object BASKET_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1524)

    /**
     * 󱔥 (0xF1525)
     */
    public data object BASKET_OFF : MaterialDesignGlyphs(0xF1525)

    /**
     * 󱔦 (0xF1526)
     */
    public data object BASKET_OFF_OUTLINE : MaterialDesignGlyphs(0xF1526)

    /**
     * 󱔧 (0xF1527)
     */
    public data object BASKET_PLUS : MaterialDesignGlyphs(0xF1527)

    /**
     * 󱔨 (0xF1528)
     */
    public data object BASKET_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1528)

    /**
     * 󱔩 (0xF1529)
     */
    public data object BASKET_REMOVE : MaterialDesignGlyphs(0xF1529)

    /**
     * 󱔪 (0xF152A)
     */
    public data object BASKET_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF152A)

    /**
     * 󱔫 (0xF152B)
     */
    public data object ACCOUNT_REACTIVATE : MaterialDesignGlyphs(0xF152B)

    /**
     * 󱔬 (0xF152C)
     */
    public data object ACCOUNT_REACTIVATE_OUTLINE : MaterialDesignGlyphs(0xF152C)

    /**
     * 󱔭 (0xF152D)
     */
    public data object CAR_LIFTED_PICKUP : MaterialDesignGlyphs(0xF152D)

    /**
     * 󱔮 (0xF152E)
     */
    public data object VIDEO_HIGH_DEFINITION : MaterialDesignGlyphs(0xF152E)

    /**
     * 󱔯 (0xF152F)
     */
    public data object PHONE_REMOVE : MaterialDesignGlyphs(0xF152F)

    /**
     * 󱔰 (0xF1530)
     */
    public data object PHONE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1530)

    /**
     * 󱔱 (0xF1531)
     */
    public data object THERMOMETER_OFF : MaterialDesignGlyphs(0xF1531)

    /**
     * 󱔲 (0xF1532)
     */
    public data object TIMELINE_CHECK : MaterialDesignGlyphs(0xF1532)

    /**
     * 󱔳 (0xF1533)
     */
    public data object TIMELINE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1533)

    /**
     * 󱔴 (0xF1534)
     */
    public data object TIMELINE_MINUS : MaterialDesignGlyphs(0xF1534)

    /**
     * 󱔵 (0xF1535)
     */
    public data object TIMELINE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1535)

    /**
     * 󱔶 (0xF1536)
     */
    public data object TIMELINE_REMOVE : MaterialDesignGlyphs(0xF1536)

    /**
     * 󱔷 (0xF1537)
     */
    public data object TIMELINE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1537)

    /**
     * 󱔸 (0xF1538)
     */
    public data object ACCESS_POINT_CHECK : MaterialDesignGlyphs(0xF1538)

    /**
     * 󱔹 (0xF1539)
     */
    public data object ACCESS_POINT_MINUS : MaterialDesignGlyphs(0xF1539)

    /**
     * 󱔺 (0xF153A)
     */
    public data object ACCESS_POINT_PLUS : MaterialDesignGlyphs(0xF153A)

    /**
     * 󱔻 (0xF153B)
     */
    public data object ACCESS_POINT_REMOVE : MaterialDesignGlyphs(0xF153B)

    /**
     * 󱔼 (0xF153C)
     */
    public data object DATA_MATRIX : MaterialDesignGlyphs(0xF153C)

    /**
     * 󱔽 (0xF153D)
     */
    public data object DATA_MATRIX_EDIT : MaterialDesignGlyphs(0xF153D)

    /**
     * 󱔾 (0xF153E)
     */
    public data object DATA_MATRIX_MINUS : MaterialDesignGlyphs(0xF153E)

    /**
     * 󱔿 (0xF153F)
     */
    public data object DATA_MATRIX_PLUS : MaterialDesignGlyphs(0xF153F)

    /**
     * 󱕀 (0xF1540)
     */
    public data object DATA_MATRIX_REMOVE : MaterialDesignGlyphs(0xF1540)

    /**
     * 󱕁 (0xF1541)
     */
    public data object DATA_MATRIX_SCAN : MaterialDesignGlyphs(0xF1541)

    /**
     * 󱕂 (0xF1542)
     */
    public data object TUNE_VARIANT : MaterialDesignGlyphs(0xF1542)

    /**
     * 󱕃 (0xF1543)
     */
    public data object TUNE_VERTICAL_VARIANT : MaterialDesignGlyphs(0xF1543)

    /**
     * 󱕄 (0xF1544)
     */
    public data object RAKE : MaterialDesignGlyphs(0xF1544)

    /**
     * 󱕅 (0xF1545)
     */
    public data object SHIMMER : MaterialDesignGlyphs(0xF1545)

    /**
     * 󱕆 (0xF1546)
     */
    public data object TRANSIT_CONNECTION_HORIZONTAL : MaterialDesignGlyphs(0xF1546)

    /**
     * 󱕇 (0xF1547)
     */
    public data object SORT_CALENDAR_ASCENDING : MaterialDesignGlyphs(0xF1547)

    /**
     * 󱕈 (0xF1548)
     */
    public data object SORT_CALENDAR_DESCENDING : MaterialDesignGlyphs(0xF1548)

    /**
     * 󱕉 (0xF1549)
     */
    public data object SORT_CLOCK_ASCENDING : MaterialDesignGlyphs(0xF1549)

    /**
     * 󱕊 (0xF154A)
     */
    public data object SORT_CLOCK_ASCENDING_OUTLINE : MaterialDesignGlyphs(0xF154A)

    /**
     * 󱕋 (0xF154B)
     */
    public data object SORT_CLOCK_DESCENDING : MaterialDesignGlyphs(0xF154B)

    /**
     * 󱕌 (0xF154C)
     */
    public data object SORT_CLOCK_DESCENDING_OUTLINE : MaterialDesignGlyphs(0xF154C)

    /**
     * 󱕍 (0xF154D)
     */
    public data object CHART_BOX : MaterialDesignGlyphs(0xF154D)

    /**
     * 󱕎 (0xF154E)
     */
    public data object CHART_BOX_OUTLINE : MaterialDesignGlyphs(0xF154E)

    /**
     * 󱕏 (0xF154F)
     */
    public data object CHART_BOX_PLUS_OUTLINE : MaterialDesignGlyphs(0xF154F)

    /**
     * 󱕐 (0xF1550)
     */
    public data object MOUSE_MOVE_DOWN : MaterialDesignGlyphs(0xF1550)

    /**
     * 󱕑 (0xF1551)
     */
    public data object MOUSE_MOVE_UP : MaterialDesignGlyphs(0xF1551)

    /**
     * 󱕒 (0xF1552)
     */
    public data object MOUSE_MOVE_VERTICAL : MaterialDesignGlyphs(0xF1552)

    /**
     * 󱕓 (0xF1553)
     */
    public data object PITCHFORK : MaterialDesignGlyphs(0xF1553)

    /**
     * 󱕔 (0xF1554)
     */
    public data object VANISH_QUARTER : MaterialDesignGlyphs(0xF1554)

    /**
     * 󱕕 (0xF1555)
     */
    public data object APPLICATION_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF1555)

    /**
     * 󱕖 (0xF1556)
     */
    public data object DELETE_CLOCK : MaterialDesignGlyphs(0xF1556)

    /**
     * 󱕗 (0xF1557)
     */
    public data object DELETE_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF1557)

    /**
     * 󱕘 (0xF1558)
     */
    public data object KANGAROO : MaterialDesignGlyphs(0xF1558)

    /**
     * 󱕙 (0xF1559)
     */
    public data object PHONE_DIAL : MaterialDesignGlyphs(0xF1559)

    /**
     * 󱕚 (0xF155A)
     */
    public data object PHONE_DIAL_OUTLINE : MaterialDesignGlyphs(0xF155A)

    /**
     * 󱕛 (0xF155B)
     */
    public data object STAR_OFF_OUTLINE : MaterialDesignGlyphs(0xF155B)

    /**
     * 󱕜 (0xF155C)
     */
    public data object TOOLTIP_CHECK : MaterialDesignGlyphs(0xF155C)

    /**
     * 󱕝 (0xF155D)
     */
    public data object TOOLTIP_CHECK_OUTLINE : MaterialDesignGlyphs(0xF155D)

    /**
     * 󱕞 (0xF155E)
     */
    public data object TOOLTIP_MINUS : MaterialDesignGlyphs(0xF155E)

    /**
     * 󱕟 (0xF155F)
     */
    public data object TOOLTIP_MINUS_OUTLINE : MaterialDesignGlyphs(0xF155F)

    /**
     * 󱕠 (0xF1560)
     */
    public data object TOOLTIP_REMOVE : MaterialDesignGlyphs(0xF1560)

    /**
     * 󱕡 (0xF1561)
     */
    public data object TOOLTIP_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1561)

    /**
     * 󱕢 (0xF1562)
     */
    public data object PRETZEL : MaterialDesignGlyphs(0xF1562)

    /**
     * 󱕣 (0xF1563)
     */
    public data object STAR_PLUS : MaterialDesignGlyphs(0xF1563)

    /**
     * 󱕤 (0xF1564)
     */
    public data object STAR_MINUS : MaterialDesignGlyphs(0xF1564)

    /**
     * 󱕥 (0xF1565)
     */
    public data object STAR_REMOVE : MaterialDesignGlyphs(0xF1565)

    /**
     * 󱕦 (0xF1566)
     */
    public data object STAR_CHECK : MaterialDesignGlyphs(0xF1566)

    /**
     * 󱕧 (0xF1567)
     */
    public data object STAR_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1567)

    /**
     * 󱕨 (0xF1568)
     */
    public data object STAR_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1568)

    /**
     * 󱕩 (0xF1569)
     */
    public data object STAR_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1569)

    /**
     * 󱕪 (0xF156A)
     */
    public data object STAR_CHECK_OUTLINE : MaterialDesignGlyphs(0xF156A)

    /**
     * 󱕫 (0xF156B)
     */
    public data object EIFFEL_TOWER : MaterialDesignGlyphs(0xF156B)

    /**
     * 󱕬 (0xF156C)
     */
    public data object SUBMARINE : MaterialDesignGlyphs(0xF156C)

    /**
     * 󱕭 (0xF156D)
     */
    public data object SOFA_OUTLINE : MaterialDesignGlyphs(0xF156D)

    /**
     * 󱕮 (0xF156E)
     */
    public data object SOFA_SINGLE : MaterialDesignGlyphs(0xF156E)

    /**
     * 󱕯 (0xF156F)
     */
    public data object SOFA_SINGLE_OUTLINE : MaterialDesignGlyphs(0xF156F)

    /**
     * 󱕰 (0xF1570)
     */
    public data object TEXT_ACCOUNT : MaterialDesignGlyphs(0xF1570)

    /**
     * 󱕱 (0xF1571)
     */
    public data object HUMAN_QUEUE : MaterialDesignGlyphs(0xF1571)

    /**
     * 󱕲 (0xF1572)
     */
    public data object FOOD_HALAL : MaterialDesignGlyphs(0xF1572)

    /**
     * 󱕳 (0xF1573)
     */
    public data object FOOD_KOSHER : MaterialDesignGlyphs(0xF1573)

    /**
     * 󱕴 (0xF1574)
     */
    public data object KEY_CHAIN : MaterialDesignGlyphs(0xF1574)

    /**
     * 󱕵 (0xF1575)
     */
    public data object KEY_CHAIN_VARIANT : MaterialDesignGlyphs(0xF1575)

    /**
     * 󱕶 (0xF1576)
     */
    public data object LAMPS : MaterialDesignGlyphs(0xF1576)

    /**
     * 󱕷 (0xF1577)
     */
    public data object APPLICATION_COG_OUTLINE : MaterialDesignGlyphs(0xF1577)

    /**
     * 󱕸 (0xF1578)
     */
    public data object DANCE_POLE : MaterialDesignGlyphs(0xF1578)

    /**
     * 󱕹 (0xF1579)
     */
    public data object SOCIAL_DISTANCE_2_METERS : MaterialDesignGlyphs(0xF1579)

    /**
     * 󱕺 (0xF157A)
     */
    public data object SOCIAL_DISTANCE_6_FEET : MaterialDesignGlyphs(0xF157A)

    /**
     * 󱕻 (0xF157B)
     */
    public data object CALENDAR_CURSOR : MaterialDesignGlyphs(0xF157B)

    /**
     * 󱕼 (0xF157C)
     */
    public data object EMOTICON_SICK : MaterialDesignGlyphs(0xF157C)

    /**
     * 󱕽 (0xF157D)
     */
    public data object EMOTICON_SICK_OUTLINE : MaterialDesignGlyphs(0xF157D)

    /**
     * 󱕾 (0xF157E)
     */
    public data object HAND_HEART_OUTLINE : MaterialDesignGlyphs(0xF157E)

    /**
     * 󱕿 (0xF157F)
     */
    public data object HAND_WASH : MaterialDesignGlyphs(0xF157F)

    /**
     * 󱖀 (0xF1580)
     */
    public data object HAND_WASH_OUTLINE : MaterialDesignGlyphs(0xF1580)

    /**
     * 󱖁 (0xF1581)
     */
    public data object HUMAN_CANE : MaterialDesignGlyphs(0xF1581)

    /**
     * 󱖂 (0xF1582)
     */
    public data object LOTION : MaterialDesignGlyphs(0xF1582)

    /**
     * 󱖃 (0xF1583)
     */
    public data object LOTION_OUTLINE : MaterialDesignGlyphs(0xF1583)

    /**
     * 󱖄 (0xF1584)
     */
    public data object LOTION_PLUS : MaterialDesignGlyphs(0xF1584)

    /**
     * 󱖅 (0xF1585)
     */
    public data object LOTION_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1585)

    /**
     * 󱖆 (0xF1586)
     */
    public data object FACE_MASK : MaterialDesignGlyphs(0xF1586)

    /**
     * 󱖇 (0xF1587)
     */
    public data object FACE_MASK_OUTLINE : MaterialDesignGlyphs(0xF1587)

    /**
     * 󱖈 (0xF1588)
     */
    public data object REITERATE : MaterialDesignGlyphs(0xF1588)

    /**
     * 󱖉 (0xF1589)
     */
    public data object BUTTERFLY : MaterialDesignGlyphs(0xF1589)

    /**
     * 󱖊 (0xF158A)
     */
    public data object BUTTERFLY_OUTLINE : MaterialDesignGlyphs(0xF158A)

    /**
     * 󱖋 (0xF158B)
     */
    public data object BAG_SUITCASE : MaterialDesignGlyphs(0xF158B)

    /**
     * 󱖌 (0xF158C)
     */
    public data object BAG_SUITCASE_OUTLINE : MaterialDesignGlyphs(0xF158C)

    /**
     * 󱖍 (0xF158D)
     */
    public data object BAG_SUITCASE_OFF : MaterialDesignGlyphs(0xF158D)

    /**
     * 󱖎 (0xF158E)
     */
    public data object BAG_SUITCASE_OFF_OUTLINE : MaterialDesignGlyphs(0xF158E)

    /**
     * 󱖏 (0xF158F)
     */
    public data object MOTION_PLAY : MaterialDesignGlyphs(0xF158F)

    /**
     * 󱖐 (0xF1590)
     */
    public data object MOTION_PAUSE : MaterialDesignGlyphs(0xF1590)

    /**
     * 󱖑 (0xF1591)
     */
    public data object MOTION_PLAY_OUTLINE : MaterialDesignGlyphs(0xF1591)

    /**
     * 󱖒 (0xF1592)
     */
    public data object MOTION_PAUSE_OUTLINE : MaterialDesignGlyphs(0xF1592)

    /**
     * 󱖓 (0xF1593)
     */
    public data object ARROW_TOP_LEFT_THIN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1593)

    /**
     * 󱖔 (0xF1594)
     */
    public data object ARROW_TOP_RIGHT_THIN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1594)

    /**
     * 󱖕 (0xF1595)
     */
    public data object ARROW_BOTTOM_RIGHT_THIN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1595)

    /**
     * 󱖖 (0xF1596)
     */
    public data object ARROW_BOTTOM_LEFT_THIN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1596)

    /**
     * 󱖗 (0xF1597)
     */
    public data object ARROW_UP_THIN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1597)

    /**
     * 󱖘 (0xF1598)
     */
    public data object ARROW_RIGHT_THIN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1598)

    /**
     * 󱖙 (0xF1599)
     */
    public data object ARROW_DOWN_THIN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1599)

    /**
     * 󱖚 (0xF159A)
     */
    public data object ARROW_LEFT_THIN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF159A)

    /**
     * 󱖛 (0xF159B)
     */
    public data object HUMAN_CAPACITY_DECREASE : MaterialDesignGlyphs(0xF159B)

    /**
     * 󱖜 (0xF159C)
     */
    public data object HUMAN_CAPACITY_INCREASE : MaterialDesignGlyphs(0xF159C)

    /**
     * 󱖝 (0xF159D)
     */
    public data object HUMAN_GREETING_PROXIMITY : MaterialDesignGlyphs(0xF159D)

    /**
     * 󱖞 (0xF159E)
     */
    public data object HVAC_OFF : MaterialDesignGlyphs(0xF159E)

    /**
     * 󱖟 (0xF159F)
     */
    public data object INBOX_REMOVE : MaterialDesignGlyphs(0xF159F)

    /**
     * 󱖠 (0xF15A0)
     */
    public data object INBOX_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF15A0)

    /**
     * 󱖡 (0xF15A1)
     */
    public data object HANDSHAKE_OUTLINE : MaterialDesignGlyphs(0xF15A1)

    /**
     * 󱖢 (0xF15A2)
     */
    public data object LADDER : MaterialDesignGlyphs(0xF15A2)

    /**
     * 󱖣 (0xF15A3)
     */
    public data object ROUTER_WIRELESS_OFF : MaterialDesignGlyphs(0xF15A3)

    /**
     * 󱖤 (0xF15A4)
     */
    public data object SEESAW : MaterialDesignGlyphs(0xF15A4)

    /**
     * 󱖥 (0xF15A5)
     */
    public data object SLIDE : MaterialDesignGlyphs(0xF15A5)

    /**
     * 󱖦 (0xF15A6)
     */
    public data object CALCULATOR_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF15A6)

    /**
     * 󱖧 (0xF15A7)
     */
    public data object SHIELD_ACCOUNT_VARIANT : MaterialDesignGlyphs(0xF15A7)

    /**
     * 󱖨 (0xF15A8)
     */
    public data object SHIELD_ACCOUNT_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF15A8)

    /**
     * 󱖩 (0xF15A9)
     */
    public data object MESSAGE_FLASH : MaterialDesignGlyphs(0xF15A9)

    /**
     * 󱖪 (0xF15AA)
     */
    public data object MESSAGE_FLASH_OUTLINE : MaterialDesignGlyphs(0xF15AA)

    /**
     * 󱖫 (0xF15AB)
     */
    public data object LIST_STATUS : MaterialDesignGlyphs(0xF15AB)

    /**
     * 󱖬 (0xF15AC)
     */
    public data object MESSAGE_BOOKMARK : MaterialDesignGlyphs(0xF15AC)

    /**
     * 󱖭 (0xF15AD)
     */
    public data object MESSAGE_BOOKMARK_OUTLINE : MaterialDesignGlyphs(0xF15AD)

    /**
     * 󱖮 (0xF15AE)
     */
    public data object COMMENT_BOOKMARK : MaterialDesignGlyphs(0xF15AE)

    /**
     * 󱖯 (0xF15AF)
     */
    public data object COMMENT_BOOKMARK_OUTLINE : MaterialDesignGlyphs(0xF15AF)

    /**
     * 󱖰 (0xF15B0)
     */
    public data object COMMENT_FLASH : MaterialDesignGlyphs(0xF15B0)

    /**
     * 󱖱 (0xF15B1)
     */
    public data object COMMENT_FLASH_OUTLINE : MaterialDesignGlyphs(0xF15B1)

    /**
     * 󱖲 (0xF15B2)
     */
    public data object MOTION : MaterialDesignGlyphs(0xF15B2)

    /**
     * 󱖳 (0xF15B3)
     */
    public data object MOTION_OUTLINE : MaterialDesignGlyphs(0xF15B3)

    /**
     * 󱖴 (0xF15B4)
     */
    public data object BICYCLE_ELECTRIC : MaterialDesignGlyphs(0xF15B4)

    /**
     * 󱖵 (0xF15B5)
     */
    public data object CAR_ELECTRIC_OUTLINE : MaterialDesignGlyphs(0xF15B5)

    /**
     * 󱖶 (0xF15B6)
     */
    public data object CHART_TIMELINE_VARIANT_SHIMMER : MaterialDesignGlyphs(0xF15B6)

    /**
     * 󱖷 (0xF15B7)
     */
    public data object MOPED_ELECTRIC : MaterialDesignGlyphs(0xF15B7)

    /**
     * 󱖸 (0xF15B8)
     */
    public data object MOPED_ELECTRIC_OUTLINE : MaterialDesignGlyphs(0xF15B8)

    /**
     * 󱖹 (0xF15B9)
     */
    public data object MOPED_OUTLINE : MaterialDesignGlyphs(0xF15B9)

    /**
     * 󱖺 (0xF15BA)
     */
    public data object MOTORBIKE_ELECTRIC : MaterialDesignGlyphs(0xF15BA)

    /**
     * 󱖻 (0xF15BB)
     */
    public data object RICKSHAW : MaterialDesignGlyphs(0xF15BB)

    /**
     * 󱖼 (0xF15BC)
     */
    public data object RICKSHAW_ELECTRIC : MaterialDesignGlyphs(0xF15BC)

    /**
     * 󱖽 (0xF15BD)
     */
    public data object SCOOTER : MaterialDesignGlyphs(0xF15BD)

    /**
     * 󱖾 (0xF15BE)
     */
    public data object SCOOTER_ELECTRIC : MaterialDesignGlyphs(0xF15BE)

    /**
     * 󱖿 (0xF15BF)
     */
    public data object HORSE : MaterialDesignGlyphs(0xF15BF)

    /**
     * 󱗀 (0xF15C0)
     */
    public data object HORSE_HUMAN : MaterialDesignGlyphs(0xF15C0)

    /**
     * 󱗁 (0xF15C1)
     */
    public data object HORSE_VARIANT : MaterialDesignGlyphs(0xF15C1)

    /**
     * 󱗂 (0xF15C2)
     */
    public data object UNICORN : MaterialDesignGlyphs(0xF15C2)

    /**
     * 󱗃 (0xF15C3)
     */
    public data object UNICORN_VARIANT : MaterialDesignGlyphs(0xF15C3)

    /**
     * 󱗄 (0xF15C4)
     */
    public data object ALARM_PANEL : MaterialDesignGlyphs(0xF15C4)

    /**
     * 󱗅 (0xF15C5)
     */
    public data object ALARM_PANEL_OUTLINE : MaterialDesignGlyphs(0xF15C5)

    /**
     * 󱗆 (0xF15C6)
     */
    public data object BIRD : MaterialDesignGlyphs(0xF15C6)

    /**
     * 󱗇 (0xF15C7)
     */
    public data object SHOE_CLEAT : MaterialDesignGlyphs(0xF15C7)

    /**
     * 󱗈 (0xF15C8)
     */
    public data object SHOE_SNEAKER : MaterialDesignGlyphs(0xF15C8)

    /**
     * 󱗉 (0xF15C9)
     */
    public data object HUMAN_FEMALE_DANCE : MaterialDesignGlyphs(0xF15C9)

    /**
     * 󱗊 (0xF15CA)
     */
    public data object SHOE_BALLET : MaterialDesignGlyphs(0xF15CA)

    /**
     * 󱗋 (0xF15CB)
     */
    public data object NUMERIC_POSITIVE_1 : MaterialDesignGlyphs(0xF15CB)

    /**
     * 󱗌 (0xF15CC)
     */
    public data object FACE_MAN_SHIMMER : MaterialDesignGlyphs(0xF15CC)

    /**
     * 󱗍 (0xF15CD)
     */
    public data object FACE_MAN_SHIMMER_OUTLINE : MaterialDesignGlyphs(0xF15CD)

    /**
     * 󱗎 (0xF15CE)
     */
    public data object FACE_WOMAN_SHIMMER : MaterialDesignGlyphs(0xF15CE)

    /**
     * 󱗏 (0xF15CF)
     */
    public data object FACE_WOMAN_SHIMMER_OUTLINE : MaterialDesignGlyphs(0xF15CF)

    /**
     * 󱗐 (0xF15D0)
     */
    public data object HOME_ALERT_OUTLINE : MaterialDesignGlyphs(0xF15D0)

    /**
     * 󱗑 (0xF15D1)
     */
    public data object LOCK_ALERT_OUTLINE : MaterialDesignGlyphs(0xF15D1)

    /**
     * 󱗒 (0xF15D2)
     */
    public data object LOCK_OPEN_ALERT_OUTLINE : MaterialDesignGlyphs(0xF15D2)

    /**
     * 󱗓 (0xF15D3)
     */
    public data object SIM_ALERT_OUTLINE : MaterialDesignGlyphs(0xF15D3)

    /**
     * 󱗔 (0xF15D4)
     */
    public data object SIM_OFF_OUTLINE : MaterialDesignGlyphs(0xF15D4)

    /**
     * 󱗕 (0xF15D5)
     */
    public data object SIM_OUTLINE : MaterialDesignGlyphs(0xF15D5)

    /**
     * 󱗖 (0xF15D6)
     */
    public data object BOOK_OPEN_PAGE_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF15D6)

    /**
     * 󱗗 (0xF15D7)
     */
    public data object FIRE_ALERT : MaterialDesignGlyphs(0xF15D7)

    /**
     * 󱗘 (0xF15D8)
     */
    public data object RAY_START_VERTEX_END : MaterialDesignGlyphs(0xF15D8)

    /**
     * 󱗙 (0xF15D9)
     */
    public data object CAMERA_FLIP : MaterialDesignGlyphs(0xF15D9)

    /**
     * 󱗚 (0xF15DA)
     */
    public data object CAMERA_FLIP_OUTLINE : MaterialDesignGlyphs(0xF15DA)

    /**
     * 󱗛 (0xF15DB)
     */
    public data object ORBIT_VARIANT : MaterialDesignGlyphs(0xF15DB)

    /**
     * 󱗜 (0xF15DC)
     */
    public data object CIRCLE_BOX : MaterialDesignGlyphs(0xF15DC)

    /**
     * 󱗝 (0xF15DD)
     */
    public data object CIRCLE_BOX_OUTLINE : MaterialDesignGlyphs(0xF15DD)

    /**
     * 󱗞 (0xF15DE)
     */
    public data object MUSTACHE : MaterialDesignGlyphs(0xF15DE)

    /**
     * 󱗟 (0xF15DF)
     */
    public data object COMMENT_MINUS : MaterialDesignGlyphs(0xF15DF)

    /**
     * 󱗠 (0xF15E0)
     */
    public data object COMMENT_MINUS_OUTLINE : MaterialDesignGlyphs(0xF15E0)

    /**
     * 󱗡 (0xF15E1)
     */
    public data object COMMENT_OFF : MaterialDesignGlyphs(0xF15E1)

    /**
     * 󱗢 (0xF15E2)
     */
    public data object COMMENT_OFF_OUTLINE : MaterialDesignGlyphs(0xF15E2)

    /**
     * 󱗣 (0xF15E3)
     */
    public data object EYE_REMOVE : MaterialDesignGlyphs(0xF15E3)

    /**
     * 󱗤 (0xF15E4)
     */
    public data object EYE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF15E4)

    /**
     * 󱗥 (0xF15E5)
     */
    public data object UNICYCLE : MaterialDesignGlyphs(0xF15E5)

    /**
     * 󱗦 (0xF15E6)
     */
    public data object GLASS_COCKTAIL_OFF : MaterialDesignGlyphs(0xF15E6)

    /**
     * 󱗧 (0xF15E7)
     */
    public data object GLASS_MUG_OFF : MaterialDesignGlyphs(0xF15E7)

    /**
     * 󱗨 (0xF15E8)
     */
    public data object GLASS_MUG_VARIANT_OFF : MaterialDesignGlyphs(0xF15E8)

    /**
     * 󱗩 (0xF15E9)
     */
    public data object BICYCLE_PENNY_FARTHING : MaterialDesignGlyphs(0xF15E9)

    /**
     * 󱗪 (0xF15EA)
     */
    public data object CART_CHECK : MaterialDesignGlyphs(0xF15EA)

    /**
     * 󱗫 (0xF15EB)
     */
    public data object CART_VARIANT : MaterialDesignGlyphs(0xF15EB)

    /**
     * 󱗬 (0xF15EC)
     */
    public data object BASEBALL_DIAMOND : MaterialDesignGlyphs(0xF15EC)

    /**
     * 󱗭 (0xF15ED)
     */
    public data object BASEBALL_DIAMOND_OUTLINE : MaterialDesignGlyphs(0xF15ED)

    /**
     * 󱗮 (0xF15EE)
     */
    public data object FRIDGE_INDUSTRIAL : MaterialDesignGlyphs(0xF15EE)

    /**
     * 󱗯 (0xF15EF)
     */
    public data object FRIDGE_INDUSTRIAL_ALERT : MaterialDesignGlyphs(0xF15EF)

    /**
     * 󱗰 (0xF15F0)
     */
    public data object FRIDGE_INDUSTRIAL_ALERT_OUTLINE : MaterialDesignGlyphs(0xF15F0)

    /**
     * 󱗱 (0xF15F1)
     */
    public data object FRIDGE_INDUSTRIAL_OFF : MaterialDesignGlyphs(0xF15F1)

    /**
     * 󱗲 (0xF15F2)
     */
    public data object FRIDGE_INDUSTRIAL_OFF_OUTLINE : MaterialDesignGlyphs(0xF15F2)

    /**
     * 󱗳 (0xF15F3)
     */
    public data object FRIDGE_INDUSTRIAL_OUTLINE : MaterialDesignGlyphs(0xF15F3)

    /**
     * 󱗴 (0xF15F4)
     */
    public data object FRIDGE_VARIANT : MaterialDesignGlyphs(0xF15F4)

    /**
     * 󱗵 (0xF15F5)
     */
    public data object FRIDGE_VARIANT_ALERT : MaterialDesignGlyphs(0xF15F5)

    /**
     * 󱗶 (0xF15F6)
     */
    public data object FRIDGE_VARIANT_ALERT_OUTLINE : MaterialDesignGlyphs(0xF15F6)

    /**
     * 󱗷 (0xF15F7)
     */
    public data object FRIDGE_VARIANT_OFF : MaterialDesignGlyphs(0xF15F7)

    /**
     * 󱗸 (0xF15F8)
     */
    public data object FRIDGE_VARIANT_OFF_OUTLINE : MaterialDesignGlyphs(0xF15F8)

    /**
     * 󱗹 (0xF15F9)
     */
    public data object FRIDGE_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF15F9)

    /**
     * 󱗺 (0xF15FA)
     */
    public data object WINDSOCK : MaterialDesignGlyphs(0xF15FA)

    /**
     * 󱗻 (0xF15FB)
     */
    public data object DANCE_BALLROOM : MaterialDesignGlyphs(0xF15FB)

    /**
     * 󱗼 (0xF15FC)
     */
    public data object DOTS_GRID : MaterialDesignGlyphs(0xF15FC)

    /**
     * 󱗽 (0xF15FD)
     */
    public data object DOTS_SQUARE : MaterialDesignGlyphs(0xF15FD)

    /**
     * 󱗾 (0xF15FE)
     */
    public data object DOTS_TRIANGLE : MaterialDesignGlyphs(0xF15FE)

    /**
     * 󱗿 (0xF15FF)
     */
    public data object DOTS_HEXAGON : MaterialDesignGlyphs(0xF15FF)

    /**
     * 󱘀 (0xF1600)
     */
    public data object CARD_MINUS : MaterialDesignGlyphs(0xF1600)

    /**
     * 󱘁 (0xF1601)
     */
    public data object CARD_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1601)

    /**
     * 󱘂 (0xF1602)
     */
    public data object CARD_OFF : MaterialDesignGlyphs(0xF1602)

    /**
     * 󱘃 (0xF1603)
     */
    public data object CARD_OFF_OUTLINE : MaterialDesignGlyphs(0xF1603)

    /**
     * 󱘄 (0xF1604)
     */
    public data object CARD_REMOVE : MaterialDesignGlyphs(0xF1604)

    /**
     * 󱘅 (0xF1605)
     */
    public data object CARD_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1605)

    /**
     * 󱘆 (0xF1606)
     */
    public data object TORCH : MaterialDesignGlyphs(0xF1606)

    /**
     * 󱘇 (0xF1607)
     */
    public data object NAVIGATION_OUTLINE : MaterialDesignGlyphs(0xF1607)

    /**
     * 󱘈 (0xF1608)
     */
    public data object MAP_MARKER_STAR : MaterialDesignGlyphs(0xF1608)

    /**
     * 󱘉 (0xF1609)
     */
    public data object MAP_MARKER_STAR_OUTLINE : MaterialDesignGlyphs(0xF1609)

    /**
     * 󱘊 (0xF160A)
     */
    public data object MANJARO : MaterialDesignGlyphs(0xF160A)

    /**
     * 󱘋 (0xF160B)
     */
    public data object FAST_FORWARD_60 : MaterialDesignGlyphs(0xF160B)

    /**
     * 󱘌 (0xF160C)
     */
    public data object REWIND_60 : MaterialDesignGlyphs(0xF160C)

    /**
     * 󱘍 (0xF160D)
     */
    public data object IMAGE_TEXT : MaterialDesignGlyphs(0xF160D)

    /**
     * 󱘎 (0xF160E)
     */
    public data object FAMILY_TREE : MaterialDesignGlyphs(0xF160E)

    /**
     * 󱘏 (0xF160F)
     */
    public data object CAR_EMERGENCY : MaterialDesignGlyphs(0xF160F)

    /**
     * 󱘐 (0xF1610)
     */
    public data object NOTEBOOK_MINUS : MaterialDesignGlyphs(0xF1610)

    /**
     * 󱘑 (0xF1611)
     */
    public data object NOTEBOOK_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1611)

    /**
     * 󱘒 (0xF1612)
     */
    public data object NOTEBOOK_PLUS : MaterialDesignGlyphs(0xF1612)

    /**
     * 󱘓 (0xF1613)
     */
    public data object NOTEBOOK_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1613)

    /**
     * 󱘔 (0xF1614)
     */
    public data object NOTEBOOK_REMOVE : MaterialDesignGlyphs(0xF1614)

    /**
     * 󱘕 (0xF1615)
     */
    public data object NOTEBOOK_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1615)

    /**
     * 󱘖 (0xF1616)
     */
    public data object CONNECTION : MaterialDesignGlyphs(0xF1616)

    /**
     * 󱘗 (0xF1617)
     */
    public data object LANGUAGE_RUST : MaterialDesignGlyphs(0xF1617)

    /**
     * 󱘘 (0xF1618)
     */
    public data object CLIPBOARD_MINUS : MaterialDesignGlyphs(0xF1618)

    /**
     * 󱘙 (0xF1619)
     */
    public data object CLIPBOARD_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1619)

    /**
     * 󱘚 (0xF161A)
     */
    public data object CLIPBOARD_OFF : MaterialDesignGlyphs(0xF161A)

    /**
     * 󱘛 (0xF161B)
     */
    public data object CLIPBOARD_OFF_OUTLINE : MaterialDesignGlyphs(0xF161B)

    /**
     * 󱘜 (0xF161C)
     */
    public data object CLIPBOARD_REMOVE : MaterialDesignGlyphs(0xF161C)

    /**
     * 󱘝 (0xF161D)
     */
    public data object CLIPBOARD_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF161D)

    /**
     * 󱘞 (0xF161E)
     */
    public data object CLIPBOARD_SEARCH : MaterialDesignGlyphs(0xF161E)

    /**
     * 󱘟 (0xF161F)
     */
    public data object CLIPBOARD_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF161F)

    /**
     * 󱘠 (0xF1620)
     */
    public data object CLIPBOARD_TEXT_OFF : MaterialDesignGlyphs(0xF1620)

    /**
     * 󱘡 (0xF1621)
     */
    public data object CLIPBOARD_TEXT_OFF_OUTLINE : MaterialDesignGlyphs(0xF1621)

    /**
     * 󱘢 (0xF1622)
     */
    public data object CLIPBOARD_TEXT_SEARCH : MaterialDesignGlyphs(0xF1622)

    /**
     * 󱘣 (0xF1623)
     */
    public data object CLIPBOARD_TEXT_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1623)

    /**
     * 󱘤 (0xF1624)
     */
    public data object DATABASE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF1624)

    /**
     * 󱘥 (0xF1625)
     */
    public data object DATABASE_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF1625)

    /**
     * 󱘦 (0xF1626)
     */
    public data object DATABASE_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF1626)

    /**
     * 󱘧 (0xF1627)
     */
    public data object DATABASE_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF1627)

    /**
     * 󱘨 (0xF1628)
     */
    public data object DATABASE_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF1628)

    /**
     * 󱘩 (0xF1629)
     */
    public data object DATABASE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1629)

    /**
     * 󱘪 (0xF162A)
     */
    public data object DATABASE_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF162A)

    /**
     * 󱘫 (0xF162B)
     */
    public data object DATABASE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF162B)

    /**
     * 󱘬 (0xF162C)
     */
    public data object DATABASE_EXPORT_OUTLINE : MaterialDesignGlyphs(0xF162C)

    /**
     * 󱘭 (0xF162D)
     */
    public data object DATABASE_IMPORT_OUTLINE : MaterialDesignGlyphs(0xF162D)

    /**
     * 󱘮 (0xF162E)
     */
    public data object DATABASE_LOCK_OUTLINE : MaterialDesignGlyphs(0xF162E)

    /**
     * 󱘯 (0xF162F)
     */
    public data object DATABASE_MARKER_OUTLINE : MaterialDesignGlyphs(0xF162F)

    /**
     * 󱘰 (0xF1630)
     */
    public data object DATABASE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1630)

    /**
     * 󱘱 (0xF1631)
     */
    public data object DATABASE_OFF_OUTLINE : MaterialDesignGlyphs(0xF1631)

    /**
     * 󱘲 (0xF1632)
     */
    public data object DATABASE_OUTLINE : MaterialDesignGlyphs(0xF1632)

    /**
     * 󱘳 (0xF1633)
     */
    public data object DATABASE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1633)

    /**
     * 󱘴 (0xF1634)
     */
    public data object DATABASE_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF1634)

    /**
     * 󱘵 (0xF1635)
     */
    public data object DATABASE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1635)

    /**
     * 󱘶 (0xF1636)
     */
    public data object DATABASE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1636)

    /**
     * 󱘷 (0xF1637)
     */
    public data object DATABASE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF1637)

    /**
     * 󱘸 (0xF1638)
     */
    public data object DATABASE_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1638)

    /**
     * 󱘹 (0xF1639)
     */
    public data object MINUS_THICK : MaterialDesignGlyphs(0xF1639)

    /**
     * 󱘺 (0xF163A)
     */
    public data object DATABASE_ALERT : MaterialDesignGlyphs(0xF163A)

    /**
     * 󱘻 (0xF163B)
     */
    public data object DATABASE_ARROW_DOWN : MaterialDesignGlyphs(0xF163B)

    /**
     * 󱘼 (0xF163C)
     */
    public data object DATABASE_ARROW_LEFT : MaterialDesignGlyphs(0xF163C)

    /**
     * 󱘽 (0xF163D)
     */
    public data object DATABASE_ARROW_RIGHT : MaterialDesignGlyphs(0xF163D)

    /**
     * 󱘾 (0xF163E)
     */
    public data object DATABASE_ARROW_UP : MaterialDesignGlyphs(0xF163E)

    /**
     * 󱘿 (0xF163F)
     */
    public data object DATABASE_CLOCK : MaterialDesignGlyphs(0xF163F)

    /**
     * 󱙀 (0xF1640)
     */
    public data object DATABASE_OFF : MaterialDesignGlyphs(0xF1640)

    /**
     * 󱙁 (0xF1641)
     */
    public data object CALENDAR_LOCK : MaterialDesignGlyphs(0xF1641)

    /**
     * 󱙂 (0xF1642)
     */
    public data object CALENDAR_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1642)

    /**
     * 󱙃 (0xF1643)
     */
    public data object CONTENT_SAVE_OFF : MaterialDesignGlyphs(0xF1643)

    /**
     * 󱙄 (0xF1644)
     */
    public data object CONTENT_SAVE_OFF_OUTLINE : MaterialDesignGlyphs(0xF1644)

    /**
     * 󱙅 (0xF1645)
     */
    public data object CREDIT_CARD_REFRESH : MaterialDesignGlyphs(0xF1645)

    /**
     * 󱙆 (0xF1646)
     */
    public data object CREDIT_CARD_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF1646)

    /**
     * 󱙇 (0xF1647)
     */
    public data object CREDIT_CARD_SEARCH : MaterialDesignGlyphs(0xF1647)

    /**
     * 󱙈 (0xF1648)
     */
    public data object CREDIT_CARD_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1648)

    /**
     * 󱙉 (0xF1649)
     */
    public data object CREDIT_CARD_SYNC : MaterialDesignGlyphs(0xF1649)

    /**
     * 󱙊 (0xF164A)
     */
    public data object CREDIT_CARD_SYNC_OUTLINE : MaterialDesignGlyphs(0xF164A)

    /**
     * 󱙋 (0xF164B)
     */
    public data object DATABASE_COG : MaterialDesignGlyphs(0xF164B)

    /**
     * 󱙌 (0xF164C)
     */
    public data object DATABASE_COG_OUTLINE : MaterialDesignGlyphs(0xF164C)

    /**
     * 󱙍 (0xF164D)
     */
    public data object MESSAGE_OFF : MaterialDesignGlyphs(0xF164D)

    /**
     * 󱙎 (0xF164E)
     */
    public data object MESSAGE_OFF_OUTLINE : MaterialDesignGlyphs(0xF164E)

    /**
     * 󱙏 (0xF164F)
     */
    public data object NOTE_MINUS : MaterialDesignGlyphs(0xF164F)

    /**
     * 󱙐 (0xF1650)
     */
    public data object NOTE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1650)

    /**
     * 󱙑 (0xF1651)
     */
    public data object NOTE_REMOVE : MaterialDesignGlyphs(0xF1651)

    /**
     * 󱙒 (0xF1652)
     */
    public data object NOTE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1652)

    /**
     * 󱙓 (0xF1653)
     */
    public data object NOTE_SEARCH : MaterialDesignGlyphs(0xF1653)

    /**
     * 󱙔 (0xF1654)
     */
    public data object NOTE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1654)

    /**
     * 󱙕 (0xF1655)
     */
    public data object BANK_CHECK : MaterialDesignGlyphs(0xF1655)

    /**
     * 󱙖 (0xF1656)
     */
    public data object BANK_OFF : MaterialDesignGlyphs(0xF1656)

    /**
     * 󱙗 (0xF1657)
     */
    public data object BANK_OFF_OUTLINE : MaterialDesignGlyphs(0xF1657)

    /**
     * 󱙘 (0xF1658)
     */
    public data object BRIEFCASE_OFF : MaterialDesignGlyphs(0xF1658)

    /**
     * 󱙙 (0xF1659)
     */
    public data object BRIEFCASE_OFF_OUTLINE : MaterialDesignGlyphs(0xF1659)

    /**
     * 󱙚 (0xF165A)
     */
    public data object BRIEFCASE_VARIANT_OFF : MaterialDesignGlyphs(0xF165A)

    /**
     * 󱙛 (0xF165B)
     */
    public data object BRIEFCASE_VARIANT_OFF_OUTLINE : MaterialDesignGlyphs(0xF165B)

    /**
     * 󱙜 (0xF165C)
     */
    public data object GHOST_OFF_OUTLINE : MaterialDesignGlyphs(0xF165C)

    /**
     * 󱙝 (0xF165D)
     */
    public data object GHOST_OUTLINE : MaterialDesignGlyphs(0xF165D)

    /**
     * 󱙞 (0xF165E)
     */
    public data object STORE_MINUS : MaterialDesignGlyphs(0xF165E)

    /**
     * 󱙟 (0xF165F)
     */
    public data object STORE_PLUS : MaterialDesignGlyphs(0xF165F)

    /**
     * 󱙠 (0xF1660)
     */
    public data object STORE_REMOVE : MaterialDesignGlyphs(0xF1660)

    /**
     * 󱙡 (0xF1661)
     */
    public data object EMAIL_REMOVE : MaterialDesignGlyphs(0xF1661)

    /**
     * 󱙢 (0xF1662)
     */
    public data object EMAIL_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1662)

    /**
     * 󱙣 (0xF1663)
     */
    public data object HEART_COG : MaterialDesignGlyphs(0xF1663)

    /**
     * 󱙤 (0xF1664)
     */
    public data object HEART_COG_OUTLINE : MaterialDesignGlyphs(0xF1664)

    /**
     * 󱙥 (0xF1665)
     */
    public data object HEART_SETTINGS : MaterialDesignGlyphs(0xF1665)

    /**
     * 󱙦 (0xF1666)
     */
    public data object HEART_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF1666)

    /**
     * 󱙧 (0xF1667)
     */
    public data object PENTAGRAM : MaterialDesignGlyphs(0xF1667)

    /**
     * 󱙨 (0xF1668)
     */
    public data object STAR_COG : MaterialDesignGlyphs(0xF1668)

    /**
     * 󱙩 (0xF1669)
     */
    public data object STAR_COG_OUTLINE : MaterialDesignGlyphs(0xF1669)

    /**
     * 󱙪 (0xF166A)
     */
    public data object STAR_SETTINGS : MaterialDesignGlyphs(0xF166A)

    /**
     * 󱙫 (0xF166B)
     */
    public data object STAR_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF166B)

    /**
     * 󱙬 (0xF166C)
     */
    public data object CALENDAR_END : MaterialDesignGlyphs(0xF166C)

    /**
     * 󱙭 (0xF166D)
     */
    public data object CALENDAR_START : MaterialDesignGlyphs(0xF166D)

    /**
     * 󱙮 (0xF166E)
     */
    public data object CANNABIS_OFF : MaterialDesignGlyphs(0xF166E)

    /**
     * 󱙯 (0xF166F)
     */
    public data object MOWER : MaterialDesignGlyphs(0xF166F)

    /**
     * 󱙰 (0xF1670)
     */
    public data object MOWER_BAG : MaterialDesignGlyphs(0xF1670)

    /**
     * 󱙱 (0xF1671)
     */
    public data object LOCK_OFF : MaterialDesignGlyphs(0xF1671)

    /**
     * 󱙲 (0xF1672)
     */
    public data object LOCK_OFF_OUTLINE : MaterialDesignGlyphs(0xF1672)

    /**
     * 󱙳 (0xF1673)
     */
    public data object SHARK_FIN : MaterialDesignGlyphs(0xF1673)

    /**
     * 󱙴 (0xF1674)
     */
    public data object SHARK_FIN_OUTLINE : MaterialDesignGlyphs(0xF1674)

    /**
     * 󱙵 (0xF1675)
     */
    public data object PAW_OUTLINE : MaterialDesignGlyphs(0xF1675)

    /**
     * 󱙶 (0xF1676)
     */
    public data object PAW_OFF_OUTLINE : MaterialDesignGlyphs(0xF1676)

    /**
     * 󱙷 (0xF1677)
     */
    public data object SNAIL : MaterialDesignGlyphs(0xF1677)

    /**
     * 󱙸 (0xF1678)
     */
    public data object PIG_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1678)

    /**
     * 󱙹 (0xF1679)
     */
    public data object PIGGY_BANK_OUTLINE : MaterialDesignGlyphs(0xF1679)

    /**
     * 󱙺 (0xF167A)
     */
    public data object ROBOT_OUTLINE : MaterialDesignGlyphs(0xF167A)

    /**
     * 󱙻 (0xF167B)
     */
    public data object ROBOT_OFF_OUTLINE : MaterialDesignGlyphs(0xF167B)

    /**
     * 󱙼 (0xF167C)
     */
    public data object BOOK_ALERT : MaterialDesignGlyphs(0xF167C)

    /**
     * 󱙽 (0xF167D)
     */
    public data object BOOK_ALERT_OUTLINE : MaterialDesignGlyphs(0xF167D)

    /**
     * 󱙾 (0xF167E)
     */
    public data object BOOK_ARROW_DOWN : MaterialDesignGlyphs(0xF167E)

    /**
     * 󱙿 (0xF167F)
     */
    public data object BOOK_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF167F)

    /**
     * 󱚀 (0xF1680)
     */
    public data object BOOK_ARROW_LEFT : MaterialDesignGlyphs(0xF1680)

    /**
     * 󱚁 (0xF1681)
     */
    public data object BOOK_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF1681)

    /**
     * 󱚂 (0xF1682)
     */
    public data object BOOK_ARROW_RIGHT : MaterialDesignGlyphs(0xF1682)

    /**
     * 󱚃 (0xF1683)
     */
    public data object BOOK_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF1683)

    /**
     * 󱚄 (0xF1684)
     */
    public data object BOOK_ARROW_UP : MaterialDesignGlyphs(0xF1684)

    /**
     * 󱚅 (0xF1685)
     */
    public data object BOOK_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF1685)

    /**
     * 󱚆 (0xF1686)
     */
    public data object BOOK_CANCEL : MaterialDesignGlyphs(0xF1686)

    /**
     * 󱚇 (0xF1687)
     */
    public data object BOOK_CANCEL_OUTLINE : MaterialDesignGlyphs(0xF1687)

    /**
     * 󱚈 (0xF1688)
     */
    public data object BOOK_CLOCK : MaterialDesignGlyphs(0xF1688)

    /**
     * 󱚉 (0xF1689)
     */
    public data object BOOK_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF1689)

    /**
     * 󱚊 (0xF168A)
     */
    public data object BOOK_COG : MaterialDesignGlyphs(0xF168A)

    /**
     * 󱚋 (0xF168B)
     */
    public data object BOOK_COG_OUTLINE : MaterialDesignGlyphs(0xF168B)

    /**
     * 󱚌 (0xF168C)
     */
    public data object BOOK_EDIT : MaterialDesignGlyphs(0xF168C)

    /**
     * 󱚍 (0xF168D)
     */
    public data object BOOK_EDIT_OUTLINE : MaterialDesignGlyphs(0xF168D)

    /**
     * 󱚎 (0xF168E)
     */
    public data object BOOK_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF168E)

    /**
     * 󱚏 (0xF168F)
     */
    public data object BOOK_LOCK_OUTLINE : MaterialDesignGlyphs(0xF168F)

    /**
     * 󱚐 (0xF1690)
     */
    public data object BOOK_MARKER : MaterialDesignGlyphs(0xF1690)

    /**
     * 󱚑 (0xF1691)
     */
    public data object BOOK_MARKER_OUTLINE : MaterialDesignGlyphs(0xF1691)

    /**
     * 󱚒 (0xF1692)
     */
    public data object BOOK_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1692)

    /**
     * 󱚓 (0xF1693)
     */
    public data object BOOK_MUSIC_OUTLINE : MaterialDesignGlyphs(0xF1693)

    /**
     * 󱚔 (0xF1694)
     */
    public data object BOOK_OFF : MaterialDesignGlyphs(0xF1694)

    /**
     * 󱚕 (0xF1695)
     */
    public data object BOOK_OFF_OUTLINE : MaterialDesignGlyphs(0xF1695)

    /**
     * 󱚖 (0xF1696)
     */
    public data object BOOK_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1696)

    /**
     * 󱚗 (0xF1697)
     */
    public data object BOOK_REFRESH : MaterialDesignGlyphs(0xF1697)

    /**
     * 󱚘 (0xF1698)
     */
    public data object BOOK_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF1698)

    /**
     * 󱚙 (0xF1699)
     */
    public data object BOOK_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1699)

    /**
     * 󱚚 (0xF169A)
     */
    public data object BOOK_SETTINGS : MaterialDesignGlyphs(0xF169A)

    /**
     * 󱚛 (0xF169B)
     */
    public data object BOOK_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF169B)

    /**
     * 󱚜 (0xF169C)
     */
    public data object BOOK_SYNC : MaterialDesignGlyphs(0xF169C)

    /**
     * 󱚝 (0xF169D)
     */
    public data object ROBOT_ANGRY : MaterialDesignGlyphs(0xF169D)

    /**
     * 󱚞 (0xF169E)
     */
    public data object ROBOT_ANGRY_OUTLINE : MaterialDesignGlyphs(0xF169E)

    /**
     * 󱚟 (0xF169F)
     */
    public data object ROBOT_CONFUSED : MaterialDesignGlyphs(0xF169F)

    /**
     * 󱚠 (0xF16A0)
     */
    public data object ROBOT_CONFUSED_OUTLINE : MaterialDesignGlyphs(0xF16A0)

    /**
     * 󱚡 (0xF16A1)
     */
    public data object ROBOT_DEAD : MaterialDesignGlyphs(0xF16A1)

    /**
     * 󱚢 (0xF16A2)
     */
    public data object ROBOT_DEAD_OUTLINE : MaterialDesignGlyphs(0xF16A2)

    /**
     * 󱚣 (0xF16A3)
     */
    public data object ROBOT_EXCITED : MaterialDesignGlyphs(0xF16A3)

    /**
     * 󱚤 (0xF16A4)
     */
    public data object ROBOT_EXCITED_OUTLINE : MaterialDesignGlyphs(0xF16A4)

    /**
     * 󱚥 (0xF16A5)
     */
    public data object ROBOT_LOVE : MaterialDesignGlyphs(0xF16A5)

    /**
     * 󱚦 (0xF16A6)
     */
    public data object ROBOT_LOVE_OUTLINE : MaterialDesignGlyphs(0xF16A6)

    /**
     * 󱚧 (0xF16A7)
     */
    public data object ROBOT_OFF : MaterialDesignGlyphs(0xF16A7)

    /**
     * 󱚨 (0xF16A8)
     */
    public data object LOCK_CHECK_OUTLINE : MaterialDesignGlyphs(0xF16A8)

    /**
     * 󱚩 (0xF16A9)
     */
    public data object LOCK_MINUS : MaterialDesignGlyphs(0xF16A9)

    /**
     * 󱚪 (0xF16AA)
     */
    public data object LOCK_MINUS_OUTLINE : MaterialDesignGlyphs(0xF16AA)

    /**
     * 󱚫 (0xF16AB)
     */
    public data object LOCK_OPEN_CHECK_OUTLINE : MaterialDesignGlyphs(0xF16AB)

    /**
     * 󱚬 (0xF16AC)
     */
    public data object LOCK_OPEN_MINUS : MaterialDesignGlyphs(0xF16AC)

    /**
     * 󱚭 (0xF16AD)
     */
    public data object LOCK_OPEN_MINUS_OUTLINE : MaterialDesignGlyphs(0xF16AD)

    /**
     * 󱚮 (0xF16AE)
     */
    public data object LOCK_OPEN_PLUS : MaterialDesignGlyphs(0xF16AE)

    /**
     * 󱚯 (0xF16AF)
     */
    public data object LOCK_OPEN_PLUS_OUTLINE : MaterialDesignGlyphs(0xF16AF)

    /**
     * 󱚰 (0xF16B0)
     */
    public data object LOCK_OPEN_REMOVE : MaterialDesignGlyphs(0xF16B0)

    /**
     * 󱚱 (0xF16B1)
     */
    public data object LOCK_OPEN_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF16B1)

    /**
     * 󱚲 (0xF16B2)
     */
    public data object LOCK_PLUS_OUTLINE : MaterialDesignGlyphs(0xF16B2)

    /**
     * 󱚳 (0xF16B3)
     */
    public data object LOCK_REMOVE : MaterialDesignGlyphs(0xF16B3)

    /**
     * 󱚴 (0xF16B4)
     */
    public data object LOCK_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF16B4)

    /**
     * 󱚵 (0xF16B5)
     */
    public data object WIFI_ALERT : MaterialDesignGlyphs(0xF16B5)

    /**
     * 󱚶 (0xF16B6)
     */
    public data object WIFI_ARROW_DOWN : MaterialDesignGlyphs(0xF16B6)

    /**
     * 󱚷 (0xF16B7)
     */
    public data object WIFI_ARROW_LEFT : MaterialDesignGlyphs(0xF16B7)

    /**
     * 󱚸 (0xF16B8)
     */
    public data object WIFI_ARROW_LEFT_RIGHT : MaterialDesignGlyphs(0xF16B8)

    /**
     * 󱚹 (0xF16B9)
     */
    public data object WIFI_ARROW_RIGHT : MaterialDesignGlyphs(0xF16B9)

    /**
     * 󱚺 (0xF16BA)
     */
    public data object WIFI_ARROW_UP : MaterialDesignGlyphs(0xF16BA)

    /**
     * 󱚻 (0xF16BB)
     */
    public data object WIFI_ARROW_UP_DOWN : MaterialDesignGlyphs(0xF16BB)

    /**
     * 󱚼 (0xF16BC)
     */
    public data object WIFI_CANCEL : MaterialDesignGlyphs(0xF16BC)

    /**
     * 󱚽 (0xF16BD)
     */
    public data object WIFI_CHECK : MaterialDesignGlyphs(0xF16BD)

    /**
     * 󱚾 (0xF16BE)
     */
    public data object WIFI_COG : MaterialDesignGlyphs(0xF16BE)

    /**
     * 󱚿 (0xF16BF)
     */
    public data object WIFI_LOCK : MaterialDesignGlyphs(0xF16BF)

    /**
     * 󱛀 (0xF16C0)
     */
    public data object WIFI_LOCK_OPEN : MaterialDesignGlyphs(0xF16C0)

    /**
     * 󱛁 (0xF16C1)
     */
    public data object WIFI_MARKER : MaterialDesignGlyphs(0xF16C1)

    /**
     * 󱛂 (0xF16C2)
     */
    public data object WIFI_MINUS : MaterialDesignGlyphs(0xF16C2)

    /**
     * 󱛃 (0xF16C3)
     */
    public data object WIFI_PLUS : MaterialDesignGlyphs(0xF16C3)

    /**
     * 󱛄 (0xF16C4)
     */
    public data object WIFI_REFRESH : MaterialDesignGlyphs(0xF16C4)

    /**
     * 󱛅 (0xF16C5)
     */
    public data object WIFI_REMOVE : MaterialDesignGlyphs(0xF16C5)

    /**
     * 󱛆 (0xF16C6)
     */
    public data object WIFI_SETTINGS : MaterialDesignGlyphs(0xF16C6)

    /**
     * 󱛇 (0xF16C7)
     */
    public data object WIFI_SYNC : MaterialDesignGlyphs(0xF16C7)

    /**
     * 󱛈 (0xF16C8)
     */
    public data object BOOK_SYNC_OUTLINE : MaterialDesignGlyphs(0xF16C8)

    /**
     * 󱛉 (0xF16C9)
     */
    public data object BOOK_EDUCATION : MaterialDesignGlyphs(0xF16C9)

    /**
     * 󱛊 (0xF16CA)
     */
    public data object BOOK_EDUCATION_OUTLINE : MaterialDesignGlyphs(0xF16CA)

    /**
     * 󱛋 (0xF16CB)
     */
    public data object WIFI_STRENGTH_1_LOCK_OPEN : MaterialDesignGlyphs(0xF16CB)

    /**
     * 󱛌 (0xF16CC)
     */
    public data object WIFI_STRENGTH_2_LOCK_OPEN : MaterialDesignGlyphs(0xF16CC)

    /**
     * 󱛍 (0xF16CD)
     */
    public data object WIFI_STRENGTH_3_LOCK_OPEN : MaterialDesignGlyphs(0xF16CD)

    /**
     * 󱛎 (0xF16CE)
     */
    public data object WIFI_STRENGTH_4_LOCK_OPEN : MaterialDesignGlyphs(0xF16CE)

    /**
     * 󱛏 (0xF16CF)
     */
    public data object WIFI_STRENGTH_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF16CF)

    /**
     * 󱛐 (0xF16D0)
     */
    public data object COOKIE_ALERT : MaterialDesignGlyphs(0xF16D0)

    /**
     * 󱛑 (0xF16D1)
     */
    public data object COOKIE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF16D1)

    /**
     * 󱛒 (0xF16D2)
     */
    public data object COOKIE_CHECK : MaterialDesignGlyphs(0xF16D2)

    /**
     * 󱛓 (0xF16D3)
     */
    public data object COOKIE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF16D3)

    /**
     * 󱛔 (0xF16D4)
     */
    public data object COOKIE_COG : MaterialDesignGlyphs(0xF16D4)

    /**
     * 󱛕 (0xF16D5)
     */
    public data object COOKIE_COG_OUTLINE : MaterialDesignGlyphs(0xF16D5)

    /**
     * 󱛖 (0xF16D6)
     */
    public data object COOKIE_PLUS : MaterialDesignGlyphs(0xF16D6)

    /**
     * 󱛗 (0xF16D7)
     */
    public data object COOKIE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF16D7)

    /**
     * 󱛘 (0xF16D8)
     */
    public data object COOKIE_REMOVE : MaterialDesignGlyphs(0xF16D8)

    /**
     * 󱛙 (0xF16D9)
     */
    public data object COOKIE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF16D9)

    /**
     * 󱛚 (0xF16DA)
     */
    public data object COOKIE_MINUS : MaterialDesignGlyphs(0xF16DA)

    /**
     * 󱛛 (0xF16DB)
     */
    public data object COOKIE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF16DB)

    /**
     * 󱛜 (0xF16DC)
     */
    public data object COOKIE_SETTINGS : MaterialDesignGlyphs(0xF16DC)

    /**
     * 󱛝 (0xF16DD)
     */
    public data object COOKIE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF16DD)

    /**
     * 󱛞 (0xF16DE)
     */
    public data object COOKIE_OUTLINE : MaterialDesignGlyphs(0xF16DE)

    /**
     * 󱛟 (0xF16DF)
     */
    public data object TAPE_DRIVE : MaterialDesignGlyphs(0xF16DF)

    /**
     * 󱛠 (0xF16E0)
     */
    public data object ABACUS : MaterialDesignGlyphs(0xF16E0)

    /**
     * 󱛡 (0xF16E1)
     */
    public data object CALENDAR_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF16E1)

    /**
     * 󱛢 (0xF16E2)
     */
    public data object CLIPBOARD_CLOCK : MaterialDesignGlyphs(0xF16E2)

    /**
     * 󱛣 (0xF16E3)
     */
    public data object CLIPBOARD_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF16E3)

    /**
     * 󱛤 (0xF16E4)
     */
    public data object COOKIE_CLOCK : MaterialDesignGlyphs(0xF16E4)

    /**
     * 󱛥 (0xF16E5)
     */
    public data object COOKIE_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF16E5)

    /**
     * 󱛦 (0xF16E6)
     */
    public data object COOKIE_EDIT : MaterialDesignGlyphs(0xF16E6)

    /**
     * 󱛧 (0xF16E7)
     */
    public data object COOKIE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF16E7)

    /**
     * 󱛨 (0xF16E8)
     */
    public data object COOKIE_LOCK : MaterialDesignGlyphs(0xF16E8)

    /**
     * 󱛩 (0xF16E9)
     */
    public data object COOKIE_LOCK_OUTLINE : MaterialDesignGlyphs(0xF16E9)

    /**
     * 󱛪 (0xF16EA)
     */
    public data object COOKIE_OFF : MaterialDesignGlyphs(0xF16EA)

    /**
     * 󱛫 (0xF16EB)
     */
    public data object COOKIE_OFF_OUTLINE : MaterialDesignGlyphs(0xF16EB)

    /**
     * 󱛬 (0xF16EC)
     */
    public data object COOKIE_REFRESH : MaterialDesignGlyphs(0xF16EC)

    /**
     * 󱛭 (0xF16ED)
     */
    public data object COOKIE_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF16ED)

    /**
     * 󱛮 (0xF16EE)
     */
    public data object DOG_SIDE_OFF : MaterialDesignGlyphs(0xF16EE)

    /**
     * 󱛯 (0xF16EF)
     */
    public data object GIFT_OFF : MaterialDesignGlyphs(0xF16EF)

    /**
     * 󱛰 (0xF16F0)
     */
    public data object GIFT_OFF_OUTLINE : MaterialDesignGlyphs(0xF16F0)

    /**
     * 󱛱 (0xF16F1)
     */
    public data object GIFT_OPEN : MaterialDesignGlyphs(0xF16F1)

    /**
     * 󱛲 (0xF16F2)
     */
    public data object GIFT_OPEN_OUTLINE : MaterialDesignGlyphs(0xF16F2)

    /**
     * 󱛳 (0xF16F3)
     */
    public data object MOVIE_CHECK : MaterialDesignGlyphs(0xF16F3)

    /**
     * 󱛴 (0xF16F4)
     */
    public data object MOVIE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF16F4)

    /**
     * 󱛵 (0xF16F5)
     */
    public data object MOVIE_COG : MaterialDesignGlyphs(0xF16F5)

    /**
     * 󱛶 (0xF16F6)
     */
    public data object MOVIE_COG_OUTLINE : MaterialDesignGlyphs(0xF16F6)

    /**
     * 󱛷 (0xF16F7)
     */
    public data object MOVIE_MINUS : MaterialDesignGlyphs(0xF16F7)

    /**
     * 󱛸 (0xF16F8)
     */
    public data object MOVIE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF16F8)

    /**
     * 󱛹 (0xF16F9)
     */
    public data object MOVIE_OFF : MaterialDesignGlyphs(0xF16F9)

    /**
     * 󱛺 (0xF16FA)
     */
    public data object MOVIE_OFF_OUTLINE : MaterialDesignGlyphs(0xF16FA)

    /**
     * 󱛻 (0xF16FB)
     */
    public data object MOVIE_OPEN_CHECK : MaterialDesignGlyphs(0xF16FB)

    /**
     * 󱛼 (0xF16FC)
     */
    public data object MOVIE_OPEN_CHECK_OUTLINE : MaterialDesignGlyphs(0xF16FC)

    /**
     * 󱛽 (0xF16FD)
     */
    public data object MOVIE_OPEN_COG : MaterialDesignGlyphs(0xF16FD)

    /**
     * 󱛾 (0xF16FE)
     */
    public data object MOVIE_OPEN_COG_OUTLINE : MaterialDesignGlyphs(0xF16FE)

    /**
     * 󱛿 (0xF16FF)
     */
    public data object MOVIE_OPEN_EDIT : MaterialDesignGlyphs(0xF16FF)

    /**
     * 󱜀 (0xF1700)
     */
    public data object MOVIE_OPEN_EDIT_OUTLINE : MaterialDesignGlyphs(0xF1700)

    /**
     * 󱜁 (0xF1701)
     */
    public data object MOVIE_OPEN_MINUS : MaterialDesignGlyphs(0xF1701)

    /**
     * 󱜂 (0xF1702)
     */
    public data object MOVIE_OPEN_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1702)

    /**
     * 󱜃 (0xF1703)
     */
    public data object MOVIE_OPEN_OFF : MaterialDesignGlyphs(0xF1703)

    /**
     * 󱜄 (0xF1704)
     */
    public data object MOVIE_OPEN_OFF_OUTLINE : MaterialDesignGlyphs(0xF1704)

    /**
     * 󱜅 (0xF1705)
     */
    public data object MOVIE_OPEN_PLAY : MaterialDesignGlyphs(0xF1705)

    /**
     * 󱜆 (0xF1706)
     */
    public data object MOVIE_OPEN_PLAY_OUTLINE : MaterialDesignGlyphs(0xF1706)

    /**
     * 󱜇 (0xF1707)
     */
    public data object MOVIE_OPEN_PLUS : MaterialDesignGlyphs(0xF1707)

    /**
     * 󱜈 (0xF1708)
     */
    public data object MOVIE_OPEN_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1708)

    /**
     * 󱜉 (0xF1709)
     */
    public data object MOVIE_OPEN_REMOVE : MaterialDesignGlyphs(0xF1709)

    /**
     * 󱜊 (0xF170A)
     */
    public data object MOVIE_OPEN_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF170A)

    /**
     * 󱜋 (0xF170B)
     */
    public data object MOVIE_OPEN_SETTINGS : MaterialDesignGlyphs(0xF170B)

    /**
     * 󱜌 (0xF170C)
     */
    public data object MOVIE_OPEN_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF170C)

    /**
     * 󱜍 (0xF170D)
     */
    public data object MOVIE_OPEN_STAR : MaterialDesignGlyphs(0xF170D)

    /**
     * 󱜎 (0xF170E)
     */
    public data object MOVIE_OPEN_STAR_OUTLINE : MaterialDesignGlyphs(0xF170E)

    /**
     * 󱜏 (0xF170F)
     */
    public data object MOVIE_PLAY : MaterialDesignGlyphs(0xF170F)

    /**
     * 󱜐 (0xF1710)
     */
    public data object MOVIE_PLAY_OUTLINE : MaterialDesignGlyphs(0xF1710)

    /**
     * 󱜑 (0xF1711)
     */
    public data object MOVIE_PLUS : MaterialDesignGlyphs(0xF1711)

    /**
     * 󱜒 (0xF1712)
     */
    public data object MOVIE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1712)

    /**
     * 󱜓 (0xF1713)
     */
    public data object MOVIE_REMOVE : MaterialDesignGlyphs(0xF1713)

    /**
     * 󱜔 (0xF1714)
     */
    public data object MOVIE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1714)

    /**
     * 󱜕 (0xF1715)
     */
    public data object MOVIE_SETTINGS : MaterialDesignGlyphs(0xF1715)

    /**
     * 󱜖 (0xF1716)
     */
    public data object MOVIE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF1716)

    /**
     * 󱜗 (0xF1717)
     */
    public data object MOVIE_STAR : MaterialDesignGlyphs(0xF1717)

    /**
     * 󱜘 (0xF1718)
     */
    public data object MOVIE_STAR_OUTLINE : MaterialDesignGlyphs(0xF1718)

    /**
     * 󱜙 (0xF1719)
     */
    public data object ROBOT_HAPPY : MaterialDesignGlyphs(0xF1719)

    /**
     * 󱜚 (0xF171A)
     */
    public data object ROBOT_HAPPY_OUTLINE : MaterialDesignGlyphs(0xF171A)

    /**
     * 󱜛 (0xF171B)
     */
    public data object TURKEY : MaterialDesignGlyphs(0xF171B)

    /**
     * 󱜜 (0xF171C)
     */
    public data object FOOD_TURKEY : MaterialDesignGlyphs(0xF171C)

    /**
     * 󱜝 (0xF171D)
     */
    public data object FAN_AUTO : MaterialDesignGlyphs(0xF171D)

    /**
     * 󱜞 (0xF171E)
     */
    public data object ALARM_LIGHT_OFF : MaterialDesignGlyphs(0xF171E)

    /**
     * 󱜟 (0xF171F)
     */
    public data object ALARM_LIGHT_OFF_OUTLINE : MaterialDesignGlyphs(0xF171F)

    /**
     * 󱜠 (0xF1720)
     */
    public data object BROADCAST : MaterialDesignGlyphs(0xF1720)

    /**
     * 󱜡 (0xF1721)
     */
    public data object BROADCAST_OFF : MaterialDesignGlyphs(0xF1721)

    /**
     * 󱜢 (0xF1722)
     */
    public data object FIRE_OFF : MaterialDesignGlyphs(0xF1722)

    /**
     * 󱜣 (0xF1723)
     */
    public data object FIREWORK_OFF : MaterialDesignGlyphs(0xF1723)

    /**
     * 󱜤 (0xF1724)
     */
    public data object PROJECTOR_SCREEN_OUTLINE : MaterialDesignGlyphs(0xF1724)

    /**
     * 󱜥 (0xF1725)
     */
    public data object SCRIPT_TEXT_KEY : MaterialDesignGlyphs(0xF1725)

    /**
     * 󱜦 (0xF1726)
     */
    public data object SCRIPT_TEXT_KEY_OUTLINE : MaterialDesignGlyphs(0xF1726)

    /**
     * 󱜧 (0xF1727)
     */
    public data object SCRIPT_TEXT_PLAY : MaterialDesignGlyphs(0xF1727)

    /**
     * 󱜨 (0xF1728)
     */
    public data object SCRIPT_TEXT_PLAY_OUTLINE : MaterialDesignGlyphs(0xF1728)

    /**
     * 󱜩 (0xF1729)
     */
    public data object SURROUND_SOUND_2_1 : MaterialDesignGlyphs(0xF1729)

    /**
     * 󱜪 (0xF172A)
     */
    public data object SURROUND_SOUND_5_1_2 : MaterialDesignGlyphs(0xF172A)

    /**
     * 󱜫 (0xF172B)
     */
    public data object TAG_ARROW_DOWN : MaterialDesignGlyphs(0xF172B)

    /**
     * 󱜬 (0xF172C)
     */
    public data object TAG_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF172C)

    /**
     * 󱜭 (0xF172D)
     */
    public data object TAG_ARROW_LEFT : MaterialDesignGlyphs(0xF172D)

    /**
     * 󱜮 (0xF172E)
     */
    public data object TAG_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF172E)

    /**
     * 󱜯 (0xF172F)
     */
    public data object TAG_ARROW_RIGHT : MaterialDesignGlyphs(0xF172F)

    /**
     * 󱜰 (0xF1730)
     */
    public data object TAG_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF1730)

    /**
     * 󱜱 (0xF1731)
     */
    public data object TAG_ARROW_UP : MaterialDesignGlyphs(0xF1731)

    /**
     * 󱜲 (0xF1732)
     */
    public data object TAG_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF1732)

    /**
     * 󱜳 (0xF1733)
     */
    public data object TRAIN_CAR_PASSENGER : MaterialDesignGlyphs(0xF1733)

    /**
     * 󱜴 (0xF1734)
     */
    public data object TRAIN_CAR_PASSENGER_DOOR : MaterialDesignGlyphs(0xF1734)

    /**
     * 󱜵 (0xF1735)
     */
    public data object TRAIN_CAR_PASSENGER_DOOR_OPEN : MaterialDesignGlyphs(0xF1735)

    /**
     * 󱜶 (0xF1736)
     */
    public data object TRAIN_CAR_PASSENGER_VARIANT : MaterialDesignGlyphs(0xF1736)

    /**
     * 󱜷 (0xF1737)
     */
    public data object WEBCAM_OFF : MaterialDesignGlyphs(0xF1737)

    /**
     * 󱜸 (0xF1738)
     */
    public data object CHAT_QUESTION : MaterialDesignGlyphs(0xF1738)

    /**
     * 󱜹 (0xF1739)
     */
    public data object CHAT_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF1739)

    /**
     * 󱜺 (0xF173A)
     */
    public data object MESSAGE_QUESTION : MaterialDesignGlyphs(0xF173A)

    /**
     * 󱜻 (0xF173B)
     */
    public data object MESSAGE_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF173B)

    /**
     * 󱜼 (0xF173C)
     */
    public data object KETTLE_POUR_OVER : MaterialDesignGlyphs(0xF173C)

    /**
     * 󱜽 (0xF173D)
     */
    public data object MESSAGE_REPLY_OUTLINE : MaterialDesignGlyphs(0xF173D)

    /**
     * 󱜾 (0xF173E)
     */
    public data object MESSAGE_REPLY_TEXT_OUTLINE : MaterialDesignGlyphs(0xF173E)

    /**
     * 󱜿 (0xF173F)
     */
    public data object KOALA : MaterialDesignGlyphs(0xF173F)

    /**
     * 󱝀 (0xF1740)
     */
    public data object CHECK_DECAGRAM_OUTLINE : MaterialDesignGlyphs(0xF1740)

    /**
     * 󱝁 (0xF1741)
     */
    public data object STAR_SHOOTING : MaterialDesignGlyphs(0xF1741)

    /**
     * 󱝂 (0xF1742)
     */
    public data object STAR_SHOOTING_OUTLINE : MaterialDesignGlyphs(0xF1742)

    /**
     * 󱝃 (0xF1743)
     */
    public data object TABLE_PICNIC : MaterialDesignGlyphs(0xF1743)

    /**
     * 󱝄 (0xF1744)
     */
    public data object KITESURFING : MaterialDesignGlyphs(0xF1744)

    /**
     * 󱝅 (0xF1745)
     */
    public data object PARAGLIDING : MaterialDesignGlyphs(0xF1745)

    /**
     * 󱝆 (0xF1746)
     */
    public data object SURFING : MaterialDesignGlyphs(0xF1746)

    /**
     * 󱝇 (0xF1747)
     */
    public data object FLOOR_LAMP_TORCHIERE : MaterialDesignGlyphs(0xF1747)

    /**
     * 󱝈 (0xF1748)
     */
    public data object MORTAR_PESTLE : MaterialDesignGlyphs(0xF1748)

    /**
     * 󱝉 (0xF1749)
     */
    public data object CAST_AUDIO_VARIANT : MaterialDesignGlyphs(0xF1749)

    /**
     * 󱝊 (0xF174A)
     */
    public data object GRADIENT_HORIZONTAL : MaterialDesignGlyphs(0xF174A)

    /**
     * 󱝋 (0xF174B)
     */
    public data object ARCHIVE_CANCEL : MaterialDesignGlyphs(0xF174B)

    /**
     * 󱝌 (0xF174C)
     */
    public data object ARCHIVE_CANCEL_OUTLINE : MaterialDesignGlyphs(0xF174C)

    /**
     * 󱝍 (0xF174D)
     */
    public data object ARCHIVE_CHECK : MaterialDesignGlyphs(0xF174D)

    /**
     * 󱝎 (0xF174E)
     */
    public data object ARCHIVE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF174E)

    /**
     * 󱝏 (0xF174F)
     */
    public data object ARCHIVE_CLOCK : MaterialDesignGlyphs(0xF174F)

    /**
     * 󱝐 (0xF1750)
     */
    public data object ARCHIVE_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF1750)

    /**
     * 󱝑 (0xF1751)
     */
    public data object ARCHIVE_COG : MaterialDesignGlyphs(0xF1751)

    /**
     * 󱝒 (0xF1752)
     */
    public data object ARCHIVE_COG_OUTLINE : MaterialDesignGlyphs(0xF1752)

    /**
     * 󱝓 (0xF1753)
     */
    public data object ARCHIVE_EDIT : MaterialDesignGlyphs(0xF1753)

    /**
     * 󱝔 (0xF1754)
     */
    public data object ARCHIVE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF1754)

    /**
     * 󱝕 (0xF1755)
     */
    public data object ARCHIVE_EYE : MaterialDesignGlyphs(0xF1755)

    /**
     * 󱝖 (0xF1756)
     */
    public data object ARCHIVE_EYE_OUTLINE : MaterialDesignGlyphs(0xF1756)

    /**
     * 󱝗 (0xF1757)
     */
    public data object ARCHIVE_LOCK : MaterialDesignGlyphs(0xF1757)

    /**
     * 󱝘 (0xF1758)
     */
    public data object ARCHIVE_LOCK_OPEN : MaterialDesignGlyphs(0xF1758)

    /**
     * 󱝙 (0xF1759)
     */
    public data object ARCHIVE_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF1759)

    /**
     * 󱝚 (0xF175A)
     */
    public data object ARCHIVE_LOCK_OUTLINE : MaterialDesignGlyphs(0xF175A)

    /**
     * 󱝛 (0xF175B)
     */
    public data object ARCHIVE_MARKER : MaterialDesignGlyphs(0xF175B)

    /**
     * 󱝜 (0xF175C)
     */
    public data object ARCHIVE_MARKER_OUTLINE : MaterialDesignGlyphs(0xF175C)

    /**
     * 󱝝 (0xF175D)
     */
    public data object ARCHIVE_MINUS : MaterialDesignGlyphs(0xF175D)

    /**
     * 󱝞 (0xF175E)
     */
    public data object ARCHIVE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF175E)

    /**
     * 󱝟 (0xF175F)
     */
    public data object ARCHIVE_MUSIC : MaterialDesignGlyphs(0xF175F)

    /**
     * 󱝠 (0xF1760)
     */
    public data object ARCHIVE_MUSIC_OUTLINE : MaterialDesignGlyphs(0xF1760)

    /**
     * 󱝡 (0xF1761)
     */
    public data object ARCHIVE_OFF : MaterialDesignGlyphs(0xF1761)

    /**
     * 󱝢 (0xF1762)
     */
    public data object ARCHIVE_OFF_OUTLINE : MaterialDesignGlyphs(0xF1762)

    /**
     * 󱝣 (0xF1763)
     */
    public data object ARCHIVE_PLUS : MaterialDesignGlyphs(0xF1763)

    /**
     * 󱝤 (0xF1764)
     */
    public data object ARCHIVE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1764)

    /**
     * 󱝥 (0xF1765)
     */
    public data object ARCHIVE_REFRESH : MaterialDesignGlyphs(0xF1765)

    /**
     * 󱝦 (0xF1766)
     */
    public data object ARCHIVE_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF1766)

    /**
     * 󱝧 (0xF1767)
     */
    public data object ARCHIVE_REMOVE : MaterialDesignGlyphs(0xF1767)

    /**
     * 󱝨 (0xF1768)
     */
    public data object ARCHIVE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1768)

    /**
     * 󱝩 (0xF1769)
     */
    public data object ARCHIVE_SEARCH : MaterialDesignGlyphs(0xF1769)

    /**
     * 󱝪 (0xF176A)
     */
    public data object ARCHIVE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF176A)

    /**
     * 󱝫 (0xF176B)
     */
    public data object ARCHIVE_SETTINGS : MaterialDesignGlyphs(0xF176B)

    /**
     * 󱝬 (0xF176C)
     */
    public data object ARCHIVE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF176C)

    /**
     * 󱝭 (0xF176D)
     */
    public data object ARCHIVE_STAR : MaterialDesignGlyphs(0xF176D)

    /**
     * 󱝮 (0xF176E)
     */
    public data object ARCHIVE_STAR_OUTLINE : MaterialDesignGlyphs(0xF176E)

    /**
     * 󱝯 (0xF176F)
     */
    public data object ARCHIVE_SYNC : MaterialDesignGlyphs(0xF176F)

    /**
     * 󱝰 (0xF1770)
     */
    public data object ARCHIVE_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1770)

    /**
     * 󱝱 (0xF1771)
     */
    public data object BRUSH_OFF : MaterialDesignGlyphs(0xF1771)

    /**
     * 󱝲 (0xF1772)
     */
    public data object FILE_IMAGE_MARKER : MaterialDesignGlyphs(0xF1772)

    /**
     * 󱝳 (0xF1773)
     */
    public data object FILE_IMAGE_MARKER_OUTLINE : MaterialDesignGlyphs(0xF1773)

    /**
     * 󱝴 (0xF1774)
     */
    public data object FILE_MARKER : MaterialDesignGlyphs(0xF1774)

    /**
     * 󱝵 (0xF1775)
     */
    public data object FILE_MARKER_OUTLINE : MaterialDesignGlyphs(0xF1775)

    /**
     * 󱝶 (0xF1776)
     */
    public data object HAMBURGER_CHECK : MaterialDesignGlyphs(0xF1776)

    /**
     * 󱝷 (0xF1777)
     */
    public data object HAMBURGER_MINUS : MaterialDesignGlyphs(0xF1777)

    /**
     * 󱝸 (0xF1778)
     */
    public data object HAMBURGER_OFF : MaterialDesignGlyphs(0xF1778)

    /**
     * 󱝹 (0xF1779)
     */
    public data object HAMBURGER_PLUS : MaterialDesignGlyphs(0xF1779)

    /**
     * 󱝺 (0xF177A)
     */
    public data object HAMBURGER_REMOVE : MaterialDesignGlyphs(0xF177A)

    /**
     * 󱝻 (0xF177B)
     */
    public data object IMAGE_MARKER : MaterialDesignGlyphs(0xF177B)

    /**
     * 󱝼 (0xF177C)
     */
    public data object IMAGE_MARKER_OUTLINE : MaterialDesignGlyphs(0xF177C)

    /**
     * 󱝽 (0xF177D)
     */
    public data object NOTE_ALERT : MaterialDesignGlyphs(0xF177D)

    /**
     * 󱝾 (0xF177E)
     */
    public data object NOTE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF177E)

    /**
     * 󱝿 (0xF177F)
     */
    public data object NOTE_CHECK : MaterialDesignGlyphs(0xF177F)

    /**
     * 󱞀 (0xF1780)
     */
    public data object NOTE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1780)

    /**
     * 󱞁 (0xF1781)
     */
    public data object NOTE_EDIT : MaterialDesignGlyphs(0xF1781)

    /**
     * 󱞂 (0xF1782)
     */
    public data object NOTE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF1782)

    /**
     * 󱞃 (0xF1783)
     */
    public data object NOTE_OFF : MaterialDesignGlyphs(0xF1783)

    /**
     * 󱞄 (0xF1784)
     */
    public data object NOTE_OFF_OUTLINE : MaterialDesignGlyphs(0xF1784)

    /**
     * 󱞅 (0xF1785)
     */
    public data object PRINTER_OFF_OUTLINE : MaterialDesignGlyphs(0xF1785)

    /**
     * 󱞆 (0xF1786)
     */
    public data object PRINTER_OUTLINE : MaterialDesignGlyphs(0xF1786)

    /**
     * 󱞇 (0xF1787)
     */
    public data object PROGRESS_PENCIL : MaterialDesignGlyphs(0xF1787)

    /**
     * 󱞈 (0xF1788)
     */
    public data object PROGRESS_STAR : MaterialDesignGlyphs(0xF1788)

    /**
     * 󱞉 (0xF1789)
     */
    public data object SAUSAGE_OFF : MaterialDesignGlyphs(0xF1789)

    /**
     * 󱞊 (0xF178A)
     */
    public data object FOLDER_EYE : MaterialDesignGlyphs(0xF178A)

    /**
     * 󱞋 (0xF178B)
     */
    public data object FOLDER_EYE_OUTLINE : MaterialDesignGlyphs(0xF178B)

    /**
     * 󱞌 (0xF178C)
     */
    public data object INFORMATION_OFF : MaterialDesignGlyphs(0xF178C)

    /**
     * 󱞍 (0xF178D)
     */
    public data object INFORMATION_OFF_OUTLINE : MaterialDesignGlyphs(0xF178D)

    /**
     * 󱞎 (0xF178E)
     */
    public data object STICKER_TEXT : MaterialDesignGlyphs(0xF178E)

    /**
     * 󱞏 (0xF178F)
     */
    public data object STICKER_TEXT_OUTLINE : MaterialDesignGlyphs(0xF178F)

    /**
     * 󱞐 (0xF1790)
     */
    public data object WEB_CANCEL : MaterialDesignGlyphs(0xF1790)

    /**
     * 󱞑 (0xF1791)
     */
    public data object WEB_REFRESH : MaterialDesignGlyphs(0xF1791)

    /**
     * 󱞒 (0xF1792)
     */
    public data object WEB_SYNC : MaterialDesignGlyphs(0xF1792)

    /**
     * 󱞓 (0xF1793)
     */
    public data object CHANDELIER : MaterialDesignGlyphs(0xF1793)

    /**
     * 󱞔 (0xF1794)
     */
    public data object HOME_SWITCH : MaterialDesignGlyphs(0xF1794)

    /**
     * 󱞕 (0xF1795)
     */
    public data object HOME_SWITCH_OUTLINE : MaterialDesignGlyphs(0xF1795)

    /**
     * 󱞖 (0xF1796)
     */
    public data object SUN_SNOWFLAKE : MaterialDesignGlyphs(0xF1796)

    /**
     * 󱞗 (0xF1797)
     */
    public data object CEILING_FAN : MaterialDesignGlyphs(0xF1797)

    /**
     * 󱞘 (0xF1798)
     */
    public data object CEILING_FAN_LIGHT : MaterialDesignGlyphs(0xF1798)

    /**
     * 󱞙 (0xF1799)
     */
    public data object SMOKE : MaterialDesignGlyphs(0xF1799)

    /**
     * 󱞚 (0xF179A)
     */
    public data object FENCE : MaterialDesignGlyphs(0xF179A)

    /**
     * 󱞛 (0xF179B)
     */
    public data object LIGHT_RECESSED : MaterialDesignGlyphs(0xF179B)

    /**
     * 󱞜 (0xF179C)
     */
    public data object BATTERY_LOCK : MaterialDesignGlyphs(0xF179C)

    /**
     * 󱞝 (0xF179D)
     */
    public data object BATTERY_LOCK_OPEN : MaterialDesignGlyphs(0xF179D)

    /**
     * 󱞞 (0xF179E)
     */
    public data object FOLDER_HIDDEN : MaterialDesignGlyphs(0xF179E)

    /**
     * 󱞟 (0xF179F)
     */
    public data object MIRROR_RECTANGLE : MaterialDesignGlyphs(0xF179F)

    /**
     * 󱞠 (0xF17A0)
     */
    public data object MIRROR_VARIANT : MaterialDesignGlyphs(0xF17A0)

    /**
     * 󱞡 (0xF17A1)
     */
    public data object ARROW_DOWN_LEFT : MaterialDesignGlyphs(0xF17A1)

    /**
     * 󱞢 (0xF17A2)
     */
    public data object ARROW_DOWN_LEFT_BOLD : MaterialDesignGlyphs(0xF17A2)

    /**
     * 󱞣 (0xF17A3)
     */
    public data object ARROW_DOWN_RIGHT : MaterialDesignGlyphs(0xF17A3)

    /**
     * 󱞤 (0xF17A4)
     */
    public data object ARROW_DOWN_RIGHT_BOLD : MaterialDesignGlyphs(0xF17A4)

    /**
     * 󱞥 (0xF17A5)
     */
    public data object ARROW_LEFT_BOTTOM : MaterialDesignGlyphs(0xF17A5)

    /**
     * 󱞦 (0xF17A6)
     */
    public data object ARROW_LEFT_BOTTOM_BOLD : MaterialDesignGlyphs(0xF17A6)

    /**
     * 󱞧 (0xF17A7)
     */
    public data object ARROW_LEFT_TOP : MaterialDesignGlyphs(0xF17A7)

    /**
     * 󱞨 (0xF17A8)
     */
    public data object ARROW_LEFT_TOP_BOLD : MaterialDesignGlyphs(0xF17A8)

    /**
     * 󱞩 (0xF17A9)
     */
    public data object ARROW_RIGHT_BOTTOM : MaterialDesignGlyphs(0xF17A9)

    /**
     * 󱞪 (0xF17AA)
     */
    public data object ARROW_RIGHT_BOTTOM_BOLD : MaterialDesignGlyphs(0xF17AA)

    /**
     * 󱞫 (0xF17AB)
     */
    public data object ARROW_RIGHT_TOP : MaterialDesignGlyphs(0xF17AB)

    /**
     * 󱞬 (0xF17AC)
     */
    public data object ARROW_RIGHT_TOP_BOLD : MaterialDesignGlyphs(0xF17AC)

    /**
     * 󱞭 (0xF17AD)
     */
    public data object ARROW_U_DOWN_LEFT : MaterialDesignGlyphs(0xF17AD)

    /**
     * 󱞮 (0xF17AE)
     */
    public data object ARROW_U_DOWN_LEFT_BOLD : MaterialDesignGlyphs(0xF17AE)

    /**
     * 󱞯 (0xF17AF)
     */
    public data object ARROW_U_DOWN_RIGHT : MaterialDesignGlyphs(0xF17AF)

    /**
     * 󱞰 (0xF17B0)
     */
    public data object ARROW_U_DOWN_RIGHT_BOLD : MaterialDesignGlyphs(0xF17B0)

    /**
     * 󱞱 (0xF17B1)
     */
    public data object ARROW_U_LEFT_BOTTOM : MaterialDesignGlyphs(0xF17B1)

    /**
     * 󱞲 (0xF17B2)
     */
    public data object ARROW_U_LEFT_BOTTOM_BOLD : MaterialDesignGlyphs(0xF17B2)

    /**
     * 󱞳 (0xF17B3)
     */
    public data object ARROW_U_LEFT_TOP : MaterialDesignGlyphs(0xF17B3)

    /**
     * 󱞴 (0xF17B4)
     */
    public data object ARROW_U_LEFT_TOP_BOLD : MaterialDesignGlyphs(0xF17B4)

    /**
     * 󱞵 (0xF17B5)
     */
    public data object ARROW_U_RIGHT_BOTTOM : MaterialDesignGlyphs(0xF17B5)

    /**
     * 󱞶 (0xF17B6)
     */
    public data object ARROW_U_RIGHT_BOTTOM_BOLD : MaterialDesignGlyphs(0xF17B6)

    /**
     * 󱞷 (0xF17B7)
     */
    public data object ARROW_U_RIGHT_TOP : MaterialDesignGlyphs(0xF17B7)

    /**
     * 󱞸 (0xF17B8)
     */
    public data object ARROW_U_RIGHT_TOP_BOLD : MaterialDesignGlyphs(0xF17B8)

    /**
     * 󱞹 (0xF17B9)
     */
    public data object ARROW_U_UP_LEFT : MaterialDesignGlyphs(0xF17B9)

    /**
     * 󱞺 (0xF17BA)
     */
    public data object ARROW_U_UP_LEFT_BOLD : MaterialDesignGlyphs(0xF17BA)

    /**
     * 󱞻 (0xF17BB)
     */
    public data object ARROW_U_UP_RIGHT : MaterialDesignGlyphs(0xF17BB)

    /**
     * 󱞼 (0xF17BC)
     */
    public data object ARROW_U_UP_RIGHT_BOLD : MaterialDesignGlyphs(0xF17BC)

    /**
     * 󱞽 (0xF17BD)
     */
    public data object ARROW_UP_LEFT : MaterialDesignGlyphs(0xF17BD)

    /**
     * 󱞾 (0xF17BE)
     */
    public data object ARROW_UP_LEFT_BOLD : MaterialDesignGlyphs(0xF17BE)

    /**
     * 󱞿 (0xF17BF)
     */
    public data object ARROW_UP_RIGHT : MaterialDesignGlyphs(0xF17BF)

    /**
     * 󱟀 (0xF17C0)
     */
    public data object ARROW_UP_RIGHT_BOLD : MaterialDesignGlyphs(0xF17C0)

    /**
     * 󱟁 (0xF17C1)
     */
    public data object SELECT_REMOVE : MaterialDesignGlyphs(0xF17C1)

    /**
     * 󱟂 (0xF17C2)
     */
    public data object SELECTION_ELLIPSE_REMOVE : MaterialDesignGlyphs(0xF17C2)

    /**
     * 󱟃 (0xF17C3)
     */
    public data object SELECTION_REMOVE : MaterialDesignGlyphs(0xF17C3)

    /**
     * 󱟄 (0xF17C4)
     */
    public data object HUMAN_GREETING : MaterialDesignGlyphs(0xF17C4)

    /**
     * 󱟅 (0xF17C5)
     */
    public data object PH : MaterialDesignGlyphs(0xF17C5)

    /**
     * 󱟆 (0xF17C6)
     */
    public data object WATER_SYNC : MaterialDesignGlyphs(0xF17C6)

    /**
     * 󱟇 (0xF17C7)
     */
    public data object CEILING_LIGHT_OUTLINE : MaterialDesignGlyphs(0xF17C7)

    /**
     * 󱟈 (0xF17C8)
     */
    public data object FLOOR_LAMP_OUTLINE : MaterialDesignGlyphs(0xF17C8)

    /**
     * 󱟉 (0xF17C9)
     */
    public data object WALL_SCONCE_FLAT_OUTLINE : MaterialDesignGlyphs(0xF17C9)

    /**
     * 󱟊 (0xF17CA)
     */
    public data object WALL_SCONCE_FLAT_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF17CA)

    /**
     * 󱟋 (0xF17CB)
     */
    public data object WALL_SCONCE_OUTLINE : MaterialDesignGlyphs(0xF17CB)

    /**
     * 󱟌 (0xF17CC)
     */
    public data object WALL_SCONCE_ROUND_OUTLINE : MaterialDesignGlyphs(0xF17CC)

    /**
     * 󱟍 (0xF17CD)
     */
    public data object WALL_SCONCE_ROUND_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF17CD)

    /**
     * 󱟎 (0xF17CE)
     */
    public data object FLOOR_LAMP_DUAL_OUTLINE : MaterialDesignGlyphs(0xF17CE)

    /**
     * 󱟏 (0xF17CF)
     */
    public data object FLOOR_LAMP_TORCHIERE_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF17CF)

    /**
     * 󱟐 (0xF17D0)
     */
    public data object LAMP_OUTLINE : MaterialDesignGlyphs(0xF17D0)

    /**
     * 󱟑 (0xF17D1)
     */
    public data object LAMPS_OUTLINE : MaterialDesignGlyphs(0xF17D1)

    /**
     * 󱟒 (0xF17D2)
     */
    public data object CANDELABRA : MaterialDesignGlyphs(0xF17D2)

    /**
     * 󱟓 (0xF17D3)
     */
    public data object CANDELABRA_FIRE : MaterialDesignGlyphs(0xF17D3)

    /**
     * 󱟔 (0xF17D4)
     */
    public data object MENORAH : MaterialDesignGlyphs(0xF17D4)

    /**
     * 󱟕 (0xF17D5)
     */
    public data object MENORAH_FIRE : MaterialDesignGlyphs(0xF17D5)

    /**
     * 󱟖 (0xF17D6)
     */
    public data object FLOOR_LAMP_TORCHIERE_OUTLINE : MaterialDesignGlyphs(0xF17D6)

    /**
     * 󱟗 (0xF17D7)
     */
    public data object CREDIT_CARD_EDIT : MaterialDesignGlyphs(0xF17D7)

    /**
     * 󱟘 (0xF17D8)
     */
    public data object CREDIT_CARD_EDIT_OUTLINE : MaterialDesignGlyphs(0xF17D8)

    /**
     * 󱟙 (0xF17D9)
     */
    public data object BRIEFCASE_EYE : MaterialDesignGlyphs(0xF17D9)

    /**
     * 󱟚 (0xF17DA)
     */
    public data object BRIEFCASE_EYE_OUTLINE : MaterialDesignGlyphs(0xF17DA)

    /**
     * 󱟛 (0xF17DB)
     */
    public data object SOUNDBAR : MaterialDesignGlyphs(0xF17DB)

    /**
     * 󱟜 (0xF17DC)
     */
    public data object CROWN_CIRCLE : MaterialDesignGlyphs(0xF17DC)

    /**
     * 󱟝 (0xF17DD)
     */
    public data object CROWN_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF17DD)

    /**
     * 󱟞 (0xF17DE)
     */
    public data object BATTERY_ARROW_DOWN : MaterialDesignGlyphs(0xF17DE)

    /**
     * 󱟟 (0xF17DF)
     */
    public data object BATTERY_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF17DF)

    /**
     * 󱟠 (0xF17E0)
     */
    public data object BATTERY_ARROW_UP : MaterialDesignGlyphs(0xF17E0)

    /**
     * 󱟡 (0xF17E1)
     */
    public data object BATTERY_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF17E1)

    /**
     * 󱟢 (0xF17E2)
     */
    public data object BATTERY_CHECK : MaterialDesignGlyphs(0xF17E2)

    /**
     * 󱟣 (0xF17E3)
     */
    public data object BATTERY_CHECK_OUTLINE : MaterialDesignGlyphs(0xF17E3)

    /**
     * 󱟤 (0xF17E4)
     */
    public data object BATTERY_MINUS : MaterialDesignGlyphs(0xF17E4)

    /**
     * 󱟥 (0xF17E5)
     */
    public data object BATTERY_MINUS_OUTLINE : MaterialDesignGlyphs(0xF17E5)

    /**
     * 󱟦 (0xF17E6)
     */
    public data object BATTERY_PLUS : MaterialDesignGlyphs(0xF17E6)

    /**
     * 󱟧 (0xF17E7)
     */
    public data object BATTERY_PLUS_OUTLINE : MaterialDesignGlyphs(0xF17E7)

    /**
     * 󱟨 (0xF17E8)
     */
    public data object BATTERY_REMOVE : MaterialDesignGlyphs(0xF17E8)

    /**
     * 󱟩 (0xF17E9)
     */
    public data object BATTERY_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF17E9)

    /**
     * 󱟪 (0xF17EA)
     */
    public data object CHILI_ALERT : MaterialDesignGlyphs(0xF17EA)

    /**
     * 󱟫 (0xF17EB)
     */
    public data object CHILI_ALERT_OUTLINE : MaterialDesignGlyphs(0xF17EB)

    /**
     * 󱟬 (0xF17EC)
     */
    public data object CHILI_HOT_OUTLINE : MaterialDesignGlyphs(0xF17EC)

    /**
     * 󱟭 (0xF17ED)
     */
    public data object CHILI_MEDIUM_OUTLINE : MaterialDesignGlyphs(0xF17ED)

    /**
     * 󱟮 (0xF17EE)
     */
    public data object CHILI_MILD_OUTLINE : MaterialDesignGlyphs(0xF17EE)

    /**
     * 󱟯 (0xF17EF)
     */
    public data object CHILI_OFF_OUTLINE : MaterialDesignGlyphs(0xF17EF)

    /**
     * 󱟰 (0xF17F0)
     */
    public data object CAKE_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF17F0)

    /**
     * 󱟱 (0xF17F1)
     */
    public data object CARD_MULTIPLE : MaterialDesignGlyphs(0xF17F1)

    /**
     * 󱟲 (0xF17F2)
     */
    public data object CARD_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF17F2)

    /**
     * 󱟳 (0xF17F3)
     */
    public data object ACCOUNT_COWBOY_HAT_OUTLINE : MaterialDesignGlyphs(0xF17F3)

    /**
     * 󱟴 (0xF17F4)
     */
    public data object LIGHTBULB_SPOT : MaterialDesignGlyphs(0xF17F4)

    /**
     * 󱟵 (0xF17F5)
     */
    public data object LIGHTBULB_SPOT_OFF : MaterialDesignGlyphs(0xF17F5)

    /**
     * 󱟶 (0xF17F6)
     */
    public data object FENCE_ELECTRIC : MaterialDesignGlyphs(0xF17F6)

    /**
     * 󱟷 (0xF17F7)
     */
    public data object GATE_ARROW_LEFT : MaterialDesignGlyphs(0xF17F7)

    /**
     * 󱟸 (0xF17F8)
     */
    public data object GATE_ALERT : MaterialDesignGlyphs(0xF17F8)

    /**
     * 󱟹 (0xF17F9)
     */
    public data object BOOM_GATE_UP : MaterialDesignGlyphs(0xF17F9)

    /**
     * 󱟺 (0xF17FA)
     */
    public data object BOOM_GATE_UP_OUTLINE : MaterialDesignGlyphs(0xF17FA)

    /**
     * 󱟻 (0xF17FB)
     */
    public data object GARAGE_LOCK : MaterialDesignGlyphs(0xF17FB)

    /**
     * 󱟼 (0xF17FC)
     */
    public data object GARAGE_VARIANT_LOCK : MaterialDesignGlyphs(0xF17FC)

    /**
     * 󱟽 (0xF17FD)
     */
    public data object CELLPHONE_CHECK : MaterialDesignGlyphs(0xF17FD)

    /**
     * 󱟾 (0xF17FE)
     */
    public data object SUN_WIRELESS : MaterialDesignGlyphs(0xF17FE)

    /**
     * 󱟿 (0xF17FF)
     */
    public data object SUN_WIRELESS_OUTLINE : MaterialDesignGlyphs(0xF17FF)

    /**
     * 󱠀 (0xF1800)
     */
    public data object LIGHTBULB_AUTO : MaterialDesignGlyphs(0xF1800)

    /**
     * 󱠁 (0xF1801)
     */
    public data object LIGHTBULB_AUTO_OUTLINE : MaterialDesignGlyphs(0xF1801)

    /**
     * 󱠂 (0xF1802)
     */
    public data object LIGHTBULB_VARIANT : MaterialDesignGlyphs(0xF1802)

    /**
     * 󱠃 (0xF1803)
     */
    public data object LIGHTBULB_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1803)

    /**
     * 󱠄 (0xF1804)
     */
    public data object LIGHTBULB_FLUORESCENT_TUBE : MaterialDesignGlyphs(0xF1804)

    /**
     * 󱠅 (0xF1805)
     */
    public data object LIGHTBULB_FLUORESCENT_TUBE_OUTLINE : MaterialDesignGlyphs(0xF1805)

    /**
     * 󱠆 (0xF1806)
     */
    public data object WATER_CIRCLE : MaterialDesignGlyphs(0xF1806)

    /**
     * 󱠇 (0xF1807)
     */
    public data object FIRE_CIRCLE : MaterialDesignGlyphs(0xF1807)

    /**
     * 󱠈 (0xF1808)
     */
    public data object SMOKE_DETECTOR_OUTLINE : MaterialDesignGlyphs(0xF1808)

    /**
     * 󱠉 (0xF1809)
     */
    public data object SMOKE_DETECTOR_OFF : MaterialDesignGlyphs(0xF1809)

    /**
     * 󱠊 (0xF180A)
     */
    public data object SMOKE_DETECTOR_OFF_OUTLINE : MaterialDesignGlyphs(0xF180A)

    /**
     * 󱠋 (0xF180B)
     */
    public data object SMOKE_DETECTOR_VARIANT : MaterialDesignGlyphs(0xF180B)

    /**
     * 󱠌 (0xF180C)
     */
    public data object SMOKE_DETECTOR_VARIANT_OFF : MaterialDesignGlyphs(0xF180C)

    /**
     * 󱠍 (0xF180D)
     */
    public data object PROJECTOR_SCREEN_OFF : MaterialDesignGlyphs(0xF180D)

    /**
     * 󱠎 (0xF180E)
     */
    public data object PROJECTOR_SCREEN_OFF_OUTLINE : MaterialDesignGlyphs(0xF180E)

    /**
     * 󱠏 (0xF180F)
     */
    public data object PROJECTOR_SCREEN_VARIANT : MaterialDesignGlyphs(0xF180F)

    /**
     * 󱠐 (0xF1810)
     */
    public data object PROJECTOR_SCREEN_VARIANT_OFF : MaterialDesignGlyphs(0xF1810)

    /**
     * 󱠑 (0xF1811)
     */
    public data object PROJECTOR_SCREEN_VARIANT_OFF_OUTLINE : MaterialDesignGlyphs(0xF1811)

    /**
     * 󱠒 (0xF1812)
     */
    public data object PROJECTOR_SCREEN_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1812)

    /**
     * 󱠓 (0xF1813)
     */
    public data object BRUSH_VARIANT : MaterialDesignGlyphs(0xF1813)

    /**
     * 󱠔 (0xF1814)
     */
    public data object CAR_WRENCH : MaterialDesignGlyphs(0xF1814)

    /**
     * 󱠕 (0xF1815)
     */
    public data object ACCOUNT_INJURY : MaterialDesignGlyphs(0xF1815)

    /**
     * 󱠖 (0xF1816)
     */
    public data object ACCOUNT_INJURY_OUTLINE : MaterialDesignGlyphs(0xF1816)

    /**
     * 󱠗 (0xF1817)
     */
    public data object BALCONY : MaterialDesignGlyphs(0xF1817)

    /**
     * 󱠘 (0xF1818)
     */
    public data object BATHTUB : MaterialDesignGlyphs(0xF1818)

    /**
     * 󱠙 (0xF1819)
     */
    public data object BATHTUB_OUTLINE : MaterialDesignGlyphs(0xF1819)

    /**
     * 󱠚 (0xF181A)
     */
    public data object BLENDER_OUTLINE : MaterialDesignGlyphs(0xF181A)

    /**
     * 󱠛 (0xF181B)
     */
    public data object COFFEE_MAKER_OUTLINE : MaterialDesignGlyphs(0xF181B)

    /**
     * 󱠜 (0xF181C)
     */
    public data object COUNTERTOP : MaterialDesignGlyphs(0xF181C)

    /**
     * 󱠝 (0xF181D)
     */
    public data object COUNTERTOP_OUTLINE : MaterialDesignGlyphs(0xF181D)

    /**
     * 󱠞 (0xF181E)
     */
    public data object DOOR_SLIDING : MaterialDesignGlyphs(0xF181E)

    /**
     * 󱠟 (0xF181F)
     */
    public data object DOOR_SLIDING_LOCK : MaterialDesignGlyphs(0xF181F)

    /**
     * 󱠠 (0xF1820)
     */
    public data object DOOR_SLIDING_OPEN : MaterialDesignGlyphs(0xF1820)

    /**
     * 󱠡 (0xF1821)
     */
    public data object HAND_WAVE : MaterialDesignGlyphs(0xF1821)

    /**
     * 󱠢 (0xF1822)
     */
    public data object HAND_WAVE_OUTLINE : MaterialDesignGlyphs(0xF1822)

    /**
     * 󱠣 (0xF1823)
     */
    public data object HUMAN_MALE_FEMALE_CHILD : MaterialDesignGlyphs(0xF1823)

    /**
     * 󱠤 (0xF1824)
     */
    public data object IRON : MaterialDesignGlyphs(0xF1824)

    /**
     * 󱠥 (0xF1825)
     */
    public data object IRON_OUTLINE : MaterialDesignGlyphs(0xF1825)

    /**
     * 󱠦 (0xF1826)
     */
    public data object LIQUID_SPOT : MaterialDesignGlyphs(0xF1826)

    /**
     * 󱠧 (0xF1827)
     */
    public data object MOSQUE : MaterialDesignGlyphs(0xF1827)

    /**
     * 󱠨 (0xF1828)
     */
    public data object SHIELD_MOON : MaterialDesignGlyphs(0xF1828)

    /**
     * 󱠩 (0xF1829)
     */
    public data object SHIELD_MOON_OUTLINE : MaterialDesignGlyphs(0xF1829)

    /**
     * 󱠪 (0xF182A)
     */
    public data object TRAFFIC_LIGHT_OUTLINE : MaterialDesignGlyphs(0xF182A)

    /**
     * 󱠫 (0xF182B)
     */
    public data object HAND_FRONT_LEFT : MaterialDesignGlyphs(0xF182B)

    /**
     * 󱠬 (0xF182C)
     */
    public data object HAND_BACK_LEFT_OUTLINE : MaterialDesignGlyphs(0xF182C)

    /**
     * 󱠭 (0xF182D)
     */
    public data object HAND_BACK_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF182D)

    /**
     * 󱠮 (0xF182E)
     */
    public data object HAND_FRONT_LEFT_OUTLINE : MaterialDesignGlyphs(0xF182E)

    /**
     * 󱠯 (0xF182F)
     */
    public data object HAND_FRONT_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF182F)

    /**
     * 󱠰 (0xF1830)
     */
    public data object HAND_BACK_LEFT_OFF : MaterialDesignGlyphs(0xF1830)

    /**
     * 󱠱 (0xF1831)
     */
    public data object HAND_BACK_RIGHT_OFF : MaterialDesignGlyphs(0xF1831)

    /**
     * 󱠲 (0xF1832)
     */
    public data object HAND_BACK_LEFT_OFF_OUTLINE : MaterialDesignGlyphs(0xF1832)

    /**
     * 󱠳 (0xF1833)
     */
    public data object HAND_BACK_RIGHT_OFF_OUTLINE : MaterialDesignGlyphs(0xF1833)

    /**
     * 󱠴 (0xF1834)
     */
    public data object BATTERY_SYNC : MaterialDesignGlyphs(0xF1834)

    /**
     * 󱠵 (0xF1835)
     */
    public data object BATTERY_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1835)

    /**
     * 󱠶 (0xF1836)
     */
    public data object FOOD_TAKEOUT_BOX : MaterialDesignGlyphs(0xF1836)

    /**
     * 󱠷 (0xF1837)
     */
    public data object FOOD_TAKEOUT_BOX_OUTLINE : MaterialDesignGlyphs(0xF1837)

    /**
     * 󱠸 (0xF1838)
     */
    public data object IRON_BOARD : MaterialDesignGlyphs(0xF1838)

    /**
     * 󱠹 (0xF1839)
     */
    public data object POLICE_STATION : MaterialDesignGlyphs(0xF1839)

    /**
     * 󱠺 (0xF183A)
     */
    public data object CELLPHONE_MARKER : MaterialDesignGlyphs(0xF183A)

    /**
     * 󱠻 (0xF183B)
     */
    public data object TOOLTIP_CELLPHONE : MaterialDesignGlyphs(0xF183B)

    /**
     * 󱠼 (0xF183C)
     */
    public data object TABLE_PIVOT : MaterialDesignGlyphs(0xF183C)

    /**
     * 󱠽 (0xF183D)
     */
    public data object TUNNEL : MaterialDesignGlyphs(0xF183D)

    /**
     * 󱠾 (0xF183E)
     */
    public data object TUNNEL_OUTLINE : MaterialDesignGlyphs(0xF183E)

    /**
     * 󱠿 (0xF183F)
     */
    public data object ARROW_PROJECTILE_MULTIPLE : MaterialDesignGlyphs(0xF183F)

    /**
     * 󱡀 (0xF1840)
     */
    public data object ARROW_PROJECTILE : MaterialDesignGlyphs(0xF1840)

    /**
     * 󱡁 (0xF1841)
     */
    public data object BOW_ARROW : MaterialDesignGlyphs(0xF1841)

    /**
     * 󱡂 (0xF1842)
     */
    public data object AXE_BATTLE : MaterialDesignGlyphs(0xF1842)

    /**
     * 󱡃 (0xF1843)
     */
    public data object MACE : MaterialDesignGlyphs(0xF1843)

    /**
     * 󱡄 (0xF1844)
     */
    public data object MAGIC_STAFF : MaterialDesignGlyphs(0xF1844)

    /**
     * 󱡅 (0xF1845)
     */
    public data object SPEAR : MaterialDesignGlyphs(0xF1845)

    /**
     * 󱡆 (0xF1846)
     */
    public data object CURTAINS : MaterialDesignGlyphs(0xF1846)

    /**
     * 󱡇 (0xF1847)
     */
    public data object CURTAINS_CLOSED : MaterialDesignGlyphs(0xF1847)

    /**
     * 󱡈 (0xF1848)
     */
    public data object HUMAN_NON_BINARY : MaterialDesignGlyphs(0xF1848)

    /**
     * 󱡉 (0xF1849)
     */
    public data object WATERFALL : MaterialDesignGlyphs(0xF1849)

    /**
     * 󱡊 (0xF184A)
     */
    public data object EGG_FRIED : MaterialDesignGlyphs(0xF184A)

    /**
     * 󱡋 (0xF184B)
     */
    public data object FOOD_HOT_DOG : MaterialDesignGlyphs(0xF184B)

    /**
     * 󱡌 (0xF184C)
     */
    public data object INDUCTION : MaterialDesignGlyphs(0xF184C)

    /**
     * 󱡍 (0xF184D)
     */
    public data object PIPE_VALVE : MaterialDesignGlyphs(0xF184D)

    /**
     * 󱡎 (0xF184E)
     */
    public data object SHIPPING_PALLET : MaterialDesignGlyphs(0xF184E)

    /**
     * 󱡏 (0xF184F)
     */
    public data object EARBUDS : MaterialDesignGlyphs(0xF184F)

    /**
     * 󱡐 (0xF1850)
     */
    public data object EARBUDS_OFF : MaterialDesignGlyphs(0xF1850)

    /**
     * 󱡑 (0xF1851)
     */
    public data object EARBUDS_OFF_OUTLINE : MaterialDesignGlyphs(0xF1851)

    /**
     * 󱡒 (0xF1852)
     */
    public data object EARBUDS_OUTLINE : MaterialDesignGlyphs(0xF1852)

    /**
     * 󱡓 (0xF1853)
     */
    public data object CIRCLE_OPACITY : MaterialDesignGlyphs(0xF1853)

    /**
     * 󱡔 (0xF1854)
     */
    public data object SQUARE_OPACITY : MaterialDesignGlyphs(0xF1854)

    /**
     * 󱡕 (0xF1855)
     */
    public data object WATER_OPACITY : MaterialDesignGlyphs(0xF1855)

    /**
     * 󱡖 (0xF1856)
     */
    public data object VECTOR_POLYGON_VARIANT : MaterialDesignGlyphs(0xF1856)

    /**
     * 󱡗 (0xF1857)
     */
    public data object VECTOR_SQUARE_CLOSE : MaterialDesignGlyphs(0xF1857)

    /**
     * 󱡘 (0xF1858)
     */
    public data object VECTOR_SQUARE_OPEN : MaterialDesignGlyphs(0xF1858)

    /**
     * 󱡙 (0xF1859)
     */
    public data object WAVES_ARROW_LEFT : MaterialDesignGlyphs(0xF1859)

    /**
     * 󱡚 (0xF185A)
     */
    public data object WAVES_ARROW_RIGHT : MaterialDesignGlyphs(0xF185A)

    /**
     * 󱡛 (0xF185B)
     */
    public data object WAVES_ARROW_UP : MaterialDesignGlyphs(0xF185B)

    /**
     * 󱡜 (0xF185C)
     */
    public data object CASH_FAST : MaterialDesignGlyphs(0xF185C)

    /**
     * 󱡝 (0xF185D)
     */
    public data object RADIOACTIVE_CIRCLE : MaterialDesignGlyphs(0xF185D)

    /**
     * 󱡞 (0xF185E)
     */
    public data object RADIOACTIVE_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF185E)

    /**
     * 󱡟 (0xF185F)
     */
    public data object CCTV_OFF : MaterialDesignGlyphs(0xF185F)

    /**
     * 󱡠 (0xF1860)
     */
    public data object FORMAT_LIST_GROUP : MaterialDesignGlyphs(0xF1860)

    /**
     * 󱡡 (0xF1861)
     */
    public data object CLOCK_PLUS : MaterialDesignGlyphs(0xF1861)

    /**
     * 󱡢 (0xF1862)
     */
    public data object CLOCK_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1862)

    /**
     * 󱡣 (0xF1863)
     */
    public data object CLOCK_MINUS : MaterialDesignGlyphs(0xF1863)

    /**
     * 󱡤 (0xF1864)
     */
    public data object CLOCK_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1864)

    /**
     * 󱡥 (0xF1865)
     */
    public data object CLOCK_REMOVE : MaterialDesignGlyphs(0xF1865)

    /**
     * 󱡦 (0xF1866)
     */
    public data object CLOCK_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1866)

    /**
     * 󱡧 (0xF1867)
     */
    public data object ACCOUNT_ARROW_UP : MaterialDesignGlyphs(0xF1867)

    /**
     * 󱡨 (0xF1868)
     */
    public data object ACCOUNT_ARROW_DOWN : MaterialDesignGlyphs(0xF1868)

    /**
     * 󱡩 (0xF1869)
     */
    public data object ACCOUNT_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF1869)

    /**
     * 󱡪 (0xF186A)
     */
    public data object ACCOUNT_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF186A)

    /**
     * 󱡫 (0xF186B)
     */
    public data object AUDIO_INPUT_RCA : MaterialDesignGlyphs(0xF186B)

    /**
     * 󱡬 (0xF186C)
     */
    public data object AUDIO_INPUT_STEREO_MINIJACK : MaterialDesignGlyphs(0xF186C)

    /**
     * 󱡭 (0xF186D)
     */
    public data object AUDIO_INPUT_XLR : MaterialDesignGlyphs(0xF186D)

    /**
     * 󱡮 (0xF186E)
     */
    public data object HORSE_VARIANT_FAST : MaterialDesignGlyphs(0xF186E)

    /**
     * 󱡯 (0xF186F)
     */
    public data object EMAIL_FAST : MaterialDesignGlyphs(0xF186F)

    /**
     * 󱡰 (0xF1870)
     */
    public data object EMAIL_FAST_OUTLINE : MaterialDesignGlyphs(0xF1870)

    /**
     * 󱡱 (0xF1871)
     */
    public data object CAMERA_DOCUMENT : MaterialDesignGlyphs(0xF1871)

    /**
     * 󱡲 (0xF1872)
     */
    public data object CAMERA_DOCUMENT_OFF : MaterialDesignGlyphs(0xF1872)

    /**
     * 󱡳 (0xF1873)
     */
    public data object GLASS_FRAGILE : MaterialDesignGlyphs(0xF1873)

    /**
     * 󱡴 (0xF1874)
     */
    public data object MAGNIFY_EXPAND : MaterialDesignGlyphs(0xF1874)

    /**
     * 󱡵 (0xF1875)
     */
    public data object TOWN_HALL : MaterialDesignGlyphs(0xF1875)

    /**
     * 󱡶 (0xF1876)
     */
    public data object MONITOR_SMALL : MaterialDesignGlyphs(0xF1876)

    /**
     * 󱡷 (0xF1877)
     */
    public data object DIVERSIFY : MaterialDesignGlyphs(0xF1877)

    /**
     * 󱡸 (0xF1878)
     */
    public data object CAR_WIRELESS : MaterialDesignGlyphs(0xF1878)

    /**
     * 󱡹 (0xF1879)
     */
    public data object CAR_SELECT : MaterialDesignGlyphs(0xF1879)

    /**
     * 󱡺 (0xF187A)
     */
    public data object AIRPLANE_ALERT : MaterialDesignGlyphs(0xF187A)

    /**
     * 󱡻 (0xF187B)
     */
    public data object AIRPLANE_CHECK : MaterialDesignGlyphs(0xF187B)

    /**
     * 󱡼 (0xF187C)
     */
    public data object AIRPLANE_CLOCK : MaterialDesignGlyphs(0xF187C)

    /**
     * 󱡽 (0xF187D)
     */
    public data object AIRPLANE_COG : MaterialDesignGlyphs(0xF187D)

    /**
     * 󱡾 (0xF187E)
     */
    public data object AIRPLANE_EDIT : MaterialDesignGlyphs(0xF187E)

    /**
     * 󱡿 (0xF187F)
     */
    public data object AIRPLANE_MARKER : MaterialDesignGlyphs(0xF187F)

    /**
     * 󱢀 (0xF1880)
     */
    public data object AIRPLANE_MINUS : MaterialDesignGlyphs(0xF1880)

    /**
     * 󱢁 (0xF1881)
     */
    public data object AIRPLANE_PLUS : MaterialDesignGlyphs(0xF1881)

    /**
     * 󱢂 (0xF1882)
     */
    public data object AIRPLANE_REMOVE : MaterialDesignGlyphs(0xF1882)

    /**
     * 󱢃 (0xF1883)
     */
    public data object AIRPLANE_SEARCH : MaterialDesignGlyphs(0xF1883)

    /**
     * 󱢄 (0xF1884)
     */
    public data object AIRPLANE_SETTINGS : MaterialDesignGlyphs(0xF1884)

    /**
     * 󱢅 (0xF1885)
     */
    public data object FLOWER_POLLEN : MaterialDesignGlyphs(0xF1885)

    /**
     * 󱢆 (0xF1886)
     */
    public data object FLOWER_POLLEN_OUTLINE : MaterialDesignGlyphs(0xF1886)

    /**
     * 󱢇 (0xF1887)
     */
    public data object HAMMER_SICKLE : MaterialDesignGlyphs(0xF1887)

    /**
     * 󱢈 (0xF1888)
     */
    public data object VIEW_GALLERY : MaterialDesignGlyphs(0xF1888)

    /**
     * 󱢉 (0xF1889)
     */
    public data object VIEW_GALLERY_OUTLINE : MaterialDesignGlyphs(0xF1889)

    /**
     * 󱢊 (0xF188A)
     */
    public data object UMBRELLA_BEACH : MaterialDesignGlyphs(0xF188A)

    /**
     * 󱢋 (0xF188B)
     */
    public data object UMBRELLA_BEACH_OUTLINE : MaterialDesignGlyphs(0xF188B)

    /**
     * 󱢌 (0xF188C)
     */
    public data object CABIN_A_FRAME : MaterialDesignGlyphs(0xF188C)

    /**
     * 󱢍 (0xF188D)
     */
    public data object ALL_INCLUSIVE_BOX : MaterialDesignGlyphs(0xF188D)

    /**
     * 󱢎 (0xF188E)
     */
    public data object ALL_INCLUSIVE_BOX_OUTLINE : MaterialDesignGlyphs(0xF188E)

    /**
     * 󱢏 (0xF188F)
     */
    public data object HAND_COIN : MaterialDesignGlyphs(0xF188F)

    /**
     * 󱢐 (0xF1890)
     */
    public data object HAND_COIN_OUTLINE : MaterialDesignGlyphs(0xF1890)

    /**
     * 󱢑 (0xF1891)
     */
    public data object TRUCK_FLATBED : MaterialDesignGlyphs(0xF1891)

    /**
     * 󱢒 (0xF1892)
     */
    public data object LAYERS_EDIT : MaterialDesignGlyphs(0xF1892)

    /**
     * 󱢓 (0xF1893)
     */
    public data object MULTICAST : MaterialDesignGlyphs(0xF1893)

    /**
     * 󱢔 (0xF1894)
     */
    public data object HYDROGEN_STATION : MaterialDesignGlyphs(0xF1894)

    /**
     * 󱢕 (0xF1895)
     */
    public data object THERMOMETER_BLUETOOTH : MaterialDesignGlyphs(0xF1895)

    /**
     * 󱢖 (0xF1896)
     */
    public data object TIRE : MaterialDesignGlyphs(0xF1896)

    /**
     * 󱢗 (0xF1897)
     */
    public data object FOREST : MaterialDesignGlyphs(0xF1897)

    /**
     * 󱢘 (0xF1898)
     */
    public data object ACCOUNT_TIE_HAT : MaterialDesignGlyphs(0xF1898)

    /**
     * 󱢙 (0xF1899)
     */
    public data object ACCOUNT_TIE_HAT_OUTLINE : MaterialDesignGlyphs(0xF1899)

    /**
     * 󱢚 (0xF189A)
     */
    public data object ACCOUNT_WRENCH : MaterialDesignGlyphs(0xF189A)

    /**
     * 󱢛 (0xF189B)
     */
    public data object ACCOUNT_WRENCH_OUTLINE : MaterialDesignGlyphs(0xF189B)

    /**
     * 󱢜 (0xF189C)
     */
    public data object BICYCLE_CARGO : MaterialDesignGlyphs(0xF189C)

    /**
     * 󱢝 (0xF189D)
     */
    public data object CALENDAR_COLLAPSE_HORIZONTAL : MaterialDesignGlyphs(0xF189D)

    /**
     * 󱢞 (0xF189E)
     */
    public data object CALENDAR_EXPAND_HORIZONTAL : MaterialDesignGlyphs(0xF189E)

    /**
     * 󱢟 (0xF189F)
     */
    public data object CARDS_CLUB_OUTLINE : MaterialDesignGlyphs(0xF189F)

    /**
     * 󱢡 (0xF18A1)
     */
    public data object CARDS_PLAYING : MaterialDesignGlyphs(0xF18A1)

    /**
     * 󱢢 (0xF18A2)
     */
    public data object CARDS_PLAYING_CLUB : MaterialDesignGlyphs(0xF18A2)

    /**
     * 󱢣 (0xF18A3)
     */
    public data object CARDS_PLAYING_CLUB_MULTIPLE : MaterialDesignGlyphs(0xF18A3)

    /**
     * 󱢤 (0xF18A4)
     */
    public data object CARDS_PLAYING_CLUB_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF18A4)

    /**
     * 󱢥 (0xF18A5)
     */
    public data object CARDS_PLAYING_CLUB_OUTLINE : MaterialDesignGlyphs(0xF18A5)

    /**
     * 󱢦 (0xF18A6)
     */
    public data object CARDS_PLAYING_DIAMOND : MaterialDesignGlyphs(0xF18A6)

    /**
     * 󱢧 (0xF18A7)
     */
    public data object CARDS_PLAYING_DIAMOND_MULTIPLE : MaterialDesignGlyphs(0xF18A7)

    /**
     * 󱢨 (0xF18A8)
     */
    public data object CARDS_PLAYING_DIAMOND_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF18A8)

    /**
     * 󱢩 (0xF18A9)
     */
    public data object CARDS_PLAYING_DIAMOND_OUTLINE : MaterialDesignGlyphs(0xF18A9)

    /**
     * 󱢪 (0xF18AA)
     */
    public data object CARDS_PLAYING_HEART : MaterialDesignGlyphs(0xF18AA)

    /**
     * 󱢫 (0xF18AB)
     */
    public data object CARDS_PLAYING_HEART_MULTIPLE : MaterialDesignGlyphs(0xF18AB)

    /**
     * 󱢬 (0xF18AC)
     */
    public data object CARDS_PLAYING_HEART_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF18AC)

    /**
     * 󱢭 (0xF18AD)
     */
    public data object CARDS_PLAYING_HEART_OUTLINE : MaterialDesignGlyphs(0xF18AD)

    /**
     * 󱢮 (0xF18AE)
     */
    public data object CARDS_PLAYING_SPADE : MaterialDesignGlyphs(0xF18AE)

    /**
     * 󱢯 (0xF18AF)
     */
    public data object CARDS_PLAYING_SPADE_MULTIPLE : MaterialDesignGlyphs(0xF18AF)

    /**
     * 󱢰 (0xF18B0)
     */
    public data object CARDS_PLAYING_SPADE_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF18B0)

    /**
     * 󱢱 (0xF18B1)
     */
    public data object CARDS_PLAYING_SPADE_OUTLINE : MaterialDesignGlyphs(0xF18B1)

    /**
     * 󱢲 (0xF18B2)
     */
    public data object CARDS_SPADE_OUTLINE : MaterialDesignGlyphs(0xF18B2)

    /**
     * 󱢳 (0xF18B3)
     */
    public data object COMPARE_REMOVE : MaterialDesignGlyphs(0xF18B3)

    /**
     * 󱢴 (0xF18B4)
     */
    public data object DOLPHIN : MaterialDesignGlyphs(0xF18B4)

    /**
     * 󱢵 (0xF18B5)
     */
    public data object FUEL_CELL : MaterialDesignGlyphs(0xF18B5)

    /**
     * 󱢶 (0xF18B6)
     */
    public data object HAND_EXTENDED : MaterialDesignGlyphs(0xF18B6)

    /**
     * 󱢷 (0xF18B7)
     */
    public data object HAND_EXTENDED_OUTLINE : MaterialDesignGlyphs(0xF18B7)

    /**
     * 󱢸 (0xF18B8)
     */
    public data object PRINTER_3D_NOZZLE_HEAT : MaterialDesignGlyphs(0xF18B8)

    /**
     * 󱢹 (0xF18B9)
     */
    public data object PRINTER_3D_NOZZLE_HEAT_OUTLINE : MaterialDesignGlyphs(0xF18B9)

    /**
     * 󱢺 (0xF18BA)
     */
    public data object SHARK : MaterialDesignGlyphs(0xF18BA)

    /**
     * 󱢻 (0xF18BB)
     */
    public data object SHARK_OFF : MaterialDesignGlyphs(0xF18BB)

    /**
     * 󱢼 (0xF18BC)
     */
    public data object SHIELD_CROWN : MaterialDesignGlyphs(0xF18BC)

    /**
     * 󱢽 (0xF18BD)
     */
    public data object SHIELD_CROWN_OUTLINE : MaterialDesignGlyphs(0xF18BD)

    /**
     * 󱢾 (0xF18BE)
     */
    public data object SHIELD_SWORD : MaterialDesignGlyphs(0xF18BE)

    /**
     * 󱢿 (0xF18BF)
     */
    public data object SHIELD_SWORD_OUTLINE : MaterialDesignGlyphs(0xF18BF)

    /**
     * 󱣀 (0xF18C0)
     */
    public data object SICKLE : MaterialDesignGlyphs(0xF18C0)

    /**
     * 󱣁 (0xF18C1)
     */
    public data object STORE_ALERT : MaterialDesignGlyphs(0xF18C1)

    /**
     * 󱣂 (0xF18C2)
     */
    public data object STORE_ALERT_OUTLINE : MaterialDesignGlyphs(0xF18C2)

    /**
     * 󱣃 (0xF18C3)
     */
    public data object STORE_CHECK : MaterialDesignGlyphs(0xF18C3)

    /**
     * 󱣄 (0xF18C4)
     */
    public data object STORE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF18C4)

    /**
     * 󱣅 (0xF18C5)
     */
    public data object STORE_CLOCK : MaterialDesignGlyphs(0xF18C5)

    /**
     * 󱣆 (0xF18C6)
     */
    public data object STORE_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF18C6)

    /**
     * 󱣇 (0xF18C7)
     */
    public data object STORE_COG : MaterialDesignGlyphs(0xF18C7)

    /**
     * 󱣈 (0xF18C8)
     */
    public data object STORE_COG_OUTLINE : MaterialDesignGlyphs(0xF18C8)

    /**
     * 󱣉 (0xF18C9)
     */
    public data object STORE_EDIT : MaterialDesignGlyphs(0xF18C9)

    /**
     * 󱣊 (0xF18CA)
     */
    public data object STORE_EDIT_OUTLINE : MaterialDesignGlyphs(0xF18CA)

    /**
     * 󱣋 (0xF18CB)
     */
    public data object STORE_MARKER : MaterialDesignGlyphs(0xF18CB)

    /**
     * 󱣌 (0xF18CC)
     */
    public data object STORE_MARKER_OUTLINE : MaterialDesignGlyphs(0xF18CC)

    /**
     * 󱣍 (0xF18CD)
     */
    public data object STORE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF18CD)

    /**
     * 󱣎 (0xF18CE)
     */
    public data object STORE_OFF : MaterialDesignGlyphs(0xF18CE)

    /**
     * 󱣏 (0xF18CF)
     */
    public data object STORE_OFF_OUTLINE : MaterialDesignGlyphs(0xF18CF)

    /**
     * 󱣐 (0xF18D0)
     */
    public data object STORE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF18D0)

    /**
     * 󱣑 (0xF18D1)
     */
    public data object STORE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF18D1)

    /**
     * 󱣒 (0xF18D2)
     */
    public data object STORE_SEARCH : MaterialDesignGlyphs(0xF18D2)

    /**
     * 󱣓 (0xF18D3)
     */
    public data object STORE_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF18D3)

    /**
     * 󱣔 (0xF18D4)
     */
    public data object STORE_SETTINGS : MaterialDesignGlyphs(0xF18D4)

    /**
     * 󱣕 (0xF18D5)
     */
    public data object STORE_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF18D5)

    /**
     * 󱣖 (0xF18D6)
     */
    public data object SUN_THERMOMETER : MaterialDesignGlyphs(0xF18D6)

    /**
     * 󱣗 (0xF18D7)
     */
    public data object SUN_THERMOMETER_OUTLINE : MaterialDesignGlyphs(0xF18D7)

    /**
     * 󱣘 (0xF18D8)
     */
    public data object TRUCK_CARGO_CONTAINER : MaterialDesignGlyphs(0xF18D8)

    /**
     * 󱣙 (0xF18D9)
     */
    public data object VECTOR_SQUARE_EDIT : MaterialDesignGlyphs(0xF18D9)

    /**
     * 󱣚 (0xF18DA)
     */
    public data object VECTOR_SQUARE_MINUS : MaterialDesignGlyphs(0xF18DA)

    /**
     * 󱣛 (0xF18DB)
     */
    public data object VECTOR_SQUARE_PLUS : MaterialDesignGlyphs(0xF18DB)

    /**
     * 󱣜 (0xF18DC)
     */
    public data object VECTOR_SQUARE_REMOVE : MaterialDesignGlyphs(0xF18DC)

    /**
     * 󱣝 (0xF18DD)
     */
    public data object CEILING_LIGHT_MULTIPLE : MaterialDesignGlyphs(0xF18DD)

    /**
     * 󱣞 (0xF18DE)
     */
    public data object CEILING_LIGHT_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF18DE)

    /**
     * 󱣟 (0xF18DF)
     */
    public data object WIPER_WASH_ALERT : MaterialDesignGlyphs(0xF18DF)

    /**
     * 󱣠 (0xF18E0)
     */
    public data object CART_HEART : MaterialDesignGlyphs(0xF18E0)

    /**
     * 󱣡 (0xF18E1)
     */
    public data object VIRUS_OFF : MaterialDesignGlyphs(0xF18E1)

    /**
     * 󱣢 (0xF18E2)
     */
    public data object VIRUS_OFF_OUTLINE : MaterialDesignGlyphs(0xF18E2)

    /**
     * 󱣣 (0xF18E3)
     */
    public data object MAP_MARKER_ACCOUNT : MaterialDesignGlyphs(0xF18E3)

    /**
     * 󱣤 (0xF18E4)
     */
    public data object MAP_MARKER_ACCOUNT_OUTLINE : MaterialDesignGlyphs(0xF18E4)

    /**
     * 󱣥 (0xF18E5)
     */
    public data object BASKET_CHECK : MaterialDesignGlyphs(0xF18E5)

    /**
     * 󱣦 (0xF18E6)
     */
    public data object BASKET_CHECK_OUTLINE : MaterialDesignGlyphs(0xF18E6)

    /**
     * 󱣧 (0xF18E7)
     */
    public data object CREDIT_CARD_LOCK : MaterialDesignGlyphs(0xF18E7)

    /**
     * 󱣨 (0xF18E8)
     */
    public data object CREDIT_CARD_LOCK_OUTLINE : MaterialDesignGlyphs(0xF18E8)

    /**
     * 󱣩 (0xF18E9)
     */
    public data object FORMAT_UNDERLINE_WAVY : MaterialDesignGlyphs(0xF18E9)

    /**
     * 󱣪 (0xF18EA)
     */
    public data object CONTENT_SAVE_CHECK : MaterialDesignGlyphs(0xF18EA)

    /**
     * 󱣫 (0xF18EB)
     */
    public data object CONTENT_SAVE_CHECK_OUTLINE : MaterialDesignGlyphs(0xF18EB)

    /**
     * 󱣬 (0xF18EC)
     */
    public data object FILTER_CHECK : MaterialDesignGlyphs(0xF18EC)

    /**
     * 󱣭 (0xF18ED)
     */
    public data object FILTER_CHECK_OUTLINE : MaterialDesignGlyphs(0xF18ED)

    /**
     * 󱣮 (0xF18EE)
     */
    public data object FLAG_OFF : MaterialDesignGlyphs(0xF18EE)

    /**
     * 󱣯 (0xF18EF)
     */
    public data object FLAG_OFF_OUTLINE : MaterialDesignGlyphs(0xF18EF)

    /**
     * 󱣱 (0xF18F1)
     */
    public data object NAVIGATION_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF18F1)

    /**
     * 󱣲 (0xF18F2)
     */
    public data object REFRESH_AUTO : MaterialDesignGlyphs(0xF18F2)

    /**
     * 󱣳 (0xF18F3)
     */
    public data object TILDE_OFF : MaterialDesignGlyphs(0xF18F3)

    /**
     * 󱣴 (0xF18F4)
     */
    public data object FIT_TO_SCREEN : MaterialDesignGlyphs(0xF18F4)

    /**
     * 󱣵 (0xF18F5)
     */
    public data object FIT_TO_SCREEN_OUTLINE : MaterialDesignGlyphs(0xF18F5)

    /**
     * 󱣶 (0xF18F6)
     */
    public data object WEATHER_CLOUDY_CLOCK : MaterialDesignGlyphs(0xF18F6)

    /**
     * 󱣷 (0xF18F7)
     */
    public data object SMART_CARD_OFF : MaterialDesignGlyphs(0xF18F7)

    /**
     * 󱣸 (0xF18F8)
     */
    public data object SMART_CARD_OFF_OUTLINE : MaterialDesignGlyphs(0xF18F8)

    /**
     * 󱣹 (0xF18F9)
     */
    public data object CLIPBOARD_TEXT_CLOCK : MaterialDesignGlyphs(0xF18F9)

    /**
     * 󱣺 (0xF18FA)
     */
    public data object CLIPBOARD_TEXT_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF18FA)

    /**
     * 󱣻 (0xF18FB)
     */
    public data object TEDDY_BEAR : MaterialDesignGlyphs(0xF18FB)

    /**
     * 󱣼 (0xF18FC)
     */
    public data object COW_OFF : MaterialDesignGlyphs(0xF18FC)

    /**
     * 󱣽 (0xF18FD)
     */
    public data object EYE_ARROW_LEFT : MaterialDesignGlyphs(0xF18FD)

    /**
     * 󱣾 (0xF18FE)
     */
    public data object EYE_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF18FE)

    /**
     * 󱣿 (0xF18FF)
     */
    public data object EYE_ARROW_RIGHT : MaterialDesignGlyphs(0xF18FF)

    /**
     * 󱤀 (0xF1900)
     */
    public data object EYE_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF1900)

    /**
     * 󱤁 (0xF1901)
     */
    public data object HOME_BATTERY : MaterialDesignGlyphs(0xF1901)

    /**
     * 󱤂 (0xF1902)
     */
    public data object HOME_BATTERY_OUTLINE : MaterialDesignGlyphs(0xF1902)

    /**
     * 󱤃 (0xF1903)
     */
    public data object HOME_LIGHTNING_BOLT : MaterialDesignGlyphs(0xF1903)

    /**
     * 󱤄 (0xF1904)
     */
    public data object HOME_LIGHTNING_BOLT_OUTLINE : MaterialDesignGlyphs(0xF1904)

    /**
     * 󱤅 (0xF1905)
     */
    public data object LEAF_CIRCLE : MaterialDesignGlyphs(0xF1905)

    /**
     * 󱤆 (0xF1906)
     */
    public data object LEAF_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1906)

    /**
     * 󱤇 (0xF1907)
     */
    public data object TAG_SEARCH : MaterialDesignGlyphs(0xF1907)

    /**
     * 󱤈 (0xF1908)
     */
    public data object TAG_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1908)

    /**
     * 󱤉 (0xF1909)
     */
    public data object CAR_BRAKE_FLUID_LEVEL : MaterialDesignGlyphs(0xF1909)

    /**
     * 󱤊 (0xF190A)
     */
    public data object CAR_BRAKE_LOW_PRESSURE : MaterialDesignGlyphs(0xF190A)

    /**
     * 󱤋 (0xF190B)
     */
    public data object CAR_BRAKE_TEMPERATURE : MaterialDesignGlyphs(0xF190B)

    /**
     * 󱤌 (0xF190C)
     */
    public data object CAR_BRAKE_WORN_LININGS : MaterialDesignGlyphs(0xF190C)

    /**
     * 󱤍 (0xF190D)
     */
    public data object CAR_LIGHT_ALERT : MaterialDesignGlyphs(0xF190D)

    /**
     * 󱤎 (0xF190E)
     */
    public data object CAR_SPEED_LIMITER : MaterialDesignGlyphs(0xF190E)

    /**
     * 󱤏 (0xF190F)
     */
    public data object CREDIT_CARD_CHIP : MaterialDesignGlyphs(0xF190F)

    /**
     * 󱤐 (0xF1910)
     */
    public data object CREDIT_CARD_CHIP_OUTLINE : MaterialDesignGlyphs(0xF1910)

    /**
     * 󱤑 (0xF1911)
     */
    public data object CREDIT_CARD_FAST : MaterialDesignGlyphs(0xF1911)

    /**
     * 󱤒 (0xF1912)
     */
    public data object CREDIT_CARD_FAST_OUTLINE : MaterialDesignGlyphs(0xF1912)

    /**
     * 󱤓 (0xF1913)
     */
    public data object INTEGRATED_CIRCUIT_CHIP : MaterialDesignGlyphs(0xF1913)

    /**
     * 󱤔 (0xF1914)
     */
    public data object THUMBS_UP_DOWN_OUTLINE : MaterialDesignGlyphs(0xF1914)

    /**
     * 󱤕 (0xF1915)
     */
    public data object FOOD_OFF_OUTLINE : MaterialDesignGlyphs(0xF1915)

    /**
     * 󱤖 (0xF1916)
     */
    public data object FOOD_OUTLINE : MaterialDesignGlyphs(0xF1916)

    /**
     * 󱤗 (0xF1917)
     */
    public data object FORMAT_PAGE_SPLIT : MaterialDesignGlyphs(0xF1917)

    /**
     * 󱤘 (0xF1918)
     */
    public data object CHART_WATERFALL : MaterialDesignGlyphs(0xF1918)

    /**
     * 󱤙 (0xF1919)
     */
    public data object GAMEPAD_OUTLINE : MaterialDesignGlyphs(0xF1919)

    /**
     * 󱤚 (0xF191A)
     */
    public data object NETWORK_STRENGTH_4_COG : MaterialDesignGlyphs(0xF191A)

    /**
     * 󱤛 (0xF191B)
     */
    public data object ACCOUNT_SYNC : MaterialDesignGlyphs(0xF191B)

    /**
     * 󱤜 (0xF191C)
     */
    public data object ACCOUNT_SYNC_OUTLINE : MaterialDesignGlyphs(0xF191C)

    /**
     * 󱤝 (0xF191D)
     */
    public data object BUS_ELECTRIC : MaterialDesignGlyphs(0xF191D)

    /**
     * 󱤞 (0xF191E)
     */
    public data object LIQUOR : MaterialDesignGlyphs(0xF191E)

    /**
     * 󱤟 (0xF191F)
     */
    public data object DATABASE_EYE : MaterialDesignGlyphs(0xF191F)

    /**
     * 󱤠 (0xF1920)
     */
    public data object DATABASE_EYE_OFF : MaterialDesignGlyphs(0xF1920)

    /**
     * 󱤡 (0xF1921)
     */
    public data object DATABASE_EYE_OFF_OUTLINE : MaterialDesignGlyphs(0xF1921)

    /**
     * 󱤢 (0xF1922)
     */
    public data object DATABASE_EYE_OUTLINE : MaterialDesignGlyphs(0xF1922)

    /**
     * 󱤣 (0xF1923)
     */
    public data object TIMER_SETTINGS : MaterialDesignGlyphs(0xF1923)

    /**
     * 󱤤 (0xF1924)
     */
    public data object TIMER_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF1924)

    /**
     * 󱤥 (0xF1925)
     */
    public data object TIMER_COG : MaterialDesignGlyphs(0xF1925)

    /**
     * 󱤦 (0xF1926)
     */
    public data object TIMER_COG_OUTLINE : MaterialDesignGlyphs(0xF1926)

    /**
     * 󱤧 (0xF1927)
     */
    public data object CHECKBOX_MARKED_CIRCLE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1927)

    /**
     * 󱤨 (0xF1928)
     */
    public data object PANORAMA_HORIZONTAL : MaterialDesignGlyphs(0xF1928)

    /**
     * 󱤩 (0xF1929)
     */
    public data object PANORAMA_VERTICAL : MaterialDesignGlyphs(0xF1929)

    /**
     * 󱤪 (0xF192A)
     */
    public data object ADVERTISEMENTS : MaterialDesignGlyphs(0xF192A)

    /**
     * 󱤫 (0xF192B)
     */
    public data object ADVERTISEMENTS_OFF : MaterialDesignGlyphs(0xF192B)

    /**
     * 󱤬 (0xF192C)
     */
    public data object TRANSMISSION_TOWER_EXPORT : MaterialDesignGlyphs(0xF192C)

    /**
     * 󱤭 (0xF192D)
     */
    public data object TRANSMISSION_TOWER_IMPORT : MaterialDesignGlyphs(0xF192D)

    /**
     * 󱤮 (0xF192E)
     */
    public data object SMOKE_DETECTOR_ALERT : MaterialDesignGlyphs(0xF192E)

    /**
     * 󱤯 (0xF192F)
     */
    public data object SMOKE_DETECTOR_ALERT_OUTLINE : MaterialDesignGlyphs(0xF192F)

    /**
     * 󱤰 (0xF1930)
     */
    public data object SMOKE_DETECTOR_VARIANT_ALERT : MaterialDesignGlyphs(0xF1930)

    /**
     * 󱤱 (0xF1931)
     */
    public data object COFFEE_MAKER_CHECK : MaterialDesignGlyphs(0xF1931)

    /**
     * 󱤲 (0xF1932)
     */
    public data object COFFEE_MAKER_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1932)

    /**
     * 󱤳 (0xF1933)
     */
    public data object COG_PAUSE : MaterialDesignGlyphs(0xF1933)

    /**
     * 󱤴 (0xF1934)
     */
    public data object COG_PAUSE_OUTLINE : MaterialDesignGlyphs(0xF1934)

    /**
     * 󱤵 (0xF1935)
     */
    public data object COG_PLAY : MaterialDesignGlyphs(0xF1935)

    /**
     * 󱤶 (0xF1936)
     */
    public data object COG_PLAY_OUTLINE : MaterialDesignGlyphs(0xF1936)

    /**
     * 󱤷 (0xF1937)
     */
    public data object COG_STOP : MaterialDesignGlyphs(0xF1937)

    /**
     * 󱤸 (0xF1938)
     */
    public data object COG_STOP_OUTLINE : MaterialDesignGlyphs(0xF1938)

    /**
     * 󱤹 (0xF1939)
     */
    public data object COPYLEFT : MaterialDesignGlyphs(0xF1939)

    /**
     * 󱤺 (0xF193A)
     */
    public data object FAST_FORWARD_15 : MaterialDesignGlyphs(0xF193A)

    /**
     * 󱤻 (0xF193B)
     */
    public data object FILE_IMAGE_MINUS : MaterialDesignGlyphs(0xF193B)

    /**
     * 󱤼 (0xF193C)
     */
    public data object FILE_IMAGE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF193C)

    /**
     * 󱤽 (0xF193D)
     */
    public data object FILE_IMAGE_PLUS : MaterialDesignGlyphs(0xF193D)

    /**
     * 󱤾 (0xF193E)
     */
    public data object FILE_IMAGE_PLUS_OUTLINE : MaterialDesignGlyphs(0xF193E)

    /**
     * 󱤿 (0xF193F)
     */
    public data object FILE_IMAGE_REMOVE : MaterialDesignGlyphs(0xF193F)

    /**
     * 󱥀 (0xF1940)
     */
    public data object FILE_IMAGE_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1940)

    /**
     * 󱥁 (0xF1941)
     */
    public data object MESSAGE_BADGE : MaterialDesignGlyphs(0xF1941)

    /**
     * 󱥂 (0xF1942)
     */
    public data object MESSAGE_BADGE_OUTLINE : MaterialDesignGlyphs(0xF1942)

    /**
     * 󱥃 (0xF1943)
     */
    public data object NEWSPAPER_CHECK : MaterialDesignGlyphs(0xF1943)

    /**
     * 󱥄 (0xF1944)
     */
    public data object NEWSPAPER_REMOVE : MaterialDesignGlyphs(0xF1944)

    /**
     * 󱥅 (0xF1945)
     */
    public data object PUBLISH_OFF : MaterialDesignGlyphs(0xF1945)

    /**
     * 󱥆 (0xF1946)
     */
    public data object REWIND_15 : MaterialDesignGlyphs(0xF1946)

    /**
     * 󱥇 (0xF1947)
     */
    public data object VIEW_DASHBOARD_EDIT : MaterialDesignGlyphs(0xF1947)

    /**
     * 󱥈 (0xF1948)
     */
    public data object VIEW_DASHBOARD_EDIT_OUTLINE : MaterialDesignGlyphs(0xF1948)

    /**
     * 󱥉 (0xF1949)
     */
    public data object OFFICE_BUILDING_COG : MaterialDesignGlyphs(0xF1949)

    /**
     * 󱥊 (0xF194A)
     */
    public data object OFFICE_BUILDING_COG_OUTLINE : MaterialDesignGlyphs(0xF194A)

    /**
     * 󱥋 (0xF194B)
     */
    public data object HAND_CLAP : MaterialDesignGlyphs(0xF194B)

    /**
     * 󱥌 (0xF194C)
     */
    public data object CONE : MaterialDesignGlyphs(0xF194C)

    /**
     * 󱥍 (0xF194D)
     */
    public data object CONE_OFF : MaterialDesignGlyphs(0xF194D)

    /**
     * 󱥎 (0xF194E)
     */
    public data object CYLINDER : MaterialDesignGlyphs(0xF194E)

    /**
     * 󱥏 (0xF194F)
     */
    public data object CYLINDER_OFF : MaterialDesignGlyphs(0xF194F)

    /**
     * 󱥐 (0xF1950)
     */
    public data object OCTAHEDRON : MaterialDesignGlyphs(0xF1950)

    /**
     * 󱥑 (0xF1951)
     */
    public data object OCTAHEDRON_OFF : MaterialDesignGlyphs(0xF1951)

    /**
     * 󱥒 (0xF1952)
     */
    public data object PYRAMID : MaterialDesignGlyphs(0xF1952)

    /**
     * 󱥓 (0xF1953)
     */
    public data object PYRAMID_OFF : MaterialDesignGlyphs(0xF1953)

    /**
     * 󱥔 (0xF1954)
     */
    public data object SPHERE : MaterialDesignGlyphs(0xF1954)

    /**
     * 󱥕 (0xF1955)
     */
    public data object SPHERE_OFF : MaterialDesignGlyphs(0xF1955)

    /**
     * 󱥖 (0xF1956)
     */
    public data object FORMAT_LETTER_SPACING : MaterialDesignGlyphs(0xF1956)

    /**
     * 󱥗 (0xF1957)
     */
    public data object FRENCH_FRIES : MaterialDesignGlyphs(0xF1957)

    /**
     * 󱥘 (0xF1958)
     */
    public data object SCENT : MaterialDesignGlyphs(0xF1958)

    /**
     * 󱥙 (0xF1959)
     */
    public data object SCENT_OFF : MaterialDesignGlyphs(0xF1959)

    /**
     * 󱥚 (0xF195A)
     */
    public data object PALETTE_SWATCH_VARIANT : MaterialDesignGlyphs(0xF195A)

    /**
     * 󱥛 (0xF195B)
     */
    public data object EMAIL_SEAL : MaterialDesignGlyphs(0xF195B)

    /**
     * 󱥜 (0xF195C)
     */
    public data object EMAIL_SEAL_OUTLINE : MaterialDesignGlyphs(0xF195C)

    /**
     * 󱥝 (0xF195D)
     */
    public data object STOOL : MaterialDesignGlyphs(0xF195D)

    /**
     * 󱥞 (0xF195E)
     */
    public data object STOOL_OUTLINE : MaterialDesignGlyphs(0xF195E)

    /**
     * 󱥟 (0xF195F)
     */
    public data object PANORAMA_WIDE_ANGLE : MaterialDesignGlyphs(0xF195F)

    /**
     * 󱥠 (0xF1960)
     */
    public data object ACCOUNT_LOCK_OPEN : MaterialDesignGlyphs(0xF1960)

    /**
     * 󱥡 (0xF1961)
     */
    public data object ACCOUNT_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF1961)

    /**
     * 󱥢 (0xF1962)
     */
    public data object ALIGN_HORIZONTAL_DISTRIBUTE : MaterialDesignGlyphs(0xF1962)

    /**
     * 󱥣 (0xF1963)
     */
    public data object ALIGN_VERTICAL_DISTRIBUTE : MaterialDesignGlyphs(0xF1963)

    /**
     * 󱥤 (0xF1964)
     */
    public data object ARROW_BOTTOM_LEFT_BOLD_BOX : MaterialDesignGlyphs(0xF1964)

    /**
     * 󱥥 (0xF1965)
     */
    public data object ARROW_BOTTOM_LEFT_BOLD_BOX_OUTLINE : MaterialDesignGlyphs(0xF1965)

    /**
     * 󱥦 (0xF1966)
     */
    public data object ARROW_BOTTOM_RIGHT_BOLD_BOX : MaterialDesignGlyphs(0xF1966)

    /**
     * 󱥧 (0xF1967)
     */
    public data object ARROW_BOTTOM_RIGHT_BOLD_BOX_OUTLINE : MaterialDesignGlyphs(0xF1967)

    /**
     * 󱥨 (0xF1968)
     */
    public data object ARROW_TOP_LEFT_BOLD_BOX : MaterialDesignGlyphs(0xF1968)

    /**
     * 󱥩 (0xF1969)
     */
    public data object ARROW_TOP_LEFT_BOLD_BOX_OUTLINE : MaterialDesignGlyphs(0xF1969)

    /**
     * 󱥪 (0xF196A)
     */
    public data object ARROW_TOP_RIGHT_BOLD_BOX : MaterialDesignGlyphs(0xF196A)

    /**
     * 󱥫 (0xF196B)
     */
    public data object ARROW_TOP_RIGHT_BOLD_BOX_OUTLINE : MaterialDesignGlyphs(0xF196B)

    /**
     * 󱥬 (0xF196C)
     */
    public data object BOOKMARK_BOX_MULTIPLE : MaterialDesignGlyphs(0xF196C)

    /**
     * 󱥭 (0xF196D)
     */
    public data object BOOKMARK_BOX_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF196D)

    /**
     * 󱥮 (0xF196E)
     */
    public data object BULLHORN_VARIANT : MaterialDesignGlyphs(0xF196E)

    /**
     * 󱥯 (0xF196F)
     */
    public data object BULLHORN_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF196F)

    /**
     * 󱥰 (0xF1970)
     */
    public data object CANDY : MaterialDesignGlyphs(0xF1970)

    /**
     * 󱥱 (0xF1971)
     */
    public data object CANDY_OFF : MaterialDesignGlyphs(0xF1971)

    /**
     * 󱥲 (0xF1972)
     */
    public data object CANDY_OFF_OUTLINE : MaterialDesignGlyphs(0xF1972)

    /**
     * 󱥳 (0xF1973)
     */
    public data object CANDY_OUTLINE : MaterialDesignGlyphs(0xF1973)

    /**
     * 󱥴 (0xF1974)
     */
    public data object CAR_CLOCK : MaterialDesignGlyphs(0xF1974)

    /**
     * 󱥵 (0xF1975)
     */
    public data object CROWD : MaterialDesignGlyphs(0xF1975)

    /**
     * 󱥶 (0xF1976)
     */
    public data object CURRENCY_RUPEE : MaterialDesignGlyphs(0xF1976)

    /**
     * 󱥷 (0xF1977)
     */
    public data object DIVING : MaterialDesignGlyphs(0xF1977)

    /**
     * 󱥸 (0xF1978)
     */
    public data object DOTS_CIRCLE : MaterialDesignGlyphs(0xF1978)

    /**
     * 󱥹 (0xF1979)
     */
    public data object ELEVATOR_PASSENGER_OFF : MaterialDesignGlyphs(0xF1979)

    /**
     * 󱥺 (0xF197A)
     */
    public data object ELEVATOR_PASSENGER_OFF_OUTLINE : MaterialDesignGlyphs(0xF197A)

    /**
     * 󱥻 (0xF197B)
     */
    public data object ELEVATOR_PASSENGER_OUTLINE : MaterialDesignGlyphs(0xF197B)

    /**
     * 󱥼 (0xF197C)
     */
    public data object EYE_REFRESH : MaterialDesignGlyphs(0xF197C)

    /**
     * 󱥽 (0xF197D)
     */
    public data object EYE_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF197D)

    /**
     * 󱥾 (0xF197E)
     */
    public data object FOLDER_CHECK : MaterialDesignGlyphs(0xF197E)

    /**
     * 󱥿 (0xF197F)
     */
    public data object FOLDER_CHECK_OUTLINE : MaterialDesignGlyphs(0xF197F)

    /**
     * 󱦀 (0xF1980)
     */
    public data object HUMAN_DOLLY : MaterialDesignGlyphs(0xF1980)

    /**
     * 󱦁 (0xF1981)
     */
    public data object HUMAN_WHITE_CANE : MaterialDesignGlyphs(0xF1981)

    /**
     * 󱦂 (0xF1982)
     */
    public data object IP_OUTLINE : MaterialDesignGlyphs(0xF1982)

    /**
     * 󱦃 (0xF1983)
     */
    public data object KEY_ALERT : MaterialDesignGlyphs(0xF1983)

    /**
     * 󱦄 (0xF1984)
     */
    public data object KEY_ALERT_OUTLINE : MaterialDesignGlyphs(0xF1984)

    /**
     * 󱦅 (0xF1985)
     */
    public data object KITE : MaterialDesignGlyphs(0xF1985)

    /**
     * 󱦆 (0xF1986)
     */
    public data object KITE_OUTLINE : MaterialDesignGlyphs(0xF1986)

    /**
     * 󱦇 (0xF1987)
     */
    public data object LIGHT_FLOOD_DOWN : MaterialDesignGlyphs(0xF1987)

    /**
     * 󱦈 (0xF1988)
     */
    public data object LIGHT_FLOOD_UP : MaterialDesignGlyphs(0xF1988)

    /**
     * 󱦉 (0xF1989)
     */
    public data object MICROPHONE_QUESTION : MaterialDesignGlyphs(0xF1989)

    /**
     * 󱦊 (0xF198A)
     */
    public data object MICROPHONE_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF198A)

    /**
     * 󱦋 (0xF198B)
     */
    public data object CRADLE : MaterialDesignGlyphs(0xF198B)

    /**
     * 󱦌 (0xF198C)
     */
    public data object PANORAMA_OUTLINE : MaterialDesignGlyphs(0xF198C)

    /**
     * 󱦍 (0xF198D)
     */
    public data object PANORAMA_SPHERE : MaterialDesignGlyphs(0xF198D)

    /**
     * 󱦎 (0xF198E)
     */
    public data object PANORAMA_SPHERE_OUTLINE : MaterialDesignGlyphs(0xF198E)

    /**
     * 󱦏 (0xF198F)
     */
    public data object PANORAMA_VARIANT : MaterialDesignGlyphs(0xF198F)

    /**
     * 󱦐 (0xF1990)
     */
    public data object PANORAMA_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1990)

    /**
     * 󱦑 (0xF1991)
     */
    public data object CRADLE_OUTLINE : MaterialDesignGlyphs(0xF1991)

    /**
     * 󱦒 (0xF1992)
     */
    public data object FRACTION_ONE_HALF : MaterialDesignGlyphs(0xF1992)

    /**
     * 󱦓 (0xF1993)
     */
    public data object PHONE_REFRESH : MaterialDesignGlyphs(0xF1993)

    /**
     * 󱦔 (0xF1994)
     */
    public data object PHONE_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF1994)

    /**
     * 󱦕 (0xF1995)
     */
    public data object PHONE_SYNC : MaterialDesignGlyphs(0xF1995)

    /**
     * 󱦖 (0xF1996)
     */
    public data object PHONE_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1996)

    /**
     * 󱦗 (0xF1997)
     */
    public data object RAZOR_DOUBLE_EDGE : MaterialDesignGlyphs(0xF1997)

    /**
     * 󱦘 (0xF1998)
     */
    public data object RAZOR_SINGLE_EDGE : MaterialDesignGlyphs(0xF1998)

    /**
     * 󱦙 (0xF1999)
     */
    public data object ROTATE_360 : MaterialDesignGlyphs(0xF1999)

    /**
     * 󱦚 (0xF199A)
     */
    public data object SHIELD_LOCK_OPEN : MaterialDesignGlyphs(0xF199A)

    /**
     * 󱦛 (0xF199B)
     */
    public data object SHIELD_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF199B)

    /**
     * 󱦜 (0xF199C)
     */
    public data object SITEMAP_OUTLINE : MaterialDesignGlyphs(0xF199C)

    /**
     * 󱦝 (0xF199D)
     */
    public data object SPRINKLER_FIRE : MaterialDesignGlyphs(0xF199D)

    /**
     * 󱦞 (0xF199E)
     */
    public data object TAB_SEARCH : MaterialDesignGlyphs(0xF199E)

    /**
     * 󱦟 (0xF199F)
     */
    public data object TIMER_SAND_COMPLETE : MaterialDesignGlyphs(0xF199F)

    /**
     * 󱦠 (0xF19A0)
     */
    public data object TIMER_SAND_PAUSED : MaterialDesignGlyphs(0xF19A0)

    /**
     * 󱦡 (0xF19A1)
     */
    public data object VACUUM : MaterialDesignGlyphs(0xF19A1)

    /**
     * 󱦢 (0xF19A2)
     */
    public data object VACUUM_OUTLINE : MaterialDesignGlyphs(0xF19A2)

    /**
     * 󱦣 (0xF19A3)
     */
    public data object WRENCH_CLOCK : MaterialDesignGlyphs(0xF19A3)

    /**
     * 󱦤 (0xF19A4)
     */
    public data object PLIERS : MaterialDesignGlyphs(0xF19A4)

    /**
     * 󱦥 (0xF19A5)
     */
    public data object SUN_COMPASS : MaterialDesignGlyphs(0xF19A5)

    /**
     * 󱦦 (0xF19A6)
     */
    public data object TRUCK_SNOWFLAKE : MaterialDesignGlyphs(0xF19A6)

    /**
     * 󱦧 (0xF19A7)
     */
    public data object CAMERA_MARKER : MaterialDesignGlyphs(0xF19A7)

    /**
     * 󱦨 (0xF19A8)
     */
    public data object CAMERA_MARKER_OUTLINE : MaterialDesignGlyphs(0xF19A8)

    /**
     * 󱦩 (0xF19A9)
     */
    public data object VIDEO_MARKER : MaterialDesignGlyphs(0xF19A9)

    /**
     * 󱦪 (0xF19AA)
     */
    public data object VIDEO_MARKER_OUTLINE : MaterialDesignGlyphs(0xF19AA)

    /**
     * 󱦫 (0xF19AB)
     */
    public data object WIND_TURBINE_ALERT : MaterialDesignGlyphs(0xF19AB)

    /**
     * 󱦬 (0xF19AC)
     */
    public data object WIND_TURBINE_CHECK : MaterialDesignGlyphs(0xF19AC)

    /**
     * 󱦭 (0xF19AD)
     */
    public data object TRUCK_PLUS : MaterialDesignGlyphs(0xF19AD)

    /**
     * 󱦮 (0xF19AE)
     */
    public data object TRUCK_MINUS : MaterialDesignGlyphs(0xF19AE)

    /**
     * 󱦯 (0xF19AF)
     */
    public data object TRUCK_REMOVE : MaterialDesignGlyphs(0xF19AF)

    /**
     * 󱦰 (0xF19B0)
     */
    public data object ARROW_RIGHT_THIN : MaterialDesignGlyphs(0xF19B0)

    /**
     * 󱦱 (0xF19B1)
     */
    public data object ARROW_LEFT_THIN : MaterialDesignGlyphs(0xF19B1)

    /**
     * 󱦲 (0xF19B2)
     */
    public data object ARROW_UP_THIN : MaterialDesignGlyphs(0xF19B2)

    /**
     * 󱦳 (0xF19B3)
     */
    public data object ARROW_DOWN_THIN : MaterialDesignGlyphs(0xF19B3)

    /**
     * 󱦴 (0xF19B4)
     */
    public data object ARROW_TOP_RIGHT_THIN : MaterialDesignGlyphs(0xF19B4)

    /**
     * 󱦵 (0xF19B5)
     */
    public data object ARROW_TOP_LEFT_THIN : MaterialDesignGlyphs(0xF19B5)

    /**
     * 󱦶 (0xF19B6)
     */
    public data object ARROW_BOTTOM_LEFT_THIN : MaterialDesignGlyphs(0xF19B6)

    /**
     * 󱦷 (0xF19B7)
     */
    public data object ARROW_BOTTOM_RIGHT_THIN : MaterialDesignGlyphs(0xF19B7)

    /**
     * 󱦸 (0xF19B8)
     */
    public data object SCALE_UNBALANCED : MaterialDesignGlyphs(0xF19B8)

    /**
     * 󱦹 (0xF19B9)
     */
    public data object DRAW_PEN : MaterialDesignGlyphs(0xF19B9)

    /**
     * 󱦺 (0xF19BA)
     */
    public data object CLOCK_EDIT : MaterialDesignGlyphs(0xF19BA)

    /**
     * 󱦻 (0xF19BB)
     */
    public data object CLOCK_EDIT_OUTLINE : MaterialDesignGlyphs(0xF19BB)

    /**
     * 󱦼 (0xF19BC)
     */
    public data object TRUCK_PLUS_OUTLINE : MaterialDesignGlyphs(0xF19BC)

    /**
     * 󱦽 (0xF19BD)
     */
    public data object TRUCK_MINUS_OUTLINE : MaterialDesignGlyphs(0xF19BD)

    /**
     * 󱦾 (0xF19BE)
     */
    public data object TRUCK_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF19BE)

    /**
     * 󱦿 (0xF19BF)
     */
    public data object CAMERA_OFF_OUTLINE : MaterialDesignGlyphs(0xF19BF)

    /**
     * 󱧀 (0xF19C0)
     */
    public data object HOME_GROUP_PLUS : MaterialDesignGlyphs(0xF19C0)

    /**
     * 󱧁 (0xF19C1)
     */
    public data object HOME_GROUP_MINUS : MaterialDesignGlyphs(0xF19C1)

    /**
     * 󱧂 (0xF19C2)
     */
    public data object HOME_GROUP_REMOVE : MaterialDesignGlyphs(0xF19C2)

    /**
     * 󱧃 (0xF19C3)
     */
    public data object FILE_SIGN : MaterialDesignGlyphs(0xF19C3)

    /**
     * 󱧄 (0xF19C4)
     */
    public data object ATTACHMENT_LOCK : MaterialDesignGlyphs(0xF19C4)

    /**
     * 󱧅 (0xF19C5)
     */
    public data object CELLPHONE_ARROW_DOWN_VARIANT : MaterialDesignGlyphs(0xF19C5)

    /**
     * 󱧆 (0xF19C6)
     */
    public data object FILE_CHART_CHECK : MaterialDesignGlyphs(0xF19C6)

    /**
     * 󱧇 (0xF19C7)
     */
    public data object FILE_CHART_CHECK_OUTLINE : MaterialDesignGlyphs(0xF19C7)

    /**
     * 󱧈 (0xF19C8)
     */
    public data object FILE_LOCK_OPEN : MaterialDesignGlyphs(0xF19C8)

    /**
     * 󱧉 (0xF19C9)
     */
    public data object FILE_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF19C9)

    /**
     * 󱧊 (0xF19CA)
     */
    public data object FOLDER_QUESTION : MaterialDesignGlyphs(0xF19CA)

    /**
     * 󱧋 (0xF19CB)
     */
    public data object FOLDER_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF19CB)

    /**
     * 󱧌 (0xF19CC)
     */
    public data object MESSAGE_FAST : MaterialDesignGlyphs(0xF19CC)

    /**
     * 󱧍 (0xF19CD)
     */
    public data object MESSAGE_FAST_OUTLINE : MaterialDesignGlyphs(0xF19CD)

    /**
     * 󱧎 (0xF19CE)
     */
    public data object MESSAGE_TEXT_FAST : MaterialDesignGlyphs(0xF19CE)

    /**
     * 󱧏 (0xF19CF)
     */
    public data object MESSAGE_TEXT_FAST_OUTLINE : MaterialDesignGlyphs(0xF19CF)

    /**
     * 󱧐 (0xF19D0)
     */
    public data object MONITOR_ARROW_DOWN : MaterialDesignGlyphs(0xF19D0)

    /**
     * 󱧑 (0xF19D1)
     */
    public data object MONITOR_ARROW_DOWN_VARIANT : MaterialDesignGlyphs(0xF19D1)

    /**
     * 󱧒 (0xF19D2)
     */
    public data object NEEDLE_OFF : MaterialDesignGlyphs(0xF19D2)

    /**
     * 󱧓 (0xF19D3)
     */
    public data object NUMERIC_OFF : MaterialDesignGlyphs(0xF19D3)

    /**
     * 󱧔 (0xF19D4)
     */
    public data object PACKAGE_VARIANT_CLOSED_MINUS : MaterialDesignGlyphs(0xF19D4)

    /**
     * 󱧕 (0xF19D5)
     */
    public data object PACKAGE_VARIANT_CLOSED_PLUS : MaterialDesignGlyphs(0xF19D5)

    /**
     * 󱧖 (0xF19D6)
     */
    public data object PACKAGE_VARIANT_CLOSED_REMOVE : MaterialDesignGlyphs(0xF19D6)

    /**
     * 󱧗 (0xF19D7)
     */
    public data object PACKAGE_VARIANT_MINUS : MaterialDesignGlyphs(0xF19D7)

    /**
     * 󱧘 (0xF19D8)
     */
    public data object PACKAGE_VARIANT_PLUS : MaterialDesignGlyphs(0xF19D8)

    /**
     * 󱧙 (0xF19D9)
     */
    public data object PACKAGE_VARIANT_REMOVE : MaterialDesignGlyphs(0xF19D9)

    /**
     * 󱧚 (0xF19DA)
     */
    public data object PAPERCLIP_LOCK : MaterialDesignGlyphs(0xF19DA)

    /**
     * 󱧛 (0xF19DB)
     */
    public data object PHONE_CLOCK : MaterialDesignGlyphs(0xF19DB)

    /**
     * 󱧜 (0xF19DC)
     */
    public data object RECEIPT_OUTLINE : MaterialDesignGlyphs(0xF19DC)

    /**
     * 󱧝 (0xF19DD)
     */
    public data object TRANSMISSION_TOWER_OFF : MaterialDesignGlyphs(0xF19DD)

    /**
     * 󱧞 (0xF19DE)
     */
    public data object TRUCK_ALERT : MaterialDesignGlyphs(0xF19DE)

    /**
     * 󱧟 (0xF19DF)
     */
    public data object TRUCK_ALERT_OUTLINE : MaterialDesignGlyphs(0xF19DF)

    /**
     * 󱧠 (0xF19E0)
     */
    public data object BONE_OFF : MaterialDesignGlyphs(0xF19E0)

    /**
     * 󱧡 (0xF19E1)
     */
    public data object LIGHTBULB_ALERT : MaterialDesignGlyphs(0xF19E1)

    /**
     * 󱧢 (0xF19E2)
     */
    public data object LIGHTBULB_ALERT_OUTLINE : MaterialDesignGlyphs(0xF19E2)

    /**
     * 󱧣 (0xF19E3)
     */
    public data object LIGHTBULB_QUESTION : MaterialDesignGlyphs(0xF19E3)

    /**
     * 󱧤 (0xF19E4)
     */
    public data object LIGHTBULB_QUESTION_OUTLINE : MaterialDesignGlyphs(0xF19E4)

    /**
     * 󱧥 (0xF19E5)
     */
    public data object BATTERY_CLOCK : MaterialDesignGlyphs(0xF19E5)

    /**
     * 󱧦 (0xF19E6)
     */
    public data object BATTERY_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF19E6)

    /**
     * 󱧧 (0xF19E7)
     */
    public data object AUTORENEW_OFF : MaterialDesignGlyphs(0xF19E7)

    /**
     * 󱧨 (0xF19E8)
     */
    public data object FOLDER_ARROW_DOWN : MaterialDesignGlyphs(0xF19E8)

    /**
     * 󱧩 (0xF19E9)
     */
    public data object FOLDER_ARROW_DOWN_OUTLINE : MaterialDesignGlyphs(0xF19E9)

    /**
     * 󱧪 (0xF19EA)
     */
    public data object FOLDER_ARROW_LEFT : MaterialDesignGlyphs(0xF19EA)

    /**
     * 󱧫 (0xF19EB)
     */
    public data object FOLDER_ARROW_LEFT_OUTLINE : MaterialDesignGlyphs(0xF19EB)

    /**
     * 󱧬 (0xF19EC)
     */
    public data object FOLDER_ARROW_LEFT_RIGHT : MaterialDesignGlyphs(0xF19EC)

    /**
     * 󱧭 (0xF19ED)
     */
    public data object FOLDER_ARROW_LEFT_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF19ED)

    /**
     * 󱧮 (0xF19EE)
     */
    public data object FOLDER_ARROW_RIGHT : MaterialDesignGlyphs(0xF19EE)

    /**
     * 󱧯 (0xF19EF)
     */
    public data object FOLDER_ARROW_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF19EF)

    /**
     * 󱧰 (0xF19F0)
     */
    public data object FOLDER_ARROW_UP : MaterialDesignGlyphs(0xF19F0)

    /**
     * 󱧱 (0xF19F1)
     */
    public data object FOLDER_ARROW_UP_DOWN : MaterialDesignGlyphs(0xF19F1)

    /**
     * 󱧲 (0xF19F2)
     */
    public data object FOLDER_ARROW_UP_DOWN_OUTLINE : MaterialDesignGlyphs(0xF19F2)

    /**
     * 󱧳 (0xF19F3)
     */
    public data object FOLDER_ARROW_UP_OUTLINE : MaterialDesignGlyphs(0xF19F3)

    /**
     * 󱧴 (0xF19F4)
     */
    public data object FOLDER_CANCEL : MaterialDesignGlyphs(0xF19F4)

    /**
     * 󱧵 (0xF19F5)
     */
    public data object FOLDER_CANCEL_OUTLINE : MaterialDesignGlyphs(0xF19F5)

    /**
     * 󱧶 (0xF19F6)
     */
    public data object FOLDER_FILE : MaterialDesignGlyphs(0xF19F6)

    /**
     * 󱧷 (0xF19F7)
     */
    public data object FOLDER_FILE_OUTLINE : MaterialDesignGlyphs(0xF19F7)

    /**
     * 󱧸 (0xF19F8)
     */
    public data object FOLDER_OFF : MaterialDesignGlyphs(0xF19F8)

    /**
     * 󱧹 (0xF19F9)
     */
    public data object FOLDER_OFF_OUTLINE : MaterialDesignGlyphs(0xF19F9)

    /**
     * 󱧺 (0xF19FA)
     */
    public data object FOLDER_PLAY : MaterialDesignGlyphs(0xF19FA)

    /**
     * 󱧻 (0xF19FB)
     */
    public data object FOLDER_PLAY_OUTLINE : MaterialDesignGlyphs(0xF19FB)

    /**
     * 󱧼 (0xF19FC)
     */
    public data object FOLDER_WRENCH : MaterialDesignGlyphs(0xF19FC)

    /**
     * 󱧽 (0xF19FD)
     */
    public data object FOLDER_WRENCH_OUTLINE : MaterialDesignGlyphs(0xF19FD)

    /**
     * 󱧾 (0xF19FE)
     */
    public data object IMAGE_REFRESH : MaterialDesignGlyphs(0xF19FE)

    /**
     * 󱧿 (0xF19FF)
     */
    public data object IMAGE_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF19FF)

    /**
     * 󱨀 (0xF1A00)
     */
    public data object IMAGE_SYNC : MaterialDesignGlyphs(0xF1A00)

    /**
     * 󱨁 (0xF1A01)
     */
    public data object IMAGE_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1A01)

    /**
     * 󱨂 (0xF1A02)
     */
    public data object PERCENT_BOX : MaterialDesignGlyphs(0xF1A02)

    /**
     * 󱨃 (0xF1A03)
     */
    public data object PERCENT_BOX_OUTLINE : MaterialDesignGlyphs(0xF1A03)

    /**
     * 󱨄 (0xF1A04)
     */
    public data object PERCENT_CIRCLE : MaterialDesignGlyphs(0xF1A04)

    /**
     * 󱨅 (0xF1A05)
     */
    public data object PERCENT_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1A05)

    /**
     * 󱨆 (0xF1A06)
     */
    public data object SALE_OUTLINE : MaterialDesignGlyphs(0xF1A06)

    /**
     * 󱨇 (0xF1A07)
     */
    public data object SQUARE_ROUNDED_BADGE : MaterialDesignGlyphs(0xF1A07)

    /**
     * 󱨈 (0xF1A08)
     */
    public data object SQUARE_ROUNDED_BADGE_OUTLINE : MaterialDesignGlyphs(0xF1A08)

    /**
     * 󱨉 (0xF1A09)
     */
    public data object TRIANGLE_SMALL_DOWN : MaterialDesignGlyphs(0xF1A09)

    /**
     * 󱨊 (0xF1A0A)
     */
    public data object TRIANGLE_SMALL_UP : MaterialDesignGlyphs(0xF1A0A)

    /**
     * 󱨋 (0xF1A0B)
     */
    public data object NOTEBOOK_HEART : MaterialDesignGlyphs(0xF1A0B)

    /**
     * 󱨌 (0xF1A0C)
     */
    public data object NOTEBOOK_HEART_OUTLINE : MaterialDesignGlyphs(0xF1A0C)

    /**
     * 󱨍 (0xF1A0D)
     */
    public data object BRUSH_OUTLINE : MaterialDesignGlyphs(0xF1A0D)

    /**
     * 󱨎 (0xF1A0E)
     */
    public data object FRUIT_PEAR : MaterialDesignGlyphs(0xF1A0E)

    /**
     * 󱨏 (0xF1A0F)
     */
    public data object RAW : MaterialDesignGlyphs(0xF1A0F)

    /**
     * 󱨐 (0xF1A10)
     */
    public data object RAW_OFF : MaterialDesignGlyphs(0xF1A10)

    /**
     * 󱨑 (0xF1A11)
     */
    public data object WALL_FIRE : MaterialDesignGlyphs(0xF1A11)

    /**
     * 󱨒 (0xF1A12)
     */
    public data object HOME_CLOCK : MaterialDesignGlyphs(0xF1A12)

    /**
     * 󱨓 (0xF1A13)
     */
    public data object HOME_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF1A13)

    /**
     * 󱨔 (0xF1A14)
     */
    public data object CAMERA_LOCK : MaterialDesignGlyphs(0xF1A14)

    /**
     * 󱨕 (0xF1A15)
     */
    public data object CAMERA_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1A15)

    /**
     * 󱨖 (0xF1A16)
     */
    public data object PLAY_BOX_LOCK : MaterialDesignGlyphs(0xF1A16)

    /**
     * 󱨗 (0xF1A17)
     */
    public data object PLAY_BOX_LOCK_OPEN : MaterialDesignGlyphs(0xF1A17)

    /**
     * 󱨘 (0xF1A18)
     */
    public data object PLAY_BOX_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF1A18)

    /**
     * 󱨙 (0xF1A19)
     */
    public data object PLAY_BOX_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1A19)

    /**
     * 󱨚 (0xF1A1A)
     */
    public data object ROBOT_INDUSTRIAL_OUTLINE : MaterialDesignGlyphs(0xF1A1A)

    /**
     * 󱨛 (0xF1A1B)
     */
    public data object GAS_BURNER : MaterialDesignGlyphs(0xF1A1B)

    /**
     * 󱨜 (0xF1A1C)
     */
    public data object VIDEO_2D : MaterialDesignGlyphs(0xF1A1C)

    /**
     * 󱨝 (0xF1A1D)
     */
    public data object BOOK_HEART : MaterialDesignGlyphs(0xF1A1D)

    /**
     * 󱨞 (0xF1A1E)
     */
    public data object BOOK_HEART_OUTLINE : MaterialDesignGlyphs(0xF1A1E)

    /**
     * 󱨟 (0xF1A1F)
     */
    public data object ACCOUNT_HARD_HAT_OUTLINE : MaterialDesignGlyphs(0xF1A1F)

    /**
     * 󱨠 (0xF1A20)
     */
    public data object ACCOUNT_SCHOOL : MaterialDesignGlyphs(0xF1A20)

    /**
     * 󱨡 (0xF1A21)
     */
    public data object ACCOUNT_SCHOOL_OUTLINE : MaterialDesignGlyphs(0xF1A21)

    /**
     * 󱨢 (0xF1A22)
     */
    public data object LIBRARY_OUTLINE : MaterialDesignGlyphs(0xF1A22)

    /**
     * 󱨣 (0xF1A23)
     */
    public data object PROJECTOR_OFF : MaterialDesignGlyphs(0xF1A23)

    /**
     * 󱨤 (0xF1A24)
     */
    public data object LIGHT_SWITCH_OFF : MaterialDesignGlyphs(0xF1A24)

    /**
     * 󱨥 (0xF1A25)
     */
    public data object TOGGLE_SWITCH_VARIANT : MaterialDesignGlyphs(0xF1A25)

    /**
     * 󱨦 (0xF1A26)
     */
    public data object TOGGLE_SWITCH_VARIANT_OFF : MaterialDesignGlyphs(0xF1A26)

    /**
     * 󱨧 (0xF1A27)
     */
    public data object ASTERISK_CIRCLE_OUTLINE : MaterialDesignGlyphs(0xF1A27)

    /**
     * 󱨨 (0xF1A28)
     */
    public data object BARREL_OUTLINE : MaterialDesignGlyphs(0xF1A28)

    /**
     * 󱨩 (0xF1A29)
     */
    public data object BELL_COG : MaterialDesignGlyphs(0xF1A29)

    /**
     * 󱨪 (0xF1A2A)
     */
    public data object BELL_COG_OUTLINE : MaterialDesignGlyphs(0xF1A2A)

    /**
     * 󱨫 (0xF1A2B)
     */
    public data object BLINDS_HORIZONTAL : MaterialDesignGlyphs(0xF1A2B)

    /**
     * 󱨬 (0xF1A2C)
     */
    public data object BLINDS_HORIZONTAL_CLOSED : MaterialDesignGlyphs(0xF1A2C)

    /**
     * 󱨭 (0xF1A2D)
     */
    public data object BLINDS_VERTICAL : MaterialDesignGlyphs(0xF1A2D)

    /**
     * 󱨮 (0xF1A2E)
     */
    public data object BLINDS_VERTICAL_CLOSED : MaterialDesignGlyphs(0xF1A2E)

    /**
     * 󱨯 (0xF1A2F)
     */
    public data object BULKHEAD_LIGHT : MaterialDesignGlyphs(0xF1A2F)

    /**
     * 󱨰 (0xF1A30)
     */
    public data object CALENDAR_TODAY_OUTLINE : MaterialDesignGlyphs(0xF1A30)

    /**
     * 󱨱 (0xF1A31)
     */
    public data object CALENDAR_WEEK_BEGIN_OUTLINE : MaterialDesignGlyphs(0xF1A31)

    /**
     * 󱨲 (0xF1A32)
     */
    public data object CALENDAR_WEEK_END : MaterialDesignGlyphs(0xF1A32)

    /**
     * 󱨳 (0xF1A33)
     */
    public data object CALENDAR_WEEK_END_OUTLINE : MaterialDesignGlyphs(0xF1A33)

    /**
     * 󱨴 (0xF1A34)
     */
    public data object CALENDAR_WEEK_OUTLINE : MaterialDesignGlyphs(0xF1A34)

    /**
     * 󱨵 (0xF1A35)
     */
    public data object CLOUD_PERCENT : MaterialDesignGlyphs(0xF1A35)

    /**
     * 󱨶 (0xF1A36)
     */
    public data object CLOUD_PERCENT_OUTLINE : MaterialDesignGlyphs(0xF1A36)

    /**
     * 󱨷 (0xF1A37)
     */
    public data object COACH_LAMP_VARIANT : MaterialDesignGlyphs(0xF1A37)

    /**
     * 󱨸 (0xF1A38)
     */
    public data object COMPOST : MaterialDesignGlyphs(0xF1A38)

    /**
     * 󱨹 (0xF1A39)
     */
    public data object CURRENCY_FRA : MaterialDesignGlyphs(0xF1A39)

    /**
     * 󱨺 (0xF1A3A)
     */
    public data object FAN_CLOCK : MaterialDesignGlyphs(0xF1A3A)

    /**
     * 󱨻 (0xF1A3B)
     */
    public data object FILE_ROTATE_LEFT : MaterialDesignGlyphs(0xF1A3B)

    /**
     * 󱨼 (0xF1A3C)
     */
    public data object FILE_ROTATE_LEFT_OUTLINE : MaterialDesignGlyphs(0xF1A3C)

    /**
     * 󱨽 (0xF1A3D)
     */
    public data object FILE_ROTATE_RIGHT : MaterialDesignGlyphs(0xF1A3D)

    /**
     * 󱨾 (0xF1A3E)
     */
    public data object FILE_ROTATE_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF1A3E)

    /**
     * 󱨿 (0xF1A3F)
     */
    public data object FILTER_MULTIPLE : MaterialDesignGlyphs(0xF1A3F)

    /**
     * 󱩀 (0xF1A40)
     */
    public data object FILTER_MULTIPLE_OUTLINE : MaterialDesignGlyphs(0xF1A40)

    /**
     * 󱩁 (0xF1A41)
     */
    public data object GYMNASTICS : MaterialDesignGlyphs(0xF1A41)

    /**
     * 󱩂 (0xF1A42)
     */
    public data object HAND_CLAP_OFF : MaterialDesignGlyphs(0xF1A42)

    /**
     * 󱩃 (0xF1A43)
     */
    public data object HEAT_PUMP : MaterialDesignGlyphs(0xF1A43)

    /**
     * 󱩄 (0xF1A44)
     */
    public data object HEAT_PUMP_OUTLINE : MaterialDesignGlyphs(0xF1A44)

    /**
     * 󱩅 (0xF1A45)
     */
    public data object HEAT_WAVE : MaterialDesignGlyphs(0xF1A45)

    /**
     * 󱩆 (0xF1A46)
     */
    public data object HOME_OFF : MaterialDesignGlyphs(0xF1A46)

    /**
     * 󱩇 (0xF1A47)
     */
    public data object HOME_OFF_OUTLINE : MaterialDesignGlyphs(0xF1A47)

    /**
     * 󱩈 (0xF1A48)
     */
    public data object LANDSLIDE : MaterialDesignGlyphs(0xF1A48)

    /**
     * 󱩉 (0xF1A49)
     */
    public data object LANDSLIDE_OUTLINE : MaterialDesignGlyphs(0xF1A49)

    /**
     * 󱩊 (0xF1A4A)
     */
    public data object LAPTOP_ACCOUNT : MaterialDesignGlyphs(0xF1A4A)

    /**
     * 󱩋 (0xF1A4B)
     */
    public data object LED_STRIP_VARIANT_OFF : MaterialDesignGlyphs(0xF1A4B)

    /**
     * 󱩌 (0xF1A4C)
     */
    public data object LIGHTBULB_NIGHT : MaterialDesignGlyphs(0xF1A4C)

    /**
     * 󱩍 (0xF1A4D)
     */
    public data object LIGHTBULB_NIGHT_OUTLINE : MaterialDesignGlyphs(0xF1A4D)

    /**
     * 󱩎 (0xF1A4E)
     */
    public data object LIGHTBULB_ON_10 : MaterialDesignGlyphs(0xF1A4E)

    /**
     * 󱩏 (0xF1A4F)
     */
    public data object LIGHTBULB_ON_20 : MaterialDesignGlyphs(0xF1A4F)

    /**
     * 󱩐 (0xF1A50)
     */
    public data object LIGHTBULB_ON_30 : MaterialDesignGlyphs(0xF1A50)

    /**
     * 󱩑 (0xF1A51)
     */
    public data object LIGHTBULB_ON_40 : MaterialDesignGlyphs(0xF1A51)

    /**
     * 󱩒 (0xF1A52)
     */
    public data object LIGHTBULB_ON_50 : MaterialDesignGlyphs(0xF1A52)

    /**
     * 󱩓 (0xF1A53)
     */
    public data object LIGHTBULB_ON_60 : MaterialDesignGlyphs(0xF1A53)

    /**
     * 󱩔 (0xF1A54)
     */
    public data object LIGHTBULB_ON_70 : MaterialDesignGlyphs(0xF1A54)

    /**
     * 󱩕 (0xF1A55)
     */
    public data object LIGHTBULB_ON_80 : MaterialDesignGlyphs(0xF1A55)

    /**
     * 󱩖 (0xF1A56)
     */
    public data object LIGHTBULB_ON_90 : MaterialDesignGlyphs(0xF1A56)

    /**
     * 󱩗 (0xF1A57)
     */
    public data object METER_ELECTRIC : MaterialDesignGlyphs(0xF1A57)

    /**
     * 󱩘 (0xF1A58)
     */
    public data object METER_ELECTRIC_OUTLINE : MaterialDesignGlyphs(0xF1A58)

    /**
     * 󱩙 (0xF1A59)
     */
    public data object METER_GAS : MaterialDesignGlyphs(0xF1A59)

    /**
     * 󱩚 (0xF1A5A)
     */
    public data object METER_GAS_OUTLINE : MaterialDesignGlyphs(0xF1A5A)

    /**
     * 󱩛 (0xF1A5B)
     */
    public data object MONITOR_ACCOUNT : MaterialDesignGlyphs(0xF1A5B)

    /**
     * 󱩜 (0xF1A5C)
     */
    public data object PILL_OFF : MaterialDesignGlyphs(0xF1A5C)

    /**
     * 󱩝 (0xF1A5D)
     */
    public data object PLUS_LOCK : MaterialDesignGlyphs(0xF1A5D)

    /**
     * 󱩞 (0xF1A5E)
     */
    public data object PLUS_LOCK_OPEN : MaterialDesignGlyphs(0xF1A5E)

    /**
     * 󱩟 (0xF1A5F)
     */
    public data object POOL_THERMOMETER : MaterialDesignGlyphs(0xF1A5F)

    /**
     * 󱩠 (0xF1A60)
     */
    public data object POST_LAMP : MaterialDesignGlyphs(0xF1A60)

    /**
     * 󱩡 (0xF1A61)
     */
    public data object RABBIT_VARIANT : MaterialDesignGlyphs(0xF1A61)

    /**
     * 󱩢 (0xF1A62)
     */
    public data object RABBIT_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1A62)

    /**
     * 󱩣 (0xF1A63)
     */
    public data object RECEIPT_TEXT_CHECK : MaterialDesignGlyphs(0xF1A63)

    /**
     * 󱩤 (0xF1A64)
     */
    public data object RECEIPT_TEXT_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1A64)

    /**
     * 󱩥 (0xF1A65)
     */
    public data object RECEIPT_TEXT_MINUS : MaterialDesignGlyphs(0xF1A65)

    /**
     * 󱩦 (0xF1A66)
     */
    public data object RECEIPT_TEXT_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1A66)

    /**
     * 󱩧 (0xF1A67)
     */
    public data object RECEIPT_TEXT_PLUS : MaterialDesignGlyphs(0xF1A67)

    /**
     * 󱩨 (0xF1A68)
     */
    public data object RECEIPT_TEXT_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1A68)

    /**
     * 󱩩 (0xF1A69)
     */
    public data object RECEIPT_TEXT_REMOVE : MaterialDesignGlyphs(0xF1A69)

    /**
     * 󱩪 (0xF1A6A)
     */
    public data object RECEIPT_TEXT_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1A6A)

    /**
     * 󱩫 (0xF1A6B)
     */
    public data object ROLLER_SHADE : MaterialDesignGlyphs(0xF1A6B)

    /**
     * 󱩬 (0xF1A6C)
     */
    public data object ROLLER_SHADE_CLOSED : MaterialDesignGlyphs(0xF1A6C)

    /**
     * 󱩭 (0xF1A6D)
     */
    public data object SEED_PLUS : MaterialDesignGlyphs(0xF1A6D)

    /**
     * 󱩮 (0xF1A6E)
     */
    public data object SEED_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1A6E)

    /**
     * 󱩯 (0xF1A6F)
     */
    public data object SHOPPING_SEARCH_OUTLINE : MaterialDesignGlyphs(0xF1A6F)

    /**
     * 󱩰 (0xF1A70)
     */
    public data object SNOWFLAKE_CHECK : MaterialDesignGlyphs(0xF1A70)

    /**
     * 󱩱 (0xF1A71)
     */
    public data object SNOWFLAKE_THERMOMETER : MaterialDesignGlyphs(0xF1A71)

    /**
     * 󱩲 (0xF1A72)
     */
    public data object SNOWSHOEING : MaterialDesignGlyphs(0xF1A72)

    /**
     * 󱩳 (0xF1A73)
     */
    public data object SOLAR_POWER_VARIANT : MaterialDesignGlyphs(0xF1A73)

    /**
     * 󱩴 (0xF1A74)
     */
    public data object SOLAR_POWER_VARIANT_OUTLINE : MaterialDesignGlyphs(0xF1A74)

    /**
     * 󱩵 (0xF1A75)
     */
    public data object STORAGE_TANK : MaterialDesignGlyphs(0xF1A75)

    /**
     * 󱩶 (0xF1A76)
     */
    public data object STORAGE_TANK_OUTLINE : MaterialDesignGlyphs(0xF1A76)

    /**
     * 󱩷 (0xF1A77)
     */
    public data object SUN_CLOCK : MaterialDesignGlyphs(0xF1A77)

    /**
     * 󱩸 (0xF1A78)
     */
    public data object SUN_CLOCK_OUTLINE : MaterialDesignGlyphs(0xF1A78)

    /**
     * 󱩹 (0xF1A79)
     */
    public data object SUN_SNOWFLAKE_VARIANT : MaterialDesignGlyphs(0xF1A79)

    /**
     * 󱩺 (0xF1A7A)
     */
    public data object TAG_CHECK : MaterialDesignGlyphs(0xF1A7A)

    /**
     * 󱩻 (0xF1A7B)
     */
    public data object TAG_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1A7B)

    /**
     * 󱩼 (0xF1A7C)
     */
    public data object TEXT_BOX_EDIT : MaterialDesignGlyphs(0xF1A7C)

    /**
     * 󱩽 (0xF1A7D)
     */
    public data object TEXT_BOX_EDIT_OUTLINE : MaterialDesignGlyphs(0xF1A7D)

    /**
     * 󱩾 (0xF1A7E)
     */
    public data object TEXT_SEARCH_VARIANT : MaterialDesignGlyphs(0xF1A7E)

    /**
     * 󱩿 (0xF1A7F)
     */
    public data object THERMOMETER_CHECK : MaterialDesignGlyphs(0xF1A7F)

    /**
     * 󱪀 (0xF1A80)
     */
    public data object THERMOMETER_WATER : MaterialDesignGlyphs(0xF1A80)

    /**
     * 󱪁 (0xF1A81)
     */
    public data object TSUNAMI : MaterialDesignGlyphs(0xF1A81)

    /**
     * 󱪂 (0xF1A82)
     */
    public data object TURBINE : MaterialDesignGlyphs(0xF1A82)

    /**
     * 󱪃 (0xF1A83)
     */
    public data object VOLCANO : MaterialDesignGlyphs(0xF1A83)

    /**
     * 󱪄 (0xF1A84)
     */
    public data object VOLCANO_OUTLINE : MaterialDesignGlyphs(0xF1A84)

    /**
     * 󱪅 (0xF1A85)
     */
    public data object WATER_THERMOMETER : MaterialDesignGlyphs(0xF1A85)

    /**
     * 󱪆 (0xF1A86)
     */
    public data object WATER_THERMOMETER_OUTLINE : MaterialDesignGlyphs(0xF1A86)

    /**
     * 󱪇 (0xF1A87)
     */
    public data object WHEELCHAIR : MaterialDesignGlyphs(0xF1A87)

    /**
     * 󱪈 (0xF1A88)
     */
    public data object WIND_POWER : MaterialDesignGlyphs(0xF1A88)

    /**
     * 󱪉 (0xF1A89)
     */
    public data object WIND_POWER_OUTLINE : MaterialDesignGlyphs(0xF1A89)

    /**
     * 󱪊 (0xF1A8A)
     */
    public data object WINDOW_SHUTTER_COG : MaterialDesignGlyphs(0xF1A8A)

    /**
     * 󱪋 (0xF1A8B)
     */
    public data object WINDOW_SHUTTER_SETTINGS : MaterialDesignGlyphs(0xF1A8B)

    /**
     * 󱪌 (0xF1A8C)
     */
    public data object ACCOUNT_TIE_WOMAN : MaterialDesignGlyphs(0xF1A8C)

    /**
     * 󱪍 (0xF1A8D)
     */
    public data object BRIEFCASE_ARROW_LEFT_RIGHT : MaterialDesignGlyphs(0xF1A8D)

    /**
     * 󱪎 (0xF1A8E)
     */
    public data object BRIEFCASE_ARROW_LEFT_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF1A8E)

    /**
     * 󱪏 (0xF1A8F)
     */
    public data object BRIEFCASE_ARROW_UP_DOWN : MaterialDesignGlyphs(0xF1A8F)

    /**
     * 󱪐 (0xF1A90)
     */
    public data object BRIEFCASE_ARROW_UP_DOWN_OUTLINE : MaterialDesignGlyphs(0xF1A90)

    /**
     * 󱪑 (0xF1A91)
     */
    public data object CASH_CLOCK : MaterialDesignGlyphs(0xF1A91)

    /**
     * 󱪒 (0xF1A92)
     */
    public data object CASH_SYNC : MaterialDesignGlyphs(0xF1A92)

    /**
     * 󱪓 (0xF1A93)
     */
    public data object FILE_ARROW_LEFT_RIGHT : MaterialDesignGlyphs(0xF1A93)

    /**
     * 󱪔 (0xF1A94)
     */
    public data object FILE_ARROW_LEFT_RIGHT_OUTLINE : MaterialDesignGlyphs(0xF1A94)

    /**
     * 󱪕 (0xF1A95)
     */
    public data object FILE_ARROW_UP_DOWN : MaterialDesignGlyphs(0xF1A95)

    /**
     * 󱪖 (0xF1A96)
     */
    public data object FILE_ARROW_UP_DOWN_OUTLINE : MaterialDesignGlyphs(0xF1A96)

    /**
     * 󱪗 (0xF1A97)
     */
    public data object FILE_DOCUMENT_ALERT : MaterialDesignGlyphs(0xF1A97)

    /**
     * 󱪘 (0xF1A98)
     */
    public data object FILE_DOCUMENT_ALERT_OUTLINE : MaterialDesignGlyphs(0xF1A98)

    /**
     * 󱪙 (0xF1A99)
     */
    public data object FILE_DOCUMENT_CHECK : MaterialDesignGlyphs(0xF1A99)

    /**
     * 󱪚 (0xF1A9A)
     */
    public data object FILE_DOCUMENT_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1A9A)

    /**
     * 󱪛 (0xF1A9B)
     */
    public data object FILE_DOCUMENT_MINUS : MaterialDesignGlyphs(0xF1A9B)

    /**
     * 󱪜 (0xF1A9C)
     */
    public data object FILE_DOCUMENT_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1A9C)

    /**
     * 󱪝 (0xF1A9D)
     */
    public data object FILE_DOCUMENT_PLUS : MaterialDesignGlyphs(0xF1A9D)

    /**
     * 󱪞 (0xF1A9E)
     */
    public data object FILE_DOCUMENT_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1A9E)

    /**
     * 󱪟 (0xF1A9F)
     */
    public data object FILE_DOCUMENT_REMOVE : MaterialDesignGlyphs(0xF1A9F)

    /**
     * 󱪠 (0xF1AA0)
     */
    public data object FILE_DOCUMENT_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1AA0)

    /**
     * 󱪡 (0xF1AA1)
     */
    public data object FILE_MINUS : MaterialDesignGlyphs(0xF1AA1)

    /**
     * 󱪢 (0xF1AA2)
     */
    public data object FILE_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1AA2)

    /**
     * 󱪣 (0xF1AA3)
     */
    public data object FILTER_COG : MaterialDesignGlyphs(0xF1AA3)

    /**
     * 󱪤 (0xF1AA4)
     */
    public data object FILTER_COG_OUTLINE : MaterialDesignGlyphs(0xF1AA4)

    /**
     * 󱪥 (0xF1AA5)
     */
    public data object FILTER_SETTINGS : MaterialDesignGlyphs(0xF1AA5)

    /**
     * 󱪦 (0xF1AA6)
     */
    public data object FILTER_SETTINGS_OUTLINE : MaterialDesignGlyphs(0xF1AA6)

    /**
     * 󱪧 (0xF1AA7)
     */
    public data object FOLDER_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF1AA7)

    /**
     * 󱪨 (0xF1AA8)
     */
    public data object FOLDER_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1AA8)

    /**
     * 󱪩 (0xF1AA9)
     */
    public data object FORUM_MINUS : MaterialDesignGlyphs(0xF1AA9)

    /**
     * 󱪪 (0xF1AAA)
     */
    public data object FORUM_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1AAA)

    /**
     * 󱪫 (0xF1AAB)
     */
    public data object FORUM_PLUS : MaterialDesignGlyphs(0xF1AAB)

    /**
     * 󱪬 (0xF1AAC)
     */
    public data object FORUM_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1AAC)

    /**
     * 󱪭 (0xF1AAD)
     */
    public data object FORUM_REMOVE : MaterialDesignGlyphs(0xF1AAD)

    /**
     * 󱪮 (0xF1AAE)
     */
    public data object FORUM_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1AAE)

    /**
     * 󱪯 (0xF1AAF)
     */
    public data object HEATING_COIL : MaterialDesignGlyphs(0xF1AAF)

    /**
     * 󱪰 (0xF1AB0)
     */
    public data object IMAGE_LOCK : MaterialDesignGlyphs(0xF1AB0)

    /**
     * 󱪱 (0xF1AB1)
     */
    public data object IMAGE_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1AB1)

    /**
     * 󱪲 (0xF1AB2)
     */
    public data object LAND_FIELDS : MaterialDesignGlyphs(0xF1AB2)

    /**
     * 󱪳 (0xF1AB3)
     */
    public data object LAND_PLOTS : MaterialDesignGlyphs(0xF1AB3)

    /**
     * 󱪴 (0xF1AB4)
     */
    public data object LAND_PLOTS_CIRCLE : MaterialDesignGlyphs(0xF1AB4)

    /**
     * 󱪵 (0xF1AB5)
     */
    public data object LAND_PLOTS_CIRCLE_VARIANT : MaterialDesignGlyphs(0xF1AB5)

    /**
     * 󱪶 (0xF1AB6)
     */
    public data object LAND_ROWS_HORIZONTAL : MaterialDesignGlyphs(0xF1AB6)

    /**
     * 󱪷 (0xF1AB7)
     */
    public data object LAND_ROWS_VERTICAL : MaterialDesignGlyphs(0xF1AB7)

    /**
     * 󱪸 (0xF1AB8)
     */
    public data object MEDICAL_COTTON_SWAB : MaterialDesignGlyphs(0xF1AB8)

    /**
     * 󱪹 (0xF1AB9)
     */
    public data object ROLODEX : MaterialDesignGlyphs(0xF1AB9)

    /**
     * 󱪺 (0xF1ABA)
     */
    public data object ROLODEX_OUTLINE : MaterialDesignGlyphs(0xF1ABA)

    /**
     * 󱪻 (0xF1ABB)
     */
    public data object SORT_VARIANT_OFF : MaterialDesignGlyphs(0xF1ABB)

    /**
     * 󱪼 (0xF1ABC)
     */
    public data object TALLY_MARK_1 : MaterialDesignGlyphs(0xF1ABC)

    /**
     * 󱪽 (0xF1ABD)
     */
    public data object TALLY_MARK_2 : MaterialDesignGlyphs(0xF1ABD)

    /**
     * 󱪾 (0xF1ABE)
     */
    public data object TALLY_MARK_3 : MaterialDesignGlyphs(0xF1ABE)

    /**
     * 󱪿 (0xF1ABF)
     */
    public data object TALLY_MARK_4 : MaterialDesignGlyphs(0xF1ABF)

    /**
     * 󱫀 (0xF1AC0)
     */
    public data object TALLY_MARK_5 : MaterialDesignGlyphs(0xF1AC0)

    /**
     * 󱫁 (0xF1AC1)
     */
    public data object ATTACHMENT_CHECK : MaterialDesignGlyphs(0xF1AC1)

    /**
     * 󱫂 (0xF1AC2)
     */
    public data object ATTACHMENT_MINUS : MaterialDesignGlyphs(0xF1AC2)

    /**
     * 󱫃 (0xF1AC3)
     */
    public data object ATTACHMENT_OFF : MaterialDesignGlyphs(0xF1AC3)

    /**
     * 󱫄 (0xF1AC4)
     */
    public data object ATTACHMENT_PLUS : MaterialDesignGlyphs(0xF1AC4)

    /**
     * 󱫅 (0xF1AC5)
     */
    public data object ATTACHMENT_REMOVE : MaterialDesignGlyphs(0xF1AC5)

    /**
     * 󱫆 (0xF1AC6)
     */
    public data object PAPERCLIP_CHECK : MaterialDesignGlyphs(0xF1AC6)

    /**
     * 󱫇 (0xF1AC7)
     */
    public data object PAPERCLIP_MINUS : MaterialDesignGlyphs(0xF1AC7)

    /**
     * 󱫈 (0xF1AC8)
     */
    public data object PAPERCLIP_OFF : MaterialDesignGlyphs(0xF1AC8)

    /**
     * 󱫉 (0xF1AC9)
     */
    public data object PAPERCLIP_PLUS : MaterialDesignGlyphs(0xF1AC9)

    /**
     * 󱫊 (0xF1ACA)
     */
    public data object PAPERCLIP_REMOVE : MaterialDesignGlyphs(0xF1ACA)

    /**
     * 󱫋 (0xF1ACB)
     */
    public data object NETWORK_POS : MaterialDesignGlyphs(0xF1ACB)

    /**
     * 󱫌 (0xF1ACC)
     */
    public data object TIMER_ALERT : MaterialDesignGlyphs(0xF1ACC)

    /**
     * 󱫍 (0xF1ACD)
     */
    public data object TIMER_ALERT_OUTLINE : MaterialDesignGlyphs(0xF1ACD)

    /**
     * 󱫎 (0xF1ACE)
     */
    public data object TIMER_CANCEL : MaterialDesignGlyphs(0xF1ACE)

    /**
     * 󱫏 (0xF1ACF)
     */
    public data object TIMER_CANCEL_OUTLINE : MaterialDesignGlyphs(0xF1ACF)

    /**
     * 󱫐 (0xF1AD0)
     */
    public data object TIMER_CHECK : MaterialDesignGlyphs(0xF1AD0)

    /**
     * 󱫑 (0xF1AD1)
     */
    public data object TIMER_CHECK_OUTLINE : MaterialDesignGlyphs(0xF1AD1)

    /**
     * 󱫒 (0xF1AD2)
     */
    public data object TIMER_EDIT : MaterialDesignGlyphs(0xF1AD2)

    /**
     * 󱫓 (0xF1AD3)
     */
    public data object TIMER_EDIT_OUTLINE : MaterialDesignGlyphs(0xF1AD3)

    /**
     * 󱫔 (0xF1AD4)
     */
    public data object TIMER_LOCK : MaterialDesignGlyphs(0xF1AD4)

    /**
     * 󱫕 (0xF1AD5)
     */
    public data object TIMER_LOCK_OPEN : MaterialDesignGlyphs(0xF1AD5)

    /**
     * 󱫖 (0xF1AD6)
     */
    public data object TIMER_LOCK_OPEN_OUTLINE : MaterialDesignGlyphs(0xF1AD6)

    /**
     * 󱫗 (0xF1AD7)
     */
    public data object TIMER_LOCK_OUTLINE : MaterialDesignGlyphs(0xF1AD7)

    /**
     * 󱫘 (0xF1AD8)
     */
    public data object TIMER_MARKER : MaterialDesignGlyphs(0xF1AD8)

    /**
     * 󱫙 (0xF1AD9)
     */
    public data object TIMER_MARKER_OUTLINE : MaterialDesignGlyphs(0xF1AD9)

    /**
     * 󱫚 (0xF1ADA)
     */
    public data object TIMER_MINUS : MaterialDesignGlyphs(0xF1ADA)

    /**
     * 󱫛 (0xF1ADB)
     */
    public data object TIMER_MINUS_OUTLINE : MaterialDesignGlyphs(0xF1ADB)

    /**
     * 󱫜 (0xF1ADC)
     */
    public data object TIMER_MUSIC : MaterialDesignGlyphs(0xF1ADC)

    /**
     * 󱫝 (0xF1ADD)
     */
    public data object TIMER_MUSIC_OUTLINE : MaterialDesignGlyphs(0xF1ADD)

    /**
     * 󱫞 (0xF1ADE)
     */
    public data object TIMER_PAUSE : MaterialDesignGlyphs(0xF1ADE)

    /**
     * 󱫟 (0xF1ADF)
     */
    public data object TIMER_PAUSE_OUTLINE : MaterialDesignGlyphs(0xF1ADF)

    /**
     * 󱫠 (0xF1AE0)
     */
    public data object TIMER_PLAY : MaterialDesignGlyphs(0xF1AE0)

    /**
     * 󱫡 (0xF1AE1)
     */
    public data object TIMER_PLAY_OUTLINE : MaterialDesignGlyphs(0xF1AE1)

    /**
     * 󱫢 (0xF1AE2)
     */
    public data object TIMER_PLUS : MaterialDesignGlyphs(0xF1AE2)

    /**
     * 󱫣 (0xF1AE3)
     */
    public data object TIMER_PLUS_OUTLINE : MaterialDesignGlyphs(0xF1AE3)

    /**
     * 󱫤 (0xF1AE4)
     */
    public data object TIMER_REFRESH : MaterialDesignGlyphs(0xF1AE4)

    /**
     * 󱫥 (0xF1AE5)
     */
    public data object TIMER_REFRESH_OUTLINE : MaterialDesignGlyphs(0xF1AE5)

    /**
     * 󱫦 (0xF1AE6)
     */
    public data object TIMER_REMOVE : MaterialDesignGlyphs(0xF1AE6)

    /**
     * 󱫧 (0xF1AE7)
     */
    public data object TIMER_REMOVE_OUTLINE : MaterialDesignGlyphs(0xF1AE7)

    /**
     * 󱫨 (0xF1AE8)
     */
    public data object TIMER_STAR : MaterialDesignGlyphs(0xF1AE8)

    /**
     * 󱫩 (0xF1AE9)
     */
    public data object TIMER_STAR_OUTLINE : MaterialDesignGlyphs(0xF1AE9)

    /**
     * 󱫪 (0xF1AEA)
     */
    public data object TIMER_STOP : MaterialDesignGlyphs(0xF1AEA)

    /**
     * 󱫫 (0xF1AEB)
     */
    public data object TIMER_STOP_OUTLINE : MaterialDesignGlyphs(0xF1AEB)

    /**
     * 󱫬 (0xF1AEC)
     */
    public data object TIMER_SYNC : MaterialDesignGlyphs(0xF1AEC)

    /**
     * 󱫭 (0xF1AED)
     */
    public data object TIMER_SYNC_OUTLINE : MaterialDesignGlyphs(0xF1AED)

    /**
     * 󱫮 (0xF1AEE)
     */
    public data object EAR_HEARING_LOOP : MaterialDesignGlyphs(0xF1AEE)

    /**
     * 󱫯 (0xF1AEF)
     */
    public data object SAIL_BOAT_SINK : MaterialDesignGlyphs(0xF1AEF)

    /**
     * 󱫰 (0xF1AF0)
     */
    public data object LECTURN : MaterialDesignGlyphs(0xF1AF0)

}
