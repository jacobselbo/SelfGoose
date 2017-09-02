package bot.commands;

import bot.Command;
import bot.entities.Config;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NickCmd extends Command {
    private Config config;
    public NickCmd(Config config) {
        this.config = config;
        this.name = "setnick";
        this.arguments = "<player> <args>";
        this.description = "sets the player nick to <args>";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        String[] parts = args.split("\\s+",2);
        String nickname = "";
        for(String s : parts) {
            if(!s.equals(parts[0])) {
                nickname = nickname + s + " ";
            }
        }
        nickname = nickname.trim();
        if(parts.length < 2) {
            replytime(":x: Invalid use of the command; The correct use is ``"+config.getPrefix()+"setnick <@117822130183208960> hello darkness``",event,5000);
            return;
        }
        User target = event.getMessage().getMentionedUsers().get(0);
        if(target.equals(null)) {
            replytime(":x: Invalid user ( Should be a mention ); Correct use is ``"+config.getPrefix()+"setnick <@117822130183208960> hello darkness``",event,5000);
            return;
        }

        if(!event.getGuild().equals(null)) {
            if(!target.equals(event.getAuthor())) {
                Member targetMember = event.getGuild().getMember(target);
                if(!event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.NICKNAME_MANAGE)) {
                    replytime(":warning: Sorry, you don't have permissions to do that",event,5000);
                    return;
                }
                event.getGuild().getController().setNickname(targetMember,nickname).queue();
                reply(":white_check_mark: "+target.getAsMention()+"'s has been set to "+nickname+"!",event);
            } else {
                if(!event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.NICKNAME_CHANGE)) {
                    replytime(":warning: Sorry, you don't have permissions to do that",event,5000);
                    return;
                }
                event.getGuild().getController().setNickname(event.getGuild().getMember(event.getAuthor()),nickname).queue();
                reply(":white_check_mark: "+target.getAsMention()+"'s has been set to "+nickname+"!",event);
            }
        } else {
            replytime(":x: Sorry, you can't set people's nicknames in a group",event,5000);
        }
    }
}
