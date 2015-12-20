
package test_test.upload.parse;
	import static org.junit.Assert.*;

	import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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

import test_test.upload.database.service.LookUpService;
import test_test.upload.model.form.Field;
import test_test.upload.model.form.Table;
import test_test.upload.model.map.conversion.Conversion;
import test_test.upload.model.map.conversion.FieldFormat;
import test_test.upload.model.map.substitute.Substitute;
import test_test.upload.model.map.substitute.Value;
import test_test.upload.model.parse.MapSource;
import test_test.upload.model.parse.Schema;
import test_test.upload.model.parse.Source;
import test_test.upload.model.staticvariablesxmlfile.StaticVariablesXML;
import test_test.upload.parseexcel.ParseExcelFile;
import test_test.upload.parsexml.ParseXml;

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration({ "file:src/sources/spring_context.xml" })
	public class TestParseExcel {
		private static final Logger logger1 = Logger.getLogger(TestParseXml.class);
		

		@Autowired
		private ParseExcelFile parseExcel;
		private Document doc;
		 @Autowired
		    LookUpService lookUpService;
		// @Autowired
		public  TestParseExcel() {

		}

		@Before
		public void setup() {


		}

		@After
		public void terdown() {
		
		}
        @Ignore
		@Test
		public void getTestSource() {
			String  excetention = "xls";
			String file = "D:\\hjhkhj\\src.xls";
			
				assertEquals( excetention,parseExcel.getExstension(file) );
		
		}
        @Ignore
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
        @Ignore
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
        @Ignore
		@Test
		public  void testGetSetNameTable(){
			Set <String> setCorrect = new HashSet<String>();
			setCorrect.add("Client");
			setCorrect.add("Order");
			Object [] stringCorr =  setCorrect.toArray();
			
			HashMap <String,String> exampl = new HashMap<String,String>();
			  exampl.put("Client.Client_No","");
			  exampl.put("Client.Client_Name","Sergey");
			  exampl.put("Order.Order_No","");
			  exampl.put("Order.Client_No","");
			  exampl.put("Order.Quantity","quentity");
			  exampl.put("Order.Delivery_Date","delivery_Date");
			  
			  Set <String> setResult = parseExcel.getSetNameTable(exampl);
			  Object [] stringResult =  setResult.toArray();
			  assertEquals((stringCorr.length) ,(stringResult.length));
			  for(int i = 0; i < stringCorr.length;i++){
				  assertEquals((stringCorr[i]) ,(stringResult[i]));
			  }
		
		}
		@Ignore 
		@Test
		public void testGetTargetVariable(){
			ArrayList<String >listResultConversionSubstitude  = new ArrayList<String>();
			listResultConversionSubstitude.add("Go");
			listResultConversionSubstitude.add("Correct");
			listResultConversionSubstitude.add("Correct");
			Map<String,String> constant = new HashMap<String,String>();
			constant.put("Constant1", "-");
			constant.put("Constant2", "+");
			String ansver = parseExcel.getTargetVariable(listResultConversionSubstitude, constant);
			logger1.info(ansver);
			assertEquals("Go-Correct+Correct" , ansver);
		}
		@Ignore
		@Test
		public void testIsHaveFiieldNameInLookUp(){
			 String fieldName = "Goods.Goods_Name";
			 Map<String,String>  mapLookUp = new HashMap<String,String>();
			 mapLookUp.put(StaticVariablesXML.LOOKUP_VARIABLE,"Goods.Goods_No");
			 mapLookUp.put(StaticVariablesXML.LOOKUP_TABLE,"Goods");
			 mapLookUp.put(StaticVariablesXML.LOOKUP_COL_IN_TABLE,"Goods_No");
			 mapLookUp.put(StaticVariablesXML.RESULT_COL_IN_TABLE,"Goods_Name");
			 boolean result =parseExcel.isHaveFiieldNameInLookUp (mapLookUp,  fieldName);
			 assertEquals(true , result);
		 }
		@Ignore
		@Test
		public void testSubstitudeValueData(){
			
			List <Substitute> listSubstitute = new  ArrayList<Substitute>();
			Substitute substitute = new Substitute();
			substitute.setVariable("Order.Quantity");
			Value value = new Value();
			HashMap<String,String> constant = new HashMap<String,String>();
			constant.put("Const1","-");
			List<FieldFormat> listfildFormatSub = new ArrayList<FieldFormat>();
			listfildFormatSub.add( new FieldFormat("trim","Order.Quantity"));
			listfildFormatSub.add( new FieldFormat("left","Goods.Goods_Name",4));
			value.setConstant(constant);
			List<Map<String,String>> listLookUpSub =  new ArrayList<Map<String,String>>();
			Map<String,String> mapSub = new HashMap<String,String>();
			mapSub.put(StaticVariablesXML.LOOKUP_VARIABLE,"Goods.Goods_No");
			mapSub.put(StaticVariablesXML.LOOKUP_TABLE,"Goods");
			mapSub.put(StaticVariablesXML.LOOKUP_COL_IN_TABLE,"Goods_No");
			mapSub.put(StaticVariablesXML.RESULT_COL_IN_TABLE,"Goods_Name");
			listLookUpSub.add(mapSub);
			value.setLookUp(listLookUpSub);
			value.setFormat(listfildFormatSub);
			substitute.setValue(value);
			listSubstitute.add(substitute);
			 HashMap <String,String>hashMapRowAndheaderexampl = new HashMap<String,String>();
			 hashMapRowAndheaderexampl.put("Client.Client_No","");
			 hashMapRowAndheaderexampl.put("Client.Client_Name","Sergey");
			 hashMapRowAndheaderexampl.put("Order.Order_No","");
			 hashMapRowAndheaderexampl.put("Order.Client_No","");
			 hashMapRowAndheaderexampl.put("Order.Quantity","quentity");
			 hashMapRowAndheaderexampl.put("Order.Delivery_Date","05.11.2015");
			 hashMapRowAndheaderexampl.put("Goods.Goods_No","1");
			 hashMapRowAndheaderexampl.put("Order.Goods_Name","GoodsName");
			parseExcel.substitudeValueData(listSubstitute, hashMapRowAndheaderexampl,lookUpService);
			 assertEquals("quentity-tvGo" , hashMapRowAndheaderexampl.get("Order.Quantity"));
			
		}
		@Ignore
		@Test
		public void  testGetListTable(){
			Schema schema = new Schema("schema1");
			List<Table> listTable = new ArrayList<Table>();
			Table tableClient = new Table("Client");
			List<Field> listFealdClient = new ArrayList<Field>();
			listFealdClient.add(new Field("Client_No"));
			listFealdClient.add(new Field("Client_Name"));
			tableClient.setListFeald(listFealdClient);
			Table tableOrder = new Table("Order");
			List<Field> listFieldOrder = new ArrayList<Field>();
			listFieldOrder.add(new Field("Order_No"));
			listFieldOrder.add((new Field("Client_No", "Client")));
			listFieldOrder.add(new Field("Quantity"));
			listFieldOrder.add(new Field("Delivery_Date"));
			tableOrder.setListFeald(listFieldOrder);
			listTable.add(tableClient);
			listTable.add(tableOrder);
	
			schema.setListTable(listTable);
			 HashMap <String,String>hashMapRowAndheaderexampl = new HashMap<String,String>();
			 hashMapRowAndheaderexampl.put("Client.Client_No","");
			 hashMapRowAndheaderexampl.put("Client.Client_Name","Sergey");
			 hashMapRowAndheaderexampl.put("Order.Order_No","");
			 hashMapRowAndheaderexampl.put("Order.Client_No","");
			 hashMapRowAndheaderexampl.put("Order.Quantity","quentity");
			 hashMapRowAndheaderexampl.put("Order.Delivery_Date","05.11.2015");
			 List<Table>  listTableR =  parseExcel.getListTable(schema,hashMapRowAndheaderexampl);
			 for (int i = 0 ; i < listTableR.size(); i++ ){
				   Table  tableResult = listTableR.get(i);
				    Table table = listTable.get(i);
				   List <Field> fildsResult = tableResult.getListField();
				   logger1.info("fildsResult.size()"+ fildsResult.size());
				   List <Field> filds = table.getListField();
				    for (int f = 0; f < fildsResult.size(); f++){
				     assertEquals(fildsResult.get(f).getName() ,filds.get(f).getName()); 
				    	 assertEquals(fildsResult.get(f).getValue() ,filds.get(f).getValue()); 
				     }
			 }
		}
		
		
		@Test
		public void testSerchForeigKey(){
			ArrayList <Table> listResultTable = new ArrayList<Table>();
			List<Table> listTable = new ArrayList<Table>();
			Table tableClient = new Table("Client");
			List<Field> listFealdClient = new ArrayList<Field>();
			listFealdClient.add(new Field("Client_No"));
			Field fieldClientName = new Field("Client_Name");
			fieldClientName.setValue("New");
			listFealdClient.add(fieldClientName);
			tableClient.setListFeald(listFealdClient);
			Table tableOrder = new Table("orde");
			List<Field> listFieldOrder = new ArrayList<Field>();
			listFieldOrder.add(new Field("Order_No"));
			listFieldOrder.add((new Field("Client_No", "Client")));
		Field fieldOrdrQuantity = new Field("Quantity");
		fieldOrdrQuantity.setValue("Quentity");
			listFieldOrder.add(fieldOrdrQuantity);
			Field fieldDelivery_Date = new Field("Delivery_Date");
			fieldDelivery_Date.setValue("12/03/2000");
		listFieldOrder.add(fieldDelivery_Date);
			Field fieldGoods= new Field("Goods_Name");
		fieldGoods.setValue("goods_name");
			listFieldOrder.add(fieldGoods);
			tableOrder.setListFeald(listFieldOrder);
			listTable.add(tableClient);
			listTable.add(tableOrder);
			for (int i = 0 ; i < listTable.size(); i++){
			parseExcel.serchForeigKey(listTable.get(i), listTable, listResultTable);
			}
			logger1.info("message"+listResultTable.size());
		}
}
