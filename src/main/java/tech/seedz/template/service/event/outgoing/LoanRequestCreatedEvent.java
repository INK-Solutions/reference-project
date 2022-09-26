package tech.seedz.template.service.event.outgoing;

import lombok.Getter;
import tech.seedz.template.dao.model.loan.LoanRequest;

import java.math.BigDecimal;

/*
This class is given as an example and should be replaced with real class
 */
@Getter
public class LoanRequestCreatedEvent extends PublishableEvent {
    private final String clientId;
    private final String loanId;
    private final BigDecimal amount;

    protected LoanRequestCreatedEvent(String clientId, String loanId, BigDecimal amount) {
        this.clientId = clientId;
        this.loanId = loanId;
        this.amount = amount;
    }

    public static LoanRequestCreatedEvent from(LoanRequest loanRequest) {
        return new LoanRequestCreatedEvent(loanRequest.getClientId(), loanRequest.getId(), loanRequest.getAmount());
    }
}
