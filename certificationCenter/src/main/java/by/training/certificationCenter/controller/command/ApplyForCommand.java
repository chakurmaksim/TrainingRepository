package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.*;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ApplicationFactory;
import by.training.certificationCenter.service.factory.DocumentFactory;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.factory.ServiceFactory;

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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class ApplyForCommand extends Command {
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    private static final String ATTRIBUTE_NAME_MESSAGE = "message";
    private static final String PARAM_NAME_DATE_ADD = "date_add";
    private static final String PARAM_NAME_REQUIREMENTS = "requirements";
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
        try {
            Application application = createApplication(request, user);
            List<Product> products = createProducts(request);
            List<Document> documents = createDocuments(request);
            application.setProducts(products);
            application.setDocuments(documents);
            ServiceFactory factory = ServiceFactory.getInstance();
            ApplicationService service = factory.getApplicationService();
            if (service.addNewApplication(application)) {
                setRedirect(true);
                setPathName(PathConfiguration.getProperty("path.page.success"));
                session.setAttribute(ATTRIBUTE_NAME_MESSAGE,
                        "Ваша заявка успешно подана!");
            }
        } catch (CommandException | ServiceException e) {
            request.setAttribute("errorMessage", e.getMessage());
            getLogger().error(String.format("user \"%s\" unsuccessfully "
                            + "tried to add application", user.getLogin()));
        }
    }

    private Application createApplication(
            final HttpServletRequest request, final User user)
            throws CommandException {
        ApplicationFactory factory = ApplicationFactory.getSingleInstance();
        String date_add_param = request.getParameter(PARAM_NAME_DATE_ADD);
        String requirements = request.getParameter(PARAM_NAME_REQUIREMENTS);
        if (date_add_param == null || date_add_param.equals("")) {
            throw new CommandException(
                    "Field with the applying date is empty");
        } else if (requirements == null || requirements.trim().equals("")) {
            throw new CommandException(
                    "Field with the application requirements is empty");
        }
        LocalDate dateAdd;
        try {
            dateAdd = LocalDate.parse(date_add_param,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new CommandException(
                    "Field can not be parsed in to the local date");
        }
        Application application = factory.createNewClientApp(dateAdd);
        factory.buildAppWithUserAndOrg(application, user.getId(),
                user.getOrganisation().getId(), requirements);
        return application;
    }

    private List<Product> createProducts(final HttpServletRequest request)
            throws CommandException {
        List<Product> productList = new ArrayList<>();
        ProductFactory factory = ProductFactory.getSingleInstance();
        String productName = request.getParameter("product_name");
        String prod_code_param = request.getParameter("product_code");
        String producer = request.getParameter("producer_name");
        String address = request.getParameter("producer_address");
        String quantity_attr_param = request.getParameter(
                "product_quantity_attribute");
        if (productName == null || productName.trim().equals("")) {
            throw new CommandException(
                    "Field with product name is empty");
        } else if (prod_code_param == null || prod_code_param.equals("")) {
            throw new CommandException(
                    "Field with product code is empty");
        } else if (producer == null || producer.trim().equals("")) {
            throw new CommandException(
                    "Field with producer name is empty");
        } else if (address == null || address.trim().equals("")) {
            throw new CommandException(
                    "Field with producer address is empty");
        }
        long productCode;
        try {
            productCode = Long.valueOf(prod_code_param);
        } catch (NumberFormatException e) {
            throw new CommandException("Product code is not a number");
        }
        int quantityAttrInd = Integer.valueOf(quantity_attr_param);
        String quantityAttrName= QuantityAttribute.AttributeName.getByIdentity(
                quantityAttrInd).getDescription();
        Product product = factory.createProduct(0, productName, productCode,
                producer, address, quantityAttrInd, quantityAttrName);
        productList.add(product);
        return productList;
    }

    private List<Document> createDocuments(final HttpServletRequest request)
            throws CommandException {
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
                if (!"".equals(originalFileName)) {
                    Document document = factory.createNewDocument(0,
                            uploadFilePath, originalFileName,
                            part.getInputStream());
                    documentList.add(document);
                }
            }
        } catch (IOException | ServletException e) {
            throw new CommandException(
                    "Files cannot be uploaded and processed on the server");
        }
        return documentList;
    }
}
