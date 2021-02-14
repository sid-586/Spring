package ru.sd.MyBookShop.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "data model of author entity")
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "author id generated by db", position = 1)
    private Integer id;

    @ApiModelProperty(value = "author's surname", example = "Roalling",
            position = 2)
    private String lastName;

    @ApiModelProperty(value = "author's first name", example = "Joann",
            position = 2)
    private String firstName;

    @ApiModelProperty(value = "some info about author", example = "cool " +
            "British author", position = 4)
    private String description;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();

    public Author(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return lastName + ' ' + firstName;
    }

}