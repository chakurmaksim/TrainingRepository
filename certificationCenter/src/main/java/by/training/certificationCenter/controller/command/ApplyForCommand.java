package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.*;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ApplicationFactory;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.factory.ServiceFactory;
import com.fasterxml.uuid.Generators;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ApplyForCommand extends Command {
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
        User user = (User) session.getAttribute("authorizedUser");
        try {
            Application application = createApplication(request, user);
            List<Product> products = createProducts(request);
            List<Document> documents = createDocuments(request);
            application.setProducts(products);
            ServiceFactory factory = ServiceFactory.getInstance();
            ApplicationService service = factory.getApplicationService();
            if (service.addNewApplication(application)) {
                setRedirect(true);
                setPathName("/jsp/success.jsp");
                request.setAttribute("message",
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
        String date_add_param = request.getParameter("date_add");
        String requirements = request.getParameter("requirements");
        if (date_add_param == null || date_add_param.equals("")) {
            throw new CommandException(
                    "Field with date of the applying for is empty");
        } else if (requirements == null || requirements.equals("")) {
            throw new CommandException(
                    "Field with the application requirements is empty");
        }
        LocalDate dateAdd = LocalDate.parse(date_add_param,
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Application application = factory.createNewClientApp(dateAdd);
        factory.buildAppWithUserAndOrg(application, user.getId(),
                user.getOrg().getId(), requirements);
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
        if (productName == null || productName.equals("")) {
            throw new CommandException(
                    "Field with product name is empty");
        } else if (prod_code_param == null || prod_code_param.equals("")) {
            throw new CommandException(
                    "Field with product code is empty");
        } else if (producer == null || producer.equals("")) {
            throw new CommandException(
                    "Field with producer name is empty");
        } else if (address == null || address.equals("")) {
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
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            System.out.println(uploadFilePath);
        }
        try {
            List<Part> fileParts = request.getParts().stream().filter(
                    part -> "attachment".equals(part.getName())).
                    collect(Collectors.toList());
            for (Part part : fileParts) {
                String originalFileName = Paths.get(
                        part.getSubmittedFileName()).getFileName().toString();
                if (!"".equals(originalFileName)) {
                    UUID fileName = Generators.timeBasedGenerator().generate();
                    String fullFileName = uploadFilePath + File.separator + originalFileName;
                    // part.write(fullFileName);
                }
            }
        } catch (IOException | ServletException e) {
            throw new CommandException(
                    "Files cannot be uploaded and processed on the server");
        }
        return documentList;
    }
}
