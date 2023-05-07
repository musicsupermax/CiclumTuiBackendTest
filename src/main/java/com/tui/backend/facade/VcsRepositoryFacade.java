package com.tui.backend.facade;

import com.tui.backend.constant.VcsRepositoryEnum;
import com.tui.backend.container.VcsRepositoryStrategyContainer;
import com.tui.backend.dto.RepositoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VcsRepositoryFacade {

    private final VcsRepositoryStrategyContainer vcsRepositoryStrategyContainer;

    public List<RepositoryInfo> findAllBy(String username) {
        var githubRepository = vcsRepositoryStrategyContainer.getFromContainer(VcsRepositoryEnum.GITHUB);
        return githubRepository.doFindAllBy(username);
    }

}