package by.lskrashchuk.jobtest.shrtly.dataaccess;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.lskrashchuk.jobtest.shrtly.dataaccess.UserProfileDao;
import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dataaccess-context-test.xml")
public class UserPrifileDaoTest {

	@Autowired
	private UserProfileDao userProfileDao;

	@Test
	@Transactional
	public void saveAndSelect() throws Exception {

		// create test user
		UserProfile savedUserProfile = new UserProfile();
		savedUserProfile.setFirstName("First name");
		savedUserProfile.setLastName("Last name");
		savedUserProfile.setEmail("Email");
		savedUserProfile.setPassword("1111");
		savedUserProfile.setCreated(new Date());

		// save user in test DB and getting record id
		userProfileDao.insert(savedUserProfile);

		UserProfile selectedUserProfile = userProfileDao.get(savedUserProfile.getId());

		// comparing users names
		Assert.assertEquals(selectedUserProfile.getFirstName(), savedUserProfile.getFirstName());
		Assert.assertEquals(selectedUserProfile.getLastName(), savedUserProfile.getLastName());
	}

}
