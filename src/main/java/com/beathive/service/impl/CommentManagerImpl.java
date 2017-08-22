
package com.beathive.service.impl;

import java.util.List;

import com.beathive.service.impl.BaseManager;
import com.beathive.model.Comment;
import com.beathive.dao.CommentDao;
import com.beathive.service.CommentManager;

public class CommentManagerImpl extends BaseManager implements CommentManager {
    private CommentDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao
     */
    public void setCommentDao(CommentDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.beathive.service.CommentManager#getComments(com.beathive.model.Comment)
     */
    public List getComments(final Comment comment) {
        return dao.getComments(comment);
    }

    /**
     * @see com.beathive.service.CommentManager#getComment(String id)
     */
    public Comment getComment(final String id) {
        return dao.getComment(new Long(id));
    }

    /**
     * @see com.beathive.service.CommentManager#saveComment(Comment comment)
     */
    public void saveComment(Comment comment) {
        dao.saveComment(comment);
    }

    /**
     * @see com.beathive.service.CommentManager#removeComment(String id)
     */
    public void removeComment(final String id) {
        dao.removeComment(new Long(id));
    }
}
