package com.gravityer.bogo.service;

import com.gravityer.bogo.config.exceptions.ProgramException;

import java.util.List;

/**
 * this is just a marker interface to mark a service
 */
public interface GenericAppService<T extends Enum<T>> {

    List<T> features();

    default ProgramException notImplementedException() {
        return new ProgramException("Current service is not implemented!");
    }

}
