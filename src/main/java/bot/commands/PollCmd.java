package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PollCmd extends Command {
    private final int REGIONAL_A = "\uD83C\uDDE6".codePointAt(0);
    public PollCmd() {
        this.name = "poll";
        this.description = "makes a poll";
        this.arguments = "<question> or <question>|<option1>|<option2>...";
        this.type = Type.EDIT_ORIGINAL;
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        String[] parts = args.split("\\|");
        if(parts.length==1)
        {
            reply(formatQuestion(args), event, m ->
            {
                m.addReaction("\uD83D\uDC4D").queue();
                m.addReaction("\uD83D\uDC4E").queue();
            });
        }
        else
        {
            StringBuilder builder = new StringBuilder(formatQuestion(parts[0]));
            for(int i=1; i<parts.length; i++)
            {
                String r = String.copyValueOf(Character.toChars(REGIONAL_A+i-1));
                builder.append("\n").append(r).append(" ").append(parts[i].trim());
            }
            reply(builder.toString(), event, m ->
            {
                for(int i=1; i<parts.length; i++)
                    m.addReaction(String.copyValueOf(Character.toChars(REGIONAL_A+i-1))).queue();
            });
        }
    }

    private static String formatQuestion(String str)
    {
        return "\uD83D\uDDF3 **"+str+"**";
    }
}
