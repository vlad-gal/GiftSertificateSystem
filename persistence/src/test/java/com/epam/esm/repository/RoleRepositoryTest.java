package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {PersistenceConfig.class})
@ActiveProfiles("test")
@Transactional
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void whenFindRoleByRoleIdThenShouldReturnRoleUser() {
        Role defaultRole = roleRepository.findRoleByRoleId(2);
        boolean conditional = defaultRole.getRoleName().equalsIgnoreCase("user");
        assertTrue(conditional);
    }
}