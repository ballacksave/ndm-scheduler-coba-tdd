package com.tosigav.nwtw.exception;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 23 Mar 2015
 */
public class DataDuplicateException extends RuntimeException{

    public DataDuplicateException() {
    }

    public DataDuplicateException(String message) {
        super(message);
    }

    public DataDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataDuplicateException(Throwable cause) {
        super(cause);
    }

}
