package com.grape.supermarket.common;

import org.apache.log4j.Logger;

public abstract class AbstractLogger {
	private Logger logger = Logger.getLogger(this.getClass());

	protected Logger getLogger() {
		return logger;
	}

}
