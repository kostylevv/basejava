/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    /**
     * Clear storage
     */
    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    /**
     * Check if storage contains element with provided uuid
     * @param uuid to search for
     * @return true if exists, false otherwise
     */
    boolean contains(String uuid) {
        if (uuid != null) {
            if (getPos(uuid) >= 0) return true;
        }
        return false;
    }

    /**
     * Save Resume to storage. Resume will be stored only
     * if it is not exist in storage and storage space is available.
     * @param r Resume object to save
     * @throws IllegalArgumentException if Storage already contains Resume
     * @throws IndexOutOfBoundsException if it is not enough storage space
     */
    void save(Resume r) throws IllegalArgumentException, IndexOutOfBoundsException{
        if (r != null) {
           if (size + 1 < 10000) {
               if (!contains(r.uuid)) {
                   storage[size++] = r;
               } else {
                   throw new IllegalArgumentException("DB already contains Resume: " + r.toString());
               }
           } else {
               throw new IndexOutOfBoundsException("DB overflow (>10000)");
           }
        }
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
        if (uuid != null) {
            int pos = getPos(uuid);
            if (pos >= 0) return storage[pos];
        }
        return null;
    }

    /**
     * Delete Resume from storage
     * @param uuid of Resume to delete
     */
    void delete(String uuid) {
        if (uuid != null) {
            int pos = getPos(uuid);
            if (pos >= 0) shift(pos);
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

    int size() {
        return size;
    }
    private  int size = 0;
}
