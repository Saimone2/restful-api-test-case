package com.saimone.restfulapitestcase.logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UserLogger {
    public static Logger LOGGER;

    static {
        try(FileInputStream ins = new FileInputStream("src/main/resources/logging.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(UserLogger.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}