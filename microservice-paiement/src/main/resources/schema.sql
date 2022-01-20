
CREATE TABLE paiement

(
  
  id INT PRIMARY KEY,
  
  id_commande INT NOT NULL,
  
  montant INT NOT NULL,
  
  numero_carte BIGINT NOT NULL
  
  
); 