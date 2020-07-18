package apitests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "apitests",
        strict = true,
        monochrome = true,
        plugin = {"pretty", "html:target/cucumber"}
)

public class ApiTestRunner {}
