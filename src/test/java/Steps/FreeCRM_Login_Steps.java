package Steps;

import org.junit.Assert;
import Base.baseClass;
import Pages.FreeCRM_Home_Page;
import Pages.FreeCRM_Landing_Page;
import Pages.FreeCRM_Login_Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class FreeCRM_Login_Steps extends baseClass {

	//WebDriver driver = null;
	FreeCRM_Landing_Page landing;
	FreeCRM_Login_Page login;
	FreeCRM_Home_Page homePage;

	@Before
	public void initialise() {
		browserInvoke();
	}


	@After
	public void tearDown() {
		browserQuit();
	}


	@Given("Free CRM site is already open in browser")
	public void free_CRM_site_is_already_open_in_browser() {		
		System.out.println("Inside Step - Browser open");
		openFreeCRMSite();
	}

	@When("I click on Login button in landing page")
	public void i_click_on_Login_button_in_landing_page() {
		System.out.println("Inside Step - click on login button");

		landing = new FreeCRM_Landing_Page();

		if(IsElepresent(landing.LandLoginBtn)) {		
			ClickEle(landing.LandLoginBtn);
		}				
	}

	@Then("Log in page appears")
	public void log_in_page_appears() {
		System.out.println("Inside Step - Login page opens");

		login = new FreeCRM_Login_Page();

		Match(getUrl(), "https://ui.freecrm.com/", "CHECKING LOOGING PAGE URL");
		Match(GetTitle(), login.loginPageTitle, "CHECKING LOOGING PAGE TITLE");

		try {
			if(GetTitle().equals(login.loginPageTitle)) {
				System.out.println("LOGIN PAGE IS LOADED SUCCESSFULLY");
			}
			else {
				System.out.println("CAN NOT LOAD LOGIN PAGE. PLEASE CHECK...");
			}
		}
		catch(Exception e){
			Assert.assertFalse("CAN NOT LOAD LOGIN PAGE. PLEASE CHECK..."+e, false);
		}
	}




	@When("^I enter (.*) and (.*) and click on Login button$")
	public void i_enter_Email_and_Password_and_click_on_Login_button(String email, String password) throws InterruptedException {
		System.out.println("Inside Step - Entering email and password");

		if(IsElepresent(login.emailAddress) && IsElepresent(login.password) && IsElepresent(login.loginPageLoginButton)) {
			TypeText(login.emailAddress, email);
			Wait(1000);
			TypeText(login.password, password);
			ClickEle(login.loginPageLoginButton);
		}

		//login.loginToCRM(email, password);
	}

	@Then("^I am successfully logged into the Free CRM application and able to see the (.*) name$")
	public void i_am_successfully_logged_into_the_Free_CRM_application_and_able_to_see_the_LoggedinUser_name(String loggedInUser) throws InterruptedException {
		System.out.println("Inside Step - User is navigated to home page");
		Wait(2000);

		homePage = new FreeCRM_Home_Page();

		if(IsElepresent(homePage.SettingsButton)) {
			System.out.println("HOME PAGE IS OPEN SUCCESSFULLY");
		}
		else {
			System.out.println("CAN NOT OPEN HOME PAGE. PLEASE CHECK...");				
		}

		//checking GetText() method
		System.out.println("TEXT OF LOGGED IN USER NAME = "+GetText(homePage.userNameDisplay));

		Wait(2000);

		//logging out
		homePage.ClickEle(homePage.SettingsButton);
		Wait(1000);
		homePage.ClickEle(homePage.LogoutLink);
		Wait(2000);
	}
}

