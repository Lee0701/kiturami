package io.github.lee0701.kiturami;

import static org.junit.Assert.*;

import io.github.lee0701.kiturami.converter.string.MultiTap;
import io.github.lee0701.kiturami.converter.string.Normalize;
import io.github.lee0701.kiturami.converter.string.ReplaceChar;
import io.github.lee0701.kiturami.converter.Sequential;
import io.github.lee0701.kiturami.converter.hangul.Combination;
import io.github.lee0701.kiturami.converter.hangul.Han2;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
            put('3', "ㅏ', 'ㅓ");
            put('4', "ㄹ");
            put('5', "ㅁ");
            put('6', "ㅗ', 'ㅜ");
            put('7', "ㅅ");
            put('8', "ㅇ");
            put('9', "ㅣ");
            put('0', "ㅡ");
        }};
        Map<String, Character> additions = new HashMap<>() {{
            put("ㅁ*", 'ㅂ');
            put("ㅂ*", 'ㅍ');
        }};
        Map<String, Character> combinations = new HashMap<>() {{
            put("ᅩᅡ", 'ᅪ'); put("ᅩᅢ", 'ᅫ'); put("ᅩᅵ", 'ᅬ'); put("ᅮᅦ", 'ᅰ'); put("ᅮᅵ", 'ᅱ'); put("ᅳᅵ", 'ᅴ');
            put("ᆨᆺ", 'ᆪ'); put("ᆫᇂ", 'ᆭ'); put("ᆫᆽ", 'ᆬ'); put("ᆯᆨ", 'ᆰ'); put("ᆯᆷ", 'ᆱ'); put("ᆯᆸ", 'ᆲ'); put("ᆯᆺ", 'ᆳ'); put("ᆯᇀ", 'ᆴ'); put("ᆯᇁ", 'ᆵ'); put("ᆯᇂ", 'ᆶ');
        }};
        Converter<String, String> converter = new Sequential<>(new Sequential<>(new Sequential<>(new Sequential<>(
                new MultiTap(layout),
                new Combination(additions)),
                new Han2()),
                new Combination(combinations)),
                new Normalize("NFC")
        );
        assertEquals("나랏글", converter.convert("23437104"));
    }
}
