CREATE TABLE Produto (
IdProduto INT PRIMARY KEY,
Valor FLOAT,
Nome VARCHAR(50),
Marca VARCHAR(50),
Descricao VARCHAR(150)
)

CREATE TABLE Estoque (
IdEstoque INT PRIMARY KEY,
Quantidade INT,
qtdMinima INT,
qtdMaxima INT,
FOREIGN KEY(IdEstoque) REFERENCES Produto (IdProduto)
)

CREATE TABLE Item_De_Estoque (
IdEstoque INT PRIMARY KEY,
Entrada DATE,
FOREIGN KEY(IdEstoque) REFERENCES Estoque (IdEstoque)
)

CREATE TABLE Carrinho (
IdVenda INT,
IdProduto INT,
Responsavel VARCHAR(50),
qtdComprada INT,
Saida DATE,
FOREIGN KEY(IdProduto) REFERENCES Item_De_Estoque (IdEstoque)
)

-- TRIGGER QUE INSERE NA TABELA ITEM_DE_ESTOQUE AO CADASTRAR PRODUTO EM ESTOQUE
CREATE TRIGGER tInsert_ESTOQUE
		AFTER INSERT
		ON ESTOQUE
BEGIN
	INSERT INTO ITEM_DE_ESTOQUE (IdESTOQUE,Entrada)
	VALUES (new.IdPRODUTO,date('now'));
END;
-- TRIGGER QUE DESATIVA UM ITEM DE ESTOQUE DELETANDO DE ITEM_DE_ESTOQUE
CREATE TRIGGER tDesactive_ESTOQUE
        AFTER UPDATE OF Status
            ON ESTOQUE
      	FOR EACH ROW
        WHEN new.Status = 0
BEGIN
    DELETE FROM ITEM_DE_ESTOQUE
          WHERE IdESTOQUE = old.IdPRODUTO;
END;
-- TRIGGER QUE ATIVA UM ITEM DE ESTOQUE INSERINDO DENOVO EM ITEM_DE_ESTOQUE
CREATE TRIGGER tActive_ESTOQUE
        AFTER UPDATE OF Status
            ON ESTOQUE
      	FOR EACH ROW
        WHEN new.Status = 1
BEGIN
    INSERT INTO ITEM_DE_ESTOQUE (IdESTOQUE,Entrada)
                VALUES (new.IdPRODUTO,date('now'));
END;
-- TRIGGER QUE REALIZA A ATUALIZAÇÃO DE QUANTIDADE EM ESTOQUE AO REALIZAR UMA VENDA
CREATE TRIGGER tAtualizaESTOQUE
        AFTER INSERT
            ON CARRINHO
BEGIN
    UPDATE ESTOQUE SET Quantidade = (
    	SELECT b.quantidade - a.quantidade
          	FROM CARRINHO a,ESTOQUE b
         	WHERE b.IdPRODUTO = a.IdPRODUTO 
         	AND IdVenda = new.IdVenda
    )
    WHERE IdPRODUTO = new.IdPRODUTO;
END;

-- CODIGOS USADOS NO PROGRAMA

-- **** CLASSE CARRINHODAO ****
-- UTILIZADO PARA VERIFICAR SE JA EXISTE UM "CODIGO" DE VENDA NA TABELA
"Select IdVenda from CARRINHO where IdVenda = " + codigo + ";"

-- VERIFICA SE COM A QUANTIDADE QUE O USUÁRIO ESCOLHEU TEM EM ESTOQUE
"Select (quantidade - " + quantidade + " ) >= 0 as valido from ESTOQUE where idPRODUTO = " + codigo + ";"

-- CADASTRA UM PEDIDO - VENDA
"INSERT INTO CARRINHO (IdVenda, Responsavel, IdPRODUTO, Quantidade, Total, Saida) "
                    + "VALUES (?,?,?,?,?,date('now'));"

-- LISTA TODOS OS PEDIDOS
"SELECT a.IdVenda,a.Responsavel, b.IdPRODUTO, c.Nome, c.Descricao, a.Quantidade, c.Valor, a.Total, a.Saida\n"
                    + " FROM CARRINHO a, ESTOQUE b, PRODUTO c\n"
                    + " WHERE a.IdPRODUTO = b.IdPRODUTO and  a.idPRODUTO=c.id ;";

-- LISTA TODOS OS ITENS DE ESTOQUES ATIVOS ( STATUS = 1 ) E COM QUANTIDADE > 0
"SELECT b.IdPRODUTO, a.Nome, a.Descricao,b.Quantidade,a.Valor\n"
                    + "                    FROM PRODUTO a, ESTOQUE b, ITEM_DE_ESTOQUE c\n"
                    + "                    WHERE a.Id = b.IdPRODUTO\n"
                    + "                    and b.Status=1\n"
                    + "                    and b.IdPRODUTO = c.IdESTOQUE\n"
                    + "                    and b.Quantidade > 0;"

-- LISTA TODOS OS ITENS DE ESTOQUE EM PROMOÇÃO COM QUANTIDADE ACIMA DA QUANTIDADE MAXIMA DECLARADA
"SELECT b.IdPRODUTO, a.Nome, a.Descricao,b.Quantidade,a.Valor\n"
                    + "                    FROM PRODUTO a, ESTOQUE b, ITEM_DE_ESTOQUE c\n"
                    + "                    WHERE a.Id = b.IdPRODUTO\n"
                    + "                    and b.Status=1\n"
                    + "                    and b.IdPRODUTO = c.IdESTOQUE\n"
                    + "                    and b.Quantidade > b.qtdMaxima;";

-- **** CLASSE ESTOQUEDAO ****
-- CADASTRA UM PRODUTO EM ESTOQUE
"INSERT INTO ESTOQUE (IdPRODUTO,Quantidade,qtdMinima,qtdMaxima)"
                    + "VALUES (?,?,?,?);";
-- LISTA ITENS CADASTRADOS EM ESTOQUE E ATIVOS
"SELECT b.IdPRODUTO, a.Nome, b.Quantidade,b.qtdMinima,b.qtdMaxima,c.Entrada\n"
                    + "FROM PRODUTO a, ESTOQUE b, ITEM_DE_ESTOQUE c\n"
                    + "WHERE a.Id = b.IdPRODUTO and b.Status=1 and b.idPRODUTO = c.idESTOQUE;"

-- ALTERA UM ITEM DE ESTOQUE
"UPDATE Estoque SET Quantidade=?,qtdMinima=?,qtdMaxima=?"
                    + "WHERE IdProduto = ?"

-- DESATIVA UM ITEM DE ESTOQUE PARA ATIVA TROCA STATUS PARA 1
"UPDATE Estoque SET Status=0 WHERE IdProduto = ?;"

-- **** CLASSE PRODUTODAO ****
-- CADASTRA UM PRODUTO
"INSERT INTO Produto (nome,descricao,marca,valor)"
                + "VALUES (?,?,?,?);"
-- ALTERA UM PRODUTO
"UPDATE Produto SET Nome=?,Descricao=?,Marca=?,Valor=?"
                    + "WHERE Id = ?"
