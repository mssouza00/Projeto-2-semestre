select * from Produto;
SELECT * FROM Produto WHERE NOME LIKE 'areia%';

DROP TABLE Produto;
CREATE TABLE Produto (
    Id INTEGER PRIMARY KEY,
    Nome VARCHAR(150) not null,
    Descricao VARCHAR(250),
    Marca VARCHAR(150),
    Valor FLOAT not null
);

DROP TABLE Estoque;
CREATE TABLE Estoque (
    IdProduto INTEGER PRIMARY KEY,
    Quantidade INT not null,
    qtdMinima INT not null,
    qtdMaxima INT not null,
    Status INT not null,
    FOREIGN KEY (IdProduto) REFERENCES Produto(Id)
);

DROP TABLE Produto_Estoque;
CREATE TABLE Produto_Estoque (
    IdEstoque INTEGER PRIMARY KEY,
    Entrada NVARCHAR(11) not null,
    FOREIGN KEY (IdEstoque) REFERENCES Estoque(IdProduto)
);

UPDATE Produto set Nome = 'Areia Fina' where Id = 1;

DROP TABLE Carrinho;
CREATE TABLE Carrinho (
    Id INT not null,
    Responsavel VARCHAR(150) not null,
    IdProduto INTEGER PRIMARY KEY,
    NomeProduto VARCHAR(150) not null,
    Quantidade INT not null,
    Valor FLOAT not null,
    Total FLOAT not null,
    Data_Saida VARCHAR(10) not null,
    FOREIGN KEY (IdProduto) REFERENCES Estoque(IdProduto)
);

SELECT * FROM Produto WHERE Id = 1;



CREATE TRIGGER inserirProduto_Estoque AFTER INSERT 
ON Estoque
BEGIN
   INSERT INTO Produto_Estoque (IdEstoque, Entrada) VALUES (old.IdProduto,datetime('now'));
END;

create trigger inserirProduto_Estoque after insert on Estoque
begin
    insert into Produto_Estoque (IdEstoque, Entrada) 
        values(new.nome,datetime('now'));
end;

insert into Produto_Estoque (IdEstoque, Entrada) 
        values('fdfdfd',(SELECT date('now')));