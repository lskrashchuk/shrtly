package by.lskrashchuk.jobtest.shrtly.webapp.component.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import by.lskrashchuk.jobtest.shrtly.dataaccess.TagDao;
import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;
import by.lskrashchuk.jobtest.shrtly.webapp.page.home.HomePage;


public class SearchPanel extends Panel {

	/**
	 * 
	 */
	public String searchInput;
	

	private static final long serialVersionUID = 1L;

	public SearchPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();


		Form form = new Form("form");
		add(form);
		
		TextField<String> searchField = new TextField<>("searchField", Model.of(""));
		searchField.setLabel(new ResourceModel("label.search"));
		form.add(searchField);
		
		form.add(new SubmitLink("searchLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				String d = searchField.getInput();
				searchInput = searchField.getInput();
				if (searchInput.isEmpty())
					searchInput = null;
				if (searchInput != null) {
					setResponsePage(new SearchResultPage(searchInput));
				}
			}
		});

	}

}
