package edu.icet.clothify_web_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCountsDTO {
    private long processing;
    private long delivering;
    private long delivered;
    private  long rejected;
}
