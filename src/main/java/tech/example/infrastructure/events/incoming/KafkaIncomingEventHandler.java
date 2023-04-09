package tech.example.infrastructure.events.incoming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tech.example.generated.loan.LoanApprovalResponse;
import tech.example.infrastructure.events.incoming.mapper.LoanApprovalResponseMapper;
import tech.example.service.event.handler.LoanApprovalDecisionEventHandler;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaIncomingEventHandler {
    private final LoanApprovalDecisionEventHandler loanApprovalDecisionEventHandler;
    private final KafkaEventProcessedCallback kafkaEventProcessedCallback;

    @KafkaListener(topics = "#{'${topic.loan.approval.response}'}", groupId = "#{'${kafka.consumer.group}'}")
    public void consume(ConsumerRecord<String, LoanApprovalResponse> record) {
        log.info("Received kafka event for loan approval decision, loanId: {}", record.value().getLoanId());
        loanApprovalDecisionEventHandler.handle(LoanApprovalResponseMapper.map(record.value()));
        kafkaEventProcessedCallback.handle(record.value().getId());
    }
}
