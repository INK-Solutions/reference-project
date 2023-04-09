
package tech.example.controller.loan;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.example.controller.loan.dto.LoanRequestCreateDto;
import tech.example.controller.loan.dto.LoanRequestDto;
import tech.example.service.loan.LoanRequestService;

import javax.validation.Valid;
/*
    This class is given as an example and should be replaced with real business logic
 */
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

    @GetMapping("/{id}")
    public ResponseEntity<LoanRequestDto> findByClientId(@PathVariable String id) {
        log.info("Obtaining loan request for loan with id {}", id);
        return ResponseEntity.ok(loanRequestService.findById(id));
    }
}
