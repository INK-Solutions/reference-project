package tech.example.infrastructure.events.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tech.example.service.event.outgoing.EventDispatcher;
import tech.example.service.event.outgoing.LoanRequestCreatedEvent;
import tech.example.service.event.outgoing.OutgoingEvent;
import tech.example.generated.loan.LoanApprovalRequest;
import tech.example.infrastructure.events.outgoing.mapper.LoanApprovalRequestMapper;


/*
This is a good class that should stay, but loanApprovalTemplate & loanApprovalRequestTopic are part of template
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaEventDispatcher implements EventDispatcher {
    private final KafkaTemplate<String, LoanApprovalRequest> loanApprovalTemplate;

    @Value("${topic.loan.approval.request}")
    private String loanApprovalRequestTopic;

    @Override
    public void dispatch(OutgoingEvent event) {
        if (event instanceof LoanRequestCreatedEvent) {
            var loanCreatedEvent = (LoanRequestCreatedEvent) event;
            log.info("Dispatching loan approval request with id: {}, loanId: {}", loanCreatedEvent.getId(), loanCreatedEvent.getLoanId());
            loanApprovalTemplate.send(loanApprovalRequestTopic, loanCreatedEvent.getLoanId(), LoanApprovalRequestMapper.map(loanCreatedEvent));
        }
    }
}
