package ru.sd.MyBookShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.sd.MyBookShop.data.Author;
import ru.sd.MyBookShop.data.AuthorsRepository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AuthorsService {

    private AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<String> getAlphabet() {
        return IntStream
                .rangeClosed('A', 'Z')
                .mapToObj(c -> "" + (char) c)
                .collect(Collectors.toList());
    }

    public Map<String, List<Author>> getAuthorsMap() {

        Map<String, List<Author>> authorsMap;
        authorsMap = getAlphabet()
                .stream()
                .collect(Collectors.toMap(Function.identity(),
                        value -> authorsRepository.findAuthorsByLastNameStartsWith(value)));
        return new TreeMap<>(authorsMap);
    }

}
