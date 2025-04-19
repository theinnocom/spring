package com.inTrack.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vijayan
 */

@RestController
public class DomainCheck {

    @GetMapping(value = "/ping")
    public String ping() {
        return "pong";
    }
}
