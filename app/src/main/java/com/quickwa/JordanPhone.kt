package com.quickwa

object JordanPhone {
    /**
     * Normalizes a Jordanian mobile number to international format without plus: 9627XXXXXXXX.
     *
     * Accepted inputs include:
     * - 0790000000
     * - 790000000
     * - +962790000000
     * - 00962790000000
     * - 962790000000
     * - Numbers with spaces/dashes/brackets
     */
    fun normalizeToInternational(input: String): String? {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return null

        // Keep a leading '+' if present; everything else must become digits.
        val hasPlus = trimmed.startsWith("+")
        val digits = buildString(trimmed.length) {
            for (ch in trimmed) {
                if (ch.isDigit()) append(ch)
            }
        }
        if (digits.isEmpty()) return null

        val normalized = when {
            hasPlus && digits.startsWith("962") -> digits
            digits.startsWith("00962") -> "962" + digits.removePrefix("00962")
            digits.startsWith("962") -> digits
            digits.length == 10 && digits.startsWith("0") -> "962" + digits.drop(1)
            digits.length == 9 && digits.startsWith("7") -> "962$digits"
            else -> null
        } ?: return null

        // Final validation: Jordan mobile numbers are 9627XXXXXXXX (12 digits).
        if (normalized.length != 12) return null
        if (!normalized.startsWith("9627")) return null
        if (!normalized.all { it.isDigit() }) return null

        return normalized
    }
}

