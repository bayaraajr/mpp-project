package business;

import business.Book;
import business.BookCopy;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecord implements Serializable {

    private static final long serialVersionUID = 6110690276685962832L;
    private LocalDate dueDate;

    private LocalDate checkoutDate;

    private BookCopy bookCopy;
    public CheckoutRecord(LocalDate dueDate, LocalDate checkoutDate, BookCopy copy){

        copy.changeAvailability();
        this.bookCopy = copy;
        this.dueDate = dueDate;
        this.checkoutDate = checkoutDate;

    }

    public CheckoutRecord(CheckoutRecord rec) {
        this.setCheckoutDate(rec.getCheckoutDate());
        this.setBookCopy(rec.getBookCopy());
        this.bookCopy.changeAvailability();
        this.setDueDate(rec.getDueDate());
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }
}
