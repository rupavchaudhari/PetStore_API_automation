package api.test;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.PetEndpoints2;
import api.endpoints.UserEndpoints;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
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
		userpayload.setFirstName(fname);
		userpayload.setLastName(lname);
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
	
	@Test(dataProvider="petdata",dataProviderClass=DataProviders.class)
	public void PostTestPet(String id, String categoryId, String categoryName,
            String petName, String tagId, String tagName, String status) 
	{
		Category category = new Category();
        category.setId(Integer.parseInt(categoryId));
        category.setName(categoryName);

        Tag tag = new Tag();
        tag.setId(Integer.parseInt(tagId));
        tag.setName(tagName);

        Pet pet = new Pet();
        pet.setId(Integer.parseInt(id));
        pet.setCategory(category);
        pet.setName(petName);
        pet.setPhotoUrls(Arrays.asList("http://example.com/photo.jpg"));
        pet.setTags(Arrays.asList(tag));
        pet.setStatus(status);

        Response response = PetEndpoints2.createPet(pet);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

	
	
	}
		
	
}
		

