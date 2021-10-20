package common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller

public class HealthCheckContoller {
	
	@RequestMapping(value = "health/check", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
	public void  getHealthCheck(HttpServletResponse response) throws IOException {
		
		String msg = "Hello Coway";
		response.getOutputStream().write(msg.getBytes());
		
	}
	
}
 