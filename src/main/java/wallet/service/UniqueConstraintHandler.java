package wallet.service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//It should really rare case, but it can happen. Also, we should specify index, cos maybe we need special processing
// only for some unique indexes
public class UniqueConstraintHandler {

    private final TransactionalHelper transactionalHelper;

    public <T> T execute(Supplier<T> function, String uniqueIndexName,
            Function<DataIntegrityViolationException, RuntimeException> exceptionMapper) {
        try {
            return transactionalHelper.createTransactionAndProceed(function);
        } catch (DataIntegrityViolationException e) {
            throw Optional.ofNullable(e.getMessage())
                    .filter(message -> message.toLowerCase().contains(uniqueIndexName.toLowerCase()))
                    .map((existingMessage) -> exceptionMapper.apply(e)).orElse(e);
        }
    }

}
