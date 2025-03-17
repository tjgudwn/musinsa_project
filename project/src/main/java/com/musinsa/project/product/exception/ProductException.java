package com.musinsa.project.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductException extends RuntimeException {
    private final ErrorCode errorCode;
}
