package model.prank;

import model.mail.Message;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Prank {
    private Person victimSender;
    private final List<Person> victimRecipients = new ArrayList<>();
    private final List<Person> witnessRecipients = new ArrayList<>();
    private String message;


    public Message generateMailMessage(){
        Message msg = new Message();

        msg.setBody(this.message+"\r\n"+victimSender.getFirstName());
        String[] to = victimRecipients
                .stream()
                .map(p -> p.getAddress())
                .collect(Collectors.toList())
                .toArray(new String[]{});
        msg.setTo(to);


        String[] cc = witnessRecipients
                .stream()
                .map(p -> p.getAddress())
                .collect(Collectors.toList())
                .toArray(new String[]{});
        msg.setCc(cc);

        msg.setFrom(victimSender.getAddress());

        return msg;

    }



    public Person getVictimSender (){
        return victimSender;
    }

    public String getMessage (){
        return message;
    }

    public List<Person> getVictimRecipients (){
        return new ArrayList<>(victimRecipients);
    }

    public List<Person> getWitnessRecipients (){
        return new ArrayList<>(witnessRecipients);
    }

    public void setVictimSender (Person victimSender){
        this.victimSender = victimSender;
    }

    public void setMessage (String message){
        this.message = message;
    }

    public void addVictimRecipients(List<Person> victims){
        victimRecipients.addAll(victims);
    }
    public void addWtiness(List<Person> witness){
        witnessRecipients.addAll(witness);
    }


}
