
-- H2
-- Deletes the public schema (which essentially deletes all tables)
DROP ALL OBJECTS DELETE FILES;
-- Restores necessary database privileges
GRANT ALL ON SCHEMA PUBLIC TO SA;