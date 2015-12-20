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
import test_test.upload.model.staticvariablesxmlfile.StaticVariablesXML;

@Component
public class ParseXml implements IParse {
	private static final Logger logger1 = Logger.getLogger(ParseXml.class);
	
	
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
				if (rootList.item(root).getNodeName().equals(StaticVariablesXML.SOURCE)) {
					source = new Source();
					Element eElement = (Element) rootList.item(root);
					source.setId(eElement.getAttribute(StaticVariablesXML.ID));
					NodeList docList = eElement.getChildNodes();
					for (int i = 0; i < docList.getLength(); i++) {
						if (docList.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Element eSource = (Element) docList.item(i);
							if (eSource.getNodeName().equals(StaticVariablesXML.INFILE)) {
								source.setInputFormat(eSource
										.getAttribute(StaticVariablesXML.FORMAT));
								source.setField_names_format(eSource
										.getAttribute(StaticVariablesXML.FIELD_NAMES_FORMAT));
								source.setHeaderRows(Integer.parseInt(eSource
										.getAttribute(StaticVariablesXML.HEADER_ROWS)));
								source.setFieldNameRowNumer(Integer.parseInt(eSource
										.getAttribute(StaticVariablesXML.FIELD_NAMES_ROW_NUMER)));
								source.setUpdate_mode_field_name((eSource
										.getAttribute(StaticVariablesXML.UPDATE_MODE_FIELD_NAME))
										.charAt(0));
								source.setPathInputFile(eSource
										.getAttribute(StaticVariablesXML.PATH));
							}
							if (eSource.getNodeName().equals(StaticVariablesXML.OUTFILE)) {
								source.setAutputFormat(eSource
										.getAttribute(StaticVariablesXML.FORMAT));
								source.setError_column(eSource
										.getAttribute(StaticVariablesXML.ERROR_COLUMN));
								source.setPathOutFile(eSource
										.getAttribute(StaticVariablesXML.PATH));
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
				if (rootList.item(root).getNodeName().equals(StaticVariablesXML.MAP)) {
				
					mapSource = new MapSource();
					mapSourceList = new ArrayList<MapSource>();
					Element elementMap = (Element) rootList.item(root);
					mapSource.setIdSchema(elementMap.getAttribute(StaticVariablesXML.SCHEMA));
					mapSource.setIdSource(elementMap.getAttribute(StaticVariablesXML.SOURCE));
					NodeList mapList = elementMap.getChildNodes();
					for (int map = 0; map < mapList.getLength(); map++) {
						if (mapList.item(map).getNodeType() == Node.ELEMENT_NODE) {
							if (mapList.item(map).getNodeName()
									.equals(StaticVariablesXML.CONVERSION)) {
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
												.equals(StaticVariablesXML.SUBSTITUDE)) {
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
															.equals(StaticVariablesXML.TARGET)) {
														variable = ((Element) converList
																.item(conv))
																.getAttribute(StaticVariablesXML.VARIABLE);
													}
													if (converList.item(conv)
															.getNodeName()
															.equals(StaticVariablesXML.VALUE)) {
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
														
														if (valueList.item(val).getNodeName().equals(StaticVariablesXML.CONSTANT)) {
															Element  elemConstant = ((Element)(valueList.item(val)));
															listConstant.put( elemConstant.getAttribute(StaticVariablesXML.VALUE),elemConstant.getTextContent().trim());
														       
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
	
		if (conversionList.item(con).getNodeName().equals(StaticVariablesXML.LOOKUP)) {
			HashMap<String,String> mapLookUp = new HashMap<String,String>();
			Element element = ((Element) conversionList.item(con));
			if(element.hasAttribute(StaticVariablesXML.LOOKUP_VARIABLE)){
				mapLookUp.put(StaticVariablesXML.LOOKUP_VARIABLE,element.getAttribute(StaticVariablesXML.LOOKUP_VARIABLE));
			}
             if(element .hasAttribute(StaticVariablesXML.LOOKUP_TABLE)){
            	 mapLookUp.put(StaticVariablesXML.LOOKUP_TABLE,element.getAttribute(StaticVariablesXML.LOOKUP_TABLE));
			}
             if(element.hasAttribute(StaticVariablesXML.LOOKUP_COL_IN_TABLE)){
            	 mapLookUp.put(StaticVariablesXML.LOOKUP_COL_IN_TABLE,element.getAttribute(StaticVariablesXML.LOOKUP_COL_IN_TABLE));
 			}
             if(element .hasAttribute(StaticVariablesXML.RESULT_COL_IN_TABLE)){
            	 mapLookUp.put(StaticVariablesXML.RESULT_COL_IN_TABLE,element.getAttribute(StaticVariablesXML.RESULT_COL_IN_TABLE));
 			}
             if(element .hasAttribute(StaticVariablesXML.UPDATE_INTO_VARIABLE)){
            	 mapLookUp.put(StaticVariablesXML.UPDATE_INTO_VARIABLE,element.getAttribute(StaticVariablesXML.UPDATE_INTO_VARIABLE));
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

		if (conversionList.item(con).getNodeName().equals(StaticVariablesXML.TRIM)
				|| conversionList.item(con).getNodeName().equals(StaticVariablesXML.LEFT)
				|| conversionList.item(con).getNodeName().equals(StaticVariablesXML.RIGHT)
				|| conversionList.item(con).getNodeName().equals(StaticVariablesXML.MID)) {
	
			fildFormat = new FieldFormat(
					conversionList.item(con).getNodeName(),
					((Element) conversionList.item(con))
							.getAttribute(StaticVariablesXML.FIELD_NAME));
			if (((Element) conversionList.item(con)).hasAttribute(StaticVariablesXML.LENGTH)){
				fildFormat.setLengthSybol(Integer.parseInt(((Element) conversionList.item(con))
							.getAttribute(StaticVariablesXML.LENGTH)));
			}
			if (((Element) conversionList.item(con)).hasAttribute(StaticVariablesXML.START_NUM)){
				fildFormat.setStartNum(Integer.parseInt(((Element) conversionList.item(con))
							.getAttribute(StaticVariablesXML.START_NUM)));
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
				if (rootList.item(root).getNodeName().equals(StaticVariablesXML.SCHEMA)) {
					schema = new Schema();
					listTable = new ArrayList<Table>();
					Element eElement = (Element) rootList.item(root);
					schema.setId(eElement.getAttribute(StaticVariablesXML.ID));
					NodeList tableList = eElement.getChildNodes();
					for (int i = 0; i < tableList.getLength(); i++) {
						if (tableList.item(i).getNodeType() == Node.ELEMENT_NODE) {
							Element tableElement = (Element) tableList.item(i);
							if (tableElement.getNodeName().equals(StaticVariablesXML.TABLE)) {
								table = new Table();
								listField = new ArrayList<Field>();
								table.setNameTable(tableElement
										.getAttribute(StaticVariablesXML.NAME));
								if (tableElement.hasAttribute(StaticVariablesXML.ROOT)){
									table.setRoot( Boolean.valueOf(tableElement
											.getAttribute(StaticVariablesXML.ROOT)));
								}
								NodeList fieldList = tableElement
										.getChildNodes();
								for (int fd = 0; fd < fieldList.getLength(); fd++) {

									if (fieldList.item(fd).getNodeType() == Node.ELEMENT_NODE) {
										Element fieldElement = (Element) fieldList
												.item(fd);
										if (fieldElement.getNodeName().equals(
												StaticVariablesXML.FIELD)) {
											field = new Field();
											field.setName(fieldElement
													.getAttribute(StaticVariablesXML.NAME));
											if (fieldElement
													.hasAttribute(StaticVariablesXML.FOREIGN_KEY_TABLE)) {
												field.setNameTableForeignKey(fieldElement
														.getAttribute(StaticVariablesXML.FOREIGN_KEY_TABLE));
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
