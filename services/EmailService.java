package tn.antraFormationSpringBoot.services;

import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.antraFormationSpringBoot.entites.Apprenant;
import tn.antraFormationSpringBoot.entites.Formateur;
import tn.antraFormationSpringBoot.entites.Manager;
import tn.antraFormationSpringBoot.repository.ApprenantRepository;
import tn.antraFormationSpringBoot.repository.FormateurRepository;
import tn.antraFormationSpringBoot.repository.ManagerRepository;

@Service
public class EmailService {
    
    @Autowired
    private ApprenantRepository apprenantRepository;
    
    @Autowired
    private ApprenantService apprenantService;

    public void sendPasswordByEmail(Long id) throws Exception {
    	Optional<Apprenant> optionalApprenant = apprenantRepository.findById(id);
        if (optionalApprenant.isPresent()) {
            Apprenant apprenant = optionalApprenant.get();
            String password = apprenantService.getMotDePasseNonCrypte(apprenant.getId());
            
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("test20.test20090@gmail.com", "zzegiyikrnfwkdop");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("test20.test20090@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(apprenant.getEmail()));
            message.setSubject("Password");
            message.setText("Your password is: " + password);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    } else {
        throw new Exception("No utilisateur found with id=" + id);
    }
}

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private FormateurService formateurService;

    public void sendPasswordByEmailF(Long id) throws Exception {
        Optional<Formateur> optionalFormateur = formateurRepository.findById(id);
        if (optionalFormateur.isPresent()) {
            Formateur formateur = optionalFormateur.get();
            String password = formateurService.getMotDePasseNonCrypte(formateur.getId());

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "*");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("test20.test20090@gmail.com", "zzegiyikrnfwkdop");
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("test20.test20090@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(formateur.getEmail()));
                message.setSubject("Password");
                message.setText("Your password is: " + password);

                Transport.send(message);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new Exception("No formateur found with id=" + id);
        }
    }

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;

    public void sendPasswordByEmailM(Long id) throws Exception {
        Optional<Manager> optionalManager = managerRepository.findById(id);
        if (optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            String password = managerService.getMotDePasseNonCrypte(manager.getId());

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "*");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("test20.test20090@gmail.com", "zzegiyikrnfwkdop");
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("test20.test20090@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(manager.getEmail()));
                message.setSubject("Password");
                message.setText("Your password is: " + password);

                Transport.send(message);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new Exception("No manager found with id=" + id);
        }
    }
}
