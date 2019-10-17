 CREATE TABLE POST(
 ID NUMBER,
 CONSTRAINT ID_POST PRIMARY KEY (ID),
 MESSAGE  VARCHAR(200) NOT NULL,
 DATE_POSTED TIMESTAMP,
 USER_POSTED_ID NUMBER,
 CONSTRAINT USER_POSTED_ID_FK FOREIGN KEY (USER_POSTED_ID) REFERENCES USER_TABLE(ID));