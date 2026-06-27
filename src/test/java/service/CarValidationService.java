package service;

import model.Car;

public class CarValidationService {

    public static boolean isValid(Car c) {

        if (c.getPrice() <= 0) return false;

        if (c.getName() == null || c.getName().isEmpty()) return false;

        return true;
    }
}