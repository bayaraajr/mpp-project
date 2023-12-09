package librarysystem;

import business.*;
import models.ReadonlyTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;

public class CheckoutListWindow extends JFrame implements LibWindow{
    public static final CheckoutListWindow INSTANCE = new CheckoutListWindow();
    ControllerInterface ci = new SystemController();




    private boolean isInitialized = false;
    public JPanel getMainPanel() {
        return mainPanel;
    }
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;

    private ReadonlyTableModel tableModel;

    private JTable table;

    private JLabel allIDsLabel, memberLabel;

    private int selectedTableRow;

    private JTextField isbnField, memberField;

    private JButton searchButton;

    private static String[] columnNames = {"ISBN", "Member ID", "Member name","Title", "Copy number", "Checkout date", "Due date", "Status"};
    private CheckoutListWindow() {}

    public void init() {
        setSize(960, 600);

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
    }

    public void readCheckoutList() {
        tableModel.setRowCount(0);
        HashMap<String, LibraryMember> members = ci.allMembers();
        members.forEach((memberId, member) -> {
            System.out.println(member.getMemberId());
            if(member.getCheckoutRecords() != null) {
                member.getCheckoutRecords().forEach((record) -> {
                    String isbn = isbnField.getText().trim();
                    String memberValue = memberField.getText().trim();
                    BookCopy copy = record.getBookCopy();
                    Book book = copy.getBook();
                    LocalDate now = LocalDate.now();
                    int isOverdue = record.getDueDate().compareTo(now);
                    Object[] data = new Object[]{ book.getIsbn(), member.getMemberId(),member.getFirstName() + " " + member.getLastName() , book.getTitle(), copy.getCopyNum(), record.getCheckoutDate().toString(), record.getDueDate().toString(), isOverdue< 0 ? "Overdue" : "Normal"  };
                   if(isbn.length() > 0 && memberId.length() > 0) {
                       if(book.getIsbn().equals(isbn) && member.getMemberId().equals(memberValue))
                        tableModel.addRow((Object[]) data);
                   }
                   else if(isbn.length() > 0 && book.getIsbn().equals(isbn)) {

                            tableModel.addRow((Object[]) data);
                   }
                   else if(memberValue.length() > 0 && member.getMemberId().equals(memberValue  )) {
                       tableModel.addRow((Object[]) data);
                   }
                   else if(isbn.length() == 0 && memberValue.length() == 0){
                       tableModel.addRow((Object[]) data);
                   }
                });
            }
        });
//        LibraryMember mem = LibrarySystem.INSTANCE.ci.getMemberById(member.getMemberId());

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Perform your action here, e.g., get data from the selected row

                        selectedTableRow = selectedRow;
                    } else {
//                        JOptionPane.showMessageDialog(MemberCheckoutListWindow.INSTANCE,"Select book copy");
                    }
                }
            }
        });
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        isbnField = new JTextField(10);
        memberField = new JTextField(10);
        allIDsLabel = new JLabel("Enter ISBN");
        memberLabel = new JLabel("Enter member ID");

        searchButton = new JButton("Search");
//        Util.adjustLabelFont(allIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(allIDsLabel);
        topPanel.add(isbnField);
        topPanel.add(memberLabel);
        topPanel.add(memberField);
        topPanel.add(searchButton);
        addReturnButtonListener(searchButton);

    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        Object[][] data = {};
        tableModel = new ReadonlyTableModel(data, columnNames);
        table = new JTable(tableModel);
        middlePanel.setLayout(fl);
        textArea = new TextArea(8,20);
        JScrollPane scrollPane = new JScrollPane(table);
        middlePanel.add(scrollPane);
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("Close");
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);

    }

    public void setData() {
        readCheckoutList();
    }
    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
//            MemberCheckoutListWindow.hideAllWindows();
            CheckoutListWindow.INSTANCE.setVisible(false);
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void addReturnButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
           readCheckoutList();
        });
    }

    @Override
    public boolean isInitialized() {

        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

}
