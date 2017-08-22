
package com.beathive.dao;

import java.util.List;

import com.beathive.dao.Dao;
import com.beathive.model.Comment;

public interface CommentDao extends Dao {

    /**
     * Retrieves all of the comments
     */
    public List getComments(Comment comment);

    /**
     * Gets comment's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if 
     * nothing is found.
     * 
     * @param id the comment's id
     * @return comment populated comment object
     */
    public Comment getComment(final Long id);

    /**
     * Saves a comment's information
     * @param comment the object to be saved
     */    
    public void saveComment(Comment comment);

    /**
     * Removes a comment from the database by id
     * @param id the comment's id
     */
    public void removeComment(final Long id);
}

