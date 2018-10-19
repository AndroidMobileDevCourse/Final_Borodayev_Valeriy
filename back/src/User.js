// @flow

import mongoose from 'mongoose';
import DB from './db';

export const UserSchema = new mongoose.Schema(
  {
    username: String,
    fname: String,
    lname: String,
    email: String,
    password: String,
  },
  {
    timestamps: true,
    collection: 'users',
  }
);

export const User = DB.data.model('User', UserSchema);
