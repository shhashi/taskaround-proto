INSERT INTO registration_codes (registration_code, expired_in, created_at)
VALUES (substr(md5(random()::text), 0, 32), now() + INTERVAL '3 DAY', now());
