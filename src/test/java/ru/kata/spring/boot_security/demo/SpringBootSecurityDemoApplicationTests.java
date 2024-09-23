package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImp;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RoleDaoImpTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private TypedQuery<Role> query;

	@InjectMocks
	private RoleDaoImp roleDao;  // Тестируемая сущность

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetUserRoles() {
		Long userId = 1L;

		// Mocking данных ролей
		Role role1 = new Role("USER");
		Role role2 = new Role("ADMIN");
		List<Role> roles = Arrays.asList(role1, role2);

		// Mocking поведение EntityManager и TypedQuery
		when(entityManager.createQuery(anyString(), eq(Role.class))).thenReturn(query);
		when(query.setParameter("id", userId)).thenReturn(query);
		when(query.getResultList()).thenReturn(roles);

		// Вызов тестируемого метода
		List<Role> resultRoles = roleDao.getUserRoles(userId);

		// Проверка результата
		assertNotNull(resultRoles);
		assertEquals(2, resultRoles.size());
		assertTrue(resultRoles.contains(role1));
		assertTrue(resultRoles.contains(role2));

		// Проверка, что EntityManager и Query были вызваны корректно
		verify(entityManager).createQuery("SELECT u.roles FROM User u WHERE u.id = :id", Role.class);
		verify(query).setParameter("id", userId);
		verify(query).getResultList();
	}
}