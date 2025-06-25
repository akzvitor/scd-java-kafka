INSERT INTO product (code, name, price, quantity) VALUES
  ('ABC123', 'Produto Teste 1', 10.50, 5),
  ('DEF456', 'Produto Teste 2', 15.00, 2),
  ('XYZ789', 'Produto Teste 3', 7.99, 0)
ON CONFLICT (code) DO NOTHING;