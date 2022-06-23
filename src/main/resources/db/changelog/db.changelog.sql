CREATE TABLE IF NOT EXISTS "clients"
(
    client_id SERIAL PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS "orders"
(
    order_id SERIAL PRIMARY KEY,
    description VARCHAR (100),
    client_id INTEGER REFERENCES clients (client_id),
    placement_data DATE NOT NULL,
    closing_data DATE,
    phase INTEGER
);

CREATE TABLE IF NOT EXISTS tasks
(
    task_id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders (order_id),
    worker_id INTEGER REFERENCES workers (worker_id),
    job_id INTEGER REFERENCES jobs (job_id),
    creator_worker_id INTEGER REFERENCES workers (worker_id),
    part_factor INTEGER,
    time_create TIMESTAMP,
    comment VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS workers
(
        worker_id SERIAL PRIMARY KEY,
        user_id INTEGER NOT NULL REFERENCES users (user_id),
        firstname VARCHAR(20) NOT NULL,
        lastname VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    user_id SERIAL PRIMARY KEY,
    login VARCHAR(15),
    password_hash INTEGER
);

CREATE TABLE IF NOT EXISTS jobs
(
    job_id SERIAL PRIMARY KEY,
    description VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS "prices"
(
    price_id SERIAL PRIMARY KEY,
    job_id INTEGER REFERENCES jobs (job_id),
    client_price DOUBLE PRECISION,
    worker_price DOUBLE PRECISION,
    date_since DATE
);

CREATE TABLE IF NOT EXISTS equipments
(
    equipment_id BIGSERIAL PRIMARY KEY,
    client_id INTEGER REFERENCES clients (client_id),
    address_id INTEGER REFERENCES address (address_id),
    description VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS "address"
(
    address_id SERIAL PRIMARY KEY,
    city VARCHAR(20),
    street VARCHAR(50),
    house VARCHAR(6),
    room VARCHAR(6)
)
