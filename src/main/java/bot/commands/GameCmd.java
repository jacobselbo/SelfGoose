package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GameCmd extends Command {

    public GameCmd() {
        this.name = "game";
        this.description = "sets or clears your current streaming";
        this.arguments = "[game]";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        String result = "";
        if (args.isEmpty()) {
            event.getJDA().getPresence().setGame(null);
            result = "\uD83D\uDC4D Game cleared!";
        } else {
            try {
                Game game;
                if(args.startsWith("twitch")) {
                    System.out.println(args);
                    String[] parts = args.substring(6).trim().split("\\s+",2);
                    args = parts[1];
                    game = Game.of(args,"http://twitch.tv/"+parts[0]);
                } else {
                    game = Game.of(args);
                }
                event.getJDA().getPresence().setGame(game);
                result = "\uD83D\uDC4D Game set to "+(game.getUrl()==null ? "Playing": "Streaming")+" `"+args+"`.";
            } catch (Exception e) {
                result = ":x: Game couldn't be set to `"+args+"`";
                e.printStackTrace();
            }
        }
        reply(result,event);
    }
}
