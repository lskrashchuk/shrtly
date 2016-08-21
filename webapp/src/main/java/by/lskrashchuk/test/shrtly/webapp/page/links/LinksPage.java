package by.lskrashchuk.test.shrtly.webapp.page.links;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.service.impl.SimpleUrlShortener;
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
		
		Form form = new Form("form");
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
				url.setUrlCode(new SimpleUrlShortener().getCode(longUrlField.getInput()));
				url.setUserProfile(AuthorizedSession.get().getLoggedUser());
				setResponsePage(new LinkEditPage(url));
			}
		});

        
        
		form.add(new FeedbackPanel("feedback"));
 
	}

}
