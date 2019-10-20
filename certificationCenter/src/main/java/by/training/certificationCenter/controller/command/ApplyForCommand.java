package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Document;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.controller.resourceBundle.ResourceBundleWrapper;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ApplicationFactory;
import by.training.certificationCenter.service.factory.DocumentFactory;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.factory.ServiceFactory;
import by.training.certificationCenter.service.validator.ApplicationValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ApplyForCommand extends Command {
    /**
     * The key that is required to get the user instance from the
     * session attribute.
     */
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    /**
     * Key that is required to set the message to the request attribute.
     */
    private static final String ATTRIBUTE_NAME_MESSAGE = "message";
    /**
     * Key that is required to set the error message to the request attribute.
     */
    private static final String ATTRIBUTE_NAME_ERROR = "errorMessage";

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        Enumeration<String> parameterNames = request.getParameterNames();
        int countParams = 0;
        if (parameterNames.hasMoreElements()) {
            countParams++;
        }
        if (countParams == 0) {
            return;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_NAME_USER);
        ResourceBundle bundle = ResourceBundleWrapper.getSingleInstance().
                createResourceBundle(session);
        try {
            Application application = createApplication(request, user, bundle);
            List<Product> products = createProducts(request,
                    application, bundle);
            List<Document> documents = createDocuments(request,
                    application, bundle);
            application.setProducts(products);
            application.setDocuments(documents);
            ApplicationService service = ServiceFactory.getInstance().
                    getApplicationService();
            if (service.addNewApplication(application)) {
                setRedirect(true);
                setPathName(PathConfiguration.getProperty("path.page.success"));
                session.setAttribute(ATTRIBUTE_NAME_MESSAGE,
                        bundle.getString("message.application.added"));
            }
        } catch (CommandException e) {
            request.setAttribute(ATTRIBUTE_NAME_ERROR, e.getMessage());
        } catch (ServiceException e) {
            request.setAttribute(ATTRIBUTE_NAME_ERROR,
                    bundle.getString(e.getMessage()));
            getLogger().error(String.format("user \"%s\" unsuccessfully "
                    + "tried to add application", user.getLogin()));
        }
    }

    private Application createApplication(
            final HttpServletRequest request, final User user,
            ResourceBundle bundle) throws CommandException {
        ApplicationFactory factory = ApplicationFactory.getSingleInstance();
        String date_add_param = request.getParameter("date_add");
        String requirements = request.getParameter("requirements");
        if (date_add_param == null || date_add_param.equals("")) {
            throw new CommandException(bundle.getString(
                    "message.application.date.empty"));
        } else if (requirements == null || requirements.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.application.requirements.empty"));
        }
        LocalDate dateAdd;
        try {
            dateAdd = LocalDate.parse(date_add_param,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new CommandException(bundle.getString(
                    "message.application.date.mistake"));
        }
        return factory.createNewClientApplication(dateAdd, user, requirements);
    }

    private List<Product> createProducts(
            final HttpServletRequest request, final Application application,
            final ResourceBundle bundle) throws CommandException {
        List<Product> productList = new ArrayList<>();
        ProductFactory factory = ProductFactory.getSingleInstance();
        String[] productNames = request.getParameterValues(
                "product_name");
        String prod_code_param = request.getParameter("product_code");
        String producer = request.getParameter("producer_name");
        String address = request.getParameter("producer_address");
        String quantity_attr_param = request.getParameter(
                "product_quantity_attribute");
        if (productNames == null) {
            throw new CommandException(bundle.getString(
                    "message.product.name.empty"));
        } else if (prod_code_param == null || prod_code_param.equals("")) {
            throw new CommandException(bundle.getString(
                    "message.product.code.empty"));
        } else if (producer == null || producer.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.producer.name.empty"));
        } else if (address == null || address.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.producer.address.empty"));
        }
        long productCode;
        try {
            productCode = Long.valueOf(prod_code_param);
        } catch (NumberFormatException e) {
            throw new CommandException(bundle.getString(
                    "message.product.code.mistake"));
        }
        int quantityAttrInd;
        try {
            quantityAttrInd = Integer.valueOf(quantity_attr_param);
        } catch (NumberFormatException | NullPointerException e) {
            throw new CommandException(bundle.getString(
                    "message.product.quantity.mistake"));
        }
        for (String productName : productNames) {
            if (productName.trim().equals("")) {
                throw new CommandException(bundle.getString(
                        "message.product.name.empty"));
            }
            Product product = factory.createNewClientProduct(
                    productName, productCode, producer, address,
                    quantityAttrInd, application);
            productList.add(product);
        }
        return productList;
    }

    private List<Document> createDocuments(
            final HttpServletRequest request, final Application application,
            final ResourceBundle bundle) throws CommandException {
        List<Document> documentList = new ArrayList<>();
        ServletContext context = request.getServletContext();
        String applicationPath = context.getRealPath("");
        String uploadPath = context.getInitParameter("upload.location");
        String uploadFilePath = applicationPath + uploadPath;
        try {
            List<Part> fileParts = request.getParts().stream().filter(
                    part -> "attachment".equals(part.getName())).
                    collect(Collectors.toList());
            DocumentFactory factory = DocumentFactory.getSingleInstance();
            for (Part part : fileParts) {
                String originalFileName = Paths.get(
                        part.getSubmittedFileName()).getFileName().toString();
                if (!"".equals(originalFileName) && ApplicationValidator.
                        validateFileExtension(originalFileName)) {
                    Document document = factory.createNewDocument(
                            uploadFilePath, originalFileName,
                            part.getInputStream(), application);
                    documentList.add(document);
                }
            }
        } catch (IOException | ServletException e) {
            throw new CommandException(
                    bundle.getString("message.document.upload.mistake"));
        }
        return documentList;
    }
}
