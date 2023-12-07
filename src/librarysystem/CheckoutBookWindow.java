package librarysystem;

import business.*;
import business.CheckoutRecord;
import models.ReadonlyTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckoutBookWindow extends JFrame implements LibWindow {
    public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();

    ControllerInterface ci = new SystemController();
    private JPanel mainPanel;
    private JPanel topPanel;

    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JTextField isbnTextArea;
    private JTextField memberTextArea;

    private JPanel leftTextPanel, rightTextPanel, buttonPanel, searchPanel, resultPanel;

    private JLabel label;

    private JTable table;

    private ReadonlyTableModel model;

    private JButton searchButton;

    private static String[] columnNames = {"ISBN", "Book name", "Member ID", "Member Name", "Due date"};

    private List<CheckoutRecord> records;

    private boolean isInitialized;
    @Override
    public void init() {
        this.records = new ArrayList<>();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);

        setSize(660, 500);
        isInitialized = true;

    }

    private void defineLeftTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        isbnTextArea = new JTextField(10);
        label = new JLabel("ISBN");
        label.setFont(Util.makeSmallFont(label.getFont()));
        bottomText.add(isbnTextArea);
        topText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText,BorderLayout.NORTH);
        leftTextPanel.add(bottomText,BorderLayout.CENTER);
    }


    private void defineButtonPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        label = new JLabel("");
        searchButton = new JButton("Add book");
        addCheckButtonListener(searchButton);
        bottomText.add(searchButton);
        topText.add(label);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(topText,BorderLayout.NORTH);
        buttonPanel.add(bottomText,BorderLayout.CENTER);

    }

    private void defineRightTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        memberTextArea = new JTextField(10);
        label = new JLabel("MemberID");
        label.setFont(Util.makeSmallFont(label.getFont()));
        bottomText.add(memberTextArea);
        topText.add(label);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText,BorderLayout.NORTH);
        rightTextPanel.add(bottomText,BorderLayout.CENTER);
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel checkoutLabel = new JLabel("Checkout book");
        Util.adjustLabelFont(checkoutLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(checkoutLabel);
    }

    public void defineMiddlePanel() {
        middlePanel=new JPanel();
        middlePanel.setLayout(new BorderLayout());
        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        defineLeftTextPanel();
        defineRightTextPanel();
        defineButtonPanel();
        searchPanel.add(leftTextPanel);
        searchPanel.add(rightTextPanel);
        searchPanel.add(buttonPanel);
        Object[][]data = {};

        model = new ReadonlyTableModel(data, columnNames);
        table = new JTable(model);
//        table.setIsEditable()
        JScrollPane scrollPane = new JScrollPane(table);
        resultPanel.add(scrollPane);


        middlePanel.add(searchPanel, BorderLayout.NORTH);
        middlePanel.add(resultPanel, BorderLayout.CENTER);

    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("Main screen");
        JButton checkOutBookButton = new JButton("Check out");
        addCheckoutListener(checkOutBookButton);
        checkOutBookButton.setBackground(new Color(2));

        addBackButtonListener(backButton);
        lowerPanel.add(backButton);
        lowerPanel.add(checkOutBookButton);

    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }
    private void addCheckButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            String isbn = isbnTextArea.getText();
            String memberId = memberTextArea.getText();
            System.out.println("ISBN:" + isbn);
            System.out.println("MemberID:" + memberId);
            try {
                BookCopy copy = ci.checkBook(isbn, memberId);

                LocalDate checkOut = LocalDate.now();
                LocalDate dueDate =  LocalDate.of(checkOut.getYear(), checkOut.getMonth(), checkOut.getDayOfMonth()).plusDays(copy.getBook().getMaxCheckoutLength());
                CheckoutRecord rec = new CheckoutRecord(dueDate, checkOut, copy);
                Object row = new Object[]{isbn, copy.getBook().getTitle(), memberId, memberId, dueDate.toString()};
                model.addRow((Object[]) row);
                this.records.add(rec);

            } catch (LibrarySystemException e) {
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        });
    }

    private void addCheckoutListener(JButton butn) {
        butn.addActionListener(evt -> {
            String memberId = memberTextArea.getText();
            ci.checkoutBooks(this.records, memberId);
            model.setRowCount(0);
            this.records.clear();
            this.memberTextArea.setText("");
            this.isbnTextArea.setText("");
            JOptionPane.showMessageDialog(this, "Successfully added checkout record");
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
