package pop.lesson03;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.testng.Assert.*;
import static pop.lesson03.ArrayOps.*;

public class ArrayOpsTest {

    // fixtures
    @BeforeTest
    public void setup() {
        System.out.println("Стартовал юнит-тесты");
    }


    @AfterTest
    public void tearDown() {
        System.out.println("Выполнил юнит-тесты");
    }

    // validator
    // проверка исключения с помощью аннотации TestNG
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyListInput() {
        validateArrayList(new ArrayList<>(Arrays.asList()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullInput() {
        validateArrayList(null);
    }

    // max
    @Test
    public void testGetMaxValueEmpty() {
        // проверка исключения с помощью try-catch
        // неэффективно, приходится дублировать текст исключения
        try {
            getMaxValue(new ArrayList<>(Arrays.asList()));
        } catch (IllegalArgumentException err) {
            assertEquals(err.getMessage(), "Список должен быть непустым");
        }
        try {
            getMaxValue(null);
        } catch (NullPointerException err) {
            assertEquals(err.getMessage(), "Аргумент должен быть не null");
        }
    }

    @Test
    public void testGetMaxValueIntsOnly() {
        ArrayList<Integer> listOfInts = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(getMaxValue(listOfInts), "5.00");
    }

    @Test
    public void testGetMaxValueDblOnly() {
        ArrayList<Double> listOfDbls = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        assertEquals(getMaxValue(listOfDbls), "5.00");
    }

    @Test
    public void testGetMaxValueDblsInts() {
        ArrayList<Object> listDblsInts = new ArrayList<>(Arrays.asList(5.0, 4, 3, 2.0, 1.0));
        assertEquals(getMaxValue(listDblsInts), "5.00");
    }

    @Test
    public void testGetMaxValueDblsIntsOthers() {
        ArrayList<Object> listDblsIntsOthers = new ArrayList<>(Arrays.asList(5.0, "badItem", 4, 3, 2.0, 1.0, false));
        assertEquals(getMaxValue(listDblsIntsOthers), "5.00");
    }

    // avg
    // проверка исключения с помощью аннотации TestNG
    @Test(expectedExceptions = NullPointerException.class)
    public void testGetAvgValueEmpty() {
        getAvgValue(null);
    }

    @Test
    public void testGetAvgValueIntsOnly() {
        ArrayList<Integer> listOfInts = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(getAvgValue(listOfInts), "3.00");
    }

    @Test
    public void testGetAvgValueDblsOnly() {
        ArrayList<Double> listOfDbls = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        assertEquals(getAvgValue(listOfDbls), "3.00");
    }

    @Test
    public void testGetAvgValueDblsInts() {
        ArrayList<Object> listDblsInts = new ArrayList<>(Arrays.asList(5.0, 4, 3, 2.0, 1.0));
        assertEquals(getAvgValue(listDblsInts), "3.00");
    }

    @Test
    public void testGetAvgValueDblsIntsOthers() {
        ArrayList<Object> listDblsIntsOthers = new ArrayList<>(Arrays.asList(5.0, "badItem", 4, 3, 2.0, 1.0, false));
        assertEquals(getAvgValue(listDblsIntsOthers), "3.00");
    }
}