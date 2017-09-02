package bot.commands;

import bot.Command;
import bot.entities.Config;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class isfatcmd extends Command {
    private String[] fat;
    private Config config;

    public isfatcmd(Config config) {
        this.name = "isfat";
        this.arguments = "<user>";
        this.description = "tells if a person is fat";
        fat = new String[]{
                "142007603549962240",
                "239489995856019456"
        };
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        if (args.equals(null) || args.equals("")) {
            reply(":x: No user provided!",event);
        } else {
            User target = event.getMessage().getMentionedUsers().get(0);
            if(target.equals(null)) {
                reply(":x: Incorrect usage of command ( Should be a mention ) ex.``"+config.getPrefix()+"isfat <@117822130183208960>``",event);
            } else {
                boolean foundperson = false;
                for (String s : fat) {
                    if (s.equals(target.getId())) {
                        foundperson = true;
                    }
                }
                if (foundperson) {
                    reply(":thumbsup: Yes,"+target.getAsMention()+" is fat!",event);
                } else {
                    reply(":thumbsdown: No,"+target.getAsMention()+" isn't fat!",event);
                }
            }
        }
    }
}
