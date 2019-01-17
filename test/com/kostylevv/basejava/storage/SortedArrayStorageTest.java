package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Test;

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

    @Test
    public void getIndex() {
        storage.clear();
        storage.save(new Resume("UUID1"));
        int index = storage.getIndex("UUID1");
        Assert.assertEquals(0, index);
        index = storage.getIndex("UUID2");
        Assert.assertTrue(index < 0);
    }

    @Test
    public void store() {
        storage.clear();
        storage.save(new Resume("C"));
        storage.save(new Resume("B"));
        storage.save(new Resume("A"));
        storage.save(new Resume("E"));
        storage.save(new Resume("D"));

        try {
            Field[] fields = storage.getClass().getSuperclass().getDeclaredFields();
            fields[2].setAccessible(true);
            Resume[] arr = (Resume[]) fields[2].get(storage);
            Assert.assertEquals(new Resume("A"), arr[0]);
            Assert.assertEquals(new Resume("E"), arr[4]);
            Assert.assertEquals(new Resume("D"), arr[3]);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void remove() {
        storage.clear();
        storage.save(new Resume("UUID1"));
        storage.save(new Resume("UUID2"));
        storage.save(new Resume("UUID3"));
        storage.save(new Resume("UUID4"));

        try {
            Field[] fields = storage.getClass().getSuperclass().getDeclaredFields();
            fields[2].setAccessible(true);
            Resume[] arr = (Resume[]) fields[2].get(storage);
            storage.remove(0);
            Assert.assertEquals(new Resume("UUID2"), arr[0]);
            storage.remove(3);
            Assert.assertEquals(new Resume("UUID3"), arr[1]);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}