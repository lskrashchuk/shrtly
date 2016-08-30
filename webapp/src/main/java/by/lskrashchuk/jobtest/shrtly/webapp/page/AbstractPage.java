package by.lskrashchuk.jobtest.shrtly.webapp.page;

import java.io.Serializable;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.lskrashchuk.jobtest.shrtly.webapp.page.home.HomePage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.login.LoginPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.signup.SignUpPage;



public abstract class AbstractPage extends WebPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AbstractPage() {
		super();
	}

	public AbstractPage(PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		Link<Void> linkHome = new Link<Void>("link-home") {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick() {
                setResponsePage(new HomePage());
            }
		};
		add(linkHome);
	       
		Link<Void> linkLogin = new Link<Void>("link-login") {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick() {
                setResponsePage(new LoginPage());
            }
        };
		add(linkLogin);

		Link<Void> linkSignUp = new Link<Void>("link-signup") {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick() {
                setResponsePage(new SignUpPage());
            }
        };
		add(linkSignUp);

		Link<Void> linkSignOut = new Link<Void>("link-signout") {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void onClick() {
                getSession().invalidate();
                setResponsePage(new HomePage());
            }
        };
		add(linkSignOut);
		
        if (AuthenticatedWebSession.get().isSignedIn()) {
        	linkLogin.setVisible(false);
        	linkSignUp.setVisible(false);
        	linkSignOut.setVisible(true);
        }
        else {
        	linkLogin.setVisible(true);
        	linkSignUp.setVisible(true);
        	linkSignOut.setVisible(false);
        }
	}
	
}
