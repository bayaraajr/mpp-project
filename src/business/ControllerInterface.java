package business;

import java.util.HashMap;
import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;


	void saveBook(String isbn, String title, int maximum_checkout_length,
				  List<Author> authors, int number_of_copies);

	boolean addBookCopy(String isbn);

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
