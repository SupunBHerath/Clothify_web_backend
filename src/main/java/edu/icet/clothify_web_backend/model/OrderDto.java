package edu.icet.clothify_web_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Integer id;
    private Integer cusId;
    private String date;
    private String billingAddress;
    private  String paymentMethod;
    private String invoiceNumber;
    private String phoneNumber;
    private String status;
    private List<OrderDetailsDto> orderDetails;
}
