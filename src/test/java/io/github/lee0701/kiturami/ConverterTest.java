package io.github.lee0701.kiturami;

import static org.junit.Assert.*;

import io.github.lee0701.kiturami.converter.Converter;
import io.github.lee0701.kiturami.converter.string.MultiTap;
import io.github.lee0701.kiturami.converter.string.Normalize;
import io.github.lee0701.kiturami.converter.string.ReplaceChar;
import io.github.lee0701.kiturami.converter.Sequential;
import io.github.lee0701.kiturami.converter.hangul.Combination;
import io.github.lee0701.kiturami.converter.hangul.Han2;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConverterTest {
    @Test
    public void testNormalize() {
        Converter<String, String> converter = new Normalize("NFC");
        assertEquals("한글", converter.convert("한글"));
        assertEquals("정규화", converter.convert("정규화"));
    }

    @Test
    public void testCombination() {
        Map<String, Character> table = new HashMap<>() {{
            put("ᆸᆺ", 'ᆹ');
            put("ᆯᆨ", 'ᆰ');
            put("ᆰᆺ", 'ᇌ');
        }};
        Converter<String, String> converter = new Combination(table);
        assertEquals("없다", converter.convert("업ᆺ다"));
        assertEquals("아ᇌ", converter.convert("알ᆨᆺ"));
    }

    @Test
    public void testHan2() {
        Converter<String, String> converter = new Han2();
        assertEquals("변환", converter.convert("ㅂㅕㄴㅎㅘㄴ"));
    }

    @Test
    public void testDudgks() {
        Map<Character, Character> layout = new HashMap<>() {{
            put('Q', 'ㅃ'); put('W', 'ㅉ'); put('E', 'ㄸ'); put('R', 'ㄲ'); put('T', 'ㅆ'); put('O', 'ㅒ'); put('P', 'ㅖ');
            put('q', 'ㅂ'); put('w', 'ㅈ'); put('e', 'ㄷ'); put('r', 'ㄱ'); put('t', 'ㅅ'); put('y', 'ㅛ'); put('u', 'ㅕ'); put('i', 'ㅑ'); put('o', 'ㅐ'); put('p', 'ㅔ');
            put('a', 'ㅁ'); put('s', 'ㄴ'); put('d', 'ㅇ'); put('f', 'ㄹ'); put('g', 'ㅎ'); put('h', 'ㅗ'); put('j', 'ㅓ'); put('k', 'ㅏ'); put('l', 'ㅣ');
            put('z', 'ㅋ'); put('x', 'ㅌ'); put('c', 'ㅊ'); put('v', 'ㅍ'); put('b', 'ㅠ'); put('n', 'ㅜ'); put('m', 'ㅡ');
        }};
        Map<String, Character> combinations = new HashMap<>() {{
            put("ᅩᅡ", 'ᅪ'); put("ᅩᅢ", 'ᅫ'); put("ᅩᅵ", 'ᅬ'); put("ᅮᅦ", 'ᅰ'); put("ᅮᅵ", 'ᅱ'); put("ᅳᅵ", 'ᅴ');
            put("ᆨᆺ", 'ᆪ'); put("ᆫᇂ", 'ᆭ'); put("ᆫᆽ", 'ᆬ'); put("ᆯᆨ", 'ᆰ'); put("ᆯᆷ", 'ᆱ'); put("ᆯᆸ", 'ᆲ'); put("ᆯᆺ", 'ᆳ'); put("ᆯᇀ", 'ᆴ'); put("ᆯᇁ", 'ᆵ'); put("ᆯᇂ", 'ᆶ');
        }};
        Converter<String, String> converter = new Sequential<>(new Sequential<>(new Sequential<>(
                new ReplaceChar(layout),
                new Han2()),
                new Combination(combinations)),
                new Normalize("NFC")
        );
        assertEquals("영한", converter.convert("dudgks"));
        assertEquals("둡벐식", converter.convert("enqqjfttlr"));
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
            put("ᅡᅵ", 'ᅢ'); put("ᅣᅵ", 'ᅤ'); put("ᅥᅵ", 'ᅦ'); put("ᅧᅵ", 'ᅨ'); put("ᅩᅡ", 'ᅪ'); put("ᅩᅢ", 'ᅫ'); put("ᅩᅵ", 'ᅬ'); put("ᅮᅦ", 'ᅰ'); put("ᅮᅵ", 'ᅱ'); put("ᅳᅵ", 'ᅴ');
            put("ᆨᆺ", 'ᆪ'); put("ᆫᇂ", 'ᆭ'); put("ᆫᆽ", 'ᆬ'); put("ᆯᆨ", 'ᆰ'); put("ᆯᆷ", 'ᆱ'); put("ᆯᆸ", 'ᆲ'); put("ᆯᆺ", 'ᆳ'); put("ᆯᇀ", 'ᆴ'); put("ᆯᇁ", 'ᆵ'); put("ᆯᇂ", 'ᆶ');
        }};
        Converter<String, String> converter = new MultiTap(layout)
                .thenSequential(new Combination(additions))
                .thenSequential(new Han2())
                .thenSequential(new Combination(combinations))
                .thenSequential(new Normalize("NFC"));
        assertEquals("나랏글", converter.convert("23437104"));
        assertEquals("변환기", converter.convert("5*33*28*63219"));
        assertEquals("얘야 밥 먹었니", converter.convert("83*983* 5*35* 53318337#29"));
    }
}
