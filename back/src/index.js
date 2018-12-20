// @flow

import express from 'express';
import path from 'path';
// $FlowFixMe
import bodyParser from 'body-parser';
// $FlowFixMe
import cookieParser from 'cookie-parser';
// $FlowFixMe
import morgan from 'morgan';
import multer from 'multer';
import { User } from './User';
import DB from './db';

DB.init();

const PORT = process.env.PORT || 8090;
const app = express();

app.use(bodyParser.json({ type: 'application/json' }));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(morgan('tiny'));

const upload = multer({ dest: path.resolve(__dirname, '../uploads') })

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
app.post('/file', upload.single('image'), async (req, res) => {
  console.log(req.file);
  console.log(req.body);
  const user = await User.findOne({username: req.body.username}).exec();
  const im = user.images;
  user.images = [req.file.filename, ...im];
  await user.save();
  res.send('ok');
});

// $FlowFixMe
app.get('/logout', (req, res) => {
  res.status(200).send({ isLoggedOut: true });
});

app.listen(PORT, console.log(`App works on ${PORT}...`)); // eslint-disable-line no-console
