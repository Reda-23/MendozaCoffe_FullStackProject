package org.mendozaproject.web;


import org.mendozaproject.models.Item;
import org.mendozaproject.services.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/item")
@PreAuthorize("hasAuthority('ADMIN')")
public class ItemRESTController {

    private ItemService itemService;


    public ItemRESTController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping("/")
    public ResponseEntity<Item> saveItem(@RequestBody Item item){
        Item savedItem = itemService.saveItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Optional<Item>> findItemById(@PathVariable Integer itemId){
        Optional<Item> item = itemService.findItemById(itemId);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }

    @GetMapping("/itemsP")
    public ResponseEntity<Page<Item>> itemsList(@RequestParam(defaultValue ="0",name = "page") int page
                                                ,@RequestParam(defaultValue = "5",name = "size") int size){
        Page<Item> items = itemService.getItems(page, size);
        return new ResponseEntity<>(items,HttpStatus.OK);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@RequestBody Item item,@PathVariable Integer itemId){
        Item updtItem = itemService.updateItem(item,itemId);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> allItems(){
        List<Item> items = itemService.findAllItems();
        return new ResponseEntity<>(items,HttpStatus.OK);
    }

}
