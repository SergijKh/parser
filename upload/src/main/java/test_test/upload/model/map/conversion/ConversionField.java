package test_test.upload.model.map.conversion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import test_test.upload.exception.ConversionNotCorectFieldData;
import test_test.upload.model.staticvariablesxmlfile.StaticVariablesXML;
import test_test.upload.parsexml.ParseXml;
@Component
public class ConversionField {
	private static final Logger logger1 = Logger
			.getLogger(ConversionField.class);
	
/**
 * empty Constructor
 */
	public ConversionField() {
		super();
		
	}
/**
 * add  conversion  value in map
 * @param hashMap
 * @param listFieldFormat
 */
	public void allMapConversion(Map<String, String> hashMap,
			List<FieldFormat> listFieldFormat) {
		Set<String> setKey = hashMap.keySet();
		Iterator<String> iterKey = (Iterator<String>) setKey.iterator();
		while (iterKey.hasNext()) {
			String keyField = iterKey.next();
			 for (int i = 0 ; i < listFieldFormat.size(); i ++){
				 if (listFieldFormat.get(i).getFieldName().equals(keyField)){
			             try{
			             if (listFieldFormat.get(i).getNameFormat().equals(StaticVariablesXML.TRIM)){
			            	
			            	 hashMap.put(keyField, trimConversion(hashMap.get(keyField)));
			             }else if (listFieldFormat.get(i).getNameFormat().equals(StaticVariablesXML.LEFT)){
			            	 hashMap.put(keyField, leftSymbol(hashMap.get(keyField),listFieldFormat.get(i).getLengthSybol()));
			             }else if (listFieldFormat.get(i).getNameFormat().equals(StaticVariablesXML.RIGHT)){
			            	 hashMap.put(keyField, rightSymbol(hashMap.get(keyField),listFieldFormat.get(i).getLengthSybol()));
			             }else if (listFieldFormat.get(i).getNameFormat().equals(StaticVariablesXML.MID)){
			            	 hashMap.put(keyField, midSymbol(hashMap.get(keyField),listFieldFormat.get(i).getStartNum(),listFieldFormat.get(i).getLengthSybol()));
			             }
			             }catch(ConversionNotCorectFieldData e){
			            	 logger1.error(e);
			             }
			    
			 }
		}

	}
	}
	/**
	 * conversion value trim,right, left, mid
	 * @param valueIn 
	 * @param fieldFormat
	 * @return String conversion
	 */
	public  String oneValueConversion(String valueIn, FieldFormat fieldFormat) {
		String value = null;
			             try{
			             if (fieldFormat.getNameFormat().equals(StaticVariablesXML.TRIM)){
			            	 value = trimConversion(valueIn);	 
			             }else if (fieldFormat.getNameFormat().equals(StaticVariablesXML.LEFT)){
			            	 value =  leftSymbol(valueIn,fieldFormat.getLengthSybol());
			            	 logger1.info("left " + value);
			             }else if (fieldFormat.getNameFormat().equals(StaticVariablesXML.RIGHT)){
			            	 value =   rightSymbol(valueIn,fieldFormat.getLengthSybol());
			             }else if (fieldFormat.getNameFormat().equals(StaticVariablesXML.MID)){
			            	 value = midSymbol(valueIn,fieldFormat.getStartNum(),fieldFormat.getLengthSybol());
			             }
			             }catch(ConversionNotCorectFieldData e){
			            	 logger1.error(e);
			             }
						return value;
			    
			 }
		
	
/**
 * trim value 
 * @param value 
 * @return
 */
	public String trimConversion(String value) {
		if (value == null || value == "") {
			logger1.error("field is empty");
		}
		return value.trim();

	}
/**
 * selects the beginning of the line of numberSymbol characters
 * @param value
 * @param numberSymbol number symbol 
 * @return  String  value 
 * @throws ConversionNotCorectFieldData
 */
	public String leftSymbol(String value, int numberSymbol)
			throws ConversionNotCorectFieldData {
		if (value == null || value == "" || numberSymbol <= 0) {
			logger1.error("field is empty or field number symbol equels 0 or less 0");
			throw new ConversionNotCorectFieldData(
					"field is empty or field number symbol equels 0 or less 0");

		}
		return value.trim().substring(0, numberSymbol);

	}

	/**
	 * selects numberSymbol characters at the end of line
	 * @param value
	 * @param numberSymbol number symbol 
	 * @return
	 * @throws ConversionNotCorectFieldData
	 */
	public String rightSymbol(String value, int numberSymbol)
			throws ConversionNotCorectFieldData {
		if (value == null || value == "" || numberSymbol <= 0
				|| numberSymbol > value.length()) {
			logger1.error("field is empty or field number symbol equels 0 or less 0");
			throw new ConversionNotCorectFieldData(
					"field is empty or field number symbol equels 0 or less 0");
		}
		return value.trim().substring((value.length() - 1) - numberSymbol,
				value.length() - 1);
	}

	/**
	 * selects the characters from the start startSymbol to the end andSymbol
	 * @param value
	 * @param startSymbol start symbol
	 * @param andSymbol  and symbol
	 * @return String 
	 * @throws ConversionNotCorectFieldData
	 */
	public String midSymbol(String value, int startSymbol, int andSymbol) throws ConversionNotCorectFieldData {
		if (value == null || value == "" || startSymbol <= 0 || andSymbol <= 0
				|| startSymbol > value.length() || andSymbol > value.length()||(Math.abs(andSymbol-startSymbol))> value.length()) {
			logger1.error("field is empty and  start and and symbol not correct");
			throw new ConversionNotCorectFieldData(
					"field is empty and  start and and symbol not correct");
		}
		return value.trim().substring(startSymbol,andSymbol);
	}
}
