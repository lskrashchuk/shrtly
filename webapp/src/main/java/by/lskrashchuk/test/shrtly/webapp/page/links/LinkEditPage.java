package by.lskrashchuk.test.shrtly.webapp.page.links;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.UrlService;
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;

public class LinkEditPage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UrlService urlService;
	
	private Url url;
	
	public LinkEditPage(Url url) {
		super();
		this.url = url;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Void> form = new Form<Void>("form");
        form.setDefaultModel(new CompoundPropertyModel<Url>(url));
 
        form.add(new RequiredTextField<String>("urlCode"));
        form.add(new RequiredTextField<String>("fullUrl"));
        form.add(new TextArea<String>("description"));

		List<Tag> list = new ArrayList<Tag>();
//		list = url.getTags();
		if (url.getTags() != null) {
			for (Tag tag : url.getTags()) {
				list.add(tag);
			}
		}

		ListView listview = new ListView("taglist", list) {
			protected void populateItem(ListItem item) {
				Link<Void> tagLink = new Link<Void>("tag-link"){

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
//						setResponsePage(new LinksPage());
					}
				};	
				item.add(tagLink);

				tagLink.add(new Label("tag", list.get(item.getIndex()).getName()));

			}
		};
		form.add(listview);

        
        
        form.add(new SubmitLink("submit-btn") {
            @Override
            public void onSubmit() {
				urlService.saveOrUpdate(url);
				LinksPage page = new LinksPage();
                page.info("Url saved");
				setResponsePage(page);
             }
        });

        add(form);

        form.add(new FeedbackPanel("feedbackpanel"));	}
	
}
