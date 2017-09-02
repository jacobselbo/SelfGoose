package bot.commands;

import bot.Command;
import bot.entities.Emojis;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DeleteCmd extends Command {
    private final Emojis emojis;
    public DeleteCmd(Emojis emojis) {
        this.emojis = emojis;
        this.name = "delete";
        this.description = "deletes <name> custom emoji";
        this.arguments = "<name>";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        if(args.isEmpty()) {
            reply(":x: Include a emoji name",event);
            return;
        }
        boolean deleted = emojis.deleteEmoji(args);
        if(deleted) {
            reply(":thumbsup: Deleted emoji ``"+args+"``",event);
        } else {
            reply(":x: Emoji ``"+args+"`` was not found.",event);
        }
    }
}
