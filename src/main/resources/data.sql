TRUNCATE TABLE access_level CASCADE;

INSERT INTO access_level (value, description) VALUES (0, 'admin') ON CONFLICT DO NOTHING;
INSERT INTO access_level (value, description) VALUES (1, 'basic') ON CONFLICT DO NOTHING;