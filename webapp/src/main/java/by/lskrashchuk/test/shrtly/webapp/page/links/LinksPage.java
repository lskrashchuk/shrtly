package by.lskrashchuk.test.shrtly.webapp.page.links;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.webapp.app.AuthorizedSession;
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.test.shrtly.webapp.page.links.panel.LinkListPanel;


public class LinksPage extends AbstractPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LinksPage() {
		super();

		UrlFilter urlFilter = new UrlFilter();
		urlFilter.setUserProfile(AuthorizedSession.get().getLoggedUser());
		
		LinkListPanel linkListPanel = new LinkListPanel("list-panel", urlFilter);
		linkListPanel.setOutputMarkupId(true);
        add(linkListPanel);

        
        add(new FeedbackPanel("feedback"));
 
	}

}
