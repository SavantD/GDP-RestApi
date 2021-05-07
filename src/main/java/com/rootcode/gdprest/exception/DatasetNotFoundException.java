package com.rootcode.gdprest.exception;

import java.io.IOException;

public class DatasetNotFoundException extends IOException {

    public DatasetNotFoundException(String msg) {
        super(msg);
    }
}
