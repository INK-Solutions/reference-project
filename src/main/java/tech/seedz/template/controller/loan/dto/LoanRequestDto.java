package tech.seedz.template.controller.loan.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LoanRequestDto {
    private String id;
    private String clientId;
    private BigDecimal amount;
    private boolean externallyApproved;
}
