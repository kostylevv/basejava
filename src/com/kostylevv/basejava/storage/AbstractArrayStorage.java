package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    private static final int MAX_STORAGE_SIZE = 10_000;

    protected Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    int size = 0;

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
        if (resume != null) {
            if (size < MAX_STORAGE_SIZE) {
                store(resume);
            } else {
                System.out.println("DB overflow ( > " + MAX_STORAGE_SIZE + ")");
            }
        } else {
            System.out.println("Resume is Null");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("DB does not contain Resume with uuid " + uuid);
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        if (resume != null) {
            int index = getIndex(resume.getUuid());
            if (index >= 0) {
                storage[index] = resume;
            } else {
                System.out.println("DB does not contain Resume with uuid " + resume.getUuid());
            }
        } else {
            System.out.println("Resume is Null");
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void store(Resume resume);
}