package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.bean.User;
import by.training.certificationCenter.controller.resourceBundle.ResourceBundleWrapper;
import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

public class DownloadDocumentCommand extends Command {
    /**
     * The key that is required to get the user instance from the
     * request attribute.
     */
    private static final String ATTRIBUTE_NAME_USER = "authorizedUser";
    /**
     * Key that is required to set the error message to the request attribute.
     */
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    /**
     * Key is required to get the file name from the request parameters.
     */
    private static final String PARAM_NAME_FILE = "fileName";
    /**
     * Key is required to get the file location from the request parameters.
     */
    private static final String PARAM_NAME_FILE_PATH = "uploadFilePath";
    /**
     * Default multipurpose internet message extension type.
     */
    private static final String CONTENT_TYPE = "APPLICATION/OCTET-STREAM";

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(ATTRIBUTE_NAME_USER);
        String uploadFilePath = request.getParameter(PARAM_NAME_FILE_PATH);
        String fileName = request.getParameter(PARAM_NAME_FILE);
        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(uploadFilePath + fileName);
        ApplicationService service = ServiceFactory.
                getInstance().getApplicationService();
        ResourceBundle bundle = ResourceBundleWrapper.getSingleInstance().
                createResourceBundle(session);
        try {
            FileInputStream inputStream = service.receiveFileInputStream(
                    uploadFilePath + fileName);
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + fileName + "\"");
            if (mimeType != null) {
                response.setContentType(mimeType);
            } else {
                response.setContentType(CONTENT_TYPE);
            }
            int i;
            while ((i = inputStream.read()) != -1) {
                out.write(i);
            }
            inputStream.close();
            out.close();
        } catch (ServiceException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    bundle.getString(e.getMessage()));
            getLogger().error(String.format("user \"%s\" unsuccessfully tried "
                    + "to download file name \"%s\"",
                    user.getLogin(), fileName));
        } catch (IOException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    bundle.getString("message.file.download.error"));
        }
    }
}
