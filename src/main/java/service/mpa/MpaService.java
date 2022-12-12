package service.mpa;

import model.mpa.Mpa;

import java.util.List;

public interface MpaService {
    List<Mpa> getAll();
    Mpa getById(int id);
}
