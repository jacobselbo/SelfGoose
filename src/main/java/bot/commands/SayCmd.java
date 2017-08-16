package bot.commands;

import bot.Command;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SayCmd extends Command {

    public SayCmd() {
        this.name = "say";
        this.description = "says the args in emotes";
        this.arguments = "<args>";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        String[] parts = args.split("");
        String message = "";
        for(String string : parts) {
            if (!string.equals(" ")) {
                String uni = "";
                switch (string) {
                    case "a":
                        uni = "\uD83C\uDDE6";
                        break;
                    case "b":
                        uni = "\uD83C\uDDE7";
                        break;
                    case "c":
                        uni = "\uD83C\uDDE8";
                        break;
                    case "d":
                        uni = "\uD83C\uDDE9";
                        break;
                    case "e":
                        uni = "\uD83C\uDDEA";
                        break;
                    case "f":
                        uni = "\uD83C\uDDEB";
                        break;
                    case "g":
                        uni = "\uD83C\uDDEC";
                        break;
                    case "h":
                        uni = "\uD83C\uDDED";
                        break;
                    case "i":
                        uni = "\uD83C\uDDEE";
                        break;
                    case "j":
                        uni = "\uD83C\uDDEF";
                        break;
                    case "k":
                        uni = "\uD83C\uDDF0";
                        break;
                    case "l":
                        uni = "\uD83C\uDDF1";
                        break;
                    case "m":
                        uni = "\uD83C\uDDF2";
                        break;
                    case "n":
                        uni = "\uD83C\uDDF3";
                        break;
                    case "o":
                        uni = "\uD83C\uDDF4";
                        break;
                    case "p":
                        uni = "\uD83C\uDDF5";
                        break;
                    case "q":
                        uni = "\uD83C\uDDF6";
                        break;
                    case "r":
                        uni = "\uD83C\uDDF7";
                        break;
                    case "s":
                        uni = "\uD83C\uDDF8";
                        break;
                    case "t":
                        uni = "\uD83C\uDDF9";
                        break;
                    case "u":
                        uni = "\uD83C\uDDFA";
                        break;
                    case "v":
                        uni = "\uD83C\uDDFB";
                        break;
                    case "w":
                        uni = "\uD83C\uDDFC";
                        break;
                    case "x":
                        uni = "\uD83C\uDDFD";
                        break;
                    case "y":
                        uni = "\uD83C\uDDFE";
                        break;
                    case "z":
                        uni = "\uD83C\uDDFF";
                        break;
                    case "0":
                        uni = "\u0030\u20E3";
                        break;
                    case "1":
                        uni = "\u0031\u20E3";
                        break;
                    case "2":
                        uni = "\u0032\u20E3";
                        break;
                    case "3":
                        uni = "\u0033\u20E3";
                        break;
                    case "4":
                        uni = "\u0034\u20E3";
                        break;
                    case "5":
                        uni = "\u0035\u20E3";
                        break;
                    case "6":
                        uni = "\u0036\u20E3";
                        break;
                    case "7":
                        uni = "\u0037\u20E3";
                        break;
                    case "8":
                        uni = "\u0038\u20E3";
                        break;
                    case "9":
                        uni = "\u0039\u20E3";
                        break;
                    case "#":
                        uni = "\u0023\u20E3";
                        break;
                }
                message = message + " " + uni;
            }
        }
        message.trim();
        reply(message,event);
    }
}
