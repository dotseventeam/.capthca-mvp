
DROP TABLE IF EXISTS app;
CREATE TABLE app
(
    app_id VARCHAR(25),
    app_secret VARCHAR(25)
);

DROP TABLE IF EXISTS captcha_test;
CREATE TABLE captcha_test
(
    base64_image TEXT,
    correct_answer1 VARCHAR(25),
    correct_answer2 VARCHAR(25)

);
DROP TABLE IF EXISTS token;
CREATE TABLE token
(
    captcha_id VARCHAR(25),
    app_id VARCHAR(25),
    hashed_correct_answers VARCHAR(300),
    expiry_time DATETIME
);