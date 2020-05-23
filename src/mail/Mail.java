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
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
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

    private class ClickListener implements ActionListener{

	public ClickListener() {
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    //System.out.println(mailSender.getComponent(1));
	    //System.out.println(e.getSource());
	    //System.out.println(((JButton)e.getSource()).getParent());
	    if(e.getActionCommand() == "Send"){
		CreateEmailFrame source = (CreateEmailFrame) ((JButton)e.getSource()).getParent().getParent().getParent().getParent();
		Email writing = new Email();
		try{
		writing.content = source.mailContent.getText();
		writing.dateSent = new Date();
		writing.priority = Integer.parseInt(source.mailPriority.getText());
		writing.subject = source.mailSubject.getText();
		writing.sender = source.mailSender.getText();
		writing.receiver = source.mailReceiver.getText();
		try {
		    addToFile(writing);
		    source.dispose();
		    
		} catch (IOException ex) {
		    Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
		}
		}catch(Exception ex){
		    System.out.println(ex);
		}
	    }
	}

    }

    private class NewEmailListener implements ActionListener {
	    
	public NewEmailListener() {
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
		new CreateEmailFrame();
	    } catch (IOException ex) {
		Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (ClassNotFoundException ex) {
		Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    class CreateEmailFrame extends JFrame{
	private int height=300;
	private int width=500;
	private String title="test";
	

	
	    EmailCreationBox mailSender;
	    EmailCreationBox mailReceiver;
	    EmailCreationBox mailSubject;
	    EmailCreationBox mailPriority;
	    EmailCreationBox mailContent;
	CreateEmailFrame() throws IOException, FileNotFoundException, ClassNotFoundException{
		mailSender = new EmailCreationBox("Sender");
		mailReceiver = new EmailCreationBox("Receiver");
		mailSubject = new EmailCreationBox("Subject");
		mailPriority = new EmailCreationBox("Priority");
		mailContent = new EmailCreationBox("Content");
	    setSize(width,height);
	    setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
	    setTitle(title);
	    add(mailSender);
	    add(mailReceiver);
	    add(mailSubject);
	    add(mailPriority);
	    add(mailContent);
	    JButton send = new JButton("Send");
	    ClickListener listener = new ClickListener();
	    send.addActionListener(listener);
	    add(send);
	    setVisible(true);
	}
    }
    class EmailCreationBox extends JPanel{
	JTextField field;
	EmailCreationBox(String type){
	    setSize(20,300);
	    add(new JLabel(type));
	    field = new JTextField(40);
	    add(field);
	}
	private String getText(){
	    return field.getText();
	}
    }
    class GUI extends JFrame{
	private int height=400;
	private int width=400;
	private String title="Email";
	private int mailLength;
	GUI() throws IOException, FileNotFoundException, ClassNotFoundException{
	    JMenuBar menuBar = new JMenuBar();     
	    setJMenuBar(menuBar);
	    menuBar.add(createFileMenu());
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle(title);
	    setSize(width,height);
	    setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
	    add(new SortPanel());
	    add(emailList());
	    //System.out.println(this.getLayout());
	    setVisible(true);
	    
	    }
    }
	private JPanel emailList() throws IOException, FileNotFoundException, ClassNotFoundException{
	    List<Email> emails = load();
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel,1));
	    for(Email em:emails){
		EmailPanel ep = new EmailPanel(em);
		panel.add(ep);
	    }
	    return panel;
	}

	private JMenuItem createFileMenu() {
      JMenuItem newItem = new JMenuItem("New");      
      ActionListener listener = new NewEmailListener();
      newItem.addActionListener(listener);
      return newItem;
	}
    private JMenuBar menuBar(){
	return null;
	
    }
    private class SortPanel extends JPanel{
	private SortItem date;
	private SortItem sender;
	private SortItem receiver;
	private SortItem subject;
	private SortItem priority;
	SortPanel(){
	    setSize(50,50);
	    setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
	    date = new SortItem("Date");
	    sender = new SortItem("Sender");
	    receiver = new SortItem("Receiver");
	    subject = new SortItem("Subject");
	    priority = new SortItem("Priority");
	    
	    add(new JCheckBox());
	    add(priority);
	    add(sender);
	    add(subject);
	    add(date);
	    add(receiver);
	    
	}
	
    }
    private class SortItem extends JMenuItem{
	SortItem(String name){
	    setText(name);
	    setBorder(new LineBorder(BLACK));
	}
    }
    private class EmailPanel extends JPanel{
	private SortItem date;
	private SortItem sender;
	private SortItem receiver;
	private SortItem subject;
	private SortItem priority;
	EmailPanel(Email e){
	    setSize(50,50);
	    setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
	    
	    add(new JCheckBox());
	    add(new SortItem(Integer.toString(e.priority)));
	    add(new SortItem(e.sender));
	    add(new SortItem(e.subject));
	    add(new SortItem(e.dateSent.toString()));
	    add(new SortItem(e.receiver));
	}
	
    }
    static class Mailbox extends ArrayList implements Serializable{
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
	private Date dateSent;
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
	m.dateSent=new Date();
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
	}else{
	    try{
	InputStream file = new FileInputStream(input);
	List<Email> emails = new ArrayList();
	ObjectInputStream in = new ObjectInputStream(file);
	    emails.add((Email) in.readObject());
	    return emails;
	    }catch(Exception e){
		return Collections.EMPTY_LIST;
	    }
	}
	return Collections.EMPTY_LIST;
    }
    private static void newFile() throws FileNotFoundException, IOException{
	//System.out.println("none");
	File output = new File("input.bin");
	output.createNewFile();
    }
    private static void addToFile(Email m) throws IOException{
	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("input.bin",true));
	writeToFile(m,out);
	out.close();
    }
    private static void writeToFile(Email m,ObjectOutputStream out) throws FileNotFoundException, IOException{
	
	out.writeObject(m);
	
	
    }
    private void testWrite() throws FileNotFoundException, IOException{
	Email createEmail = createEmail();
	//System.out.println(createEmail);
	//writeToFile(createEmail);
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
	//m.testWrite();
	//m.load();
	m.emailGui();
	//m.createEmail();
    }
    
}
