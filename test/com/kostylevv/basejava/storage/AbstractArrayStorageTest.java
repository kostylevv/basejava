package com.kostylevv.basejava.storage;

import com.kostylevv.basejava.exception.NotExistStorageException;
import com.kostylevv.basejava.exception.StorageException;
import com.kostylevv.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(new Resume(UUID_4), storage.get(UUID_4));
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
        Assert.assertEquals(new Resume(UUID_3), storage.get(UUID_3));
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_3);
        storage.update(r);
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals(r, storage.get(UUID_3));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume r = new Resume("new resume to update");
        storage.update(r);
    }

    @Test
    public void capacityTest() {
        storage.clear();
        try {
            Field[] fields = storage.getClass().getSuperclass().getDeclaredFields();
            fields[0].setAccessible(true);
            int cap = fields[0].getInt(storage);
            for (int i = 0; i < cap; i++) {
                storage.save(new Resume(i + "x"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        try {
            storage.save(new Resume("x"));
        } catch (StorageException se) {
            //pass
        }
    }

    @Test
    public void realSizeTestInit() {
        storage.clear();
        try {
            Assert.assertEquals(getRealSize(), storage.size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void realSizeTestSave() {
        storage.clear();
        try {
            storage.save(new Resume(UUID_1));
            storage.save(new Resume(UUID_2));
            Assert.assertEquals(getRealSize(), storage.size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void realSizeTestUpdate() {
        storage.clear();
        try {
            storage.save(new Resume(UUID_1));
            storage.save(new Resume(UUID_2));
            storage.update(new Resume(UUID_1));
            Assert.assertEquals(getRealSize(), storage.size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void realSizeTestDelete() {
        storage.clear();
        try {
            storage.save(new Resume(UUID_1));
            storage.save(new Resume(UUID_2));
            storage.delete(UUID_1);
            Assert.assertEquals(getRealSize(), storage.size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    private int getRealSize() throws Exception {
        Field[] fields = storage.getClass().getSuperclass().getDeclaredFields();
        fields[2].setAccessible(true);
        Resume[] arr = (Resume[]) fields[2].get(storage);
        int realSize = 0;
        for (Resume resume : arr) {
            if (resume != null) {
                realSize++;
            }
        }
        return realSize;
    }
}