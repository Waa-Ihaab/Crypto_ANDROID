package backend

private const val CESAR_DEFAULT_SHIFT = 13
private val CHAR_RANGES = listOf(
    'A'..'Z', 'a'..'z'
)

class CryptoUtil {

    /**
     * Returns encrypted text applying the Cesar cipher on [text] text with a shift of 13; works
     * only with a-z and A-Z characters; space character is left unchanged; other characters throw
     * IllegalCharException
     * @param [text] text to encrypt with Cesar code
     * @throws IllegalCharException "Illegal character for encryption: <c>" with the first faulty
     * character <c> in [text]
     */
    fun cesar(text: String): String {
        return encrypt(text, CESAR_DEFAULT_SHIFT)
    }

    private fun encrypt(text: String, shift: Int): String {
        return text.map { c ->
            if (c == ' ') {
                c
            } else {
                CHAR_RANGES.firstOrNull { range ->
                    c in range
                }?.let { range ->
                    c.plus(shift.mod(range.count())).let { shiftedChar ->
                        if (shiftedChar > range.last) {
                            shiftedChar.minus(range.last.minus(range.first) + 1)
                        } else {
                            shiftedChar
                        }
                    }
                } ?: throw IllegalCharException("Illegal character for encryption: $c")
            }
        }.joinToString(separator = "")
    }
}

class IllegalCharException(override val message: String): RuntimeException(message)
