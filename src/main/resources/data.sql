INSERT INTO authors (name) VALUES ('Leo Tolstoy'), ('Fyodor Dostoevsky');
INSERT INTO genres (name) VALUES ('Novel'), ('Philosophy');
INSERT INTO books (title, author_id, genre_id) VALUES
  ('War and Peace', 1, 1),
  ('Crime and Punishment', 2, 2);