package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CheckoutBookWindow extends JFrame implements LibWindow {
    public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();

    ControllerInterface ci = new SystemController();
    private JPanel mainPanel;
    private JPanel topPanel;

    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JTextField isbnTextArea;
    private JTextField memberTextArea;

    private JPanel leftTextPanel, rightTextPanel, searchPanel, resultPanel;

    private JLabel label;

    private boolean isInitialized;
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
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        resultPanel = new JPanel(new FlowLayout());
        defineLeftTextPanel();
        defineRightTextPanel();
        searchPanel.add(leftTextPanel);
        searchPanel.add(rightTextPanel);
        JButton checkButton = new JButton("Add book");
        searchPanel.add(checkButton);
        addCheckButtonListener(checkButton);
        middlePanel.add(searchPanel, BorderLayout.NORTH);

    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("Main screen");
        JButton checkOutBookButton = new JButton("Check out");

        addBackButtonListener(backButton);
        addCheckButtonListener(checkOutBookButton);
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

        });
    }

    private void addCheckoutListener(JButton butn) {
        butn.addActionListener(evt -> {

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
