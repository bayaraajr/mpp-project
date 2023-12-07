package librarysystem;

import business.Book;
import business.BookCopy;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;



public class Main {

	public static void main(String[] args) {
		DataAccessFacade fc = new DataAccessFacade();
		System.out.println(fc.readUserMap());
		HashMap<String,Book> books=  fc.readBooksMap();
		for (Map.Entry<String, Book> entry : books.entrySet()) {
			System.out.println(entry.getKey() + ": " +Arrays.asList( entry.getValue().getCopies()));
		}
	      EventQueue.invokeLater(() ->
	         {
	            LibrarySystem.INSTANCE.setTitle("Sample Library Application");
	            LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	            LibrarySystem.INSTANCE.init();
	            centerFrameOnDesktop(LibrarySystem.INSTANCE);
	            LibrarySystem.INSTANCE.setVisible(true);
	         });
	   }
	   
	   public static void centerFrameOnDesktop(Component f) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int height = toolkit.getScreenSize().height;
			int width = toolkit.getScreenSize().width;
			int frameHeight = f.getSize().height;
			int frameWidth = f.getSize().width;
			f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
		}
}
