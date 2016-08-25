package by.lskrashchuk.test.shrtly.webapp.page.links;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.TagService;
import by.lskrashchuk.test.shrtly.webapp.app.WicketApplication;
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.test.shrtly.webapp.page.redirect.RealUrlRedirectorPage;
import by.lskrashchuk.test.shrtly.webapp.page.tag.TagLinksViewPage;

public class LinkViewPage extends AbstractPage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Url url;
	
	@Inject
	private TagService tagService;

	public LinkViewPage(Url url) {
		super();
		this.url = url;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Link<Void> el = new Link<Void>("urlCode") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters params = new PageParameters();
				params.set("urlCode", url.getUrlCode());
				setResponsePage(new RealUrlRedirectorPage(params));
			}
		};
		el.add(new Label("linktext", Model
				.of(WicketApplication.DOMAIN_NAME + WicketApplication.URL_ADDITIONAL_NAME + "/" + url.getUrlCode())));

		add(el);
		add(new ExternalLink("fullUrl", url.getFullUrl(), url.getFullUrl()));
		add(new Label("description", url.getDescription()));

		List<Tag> list = new ArrayList<Tag>();
		if (url.getTags()!=null) {
			list.addAll(url.getTags());
		}

		ListView<Tag> listview = new ListView<Tag>("taglist", list) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem<Tag> item) {
				Link<Void> tagLink = new Link<Void>("tag-link") {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						setResponsePage(new TagLinksViewPage(tagService.getWithUrls(list.get(item.getIndex()))));
					}
				};
				item.add(tagLink);
				tagLink.add(new Label("tag", list.get(item.getIndex()).getName()));
			}
		};
		add(listview);

	}
}
