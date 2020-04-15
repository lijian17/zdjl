package cn.dxs.zdjl.domain.script;

/**
 * 脚本描述（脚本文件的第一行内容）
 *
 * @author lijian
 * @date 2020-03-26 13:54
 */
public class ScriptDes {
    private int repeatCount = -1;// 重复次数（默认为1，0代表无限次）
    private boolean pauseOnFail = true;// true：有动作失败立即暂停
    private int count;// 脚本动作的数量
    private String description;// 脚本描述
    private int minVerCode;// 最低版本号
    private String minVerName;// 最低版本名
    private int screenWidth;// 分辨率宽
    private int screenHeight;// 分辨率高
    private float screenDpi;// 像素密度

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public boolean isPauseOnFail() {
        return pauseOnFail;
    }

    public void setPauseOnFail(boolean pauseOnFail) {
        this.pauseOnFail = pauseOnFail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinVerCode() {
        return minVerCode;
    }

    public void setMinVerCode(int minVerCode) {
        this.minVerCode = minVerCode;
    }

    public String getMinVerName() {
        return minVerName;
    }

    public void setMinVerName(String minVerName) {
        this.minVerName = minVerName;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getScreenDpi() {
        return screenDpi;
    }

    public void setScreenDpi(float screenDpi) {
        this.screenDpi = screenDpi;
    }
}
