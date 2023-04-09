package tech.example.service.loan;

import tech.example.service.GenericException;

/*
This class is given as an example and should be replaced with real business logic
 */
public class LoanAlreadyGivenException extends GenericException {
    public LoanAlreadyGivenException(String message) {
        super(message);
    }

    @Override
    public String errorCode() {
        return "loan-already-given";
    }
}
