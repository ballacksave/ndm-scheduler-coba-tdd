package com.tosigav.nwtw.exception;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 23 Mar 2015
 */
public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }

}
