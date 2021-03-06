package by.lskrashchuk.jobtest.shrtly.webapp.app;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.springframework.beans.factory.annotation.Autowired;

import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile;
import by.lskrashchuk.jobtest.shrtly.service.UserProfileService;

public class AuthorizedSession extends AuthenticatedWebSession {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UserProfileService userProfileService;

	private UserProfile loggedUser;
	
    private Roles roles;
    

	public AuthorizedSession(Request request) {
		super(request);
		Injector.get().inject(this);

	}

	public static AuthorizedSession get() {
		return (AuthorizedSession) Session.get();
	}

	@Override
	public boolean authenticate(final String userName, final String password) {
		loggedUser = userProfileService.getByNameAndPassword(userName, password);
		return loggedUser != null;
	}

	@Override
	public Roles getRoles() {
        if (isSignedIn() && (roles == null)) {
            roles = new Roles();
            roles.add("SIGNED_IN");
        }
        return roles;
	}

	@Override
	public void signOut() {
		super.signOut();
		loggedUser = null;
	}

	public UserProfile getLoggedUser() {
		return loggedUser;
	}

}
