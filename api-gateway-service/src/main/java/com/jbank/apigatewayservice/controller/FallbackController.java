package com.jbank.apigatewayservice.controller;

import com.jbank.apigatewayservice.controller.response.ServiceUnavailableErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/fallback")
public class FallbackController {

    @GetMapping("/sbp")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ServiceUnavailableErrorResponse sbp(){
        return new ServiceUnavailableErrorResponse("Service temporarily unavailable");
    }

    @GetMapping("/antifraud")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ServiceUnavailableErrorResponse antifraud (){
        return new ServiceUnavailableErrorResponse("Service temporarily unavailable");
    }
}
