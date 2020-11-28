package ru.sd.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @NotEmpty
    @Size(min = 5)
    private String username;
    @NotEmpty
    private String passwordOrigin;
    @NotEmpty
    private String passwordConfirm;
    private boolean isSignedIn;

    @Override
    public String toString() {
        return "Account of " + username;
    }
}
