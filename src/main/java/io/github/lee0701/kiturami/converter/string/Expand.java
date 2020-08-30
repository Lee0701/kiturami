package io.github.lee0701.kiturami.converter.string;

import io.github.lee0701.kiturami.converter.Converter;

import java.util.Map;

public class Expand implements Converter<String, String> {
    private final Map<Character, String> table;

    public Expand(Map<Character, String> table) {
        this.table = table;
    }

    @Override
    public String convert(String input) {
        char[] chars = input.toCharArray();
        StringBuilder result = new StringBuilder();
        for(char c : chars) {
            if(table.containsKey(c)) result.append(table.get(c));
            else result.append(c);
        }
        return result.toString();
    }
}
