-- Sample values for database testing.

USE gaucha_urn;

INSERT INTO voters (first_name, last_name, date_birth) VALUES
	("Aldo", "Franquez", "1997-08-29"),
	("Another", "User", "2003-04-30")
;

/*
	Candidate types based on the homework's specifications, IDs are as follows:
	
	* 0 = President
	* 1 = Governor
	* 2 = Senator
	* 3 = Federal representative
	* 4 = Intrastate representative
*/
INSERT INTO candidate_types (name, notes) VALUES
	("President", "N/A"),
	("Governor", "N/A"),
	("Senator", "N/A"),
	("Federal representative", "N/A"),
	("Intrastate representative", "N/A")
;

-- Names of important people from Uruguay, I just chose random ones.
INSERT INTO candidates (first_name, last_name, date_birth) VALUES
	("Daniel", "Martínez", "1957-02-23"),
	("Luis", "Lacalle Pou", "1973-08-11"),
	("Ernesto", "Talvi", "1957-06-10"),
	("José", "Mujica", "1935-05-20"),
	("Beatriz", "Argimón", "1961-08-14"),
	("Guido", "Manini Ríos", "1958-08-20")
;

-- Inserts turns 1 and 2, dates are used to control voting constraints and are updated via the HTTP requests.
INSERT INTO turn (dt_begin, dt_end, is_active) VALUES
	(NOW(), NULL, true),
	(NULL, NULL, false)
;