const { faker } = require('@faker-js/faker');

describe('Criando cliente com biblioteca que gera dados aleatórios', () => {
  const randomName = faker.name.fullName();
  const randomCPF = faker.string.numeric(11);
  const randomEmail = faker.internet.email();

  it('Deve retornar um status 201 e verificar o cliente no banco de dados', () => {
    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/v1/client/create',
      body: {
        name: randomName,
        cpf: randomCPF,
        email: randomEmail,
        active: true
      },
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(201);

      cy.task('queryDatabase', {
        query: 'SELECT * FROM client WHERE cpf = $1',
        params: [randomCPF]
      }).then((rows) => {
        expect(rows).to.have.lengthOf(1);
        expect(rows[0].name).to.eq(randomName);
        expect(rows[0].email).to.eq(randomEmail);
        expect(rows[0].active).to.be.true;
      });
    });
  });
});

describe('Testando Validação de Dados', () => {
  it('Deve retornar status 409 quando os dados estão incompletos', () => {
    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/v1/client/create',
      body: {
        name: "",
        cpf: "002470355489",
        email: "not-an-email",
        active: true
      },
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(409);
    });
  });
});

describe('Testando a Criação de Conta para Cliente Existente', () => {
  it('Deve criar uma conta para o cliente existente com id_client e retornar status 201', () => {
    const accountData = {
      accountType: 'MULTI',
      idClient: 1,
      active: true
    };

    cy.request({
      method: 'POST',
      url: 'http://localhost:8080/v1/account/create',
      body: accountData,
      failOnStatusCode: false
    }).then((response) => {
      cy.log(`Status: ${response.status}`);
      cy.log(`Response Body: ${JSON.stringify(response.body)}`);

      expect(response.status === 201);
          });
        });
      });


describe('Testando a criação de conta com campo faltando', () => {
    it('Deve retornar um erro não permitindo a criação da conta sem os campos', () => {
        const accountData = {
        accountType: '',
        idClient: 9,
        active: true
        };

   cy.request({
         method: 'POST',
         url: 'http://localhost:8080/v1/account/create',
         body: accountData,
         failOnStatusCode: false
       }).then((response) => {
         cy.log(`Status: ${response.status}`);
         cy.log(`Response Body: ${JSON.stringify(response.body)}`);

         expect(response.status === 400);
             });
           });
});

describe('Depositando 100 reais em uma conta', () => {
    it('deve depositar 100 reais na primeira conta', () => {
       const transactionData = {
                   amount: 100.0,
                   account: 1,
                   transactionType: "DEPOSIT",
                   originalTransaction: null
               };

               cy.request({
                   method: 'POST',
                   url: 'http://localhost:8080/v1/transaction',
                   body: transactionData,
                   failOnStatusCode: false
               }).then((response) => {
                   cy.log(`Status: ${response.status}`);
                   cy.log(`Response Body: ${JSON.stringify(response.body)}`);

                   expect(response.status).to.eq(200);
               });
           });
       });

describe('Tentando Depositar 100 reais em uma conta mas com um campo em branco', () => {
    it('não deve depositar 100 reais na primeira conta', () => {
       const transactionData = {
                   amount: 100.0,
                   account: 1,
                   transactionType: "",
                   originalTransaction: null
               };

               cy.request({
                   method: 'POST',
                   url: 'http://localhost:8080/v1/transaction',
                   body: transactionData,
                   failOnStatusCode: false
               }).then((response) => {
                   cy.log(`Status: ${response.status}`);
                   cy.log(`Response Body: ${JSON.stringify(response.body)}`);

                   expect(response.status).to.eq(400);
               });
           });
       });

describe('testando uma tranferencia de 50 reais para a primeira conta', () => {
    it('deve tranferir 50 reais para a primeira conta com sucesso', () => {
        const transactionData = {
        amount: 50.0,
        account:1,
        transactionType: "TRANSFER",
        originalTransaction: null
        };

        cy.request({
            method: 'POST',
            url: '/v1/transaction',
            body: transactionData,
            failOnStatusCode: false
        }).then((response) => {
            cy.log('Status: $(response.status)');
            cy.log('response Body: ${JSON.stringify(response.body)}');

            expect(response.status).to.eq(200);
        })
    })
})

describe('Testando uma transferencia de 50 reais mas com um dos campos não preenchidos', () => {
    it('Não deve fazer a tranferencia', () => {
        const transactionData = {
        amount: 50.0,
        account: 1,
        transactionType: "",
        originalTransaction: null
        };

        cy.request({
            method: 'POST',
            url: '/v1/transaction',
            body: transactionData,
            failOnStatusCode: false
        }).then((response) => {
            cy.log('Status: $(response.status)');
            cy.log('response Body: ${JSON.stringify(response.body)}');

            expect(response.status).to.eq(400);
        })
    })
})

describe('Testando um cancelamento de 10 reais', () => {
    it('Deve cancelar 10 reais de uma transferencia', () => {
        const cancelamento = {
        amount: 10.0,
        account: 1,
        transactionType: "REFUND",
        originalTransaction: 1,
        };

        cy.request({
            method: 'POST',
            url: '/v1/transaction',
            body: cancelamento,
            failOnStatusCode: false
        }).then((response) => {
            cy.log('Status: ${response.status}');
            cy.log('response Body: ${JSON.stringify(response.body)}');

            expect(response.status).to.eq(200);
        })
    })
})

describe('Testando um cancelamento de 10 reais mas com um campo vazio', () => {
    it.only('Não deve cancelar 10 reais de uma transferencia', () => {
        const cancelamento = {
        amount: 10.0,
        account: "",
        transactionType: "REFUND",
        originalTransaction: 1,
        };

        cy.request({
            method: 'POST',
            url: '/v1/transaction',
            body: cancelamento,
            failOnStatusCode: false
        }).then((response) => {
            cy.log('Status: ${response.status}');
            cy.log('response Body: ${JSON.stringify(response.body)}');

            expect(response.status).to.eq(400);
        })
    })
})