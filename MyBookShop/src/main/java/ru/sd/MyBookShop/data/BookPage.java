package ru.sd.MyBookShop.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookPage {

    private Integer count;
    private List<Book> books;

    public BookPage(List<Book> books) {
        this.books = books;
        this.count = books.size();
    }

}
