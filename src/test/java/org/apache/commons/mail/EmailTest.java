package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	private static final String[] TEST_EMAIL = {"ab@bc.com", "a.b@c.org", 
			"abcdefghijklmnopqrstuvwyz@abcdefghijklmnopqrstuvwxyz.com.bd"};

	private String[] testValidChars = {" ", "a", "A", "\uc5ec", "0123456789", "01234567890123456789", "\n"};
	
	private String[] testNames = {"Ron", "Josh", "Drake", "Sammy", "Jane", "Sarah"};
	
	private String testNull = null;
	
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception{
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception{
		
	}

	@Test
	public void testAddBcc() throws Exception{
		email.addBcc(TEST_EMAIL);
		
		assertEquals(3, email.getBccAddresses().size());
	}

	@Test
	public void testAddCc() throws Exception{
		email.addCc(TEST_EMAIL[1]);
		
		assertEquals(3, email.getCcAddresses().size());
	}

	@Test
	public void testAddHeader() throws Exception{
		email.addHeader(testValidChars[0], testValidChars[4]);
		
	}

	@Test
	public void testAddReplyTo() throws Exception{
		email.addReplyTo(TEST_EMAIL[1], testValidChars[1]);
	}

	@Test
	public void testBuildMimeMessage() throws Exception{
		email.setHostName("hostName");
        email.setSmtpPort(1234);
        email.setFrom("a@b.com");
        email.addTo("a@b.com");
        email.setSubject("test mail");
        email.setCharset("ISO-8859-1");
        email.setContent("test content", "text/plain");
        email.addCc("a@b.com");
        email.addBcc("a@b.com");
        email.addHeader("test", "abc");
        email.addReplyTo(TEST_EMAIL[1], testValidChars[1]);
        email.buildMimeMessage();
	}

	@Test
	public void testGetHostName() throws Exception{
		email.setHostName("192.168.1.1");
		
		String hostName = email.getHostName();
		
		assertEquals("192.168.1.1", hostName);
		
		email.setHostName(null);
		String host = email.getHostName();
		assertEquals(null, host);
	}

	@Test
	public void testGetMailSession() throws Exception{
		Properties properties = new Properties(System.getProperties());
        properties.setProperty(EmailConstants.MAIL_TRANSPORT_PROTOCOL, EmailConstants.SMTP);
        properties.setProperty("a@b.com", "a@b.com");
        properties.setProperty(EmailConstants.MAIL_HOST, testValidChars[1]);
        properties.setProperty(EmailConstants.MAIL_DEBUG, String.valueOf(false));
        Session mySession = Session.getInstance(properties, null);
        this.email.setMailSession(mySession);
        assertEquals(mySession, this.email.getMailSession());
        
        try
        {
            this.email = new EmailConcrete();
            this.email.send();
        }
        catch (EmailException e)
        {
            assertTrue(true);
        }
	}

	@Test
	public void testGetSentDate() throws Exception{
		email.setSentDate(null);
		Date sentDate = email.getSentDate();
		
	}

	@Test
	public void getSocketConnectionTimeout() {
		email.setSocketConnectionTimeout(10);
		int temp = email.getSocketConnectionTimeout();
	}

	@Test
	public void testSetFrom() throws Exception{
		email.setFrom("a@b.com");
	}

}
