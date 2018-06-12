package fr.demo.metier.converter;

public interface Transformer<S, T> {

  T transform(final S source);

}
