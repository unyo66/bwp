package com.bwp.app.service;

import com.bwp.app.domain.Article;
import com.bwp.app.domain.UserAccount;
import com.bwp.app.dto.ArticleDto;
import com.bwp.app.dto.ArticleWithCommentsDto;
import com.bwp.app.dto.CommentDto;
import com.bwp.app.dto.UserAccountRequest;
import com.bwp.app.repository.ArticleRepository;
import com.bwp.app.repository.CommentRepository;
import com.bwp.app.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;

    public void addUserAccount(UserAccountRequest userAccountRequest, String address_detail) {
        userAccountRepository.save(userAccountRequest.toDto(address_detail).toEntity());
    }
}
