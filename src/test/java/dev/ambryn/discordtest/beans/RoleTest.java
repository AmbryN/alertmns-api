package dev.ambryn.discordtest.beans;

import dev.ambryn.discordtest.enums.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    Role role;

    @BeforeEach
    void setup() {
        role = new Role(ERole.ADMIN);
    }
    @Test
    void getName() {
        assertEquals(ERole.ADMIN, role.getName());
    }
}