package org.brocode.orderservice.Exception;

public class ProductOutOfStockException extends Exception{

    public ProductOutOfStockException(String message){
        super(message);
    }
}
