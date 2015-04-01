package com.websoftex.model;

public class Contact {

	public int id = 0;

	public String firstName = null;
	public String lastName = null;
	public String mobileNo = null;
	public String secondaryMobNo = null;
	public String emailId = null;
	public String birthDate = null;
	public String address = null;
	public byte[] imageForProfile = null;

	public Contact() {
		super();
	}
	public Contact(int id, String firstName, String lastName, String mobileNo,
			String secondaryMobNo, String emailId, String birthDate,
			String address, byte[] imageForProfile) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.secondaryMobNo = secondaryMobNo;
		this.emailId = emailId;
		this.birthDate = birthDate;
		this.address = address;
		this.imageForProfile = imageForProfile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSecondaryMobNo() {
		return secondaryMobNo;
	}
	public void setSecondaryMobNo(String secondaryMobNo) {
		this.secondaryMobNo = secondaryMobNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public byte[] getImageForProfile() {
		return imageForProfile;
	}
	public void setImageForProfile(byte[] imageForProfile) {
		this.imageForProfile = imageForProfile;
	}


}
