DROP TABLE PRODUCT CASCADE CONSTRAINTS;

CREATE TABLE PRODUCT (
PRODUCT_ID VARCHAR2(20) PRIMARY KEY,
PRODUCT_NAME VARCHAR2(20) NOT NULL,
PRICE NUMBER NOT NULL,
DESCRIPTION VARCHAR2(20),
STOCK NUMBER NOT NULL
);

COMMENT ON COLUMN PRODUCT.PRODUCT_ID IS '상품아이디';
COMMENT ON COLUMN PRODUCT.PRODUCT_NAME IS '상품명';
COMMENT ON COLUMN PRODUCT.PRICE IS '상품가격';
COMMENT ON COLUMN PRODUCT.DESCRIPTION IS '상품상세정보';
COMMENT ON COLUMN PRODUCT.STOCK IS '재고';

DROP TABLE MEMBER CASCADE;

CREATE TABLE MEMBER(
USERID VARCHAR2(20) NOT NULL UNIQUE,
USERPWD VARCHAR2(20) NOT NULL,
PRODUCT_ID VARCHAR2(20) REFERENCES PRODUCT(PRODUCT_ID)
);


INSERT INTO PRODUCT
VALUES ('nb_ss7', '삼성노트북', 1570000, '시리즈7', 10);

INSERT INTO PRODUCT
VALUES ('nb_ama4', '맥북에어', 1200000, 'xcode4', 20);

INSERT INTO PRODUCT
VALUES ('pc_ibm', 'ibmPC', 750000, 'windows10', 5);

INSERT INTO PRODUCT
VALUES ('ac_tdp', '한성노트북', 850000, 'bossmonster9', 7);

INSERT INTO PRODUCT
VALUES ('qu_pzm', 'MSI', 1000000, 'modern14', 13);

INSERT INTO PRODUCT
VALUES ('xy_avd', 'hpenvy', 999000, 'x360', 3);


INSERT INTO MEMBER
VALUES ('admin', 'admin', 'nb_ss7');

INSERT INTO MEMBER
VALUES ('user01', 'pass01', 'nb_ama4');

INSERT INTO MEMBER
VALUES ('user02', 'pass02', 'pc_ibm');

INSERT INTO MEMBER
VALUES ('user03', 'pass03', 'ac_tdp');

INSERT INTO MEMBER
VALUES ('user04', 'pass04', 'nb_ss7');

INSERT INTO MEMBER
VALUES ('user05', 'pass05', 'xy_avd');

COMMIT;

/*
SELECT PRODUCT_ID
     , PRODUCT_NAME
     , PRICE
     , DESCRIPTION
     , STOCK
FROM PRODUCT;
*/