package smtp;

import model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient{

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String srvAddress;
    private int srvPort = 25;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;


    public SmtpClient(String srvAddress, int srvPort){
        this.srvAddress = srvAddress;
        this.srvPort = srvPort;

    }

    @Override
    public void sendMessage(Message message) throws IOException {

        Socket socket = new Socket(srvAddress,srvPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        String line = reader.readLine();
        writer.printf("EHLO localhost\r\n");
        line = reader.readLine();
        if(!line.startsWith("250")){
            throw new IOException("SMTP error: "+ line);
        }
        while (line.startsWith("250-")){
            line = reader.readLine();
            LOG.info(line);
        }
        writer.write("MAIL FROM:");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        for (String to : message.getTo()){
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }
        for (String to : message.getCc()){
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        for (String to : message.getBcc()){
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("DATA:");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        writer.write("Content-Type: text/plain: charset=\"utf-8\"\r\n");
        writer.write("From: "+message.getFrom() + "\r\n");


        writer.write("To: "+message.getTo()[0]);
        for (int i = 1;i < message.getTo().length;i++){
            writer.write(","+message.getTo()[i]);
        }
        writer.write("\r\n");

        writer.write("Cc: "+message.getCc()[0]);
        for (int i = 1;i < message.getCc().length;i++){
            writer.write(","+message.getCc()[i]);
        }

        writer.write("\r\n");
        writer.flush();
        LOG.info(message.getBody());
        writer.write(message.getBody());
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        writer.write("QUIT\r\n");
        writer.flush();
        reader.close();
        writer.close();
        socket.close();

    }
}
