package com.unittesting.UnitTesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unittesting.UnitTesting.dao.AccountRepository;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockBean
    private AccountRepository repo;

    private List<Account> accounts;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.accounts = new ArrayList<>();
        this.accounts.add(new Account(3, "Sama", "SAVING",80000));
        this.accounts.add(new Account(7, "Dutt", "SAVING",40000));
        this.accounts.add(new Account(6, "Trix", "CURRENT",70000));
        this.accounts.add(new Account(8, "Trix", "CURRENT",90000));
    }

    @Test
    public void createAccountControllerTest() throws Exception {
        Account account = new Account(100, "Ran", "SAVING", 500);
        String jsonRequest = om.writeValueAsString(account);

        MvcResult result = mockMvc.perform(post("/bank/createAccount")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Response response = om.readValue(resultContent, Response.class);
        Assert.assertTrue(response.isSuccess() == Boolean.TRUE);
    }

    @Test
    void getAccountDetailsControllerTest() throws Exception{
        given(userService.getAccountDetails()).willReturn(accounts);
        this.mockMvc.perform(get("/bank/getAccountDetails"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(accounts.size()));
    }

    @Test
    void getAccountDetailsByIdControllerTest() throws Exception {
        given(userService.getAccountDetailsById(6)).willReturn(accounts.get(2));
        this.mockMvc.perform(get("/bank/getAccountDetailsById/{id}", 6))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(accounts.get(2).getName()));
    }

    @Test
    void updateAccountDetails() throws Exception{
        Account account = new Account(3, "name", "Current", 43455);
        Account newAccount = new Account(3, "newName", "Current", 434555);
        given(userService.getAccountDetailsById(3)).willReturn(account);
        given(userService.saveAccount(newAccount)).willReturn(newAccount);

        String jsonRequest = om.writeValueAsString(newAccount);

        String url = "/bank/update";
        MvcResult result = mockMvc.perform(patch(url)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
    }

    @Test
    void deleteAccountByIdTest() throws Exception{
       Integer id = 7;
       doNothing().when(userService).deleteAccountById(id);
       String url = "/bank/deleteAccountById/"+id;
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteAccountById(id);
    }
}