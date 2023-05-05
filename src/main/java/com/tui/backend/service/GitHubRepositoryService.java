package com.tui.backend.service;

import com.tui.backend.dto.RepositoryInfo;
import com.tui.backend.exception.UserNotFoundException;
import com.tui.backend.mapper.BranchConverter;
import com.tui.backend.mapper.UserInfoConverter;
import lombok.RequiredArgsConstructor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubRepositoryService implements VcsRepository {

    private static final String USER_NOT_FOUND_DESCRIPTION = "Failed to get repositories for user: ";

    private final RepositoryService repositoryService;
    private final UserInfoConverter userInfoConverter;
    private final BranchConverter branchConverter;

    //todo: think about using CompletableFuture for async cals
    @Override
    public List<RepositoryInfo> doFindAllBy(String username) {
        List<Repository> repositories = getRepositories(username);
        List<Repository> notForkRepos = filterNotForks(repositories);
        return notForkRepos.parallelStream()
                .map(this::mapToUserRepositoryInfo)
                .collect(Collectors.toList());
    }

    private RepositoryInfo mapToUserRepositoryInfo(Repository repository) {
        List<RepositoryBranch> repositoryBranches = getBranches(repository);
        RepositoryInfo repositoryInfo = userInfoConverter.toDto(repository);
        repositoryInfo.setBranches(repositoryBranches.stream()
                .map(branchConverter::toDto)
                .collect(Collectors.toList()));
        return repositoryInfo;
    }

    private List<Repository> filterNotForks(List<Repository> repositories) {
        return repositories.stream()
                .filter(Predicate.not(org.eclipse.egit.github.core.Repository::isFork))
                .collect(Collectors.toList());
    }

    private List<RepositoryBranch> getBranches(Repository repository) {
        try {
            return repositoryService.getBranches(repository);
        } catch (IOException e) {
            throw new RuntimeException("Failed to get branches for repository: " + repository.getName());
        }
    }

    private List<Repository> getRepositories(String username) {
        try {
            return repositoryService.getRepositories(username);
        } catch (RequestException e) {
            if (HttpStatus.NOT_FOUND.value() == e.getStatus()) {
                throw new UserNotFoundException("User with username " + username + " not found");
            }
            throw new RuntimeException(USER_NOT_FOUND_DESCRIPTION + username);
        } catch (IOException e) {
            throw new RuntimeException(USER_NOT_FOUND_DESCRIPTION + username);
        }
    }

}