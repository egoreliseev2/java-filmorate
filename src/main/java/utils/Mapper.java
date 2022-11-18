package utils;

public interface Mapper<F, T> {
    T mapFrom(F object);
}
