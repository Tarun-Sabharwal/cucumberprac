
package stepdefinitions;

import java.util.*;
import io.cucumber.java.en.*;
import io.cucumber.java.DataTableType;
import io.cucumber.datatable.DataTable;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccessSteps {

    List<User> users;
    List<User> haveAccess=new ArrayList<>();
    int count=0;


    @DataTableType
    public User userEntry(Map<String,String> row) {

        User u=new User();
        u.set_id(Integer.parseInt(row.get("id")));
        u.set_name(row.get("name"));
        u.set_access(Boolean.parseBoolean(row.get("access")));
        return u;

    }

    @Given("Following user exists")
    public void following_user_exists(DataTable table)
    {
        users=table.asList(User.class);

    }

    @When("access is true")
    public void access_is_true()
    {
        System.out.println("================USER ACCESS==============================");
        for(User u:users)
        {
            if(u.get_access())
            {
                count++;
                haveAccess.add(u);
            }
        }
    }

    @Then("only authorized users allowed")
    public void only_authorized_users_allowed()
    {
        assertEquals(3,count);


        // list size should match
        assertEquals(count, haveAccess.size());

        //  all users in list should have access = true
        boolean allTrue = haveAccess.stream()
                .allMatch(User::get_access);

        assertTrue(allTrue);

        //  ensure a specific user exists
        boolean johnExists = haveAccess.stream()
                .anyMatch(u -> u.get_name().equals("John"));

        assertTrue(johnExists);

        // ensure a user without access is NOT present
        boolean toneExists = haveAccess.stream()
                .anyMatch(u -> u.get_name().equals("Tone"));

        assertFalse(toneExists);

        System.out.println("Access validation passed for " + count + " users");
        for(User u : haveAccess)
        {
            System.out.println("Authorized User: " +u.get_id()+" -> "+ u.get_name());
        }
    }

}
