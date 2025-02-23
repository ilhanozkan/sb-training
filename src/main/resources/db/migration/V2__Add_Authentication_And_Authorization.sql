ALTER TABLE users
ADD COLUMN IF NOT EXISTS email VARCHAR(255) UNIQUE,
ADD COLUMN IF NOT EXISTS password VARCHAR(255),
ADD COLUMN IF NOT EXISTS role VARCHAR(50);

-- Update existing rows to have default values
UPDATE users SET email = CONCAT(name, '@example.com') WHERE email IS NULL;
UPDATE users SET password = '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG' WHERE password IS NULL; -- Default password: 'password'
UPDATE users SET role = 'USER' WHERE role IS NULL;

-- Create default Admin user
INSERT INTO users (id, name, email, password, role) VALUES (gen_random_uuid(), 'Admin', 'admin@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN');

-- Now make the columns NOT NULL
ALTER TABLE users
ALTER COLUMN email SET NOT NULL,
ALTER COLUMN password SET NOT NULL,
ALTER COLUMN role SET NOT NULL; 