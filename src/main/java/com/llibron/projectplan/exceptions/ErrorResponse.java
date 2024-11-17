package com.llibron.projectplan.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private String message;
    private int httpStatus;
    private LocalDateTime timeStamp;

}
