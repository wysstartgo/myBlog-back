package com.ambition.business.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 运营后台用户表
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否有效  1有效  2无效
     */
    private String deleteStatus;

    /**
     * 所属组织id
     */
    private Long groupId;

    /**
     * 所属组织名称
     */
    private String groupName;

    private Long createUserId;

    private String createUserName;

    private Long updateUserId;

    private String updateUserName;

    /**
     * 1是男0是女
     */
    private Integer gender;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 专业领域
     */
    private String specialArea;

    /**
     * 职称
     */
    private String professionalTile;

    /**
     * 二维码
     */
    private String qrCode;

    private String userImgUrl;

    /**
     * 简介
     */
    private String description;


}
