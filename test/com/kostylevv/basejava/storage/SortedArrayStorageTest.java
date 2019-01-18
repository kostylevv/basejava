package com.kostylevv.basejava.storage;

import java.lang.reflect.Field;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private SortedArrayStorage storage;

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
        try {
            Field field = this.getClass().getSuperclass().getDeclaredField("storage");
            field.setAccessible(true);
            storage = (SortedArrayStorage) field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}