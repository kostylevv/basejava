package com.kostylevv.basejava;

import com.kostylevv.basejava.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        try {
            System.out.println(r.getClass().getSuperclass().getDeclaredMethod("toString").invoke(r));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
