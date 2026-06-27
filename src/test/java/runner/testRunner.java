package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// Beginner-friendly Cucumber runner using JUnit 4

@RunWith(Cucumber.class)
//@SelectClasspathResource("features/studentTC.feature")
@CucumberOptions(
        features = "src/test/resources",  // where feature files live
        glue = "stepdefinitions"   ,        // where step definitions live

        tags = "@tc",   //  only TC tests
        plugin = {
                    "pretty",
                    "html:target/cucumber-report.html",
                    "json:target/cucumber.json"
                 }




)
public class testRunner {
    // Empty class — @RunWith tells JUnit to use Cucumber
}
