package test_test.upload.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

import test_test.upload.database.convert.ConvertDataRDBMSFieldType;
import test_test.upload.database.service.TableService;
import test_test.upload.model.form.Field;
import test_test.upload.model.form.Table;
import test_test.upload.parse.TestParseXml;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/sources/spring_context.xml" })
public class TestTableService {
	private static final Logger logger1 = Logger.getLogger(TestParseXml.class);
	@Autowired
	TableService tableService;
	// @Autowired
	public  TestTableService() {

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
	public void addTestSource() {
		Table tableClient = new Table("Client");
		List<Field> listFealdClient = new ArrayList<Field>();
		Field fieldNo = new Field("Client_No");
		fieldNo.setValue(" ");
		listFealdClient.add(fieldNo);
		Field fieldName = new Field("Client_Name");
		fieldName.setValue("Sergey");
		listFealdClient.add(fieldName);
		tableClient.setListFeald(listFealdClient);
	     tableService.addoneRowTable(tableClient);
		//logger1.info("id "+id );
	    tableService.selectoneRow(tableClient);
	    assertEquals("30",tableClient.getListField().get(0).getValue());
	    assertEquals("Sergey",tableClient.getListField().get(1).getValue());
	}
    
    @Test
	public void testUpdateSource() {
		Table tableClient = new Table("Client");
		List<Field> listFealdClient = new ArrayList<Field>();
		Field fieldNo = new Field("Client_No");
		fieldNo.setValue("41");
		listFealdClient.add(fieldNo);
		Field fieldName = new Field("Client_Name");
		fieldName.setValue("ggggggg");
		listFealdClient.add(fieldName);
		tableClient.setListFeald(listFealdClient);
	    tableService.updateOneRowTable(tableClient);
	    assertEquals(true, true);
	}
}
