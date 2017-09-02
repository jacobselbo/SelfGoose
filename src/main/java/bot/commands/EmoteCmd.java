package bot.commands;

import bot.Bot;
import bot.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class EmoteCmd extends Command {

    public EmoteCmd() {
        this.name = "emoji";
        this.description = "Gives a description of <emoji>";
        this.arguments = "<:emoji:>";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        String id = args.replaceAll("<:.+:(\\d+)>", "$1");
        Emote emote;
        try {
            emote = event.getJDA().getEmoteById(id);
        } catch (Exception e) {
            emote = null;
        }
        if(emote == null) {
            reply(":x: Invalid emoji or emoji ID ``"+args+"``",event);
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            if(event.getGuild()!=null)
                builder.setColor(event.getGuild().getSelfMember().getColor());
            builder.setAuthor("Info for emoji "+emote.getName(),null,null);
            StringBuilder sbuilder = new StringBuilder();
            sbuilder.append("Emoji Name - "+emote.getName()+"\n");
            sbuilder.append("Emoji ID - "+emote.getId()+"\n");
            sbuilder.append("Emoji URL - "+emote.getImageUrl()+"\n");
            sbuilder.append("Emoji Mention - "+emote.getAsMention()+"\n");
            builder.setDescription(sbuilder.toString());
            builder.setFooter(event.getAuthor().getName() + " | Self Goose bot v"+ Bot.version, emote.getImageUrl());
            event.getChannel().sendMessage(new MessageBuilder().setEmbed(builder.build()).build()).queue();
        }
    }
}
