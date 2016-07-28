package by.lskrashchuk.test.shrtly.dataaccess;

import java.util.List;

import by.lskrashchuk.test.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;

public interface UserProfileDao extends AbstractDao<UserProfile, Long> {

	Long count(UserProfileFilter filter);

	List<UserProfile> find(UserProfileFilter filter);
	
	UserProfile find(String userName, String password);
	
	UserProfile getWithUrls(Long id);


}
