package by.lskrashchuk.jobtest.shrtly.webapp.page.home;


import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;

import by.lskrashchuk.jobtest.shrtly.webapp.app.WicketApplication;
import by.lskrashchuk.jobtest.shrtly.webapp.component.search.SearchPanel;
import by.lskrashchuk.jobtest.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.links.LinksPage;

public class HomePage extends AbstractPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomePage() {
        super();
//        WicketApplication.DOMAIN_NAME = RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(urlFor(HomePage.class,null).toString()));
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
        SearchPanel searchPanel = new SearchPanel("search-panel");
        add(searchPanel);

        Link<Void> myLinksLink = new Link<Void>("my-links-link"){
 
 			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
        		setResponsePage(new LinksPage());
			}
        };
        add(myLinksLink);
        if (AuthenticatedWebSession.get().isSignedIn()) {
        	myLinksLink.setVisible(true);
        }
        else {
        	myLinksLink.setVisible(false);
        }     

    }

}
