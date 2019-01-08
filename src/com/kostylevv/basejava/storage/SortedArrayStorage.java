package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void store(Resume resume, int index) {
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
    public void remove(Resume resume, int index) {
        Objects.requireNonNull(resume, "Resume cannot be null in ArrayStorage.remove");
        if (index > 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("DB does not contain Resume with uuid: " + resume.getUuid());
        }
    }
}
