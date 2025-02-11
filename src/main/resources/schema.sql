CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(50) UNIQUE,
--     role VARCHAR(50) NOT NULL CHECK (role IN ('USER', 'ADMIN')),
    department VARCHAR(100)

    );

CREATE TABLE IF NOT EXISTS leave_types(
     id SERIAL PRIMARY KEY,
     leave_type_name VARCHAR(50),
     description VARCHAR(250),
     max_days INT NOT NULL
);

CREATE TABLE IF NOT EXISTS leave_requests(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    leave_type_id INT NOT NULL,
    start_date DATE,
    end_date DATE,
    status VARCHAR NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    reason VARCHAR(250),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (leave_type_id) REFERENCES leave_types(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS leave_balances(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    leave_type_id INT NOT NULL,
    leave_year INT,
    remaining_days INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (leave_type_id) REFERENCES leave_types(id) ON DELETE CASCADE
);

