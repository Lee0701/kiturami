package io.github.lee0701.kiturami.converter.hangul;

import io.github.lee0701.kiturami.converter.Converter;

import static io.github.lee0701.kiturami.converter.hangul.HangulConstants.*;

public class JamosToCompatibility implements Converter<String, String> {

    private static final String FROM = CONSONANT_TO_INITIAL + VOWEL_TO_MEDIAL + CONSONANT_TO_FINAL;
    private static final String TO = CONSONANT + VOWEL + CONSONANT;

    @Override
    public String convert(String input) {
        char[] chars = input.toCharArray();
        StringBuilder result = new StringBuilder();
        for(char c : chars) {
            int i = FROM.indexOf(c);
            if(c != 0 && i >= 0) result.append(TO.charAt(i));
            else result.append(c);
        }
        return result.toString();
    }
}
