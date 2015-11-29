package test_test.upload.model.map.substitute;

import java.util.List;
import java.util.Map;

import test_test.upload.model.map.conversion.FieldFormat;


public class Value {
	/**
	 * field which need format 
	 * @see FieldFormat
	 */
	private List <FieldFormat>  format;
	/**
	 * foreign keys that need to change in root table
	 */
	private List <Map<String,String>> lookUp;
	/**
	 * Delimiter  between word   
	 */
	private Map <String,String> constant ;
	
	/**
	 * empty constructor 
	 */
	public Value() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param format
	 * @param lookUp
	 * @param constant
	 */
	public Value(List<FieldFormat> format, List<Map<String,String>> lookUp,Map <String,String>constant) {
		super();
		this.format = format;
		this.lookUp =  lookUp;
		this.constant = constant;
	}
	public List<FieldFormat> getFormat() {
		return format;
	}
	public void setFormat(List<FieldFormat> format) {
		this.format = format;
	}
	public List<Map<String,String>> getLookUp() {
		return lookUp;
	}
	public void setLookUp(List<Map<String, String>> lookUpSubstitude) {
		this.lookUp = lookUpSubstitude;
	}
	public Map<String,String> getConstant() {
		return constant;
	}
	public void setConstant(Map<String,String> constant) {
		this.constant = constant;
	}
	
	

}
