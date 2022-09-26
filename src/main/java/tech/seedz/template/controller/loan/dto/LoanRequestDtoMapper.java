package tech.seedz.template.controller.loan.dto;

import tech.seedz.template.dao.model.loan.LoanRequest;

public class LoanRequestDtoMapper {
    public static LoanRequestDto map(LoanRequest loanRequest) {
        return LoanRequestDto
                .builder()
                .id(loanRequest.getId())
                .clientId(loanRequest.getClientId())
                .amount(loanRequest.getAmount())
                .externallyApproved(loanRequest.getExternallyApproved())
                .build();
    }
}
