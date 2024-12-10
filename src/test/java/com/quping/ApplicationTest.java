package com.quping;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quping.dao.mapper.CommentMapper;
import com.quping.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;

@SpringBootTest
public class ApplicationTest {
    @Autowired
    private CommentMapper commentMapper;
    @Test
    void test(){
        List<Comment> comments = commentMapper.selectList(null);
        comments.forEach(System.out::println);

        Comment comment = new Comment();
        comment.setContent("23213123");
        comment.setLikeCount(2003);
        commentMapper.insert(comment);
        System.out.println(comment.getId());
    }
    @Test
    void testPage(){
        Page<Comment> page = new Page<>(1,1);
        commentMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
    }

    @Test
    void testDelete(){
        Comment comment = new Comment();
        comment.setId(1L);
        commentMapper.deleteById(comment);
    }
}
