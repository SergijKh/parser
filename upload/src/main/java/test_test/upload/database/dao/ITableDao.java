package test_test.upload.database.dao;

import test_test.upload.model.form.Table;
/**
 * Dao table interface
 * @author Sergey
 *
 */
public interface ITableDao {
    /**
     * dd one row in table
     * @param table table 
     * @return
     */
	public int addoneRowTable(Table table);
	/*
	 * select one last row  
	 */
	 void selectOneLastRow(Table table);
	
}
