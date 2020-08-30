package io.github.lee0701.kiturami;

import static org.junit.Assert.*;

import io.github.lee0701.kiturami.converter.Normalize;
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
}
