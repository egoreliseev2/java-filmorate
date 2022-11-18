package storage;
import java.util.List;

public interface Storage<T>{
    List<T> getAll();
    T getById(int id);
    T create(T film);
    T update(int id, T film);
    T delete(int id);
}
