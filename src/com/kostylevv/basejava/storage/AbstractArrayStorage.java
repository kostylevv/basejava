package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_STORAGE_SIZE = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[MAX_STORAGE_SIZE];

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        Objects.requireNonNull(resume, "Resume cannot be null in AbstractArrayStorage.save");
        if (size < MAX_STORAGE_SIZE) {
            int index = getIndex(resume.getUuid());
            if (index < 0) {
                store(resume, index);
            } else {
                System.out.println("DB already contains Resume with uuid " + resume.getUuid());
            }
        } else {
            System.out.println("DB overflow ( > " + MAX_STORAGE_SIZE + ")");
        }
    }

    public Resume get(String uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null in method AbstractArrayStorage.get");
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("DB does not contain Resume with uuid " + uuid);
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        Objects.requireNonNull(resume, "Resume cannot be null in method AbstractArrayStorage.update");
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("DB does not contain Resume with uuid " + resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            remove(storage[index], index);
        } else {
            System.out.println("DB does not contain Resume with uuid: " + uuid);
        }
    }

    public int getMaxStorageSize() {
        return MAX_STORAGE_SIZE;
    }

    public Resume[] getStorage() {
        return storage;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void store(Resume resume, int index);

    protected abstract void remove(Resume resume, int index);
}