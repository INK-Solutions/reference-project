package tech.seedz.template.systemtest.kafka;

import house.inksoftware.systemtest.domain.config.infra.kafka.AvroSchemaWithClass;
import tech.seedz.template.generated.loan.LoanApprovalRequest;
import tech.seedz.template.generated.loan.LoanApprovalResponse;

import java.util.Map;

public class SchemaWithClassFactory {
    public static Map<String, AvroSchemaWithClass> create() {
        return Map.of(
                "template.project.loan.approval.request", new AvroSchemaWithClass(LoanApprovalRequest.SCHEMA$, LoanApprovalRequest.class),
                "template.project.loan.approval.response", new AvroSchemaWithClass(LoanApprovalResponse.SCHEMA$, LoanApprovalResponse.class)
        );
    }
}
