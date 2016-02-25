CREATE TABLE Produto (
Id INTEGER PRIMARY KEY,
Nome VARCHAR (150) NOT NULL,
Descricao VARCHAR (250),
Marca VARCHAR (150),
Valor FLOAT NOT NULL
);

CREATE TABLE Estoque (
IdProduto INTEGER PRIMARY KEY,
Quantidade INT NOT NULL,
QtdMinima INT NOT NULL,
QtdMaxima INT NOT NULL,
Status INT DEFAULT (1),
FOREIGN KEY (IdProduto) REFERENCES Produto (Id)
);

CREATE TABLE Produto_Estoque (
IdEstoque INTEGER PRIMARY KEY,
Entrada DATETIME,
FOREIGN KEY (IdEstoque) REFERENCES Estoque (IdProduto) 
);

CREATE TABLE Carrinho (
IdVenda INT NOT NULL,
Responsavel VARCHAR (150) NOT NULL,
IdProduto INTEGER,
Quantidade INT,
Total FLOAT NOT NULL,
Saida DATETIME,
FOREIGN KEY (IdProduto) REFERENCES Estoque (IdProduto) 
);

CREATE TRIGGER tInsert_Estoque
AFTER INSERT
ON Estoque
BEGIN
INSERT INTO Produto_Estoque (
IdEStoque,
Entrada
)
VALUES (
new.IdProduto,
date('now') 
);
END;

CREATE TRIGGER tDesactive_Estoque
AFTER UPDATE OF Status
ON Estoque
FOR EACH ROW
WHEN new.Status = 0
BEGIN
DELETE FROM Produto_Estoque
WHERE IdEstoque = old.IdProduto;
END;

CREATE TRIGGER tActive_Estoque
AFTER UPDATE OF Status
ON Estoque
FOR EACH ROW
WHEN new.Status = 1
BEGIN
INSERT INTO Produto_Estoque (
IdEstoque,
Entrada
)
VALUES (
new.IdProduto,
date('now') 
);
END;

