package ru.sd.app.services;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sd.app.exceptions.BookShelfLoginException;
import ru.sd.web.dto.Account;
import ru.sd.web.dto.LoginForm;

@Service
public class LoginService implements UserDetailsService {
    private final Logger logger = Logger.getLogger(LoginService.class);
    private final ProjectRepository<Account> accountRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginService(ProjectRepository<Account> accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Get starting loadUserByUsername");
        if (findByUserName(username)) {
            return accountRepo
                    .retreiveAll()
                    .stream()
                    .filter(f -> f.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
        } else {
            logger.info("!!!No such username");
            throw new UsernameNotFoundException("No such username");
        }
    }

    public boolean registrate(Account account) throws BookShelfLoginException {
        logger.debug("checking valid account");
        if (account.getPassword().equals(account.getPasswordConfirm())) {
            logger.info("check confirm password");
            for (Account acc : accountRepo.retreiveAll()) {
                if (acc.getUsername().equals(account.getUsername())) {
                    logger.info("This username is already busy");
                    throw new BookShelfLoginException("This username is already busy");
                }
            }
            account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            accountRepo.store(account);
        }
        return true;
    }

    public boolean findByUserName(String username) {
        for (Account acc : accountRepo.retreiveAll()) {
            if (acc.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public void removeAccount(Account account) {
        for (Account acc : accountRepo.retreiveAll()) {
            if (acc.equals(account)) {
                accountRepo.removeItem(acc);
            }
        }
    }
}
