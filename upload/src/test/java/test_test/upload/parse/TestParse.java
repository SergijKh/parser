package test_test.upload.parse;


import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test_test.upload.model.parse.Source;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/sources/spring_context.xml" })
public class  TestParse {
	private Properties config;
	private InputStream inputStream;
	private ParseXml parseXml ;
	//@Mock
	//@Autowired
	

	
	//@Autowired
	public TestParse(){
		this.config = new Properties();
		this.inputStream = getClass().getClassLoader().getResourceAsStream(
				"xmlFile.properties");
		try {
			config.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File  file = new File(config.getProperty("xml.file"));
		parseXml = new ParseXml(file);
	}
	  
	@Before
	public void setup() {
		
		
		
		// Process mock annotations
	//	MockitoAnnotations.initMocks(this);


}
  @Test
  public void getTest(){
	  Source source = new Source("src1","txt-tab", 2,
			  1, "table.field",'I',"D:\\src.txt","txt-tab", "ERR", "D:\\output.txt");
	 Source sourceParse = parseXml.getSource();
	   
	 assertEquals(source.getId(),sourceParse.getId());
	 assertEquals(source.getInputFormat(),sourceParse.getInputFormat());
	 assertEquals(source.getHeaderRows(),sourceParse.getHeaderRows());
	 assertEquals(source.getFieldNameRowNumer(),sourceParse.getFieldNameRowNumer());
	 assertEquals(source.getField_names_format(),sourceParse.getField_names_format());
	 assertEquals(source.getUpdate_mode_field_name(),sourceParse.getUpdate_mode_field_name());
	 assertEquals(source.getPathInputFile(),sourceParse.getPathInputFile());
	 assertEquals(source.getPathOutFile(),sourceParse.getPathOutFile());
	assertEquals(source.getError_column(),sourceParse.getError_column());
	 assertEquals(source.getAutputFormat(),sourceParse.getAutputFormat());
  }
	

	
	
	


	

}