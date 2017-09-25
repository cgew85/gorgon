package io.github.cgew85;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by cgew85 on 25.09.2017.
 */
public class StepDefsIntegrationTest extends SpringIntegrationTest {

    @When("^the client calls /movies$")
    public void the_client_issues_GET_movies() throws Throwable {
        executeGet("http://localhost:8080/movies");
    }

    @Then("^the client receives the status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        HttpStatus httpStatus = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " +
                latestResponse.getBody(), httpStatus.value(), is(statusCode));
    }
}
