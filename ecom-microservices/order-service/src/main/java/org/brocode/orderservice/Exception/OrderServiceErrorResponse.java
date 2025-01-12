package org.brocode.orderservice.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderServiceErrorResponse {

    private String errorCode;
    private String errorMessage;

}
