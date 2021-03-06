package by.lskrashchuk.jobtest.shrtly.webapp.page.tag;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.service.TagService;
import by.lskrashchuk.jobtest.shrtly.webapp.page.AbstractPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.links.LinkEditPage;

@AuthorizeInstantiation(value = {"SIGNED_IN"})
public class TagEditPage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Tag tag;

	private Url url;
	
	private List<Tag> deletedTags;
	
	private String oldTagName;

	@Inject
	private TagService tagService;

	public TagEditPage(Url url, Tag tag, List<Tag> deletedTags) {
		super();
		this.tag = tag;
		this.url = url;
		this.deletedTags = deletedTags;
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
				Tag beforeEditTag = tag;
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
					beforeEditTag.setName(oldTagName);
					LinkEditPage page = new LinkEditPage(url, deletedTags);
					page.info("Tag already exist for this url");
					setResponsePage(page);
					return;
				} else {
					url.getTags().add(tag);
					if (oldTagName != null) {
						Tag oldTag =  tagService.find(oldTagName);
						url.getTags().remove(oldTag);
						deletedTags.add(oldTag);
					}
					setResponsePage(new LinkEditPage(url, deletedTags));
				}
				
			}
		});

		add(form);

		form.add(new FeedbackPanel("feedbackpanel"));

	}

}
