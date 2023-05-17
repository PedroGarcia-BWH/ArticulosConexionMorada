package es.uca.articulosconexionmorada.sistemaCompanero.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public ChatService() {}

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
}
