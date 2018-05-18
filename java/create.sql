CREATE TABLE report (
    id int primary key NOT NULL,
    experimentId varchar(50) NOT NULL
);

CREATE TABLE image (
    id int primary key NOT NULL,
    report_id int NOT NULL references report(id),
    metric varchar(20) NOT NULL,
    chartTypes varchar(20) NOT NULL,
    imageAsBytea bytea NOT NULL
);

CREATE SEQUENCE hibernate_sequence START 1;
