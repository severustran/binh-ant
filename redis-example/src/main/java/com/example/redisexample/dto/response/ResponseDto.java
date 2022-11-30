package com.example.redisexample.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {
    private Integer code;
    private T result;
}
