package ru.sd.app.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.sd.web.dto.Account;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository implements ProjectRepository<Account> {
    private Logger logger = Logger.getLogger(AccountRepository.class);
    private List<Account> accountList = new ArrayList<>();
    private boolean isSignedIn;

    @Override
    public List<Account> retreiveAll() {
        return new ArrayList<>(accountList);
    }

    @Override
    public void store(Account account) {

        if (account.getUsername().length() > 0 &&
                account.getPasswordOrigin().length() > 0 &&
                account.getPasswordConfirm().length() > 0 &&
                account.getPasswordOrigin().equals(account.getPasswordConfirm())) {
            for (Account acc: retreiveAll()) {
                if(acc.getUsername().equals(account.getUsername()))
                    logger.info("This username is already busy");
                    return;
            }
            logger.info("check confirm password");
            isSignedIn = accountList.add(account);
        } else isSignedIn = false;
        logger.debug(isSignedIn);
        account.setSignedIn(isSignedIn);
    }

    @Override
    public boolean removeItemById(Account itemToRemove) {
        for (Account acc : retreiveAll()) {
            if (acc.equals(itemToRemove)) {
                accountList.remove(acc);
            }
        }
        return false;
    }
}
