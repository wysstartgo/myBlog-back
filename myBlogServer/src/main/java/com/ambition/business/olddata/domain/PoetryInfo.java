package com.ambition.business.olddata.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuys
 * @since 2019-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PoetryInfo对象", description="")
public class PoetryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer authorId;

    private String title;

    private String content;

    private String yunlvRule;

    private String author;

    private String simpleContent;

    private Long viewCount;

    private Long zanCount;

    private Long commentCount;

    private Long storeCount;

    private String explanation;

    private String dynasty;


}
