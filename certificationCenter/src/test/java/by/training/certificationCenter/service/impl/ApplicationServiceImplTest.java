package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Organisation;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.bean.Status;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ApplicationFactory;
import by.training.certificationCenter.service.factory.OrganisationFactory;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.factory.ServiceFactory;
import by.training.certificationCenter.service.factory.UserFactory;
import com.wix.mysql.SqlScriptSource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static com.wix.mysql.ScriptResolver.classPathScript;
import static org.testng.Assert.assertEquals;

public class ApplicationServiceImplTest extends InitServiceTest {
    private static final SqlScriptSource[] SERVICE_SCRIPT_SOURCES
            = {classPathScript("sql/fill_quantity_attribute_table.sql"),
            classPathScript("sql/fill_application_table.sql"),
            classPathScript("sql/fill_product_table.sql")};
    private ApplicationService service;
    private User client;
    private Application application;
    private Product applicationProduct;

    @BeforeMethod
    public void setUp() {
        executeScriptSource(SERVICE_SCRIPT_SOURCES);
        service = ServiceFactory.getInstance().
                getApplicationService(getConnectionWrapper());
        client = UserFactory.getSingleInstance().createUser(
                3, 2, "client_1", "ClientName_1",
                "ClientSurname_1", "ClientPatronymic_1",
                375291234567l, "client@organisation1.org",
                3, true);
        Organisation clientOrganisation = OrganisationFactory.
                getSingleInstance().createOrganisation(2,111111112,
                        "ClientOrganisation_1",
                        "ClientOrganisation_1 address",
                        375173300000l, "info@organisation1.by",
                        true);
        client.setOrganisation(clientOrganisation);
        LocalDate now = LocalDate.now();
        LocalDate today = LocalDate.of(now.getYear(), now.getMonth(),
                now.getDayOfMonth());
        application = ApplicationFactory.getSingleInstance().
                createNewClientApplication(today, client,
                        "Requirements of the application2");
        applicationProduct = ProductFactory.getSingleInstance().
                createNewClientProduct("ProductName2", 8501402009l,
                        "Producer name2", "Producer address2",
                        2, application);
        application.setProducts(new ArrayList<>(Arrays.asList(
                applicationProduct)));
        application.setDocuments(new ArrayList<>());
    }

    @AfterMethod
    public void tearDown() {
    }

    @DataProvider(name = "negativeDataForAddNewApplication")
    public Object[] createData_ForAddNewApplication() {
        Application app1 = ApplicationFactory.getSingleInstance().
                createNewClientApplication(LocalDate.of(
                        2019, 10, 20),
                        new User(0), "");
        LocalDate now = LocalDate.now();
        LocalDate today = LocalDate.of(now.getYear(), now.getMonth(),
                now.getDayOfMonth());
        Application app2 = ApplicationFactory.getSingleInstance().
                createNewClientApplication(today,
                        new User(0), "");
        Product app2Product = ProductFactory.getSingleInstance().
                createNewClientProduct("productName", 123,
                        "", "", 1, app2);
        app2.setProducts(new ArrayList<>(Arrays.asList(app2Product)));
        return new Object[]{
                app1, app2

        };
    }

    @Test(description = "positive scenario of adding an application to database")
    public void testAddNewApplication() throws ServiceException {
        boolean actual = service.addNewApplication(application);
        assertEquals(actual, true);
    }

    @Test(description = "Confirm exceptions thrown when multiplying "
            + "two matrices of different sizes in one thread",
            dataProvider = "negativeDataForAddNewApplication",
            expectedExceptions = ServiceException.class)
    public void testAddNewApplication_CheckThrownException(
            Application application) throws ServiceException {
        service.addNewApplication(application);
    }

    @Test(description = "test method of getting an application by user id from the database")
    public void testShowApplicationById() throws ServiceException {
        int appId = 2;
        int prodId = 2;
        int regNumber = 201909071;
        LocalDate addedDate = LocalDate.of(2019, 9, 7);
        LocalDate resolveDate = LocalDate.of(2019, 9, 8);
        Status rejected = Status.REJECTED;
        applicationProduct.setId(prodId);
        application.setId(appId);
        application.setReg_num(regNumber);
        application.setDate_add(addedDate);
        application.setDate_resolve(resolveDate);
        application.setStatus(rejected);
        Application actual = service.showApplicationById(application.getId(),
                application.getExecutor());
        assertEquals(actual, application);
    }
}