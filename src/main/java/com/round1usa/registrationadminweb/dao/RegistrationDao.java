package com.round1usa.registrationadminweb.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

import com.round1usa.registrationadminweb.dto.Agreement;
import com.round1usa.registrationadminweb.dto.StoreDto;

public interface RegistrationDao {

	public ArrayList<StoreDto> viewStoreList();  //get Userinformation
	public BigInteger agreementStoreCount(Map<String, Object> conditions);
	public ArrayList<Agreement> agreementStoreList(Map<String, Object> conditions, BigInteger start, BigInteger length, String store_id);

	public StoreDto checkStoreId(String org_store_id);
	public int updateStoreInfo(String org_store_id, String update_store_id, String update_store_name, String update_store_password);
	public int deleteStoreInfo(String store_id);
	public int insertStoreInfo(String insert_store_id, String insert_store_name, String insert_store_password);

}
