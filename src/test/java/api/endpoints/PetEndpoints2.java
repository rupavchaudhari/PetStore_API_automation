package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.Pet;
import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndpoints2 {

	static ResourceBundle getURL()
	{
		//method created for getting URL's from properties file
		ResourceBundle routes=ResourceBundle.getBundle("routes"); //Load properties file
		return routes;
	}
	
	
	public static Response createPet(Pet petpayload)
	{
		String post_url=getURL().getString("post_url1");
				
				Response response = given()
				.contentType("application/json")
				.accept(ContentType.JSON)
				.body(petpayload)
			
			.when()
				.post(post_url);
				
			return response;		
	}
	
	public static Response readPet(Integer Id)
	{
		String get_url = getURL().getString("get_url1");
				Response response = given()
								.pathParam("Id", Id)
								
		.when()
			.get(get_url);
		
		return response;
		
	}
	
	public static Response updatePet(Pet petpayload)
	{
		String updatePet_url = getURL().getString("update_url1");
		Response response = given()
				.contentType("application/json")
				.accept(ContentType.JSON)
				.body(petpayload)
					
			.when()
				.put(updatePet_url);
			
			return response;
	}
	
	public static Response deletePet(Integer Id)
	{
		String deletePet_url = getURL().getString("delete_url1");
			Response response = given()
								.pathParam("Id",Id)
			.when()
				.get(deletePet_url);
			
			return response;
	}
}
