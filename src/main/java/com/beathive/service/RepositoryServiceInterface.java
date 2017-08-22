/*
 * Created on Nov 27, 2004
 */
package com.beathive.service;

import java.io.File;
import java.util.List;

/**
 * @author rdennison
 * 
 */
public interface RepositoryServiceInterface {
	
	public void checkFiles(List<String> pojos) throws RepositoryException;

	

	public boolean deleteItem(String refURL) throws RepositoryException;
	public boolean[] deleteItems(String[] refURLs) throws RepositoryException;
	public boolean moveItem(String orgURL , String destURL) throws RepositoryException;
/*	public String getRealPath(String refURL) throws RepositoryException;
	public String getPartialPath(ArchivePojo item) throws RepositoryException;
*/
	/**
	 * @return
	 */
	public String getHost();


}
