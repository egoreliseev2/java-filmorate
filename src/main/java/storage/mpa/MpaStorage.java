package storage.mpa;

import model.mpa.Mpa;

import java.util.List;

public interface MpaStorage {
    List<Mpa> getAll();
    Mpa getById(int id);
}
