package by.lskrashchuk.jobtest.shrtly.service.impl;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.lskrashchuk.jobtest.shrtly.dataaccess.UserProfileDao;
import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile;
import by.lskrashchuk.jobtest.shrtly.service.UserProfileService;

@Transactional
@Service
public class UserProfileServiceImpl implements UserProfileService {
	private static Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Inject
	private UserProfileDao userProfileDao;


	@Override
	public void register(UserProfile userProfile) {
		userProfile.setCreated(new Date());
		userProfileDao.save(userProfile);
		LOGGER.info("User registred: {}", userProfile.getFirstName()+" "+userProfile.getLastName());
	}

	@Override
	public UserProfile getUserProfile(Long id) {
		return userProfileDao.findById(id);
	}

	
	@Override
	public void saveOrUpdate(UserProfile userProfile) {
		if (userProfile.getId() == null) {
			userProfile.setCreated(new Date());
			userProfileDao.save(userProfile);
			LOGGER.info("User inserted: {}", userProfile.getFirstName()+" "+userProfile.getLastName());
		} else {
			userProfileDao.update(userProfile);
			LOGGER.info("User updated: {}", userProfile.getFirstName()+" "+userProfile.getLastName());
		}
	}

	@Override
	public void delete(UserProfile userProfile) {
		userProfileDao.delete(userProfile.getId());
		LOGGER.info("User deleted: {}", userProfile.getFirstName()+" "+userProfile.getLastName());
	}

	@Override
	public List<UserProfile> find(UserProfileFilter filter) {
		return userProfileDao.find(filter);
	}

	@Override
	public List<UserProfile> getAll() {
		return userProfileDao.findAll();
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
