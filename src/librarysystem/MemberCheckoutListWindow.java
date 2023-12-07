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

    private static String[] columnNames = {"ISBN", "Title", "Copy number", "Checkout date", "Due date", "Status"};
    private MemberCheckoutListWindow() {}

    public void init() {
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
        readCheckoutList();
    }

    public void readCheckoutList() {
        tableModel.setRowCount(0);
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
                        String dataColumn1 = (String) table.getValueAt(selectedRow, 0);
                        String dataColumn2 = (String) table.getValueAt(selectedRow, 1);

                        // Example: Print the selected data
                        System.out.println("Selected Row: " + selectedRow +
                                ", Data: " + dataColumn1 + ", " + dataColumn2);
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
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);
    }

    public void setData(String data) {
        textArea.setText(data);
    }
    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
//            MemberCheckoutListWindow.hideAllWindows();
            MemberCheckoutListWindow.INSTANCE.setVisible(false);
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
