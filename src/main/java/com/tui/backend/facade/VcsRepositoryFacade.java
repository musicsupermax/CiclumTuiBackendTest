package com.tui.backend.facade;

import com.tui.backend.dto.RepositoryInfo;
import com.tui.backend.service.VcsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VcsRepositoryFacade {

    private final VcsRepository vcsRepository;

    public List<RepositoryInfo> findAllBy(String username) {
        return vcsRepository.doFindAllBy(username);
    }

}