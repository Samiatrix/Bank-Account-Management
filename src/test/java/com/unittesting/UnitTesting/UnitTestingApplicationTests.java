package com.unittesting.UnitTesting;

import com.unittesting.UnitTesting.controller.AccountController;
import com.unittesting.UnitTesting.dao.AccountRepository;
import com.unittesting.UnitTesting.model.Account;
import com.unittesting.UnitTesting.service.AccountService;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UnitTestingApplicationTests {
	@Autowired
	private AccountService service;

	@MockBean
	private AccountRepository repo;
	private AccountController control;


	@Test
	public void getDataTest(){
		when(repo.findAll()).thenReturn(Stream.of(new Account(1, "name", "Saving", 60000), new Account(2, "Haridas", "Current", 90000)).collect(Collectors.toList()));
		assertEquals(2, service.getData().size());
	}

	@Test
	public void getDataByIdTest(){
		int id = 2;
		when(repo.findById(id)).thenReturn(java.util.Optional.of(new Account(2, "name", "Saving", 60000)));
		assertEquals(2, service.getDataById(id).getAccId());
	}

	@Test
	public void getDataByNameTest(){
		String name = "name";
		when(repo.findByName(name)).thenReturn(Stream.of(new Account(1, "name", "Saving", 60000), new Account(2, "name", "Current", 90000)).collect(Collectors.toList()));
		assertEquals(2, service.getDataByName(name).size());
	}

	@Test
	public void saveDataTest(){
		Account account = new Account(3, "name", "Current", 140000);
		when(repo.save(account)).thenReturn(account);
		assertEquals(account, service.saveData(account));
	}

//	@Test
//	public void updateDataTest(){
//		Account user = new Account(8, "name", "SAVING", 402200);
//		System.out.println(user);
//		given(repo.save(user)).willReturn(user);
//		Account expected = service.updateAccountData(user);
//		System.out.println(expected);
//		assertThat(expected).isNotNull();
//		verify(repo).save(any(Account.class));
//
//	}

	@Test
	public void deleteDataTest(){
		Account account = new Account(3, "name", "Current", 140000);
		service.deleteData(3);
		verify(repo, times(1)).deleteById(3);
	}


}
