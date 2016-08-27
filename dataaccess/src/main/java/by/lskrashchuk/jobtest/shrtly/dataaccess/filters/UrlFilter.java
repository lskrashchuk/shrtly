package by.lskrashchuk.jobtest.shrtly.dataaccess.filters;

import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile;

public class UrlFilter extends AbstractFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String urlCode;
	
	private String tag;
	
	private UserProfile userProfile;
	
	private String fullUrl;

	public String getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	
	

}
