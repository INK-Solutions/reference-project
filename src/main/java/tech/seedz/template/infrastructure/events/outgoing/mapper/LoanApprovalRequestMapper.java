package tech.seedz.template.infrastructure.events.outgoing.mapper;

import tech.seedz.template.generated.loan.LoanApprovalRequest;
import tech.seedz.template.service.event.outgoing.LoanRequestCreatedEvent;

/*
This class is given as an example and should be replaced with real class
 */
public class LoanApprovalRequestMapper {
    public static LoanApprovalRequest map(LoanRequestCreatedEvent event) {
        return LoanApprovalRequest.newBuilder()
                .setEventId(event.getId())
                .setLoanId(event.getLoanId())
                .setClientId(event.getClientId())
                .setAmount2(event.getAmount())
                .build();
    }
}
