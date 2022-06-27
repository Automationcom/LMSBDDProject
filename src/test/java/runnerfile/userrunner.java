package runnerfile;


	import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

import io.cucumber.junit.Cucumber;
	import io.cucumber.junit.CucumberOptions;

	@RunWith(Cucumber.class)

	@CucumberOptions(
	           plugin={"pretty"},
	           monochrome=true,
	          tags="@@UserApiPut",
	          features={"src/test/resources/LmsFeaturefile"},
	          glue="LmsRestSteps")

	public class userrunner  extends io.cucumber.testng.AbstractTestNGCucumberTests {
		@Override
		@DataProvider(parallel=false)
		public Object[][]scenarios(){
			return super.scenarios();

}
}