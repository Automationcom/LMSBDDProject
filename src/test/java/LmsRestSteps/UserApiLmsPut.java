package LmsRestSteps;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;


import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;

import com.github.cliftonlabs.json_simple.JsonObject;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;


public class UserApiLmsPut {
	RequestSpecification Req_spec=null;
	Response resp=null;
	String baseUrl = Lmsconfig.baseURI+"/Users";
	Map<String, String> currentDataRow;
	@Given("User is on the PutMethod with the Endpoint url")
	
	public void user_is_on_the_put_method_with_the_endpoint_url() {
		RestAssured.baseURI = baseUrl;
		Req_spec = RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
		Req_spec.get(baseUrl);
		Req_spec.header("content-type","application/json");
		RequestSpecification httpRequest=RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
		httpRequest.header("content-type","application/json");
		}

	@When("user is on the PutMethod gives details to update from excel {int}")
	public void user_is_on_the_put_method_gives_details_to_update_from_excel(Integer rownum) throws Exception {
		ExcelReader reader = new ExcelReader();
		JsonObject requestParams = new JsonObject();
		try {
		List<Map<String,String>>  testData = reader.getData("src/test/resources/LMSEXcelData/LmsExcelSheets.xlsx", "UPUT"); 
		
		Map<String,String> currentRow = testData.get(rownum);
		String userId = currentRow.get("user_id");
		
		String url = baseUrl+"/"+userId;
		Req_spec.baseUri(url);
		
		currentDataRow = testData.get(rownum);
		currentDataRow.forEach((k,v) -> {
				requestParams.put(k,v);
			});
			Req_spec.given().body(requestParams.toJson());
	        resp = Req_spec.when().put();
	        
	        String respon = resp.getBody().asPrettyString();
	        System.out.println(respon);
       } catch (InvalidFormatException e) {
		
		
		Req_spec.given().body(requestParams.toJson());
        resp = Req_spec.when().put();

		
		System.out.println(resp.body().asPrettyString());
       }
	}

	@Then("message response should be sucessfully updated")
	public void message_response_should_be_sucessfully_updated() {
		System.out.println(resp.getBody().asPrettyString());
    String responsebody=resp.getBody().asPrettyString();
		resp.then().assertThat().statusCode(201);
		int statusCode=resp.getStatusCode();
	    if(statusCode == 200 || statusCode == 201)
	    {
		    JsonPath eval =  resp.jsonPath();
		     Assert.assertEquals(currentDataRow.get("user_id"), eval.get("user_id"));  
	        Assert.assertEquals(currentDataRow.get("location"), eval.get("location"));
	      // assertThat("Json Schema",responsebody,matchesJsonSchemaInClasspath("LmsFeaturefile/LmsPoPutSchema.json")); 
	    }
}
@Given("User is on the Endpointurl  with giving non existing UserId")
	public void user_is_on_the_endpointurl_with_giving_non_existing_user_id() {
		RestAssured.baseURI = baseUrl;
		Req_spec = RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
		Req_spec.get(baseUrl);
	   }
   @When("User is on put method to update the details")
	public void user_is_on_put_method_to_update_the_details() {
		resp=  Req_spec.when().get(Lmsconfig.baseURI+"/Users/J90");
}
   @Then("No User found should be shown")
	public void no_user_found_should_be_shown() {
		resp.then().assertThat().statusCode(404);
	    @SuppressWarnings("unused")
		int statuscode=resp.getStatusCode();
	    
	}

	}



