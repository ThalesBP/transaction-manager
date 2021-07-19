package br.thales.tools.transactions.manager.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransferMessage {
    Long fromId;
    Long toId;
    Float value;
}
