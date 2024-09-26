package org.brocode.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionDto {

    private HttpStatus errorCode;
    private String errorMessage;

    public ExceptionDto(HttpStatus errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
