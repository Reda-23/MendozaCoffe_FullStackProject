package org.mendozaproject.services;

import org.mendozaproject.models.Waiter;

import java.util.List;
import java.util.Optional;

public interface WaiterService {


    Waiter saveWaiter(Waiter waiter);
    List<Waiter> getWaiters();

    Optional<Waiter> getWaiterById(Integer waiterId);

    void deleteWaiter(Integer waiterId);
}
