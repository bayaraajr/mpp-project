package dataaccess;

import java.util.HashMap;

import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess {
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();

	public String getNewMemberId();

	public void saveBook(Book book);

	public void saveMembers(HashMap<String, LibraryMember> members);
}
