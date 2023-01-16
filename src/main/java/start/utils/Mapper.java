package start.utils;

public interface Mapper<F, T> {
    T mapFrom(F object);
}
