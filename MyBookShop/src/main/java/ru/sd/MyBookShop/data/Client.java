package ru.sd.MyBookShop.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String surname;
    @Column(name = "first_name")
    private String firstName;
    private String contactData;
    private double balance;

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
