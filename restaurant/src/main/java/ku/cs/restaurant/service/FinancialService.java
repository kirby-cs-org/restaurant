package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.financial.CreateFinancialRequest;
import ku.cs.restaurant.entity.Financial;
import ku.cs.restaurant.repository.FinancialRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class FinancialService {
    private final FinancialRepository financialRepository;

    public FinancialService(FinancialRepository financialRepository) {
        this.financialRepository = financialRepository;
    }

    public void addFinancial(CreateFinancialRequest request) {
        Date today = new Date();

        Optional<Financial> optionalFinancial = financialRepository.findById(today);

        if (optionalFinancial.isPresent()) {
            Financial exist = optionalFinancial.get();
            exist.setIncome(exist.getIncome() + request.getIncome());
            exist.setExpense(exist.getExpense() + request.getExpense());
            exist.setTotal(exist.getIncome() - exist.getExpense());
            financialRepository.save(exist);
        } else {
            Financial financial = new Financial();
            financial.setDate(today);
            financial.setIncome(request.getIncome());
            financial.setExpense(request.getExpense());
            financial.setTotal(request.getIncome() - request.getExpense());
            financialRepository.save(financial);
        }
    }
}
