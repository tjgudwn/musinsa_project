package com.musinsa.project.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),
    PRODUCT_NOT_FOUND(404, "존재하지 않는 상품 입니다."),
    ALREADY_SAVED_PRODUCT(409, "이미 저장한 상품 입니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다.");
    
    private final int status;
    private final String message;
}
