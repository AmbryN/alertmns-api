package dev.ambryn.discordtest.mappers.dto;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.UserGetDTO;
import dev.ambryn.discordtest.dto.UserPostDTO;
import jakarta.enterprise.context.Dependent;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

@Dependent
public class UserMapper {
    public User toUser(UserPostDTO userDTO) {
        Pbkdf2PasswordHashImpl passwordHasher = new Pbkdf2PasswordHashImpl();
        String hashedPassword = passwordHasher.generate(userDTO.getPassword().toCharArray());

        String email = userDTO.getEmail();
        String lastname = userDTO.getLastname();
        String firstname = userDTO.getFirstname();
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setLastname(lastname);
        user.setFirstname(firstname);
        return user;
    }

    public UserGetDTO toDto(User user) {
        Long id = user.getId();
        String email = user.getEmail();
        String lastname = user.getLastname();
        String firstname = user.getFirstname();
        return new UserGetDTO(id, email, lastname, firstname);
    }
}
