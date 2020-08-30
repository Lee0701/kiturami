package io.github.lee0701.kiturami.converter;

public interface Converter<In, Out> {
    Out convert(In input);

    default <Then> Sequential<In, Out, Then> then(Converter<Out, Then> another) {
        return new Sequential<>(this, another);
    }
}
