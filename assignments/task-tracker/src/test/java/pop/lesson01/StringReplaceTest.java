package pop.lesson01;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static pop.lesson01.StringReplace.*;

public class StringReplaceTest {

    @Test
    public void testReplaceLetterPlusAllLetters() {
        assertEquals(replaceLetterPlus("XXXX"), "CCCC");
    }

    @Test
    public void testReplaceLetterPlusOneLetter() {
        assertEquals(replaceLetterPlus("BADX"), "BADC");
    }

    @Test
    public void testReplaceLetterPlusNoReplace() {
        assertEquals(replaceLetterPlus("ABBADDCC"), "ABBADDCC");
    }

    @Test
    public void testReplaceLetterConcatAllLetters() {
        assertEquals(replaceLetterConcat("XXXX"), "CCCC");
    }

    @Test
    public void testReplaceLetterConcatOneLetter() {
        assertEquals(replaceLetterConcat("BADX"), "BADC");
    }

    @Test
    public void testReplaceLetterConcatNoReplace() {
        assertEquals(replaceLetterConcat("ABBADDCC"), "ABBADDCC");
    }

    @Test
    public void testReplaceLetterCharArrayAllLetters() {
        assertEquals(replaceLetterCharArray("XXXX"), "CCCC");
    }

    @Test
    public void testReplaceLetterCharArrayOneLetter() {
        assertEquals(replaceLetterCharArray("BADX"), "BADC");
    }

    @Test
    public void testReplaceLetterCharArrayNoReplace() {
        assertEquals(replaceLetterCharArray("ABBADDCC"), "ABBADDCC");
    }

    @Test
    public void testReplaceLetterStringBuilderAllLetters() {
        assertEquals(replaceLetterStringBuilder("XXXX"), "CCCC");
    }

    @Test
    public void testReplaceLetterStringBuilderOneLetter() {
        assertEquals(replaceLetterStringBuilder("BADX"), "BADC");
    }

    @Test
    public void testReplaceLetterStringBuilderNoReplace() {
        assertEquals(replaceLetterStringBuilder("ABBADDCC"), "ABBADDCC");
    }

    @Test
    public void testReplaceLetterStringBufferAllLetters() {
        assertEquals(replaceLetterStringBuffer("XXXX"), "CCCC");
    }

    @Test
    public void testReplaceLetterStringBufferOneLetter() {
        assertEquals(replaceLetterStringBuffer("BADX"), "BADC");
    }

    @Test
    public void testReplaceLetterStringBufferNoReplace() {
        assertEquals(replaceLetterStringBuffer("ABBADDCC"), "ABBADDCC");
    }

}