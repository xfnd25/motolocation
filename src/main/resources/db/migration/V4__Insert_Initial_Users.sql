INSERT INTO USERS (ID, USERNAME, PASSWORD, ROLE)
VALUES (USERS_SEQ.nextval, 'admin', '$2a$10$fP22bQy7sT41iM54G.3v4.P.9w/Yj.HM.g/9.3.T.tgl9e.e/zY.K', 'ROLE_ADMIN');

INSERT INTO USERS (ID, USERNAME, PASSWORD, ROLE)
VALUES (USERS_SEQ.nextval, 'user', '$2a$10$b7b.s.G.q.p1y.qg.01h.uX.Vb.5u.1b.i2t.f.h.a.5c.f.i.s.s', 'ROLE_USER');