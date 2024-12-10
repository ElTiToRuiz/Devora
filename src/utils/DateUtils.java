package src.utils;

import java.time.LocalDate;
import java.time.DayOfWeek;

public class DateUtils {
    public static LocalDate getStartOfWeek() {
        return LocalDate.now().with(DayOfWeek.MONDAY); 
    }

    public static LocalDate getEndOfWeek() {
        return LocalDate.now().with(DayOfWeek.SUNDAY); 
    }
}