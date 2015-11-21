package test_test.upload.model.parse;
/**
 * 
 * @author Sergey
 * class describe  Source part xml fail 
 */
public class Source {
	/**
	 * id source
	 */
	private String id;
	/**
 	 * format parse of the  in file 
	 */
	private String inputFormat;
	/**
	 * how many lines should  be skipped in file 
	 */
	private int headerRows;
	/**
	 * which row field  names
	 */
	private int fieldNameRowNumer;
	/**
	 *  format field in file
	 */
	private String field_names_format;
	/**
	 * does matter update or insert data 
	 */
	private char update_mode_field_name;
	/**
	 * the path to a file that contains the data to insert and update
	 */
	private String pathInputFile;
	/**
	 * format parse of the  out  file 
	 */
	 
	private String autputFormat;
 /**
 * 
 */
	private String error_column;
	/**
	 * the path to a file where write correct or error insert or update  
	 */
	private String pathOutFile ;
	
	
	
	/**
	 * empty constructor
	 */
	public Source() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param fieldNameRowNumer
	 * @param field_names_format
	 * @param update_mode_field_name
	 * @param pathInputFile
	 * @param autputFormat
	 * @param error_column
	 * @param pathOutFile
	 * @param inputFormat
	 * @param headerRows
	 */
	public Source(int fieldNameRowNumer, String field_names_format,
			char update_mode_field_name, String pathInputFile,
			String autputFormat, String error_column, String pathOutFile,
			String inputFormat, int headerRows) {
		this.fieldNameRowNumer = fieldNameRowNumer;
		this.field_names_format = field_names_format;
		this.update_mode_field_name = update_mode_field_name;
		this.pathInputFile = pathInputFile;
		this.autputFormat = autputFormat;
		this.error_column = error_column;
		this.pathOutFile = pathOutFile;
		this.inputFormat = inputFormat;
		this.headerRows = headerRows;
	}
	
	
	/**
	 * @param id
	 * @param inputFormat
	 * @param headerRows
	 * @param fieldNameRowNumer
	 * @param field_names_format
	 * @param update_mode_field_name
	 * @param pathInputFile
	 * @param autputFormat
	 * @param error_column
	 * @param pathOutFile
	 */
	public Source(String id, String inputFormat, int headerRows,
			int fieldNameRowNumer, String field_names_format,
			char update_mode_field_name, String pathInputFile,
			String autputFormat, String error_column, String pathOutFile) {
		this.id = id;
		this.inputFormat = inputFormat;
		this.headerRows = headerRows;
		this.fieldNameRowNumer = fieldNameRowNumer;
		this.field_names_format = field_names_format;
		this.update_mode_field_name = update_mode_field_name;
		this.pathInputFile = pathInputFile;
		this.autputFormat = autputFormat;
		this.error_column = error_column;
		this.pathOutFile = pathOutFile;
	}
	/**
	 * @return the inputFormat
	 */
	public String getInputFormat() {
		return inputFormat;
	}
	/**
	 * @param inputFormat the inputFormat to set
	 */
	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}
	/**
	 * @return the headerRows
	 */
	public int getHeaderRows() {
		return headerRows;
	}
	/**
	 * @param headerRows the headerRows to set
	 */
	public void setHeaderRows(int headerRows) {
		this.headerRows = headerRows;
	}
	/**
	 * @return the fieldNameRowNumer
	 */
	public int getFieldNameRowNumer() {
		return fieldNameRowNumer;
	}
	/**
	 * @param fieldNameRowNumer the fieldNameRowNumer to set
	 */
	public void setFieldNameRowNumer(int fieldNameRowNumer) {
		this.fieldNameRowNumer = fieldNameRowNumer;
	}
	/**
	 * @return the field_names_format
	 */
	public String getField_names_format() {
		return field_names_format;
	}
	/**
	 * @param field_names_format the field_names_format to set
	 */
	public void setField_names_format(String field_names_format) {
		this.field_names_format = field_names_format;
	}
	/**
	 * @return the update_mode_field_name
	 */
	public char getUpdate_mode_field_name() {
		return update_mode_field_name;
	}
	/**
	 * @param update_mode_field_name the update_mode_field_name to set
	 */
	public void setUpdate_mode_field_name(char update_mode_field_name) {
		this.update_mode_field_name = update_mode_field_name;
	}
	/**
	 * @return the pathInputFile
	 */
	public String getPathInputFile() {
		return pathInputFile;
	}
	/**
	 * @param pathInputFile the pathInputFile to set
	 */
	public void setPathInputFile(String pathInputFile) {
		this.pathInputFile = pathInputFile;
	}
	/**
	 * @return the autputFormat
	 */
	public String getAutputFormat() {
		return autputFormat;
	}
	/**
	 * @param autputFormat the autputFormat to set
	 */
	public void setAutputFormat(String autputFormat) {
		this.autputFormat = autputFormat;
	}
	/**
	 * @return the error_column
	 */
	public String getError_column() {
		return error_column;
	}
	/**
	 * @param error_column the error_column to set
	 */
	public void setError_column(String error_column) {
		this.error_column = error_column;
	}
	/**
	 * @return the pathOutFile
	 */
	public String getPathOutFile() {
		return pathOutFile;
	}
	/**
	 * @param pathOutFile the pathOutFile to set
	 */
	public void setPathOutFile(String pathOutFile) {
		this.pathOutFile = pathOutFile;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((autputFormat == null) ? 0 : autputFormat.hashCode());
		result = prime * result
				+ ((error_column == null) ? 0 : error_column.hashCode());
		result = prime * result + fieldNameRowNumer;
		result = prime
				* result
				+ ((field_names_format == null) ? 0 : field_names_format
						.hashCode());
		result = prime * result + headerRows;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((inputFormat == null) ? 0 : inputFormat.hashCode());
		result = prime * result
				+ ((pathInputFile == null) ? 0 : pathInputFile.hashCode());
		result = prime * result
				+ ((pathOutFile == null) ? 0 : pathOutFile.hashCode());
		result = prime * result + update_mode_field_name;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Source other = (Source) obj;
		if (autputFormat == null) {
			if (other.autputFormat != null)
				return false;
		} else if (!autputFormat.equals(other.autputFormat))
			return false;
		if (error_column == null) {
			if (other.error_column != null)
				return false;
		} else if (!error_column.equals(other.error_column))
			return false;
		if (fieldNameRowNumer != other.fieldNameRowNumer)
			return false;
		if (field_names_format == null) {
			if (other.field_names_format != null)
				return false;
		} else if (!field_names_format.equals(other.field_names_format))
			return false;
		if (headerRows != other.headerRows)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inputFormat == null) {
			if (other.inputFormat != null)
				return false;
		} else if (!inputFormat.equals(other.inputFormat))
			return false;
		if (pathInputFile == null) {
			if (other.pathInputFile != null)
				return false;
		} else if (!pathInputFile.equals(other.pathInputFile))
			return false;
		if (pathOutFile == null) {
			if (other.pathOutFile != null)
				return false;
		} else if (!pathOutFile.equals(other.pathOutFile))
			return false;
		if (update_mode_field_name != other.update_mode_field_name)
			return false;
		return true;
	}
	

}
