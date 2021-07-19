package br.thales.tools.transactions.manager.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CashMessage {
    Long id;
    Float value;
}
