INSERT INTO Produto (descricao, valor, _status) VALUES
('Arroz 5kg', 21.90, 'ATIVO'),
('Feijão Preto 1kg', 6.50, 'ATIVO'),
('Óleo de Soja 900ml', 7.20, 'INATIVO'),
('Açúcar Refinado 1kg', 4.80, 'ATIVO'),
('Macarrão Espaguete', 3.75, 'ATIVO'),
('Café Torrado 500g', 12.99, 'ATIVO'),
('Farinha de Trigo 1kg', 5.20, 'INATIVO'),
('Leite Integral 1L', 4.50, 'ATIVO'),
('Margarina 500g', 6.10, 'ATIVO'),
('Detergente Neutro', 2.30, 'ATIVO');

INSERT INTO Marmita (descricao, max_mistura, max_guarnicao, valor, _status) VALUES
('Marmita P', 1, 1, 12.00, 'ATIVO'),
('Marmita M', 2, 2, 15.00, 'ATIVO'),
('Marmita G', 3, 2, 18.50, 'ATIVO'),
('Combo', 3, 3, 25.00, 'ATIVO'),
('Executiva', 1, 1, 14.00, 'ATIVO'),
('Mini', 1, 1, 16.00, 'ATIVO'),
('Marmita Vegana', 2, 2, 17.50, 'ATIVO'),
('Marmita Kids', 1, 1, 10.00, 'ATIVO'),
('Marmita Executiva', 2, 2, 20.00, 'ATIVO'),
('Marmita Premium', 3, 3, 28.90, 'ATIVO');

INSERT INTO Bairro (nome, valor_entrega, _status) VALUES
('Centro', 5.00, 'ATIVO'),
('Jardim das Flores', 6.50, 'ATIVO'),
('Vila Nova', 7.00, 'ATIVO'),
('Morada do Sol', 8.00, 'ATIVO'),
('Bela Vista', 6.00, 'ATIVO'),
('Parque Industrial', 9.50, 'ATIVO'),
('Jardim América', 7.25, 'ATIVO'),
('Vila Esperança', 5.75, 'ATIVO'),
('Residencial São João', 10.00, 'ATIVO'),
('Santa Rita', 6.80, 'ATIVO');

INSERT INTO Mensalista (id_bairro, nome, CPF, conta, endereco, _status) VALUES
(1, 'João Silva', '123.456.789-00', 150.00, 'Rua A, 123 - Centro', 'ATIVO'),
(2, 'Maria Oliveira', '234.567.890-11', 200.00, 'Rua das Rosas, 45 - Jardim das Flores', 'ATIVO'),
(3, 'Carlos Souza', '345.678.901-22', 50.00, 'Av. Brasil, 789 - Vila Nova', 'ATIVO'),
(4, 'Ana Paula', '456.789.012-33', 0.00, 'Rua Sol Nascente, 321 - Morada do Sol', 'ATIVO'),
(5, 'Ricardo Santos', '567.890.123-44', 80.00, 'Rua das Palmeiras, 98 - Bela Vista', 'ATIVO'),
(6, 'Fernanda Lima', '678.901.234-55', 120.00, 'Av. das Indústrias, 100 - Parque Industrial', 'ATIVO'),
(7, 'Bruno Almeida', '789.012.345-66', 95.50, 'Rua Goiás, 56 - Jardim América', 'ATIVO'),
(8, 'Camila Rocha', '890.123.456-77', 30.75, 'Travessa Esperança, 10 - Vila Esperança', 'ATIVO'),
(9, 'Lucas Pereira', '901.234.567-88', 60.00, 'Rua São João, 200 - Residencial São João', 'ATIVO'),
(10, 'Juliana Fernandes', '012.345.678-99', 75.25, 'Av. Santa Rita, 300 - Santa Rita', 'ATIVO');





