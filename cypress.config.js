const { Client } = require('pg');

module.exports = {
  e2e: {
    setupNodeEvents(on, config) {
      on('task', {
        async queryDatabase({ query, params }) {
          const client = new Client({
            host: 'localhost',
            user: 'postgres',
            password: 'Postgres2024!',
            database: 'ap-bank',
            port: 5432
          });

          try {
            await client.connect();
            console.log('Conectado ao banco de dados com sucesso!');

            const res = await client.query(query, params);
            return res.rows;
          } catch (error) {
            console.error('Erro ao consultar o banco de dados:', error);
            throw error;
          } finally {
            await client.end();
          }
        }
      });

      return config;
    },
  },
};
