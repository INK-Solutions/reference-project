package tech.example.service.loan;

import tech.example.dao.model.loan.LoanRequest;

import java.math.BigDecimal;

/*
This class is given as an example and should be replaced with real business logic
 */
public class LoanRequestFactory {
    public static LoanRequest newLoanRequest(String clientId, BigDecimal amount) {
        LoanRequest result = new LoanRequest();

        result.setClientId(clientId);
        result.setAmount(amount);
        result.setApproved(false);

        return result;
    }
}
