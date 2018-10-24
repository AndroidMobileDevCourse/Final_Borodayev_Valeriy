import fetch from 'isomorphic-fetch';

const run = async () => {
  process.env.NODE_TLS_REJECT_UNAUTHORIZED = 0;
  while (true) {
    await fetch('https://campus.iitu.kz'); // eslint-disable-line
  }
};

run();
