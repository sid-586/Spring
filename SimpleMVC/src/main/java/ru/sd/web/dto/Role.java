package ru.sd.web.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
public class Role implements GrantedAuthority {
   private String name;
   private Set<Account> accountsWithRole;

    @Override
    public String getAuthority() {
        return getName();
    }
}
