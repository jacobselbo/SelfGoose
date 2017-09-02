package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class GifFolderCmd extends Command {

    public GifFolderCmd() {
        this.name = "giff";
        this.description = "gives a random gif from the generated gif folder :P";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        File jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        File dir = new File(jar.getParentFile().getPath()+"\\gifs");
        dir.mkdir();
        if(dir.list().length==0) {
            reply(":cry: theres no gifs to choose from!",event);
            return;
        }
        int randomint = ThreadLocalRandom.current().nextInt(0,dir.listFiles().length);
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append("Random gif");
        Message msg = messageBuilder.build();
        event.getChannel().sendFile(dir.listFiles()[randomint],msg).queue();
    }
}
