package io.github.lee0701.kiturami;

public interface Converter<In, Out> {
    Out convert(In input);
}
