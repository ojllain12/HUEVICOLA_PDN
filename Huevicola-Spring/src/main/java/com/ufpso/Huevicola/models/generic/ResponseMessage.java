package com.ufpso.Huevicola.models.generic;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ResponseMessage {
    LocalDate date;
    List<String> message;
    int statusCode;
}