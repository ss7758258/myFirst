package com.xz.framework.utils.id;

import java.util.UUID;

/**
 * ID生成工具
 */
public class IdUtil {
    private static int mechineId = 1;

    public IdUtil() {
    }
    /**
     * 获取UUID
     *
     * @return
     */
    public static String getDefaultUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取LongID
     *
     * @return
     */
    public static String getLongIdByUuid() {
        int uuidHashCode = getDefaultUuid().hashCode();
        if (uuidHashCode < 0)
            uuidHashCode = -uuidHashCode;
        return (new StringBuilder(String.valueOf(mechineId)))
                .append(String.format("%015d", new Object[]{Integer.valueOf(uuidHashCode)})).toString();
    }
}