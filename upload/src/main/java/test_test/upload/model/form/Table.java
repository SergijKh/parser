package test_test.upload.model.form;

import java.util.List;

/**
 * 
 * @author Sergey
 *class describe  table which need modify
 * root table  when table have all foreign key anther table
 */
public class Table {
	/**
	 * @return the root
	 */
	public boolean isRoot() {
		return root;
	}


	/**
	 * @param root the root to set
	 */
	public void setRoot(boolean root) {
		this.root = root;
	}
	/**
	 * names table
	 */
	private String nameTable;
	/**
	 * list field table 
	 */
	private List<Field> listField;
	/**
	 * table root  if root true  and nod root if false 
	 */
	private  boolean root;

	
	/**
	 * empty constructor
	 */
	public Table() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @param nameTable name table 
	 * @param listField  list field 
	 */
	public Table(String nameTable, List<Field> listField) {
		this.nameTable = nameTable;
		this.listField = listField;
	}


	/**
	 * @param nameTable name  table 
	 * @param listField list  field
	 * @param root root table or not root 
	 */
	public Table(String nameTable, List<Field> listField, boolean root) {
		this.nameTable = nameTable;
		this.listField = listField;
		this.root = root;
	}


	/**
	 * @return the nameTable
	 */
	public String getNameTable() {
		return nameTable;
	}
	/**
	 * @param nameTable the nameTable to set
	 */
	public void setNameTable(String nameTable) {
		this.nameTable = nameTable;
	}
	/**
	 * @return the listField
	 */
	public List<Field> getListField() {
		return listField;
	}
	/**
	 * @param listField the listField to set
	 */
	public void setListField(List<Field> listField) {
		this.listField = listField;
	}
 
}