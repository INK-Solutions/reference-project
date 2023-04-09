package tech.example.infrastructure.events.outgoing.mapper;

import tech.example.service.event.outgoing.LoanRequestCreatedEvent;
import tech.example.generated.loan.LoanApprovalRequest;

/*
This class is given as an example and should be replaced with real class
 */
public class LoanApprovalRequestMapper {
    public static LoanApprovalRequest map(LoanRequestCreatedEvent event) {
        return LoanApprovalRequest.newBuilder()
                .setEventId(event.getId())
                .setLoanId(event.getLoanId())
                .setClientId(event.getClientId())
                .setAmount(event.getAmount().doubleValue())
                .build();
    }
}
