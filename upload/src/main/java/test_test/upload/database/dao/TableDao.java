package test_test.upload.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import test_test.upload.database.convert.ConvertDataFieldTypeMysql;
import test_test.upload.database.convert.IConvertDataFieldType;
import test_test.upload.model.form.Table;
@Repository
public class TableDao implements ITableDao {
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
@Qualifier("ConvertDataFieldTypeMysql")
IConvertDataFieldType convert;
	@Override
	public int addoneRowTable(Table table) {
		
	String sql = "insert into "+ table.getNameTable() + "(";
	StringBuilder stringSqNameField  = new StringBuilder();
	StringBuilder stringSqValue  = new StringBuilder("values(");
	StringBuilder stringSql  = new StringBuilder();
	 for (int i = 0 ; i < table.getListField().size(); i ++){
		 stringSqNameField.append(table.getListField().get(i)+",");
		 stringSql.append("?, ");
		 if (i==table.getListField().size()-1){
			 stringSqNameField.append(table.getListField().get(i)+")");
			 stringSql.append("?);");
		 }
	 }
	 sql  = sql +stringSqNameField.toString()+stringSql.toString();
	Object [] sd = {};
	jdbcTemplate.update(sql , sd);
		//jdbcTemplate.;
		return 0;
	}

	@Override
	public Table selectoneRow(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public IConvertDataFieldType getConvertMysql() {
		return convert;
	}

	public void setConvertMysql(IConvertDataFieldType convertMysql) {
		this.convert = convertMysql;
	}

}
