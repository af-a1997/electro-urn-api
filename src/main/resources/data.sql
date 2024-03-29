-- Sample values for database testing.

USE gaucha_urn;

INSERT INTO voters (first_name, last_name, date_birth) VALUES
	("Aldo", "Franquez", "1997-08-29"),
	("Another", "User", "2003-04-30")
;

/*
	Candidate types based on the homework's specifications, IDs are as follows:
	
	* 1 = President
	* 2 = Governor
	* 3 = Senator
	* 4 = Federal representative
	* 5 = Intrastate representative
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

-- Inserts some sample votes, these are used to quickly test getting most voted candidates by role, such as most voted for president.
INSERT INTO vote_reg (dt_vote, turn_id, candidate_to, voter_id, vt_cndt_id) VALUES
    (NOW(), 1, 2, 1, 2),
    (NOW(), 1, 2, 2, 2),
    (NOW(), 1, 4, 1, 3),
    (NOW(), 1, 4, 2, 3),
    (NOW(), 1, 4, 2, 4),
    (NOW(), 1, 5, 1, 4),
    (NOW(), 1, 5, 1, 4)
;