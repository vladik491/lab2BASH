package by.gstu.project;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private List<Car> cars;

    public CarService(List<Car> cars) {
        this.cars = cars;
    }

    public List<Car> findByBrand(String brand) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(brand)) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Car> findByModelAndYears(String model, int years) {
        List<Car> result = new ArrayList<>();
        int currentYear = 2025;
        for (Car car : cars) {
            if (car.getModel().equalsIgnoreCase(model) && (currentYear - car.getYear()) > years) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Car> findByYearAndPrice(int year, double minPrice) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getYear() == year && car.getPrice() > minPrice) {
                result.add(car);
            }
        }
        return result;
    }
}