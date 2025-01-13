package org.mendozaproject.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mendozaproject.enums.PaymentMethod;
import org.mendozaproject.models.Bill;
import org.mendozaproject.models.Customer;
import org.mendozaproject.models.Order;
import org.mendozaproject.services.BillService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;



@SpringBootTest
@AutoConfigureMockMvc
public class BillControllerTest {

    @MockBean
    private BillService billService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Bill bill;


    @BeforeEach
    void setUp() {
        bill = new Bill(1,new Order(),478,2.5,523, PaymentMethod.MOBILE_APPLICATION);
    }



    @Test
    @WithMockUser
    public void givenValidInfo_createBill() throws Exception {
        Mockito.when(billService.createBill(1,3,20)).thenReturn(bill);
        ResultActions actions = mockMvc.perform(post("/v1/bill/1/1/3/20")
                .content(objectMapper.writeValueAsString(bill))
                .contentType(MediaType.APPLICATION_JSON));
        actions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.finalPrice",is(bill.getFinalPrice())))
                .andExpect(jsonPath("$.tax",is(bill.getTax())));


        Mockito.verify(billService).createBill(1,3,20);
    }

    @Test
    @WithMockUser
    public void shouldReturnListOfBills() throws Exception {
        Bill bill1 = new Bill(2,new Order(),122,2.5,255, PaymentMethod.CASH);
        List<Bill> bb = List.of(bill,bill1);
        Mockito.when(billService.billsHistory()).thenReturn(bb);

        ResultActions actions = mockMvc.perform(get("/v1/bill/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bb)));
        actions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(bb.size())));

        Mockito.verify(billService).billsHistory();


    }
}
