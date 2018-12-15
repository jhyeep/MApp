package com.example.junhan.mapp;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;


//TODO: clean up
public class FetchingEmail {

    // main method for getting emails
    public static HashMap<String,String[]> getEmails(String user,
                                                     String password) {
        String pop3Host = "";
        String storeType = "pop3";

        if (user.contains("@gmail.com")){
            pop3Host = "pop.gmail.com";
        }

        HashMap<String, String[]> fetched;
        try {
            fetched = fetch(pop3Host, storeType, user, password);
        } catch (java.lang.NoClassDefFoundError e ){
            fetched = null;
        }
        return fetched;
    }

    public static HashMap<String,String[]> fetch(String pop3Host, String storeType, String user,
                                                 String password) {
        HashMap<String,String[]> allText = new HashMap<>();
        try {
            // create properties field
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3.host", pop3Host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);
            // emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(pop3Host, user, password);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();

            //Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), true));
            //FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            //emailFolder.open(Folder.READ_ONLY);//set access type of Inbox
            //Message[] messages = emailFolder.search(ft);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String[] text = writePart(message);
                if (text[0].length() > 0 && text[1].length() > 0){
                    String[] info = EmailRegex.getInfo(text[1]);
                    if (info != null)
                        allText.put(text[0],info);
                }
            }

            // close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allText;
    }

    /*
     * This method checks for content-type
     * based on which, it processes and
     * fetches the content of the message
     */
    public static String[] writePart(Part p) throws Exception {
        String Subject = "";
        if (p instanceof Message)
            //Call method writeEnvelope
            Subject = writeEnvelope((Message) p);

        String result = "";
        if (p.isMimeType("text/plain")) {
            result = p.getContent().toString();
        } else if (p.isMimeType("multipart/*")) {
            result = "";
            MimeMultipart mimeMultipart = (MimeMultipart) p.getContent();
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result = result + "\n" + bodyPart.getContent();
                    break;  //without break same text appears twice
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + Jsoup.parse(html).text();
                }
            }
        }
        String[] text = {Subject, result};
        return text;
    }

    /*
     * This method would print FROM,TO and SUBJECT of the message
     */
    public static String writeEnvelope(Message m) throws Exception {
        Address[] a;

        if (m.getSubject() != null)
            return m.getSubject();
        else
            return "";
    }

}