package org.mendozaproject.repos;

import org.mendozaproject.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {



    Item findItemByItemId(Integer itemId);
    Page<Item> findAll(Pageable pageable);
}
