package org.mendozaproject.web;


import org.mendozaproject.models.Waiter;
import org.mendozaproject.services.WaiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/waiter/")
@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
public class WaiterRESTController {


    private WaiterService waiterService;

    public WaiterRESTController(WaiterService waiterService) {
        this.waiterService = waiterService;
    }

    @PostMapping("/")
    public ResponseEntity<Waiter> saveWaiter(@RequestBody Waiter waiter){
        Waiter savedWaiter = waiterService.saveWaiter(waiter);
        return new ResponseEntity<>(savedWaiter, HttpStatus.CREATED);
    }
    @GetMapping("/{waiterId}")
    public ResponseEntity<Optional<Waiter>> findWaiterById(@PathVariable Integer waiterId){
        Optional<Waiter> waiter = waiterService.getWaiterById(waiterId);
        return new ResponseEntity<>(waiter,HttpStatus.OK);
    }

    @GetMapping("/waiters")
    public ResponseEntity<List<Waiter>> waitersList(){
        List<Waiter> waiters = waiterService.getWaiters();
        return new ResponseEntity<>(waiters,HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteWaiter(@PathVariable Integer waiterId){
        waiterService.deleteWaiter(waiterId);
        return new ResponseEntity<>("waiter deleted ",HttpStatus.OK);
    }
}
