package io.github.cgew85;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by cgew85 on 25.09.2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CucumberIntegrationTest {
}
