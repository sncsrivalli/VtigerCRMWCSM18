package mavenCommandLineParameters;

import org.testng.annotations.Test;

public class GetParametersFrommvncmdTest {
	
	@Test
	public void demoTest() {
		String browser = System.getProperty("b");
		String url = System.getProperty("u");
		System.out.println(browser+"\n"+url);
	}

}
