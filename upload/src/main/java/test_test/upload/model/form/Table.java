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
	 *  true where table is update 
	 */
	 private boolean update;
	
	/**
	 * empty constructor
	 */
	public Table() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param nameTable
	 * @param listField
	 * @param root
	 * @param update
	 */
	public Table(String nameTable, List<Field> listField, boolean root, boolean update) {
		super();
		this.nameTable = nameTable;
		this.listField = listField;
		this.root = root;
		this.update = update;
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
 * 
 * @param nameTable
 */

	public Table(String nameTable) {
		super();
		this.nameTable = nameTable;
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
	public void setListFeald(List<Field> listField) {
		this.listField = listField;
	}
	
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


	public boolean isUpdate() {
		return update;
	}


	public void setUpdate(boolean update) {
		this.update = update;
	}


	public void setListField(List<Field> listField) {
		this.listField = listField;
	}
 
}
