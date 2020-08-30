package io.github.lee0701.kiturami.converter.hangul;

import io.github.lee0701.kiturami.Converter;

public class Han2 implements Converter<String, String> {

    public static final String CONSONANT = "ㄱㄲㄳㄴㄵㄶㄷㄸㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅃㅄㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ";
    public static final String VOWEL = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ";
    public static final String INITIAL = "ᄀᄁᄂᄃᄄᄅᄆᄇᄈᄉᄊᄋᄌᄍᄎᄏᄐᄑᄒ";
    public static final String MEDIAL = "ᅡᅢᅣᅤᅥᅦᅧᅨᅩᅪᅫᅬᅭᅮᅯᅰᅱᅲᅳᅴᅵ";
    public static final String FINAL = "ᆨᆩᆪᆫᆬᆭᆮᆯᆰᆱᆲᆳᆴᆵᆶᆷᆸᆹᆺᆻᆼᆽᆾᆿᇀᇁᇂ";
    public static final String CONSONANT_TO_INITIAL = "ᄀᄁ\0ᄂ\0\0ᄃᄄᄅ\0\0\0\0\0\0\0ᄆᄇᄈ\0ᄉᄊᄋᄌᄍᄎᄏᄐᄑᄒ";
    public static final String VOWEL_TO_MEDIAL = MEDIAL;
    public static final String CONSONANT_TO_FINAL = "ᆨᆩᆪᆫᆬᆭᆮ\0ᆯᆰᆱᆲᆳᆴᆵᆶᆷᆸ\0ᆹᆺᆻᆼᆽ\0ᆾᆿᇀᇁᇂ";

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
                } else if(initial != 0) result.append(initial);
                else result.append(c);
            } else if(vowel >= 0) {
                char medial = VOWEL_TO_MEDIAL.charAt(vowel);
                if(FINAL.indexOf(last) >= 0) {
                    char initial = CONSONANT_TO_INITIAL.charAt(CONSONANT_TO_FINAL.indexOf(last));
                    result.deleteCharAt(result.length()-1);
                    result.append(initial);
                    result.append(medial);
                } else if(medial != 0) result.append(medial);
                else result.append(c);
            } else result.append(c);
        }
        return result.toString();
    }
}
