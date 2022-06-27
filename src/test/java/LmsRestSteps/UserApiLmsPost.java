package LmsRestSteps;

import io.cucumber.java.en.Given;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;

import com.github.cliftonlabs.json_simple.JsonObject;



public class UserApiLmsPost {
	String excelpath = "src/test/resources/LMSExcelData/LmsExcelSheets.xlsx";
	RequestSpecification Req_spec=null;
	Response resp=null;
	
	//String excelFile = "src/test/resources/TestData/LmsUserTestdata.xlsx";
	String baseUrl = Lmsconfig.baseURI+"/Users";
	Map<String, String> currentDataRow;
	@Given("User is on the Endpointurl with post method")
	public void user_is_on_the_endpointurl_with_post_method() {
		RestAssured.baseURI = baseUrl;
		Req_spec = RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
		Req_spec.get(baseUrl);
		Req_spec.header("content-type","application/json");
	}

	
	@When("user sends post request with endpoint inputs data from excel {int}")
	public void user_sends_post_request_with_endpoint_inputs_data_from_excel(Integer rownumber) {
		RestAssured.given().auth().preemptive().basic(Lmsconfig.userId,Lmsconfig.password);
		ExcelReader reader=new ExcelReader();
	  List<Map<String, String>> testdata;
	  JsonObject requestParams=new JsonObject();
	  
	try {
		testdata = reader.getData("src/test/resources/LMSExcelData/LmsExcelSheets.xlsx","Sheet1");
		
		currentDataRow = testdata.get(rownumber);
		currentDataRow.forEach((k,v) -> {
			
				requestParams.put(k,v);
			});
	
			Req_spec.given().body(requestParams.toJson());
	        resp = Req_spec.when().post();
	        System.out.println(resp.getBody().asPrettyString());
       }catch(Exception ex)
		{
    	   ex.printStackTrace();
		}
	
		
	}
	@Then("shows a message as sucessfully created user id and updates id in excel {int}")
	public void shows_a_message_as_sucessfully_created_user_id_and_updates_id_in_excel(Integer rowNumber) throws InvalidFormatException, IOException  {
		System.out.println(resp.getBody().asPrettyString());
		 String responsebody=resp.getBody().asPrettyString();
		resp.then().assertThat().statusCode(201);
	    
		int statusCode=resp.getStatusCode();
	    if(statusCode == 200 || statusCode == 201)
	    {
		    JsonPath eval =  resp.jsonPath();
		       
	        Assert.assertEquals(currentDataRow.get("name"), eval.get("name"));
	        Assert.assertEquals(currentDataRow.get("visa_status"), eval.get("visa_status"));
	        Assert.assertEquals(currentDataRow.get("location"), eval.get("location"));
	        Assert.assertEquals(currentDataRow.get("time_zone"),eval.get("time_zone"));
	        Assert.assertEquals(currentDataRow.get("linkedin_url"),eval.get("linkedin_url"));
	        Assert.assertEquals(currentDataRow.get("education_ug"), eval.get("education_ug"));
	        Assert.assertEquals(currentDataRow.get("education_pg"), eval.get("education_pg"));
	        Assert.assertEquals(currentDataRow.get("comments"), eval.get("comments"));
	        String cellvalue = eval.getString("user_id");
		       
		      // rowNum=rowNum+1;
				new ExcelReader().UpdateRow(excelpath, "Sheet1", rowNumber+1,
						9, cellvalue);
				assertThat("Json Schema",responsebody,matchesJsonSchemaInClasspath("LmsPoPutSchema.json"));
	}
	}
	
	        
	        
	        //schema validation
	 	  // assertThat("Json Schema",Responsebody.replaceAll("NaN","null"),matchesJsonSchemaInClasspath("Lmsschema.json"));
	 	
	        
	        
	


	@Given("User is on the postrequest  by giving invalid url")
	public void user_is_on_the_postrequest_by_giving_invalid_url() {
		Req_spec = given().auth().basic(Lmsconfig.userId,Lmsconfig.password);
	}

	@When("User sends the postMethod with incorrect url")
	public void user_sends_the_post_method_with_incorrect_url() {
		resp=  Req_spec.when().post(Lmsconfig.baseURI+"/UserS");  	   
	}

	@Then("Error message with statuscode is displayed")
	public void error_message_with_statuscode_is_displayed() {
	    
		resp.then().assertThat().statusCode(404);
	    int statuscode=resp.getStatusCode();
	    System.out.println(resp.getBody());
	    System.out.println(statuscode);
	}
}