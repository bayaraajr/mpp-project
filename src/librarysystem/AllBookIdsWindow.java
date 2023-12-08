package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import business.*;
import models.ReadonlyTableModel;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;

	private ReadonlyTableModel tableModel;

	private JTable table;

	private static String[] columnNames = {"ISBN", "Title", "Authors", "Number of copies", "Available copies", "Max checkout length"};
	//Singleton class
	private AllBookIdsWindow() {}
	
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
		setSize(960, 600);
		readBooks();
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Book IDs");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		Object[][]data ={ };
		tableModel = new ReadonlyTableModel(data, columnNames);
		table = new JTable(tableModel);
		//populateTextArea();

		JScrollPane scrollPane = new JScrollPane(table);
		middlePanel.add(scrollPane);
		
	}
	
	public void defineLowerPanel() {
		
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
		lowerPanel.add(backToMainButn);
	}

	public void readBooks() {
		tableModel.setRowCount(0);
		HashMap<String, Book> books = ci.allBooks();
		books.forEach((isbn, book) -> {
			String authors = "";
			List<String> authorNames = new ArrayList<>();
			book.getAuthors().forEach(author -> {
				authorNames.add(author.getFirstName() + " " + author.getLastName());
			});

			authors = String.join(",", authorNames);

			int availablaeCopies = 0;
			for (BookCopy copy : book.getCopies()) {
				if(copy.isAvailable()) availablaeCopies++;
			}

			Object[] data = new Object[]{ isbn,book.getTitle(),  authors, book.getCopies().length, availablaeCopies,book.getMaxCheckoutLength()  };
			tableModel.addRow((Object[]) data);
		});
	}
	
	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
    		
		}
	}
	
	public void setData() {
		readBooks();
	}
	
//	private void populateTextArea() {
//		//populate
//		List<String> ids = ci.allBookIds();
//		Collections.sort(ids);
//		StringBuilder sb = new StringBuilder();
//		for(String s: ids) {
//			sb.append(s + "\n");
//		}
//		textArea.setText(sb.toString());
//	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
}
