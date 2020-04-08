package fr.info.pl2020.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FunctionsUtilsTest {

    @Test
    public void isEmail() {
        assertTrue(FunctionsUtils.isEmail("toto@gmail.com"));
        assertTrue(FunctionsUtils.isEmail("toto.tata-titi@gmail.com"));
        assertTrue(FunctionsUtils.isEmail("toto@gmail-yahoo.com"));
        assertTrue(FunctionsUtils.isEmail("toto@gmail.co.fr"));

        assertFalse(FunctionsUtils.isEmail(".toto@gmail.com"));
        assertFalse(FunctionsUtils.isEmail("toto@gmail.com."));
        assertFalse(FunctionsUtils.isEmail("toto@gmail.c"));
        assertFalse(FunctionsUtils.isEmail("toto@gmail..com"));
        assertFalse(FunctionsUtils.isEmail("toto@gmail.commercial"));
    }

    @Test
    public void isNullOrBlank() {
        assertTrue(FunctionsUtils.isNullOrBlank(null));
        assertTrue(FunctionsUtils.isNullOrBlank(""));
        assertTrue(FunctionsUtils.isNullOrBlank("    "));
        assertTrue(FunctionsUtils.isNullOrBlank("\n"));
        assertTrue(FunctionsUtils.isNullOrBlank("\t"));
        assertTrue(FunctionsUtils.isNullOrBlank("\r"));

        assertFalse(FunctionsUtils.isNullOrBlank("abc"));
        assertFalse(FunctionsUtils.isNullOrBlank("123"));
        assertFalse(FunctionsUtils.isNullOrBlank(" abc "));
        assertFalse(FunctionsUtils.isNullOrBlank("\tabc\n"));
    }
}
