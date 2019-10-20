package by.training.certificationCenter.controller.command;

public class PaginationHelper {
    public static int calculateSkipPages(
            final int pageNumber, final int pageLimit) {
        return (pageNumber - 1) * pageLimit;
    }

    public static int calculateLastPage(final int total, final int pageLimit) {
        if (total % pageLimit == 0) {
            return total / pageLimit;
        } else {
            return total / pageLimit + 1;
        }
    }
}
