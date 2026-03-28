package com.example.Group_4_JavaGrader.config;

import com.example.Group_4_JavaGrader.dto.ErrorViewModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleUnexpectedException(Exception exception, Model model) {
        model.addAttribute(
                "error",
                new ErrorViewModel(
                        "UNEXPECTED_ERROR",
                        "An unexpected error occurred.",
                        "Please retry the action or contact the team if the problem persists."
                )
        );
        return "error/error";
    }
}
