package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.service.ApplicationService;
import by.training.certificationCenter.service.configuration.PathConfiguration;
import by.training.certificationCenter.service.exception.ServiceException;
import by.training.certificationCenter.service.factory.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadDocumentCommand extends Command {
    private static final String PARAM_NAME_FILE = "fileName";
    private static final String PARAM_NAME_FILE_PATH = "uploadFilePath";
    /**
     * Default MIME type.
     */
    private static final String CONTENT_TYPE = "APPLICATION/OCTET-STREAM";

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        String uploadFilePath = request.getParameter(PARAM_NAME_FILE_PATH);
        String fileName = request.getParameter(PARAM_NAME_FILE);
        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(uploadFilePath + fileName);
        ApplicationService service = ServiceFactory.
                getInstance().getApplicationService();
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
        } catch (ServiceException | IOException e) {
            setPathName(PathConfiguration.getProperty("path.page.error"));
            request.setAttribute("errorMessage", e.getMessage());
        }
    }
}
