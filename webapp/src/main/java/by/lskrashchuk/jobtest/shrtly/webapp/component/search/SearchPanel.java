package by.lskrashchuk.jobtest.shrtly.webapp.component.search;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;



public class SearchPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SearchPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();


		Form<Void> form = new Form<Void>("form");
		add(form);
		
		TextField<String> searchField = new TextField<>("searchField", Model.of(""));
		searchField.setLabel(new ResourceModel("label.search"));
		form.add(searchField);
		
		form.add(new SubmitLink("searchLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				String searchInput = searchField.getInput();
				if (searchInput.isEmpty())
					searchInput = null;
				if (searchInput != null) {
					setResponsePage(new SearchResultPage(searchInput));
				}
			}
		});

	}

}
