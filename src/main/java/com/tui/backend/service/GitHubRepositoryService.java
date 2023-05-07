package com.tui.backend.service;

import com.tui.backend.dto.RepositoryInfo;
import com.tui.backend.facade.RepositoryServiceFacade;
import com.tui.backend.mapper.BranchConverter;
import com.tui.backend.mapper.RepositoryInfoConverter;
import lombok.RequiredArgsConstructor;
import org.eclipse.egit.github.core.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubRepositoryService implements VcsRepository {

    private final RepositoryServiceFacade repositoryServiceFacade;
    private final RepositoryInfoConverter repositoryInfoConverter;
    private final BranchConverter branchConverter;

    //todo: think about using CompletableFuture for async calls
    @Override
    public List<RepositoryInfo> doFindAllBy(String username) {
        var repositories = repositoryServiceFacade.getRepositories(username);
        var notForkRepos = filterNotForks(repositories);
        return notForkRepos.parallelStream()
                .map(this::mapToUserRepositoryInfo)
                .collect(Collectors.toList());
    }

    private RepositoryInfo mapToUserRepositoryInfo(Repository repository) {
        var repositoryBranches = repositoryServiceFacade.getBranches(repository);
        var repositoryInfo = repositoryInfoConverter.toDto(repository);
        repositoryInfo.setBranches(repositoryBranches.stream()
                .map(branchConverter::toDto)
                .collect(Collectors.toList()));
        return repositoryInfo;
    }

    private List<Repository> filterNotForks(List<Repository> repositories) {
        return repositories.stream()
                .filter(Predicate.not(Repository::isFork))
                .collect(Collectors.toList());
    }

}