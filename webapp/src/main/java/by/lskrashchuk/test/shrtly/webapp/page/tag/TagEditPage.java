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
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.test.shrtly.webapp.page.links.LinkEditPage;

public class TagEditPage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Tag tag;

	private Url url;

	@Inject
	private TagService tagService;

	public TagEditPage(Url url, Tag tag) {
		super();
		this.tag = tag;
		this.url = url;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Void> form = new Form<Void>("form");
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
					tagService.saveOrUpdate(tag);
				}

				if (url.getTags() == null) {
					List<Tag> tags = new ArrayList<Tag>();
					url.setTags(tags);
				}

				if (url.getTags().contains(tag)) {
					LinkEditPage page = new LinkEditPage(url);
					page.info("Tag already exist for this url");
					setResponsePage(page);
					return;
				} else {
					url.getTags().add(tag);
					setResponsePage(new LinkEditPage(url));

				}
			}
		});

		add(form);

		form.add(new FeedbackPanel("feedbackpanel"));

	}

}
