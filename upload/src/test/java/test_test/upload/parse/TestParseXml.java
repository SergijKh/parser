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
import test_test.upload.model.staticvariablesxmlfile.StaticVariablesXML;
import test_test.upload.parsexml.ParseXml;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/sources/spring_context.xml" })
public class TestParseXml {
	private static final Logger logger1 = Logger.getLogger(TestParseXml.class);
	private Properties config;
	private InputStream inputStream;

	@Autowired
	private ParseXml parseXml;
	private Document doc;

	// @Autowired
	public TestParseXml() {

	}

	@Before
	public void setup() {

		this.config = new Properties();
		this.inputStream = getClass().getClassLoader().getResourceAsStream(
				"xmlFile.properties");

		try {
			config.load(inputStream);
			File file = new File(config.getProperty("xml.file"));
			parseXml.setFileXml(file);
			doc = parseXml.initDomParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		// Process mock annotations
		// MockitoAnnotations.initMocks(this);

	}

	@After
	public void terdown() {
		doc = null;
	}

	@Ignore
	@Test
	public void getTestSource() {
		Source source = new Source("src1", "txt-tab", 2, 1, "table.field", "I",
				"D:\\src.txt", "txt-tab", "ERR", "D:\\output.txt");
		ArrayList<Source> sourceParse = (ArrayList<Source>) parseXml
				.getSource(doc);
		logger1.info("/////////////");
		for (int i = 0; i < sourceParse.size(); i++) {
			assertEquals(source.getId(), sourceParse.get(i).getId());
			assertEquals(source.getInputFormat(), sourceParse.get(i)
					.getInputFormat());
			assertEquals(source.getHeaderRows(), sourceParse.get(i)
					.getHeaderRows());
			assertEquals(source.getFieldNameRowNumer(), sourceParse.get(i)
					.getFieldNameRowNumer());
			assertEquals(source.getField_names_format(), sourceParse.get(i)
					.getField_names_format());
			assertEquals(source.getUpdate_mode_field_name(), sourceParse.get(i)
					.getUpdate_mode_field_name());
			assertEquals(source.getPathInputFile(), sourceParse.get(i)
					.getPathInputFile());
			assertEquals(source.getPathOutFile(), sourceParse.get(i)
					.getPathOutFile());
			assertEquals(source.getError_column(), sourceParse.get(i)
					.getError_column());
			assertEquals(source.getAutputFormat(), sourceParse.get(i)
					.getAutputFormat());
		}
	}

	@Test
	public void getTestShema() {
		Schema schema = new Schema("schema1");
		List<Table> listTable = new ArrayList<Table>();
		Table tableClient = new Table("Client");
		List<Field> listFealdClient = new ArrayList<Field>();
		listFealdClient.add(new Field("Client_No"));
		listFealdClient.add(new Field("Client_Name"));
		tableClient.setListFeald(listFealdClient);
		Table tableOrder = new Table("Order");
		tableOrder.setRoot(true);
		List<Field> listFieldOrder = new ArrayList<Field>();
		listFieldOrder.add(new Field("Order_No"));
		listFieldOrder.add((new Field("Client_No", "Client")));
		listFieldOrder.add(new Field("Quantity"));
		listFieldOrder.add(new Field("Delivery_Date"));
		listFieldOrder.add(new Field("Goods_Name"));
		tableOrder.setListFeald(listFieldOrder);
		listTable.add(tableClient);
		listTable.add(tableOrder);
		schema.setListTable(listTable);
		ArrayList<Schema> schemaParse = (ArrayList<Schema>) parseXml
				.getSxema(doc);

		for (int i = 0; i < schemaParse.size(); i++) {
			assertEquals(schema.getId(), schemaParse.get(i).getId());

			for (int tab = 0; tab < schemaParse.get(i).getListTable().size(); tab++) {
				assertEquals(schema.getListTable().get(tab).getNameTable(),
						schemaParse.get(i).getListTable().get(tab)
								.getNameTable());
				assertEquals(schema.getListTable().get(tab).isRoot(),
						schemaParse.get(i).getListTable().get(tab).isRoot());
				for (int field = 0; field < schemaParse.get(i).getListTable()
						.get(tab).getListField().size(); field++) {
					 logger1.info("size//////////"+field + " "+schemaParse.get(i)
								.getListTable().get(tab).getListField().get(field)
								.getName()+ " " + tab);
					assertEquals(schema.getListTable().get(tab).getListField()
							.get(field).getName(), schemaParse.get(i)
							.getListTable().get(tab).getListField().get(field)
							.getName());
					assertEquals(schema.getListTable().get(tab).getListField()
							.get(field).getNameTableForeignKey(),
							schemaParse.get(i).getListTable().get(tab)
									.getListField().get(field)
									.getNameTableForeignKey());

				}
			}
		}
	}
	@Ignore
	@Test
	public void testMapSource(){
		MapSource mapSource = new MapSource();
		mapSource.setIdSchema("schema1");
		mapSource.setIdSource("src1");
		Conversion conversion = new Conversion();
		List<FieldFormat> listfildFormat = new ArrayList<FieldFormat>();
		listfildFormat.add( new FieldFormat("trim","Client.Name"));
		List<Map<String,String>> listLookUp =  new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put(StaticVariablesXML.LOOKUP_VARIABLE,"Goods.Goods_No");
		map.put(StaticVariablesXML.LOOKUP_TABLE,"Goods");
		map.put(StaticVariablesXML.LOOKUP_COL_IN_TABLE,"Goods_No");
		map.put(StaticVariablesXML.RESULT_COL_IN_TABLE,"Goods_Name");
		map.put(StaticVariablesXML.UPDATE_INTO_VARIABLE,"Order.Goods_Name");
		listLookUp.add(map);
		conversion.setLookUp(listLookUp);
		conversion.setListFormat(listfildFormat);
		List <Substitute> listSubstitute = new  ArrayList<Substitute>();
		Substitute substitute = new Substitute();
		substitute.setVariable("Order.Quantity");
		Value value = new Value();
		HashMap<String,String> constant = new HashMap<String,String>();
		constant.put("Contst1","-");
		List<FieldFormat> listfildFormatSub = new ArrayList<FieldFormat>();
		listfildFormatSub.add( new FieldFormat("trim","Order.Quantity"));
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
		conversion.setListSubstitute(listSubstitute);
		mapSource.seConversion(conversion);
		ArrayList<MapSource> mapSourceParse = (ArrayList<MapSource>) parseXml.getMapSource(doc);

		for (int i = 0; i < mapSourceParse.size(); i++) {
			assertEquals(mapSource.getIdSchema(), mapSourceParse.get(i).getIdSchema());
			assertEquals(mapSource.getIdSource(), mapSourceParse.get(i).getIdSource());
			assertEquals(mapSource.getConversion().getFormat().get(0).getFieldName(), mapSourceParse.get(i).getConversion().getFormat().get(0).getFieldName());
			assertEquals(mapSource.getConversion().getFormat().get(0).getNameFormat(), mapSourceParse.get(i).getConversion().getFormat().get(0).getNameFormat());
			assertEquals(mapSource.getConversion().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_VARIABLE), mapSourceParse.get(i).getConversion().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_VARIABLE));
			assertEquals(mapSource.getConversion().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_TABLE), mapSourceParse.get(i).getConversion().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_TABLE));
			assertEquals(mapSource.getConversion().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_COL_IN_TABLE), mapSourceParse.get(i).getConversion().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_COL_IN_TABLE));
			assertEquals(mapSource.getConversion().getLookUp().get(0).get(StaticVariablesXML.RESULT_COL_IN_TABLE), mapSourceParse.get(i).getConversion().getLookUp().get(0).get(StaticVariablesXML.RESULT_COL_IN_TABLE));
			assertEquals(mapSource.getConversion().getLookUp().get(0).get(StaticVariablesXML.UPDATE_INTO_VARIABLE), mapSourceParse.get(i).getConversion().getLookUp().get(0).get(StaticVariablesXML.UPDATE_INTO_VARIABLE));
			
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getVariable(), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getVariable());
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getValue().getConstant().get("Contst1"), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getValue().getConstant().get("Contst1"));
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getValue().getFormat().get(0).getFieldName(), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getValue().getFormat().get(0).getFieldName());
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getValue().getFormat().get(0).getNameFormat(), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getValue().getFormat().get(0).getNameFormat());
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getValue().getFormat().get(0).getNameFormat(), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getValue().getFormat().get(0).getNameFormat());
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getValue().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_VARIABLE), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getValue().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_VARIABLE));
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getValue().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_TABLE), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getValue().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_TABLE));
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getValue().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_COL_IN_TABLE), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getValue().getLookUp().get(0).get(StaticVariablesXML.LOOKUP_COL_IN_TABLE));
			assertEquals(mapSource.getConversion().getListSubstitute().get(0).getValue().getLookUp().get(0).get(StaticVariablesXML.RESULT_COL_IN_TABLE), mapSourceParse.get(i).getConversion().getListSubstitute().get(0).getValue().getLookUp().get(0).get(StaticVariablesXML.RESULT_COL_IN_TABLE));
		}
		
		
	}
}