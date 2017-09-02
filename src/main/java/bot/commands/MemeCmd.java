package bot.commands;

import bot.Command;
import bot.utils.Http;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MemeCmd extends Command {

    public MemeCmd() {
        this.name = "meme";
        this.description = "gives a random meme :P";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        Http httpmet = new Http();
        try {
            String response = httpmet.sendGet("https://api.imgflip.com/get_memes").toString();
            Map root = new Gson().fromJson(response,Map.class);
            if (root.get("success").equals(true)) {
                Map data = (Map) root.get("data");
                ArrayList memes = (ArrayList) data.get("memes");
                int randomint = ThreadLocalRandom.current().nextInt(0,memes.size());
                Map selmeme = (Map) memes.get(randomint);
                reply(selmeme.get("name") + "\n"+selmeme.get("url"),event);
            } else {
                reply(":x: api.imgflip.com/get_memes has errored!",event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
