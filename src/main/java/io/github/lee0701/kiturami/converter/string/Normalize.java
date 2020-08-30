package io.github.lee0701.kiturami.converter.string;

import io.github.lee0701.kiturami.Converter;

import java.text.Normalizer;

public class Normalize implements Converter<String, String> {
    private Normalizer.Form form;

    public Normalize(Normalizer.Form form) {
        this.form = form;
    }

    public Normalize(String form) {
        switch(form) {
            case "NFD":
                this.form = Normalizer.Form.NFD;
                break;
            case "NFC":
                this.form = Normalizer.Form.NFC;
                break;
            case "NFKD":
                this.form = Normalizer.Form.NFKD;
                break;
            case "NFKC":
                this.form = Normalizer.Form.NFKC;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String convert(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFC);
    }
}
