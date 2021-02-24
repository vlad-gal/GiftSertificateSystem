package com.epam.esm.repository;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {PersistenceConfig.class})
@EnableJpaRepositories("com.epam.esm")
@ActiveProfiles("test")
@Transactional
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void whenFindDefaultRoleThenShouldReturnRoleUser() {
        Role defaultRole = roleRepository.findDefaultRole();
        boolean conditional = defaultRole.getRoleName().equalsIgnoreCase("user");
        assertTrue(conditional);
    }
}