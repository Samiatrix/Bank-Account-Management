package com.unittesting.UnitTesting.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@AllArgsConstructor
public class ErrorDetails {
    private Date date;
    private String message;
    private String details;

}
