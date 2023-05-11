package domain;

import java.sql.Date;

import javax.xml.crypto.Data;

public class Author {
	// Идентификатор 
	private Long id;
	
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private Date dataregistration;
	public Author() {
	}
	
	
	public Author(Long id, String firstname, String lastname, String email,
			String phone, Date dataregistration) {
	this.id = id;
	this.firstname = firstname;
	this.lastname = lastname;
	this.email = email;
	this.phone = phone;
	this.dataregistration = dataregistration;
	}
	public Long getId() {
	return id;
	}
	public void setId(Long id) {

	this.id = id;
	}
	public String getFirstName() {
	return firstname;
	}
	public void setFirstName(String firstname) {
	this.firstname = firstname;
	}
	
	public String getLastName() {
		return lastname;
		}
		public void setLastName(String lastname) {
		this.lastname = lastname;
		}
		
		public String getEmail() {
			return email;
			}
			public void setEmail(String email) {
			this.email = email;
			}
			
			public String getPhone() {
				return phone;
				}
				public void setPhone(String phone) {
				this.phone = phone;
				}
				
				public Date getDataRegistration() {
					return dataregistration;
					}
					public void setDataRegistration(Date dataregistration) {
					this.dataregistration = dataregistration;
					}
	@Override
	public String toString() {
	return "Author {" + "Id = " + id + ", FirstName = " + firstname+", " + "LastName = " + lastname + ", " + "Email = " + email + ", " + "Phone = " + phone + ", " + "DataRegistration = " + dataregistration +  "}";
	}
	}