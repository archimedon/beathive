/**
 * 
 */
package com.beathive.webapp.action;

import java.util.List;

/**
 * @author ron
 *
 */
public interface ListHandler {
    

	public int getSize(); //obtains the size of the result set.
  

	public Object getCurrentElement();//obtains the current Transfer Object from the list.
  

    public List getPreviousElements(int howMany);// obtains a collection of Transfer Objects that are in the list prior to the current element.
  

    public List getNextElements(int howMany);// obtains a collection of Transfer Objects that are in the list after the current element.
  

    public void resetIndex();// resets the index to the start of the list.

}
