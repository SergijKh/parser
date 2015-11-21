package test_test.upload.model.map.conversion;

import java.util.List;

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
	private List <LookUp> lookUp;
	 /**
	  * list substitute
	  */
	 private List<Substitute> listSubstitude;
	 
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
	public Conversion(List<FieldFormat> format, List<LookUp> lookUp,
			List<Substitute> listSubstitude) {
		this.format = format;
		this.lookUp = lookUp;
		this.listSubstitude = listSubstitude;
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
	public void setFormat(List<FieldFormat> format) {
		this.format = format;
	}

	/**
	 * @return the lookUp
	 */
	public List<LookUp> getLookUp() {
		return lookUp;
	}

	/**
	 * @param lookUp the lookUp to set
	 */
	public void setLookUp(List<LookUp> lookUp) {
		this.lookUp = lookUp;
	}

	/**
	 * @return the listSubstitude
	 */
	public List<Substitute> getListSubstitude() {
		return listSubstitude;
	}

	/**
	 * @param listSubstitude the listSubstitude to set
	 */
	public void setListSubstitude(List<Substitute> listSubstitude) {
		this.listSubstitude = listSubstitude;
	}

	
	
	 
}
