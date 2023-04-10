package tech.example.service.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.example.dao.repository.LoanRequestRepository;
import tech.example.service.event.incoming.LoanApprovalResponse;

/*
This class is given as an example and should be replaced with real business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoanApprovalDecisionEventHandler implements IncomingEventHandler<LoanApprovalResponse> {
    private final LoanRequestRepository loanRequestRepository;

    @Override
    public void handle(LoanApprovalResponse event) {
        log.info("Handling loan approval decision event with id: {}, loan id: {}", event.getId(), event.getLoanId());

        var loanRequest = loanRequestRepository
                .findById(event.getLoanId())
                .orElseThrow(() -> new IllegalArgumentException("Loan with id " + event.getLoanId() + " is not found"));

        loanRequest.setApproved(event.isApproved());
        loanRequestRepository.save(loanRequest);
    }
}


