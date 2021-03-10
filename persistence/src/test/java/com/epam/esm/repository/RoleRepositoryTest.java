package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
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