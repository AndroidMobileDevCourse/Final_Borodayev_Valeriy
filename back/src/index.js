// @flow

import express from 'express';
// $FlowFixMe
import bodyParser from 'body-parser';
// $FlowFixMe
import cookieParser from 'cookie-parser';
// $FlowFixMe
import morgan from 'morgan';
import { User } from './User';
import DB from './db';

DB.init();

const PORT = process.env.PORT || 8090;
const app = express();

app.use(bodyParser.json({ type: 'application/json' }));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(morgan('tiny'));

// $FlowFixMe
app.post('/login', async (req, res) => {
  const { username, password } = req.body;
  const user = await User.findOne({ username });
  if (user && username === user.username && password === user.password) {
    res.send(user);
  } else {
    res.status(403).send({ error: 'wrong credentials or user not found' });
  }
});

// $FlowFixMe
app.post('/signup', async (req, res) => {
  const { username, password, fname, lname, email } = req.body;
  const user = await User.create({
    username,
    password,
    fname,
    lname,
    email,
  });

  if (user && user) {
    res.send(user);
  } else {
    res.status(403).send({ error: 'Error while creating new user' });
  }
});

// $FlowFixMe
app.post('/file', (req, res) => {
  console.log(req.body);
  res.end();
});

// $FlowFixMe
app.get('/logout', (req, res) => {
  res.status(200).send({ isLoggedOut: true });
});

app.listen(PORT, console.log(`App works on ${PORT}...`)); // eslint-disable-line no-console
