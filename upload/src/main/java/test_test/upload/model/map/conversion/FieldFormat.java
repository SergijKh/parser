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
	 * 
	 */
	/**
	 * value field start  in startSymbol
	 */
	private int  startNum ;
	/**
	 * length symbol in field 
	 */
	private int  lengthSybol;
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
	
	public FieldFormat(String nameFormat, String fieldName, int startNum,
			int lengthSybol) {
		super();
		this.nameFormat = nameFormat;
		this.fieldName = fieldName;
		this.startNum = startNum;
		this.lengthSybol = lengthSybol;
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
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getLengthSybol() {
		return lengthSybol;
	}
	public void setLengthSybol(int lengthSybol) {
		this.lengthSybol = lengthSybol;
	}
	
      
}
