package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null in ArrayStorage.getIndex");
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void store(Resume resume, int index) {
        storage[size++] = resume;
    }
}