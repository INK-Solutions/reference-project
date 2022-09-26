package tech.seedz.template.service.event.incoming;

import lombok.Builder;
import lombok.Getter;

/*
This class is given as an example and should be replaced with real class
 */
@Getter
public class LoanApprovalDecisionEvent extends IncomingEvent {

    private final String loanId;
    private final boolean approved;

    @Builder
    public LoanApprovalDecisionEvent(String id, String loanId, boolean approved) {
        super(id);
        this.loanId = loanId;
        this.approved = approved;
    }
}
