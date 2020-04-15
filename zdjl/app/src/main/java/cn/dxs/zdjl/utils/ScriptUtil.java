package cn.dxs.zdjl.utils;

/**
 * 脚本工具类
 *
 * @author lijian
 * @date 2020-04-09 14:10
 */
public class ScriptUtil {

    /**
     * 获取一个修正后的重复次数
     *
     * @param repeatCount
     * @return
     */
    public static int getRepeatCount(int repeatCount) {
        // 修正 repeatCount 值
        if (repeatCount == -1) {// 未设置次数时
            repeatCount = 1;
        } else if (repeatCount == 0) {// 无限次
            repeatCount = Integer.MAX_VALUE;
        } else if (repeatCount > Integer.MAX_VALUE) {// 用户设置的值过大时修正
            repeatCount = Integer.MAX_VALUE;
        }
        return repeatCount;
    }
}
