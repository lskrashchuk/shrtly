package by.lskrashchuk.jobtest.shrtly.webapp.page.links;

import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.service.UrlService;
import by.lskrashchuk.jobtest.shrtly.service.impl.SimpleUrlShortener;
import by.lskrashchuk.jobtest.shrtly.service.impl.UrlByIdShortener;
import by.lskrashchuk.jobtest.shrtly.webapp.app.AuthorizedSession;
import by.lskrashchuk.jobtest.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.links.panel.LinkListPanel;


@AuthorizeInstantiation(value = {"SIGNED_IN"})
public class LinksPage extends AbstractPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SimpleUrlShortener simpleUrlShortener;
	
	@Inject
	private UrlService urlService;

	public LinksPage() {
		super();
		
		Form<Void> form = new Form<Void>("form");
		add(form);


		UrlFilter urlFilter = new UrlFilter();
		urlFilter.setUserProfile(AuthorizedSession.get().getLoggedUser());
		
		LinkListPanel linkListPanel = new LinkListPanel("list-panel", urlFilter);
		linkListPanel.setOutputMarkupId(true);
        form.add(linkListPanel);

        RequiredTextField<String> longUrlField = new RequiredTextField<>("longUrlField", Model.of(""));
		form.add(longUrlField);
		
		form.add(new SubmitLink("shortLink") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit() {
				super.onSubmit();
				Url url = new Url();
				url.setFullUrl(longUrlField.getInput());
				url.setUserProfile(AuthorizedSession.get().getLoggedUser());
				url.setClicks(0);
//				url.setUrlCode(simpleUrlShortener.getCode(url));
				url.setUrlCode("temporary");
				urlService.saveOrUpdate(url);
				url.setUrlCode(new UrlByIdShortener().getCode(url));
				urlService.saveOrUpdate(url);
			
				setResponsePage(new LinkEditPage(url, new ArrayList<Tag>()));
			}
		});

        
        
		form.add(new FeedbackPanel("feedback"));
 
	}

}
