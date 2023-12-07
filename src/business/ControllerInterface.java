package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();

	public HashMap<String, LibraryMember> allMembers();

	public HashMap<String, Book> allBooks();

	public LibraryMember getMemberById(String memberId);
	public List<String> allBookIds();

	public BookCopy checkBook(String isbn, String member) throws LibrarySystemException;

	public void checkoutBooks(List<CheckoutRecord> list, String memberId);
	public void saveMember(String memberId,String firstname,String lastname,String street,String city,String state,String zip,String telephone);
	public void checkoutBook();
	
}
