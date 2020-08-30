package io.github.lee0701.kiturami.converter.string;

import io.github.lee0701.kiturami.Converter;

import java.util.Map;

public class ReplaceChar implements Converter<String, String> {
    private final Map<Character, Character> table;

    public ReplaceChar(Map<Character, Character> table) {
        this.table = table;
    }

    @Override
    public String convert(String input) {
        char[] chars = input.toCharArray();
        for(int i = 0 ; i < chars.length ; i++) {
            char c = chars[i];
            if(table.containsKey(c)) chars[i] = table.get(c);
        }
        return new String(chars);
    }
}
