CREATE TABLE problems
(id SERIAL PRIMARY KEY,
 title VARCHAR(50) NOT NULL,
 submitted TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 difficulty INT NOT NULL DEFAULT 0,
 submitter VARCHAR(50) NOT NULL,
 times_solved INT NOT NULL DEFAULT 0,
 description VARCHAR(500) NOT NULL,
 code VARCHAR(300) NOT NULL,
 solved BOOLEAN NOT NULL DEFAULT false);

INSERT INTO problems (title, submitter, description, code)
 values ('This is a test problem.', 'Alice', 'Test description', '(+ 1 1)');

INSERT INTO problems (title, submitter, description, code)
 values ('The 2nd Problem', 'Bob', 'Another description', '(* 2 2)');

INSERT INTO problems (title, submitter, description, code)
 values ('The 3rd and final problem', 'Charles', 'No description for you!', '(print "hello"');