package ru.sd.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String username;
    private String passwordOrigin;
    private String passwordConfirm;
    private boolean isSignedIn;

    @Override
    public String toString() {
    return "Account of " + username;
    }
}
