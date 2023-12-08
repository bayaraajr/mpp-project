package librarysystem;

import business.Address;
import business.Author;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddAuthorWindow extends JFrame implements LibWindow {


    public static final AddAuthorWindow INSTANCE = new AddAuthorWindow();

    private AddAuthorWindow() {
    }

    private JPanel mainPanel;
    private JPanel topPanel;

    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JTextField firstName, lastName, telephone, street, city, state, zip, bio;

    private boolean isInitialized;

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel checkoutLabel = new JLabel("Add Author");
        Util.adjustLabelFont(checkoutLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(checkoutLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(9, 2));

        firstName = new JTextField(10);
        lastName = new JTextField(10);
        telephone = new JTextField(10);
        street = new JTextField(10);
        city = new JTextField(10);
        state = new JTextField(10);
        zip = new JTextField(10);
        bio = new JTextField(10);


        // Add components to the frame
        middlePanel.add(new JLabel("firstName:"));
        middlePanel.add(firstName);
        middlePanel.add(new JLabel("lastName:"));
        middlePanel.add(lastName);
        middlePanel.add(new JLabel("telephone:"));
        middlePanel.add(telephone);
        middlePanel.add(new JLabel("street:"));
        middlePanel.add(street);
        middlePanel.add(new JLabel("city:"));
        middlePanel.add(city);
        middlePanel.add(new JLabel("state:"));
        middlePanel.add(state);

        middlePanel.add(new JLabel("zip:"));
        middlePanel.add(zip);

        middlePanel.add(new JLabel("bio"));
        middlePanel.add(bio);

    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<- Back");
        JButton addAuthor = new JButton("Add Author");

        addBackButtonListener(backButton);
        addAuthorButtonListener(addAuthor);
        lowerPanel.add(backButton);
        lowerPanel.add(addAuthor);
    }

    private void cleanFields() {

        // Clear all text fields after submission
        firstName.setText("");
        lastName.setText("");
        telephone.setText("");
        street.setText("");
        city.setText("");
        state.setText("");
        zip.setText("");
        bio.setText("");

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
            //Going back to book class
            goBack();
        });
    }

    private void goBack(){
        LibrarySystem.hideAllWindows();
        if (!AddBookWindow.INSTANCE.isInitialized())
            AddBookWindow.INSTANCE.init();
        AddBookWindow.INSTANCE.updateAuthorJFiled();
        Util.centerFrameOnDesktop(AddBookWindow.INSTANCE);
        AddBookWindow.INSTANCE.setVisible(true);
        AddBookWindow.INSTANCE.pack();
    }


    private void addAuthorButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || telephone.getText().isEmpty() || street.getText().isEmpty() || city.getText().isEmpty() || state.getText().isEmpty() || zip.getText().isEmpty() || bio.getText().isEmpty())
                JOptionPane.showMessageDialog(this, "Please fill up all fields");
            else{
                AddBookWindow.INSTANCE.author_list.add(new Author(firstName.getText(), lastName.getText(), telephone.getText(), new Address(street.getText(), city.getText(), state.getText(), zip.getText()), bio.getText()));
                JOptionPane.showMessageDialog(this, "Author Added successfully");
                goBack();
            }

            System.out.println(AddBookWindow.INSTANCE.author_list.size());

        });
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




