package by.training.task1.main;

import by.training.task1.bean.entity.*;
import by.training.task1.dao.repository.SaladsHandler;
import by.training.task1.service.action.Kitchen;
import by.training.task1.service.specification.FindSaladById;
import by.training.task1.service.specification.SortSaladByWeight;
import by.training.task1.service.specification.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        Kitchen kitchen = new Kitchen();
        String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm"));
        System.out.println(timeStr);

        kitchen.produceSalads();

        List<Salad> saladList = SaladsHandler.getInstance().getSaladList();
        for (Salad salad : saladList) {
            System.out.println(salad);
        }
        System.out.println("------------------------------");

        Specification specification = new FindSaladById(4);
        saladList = SaladsHandler.getInstance().query(specification);
        for (Salad salad : saladList) {
            System.out.println(salad);
        }
        System.out.println("------------------------------");

        specification = new SortSaladByWeight();
        saladList = SaladsHandler.getInstance().query(specification);
        for (Salad salad : saladList) {
            System.out.println(salad);
        }
        System.out.println("------------------------------");
    }
}
