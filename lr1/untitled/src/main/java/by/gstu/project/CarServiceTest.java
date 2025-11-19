package by.gstu.project;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarServiceTest {
    private final List<Car> cars = Arrays.asList(
            new Car(1, "Toyota", "Camry", 2020, "Черный", 2500000, "A123BC77"),
            new Car(2, "BMW", "X5", 2018, "Белый", 4500000, "E456KH99")
    );
    private final CarService service = new CarService(cars);

    @Test
    public void testFindByBrand() {
        assertEquals(1, service.findByBrand("Toyota").size());
    }

    @Test
    public void testFindByModelAndYears() {
        assertEquals(1, service.findByModelAndYears("X5", 6).size());
    }

    @Test
    public void testFindByYearAndPrice() {
        assertEquals(1, service.findByYearAndPrice(2020, 2000000).size());
    }
}