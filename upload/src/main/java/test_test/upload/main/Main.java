package test_test.upload.main;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import test_test.upload.parseexcel.ParseExcelFile;
import test_test.upload.parsexml.ParseXml;

/**
 * Hello world!
 *
 */
public class Main 
{
	
    public static void main( String[] args )
    {
    	 final String  fileXml = "D:\\workEclipse\\americanwork\\parser\\xml\\upload.xml" ;
    	 final String  schemaFileXml = "D:\\workEclipse\\americanwork\\parser\\xml\\load.xsd" ;
    	ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring_context.xml");
		ParseXml parseXml =  (ParseXml) context.getBean("parseXml");
		try {
			parseXml.setFileXml(new File(fileXml));
			parseXml.setSchemaFile(new File(schemaFileXml));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParseExcelFile parseExcel = (ParseExcelFile)context.getBean("parseExcel");
		parseExcel.parseExcelFile(parseXml);
    }
}
