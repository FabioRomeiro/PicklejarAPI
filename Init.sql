CREATE TABLE USER (
    USR_ID INT AUTO_INCREMENT NOT NULL,
    USR_NAME VARCHAR(60) NOT NULL,
    USR_EMAIL VARCHAR(60) NOT NULL,
    USR_PASSWORD VARCHAR(60) NOT NULL,
    USR_PICTURE VARCHAR(255) NOT NULL
);

CREATE TABLE PASSWORD (
    PWD_ID INT AUTO_INCREMENT NOT NULL,
    PWD_NAME VARCHAR(60) NOT NULL,
    PWD_USERNAME VARCHAR(60) NOT NULL,
    PWD_PASSWORD VARCHAR(60) NOT NULL,
    PWD_FAVORITE BOOLEAN NOT NULL,
    PWD_LINK VARCHAR(255),
    PWD_IMAGE VARCHAR(255),
    PWD_STATUS VARCHAR(30) NOT NULL,
    PWD_ACTIVE BOOLEAN NOT NULL,
    PWD_LASTACCESS DATETIME NOT NULL,
    PWD_LASTUPDATE DATETIME NOT NULL,
    PWD_CREATEDAT DATETIME NOT NULL,
    USR_ID INT NOT NULL,
    PRIMARY KEY(PWD_ID),
    FOREIGN KEY(USR_ID) REFERENCES USER(USR_ID),
);

CREATE TABLE LOG (
    LOG_ID INT AUTO_INCREMENT NOT NULL,
    LOG_TYPE VARCHAR(60) NOT NULL,
    LOG_METADATA VARCHAR(255),
    LOG_CREATEDAT DATETIME NOT NULL,
    LOG_USERAGENT VARCHAR(255) NOT NULL,
    FOREIGN KEY(USR_ID) REFERENCES USER(USR_ID),
    FOREIGN KEY(PWD_ID) REFERENCES PASSWORD(PWD_ID)
);

CREATE TABLE ROLE (
    ROL_ID INT AUTO_INCREMENT NOT NULL,
    ROL_NAME VARCHAR(60) NOT NULL
);

INSERT INTO ROLE (ROL_NAME) VALUES ('ROLE_ADMIN');

CREATE TABLE USER_ROLES (
    USER_USR_ID INT NOT NULL,
    ROLES_ROL_ID INT NOT NULL,
    FOREIGN KEY(USER_USR_ID) REFERENCES USER(USR_ID),
    FOREIGN KEY(ROLES_ROL_ID) REFERENCES ROLE(ROL_ID)
);