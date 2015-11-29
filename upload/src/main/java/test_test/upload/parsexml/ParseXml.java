package test_test.upload.parsexml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

@Component
public class ParseXml implements IParse {
	private static final Logger logger1 = Logger.getLogger(ParseXml.class);
	private static final String SOURCE = "source";
	private static final String ID = "id";
	private static final String INFILE = "infile";
	private static final String OUTFILE = "outfile";
	private static final String FORMAT = "format";
	private static final String HEADER_ROWS = "header_rows";
	private static final String FIELD_NAMES_ROW_NUMER = "field_names_row_numer";
	private static final String FIELD_NAMES_FORMAT = "field_names_format";
	private static final String UPDATE_MODE_FIELD_NAME = "update_mode_field_name";
	private static final String PATH = "path";
	private static final String ERROR_COLUMN = "error_column";
	private static final String TABLE = "table";
	private static final String NAME = "name";
	private static final String FOREIGN_KEY_TABLE = "foreign_key_table";
	private static final String FIELD = "field";
	private static final String SCHEMA = "schema";

	private static final String MAP = "map";
	private static final String CONVERSION = "conversion";
	public static final String TRIM = "trim";
	public  static final String LEFT = "left";
	public  static final String RIGHT = "right";
	public  static final String MID = "mid";
	private static final String FIELD_NAME = "field_name";
	private static final String LOOKUP = "lookup";
	public static final String LOOKUP_VARIABLE = "lookup_variable";
	public  static final String LOOKUP_TABLE = "lookup_table";
	public  static final String LOOKUP_COL_IN_TABLE = "lookup_col_in_table";
	public static final String RESULT_COL_IN_TABLE = "result_col_in_table";
	public  static final String UPDATE_INTO_VARIABLE = "update_into_variable";
	private static final String SUBSTITUDE = "substitude";
	private static final String VALUE = "value";
	private static final String TARGET = "target";
	private static final String VARIABLE = "variable";
	private static final String CONSTANT ="constant";
	private static final String LENGTH  = "length";
	private static final String START_NUM ="start_num";
	
	private File fileXml;

	

	/**
	 * @param fileXml
	 */
	public ParseXml(File fileXml) {
		this.fileXml = fileXml;

	}

	public ParseXml() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the fileXml
	 */
	public File getFileXml() {
		return fileXml;
	}

	/**
	 * @param fileXml
	 *            the fileXml to set
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void setFileXml(File fileXml) throws ParserConfigurationException,
			SAXException {
		this.fileXml = fileXml;

	}

	public Document initDomParser() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(fileXml);

		} catch (ParserConfigurationException | SAXException | IOException e) {

			logger1.error(e);
		}

		return doc;
	}

	@Override
	public List<Source> getSource(Document doc) {
		Source source = null;
		ArrayList<Source> listSource = new ArrayList<Source>();
		Node nodeRoot = doc.getDocumentElement();
		NodeList rootList = nodeRoot.getChildNodes();
		for (int root = 0; root < rootList.getLength(); root++) {
			if (rootList.item(root).getNodeType() == Node.ELEMENT_NODE) {
				if (rootList.item(root).getNodeName().equals(SOURCE)) {
					source = new Source();
					Element eElement = (Element) rootList.item(root);
					source.setId(eElement.getAttribute(ID));
					NodeList docList = eElement.getChildNodes();
					for (int i = 0; i < docList.getLength(); i++) {
						if (docList.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Element eSource = (Element) docList.item(i);
							if (eSource.getNodeName().equals(INFILE)) {
								source.setInputFormat(eSource
										.getAttribute(FORMAT));
								source.setField_names_format(eSource
										.getAttribute(FIELD_NAMES_FORMAT));
								source.setHeaderRows(Integer.parseInt(eSource
										.getAttribute(HEADER_ROWS)));
								source.setFieldNameRowNumer(Integer.parseInt(eSource
										.getAttribute(FIELD_NAMES_ROW_NUMER)));
								source.setUpdate_mode_field_name((eSource
										.getAttribute(UPDATE_MODE_FIELD_NAME))
										.charAt(0));
								source.setPathInputFile(eSource
										.getAttribute(PATH));
							}
							if (eSource.getNodeName().equals(OUTFILE)) {
								source.setAutputFormat(eSource
										.getAttribute(FORMAT));
								source.setError_column(eSource
										.getAttribute(ERROR_COLUMN));
								source.setPathOutFile(eSource
										.getAttribute(PATH));
							}
						}

					}
					listSource.add(source);
				}
			}
		}

		return listSource;
	}

	@SuppressWarnings("null")
	@Override
	public List<MapSource> getMapSource(Document doc) {
		MapSource mapSource = null;
		List<MapSource> mapSourceList = null;
		FieldFormat fildFormat = null;
		List<FieldFormat> fieldForamtList = null;
		List<FieldFormat> fieldSubstitudeForamtList = null;
		List<Map<String, String>> lookUpList = null;
		List<Map<String, String>> lookUpSubstitude = null;
		Substitute substitute;
		List<Substitute> listSubstitude = null;
		Conversion conversion = null;
		Value value = null;
		Node nodeRoot = doc.getDocumentElement();
		NodeList rootList = nodeRoot.getChildNodes();
		for (int root = 0; root < rootList.getLength(); root++) {
			if (rootList.item(root).getNodeType() == Node.ELEMENT_NODE) {
				if (rootList.item(root).getNodeName().equals(MAP)) {
				
					mapSource = new MapSource();
					mapSourceList = new ArrayList<MapSource>();
					Element elementMap = (Element) rootList.item(root);
					mapSource.setIdSchema(elementMap.getAttribute(SCHEMA));
					mapSource.setIdSource(elementMap.getAttribute(SOURCE));
					NodeList mapList = elementMap.getChildNodes();
					for (int map = 0; map < mapList.getLength(); map++) {
						if (mapList.item(map).getNodeType() == Node.ELEMENT_NODE) {
							if (mapList.item(map).getNodeName()
									.equals(CONVERSION)) {
								logger1.info("conversion");
								fieldForamtList = new ArrayList<FieldFormat>();
								lookUpList = new ArrayList<Map<String,String>>();
								conversion = new Conversion();
								listSubstitude = new ArrayList<Substitute>();
								NodeList conversionList = mapList.item(map)
										.getChildNodes();
								for (int con = 0; con < conversionList
										.getLength(); con++) {
									if (conversionList.item(con).getNodeType() == Node.ELEMENT_NODE) {
										getForamtList(fieldForamtList,
												conversionList, con);
										getLookUPList(lookUpList,
												conversionList, con);
										if (conversionList.item(con)
												.getNodeName()
												.equals(SUBSTITUDE)) {
											substitute = new Substitute();
											String variable = null;
											NodeList converList = conversionList
													.item(con).getChildNodes();
											for (int conv = 0; conv < converList
													.getLength(); conv++) {
												if (converList.item(conv)
														.getNodeType() == Node.ELEMENT_NODE) {
													if (converList.item(conv)
															.getNodeName()
															.equals(TARGET)) {
														variable = ((Element) converList
																.item(conv))
																.getAttribute(VARIABLE);
													}
													if (converList.item(conv)
															.getNodeName()
															.equals(VALUE)) {
														logger1.info("????????????????????????????");
														value = new Value();
													HashMap<String,String>  listConstant = new HashMap<String,String>();
														fieldSubstitudeForamtList = new ArrayList<FieldFormat>();
														lookUpSubstitude = new ArrayList<Map<String,String>>();
														NodeList valueList = converList.item(conv)
																.getChildNodes();
														for (int val = 0; val < valueList
																.getLength(); val++) {
															if (valueList.item(val).getNodeType() == Node.ELEMENT_NODE) {	
														getForamtList(
																fieldSubstitudeForamtList,
																valueList,
																val);

														getLookUPList(
																lookUpSubstitude,
																valueList,
																val);
														
														if (valueList.item(val).getNodeName().equals(CONSTANT)) {
															Element  elemConstant = ((Element)(valueList.item(val)));
															listConstant.put( elemConstant.getAttribute(VALUE),elemConstant.getTextContent().trim());
														       
														}
														value.setLookUp(lookUpSubstitude);
														value.setFormat(fieldSubstitudeForamtList);
														value.setConstant(listConstant);
															}
														}
													}
												}
											}
											substitute.setVariable(variable);
											substitute.setValue(value);
											listSubstitude.add(substitute);
										}
									}

								}
								
								conversion.setListFormat((fieldForamtList));
								conversion.setLookUp(lookUpList);
								conversion.setListSubstitute(listSubstitude);
								mapSource.seConversion(conversion);
							}

							
						}
						
					}
					mapSourceList.add(mapSource);
				}
			}
		}

		return mapSourceList;
	}
/**
 * search Lookup in tag and
 * add LookUp in list lookUp 
 * @param lookUpList
 * @param conversionList
 * @param con
 */
	private void getLookUPList(List<Map<String,String>> lookUpList,
			NodeList conversionList, int con) {
	
		if (conversionList.item(con).getNodeName().equals(LOOKUP)) {
			HashMap<String,String> mapLookUp = new HashMap<String,String>();
			Element element = ((Element) conversionList.item(con));
			if(element.hasAttribute(LOOKUP_VARIABLE)){
				mapLookUp.put(LOOKUP_VARIABLE,element.getAttribute(LOOKUP_VARIABLE));
			}
             if(element .hasAttribute(LOOKUP_TABLE)){
            	 mapLookUp.put(LOOKUP_TABLE,element.getAttribute(LOOKUP_TABLE));
			}
             if(element.hasAttribute(LOOKUP_COL_IN_TABLE)){
            	 mapLookUp.put(LOOKUP_COL_IN_TABLE,element.getAttribute(LOOKUP_COL_IN_TABLE));
 			}
             if(element .hasAttribute(RESULT_COL_IN_TABLE)){
            	 mapLookUp.put(RESULT_COL_IN_TABLE,element.getAttribute(RESULT_COL_IN_TABLE));
 			}
             if(element .hasAttribute(UPDATE_INTO_VARIABLE)){
            	 mapLookUp.put(UPDATE_INTO_VARIABLE,element.getAttribute(UPDATE_INTO_VARIABLE));
 			}
             lookUpList.add(mapLookUp);
		}
	}
/**
 * search in tag  trim, left, right,mid
 * create FieldFormat 
 * and add all fieldFormat  in list FildFormat 
 * @param fieldForamtList
 * @param conversionList
 * @param con
 */
	private void getForamtList(List<FieldFormat> fieldForamtList,
			NodeList conversionList, int con) {
		FieldFormat fildFormat;

		if (conversionList.item(con).getNodeName().equals(TRIM)
				|| conversionList.item(con).getNodeName().equals(LEFT)
				|| conversionList.item(con).getNodeName().equals(RIGHT)
				|| conversionList.item(con).getNodeName().equals(MID)) {
	
			fildFormat = new FieldFormat(
					conversionList.item(con).getNodeName(),
					((Element) conversionList.item(con))
							.getAttribute(FIELD_NAME));
			if (((Element) conversionList.item(con)).hasAttribute(LENGTH)){
				fildFormat.setLengthSybol(Integer.parseInt(((Element) conversionList.item(con))
							.getAttribute(LENGTH)));
			}
			if (((Element) conversionList.item(con)).hasAttribute(START_NUM)){
				fildFormat.setStartNum(Integer.parseInt(((Element) conversionList.item(con))
							.getAttribute(START_NUM)));
			}
			fieldForamtList.add(fildFormat);
		}
	}

	@Override
	public List<Schema> getSxema(Document doc) {
		Schema schema = null;
		List<Table> listTable = null;
		Table table = null;
		Field field = null;
		List<Field> listField = null;
		ArrayList<Schema> listSchema = new ArrayList<Schema>();
		Node nodeRoot = doc.getDocumentElement();
		NodeList rootList = nodeRoot.getChildNodes();
		for (int root = 0; root < rootList.getLength(); root++) {
			if (rootList.item(root).getNodeType() == Node.ELEMENT_NODE) {
				if (rootList.item(root).getNodeName().equals(SCHEMA)) {
					schema = new Schema();
					listTable = new ArrayList<Table>();
					Element eElement = (Element) rootList.item(root);
					schema.setId(eElement.getAttribute(ID));
					NodeList tableList = eElement.getChildNodes();
					for (int i = 0; i < tableList.getLength(); i++) {
						if (tableList.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Element tableElement = (Element) tableList.item(i);
							if (tableElement.getNodeName().equals(TABLE)) {
								table = new Table();
								listField = new ArrayList<Field>();
								table.setNameTable(tableElement
										.getAttribute(NAME));
								NodeList fieldList = tableElement
										.getChildNodes();
								for (int fd = 0; fd < fieldList.getLength(); fd++) {

									if (fieldList.item(fd).getNodeType() == Node.ELEMENT_NODE) {
										Element fieldElement = (Element) fieldList
												.item(fd);
										if (fieldElement.getNodeName().equals(
												FIELD)) {
											field = new Field();
											field.setName(fieldElement
													.getAttribute(NAME));
											if (fieldElement
													.hasAttribute(FOREIGN_KEY_TABLE)) {
												field.setNameTableForeignKey(fieldElement
														.getAttribute(FOREIGN_KEY_TABLE));
											}
											listField.add(field);
										}
									}
								}
								table.setListFeald(listField);
								listTable.add(table);
							}
						}
					}
					schema.setListTable(listTable);
					listSchema.add(schema);
				}
			}
		}

		return listSchema;
	}

}
