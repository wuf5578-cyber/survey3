package io.cug.common.utils;

/**
 * 对象工具类
 * @author
 * @date 2022/11/4 9:49
 */
public class ObjectUtil {

    /**
     * @Title: isEmpty
     * @Description: 判断对象是否为空
     * @param obj
     * @return
     * @return Integer
     */
    public static boolean isEmpty(Object obj) {
        return null == obj || "".equals(obj);
    }

}
