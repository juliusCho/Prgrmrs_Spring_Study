INSERT INTO users (email, passwd, login_count, last_login_at, create_at)
SELECT 'test00@gmail.com', '$2a$10$mzF7/rMylsnxxwNcTsJTEOFhh1iaHv3xVox.vpf6JQybEhE4jDZI.', 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT seq FROM users WHERE email = 'test00@gmail.com');
INSERT INTO users (email, passwd, login_count, last_login_at, create_at)
SELECT 'test01@gmail.com', '$2a$10$Mu/akK4gI.2RHm7BQo/kAO1cng2TUgxpoP.zBbPOeccVGP4lKVGYy', 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT seq FROM users WHERE email = 'test01@gmail.com');
INSERT INTO users (email, passwd, login_count, last_login_at, create_at)
SELECT 'test02@gmail.com', '$2a$10$hO38hmoHN1k7Zm3vm95C2eZEtSOaiI/6xZrRAx8l0e78i9.NK8bHG', 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT seq FROM users WHERE email = 'test02@gmail.com');


INSERT INTO posts (user_seq, contents, like_count, comment_count, create_at)
SELECT 1, 'test01 first post', 1, 1, '2019-03-01 13:10:00'
WHERE NOT EXISTS (SELECT seq FROM posts WHERE user_seq = 1 AND contents = 'test01 first post');
INSERT INTO posts (user_seq, contents, like_count, comment_count, create_at)
SELECT 1, 'test01 second post', 0, 0, '2019-03-12 09:45:00'
WHERE NOT EXISTS (SELECT seq FROM posts WHERE user_seq = 1 AND contents = 'test01 second post');
INSERT INTO posts (user_seq, contents, like_count, comment_count, create_at)
SELECT 1, 'test01 third post', 0, 0, '2019-03-20 19:05:00'
WHERE NOT EXISTS (SELECT seq FROM posts WHERE user_seq = 1 AND contents = 'test01 third post');
INSERT INTO posts (user_seq, contents, like_count, comment_count, create_at)
SELECT 2, 'test02 post', 0, 0, '2019-03-20 15:13:20'
WHERE NOT EXISTS (SELECT seq FROM posts WHERE user_seq = 2 AND contents = 'test02 post');


INSERT INTO comments (user_seq, post_seq, contents, create_at)
SELECT 1, 1, 'first comment', '2019-03-01 13:15:00'
WHERE NOT EXISTS (SELECT seq FROM comments WHERE user_seq = 1 AND post_seq = 1 AND contents = 'first comment');
INSERT INTO comments (user_seq, post_seq, contents, create_at)
SELECT 2, 4, 'first comment', '2019-03-01 13:15:00'
WHERE NOT EXISTS (SELECT seq FROM comments WHERE user_seq = 2 AND post_seq = 4 AND contents = 'first comment');


INSERT INTO connections (user_seq, target_seq, granted_at, create_at)
SELECT 1, 2, '2019-03-31 13:00:00', '2019-03-31 00:10:00'
WHERE NOT EXISTS (SELECT seq FROM connections WHERE user_seq = 1 AND target_seq = 2 AND granted_at = '2019-03-31 13:00:00');