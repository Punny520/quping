package com.quping.entry;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
* 
* @TableName comment
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

    /**
    * 主键
    */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 内容
    */
    private String content;
    /**
    * 点赞数量
    */
    private Integer likeCount;

    /**
     * 公共字段
     */
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Boolean deleted;

}
