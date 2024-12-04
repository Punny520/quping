package com.quping.entry;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Boolean deleted;

}
