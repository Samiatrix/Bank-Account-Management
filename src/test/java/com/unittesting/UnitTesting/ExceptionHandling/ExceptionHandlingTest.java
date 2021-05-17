package com.unittesting.UnitTesting.ExceptionHandling;

import com.unittesting.UnitTesting.Exception.ResourceNotFoundException;
import com.unittesting.UnitTesting.controller.AccountController;
import com.unittesting.UnitTesting.dao.AccountRepository;
import com.unittesting.UnitTesting.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExceptionHandlingTest {
	@Autowired
	private AccountService service;

	@MockBean
	private AccountRepository repo;
	private AccountController control;

	@Test
	public void resourceNotFoundExceptionTest(){
		when(repo.findAll()).thenThrow(ResourceNotFoundException.class);
		assertThrows(ResourceNotFoundException.class, () -> service.getAccountDetails());
	}


}
