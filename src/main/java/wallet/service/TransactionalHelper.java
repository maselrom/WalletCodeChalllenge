package wallet.service;

import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalHelper {
    //Transactional method here for best controlling begin and end of transactions where DataIntegrityViolationException
    //is possible. wallet/integration/UniqueIndexIntegrationTest.java test possible DataIntegrityViolationException
    // throwing
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public <T> T createTransactionAndProceed(Supplier<T> supplier) {
        return supplier.get();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public <T> T createReadTransactionAndProceed(Supplier<T> supplier) {
        return supplier.get();
    }

}
