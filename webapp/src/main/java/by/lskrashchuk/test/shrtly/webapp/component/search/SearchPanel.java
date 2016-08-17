package by.lskrashchuk.test.shrtly.webapp.component.search;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;


public class SearchPanel extends Panel{

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
		
		form.add(new AjaxSubmitLink("searchLink") {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				searchInput = searchField.getInput();
				if (searchInput.isEmpty()) searchInput = null;
/*				UserFilter userFilter = new UserFilter();
				userFilter.setUserName(searchInput);
				userListPanel.setUserFilter(userFilter);
				target.add(userListPanel);*/
			}
		});

		
	}

}
