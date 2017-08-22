
package com.beathive.dao.hibernate;

import java.util.List;

import com.beathive.dao.hibernate.BaseDaoHibernate;
import com.beathive.model.Comment;
import com.beathive.dao.CommentDao;

import org.springframework.orm.ObjectRetrievalFailureException;

public class CommentDaoHibernate extends BaseDaoHibernate implements CommentDao {

    /**
     * @see com.beathive.dao.CommentDao#getComments(com.beathive.model.Comment)
     */
    public List getComments(final Comment comment) {
        return getHibernateTemplate().find("from Comment");
    }

    /**
     * @see com.beathive.dao.CommentDao#getComment(Long id)
     */
    public Comment getComment(final Long id) {
        Comment comment = (Comment) getHibernateTemplate().get(Comment.class, id);
        if (comment == null) {
            log.warn("uh oh, comment with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(Comment.class, id);
        }

        return comment;
    }

    /**
     * @see com.beathive.dao.CommentDao#saveComment(Comment comment)
     */    
    public void saveComment(final Comment comment) {
        getHibernateTemplate().saveOrUpdate(comment);
    }

    /**
     * @see com.beathive.dao.CommentDao#removeComment(Long id)
     */
    public void removeComment(final Long id) {
        getHibernateTemplate().delete(getComment(id));
    }
}
