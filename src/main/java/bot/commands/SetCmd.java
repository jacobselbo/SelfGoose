package bot.commands;

import bot.Command;
import bot.entities.Emojis;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetCmd extends Command {
    private final Emojis emojis;
    public SetCmd(Emojis emojis) {
        this.emojis = emojis;
        this.name = "set";
        this.description = "sets <name> to <URL> in emojis.json";
        this.arguments = "<name> <URL>";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        String[] parts = args.split("\\s+",2);
        if(parts.length<2) {
            reply(":x: Must include an emoji name and content",event);
        }
        emojis.setEmoji(parts[0],parts[1]);
        reply(":thumbsup: Added emoji `"+parts[0]+"` that will convert to `"+parts[1]+"`",event);
    }
}
