package com.example.chat_backend.service;

import com.example.chat_backend.service.dto.user.AppUserDTO;
import com.example.chat_backend.service.dto.user.ExistingContactQuery;
import com.example.chat_backend.service.dto.user.PublicContactQuery;
import org.springframework.data.domain.Page;

public interface UserContactService {
    /**
     * Search public contact
     * @param publicContactQuery publicContactQuery
     * @return List<AppUserDTO>
     */
    Page<AppUserDTO> searchPublicContact(PublicContactQuery publicContactQuery);

    /**
     * Search existing contact of current user
     * @param existingContactQuery publicContactQuery
     * @return List<AppUserDTO>
     */
    Page<AppUserDTO> searchExistingContact(ExistingContactQuery existingContactQuery);
}
