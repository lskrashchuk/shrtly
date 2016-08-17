package by.lskrashchuk.test.shrtly.service;

import java.util.List;

import javax.transaction.Transactional;

import by.lskrashchuk.test.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;

public interface UserProfileService {
	
	@Transactional
	void register(UserProfile userProfile);
	
	UserProfile getUserProfile(Long id);
	
	@Transactional
	void saveOrUpdate(UserProfile userProfile);
	
	@Transactional
	void delete(UserProfile userProfile);
	
    List<UserProfile> find(UserProfileFilter filter);

    List<UserProfile> getAll();
    
    Long count(UserProfileFilter filter);
    
    UserProfile getByNameAndPassword(String userName, String password);
    
}
