package es.uca.articulosconexionmorada.sistemaCompanero.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public ChatService() {}

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    public List<Chat> findAllChatsByUserId(String uuidUser) {
        List<Chat> chats = chatRepository.findByUuidUser1OrUuidUser2(uuidUser, uuidUser);
        return chats;
    }

    public Optional<Chat> findById(UUID id) {
        return chatRepository.findById(id);
    }

    public Optional<Chat> findByUuidUser1AndUuidUser2(String uuidUser1, String uuidUser2) {
        return chatRepository.findByUuidUser1AndUuidUser2(uuidUser1, uuidUser2);
    }
}
