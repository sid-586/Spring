package ru.sd.app.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sd.web.dto.Account;
import ru.sd.web.dto.LoginForm;

@Service
public class LoginService {
    private Logger logger = Logger.getLogger(LoginService.class);
    private final ProjectRepository<Account> accountRepo;

    @Autowired
    public LoginService(ProjectRepository<Account> accountRepo) {
        this.accountRepo = accountRepo;
    }

    public boolean authentificate(LoginForm loginForm) {
        logger.info("try auth with loginform: " + loginForm);
        boolean isAuthentificate = false;
        for (Account account : accountRepo.retreiveAll()) {
            if (loginForm.getUsername().equals(account.getUsername())
                    && loginForm.getPassword().equals(account.getPasswordOrigin())) {
                isAuthentificate = true;
            }
        }
        return isAuthentificate;
    }

    public boolean registrate(Account account) {
        logger.debug("checking valid account");
        accountRepo.store(account);
        logger.debug(account.isSignedIn());
        return account.isSignedIn();
    }
}
