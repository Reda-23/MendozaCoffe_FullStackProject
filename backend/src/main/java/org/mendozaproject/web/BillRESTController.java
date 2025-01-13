package org.mendozaproject.web;


import org.mendozaproject.models.Bill;
import org.mendozaproject.services.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/bill")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class BillRESTController {



    private BillService billService;

    public BillRESTController(BillService billService) {
        this.billService = billService;
    }


    @PostMapping("/{orderId}/{paymentMethod}/{tax}")
    public ResponseEntity<Bill> createBill(@PathVariable Integer orderId,@PathVariable int paymentMethod,@PathVariable int tax){
        Bill bill = billService.createBill( orderId, paymentMethod, tax);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    @GetMapping("/bills")
    public ResponseEntity<List<Bill>> billsHistory(){
        List<Bill> bills = billService.billsHistory();
        return new ResponseEntity<>(bills,HttpStatus.OK);
    }
}
