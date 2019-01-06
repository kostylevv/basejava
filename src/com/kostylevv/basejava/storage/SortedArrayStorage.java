package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    public void store(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            index = -index - 1;
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = resume;
            size++;
        } else {
            System.out.println("DB already contains Resume with uuid: " + resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            storage[size] = null;
            size--;
        } else {
            System.out.println("DB does not contain Resume with uuid: " + uuid);
        }


    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}