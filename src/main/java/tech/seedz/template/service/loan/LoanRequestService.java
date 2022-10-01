package tech.seedz.template.service.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.seedz.template.controller.loan.dto.LoanRequestCreateDto;
import tech.seedz.template.controller.loan.dto.LoanRequestDto;
import tech.seedz.template.controller.loan.dto.LoanRequestDtoMapper;
import tech.seedz.template.dao.model.loan.LoanRequest;
import tech.seedz.template.dao.repository.LoanRequestRepository;
import tech.seedz.template.service.event.outgoing.EventDispatcher;
import tech.seedz.template.service.event.outgoing.LoanRequestCreatedEvent;

import java.util.List;
import java.util.stream.Collectors;

/*
This class is given as an example and should be replaced with real handler
 */
@RequiredArgsConstructor
@Service
public class LoanRequestService {

    private final LoanRequestRepository repository;
    private final EventDispatcher eventDispatcher;


    public LoanRequestDto create(LoanRequestCreateDto dto) {
        var clientAppliedAlready = !repository.findByClientId(dto.getClientId()).isEmpty();
//        if (clientAppliedAlready) {
//            throw new IllegalArgumentException("Client with id " + dto.getClientId() + " applied for the loan already");
//        } else {
            LoanRequest loanRequest = new LoanRequest();
            loanRequest.setClientId(dto.getClientId());
            loanRequest.setAmount(dto.getAmount());
            loanRequest.setExternallyApproved(false);
            repository.save(loanRequest);

            eventDispatcher.dispatch(LoanRequestCreatedEvent.from(loanRequest));
//
            return LoanRequestDtoMapper.map(loanRequest);
//        }
    }

    public List<LoanRequestDto> findByClientId(String clientId) {
        return repository
                .findByClientId(clientId)
                .stream()
                .map(LoanRequestDtoMapper::map)
                .collect(Collectors.toList());
    }
}
