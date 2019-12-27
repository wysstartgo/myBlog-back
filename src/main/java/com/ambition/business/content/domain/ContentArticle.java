package com.ambition.business.content.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ContentArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String userName;

    private String userImgUrl;

    /**
     * 最终展示的内容,html格式的
     */
    private String content;

    private String contentMarkdown;

    private String title;

    private String brief;

    private String contentImgUrl;

    private Date createTime;

    private Long views;

    private Long zans;

    private Long stores;

    private String author;


}
