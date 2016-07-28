package by.lskrashchuk.test.shrtly.datamodel;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class UserProfile extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column (updatable = false)
	private String email;
	
	@Column
	private String password;
	
	@Column
	private Date created;
	
	@OneToMany(mappedBy = "user_profile", fetch = FetchType.LAZY)
	private List<Url> urls;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
