package org.mendozaproject.services;

import org.mendozaproject.models.Item;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item saveItem(Item item);
    Optional<Item> findItemById(Integer itemId);



    Page<Item> getItems(int page, int size);

    List<Item> findAllItems();

    Item updateItem(Item item, Integer itemId);


}
