package ru.sd.app.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.sd.web.dto.Account;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository implements ProjectRepository<Account> {
    private final Logger logger = Logger.getLogger(AccountRepository.class);
    private final List<Account> accountList = new ArrayList<>();

    @Override
    public List<Account> retreiveAll() {
        return new ArrayList<>(accountList);
    }

    @Override
    public void store(Account account) {
        accountList.add(account);
        logger.debug("accountList.size " + accountList.size());
    }

    @Override
    public boolean removeItem(Account itemToRemove) {

        return accountList.remove(itemToRemove);
    }
}
