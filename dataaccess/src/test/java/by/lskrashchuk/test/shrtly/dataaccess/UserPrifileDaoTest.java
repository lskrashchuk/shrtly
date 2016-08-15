package by.lskrashchuk.test.shrtly.dataaccess;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.lskrashchuk.test.shrtly.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dataaccess-context-test.xml")
public class UserPrifileDaoTest {
	 
	    @Autowired private UserProfileDao userProfileDao;
	 
	    @Test
	    @Transactional
	    public void saveAndSelect() throws Exception {
	 
//	        create test client 
	        UserProfile savedUserProfile = new UserProfile();
	        savedUserProfile.setFirstName("First name");
	        savedUserProfile.setLastName("Last name");
	        savedUserProfile.setEmail("Email");
	        savedUserProfile.setPassword("1111");
	        savedUserProfile.setCreated(new Date());

	 
//	        save client in test DB and getting record id
	        userProfileDao.insert(savedUserProfile);
	 
	        UserProfile selectedUserProfile = userProfileDao.get(savedUserProfile.getId());
	 
//	        compering clients names
	        Assert.assertEquals(selectedUserProfile.getFirstName(), savedUserProfile.getFirstName());
	        Assert.assertEquals(selectedUserProfile.getLastName(), savedUserProfile.getLastName());
	    }
	
}
