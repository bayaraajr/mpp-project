package dataaccess;

import business.Book;
import business.BookCopy;

import java.time.LocalDate;

public class CheckoutRecord {
    private LocalDate dueDate;

    private LocalDate checkoutDate;

    private BookCopy bookCopy;
    CheckoutRecord(LocalDate dueDate, LocalDate checkoutDate, BookCopy copy){

        copy.changeAvailability();
        this.bookCopy = copy;
        this.dueDate = dueDate;
        this.checkoutDate = checkoutDate;

    }

}
