package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class MemeFolderCmd extends Command {

    public MemeFolderCmd() {
        this.name = "memef";
        this.description = "gives a meme from the generated meme folder :P";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        File jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        File dir = new File(jar.getParentFile().getPath()+"\\memes");
        System.out.println("jarpath:"+jar.getPath()+" | memefolder:"+dir.getPath());
        dir.mkdir();
        if(dir.list().length==0) {
            reply(":cry: theres no memes to choose from!",event);
            return;
        }
        int randomint = ThreadLocalRandom.current().nextInt(0,dir.listFiles().length);
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append("Random meme");
        Message msg = messageBuilder.build();
        event.getChannel().sendFile(dir.listFiles()[randomint],msg).queue();
    }
}
