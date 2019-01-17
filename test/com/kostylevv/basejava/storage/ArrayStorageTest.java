package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Test;

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

    @Test
    public void getIndexTest() {
        storage.clear();
        storage.save(new Resume("UUID1"));
        Assert.assertEquals(0, storage.getIndex("UUID1"));
        Assert.assertEquals(-1, storage.getIndex("UUID2"));
    }

    @Test(expected = NullPointerException.class)
    public void getNull() throws Exception {
        storage.getIndex(null);
    }

    @Test
    public void storeTest() {
        try {
            storage.store(new Resume("UUID500"), 500);
            Field[] fields = storage.getClass().getSuperclass().getDeclaredFields();
            fields[2].setAccessible(true);
            Resume[] arr = (Resume[]) fields[2].get(storage);
            Assert.assertEquals(new Resume("UUID500"), arr[storage.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testRemove() {
        storage.clear();
        storage.save(new Resume("UUID1"));
        storage.save(new Resume("UUID2"));
        storage.save(new Resume("UUID3"));
        try {
            Field[] fields = storage.getClass().getSuperclass().getDeclaredFields();
            fields[2].setAccessible(true);
            Resume[] arr = (Resume[]) fields[2].get(storage);
            storage.remove(0);
            Assert.assertNotEquals(new Resume("UUID1"), arr[0]);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}