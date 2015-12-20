package test_test.upload.model.map.conversion;

import java.util.List;
import java.util.Map;

import test_test.upload.model.map.substitute.Substitute;

public class Conversion {
	
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
	  * list substitute
	  */
	 private List<Substitute> listSubstitute;
	 
	/**
	 * empty constructor
	 */
	public Conversion() {
		super();
		
	}

	/**
	 * @param format
	 * @param lookUp
	 * @param listSubstitude
	 */
	public Conversion(List<FieldFormat> format, List<Map<String,String>> lookUp,
			List<Substitute> listSubstitute) {
		this.format = format;
		this.lookUp = lookUp;
		this.listSubstitute = listSubstitute;
	}

	/**
	 * @return the format
	 */
	public List<FieldFormat> getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setListFormat(List<FieldFormat> format) {
		this.format = format;
	}

	/**
	 * @return the lookUp
	 */
	public List<Map<String,String>>getLookUp() {
		return  lookUp;
	}

	/**
	 * @param listLookUp the lookUp to set
	 */
	public void setLookUp(List<Map<String, String>> listLookUp) {
		this.lookUp = listLookUp;
	}

	/**
	 * @return the listSubstitude
	 */
	public List<Substitute> getListSubstitute() {
		return listSubstitute;
	}

	/**
	 * @param listSubstitude the listSubstitude to set
	 */
	public void setListSubstitute(List<Substitute> listSubstitude) {
		this.listSubstitute = listSubstitude;
	}

	
	
	 
}
