package by.gstu.project;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(
                new Car(1, "Toyota", "Camry", 2020, "Черный", 2500000, "A123BC77"),
                new Car(2, "BMW", "X5", 2018, "Белый", 4500000, "E456KH99"),
                new Car(3, "Toyota", "Corolla", 2015, "Синий", 1200000, "M789PT55"),
                new Car(4, "Lada", "Vesta", 2023, "Красный", 950000, "O111OO22"),
                new Car(5, "BMW", "X5", 2022, "Черный", 6800000, "K222KK33"),
                new Car(6, "Toyota", "RAV4", 2019, "Серебристый", 2800000, "T555TT44"),
                new Car(7, "Mercedes", "C-Class", 2021, "Серый", 3900000, "Y777YY66"),
                new Car(8, "Lada", "Granta", 2017, "Зеленый", 550000, "H999HH88")
        );

        CarService service = new CarService(cars);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Автосалон ===");
            System.out.println("1. Автомобили заданной марки");
            System.out.println("2. Автомобили модели, эксплуатируемые > n лет");
            System.out.println("3. Автомобили заданного года, цена > указанной");
            System.out.println("0. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) break;

            List<Car> result = null;
            switch (choice) {
                case 1:
                    System.out.print("Введите марку: ");
                    String brand = scanner.nextLine();
                    result = service.findByBrand(brand);
                    printResult(result, "Автомобили марки " + brand);
                    break;
                case 2:
                    System.out.print("Введите модель: ");
                    String model = scanner.nextLine();
                    System.out.print("Введите количество лет: ");
                    int years = scanner.nextInt();
                    result = service.findByModelAndYears(model, years);
                    printResult(result, "Автомобили модели " + model + " старше " + years + " лет");
                    break;
                case 3:
                    System.out.print("Введите год: ");
                    int year = scanner.nextInt();
                    System.out.print("Введите минимальную цену: ");
                    double price = scanner.nextDouble();
                    result = service.findByYearAndPrice(year, price);
                    printResult(result, "Автомобили " + year + " года дороже " + price);
                    break;
            }
        }
    }

    private static void printResult(List<Car> cars, String title) {
        System.out.println("\n" + title + ":");
        if (cars.isEmpty()) {
            System.out.println("Ничего не найдено.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }
}