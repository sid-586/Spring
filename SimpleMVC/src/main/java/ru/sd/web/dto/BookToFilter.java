package ru.sd.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookToFilter {
    private String author;
    private String title;
    @Digits(integer = 4, fraction = 0)
    private Integer size;

    @Override
    public String toString() {
        return "BookToFilter{\"'author='" + author +
                '\'' + ", title='" + title +
                '\'' + ", size=" + size + '}';
    }
}
