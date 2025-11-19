package by.gstu.project.cli;

import by.gstu.project.Car;
import by.gstu.project.CarService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.jar.Manifest;

import org.fusesource.jansi.AnsiConsole;

public class App {

    private static String environment = "UNKNOWN";
    private static String welcomeMessage = "";
    private static int initialCarsCount = 3;

    static {
        try (InputStream is = App.class.getResourceAsStream("/META-INF/MANIFEST.MF")) {
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
        AnsiConsole.systemInstall();

        try {
            List<Car> cars = new ArrayList<>();
            initializeTestData(cars);

            CarService service = new CarService(cars);
            Scanner scanner = new Scanner(System.in, "UTF-8");

            System.out.println("=== Car Dealership ===");
            System.out.println("Mode: " + environment);
            if (!welcomeMessage.isEmpty()) {
                System.out.println(welcomeMessage);
            }
            System.out.println("Loaded " + cars.size() + " test vehicles.\n");

            while (true) {
                System.out.println("1. Cars of a specific brand");
                System.out.println("2. Cars of a specific model used for more than n years");
                System.out.println("3. Cars of a specific year with price higher than specified");
                System.out.println("0. Exit");
                System.out.print("Choice: ");

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Please enter a valid number!\n");
                    continue;
                }

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number!\n");
                    continue;
                }

                if (choice == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter brand: ");
                        String brand = scanner.nextLine().trim();
                        printResult(service.findByBrand(brand), "Cars of brand " + brand);
                    }
                    case 2 -> {
                        System.out.print("Enter model: ");
                        String model = scanner.nextLine().trim();
                        System.out.print("Enter number of years: ");
                        String yearsStr = scanner.nextLine().trim();
                        try {
                            int years = Integer.parseInt(yearsStr);
                            printResult(service.findByModelAndYears(model, years),
                                    "Cars of model " + model + " older than " + years + " years");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number of years!\n");
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter year: ");
                        String yearStr = scanner.nextLine().trim();
                        System.out.print("Enter minimum price: ");
                        String priceStr = scanner.nextLine().trim();
                        try {
                            int year = Integer.parseInt(yearStr);
                            double price = Double.parseDouble(priceStr);
                            printResult(service.findByYearAndPrice(year, price),
                                    "Cars from " + year + " priced above " + price);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input!\n");
                        }
                    }
                    default -> System.out.println("Invalid choice!");
                }
                System.out.println();
            }
            scanner.close();
        } finally {
            AnsiConsole.systemUninstall();
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
                        case 0 -> "Black";
                        case 1 -> "White";
                        case 2 -> "Blue";
                        default -> "Red";
                    },
                    1_500_000 + i * 350_000,
                    String.format("A%03dBB77", i)
            ));
        }
    }

    private static void printResult(List<Car> cars, String title) {
        System.out.println("\n" + title + ":");
        if (cars.isEmpty()) {
            System.out.println("Nothing found.");
        } else {
            cars.forEach(System.out::println);
        }
    }
}