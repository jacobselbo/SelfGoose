package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.temporal.ChronoUnit;

public class PingCmd extends Command {
    public PingCmd() {
        this.name = "ping";
        this.description = "check the bot's connection";
        this.type = Type.KEEP_AND_RESEND;
    }
    @Override
    public void execute(String args, MessageReceivedEvent event) {
        event.getMessage().editMessage("Pinging...").queue(m -> {
            m.editMessage("\uD83C\uDFD3 Pong! Took " + m.getCreationTime().until(m.getEditedTime(), ChronoUnit.MILLIS) + "ms").queue();
        });
    }
}
