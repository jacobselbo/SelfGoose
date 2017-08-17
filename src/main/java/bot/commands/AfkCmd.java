package bot.commands;

import bot.Bot;
import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AfkCmd extends Command {

    public AfkCmd() {
        this.name = "afk";
        this.arguments = "[reason]";
        this.description = "tells everyone your afk for [reason]";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        if (args.equals("")) {
            reply(event.getAuthor().getAsMention() + " is now AFK!",event);
        } else {
            reply(event.getAuthor().getAsMention() + " is now AFK for "+args,event);
        }
    }
}
