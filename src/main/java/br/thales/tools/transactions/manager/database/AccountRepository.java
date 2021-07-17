package br.thales.tools.transactions.manager.database;

import br.thales.tools.transactions.manager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
