package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface FinancialRepository extends JpaRepository<Financial, LocalDate> {
}
