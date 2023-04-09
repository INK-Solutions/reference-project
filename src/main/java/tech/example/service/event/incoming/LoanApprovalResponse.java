package tech.example.service.event.incoming;

import lombok.Builder;
import lombok.Getter;

/*
This class is given as an example and should be replaced with real business logic
 */
@Getter
public class LoanApprovalResponse extends IncomingEvent {

    private final String loanId;
    private final boolean approved;

    @Builder
    public LoanApprovalResponse(String id, String loanId, boolean approved) {
        super(id);
        this.loanId = loanId;
        this.approved = approved;
    }
}
