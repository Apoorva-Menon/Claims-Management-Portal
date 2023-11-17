create schema claimsmanagement;

create table Persons(
	ID int,
    `Password` varchar(30),
    Email varchar(30),
    CONSTRAINT PK_Persons PRIMARY KEY (ID)
);

create table Policy(
	PID int,
    Policy_Type varchar(30),
    Tenure int,
    Cap_Amount int,
    Premium int,
    CONSTRAINT PK_Policy PRIMARY KEY (PID)
);

create table Hospital(
	HID int,
    `Name` varchar(30),
    Location varchar(45),
    CONSTRAINT PK_Hospital PRIMARY KEY (HID)
);

create table Benefits(
	BID int,
    `Name` varchar(40),
    CONSTRAINT PK_Benefits PRIMARY KEY (BID)
);

create table Benefit_Provider(
	PolicyID int,
    BenefitID int,
    CONSTRAINT PK_Benefit_Provider PRIMARY KEY (PolicyID,BenefitID),
    CONSTRAINT FK_Benefit_Provider_BenefitID FOREIGN KEY (BenefitID) REFERENCES Benefits(BID),
    CONSTRAINT FK_Benefit_Provider_PolicyID FOREIGN KEY (PolicyID) REFERENCES Policy(PID)
);

create table Member_Table(
	`MID` int,
    `Name` varchar(30),
    Age int,
    Address varchar(40),
    PolicyID int,
    Subscription_Date date,
    Premium_Paid boolean,
    Premium_Last_Date date,
    CONSTRAINT PK_Member_Table PRIMARY KEY (`MID`),
    CONSTRAINT FK_Member_Table_PolicyID FOREIGN KEY (PolicyID) REFERENCES Policy(PID),
    CONSTRAINT FK_Member_Table_MID FOREIGN KEY (`MID`) REFERENCES Persons(ID)
);

create table Provider_Policy(
	PPID int,
    HID int,
    CONSTRAINT PK_Provider_Policy PRIMARY KEY (PPID,HID),
    CONSTRAINT FK_PPID FOREIGN KEY (PPID) REFERENCES Policy(PID),
    CONSTRAINT FK_HID FOREIGN KEY (HID) REFERENCES Hospital(HID)
);

create table Claims(
	CID int,
    `Status` varchar(20),
    Claim_Amount int,
    Remarks varchar(45),
    HospitalId int,
    PolicyID int,
    BenefitID int,
    
    CONSTRAINT PK_Claims PRIMARY KEY (CID),
    CONSTRAINT FK_Claims_HospitalId FOREIGN KEY (HospitalId) REFERENCES Hospital(HID),
    CONSTRAINT FK_Claims_PolicyID FOREIGN KEY (PolicyID) REFERENCES Policy(PID)
);

create table Bills(MID int,
PID int,
LastPaidDate date,
DueAmount int,
LateCharge int,
DueDate date);

insert into Bills(mid, pid,  last_paid_date, due_amount, late_charge, due_date) values(1, 101, '2020/06/01', 10000, 800, '2021/02/01');
insert into Bills(mid, pid,  last_paid_date, due_amount, late_charge, due_date) values(2, 102, '2019/12/10', 50500, 6500, '2020/12/10');
insert into Bills(mid, pid,  last_paid_date, due_amount, late_charge, due_date) values(3, 103, '2021/07/20', 12000, 0, '2022/07/30');
insert into Bills(mid, pid,  last_paid_date, due_amount, late_charge, due_date) values(4, 102, '2021/01/01', 8000, 0, '2021/08/10');
insert into Bills(mid, pid,  last_paid_date, due_amount, late_charge, due_date) values(5, 101, '2020/01/05', 25000, 4000, '2021/07/05');










