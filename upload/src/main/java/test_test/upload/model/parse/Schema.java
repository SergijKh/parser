package test_test.upload.model.parse;

import java.util.List;

import javax.xml.validation.Validator;

import test_test.upload.model.form.Table;

/**
 * 
 * @author Sergey
 *class schema  contains  list table which necessary modify (insert or update)
 */
public class Schema {
	/**
	 * list table 
	 */
	private  List<Table> listTable;
	/**
	 * id schema
	 */
	private String  id;
	/**
	 * empty constructor
	 */
	public Schema() {
		super();
		
	}

	/**
	 * @param listTable
	 */
	public Schema(List<Table> listTable) {
		this.listTable = listTable;
	}

	/**
	 * @param listTable
	 * @param id
	 */
	public Schema(List<Table> listTable, String id) {
		this.listTable = listTable;
		this.id = id;
	}
/**
 * 
 * @param id
 */
	public Schema(String id) {
		super();
		this.id = id;
	}

	/**
	 * @return the listTable
	 */
	public List<Table> getListTable() {
		return listTable;
	}

	
	/**
	 * @param listTable the listTable to set
	 */
	public void setListTable(List<Table> listTable) {
		this.listTable = listTable;
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

	


}
