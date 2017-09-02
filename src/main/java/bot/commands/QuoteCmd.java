package bot.commands;

import bot.Bot;
import bot.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.OffsetDateTime;

public class QuoteCmd extends Command {

    public QuoteCmd() {
        this.name = "quote";
        this.description = "quotes a message";
        this.arguments = "<message id> [channel mention or id] [text]";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        String[] parts = args.split("\\s+",2);
        String id1 = parts[0].replaceAll("<#(\\d+)>", "$1");
        if(!isId(id1)) {
            reply(":x: `"+id1+"` is not a valid message or channel ID",event);
            return;
        }
        MessageChannel channel = resolveChannel(id1,event.getJDA());
        String messageId;
        String followingText = null;
        if (channel != null) {
            if(parts.length == 1) {
                reply(":x: Channel provided but no message id!",event);
                return;
            }
            parts = parts[1].split("\\s+",2);
            if(!isId(parts[0])) {
                reply(":x: `"+parts[0]+"` is not a valid message ID",event);
                return;
            }
            messageId = parts[0];
            followingText = parts.length>1 ? parts[1] : null;
        } else {
            messageId = id1;
            if(parts.length>1) {
                String[] parts2 = parts[1].split("\\s+",2);
                String id2 = parts2[0].replaceAll("<#(\\d+)>", "$1");
                channel = resolveChannel(id2,event.getJDA());
                if(channel == null)
                    followingText = parts[1];
                else
                    followingText = parts2.length > 1 ? parts2[1] : null;
            }
        }
        String follow = followingText;
        if(channel == null)
            channel = event.getChannel();
        try {
            String foot = channel.equals(event.getChannel()) ? "" : " in #"+channel.getName();
            channel.getHistoryAround(messageId,2).queue(mh -> {
                if(mh.getRetrievedHistory().isEmpty()) {
                    reply(":x: No message history around `"+messageId+"`",event);
                    return;
                }
                Message msg = mh.getRetrievedHistory().size()==1 || mh.getRetrievedHistory().size() == 2 ? mh.getRetrievedHistory().get(0) : mh.getRetrievedHistory().get(1);
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor(msg.getAuthor().getName()+"#"+msg.getAuthor().getDiscriminator(),null,msg.getAuthor().getEffectiveAvatarUrl());
                if(msg.getGuild()!=null) {
                    Member member = msg.getGuild().getMemberById(event.getAuthor().getId());
                    if(member!=null)
                        builder.setColor(member.getColor());
                }
                if(!msg.getAttachments().isEmpty() && msg.getAttachments().get(0).isImage())
                    builder.setImage(msg.getAttachments().get(0).getUrl());
                if(msg.isEdited()) {
                    OffsetDateTime edited = msg.getEditedTime();
                    String time = edited.getYear()+"-"+edited.getMonthValue()+"-"+edited.getDayOfMonth()+" : "+edited.getHour()+":"+edited.getMinute()+":"+edited.getSecond();
                    builder.addField("Edited",time,true);
                }
                OffsetDateTime made = msg.getCreationTime();
                String time = made.getYear()+"-"+made.getMonthValue()+"-"+made.getDayOfMonth()+" : "+made.getHour()+":"+made.getMinute()+":"+made.getSecond();
                builder.addField("Sent",time,true);
                builder.setDescription(msg.getRawContent());
                builder.setFooter(event.getAuthor().getName() + " | Self Goose bot v"+ Bot.version, event.getJDA().getSelfUser().getAvatarUrl()==null ? event.getJDA().getSelfUser().getDefaultAvatarUrl() : event.getJDA().getSelfUser().getAvatarUrl());
                reply(builder.build(),event,follow==null ? null : s-> event.getChannel().sendMessage(follow).queue());
            },f -> reply(":x: Failed to retrieve history around `"+messageId+"`",event));
        } catch (Exception e) {
            reply(":x: Could not retrieve history: "+e,event);
        }
    }

    private static MessageChannel resolveChannel(String id,JDA jda) {
        MessageChannel mc = jda.getTextChannelById(id);
        if(mc!=null)
            return mc;
        mc = jda.getPrivateChannelById(id);
        if(mc!=null)
            return mc;
        User u = jda.getUserById(id);
        if(u!=null)
            return u.openPrivateChannel().complete();
        return null;
    }

    public static boolean isId(String id) {
        return id.matches("\\d{17,22}");
    }
}
