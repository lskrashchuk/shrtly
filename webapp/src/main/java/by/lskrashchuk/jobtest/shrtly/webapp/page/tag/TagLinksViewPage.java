package by.lskrashchuk.jobtest.shrtly.webapp.page.tag;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.webapp.app.WicketApplication;
import by.lskrashchuk.jobtest.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.redirect.RealUrlRedirectorPage;

public class TagLinksViewPage extends AbstractPage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Tag tag;
	
	public TagLinksViewPage(Tag tag) {
		super();
		this.tag = tag;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		List<Url> list = new ArrayList<Url>();
		if (tag.getUrls()!=null) {
			list.addAll(tag.getUrls());
		}

		ListView<Url> listview = new ListView<Url>("urllist", list) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem<Url> item) {
				Link<Void> urlLink = new Link<Void>("url-link") {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						PageParameters params = new PageParameters();
						params.set("urlCode", list.get(item.getIndex()).getUrlCode());
						setResponsePage(new RealUrlRedirectorPage(params));
					}
				};
				item.add(urlLink);
				urlLink.add(new Label("url", WicketApplication.getDomainName()+WicketApplication.URL_ADDITIONAL_NAME+"/"+list.get(item.getIndex()).getUrlCode()));
			}
		};
		add(listview);
		
	}

}
