package wallet.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import wallet.controller.PlayerController;
import wallet.controller.WalletController;
import wallet.domain.TransactionType;
import wallet.domain.Wallet;
import wallet.exception.WalletException;
import wallet.repository.WalletRepository;
import wallet.repository.WalletTransactionRepository;
import wallet.service.PlayerService;
import wallet.service.WalletServiceImpl;
import wallet.service.dto.PlayerDto;
import wallet.service.dto.WalletDto;
import wallet.service.dto.WalletTransactionDto;

@SpringBootTest
class UniqueIndexIntegrationTest {

    private final UUID uuid = UUID.randomUUID();

    @Autowired
    WalletController walletController;
    @Autowired
    PlayerController playerController;
    @Autowired
    private WalletServiceImpl walletService;
    @Autowired
    private PlayerService playerService;
    @SpyBean
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Test
    void uniqueIndexConstraintViolationTest() {

        Wallet dbWallet = new Wallet();
        dbWallet.setRegistrationDate(LocalDateTime.now());
        dbWallet.setAmount(BigDecimal.ZERO);
        playerService.registerPlayer(new PlayerDto("name"));
        walletService.createWallet(1L);
        walletService.createWallet(1L);
        walletService.transfer(2L,
                new WalletTransactionDto(uuid, TransactionType.CREDIT, BigDecimal.ONE));
        doReturn(false).when(walletTransactionRepository).existsByBusinessId(any());
        Exception ex = assertThrows(WalletException.class,
                () -> walletService.transfer(3L,
                        new WalletTransactionDto(uuid, TransactionType.CREDIT, BigDecimal.ONE)));
        assertEquals(String.format("409 WalletTransaction with id %s already exists", uuid), ex.getMessage());
    }

    @Test
    public void playerControllerTest() {
        PlayerDto playerDto = playerController.registerPlayer(new PlayerDto("testName"));
        assertEquals("testName", playerDto.name());
    }

    @Test
    public void playerWalletControllerTest() {
        WalletDto initialDto = walletController.addWallet(1L);
        WalletDto walletDto = walletController.transfer(initialDto.id(),
                new WalletTransactionDto(uuid, TransactionType.CREDIT, BigDecimal.ONE));
        List<WalletTransactionDto> transactions = walletController.getTransactions(initialDto.id());
        assertAll(
                () -> assertEquals(BigDecimal.ZERO.setScale(0), initialDto.amount()),
                () -> assertEquals(BigDecimal.ONE.setScale(2), walletDto.amount()),
                () -> assertEquals(1, transactions.size()));
    }

}


