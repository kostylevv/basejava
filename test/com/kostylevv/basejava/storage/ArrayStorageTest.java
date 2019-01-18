package com.kostylevv.basejava.storage;

import java.lang.reflect.Field;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private ArrayStorage storage;

    public ArrayStorageTest() {
        super(new ArrayStorage());
        try {
            Field field = this.getClass().getSuperclass().getDeclaredField("storage");
            field.setAccessible(true);
            storage = (ArrayStorage) field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}