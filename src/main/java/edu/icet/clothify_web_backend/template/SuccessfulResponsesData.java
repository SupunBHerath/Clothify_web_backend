package edu.icet.clothify_web_backend.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessfulResponsesData {
    private String status;
    private String message;
    private Object data;

}
