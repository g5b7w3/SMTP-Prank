import config.ConfigurationManager;
import config.IConfigurationManager;
import model.mail.Group;
import model.mail.Person;
import model.prank.Prank;
import model.prank.PrankGenerator;
import smtp.ISmtpClient;
import smtp.SmtpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class Main {

    public static void main(String[] args) throws IOException {
        IConfigurationManager icm = new ConfigurationManager();
        List<Prank> prankList;
        List<Group> groupList;
        List<Person> victimList;

        PrankGenerator pg = new PrankGenerator(icm);

        victimList = icm.getVictims();
        prankList = pg.generatePranks();
        pg.generateGroups(victimList,5);

        Properties properties = new Properties();

        properties.load(new FileInputStream("config/config.properties"));


        ISmtpClient smtpClient = new SmtpClient(icm.getSrvAddress(),icm.getSrvPort());
        for (Prank p : prankList){
            smtpClient.sendMessage(p.generateMailMessage());
        }

    }

}
