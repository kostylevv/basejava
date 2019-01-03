import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    private static final int MAX_STORAGE_SIZE = 10_000;
    private int size = 0;

    int size() {
        return size;
    }

    void update(Resume resume) {
        if (resume != null) {
            int index = getIndex(resume.getUuid());
            if (index >= 0) {
                storage[index] = resume;
            } else {
                System.out.println("DB does not contain Resume with uuid " + resume.getUuid());
            }
        } else {
            System.out.println("Resume is Null");
        }
    }

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        if (resume != null) {
            if (size <= MAX_STORAGE_SIZE) {
                int index = getIndex(resume.getUuid());
                if (index == -1) {
                    storage[size++] = resume;
                } else {
                    System.out.println("DB already contains Resume with uuid " + resume.getUuid());
                }
            } else {
                System.out.println("DB overflow ( > " + MAX_STORAGE_SIZE + ")");
            }
        } else {
            System.out.println("Resume is Null");
        }
    }

    Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("DB does not contain Resume: " + uuid);
        return null;
    }

    void delete(String uuid) {
        int pos = getIndex(uuid);
        if (pos >= 0) {
            storage[pos] = null;
            System.arraycopy(storage, 0, storage, 0, pos);
            System.arraycopy(storage, pos + 1, storage, pos, size - 1 - pos);
            size = size - 1;
        } else {
            System.out.println("DB does not contain Resume: " + uuid);
        }
    }

    Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }

    private int getIndex(String uuid) {
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