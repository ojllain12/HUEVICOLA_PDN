package com.ufpso.Huevicola.utils;

import com.ufpso.Huevicola.models.generic.ResponseMessage;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

public class Utils {
    public static ResponseMessage createResponse(String message, HttpStatus httpStatus) {
        return ResponseMessage.builder()
                .date(LocalDate.now())
                .message(List.of(message))
                .statusCode(httpStatus.value())
                .build();
    }
}
