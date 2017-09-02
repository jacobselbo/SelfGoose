package bot.commands;

import bot.Bot;
import bot.Command;
import bot.entities.Emojis;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Collection;

public class EmoteListCmd extends Command {
    private Emojis emojis;

    public EmoteListCmd(Emojis emojis) {
        this.emojis = emojis;
        this.name = "emojis";
        this.description = "lists all the custom emojis";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        Collection<String> list = emojis.getEmojiList();
        if(list.isEmpty()) {
            reply(":x: No custom emojis!",event);
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        if(event.getGuild()!=null)
            builder.setColor(event.getGuild().getSelfMember().getColor());
        builder.setAuthor("Custom Emojis",null,null);

        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append("Custom Emojis : \n");
        for(String name : list) {
            String content = emojis.getEmoji(name);
            sbuilder.append("``"+name+"`` - "+content+"\n");
        }
        builder.setTimestamp(event.getMessage().getCreationTime());
        builder.setDescription(sbuilder.toString());
        builder.setFooter(event.getAuthor().getName() + " | Self Goose bot v"+ Bot.version, event.getJDA().getSelfUser().getAvatarUrl()==null ? event.getJDA().getSelfUser().getDefaultAvatarUrl() : event.getJDA().getSelfUser().getAvatarUrl());
        reply(builder.build(),event);
    }
}
