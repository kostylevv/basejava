package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void store(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            storage[size++] = resume;
        } else {
            System.out.println("DB already contains Resume with uuid " + resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("DB does not contain Resume with uuid: " + uuid);
        }
    }

    protected int getIndex(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }
}