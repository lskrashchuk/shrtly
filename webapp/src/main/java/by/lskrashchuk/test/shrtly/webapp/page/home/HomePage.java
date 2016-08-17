package by.lskrashchuk.test.shrtly.webapp.page.home;


import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.webapp.app.AuthorizedSession;
import by.lskrashchuk.test.shrtly.webapp.component.search.SearchPanel;
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;

@AuthorizeInstantiation(value = {})
public class HomePage extends AbstractPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomePage() {
        super();
        
        UserProfile loggedUser = AuthorizedSession.get().getLoggedUser();
        
		SearchPanel searchPanel = new SearchPanel("search-panel");
        add(searchPanel);

    }

}