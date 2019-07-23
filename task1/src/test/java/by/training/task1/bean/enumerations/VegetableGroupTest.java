package by.training.task1.bean.enumerations;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class VegetableGroupTest {

    @DataProvider(name = "positiveDataForGetGroupName")
    public Object[][] createDataFor_TestGetGroupName() {
        return new Object[][]{
                {VegetableGroup.BEAN.getGroupName(), "bean vegetable"},
                {VegetableGroup.BULBOUS.getGroupName(), "bulbous vegetable"},
                {VegetableGroup.FRUIT.getGroupName(), "fruit vegetable"},
                {VegetableGroup.LEAF.getGroupName(), "leafy vegetable"},
                {VegetableGroup.ROOT.getGroupName(), "root vegetable"}
        };
    }

    @Test(description = "Validate get group name",
            dataProvider = "positiveDataForGetGroupName")
    public void testGetGroupName(String actual, String expected) {
        assertEquals(actual, expected);
    }
}