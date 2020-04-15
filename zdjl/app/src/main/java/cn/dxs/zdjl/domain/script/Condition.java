package cn.dxs.zdjl.domain.script;

/**
 * 运行条件
 *
 * @author lijian
 * @date 2020-04-06 21:50
 */
public class Condition {
    private String type;// "textFount",// 条件类型
    private boolean runWhenFalse;// true // 条件反向（逻辑非）
    private boolean repeatWhenFalse;// true// 重复检查直到成功
    private int repeatWhenFalseLimitTimes;// 10// 重复上限
    private int repeatWhenFalseRepeatDelay; // 500// 重复间隔（单位毫秒）
    private boolean checkBeforeDelay;// true// 条件在等待延迟前检查
    private String text; // "你好"// 出现文字
    private String limitArea;// "18.425926% 40.315426% 37.916187% 44.65254%",// 限制区域
    private String ocrMode;// "local"// 识图模式

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRunWhenFalse() {
        return runWhenFalse;
    }

    public void setRunWhenFalse(boolean runWhenFalse) {
        this.runWhenFalse = runWhenFalse;
    }

    public boolean isRepeatWhenFalse() {
        return repeatWhenFalse;
    }

    public void setRepeatWhenFalse(boolean repeatWhenFalse) {
        this.repeatWhenFalse = repeatWhenFalse;
    }

    public int getRepeatWhenFalseLimitTimes() {
        return repeatWhenFalseLimitTimes;
    }

    public void setRepeatWhenFalseLimitTimes(int repeatWhenFalseLimitTimes) {
        this.repeatWhenFalseLimitTimes = repeatWhenFalseLimitTimes;
    }

    public int getRepeatWhenFalseRepeatDelay() {
        return repeatWhenFalseRepeatDelay;
    }

    public void setRepeatWhenFalseRepeatDelay(int repeatWhenFalseRepeatDelay) {
        this.repeatWhenFalseRepeatDelay = repeatWhenFalseRepeatDelay;
    }

    public boolean isCheckBeforeDelay() {
        return checkBeforeDelay;
    }

    public void setCheckBeforeDelay(boolean checkBeforeDelay) {
        this.checkBeforeDelay = checkBeforeDelay;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLimitArea() {
        return limitArea;
    }

    public void setLimitArea(String limitArea) {
        this.limitArea = limitArea;
    }

    public String getOcrMode() {
        return ocrMode;
    }

    public void setOcrMode(String ocrMode) {
        this.ocrMode = ocrMode;
    }
}
