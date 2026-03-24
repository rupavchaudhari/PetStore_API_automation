package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {

	@Test(priority=1, dataProvider="data",dataProviderClass=DataProviders.class)
	public void testPostUser(String userID,String userName,String fname,String lname,String useremail,String pwd,String phone)
	{
		User userpayload = new User();
		userpayload.setId(Integer.parseInt(userID));
		userpayload.setUsername(userName);
		userpayload.setFirstname(fname);
		userpayload.setLastname(lname);
		userpayload.setEmail(useremail);
		userpayload.setPassword(pwd);
		userpayload.setPhone(phone);
		
		Response response=UserEndpoints.createUser(userpayload);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{
		
		Response response=UserEndpoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
		

