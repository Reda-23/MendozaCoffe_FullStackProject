package org.mendozaproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mendozaproject.models.Item;
import org.mendozaproject.repos.ItemRepository;
import org.mendozaproject.services.impl.ItemServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
public class ItemServiceTest {


    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    private Item item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        item = new Item(1,"Coffee",12,"the best in the town");
    }

    @Test
    public void givenAValidItem_checkIfSavedCorrectly(){
        Mockito.when(itemRepository.save(item)).thenReturn(item);
        Item savedItem = itemService.saveItem(item);

        Assertions.assertEquals(item,savedItem);
        Assertions.assertNotNull(savedItem);

        Mockito.verify(itemRepository).save(item);
    }

    @Test
    public void givenAnId_returnAnItem(){
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        Optional<Item> it = itemService.findItemById(1);

        Assertions.assertNotNull(it);
        Assertions.assertEquals(it.isPresent(),true);


        Mockito.verify(itemRepository).findById(1);
    }

    @Test
    public void givenNonValidId_throwAnException(){
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception =Assertions.assertThrows(RuntimeException.class,()-> itemService.findItemById(1),"Item is not found " + item.getItemId());

        Assertions.assertEquals(exception.getMessage(),"Item is not found " + item.getItemId());

        Mockito.verify(itemRepository).findById(1);
    }

    @Test
    public void testUpdateItem_shouldReturnNewItem(){
        Mockito.when(itemRepository.findItemByItemId(1)).thenReturn(item);
        item.setName("orange jus");
        item.setDescription("the best");
        Mockito.when(itemRepository.save(item)).thenReturn(item);

        Item updatedItem = itemService.updateItem(item,1);

        Assertions.assertEquals(updatedItem.getName(),item.getName());
        Assertions.assertEquals(item.getDescription(),updatedItem.getDescription());
        Assertions.assertNotNull(updatedItem,"upd item should not be null");

        Mockito.verify(itemRepository).findItemByItemId(1);
        Mockito.verify(itemRepository).save(item);
    }

    @Test
    public void checkIfGetItems_returnsListOfItems(){
        Item item1 = new Item(2,"Thé",13,"fresh iced thé");
        Mockito.when(itemRepository.findAll()).thenReturn(List.of(item,item1));

        List<Item> items =(List<Item>) itemService.getItems(1,2);


        Assertions.assertNotNull(items);
        Assertions.assertEquals(items.size(),2);

        Mockito.verify(itemRepository).findAll();
    }
}
