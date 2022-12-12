package service.mpa;

import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import model.mpa.Mpa;
import org.springframework.stereotype.Service;
import storage.mpa.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaStorage mpaStorage;

    @Override
    public List<Mpa> getAll() {
        return mpaStorage.getAll();
    }

    @Override
    public Mpa getById(int id) {
        Mpa mpa = mpaStorage.getById(id);

        if (mpa == null) {
            throw new NotFoundException("mpa");
        }

        return mpa;
    }
}
