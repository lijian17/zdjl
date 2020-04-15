package cn.dxs.zdjl.domain.script;

/**
 * 坐标
 *
 * @author lijian
 * @date 2020-04-06 21:12
 */
public class Point {
    private String type = "location";
    private String x;// x坐标（零点坐标为屏幕左上方。单位％以屏幕长宽为基准，单位dp是逻辑像素）
    private String y;// y坐标
    private String xOffset;// x偏移量（找到的位置会与这个值求和后，再被使用。可以填写一个数值范围(如：1-2)，会取范围内随机值）
    private String yOffset;// y偏移量

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getxOffset() {
        return xOffset;
    }

    public void setxOffset(String xOffset) {
        this.xOffset = xOffset;
    }

    public String getyOffset() {
        return yOffset;
    }

    public void setyOffset(String yOffset) {
        this.yOffset = yOffset;
    }
}
