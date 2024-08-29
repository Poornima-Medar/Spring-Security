package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.utils;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Permissions;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Permissions.*;
import static com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permissions>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(POST_CREATE,USER_UPDATE, POST_UPDATE),
            ADMIN, Set.of(POST_CREATE,USER_UPDATE, POST_UPDATE, USER_DELETE, USER_CREATE, POST_DELETE)
    );


    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role)
                .stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toSet());
    }
}
