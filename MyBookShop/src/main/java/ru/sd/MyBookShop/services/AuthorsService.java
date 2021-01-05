package ru.sd.MyBookShop.services;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.sd.MyBookShop.dto.Author;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AuthorsService {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorsService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAlphabet() {
        return IntStream
                .rangeClosed('A', 'Z')
                .mapToObj(c -> "" + (char) c)
                .collect(Collectors.toList());
    }

    public Map<String, List<Author>> getAuthorsMap() {

        Map<String, List<Author>> authorsMap;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        authorsMap = getAlphabet()
                .stream()
                .collect(Collectors.toMap(Function.identity(),
                        value -> {
                            parameterSource.addValue("authorL",
                                    value + "%");
                            return jdbcTemplate
                                    .query("SELECT * FROM authors WHERE " +
                                                    "author LIKE :authorL",
                                            parameterSource,
                                            (ResultSet rs, int intRow) -> {
                                                Author author = new Author();
                                                author.setId(rs.getInt("id"));
                                                author.setName(rs.getString("author"));
                                                return author;
                                            });
                        }));
        return new TreeMap<>(authorsMap);
    }

}
