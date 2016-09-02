package by.lskrashchuk.jobtest.shrtly.webapp.page.redirect;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.service.UrlService;
import by.lskrashchuk.jobtest.shrtly.webapp.app.WicketApplication;
import by.lskrashchuk.jobtest.shrtly.webapp.page.AbstractPage;

public class RealUrlRedirectorPage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String urlCode;

	@Inject
	private UrlService urlService;

	public RealUrlRedirectorPage(PageParameters parameters) {
		super(parameters);
		urlCode = parameters.get("urlCode").toString();
		if (!urlCode.isEmpty()) {
			Url url = urlService.find(this.urlCode);
			if (url != null) {
				String fullUrl = urlService.redirect(url);
				throw new RedirectToUrlException(fullUrl);
			}
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("url", "URL " + WicketApplication.getDomainName() + WicketApplication.URL_ADDITIONAL_NAME + "/"
				+ urlCode + " not found"));
	}

}
