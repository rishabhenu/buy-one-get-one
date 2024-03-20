package com.gravityer.bogo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Getter
@Setter
public class GenericResponseDto<T> {

    private String message;
    private T body;
    private String[] errors;

    public static <T> GenericResponseDto<T> ok(T body) {
        return new GenericResponseDto<T>().setMessage("SUCCESS").setBody(body);
    }

    public static <T> GenericResponseDto<T> error(String... errors) {
        return new GenericResponseDto<T>().setMessage("FAILURE").setErrors(errors);
    }

}
