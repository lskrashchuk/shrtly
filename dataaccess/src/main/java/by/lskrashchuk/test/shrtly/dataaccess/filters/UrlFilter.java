package by.lskrashchuk.test.shrtly.dataaccess.filters;

import by.lskrashchuk.test.shrtly.datamodel.UserProfile;

public class UrlFilter extends AbstractFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String urlCode;
	
	private String tag;
	
	private UserProfile userProfile;

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
	
	

}
