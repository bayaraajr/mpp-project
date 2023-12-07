package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;


import dataaccess.CheckoutRecord;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;

	private List<CheckoutRecord> checkoutRecords;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.checkoutRecords = new ArrayList<>();
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	public void addCheckoutRecord(CheckoutRecord record) {
		this.checkoutRecords.add(record);
	}

	public List<CheckoutRecord> getCheckoutRecords() {
		return this.checkoutRecords;
	}

	private static final long serialVersionUID = -2226197306790714013L;

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setCheckoutRecords(List<CheckoutRecord> checkoutRecords) {
		this.checkoutRecords = checkoutRecords;
	}
}
