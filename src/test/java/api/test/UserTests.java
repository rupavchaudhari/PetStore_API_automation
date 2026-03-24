package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
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
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
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
		Response response = UserEndpoints.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("-----User created Successfully--------");
	}

	@Test(priority=2)
	public void getTestUser()
	{
		Response response = UserEndpoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("--------Reading user info -------------");
	}
	
	@Test(priority=3)
	public void updateTestUser()
	{
		logger.info("-------Updating user data-----------");
		//update data
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints.updateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking after update
		Response responseafterupdate = UserEndpoints.readUser(this.userpayload.getUsername());
		//response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("------Updated user data -----------------");
		
	}
	@Test(priority=4)
	public void deleteTestUser()
	{
		logger.info("------Deleting user data -----------------");
		Response response = UserEndpoints.deleteUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("------Deleted user data -----------------");
	}
	
	
}
