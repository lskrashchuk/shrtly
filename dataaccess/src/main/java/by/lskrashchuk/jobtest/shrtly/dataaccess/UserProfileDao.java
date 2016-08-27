package by.lskrashchuk.jobtest.shrtly.dataaccess;

import java.util.List;

import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile;

public interface UserProfileDao extends AbstractDao<UserProfile, Long> {

	Long count(UserProfileFilter filter);

	List<UserProfile> find(UserProfileFilter filter);
	
	UserProfile find(String userName, String password);
	
	UserProfile getWithUrls(Long id);


}
