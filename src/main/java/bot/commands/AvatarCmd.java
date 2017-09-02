package bot.commands;

import bot.Bot;
import bot.Command;
import bot.entities.Config;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AvatarCmd extends Command {
    private Config config;
    public AvatarCmd(Config config) {
        this.config = config;
        this.name = "avatar";
        this.arguments = "<player>";
        this.description = "gets <player>'s avatar";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        if(args.equals("") || args.equals(null) || event.getMessage().getMentionedUsers().get(0).equals(null)) {
            replytime(":x: Invalid use of the command; The correct use is ``"+config.getPrefix()+"avatar <@117822130183208960>``",event,5000);
            return;
        }
        User target = event.getMessage().getMentionedUsers().get(0);
        EmbedBuilder builder = new EmbedBuilder();
        if(event.getGuild()!=null)
            builder.setColor(event.getGuild().getSelfMember().getColor());
        builder.setAuthor(target.getName()+"'s avatar",null,null);
        builder.setTimestamp(event.getMessage().getCreationTime());
        builder.setImage(target.getAvatarUrl()==null ? target.getDefaultAvatarUrl() : target.getAvatarUrl());
        builder.setFooter(event.getAuthor().getName() + " | Self Goose bot v"+ Bot.version, event.getJDA().getSelfUser().getAvatarUrl());
        reply(new MessageBuilder().setEmbed(builder.build()).build(),event);
    }
}
