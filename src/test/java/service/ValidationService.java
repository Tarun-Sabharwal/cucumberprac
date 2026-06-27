package service;

import model.Student;

public class ValidationService {

    public static boolean isValid(Student s) {

        //  Rule 1: score must be between 0 and 100
        if (s.getScore() < 0 || s.getScore() > 100) {
            return false;
        }

        //  Rule 2: name must not be empty
        if (s.getName() == null || s.getName().isEmpty()) {
            return false;
        }

        return true;
    }
}