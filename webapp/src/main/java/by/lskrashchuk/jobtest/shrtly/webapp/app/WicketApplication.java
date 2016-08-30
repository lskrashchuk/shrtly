package by.lskrashchuk.jobtest.shrtly.webapp.app;

import javax.inject.Inject;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import by.lskrashchuk.jobtest.shrtly.webapp.page.home.HomePage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.links.LinkEditPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.links.LinksPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.login.LoginPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.redirect.RealUrlRedirectorPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.signup.SignUpPage;


@Component("wicketWebApplicationBean")
public class WicketApplication extends AuthenticatedWebApplication {

//	public static final String DOMAIN_NAME = "localhost:8081";
	public static String DOMAIN_NAME;
	public static final String URL_ADDITIONAL_NAME = "/s"; 

	
	@Inject
    private ApplicationContext applicationContext;

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        getMarkupSettings().setStripWicketTags(true);
        // add your configuration here
        
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, getApplicationContext()));
        
        getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this));

        // mount
        mountPage("/login", LoginPage.class);
        mountPage("/signup", SignUpPage.class);
        mountPage("/linkedit", LinkEditPage.class);
        mountPage("/links", LinksPage.class);
        mountPage(URL_ADDITIONAL_NAME+"/${urlCode}", RealUrlRedirectorPage.class);

    }
    
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return AuthorizedSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return HomePage.class;
	}
	


}