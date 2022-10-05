package tech.seedz.template.infrastructure.events.incoming.mapper;

import tech.seedz.template.service.event.incoming.LoanApprovalResponse;

/*
This class is given as an example and should be replaced with real class
 */
public class LoanApprovalResponseMapper {
    public static LoanApprovalResponse map(tech.seedz.template.generated.loan.LoanApprovalResponse event) {
        return LoanApprovalResponse.builder()
                .id(event.getId())
                .loanId(event.getLoanId())
                .approved(event.getApproved())
                .build();
    }
}
