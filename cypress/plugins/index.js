const pg = require('pg/promise');

module.exports = (on, config) => {
  on('task', {
    async queryDatabase({ query, params }) {
      const connection = await pg.createConnection({
        host: 'localhost:5432',
        user: 'postgres',
        password: 'Postgres2024!',
        database: 'ap-bank'
      });

      const [rows] = await connection.execute(query, params);
      await connection.end();
      return rows;
    }
  });
};
