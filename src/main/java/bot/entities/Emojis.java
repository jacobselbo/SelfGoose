package bot.entities;

import bot.utils.FormatUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

public class Emojis {
    private final HashMap<String,String> emojis;
    private final static String FILENAME = "emojis.json";


    public Emojis() {
        emojis = new HashMap<>();
        JSONObject obj;
        try {
            obj = new JSONObject(FormatUtil.join(Files.readAllLines(Paths.get(FILENAME)),"\n"));
            obj.keySet().stream().forEach(name -> emojis.put(name.toLowerCase(),obj.getString(name)));
            System.out.println("Successfully loaded "+emojis.keySet().size()+" custom emojis!");
        } catch(IOException e) {
            System.out.println(FILENAME +" was not found! ingore this if you haven't set any custom emojis");
        } catch(JSONException e) {
            System.out.println("the emoji file, "+FILENAME+" is corrupted. fix this file before adding emojis or the file will be overwritten");
        }
    }

    private void write() {
        JSONObject obj = new JSONObject();
        emojis.keySet().stream().forEach(name -> obj.put(name, emojis.get(name)));
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILENAME), "UTF-8"))) {
            out.write(obj.toString(2));
        }catch(IOException e) {
            System.out.println("Failed to save emojis to "+FILENAME);
        }
    }

    public void setEmoji(String name,String content) {
        emojis.put(name.toLowerCase(),content);
        write();
    }

    public boolean deleteEmoji(String name) {
        boolean removed = emojis.remove(name.toLowerCase())!=null;
        if(removed)
            write();
        return removed;
    }

    public String getEmoji(String name) {
        System.out.println(emojis.get(name));
        return emojis.get(name);
    }

    public Collection<String> getEmojiList() {
        return emojis.keySet();
    }
}
