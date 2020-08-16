INSERT INTO users (email, passwd, login_count, last_login_at, create_at)
SELECT 'user1@prgrmrs6.com', '1', 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT seq FROM users WHERE email = 'user1@prgrmrs6.com');
SELECT 'user2@prgrmrs6.com', '1', 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT seq FROM users WHERE email = 'user2@prgrmrs6.com');
SELECT 'user3@prgrmrs6.com', '1', 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT seq FROM users WHERE email = 'user3@prgrmrs6.com');