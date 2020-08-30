package io.github.lee0701.kiturami.converter.string;

import io.github.lee0701.kiturami.Converter;

import java.util.List;
import java.util.Map;

public class MultiTap implements Converter<String, String> {
    private final Map<Character, String> table;

    public MultiTap(Map<Character, String> table) {
        this.table = table;
    }

    @Override
    public String convert(String input) {
        char[] chars = input.toCharArray();
        StringBuilder result = new StringBuilder();
        char last = 0;
        int index = 0;
        for(char c : chars) {
            if(table.containsKey(c)) {
                String output = table.get(c);
                if(last != c) index = 0;
                else result.deleteCharAt(result.length() - 1);
                result.append(output.charAt(index));
                if(++index >= output.length()) index = 0;
            } else result.append(c);
            last = c;
        }
        return result.toString();
    }
}
