const express = require('express');
const app = express();


const cors = require('cors');
app.use(cors({
  origin: [
    "http://localhost:3000",
    "https://to-do-mu-dusky.vercel.app/"
  ], // frontend URL
  credentials: true                // 👈 allow cookies
}));

const cookieParser = require("cookie-parser");
app.use(cookieParser());
app.use(express.json());


const tasks = require('./routes/tasks.routes')
app.use('/api/tasks', tasks)

const auth = require('./routes/auth.routes')
app.use('/api/auth', auth);

app.get('/',(req,res)=>{
  res.send("Server is listening")
})



module.exports = app;