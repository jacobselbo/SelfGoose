package bot.commands;

import bot.Bot;
import bot.Command;
import bot.Listener;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdsCmd extends Command {
    private String prefix;

    public CmdsCmd(String prefix) {
        this.prefix = prefix;
        this.name = "cmds";
        this.description = "shows the commands";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        if(event.getGuild()!=null)
            builder.setColor(event.getGuild().getSelfMember().getColor());
        builder.setAuthor(event.getJDA().getSelfUser().getName()+" Commands", null, event.getJDA().getSelfUser().getAvatarUrl()==null ? event.getJDA().getSelfUser().getDefaultAvatarUrl() : event.getJDA().getSelfUser().getAvatarUrl());
        StringBuilder sbuilder = new StringBuilder("Commands:");
        for(Command command : Listener.commands)
        {
            sbuilder.append("\n`").append(prefix).append(command.name);
            if(command.arguments!=null)
                sbuilder.append(" ").append(command.arguments);
            sbuilder.append("` ").append(command.description);
        }
        builder.setTimestamp(event.getMessage().getCreationTime());
        builder.setDescription(sbuilder.toString());
        builder.setFooter(event.getAuthor().getName() + " | Self Goose bot v"+Bot.version, event.getJDA().getSelfUser().getAvatarUrl()==null ? event.getJDA().getSelfUser().getDefaultAvatarUrl() : event.getJDA().getSelfUser().getAvatarUrl());
        event.getChannel().sendMessage(new MessageBuilder().setEmbed(builder.build()).build()).queue();
    }
}
