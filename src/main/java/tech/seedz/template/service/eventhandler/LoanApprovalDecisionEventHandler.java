package tech.seedz.template.service.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.seedz.template.dao.repository.LoanRequestRepository;
import tech.seedz.template.service.event.incoming.LoanApprovalResponse;

/*
This class is given as an example and should be replaced with real handler
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoanApprovalDecisionEventHandler implements IncomingEventHandler<LoanApprovalResponse> {
    private final LoanRequestRepository loanRequestRepository;

    @Override
    public void handle(LoanApprovalResponse event) {
        log.info("Handling loan approval decision event with id: {}, loan id: {}", event.getId(), event.getLoanId());

        loanRequestRepository
                .findById(event.getLoanId())
                .orElseThrow(() -> new IllegalArgumentException("Loan with id " + event.getLoanId() + " is not found"));
    }
}
