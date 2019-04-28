package com.tenquare.article.dao;

import com.tenquare.article.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 评论dao
 */
public interface CommentDao extends JpaRepository<Comment,String>, JpaSpecificationExecutor<Comment> {
    public List<Comment> findByArticleid(String articleid);
}