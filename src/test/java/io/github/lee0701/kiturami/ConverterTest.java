package io.github.lee0701.kiturami;

import static org.junit.Assert.*;

import io.github.lee0701.kiturami.converter.Normalize;
import org.junit.Test;

public class ConverterTest {
    @Test
    public void testNormalize() {
        Converter<String, String> converter = new Normalize("NFC");
        assertEquals("한글", converter.convert("한글"));
        assertEquals("정규화", converter.convert("정규화"));
    }
}
