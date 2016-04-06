CREATE TABLE users (
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name VARCHAR(25) NOT NULL,
	password VARCHAR(64),
	db_user BOOLEAN DEFAULT FALSE,
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT unique_name UNIQUE (name)
);

CREATE TABLE documents (
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	category VARCHAR(25) NOT NULL,
	description VARCHAR(1000),
	due_date DATE,
	deleted BOOLEAN NOT NULL,
	owner_fk INTEGER,
	CONSTRAINT documents_pk PRIMARY KEY (id),
	CONSTRAINT document_owner_fk FOREIGN KEY (owner_fk) REFERENCES users(id)
);

CREATE TABLE attachments (
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name VARCHAR(250) NOT NULL,
	upload_time TIMESTAMP,
	document_fk INTEGER,
	temp BOOLEAN NOT NULL,
	CONSTRAINT attachments_pk PRIMARY KEY (id),
	CONSTRAINT document_attachments_fk FOREIGN KEY (document_fk) REFERENCES documents(id)
);