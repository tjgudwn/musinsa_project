package com.musinsa.project.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_PARAMETER(400, "�Ķ���� ���� Ȯ�����ּ���."),
    PRODUCT_NOT_FOUND(404, "�������� �ʴ� ��ǰ �Դϴ�."),
    ALREADY_SAVED_PRODUCT(409, "�̹� ������ ��ǰ �Դϴ�."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "���� �����Դϴ�.");
    
    private final int status;
    private final String message;
}
