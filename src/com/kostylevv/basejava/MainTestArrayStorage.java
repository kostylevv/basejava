package com.kostylevv.basejava;

import com.kostylevv.basejava.model.Resume;
import com.kostylevv.basejava.storage.ArrayStorage;
import com.kostylevv.basejava.storage.SortedArrayStorage;
import com.kostylevv.basejava.storage.Storage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage SORTED_STORAGE = new SortedArrayStorage();
    private static final Storage ARRAY_STORAGE = new ArrayStorage();
    private static final String[] ALPHABET = {"a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "x", "y", "z"};

    public static void main(String[] args) {
        System.out.println("Testing Sorted (SortedArrayStorage) and Unsorted storage (ArrayStorage)");
        System.out.println("Create 10 Resumes with uuid form L to B, add to both storages");
        for (int i = 10; i >= 1; i--) {
            Resume resume = new Resume(ALPHABET[i]);
            SORTED_STORAGE.save(resume);
            ARRAY_STORAGE.save(resume);
        }
        printOut();

        System.out.println("Add Resume with uuid N");
        Resume resumeN = new Resume(ALPHABET[12]);
        SORTED_STORAGE.save(resumeN);
        ARRAY_STORAGE.save(resumeN);
        printOut();

        System.out.println("Add Resume with uuid A");
        Resume resumeA = new Resume(ALPHABET[0]);
        SORTED_STORAGE.save(resumeA);
        ARRAY_STORAGE.save(resumeA);
        printOut();

        System.out.println("Add Resume with uuid B (which is already in storage)");
        Resume resumeB = new Resume(ALPHABET[1]);
        SORTED_STORAGE.save(resumeB);
        ARRAY_STORAGE.save(resumeB);
        printOut();

        System.out.println("Add Resume with uuid M");
        Resume resumeM = new Resume(ALPHABET[11]);
        SORTED_STORAGE.save(resumeM);
        ARRAY_STORAGE.save(resumeM);
        printOut();

        System.out.println("Get Resume with uuid C");
        System.out.println("Sorted: " + SORTED_STORAGE.get(ALPHABET[2]));
        System.out.println("Unsorted: " + ARRAY_STORAGE.get(ALPHABET[2]));
        printOut();

        System.out.println("Get Resume with uuid A");
        System.out.println("Sorted: " + SORTED_STORAGE.get(ALPHABET[0]));
        System.out.println("Unsorted: " + ARRAY_STORAGE.get(ALPHABET[0]));
        printOut();

        System.out.println("Get Resume with uuid X (which is not in storage)");
        System.out.println("Sorted: " + SORTED_STORAGE.get(ALPHABET[13]));
        System.out.println("Unsorted: " + ARRAY_STORAGE.get(ALPHABET[13]));
        printOut();

        System.out.println("Delete Resume with uuid C");
        SORTED_STORAGE.delete(ALPHABET[2]);
        ARRAY_STORAGE.delete(ALPHABET[2]);
        printOut();

        System.out.println("Delete Resume with uuid A");
        SORTED_STORAGE.delete(ALPHABET[0]);
        ARRAY_STORAGE.delete(ALPHABET[0]);
        printOut();

        System.out.println("Delete Resume with uuid X (which is not in storage)");
        SORTED_STORAGE.delete(ALPHABET[13]);
        ARRAY_STORAGE.delete(ALPHABET[13]);
        printOut();

        System.out.println("Clear both storages");
        SORTED_STORAGE.clear();
        ARRAY_STORAGE.clear();
        printOut();

        System.out.println("Try to add more than 10k elements");
        for (int i = 0; i <= 10000; i++) {
            Resume resume = new Resume(i + "x");
            SORTED_STORAGE.save(resume);
            ARRAY_STORAGE.save(resume);
        }
        printOut();

        System.out.println("Clear both storages");
        SORTED_STORAGE.clear();
        ARRAY_STORAGE.clear();
        printOut();

        System.out.println("Try to add exactly 10k elements");
        for (int i = 0; i < 10000; i++) {
            Resume resume = new Resume(i + "x");
            SORTED_STORAGE.save(resume);
            ARRAY_STORAGE.save(resume);
        }
        printOut();

        System.out.println("Clear both storages");
        SORTED_STORAGE.clear();
        ARRAY_STORAGE.clear();
        printAll(SORTED_STORAGE);
        printAll(ARRAY_STORAGE);
        System.out.println();

        System.out.println("Try to add null");
        SORTED_STORAGE.save(null);
        ARRAY_STORAGE.save(null);
        printOut();

        System.out.println("Try to add to Sorted only");
        Resume y = new Resume(ALPHABET[14]);
        SORTED_STORAGE.save(y);
        printOut();

        System.out.println("Try to add to Unsorted only");
        Resume z = new Resume(ALPHABET[15]);
        ARRAY_STORAGE.save(z);
        printOut();
    }

    private static void printOut() {
        printAll(SORTED_STORAGE);
        printAll(ARRAY_STORAGE);
        System.out.println("Sorted size = " + SORTED_STORAGE.size());
        System.out.println("Unsorted size = " + ARRAY_STORAGE.size());
        System.out.println("-----------------------------------------------------------");
    }

    private static void printAll(Storage storage) {
        System.out.println("\nGet All from instance of " + storage.getClass().getSimpleName());
        if (storage.size() > 0) {
            for (Resume r : storage.getAll()) {
                System.out.print(r + ";");
            }
            System.out.println();
        } else {
            System.out.println("Storage is empty");
        }
    }
}
