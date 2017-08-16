package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.ThreadLocalRandom;

public class FlipCmd extends Command {

    public FlipCmd() {
        this.name = "flip";
        this.description = "heads or tails?";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        int randomNum = ThreadLocalRandom.current().nextInt(0,2);
        switch(randomNum) {
            case 1:
                reply(":arrows_counterclockwise: Heads!", event);
                break;
            case 0:
                reply(":arrows_counterclockwise: Tails!", event);
                break;
        }
    }
}
