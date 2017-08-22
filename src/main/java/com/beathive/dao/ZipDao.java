package com.beathive.dao;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.beathive.model.Zip;

public interface ZipDao extends Dao {

	public void removeZip(Long id) throws ObjectRetrievalFailureException;;

	public List getZips(Zip zipObject);

	public void saveZip(Zip zipObject);

	public Zip getZip(Long id)  throws ObjectRetrievalFailureException;

	public List getUserZips(String username);

	public Zip getZip(String username, String zipname);
	
	public Zip getZipByKey(String key);
	public int incrementZipAccess(Long id) throws ObjectRetrievalFailureException;

}
