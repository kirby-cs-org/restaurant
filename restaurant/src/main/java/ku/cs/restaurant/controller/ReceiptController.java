package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.Receipt.CreateRequest;
import ku.cs.restaurant.entity.Receipt;
import ku.cs.restaurant.service.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public class ReceiptController {

    private ReceiptService service;

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
    public ResponseEntity<Receipt> getReceiptById(@RequestBody UUID id) {
        return service.getReceipt(id)
                .map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
