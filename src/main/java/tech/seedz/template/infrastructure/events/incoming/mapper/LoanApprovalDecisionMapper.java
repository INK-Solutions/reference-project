package tech.seedz.template.infrastructure.events.incoming.mapper;

import tech.seedz.template.service.event.incoming.LoanApprovalDecisionEvent;

/*
This class is given as an example and should be replaced with real class
 */
public class LoanApprovalDecisionMapper {
    public static LoanApprovalDecisionEvent map(tech.seedz.template.generated.loan.LoanApprovalDecisionEvent event) {
        return LoanApprovalDecisionEvent.builder()
                .id(event.getId())
                .loanId(event.getLoanId())
                .approved(event.getApproved())
                .build();
    }
}
