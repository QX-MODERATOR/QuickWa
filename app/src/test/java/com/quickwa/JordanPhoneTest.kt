package com.quickwa

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class JordanPhoneTest {
    @Test
    fun examples_normalize_correctly() {
        assertEquals("962790000000", JordanPhone.normalizeToInternational("0790000000"))
        assertEquals("962790000000", JordanPhone.normalizeToInternational("+962790000000"))
        assertEquals("962790000000", JordanPhone.normalizeToInternational("790000000"))
        assertEquals("962790000000", JordanPhone.normalizeToInternational("00962790000000"))
        assertEquals("962790000000", JordanPhone.normalizeToInternational("962790000000"))
    }

    @Test
    fun strips_separators() {
        assertEquals("962790000000", JordanPhone.normalizeToInternational("(079) 000-0000"))
        assertEquals("962790000000", JordanPhone.normalizeToInternational("+962 79 000 0000"))
    }

    @Test
    fun rejects_invalid_numbers() {
        assertNull(JordanPhone.normalizeToInternational(""))
        assertNull(JordanPhone.normalizeToInternational("123"))
        assertNull(JordanPhone.normalizeToInternational("962890000000"))
        assertNull(JordanPhone.normalizeToInternational("+961790000000"))
        assertNull(JordanPhone.normalizeToInternational("96279000000")) // too short
        assertNull(JordanPhone.normalizeToInternational("9627900000000")) // too long
    }
}

