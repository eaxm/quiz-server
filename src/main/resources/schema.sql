CREATE TABLE IF NOT EXISTS account (
    user_id SERIAL PRIMARY KEY,
    username TEXT UNIQUE,
    password TEXT,
    register_date TIMESTAMP,
    salt TEXT
);

CREATE TABLE IF NOT EXISTS quiz (
    quiz_id SERIAL PRIMARY KEY,
    quiz_name TEXT,
    creation_date TIMESTAMP,
    creator_id INTEGER REFERENCES account (user_id)
);

CREATE TABLE IF NOT EXISTS question (
    question_id SERIAL PRIMARY KEY,
    question_text TEXT,
    quiz_id INTEGER REFERENCES quiz (quiz_id)
);

CREATE TABLE IF NOT EXISTS answer (
    answer_id SERIAL PRIMARY KEY,
    answer_text TEXT,
    correct_answer BOOLEAN,
    question_id INTEGER REFERENCES question (question_id)
);