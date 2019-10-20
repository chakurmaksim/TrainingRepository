package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.Application;
import by.training.certificationCenter.bean.Product;
import by.training.certificationCenter.controller.resourceBundle.ResourceBundleWrapper;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ProductFactory;
import by.training.certificationCenter.service.factory.ServiceFactory;
import by.training.certificationCenter.service.validator.ApplicationValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditApplicationCommand extends Command {
    /**
     * Key that is required to set the error message to the request attribute.
     */
    private static final String ATTRIBUTE_NAME_ERROR = "errorMessage";
    /**
     * Key that is required to set the message to the request attribute.
     */
    private static final String ATTRIBUTE_NAME_MESSAGE = "message";
    /**
     * Key that is required to set the Application instance to the
     * request attribute.
     */
    private static final String ATTRIBUTE_NAME_APPLICATION = "application";

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Application application = (Application) session.getAttribute(
                "currentApplication");
        String requirements = request.getParameter("requirements");
        if (application != null && requirements == null) {
            session.removeAttribute("currentApplication");
            request.setAttribute(ATTRIBUTE_NAME_APPLICATION, application);
            setPathName(PathConfiguration.getProperty("path.page.applyFor"));
        }
        if (application != null && requirements != null) {
            ResourceBundle bundle = ResourceBundleWrapper.getSingleInstance().
                    createResourceBundle(session);
            ApplicationService service = ServiceFactory.getInstance().
                    getApplicationService();
            try {
                editApplication(requirements, application, bundle);
                editProducts(request, application, bundle);
                if (service.updateApplication(application)) {
                    setRedirect(true);
                    setPathName(PathConfiguration.getProperty("path.page.success"));
                    session.setAttribute(ATTRIBUTE_NAME_MESSAGE,
                            bundle.getString("message.application.edited"));
                }
            } catch (CommandException e) {
                request.setAttribute(ATTRIBUTE_NAME_APPLICATION, application);
                request.setAttribute(ATTRIBUTE_NAME_ERROR, e.getMessage());
                setPathName(PathConfiguration.getProperty(
                        "path.page.applyFor"));
            } catch (ServiceException e) {
                setRedirect(true);
                session.setAttribute(ATTRIBUTE_NAME_ERROR,
                        bundle.getString(e.getMessage()));
                setPathName(PathConfiguration.getProperty("path.page.error"));
                getLogger().error(String.format("user \"%s\" unsuccessfully "
                        + "tried to edit application",
                        application.getExecutor().getLogin()));
            }
        }
    }

    private void editApplication(
            final String requirements, final Application application,
            ResourceBundle bundle) throws CommandException {
        if (requirements.trim().equals("")) {
            throw new CommandException(bundle.getString(
                    "message.application.requirements.empty"));
        }
        application.setRequirements(requirements);
    }

    private void editProducts(
            final HttpServletRequest request, final Application application,
            final ResourceBundle bundle) throws CommandException {
        List<Product> productList = new ArrayList<>();
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
        if (!ApplicationValidator.validateProductCode(productCode)) {
            throw new CommandException("message.product.code.validate");
        }
        int quantityAttrInd;
        try {
            quantityAttrInd = Integer.valueOf(quantity_attr_param);
        } catch (NumberFormatException | NullPointerException e) {
            throw new CommandException(bundle.getString(
                    "message.product.quantity.mistake"));
        }
        ProductFactory factory = ProductFactory.getSingleInstance();
        for (int i = 0; i < productNames.length; i++) {
            String productName = productNames[i];
            if (productName.trim().equals("")) {
                throw new CommandException(bundle.getString(
                        "message.product.name.empty"));
            }
            Product product = factory.createNewClientProduct(
                    productName, productCode, producer, address,
                    quantityAttrInd, application);
            if (productNames.length <= application.getProducts().size()) {
                int productId = application.getProducts().get(i).getId();
                product.setId(productId);
            }
            productList.add(product);
        }
        application.setProducts(productList);
    }
}
