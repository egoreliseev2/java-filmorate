package start.storage.mpa;

import start.model.mpa.Mpa;

import java.util.List;

public interface MpaStorage {
    List<Mpa> getAll();
    Mpa getById(int id);
}
