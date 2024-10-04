package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.Receipt.CreateRequest;
import ku.cs.restaurant.entity.Receipt;
import ku.cs.restaurant.service.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ReceiptController {
    private final ReceiptService service;

    public ReceiptController(ReceiptService service) {
        this.service = service;
    }

    @PostMapping("/receipt")
    public ResponseEntity<Receipt> createReceipt(@RequestBody CreateRequest receipt) {
        try {
            Receipt receipts = service.createReceipt(receipt);
            return new ResponseEntity<>(receipts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/receipt")
    public ResponseEntity<List<Receipt>> getReceipts() {
        List<Receipt> receipts = service.getAllReceipts();
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable UUID id) {
        Optional<Receipt> optionalReceipt = service.getReceiptById(id);
        if (optionalReceipt.isPresent()) {
            return new ResponseEntity<>(optionalReceipt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
