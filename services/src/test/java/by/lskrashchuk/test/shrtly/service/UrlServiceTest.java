package by.lskrashchuk.test.shrtly.service;

import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.lskrashchuk.test.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.test.shrtly.dataaccess.impl.AbstractDaoImpl;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.service.impl.SimpleUrlShortener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context-test.xml"})
public class UrlServiceTest {
	
	@Inject
	private UrlDao urlDao;

	@Inject
	private UrlService urlService;
	
	@Inject
	private UserProfileService userProfileService;
	
	@Inject
	private SimpleUrlShortener simpleUrlShortener;

	@Test
	public void test() {
		
		Assert.assertNotNull(urlService);
				
	}
	
	@Test
	public void testEntityManagerInitialization() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field f = AbstractDaoImpl.class.getDeclaredField("entityManager");
		f.setAccessible(true);
		EntityManager em = (EntityManager) f.get(urlDao);
		
		Assert.assertNotNull(em);
		
	}
	
	private Url registrationUrl() {
		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName("f");
		userProfile.setLastName("l");
        userProfile.setEmail(System.currentTimeMillis() + "mail@test.by");
        userProfile.setPassword("pswd");
        userProfileService.register(userProfile);

		Url url = new Url();
		url.setFullUrl("full url");
		url.setUrlCode(simpleUrlShortener.getCode(url));
        url.setDescription("description....");
        url.setUserProfile(userProfile);
        urlService.saveOrUpdate(url);
        
 		return url;
	}
	
	
	@Test
	public void testRegistrationUrl(){
		Url url = registrationUrl();
		
        Url registredUrl = urlService.getUrl(url.getId());

        Assert.assertNotNull(registredUrl);


        String updatedDescription = "updatedDescription";
        url.setDescription(updatedDescription);
        urlService.saveOrUpdate(url);

        Assert.assertEquals(updatedDescription, urlService.getUrl(url.getId()).getDescription());

  //      delete url;
        urlService.delete(url);

  
        Assert.assertNull(urlService.getUrl(url.getId()));
	}

	

}
