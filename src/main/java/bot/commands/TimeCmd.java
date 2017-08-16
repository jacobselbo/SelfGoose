package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeCmd extends Command {
    private final ZoneId zone;
    public TimeCmd(ZoneId zone)
    {
        this.zone = zone;
        this.name = "time";
        this.description = "checks the time";
        this.type = Type.EDIT_ORIGINAL;
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        ZonedDateTime t = event.getMessage().getCreationTime().atZoneSameInstant(zone);
        String time = t.format(DateTimeFormatter.ofPattern("h:mma"));
        String time24 = t.format(DateTimeFormatter.ofPattern("HH:mm"));
        String name = event.getGuild()==null ? event.getAuthor().getName() : event.getMember().getEffectiveName();
        reply("\u231A Currently it is `"+time+"` (`"+time24+"`)", event);
    }
}
