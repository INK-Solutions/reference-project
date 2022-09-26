
package tech.seedz.template.controller.loan;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.seedz.template.controller.loan.dto.LoanRequestCreateDto;
import tech.seedz.template.controller.loan.dto.LoanRequestDto;
import tech.seedz.template.service.loan.LoanRequestService;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/loan-requests")
public class LoanRequestController {
    private final LoanRequestService loanRequestService;

    @PostMapping
    public ResponseEntity<LoanRequestDto> createLoanRequest(@Valid @RequestBody LoanRequestCreateDto dto) {
        log.info("Creating loan request for client: {}", dto.getClientId());
        return ResponseEntity.ok(loanRequestService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<LoanRequestDto>> findByClientId(@RequestParam(name = "client-id") String clientId) {
        log.info("Obtaining loan requests for client with id {}", clientId);
        return ResponseEntity.ok(loanRequestService.findByClientId(clientId));
    }
}
