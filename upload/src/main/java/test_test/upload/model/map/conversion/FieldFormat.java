package test_test.upload.model.map.conversion;

public class FieldFormat {
	/**
	 * name format example  trim, left, right, trim
	 */
	private String nameFormat;
	/**
	 * name field which need change
	 */
	private  String fieldName;
	/**
	 * empty constructor
	 */
	public FieldFormat() {
		super();


	}
	/**
	 * 
	 * @param nameFormat
	 * @param fieldName
	 */
	public FieldFormat(String nameFormat, String fieldName) {
		super();
		this.nameFormat = nameFormat;
		this.fieldName = fieldName;
	}
	public String getNameFormat() {
		return nameFormat;
	}
	public void setNameFormat(String nameFormat) {
		this.nameFormat = nameFormat;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
      
}
