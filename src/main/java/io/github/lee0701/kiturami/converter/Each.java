package io.github.lee0701.kiturami.converter;

import java.util.LinkedList;
import java.util.List;

public class Each<In, Out> implements Converter<List<In>, List<Out>> {
    private final Converter<In, Out> converter;

    public Each(Converter<In, Out> converter) {
        this.converter = converter;
    }

    @Override
    public List<Out> convert(List<In> inputs) {
        List<Out> result = new LinkedList<>();
        for(In input : inputs) {
            result.add(converter.convert(input));
        }
        return result;
    }
}
