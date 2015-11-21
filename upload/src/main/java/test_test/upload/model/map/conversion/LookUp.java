package test_test.upload.model.map.conversion;
/**
 * class describe which value in one table need insert another table where value is secondary key
 * @author Sergey
 *
 */
public class LookUp {
	/**
	 * field value source
	 */
	private String lookUpVariable;
	/**
	 * name table  which 
	 */
	private String lookTable;
	/**
	 * name column  in table 
	 */
	private String lookColInTable;
	 /**
	  * result column  lookColInTable
	  */
	private String resultIntable;
	  /**
	   *  table field which need inserting update
	   */
	private String upadeteIntoVariable;
	
	
	/**
	 * 
	 */
	public LookUp() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param lookUpVariable
	 * @param lookTable
	 * @param lookColInTable
	 * @param resultIntable
	 */
	public LookUp(String lookUpVariable, String lookTable,
			String lookColInTable, String resultIntable) {
		this.lookUpVariable = lookUpVariable;
		this.lookTable = lookTable;
		this.lookColInTable = lookColInTable;
		this.resultIntable = resultIntable;
	}
	/**
	 * @param lookUpVariable
	 * @param lookTable
	 * @param lookColInTable
	 * @param resultIntable
	 * @param upadeteIntoVariable
	 */
	public LookUp(String lookUpVariable, String lookTable,
			String lookColInTable, String resultIntable,
			String upadeteIntoVariable) {
		this.lookUpVariable = lookUpVariable;
		this.lookTable = lookTable;
		this.lookColInTable = lookColInTable;
		this.resultIntable = resultIntable;
		this.upadeteIntoVariable = upadeteIntoVariable;
	}
	/**
	 * @return the lookUpVariable
	 */
	public String getLookUpVariable() {
		return lookUpVariable;
	}
	/**
	 * @param lookUpVariable the lookUpVariable to set
	 */
	public void setLookUpVariable(String lookUpVariable) {
		this.lookUpVariable = lookUpVariable;
	}
	/**
	 * @return the lookTable
	 */
	public String getLookTable() {
		return lookTable;
	}
	/**
	 * @param lookTable the lookTable to set
	 */
	public void setLookTable(String lookTable) {
		this.lookTable = lookTable;
	}
	/**
	 * @return the lookColInTable
	 */
	public String getLookColInTable() {
		return lookColInTable;
	}
	/**
	 * @param lookColInTable the lookColInTable to set
	 */
	public void setLookColInTable(String lookColInTable) {
		this.lookColInTable = lookColInTable;
	}
	/**
	 * @return the resultIntable
	 */
	public String getResultIntable() {
		return resultIntable;
	}
	/**
	 * @param resultIntable the resultIntable to set
	 */
	public void setResultIntable(String resultIntable) {
		this.resultIntable = resultIntable;
	}
	/**
	 * @return the upadeteIntoVariable
	 */
	public String getUpadeteIntoVariable() {
		return upadeteIntoVariable;
	}
	/**
	 * @param upadeteIntoVariable the upadeteIntoVariable to set
	 */
	public void setUpadeteIntoVariable(String upadeteIntoVariable) {
		this.upadeteIntoVariable = upadeteIntoVariable;
	}

}
