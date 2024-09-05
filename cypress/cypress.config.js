const pgp = require('pg-promise')();
const { db } = require('./config/dbConfig.js');

module.exports = {
  e2e: {
    baseUrl: 'http://localhost:8080',
    setupNodeEvents(on, config) {
      on('task', {
        queryDatabase({ query, params }) {
          return db.any(query, params);
        },
      });
    },
    defaultCommandTimeout:20000,
    },
};
