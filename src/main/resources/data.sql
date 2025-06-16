INSERT INTO [BizTrack].[Departments] (Description, Name)
VALUES ('IT', 'IT'), ('Finance', 'Finance'), ('Marketing', 'Marketing')

INSERT INTO [BizTrack].[Roles] (Description, Name)
VALUES ('Staff', 'Staff'), ('Admin', 'Admin'), ('Finance', 'Finance'), ('Manager', 'Manager')

INSERT INTO [BizTrack].[Users] (isActive, DepartmentID, RoleID, EmployeeNumber, Email, FullName, Password)
VALUES (1, 1, 2, 'EMP0001', 'admin@example.com', 'Admin User', '$2a$12$SB5VfagJxtdDvc4Ox8WeseM3/WDt9RtfiyU8iHVeBxE7tWZAcae/2');

INSERT INTO [BizTrack].[Status] (Name, Description)
VALUES
  ('Pending Manager', 'Menunggu approval Manager'),
  ('Pending Finance', 'Menunggu approval Finance'),
  ('Reject Manager', 'Ditolak oleh Manager'),
  ('Reject Finance', 'Ditolak oleh Finance'),
  ('Offering', 'Offering dari Manager'),
  ('Approve', 'Sukses approval');
