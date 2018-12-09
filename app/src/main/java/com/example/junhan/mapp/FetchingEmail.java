package com.example.junhan.mapp;

import com.example.junhan.mapp.EmailRegex;

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
import javax.mail.search.FlagTerm;

public class FetchingEmail {

    public static HashMap<String,String[]> getEmails(String user,
                                                     String password) {
        String pop3Host = "";
        String storeType = "pop3";

        if (user.contains("@gmail.com")){
            pop3Host = "smtp.gmail.com";
        } //TODO ADD FOR SUTD


        HashMap<String,String[]> fetched = null;
        try {
            fetched = fetch(pop3Host, storeType, user, password);
        } catch (java.lang.NoClassDefFoundError e ){
            System.out.println("cmi");
            fetched = null;
        }
        return fetched;
    }

    public static HashMap<String,String[]> fetch(String pop3Host, String storeType, String user,
                                                 String password) {
        HashMap<String,String[]> allText = new HashMap<String,String[]>();
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

            System.out.println("messages.length---" + messages.length);



            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("MESSAGE " + i);
                String[] text = writePart(message);
                if (text[0].length() > 0 && text[1].length() > 0){
                    String[] info = EmailRegex.getInfo(text[1]);
                    if (info != null)
                        allText.put(text[0],info);
                }
//                String line = reader.readLine();
//                System.out.println(line);
//                if ("YES".equals(line)) {
//                    message.writeTo(System.out);
//                } else if ("QUIT".equals(line)) {
//                    break;
//                }
//                System.out.println("end of message 1");
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
    public static void main(String[] args) {

        String host = "smtp.gmail.com" ;// change accordingly
        String mailStoreType = "pop3";
        String username = "Marauder.mApp36@gmail.com";// change accordingly
        String password = "Group3-6";// change accordingly

        //Call method fetch
        HashMap<String,String[]> fetched = null;
        try {
            fetched = fetch(host, mailStoreType, username, password);
        } catch (java.lang.NoClassDefFoundError e ){
            System.out.println("cmi");
            fetched = null;
        }

        // just for printing
        /*
        Iterator it = fetched.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("--------------------------------------------------------------");
            System.out.println(pair.getKey());
            String[] info =  fetched.get(pair.getKey());
            for (String i : info){
                System.out.println(i);
            }

            it.remove(); // avoids a ConcurrentModificationException
        }
        */
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

//        System.out.println("----------------------------");
//        System.out.println("CONTENT-TYPE: " + p.getContentType());

        String result = "";
        if (p.isMimeType("text/plain")) {
            result = p.getContent().toString();
            // System.out.println(result);
        } else if (p.isMimeType("multipart/*")) {
            result = "";
            MimeMultipart mimeMultipart = (MimeMultipart) p.getContent();
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result = result + "\n" + bodyPart.getContent();
                    //System.out.println(result);
                    break;  //without break same text appears twice in my tests
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + Jsoup.parse(html).text();
                    //System.out.println(result);
                }
            }
        }
        /*
        System.out.println("PRINTED BY ME");
        System.out.println(Subject + "\n");
        System.out.println("PRINTED BY ME");
        System.out.println(result);
        */
        String[] text = {Subject, result};
        return text;
    }

    /*
     * This method would print FROM,TO and SUBJECT of the message
     */
    public static String writeEnvelope(Message m) throws Exception {
//        System.out.println("This is the message envelope");
//        System.out.println("---------------------------");
        Address[] a;

        // FROM
        /*
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++)
                System.out.println("FROM: " + a[j].toString());
        }
        */

        // TO
//        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
//            for (int j = 0; j < a.length; j++)
//                System.out.println("TO: " + a[j].toString());
//        }

        // SUBJECT
        /*
        if (m.getSubject() != null)
            System.out.println("SUBJECT: " + m.getSubject());
        */
        if (m.getSubject() != null)
            return m.getSubject();
        else
            return "";
    }

}