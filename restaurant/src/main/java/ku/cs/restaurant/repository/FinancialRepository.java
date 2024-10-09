package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FinancialRepository extends JpaRepository<Financial, Date> {
}
