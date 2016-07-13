package com.otp.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logger
 * 
 * @author Max
 *
 */
public final class CustomLogger {

    private final Logger realLogger;

    public static CustomLogger getLogger(String name) {
	return new CustomLogger(name);
    }

    public CustomLogger(String name) {
	realLogger = java.util.logging.Logger.getLogger(name);
    }

    public void info(String sourceClass, String sourceMethod, String message) {
	realLogger.logp(Level.INFO, sourceClass, sourceMethod, message);
    }

    public void info(String msg) {
	realLogger.info(msg);
    }

    public void info(String sourceClass, String sourceMethod, String message, Throwable thrown) {
	realLogger.logp(Level.INFO, sourceClass, sourceMethod, message, thrown);
    }

    public void debug(String sourceClass, String sourceMethod, String message) {
	realLogger.logp(Level.FINE, sourceClass, sourceMethod, message);
    }

    public void debug(String sourceClass, String sourceMethod, String message, Throwable thrown) {
	realLogger.logp(Level.FINE, sourceClass, sourceMethod, message, thrown);
    }

    public void trace(String sourceClass, String sourceMethod, String message) {
	realLogger.logp(Level.FINER, sourceClass, sourceMethod, message);
    }

    public void trace(String sourceClass, String sourceMethod, String message, Throwable thrown) {
	realLogger.logp(Level.FINER, sourceClass, sourceMethod, message, thrown);
    }

    public void warn(String sourceClass, String sourceMethod, String message) {
	realLogger.logp(Level.WARNING, sourceClass, sourceMethod, message);
    }

    public void warn(String sourceClass, String sourceMethod, String message, Throwable thrown) {
	realLogger.logp(Level.WARNING, sourceClass, sourceMethod, message, thrown);
    }

    public void error(String sourceClass, String sourceMethod, String message) {
	realLogger.logp(Level.SEVERE, sourceClass, sourceMethod, message);
    }

    public void error(String sourceClass, String sourceMethod, String message, Throwable thrown) {
	realLogger.logp(Level.SEVERE, sourceClass, sourceMethod, message, thrown);
    }

    public void error(String message, Throwable thrown) {
	realLogger.log(Level.SEVERE, message, thrown);
    }

    public void warn(String msg) {
	realLogger.warning(msg);
    }

    public boolean isTraceEnabled() {
	return realLogger.isLoggable(Level.FINER);
    }

    public boolean isDebugEnabled() {
	return realLogger.isLoggable(Level.FINE);
    }

    public void enableDebug() {
	realLogger.setLevel(Level.FINE);
    }

    public void disableDebug() {
	realLogger.setLevel(Level.INFO);
    }
}
