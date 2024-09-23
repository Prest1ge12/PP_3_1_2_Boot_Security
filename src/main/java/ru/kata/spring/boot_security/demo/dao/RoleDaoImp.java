package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAvailableRoles() {
        String jpql = "SELECT u FROM Role u";
        TypedQuery<Role> query = entityManager.createQuery(jpql, Role.class);
        return query.getResultList();
    }

    @Override
    public List<Role> getUserRoles(Long id) {
        String jpql = "SELECT r FROM User u JOIN u.roles r WHERE u.id = :id";  // Получаем роли пользователя по его id
        TypedQuery<Role> query = entityManager.createQuery(jpql, Role.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Role getRoleByID(Long id) {
        try {
            String jpql = "SELECT r FROM Role r WHERE r.id = :id";
            TypedQuery<Role> query = entityManager.createQuery(jpql, Role.class);
            query.setParameter("id", id);

            Role role = query.getSingleResult();
            return role;
        } catch (NoResultException e) {
            // Если роль не найдена, возвращаем пустой список
            return null;
        }
    }
}
