
package tech.seedz.template.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.seedz.template.dao.model.loan.LoanRequest;

import java.util.List;


/*
This class is given as an example and should be replaced with real class
 */
@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, String> {
    List<LoanRequest> findByClientId(String clientId);
}
