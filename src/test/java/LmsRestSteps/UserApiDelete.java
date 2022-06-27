package LmsRestSteps;


import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.IOException;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserApiDelete {
	RequestSpecification Req_spec=null;
	Response resp=null;
	String baseUrl = Lmsconfig.baseURI+"/Users";
	Map<String, String> currentDataRow;

	@Given("user is on the Endpoint url trying to delete the details")
	public void user_is_on_the_endpoint_url_trying_to_delete_the_details() {
	    
		    RestAssured.baseURI = baseUrl;
			Req_spec = RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
			Req_spec.get(baseUrl);
			Req_spec.header("content-type","application/json");
			RequestSpecification httpRequest=RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
			httpRequest.header("content-type","application/json");
			}

	@When("Delete an existing User details fro sheet {int}")
	public void delete_an_existing_user_details_fro_sheet(Integer rownum) throws IOException {
		
		
		ExcelReader reader = new ExcelReader();
		JsonObject requestParams = new JsonObject();
		try {
		List<Map<String,String>>  testData = reader.getData("src/test/resources/LMSEXcelData/LmsExcelSheets.xlsx", "UDELETE"); 
		
		Map<String,String> currentRow = testData.get(rownum);
		String userId = currentRow.get("user_id");
		
		String url = baseUrl+"/"+userId;
		Req_spec.baseUri(url);
		
		currentDataRow = testData.get(rownum);
		currentDataRow.forEach((k,v) -> {
				requestParams.put(k,v);
			});
			Req_spec.given().body(requestParams.toJson());
	        resp = Req_spec.when().delete();
	        
	        String respon = resp.getBody().asPrettyString();
	        System.out.println(respon);
       } catch (InvalidFormatException e) {
		
		
		Req_spec.given().body(requestParams.toJson());
        resp = Req_spec.when().delete();

		
		System.out.println(resp.body().asPrettyString());
       }
		
	    
	}

	
	
	@Then("user should get the messageresponse Successfullydeleted")
	public void user_should_get_the_messageresponse_successfullydeleted() {

		System.out.println(resp.getBody().asPrettyString());
		System.out.println(resp.getStatusCode());

		
		
	}
	@Given("user is on Endpoint url delete method")
	public void user_is_on_endpoint_url_delete_method() {
		 RestAssured.baseURI = baseUrl;
			Req_spec = RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
			Req_spec.get(baseUrl);
			Req_spec.header("content-type","application/json");
			RequestSpecification httpRequest=RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
			httpRequest.header("content-type","application/json");
		
	}

	@When("User delete the request using invalid Userid")
	public void user_delete_the_request_using_invalid_userid() {
		resp=  Req_spec.when().get(Lmsconfig.baseURI+"/Users/J90");
	    
	}
	@Then("user should get the error messageresponse")
	public void user_should_get_the_error_messageresponse() {
		resp.then().assertThat().statusCode(404);
	    @SuppressWarnings("unused")
		int statuscode=resp.getStatusCode();
	}
	}

