package by.lskrashchuk.jobtest.shrtly.service;

import java.util.List;

import javax.transaction.Transactional;

import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile;

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
