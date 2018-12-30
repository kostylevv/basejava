import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private  int size = 0;

    int size() {
        return size;
    }

    void update(Resume r) {
        if (r != null && contains(r.uuid)) {
            int pos = getPos(r.uuid);
            storage[pos] = r;
        } else {
            System.out.println("DB does not contain Resume: " + r.uuid);
        }
    }

    void update(String uuid) {
        Resume r = get(uuid);
        if (!contains(uuid)) {
            System.out.println("DB does not contain Resume: " + uuid);
        } else {
            update(r);
        }
    }

    /**
     * Clear storage
     */
    void clear() {
        Arrays.fill(storage, 0,size,null);
        size = 0;
    }

    /**
     * Save Resume to storage. Resume will be stored only
     * if it is not exist in storage and storage space is available.
     * @param r Resume object to save
     */
    void save(Resume r) {
        if (r != null) {
           if (size  <= 10000) {
               if (!contains(r.uuid)) {
                   storage[size++] = r;
               } else {
                   System.out.println("DB already contains Resume: " + r.toString());
               }
           } else {
               System.out.println("DB overflow (>10000)");
           }
        }
    }

    /**
     * Check if storage contains element with provided uuid
     * @param uuid to search for
     * @return true if exists, false otherwise
     */
    boolean contains(String uuid) {
        return getPos(uuid) >= 0;
    }

    /**
     * Get index of Resume object with uuid provided in Storage
     * @param uuid to search for
     * @return index of Resume if present in storage, -1 otherwise
     */
    private int getPos(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Return Resume from storage
     * @param uuid of Resume to search for
     * @return Resume from storage if it exists, null otherwise
     */
    Resume get(String uuid) {
       int pos = getPos(uuid);
       if (pos >= 0) {
           return storage[pos];
       }
       System.out.println("DB does not contain Resume: " + uuid);
       return null;
    }

    /**
     * Delete Resume from storage
     * @param uuid of Resume to delete
     */
    void delete(String uuid) {
        int pos = getPos(uuid);
        if (pos >= 0) {
            shift(pos);
        } else {
            System.out.println("DB does not contain Resume: " + uuid);
        }
    }

    /**
     * Shift elements to the left.
     * @param from - index of element from which shift should be performed
     */
    private void shift(int from){
        if (from < size && from >= 0) {
            for (int i = from; i <= size; i++) {
                storage[i] = storage[i+1];
            }
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }
}
