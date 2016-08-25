package by.lskrashchuk.test.shrtly.webapp.page.links;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.TagService;
import by.lskrashchuk.test.shrtly.service.UrlService;
import by.lskrashchuk.test.shrtly.webapp.app.WicketApplication;
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.test.shrtly.webapp.page.redirect.RealUrlRedirectorPage;
import by.lskrashchuk.test.shrtly.webapp.page.tag.TagEditPage;
import by.lskrashchuk.test.shrtly.webapp.page.tag.TagLinksViewPage;

public class LinkEditPage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UrlService urlService;

	@Inject
	private TagService tagService;

	private Url url;
	
	private List<Tag> deletedTags;

	public LinkEditPage() {
		super();
	}
	
	public LinkEditPage(PageParameters parameters) {
		super(parameters);
	}

	public LinkEditPage(Url url, List<Tag> deletedTags) {
		super();
		this.url = url;
		this.deletedTags = deletedTags;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Url> form = new Form<Url>("form");
		form.setDefaultModel(new CompoundPropertyModel<Url>(url));
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

		form.add(el);
		form.add(new ExternalLink("fullUrl", url.getFullUrl(), url.getFullUrl()));
		form.add(new TextArea<String>("description"));

		form.add(new Link<Void>("add-tag-button") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new TagEditPage(url, new Tag(), deletedTags));
			}

		});

		List<Tag> list = new ArrayList<Tag>();
		if (url.getTags()!=null) {
			list.addAll(url.getTags());
		}
		// list = url.getTags();
/*		if (url.getTags() != null) {
			for (Tag tag : url.getTags()) {
				list.add(tag);
			}
		}*/

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
				tagLink.add(new Link<Void>("tag-delete-link") {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						url.getTags().remove(list.get(item.getIndex()));
						deletedTags.add(list.get(item.getIndex()));
						setResponsePage(new LinkEditPage(url, deletedTags));
					}
						
				});
				tagLink.add(new Link<Void>("tag-edit-link") {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						setResponsePage(new TagEditPage(url, list.get(item.getIndex()), deletedTags));
					}

				});

			}
		};
		form.add(listview);

		form.add(new SubmitLink("submit-btn") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				if (url.getId() != null) {
					url.setClicks(urlService.find(url.getUrlCode()).getClicks());
				}
				urlService.saveOrUpdate(url);
				for (Tag tag : deletedTags) {
					tagService.delete(tag);
				}
				LinksPage page = new LinksPage();
				setResponsePage(page);
			}
		});

		add(form);

		form.add(new FeedbackPanel("feedbackpanel"));
	}

}
