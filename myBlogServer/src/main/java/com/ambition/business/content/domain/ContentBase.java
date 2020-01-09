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
public class ContentBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String userName;

    private Long contentId;

    /**
     * 创建或更新时间
     */
    private Date createTime;

    private String title;

    private String brief;

    /**
     * 浏览数
     */
    private Long views;

    /**
     * 点赞数
     */
    private Long zans;

    /**
     * 图片地址
     */
    private String contentImgUrl;

    /**
     * 内容分类id
     */
    private Long contentCategoryId;

    /**
     * 内容分类名称
     */
    private String contentCategoryName;

    private String userImgUrl;

    /**
     * 收藏数
     */
    private Long stores;

    private String author;


}
