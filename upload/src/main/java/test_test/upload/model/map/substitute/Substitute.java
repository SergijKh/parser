package test_test.upload.model.map.substitute;

import test_test.upload.model.map.conversion.Conversion;
import test_test.upload.model.map.conversion.LookUp;
/**
 * class describe name field table which need substitute
 * @author Sergey
 *
 */
public class Substitute {
	
	/**
	 * name table and field which need substitute
	 */
	private String  variable;
	/**
	 * describe  where substitute
	 * @see  LookUp
	 */
	private  LookUp lookUp;
	/**
	 * conversion substitution fieald
	 */
	private Conversion conversionSubstitudeField;
	/**
	 * empty constructor
	 */
	public Substitute() {
		super();
		
	}
	/**
	 * @param variable
	 * @param lookUp
	 */
	public Substitute(String variable, LookUp lookUp) {
		this.variable = variable;
		this.lookUp = lookUp;
	}
	
	/**
	 * @param variable
	 * @param lookUp
	 * @param conversionSubstitudeField
	 */
	public Substitute(String variable, LookUp lookUp,
			Conversion conversionSubstitudeField) {
		this.variable = variable;
		this.lookUp = lookUp;
		this.conversionSubstitudeField = conversionSubstitudeField;
	}
	
      
}
