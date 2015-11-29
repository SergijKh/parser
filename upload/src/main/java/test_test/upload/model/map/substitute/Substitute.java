package test_test.upload.model.map.substitute;



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
	
	private Value value;
	/**
	 * empty constructor
	 */
	public Substitute() {
		super();
		
	}
	
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
	
	
      
}
