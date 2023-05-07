package com.tui.backend.facade;

import com.tui.backend.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RepositoryServiceFacade {

    private static final String BRANCH_EXCEPTION_MESSAGE = "Failed to get branches for repository: ";
    private static final String REPOSITORY_EXCEPTION_MESSAGE = "Failed to get repositories for user: ";

    private final RepositoryService repositoryService;

    public List<RepositoryBranch> getBranches(Repository repository) {
        try {
            return repositoryService.getBranches(repository);
        } catch (IOException e) {
            throw new RuntimeException(BRANCH_EXCEPTION_MESSAGE + repository.getName());
        }
    }

    public List<Repository> getRepositories(String username) {
        try {
            return repositoryService.getRepositories(username);
        } catch (RequestException e) {
            if (HttpStatus.NOT_FOUND.value() == e.getStatus()) {
                throw new UserNotFoundException("User with username " + username + " not found");
            }
            throw new RuntimeException(REPOSITORY_EXCEPTION_MESSAGE + username);
        } catch (IOException e) {
            throw new RuntimeException(REPOSITORY_EXCEPTION_MESSAGE + username);
        }
    }

}