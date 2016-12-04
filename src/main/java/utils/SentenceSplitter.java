package utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mateusz on 26-11-2016.
 */
public class SentenceSplitter {
    //private static String pattern = "([\\.\\s,\\?!\\(\\)\\[\\]<>/]?\\s)";
    private static String pattern = "[^\\p{LD}]+";  //pozbywa sie calej interpunkcji ze zdania

    public static List<String> split(String line) {
        return Arrays.asList(line.split(pattern));
    }
}