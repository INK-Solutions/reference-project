package tech.seedz.template.systemtest.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.seedz.template.generated.loan.LoanApprovalRequest;

import java.util.Map;

@Service
public class LosKafkaAvroValueDeserializerFactory {
    @Value(value = "topic.loan.approval.request")
    private String topicLoanApprovalRequest;

    public LosKafkaAvroValueDeserializer create() {
        return new LosKafkaAvroValueDeserializer(
                Map.of(topicLoanApprovalRequest, LoanApprovalRequest.SCHEMA$)
        );
    }
}
