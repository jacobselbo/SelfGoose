package bot.commands;

import bot.Bot;
import bot.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Cal extends Command{

    public Cal() {
        this.name = "cal";
        this.arguments = "<asd>";
        this.description = "asd testing";
    }

    @Override
    public void execute(String args, MessageReceivedEvent event) {
        Message ms = event.getTextChannel().sendMessage("Calculating results please wait..").complete();
        String[] parts = args.split(" ");
        ArrayList<String> used = new ArrayList<>();
        ArrayList<Integer> ints = new ArrayList<>();
        for (String s : parts) {
            ints.add(Integer.parseInt(s));
        }
        int goal = ints.get(0);
        boolean finished = false;
        while(!finished) {
            ArrayList<Integer> integers = getInts(used);
            System.out.println("Trying "+integers.get(0)+","+integers.get(1)+","+integers.get(2)+","+integers.get(3));
            int a = math(ints.get(1),ints.get(2),integers.get(0));
            int b = math(a,ints.get(3),integers.get(1));
            int c = math(b,ints.get(4),integers.get(2));
            int d = math(c,ints.get(5),integers.get(3));
            if(d == goal) {
                finished = true;
                EmbedBuilder builder = new EmbedBuilder();
                if(event.getGuild()!=null)
                    builder.setColor(event.getGuild().getSelfMember().getColor());
                builder.setAuthor("Calculator",null,null);

                StringBuilder sbuilder = new StringBuilder();
                sbuilder.append("**Input**\n");
                sbuilder.append("```" +
                        "");
                sbuilder.append("**Output**\n");
                sbuilder.append("```" +
                        "");

                builder.setTimestamp(event.getMessage().getCreationTime());
                builder.setDescription(sbuilder.toString());
                builder.setFooter(event.getAuthor().getName() + " | Self Goose bot v"+ Bot.version, event.getAuthor().getAvatarUrl());
                ms.editMessage(new MessageBuilder().setEmbed(builder.build()).build()).queue();
                integers.forEach(System.out::println);
                break;
            } else {
                used.add(integers.toString());
            }
        }
    }

    private boolean check(String ints,ArrayList<String> used) {
        boolean usedInts = false;
        for(String s : used) {
            if(ints.equals(s)) {
                usedInts = true;
            }
        }
        return usedInts;
    }

    private ArrayList<Integer> getInts(ArrayList<String> used) {
        boolean finished = false;
        ArrayList<Integer> ints = new ArrayList<>();
        while(!finished) {
            ArrayList<Integer> integers = getRandomObj();
            boolean check = check(integers.toString(),used);
            if(!check) {
                finished = true;
                ints = integers;
                break;
            }
        }
        return ints;
    }

    private ArrayList<Integer> getRandomObj() {
        ArrayList<Integer> returnval = new ArrayList<>();
        returnval.add(ThreadLocalRandom.current().nextInt(1, 5));
        returnval.add(ThreadLocalRandom.current().nextInt(1, 5));
        returnval.add(ThreadLocalRandom.current().nextInt(1, 5));
        returnval.add(ThreadLocalRandom.current().nextInt(1, 5));
        return returnval;
    }

    /*private String getOperation(int operation) {
        String returnVal;
        switch(operation) {
            case 1:
                returnVal = "+";
                break;
            case 2:
                returnVal = "-";
                break;
            case 3:
                returnVal = x*y;
                break;
            case 4:
                returnVal = x/y;
                break;
        }
        return returnVal;
    }*/

    private int math(int x,int y,int obj) {
        int returnVal=0;
        switch(obj) {
            case 1:
                returnVal = x+y;
                break;
            case 2:
                returnVal = x-y;
                break;
            case 3:
                returnVal = x*y;
                break;
            case 4:
                returnVal = x/y;
                break;
        }
        return returnVal;
    }
}
