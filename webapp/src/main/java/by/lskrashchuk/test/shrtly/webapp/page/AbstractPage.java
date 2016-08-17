package by.lskrashchuk.test.shrtly.webapp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.lskrashchuk.test.shrtly.webapp.page.home.HomePage;



public abstract class AbstractPage extends WebPage {

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

		add(new Link("link-home") {
            @Override
            public void onClick() {
                setResponsePage(new HomePage());
            }
        });
	       

//		add(new LoginPanel("login-panel"));
	}
	

}
