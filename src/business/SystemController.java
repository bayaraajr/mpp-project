package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.LibrarySystem;

public class SystemController implements ControllerInterface {
    public static Auth currentAuth = null;

    /**
     * Login section and check id and password. If false, a LoginException is thrown.
     *
     * @param id
     * @param password
     * @throws LoginException
     */
    public void login(String id, String password) throws LoginException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(id).getAuthorization();
    }

    /**
     *  This is insert member function. The information below is required.
     *
     * @param memberId
     * @param firstname
     * @param lastname
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param telephone
     */
	@Override
	public void saveMember(
			String memberId,
			String firstname,
			String lastname,
			String street,
			String city,
			String state,
			String zip,
			String telephone
	) {
		DataAccess da = new DataAccessFacade();
		// save to data
		da.saveNewMember(new LibraryMember(memberId,firstname,lastname,telephone, new Address(street,city,state,zip)));

	}

    @Override
    public void returnBook(String isbn, int copyNum, LibraryMember member) {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> books = da.readBooksMap();
        HashMap<String, LibraryMember> members = da.readMemberMap();
        Book book = books.get(isbn);
        System.out.println("BOOK: " + book.getIsbn());
        for (BookCopy copy : book.getCopies()) {
            if(copy.getCopyNum() == copyNum) {
                System.out.println("COPY: " + copy.getCopyNum());
                copy.changeAvailability(true);
            }
        }
        da.saveBooks(books);

        CheckoutRecord rec = member.getCheckoutRecords().stream().filter((record) -> record.getBookCopy().getCopyNum() == copyNum && record.getBookCopy().getBook().getIsbn().equals(isbn)).findFirst().orElse(null);
        member.getCheckoutRecords().remove(rec);
        members.put(member.getMemberId(), member);
        da.saveMembers(members);
    }

    @Override
    public void saveBook(String isbn, String title, int maximum_checkout_length,
                         List<Author> authors, int number_of_copies)
    {
        Book b = new Book(isbn,title,maximum_checkout_length,authors);
        if(number_of_copies>1){
            for (int i = 1; i < number_of_copies; i++) {
                b.addCopy();
            }
        }
        //add a member
        DataAccessFacade da = new DataAccessFacade();
        // save to data
        da.saveBook(b);

    }

    @Override
    public boolean addBookCopy(String isbn){
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> books = da.readBooksMap();

        if(books.containsKey(isbn)){
            Book book = books.get(isbn);
            book.addCopy();
            da.saveBook(book);
            return true;
        }
        return false;
    }


    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    /**
     * This is return all members list.
     * @return HashMap
     */
    @Override
    public HashMap<String, LibraryMember> allMembers() {
        DataAccess da = new DataAccessFacade();
        return da.readMemberMap();
    }

    /**
     *
     * @Description Returns all the books in storage file.
     * @param
     * @return Book list
     */
    @Override
    public HashMap<String, Book> allBooks() {
        DataAccess da = new DataAccessFacade();
        return da.readBooksMap();
    }

    /**
     * @Description Searches for member and returns if available
     * @param memberId Library member ID
     * @return
     */
    @Override
    public LibraryMember getMemberById(String memberId) {
        DataAccess da = new DataAccessFacade();
        return da.readMemberMap().get(memberId);
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    /**
     * @Description Checks if specified book's copy available or not.
     * If the book copy is available change it to unavailable and store the books to storage
     * @param isbn Book ISBN
     * @param memberId Library member ID
     * @return BookCopy - book copy instance
     * @throws LibrarySystemException
     */
    @Override
    public BookCopy checkBook(String isbn, String memberId) throws LibrarySystemException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> books = da.readBooksMap();
        System.out.println(isbn);
        System.out.println(memberId);
        if(!books.containsKey(isbn)) {
            throw new LibrarySystemException("Book not found");
        }

        HashMap<String, LibraryMember> members = da.readMemberMap();
        if(!members.containsKey(memberId)) {
            throw new LibrarySystemException("Member not found");
        }

        Book book = books.get(isbn);
        if(book.isAvailable()) {
            BookCopy bookCopy = book.getNextAvailableCopy();
            System.out.println("IS AVAILABLE");
            System.out.println(bookCopy.isAvailable());
            if(bookCopy == null) throw new LibrarySystemException("Available copy is not found");
            bookCopy.changeAvailability(false);
            da.saveBooks(books);
            return bookCopy;
        }
        throw new LibrarySystemException("Available copy is not found");
    }

    /**
     * @Description Checks out selected books and saves them to storage
     * @param records Current checkout records
     * @param memberId Library member ID
     */
    @Override
    public void checkoutBooks(List<CheckoutRecord> records, String memberId) {
        DataAccess da = new DataAccessFacade();
        HashMap<String, LibraryMember> members = da.readMemberMap();

        members.forEach((id, member) -> {
            if(id.equals(memberId)) {
                records.forEach(record -> {
                    if(member.getCheckoutRecords() == null) {
                        member.setCheckoutRecords(new ArrayList<>());
                    }
                    member.addCheckoutRecord(record);
                });
            }
        });

        da.saveMembers(members);
    }


}
