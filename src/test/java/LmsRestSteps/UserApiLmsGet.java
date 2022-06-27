package LmsRestSteps;
    import io.cucumber.java.en.Given;
	import io.cucumber.java.en.Then;
	import io.cucumber.java.en.When;
	import io.restassured.response.Response;
	import io.restassured.response.ResponseBody;
	import io.restassured.specification.RequestSpecification;

	import static io.restassured.RestAssured.*;
	import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
	import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.*;

	import org.junit.Assert;

	import LmsRestSteps.Lmsconfig;
	@SuppressWarnings("unused")
	public class UserApiLmsGet {
		RequestSpecification Req_spec;
		Response resp=null;
		//readExcelData obj=new readExcelData();
		
		
		@Given("user enters detials with Endpoint\\/url\\/Users")
		public void user_enters_detials_with_endpoint_url_users() {
		 System.out.println(Lmsconfig.userId + "--" + Lmsconfig.password);
		 
		 Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
		 Req_spec.get(Lmsconfig.baseURI+"/Users");
		 System.out.println(Lmsconfig.baseURI); 
		}

		@When("user sends the get request")
		public void user_sends_the_get_request() throws Exception {
			Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
		     resp=  Req_spec.when().get(Lmsconfig.baseURI+"/Users");
		
		}

		@Then("should gets list of  of all users")
		public void should_gets_list_of_of_all_users() {
			Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
			resp.then().assertThat().statusCode(200);
			String responsebody=resp.getBody().asPrettyString();
			System.out.println(responsebody);
			int statuscode=resp.getStatusCode();
			System.out.println(responsebody);
			Assert.assertEquals(statuscode, 200);
			Assert.assertTrue(responsebody.contains("user_id"));
			Assert.assertTrue(responsebody.contains("name"));
			Assert.assertTrue(responsebody.contains("phone_number"));
			Assert.assertTrue(responsebody.contains("location"));
	        Assert.assertTrue(responsebody.contains("time_zone"));
	       Assert.assertTrue(responsebody.contains("linkedin_url"));

	     assertThat("Json Schema",responsebody,matchesJsonSchemaInClasspath("LmsGetSchema.json"));
		}
		@Given("user is on get method with Endpoint url")
		public void user_is_on_get_method_with_endpoint_url() {
		   Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
		   
		   
		}
		@When("user sends request for specific UserID")
		public void user_sends_request_for_specific_user_id() throws Exception  {
			Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
			
			 Object[][] Testdata=Xlutils.readExcelData("src/test/resources/LMSExcelData/LmsExcelSheets.xlsx","UGET"); 
			 //System.out.println(Testdata[0][0]);
			String url = Lmsconfig.baseURI+"/Users/"+Testdata[0][0];
			resp=  Req_spec.when().get(url);
			
			System.out.println(resp.body().asPrettyString());
		}


		
		@Then("User recieves status code {int} and should gets the details of specific userId")
		public void user_recieves_status_code_and_should_gets_the_details_of_specific_user_id(Integer int1) {
		    resp.then().assertThat().statusCode(200);
		    resp.getBody();
		    String responsebody=resp.getBody().asPrettyString();
		    int statuscode=resp.getStatusCode();
		    System.out.println(resp.getBody());
		    System.out.println(statuscode);
		    
		    assertThat("Json Schema",responsebody,matchesJsonSchemaInClasspath("LmsGetIDSch.json"));
		    
		}
		@Given("user sends the request  method with incorrect url")
		public void user_sends_the_request_method_with_incorrect_url() {
			Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
		}

		@When("user is on get method invalid url")
		public void user_is_on_get_method_invalid_url() {
			resp=  Req_spec.when().get(Lmsconfig.baseURI+"/UserS");
			
		}

		@Then("user recieves {int} error message")
		public void user_recieves_error_message(Integer int1) {
			resp.then().assertThat().statusCode(404);
		    int statuscode=resp.getStatusCode();
		    System.out.println(resp.getBody());
		    System.out.println(statuscode);
		    
		}
		
		
		
	@Given("User is on Get Method with Basic Authdetails")
		public void user_is_on_get_method_with_basic_authdetails() {
		Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
		}

		@When("User sends get request by giving valid Basic Authdetails")
		public void user_sends_get_request_by_giving_valid_basic_authdetails() {
			//Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
			resp=Req_spec.get(Lmsconfig.baseURI+"/Users"); 
		}
		
		@Then("User is able to acess theApi with valid credentials")
		public void user_is_able_to_acess_the_api_with_valid_credentials() {
			resp.then().assertThat().statusCode(200);
			resp.getBody();
			resp.getContentType();
			System.out.println(	resp.getContentType());
			
		}	
	}



