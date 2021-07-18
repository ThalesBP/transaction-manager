package br.thales.tools.transactions.manager.database;

import br.thales.tools.transactions.manager.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacionRepository extends JpaRepository<Transaction, Long> {
}
