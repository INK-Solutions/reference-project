package tech.example.controller.loan.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/*
    This class is given as an example and should be replaced with real business logic
 */
@Builder
@Data
public class LoanRequestDto {
    private String id;
    private String clientId;
    private BigDecimal amount;
    private boolean approved;
}
