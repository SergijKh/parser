package test_test.upload.database.service;
import org.springframework.stereotype.Service;

/*
 * service Table
 */
import test_test.upload.model.form.Table;
/**
 * 
 * @author Sergey
 *
 */

public interface ITableService {
	/**
	 * add one row  table in  database
	 * @param table
	 * @return
	 */
	public int addoneRowTable(Table table);
	/**
	 * select one row insert last
	 */
	public void selectoneRow (Table table);
	/**
	 * return field primary key
	 * @param table
	 * @return
	 */
	public String getNameFieldPrimaryKay(Table table); 
	/**
	 * update one row  table in  database 
	 * @param table
	 */
	public void  updateOneRowTable(Table table);
}     
