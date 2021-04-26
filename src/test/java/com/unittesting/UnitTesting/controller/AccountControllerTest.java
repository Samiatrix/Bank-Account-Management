package com.unittesting.UnitTesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unittesting.UnitTesting.model.Account;
import com.unittesting.UnitTesting.model.Response;
import com.unittesting.UnitTesting.service.AccountService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = AccountController.class)
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper om = new ObjectMapper();

    @InjectMocks
    private AccountController control;

    @MockBean
    private AccountService userService;

    private List<Account> accounts;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.accounts = new ArrayList<>();
        this.accounts.add(new Account(5, "Sama", "SAVING",80000));
        this.accounts.add(new Account(7, "Dutt", "SAVING",40000));
        this.accounts.add(new Account(6, "Trix", "CURRENT",70000));
    }

    @Test
    public void insertDataTest() throws Exception {
        Account account = new Account();
        account.setName("Ran");
        account.setBalance(500);
        account.setType("SAVING");
        String jsonRequest = om.writeValueAsString(account);

        MvcResult result = mockMvc.perform(post("/bank/insertData")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Response response = om.readValue(resultContent, Response.class);
        Assert.assertTrue(response.isSuccess() == Boolean.TRUE);
    }

    @Test
    void findAllData() throws Exception{
        given(userService.getData()).willReturn(accounts);
        System.out.println(accounts);
        this.mockMvc.perform(get("/bank/getData"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(accounts.size()));
    }

    @Test
    void findDataByIdTest() throws Exception {
        given(userService.getDataById(6)).willReturn(accounts.get(2));
        this.mockMvc.perform(get("/bank/getDataById/{id}", 6))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(accounts.get(2).getName()));
    }

    @Test
    void findDataByNameTest() throws Exception {
        given(userService.getDataByName("Trix")).willReturn(accounts.get(2));
        this.mockMvc.perform(get("/bank/getDataByName/{name}", "Trix"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accId").value(accounts.get(2).getAccId()));
    }

    @Test
    void updateData() throws Exception{
        Account account = new Account();
        account.setName("hhh");
        account.setType("CURRENT");
        account.setBalance(32000);
        when(userService.updateData(account)).thenReturn(account);
        String json = om.writeValueAsString(account);
        mockMvc.perform(put("/bank/update").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("hhh"));
    }

    @Test
    void deleteUser() throws Exception{
        when(userService.deleteData(7)).thenReturn("SUCCESS");
        mockMvc.perform(delete("/bank/deleteData/{id}", 7))
                .andExpect(status().isOk());
    }
}