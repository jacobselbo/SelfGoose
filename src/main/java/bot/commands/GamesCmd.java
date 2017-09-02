package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GamesCmd extends Command {

    public GamesCmd() {
        this.name = "games";
        this.description = "Gives all the games";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {

    }
}
