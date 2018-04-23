package com.quarkdata.data.model.common;

import java.util.HashMap;
import java.util.Map;




public class Constants {
	
	public static String SYSTEM="SYSTEM";
	public static String SUCCESS="SUCCESS";
	public static String FAIL="FAIL";

	public static final String ONEIOT_REDIS_PREFIX = "oi";
	public static final String ONEIOT_REDIS_DELIMITER = ":";
	public static final String ONEIOT_REDIS_USER_INFO_VO = "userInfoVO";
	public static final String ONEIOT_REDIS_TEN = "ten";
	public static final String ONEIOT_REDIS_LATEST_STATE = "latestState";

	public static final String ONEIOT_REDIS_TOKEN = "token";
	public static final String ONEIOT_REDIS_SECRET_ID = "secretId";

	public static final String ONEIOT_REDIS_DEVICE_MODEL = "deviceModel";
	public static final String ONEIOT_REDIS_DEVICE = "device";
	
	public static final String ONEIOT_REDIS_TRIGGERS_INFO = "triggersInfo";
	public static final String ONEIOT_REDIS_TRIGGER_TIMES = "triggerTimes";

	public static final String ONEIOT_DEVICE_DATA_PREFIX_PRODUCT_ID = "product_id";
	public static final String ONEIOT_DEVICE_DATA_PREFIX_DEVICE_ID = "device_id";
	public static final String ONEIOT_DEVICE_DATA_PREFIX_CLOUD_TIME = "cloud_time";

	public static final String ONEIOT_DEVICE_DATA_KAFKA_TOPIC = "device_data";

	//触发器
	public static final Byte DEVICE_TRIGGER_OBJECT_OBJECT_TYPE_DEVICE = 0;//类型0-设备,1-设备组,2-全部设备
	public static final Byte DEVICE_TRIGGER_OBJECT_OBJECT_TYPE_DEVICEGROUP = 1;
	public static final Byte DEVICE_TRIGGER_OBJECT_OBJECT_TYPE_ALLDEVICE = 2;
	public static final String TRIGGER_CONDITION_EXPRESS_PREFIX = "${";
	public static final String TRIGGER_CONDITION_EXPRESS_SUFFIX = "}";
	public static final Byte TRIGGER_RESULT_RESULT_TYPE_EMAIL = 0;//结果类型(0:邮件通知,1:url)
	public static final Byte TRIGGER_RESULT_RESULT_TYPE_URL = 1;
	public static final Byte TRIGGER_CONDITION_CONNECT_TYPE_0 = 0;//连接方式(0:与,1:或)
	public static final Byte TRIGGER_CONDITION_CONNECT_TYPE_1 = 1;

	public static final String ONEIOT_REDIS_VALIDATE_CODE = "validateCode";

	public static final String ONEIOT_REDIS_RESET_EMAIL_LINK = "resetEmailLink";

	//触发条件运算符
	public static final Map<Byte,String> TRIGGER_CONDITION_OPERATOR_MAP = new HashMap<Byte,String>();
	static{
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 0, "=");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 1, ">");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 2, "<");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 3, ">=");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 4, "<=");
		TRIGGER_CONDITION_OPERATOR_MAP.put((byte) 5, "<>");
	}
}
