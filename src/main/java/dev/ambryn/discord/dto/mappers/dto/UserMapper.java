package dev.ambryn.discord.dto.mappers.dto;

import dev.ambryn.discord.beans.Role;
import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.dto.UserGetDTO;
import dev.ambryn.discord.dto.UserCreateDTO;
import dev.ambryn.discord.dto.UserGetFinestDTO;
import jakarta.enterprise.context.Dependent;

import java.util.List;
import java.util.Objects;

@Dependent
public class UserMapper {
    public static User toUser(UserCreateDTO userDTO) {
        Long id = userDTO.id();
        String email = userDTO.email();
        String password = userDTO.password();
        String lastname = userDTO.lastname();
        String firstname = userDTO.firstname();

        User user = new User();
        user.setId(id);
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
