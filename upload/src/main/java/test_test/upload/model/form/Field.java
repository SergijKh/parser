package test_test.upload.model.form;
/**
 * 
 * @author Sergey
 * class  Field describe field in table
 */
public class Field {
	 

	/**
	  * name field
	  */
	private String name;
	/**
	 * value field name
	 */
	private String  value;
	/**
	 * name table foreign key 
	 */
	private  String nameTableForeignKey;
	
	/**
	 * empty constructor
	 */
	public Field() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public Field(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @param name name field 
	 * @param nameTableForeignKey foreign key
	 */

	public Field(String name, String nameTableForeignKey) {
		super();
		this.name = name;
		this.nameTableForeignKey = nameTableForeignKey;
	}

	/**
	 * @param name 
	 * @param value
	 * @param nameTableForeignKey
	 */
	public Field(String name, String value, String nameTableForeignKey) {
		this.name = name;
		this.value = value;
		this.nameTableForeignKey = nameTableForeignKey;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the nameTableForeignKey
	 */
	public String getNameTableForeignKey() {
		return nameTableForeignKey;
	}

	/**
	 * @param nameTableForeignKey the nameTableForeignKey to set
	 * @return 
	 */
	public void setNameTableForeignKey(String nameTableForeignKey) {
		this.nameTableForeignKey = nameTableForeignKey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
