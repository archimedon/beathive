
package com.beathive.service;

import java.util.List;

import com.beathive.service.Manager;
import com.beathive.model.Comment;
import com.beathive.dao.CommentDao;

public interface CommentManager extends Manager {
    /**
     * Retrieves all of the comments
     */
    public List getComments(Comment comment);

    /**
     * Gets comment's information based on id.
     * @param id the comment's id
     * @return comment populated comment object
     */
    public Comment getComment(final String id);

    /**
     * Saves a comment's information
     * @param comment the object to be saved
     */
    public void saveComment(Comment comment);

    /**
     * Removes a comment from the database by id
     * @param id the comment's id
     */
    public void removeComment(final String id);
}

