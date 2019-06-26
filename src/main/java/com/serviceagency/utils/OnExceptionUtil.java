package com.serviceagency.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class OnExceptionUtil {
    private static final String DB_ERROR_MSG = "Data Base error.";
    private static final String INVALID_PARAMETERS_MSG = "Invalid request data.";
    private static final String UNKNOWN_ERROR_MSG = "Unknown error.";


    public static void processErrorException(Class className, Throwable ex, String msg, HttpServletRequest request) {
        Logger logger = Logger.getLogger(className);
        logger.warn(msg, ex);
        request.setAttribute("message", msg);
    }

    public static void processError(Class className, String msg, HttpServletRequest request) {
        Logger logger = Logger.getLogger(className);
        logger.warn(msg);
        request.setAttribute("message", msg);
    }

    public static void processErrorDbException(Class className, Throwable ex, HttpServletRequest request) {
        Logger logger = Logger.getLogger(className);
        logger.warn(DB_ERROR_MSG, ex);
        request.setAttribute("message", DB_ERROR_MSG);
    }

    public static void processDbError(Class className, HttpServletRequest request) {
        Logger logger = Logger.getLogger(className);
        logger.warn(DB_ERROR_MSG);
        request.setAttribute("message", DB_ERROR_MSG);
    }

    public static void processErrorInvalidParamException(Class className, Throwable ex, HttpServletRequest request) {
        Logger logger = Logger.getLogger(className);
        logger.warn(INVALID_PARAMETERS_MSG, ex);
        request.setAttribute("message", INVALID_PARAMETERS_MSG);
    }

    public static void processInvalidParamError(Class className, HttpServletRequest request) {
        Logger logger = Logger.getLogger(className);
        logger.warn(DB_ERROR_MSG);
        request.setAttribute("message", DB_ERROR_MSG);
    }

    public static void processErrorUnknownException(Class className, Throwable ex, HttpServletRequest request) {
        Logger logger = Logger.getLogger(className);
        logger.warn(UNKNOWN_ERROR_MSG, ex);
        request.setAttribute("message", UNKNOWN_ERROR_MSG);
    }

    public static void processUnknownError(Class className, HttpServletRequest request) {
        Logger logger = Logger.getLogger(className);
        logger.warn(UNKNOWN_ERROR_MSG);
        request.setAttribute("message", UNKNOWN_ERROR_MSG);
    }


}
