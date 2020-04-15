package cn.dxs.zdjl.domain;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.dxs.zdjl.domain.script.ScriptDes;

/**
 * 脚本内容
 *
 * @author lijian
 * @date 2020-03-26 12:43
 */
public class ScriptContent {

    private File file;
    private List<JsonObject> jObjs = new ArrayList<>();
    private Gson gson = new Gson();
    private JsonParser jsonParser = new JsonParser();

    public ScriptContent(String path) {
        this.file = new File(path);
        init();
    }

    public ScriptContent(File file) {
        this.file = file;
        init();
    }

    private void init() {
        FileReader fr = null;
        BufferedReader bufr = null;

        try {
            fr = new FileReader(file);
            bufr = new BufferedReader(fr);
            String temp;
            while ((temp = bufr.readLine()) != null) {
                jObjs.add(jsonParser.parse(temp).getAsJsonObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (bufr != null) {
                    bufr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 脚本描述（脚本文件的第一行内容）
     *
     * @return
     */
    public ScriptDes getScriptDes() {
        return gson.fromJson(jObjs.get(0), ScriptDes.class);
    }

    public List<JsonObject> getjObjs(){
        return jObjs;
    }
}
