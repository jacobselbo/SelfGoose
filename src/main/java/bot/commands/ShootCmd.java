package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ShootCmd extends Command {

    public ShootCmd() {
        this.name = "shoot";
        this.arguments = "<player>";
        this.description = "shoots <player>";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        reply(args + " :gun: You have been shot!",event);
    }
}
