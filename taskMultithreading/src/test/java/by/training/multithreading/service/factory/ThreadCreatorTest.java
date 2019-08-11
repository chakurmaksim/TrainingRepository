package by.training.multithreading.service.factory;

import by.training.multithreading.bean.exception.CustomThreadException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.testng.Assert.assertEquals;

public class ThreadCreatorTest {
    ThreadCreator creator;
    List<String> initialList;
    List<String> expectedNames;
    List<Integer> expectedPriorities;

    @BeforeMethod
    public void setUp() {
        creator = ThreadCreator.getThreadCreator();
        initialList = new ArrayList<>(Arrays.asList(new String[]{
                "{\"Thread[Thread-1,1,main]\":11}",
                "{\"Thread[Thread-2,5,main]\":22}",
                "{\"Thread[Thread-3,7,main]\":33}",
                "{\"Thread[Thread-4,10,main]\":44}",
        }));
        expectedNames = new LinkedList<>(Arrays.asList(new String[]{
                "Thread-1",
                "Thread-2",
                "Thread-3",
                "Thread-4",
        }));
        expectedPriorities = new LinkedList<>(Arrays.asList(new Integer[]{
                1,
                5,
                7,
                10
        }));
    }

    @AfterMethod
    public void tearDown() {
        creator = null;
        initialList = null;
        expectedNames = null;
        expectedPriorities = null;
    }

    @DataProvider(name = "negativeDataForCreateThreads")
    public Object[] createNegativeDataFor_CreateMatrixFromString() {
        List<String> baseList = new ArrayList<>(Arrays.asList(new String[]{
                "{\"Thread[Thread-1,1,main]\":11}",
                "{\"Thread[Thread-2,5,main]\":22}",
                "{\"Thread[Thread-3,7,main]\":33}",
                "{\"Thread[Thread-4,10,main]\":44}",
        }));
        List<String> l1 = new ArrayList<>(baseList);
        l1.remove(0);
        List<String> l2 = new ArrayList<>(baseList);
        l2.set(0, "\"Thread[Thread-1,1,main]\":11}");
        List<String> l3 = new ArrayList<>(baseList);
        l3.set(0, "{\"Thread Thread-1,1,main]\":11}");
        List<String> l4 = new ArrayList<>(baseList);
        l4.set(0, "{\"Thread[Thread-1,1,main]\"11}");
        List<String> l5 = new ArrayList<>(baseList);
        l5.set(0, "{\"Thread[Thread-1 1,main]\":11}");
        List<String> l6 = new ArrayList<>(baseList);
        l6.set(1, "{\"Thread[Thread-1,5,main]\":22}");
        List<String> l7 = new ArrayList<>(baseList);
        l7.set(1, "{\"Thread[Thread-2,5,main]\":11}");
        List<String> l8 = new ArrayList<>(baseList);
        l8.set(1, "{\"Thread[Thread-2,11,main]\":22}");
        return new Object[]{l1, l2, l3, l4, l5, l6, l7, l8};
    }

    @Test(description = "Positive scenario of creating threads from list of strings")
    public void testCreateThreadsForFillConcurrently()
            throws CustomThreadException {
        List<Thread> resultList = creator.createThreadsForFillConcurrently(
                initialList);
        List<String> actualNames = new LinkedList<>();
        List<Integer> actualPriorities = new LinkedList<>();
        for (Thread th : resultList) {
            actualNames.add(th.getName());
            actualPriorities.add(th.getPriority());
        }
        assertEquals(actualNames, expectedNames);
        assertEquals(actualPriorities, expectedPriorities);
    }

    @Test(description = "Positive scenario of creating threads from list of strings")
    public void testCreateThreadsForFillUsedAtomic()
            throws CustomThreadException {
        List<Thread> resultList = creator.createThreadsForFillUsedAtomic(
                initialList);
        List<String> actualNames = new LinkedList<>();
        List<Integer> actualPriorities = new LinkedList<>();
        for (Thread th : resultList) {
            actualNames.add(th.getName());
            actualPriorities.add(th.getPriority());
        }
        assertEquals(actualNames, expectedNames);
        assertEquals(actualPriorities, expectedPriorities);
    }

    @Test(description = "Confirm exceptions thrown when creating a threads",
            dataProvider = "negativeDataForCreateThreads",
            expectedExceptions = CustomThreadException.class)
    public void testCreateThreadsForFillConcurrently_CheckThrownException(
            List<String> list) throws CustomThreadException {
        creator.createThreadsForFillConcurrently(list);
    }

    @Test(description = "Confirm exceptions thrown when creating a threads",
            dataProvider = "negativeDataForCreateThreads",
            expectedExceptions = CustomThreadException.class)
    public void testCreateThreadsForFillUsedAtomic_CheckThrownException(
            List<String> list) throws CustomThreadException {
        creator.createThreadsForFillUsedAtomic(list);
    }
}