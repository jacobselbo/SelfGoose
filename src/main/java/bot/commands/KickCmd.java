package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class KickCmd extends Command {

    public KickCmd() {
        this.name = "kick";
        this.arguments = "<player>";
        this.description = "kicks <player> if you have the permissions to do it";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        if(event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.KICK_MEMBERS)) {
            User tuser = event.getMessage().getMentionedUsers().get(0);
            if(tuser == null) {
                replytime(":x: "+args+" is not a user (Must be a mention)",event,5000);
                return;
            }
            event.getGuild().getController().kick(event.getGuild().getMember(tuser)).queue();
            reply(":white_check_mark: "+args+" has been kicked!",event);
        } else {
            replytime(":warning: Sorry, you don't have permissions to do that",event,5000);
        }
    }
}