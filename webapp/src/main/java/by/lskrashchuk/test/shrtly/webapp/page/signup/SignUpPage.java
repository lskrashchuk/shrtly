package by.lskrashchuk.test.shrtly.webapp.page.signup;

import javax.inject.Inject;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;

import by.lskrashchuk.test.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.service.UserProfileService;
import by.lskrashchuk.test.shrtly.webapp.page.AbstractPage;

public class SignUpPage extends AbstractPage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserProfileService userProfileService;

	public static final String ID_FORM = "form";

    private UserProfile userProfile;
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordAgain;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        
        userProfile = new UserProfile();

        // if already logged then should not see login page at all
        if (AuthenticatedWebSession.get().isSignedIn()) {
            setResponsePage(Application.get().getHomePage());
        }

        final Form<Void> form = new Form<Void>(ID_FORM);
        form.setDefaultModel(new CompoundPropertyModel<SignUpPage>(this));
        form.add(new RequiredTextField<String>("firstName").setLabel(new ResourceModel("fname")));
        form.add(new RequiredTextField<String>("lastName").setLabel(new ResourceModel("lname")));
        form.add(new RequiredTextField<String>("email").setLabel(new ResourceModel("email")));
        form.add(new PasswordTextField("password").setLabel(new ResourceModel("password")));
        form.add(new PasswordTextField("passwordAgain").setLabel(new ResourceModel("passwordagain")));

        form.add(new SubmitLink("submit-btn") {
            @Override
            public void onSubmit() {
                super.onSubmit();
                
                if (!password.equals(passwordAgain)){
                	error("Not repeat the same password");
                	return;
                }
                
                UserProfileFilter uf = new UserProfileFilter();
                uf.setEmail(email);
                if (userProfileService.find(uf).size()>0){
                	error("This email already registered");
                	return;
                }
                
                userProfile.setFirstName(firstName);
                userProfile.setLastName(lastName);
                userProfile.setEmail(email);
                userProfile.setPassword(password);
                
				userProfileService.register(userProfile);
				
                if (Strings.isEmpty(email) || Strings.isEmpty(password)) {
                    return;
                }
                final boolean authResult = AuthenticatedWebSession.get().signIn(email, password);
                if (authResult) {
                    // continueToOriginalDestination();
                    setResponsePage(Application.get().getHomePage());
                } else {
                    error("Wrong password or username");
                }
            }
        });

        add(form);

        add(new FeedbackPanel("feedbackpanel"));

    }
    
}
