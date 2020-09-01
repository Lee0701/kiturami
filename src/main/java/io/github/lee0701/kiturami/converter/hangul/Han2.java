package io.github.lee0701.kiturami.converter.hangul;

import io.github.lee0701.kiturami.converter.Converter;

import static io.github.lee0701.kiturami.converter.hangul.HangulConstants.*;

public class Han2 implements Converter<String, String> {

    @Override
    public String convert(String input) {
        char[] chars = input.toCharArray();
        StringBuilder result = new StringBuilder();
        for(int i = 0 ; i < chars.length ; i++) {
            char c = chars[i];
            char last = i == 0 ? 0 : result.charAt(result.length()-1);
            int consonant = CONSONANT.indexOf(c);
            int vowel = VOWEL.indexOf(c);
            if(consonant >= 0) {
                char initial = CONSONANT_TO_INITIAL.charAt(consonant);
                char fin = CONSONANT_TO_FINAL.charAt(consonant);
                if(FINAL.indexOf(last) >= 0 || MEDIAL.indexOf(last) >= 0) {
                    if(fin != 0) result.append(fin);
                    else if(initial != 0) result.append(initial);
                } else if(initial != 0) result.append(initial);
            } else if(vowel >= 0) {
                char medial = VOWEL_TO_MEDIAL.charAt(vowel);
                if(FINAL.indexOf(last) >= 0) {
                    char initial = CONSONANT_TO_INITIAL.charAt(CONSONANT_TO_FINAL.indexOf(last));
                    result.deleteCharAt(result.length()-1);
                    result.append(initial);
                    result.append(medial);
                } else if(medial != 0) result.append(medial);
            } else result.append(c);
        }
        return result.toString();
    }
}
