package com.example.chat_backend.service.impl;

import com.example.chat_backend.domain.ChatMessage;
import com.example.chat_backend.repository.ChatMessageRepository;
import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import com.example.chat_backend.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageDTO create(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = new ChatMessage(chatMessageDTO);
        chatMessage.setId(null);
        chatMessage = chatMessageRepository.save(chatMessage);
        return new ChatMessageDTO(chatMessage);
    }

    @Override
    public ChatMessageDTO create(String payload) {
        ChatMessageDTO chatMessageDTO = ConvertUtil.convertToObject(payload, ChatMessageDTO.class);
        return create(chatMessageDTO);
    }

    @Override
    public List<ChatMessageDTO> query(Long roomId, Pageable pageable) {
        return chatMessageRepository.findAllByRoomId(roomId, pageable).map(ChatMessageDTO::new).getContent();
    }
}
