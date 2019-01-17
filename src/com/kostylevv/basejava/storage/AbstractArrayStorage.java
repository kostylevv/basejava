package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.exception.ExistStorageException;
import com.kostylevv.basejava.exception.NotExistStorageException;
import com.kostylevv.basejava.exception.StorageException;
import com.kostylevv.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int CAPACITY = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[CAPACITY];

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        Objects.requireNonNull(resume, "Resume cannot be null in AbstractArrayStorage.save");
        if (size < CAPACITY) {
            int index = getIndex(resume.getUuid());
            if (index < 0) {
                store(resume, index);
                size++;
            } else {
                throw new ExistStorageException(resume.getUuid());
            }
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null in method AbstractArrayStorage.get");
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public void update(Resume resume) {
        Objects.requireNonNull(resume, "Resume cannot be null in method AbstractArrayStorage.update");
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            remove(index);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void store(Resume resume, int index);

    protected abstract void remove(int index);
}