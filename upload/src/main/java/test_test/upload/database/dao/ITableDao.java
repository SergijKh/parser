package test_test.upload.database.dao;

import test_test.upload.model.form.Table;

public interface ITableDao {

	public int addoneRowTable(Table table);
	public Table selectoneRow (int id);
	
}
