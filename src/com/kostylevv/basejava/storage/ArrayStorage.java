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
    public void store(Resume resume, int index) {
        Objects.requireNonNull(resume, "Resume cannot be null in ArrayStorage.store");
        if (index == -1) {
            storage[size++] = resume;
        } else {
            System.out.println("DB already contains Resume with uuid: " + resume.getUuid());
        }
    }

    @Override
    public void remove(Resume resume, int index) {
        Objects.requireNonNull(resume, "Resume cannot be null in ArrayStorage.remove");
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("DB does not contain Resume with uuid: " + resume.getUuid());
        }
    }
}