package by.lskrashchuk.test.shrtly.service.impl;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.lskrashchuk.test.shrtly.dataaccess.UserProfileDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.service.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	private static Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Inject
	private UserProfileDao userProfileDao;


	@Override
	public void register(UserProfile userProfile) {
		userProfile.setCreated(new Date());
		userProfileDao.insert(userProfile);
		LOGGER.info("User registred: {}", userProfile);
	}

	@Override
	public UserProfile getUserProfile(Long id) {
		return userProfileDao.get(id);
	}

	@Override
	public void saveOrUpdate(UserProfile userProfile) {
		userProfile.setCreated(new Date());
		if (userProfile.getId() == null) {
			userProfileDao.insert(userProfile);
			LOGGER.info("User inserted: {}", userProfile);
		} else {
			userProfileDao.update(userProfile);
			LOGGER.info("User updated: {}", userProfile);
		}
	}

	@Override
	public void delete(UserProfile userProfile) {
		userProfileDao.delete(userProfile.getId());
		LOGGER.info("User deleted: {}", userProfile);
	}

	@Override
	public List<UserProfile> find(UserProfileFilter filter) {
		return userProfileDao.find(filter);
	}

	@Override
	public List<UserProfile> getAll() {
		return userProfileDao.getAll();
	}

	@Override
	public Long count(UserProfileFilter filter) {
		return userProfileDao.count(filter);

	}

	@Override
	public UserProfile getByNameAndPassword(String userName, String password) {
		return userProfileDao.find(userName, password);
	}

}
