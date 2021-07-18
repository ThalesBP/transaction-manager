CREATE TABLE main.`transaction` (
	ID BIGINT auto_increment NOT NULL,
	From_Account BIGINT NULL,
	To_Account BIGINT NULL,
	`Type` varchar(100) NULL,
	Valeu FLOAT NOT NULL,
	`Date` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT transaction_pk PRIMARY KEY (ID),
	CONSTRAINT from_FK FOREIGN KEY (From_Account) REFERENCES main.account(ID),
	CONSTRAINT to_FK FOREIGN KEY (To_Account) REFERENCES main.account(ID)
)
ENGINE=InnoDB
DEFAULT CHARSET=ascii
COLLATE=ascii_bin;
