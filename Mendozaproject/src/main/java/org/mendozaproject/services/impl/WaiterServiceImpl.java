package org.mendozaproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.mendozaproject.models.Waiter;
import org.mendozaproject.repos.WaiterRepository;
import org.mendozaproject.services.WaiterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class WaiterServiceImpl implements WaiterService {


    private WaiterRepository waiterRepository;

    public WaiterServiceImpl(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    @Override
    public Waiter saveWaiter(Waiter waiter) {
        return waiterRepository.save(waiter);
    }

    @Override
    public List<Waiter> getWaiters() {
        return waiterRepository.findAll();
    }

    @Override
    public Optional<Waiter> getWaiterById(Integer waiterId) {
        Optional<Waiter> waiter = waiterRepository.findById(waiterId);
        if (!waiter.isPresent()) throw new RuntimeException("waiter is not found " + waiter);
        return waiter;
    }

    @Override
    public void deleteWaiter(Integer waiterId) {
            waiterRepository.deleteById(waiterId);
    }
}
