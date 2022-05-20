package com.kyu0.foogether.utility.api;

public class ApiUtils {
    
    public static <T>ApiResult<T> success(T response) {
        return new ApiResult<T>(true, response, null);
    }

    public static ApiResult<?> error(String message, int status) {
        return new ApiResult<>(false, null, new ApiError(message, status));
    }
}
