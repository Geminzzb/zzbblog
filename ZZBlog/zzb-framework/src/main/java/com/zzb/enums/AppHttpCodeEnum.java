package com.zzb.enums;

/**
 * @author: zahngzongbin
 * @date: 2023/3/8 - 15:56
 * @mail: 2218722664@qq.com
 * @info:
 */
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    CONTENT_NOT_NULL(506,"评论内容不能为空"),
    FILE_TYPE_ERROR(507,"文件类型性错误，请上传PNG文件"),
    USERNAME_NOT_NULL(508,"用户名不能为空"),
    NICKNAME_NOT_NULL(509,"昵称不能为空"),
    PASSWORD_NOT_NULL(510,"密码不能为空"),
    EMAIL_NOT_NULL(511,"邮箱不能为空"),
    NICKNAME_EXIST(512,"昵称已存在"),
    TAG_IS_EXIST(513,"标签已存在"),
    TAG_IS_NOEXIST(514, "该标签不存在"),
    CONTENT_IS_BLANK(515, "内容不能为空"),
    DELETE_ARTICLE_FAIL(516, "删除文章失败"),
    ADD_MENU_FAIL(517, "当前菜单已存在"),
    UPDATE_MENU_FAIL(517, "不能让修改对象成为自己的父类"),
    DELETE_MENU_REFUSE(518, "当前菜单存在子菜单，不允许删除"),
    ROLE_INFO_EXIST(519, "角色信息已经存在"),
    ROLEKEY_INFO_EXIST(520, "角色权限信息已经存在"),
    DELETE_USER_REFUSE(521, "不能删除当前已经登录的用户"),


    LOGIN_ERROR(505,"用户名或密码错误");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
