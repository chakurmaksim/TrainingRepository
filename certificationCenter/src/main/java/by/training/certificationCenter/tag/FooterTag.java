package by.training.certificationCenter.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


import java.io.IOException;
import java.time.LocalDate;

public class FooterTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        LocalDate ld = LocalDate.now();
        String copyright = "<hr/><b> © " + ld.getYear() + " Copyright: "
                + "<a href=\"index.html\"> certification.by</a></b><hr/>";
        try {
            JspWriter out = pageContext.getOut();
            out.write(copyright);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
