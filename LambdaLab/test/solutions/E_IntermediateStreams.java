package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This set of exercises covers more advanced stream operations
 * longer stream pipelines, and simple reductions.
 */
public class E_IntermediateStreams {

    /**
     * Convert a list of strings into a list of characters.
     */
    @Test
    public void e1_stringsToCharacters() {
        List<String> input = Arrays.asList("alfa", "bravo", "charlie");

        //TODO//List<Character> result = null;
        //BEGINREMOVE
        List<Character> result =
            input.stream()
                .flatMap(word -> word.chars().mapToObj(i -> (char)i))
                .collect(Collectors.toList());
        //ENDREMOVE

        assertEquals("[a, l, f, a, b, r, a, v, o, c, h, a, r, l, i, e]", result.toString());
        assertTrue(result.stream().allMatch(x -> x instanceof Character));
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.flatMap().
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Pay attention to the return type of String.chars() and boxing conversion.
    // </editor-fold>


    /**
     * Collect all the words from the text file into a list.
     * Use the regular expression pattern WORD_PATTERN to split
     * a string into words, and use Pattern.splitAsStream(String)
     * to do the splitting. WORD_PATTERN is defined at the bottom
     * of this file.
     *
     * @throws IOException
     */
    @Test
    public void e2_listOfAllWords() throws IOException {
        //TODO//List<String> output = null;
        //BEGINREMOVE
        List<String> output =
            reader.lines()
                  .flatMap(line -> WORD_PATTERN.splitAsStream(line))
                  .collect(Collectors.toList());
        // Alternatively, Stream.of() can be used instead of Arrays.stream().
        //ENDREMOVE

        assertEquals(
            Arrays.asList(
                "From", "fairest", "creatures", "we", "desire", "increase",
                "That", "thereby", "beauty's", "rose", "might", "never", "die",
                "But", "as", "the", "riper", "should", "by", "time", "decease",
                "His", "tender", "heir", "might", "bear", "his", "memory",
                "But", "thou", "contracted", "to", "thine", "own", "bright", "eyes",
                "Feed'st", "thy", "light's", "flame", "with", "self", "substantial", "fuel",
                "Making", "a", "famine", "where", "abundance", "lies",
                "Thy", "self", "thy", "foe", "to", "thy", "sweet", "self", "too", "cruel",
                "Thou", "that", "art", "now", "the", "world's", "fresh", "ornament",
                "And", "only", "herald", "to", "the", "gaudy", "spring",
                "Within", "thine", "own", "bud", "buriest", "thy", "content",
                "And", "tender", "churl", "mak'st", "waste", "in", "niggarding",
                "Pity", "the", "world", "or", "else", "this", "glutton", "be",
                "To", "eat", "the", "world's", "due", "by", "the", "grave", "and", "thee"),
            output);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.flatMap().
    // </editor-fold>


    /**
     * Read the words from the text file, and create a list containing the words
     * of length 8 or longer, converted to lower case, and sorted alphabetically.
     *
     * @throws IOException
     */
    @Test
    public void e3_longLowerCaseSortedWords() throws IOException {
        //TODO//List<String> output = null;
        //BEGINREMOVE
        List<String> output =
            reader.lines()
                  .flatMap(line -> WORD_PATTERN.splitAsStream(line))
                  .filter(word -> word.length() >= 8)
                  .map(String::toLowerCase)
                  .sorted()
                  .collect(Collectors.toList());
        //ENDREMOVE

        assertEquals(
            Arrays.asList(
                "abundance", "beauty's", "contracted", "creatures",
                "increase", "niggarding", "ornament", "substantial"),
            output);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.sorted().
    // </editor-fold>


    /**
     * Read the words from the text file, and create a list containing the words
     * of length 8 or longer, converted to lower case, and sorted reverse alphabetically.
     * (Same as above except for reversed sort order.)
     *
     * @throws IOException
     */
    @Test
    public void e4_longLowerCaseReverseSortedWords() throws IOException {
        //TODO//List<String> result = null;
        //BEGINREMOVE
        List<String> result =
            reader.lines()
                  .flatMap(line -> WORD_PATTERN.splitAsStream(line))
                  .filter(word -> word.length() >= 8)
                  .map(String::toLowerCase)
                  .sorted(Comparator.reverseOrder())
                  .collect(Collectors.toList());
        //ENDREMOVE

        assertEquals(
            Arrays.asList(
                "substantial", "ornament", "niggarding", "increase",
                "creatures", "contracted", "beauty's", "abundance"),
            result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Use Comparator.reverseOrder().
    // </editor-fold>


    /**
     * Read words from the text file, and sort unique, lower-cased words by length,
     * then alphabetically within length, and place the result into an output list.
     *
     * @throws IOException
     */
    @Test
    public void e5_sortedLowerCaseDistinctByLengthThenAlphabetically() throws IOException {
        //TODO//List<String> result = null;
        //BEGINREMOVE
        List<String> result =
            reader.lines()
                  .flatMap(line -> WORD_PATTERN.splitAsStream(line))
                  .map(String::toLowerCase)
                  .distinct()
                  .sorted(Comparator.comparingInt(String::length)
                                    .thenComparing(Comparator.naturalOrder()))
                  .collect(Collectors.toList());
        //ENDREMOVE

        assertEquals(
            Arrays.asList(
                "a", "as", "be", "by", "in", "or", "to", "we",
                "and", "art", "bud", "but", "die", "due", "eat", "foe",
                "his", "now", "own", "the", "thy", "too", "bear", "else",
                "eyes", "from", "fuel", "heir", "lies", "only",
                "pity", "rose", "self", "that", "thee", "this", "thou",
                "time", "with", "churl", "cruel", "flame", "fresh", "gaudy",
                "grave", "might", "never", "riper", "sweet", "thine",
                "waste", "where", "world", "bright", "desire", "famine",
                "herald", "mak'st", "making", "memory", "should", "spring",
                "tender", "within", "buriest", "content", "decease",
                "fairest", "feed'st", "glutton", "light's", "thereby", "world's", "beauty's",
                "increase", "ornament", "abundance", "creatures", "contracted", "niggarding",
                "substantial"),
            result);
    }
    // Hint 1:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.distinct().
    // </editor-fold>
    // Hint 2:
    // <editor-fold defaultstate="collapsed">
    // Use Comparator.theComparing().
    // </editor-fold>

        /**
     * Compute the value of 21!, that is, 21 factorial. This value is larger than
     * Long.MAX_VALUE, so you must use BigInteger.
     */
    @Test
    public void e6_bigFactorial() {
        //TODO//BigInteger result = BigInteger.ONE;
        //BEGINREMOVE
        BigInteger result =
            IntStream.rangeClosed(1, 21)
                     .mapToObj(n -> BigInteger.valueOf(n))
                     .reduce(BigInteger.ONE, (m, n) -> m.multiply(n));
        //ENDREMOVE

        assertEquals(new BigInteger("51090942171709440000"), result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Use LongStream and reduction.
    // </editor-fold>


    /**
     * Get the last word in the text file.
     *
     * @throws IOException
     */
    @Test
    public void e7_getLastWord() throws IOException {
        //TODO//String result = null;
        //BEGINREMOVE
        String result =
            reader.lines()
                  .flatMap(line -> WORD_PATTERN.splitAsStream(line))
                  .reduce((a, b) -> b)
                  .orElse("");
        //ENDREMOVE

        assertEquals("thee", result);
    }
    // Hint:
    // <editor-fold defaultstate="collapsed">
    // Use Stream.reduce().
    // </editor-fold>


// ========================================================
// END OF EXERCISES
// TEST INFRASTRUCTURE IS BELOW
// ========================================================


    // Pattern for splitting a string into words
    static final Pattern WORD_PATTERN = Pattern.compile("[- .:,]+");

    private BufferedReader reader;

    @Before
    public void z_setUpBufferedReader() throws IOException {
        reader = Files.newBufferedReader(
                Paths.get("SonnetI.txt"), StandardCharsets.UTF_8);
    }

    @After
    public void z_closeBufferedReader() throws IOException {
        reader.close();
    }

}