package org.mendozaproject.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mendozaproject.models.Item;
import org.mendozaproject.repos.ItemRepository;
import org.mendozaproject.services.ItemService;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemRepository itemRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private Item item;


    @BeforeEach
    void setUp() {
        item = new Item(1,"Coffee",15,"the best coffee");
    }

    @Test
    public void givenAValidItem_shouldSaveItem() throws Exception {
        Mockito.when(itemService.saveItem(Mockito.any(Item.class))).thenReturn(item);

        ResultActions actions = mockMvc.perform(post("/v1/item/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(item)));
        actions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name",is(item.getName())))
                .andExpect(jsonPath("$.price",is(item.getPrice())));
    }

    @Test
    public void givenAnId_shouldReturnAnItem() throws Exception {
        Mockito.when(itemService.findItemById(1)).thenReturn(Optional.of(item));

        ResultActions actions = mockMvc.perform(get("/v1/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(item)))
                .andDo(print())
                .andExpectAll(jsonPath("$.name",is(item.getName())),
                        jsonPath("$.itemId",is(item.getItemId())));
    }

    @Test
    public void givenAnId_returnAnItemThenUpdate() throws Exception {
        Mockito.when(itemService.findItemById(1)).thenReturn(Optional.of(item));
        item.setName("Jus Avocat");
        item.setPrice(37);
        Mockito.when(itemService.updateItem(item,1)).thenReturn(item);

        ResultActions actions = mockMvc.perform(put("/v1/item/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(item)))
                .andDo(print())
                .andExpectAll(jsonPath("$.name",is(item.getName())),
                        jsonPath("$.itemId",is(item.getItemId())));

    }
}
