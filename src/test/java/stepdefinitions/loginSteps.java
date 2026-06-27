package stepdefinitions;

import io.cucumber.java.en.*;

public class loginSteps {

    String enteredUsername;
    String enteredPassword;
    boolean loginStatus = false;

    @Given("user is on login page")
    public void user_is_on_login_page() {
        System.out.println("User is on login page");
    }

    @When("user enters username {string} and password {string}")
    public void user_enters_credentials(String username, String password) {
        enteredUsername = username;
        enteredPassword = password;
    }

    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        // simple validation
        if (enteredUsername.equals("admin") && enteredPassword.equals("admin123")) {
            loginStatus = true;
        }

        if (loginStatus) {
            System.out.println("Login successful ");
        } else {
            throw new AssertionError("Login failed ❌");
        }
    }

    @Then("login should fail")
    public void login_should_fail() {
        if (!(enteredUsername.equals("admin") && enteredPassword.equals("admin123"))) {
            System.out.println("Login failed as expected test case ");
        } else {
            throw new AssertionError("Login should have failed ❌");
        }
    }
}
