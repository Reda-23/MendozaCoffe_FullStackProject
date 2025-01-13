package org.mendozaproject.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mendozaproject.models.Item;
import org.mendozaproject.repos.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    private Item item;


    @BeforeEach
    void setUp() {
        item = new Item(1,"Orange jus",33,"the best jus");
        item = itemRepository.save(item);
    }

    @AfterEach
    void tearDown() {
        itemRepository.delete(item);
    }


    @Test
    public void givenAnItemWhenSaveThenCheckIfSaved(){
        Item savedItem = itemRepository.findItemByItemId(item.getItemId());
        assertNotNull(savedItem);
        assertEquals(savedItem.getName(),item.getName());
        assertEquals(savedItem.getPrice(),item.getPrice());
        assertEquals(savedItem.getDescription(),item.getDescription());
    }
}
