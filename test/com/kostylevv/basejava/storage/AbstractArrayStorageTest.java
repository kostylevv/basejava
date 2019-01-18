package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.exception.ExistStorageException;
import com.kostylevv.basejava.exception.NotExistStorageException;
import com.kostylevv.basejava.exception.StorageException;
import com.kostylevv.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "UUID1";
    private static final String UUID_2 = "UUID2";
    private static final String UUID_3 = "UUID3";
    private static final String UUID_4 = "UUID4";
    private static final String DUMMY_ID = "dummy";

    private static final Resume RESUME1 = new Resume(UUID_1);
    private static final Resume RESUME2 = new Resume(UUID_2);
    private static final Resume RESUME3 = new Resume(UUID_3);
    private static final Resume RESUME4 = new Resume(UUID_4);
    private static final Resume DUMMY = new Resume(DUMMY_ID);
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME1, storage.get(RESUME1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(DUMMY.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(DUMMY);
    }

    @Test
    public void getAll() {
        Resume[] expected = {RESUME1, RESUME2, RESUME3};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void save() {
        storage.save(RESUME4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME4, storage.get(RESUME4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME2);
    }

    @Test
    public void update() {
        storage.update(RESUME3);
        Assert.assertEquals(3, storage.size());
        Assert.assertSame(RESUME3, storage.get(RESUME3.getUuid()));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(DUMMY.getUuid());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            Field[] fields = storage.getClass().getSuperclass().getDeclaredFields();
            fields[0].setAccessible(true);
            int STORAGE_CAPACITY = fields[0].getInt(storage);
            for (int i = 0; i < STORAGE_CAPACITY; i++) {
                storage.save(new Resume(i + "x"));
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        storage.save(new Resume("x"));
    }
}