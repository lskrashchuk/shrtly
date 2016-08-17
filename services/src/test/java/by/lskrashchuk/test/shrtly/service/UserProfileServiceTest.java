package by.lskrashchuk.test.shrtly.service;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.lskrashchuk.test.shrtly.dataaccess.UserProfileDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.test.shrtly.dataaccess.impl.AbstractDaoImpl;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile_;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context-test.xml"})
public class UserProfileServiceTest {
	
	@Inject
	private UserProfileService userProfileService;
	
	@Inject
	private UserProfileDao userProfileDao;
	
	@Test
	public void test() {
		
		Assert.assertNotNull(userProfileService);
				
	}
	
	@Test
	public void testEntityManagerInitialization() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field f = AbstractDaoImpl.class.getDeclaredField("entityManager");
		f.setAccessible(true);
		EntityManager em = (EntityManager) f.get(userProfileDao);
		
		Assert.assertNotNull(em);
		
	}
	
	
	private UserProfile registrationUser(String fn, String ln) {
		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName(fn);
		userProfile.setLastName(ln);
        userProfile.setEmail(System.currentTimeMillis() + "mail@test.by");
        userProfile.setPassword("pswd");
        userProfileService.register(userProfile);
        
 		return userProfile;
	}
	
	
	@Test
	public void testRegistrationUser(){
		UserProfile user = registrationUser("testFName", "testLName");
		
        UserProfile registredUser = userProfileService.getUserProfile(user.getId());

        Assert.assertNotNull(registredUser);


        String updatedFName = "updatedName";
        user.setFirstName(updatedFName);
        userProfileService.saveOrUpdate(user);

        Assert.assertEquals(updatedFName, userProfileService.getUserProfile(user.getId()).getFirstName());

  //      deleteUser(user);
        userProfileService.delete(user);

  
        Assert.assertNull(userProfileService.getUserProfile(user.getId()));
	}

	
	
	
	   @Test
	    public void testSearch() {
	        // clean all data from users
	        List<UserProfile> all = userProfileService.getAll();
	        for (UserProfile user : all) {
	            userProfileService.delete(user);
	        }

	        // start create new data
	        int testObjectsCount = 5;
	        for (int i = 0; i < testObjectsCount; i++) {
	    		registrationUser("testFName"+i, "testLName"+i);
	        }

	        UserProfileFilter filter = new UserProfileFilter();
	        List<UserProfile> result = userProfileService.find(filter);
	        Assert.assertEquals(testObjectsCount, result.size());
	        // test paging
	        int limit = 3;
	        filter.setLimit(limit);
	        filter.setOffset(0);
	        result = userProfileService.find(filter);
	        Assert.assertEquals(limit, result.size());

	        // test sort

	        filter.setLimit(null);
	        filter.setOffset(null);
	        filter.setSortOrder(true);
	        filter.setSortProperty(UserProfile_.firstName);
	        result = userProfileService.find(filter);
	        Assert.assertEquals(testObjectsCount, result.size());

	    }


}
