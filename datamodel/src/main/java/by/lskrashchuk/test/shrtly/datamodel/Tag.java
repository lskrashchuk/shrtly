package by.lskrashchuk.test.shrtly.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class Tag extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String name;
	
	@JoinTable(name = "url_2_tag", joinColumns = {@JoinColumn(name = "tag_id")}, inverseJoinColumns = {@JoinColumn(name = "url_id")})
	@ManyToMany(targetEntity = Url.class, fetch = FetchType.LAZY)
	private List<Url> urls;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
