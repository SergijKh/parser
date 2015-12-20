package test_test.upload.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test_test.upload.database.dao.TableDao;
import test_test.upload.model.form.Table;
@Service
public class TableService implements ITableService{
@Autowired 
TableDao tableDao;
	@Override
	public int addoneRowTable(Table table) {
	
		return tableDao.addoneRowTable(table);
	}

	@Override
	public void selectoneRow(Table table) {
		tableDao.selectOneLastRow(table);
	}

	@Override
	public String getNameFieldPrimaryKay(Table table) {
		return tableDao.getNameFieldPrimaryKay(table);
	}

}
