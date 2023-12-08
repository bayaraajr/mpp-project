package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
    public static Auth currentAuth = null;

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
	)
    {
		//add a member
		DataAccess da = new DataAccessFacade();
		// save to data
		da.saveNewMember(new LibraryMember(memberId,firstname,lastname,telephone, new Address(street,city,state,zip)));

	}

    @Override
    public void checkoutBook() {

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

    @Override
    public HashMap<String, LibraryMember> allMembers() {
        DataAccess da = new DataAccessFacade();
        return da.readMemberMap();
    }

    @Override
    public HashMap<String, Book> allBooks() {
        DataAccess da = new DataAccessFacade();
        return da.readBooksMap();
    }

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
        BookCopy bookCopy = book.getNextAvailableCopy();
        if(bookCopy == null) throw new LibrarySystemException("Available copy is not found");

        return bookCopy;
    }

    @Override
    public void checkoutBooks(List<CheckoutRecord> records, String memberId) {
        DataAccess da = new DataAccessFacade();
        HashMap<String, LibraryMember> members = da.readMemberMap();
        List<LibraryMember> mems = new ArrayList<>();
        members.forEach((id, member) -> {
            if(id.equals(memberId)) {
                records.forEach(record -> {
                    if(member.getCheckoutRecords() == null) {
                        member.setCheckoutRecords(new ArrayList<>());
                    }
                    member.addCheckoutRecord(record);
                });
            }
            mems.add(member);
        });

        da.saveMembers(members);
    }


}
