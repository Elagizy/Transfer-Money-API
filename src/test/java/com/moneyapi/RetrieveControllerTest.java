package com.moneyapi;

import com.moneyapi.mongo.BalanceRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RetrieveController.class)
public class RetrieveControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BalanceRepo balanceRepo;

    @Test
    public void retrieve_get() throws Exception {
        MvcResult result = mvc.perform(get("/retrieve")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.balance").exists())
                .andExpect(jsonPath("$.lastUpdate").exists())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void retrieve_post() throws Exception {
        MvcResult result = mvc.perform(post("/retrieve")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
