
package tech.example.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.example.dao.model.loan.LoanRequest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


/*
This class is given as an example and should be replaced with real business logic
 */
@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, String> {
    Optional<LoanRequest> findByClientId(String clientId);
}
