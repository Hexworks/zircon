package org.hexworks.zircon.api.graphics.nerd

public enum class CustomGlyphs(
    public val codePoint: Int,
) {


    /**
     *  (0xE5FA)
     */
    FOLDER_NPM(0xE5FA),

    /**
     *  (0xE5FB)
     */
    FOLDER_GIT(0xE5FB),

    /**
     *  (0xE5FB)
     */
    FOLDER_GIT_BRANCH(0xE5FB),

    /**
     *  (0xE5FC)
     */
    FOLDER_CONFIG(0xE5FC),

    /**
     *  (0xE5FD)
     */
    FOLDER_GITHUB(0xE5FD),

    /**
     *  (0xE5FE)
     */
    FOLDER_OPEN(0xE5FE),

    /**
     *  (0xE5FF)
     */
    FOLDER(0xE5FF),

    /**
     *  (0xE602)
     */
    PLAY_ARROW(0xE602),

    /**
     *  (0xE605)
     */
    RUBY(0xE605),

    /**
     *  (0xE612)
     */
    DEFAULT(0xE612),

    /**
     *  (0xE617)
     */
    HOME(0xE617),

    /**
     *  (0xE61D)
     */
    CPP(0xE61D),

    /**
     *  (0xE61E)
     */
    C(0xE61E),

    /**
     *  (0xE626)
     */
    GO(0xE626),

    /**
     *  (0xE629)
     */
    MSDOS(0xE629),

    /**
     *  (0xE62A)
     */
    WINDOWS(0xE62A),

    /**
     *  (0xE62B)
     */
    VIM(0xE62B),

    /**
     *  (0xE62C)
     */
    ELM(0xE62C),

    /**
     *  (0xE62D)
     */
    ELIXIR(0xE62D),

    /**
     *  (0xE62E)
     */
    ELECTRON(0xE62E),

    /**
     *  (0xE62F)
     */
    CRYSTAL(0xE62F),

    /**
     *  (0xE630)
     */
    PURESCRIPT(0xE630),

    /**
     *  (0xE631)
     */
    PUPPET(0xE631),

    /**
     *  (0xE632)
     */
    EMACS(0xE632),

    /**
     *  (0xE633)
     */
    ORGMODE(0xE633),

    /**
     *  (0xE634)
     */
    KOTLIN(0xE634),

    /**
     *  (0xE63A)
     */
    BAZEL(0xE63A),

    /**
     *  (0xE657)
     */
    FIREBASE(0xE657),

    /**
     *  (0xE6AB)
     */
    ASM(0xE6AB),

    /**
     *  (0xE6AC)
     */
    V_LANG(0xE6AC),

    /**
     *  (0xE6AD)
     */
    FOLDER_OCT(0xE6AD),

    /**
     *  (0xE6AE)
     */
    NEOVIM(0xE6AE),

    /**
     *  (0xE6AF)
     */
    FENNEL(0xE6AF),

    /**
     *  (0xE6B0)
     */
    COMMON_LISP(0xE6B0),

    /**
     *  (0xE6B1)
     */
    SCHEME(0xE6B1),

    /**
     *  (0xE6B2)
     */
    TOML(0xE6B2),

    /**
     *  (0xE6B3)
     */
    ASTRO(0xE6B3),

    /**
     *  (0xE6B4)
     */
    PRETTIER(0xE6B4),

    /**
     *  (0xE6B5)
     */
    ADA(0xE6B5),

    /**
     *  (0xE6B6)
     */
    CHUCK(0xE6B6),

    /**
     *  (0xE6B7)
     */
    VITRUVIAN(0xE6B7),

    /**
     *  (0xE6B8)
     */
    CSS(0xE6B8);


    /** The glyph as a string (a surrogate pair above U+FFFF). */
    public val glyph: String get() = codePointToString(codePoint)
}
