package by.lskrashchuk.jobtest.shrtly.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile;
import by.lskrashchuk.jobtest.shrtly.service.impl.SimpleUrlShortener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context-test.xml"})
public class TagServiceTest {
	@Inject
	private TagService tagService;

	@Inject
	private UrlService urlService;
	
	@Inject
	private UserProfileService userProfileService;
	

	@Test
	public void test() {
		
		Assert.assertNotNull(tagService);
				
	}
	
/*	@Test
	public void testEntityManagerInitialization() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field f = AbstractDaoImpl.class.getDeclaredField("entityManager");
		f.setAccessible(true);
		EntityManager em = (EntityManager) f.get(urlDao);
		
		Assert.assertNotNull(em);
		
	} */
	
	
	private Tag registrationTag() {
		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName("f");
		userProfile.setLastName("l");
        userProfile.setEmail(System.currentTimeMillis() + "mail@test.by");
        userProfile.setPassword("pswd");
        userProfileService.saveOrUpdate(userProfile);

		Url url = new Url();
		url.setFullUrl("full url");
		url.setUrlCode("ggg");
        url.setDescription("description....");
        url.setUserProfile(userProfile);
        urlService.saveOrUpdate(url);
        
        Tag tag = new Tag();
        tag.setName("name");
        List<Url> l = new ArrayList<Url>();
        l.add(url);
        tag.setUrls(l);
        tagService.insert(tag);
        
 		return tag;
	}
	
	
	@Test
	public void testRegistrationTag(){
		Tag tag = registrationTag();
		
        Tag registredTag = tagService.find("name");

        Assert.assertNotNull(registredTag);



  //      delete url;
        urlService.delete((tag.getUrls().get(0)));
        deleteTag(tag);

  
        Assert.assertNull(tagService.find("name"));
	}

	private void deleteTag(Tag tag) {
		tagService.delete(tag);
	}

}
