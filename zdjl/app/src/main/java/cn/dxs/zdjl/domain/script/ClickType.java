package cn.dxs.zdjl.domain.script;

/**
 * 点击类型
 *
 * @author lijian
 * @date 2020-04-06 21:08
 */
public class ClickType extends BaseScriptType {

    private String type = "点击";// 动作类型
    private Point posData;// 点击位置
    private String duration;// 按下时间（一般大于600毫秒时为长按动作。默认100毫秒。可以填写100-300这样的格式代表按下时间是范围内的随机值）
    private String delay;// 运行延时（运行动作前的等待时间，如果要设置范围内随机则填写1-3这样的格式。不设置则以全局设置里的延迟为准(默认1秒)）
    private int delayUnit;// 运行延时的时间单位（1：秒，2：毫秒，3：分钟）
    private int repeatCount;// 重复次数（运行这个动作的重复次数，默认为1,0代表无限次）
    private Condition condition;// 运行条件（动作的运行条件，满足该条件动作才能运行）
    private String desc;// 动作描述（动作的描述，设置后会展示列表里）
    private ScriptCallbacks scriptCallbacks;// 运行回调


}
