
package io.cug.common.validator;

import io.cug.common.exception.cugException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new cugException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new cugException(message);
        }
    }
}
