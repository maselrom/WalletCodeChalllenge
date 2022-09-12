package wallet.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.service.PlayerService;
import wallet.service.dto.PlayerDto;

@RestController
@RequestMapping(value = "/v1/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping(path = "")
    public PlayerDto registerPlayer(@Valid @RequestBody PlayerDto playerDto) {
        return playerService.registerPlayer(playerDto);
    }

}
