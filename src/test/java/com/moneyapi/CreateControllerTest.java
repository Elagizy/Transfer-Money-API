package com.moneyapi;

import com.moneyapi.mongo.BalanceRepo;
import com.moneyapi.mongo.TransactionsRepo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CreateController.class)
public class CreateControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestTemplate restTemplate;
    @MockBean
    private BalanceRepo balanceRepo;
    @MockBean
    private TransactionsRepo transactionsRepo;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void create_get() throws Exception {
        MvcResult result = mvc.perform(get("/create")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void create_post_unsupported_media() throws Exception {
        MvcResult result = mvc.perform(post("/create")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test(expected = NestedServletException.class)
    public void create_post_invalid_value() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("amount", "", "", "abc".getBytes());
        MvcResult result = mvc.perform(multipart("/create")
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isInternalServerError())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void create_post_ok() throws Exception {
        String testValue = "12.3";
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(manyTimes(), requestTo("http://localhost:8080/retrieve")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{ \"balance\" : \"" + testValue + "\", \"lastUpdate\" : \"Tue Jul 23 21:05:08 CST 2019\"}", MediaType.APPLICATION_JSON));
        MockMultipartFile multipartFile = new MockMultipartFile("amount", "", "", testValue.getBytes());
        MvcResult result = mvc.perform(multipart("/create")
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.amount").value(testValue))
                .andExpect(jsonPath("$.currentBalance").value(testValue))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
