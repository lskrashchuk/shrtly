package by.lskrashchuk.jobtest.shrtly.datamodel;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Url extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String fullUrl;
	
	@Column
	private String urlCode;
	
	@Column
	private Date created;

	@Column
	private String description;
	
	@Column
	private Integer clicks;
	
	@ManyToOne(targetEntity = UserProfile.class, fetch = FetchType.LAZY)
	private UserProfile userProfile;
	
	@JoinTable(name = "url_2_tag", joinColumns = {@JoinColumn(name = "url_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
	@ManyToMany(targetEntity = Tag.class, fetch = FetchType.LAZY)
	private List<Tag> tags;

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public String getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
