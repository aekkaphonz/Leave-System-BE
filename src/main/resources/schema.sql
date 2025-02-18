CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE,
    department VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS leave_types(
     id SERIAL PRIMARY KEY,
     leave_type_name VARCHAR(50) NOT NULL,
     description VARCHAR(250),
     max_days INT NOT NULL
);

CREATE TABLE IF NOT EXISTS leave_requests(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    leave_type_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    reason VARCHAR(250),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (leave_type_id) REFERENCES leave_types(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS leave_balances(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    leave_type_id INT NOT NULL,
    leave_year INT NOT NULL,
    remaining_days INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (leave_type_id) REFERENCES leave_types(id) ON DELETE CASCADE
);

