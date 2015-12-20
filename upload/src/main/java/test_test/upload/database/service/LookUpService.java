package test_test.upload.database.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test_test.upload.database.dao.ILookUpDao;

@Service
public class LookUpService implements ILookUpService {
 @Autowired 
 ILookUpDao lookUPDao;
	@Override
	public String seletGetFieldbyID(Map<String, String> mapLooKUplValue) {
		return lookUPDao.seletGetFieldbyID(mapLooKUplValue);
	}

}
