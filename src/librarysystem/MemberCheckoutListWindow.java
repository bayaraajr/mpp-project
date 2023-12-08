package librarysystem;

import business.*;
import models.ReadonlyTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;

public class MemberCheckoutListWindow extends JFrame implements LibWindow{
    public static final MemberCheckoutListWindow INSTANCE = new MemberCheckoutListWindow();
    ControllerInterface ci = new SystemController();


    private LibraryMember member;

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

    private JLabel allIDsLabel;

    private int selectedTableRow;

    private static String[] columnNames = {"ISBN", "Title", "Copy number", "Checkout date", "Due date", "Status"};
    private MemberCheckoutListWindow() {}

    public void init() {
        setSize(960, 600);
        member = null;
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
//        LibraryMember mem = LibrarySystem.INSTANCE.ci.getMemberById(member.getMemberId());
        System.out.println(member.getMemberId());
        System.out.println(member.getCheckoutRecords().size());
        if(member != null && member.getCheckoutRecords() != null) {
            member.getCheckoutRecords().forEach((record) -> {
                    BookCopy copy = record.getBookCopy();
                    Book book = copy.getBook();
                    LocalDate now = LocalDate.now();
                    int isOverdue = record.getDueDate().compareTo(now);
                    Object[] data = new Object[]{ book.getIsbn(), book.getTitle(), copy.getCopyNum(), record.getCheckoutDate().toString(), record.getDueDate().toString(), isOverdue< 0 ? "Overdue" : "Normal"  };
                    tableModel.addRow((Object[]) data);
                });
        }
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
        allIDsLabel = new JLabel((member != null ? member.getFirstName() : "")  + ": Checkout list");
        Util.adjustLabelFont(allIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(allIDsLabel);
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
        JButton returnButton = new JButton("Return book");
        addBackButtonListener(backButton);
        addReturnButtonListener(returnButton);
        lowerPanel.add(backButton);
        lowerPanel.add(returnButton);
    }

    public void setData() {
        readCheckoutList();
    }
    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
//            MemberCheckoutListWindow.hideAllWindows();
            MemberCheckoutListWindow.INSTANCE.setVisible(false);
        });
    }

    private void addReturnButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            if(selectedTableRow < 0) {
                JOptionPane.showMessageDialog(MemberCheckoutListWindow.INSTANCE,"Please select book copy");
                return;
            }
            String isbn = (String) table.getValueAt(selectedTableRow, 0);
            int copyNum = (int) table.getValueAt(selectedTableRow, 2);
            LibrarySystem.INSTANCE.ci.returnBook(isbn, copyNum, member);
            tableModel.removeRow(selectedTableRow);
            JOptionPane.showMessageDialog(MemberCheckoutListWindow.INSTANCE,"Successfully returned the book");
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




    public LibraryMember getMember() {
        return member;
    }

    public void setMember(LibraryMember mem) {
        this.member = mem;
        allIDsLabel.setText(member.getFirstName() + ": checkout list");
    }


}
