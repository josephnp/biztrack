SET IDENTITY_INSERT [BizTrack].[Department] ON;
    INSERT INTO [BizTrack].[Department] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'1', N'Admin', N'Ini deskripsi departement admin', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
SET IDENTITY_INSERT [BizTrack].[Department] OFF;

SET IDENTITY_INSERT [BizTrack].[MstRole] ON;
    INSERT INTO [BizTrack].[MstRole] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'1', N'Admin', N'Ini deskripsi Role admin', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
SET IDENTITY_INSERT [BizTrack].[MstRole] OFF;

SET IDENTITY_INSERT [BizTrack].[MstMenu] ON;
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'1', N'Department', N'Departemen', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'2', N'Role', N'Role', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'3', N'Menu', N'Menu', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'4', N'User', N'User', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'5', N'Status', N'Status', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'6', N'Request', N'Request', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'7', N'Approve Request', N'Approve Request', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'8', N'Report', N'Report', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstMenu] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'9', N'Approve Report', N'Approve Report', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
SET IDENTITY_INSERT [BizTrack].[MstMenu] OFF;

SET IDENTITY_INSERT [BizTrack].[MstUser] ON;
    INSERT INTO [BizTrack].[MstUser] ([ID], [RoleID], [DepartmentID], [EmployeeNumber], [FullName], [Email], [Password], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'1',  N'1',  N'1', N'EMP0001', N'Admin', N'admin@gmail.com', N'$2a$11$PWtX9gJMwBFfrLbrzl5FIOTp7OID0t.HrDynycZejLr0MAMQ/x7E6', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
SET IDENTITY_INSERT [BizTrack].[MstUser] OFF;

INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'1');
INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'2');
INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'3');
INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'4');
INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'5');
INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'6');
INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'7');
INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'8');
INSERT INTO [BizTrack].[MapRoleMenu] ([IDRole], [IDMenu]) VALUES (N'1', N'9');

SET IDENTITY_INSERT [BizTrack].[MstStatus] ON;
    INSERT INTO [BizTrack].[MstStatus] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'1', N'Pending Manager', N'Ini deskripsi untuk status pending manager', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstStatus] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'2', N'Cancel', N'Ini deskripsi untuk status cancel', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstStatus] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'3', N'Pending Finance', N'Ini deskripsi untuk status pending finance', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstStatus] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'4', N'Reject', N'Ini deskripsi untuk status reject', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
    INSERT INTO [BizTrack].[MstStatus] ([ID], [Name], [Description], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES ( N'5', N'Approve', N'Ini deskripsi untuk status approve', N'1', N'2025-05-29 23:45:57.000000', NULL, NULL);
SET IDENTITY_INSERT [BizTrack].[MstStatus] OFF;