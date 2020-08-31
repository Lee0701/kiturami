package io.github.lee0701.kiturami.converter;

import java.util.LinkedList;
import java.util.List;

public class Parallel<In, Out> implements ListConverter<In, Out> {
    private final Converter<In, Out>[] converters;

    public Parallel(Converter<In, Out>... converters) {
        this.converters = converters;
    }

    @Override
    public List<Out> convert(In input) {
        List<Out> result = new LinkedList<>();
        for(Converter<In, Out> converter : converters) {
            result.add(converter.convert(input));
        }
        return result;
    }
}
