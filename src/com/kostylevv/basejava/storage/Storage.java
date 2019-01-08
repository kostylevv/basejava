package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;

public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();

    int getMaxStorageSize();

    Resume[] getStorage();
}
