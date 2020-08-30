package io.github.lee0701.kiturami;

import static org.junit.Assert.*;

import io.github.lee0701.kiturami.converter.Converter;
import io.github.lee0701.kiturami.converter.string.*;
import io.github.lee0701.kiturami.converter.Sequential;
import io.github.lee0701.kiturami.converter.hangul.Han2;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConverterTest {

    private static final String LAYOUT_ALPHABET_QWERTY = " `~1!2@3#4$5%6^7&8*9(0)-_=+\\\\|qQwWeErRtTyYuUiIoOpP[{]}aAsSdDfFgGhHjJkKlL;:'\"zZxXcCvVbBnNmM,<.>/?";
    private static final String LAYOUT_DUBEOL_STANDARD = " `~1!2@3#4$5%6^7&8*9(0)-_=+\\\\|ㅂㅃㅈㅉㄷㄸㄱㄲㅅㅆㅛㅛㅕㅕㅑㅑㅐㅒㅔㅖ[{]}ㅁㅁㄴㄴㅇㅇㄹㄹㅎㅎㅗㅗㅓㅓㅏㅏㅣㅣ;:'\"ㅋㅋㅌㅌㅊㅊㅍㅍㅠㅠㅜㅜㅡㅡ,<.>/?";
    private static final String LAYOUT_SEBEOL_FINAL = " *※ᇂᆩᆻᆰᆸᆽᅭᆵᅲᆴᅣ=ᅨ“ᅴ”ᅮ'ᄏ~);>+:\\\\ᆺᇁᆯᇀᅧᆬᅢᆶᅥᆳᄅ5ᄃ6ᄆ7ᄎ8ᄑ9(%</ᆼᆮᆫᆭᅵᆲᅡᆱᅳᅤᄂ0ᄋ1ᄀ2ᄌ3ᄇ4ᄐ·ᆷᆾᆨᆹᅦᆿᅩᆪᅮ?ᄉ-ᄒ\",,..ᅩ!";

    private Map<Character, Character> generateLayout(String source, String destination) {
        Map<Character, Character> result = new HashMap<>();
        for(int i = 0 ; i < Math.min(source.length(), destination.length()) ; i++) {
            result.put(source.charAt(i), destination.charAt(i));
        }
        return result;
    }

    @Test
    public void testNormalize() {
        Converter<String, String> converter = new Normalize("NFC");
        assertEquals("한글", converter.convert("한글"));
        assertEquals("정규화", converter.convert("정규화"));
    }

    @Test
    public void testCombine() {
        Map<String, Character> table = new HashMap<>() {{
            put("ᆸᆺ", 'ᆹ');
            put("ᆯᆨ", 'ᆰ');
            put("ᆰᆺ", 'ᇌ');
        }};
        Converter<String, String> converter = new Combine(table);
        assertEquals("없다", converter.convert("업ᆺ다"));
        assertEquals("아ᇌ", converter.convert("알ᆨᆺ"));
    }

    @Test
    public void testExpand() {
        Map<Character, String> table = new HashMap<>() {{
            put('A', "asdf");
            put('S', "success");
        }};
        Converter<String, String> converter = new Expand(table);
        assertEquals("This is a successful asdf", converter.convert("This is a Sful A"));
    }

    @Test
    public void testHan2() {
        Converter<String, String> converter = new Han2();
        assertEquals("변환", converter.convert("ㅂㅕㄴㅎㅘㄴ"));
    }

    @Test
    public void testDudgks() {
        Map<Character, Character> layout = generateLayout(LAYOUT_ALPHABET_QWERTY, LAYOUT_DUBEOL_STANDARD);
        Map<String, Character> combinations = new HashMap<>() {{
            put("ᅩᅡ", 'ᅪ'); put("ᅩᅢ", 'ᅫ'); put("ᅩᅵ", 'ᅬ'); put("ᅮᅥ", 'ᅯ'); put("ᅮᅦ", 'ᅰ'); put("ᅮᅵ", 'ᅱ'); put("ᅳᅵ", 'ᅴ');
            put("ᆨᆺ", 'ᆪ'); put("ᆫᇂ", 'ᆭ'); put("ᆫᆽ", 'ᆬ'); put("ᆯᆨ", 'ᆰ'); put("ᆯᆷ", 'ᆱ'); put("ᆯᆸ", 'ᆲ'); put("ᆯᆺ", 'ᆳ'); put("ᆯᇀ", 'ᆴ'); put("ᆯᇁ", 'ᆵ'); put("ᆯᇂ", 'ᆶ');
        }};
        Converter<String, String> converter = new Sequential<>(new Sequential<>(new Sequential<>(
                new ReplaceChar(layout),
                new Han2()),
                new Combine(combinations)),
                new Normalize("NFC")
        );
        assertEquals("영한", converter.convert("dudgks"));
        assertEquals("둡벐식", converter.convert("enqqjfttlr"));
    }

    @Test
    public void testMfsjea() {
        Map<Character, Character> layout = generateLayout(LAYOUT_ALPHABET_QWERTY, LAYOUT_SEBEOL_FINAL);
        Map<String, Character> combinations = new HashMap<>() {{
            put("ᄀᄀ", 'ᄁ'); put("ᄃᄃ", 'ᄄ'); put("ᄇᄇ", 'ᄈ'); put("ᄉᄉ", 'ᄊ'); put("ᄌᄌ", 'ᄍ');
            put("ᅩᅡ", 'ᅪ'); put("ᅩᅢ", 'ᅫ'); put("ᅩᅵ", 'ᅬ'); put("ᅮᅥ", 'ᅯ'); put("ᅮᅦ", 'ᅰ'); put("ᅮᅵ", 'ᅱ'); put("ᅳᅵ", 'ᅴ');
            put("ᆨᆨ", 'ᆩ'); put("ᆨᆺ", 'ᆪ'); put("ᆫᇂ", 'ᆭ'); put("ᆫᆽ", 'ᆬ'); put("ᆯᆨ", 'ᆰ'); put("ᆯᆷ", 'ᆱ'); put("ᆯᆸ", 'ᆲ'); put("ᆯᆺ", 'ᆳ'); put("ᆯᇀ", 'ᆴ'); put("ᆯᇁ", 'ᆵ'); put("ᆯᇂ", 'ᆶ'); put("ᆺᆺ", 'ᆻ');
        }};
        Converter<String, String> converter = new ReplaceChar(layout)
                .then(new Combine(combinations))
                .then(new Normalize("NFC"));
        assertEquals("세벌식 영한", converter.convert("nc;twndx jeamfs"));
        assertEquals("쀍쒙퇋", converter.convert(";;9c@nn9tW'/fR"));
    }

    @Test
    public void testHangulMultiTap() {
        Map<Character, String> layout = new HashMap<>() {{
            put('1', "ㄱ");
            put('2', "ㄴ");
            put('3', "ㅏㅓ");
            put('4', "ㄹ");
            put('5', "ㅁ");
            put('6', "ㅗㅜ");
            put('7', "ㅅ");
            put('8', "ㅇ");
            put('9', "ㅣ");
            put('0', "ㅡ");
        }};
        Map<String, Character> additions = new HashMap<>() {{
            put("ㄱ*", 'ㅋ');
            put("ㄴ*", 'ㄷ');
            put("ㄷ*", 'ㅌ');
            put("ㅁ*", 'ㅂ');
            put("ㅂ*", 'ㅍ');
            put("ㅅ*", 'ㅈ');
            put("ㅈ*", 'ㅊ');
            put("ㅇ*", 'ㅎ');

            put("ㄱ#", 'ㄲ');
            put("ㄷ#", 'ㄸ');
            put("ㅂ#", 'ㅃ');
            put("ㅅ#", 'ㅆ');
            put("ㅈ#", 'ㅉ');

            put("ㅏ*", 'ㅑ');
            put("ㅐ*", 'ㅒ');
            put("ㅓ*", 'ㅕ');
            put("ㅔ*", 'ㅖ');
            put("ㅗ*", 'ㅛ');
            put("ㅜ*", 'ㅠ');
        }};
        Map<String, Character> combinations = new HashMap<>() {{
            put("ᅡᅵ", 'ᅢ'); put("ᅣᅵ", 'ᅤ'); put("ᅥᅵ", 'ᅦ'); put("ᅧᅵ", 'ᅨ'); put("ᅩᅡ", 'ᅪ'); put("ᅩᅢ", 'ᅫ'); put("ᅩᅵ", 'ᅬ'); put("ᅮᅥ", 'ᅯ'); put("ᅮᅦ", 'ᅰ'); put("ᅮᅵ", 'ᅱ'); put("ᅳᅵ", 'ᅴ');
            put("ᆨᆺ", 'ᆪ'); put("ᆫᇂ", 'ᆭ'); put("ᆫᆽ", 'ᆬ'); put("ᆯᆨ", 'ᆰ'); put("ᆯᆷ", 'ᆱ'); put("ᆯᆸ", 'ᆲ'); put("ᆯᆺ", 'ᆳ'); put("ᆯᇀ", 'ᆴ'); put("ᆯᇁ", 'ᆵ'); put("ᆯᇂ", 'ᆶ');
        }};
        Converter<String, String> converter = new MultiTap(layout)
                .then(new Combine(additions))
                .then(new Han2())
                .then(new Combine(combinations))
                .then(new Normalize("NFC"));
        assertEquals("나랏글", converter.convert("23437104"));
        assertEquals("변환기", converter.convert("5*33*28*63219"));
        assertEquals("얘야 밥 먹었니", converter.convert("83*983* 5*35* 53318337#29"));
    }
}
