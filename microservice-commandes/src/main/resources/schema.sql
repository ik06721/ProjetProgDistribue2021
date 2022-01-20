
CREATE TABLE commande

(
  
  id INT PRIMARY KEY,
  
  product_id INT NOT NULL,
  
  date_commande DATETIME,
  
  quantite INT NOT NULL,
  
  commande_payee BOOLEAN
  
); 