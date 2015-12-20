package test_test.upload.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test_test.upload.database.convert.ConvertDataRDBMSFieldType;
import test_test.upload.database.service.ILookUpService;
import test_test.upload.model.staticvariablesxmlfile.StaticVariablesXML;
import test_test.upload.parse.TestParseXml;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/sources/spring_context.xml" })
public class TestLooUpService {
private static final Logger logger1 = Logger.getLogger(TestParseXml.class);
	

	@Autowired
	ILookUpService lookUpService;

	// @Autowcired
	public  TestLooUpService() {

	}

	@Before
	public void setup() {


	}

	@After
	public void terdown() {
	
	}
	
	
	@Test
	public void getTestSeletGetFieldbyID() {
		Map<String,String> map = new HashMap<String,String>();
		map.put(StaticVariablesXML.LOOKUP_VARIABLE,"Goods.Goods_No");
		map.put(StaticVariablesXML.LOOKUP_TABLE,"Goods");
		map.put(StaticVariablesXML.LOOKUP_COL_IN_TABLE,"Goods_No");
		map.put(StaticVariablesXML.RESULT_COL_IN_TABLE,"Goods_Name");
		map.put(StaticVariablesXML.UPDATE_INTO_VARIABLE,"Order.Goods_Name");
	      String value = lookUpService.seletGetFieldbyID(map);
	      logger1.info("value "+value);
	      assertEquals( "tv", value );
	}
}
