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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
    public void insertDataTest() throws Exception {
        Account account = new Account(100, "Ran", "SAVING", 500);
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

//    @Test
//    void updateData() throws Exception{
//        given(userService.getDataById(3)).willReturn(accounts.get(2));
//
//        Account account = new Account();
//        account.setAccId(3);
//        account.setName("hhh");
//        account.setType("CURRENT");
//        account.setBalance(32000);
//        when(userService.updateData(account)).thenReturn(account);
//        String json = om.writeValueAsString(account);
//        mockMvc.perform(put("/bank/update").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
//                .content(json).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("hhh"));
//    }

    @Test
    void deleteUser() throws Exception{
       Integer id = 7;
       doNothing().when(userService).deleteAccountById(id);
       String url = "/bank/deleteById/"+id;
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteAccountById(id);
    }
}