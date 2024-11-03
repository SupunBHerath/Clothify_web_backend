package edu.icet.clothify_web_backend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorException {

    private  String status;
    private  String message;
}
