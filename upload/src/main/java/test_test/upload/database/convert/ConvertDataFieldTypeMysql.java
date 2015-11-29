package test_test.upload.database.convert;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import test_test.upload.model.form.Table;
import test_test.upload.parseexcel.ParseExcelFile;


@Component
@Qualifier("ConvertDataFieldTypeMysql")
public class ConvertDataFieldTypeMysql implements IConvertDataFieldType {
	private static final Logger logger1 = Logger.getLogger(ConvertDataFieldTypeMysql.class);
	@Override
	public void convertDataTypeInBase(Table table, JdbcTemplate jdbcTemplate) {
		DataSource data = jdbcTemplate.getDataSource();
		try {
			DatabaseMetaData metaData = data.getConnection().getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger1.error(e);
		}
	}

}
