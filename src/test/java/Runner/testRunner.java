package Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/Features", glue= {"Steps"},
monochrome=true,
plugin = {"pretty", "html:target/HTMLReports/",
		"json:target/JSONReports/report.json",
		"junit:target/JUnitReports/report.xml",
		"json:target/cucumber.json",}
//tags="@SmokeTest"
		)
public class testRunner {

}
