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
	) {
		//add a memeber
		DataAccess da = new DataAccessFacade();
		// save to data
		da.saveNewMember(new LibraryMember(memberId,firstname,lastname,telephone, new Address(street,city,state,zip)));

	}

	@Override
	public void checkoutBook() {

    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
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
        if(!books.containsKey(isbn)) {
            throw new LibrarySystemException("Book not found");
        }

        HashMap<String, LibraryMember> members = da.readMemberMap();
        if(!members.containsKey(memberId)) {
            throw new LibrarySystemException("Member not found");
        }

        Book book = books.get(isbn);
        BookCopy bookCopy = null;
        for(BookCopy copy: book.getCopies()) {
            if(copy.isAvailable()) {
                bookCopy = copy;
                break;
            };
        }

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
