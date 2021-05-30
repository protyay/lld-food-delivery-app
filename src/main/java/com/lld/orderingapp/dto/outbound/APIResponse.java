package com.lld.orderingapp.dto.outbound;

import lombok.Data;

@Data
public class APIResponse {
    private Object data;
    private boolean isSuccess;
    private String errorResponse;


}
