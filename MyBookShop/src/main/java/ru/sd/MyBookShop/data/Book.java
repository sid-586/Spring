package ru.sd.MyBookShop.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "BA_FK"))
    private Author author;
    @Column(nullable = false)
    private String title;
    @Column(name = "pub_date", columnDefinition = "timestamp")
    private Date publicationDate;
    @Column(name = "is_bestseller", nullable = false)
    private boolean isBestseller;

    private String priceOld;
    private String price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "BG_FK"))
    private Genre genre;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", priceOld='" + priceOld + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
