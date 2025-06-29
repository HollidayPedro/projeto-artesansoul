package com.artesansoul.artesansoulV3.Models.Produto.comportamental;

import com.artesansoul.artesansoulV3.Models.Cliente;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailParticipacaoObserver implements ParticipacaoObserver {

    private final JavaMailSender mailSender;

    public EmailParticipacaoObserver(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void notificar(Cliente cliente, Feirinha feirinha, String acao) {
        String to = cliente.getEmail(); // Ajuste para o campo de e-mail real
        String subject = "Confirmação de " + acao + " na Feirinha!";
        String text = "Olá " + cliente.getNome() + ",\n\n" +
                "Você acabou de " + acao + " sua participação na feirinha: " + feirinha.getNome() + ".\n\n" +
                "Data: " + feirinha.getDataFormatada() + "\n" +
                "Local: " + feirinha.getLocalizacao() + "\n\n" +
                "Obrigado por utilizar nosso sistema!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
