package test_test.upload.database;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import test_test.upload.database.convert.ConvertDataRDBMSFieldType;
import test_test.upload.model.form.Field;
import test_test.upload.model.form.Table;
import test_test.upload.model.map.conversion.Conversion;
import test_test.upload.model.map.conversion.FieldFormat;
import test_test.upload.model.map.substitute.Substitute;
import test_test.upload.model.map.substitute.Value;
import test_test.upload.model.parse.MapSource;
import test_test.upload.model.parse.Schema;
import test_test.upload.model.parse.Source;
import test_test.upload.parse.TestParseXml;
import test_test.upload.parseexcel.ParseExcelFile;
import test_test.upload.parsexml.ParseXml;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/sources/spring_context.xml" })
public class TestConvertDataFieldTypeMysql {
	private static final Logger logger1 = Logger.getLogger(TestParseXml.class);
	

	@Autowired
	@Qualifier("ConvertDataFieldTypeMysql")
	private ConvertDataRDBMSFieldType convertData;
	@Autowired
	JdbcTemplate jdbcTemplate;

	// @Autowired
	public  TestConvertDataFieldTypeMysql() {

	}

	@Before
	public void setup() {


	}

	@After
	public void terdown() {
	
	}
    @Ignore
	@SuppressWarnings("unused")
	@Test
	public void getTestSource() {
		   HashMap <String,Map<String,String>> hashTable  = new HashMap<String,Map<String,String>>();
		   HashMap<String,String> hashMapFieldsClients = new HashMap<String,String>();
		   hashMapFieldsClients.put("Client_No", "INT");
		   hashMapFieldsClients.put("Client_Name", "VARCHAR");
		   hashTable.put("client", hashMapFieldsClients);
		   HashMap<String,String> hashMapFieldsOrder= new HashMap<String,String>();
		   hashMapFieldsOrder.put("Order_No'","INT");
		   hashMapFieldsOrder.put("Client_No","INT");
		   hashMapFieldsOrder.put("Quantity","Varchar");
		   hashMapFieldsOrder.put("Delivery_Date", "INT");
		   hashTable.put("order", hashMapFieldsOrder);
		   DataSource data = jdbcTemplate.getDataSource();
		   DatabaseMetaData metaData = null;
			try {
				 metaData = data.getConnection().getMetaData();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger1.error(e);
			}
		   ArrayList<String> listTable =null;
		   Map<String, Map<String, String>> hashCorrectMap = null;
		try {
			listTable = convertData.getTablesMetadata(metaData);
			hashCorrectMap = convertData.getColumnsMetadata(listTable, metaData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger1.error(1);
		}
		 
		  Set<String> setkeyCorrect = hashTable.keySet();
		   Iterator<String> iteratorCorr =  setkeyCorrect.iterator();
		   Set<String> setkeyTest= hashCorrectMap.keySet();
		   Iterator<String> iteratorTest =  setkeyTest.iterator();
			HashMap<String,String>  mapTest = null;
			  HashMap<String,String>  mapCorrect = null;
		  String tableCorrect = null;
		  String tableTest = null;
			  while(iteratorCorr.hasNext()){
			    while(iteratorTest.hasNext()){  
			    	tableCorrect = iteratorCorr.next().toLowerCase();
			    	tableTest=  iteratorTest.next().toLowerCase();
			    	if ((tableCorrect.equalsIgnoreCase("client"))&&(tableTest.equalsIgnoreCase("client"))){		
			    		
			    		mapCorrect = (HashMap<String, String>) hashCorrectMap.get( tableCorrect);
			              mapTest = (HashMap<String, String>) hashCorrectMap.get( tableTest);
			              assertEquals( mapCorrect.get("Client_No"), (mapTest.get("Client_No")));
			              assertEquals( mapCorrect.get("Client_Name"), (mapTest.get("Client_Name")));
			              logger1.info("11111111111111111111111111");
			    	}
			    	  if ((tableCorrect.equalsIgnoreCase("order"))&&(tableTest.equalsIgnoreCase("order"))){
			    		  mapCorrect = (HashMap<String, String>) hashCorrectMap.get( tableCorrect);
			              mapTest = (HashMap<String, String>) hashCorrectMap.get( tableTest);
			              assertEquals( mapCorrect.get("Order_No"), (mapTest.get("Client_No")));
			              assertEquals( mapCorrect.get("Client_Name"), (mapTest.get("Client_Name")));
			              assertEquals( mapCorrect.get("Quantity"), (mapTest.get("Quantity")));
			              assertEquals( mapCorrect.get("Delivery_Date"), (mapTest.get("Delivery_Date")));
			              logger1.info("22222222222222222222222222222");
			    	  }
			    }
		   }
		   
		
	
	}
	@SuppressWarnings("unused")
	@Test
	public void getTestGetNameFielPrimeKey() {
		Table table = new Table("Client");
		String resultFiealdAutoIncreament ="Client_No";
		String nameFieldAutoincrement = convertData.getNameFielDPrimaryAutoincrement(table, jdbcTemplate);
		assertEquals(resultFiealdAutoIncreament ,nameFieldAutoincrement);
	}
}
