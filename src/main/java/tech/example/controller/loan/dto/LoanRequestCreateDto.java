package tech.example.controller.loan.dto;

import lombok.Data;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

/*
    This class is given as an example and should be replaced with real business logic
 */
@Data
public class LoanRequestCreateDto {
    @NotNull(message = "clientId should be specified")
    private String clientId;

    @NotNull(message = "amount should be specified")
    private BigDecimal amount;
}
