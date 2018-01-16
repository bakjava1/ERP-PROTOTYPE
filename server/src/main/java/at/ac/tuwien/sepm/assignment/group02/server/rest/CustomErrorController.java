/*package at.ac.tuwien.sepm.assignment.group02.server.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "custom error handling")
    public String error() {
        return "ERROR";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}*/