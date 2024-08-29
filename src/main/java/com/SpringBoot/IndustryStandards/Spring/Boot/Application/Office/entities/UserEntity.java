package com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities;

import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Permissions;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.entities.enums.Role;
import com.SpringBoot.IndustryStandards.Spring.Boot.Application.Office.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
public class UserEntity implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /*@ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Permissions> permissions;*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role-> new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toSet());

        permissions.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.name()));
        });
*/

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role-> {
            Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritiesForRole(role);
            authorities.addAll(permissions);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
