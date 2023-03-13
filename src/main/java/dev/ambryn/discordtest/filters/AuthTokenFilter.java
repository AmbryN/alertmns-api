package dev.ambryn.discordtest.filters;

import dev.ambryn.discordtest.beans.Role;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.enums.ERole;
import dev.ambryn.discordtest.errors.UnauthorizedException;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.security.JwtUtils;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

@Provider
@Authorize
@Priority(Priorities.AUTHORIZATION)
public class AuthTokenFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;
    @Inject
    private JwtUtils jwt;
    @Inject
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        boolean hasPermission =
                Optional.ofNullable(requestContext.getHeaderString("Authorization"))
                        .flatMap(token -> {
                            if (jwt.validateJwtToken(token)) {
                                return userRepository.getUserByEmail(jwt.getEmailFromToken(token))
                                        .map(User::getRoles)
                                        .map(roles -> roles.stream().map(Role::getName))
                                        .map(roleNames -> roleNames.anyMatch(erole -> erole == getAuthLevel()))
                                        .filter(hasAdequateAuthLevel -> hasAdequateAuthLevel);
                            } else return Optional.empty();
                        })
                        .orElseGet(() -> false);

        if (!hasPermission)
            throw new UnauthorizedException("You don't have permissions to perform this action");
    }

    public ERole getAuthLevel() {
        Class<?> resourceClass = resourceInfo.getResourceClass();
        Method resourceMethod = resourceInfo.getResourceMethod();
        System.out.println(resourceMethod);

        return Optional.ofNullable(resourceMethod.getAnnotation(Authorize.class))
                .map(Authorize::level)
                .orElseGet(() ->
                        Optional.ofNullable(resourceClass.getAnnotation(Authorize.class))
                                .map(Authorize::level)
                                .orElse(ERole.USER));
    }
}
