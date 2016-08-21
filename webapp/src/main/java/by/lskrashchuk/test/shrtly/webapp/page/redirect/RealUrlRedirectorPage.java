package by.lskrashchuk.test.shrtly.webapp.page.redirect;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;

public class RealUrlRedirectorPage extends AbstractPage{
	
	private String urlCode;

	public RealUrlRedirectorPage(PageParameters parameters) {
		super(parameters);
		StringValue urlCode = parameters.get("urlCode");
		this.urlCode = urlCode.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("url", urlCode));
	}

}
