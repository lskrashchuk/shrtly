package by.lskrashchuk.test.shrtly.dataaccess.filters;

import org.springframework.stereotype.Repository;

public class UserProfileFilter extends AbstractFilter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
