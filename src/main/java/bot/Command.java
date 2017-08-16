package bot;

import java.util.function.Consumer;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;

public abstract class Command {
    public String name = "null";
    public String description = "No description provided";
    public String arguments = null;
    public Type type = Type.DELETE_AND_RESEND;

    public abstract void execute(String args, MessageReceivedEvent event);

    public void run(String args, MessageReceivedEvent event) {
        try {
            if (type == Type.DELETE_AND_RESEND)
                event.getMessage().delete().queue();
            execute(args, event);
        } catch (Exception e) {
            failure(event);
            throw e;
        }
    }

    public void reply(String reply, MessageReceivedEvent event) {
        if (type == Type.EDIT_ORIGINAL)
            event.getMessage().editMessage(reply).queue();
        else
            event.getChannel().sendMessage(reply).queue();
    }

    public void reply(String reply, MessageReceivedEvent event, Consumer<Message> consumer) {
        if (type == Type.EDIT_ORIGINAL)
            event.getMessage().editMessage(reply).queue(consumer);
        else
            event.getChannel().sendMessage(reply).queue(consumer);
    }

    public void reply(Message reply, MessageReceivedEvent event) {
        if (type == Type.EDIT_ORIGINAL)
            event.getMessage().editMessage(reply).queue();
        else
            event.getChannel().sendMessage(reply).queue();
    }

    public void reply(Message reply, MessageReceivedEvent event, Consumer<Message> consumer) {
        if (type == Type.EDIT_ORIGINAL)
            event.getMessage().editMessage(reply).queue(consumer);
        else
            event.getChannel().sendMessage(reply).queue(consumer);
    }

    public void reply(MessageEmbed embed, MessageReceivedEvent event) {
        if (type == Type.EDIT_ORIGINAL)
            event.getMessage().editMessage(new MessageBuilder().setEmbed(embed).build()).queue();
        else
            event.getChannel().sendMessage(new MessageBuilder().setEmbed(embed).build()).queue();
    }

    public void reply(MessageEmbed embed, MessageReceivedEvent event, Consumer<Message> consumer) {
        if (type == Type.EDIT_ORIGINAL)
            event.getMessage().editMessage(new MessageBuilder().setEmbed(embed).build()).queue(consumer);
        else
            event.getChannel().sendMessage(new MessageBuilder().setEmbed(embed).build()).queue(consumer);
    }

    public void failure(MessageReceivedEvent event) {
        replytime("\u274C" + "[Error]", event, 10000);
    }

    public void tempReply(String message, MessageReceivedEvent event) {
        RestAction<Message> action;
        if (type == Type.EDIT_ORIGINAL)
            action = event.getMessage().editMessage(message);
        else
            action = event.getChannel().sendMessage(message);
        action.queue(m -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            m.delete().queue();
        });
    }

    public void replytime(String message, MessageReceivedEvent event, int time) {
        RestAction<Message> action;
        if (type == Type.EDIT_ORIGINAL)
            action = event.getMessage().editMessage(message);
        else
            action = event.getChannel().sendMessage(message);
        action.queue(m -> {
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
            m.delete().queue();
        });
    }

    public enum Type {
        DELETE_AND_RESEND, EDIT_ORIGINAL, KEEP_AND_RESEND
    }
}