package test_test.upload.model.parse;

import java.util.List;

import test_test.upload.model.map.conversion.Conversion;
import test_test.upload.model.map.substitute.Substitute;

public class MapSource {
	/**
	 * id Source in file
	 */
	private String idSource;
	/**
	 * id schema in file
	 */
	private String idSchema;
	/**
	 * list field which need removes spaces before as put in table  
	 */
	private List<Conversion> listConversion;
	
	

	/**
	 * empty constructor
	 */
	public MapSource() {
		super();
		
	}



	/**
	 * @param idSource
	 * @param idSchema
	 * @param listConversion
	 */
	public MapSource(String idSource, String idSchema,
			List<Conversion> listConversion) {
		this.idSource = idSource;
		this.idSchema = idSchema;
		this.listConversion = listConversion;
	}

	/**
	 * @return the idSource
	 */
	public String getIdSource() {
		return idSource;
	}

	/**
	 * @param idSource the idSource to set
	 */
	public void setIdSource(String idSource) {
		this.idSource = idSource;
	}

	/**
	 * @return the idSchema
	 */
	public String getIdSchema() {
		return idSchema;
	}

	/**
	 * @param idSchema the idSchema to set
	 */
	public void setIdSchema(String idSchema) {
		this.idSchema = idSchema;
	}

	/**
	 * @return the listConversion
	 */
	public List<Conversion> getListConversion() {
		return listConversion;
	}

	/**
	 * @param listConversion the listConversion to set
	 */
	public void setListConversion(List<Conversion> listConversion) {
		this.listConversion = listConversion;
	}

	

	
}
