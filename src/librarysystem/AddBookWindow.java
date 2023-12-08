package librarysystem;

import business.Address;
import business.Author;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class AddBookWindow extends JFrame implements LibWindow {


    public static final AddBookWindow INSTANCE = new AddBookWindow();

    private AddBookWindow() {
    }

    List<Author> author_list = new ArrayList<>();


    private JPanel mainPanel;
    private JPanel topPanel;

    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JTextField isbn, title, maximum_checkout_length, number_of_copies;
    private JLabel author;
    private boolean isInitialized;

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel checkoutLabel = new JLabel("Add New Book");
        Util.adjustLabelFont(checkoutLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(checkoutLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(9, 2));


        isbn = new JTextField(10);
        title = new JTextField(10);
        maximum_checkout_length = new JTextField(10);
        number_of_copies = new JTextField(10);

        // Add components to the frame
        middlePanel.add(new JLabel("ISBN:"));
        middlePanel.add(isbn);
        middlePanel.add(new JLabel("Title:"));
        middlePanel.add(title);

        author = new JLabel("AuthorList:" + (getAuthorNames().isEmpty() ? " No Author Added" : getAuthorNames()));
        middlePanel.add(author);

        middlePanel.add(new JLabel("Checkout Duration:"));
        middlePanel.add(maximum_checkout_length);

        middlePanel.add(new JLabel("Number of copies:"));
        middlePanel.add(number_of_copies);

    }

    private  String getAuthorNames(){
        StringBuilder author_names = new StringBuilder();
        for (Author au : author_list) {
            author_names.append(au.getFirstName());
            author_names.append(" ");
        }

        return  author_names.toString();
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<- Main screen");
        JButton AddBookButton = new JButton("Add Book");
        JButton addAuthor = new JButton("Add Author");

        addBackButtonListener(backButton);
        addBookButtonListener(AddBookButton);
        addAuthorButtonListener(addAuthor);
        lowerPanel.add(backButton);
        lowerPanel.add(AddBookButton);
        lowerPanel.add(addAuthor);
    }

    private void cleanFields() {

        // Clear all text fields after submission
        isbn.setText("");
        title.setText("");
        maximum_checkout_length.setText("");
        number_of_copies.setText("");
    }

    @Override
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

        setSize(330, 350);
        isInitialized = true;

    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            author_list.clear();
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void addBookButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            if(author_list.isEmpty())
                JOptionPane.showMessageDialog(this, "Please add an author");
            else if (isbn.getText().isEmpty() || title.getText().isEmpty() ||  maximum_checkout_length.getText().isEmpty() || number_of_copies.getText().isEmpty())
                JOptionPane.showMessageDialog(this, "Please fill up all fields");
            else{
                LibrarySystem.INSTANCE.ci.saveBook(isbn.getText(),title.getText(),Integer.parseInt(maximum_checkout_length.getText()),author_list,Integer.parseInt(number_of_copies.getText()));
                JOptionPane.showMessageDialog(this, "Book Added successfully");

                //Go to books page -----------> code copied from library system
                LibrarySystem.INSTANCE.goToAllBooksDisplayPage();
            }
        });
    }

    private void addAuthorButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
//            System.out.println("Hello");
            LibrarySystem.hideAllWindows();
            if(!AddAuthorWindow.INSTANCE.isInitialized())
                AddAuthorWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(AddAuthorWindow.INSTANCE);
            AddAuthorWindow.INSTANCE.setVisible(true);
            AddAuthorWindow.INSTANCE.pack();
        });
    }

    public void updateAuthorJFiled(){
        author.setText("AuthorList:" + (getAuthorNames().isEmpty() ? " No Author Added" : getAuthorNames()));
    }
    @Override
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }
}



