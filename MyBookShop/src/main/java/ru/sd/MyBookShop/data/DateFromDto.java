package ru.sd.MyBookShop.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateFromDto {
    private String from;
//    private String to;
    public Date getFrom() throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(from);
    }

}
