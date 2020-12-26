package Pages;

import Base.baseClass;

public class FreeCRM_Login_Page extends baseClass{
	
	public String emailAddress = "//input[@name = 'email']";
	public String password = "//input[@name = 'password']";
	public String loginPageLoginButton = "//*[@class= 'ui fluid large blue submit button'][contains(text(), 'Login')]";
	
	public String loginPageTitle = "Cogmento CRM";

}
