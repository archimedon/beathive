package com.beathive.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.beathive.Constants;

/**
 * Trackpack
 * @struts.form include-all="true" extends="SoundClipForm"
 * @hibernate.subclass discriminator-value="Trackpack"
 */
public class Trackpack extends SoundClip {
	
	
	private static final long serialVersionUID = 5167570807297040795L;
	//~ Instance fields
	protected List loops;
	protected String type="Trackpack";
	protected Integer loopsExpected = new Integer(Constants.MIN_LOOP_IN_PACK);
	
	
	public Trackpack(){
		super();
	}

	/**
	 * Returns the clip's loops.
	 * 
	 * @return List
	 * 
	 * hibernate.list table="pack_loop" name="loops" inverse="false" lazy="true" cascade="none"
	 * hibernate.list-index column="idx"
	 * 
	 * @hibernate.bag name="loops" inverse="false" lazy="true" cascade="none"
	 * @hibernate.collection-key column="trackpackId"
	 * @hibernate.collection-one-to-many class="com.beathive.model.Loop" Xcolumn="loopId"
	 *  
	 */
	public List getLoops() {
		return loops;
	}

	/**
	 * Sets the loops.
	 * @param loops The loops to set
	 */
	public void setLoops(List loops) {
		this.loops = loops;
	}
	/**new Integer(Math.max((loops!=null?loops.size():0), Constants.MIN_LOOP_IN_PACK));
	 * @return Returns the loopsExpected.
	 * @hibernate.property
	 */
	public Integer getLoopsExpected() {
		//TODO really just a strong note that I am funging values on the model layer
			int tmp = Math.max((loops!=null?loops.size():0), Constants.MIN_LOOP_IN_PACK);
			if (loopsExpected == null || loopsExpected.intValue() < tmp){
				loopsExpected = new Integer(tmp);
			}
		return loopsExpected;
	}
	/**
	 * @param loopsExpected The loopsExpected to set.
	 */
	public void setLoopsExpected(Integer expected) {
		this.loopsExpected = expected;
	}

	public String toString(){
		return super.stringBuilder().append("loops",this.loops).append("type", this.type).toString();
	}

	public boolean isReady(){
		if (loops==null || loops.isEmpty() || loops.size() < Constants.MIN_LOOP_IN_PACK){
			return false;
		}
		java.util.Iterator it = loops.iterator();
		while(it.hasNext()){
			if (! ((com.beathive.model.Loop)it.next()).isReady()){return false;}
		}
		int expnum = loopsExpected.intValue();
		
		return ( (super.isReady()) 	&& (loops.size() >= expnum)	);
	}
}