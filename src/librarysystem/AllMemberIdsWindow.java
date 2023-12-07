package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import business.ControllerInterface;
import business.LibraryMember;
import business.LibrarySystemException;
import business.SystemController;
import models.ReadonlyTableModel;


public class AllMemberIdsWindow extends JFrame implements LibWindow {
	public static final AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
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

	private String selectedMember;

	private ReadonlyTableModel tableModel;

	private JTable table;

	private static String[] columnNames = {"Member ID", "Firstname", "Lastname", "Telephone"};
	private AllMemberIdsWindow() {}
	
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
		readMembers();
	}

	public void readMembers() {
		HashMap<String, LibraryMember> members = ci.allMembers();
		members.forEach((memberId, member) -> {
			Object[] data = new Object[]{ memberId,  member.getFirstName(), member.getLastName(), member.getTelephone()  };
			tableModel.addRow((Object[]) data);
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						// Perform your action here, e.g., get data from the selected row
						String memberId = (String) table.getValueAt(selectedRow, 0);
						selectedMember = memberId;
					}
				}
			}
		});
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("Member list");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
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
		JButton backButton = new JButton("Main menu");
		JButton checkoutButton = new JButton("Checkout list");
		JButton editButton = new JButton("Edit member");
		addBackButtonListener(backButton);
		addCheckoutButtonListener(checkoutButton);
		editButtonListened(editButton);
		lowerPanel.add(backButton);
		lowerPanel.add(checkoutButton);
		lowerPanel.add(editButton
		);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}
	
	public void setData(String data) {
		textArea.setText(data);
	}
	private void addCheckoutButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			if(selectedMember == null) {
				JOptionPane.showMessageDialog(this,"Select member first");
				return;
			}
			if(!MemberCheckoutListWindow.INSTANCE.isInitialized())
				MemberCheckoutListWindow.INSTANCE.init();
			LibraryMember member = ci.getMemberById(selectedMember);
			System.out.println(member.getFirstName());
			MemberCheckoutListWindow.INSTANCE.setMember(member);
			MemberCheckoutListWindow.INSTANCE.setVisible(true);
			MemberCheckoutListWindow.INSTANCE.readCheckoutList();
			Util.centerFrameOnDesktop(MemberCheckoutListWindow.INSTANCE);
		});
	}

	private void editButtonListened(JButton butn) {
		butn.addActionListener(evt -> {
			if(selectedMember == null) {
				JOptionPane.showMessageDialog(this,"Select member first");
				return;
			}

			LibrarySystem.hideAllWindows();

			if(!NewMemberWindow.INSTANCE.isInitialized())
				NewMemberWindow.INSTANCE.init();
			LibraryMember member = ci.getMemberById(selectedMember);
			NewMemberWindow.INSTANCE.setFormData(member);
			Util.centerFrameOnDesktop(NewMemberWindow.INSTANCE);
			NewMemberWindow.INSTANCE.setVisible(true);
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


