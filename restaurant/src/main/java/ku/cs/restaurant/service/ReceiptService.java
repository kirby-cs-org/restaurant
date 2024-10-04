package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.Receipt.CreateRequest;
import ku.cs.restaurant.entity.Receipt;
import ku.cs.restaurant.repository.ReceiptRepository;

import java.util.Optional;
import java.util.UUID;


public class ReceiptService {
    private ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public Receipt createReceipt(CreateRequest receiptRequest) {
        Receipt newReceipt = new Receipt();
        newReceipt.setTotal(receiptRequest.getTotal());
        newReceipt.setAmount(receiptRequest.getAmonut());

        return receiptRepository.save(newReceipt);
    }

    public Optional<Receipt> getReceipt(UUID id) {
        return receiptRepository.findById(id);
    }
}

