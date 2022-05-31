package com.kyu0.foogether.utility.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    private boolean success;
    private T response;
    private ApiError error;

}
