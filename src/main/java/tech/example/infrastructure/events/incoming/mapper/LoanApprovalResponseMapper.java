package tech.example.infrastructure.events.incoming.mapper;

import tech.example.service.event.incoming.LoanApprovalResponse;

/*
This class is given as an example and should be replaced with real business logic
 */
public class LoanApprovalResponseMapper {
    public static LoanApprovalResponse map(tech.example.generated.loan.LoanApprovalResponse event) {
        return LoanApprovalResponse.builder()
                .id(event.getId())
                .loanId(event.getLoanId())
                .approved(event.getApproved())
                .build();
    }
}
