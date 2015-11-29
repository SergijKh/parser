
package test_test.upload.parse;
	import static org.junit.Assert.*;

	import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

	import javax.xml.parsers.ParserConfigurationException;

	import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

	import test_test.upload.model.form.Field;
import test_test.upload.model.form.Table;
import test_test.upload.model.map.conversion.Conversion;
import test_test.upload.model.map.conversion.FieldFormat;
import test_test.upload.model.map.substitute.Substitute;
import test_test.upload.model.map.substitute.Value;
import test_test.upload.model.parse.MapSource;
import test_test.upload.model.parse.Schema;
import test_test.upload.model.parse.Source;
import test_test.upload.parseexcel.ParseExcelFile;
import test_test.upload.parsexml.ParseXml;

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration({ "file:src/sources/spring_context.xml" })
	public class TestParseExcel {
		private static final Logger logger1 = Logger.getLogger(TestParseXml.class);
		

		@Autowired
		private ParseExcelFile parseExcel;
		private Document doc;

		// @Autowired
		public  TestParseExcel() {

		}

		@Before
		public void setup() {


		}

		@After
		public void terdown() {
		
		}

		@Test
		public void getTestSource() {
			String  excetention = "xls";
			String file = "D:\\hjhkhj\\src.xls";
			
				assertEquals( excetention,parseExcel.getExstension(file) );
		
		}
		
		@Test
		public void getParseheader() {
			String [] headerArray ={"Client.Client_No","Client.Client_Name","Order.Order_No","Order.Client_No","Order.Quantity","Order.Delivery_Date"};
			String file = ("D:\\workEclipse\\project_1\\excel\\src.xlsx");
			ArrayList<String> headerList=null;
			try {
				headerList = (ArrayList<String>) parseExcel.getHeaderList(parseExcel.readFromExcel(file), 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger1.info(headerList.get(1));
			assertEquals(headerArray.length,headerList.size());
			for (int  i = 0 ; i < headerList.size(); i++){
				assertEquals(headerArray[i] ,headerList.get(i));
			}
				
		
		}
		@SuppressWarnings("null")
		@Test
		public void getOneRow() {
			 HashMap <String,String> exampl = new HashMap<String,String>();
			  exampl.put("Client.Client_No","");
			  exampl.put("Client.Client_Name","Sergey");
			  exampl.put("Order.Order_No","");
			  exampl.put("Order.Client_No","");
			  exampl.put("Order.Quantity","quentity");
			  exampl.put("Order.Delivery_Date","delivery_Date");
			String file = ("D:\\workEclipse\\project_1\\excel\\src.xlsx");
			 HashMap <String,String> resultHashMap = new  HashMap <String,String>();
			ArrayList<String> headerList=null;
			try {
				headerList = (ArrayList<String>) parseExcel.getHeaderList(parseExcel.readFromExcel(file), 2);
				Sheet sheet = parseExcel.readFromExcel(file);
				 Row  rowData = sheet.getRow(3);
				 parseExcel.getOneRow(rowData,headerList ,resultHashMap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger1.info(headerList.get(1));
			assertEquals(exampl.size(),resultHashMap.size());
			for (int  i = 0 ; i < resultHashMap.size(); i++){
				assertEquals(exampl.get(i) ,resultHashMap.get(i));
			}
				
		
		}
		
		 
}
