package tech.example.service.loan;

import tech.example.controller.loan.dto.LoanRequestDto;
import tech.example.dao.model.loan.LoanRequest;

public class LoanRequestDtoMapper {
    public static LoanRequestDto map(LoanRequest loanRequest) {
        return LoanRequestDto
                .builder()
                .id(loanRequest.getId())
                .clientId(loanRequest.getClientId())
                .amount(loanRequest.getAmount())
                .approved(loanRequest.getApproved())
                .build();
    }
}
