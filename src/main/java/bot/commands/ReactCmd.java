package bot.commands;

import bot.Command;
import net.dv8tion.jda.client.managers.EmoteManager;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class ReactCmd extends Command {

    public ReactCmd() {
        this.name = "react";
        this.description = "reacts to the above message";
        this.arguments = "<args>";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        event.getChannel().getHistory().retrievePast(1).queue(success -> {
            Message message = success.get(0);
            String[] parts = args.split("");
            if(message.getContent().equals(event.getMessage().getContent()) || message.getRawContent().equals(event.getMessage().getRawContent()) || message.equals(event.getMessage())) {
                try {
                    Thread.sleep(1000);
                    List<Message> messageList = event.getChannel().getHistory().retrievePast(1).complete();
                    message = messageList.get(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(String string : parts) {
                String uni = "";
                switch (string.toLowerCase()) {
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
                message.addReaction(uni).queue();
            }
            System.out.println(message.getRawContent());
        });
    }
}
