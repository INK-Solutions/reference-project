package tech.seedz.template.controller.loan.dto;

import lombok.Data;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Data
public class LoanRequestCreateDto {
    @NotNull(message = "clientId should be specified")
    private String clientId;

    @NotNull(message = "amount should be specified")
    private BigDecimal amount;
}
