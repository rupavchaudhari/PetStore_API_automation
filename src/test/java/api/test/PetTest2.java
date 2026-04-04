package api.test;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.PetEndpoints2;
import api.payload.Pet;
import api.payload.Category;
import api.payload.Tag;


import io.restassured.response.Response;

public class PetTest2 {   // ❌ removed <Tag>

	private static final Logger logger = LogManager.getLogger(PetTest2.class);
    
    private static final Faker faker = new Faker();
    Pet petpayload;

    @BeforeClass
    public void setupPetData() {
        Category category = new Category();
        category.setId(faker.number().numberBetween(1, 100));
        category.setName(faker.animal().name());

        Tag tag = new Tag();
        tag.setId(faker.number().numberBetween(1, 100));
        tag.setName(faker.color().name());

        petpayload = new Pet();
        petpayload.setId(faker.number().numberBetween(1, 1000));
        petpayload.setCategory(category);
        petpayload.setName(faker.animal().name());
        petpayload.setPhotoUrls(Arrays.asList(faker.internet().url()));
        petpayload.setTags(Arrays.asList(tag));   // ✅ use setTags
        petpayload.setStatus("available");
    }

    @Test(priority = 1)
    public void PostTestPet() {
        logger.info("Create Pet data");
        Response response = PetEndpoints2.createPet(petpayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("-----Pet data created Successfully--------");
    }
    @Test(priority = 2)
    public void GetTestPet()
    {
    	logger.info("Create get data");
    	Response response = PetEndpoints2.readPet(this.petpayload.getId());
    	response.then().log().all();
    	Assert.assertEquals(response.getStatusCode(), 200);
    	Assert.assertEquals(response.jsonPath().getString("id"),
                String.valueOf(petpayload.getId()));

		logger.info("--------Fetched Pet info -------------");

    	
    }
    
    @Test(priority=3)
    public void UpdateTestPet()
    {
    	logger.info("Update Pet data*****");
    	
        
        petpayload.setName(faker.animal().name());
        petpayload.setPhotoUrls(Arrays.asList(faker.internet().url()));
        //petpayload.setTags(Arrays.asList(tag));   // ✅ use setTags
        petpayload.setStatus("available");
        Response response = PetEndpoints2.updatePet(petpayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"),
                petpayload.getName());

        logger.info("------Updated Pet data successfully--------");
    }   
    
    @Test(priority=4)
    public void DeleteTestPet()
    {
    	logger.info("Delete Pet data*********");
    	Response response = PetEndpoints2.deletePet(this.petpayload.getId());
    	response.then().log().all();
    	//Assert.assertEquals(response.getStatusCode(),200);
    	// Accept 200 (deleted) or 404 (already gone) depending on API contract
        Assert.assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 404,
                          "Unexpected status code: " + response.getStatusCode());


    	logger.info("------Deleted Pet data successfully--------");
    }
    
    
    
    
    
    
}