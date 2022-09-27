package tech.seedz.template.infrastructure.events.incoming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tech.seedz.template.generated.loan.LoanApprovalDecisionEvent;
import tech.seedz.template.infrastructure.events.incoming.mapper.LoanApprovalDecisionMapper;
import tech.seedz.template.service.eventhandler.LoanApprovalDecisionEventHandler;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaIncomingEventHandler {
    private final LoanApprovalDecisionEventHandler loanApprovalDecisionEventHandler;

    @KafkaListener(topics = "#{'${topic.loan.approval.decision}'}", groupId = "#{'${kafka.consumer.group}'}")
    public void consume(ConsumerRecord<String, LoanApprovalDecisionEvent> record) {
        log.info("Received kafka event for loan approval decision, loanId: {}", record.value().getLoanId());
        loanApprovalDecisionEventHandler.handle(LoanApprovalDecisionMapper.map(record.value()));
    }
}
