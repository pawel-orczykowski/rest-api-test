package apitests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        plugin = {"pretty", "html:target/CucumberReport", "json:target/CucumberReport.json"},
        glue = "apitests",
        strict = true,
        monochrome = true
)

public class ApiTestRunner {}
