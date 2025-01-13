package org.mendozaproject.repos;

import org.mendozaproject.models.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter,Integer> {




    Waiter findWaiterByWaiterId(Integer waiterId);
}
