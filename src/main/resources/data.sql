INSERT INTO [projectz].[Departments](Description, Name) VALUES ('IT', 'IT'), ('Finance', 'Finance'), ('Marketing', 'Marketing')

INSERT INTO [projectz].[Roles](Description, Name) VALUES ('Staff', 'Staff'), ('Admin', 'Admin'), ('Finance', 'Finance'), ('Manager', 'Manager')

INSERT INTO [projectz].[Users] (ID, isActive, DepartmentID, RoleID, EmployeeNumber, Email, FullName, Password)
VALUES ('a1b2c3d4-e5f6-7890-1234-567890abcdef', 1, 1, 2, 'EMP0001', 'admin@example.com', 'Admin User', 'P@sword1!');