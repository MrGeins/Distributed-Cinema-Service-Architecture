let express = require('express');
let path = require('path');
const { engine } = require('express-handlebars');
let createError = require('http-errors');

const axios = require('axios');
let logger = require('morgan');
let cookieParser = require('cookie-parser');

let indexRouter = require('./routes/index');
let usersRouter = require('./routes/users');
let actorsRouter = require('./routes/actors')

const swaggerUi = require('swagger-ui-express');
const swaggerJsDoc = require('swagger-jsdoc');
const session = require("express-session");

let app = express();


const options = {
    definition: {
        openapi: '3.1.0',
        info: {
            title: 'API Title',
            version: '1.0',
        },
        servers: [{ url: 'http://localhost:3000', description: 'Main Server'}],
    },
    apis: ['./routes/*.js'],
};

const swaggerSpecification = swaggerJsDoc(options);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpecification));

// View engine setup (Handlebars)
app.engine('hbs', engine({
    extname: '.hbs',
    defaultLayout: 'layout',
    layoutsDir: path.join(__dirname, 'views/layouts'),
    partialsDir: path.join(__dirname, 'views/partials'),
    helpers: {
        section: function(name, options) {
            if (!this._sections) this._sections = {};
            this._sections[name] = options.fn(this);
            return null;
        },
        replace: function(str) {
            if(typeof str === 'string')
                return str.replace(/\s+/g, '+');
            return str;
        }
    },
    runtimeOptions: {
        allowProtoPropertiesByDefault: true,
        allowProtoMethodsByDefault: true,
    }
}));


app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

// Middleware
app.use(logger('dev'));
app.use(cookieParser());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, 'public')));

const sessionMiddleware = session({
    secret: 'IUM-TWeb-2026',
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false }
});

app.use(sessionMiddleware);
app.use((req, res, next) => {

    res.locals.user = req.session.user || null;
    next();
});


app.sessionMiddleware = sessionMiddleware;

// Routes
app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/actors', actorsRouter);

// Catch 404 and forward to error handler
app.use(function(req, res, next) {
    next(createError(404));
});

// Error handler
app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error'); // Assicurati di avere views/error.hbs
});

module.exports = app;

