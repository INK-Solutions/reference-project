package tech.example.service.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.example.controller.loan.dto.LoanRequestCreateDto;
import tech.example.controller.loan.dto.LoanRequestDto;
import tech.example.dao.model.loan.LoanRequest;
import tech.example.dao.repository.LoanRequestRepository;
import tech.example.service.event.outgoing.EventDispatcher;
import tech.example.service.event.outgoing.LoanRequestCreatedEvent;
import tech.example.service.EntityNotFoundException;

/*
This class is given as an example and should be replaced with real business logic
 */
@RequiredArgsConstructor
@Service
public class LoanRequestService {

    private final LoanRequestRepository repository;
    private final EventDispatcher eventDispatcher;


    public LoanRequestDto create(LoanRequestCreateDto dto) {
        assertFirstLoan(dto.getClientId());

        LoanRequest loanRequest = LoanRequestFactory.newLoanRequest(dto.getClientId(), dto.getAmount());

        repository.save(loanRequest);
        eventDispatcher.dispatch(LoanRequestCreatedEvent.from(loanRequest));

        return LoanRequestDtoMapper.map(loanRequest);
    }

    private void assertFirstLoan(String clientId) {
        var loan = repository.findByClientId(clientId);

        if (loan.isPresent()) {
            throw new LoanAlreadyGivenException("Loan for customer " + clientId + " was already given");
        }
    }

    public LoanRequestDto findById(String id) {
        return repository
                .findById(id)
                .map(LoanRequestDtoMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Loan request with id " + id + " is not found"));
    }
}
