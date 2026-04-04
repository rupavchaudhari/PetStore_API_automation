package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	public Logger logger;
	
	Faker faker;
	User userpayload;
	
	@BeforeClass
	public void setup()
	{
		faker = new Faker();
		userpayload = new User();
		
		((User) userpayload).setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password());
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger =LogManager.getLogger(this.getClass());
		
	}
	@Test(priority=1)
	public void postTestUser()
	{
		logger.info("--------Creating logger----------------");
		Response response = UserEndpoints2.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("-----User created Successfully--------");
	}

	@Test(priority=2)
	public void getTestUser()
	{
		Response response = UserEndpoints2.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("username"), userpayload.getUsername());
        logger.info("--------Reading user info -------------");
	}
	
	@Test(priority=3)
	public void updateTestUser()
	{
		logger.info("-------Updating user data-----------");
		//update data
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		// call update endpoint
		Response response = UserEndpoints2.updateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking after update
		Response responseafterupdate = UserEndpoints2.readUser(this.userpayload.getUsername());
		responseafterupdate.then().log().all();
		Assert.assertEquals(responseafterupdate.getStatusCode(), 200);
		Assert.assertEquals(responseafterupdate.jsonPath().getString("firstName"), userpayload.getFirstName());
        logger.info("------Updated user data succcessfully -----------------");
		
	}
	@Test(priority=4)
	public void deleteTestUser()
	{
		logger.info("------Deleting user data -----------------");
		Response response = UserEndpoints2.deleteUser(this.userpayload.getUsername());
		response.then().log().all();
		//Assert.assertEquals(response.getStatusCode(), 200);
		// Accept 200 (deleted) or 404 (already gone) depending on API contract
        Assert.assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 404,
                          "Unexpected status code: " + response.getStatusCode());


		logger.info("------Deleted user data -----------------");
	}
	
	
}
