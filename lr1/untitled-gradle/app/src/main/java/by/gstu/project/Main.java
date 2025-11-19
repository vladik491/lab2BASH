package by.gstu.project;

import by.gstu.project.Car;
import by.gstu.project.CarService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.jar.Manifest;

public class Main {

    private static String environment = "НЕИЗВЕСТНО";
    private static String welcomeMessage = "";
    private static int initialCarsCount = 3;

    static {
        try (InputStream is = Main.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {
            if (is != null) {
                Manifest manifest = new Manifest(is);
                var attrs = manifest.getMainAttributes();

                String env = attrs.getValue("app-environment");
                if (env != null && !env.isEmpty()) environment = env;

                String welcome = attrs.getValue("app-welcome-message");
                if (welcome != null && !welcome.isEmpty()) welcomeMessage = welcome;

                String count = attrs.getValue("app-initial-cars");
                if (count != null && !count.isEmpty()) {
                    initialCarsCount = Integer.parseInt(count.trim());
                }
            }
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        List<Car> cars = new ArrayList<>();
        initializeTestData(cars);

        CarService service = new CarService(cars);
        Scanner scanner = new Scanner(System.in, "UTF-8");

        System.out.println("=== Автосалон ===");
        System.out.println("Режим: " + environment);
        if (!welcomeMessage.isEmpty()) {
            System.out.println(welcomeMessage);
        }
        System.out.println("Загружено " + cars.size() + " тестовых автомобилей.\n");

        while (true) {
            System.out.println("1. Автомобили заданной марки");
            System.out.println("2. Автомобили модели, эксплуатируемые > n лет");
            System.out.println("3. Автомобили заданного года, цена > указанной");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Введите корректное число!\n");
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("До свидания!");
                break;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Введите марку: ");
                    String brand = scanner.nextLine();
                    printResult(service.findByBrand(brand), "Автомобили марки " + brand);
                }
                case 2 -> {
                    System.out.print("Введите модель: ");
                    String model = scanner.nextLine();
                    System.out.print("Введите количество лет: ");
                    int years = scanner.nextInt();
                    scanner.nextLine();
                    printResult(service.findByModelAndYears(model, years),
                            "Автомобили модели " + model + " старше " + years + " лет");
                }
                case 3 -> {
                    System.out.print("Введите год: ");
                    int year = scanner.nextInt();
                    System.out.print("Введите минимальную цену: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    printResult(service.findByYearAndPrice(year, price),
                            "Автомобили " + year + " года дороже " + price);
                }
                default -> System.out.println("Неверный выбор!");
            }
            System.out.println();
        }
    }

    private static void initializeTestData(List<Car> cars) {
        for (int i = 1; i <= initialCarsCount; i++) {
            cars.add(new Car(
                    i,
                    i % 2 == 0 ? "Toyota" : "BMW",
                    i % 2 == 0 ? "Camry" : "X5",
                    2020 + (i % 5),
                    switch (i % 4) {
                        case 0 -> "Черный";
                        case 1 -> "Белый";
                        case 2 -> "Синий";
                        default -> "Красный";
                    },
                    1_500_000 + i * 350_000,
                    String.format("A%03dBB77", i)
            ));
        }
    }

    private static void printResult(List<Car> cars, String title) {
        System.out.println("\n" + title + ":");
        if (cars.isEmpty()) {
            System.out.println("Ничего не найдено.");
        } else {
            cars.forEach(car -> System.out.println(car));
        }
    }
}