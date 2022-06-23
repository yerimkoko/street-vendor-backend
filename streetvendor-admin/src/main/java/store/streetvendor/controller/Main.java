package store.streetvendor.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@RequiredArgsConstructor
public class Main {

    @GetMapping("/admin/ping")
    public String ping() {
        return "pong";
    }


}
