| Client       | Transaction         | Account         |
|--------------|---------------------|-----------------|
| id_user (PK) | id_transaction (PK) | id_account (PK) |
| cpf          | id_account (FK)     | id_user (FK)    |
| name         | amount              | account_type    |
| email        | transaction_type    |                 |

<div style="text-align: center;">
  <img src="doc/esquema.png">
  <p>Figura 1: Relação entre entidades</p>
</div>