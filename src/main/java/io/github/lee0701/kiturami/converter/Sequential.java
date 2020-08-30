package io.github.lee0701.kiturami.converter;

public class Sequential<In, Mid, Out> implements Converter<In, Out> {
    private final Converter<In, Mid> first;
    private final Converter<Mid, Out> second;

    public Sequential(Converter<In, Mid> first, Converter<Mid, Out> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Out convert(In input) {
        return second.convert(first.convert(input));
    }

}
