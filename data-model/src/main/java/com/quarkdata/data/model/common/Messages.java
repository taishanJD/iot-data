package com.quarkdata.data.model.common;

public class Messages {

    public static int SUCCESS_CODE = 0;
    public static String SUCCESS_MSG = "success";

    public static int MISSING_INPUT_CODE = 10000;
    public static String MISSING_INPUT_MSG = "missing required input params";

    public static final int ACCOUNT_LOGIN_ERROR_CODE = 10001;
    public static final String ACCOUNT_LOGIN_ERROR_MSG = "用户名或密码错误";
    public static final int ACCOUNT_INACTIVE_CODE = 10002;
    public static final String ACCOUNT_INACTIVE_MSG = "账号未激活";
    public static final int ACCOUNT_LOCKED_CODE = 10003;
    public static final String ACCOUNT_LOCKED_MSG = "账号被冻结，请联系管理员";
    public static final int ACCOUNT_STATUS_ERROR_CODE = 10004;
    public static final String ACCOUNT_STATUS_ERROR_MSG = "账号状态异常";
    public static final int ACCOUNT_NOT_FOUND_CODE = 10005;
    public static final String ACCOUNT_NOT_FOUND_MSG = "账号不存在";
    public static final int TEANT_INFO_ERROR_CODE = 10006;
    public static final String TEANT_INFO_ERROR_MSG = "查询租户信息失败";

    public static final int VERIFICATION_GET_ERROR_CODE = 11001;
    public static final String VERIFICATION_GET_ERROR_MSG = "获取验证码失败";
    public static final int VERIFICATION_CODE_ERROR_CODE = 11002;
    public static final String VERIFICATION_CODE_ERROR_MSG = "验证码错误";

    public static final int AES_DECODE_ERROR_CODE = 12001;
    public static final String AES_DECODE_ERROR_MSG = "邮件链接已失效";

    public static final int UPDATE_PASSWORD_ERROR_CODE = 13001;
    public static final String UPDATE_PASSWORD_ERROR_MSG = "修改密码失败";

    public static final int EMAIL_RECEIVER_ERROR_CODE = 14001;
    public static final String EMAIL_RECEIVER_ERROR_MSG = "邮件收件人不能为空";
    public static final int EMAIL_ACCOUNT_ERROR_CODE = 14002;
    public static final String EMAIL_ACCOUNT_ERROR_MSG = "邮件收件人邮箱格式错误";
    public static final int EMAIL_CONTENT_ERROR_CODE = 14003;
    public static final String EMAIL_CONTENT_ERROR_MSG = "邮件内容参数错误";
    public static final int EMAIL_FORMAT_ERROR_CODE = 14004;
    public static final String EMAIL_FORMAT_ERROR_MSG = "邮件格式参数错误";


    public static final int API_AUTHENTICATION_FAILED_CODE = 40001;
    public static final String API_AUTHENTICATION_FAILED_MSG = "token不存在，请重新登录";


    public static final int TOKEN_OUT_OF_TIME_CODE = 80000;
    public static final String TOKEN_OUT_OF_TIME_MSG = "身份验证过期,请重新登录";

    public static int API_ERROR_CODE = 90000;
    public static String API_ERROR_MSG = "接口繁忙，请稍后重试";

    public static final int API_AUTHEXCEPTION_CODE = 90001;
    public static final String API_AUTHEXCEPTION_MSG = "API权限校验失败";

    public static int AUTH_SECRET_ID_NOT_EXIST_CODE = 90002;
    public static String AUTH_SECRET_ID_NOT_EXIST_MSG = "用户secretId不存在";

    public static int AUTH_SECRET_KEY_NOT_EXIST_CODE = 90003;
    public static String AUTH_SECRET_KEY_NOT_EXIST_MSG = "用户secretKey不存在";

    public static final Integer JWT_ERRCODE_EXPIRE_CODE = 71000;
    public static final String JWT_ERRCODE_EXPIRE_MSG = "token已过期，请重新登录";
    public static final Integer JWT_ERRCODE_SIGN_CODE = 71001;
    public static final String JWT_ERRCODE_SIGN_MSG = "token签名无效";
    public static final Integer JWT_ERRCODE_FAIL_CODE = 71002;
    public static final String JWT_ERRCODE_FAIL_MSG = "token验证失败";
    public static final Integer JWT_ERRCODE_TOKEN_INVALID_CODE = 71003;
    public static final String JWT_ERRCODE_TOKEN_INVALID_MSG = "token无效";



}
