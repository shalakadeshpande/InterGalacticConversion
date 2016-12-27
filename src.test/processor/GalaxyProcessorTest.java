package processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class GalaxyProcessorTest {

	GalaxyProcessor galaxyProcessor = new GalaxyProcessor();

	@Test
	public void shouldTestPopulateGalacticToRomanMap() {
		List<String> fileContents = new ArrayList<>();
		fileContents.add("glob is I");
		String output = galaxyProcessor.process(fileContents);
		Assert.assertEquals(output, "");
		Map<String, String> expectedMapping = new HashMap<>();
		expectedMapping.put("glob", "I");
		Assert.assertEquals(galaxyProcessor.getGalacticToRomanUnits(), expectedMapping);
	}
	
	@Test
	public void shouldTestPopulateMetalInfo(){
		List<String> fileContents = new ArrayList<>();
		fileContents.add("glob is I");
		fileContents.add("prok is V");
		fileContents.add("pish is X");
		fileContents.add("tegj is L");
		fileContents.add("glob glob Silver is 34 Credits");
		fileContents.add("glob prok Gold is 57800 Credits");
		fileContents.add("pish pish Iron is 3910 Credits");
		String output = galaxyProcessor.process(fileContents);
		Assert.assertEquals(output, "");
		Assert.assertEquals(galaxyProcessor.getMetalInfoList().get(0).getName(), "Silver");
		Assert.assertEquals(galaxyProcessor.getMetalInfoList().get(0).getRate(), 17);
		
		Assert.assertEquals(galaxyProcessor.getMetalInfoList().get(1).getName(), "Gold");
		Assert.assertEquals(galaxyProcessor.getMetalInfoList().get(1).getRate(), 14450);
		
		Assert.assertEquals(galaxyProcessor.getMetalInfoList().get(2).getName(), "Iron");
		Assert.assertEquals(galaxyProcessor.getMetalInfoList().get(2).getRate(), 195);
	}
	
	@Test
	public void shouldTestHandleQueryForHowMuch(){
		List<String> fileContents = new ArrayList<>();
		fileContents.add("glob is I");
		fileContents.add("prok is V");
		fileContents.add("pish is X");
		fileContents.add("tegj is L");
		fileContents.add("glob glob Silver is 34 Credits");
		fileContents.add("glob prok Gold is 57800 Credits");
		fileContents.add("pish pish Iron is 3910 Credits");
		fileContents.add("how much is pish tegj glob glob ?");
		String output = galaxyProcessor.process(fileContents);
		Assert.assertEquals(output, "pish tegj glob glob is 42");
		
		
	}
	
	@Test
	public void shouldTestHandleQueryForHowMany(){
		List<String> fileContents = new ArrayList<>();
		fileContents.add("glob is I");
		fileContents.add("prok is V");
		fileContents.add("pish is X");
		fileContents.add("tegj is L");
		fileContents.add("glob glob Silver is 34 Credits");
		fileContents.add("glob prok Gold is 57800 Credits");
		fileContents.add("pish pish Iron is 3910 Credits");
		fileContents.add("how many Credits is glob prok Silver ?");
		String output = galaxyProcessor.process(fileContents);
		Assert.assertEquals(output, "glob prok Silver is 68 Credits");
		
		
	}
	
	@Test
	public void shouldTestHandleQueryForInvalidMetalName(){
		List<String> fileContents = new ArrayList<>();
		fileContents.add("glob is I");
		fileContents.add("prok is V");
		fileContents.add("pish is X");
		fileContents.add("tegj is L");
		fileContents.add("glob glob Silver is 34 Credits");
		fileContents.add("glob prok Gold is 57800 Credits");
		fileContents.add("pish pish Iron is 3910 Credits");
		fileContents.add("how many Credits is glob prok Platenium ?");
		String output = galaxyProcessor.process(fileContents);
		assert(output.contains("I have no idea what you are talking about glob prok Platenium ?"));
		
		
	}

}
