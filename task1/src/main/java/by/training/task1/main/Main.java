package by.training.task1.main;

import by.training.task1.bean.entity.Salad;
import by.training.task1.controller.Chef;

/**
 * The Main class for starting programme.
 */
final class Main {
    /**
     * An object of this class is not created.
     */
    private Main() {

    }

    /**
     * The whole programme starts with the main method.
     *
     * @param args input parameters
     */
    public static void main(final String[] args) {
        Chef chef = new Chef();

        for (Salad salad : chef.createSaladList()) {
            System.out.println(salad);
        }
        System.out.println("------------------------------");

        for (Salad salad : chef.findSaladById(4)) {
            System.out.println(salad);
        }
        System.out.println("------------------------------");

        for (Salad salad : chef.findSaladByName("Vegetable salad with spinach")) {
            System.out.println(salad);
        }
        System.out.println("------------------------------");

        for (Salad salad : chef.sortSaladsByWeight()) {
            System.out.println(salad);
        }
        System.out.println("------------------------------");
        for (Salad salad : chef.sortSaladByKcalPer100g()) {
            System.out.println(salad);
        }
        System.out.println("------------------------------");
    }
}
