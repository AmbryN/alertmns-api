package dev.ambryn.discordtest.dto.mappers.dto;

import dev.ambryn.discordtest.beans.Role;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.RoleGetDTO;
import dev.ambryn.discordtest.dto.UserGetDTO;
import dev.ambryn.discordtest.dto.UserCreateDTO;
import dev.ambryn.discordtest.dto.UserGetFinestDTO;
import jakarta.enterprise.context.Dependent;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Dependent
public class UserMapper {
    public static User toUser(UserCreateDTO userDTO) {
        String email = userDTO.email();
        String password = userDTO.password();
        String lastname = userDTO.lastname();
        String firstname = userDTO.firstname();

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setLastname(lastname);
        user.setFirstname(firstname);

        return user;
    }

    public static UserGetDTO toDto(User user) {
        Long id = user.getId();
        String email = user.getEmail();
        String lastname = user.getLastname();
        String firstname = user.getFirstname();
        return new UserGetDTO(id, email, lastname, firstname);
    }

    public static UserGetFinestDTO toFinestDto(User user) {
        Long id = user.getId();
        String email = user.getEmail();
        String lastname = user.getLastname();
        String firstname = user.getFirstname();
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .map(Objects::toString)
                .toList();
        return new UserGetFinestDTO(id, email, lastname, firstname, roles);
    }
}
