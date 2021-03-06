package com.telecom.shop.config.error;

import com.telecom.shop.util.result.AppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系统异常处理
 * @author admin
 */
@RestController
public class GlobalErrorController implements ErrorController {
    private final String PATH = "/error";
    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @Autowired
    public GlobalErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes=errorAttributes;
    }

    @RequestMapping(value = PATH, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public AppResult handleError(HttpServletRequest request) {
        ServletWebRequest requestAttributes =new ServletWebRequest(request);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        return AppResult.errorReturn((int)attr.get("status"),(String) attr.get("error"),(String) attr.get("message"));
    }
}