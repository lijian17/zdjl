package cn.dxs.zdjl.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 脚本文件信息
 *
 * @author lijian
 * @date 2020-03-24 16:34
 */
public class ScriptInfo implements Parcelable {
    private String name;// 脚本文件名
    private String size;// 脚本文件大小
    private String createTime;// 脚本文件创建时间
    private String lastModifiedTime;// 脚本文件最后修改时间
    private String path;// 脚本文件路径

    public ScriptInfo() {

    }

    protected ScriptInfo(Parcel in) {
        name = in.readString();
        size = in.readString();
        createTime = in.readString();
        lastModifiedTime = in.readString();
        path = in.readString();
    }

    public static final Creator<ScriptInfo> CREATOR = new Creator<ScriptInfo>() {
        @Override
        public ScriptInfo createFromParcel(Parcel in) {
            return new ScriptInfo(in);
        }

        @Override
        public ScriptInfo[] newArray(int size) {
            return new ScriptInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ScriptInfo{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastModifiedTime='" + lastModifiedTime + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(createTime);
        dest.writeString(lastModifiedTime);
        dest.writeString(path);
    }
}
