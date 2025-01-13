package org.mendozaproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.mendozaproject.models.Item;
import org.mendozaproject.repos.ItemRepository;
import org.mendozaproject.services.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {


    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> findItemById(Integer itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.isPresent()) throw new RuntimeException("Item is not found " + itemId);
        return item;
    }

    @Override
    public Page<Item> getItems(int page, int size) {
        return  itemRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    @Override
    public Item updateItem(Item item, Integer itemId) {
        Item itm = itemRepository.findItemByItemId(itemId);
        if (item == null) throw new RuntimeException("Item is not found " + itemId);
        itm.setItemId(itm.getItemId());
        itm.setName(item.getName());
        itm.setDescription(item.getDescription());
        itm.setPrice(item.getPrice());
        var updItem = itemRepository.save(itm);
        return updItem;
    }
}
