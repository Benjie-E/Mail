/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.awt.BorderLayout;
import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Benjie
 */
public class Mail implements Serializable{

    private void emailGui() throws IOException, FileNotFoundException, ClassNotFoundException {
	GUI frame = new GUI();
	
    }
    Mail(){
	
    }
    class GUI extends JFrame{
	private int height=400;
	private int width=400;
	private String title="Email";
	GUI() throws IOException, FileNotFoundException, ClassNotFoundException{
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle(title);
	    setSize(width,height);
	    setLayout(new GridLayout(10,5));
	    add(new SortBar());
	    add(emailList());
	    System.out.println(this.getLayout());
	    setVisible(true);
	    
    }
	private JPanel emailList() throws IOException, FileNotFoundException, ClassNotFoundException{
	    List<Email> emails = load();
	    JPanel panel = new JPanel();
	    for(Email em:emails){
		EmailPanel ep = new EmailPanel(em);
		ep.panel();
		panel.add(ep);
	    }
	    return panel;
	}
    }
    private class SortBar extends JPanel{
	private SortItem date;
	private SortItem sender;
	private SortItem receiver;
	private SortItem subject;
	private SortItem priority;
	SortBar(){
	    setSize(50,50);
	    setLayout(new GridLayout(1,5));
	    date = new SortItem("Date");
	    sender = new SortItem("Sender");
	    receiver = new SortItem("Receiver");
	    subject = new SortItem("Subject");
	    priority = new SortItem("Priority");
	    
	    add(date);
	    add(sender);
	    add(receiver);
	    add(subject);
	    add(priority);
	}
	
    }
    private class SortItem extends JMenuItem{
	SortItem(String name){
	    setText(name);
	    setBorder(new LineBorder(BLACK));
	}
    }
    private class EmailPanel extends JPanel{
	private final int priority;
	private final String subject;
	private final String receiver;
	private final String sender;
	private final String dateSent;
	private final boolean checked;
	EmailPanel(Email e){
	    priority = e.priority;
	    subject = e.subject;
	    receiver = e.receiver;
	    sender = e.sender;
	    dateSent = e.dateSent;
	    checked = false;
	}
	private void panel(){
	    
	    setLayout(new GridLayout(1,5));
	    
	    setBorder(new LineBorder(BLACK));
	    add(new JCheckBox());
	    add(new SortItem(subject));
	    add(new SortItem(receiver));
	    add(new SortItem(sender));
	    add(new SortItem(dateSent));
	}
	
    }
    class Mailbox implements Serializable{
	public List<Email> emails;
	private File fileName;
	Mailbox() throws IOException, FileNotFoundException, ClassNotFoundException{
	    fileName = new File("input.bin");
	}
	private List<Email> load() throws IOException, FileNotFoundException, ClassNotFoundException{
	    emails=pullFromFile(fileName);
	    //System.out.println(emails.get(0).content);
	    //emails.add(createEmail());
	    return emails;
	}
    }
    class Email implements Serializable{
	private String dateSent;
	private String sender;
	private int priority;
	private String subject;
	private String receiver;
	private String content; 
	Email(){
	}
    }
    private Email testEmail(){
	Email m = new Email();
	m.content="this is a test";
	m.sender="human@email.com";
	m.dateSent="day";
	m.priority=4;
	m.receiver="you";
	m.subject="test";
	return m;
    }
    private Email createEmail(){
	Email m = testEmail();
	
	return m;
    }
    private List<Email> load() throws IOException, FileNotFoundException, ClassNotFoundException{
	Mailbox mailbox = new Mailbox();
	
	return mailbox.load();
    }
    private static List<Email> pullFromFile(File input) throws FileNotFoundException, IOException, ClassNotFoundException{
	if(!input.exists()){
	    input.createNewFile();
	}
	InputStream file = new FileInputStream(input);
	List<Email> emails = new ArrayList();
	ObjectInputStream in = new ObjectInputStream(file);
	try{
	    emails.add((Email) in.readObject());
	    
	    return emails;
	}catch(Exception e){
	    System.out.println(e);
	    return Collections.EMPTY_LIST;
	}
	
	

	
	
    }
    private static void newFile() throws FileNotFoundException, IOException{
	System.out.println("none");
	File output = new File("input.bin");
	output.createNewFile();
    }
    private static void writeToFile(Email m) throws FileNotFoundException, IOException{
	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("input.bin"));
	out.writeObject(m);
	out.close();
	
	
    }
    private void testWrite() throws FileNotFoundException, IOException{
	Email createEmail = createEmail();
	//System.out.println(createEmail);
	writeToFile(createEmail);
    }
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, URISyntaxException, ClassNotFoundException {
	// TODO code application logic here
	Mail m = new Mail();
	m.testWrite();
	//m.load();
	m.emailGui();
	//m.createEmail();
    }
    
}
