package com.logger.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ltrf.LogMessage;

@Controller
public class LogServer {
	
	private static Logger logger = LoggerFactory.getLogger(LogServer.class);
	
	@RequestMapping(value="/saveLogMessage",method = RequestMethod.POST)
	public @ResponseBody void saveLogMessage(@RequestBody LogMessage message)
	{
		System.out.println(message.getAppName());
		logger.info(message.getKey());
		logger.info(message.getMessage());
	}
	
}
