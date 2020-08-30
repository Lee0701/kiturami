package io.github.lee0701.kiturami.converter.hangul;

import io.github.lee0701.kiturami.Converter;

import java.util.Map;

public class Combination implements Converter<String, String> {
    private final Map<String, Character> table;

    public Combination(Map<String, Character> table) {
        this.table = table;
    }

    @Override
    public String convert(String input) {
        char[] chars = input.toCharArray();
        StringBuilder result = new StringBuilder();
        for(int i = 0 ; i < chars.length ; i++) {
            if(i == 0) continue;
            char c = chars[i];
            char last = result.charAt(result.length() - 1);
            String key = new String(new char[] {last, c});
            if(table.containsKey(key)) {
                result.deleteCharAt(result.length() - 1);
                result.append(table.get(key));
            } else result.append(c);
        }
        return null;
    }
}
