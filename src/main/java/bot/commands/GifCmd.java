package bot.commands;

import bot.Command;
import bot.utils.Http;
import com.google.gson.Gson;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GifCmd extends Command {

    public GifCmd() {
        this.name = "gif";
        this.description = "gives a random gif that is related to [tags] :P";
        this.arguments = "[tags]";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        if(args.equals("")) {
            Http httpmet = new Http();
            try {
                String response = httpmet.sendGet("http://api.giphy.com/v1/gifs/random?api_key=dc6zaTOxFJmzC").toString();
                Map root = new Gson().fromJson(response,Map.class);
                Map data = (Map) root.get("data");
                reply((String) data.get("url"),event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Http httpmet = new Http();
            try {
                String response = httpmet.sendGet("http://api.giphy.com/v1/gifs/random?api_key=dc6zaTOxFJmzC&tag="+args).toString();
                Map root = new Gson().fromJson(response,Map.class);
                Map data = (Map) root.get("data");
                reply((String) data.get("url"),event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
