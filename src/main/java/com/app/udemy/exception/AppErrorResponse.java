package com.app.udemy.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppErrorResponse {
    private Timestamp timestamp;
    private String message;
    private Integer status;
}
