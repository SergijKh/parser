package test_test.upload.model.map.conversion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import test_test.upload.exception.ConversionNotCorectFieldData;
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

	public void allMapConversion(HashMap<String, String> hashMap,
			List<FieldFormat> listFieldFormat) {
		Set<String> setKey = hashMap.keySet();
		Iterator<String> iterKey = (Iterator<String>) setKey.iterator();
		while (iterKey.hasNext()) {
			String keyField = iterKey.next();
			 for (int i = 0 ; i < listFieldFormat.size(); i ++){
				 if (listFieldFormat.get(i).getFieldName().equals(keyField)){
			             try{
			             if (listFieldFormat.get(i).getNameFormat().equals(ParseXml.TRIM)){
			            	
			            	 hashMap.put(keyField, trimConversion(hashMap.get(keyField)));
			             }else if (listFieldFormat.get(i).getNameFormat().equals(ParseXml.LEFT)){
			            	 hashMap.put(keyField, leftSymbol(hashMap.get(keyField),listFieldFormat.get(i).getLengthSybol()));
			             }else if (listFieldFormat.get(i).getNameFormat().equals(ParseXml.RIGHT)){
			            	 hashMap.put(keyField, rightSymbol(hashMap.get(keyField),listFieldFormat.get(i).getLengthSybol()));
			             }else if (listFieldFormat.get(i).getNameFormat().equals(ParseXml.MID)){
			            	 hashMap.put(keyField, midSymbol(hashMap.get(keyField),listFieldFormat.get(i).getStartNum(),listFieldFormat.get(i).getLengthSybol()));
			             }
			             }catch(ConversionNotCorectFieldData e){
			            	 logger1.error(e);
			             }
			    
			 }
		}

	}
	}

	public String trimConversion(String value) {
		if (value == null || value == "") {
			logger1.error("field is empty");
		}
		return value.trim();

	}

	public String leftSymbol(String value, int numberSymbol)
			throws ConversionNotCorectFieldData {
		if (value == null || value == "" || numberSymbol <= 0) {
			logger1.error("field is empty or field number symbol equels 0 or less 0");
			throw new ConversionNotCorectFieldData(
					"field is empty or field number symbol equels 0 or less 0");

		}
		return value.substring(0, numberSymbol);

	}

	public String rightSymbol(String value, int numberSymbol)
			throws ConversionNotCorectFieldData {
		if (value == null || value == "" || numberSymbol <= 0
				|| numberSymbol > value.length()) {
			logger1.error("field is empty or field number symbol equels 0 or less 0");
			throw new ConversionNotCorectFieldData(
					"field is empty or field number symbol equels 0 or less 0");
		}
		return value.substring((value.length() - 1) - numberSymbol,
				value.length() - 1);
	}

	public String midSymbol(String value, int startSymbol, int andSymbol) throws ConversionNotCorectFieldData {
		if (value == null || value == "" || startSymbol <= 0 || andSymbol <= 0
				|| startSymbol > value.length() || andSymbol > value.length()||(Math.abs(andSymbol-startSymbol))> value.length()) {
			logger1.error("field is empty and  start and and symbol not correct");
			throw new ConversionNotCorectFieldData(
					"field is empty and  start and and symbol not correct");
		}
		return value.substring(startSymbol,andSymbol);
	}
}
