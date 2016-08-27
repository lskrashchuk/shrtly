package by.lskrashchuk.jobtest.shrtly.webapp.page;

import java.io.Serializable;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
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
		
		Link linkHome = new Link("link-home") {
            @Override
            public void onClick() {
                setResponsePage(new HomePage());
            }
		};
		add(linkHome);
	       
		Link linkLogin = new Link("link-login") {
            @Override
            public void onClick() {
                setResponsePage(new LoginPage());
            }
        };
		add(linkLogin);

		Link linkSignUp = new Link("link-signup") {
            @Override
            public void onClick() {
                setResponsePage(new SignUpPage());
            }
        };
		add(linkSignUp);

		Link linkSignOut = new Link("link-signout") {
            @Override
            public void onClick() {
                getSession().invalidate();
                setResponsePage(new HomePage());
            }
        };
		add(linkSignOut);
		
        if (AuthenticatedWebSession.get().isSignedIn()) {
//          setResponsePage(Application.get().getHomePage());
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
