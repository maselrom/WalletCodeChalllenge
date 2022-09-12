package wallet.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.service.WalletService;
import wallet.service.dto.WalletDto;
import wallet.service.dto.WalletTransactionDto;

@RestController
@RequestMapping(value = "/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping(path = "/")
    public WalletDto addWallet(Long playerId) {
        return walletService.createWallet(playerId);
    }

    @PostMapping(path = "/{walletId}")
    public WalletDto transfer(@PathVariable Long walletId, @Valid @RequestBody WalletTransactionDto dto) {
        return walletService.transfer(walletId, dto);

    }

    @GetMapping(path = "/{walletId}")
    public List<WalletTransactionDto> getTransactions(@PathVariable Long walletId) {
        return walletService.getAllTransactions(walletId);
    }
}
