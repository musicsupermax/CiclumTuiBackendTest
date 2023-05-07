package com.tui.backend.controller;

import com.tui.backend.dto.RepositoryInfo;
import com.tui.backend.facade.VcsRepositoryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/repos")
@RequiredArgsConstructor
@Log4j2
public class RepositoryController {

    private final VcsRepositoryFacade vcsRepositoryFacade;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RepositoryInfo>> findAllByUsername(@PathVariable String username) {
        log.info("Retrieving vcsRepository info for user with username: " + username);
        var repositoryInfo = vcsRepositoryFacade.findAllBy(username);
        return ResponseEntity.ok()
                .body(repositoryInfo);
    }

}