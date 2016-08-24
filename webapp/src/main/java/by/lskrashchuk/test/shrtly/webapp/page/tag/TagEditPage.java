package by.lskrashchuk.test.shrtly.webapp.page.tag;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.TagService;
import by.lskrashchuk.test.shrtly.service.UrlService;
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.test.shrtly.webapp.page.links.LinkEditPage;

public class TagEditPage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Tag tag;

	private Url url;
	
	private Boolean b;
	
	private List<Tag> l;
	
	private String oldTagName;

	@Inject
	private TagService tagService;

	@Inject
	private UrlService urlService;

	public TagEditPage(Url url, Tag tag, Boolean b, List<Tag> l) {
		super();
		this.tag = tag;
		this.url = url;
		this.b = b;
		this.l = l;
		oldTagName = tag.getName();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Tag> form = new Form<Tag>("form");
		form.setDefaultModel(new CompoundPropertyModel<Tag>(tag));

		form.add(new RequiredTextField<String>("name"));
		form.add(new SubmitLink("submit-btn") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				Tag findTag = tagService.find(tag.getName());
				if (findTag != null) {
					tag = findTag;
				} else {
					Tag newTag = new Tag();
					newTag.setName(tag.getName());
					tagService.insert(newTag);
					tag = newTag;
				}

				if (url.getTags() == null) {
					List<Tag> tags = new ArrayList<Tag>();
					url.setTags(tags);
				}

				if (url.getTags().contains(tag)) {
					LinkEditPage page = new LinkEditPage(url, b, l);
					page.info("Tag already exist for this url");
					setResponsePage(page);
					return;
				} else {
					url.getTags().add(tag);
					if (oldTagName != null) {
						url.getTags().remove(tagService.find(oldTagName));
					}
					setResponsePage(new LinkEditPage(url, b, l));
				}
				
			}
		});

		add(form);

		form.add(new FeedbackPanel("feedbackpanel"));

	}

}
