package service;

import model.StudentTC;

public class ValidationServiceTC {

    public static boolean isValid(StudentTC s) {

        if (s.getScore() < 0 || s.getScore() > 100) {
            return false;
        }

        if (s.getName() == null || s.getName().isEmpty()) {
            return false;
        }

        return true;
    }
}
