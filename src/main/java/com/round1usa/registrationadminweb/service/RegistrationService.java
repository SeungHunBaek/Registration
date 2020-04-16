package com.round1usa.registrationadminweb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.round1usa.registrationadminweb.control.RegistrationAdminControl;
import com.round1usa.registrationadminweb.dao.RegistrationDao;
import com.round1usa.registrationadminweb.dto.StoreDto;

public class RegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationAdminControl.class);

	public boolean updateStoreService(RegistrationDao dao, String org_store_id, String update_store_id, String update_store_name, String update_store_password) {
		StoreDto store = dao.checkStoreId(org_store_id);
		logger.info("store EmptyCheck : " + store);
		boolean result = false;
		if(store!=null){
			int resultUpdate = dao.updateStoreInfo(org_store_id, update_store_id, update_store_name, update_store_password);
			if(resultUpdate!=0){
				result = true;
			}
		}
		return result;
	}

	public boolean deleteStoreService(RegistrationDao dao, String store_id) {
		StoreDto store = dao.checkStoreId(store_id);
		logger.info("store EmptyCheck : " + store);
		boolean result = false;
		if(store!=null){
			int resultDelete = dao.deleteStoreInfo(store_id);
			if(resultDelete!=0){
				result = true;
			}
		}
		return result;
	}

	public boolean insertStoreService(RegistrationDao dao, String insert_store_id, String insert_store_name, String insert_store_password) {
		StoreDto store = dao.checkStoreId(insert_store_id);
		logger.info("store EmptyCheck : " + store);
		boolean result = false;
		if(store==null){
			int resultInsert = dao.insertStoreInfo(insert_store_id, insert_store_name, insert_store_password);
			if(resultInsert!=0){
				result = true;
			}
		}
		return result;
	}

}
