package librarysystem;

import business.Address;
import business.LibraryMember;
import business.LoginException;
import rulesets.MemberRuleSet;
import rulesets.RuleSetFactory;

import javax.swing.*;
import java.awt.*;

public class NewMemberWindow extends JFrame implements LibWindow {
    public static final NewMemberWindow INSTANCE = new NewMemberWindow();
    private JPanel mainPanel;
    private JPanel topPanel;

    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private LibraryMember member;

    private JTextField memberIdField, firstNameField, lastNameField, streetField, cityField, stateField, zipField, telephoneField;

    private boolean isInitialized;

    public  String getMemberId(){
        return this.memberIdField.getText();
    }

    public  String getFirstNameField(){
        return this.firstNameField.getText();
    }

    public  String getLastNameField(){
        return this.lastNameField.getText();
    }

    public  String gertStreetField(){
        return this.streetField.getText();
    }

    public  String gertCityField(){
        return this.cityField.getText();
    }

    public  String getStateField(){
        return this.stateField.getText();
    }

    public  String getZipField(){
        return this.zipField.getText();
    }

    public  String getTelephoneField(){
        return this.telephoneField.getText();
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel checkoutLabel = new JLabel("New Member");
        Util.adjustLabelFont(checkoutLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(checkoutLabel);
    }

    public void defineMiddlePanel() {
        middlePanel=new JPanel();
        middlePanel.setLayout(new GridLayout(9, 2));


        memberIdField = new JTextField(10);
        memberIdField.setEditable(false);
        firstNameField = new JTextField(10);
        lastNameField = new JTextField(10);
        streetField = new JTextField(10);
        cityField = new JTextField(10);
        stateField = new JTextField(10);
        zipField = new JTextField(10);
        telephoneField = new JTextField(10);

        // Add components to the frame
        middlePanel.add(new JLabel("Member ID:"));
        middlePanel.add(memberIdField);
        middlePanel.add(new JLabel("First Name:"));
        middlePanel.add(firstNameField);
        middlePanel.add(new JLabel("Last Name:"));
        middlePanel.add(lastNameField);
        middlePanel.add(new JLabel("Street:"));
        middlePanel.add(streetField);
        middlePanel.add(new JLabel("City:"));
        middlePanel.add(cityField);
        middlePanel.add(new JLabel("State:"));
        middlePanel.add(stateField);
        middlePanel.add(new JLabel("ZIP:"));
        middlePanel.add(zipField);
        middlePanel.add(new JLabel("Telephone:"));
        middlePanel.add(telephoneField);

    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<- Main screen");
        JButton checkOutBookButton = new JButton("Save Member");

        addBackButtonListener(backButton);
        addMemberButtonListener(checkOutBookButton);
        lowerPanel.add(backButton);
        lowerPanel.add(checkOutBookButton);

    }

    private void cleanFields() {
        // Clear all text fields after submission
        memberIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
        telephoneField.setText("");
    }

    public void setFormData(LibraryMember m){

        memberIdField.setText(m.getMemberId());
        firstNameField.setText(m.getFirstName());
        lastNameField.setText(m.getLastName());
        streetField.setText(m.getAddress().getStreet());
        cityField.setText(m.getAddress().getCity());
        stateField.setText(m.getAddress().getState());
        zipField.setText(m.getAddress().getZip());
        telephoneField.setText(m.getTelephone());
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
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
            cleanFields();
        });
    }
    private void addMemberButtonListener(JButton butn) {
        butn.addActionListener(evt -> {


            try {

                MemberRuleSet rules = (MemberRuleSet) RuleSetFactory.getRuleSet(NewMemberWindow.this);
                rules.applyRules(NewMemberWindow.this);

                Address address = new Address(streetField.getText(), cityField.getText(), stateField.getText(), zipField.getText());
                LibraryMember member = new LibraryMember(memberIdField.getText(), firstNameField.getText(), lastNameField.getText(), telephoneField.getText(), address);
                LibrarySystem.INSTANCE.ci.saveMember(member);

                cleanFields();
                JOptionPane.showMessageDialog(this,"Successful Added");
                LibrarySystem.hideAllWindows();
                LibrarySystem.INSTANCE.setVisible(true);

//					SystemController.currentAuth
            } catch (Exception e) {
                //throw new RuntimeException(e);
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
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
