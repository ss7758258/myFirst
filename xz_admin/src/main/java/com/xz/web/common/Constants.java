package com.xz.web.common;

public class Constants {
	/** 机构编码分割符 **/
	public static final String ORG_SEPARATOR = "-"; 
	/** 叶子节点 **/
	public static final int LEAF = 1; 
	/** 不是叶子节点 **/
	public static final int NOT_LEAF = 0; 
	/** 设备在线加1 **/
	public static final int EMT_ONLINE 	= 1;
	/** 设备不在线-1 **/
	public static final int EMT_OFFLINE = -1;
	/** 成功 */
	public static final int GROUP_SUCCESS   = 0; 
	/** 处理失败 */
	public static final int GROUP_ERROR_001 = 1; 
	/** 还存在子节点 */
    public static final int GROUP_ERROR_002 = 2;
    /** 还存在子节点 */
	public static final String ROOT_LEVEL = "0";
	/** 查询所有设备 **/
	public static final int  EMT_ALL  = -1;
	/** 设备在线 **/
	public static final int  EMT_ACTIVE  = 1;
	/** 设备不在线 **/
	public static final int  EMT_INACTIVE = 0;
	public static final String CERT_TYPE_FOR_IDCARD = "1";

}
