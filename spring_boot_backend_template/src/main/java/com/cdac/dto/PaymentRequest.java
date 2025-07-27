package com.cdac.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate; // Import LocalDate

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long memberDetailsId;
    private Long paidForPlanId;
    private double amount;
    private LocalDate paymentDate; // Can be null, controller will set LocalDate.now()
    private String paymentMethod;
}