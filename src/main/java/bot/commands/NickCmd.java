package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NickCmd extends Command {

    public NickCmd() {
        this.name = "nick";
        this.arguments = "<args>";
        this.description = "sets the player nick to <args>";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        event.getGuild().getController().setNickname(event.getGuild().getMember(event.getAuthor()),args).queue();
        reply("\uD83D\uDC4D Nickname set to `"+args+"`",event);
    }
}
