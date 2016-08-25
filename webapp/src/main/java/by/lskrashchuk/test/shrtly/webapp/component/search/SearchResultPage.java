package by.lskrashchuk.test.shrtly.webapp.component.search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import by.lskrashchuk.test.shrtly.dataaccess.TagDao;
import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.TagService;
import by.lskrashchuk.test.shrtly.service.UrlService;
import by.lskrashchuk.test.shrtly.webapp.app.WicketApplication;
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.test.shrtly.webapp.page.links.LinkViewPage;
import by.lskrashchuk.test.shrtly.webapp.page.tag.TagLinksViewPage;

public class SearchResultPage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String searchInput;

	@Inject
	private TagService tagService;
	
	@Inject
	private UrlService urlService;

	public SearchResultPage(String searchInput) {
		super();
		this.searchInput = searchInput;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form");
		add(form);

		List<Tag> list = new ArrayList<Tag>();
		if (searchInput != null) {
			String urlPrefix = WicketApplication.DOMAIN_NAME+WicketApplication.URL_ADDITIONAL_NAME+"/";
			Integer prefixIndex = searchInput.indexOf(urlPrefix);
			if (prefixIndex != -1) {
				Url url = new Url();
				url = urlService.find(searchInput.substring(prefixIndex+urlPrefix.length()));
				setResponsePage(new LinkViewPage(urlService.getUrlWithTags(url.getId())));
			}
			for (Tag tag : tagService.getAll()) {
				if (tag.getName().indexOf(searchInput) != -1)
					list.add(tag);
			}
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
		form.add(listview);
	}

	public String getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}

}
