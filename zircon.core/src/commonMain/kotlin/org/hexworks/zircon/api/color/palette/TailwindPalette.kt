//MIT License
//
//Copyright (c) Tailwind Labs, Inc.
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

package org.hexworks.zircon.api.color.palette

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.Color.Companion.fromString as color
import org.hexworks.zircon.api.color.Palette


/**
 * This is a palette of colors based on the Tailwind CSS color palette
 * and naming conventions.
 * @see <a href="https://v3.tailwindcss.com/docs/customizing-colors">Tailwind CSS Colors</a>
 */
enum class TailwindPalette(
    val color: Color
) {
    // Slate
    SLATE_50(color("#f8fafc")),
    SLATE_100(color("#f1f5f9")),
    SLATE_200(color("#e2e8f0")),
    SLATE_300(color("#cbd5e1")),
    SLATE_400(color("#94a3b8")),
    SLATE_500(color("#64748b")),
    SLATE_600(color("#475569")),
    SLATE_700(color("#334155")),
    SLATE_800(color("#1e293b")),
    SLATE_900(color("#0f172a")),
    SLATE_950(color("#020617")),

    // Gray
    GRAY_50(color("#f9fafb")),
    GRAY_100(color("#f3f4f6")),
    GRAY_200(color("#e5e7eb")),
    GRAY_300(color("#d1d5db")),
    GRAY_400(color("#9ca3af")),
    GRAY_500(color("#6b7280")),
    GRAY_600(color("#4b5563")),
    GRAY_700(color("#374151")),
    GRAY_800(color("#1f2937")),
    GRAY_900(color("#111827")),
    GRAY_950(color("#030712")),

    // Zinc
    ZINC_50(color("#fafafa")),
    ZINC_100(color("#f4f4f5")),
    ZINC_200(color("#e4e4e7")),
    ZINC_300(color("#d4d4d8")),
    ZINC_400(color("#a1a1aa")),
    ZINC_500(color("#71717a")),
    ZINC_600(color("#52525b")),
    ZINC_700(color("#3f3f46")),
    ZINC_800(color("#27272a")),
    ZINC_900(color("#18181b")),
    ZINC_950(color("#09090b")),

    // Neutral
    NEUTRAL_50(color("#fafafa")),
    NEUTRAL_100(color("#f5f5f5")),
    NEUTRAL_200(color("#e5e5e5")),
    NEUTRAL_300(color("#d4d4d4")),
    NEUTRAL_400(color("#a3a3a3")),
    NEUTRAL_500(color("#737373")),
    NEUTRAL_600(color("#525252")),
    NEUTRAL_700(color("#404040")),
    NEUTRAL_800(color("#262626")),
    NEUTRAL_900(color("#171717")),
    NEUTRAL_950(color("#0a0a0a")),

    // Stone
    STONE_50(color("#fafaf9")),
    STONE_100(color("#f5f5f4")),
    STONE_200(color("#e7e5e4")),
    STONE_300(color("#d6d3d1")),
    STONE_400(color("#a8a29e")),
    STONE_500(color("#78716c")),
    STONE_600(color("#57534e")),
    STONE_700(color("#44403c")),
    STONE_800(color("#292524")),
    STONE_900(color("#1c1917")),
    STONE_950(color("#0c0a09")),

    // Red
    RED_50(color("#fef2f2")),
    RED_100(color("#fee2e2")),
    RED_200(color("#fecaca")),
    RED_300(color("#fca5a5")),
    RED_400(color("#f87171")),
    RED_500(color("#ef4444")),
    RED_600(color("#dc2626")),
    RED_700(color("#b91c1c")),
    RED_800(color("#991b1b")),
    RED_900(color("#7f1d1d")),
    RED_950(color("#450a0a")),

    // Orange
    ORANGE_50(color("#fff7ed")),
    ORANGE_100(color("#ffedd5")),
    ORANGE_200(color("#fed7aa")),
    ORANGE_300(color("#fdba74")),
    ORANGE_400(color("#fb923c")),
    ORANGE_500(color("#f97316")),
    ORANGE_600(color("#ea580c")),
    ORANGE_700(color("#c2410c")),
    ORANGE_800(color("#9a3412")),
    ORANGE_900(color("#7c2d12")),
    ORANGE_950(color("#431407")),

    // Amber
    AMBER_50(color("#fffbeb")),
    AMBER_100(color("#fef3c7")),
    AMBER_200(color("#fde68a")),
    AMBER_300(color("#fcd34d")),
    AMBER_400(color("#fbbf24")),
    AMBER_500(color("#f59e0b")),
    AMBER_600(color("#d97706")),
    AMBER_700(color("#b45309")),
    AMBER_800(color("#92400e")),
    AMBER_900(color("#78350f")),
    AMBER_950(color("#451a03")),

    // Yellow
    YELLOW_50(color("#fefce8")),
    YELLOW_100(color("#fef9c3")),
    YELLOW_200(color("#fef08a")),
    YELLOW_300(color("#fde047")),
    YELLOW_400(color("#facc15")),
    YELLOW_500(color("#eab308")),
    YELLOW_600(color("#ca8a04")),
    YELLOW_700(color("#a16207")),
    YELLOW_800(color("#854d0e")),
    YELLOW_900(color("#713f12")),
    YELLOW_950(color("#422006")),

    // Lime
    LIME_50(color("#f7fee7")),
    LIME_100(color("#ecfccb")),
    LIME_200(color("#d9f99d")),
    LIME_300(color("#bef264")),
    LIME_400(color("#a3e635")),
    LIME_500(color("#84cc16")),
    LIME_600(color("#65a30d")),
    LIME_700(color("#4d7c0f")),
    LIME_800(color("#3f6212")),
    LIME_900(color("#365314")),
    LIME_950(color("#1a2e05")),

    // Green
    GREEN_50(color("#f0fdf4")),
    GREEN_100(color("#dcfce7")),
    GREEN_200(color("#bbf7d0")),
    GREEN_300(color("#86efac")),
    GREEN_400(color("#4ade80")),
    GREEN_500(color("#22c55e")),
    GREEN_600(color("#16a34a")),
    GREEN_700(color("#15803d")),
    GREEN_800(color("#166534")),
    GREEN_900(color("#14532d")),
    GREEN_950(color("#052e16")),

    // Emerald
    EMERALD_50(color("#ecfdf5")),
    EMERALD_100(color("#d1fae5")),
    EMERALD_200(color("#a7f3d0")),
    EMERALD_300(color("#6ee7b7")),
    EMERALD_400(color("#34d399")),
    EMERALD_500(color("#10b981")),
    EMERALD_600(color("#059669")),
    EMERALD_700(color("#047857")),
    EMERALD_800(color("#065f46")),
    EMERALD_900(color("#064e3b")),
    EMERALD_950(color("#022c22")),

    // Teal
    TEAL_50(color("#f0fdfa")),
    TEAL_100(color("#ccfbf1")),
    TEAL_200(color("#99f6e4")),
    TEAL_300(color("#5eead4")),
    TEAL_400(color("#2dd4bf")),
    TEAL_500(color("#14b8a6")),
    TEAL_600(color("#0d9488")),
    TEAL_700(color("#0f766e")),
    TEAL_800(color("#115e59")),
    TEAL_900(color("#134e4a")),
    TEAL_950(color("#042f2e")),

    // Cyan
    CYAN_50(color("#ecfeff")),
    CYAN_100(color("#cffafe")),
    CYAN_200(color("#a5f3fc")),
    CYAN_300(color("#67e8f9")),
    CYAN_400(color("#22d3ee")),
    CYAN_500(color("#06b6d4")),
    CYAN_600(color("#0891b2")),
    CYAN_700(color("#0e7490")),
    CYAN_800(color("#155e75")),
    CYAN_900(color("#164e63")),
    CYAN_950(color("#083344")),

    // Sky
    SKY_50(color("#f0f9ff")),
    SKY_100(color("#e0f2fe")),
    SKY_200(color("#bae6fd")),
    SKY_300(color("#7dd3fc")),
    SKY_400(color("#38bdf8")),
    SKY_500(color("#0ea5e9")),
    SKY_600(color("#0284c7")),
    SKY_700(color("#0369a1")),
    SKY_800(color("#075985")),
    SKY_900(color("#0c4a6e")),
    SKY_950(color("#082f49")),

    // Blue
    BLUE_50(color("#eff6ff")),
    BLUE_100(color("#dbeafe")),
    BLUE_200(color("#bfdbfe")),
    BLUE_300(color("#93c5fd")),
    BLUE_400(color("#60a5fa")),
    BLUE_500(color("#3b82f6")),
    BLUE_600(color("#2563eb")),
    BLUE_700(color("#1d4ed8")),
    BLUE_800(color("#1e40af")),
    BLUE_900(color("#1e3a8a")),
    BLUE_950(color("#172554")),

    // Indigo
    INDIGO_50(color("#eef2ff")),
    INDIGO_100(color("#e0e7ff")),
    INDIGO_200(color("#c7d2fe")),
    INDIGO_300(color("#a5b4fc")),
    INDIGO_400(color("#818cf8")),
    INDIGO_500(color("#6366f1")),
    INDIGO_600(color("#4f46e5")),
    INDIGO_700(color("#4338ca")),
    INDIGO_800(color("#3730a3")),
    INDIGO_900(color("#312e81")),
    INDIGO_950(color("#1e1b4b")),

    // Violet
    VIOLET_50(color("#f5f3ff")),
    VIOLET_100(color("#ede9fe")),
    VIOLET_200(color("#ddd6fe")),
    VIOLET_300(color("#c4b5fd")),
    VIOLET_400(color("#a78bfa")),
    VIOLET_500(color("#8b5cf6")),
    VIOLET_600(color("#7c3aed")),
    VIOLET_700(color("#6d28d9")),
    VIOLET_800(color("#5b21b6")),
    VIOLET_900(color("#4c1d95")),
    VIOLET_950(color("#2e1065")),

    // Purple
    PURPLE_50(color("#faf5ff")),
    PURPLE_100(color("#f3e8ff")),
    PURPLE_200(color("#e9d5ff")),
    PURPLE_300(color("#d8b4fe")),
    PURPLE_400(color("#c084fc")),
    PURPLE_500(color("#a855f7")),
    PURPLE_600(color("#9333ea")),
    PURPLE_700(color("#7e22ce")),
    PURPLE_800(color("#6b21a8")),
    PURPLE_900(color("#581c87")),
    PURPLE_950(color("#3b0764")),

    // Fuchsia
    FUCHSIA_50(color("#fdf4ff")),
    FUCHSIA_100(color("#fae8ff")),
    FUCHSIA_200(color("#f5d0fe")),
    FUCHSIA_300(color("#f0abfc")),
    FUCHSIA_400(color("#e879f9")),
    FUCHSIA_500(color("#d946ef")),
    FUCHSIA_600(color("#c026d3")),
    FUCHSIA_700(color("#a21caf")),
    FUCHSIA_800(color("#86198f")),
    FUCHSIA_900(color("#701a75")),
    FUCHSIA_950(color("#4a044e")),

    // Pink
    PINK_50(color("#fdf2f8")),
    PINK_100(color("#fce7f3")),
    PINK_200(color("#fbcfe8")),
    PINK_300(color("#f9a8d4")),
    PINK_400(color("#f472b6")),
    PINK_500(color("#ec4899")),
    PINK_600(color("#db2777")),
    PINK_700(color("#be185d")),
    PINK_800(color("#9d174d")),
    PINK_900(color("#831843")),
    PINK_950(color("#500724")),

    // Rose
    ROSE_50(color("#fff1f2")),
    ROSE_100(color("#ffe4e6")),
    ROSE_200(color("#fecdd3")),
    ROSE_300(color("#fda4af")),
    ROSE_400(color("#fb7185")),
    ROSE_500(color("#f43f5e")),
    ROSE_600(color("#e11d48")),
    ROSE_700(color("#be123c")),
    ROSE_800(color("#9f1239")),
    ROSE_900(color("#881337")),
    ROSE_950(color("#4c0519"));

    companion object : Palette<TailwindPalette> {
        override val keys = entries.toSet()
        override val colors = entries.map { it.color }.toSet()

        override fun get(key: TailwindPalette) = key.color
    }
}