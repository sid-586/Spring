package ru.sd.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Digits(integer = 10, fraction = 0)
    private Integer id;
    @NotEmpty
    private String author;
    @NotEmpty
    private String title;
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer size;

    @Override
    public String toString() {
        return "Book{\"id=" +
                id + ", author='" + author +
                '\'' + ", title='" + title +
                '\'' + ", size=" + size + '}';
    }
}
