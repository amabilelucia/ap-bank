package com.apbank.apbank.exceptions;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DetailsException {
    String title;
    int status;
    String message;
    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();
}
