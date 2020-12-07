package ru.sd.app.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sd.web.dto.Account;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository implements ProjectRepository<Account> {
    private final Logger logger = Logger.getLogger(AccountRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> retreiveAll() {
        List<Account> accounts = jdbcTemplate.query("SELECT * FROM accounts",
                (ResultSet rs, int rowNum) -> {
                    Account acc = new Account();
                    acc.setUsername(rs.getString("username"));
                    acc.setPassword(rs.getString("password"));
                    acc.setPasswordConfirm(rs.getString("password"));
                    return acc;
                });

        return new ArrayList<>(accounts);
    }

    @Override
    public void store(Account account) {
        MapSqlParameterSource paramSourse = new MapSqlParameterSource();
        paramSourse.addValue("username", account.getUsername());
        paramSourse.addValue("password", account.getPassword());
        paramSourse.addValue("role", "USER");
        jdbcTemplate.update("INSERT INTO accounts(username,password) VALUES" +
                "(:username,:password)", paramSourse);
        jdbcTemplate.update("INSERT INTO roles(username,role) VALUES" +
                "(:username,:role)", paramSourse);
        logger.debug("Account " + account.toString() + "was added. " +
                "accountsDBSize " + retreiveAll().size());
    }

    @Override
    public boolean removeItem(Account itemToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", itemToRemove.getUsername());
        jdbcTemplate.update("DELETE FROM accounts WHERE username = :username",
                parameterSource);
        logger.info("Account " + itemToRemove.toString() + "was removed. " +
                "accountsDBSize " + retreiveAll().size());
        return true;
    }
}
