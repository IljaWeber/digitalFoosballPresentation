package com.valtech.digitalFoosball.domain.common.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NameDuplicateException extends RuntimeException {

    public NameDuplicateException(String message) {
        super(message);
        Logger logger = LogManager.getLogger(NameDuplicateException.class);
        logger.error("{} is a duplicated value", message);

    }
}
