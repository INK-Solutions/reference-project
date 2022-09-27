package tech.seedz.template.infrastructure.events.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tech.seedz.template.generated.loan.LoanApprovalRequest;
import tech.seedz.template.infrastructure.events.outgoing.mapper.LoanApprovalRequestMapper;
import tech.seedz.template.service.event.outgoing.EventDispatcher;
import tech.seedz.template.service.event.outgoing.LoanRequestCreatedEvent;
import tech.seedz.template.service.event.outgoing.PublishableEvent;


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
    public void dispatch(PublishableEvent event) {
        if (event instanceof LoanRequestCreatedEvent) {
            var loanCreatedEvent = (LoanRequestCreatedEvent) event;
            log.info("Dispatching loan approval request with id: {}, loanId: {}", loanCreatedEvent.getId(), loanCreatedEvent.getLoanId());
            loanApprovalTemplate.send(loanApprovalRequestTopic, loanCreatedEvent.getLoanId(), LoanApprovalRequestMapper.map(loanCreatedEvent));
        }
    }
}
