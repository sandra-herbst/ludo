package Exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IllegalFactoryArgument extends Exception{

    private static final Logger logger = LogManager.getLogger(IllegalFactoryArgument.class);

    public IllegalFactoryArgument(String msg) {
        super(msg);
        logger.fatal(msg);
    }
}
