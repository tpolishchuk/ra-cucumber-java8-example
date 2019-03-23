package org.lrp.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber-reports"},
                 features = "src/test/resources/features/",
                 glue = {"org.lrp.steps"},
                 tags = {"not @excluded"})
public class CucumberRunner {
}
