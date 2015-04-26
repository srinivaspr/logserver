package com.logger.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ltrf.LogMessage;

@Controller
public class LogServer {
	
	private static Logger logger = LoggerFactory.getLogger(LogServer.class);
	
	@RequestMapping(value="/saveLogMessage",method = RequestMethod.POST)
	public @ResponseBody void saveLogMessage(@RequestBody LogMessage message)
	{
		System.out.println(message.getMessage());
		DBMessageLogWriter writer = new DBMessageLogWriter();
		writer.writeLogMessage(message);
	}
	
	@RequestMapping("/alerts")
	public ModelAndView readLogMessage(){
		DBMessageLogWriter writer = new DBMessageLogWriter();
		ModelAndView view = new ModelAndView("alert");
		view.addObject("alerts", writer.readLogMessage());
		return view;
	}
	
}
